package uk.ac.reigate.api;

import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE
import io.swagger.annotations.Api
import io.swagger.annotations.ApiOperation
import io.swagger.annotations.ApiParam
import io.swagger.annotations.ApiResponse
import io.swagger.annotations.ApiResponses

import org.apache.log4j.Logger
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.HttpStatus
import org.springframework.http.MediaType
import org.springframework.http.ResponseEntity
import org.springframework.stereotype.Controller
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestMethod
import org.springframework.web.bind.annotation.RequestParam

import uk.ac.reigate.domain.academic.StudentYear
import uk.ac.reigate.domain.lookup.WarningCodeChange
import uk.ac.reigate.dto.StudentWarningDto
import uk.ac.reigate.dto.WarningCodeChangeDto
import uk.ac.reigate.exceptions.InvalidDataException
import uk.ac.reigate.exceptions.NotFoundException
import uk.ac.reigate.services.AcademicYearService
import uk.ac.reigate.services.StudentService
import uk.ac.reigate.services.StudentYearService
import uk.ac.reigate.services.WarningCodeChangeService


@Controller
@RequestMapping(value = "/api/students", produces = [ MediaType.APPLICATION_JSON_VALUE ])
@Api(value = "/api/students", description = "the studentWarnings API")
public class StudentWarningsApi {
    
    private static final Logger LOGGER = Logger.getLogger(StudentWarningsApi.class);
    
    @Autowired
    AcademicYearService academicYearService
    
    @Autowired
    StudentService studentService
    
    @Autowired
    StudentYearService studentYearService;
    
    @Autowired
    WarningCodeChangeService warningCodeChangeService;
    
    /**
     * Default NoArgs constructor
     */
    StudentWarningsApi() {}
    
    /**
     * The studentWarningsStudentWarningIdGet method is used to retrieve an instance of a StudentWarningDto object as identified by the studentWarningId provided
     *
     * @param studentWarningId the studentWarning ID for the StudentWarning object retrieve
     * @return A ResponseEntity with the corresponding StudentWarningDto object
     * @throws NotFoundException if nothing is found then the the NotFoundException is thrown.
     */
    @ApiOperation(value = "Retrieves an indivdual instance of a StudentWarning identified by the studentWarningId", notes = "A getGET request to the StudentWarning instance endpoint will retrieve an instance of a StudentWarning entity as identified by the studentWarningId provided in the URI.", response = StudentWarningDto.class)
    @ApiResponses(value = [
        @ApiResponse(code = 200, message = "Returns a copy of the StudentWarning as identified by the studentWarningId"),
        @ApiResponse(code = 404, message = "Returns an Error object stating that the resource could not be found. This happens if the specified id cannot be found in the database.")
    ])
    @RequestMapping(value = "/{studentId}/warnings", produces = ["application/json"], method = RequestMethod.GET)
    public ResponseEntity<StudentWarningDto> studentWarningsStudentWarningIdGet(
            @ApiParam(value = "The unique ID of the StudentWarning to retrieve", required = true)
            @PathVariable("studentId") Integer studentId,
            @ApiParam(value = "The year ID of the StudentWarning to retrieve", required = false)
            @RequestParam(value = "yearId", required = false) Integer yearId
    ) throws NotFoundException {
        LOGGER.info("** StudentWarningsApi - studentWarningsStudentWarningIdGet");
        if (yearId == null) {
            yearId = academicYearService.getCurrentAcademicYear().id;
        }
        StudentYear studentYear = studentYearService.findByStudentAndYear(studentId, yearId);
        if (studentYear != null) {
            return new ResponseEntity<StudentWarningDto>(new StudentWarningDto(studentYear), HttpStatus.OK);
        } else {
            throw new NotFoundException("There are no student year data for the requested student/year.");
        }
    }
    
