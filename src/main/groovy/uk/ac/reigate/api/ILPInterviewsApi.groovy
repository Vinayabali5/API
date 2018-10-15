package uk.ac.reigate.api;

import javax.validation.Valid

import io.swagger.annotations.Api
import io.swagger.annotations.ApiOperation
import io.swagger.annotations.ApiParam
import io.swagger.annotations.ApiResponse
import io.swagger.annotations.ApiResponses

import org.apache.log4j.Logger

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.security.web.servletapi.SecurityContextHolderAwareRequestWrapper
import org.springframework.stereotype.Controller
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestMethod

import uk.ac.reigate.domain.Staff
import uk.ac.reigate.domain.academic.AcademicYear
import uk.ac.reigate.domain.academic.CourseGroup
import uk.ac.reigate.domain.academic.Student
import uk.ac.reigate.domain.ilp.ILPInterview
import uk.ac.reigate.domain.ilp.ILPInterviewType
import uk.ac.reigate.dto.ILPInterviewDto
import uk.ac.reigate.exceptions.InvalidDataException
import uk.ac.reigate.exceptions.NotFoundException
import uk.ac.reigate.services.AcademicYearService
import uk.ac.reigate.services.CorrespondenceTypeService
import uk.ac.reigate.services.CourseGroupService
import uk.ac.reigate.services.ILPInterviewService
import uk.ac.reigate.services.ILPInterviewTypeService
import uk.ac.reigate.services.StaffService
import uk.ac.reigate.services.StudentService

import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE


@Controller
@RequestMapping(value = "/api", produces = [ APPLICATION_JSON_VALUE ])
@Api(value = "/api", description = "the iLPInterviews API")
public class ILPInterviewsApi {
    
    private static final Logger LOGGER = Logger.getLogger(ILPInterviewsApi.class);
    
    @Autowired
    private final ILPInterviewService iLPInterviewService;
    
    @Autowired
    private final ILPInterviewTypeService iLPInterviewTypeService;
    
    @Autowired
    private final StudentService studentService;
    
    @Autowired
    private final CourseGroupService courseGroupService;
    
    @Autowired
    private final StaffService staffService;
    
    @Autowired
    private final CorrespondenceTypeService correspondenceTypeService;
    
    @Autowired
    private final AcademicYearService academicYearService;
    
    /**
     * Default NoArgs constructor
     */
    ILPInterviewsApi() {}
    
    /**
     * Autowired constructor
     */
    ILPInterviewsApi(ILPInterviewService iLPInterviewService) {
        this.iLPInterviewService = iLPInterviewService;
    }
    
    
    /**
     * The iLPInterviewsStudentIdGet method is used to retrieve an instance of a ILPInterviewDto object as identified by the studentId provided
     *
     * @param studentId the student ID for the ILPInterview object retrieve
     * @return A ResponseEntity with the corresponding ILPInterviewDto object
     * @throws NotFoundException if nothing is found then the the NotFoundException is thrown.
     */
    @ApiOperation(value = "Retrieves an indivdual instance of a ILPInterview identified by the iLPInterviewId", notes = "A getGET request to the ILPInterview instance endpoint will retrieve an instance of a ILPInterview entity as identified by the iLPInterviewId provided in the URI.", response = ILPInterviewDto.class)
    @ApiResponses(value = [
        @ApiResponse(code = 200, message = "Returns a copy of the ILPInterview as identified by the iLPInterviewId"),
        @ApiResponse(code = 404, message = "Returns an Error object stating that the resource could not be found. This happens if the specified id cannot be found in the database.")
    ])
    @RequestMapping(value = "/students/{studentId}/ilpInterviews", produces = ["application/json"], method = RequestMethod.GET)
    public ResponseEntity<ILPInterviewDto> iLPInterviewsStudentIdGet(SecurityContextHolderAwareRequestWrapper request,
            @ApiParam(value = "The unique ID of the ILPInterview to retrieve", required = true)
            @PathVariable("studentId") Integer studentId
    ) throws NotFoundException {
        LOGGER.info("** ILPInterviewsApi - iLPInterviewsILPInterviewIdGet");
        List<ILPInterview> iLPInterview
        Boolean userIsPastoral = request.isUserInRole('ROLE_Pastoral')
        if (userIsPastoral) {
            LOGGER.info('Pastoral User Detected')
            iLPInterview = iLPInterviewService.getByStudentAll(studentId);
        } else {
            iLPInterview = iLPInterviewService.getByStudent(studentId);
        }
        if (iLPInterview == null) {
            throw new NotFoundException();
        }
        return new ResponseEntity<ILPInterviewDto>(ILPInterviewDto.mapFromILPInterviewsEntities(iLPInterview), HttpStatus.OK);
    }
    
