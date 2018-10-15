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

import uk.ac.reigate.config.security.SecurityConfiguration
import uk.ac.reigate.domain.academic.EntryQualification
import uk.ac.reigate.domain.academic.Student
import uk.ac.reigate.domain.academic.StudentEntryQualification
import uk.ac.reigate.domain.exam.ExamBoard
import uk.ac.reigate.dto.StudentEntryQualificationDto
import uk.ac.reigate.exceptions.InvalidDataException
import uk.ac.reigate.exceptions.NotFoundException
import uk.ac.reigate.model.SearchResult
import uk.ac.reigate.services.EntryQualificationService
import uk.ac.reigate.services.StudentEntryQualificationService
import uk.ac.reigate.services.StudentService
import uk.ac.reigate.services.exam.ExamBoardService


@Controller
@RequestMapping(value = "/api", produces = [ APPLICATION_JSON_VALUE ])
@Api(value = "/api", description = "the studentEntryQualifications API")
public class StudentEntryQualificationsApi {
    
    private static final Logger LOGGER = Logger.getLogger(StudentEntryQualificationsApi.class);
    
    @Autowired
    private final StudentEntryQualificationService studentEntryQualificationService;
    
    @Autowired
    private final StudentService studentService;
    
    @Autowired
    private final EntryQualificationService entryQualificationService;
    
    @Autowired
    private final ExamBoardService examBoardService;
    
    /**
     * Default NoArgs constructor
     */
    StudentEntryQualificationsApi() {}
    
    /**
     * Autowired constructor
     */
    StudentEntryQualificationsApi(StudentEntryQualificationService studentEntryQualificationService, EntryQualificationService entryQualificationService) {
        this.studentEntryQualificationService = studentEntryQualificationService;
    }
    
    /**
     * The StudentEntryQualificationsGet method is used to retrieve a full list of all the StudentEntryQualificationDto objects
     *
     * @return A ResponseEntity with the corresponding list of StudentEntryQualificationDto objects
     * @throws NotFoundException if nothing is found then the the NotFoundException is thrown.
     */
    @ApiOperation(value = "Collection of StudentEntryQualification entities", notes = "A GET request to the StudentEntryQualifications endpoint returns an array of all the StudentEntryQualifications in the system.", response = StudentEntryQualificationDto.class, responseContainer = "List")
    @ApiResponses(value = [
        @ApiResponse(code = 200, message = "An array of StudentEntryQualifications")
    ])
    @RequestMapping(value = "/studentEntryQualifications", produces = ["application/json"], method = RequestMethod.GET)
    public ResponseEntity<List<StudentEntryQualificationDto>> studentEntryQualificationGet() throws NotFoundException {
        LOGGER.info("** StudentEntryQualificationsApi - StudentEntryQualificationsGet");
        List<StudentEntryQualification> studentEntryQualifications = studentEntryQualificationService.findAll();
        return new ResponseEntity<List<StudentEntryQualificationDto>>(StudentEntryQualificationDto.mapFromStudentEntryQualificationsEntities(studentEntryQualifications), HttpStatus.OK);
    }
    
    
    /**
     * The StudentEntryQualificationsGet method is used to retrieve a full list of students by the StudentEntryQualificationDto objects
     *
     * @return A ResponseEntity with the corresponding list of StudentEntryQualificationDto objects
     * @throws NotFoundException if nothing is found then the the NotFoundException is thrown.
     */
    @ApiOperation(value = "Collection of StudentEntryQualification entities", notes = "A GET request to the StudentEntryQualifications endpoint returns an array of all the StudentEntryQualifications in the system.", response = StudentEntryQualificationDto.class, responseContainer = "List")
    @ApiResponses(value = [
        @ApiResponse(code = 200, message = "An array of StudentEntryQualifications")
    ])
    @RequestMapping(value = "/students/{studentId}/entryQualifications", produces = ["application/json"], method = RequestMethod.GET)
    public ResponseEntity<List<StudentEntryQualificationDto>> studentEntryQualificationGet(
            @ApiParam(value = "The unique ID of the Student retrieves the List of entryQualifications for the selected student", required = true)
            @PathVariable("studentId") Integer studentId
    ) throws NotFoundException {
        LOGGER.info("** StudentEntryQualificationsApi - StudentEntryQualificationsGet");
        List<StudentEntryQualification> studentEntryQualification = studentEntryQualificationService.getByStudent(studentId);
        if (studentEntryQualification == null) {
            throw new NotFoundException();
        }
        return new ResponseEntity<List<StudentEntryQualificationDto>>(StudentEntryQualificationDto.mapFromStudentEntryQualificationsEntities(studentEntryQualification), HttpStatus.OK);
    }
    
