package uk.ac.reigate.api.exam


import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE

import java.util.List;

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
import uk.ac.reigate.domain.exam.ExamBoard
import uk.ac.reigate.domain.exam.StudentAlternativeUci
import uk.ac.reigate.dto.exam.StudentAlternativeUciDto
import uk.ac.reigate.exceptions.InvalidDataException
import uk.ac.reigate.exceptions.NotFoundException
import uk.ac.reigate.services.StudentService
import uk.ac.reigate.services.exam.ExamBoardService
import uk.ac.reigate.services.exam.StudentAlternativeUciService


/**
 * @author sat
 *
 */
@Controller
@RequestMapping(value = "/api", produces = [ APPLICATION_JSON_VALUE ])
@Api(value = "/api", description = "the studentAlternativeUci API")
class StudentAlternativeUciApi {
    
    private static final Logger LOGGER = Logger.getLogger(StudentAlternativeUciApi.class);
    
    @Autowired
    private final StudentAlternativeUciService studentAlternativeUciService;
    @Autowired
    private final StudentService studentService;
    @Autowired
    private final ExamBoardService examBoardService;
    
    StudentAlternativeUciApi(){}
    
    StudentAlternativeUciApi(StudentAlternativeUciService studentAlternativeUciService){
        this.studentAlternativeUciService=studentAlternativeUciService
    }
    
    /**
     * The StudentAlternativeUciGet method is used to retrieve a list of alternativeUci for a exam board by the StudentAlternativeUciDto objects
     *
     * @return A ResponseEntity with the corresponding list of StudentAlternativeUciDto objects
     * @throws NotFoundException if nothing is found then the the NotFoundException is thrown.
     */
    @ApiOperation(value = "Collection of StudentAlternativeUci entities", notes = "A GET request to the StudentAlternativeUci endpoint returns an array of all the alternative uci for the student.", response = StudentAlternativeUciDto.class, responseContainer = "List")
    @ApiResponses(value = [
        @ApiResponse(code = 200, message = "An array of StudentAlternativeUci")
    ])
    @RequestMapping(value = "/students/{studentId}/alternative-ucis", produces = ["application/json"], method = RequestMethod.GET)
    public ResponseEntity<List<StudentAlternativeUciDto>> studentAlternativeUciGet(
            @ApiParam(value = "The unique ID of the Student retrieves the List of Altenative Ucis for the selected student", required = true)
            @PathVariable("studentId") Integer studentId
    ) throws NotFoundException {
        LOGGER.info("** StudentAlternativeUciApi - StudentAlternativeUciGet");
        List<StudentAlternativeUciDto> studentAlternativeUcis = studentAlternativeUciService.getByStudent(studentId);
        if (studentAlternativeUcis == null) {
            throw new NotFoundException();
        }
        return new ResponseEntity<List<StudentAlternativeUciDto>>(StudentAlternativeUciDto.mapFromStudentAlternativeUciEntities(studentAlternativeUcis), HttpStatus.OK);
    }
    
    /**
     * The StudentAlternativeUciGet method is used to retrieve a list of alternativeUci for a exam board by the StudentAlternativeUciDto objects
     *
     * @return A ResponseEntity with the corresponding list of StudentAlternativeUciDto objects
     * @throws NotFoundException if nothing is found then the the NotFoundException is thrown.
     */
    @ApiOperation(value = "A record of StudentAlternativeUci entity", notes = "A GET request to the StudentAlternativeUci endpoint returns a record for a particular student with an exam board id.", response = StudentAlternativeUciDto.class)
    @ApiResponses(value = [
        @ApiResponse(code = 200, message = "A record of StudentAlternativeUci")
    ])
    @RequestMapping(value = "/students/{studentId}/alternative-ucis/{examBoardId}", produces = ["application/json"], method = RequestMethod.GET)
    public ResponseEntity<StudentAlternativeUciDto> studentAlternativeUciExamBoardIdGet(
            @ApiParam(value = "The unique ID of the Student", required = true) @PathVariable("studentId") Integer studentId,
            @ApiParam(value = "The unique ID of the Student", required = true) @PathVariable("examBoardId") Integer examBoardId
    ) throws NotFoundException {
        LOGGER.info("** StudentAlternativeUciApi - StudentAlternativeUciGet");
        StudentAlternativeUci studentAlternativeUci = studentAlternativeUciService.getByStudentAndExamBoardId(studentId, examBoardId);
        if(studentAlternativeUci == null){
            throw new NotFoundException();
        }
        return new ResponseEntity<StudentAlternativeUciDto>(StudentAlternativeUciDto.mapFromStudentAlternativeUciEntity(studentAlternativeUci),HttpStatus.OK);
    }
    
    
    
