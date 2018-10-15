package uk.ac.reigate.api;

import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE
import io.swagger.annotations.Api
import io.swagger.annotations.ApiOperation
import io.swagger.annotations.ApiParam
import io.swagger.annotations.ApiResponse
import io.swagger.annotations.ApiResponses

import javax.validation.Valid

import org.apache.log4j.Logger
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.stereotype.Controller
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestMethod

import uk.ac.reigate.domain.Staff
import uk.ac.reigate.domain.academic.Course;
import uk.ac.reigate.domain.academic.CourseGroup
import uk.ac.reigate.domain.academic.Student
import uk.ac.reigate.domain.register.AttendanceCode;
import uk.ac.reigate.domain.register.Register
import uk.ac.reigate.domain.register.RegisterMark
import uk.ac.reigate.dto.RegisterMarkDto
import uk.ac.reigate.exceptions.InvalidDataException
import uk.ac.reigate.exceptions.NotFoundException
import uk.ac.reigate.model.SearchResult
import uk.ac.reigate.services.AttendanceCodeService
import uk.ac.reigate.services.CourseGroupService
import uk.ac.reigate.services.CourseService
import uk.ac.reigate.services.RegisterMarkService
import uk.ac.reigate.services.RegisterService
import uk.ac.reigate.services.StaffService
import uk.ac.reigate.services.StudentService


@Controller
@RequestMapping(value = "/api/registerMarks", produces = [ APPLICATION_JSON_VALUE ])
@Api(value = "/api/registerMarks", description = "the registerMarks API")
public class RegisterMarksApi {
    
    private static final Logger LOGGER = Logger.getLogger(RegisterMarksApi.class);
    
    @Autowired
    private final RegisterMarkService registerMarkService;
    
    @Autowired
    private final RegisterService registerService;
    
    @Autowired
    private final CourseGroupService courseGroupService;
    
    @Autowired
    private final CourseService courseService;
    
    @Autowired
    private final StudentService studentService;
    
    @Autowired
    private final StaffService staffService;
    
    @Autowired
    private final AttendanceCodeService attendanceCodeService;
    
    /**
     * Default NoArgs constructor
     */
    RegisterMarksApi() {}
    
    /**
     * Autowired constructor
     */
    RegisterMarksApi(RegisterMarkService registerMarkService) {
        this.registerMarkService = registerMarkService;
    }
    
    /**
     * The registerMarksGet method is used to retrieve a full list of all the RegisterMarkDto objects
     *
     * @return A ResponseEntity with the corresponding list of RegisterMarkDto objects
     * @throws NotFoundException if nothing is found then the the NotFoundException is thrown.
     */
    @ApiOperation(value = "Collection of RegisterMark entities", notes = "A GET request to the RegisterMarks endpoint returns an array of all the registerMarks in the system.", response = RegisterMarkDto.class, responseContainer = "List")
    @ApiResponses(value = [
        @ApiResponse(code = 200, message = "An array of registerMarks")
    ])
    @RequestMapping(value = "", produces = ["application/json"], method = RequestMethod.GET)
    public ResponseEntity<List<RegisterMarkDto>> registerMarksGet() throws NotFoundException {
        LOGGER.info("** RegisterMarksApi - registerMarksGet");
        List<RegisterMark> registerMarks = registerMarkService.findAll();
        return new ResponseEntity<List<RegisterMarkDto>>(RegisterMarkDto.mapFromRegisterMarksEntities(registerMarks), HttpStatus.OK);
    }
    
    /**
     * The registerMarksPost method is used to create a new instance of a RegisterMark from the supplied RegisterMarkDto
     *
     * @param registerMark the RegisterMarkDto to use to create the new RegisterMark object
     * @return A ResponseEntity with the newly created RegisterMark object
     * @throws InvalidDataException if there is an issue with the data provided in the RequestBody then an InvalidDataException is thrown
     */
    @ApiOperation(value = "Creates a new RegisterMark entity", notes = "A POST request to the RegisterMarks endpoint with a RegisterMark object in the request body will create a new RegisterMark entity in the database.", response = RegisterMarkDto.class)
    @ApiResponses(value = [
        @ApiResponse(code = 201, message = "Returns a copy of the created RegisterMark entity including the registerMarkId that has just been created."),
        @ApiResponse(code = 400, message = "Returns an Error object stating the request body does not match the expected input.")
    ])
    @RequestMapping(value = "", produces = ["application/json"], method = RequestMethod.POST)
    public ResponseEntity<RegisterMarkDto> registerMarksPost(@ApiParam(value = "The RegisterMark object to be created, without the registerMarkId fields", required = true) @RequestBody @Valid RegisterMarkDto registerMark) throws NotFoundException, InvalidDataException {
        LOGGER.info("** RegisterMarksApi - registerMarksPOST");
        if (registerMark.id != null) {
            throw new InvalidDataException(400, "No ID field should be provided when creating")
        }
        Course course
        if(registerMark.courseId != null) {
            course = courseService.findById(registerMark.courseId)
        }
        CourseGroup courseGroup
        if(registerMark.courseGroupId != null) {
            courseGroup = courseGroupService.findById(registerMark.courseGroupId)
        }
        Register register
        if(registerMark.registerId != null) {
            register = registerService.findById(registerMark.registerId)
        }
        Student student
        if(registerMark.studentId != null) {
            student = studentService.findById(registerMark.studentId)
        }
        AttendanceCode attendanceCode
        if(registerMark.attendanceCodeId != null) {
            attendanceCode = attendanceCodeService.findById(registerMark.attendanceCodeId)
        }
        RegisterMark registerMarkToSave = RegisterMarkDto.mapToRegisterMarkEntity(registerMark, course, courseGroup, register, student, attendanceCode)
        RegisterMark registerMarkSaved = registerMarkService.save(registerMarkToSave)
        return new ResponseEntity<RegisterMarkDto>(RegisterMarkDto.mapFromRegisterMarkEntity(registerMarkSaved), HttpStatus.CREATED);
    }
    