    /**
     * The studentEntryQualificationsIdGet method is used to retrieve an instance of a studentEntryQualificationDto object as identified by the studentEntryQualificationId provided
     *
     * @param studentEntryQualificationId the studentEntryQualification ID for the studentEntryQualification object retrieve
     * @return A ResponseEntity with the corresponding studentEntryQualificationDto object
     * @throws NotFoundException if nothing is found then the the NotFoundException is thrown.
     */
    @RequestMapping(value = "/students/{studentId}/entryQualifications/{studentEntryQualificationId}", produces = ["application/json"], method = RequestMethod.GET)
    public ResponseEntity<StudentEntryQualificationDto> studentEntryQualificationIdGet(
            @ApiParam(value = "The unique ID of the Student to retrieve", required = true)
            @PathVariable("studentId") Integer studentId,
            @ApiParam(value = "The unique ID of the StudentEntryQualification to retrieve", required = true)
            @PathVariable("studentEntryQualificationId") Integer studentEntryQualificationId
            
    ) throws NotFoundException {
        LOGGER.info("** StudentEntryQualificationsApi - StudentEntryQualificationsIdGet");
        
        StudentEntryQualification studentEntryQualification = studentEntryQualificationService.getByStudentEntryQualifications(studentId, studentEntryQualificationId);
        if (studentEntryQualification == null) {
            throw new NotFoundException();
        }
        return new ResponseEntity<StudentEntryQualificationDto>(StudentEntryQualificationDto.mapFromStudentEntryQualificationEntity(studentEntryQualification), HttpStatus.OK);
    }
    
    
    /**
     * The studentEntryQualificationsPost method is used to create a new instance of a StudentEntryQualification from the supplied StudentEntryQualificationDto
     *
     * @param studentEntryQualification the StudentEntryQualificationDto to use to create the new StudentEntryQualification object
     * @return A ResponseEntity with the newly created StudentEntryQualification object
     * @throws InvalidDataException if there is an issue with the data provided in the RequestBody then an InvalidDataException is thrown
     */
    @RequestMapping(value = "/studentEntryQualifications", produces = ["application/json"], method = RequestMethod.POST)
    public ResponseEntity<StudentEntryQualificationDto> studentEntryQualificationsPost(
            @ApiParam(value = "The StudentEntryQualification object to be created, without the contactId fields", required = true)
            @RequestBody @Valid StudentEntryQualificationDto studentEntryQualificationDto
    ) throws NotFoundException, InvalidDataException {
        LOGGER.info("** StudentEntryQualificationsApi - StudentEntryQualificationsPOST");
        
        if (studentEntryQualificationDto.studentEntryQualificationId != null) {
            throw new InvalidDataException(400, "No ID field should be provided when creating")
        }
        
        Student student
        if(studentEntryQualificationDto.studentId != null) {
            student = studentService.findById(studentEntryQualificationDto.studentId)
        }
        EntryQualification entryQualification
        if(studentEntryQualificationDto.entryQualificationId != null) {
            entryQualification = entryQualificationService.findById(studentEntryQualificationDto.entryQualificationId)
        }
        ExamBoard examBoard
        if(studentEntryQualificationDto.examBoardId != null){
            examBoard = examBoardService.findById(studentEntryQualificationDto.examBoardId)
        }
        StudentEntryQualification studentEntryQualificationToSave = StudentEntryQualificationDto.mapToStudentEntryQualificationEntity(studentEntryQualificationDto, student, entryQualification, examBoard)
        StudentEntryQualification studentEntryQualificationSaved = studentEntryQualificationService.save(studentEntryQualificationToSave)
        return new ResponseEntity<StudentEntryQualificationDto>(StudentEntryQualificationDto.mapFromStudentEntryQualificationEntity(studentEntryQualificationSaved), HttpStatus.CREATED);
    }
    
    
    