    /**alternativeUciExists method checks whether the Alternative Uci already exists for a given Student Id and ExamBoard ID
     * 
     * @param studentAlternativeUciDto - The dto object that is to be created.
     * @return true if alternative Uci exists
     * @return false if alternative Uci does not exist
     */
    public boolean alternativeUciExists(StudentAlternativeUciDto studentAlternativeUciDto){
        StudentAlternativeUci newStudentAlternativeUciObj=studentAlternativeUciService.getByStudentAndExamBoardId(studentAlternativeUciDto.studentId, studentAlternativeUciDto.examBoardId);
        if(newStudentAlternativeUciObj!=null){
            return true;
        }else
            return false
    }
    
    /**
     * The studentAlternativeUciStudentIdPost method is used to create a new instance of a StudentAlternativeUci from the supplied StudentAlternativeUciDto.
     * @param studentId the studentId
     * @param studentAlternativeUciDto the StudentAlternativeUciDto to use to create the new StudentAlternativeUci object
     * @return A ResponseEntity with the newly created StudentAlternativeUci object
     * @throws InvalidDataException if there is an issue with the data provided in the RequestBody then an InvalidDataException is thrown
     */
    @ApiOperation(value = "Creates a new StudentAlternativeUci entity", notes = "A POST request to the alternative-ucis endpoint with a StudentAlternativeUCI object in the request body will create a new StudentAlternativeUCI entity in the database.", response = StudentAlternativeUciDto.class)
    @ApiResponses(value = [
        @ApiResponse(code = 201, message = "Returns a copy of the created StudentAlternativeUci entity"),
        @ApiResponse(code = 400, message = "Returns an Error object stating the request body does not match the expected input.")
    ])
    @RequestMapping(value = "/students/{studentId}/alternative-ucis", produces = ["application/json"], method = RequestMethod.POST)
    public ResponseEntity<StudentAlternativeUciDto> studentAlternativeUciStudentIdPost(
            @ApiParam(value ="The Student Id with which the new object is created",required=true) @PathVariable(value="studentId") Integer studentId,
            @ApiParam(value = "The StudentAlternativeUci object to be created, without the alternativeUci fields", required = true) @RequestBody @Valid StudentAlternativeUciDto studentAlternativeUci
    ) throws NotFoundException, InvalidDataException {
        LOGGER.info("**StudentAlternativeUCI-- StudentAlternative POST**");
        if(studentId!=studentAlternativeUci.studentId){
            throw new InvalidDataException(400,"Incorrect data provided");
        }
        Student student;
        ExamBoard examBoard;
        student=studentService.findById(studentAlternativeUci.studentId);
        examBoard=examBoardService.findById(studentAlternativeUci.examBoardId);
        if (student!=null && examBoard!=null){
            if(!alternativeUciExists(studentAlternativeUci)){
                StudentAlternativeUci studentAlternativeUciToSave = StudentAlternativeUciDto.mapFromStudentAlternativeUciDto(student,examBoard,studentAlternativeUci);
                StudentAlternativeUci studentAlternativeUciSaved = studentAlternativeUciService.save(studentAlternativeUciToSave);
                return new ResponseEntity<StudentAlternativeUciDto>(StudentAlternativeUciDto.mapFromStudentAlternativeUciEntity(studentAlternativeUciSaved),HttpStatus.CREATED);
            }else {
                throw new InvalidDataException(400, "Alternative UCI already exists");
            }
        }else
            throw new InvalidDataException(400,"Incorrect data provided");
    }
    
