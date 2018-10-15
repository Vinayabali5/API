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
import org.springframework.http.ResponseEntity
import org.springframework.stereotype.Controller
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestMethod
import org.springframework.web.bind.annotation.RequestParam

import uk.ac.reigate.domain.academic.StudentYear
import uk.ac.reigate.domain.lookup.AttendanceMonitoring
import uk.ac.reigate.dto.StudentBursaryDto
import uk.ac.reigate.dto.StudentBursaryDto;
import uk.ac.reigate.exceptions.InvalidDataException
import uk.ac.reigate.exceptions.NotFoundException
import uk.ac.reigate.services.AcademicYearService
import uk.ac.reigate.services.StudentBursaryService
import uk.ac.reigate.services.StudentService;
import uk.ac.reigate.services.StudentYearService;


@Controller
@RequestMapping(value = "/api/students", produces = [ APPLICATION_JSON_VALUE ])
@Api(value = "/api/students", description = "the studentBursarys API")
public class StudentBursarysApi {
    
    private static final Logger LOGGER = Logger.getLogger(StudentBursarysApi.class);
    
    @Autowired
    AcademicYearService academicYearService
    
    @Autowired
    StudentService studentService
    
    @Autowired
    private final StudentYearService studentYearService;
    
    @Autowired
    private final StudentBursaryService studentBursaryService;
    
    /**
     * Default NoArgs constructor
     */
    StudentBursarysApi() {}
    
    /**
     * Autowired constructor
     */
    StudentBursarysApi(StudentBursaryService studentBursaryService) {
        this.studentBursaryService = studentBursaryService;
    }
    
    
    /**
     * The studentBursarysStudentBursaryIdGet method is used to retrieve an instance of a StudentBursaryDto object as identified by the studentBursaryId provided
     *
     * @param studentBursaryId the studentBursary ID for the StudentBursary object retrieve
     * @return A ResponseEntity with the corresponding StudentBursaryDto object
     * @throws NotFoundException if nothing is found then the the NotFoundException is thrown.
     */
    @ApiOperation(value = "Retrieves an indivdual instance of a StudentBursary identified by the studentBursaryId", notes = "A getGET request to the StudentBursary instance endpoint will retrieve an instance of a StudentBursary entity as identified by the studentBursaryId provided in the URI.", response = StudentBursaryDto.class)
    @ApiResponses(value = [
        @ApiResponse(code = 200, message = "Returns a copy of the StudentBursary as identified by the studentBursaryId"),
        @ApiResponse(code = 404, message = "Returns an Error object stating that the resource could not be found. This happens if the specified id cannot be found in the database.")
    ])
    @RequestMapping(value = "/{studentId}/bursary", produces = ["application/json"], method = RequestMethod.GET)
    public ResponseEntity<StudentBursaryDto> studentBursaryStudentBursaryIdGet(
            @ApiParam(value = "The unique ID of the StudentBursary to retrieve", required = true)
            @PathVariable("studentId") Integer studentId,
            @ApiParam(value = "The year ID of the StudentBursary to retrieve", required = false)
            @RequestParam(value = "yearId", required = false) Integer yearId
    ) throws NotFoundException {
        LOGGER.info("** StudentBursarysApi - studentBursarysStudentBursaryIdGet");
        if (yearId == null) {
            yearId = academicYearService.getCurrentAcademicYear().id;
        }
        StudentYear studentBursary = studentYearService.findByStudentAndYear(studentId, yearId);
        if (studentBursary) {
            return new ResponseEntity<StudentBursaryDto>(StudentBursaryDto.mapFromStudentBursaryEntity(studentBursary), HttpStatus.OK);
        } else {
            throw new NotFoundException("There are no student year data for the requested student/year.");
        }
    }
    
    /**
     * The studentBursaryStudentBursaryIdPut is used to update
     *
     * @param studentId the student ID for the StudentBursary object to update
     * @param studentBursary the new data for the StudentBursary object
     * @return the newly updated StudentBursaryDto object
     * @throws NotFoundException if nothing is found then the the NotFoundException is thrown.
     * @throws InvalidDataException if there is an issue with the data provided in the RequestBody then an InvalidDataException is thrown
     */
    @ApiOperation(value = "Used to update a StudentBursary entity", notes = "A PUT request to the StudentBursary instance endpoint with a StudentBursary object in the request body will update an existing StudentBursary entity in the database.", response = StudentBursaryDto.class)
    @ApiResponses(value = [
        @ApiResponse(code = 200, message = "Returns a copy of the newly updated StudentBursary object."),
        @ApiResponse(code = 400, message = "Returns an Error object stating the request body does not match the expected input."),
        @ApiResponse(code = 404, message = "Returns an Error object stating that the resource could not be found. This happens if the specified id cannot be found in the database.")
    ])
    @RequestMapping(value = "/{studentId}/bursary", produces = ["application/json"], method = RequestMethod.PUT)
    public ResponseEntity<StudentBursaryDto> studentBursaryStudentBursaryIdPut(
            @ApiParam(value = "The unique ID of the StudentBursary to retrieve", required = true)
            @PathVariable("studentId") Integer studentId,
            @ApiParam(value = "The StudentBursary object to be created, without the studentBursaryId fields", required = true)
            @RequestBody StudentBursaryDto studentBursary
    ) throws NotFoundException, InvalidDataException {
        
        LOGGER.info("** StudentBursarysApi - studentBursarysPUT");
        if (studentId == studentBursary.studentId) {
            StudentYear studentYear = studentBursaryService.updateStudentBursary(studentBursary.studentId, studentBursary.yearId, studentBursary.gb, studentBursary.db, studentBursary.freeMealsEligibility, studentBursary.receivingFreeMeals);
            StudentBursaryDto output = new StudentBursaryDto(studentYear);
            
            return new ResponseEntity<StudentBursaryDto>(output, HttpStatus.OK);
        } else {
            throw new InvalidDataException();
        }
    }
}