    /**
     * The studentEntryQualificationsIdPut is used to update
     *
     * @param studentEntryQualificationId the contact ID for the studentEntryQualification object to update
     * @param studentEntryQualification the new data for the studentEntryQualification object
     * @return the newly updated studentEntryQualificationDto object
     * @throws NotFoundException if nothing is found then the the NotFoundException is thrown.
     * @throws InvalidDataException if there is an issue with the data provided in the RequestBody then an InvalidDataException is thrown
     */
    @RequestMapping(value = "/students/{studentId}/entryQualifications/{studentEntryQualificationId}", produces = ["application/json"], method = RequestMethod.PUT)
    public ResponseEntity<StudentEntryQualificationDto> studentEntryQualificationIdsPut(
            @ApiParam(value = "The unique ID of the Student to retrieve", required = true) @PathVariable("studentId") Integer studentId,
            @ApiParam(value = "The unique ID of the StudentEntryQualification to retrieve", required = true) @PathVariable("studentEntryQualificationId") Integer studentEntryQualificationId,
            @ApiParam(value = "The StudentEntryQualification object to be created, without the studentEntryQualificationId fields", required = true) @RequestBody StudentEntryQualificationDto studentEntryQualification
    ) throws NotFoundException, InvalidDataException {
        LOGGER.info("** StudentEntryQualificationsApi - StudentEntryQualificationsIdPut");
        if (studentEntryQualificationId != studentEntryQualification.studentEntryQualificationId) {
            throw new InvalidDataException()
        }
        Student student
        if(studentEntryQualification.studentId != null) {
            student = studentService.findById(studentEntryQualification.studentId)
        }
        EntryQualification entryQualification
        if(studentEntryQualification.entryQualificationId != null) {
            entryQualification = entryQualificationService.findById(studentEntryQualification.entryQualificationId)
        }
        ExamBoard examBoard
        if(studentEntryQualification.examBoardId != null){
            examBoard = examBoardService.findById(studentEntryQualification.examBoardId)
        }
        
        StudentEntryQualification studentEntryQualificationToSave = StudentEntryQualificationDto.mapToStudentEntryQualificationEntity(studentEntryQualification, student, entryQualification, examBoard)
        LOGGER.info("*** StudentEntryQualificationsApi - mapToStudentEntryQualificationEntity");
        StudentEntryQualification studentEntryQualificationSaved = studentEntryQualificationService.updateStudentEntryQualification(studentEntryQualificationToSave)
        return new ResponseEntity<StudentEntryQualificationDto>(StudentEntryQualificationDto.mapFromStudentEntryQualificationEntity(studentEntryQualificationSaved), HttpStatus.OK);
    }
    
