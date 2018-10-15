package uk.ac.reigate.api.exam

import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE
import io.swagger.annotations.Api
import io.swagger.annotations.ApiOperation
import io.swagger.annotations.ApiParam
import io.swagger.annotations.ApiResponse
import io.swagger.annotations.ApiResponses

import javax.persistence.EntityManager
import javax.validation.Valid

import org.apache.log4j.Logger
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.security.access.annotation.Secured
import org.springframework.stereotype.Controller
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestMethod
import org.springframework.web.bind.annotation.RequestParam
import uk.ac.reigate.domain.academic.Student
import uk.ac.reigate.domain.exam.EdiStatusType
import uk.ac.reigate.domain.exam.ExamOption
import uk.ac.reigate.domain.exam.StatusType
import uk.ac.reigate.domain.exam.StudentOptionEntry
import uk.ac.reigate.dto.ErrorMessageDto
import uk.ac.reigate.dto.exam.StudentOptionEntryDto
import uk.ac.reigate.exceptions.NotFoundException
import uk.ac.reigate.model.SearchResult
import uk.ac.reigate.services.EdiStatusTypeService
import uk.ac.reigate.services.StatusTypeService
import uk.ac.reigate.services.StudentService
import uk.ac.reigate.services.exam.ExamOptionService
import uk.ac.reigate.services.exam.StudentOptionEntryService


@Controller
@RequestMapping(value = "/api", produces = [ APPLICATION_JSON_VALUE ])
@Api(value = "/api", description = "the optionEntries API")
public class StudentOptionEntriesApi {
    
    private static final Logger LOGGER = Logger.getLogger(StudentOptionEntriesApi.class);
    
    @Autowired
    EntityManager entityManager
    
    @Autowired
    private final StudentOptionEntryService studentOptionEntryService;
    
    @Autowired
    private final StudentService studentService;
    
    @Autowired
    private final StatusTypeService statusTypeService;
    
    @Autowired
    private final EdiStatusTypeService ediStatusTypeService;
    
    @Autowired
    private final ExamOptionService examOptionService;
    /**
     * Default No Args constructor
     */
    StudentOptionEntriesApi() {}
    
    /**
     * Default Autowired constructor
     */
    StudentOptionEntriesApi(StudentOptionEntryService studentOptionEntryService) {
        this.studentOptionEntryService = studentOptionEntryService;
    }
    