    /**
     * The studentAlternativeUciPut method is used to update an existing instance of a StudentAlternativeUci from the supplied StudentAlternativeUciDto
     * @param studentId the studentId
     * @param examBoardId the examBoardId
     * @param studentAlternativeUciDto the StudentAlternativeUciDto to use to create the new StudentAlternativeUci object
     * @return A ResponseEntity with the newly created StudentAlternativeUci object
     * @throws InvalidDataException if there is an issue with the data provided in the RequestBody then an InvalidDataException is thrown
     */
    
    @ApiOperation(value = "Updates an existing StudentAlternativeUci entity", notes = "A PUT request to the alternative-ucis endpoint with a StudentAlternativeUCI object in the request body will update an existing StudentAlternativeUCI entity in the database.", response = StudentAlternativeUciDto.class)
    @ApiResponses(value = [
        @ApiResponse(code = 201, message = "Returns a copy of the updated StudentAlternativeUci entity"),
        @ApiResponse(code = 400, message = "Returns an Error object stating the request body does not match the expected input.")
    ])
    @RequestMapping(value="/students/{studentId}/alternative-ucis/{examBoardId}",produces = ["application/json"],method = RequestMethod.PUT)
    public ResponseEntity<StudentAlternativeUciDto> studentAlternativeUciPut(
            @ApiParam(value="The student id to retrieve",required=true) @PathVariable ("studentId") Integer studentId,
            @ApiParam(value="The Exam Board id to retrieve",required = true) @PathVariable("examBoardId") Integer examBoardId,
            @ApiParam(value="The Exam Board id to retrieve",required = true) @RequestBody StudentAlternativeUciDto studentAlternativeUci) throws NotFoundException, InvalidDataException{
        
        LOGGER.info("**StudentAlternativeUCI ---StudentAlternativeUciPUT");
        
        if(studentId!= studentAlternativeUci.studentId || examBoardId != studentAlternativeUci.examBoardId){
            throw new InvalidDataException(400,"Incorrect data provided");
        }
        Student student = studentService.findById(studentId);
        ExamBoard examBoard=examBoardService.findById(examBoardId);
        if(student!=null && examBoard!=null){
            StudentAlternativeUci studentAlternativeUciObj=studentAlternativeUciService.getByStudentAndExamBoardId(studentId, examBoardId);
            if(studentAlternativeUciObj!=null){
                studentAlternativeUciObj.setAlternativeUci(studentAlternativeUci.alternativeUci);
                StudentAlternativeUci studentAlternativeUciSaved = studentAlternativeUciService.save(studentAlternativeUciObj);
                return new ResponseEntity<StudentAlternativeUciDto>(StudentAlternativeUciDto.mapFromStudentAlternativeUciEntity(studentAlternativeUciSaved),HttpStatus.OK);
            }else
                throw new InvalidDataException(400, "Student Id "+studentId + " with Exam Board Id: "+examBoardId+" does not exist");
        }else
        
            throw new InvalidDataException(400,"Incorrect data provided");
    }
    
    
    
    /**  This method is to delete an StudentAlternativeUci Object of a particular studentId and examBoardId
     * @param studentId
     * @param examBoardId
     * @return
     */
    @RequestMapping(value = "/students/{studentId}/alternative-ucis/{examBoardId}", produces = ["application/json"], method = RequestMethod.DELETE)
    public ResponseEntity<?> deleteAlternativeUci(
            @ApiParam(value="The student id to retrieve",required=true) @PathVariable("studentId") Integer studentId,
            @ApiParam(value="The examBoardId",required=true) @PathVariable("examBoardId")Integer examBoardId) {
        
        if(studentAlternativeUciService.deleteAltenativeUci(studentId, examBoardId)){
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
        
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