    /**
     * The studentEntryQualificationsIdPut is used to update
     *
     * @param studentEntryQualificationId the contact ID for the studentEntryQualification object to update
     * @param studentEntryQualification the new data for the studentEntryQualification object
     * @return the newly updated studentEntryQualificationDto object
     * @throws NotFoundException if nothing is found then the the NotFoundException is thrown.
     * @throws InvalidDataException if there is an issue with the data provided in the RequestBody then an InvalidDataException is thrown
     */
    @RequestMapping(value = "/studentEntryQualifications/{studentEntryQualificationId}", produces = ["application/json"], method = RequestMethod.PUT)
    public ResponseEntity<StudentEntryQualificationDto> studentEntryQualificationIdPut(
            @ApiParam(value = "The unique ID of the StudentEntryQualification to retrieve", required = true) @PathVariable("studentEntryQualificationId") Integer studentEntryQualificationId,
            @ApiParam(value = "The StudentEntryQualification object to be created, without the studentEntryQualificationId fields", required = true) @RequestBody StudentEntryQualificationDto studentEntryQualification
    ) throws NotFoundException, InvalidDataException {
        LOGGER.info("** StudentEntryQualificationsApi - StudentEntryQualificationsIdPut");
        if (studentEntryQualificationId != studentEntryQualification.studentEntryQualificationId) {
            throw new InvalidDataException()
        }
        Student student
        if(studentEntryQualification.studentId != null) {
            student = studentService.findById(studentEntryQualification.studentId)
        }
        EntryQualification entryQualification
        if(studentEntryQualification.entryQualificationId != null) {
            entryQualification = entryQualificationService.findById(studentEntryQualification.entryQualificationId)
        }
        ExamBoard examBoard
        if(studentEntryQualification.examBoardId != null){
            examBoard = examBoardService.findById(studentEntryQualification.examBoardId)
        }
        StudentEntryQualification studentEntryQualificationToSave = StudentEntryQualificationDto.mapToStudentEntryQualificationEntity(studentEntryQualification, student, entryQualification, examBoard)
        LOGGER.info("*** StudentEntryQualificationsApi - mapToStudentEntryQualificationEntity");
        StudentEntryQualification studentEntryQualificationSaved = studentEntryQualificationService.updateStudentEntryQualification(studentEntryQualificationToSave)
        return new ResponseEntity<StudentEntryQualificationDto>(StudentEntryQualificationDto.mapFromStudentEntryQualificationEntity(studentEntryQualificationSaved), HttpStatus.OK);
    }
    
    
    /**
     *  The delete is used to delete the StudentEntryQualificationById
     */
    
    @RequestMapping(value = "/studentEntryQualifications/{studentEntryQualificationId}", produces = ["application/json"], method = RequestMethod.DELETE)
    public ResponseEntity<?> delete (
            @ApiParam(value = "The unique ID of the entryQualification studentID to retrieve", required = true)
            @PathVariable("studentEntryQualificationId") Integer studentEntryQualificationId
    ) throws NotFoundException {
        LOGGER.info("** StudentEntryQualificationsApi - studentEntryQualificationsDELETE");
        studentEntryQualificationService.delete(studentEntryQualificationId);
        LOGGER.info("***StudentEntryQualificationsApi:- Deleted !!! ")
        return new ResponseEntity<>(HttpStatus.NO_CONTENT)
    }
    
    /**
     * This method retrieves the individual studentEntryQualification by Id
     */
    @RequestMapping(value = "/studentEntryQualifications/{studentEntryQualificationId}", produces = ["application/json"], method = RequestMethod.GET)
    public ResponseEntity<StudentEntryQualificationDto> studentEntryQualificationIdGet(
            @ApiParam(value = "The unique ID of the StudentEntryQualification to retrieve", required = true)
            @PathVariable("studentEntryQualificationId") Integer studentEntryQualificationId
    ) throws NotFoundException {
        LOGGER.info("** StudentEntryQualificationsApi - StudentEntryQualificationsIdGet");
        StudentEntryQualification studentEntryQualification = studentEntryQualificationService.findById(studentEntryQualificationId);
        if (studentEntryQualification == null) {
            throw new NotFoundException();
        }
        return new ResponseEntity<StudentEntryQualificationDto>(StudentEntryQualificationDto.mapFromStudentEntryQualificationEntity(studentEntryQualification), HttpStatus.OK);
    }
}
