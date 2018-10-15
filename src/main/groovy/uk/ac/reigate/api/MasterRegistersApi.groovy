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
import org.springframework.web.bind.annotation.RequestParam
import uk.ac.reigate.domain.academic.Student
import uk.ac.reigate.domain.cristal.MasterRegister
import uk.ac.reigate.domain.register.AttendanceCode
import uk.ac.reigate.dto.MasterRegisterDto
import uk.ac.reigate.exceptions.InvalidDataException
import uk.ac.reigate.exceptions.NotFoundException
import uk.ac.reigate.model.SearchResult
import uk.ac.reigate.services.AcademicYearService
import uk.ac.reigate.services.AttendanceCodeService
import uk.ac.reigate.services.MasterRegisterService
import uk.ac.reigate.services.StudentService


@Controller
@RequestMapping(value = "/api", produces = [ APPLICATION_JSON_VALUE ])
@Api(value = "/api/masterRegisters", description = "the masterRegisters API")
public class MasterRegistersApi {
    
    private static final Logger LOGGER = Logger.getLogger(MasterRegistersApi.class);
    
    @Autowired
    private final AcademicYearService academicYearService
    
    @Autowired
    private final MasterRegisterService masterRegisterService;
    
    @Autowired
    private final StudentService studentService;
    
    @Autowired
    private final AttendanceCodeService attendanceCodeService;
    
    
    
    /**
     * Default NoArgs constructor
     */
    MasterRegistersApi() {}
    
    /**
     * Autowired constructor
     */
    MasterRegistersApi(MasterRegisterService masterRegisterService) {
        this.masterRegisterService = masterRegisterService;
    }
    
    /**
     * The masterRegistersGet method is used to retrieve a full list of all the MasterRegisterDto objects
     *
     * @return A ResponseEntity with the corresponding list of MasterRegisterDto objects
     * @throws NotFoundException if nothing is found then the the NotFoundException is thrown.
     */
    @ApiOperation(value = "Collection of MasterRegister entities", notes = "A GET request to the MasterRegisters endpoint returns an array of all the masterRegisters in the system.", response = MasterRegisterDto.class, responseContainer = "List")
    @ApiResponses(value = [
        @ApiResponse(code = 200, message = "An array of masterRegisters")
    ])
    @RequestMapping(value = "/masterRegisters", produces = ["application/json"], method = RequestMethod.GET)
    public ResponseEntity<List<MasterRegisterDto>> masterRegistersGet() throws NotFoundException {
        LOGGER.info("** MasterRegistersApi - masterRegistersGet");
        List<MasterRegister> masterRegisters = masterRegisterService.findAll();
        return new ResponseEntity<List<MasterRegisterDto>>(MasterRegisterDto.mapFromMasterRegistersEntities(masterRegisters), HttpStatus.OK);
    }
    
    /**
     * The masterRegistersPost method is used to create a new instance of a MasterRegister from the supplied MasterRegisterDto
     *
     * @param masterRegister the MasterRegisterDto to use to create the new MasterRegister object
     * @return A ResponseEntity with the newly created MasterRegister object
     * @throws InvalidDataException if there is an issue with the data provided in the RequestBody then an InvalidDataException is thrown
     */
    @ApiOperation(value = "Creates a new MasterRegister entity", notes = "A POST request to the MasterRegisters endpoint with a MasterRegister object in the request body will create a new MasterRegister entity in the database.", response = MasterRegisterDto.class)
    @ApiResponses(value = [
        @ApiResponse(code = 201, message = "Returns a copy of the created MasterRegister entity including the masterRegisterId that has just been created."),
        @ApiResponse(code = 400, message = "Returns an Error object stating the request body does not match the expected input.")
    ])
    @RequestMapping(value = "/masterRegisters", produces = ["application/json"], method = RequestMethod.POST)
    public ResponseEntity<MasterRegisterDto> masterRegistersPost(@ApiParam(value = "The MasterRegister object to be created, without the masterRegisterId fields", required = true) @RequestBody @Valid MasterRegisterDto masterRegister) throws NotFoundException, InvalidDataException {
        LOGGER.info("** MasterRegistersApi - masterRegistersPOST");
        if (masterRegister.id != null) {
            throw new InvalidDataException(400, "No ID field should be provided when creating")
        }
        Student student
        if(masterRegister.studentId != null){
            student = studentService.findById(masterRegister.studentId)
        }
        AttendanceCode attendance
        if(masterRegister.attendanceId != null){
            attendance = attendanceCodeService.findById(masterRegister.attendanceId)
        }
        MasterRegister masterRegisterToSave = MasterRegisterDto.mapToMasterRegisterEntity(masterRegister, student, attendance)
        MasterRegister masterRegisterSaved = masterRegisterService.save(masterRegisterToSave)
        return new ResponseEntity<MasterRegisterDto>(MasterRegisterDto.mapFromMasterRegisterEntity(masterRegisterSaved), HttpStatus.CREATED);
    }
    