    /**
     * The studentOptionEntriesGet method is used to retrieve a full list of all the StudentOptionEntryDto objects
     *
     * @return A ResponseEntity with the corresponding list of StudentOptionEntryDto objects
     * @throws NotFoundException if nothing is found then the the NotFoundException is thrown.
     */
    @ApiOperation(value = "Collection of StudentOptionEntry entities", notes = "A GET request to the StudentOptionEntries endpoint returns an array of all the studentOptionEntries in the system.", response = StudentOptionEntryDto.class, responseContainer = "List")
    @ApiResponses(value = [
        @ApiResponse(code = 200, message = "An array of studentOptionEntries")
    ])
    @RequestMapping(value = "/studentOptionEntries", produces = ["application/json"], method = RequestMethod.GET)
    public ResponseEntity<List<StudentOptionEntryDto>> studentOptionEntriesGet() throws NotFoundException {
        LOGGER.info("** StudentOptionEntriesApi - studentOptionEntriesGet");
        List<StudentOptionEntry> studentOptionEntries = studentOptionEntryService.findAll();
        return new ResponseEntity<List<StudentOptionEntryDto>>(StudentOptionEntryDto.mapFromStudentOptionEntriesEntities(studentOptionEntries), HttpStatus.OK);
    }
    
    
    /** The studentOptionEntryGetByStudentAndOption method is used to retrieve a StudentOptionEntryDto object of a given StudentId and OptionId
     * @param studentId
     * @param optionId
     * @return
     * @throws NotFoundException
     */
    @RequestMapping(value = "/students/{studentId}/optionEntries/{optionId}", produces = ["application/json"], method = RequestMethod.GET)
    public ResponseEntity<StudentOptionEntryDto> studentOptionEntryGetByStudentAndOption(
            @ApiParam(value = "The unique ID of the studentId to retrieve", required = true)
            @PathVariable("studentId") Integer studentId,
            @ApiParam(value = "The unique ID of the Option to retrieve", required = true)
            @PathVariable("optionId") Integer optionId) throws NotFoundException {
        StudentOptionEntry studentOptionEntry = studentOptionEntryService.findByStudentAndOption(studentId, optionId)
        return new ResponseEntity<StudentOptionEntryDto>(new StudentOptionEntryDto(studentOptionEntry), HttpStatus.OK);
    }
    
    
    /** The studentOptionEntrysGetByStudent method is used to retrieve List of StudentOptionEntryDto objects of a given Student
     * @param studentId
     * @return
     * @throws NotFoundException
     */
    @RequestMapping(value = "/students/{studentId}/optionEntries", produces = ["application/json"], method = RequestMethod.GET)
    public ResponseEntity<List<StudentOptionEntryDto>> studentOptionEntrysGetByStudent(
            @ApiParam(value = "The unique ID of the StudentOptionEntry to retrieve", required = true)
            @PathVariable("studentId") Integer studentId,
            @ApiParam(value="yearId", required=false)
            @RequestParam(value="yearId", required= false) Integer yearId
    ) throws NotFoundException {
        List<StudentOptionEntry> optionEntries = new ArrayList();
        if(yearId){
            optionEntries = studentOptionEntryService.getByOptionYearId(yearId, studentId);
        }
        if(optionEntries == null){
            throw new NotFoundException();
        }
        return new ResponseEntity<List<StudentOptionEntryDto>>(StudentOptionEntryDto.mapFromStudentOptionEntriesEntities(optionEntries), HttpStatus.OK)
    }
    
    
    /**  The studentOptionEntryPost method is used to create a new StudentOptionEntry for a Student
     * @param studentId
     * @param studentOptionEntryDto
     * @return
     * @throws NotFoundException
     */
    @RequestMapping(value = "/students/{studentId}/optionEntries", produces = ["application/json"], method = RequestMethod.POST)
    public ResponseEntity<StudentOptionEntryDto> studentOptionEntryPost(
            @ApiParam(value = "The unique ID of the studentId to retrieve", required = true)
            @PathVariable("studentId") Integer studentId,
            @ApiParam(value = "The StudentOptionEntry object to be created, without the studentYearId fields", required = true)
            @RequestBody @Valid  StudentOptionEntryDto studentOptionEntryDto
    ) throws NotFoundException {
        
        LOGGER.info("** StudentOptionEntrysApi - studentOptionEntrysGetByStudentPOST");
        // Check to see if the studentId and examOptionId does not already exist in the database. if the entry exist through the BAD_REQUEST with messgae.
        StudentOptionEntry studentOptionEntry = studentOptionEntryService.findByStudentAndOption(studentId, studentOptionEntryDto.examOptionId)
        if(studentOptionEntry){
            return new ResponseEntity(new ErrorMessageDto( "studentId and examOptionId exists","The entry code has already been added to this student."), HttpStatus.BAD_REQUEST)
        }
        
        Student student
        if(studentOptionEntryDto.studentId != null){
            student = studentService.findById(studentOptionEntryDto.studentId)
        }
        ExamOption examOption
        if(studentOptionEntryDto.examOptionId != null){
            examOption = examOptionService.findById(studentOptionEntryDto.examOptionId)
        }
        StatusType statusType
        if(studentOptionEntryDto.statusTypeId != null){
            statusType = statusTypeService.findById(studentOptionEntryDto.statusTypeId)
        }
        EdiStatusType ediStatusType
        if(studentOptionEntryDto.ediStatusTypeId != null){
            ediStatusType = ediStatusTypeService.findById(studentOptionEntryDto.ediStatusTypeId)
        }
        
        StudentOptionEntry studentOptionEntryToSave = StudentOptionEntryDto.mapToStudentOptionEntryEntity(studentOptionEntryDto, student, examOption, statusType, ediStatusType)
        StudentOptionEntry studentOptionEntrySaved = studentOptionEntryService.save(studentOptionEntryToSave)
        return new ResponseEntity<StudentOptionEntryDto>(StudentOptionEntryDto.mapFromStudentOptionEntryEntity(studentOptionEntrySaved), HttpStatus.CREATED);
    }
    
    
    