    /**
     * The studentWarningsStudentWarningCodeChangeIdGet method is used to retrieve an instance of a StudentWarningDto object as identified by the studentWarningId provided
     *
     * @param studentWarningId the studentWarning ID for the StudentWarning object retrieve
     * @return A ResponseEntity with the corresponding StudentWarningDto object
     * @throws NotFoundException if nothing is found then the the NotFoundException is thrown.
     */
    @ApiOperation(value = "Retrieves an indivdual instance of a StudentWarning identified by the studentWarningId", notes = "A getGET request to the StudentWarning instance endpoint will retrieve an instance of a StudentWarning entity as identified by the studentWarningId provided in the URI.", response = WarningCodeChangeDto.class)
    @ApiResponses(value = [
        @ApiResponse(code = 200, message = "Returns a copy of the StudentWarning as identified by the studentWarningId"),
        @ApiResponse(code = 404, message = "Returns an Error object stating that the resource could not be found. This happens if the specified id cannot be found in the database.")
    ])
    @RequestMapping(value = "/{studentId}/warning-code-changes", produces = ["application/json"], method = RequestMethod.GET)
    public ResponseEntity<WarningCodeChangeDto> warningCodeChangeIdGet(
            @ApiParam(value = "The unique ID of the StudentWarning to retrieve", required = true)
            @PathVariable("studentId") Integer studentId,
            @ApiParam(value = "The year ID of the StudentWarning to retrieve", required = false)
            @RequestParam(value = "yearId", required = false) Integer yearId
    ) throws NotFoundException {
        LOGGER.info("** StudentWarningsApi - studentWarningsStudentWarningIdGet");
        if (yearId == null) {
            yearId = academicYearService.getCurrentAcademicYear().id;
        }
        List<WarningCodeChange> warningCodeChange = warningCodeChangeService.getByStudentAndYear(studentId, yearId);
        if (warningCodeChange != null) {
            return new ResponseEntity<List<WarningCodeChangeDto>>(WarningCodeChangeDto.mapFromWarningCodeChangesEntities(warningCodeChange), HttpStatus.OK);
        } else {
            throw new NotFoundException("There are no student year data for the requested student/year.");
        }
    }
    
    /**
     * The studentWarningsStudentWarningIdPut is used to update
     *
     * @param studentId the student ID for the StudentWarning object to update
     * @param studentWarning the new data for the StudentWarning object
     * @return the newly updated StudentWarningDto object
     * @throws NotFoundException if nothing is found then the the NotFoundException is thrown.
     * @throws InvalidDataException if there is an issue with the data provided in the RequestBody then an InvalidDataException is thrown
     */
    @ApiOperation(value = "Used to update a StudentWarning entity", notes = "A PUT request to the StudentWarning instance endpoint with a StudentWarning object in the request body will update an existing StudentWarning entity in the database.", response = StudentWarningDto.class)
    @ApiResponses(value = [
        @ApiResponse(code = 200, message = "Returns a copy of the newly updated StudentWarning object."),
        @ApiResponse(code = 400, message = "Returns an Error object stating the request body does not match the expected input."),
        @ApiResponse(code = 404, message = "Returns an Error object stating that the resource could not be found. This happens if the specified id cannot be found in the database.")
    ])
    @RequestMapping(value = "/{studentId}/warnings", produces = ["application/json"], method = RequestMethod.PUT)
    public ResponseEntity<StudentWarningDto> studentWarningsStudentWarningIdPut(
            @ApiParam(value = "The unique ID of the StudentWarning to retrieve", required = true)
            @PathVariable("studentId") Integer studentId,
            @ApiParam(value = "The StudentWarning object to be created, without the studentWarningId fields", required = true)
            @RequestBody StudentWarningDto studentWarning
    ) throws NotFoundException, InvalidDataException {
        
        LOGGER.info("** StudentWarningsApi - studentWarningsPUT");
        if (studentId == studentWarning.studentId) {
            StudentYear studentYear = studentYearService.updateStudentWarning(studentWarning.studentId, studentWarning.yearId, studentWarning.attendanceMonitoringId, studentWarning.punctualityMonitoringId);
            StudentWarningDto output = new StudentWarningDto(studentYear);
            return new ResponseEntity<StudentWarningDto>(output, HttpStatus.OK);
        } else {
            throw new InvalidDataException();
        }
    }
}