    /**
     * The iLPInterviewsPost method is used to create a new instance of a ILPInterview from the supplied ILPInterviewDto
     *
     * @param iLPInterview the ILPInterviewDto to use to create the new ILPInterview object
     * @return A ResponseEntity with the newly created ILPInterview object
     * @throws InvalidDataException if there is an issue with the data provided in the RequestBody then an InvalidDataException is thrown
     */
    @ApiOperation(value = "Creates a new ILPInterview entity", notes = "A POST request to the ILPInterviews endpoint with a ILPInterview object in the request body will create a new ILPInterview entity in the database.", response = ILPInterviewDto.class)
    @ApiResponses(value = [
        @ApiResponse(code = 201, message = "Returns a copy of the created ILPInterview entity including the iLPInterviewId that has just been created."),
        @ApiResponse(code = 400, message = "Returns an Error object stating the request body does not match the expected input.")
    ])
    @RequestMapping(value = "/ilpInterview", produces = ["application/json"], method = RequestMethod.POST)
    public ResponseEntity<ILPInterviewDto> iLPInterviewsPost(@ApiParam(value = "The ILPInterview object to be created, without the iLPInterviewId fields", required = true) @RequestBody @Valid ILPInterviewDto iLPInterviewDto) throws NotFoundException, InvalidDataException {
        LOGGER.info("** ILPInterviewsApi - iLPInterviewsPOST");
        if (iLPInterviewDto.id != null) {
            throw new InvalidDataException(400, "No ID field should be provided when creating")
        }
        Student student
        if(iLPInterviewDto.studentId != null) {
            student = studentService.findById(iLPInterviewDto.studentId)
        }
        CourseGroup courseGroup
        if(iLPInterviewDto.courseGroupId != null) {
            courseGroup = courseGroupService.findById(iLPInterviewDto.courseGroupId)
        }
        Staff staff
        if(iLPInterviewDto.staffId != null) {
            staff = staffService.findById(iLPInterviewDto.staffId)
        }
        ILPInterviewType type
        if(iLPInterviewDto.typeId != null) {
            type = iLPInterviewTypeService.findById(iLPInterviewDto.typeId)
        }
        AcademicYear academicYear
        if(iLPInterviewDto.academicYearId != null) {
            academicYear = academicYearService.findById(iLPInterviewDto.academicYearId);
        }
        ILPInterview iLPInterviewToSave = ILPInterviewDto.mapToILPInterviewEntity(iLPInterviewDto, student, courseGroup, type, staff , academicYear)
        ILPInterview iLPInterviewSaved = iLPInterviewService.save(iLPInterviewToSave)
        return new ResponseEntity<ILPInterviewDto>(ILPInterviewDto.mapFromILPInterviewEntity(iLPInterviewSaved), HttpStatus.CREATED);
    }
    