    /**
     * The registerMarksRegisterMarkIdGet method is used to retrieve an instance of a RegisterMarkDto object as identified by the registerMarkId provided
     *
     * @param registerMarkId the registerMark ID for the RegisterMark object retrieve
     * @return A ResponseEntity with the corresponding RegisterMarkDto object
     * @throws NotFoundException if nothing is found then the the NotFoundException is thrown.
     */
    @ApiOperation(value = "Retrieves an indivdual instance of a RegisterMark identified by the registerMarkId", notes = "A getGET request to the RegisterMark instance endpoint will retrieve an instance of a RegisterMark entity as identified by the registerMarkId provided in the URI.", response = RegisterMarkDto.class)
    @ApiResponses(value = [
        @ApiResponse(code = 200, message = "Returns a copy of the RegisterMark as identified by the registerMarkId"),
        @ApiResponse(code = 404, message = "Returns an Error object stating that the resource could not be found. This happens if the specified id cannot be found in the database.")
    ])
    @RequestMapping(value = "/{registerMarkId}", produces = ["application/json"], method = RequestMethod.GET)
    public ResponseEntity<RegisterMarkDto> registerMarksRegisterMarkIdGet(@ApiParam(value = "The unique ID of the RegisterMark to retrieve", required = true) @PathVariable("registerMarkId") Integer registerMarkId) throws NotFoundException {
        LOGGER.info("** RegisterMarksApi - registerMarksRegisterMarkIdGet");
        RegisterMark registerMark = registerMarkService.findById(registerMarkId);
        if (registerMark == null) {
            throw new NotFoundException();
        }
        return new ResponseEntity<RegisterMarkDto>(RegisterMarkDto.mapFromRegisterMarkEntity(registerMark), HttpStatus.OK);
    }
    
    /**
     * The registerMarksRegisterMarkIdPut is used to update
     *
     * @param registerMarkId the registerMark ID for the RegisterMark object to update
     * @param registerMark the new data for the RegisterMark object
     * @return the newly updated RegisterMarkDto object
     * @throws NotFoundException if nothing is found then the the NotFoundException is thrown.
     * @throws InvalidDataException if there is an issue with the data provided in the RequestBody then an InvalidDataException is thrown
     */
    @ApiOperation(value = "Used to update a RegisterMark entity", notes = "A PUT request to the RegisterMark instance endpoint with a RegisterMark object in the request body will update an existing RegisterMark entity in the database.", response = RegisterMarkDto.class)
    @ApiResponses(value = [
        @ApiResponse(code = 200, message = "Returns a copy of the newly updated RegisterMark object."),
        @ApiResponse(code = 400, message = "Returns an Error object stating the request body does not match the expected input."),
        @ApiResponse(code = 404, message = "Returns an Error object stating that the resource could not be found. This happens if the specified id cannot be found in the database.")
    ])
    @RequestMapping(value = "/{registerMarkId}", produces = ["application/json"], method = RequestMethod.PUT)
    public ResponseEntity<RegisterMarkDto> registerMarksRegisterMarkIdPut(
            @ApiParam(value = "The unique ID of the RegisterMark to retrieve", required = true) @PathVariable("registerMarkId") Integer registerMarkId,
            @ApiParam(value = "The RegisterMark object to be created, without the registerMarkId fields", required = true) @RequestBody RegisterMarkDto registerMark) throws NotFoundException, InvalidDataException {
        LOGGER.info("** RegisterMarksApi - registerMarksPUT");
        if (registerMarkId != registerMark.id) {
            throw new InvalidDataException()
        }
        Course course
        if(registerMark.courseId != null) {
            course = courseService.findById(registerMark.courseId)
        }
        CourseGroup courseGroup
        if(registerMark.courseGroupId != null) {
            courseGroup = courseGroupService.findById(registerMark.courseGroupId)
        }
        Register register
        if(registerMark.registerId != null) {
            register = registerService.findById(registerMark.registerId)
        }
        Student student
        if(registerMark.studentId != null) {
            student = studentService.findById(registerMark.studentId)
        }
        AttendanceCode attendanceCode
        if(registerMark.attendanceCodeId != null) {
            attendanceCode = attendanceCodeService.findById(registerMark.attendanceCodeId)
        }
        RegisterMark registerMarkToSave = RegisterMarkDto.mapToRegisterMarkEntity(registerMark, course, courseGroup, register, student, attendanceCode)
        RegisterMark registerMarkSaved = registerMarkService.save(registerMarkToSave)
        return new ResponseEntity<RegisterMarkDto>(RegisterMarkDto.mapFromRegisterMarkEntity(registerMarkSaved), HttpStatus.OK);
    }
}
