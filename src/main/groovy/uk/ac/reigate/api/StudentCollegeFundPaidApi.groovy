package uk.ac.reigate.api

import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE

import io.swagger.annotations.Api
import io.swagger.annotations.ApiOperation
import io.swagger.annotations.ApiParam
import io.swagger.annotations.ApiResponse
import io.swagger.annotations.ApiResponses
import java.util.logging.Logger
import javax.validation.Valid
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.stereotype.Controller
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestMethod
import uk.ac.reigate.domain.academic.Student
import uk.ac.reigate.domain.admissions.CollegeFundPaid
import uk.ac.reigate.dto.StudentBursaryDto
import uk.ac.reigate.dto.StudentCollegeFundPaidDto
import uk.ac.reigate.exceptions.InvalidDataException
import uk.ac.reigate.exceptions.NotFoundException
import uk.ac.reigate.services.CollegeFundPaidService
import uk.ac.reigate.services.StudentService

@Controller
@RequestMapping(value="/api/students", produces=[APPLICATION_JSON_VALUE])
@Api(value="/api/students", description = "The Student College Fund API")
class StudentCollegeFundPaidApi {
    
    
    @Autowired
    StudentService studentService
    
    @Autowired
    CollegeFundPaidService collegeFundPaidService
    
    
    StudentCollegeFundPaidApi(){}
    
    
    
    /**
     * @param studentId - student Id of whose the college Ffund pai has to be retrieved
     * @return
     */
    @ApiOperation(value=" Retrieves an indivdual instance of a StudentCollegeFundPaid identified by the studentId", notes="")
    @ApiResponses(value= [
        @ApiResponse(code = 200 , message ="Returns a copy of StudentCollegeFundPaid identified by student Id"),
        @ApiResponse(code= 404, message = "Returns an Error object stating that the resource could not be found. This happens if the specified id cannot be found in the database.")
    ]
    )
    @RequestMapping(value="/{studentId}/collegeFund", produces = [APPLICATION_JSON_VALUE], method = RequestMethod.GET)
    public ResponseEntity<StudentCollegeFundPaidDto> getStudentCollegeFundPaid(
            @ApiParam(value = "The student Id" , required = true)
            @PathVariable("studentId")  Integer studentId
    ){
        Student student = studentService.findById(studentId)
        if(student == null){
            throw new NotFoundException("No Such Student Found");
        }
        CollegeFundPaid collegeFundPaid = student.collegeFundPaid != null ? collegeFundPaidService.findById(student.collegeFundPaid.id): null
        if(collegeFundPaid!=null){
            StudentCollegeFundPaidDto studentCollegeFundPaidDto = new  StudentCollegeFundPaidDto(student, collegeFundPaid)
            return new ResponseEntity<StudentCollegeFundPaidDto>(studentCollegeFundPaidDto, HttpStatus.OK)
        }else
            throw new NotFoundException("Not Found");
    }
    
    
    
    
    /**
     * @param studentId - student Id of whose the collegeFund Paid has to be updated.
     * @param studentCollegeFundDto  - Request Body to be updated.
     * @return
     */
    @ApiOperation(value=" Retrieves an indivdual instance of a CollegeFundPaid identified by the studentId", notes="")
    @ApiResponses(value= [
        @ApiResponse(code = 200 , message ="Returns the updated StudentCollegeFundPaidDto"),
        @ApiResponse(code= 404, message = "Returns an Error object stating that the resource could not be found. This happens if the specified id cannot be found in the database.")
    ]
    )
    @RequestMapping(value = "/{studentId}/collegeFund", produces =[APPLICATION_JSON_VALUE], method= RequestMethod.PUT)
    public ResponseEntity<StudentCollegeFundPaidDto> postStudentCollegeFundPaid(
            @ApiParam(value = " The unique studentId ", required = true)
            @PathVariable(value = "studentId", required = true) Integer studentId,
            @ApiParam(value ="The StudentCollegeFundDto request body", required = true)
            @Valid @RequestBody StudentCollegeFundPaidDto studentCollegeFundDto
    ){
        if(studentId == studentCollegeFundDto.studentId){
            Student student = studentService.findById(studentId)
            CollegeFundPaid collegeFundPaid = studentCollegeFundDto.collegeFundId != null? collegeFundPaidService.findById(studentCollegeFundDto.collegeFundId) : null
            student = studentService.updateCollegeFundPaid(student, collegeFundPaid,studentCollegeFundDto.collegeFundPaidYears )
            return new ResponseEntity<StudentCollegeFundPaidDto>(new  StudentCollegeFundPaidDto(student, collegeFundPaid), HttpStatus.OK)
        } else
            throw new InvalidDataException()
    }
}