    /**
     * The iLPInterviewsILPInterviewIdGet method is used to retrieve an instance of a ILPInterviewDto object as identified by the studentId and iLPInterviewId provided
     *
     * @param studentId, iLPInterviewId the student ID and ILPInterview Id for the ILPInterview object retrieve
     * @return A ResponseEntity with the corresponding ILPInterviewDto object
     * @throws NotFoundException if nothing is found then the the NotFoundException is thrown.
     */
    @ApiOperation(value = "Retrieves an indivdual instance of a ILPInterview identified by the iLPInterviewId", notes = "A getGET request to the ILPInterview instance endpoint will retrieve an instance of a ILPInterview entity as identified by the iLPInterviewId provided in the URI.", response = ILPInterviewDto.class)
    @ApiResponses(value = [
        @ApiResponse(code = 200, message = "Returns a copy of the ILPInterview as identified by the iLPInterviewId"),
        @ApiResponse(code = 404, message = "Returns an Error object stating that the resource could not be found. This happens if the specified id cannot be found in the database.")
    ])
    @RequestMapping(value = "/students/{studentId}/ilpInterviews/{iLPInterviewId}", produces = ["application/json"], method = RequestMethod.GET)
    public ResponseEntity<ILPInterviewDto> iLPInterviewsILPInterviewIdGet(
            @ApiParam(value = "The unique ID of the Student to retrieve", required = true)
            @PathVariable("studentId") Integer studentId,
            @ApiParam(value = "The unique ID of the ILPInterview to retrieve", required = true)
            @PathVariable("iLPInterviewId") Integer iLPInterviewId) throws NotFoundException {
        LOGGER.info("** ILPInterviewsApi - iLPInterviewsILPInterviewIdGet");
        ILPInterview iLPInterview = iLPInterviewService.getByStudentAndILPInterview(studentId, iLPInterviewId);
        if (iLPInterview == null) {
            throw new NotFoundException();
        }
        return new ResponseEntity<ILPInterviewDto>(ILPInterviewDto.mapFromILPInterviewEntity(iLPInterview), HttpStatus.OK);
    }
    
    /**
     * The iLPInterviewsILPInterviewIdPut is used to update
     *
     * @param iLPInterviewId the iLPInterview ID for the ILPInterview object to update
     * @param iLPInterview the new data for the ILPInterview object
     * @return the newly updated ILPInterviewDto object
     * @throws NotFoundException if nothing is found then the the NotFoundException is thrown.
     * @throws InvalidDataException if there is an issue with the data provided in the RequestBody then an InvalidDataException is thrown
     */
    @ApiOperation(value = "Used to update a ILPInterview entity", notes = "A PUT request to the ILPInterview instance endpoint with a ILPInterview object in the request body will update an existing ILPInterview entity in the database.", response = ILPInterviewDto.class)
    @ApiResponses(value = [
        @ApiResponse(code = 200, message = "Returns a copy of the newly updated ILPInterview object."),
        @ApiResponse(code = 400, message = "Returns an Error object stating the request body does not match the expected input."),
        @ApiResponse(code = 404, message = "Returns an Error object stating that the resource could not be found. This happens if the specified id cannot be found in the database.")
    ])
    @RequestMapping(value = "/ilpInterview/{iLPInterviewId}", produces = ["application/json"], method = RequestMethod.PUT)
    public ResponseEntity<ILPInterviewDto> iLPInterviewsILPInterviewIdPut(
            @ApiParam(value = "The unique ID of the ILPInterview to retrieve", required = true) @PathVariable("iLPInterviewId") Integer iLPInterviewId,
            @ApiParam(value = "The ILPInterview object to be created, without the iLPInterviewId fields", required = true) @RequestBody ILPInterviewDto iLPInterviewDto) throws NotFoundException, InvalidDataException {
        LOGGER.info("** ILPInterviewsApi - iLPInterviewsPUT");
        if (iLPInterviewId != iLPInterviewDto.id) {
            throw new InvalidDataException()
        }
        Student student
        if(iLPInterviewDto.studentId != null) {
            student = studentService.findById(iLPInterviewDto.studentId)
        }
        CourseGroup courseGroup
        if(iLPInterviewDto.courseGroupId != null) {
            courseGroup = courseGroupService.findById(iLPInterviewDto.courseGroupId)
        }
        Staff staff
        if(iLPInterviewDto.staffId != null) {
            staff = staffService.findById(iLPInterviewDto.staffId)
        }
        ILPInterviewType type
        if(iLPInterviewDto.typeId != null) {
            type = iLPInterviewTypeService.findById(iLPInterviewDto.typeId)
        }
        AcademicYear academicYear
        if(iLPInterviewDto.academicYearId != null) {
            academicYear = academicYearService.findById(iLPInterviewDto.academicYearId);
        }
        ILPInterview iLPInterviewToSave = ILPInterviewDto.mapToILPInterviewEntity(iLPInterviewDto, student, courseGroup, type, staff, academicYear)
        ILPInterview iLPInterviewSaved = iLPInterviewService.save(iLPInterviewToSave)
        return new ResponseEntity<ILPInterviewDto>(ILPInterviewDto.mapFromILPInterviewEntity(iLPInterviewSaved), HttpStatus.OK);
    }
}
