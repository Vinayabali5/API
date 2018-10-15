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
import org.springframework.security.access.annotation.Secured
import org.springframework.stereotype.Controller
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestMethod
import org.springframework.web.bind.annotation.RequestParam

import uk.ac.reigate.domain.academic.Student
import uk.ac.reigate.domain.learning_support.ReferralReason
import uk.ac.reigate.domain.learning_support.StudentReferralReason
import uk.ac.reigate.dto.StudentReferralReasonDto
import uk.ac.reigate.exceptions.InvalidDataException
import uk.ac.reigate.exceptions.NotFoundException
import uk.ac.reigate.repositories.learning_support.ReferralReasonRepository
import uk.ac.reigate.repositories.learning_support.StudentReferralReasonRepository
import uk.ac.reigate.services.ReferralReasonService
import uk.ac.reigate.services.StudentReferralReasonService
import uk.ac.reigate.services.StudentService


@Controller
@RequestMapping(value = "/api/students/{studentId}/referralReasons", produces = [ APPLICATION_JSON_VALUE ])
@Api(value = "/api/students/{studentId}/referralReasons", description = "the studentReferralReasons API")
public class StudentReferralReasonsApi {
    
    private static final Logger LOGGER = Logger.getLogger(StudentReferralReasonsApi.class);
    
    @Autowired
    private final StudentService studentService;
    
    @Autowired
    private final StudentReferralReasonService studentReferralReasonService;
    
    @Autowired
    private final ReferralReasonService referralReasonService;
    
    @Autowired
    StudentReferralReasonRepository studentReferralReasonRepository
    
    @Autowired
    ReferralReasonRepository referralReasonRepository
    
    /**
     * Default NoArgs constructor
     */
    StudentReferralReasonsApi() {}
    
    /**
     * Autowired constructor
     */
    StudentReferralReasonsApi(StudentService studentService, ReferralReasonService referralReasonService) {
        this.studentService = studentService;
    }
    
    /**
     * The studentReferralReasonsGet method is used to retrieve a full list of all the StudentReferralReasonDto objects
     *
     * @return A ResponseEntity with the corresponding list of StudentReferralReasonDto objects
     * @throws NotFoundException if nothing is found then the the NotFoundException is thrown.
     */
    @ApiOperation(value = "Collection of StudentReferralReason entities", notes = "A GET request to the StudentReferralReasons endpoint returns an array of all the studentReferralReasons in the system.", response = StudentReferralReasonDto.class, responseContainer = "List")
    @ApiResponses(value = [
        @ApiResponse(code = 200, message = "An array of studentReferralReasons")
    ])
    @RequestMapping(value = "", produces = ["application/json"], method = RequestMethod.GET)
    public ResponseEntity<List<StudentReferralReasonDto>> studentReferralReasonsGet(
            @ApiParam(value = "The unique ID of the StudentReferralReason to retrieve", required = true)
            @PathVariable("studentId") Integer studentId) throws NotFoundException {
        LOGGER.info("** StudentReferralReasonsApi - studentReferralReasonsGet");
        List<StudentReferralReason> studentReferralReasons = studentReferralReasonService.getByStudent(studentId);
        if(studentReferralReasons.size() != 0) {
            LOGGER.info("II Results found: " + studentReferralReasons.size())
            List<StudentReferralReasonDto> studentConcession = new ArrayList<StudentReferralReasonDto>()
            return new ResponseEntity<List<StudentReferralReasonDto>>(StudentReferralReasonDto.mapFromStudentReferralReasonsEntities(studentReferralReasons), HttpStatus.OK)
        } else {
            throw new NotFoundException()
        }
    }
    
    /**
     * The studentReferralReasonsPost method is used to create a new instance of a StudentReferralReason from the supplied StudentReferralReasonDto
     *
     * @param studentReferralReason the StudentReferralReasonDto to use to create the new StudentReferralReason object
     * @return A ResponseEntity with the newly created StudentReferralReason object
     * @throws InvalidDataException if there is an issue with the data provided in the RequestBody then an InvalidDataException is thrown
     */
    @RequestMapping(value = "", produces = ["application/json"], method = RequestMethod.POST)
    public ResponseEntity<StudentReferralReasonDto> studentReferralReasonsPost(
            @ApiParam(value = "The unique ID of the StudentReferralReason to retrieve", required = true)
            @PathVariable("studentId") Integer studentId,
            @ApiParam(value = "The StudentReferralReason object to be created", required = true)
            @RequestBody @Valid StudentReferralReasonDto studentReferralReason)
    throws NotFoundException, InvalidDataException {
        LOGGER.info("** StudentReferralReasonsApi - studentReferralReasonsPOST");
        
        if (studentId == studentReferralReason.studentId) {
            Student student
            if (studentReferralReason.studentId != null) {
                student = studentService.findById(studentReferralReason.studentId)
            }
            
            ReferralReason referralReason
            if(studentReferralReason.referralReasonId != null) {
                referralReason = referralReasonService.findById(studentReferralReason.referralReasonId)
            }
            
            LOGGER.info("** StudentReferralReasonsApi - studentReferralReasonsPOST12");
            StudentReferralReason studentReferralReasonToSave = StudentReferralReasonDto.mapToStudentReferralReasonEntity(studentReferralReason, student, referralReason )
            
            StudentReferralReason studentReferralReasonSaved = studentReferralReasonService.save(studentReferralReasonToSave)
            
            return new ResponseEntity<StudentReferralReasonDto>(StudentReferralReasonDto.mapFromStudentReferralReasonEntity(studentReferralReasonSaved), HttpStatus.CREATED);
        } else {
            throw new InvalidDataException()
        }
    }
    
    
    /**
     * This method is using studentId and referralReasonId to delete By StudentReferralReason
     */
    @Secured('ROLE_Exams Officer')
    @RequestMapping(value = "/deleteByStudentIdAndConcessionId", produces = ["application/json"], method = RequestMethod.DELETE)
    public ResponseEntity<StudentReferralReasonDto> studentReferralReasonsDeleteByStudentAndReferralReason(
            @ApiParam(value = "The unique ID of the CourseGroup to retrieve", required = true)  @RequestParam (value='studentId', required = false) Integer studentId,
            @ApiParam(value = "The referral reason ID to be deleted", required = true) @RequestParam (value='referralReasonId', required = false) Integer referralReasonId ) {
        LOGGER.info("** StudentReferralReasonsApi - studentReferralReasonsDelete");
        
        List<StudentReferralReason> studentReferralReasons = studentReferralReasonService.deleteByStudent(studentId, referralReasonId);
        LOGGER.info("***StudentReferralReasonsApi:- Deleted !!! ")
        return new ResponseEntity<List<StudentReferralReasonDto>>(StudentReferralReasonDto.mapFromStudentReferralReasonsEntities(studentReferralReasons), HttpStatus.OK)
    }
}