    /**  The studentOptionEntryPut method is used to update an existing StudentOptionEntry for a StudentId 
     * @param studentId
     * @param optionId
     * @param studentOptionEntryDto
     * @return
     * @throws NotFoundException
     */
    @RequestMapping(value = "/students/{studentId}/optionEntries/{optionId}", produces = ["application/json"], method = RequestMethod.PUT)
    public ResponseEntity<StudentOptionEntryDto> studentOptionEntryPut(
            @ApiParam(value = "The unique ID of the studentId to retrieve", required = true)
            @PathVariable("studentId") Integer studentId,
            @ApiParam(value = "The unique ID of the Option to retrieve", required = true)
            @PathVariable("optionId") Integer optionId,
            @ApiParam(value = "The StudentOptionEntry object to be created, without the studentYearId fields", required = true)
            @RequestBody StudentOptionEntryDto studentOptionEntryDto) throws NotFoundException {
        
        LOGGER.info("** StudentOptionEntrysApi - studentOptionEntrysPUT");
        
        StudentOptionEntry studentOptionEntry = studentOptionEntryService.findByStudentAndOption(studentOptionEntryDto.studentId, studentOptionEntryDto.examOptionId)
        if(studentOptionEntry != null){
            studentOptionEntry.statusType = studentOptionEntryDto.statusTypeId != null ? statusTypeService.findById(studentOptionEntryDto.statusTypeId) : studentOptionEntry.statusType
            studentOptionEntry.ediStatusType = studentOptionEntryDto.ediStatusTypeId != null ? ediStatusTypeService.findById(studentOptionEntryDto.ediStatusTypeId) : studentOptionEntry.ediStatusType
            studentOptionEntry.resit = studentOptionEntryDto.resit
            studentOptionEntry.privateStudent = studentOptionEntryDto.privateStudent
            StudentOptionEntry studentOptionEntrySaved = studentOptionEntryService.save(studentOptionEntry)
            return new ResponseEntity<StudentOptionEntryDto>(new StudentOptionEntryDto(studentOptionEntry), HttpStatus.OK);
        }
    }
    
    
    /** The delete method is used to delete an existing StudentOptionEntry for a StudentId 
     * @param studentId
     * @param examOptionId
     * @return
     * @throws NotFoundException
     */
    @RequestMapping(value = "/students/{studentId}/optionEntries/{examOptionId}", produces = ["application/json"], method = RequestMethod.DELETE)
    public ResponseEntity<?> delete (
            @ApiParam(value = "The unique ID of the studentId to retrieve", required = true)
            @PathVariable("studentId") Integer studentId,
            @ApiParam(value = "The unique ID of the student options entry studentID to retrieve", required = true)
            @PathVariable("examOptionId") Integer examOptionId
    ) throws NotFoundException {
        LOGGER.info("** StudentOptionEntrysApi - StudentOptionEntrysDELETE");
        studentOptionEntryService.deleteByIds(studentId, examOptionId);
        LOGGER.info("***studentOptionEntryApi:- Deleted !!! ")
        return new ResponseEntity<>(HttpStatus.NO_CONTENT)
    }
    
    
    
    /** studentExamAmendmentsRequired is used to update the Edi Status as 'Mark Ammendment' for a Student whose Status Type is 'Live'
     * @param studentId
     * @return
     * @throws NotFoundException
     */
    @RequestMapping(value="/students/{studentId}/examAmendmentsRequired",produces=["application/json"],method=RequestMethod.POST)
    public ResponseEntity<?> studentExamAmendmentsRequired(
            @ApiParam(value = "The unique ID of the studentId to retrieve", required = true)
            @PathVariable("studentId") Integer studentId
    ) throws NotFoundException{
        LOGGER.info("StudentOptionEntryApi ---- studentExamAmendmentsRequired--POST");
        List<StudentOptionEntry> studentOptionEntries = studentOptionEntryService.getByStudent(studentId)
        StatusType liveStatus = statusTypeService.findById(1)
        EdiStatusType needsAmendmemt = ediStatusTypeService.findById(6)
        studentOptionEntries.each {it->
            
            if(it.statusType.id == liveStatus.id){
                it.ediStatusType=needsAmendmemt
                studentOptionEntryService.save(it)
            }
        }
        return new ResponseEntity<?>(HttpStatus.OK);
    }
    
    
}