    /**
     * The masterRegistersMasterRegisterIdGet method is used to retrieve an instance of a MasterRegisterDto object as identified by the masterRegisterId provided
     *
     * @param masterRegisterId the masterRegister ID for the MasterRegister object retrieve
     * @return A ResponseEntity with the corresponding MasterRegisterDto object
     * @throws NotFoundException if nothing is found then the the NotFoundException is thrown.
     */
    @ApiOperation(value = "Retrieves an indivdual instance of a MasterRegister identified by the masterRegisterId", notes = "A getGET request to the MasterRegister instance endpoint will retrieve an instance of a MasterRegister entity as identified by the masterRegisterId provided in the URI.", response = MasterRegisterDto.class)
    @ApiResponses(value = [
        @ApiResponse(code = 200, message = "Returns a copy of the MasterRegister as identified by the masterRegisterId"),
        @ApiResponse(code = 404, message = "Returns an Error object stating that the resource could not be found. This happens if the specified id cannot be found in the database.")
    ])
    @RequestMapping(value = "/masterRegisters/{masterRegisterId}", produces = ["application/json"], method = RequestMethod.GET)
    public ResponseEntity<MasterRegisterDto> masterRegistersMasterRegisterIdGet(@ApiParam(value = "The unique ID of the MasterRegister to retrieve", required = true) @PathVariable("masterRegisterId") Integer masterRegisterId) throws NotFoundException {
        LOGGER.info("** MasterRegistersApi - masterRegistersMasterRegisterIdGet");
        MasterRegister masterRegister = masterRegisterService.findById(masterRegisterId);
        if (masterRegister == null) {
            throw new NotFoundException();
        }
        return new ResponseEntity<MasterRegisterDto>(MasterRegisterDto.mapFromMasterRegisterEntity(masterRegister), HttpStatus.OK);
    }
    
    /**
     * The masterRegistersMasterRegisterIdPut is used to update
     *
     * @param masterRegisterId the masterRegister ID for the MasterRegister object to update
     * @param masterRegister the new data for the MasterRegister object
     * @return the newly updated MasterRegisterDto object
     * @throws NotFoundException if nothing is found then the the NotFoundException is thrown.
     * @throws InvalidDataException if there is an issue with the data provided in the RequestBody then an InvalidDataException is thrown
     */
    @ApiOperation(value = "Used to update a MasterRegister entity", notes = "A PUT request to the MasterRegister instance endpoint with a MasterRegister object in the request body will update an existing MasterRegister entity in the database.", response = MasterRegisterDto.class)
    @ApiResponses(value = [
        @ApiResponse(code = 200, message = "Returns a copy of the newly updated MasterRegister object."),
        @ApiResponse(code = 400, message = "Returns an Error object stating the request body does not match the expected input."),
        @ApiResponse(code = 404, message = "Returns an Error object stating that the resource could not be found. This happens if the specified id cannot be found in the database.")
    ])
    @RequestMapping(value = "/masterRegisters/{masterRegisterId}", produces = ["application/json"], method = RequestMethod.PUT)
    public ResponseEntity<MasterRegisterDto> masterRegistersMasterRegisterIdPut(
            @ApiParam(value = "The unique ID of the MasterRegister to retrieve", required = true) @PathVariable("masterRegisterId") Integer masterRegisterId,
            @ApiParam(value = "The MasterRegister object to be created, without the masterRegisterId fields", required = true) @RequestBody MasterRegisterDto masterRegister) throws NotFoundException, InvalidDataException {
        LOGGER.info("** MasterRegistersApi - masterRegistersPUT");
        if (masterRegisterId != masterRegister.id) {
            throw new InvalidDataException()
        }
        Student student
        if(masterRegister.studentId != null){
            student = studentService.findById(masterRegister.studentId)
        }
        AttendanceCode attendance
        if(masterRegister.attendanceId != null){
            attendance = attendanceCodeService.findById(masterRegister.attendanceId)
        }
        MasterRegister masterRegisterToSave = MasterRegisterDto.mapToMasterRegisterEntity(masterRegister, student, attendance)
        MasterRegister masterRegisterSaved = masterRegisterService.updateMasterRegister(masterRegisterToSave)
        return new ResponseEntity<MasterRegisterDto>(MasterRegisterDto.mapFromMasterRegisterEntity(masterRegisterSaved), HttpStatus.OK);
    }
    
    /**
     * This method is used to retrieve a master register collection for a given student.
     *     
     * @param studentId the studentId to use for retrieving the master register data
     * @return a ResponseEntity populated with the student register data 
     * @throws NotFoundException
     */
    @ApiOperation(value = "Retrieves an indivdual instance of a MasterRegister identified by the masterRegisterId", notes = "A getGET request to the MasterRegister instance endpoint will retrieve an instance of a MasterRegister entity as identified by the masterRegisterId provided in the URI.", response = MasterRegisterDto.class)
    @ApiResponses(value = [
        @ApiResponse(code = 200, message = "Returns a copy of the MasterRegister as identified by the masterRegisterId"),
        @ApiResponse(code = 404, message = "Returns an Error object stating that the resource could not be found. This happens if the specified id cannot be found in the database.")
    ])
    @RequestMapping(value = "/students/{studentId}/masterRegisters", produces = ["application/json"], method = RequestMethod.GET)
    public ResponseEntity<List<MasterRegisterDto>> getMasterRegisterForStudent(
            @ApiParam(value = "The ID of the student whose register is to be retrieved", required = true)
            @PathVariable("studentId") Integer studentId,
            @ApiParam(value = "The ID of the year year to retrieve the retgister for", required = false)
            @RequestParam(name = "yearId", required = false) Integer yearId
    ) throws NotFoundException {
        LOGGER.info("** MasterRegistersApi - masterRegistersMasterRegisterIdGet");
        if (yearId == null) {
            yearId = academicYearService.getCurrentAcademicYear().id
        }
        List<MasterRegister> masterRegister = masterRegisterService.getByStudentAndYear(studentId, yearId);
        if (masterRegister == null) {
            throw new NotFoundException();
        }
        List<MasterRegister> output = MasterRegisterDto.mapFromMasterRegistersEntities(masterRegister)
        return new ResponseEntity<List<MasterRegisterDto>>(output, HttpStatus.OK);
    }
}
