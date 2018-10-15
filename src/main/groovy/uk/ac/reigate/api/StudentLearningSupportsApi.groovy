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


import uk.ac.reigate.domain.academic.Student
import uk.ac.reigate.domain.learning_support.StudentLearningSupport
import uk.ac.reigate.dto.StudentDto
import uk.ac.reigate.dto.StudentLearningSupportDto
import uk.ac.reigate.dto.StudentYearDto
import uk.ac.reigate.exceptions.InvalidDataException
import uk.ac.reigate.exceptions.NotFoundException
import uk.ac.reigate.model.SearchResult
import uk.ac.reigate.services.StudentLearningSupportService
import uk.ac.reigate.services.StudentService
import uk.ac.reigate.services.SupportTypeService


@Controller
@RequestMapping(value = "/api", produces = [ APPLICATION_JSON_VALUE ])
@Api(value = "/api", description = "the studentLearningSupports API")
public class StudentLearningSupportsApi {
    
    private static final Logger LOGGER = Logger.getLogger(StudentLearningSupportsApi.class);
    
    @Autowired
    private final StudentLearningSupportService studentLearningSupportService;
    
    @Autowired
    StudentService studentService
    
    @Autowired
    SupportTypeService supportTypeService
    /**
     * Default NoArgs constructor
     */
    StudentLearningSupportsApi() {}
    
    /**
     * Autowired constructor
     */
    StudentLearningSupportsApi(StudentLearningSupportService studentLearningSupportService) {
        this.studentLearningSupportService = studentLearningSupportService;
    }
    
    
    /**
     * The studentLearningSupportsStudentLearningSupportIdGet method is used to retrieve an instance of a StudentLearningSupportDto object as identified by the studentLearningSupportId provided
     *
     * @param studentLearningSupportId the studentLearningSupport ID for the StudentLearningSupport object retrieve
     * @return A ResponseEntity with the corresponding StudentLearningSupportDto object
     * @throws NotFoundException if nothing is found then the the NotFoundException is thrown.
     */
    @ApiOperation(value = "Retrieves an indivdual instance of a StudentLearningSupport identified by the studentLearningSupportId", notes = "A getGET request to the StudentLearningSupport instance endpoint will retrieve an instance of a StudentLearningSupport entity as identified by the studentLearningSupportId provided in the URI.", response = StudentLearningSupportDto.class)
    @ApiResponses(value = [
        @ApiResponse(code = 200, message = "Returns a copy of the StudentLearningSupport as identified by the studentLearningSupportId"),
        @ApiResponse(code = 404, message = "Returns an Error object stating that the resource could not be found. This happens if the specified id cannot be found in the database.")
    ])
    @RequestMapping(value = "/students/{studentId}/learningSupport", produces = ["application/json"], method = RequestMethod.GET)
    public ResponseEntity<StudentLearningSupportDto> studentLearningSupportsStudentLearningSupportIdGet(@ApiParam(value = "The unique ID of the StudentLearningSupport to retrieve", required = true) @PathVariable("studentId") Integer studentId) throws NotFoundException {
        LOGGER.info("** StudentLearningSupportsApi - studentLearningSupportsStudentLearningSupportIdGet");
        Student student = studentService.findById(studentId)
        StudentLearningSupport studentLearningSupport = studentLearningSupportService.findStudentLearningSupport(studentId)
        return new ResponseEntity<?>(new StudentLearningSupportDto(student, studentLearningSupport), HttpStatus.OK);
    }
    
    @RequestMapping(value = "/students/{studentId}/learningSupport", produces = ["application/json"], method = RequestMethod.PUT)
    public ResponseEntity<StudentLearningSupportDto> studentLearningSupportsStudentLearningSupportIdPut(
            @ApiParam(value = "The unique ID of the StudentLearningSupport to retrieve", required = true)
            @PathVariable("studentId") Integer studentId,
            @ApiParam(value = "The StudentLearningSupport object to be created, without the studentLearningSupportId fields", required = true)
            @RequestBody StudentLearningSupportDto studentLearningSupportDto
    ) throws NotFoundException {
        LOGGER.info("** StudentLearningSupportsApi - studentLearningSupportsStudentLearningSupportIdGet");
        StudentLearningSupport studentLearningSupport = studentLearningSupportService.findByStudent(studentLearningSupportDto.studentId)
        // StudentLearningSupport studentLearningSupport = studentLearningSupportService.findStudentLearningSupport(studentId)
        if(studentLearningSupport != null){
            studentLearningSupport.referralNotes = studentLearningSupportDto.referralNotes
            studentLearningSupport.supportNotes = studentLearningSupportDto.supportNotes
            studentLearningSupport.previousConcession = studentLearningSupportDto.previousConcession
            studentLearningSupport.concessionApplication = studentLearningSupportDto.concessionApplication
            studentLearningSupport.examConcessionApproved = studentLearningSupportDto.examConcessionApproved
            studentLearningSupport.btecConcessionApproved = studentLearningSupportDto.btecConcessionApproved
            studentLearningSupport.fsConcessionApproved = studentLearningSupportDto.fsConcessionApproved
            studentLearningSupport.assessmentDate = studentLearningSupportDto.assessmentDate
            studentLearningSupport.supportType = studentLearningSupportDto.supportTypeId != null ? supportTypeService.findById(studentLearningSupportDto.supportTypeId) : studentLearningSupport.supportType
            
            StudentLearningSupport studentLearningSupportSaved = studentLearningSupportService.save(studentLearningSupport)
            return new ResponseEntity<?>(HttpStatus.OK);
        } else {
            throw new InvalidDataException();
        }
    }
}
