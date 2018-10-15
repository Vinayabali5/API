package uk.ac.reigate.api

import io.swagger.annotations.Api
import io.swagger.annotations.ApiOperation
import io.swagger.annotations.ApiParam
import io.swagger.annotations.ApiResponse
import io.swagger.annotations.ApiResponses
import org.springframework.http.HttpStatus

import java.util.List
import javax.validation.Valid
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.MediaType
import org.springframework.http.ResponseEntity
import org.springframework.stereotype.Controller
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestMethod
import uk.ac.reigate.domain.academic.Student
import uk.ac.reigate.domain.ilr.RestrictedUseIndicator
import uk.ac.reigate.dto.GenderDto
import uk.ac.reigate.dto.StudentDataSharingOptionsDto
import uk.ac.reigate.exceptions.NotFoundException
import uk.ac.reigate.services.RestrictedUseIndicatorService
import uk.ac.reigate.services.StudentService

@Controller
@RequestMapping(value = "/api/students", produces =  [ MediaType.APPLICATION_JSON_VALUE ])
@Api(value="/api/students", description ="Student Data Sharing Options API")
public class StudentDataSharingOptionApi {
    
    
    @Autowired
    StudentService studentService
    
    @Autowired
    RestrictedUseIndicatorService restrictedUseIndicatorService
    
    
    /**
     * @param studentId  - 
     * @return
     * @throws NotFoundException
     */
    @ApiOperation(value = "Retrieves Data Sharing Options details of a particular student", notes=" A GET method to retrieves Data Sharing Options details of a particular student")
    @ApiResponses(value =[
        @ApiResponse(code= 200, message= "Returns a copy of the Student as identified by the studentId"),
        @ApiResponse(code =404, message ="Returns an Error object stating that the resource could not be found. This happens if the specified id cannot be found in the database.")
    ]
    )
    @RequestMapping(value ="{studentId}/dataSharingOptions", produces =[
        MediaType.APPLICATION_JSON_VALUE
    ], method = RequestMethod.GET)
    public ResponseEntity<StudentDataSharingOptionsDto> getDataSharing(
            @ApiParam(value= "studentId", required = true)
            @PathVariable(value = "studentId", required = true) Integer studentId
    ) throws NotFoundException{
        Student student = studentService.findById(studentId);
        if(student == null){
            throw new NotFoundException();
        }
        return new ResponseEntity<StudentDataSharingOptionsDto>(new StudentDataSharingOptionsDto(student), HttpStatus.OK);
    }
    
    
    /**
     * @param studentId
     * @param studentDataSharingOptionsDto
     * @return
     * @throws NotFoundException
     */
    @ApiOperation(value ="Updates Data Sharing Details for a student", notes ="PUT method to update the Data Sharing details")
    @ApiResponses(value = [
        @ApiResponse(code = 200 , message = "Returns a copy of the Student as identified by the studentId"),
        @ApiResponse(code = 404, message = "Returns an Error object stating that the resource could not be found. This happens if the specified id cannot be found in the database.")
    ])
    @RequestMapping(value ="/{studentId}/dataSharingOptions", produces = [
        MediaType.APPLICATION_JSON_VALUE
    ], method= RequestMethod.PUT)
    public ResponseEntity<StudentDataSharingOptionsDto> putDataSharing(
            @ApiParam(value ="studentId", required = true)
            @PathVariable(value ="studentId", required = true) Integer studentId,
            @ApiParam(value ="", required = true) @Valid @RequestBody StudentDataSharingOptionsDto studentDataSharingOptionsDto
    )throws NotFoundException{
        Student student = studentService.findById(studentId);
        if(student == null){
            throw new NotFoundException();
        }
        RestrictedUseIndicator restrictedUseIndicator = restrictedUseIndicatorService.findById(studentDataSharingOptionsDto.restrictedUseIndicatorId)
        Student updatedStudent = StudentDataSharingOptionsDto.updateStudentDataSharing(student, studentDataSharingOptionsDto, restrictedUseIndicator);
        Student savedStudent = studentService.save(updatedStudent);
        return new ResponseEntity<StudentDataSharingOptionsDto>(new StudentDataSharingOptionsDto(savedStudent), HttpStatus.OK);
    }
}
