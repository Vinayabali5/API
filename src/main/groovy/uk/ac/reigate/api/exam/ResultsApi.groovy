package uk.ac.reigate.api.exam
import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE

import java.awt.event.ItemEvent
import java.util.List
import javax.validation.Valid
import org.apache.log4j.Logger

import io.swagger.annotations.Api
import io.swagger.annotations.ApiOperation
import io.swagger.annotations.ApiParam
import io.swagger.annotations.ApiResponse
import io.swagger.annotations.ApiResponses

import org.springframework.http.HttpStatus
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.ResponseEntity
import org.springframework.stereotype.Controller
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestMethod
import uk.ac.reigate.domain.exam.Results
import uk.ac.reigate.dto.exam.ResultsDto
import uk.ac.reigate.dto.exam.StudentAlternativeUciDto
import uk.ac.reigate.exceptions.InvalidDataException
import uk.ac.reigate.exceptions.NotFoundException
import uk.ac.reigate.services.exam.ResultsService


@Controller
@RequestMapping(value = "/api/students/", produces = [ APPLICATION_JSON_VALUE ])
@Api(value = "/api/students/", description = "the Student Results API")
class ResultsApi {
    
    private static final Logger LOGGER = Logger.getLogger(ResultsApi.class);
    @Autowired
    private final ResultsService resultService;
    
    
    /**
     * The getResultsByStudentId method is used to retrieve a list of exam-results for a given student id
     *
     * @return A ResponseEntity with the corresponding list of Results objects
     * @throws NotFoundException if nothing is found then the the NotFoundException is thrown.
     */
    @ApiOperation(value = "Collection of Results entities", notes = "A GET request returns an array of all the results for the student.", response = ResultsDto.class, responseContainer = "List")
    @ApiResponses(value = [
        @ApiResponse(code = 200, message = "An array of Results")
    ])
    @RequestMapping(value="{studentId}/exam-results",produces = ["application/json"], method = RequestMethod.GET)
    public ResponseEntity<List<ResultsDto>> getResultsByStudentId(
            @ApiParam(value="studentId",required=true) @PathVariable("studentId")Integer studentId
    ) throws NotFoundException{
        List<Results> resultsList 	= resultService.getByStudentId(studentId)
        if(resultsList == null){
            throw new NotFoundException();
        }
        return new ResponseEntity<List<ResultsDto>>(ResultsDto.mapFromResultEntities(resultsList), HttpStatus.OK);
    }
    
    /** updateResultByPUT is to update the ResultDto entity with grade and score
     * @param resultId - resultId
     * @param resultDto 
     * @return
     */
    @ApiOperation(value = "Updates the Result entity with score and grade" , notes = "A POST request to update an existing Result" ,  response =  ResultsDto.class)
    @ApiResponses(value = [
        @ApiResponse(code = 200, message = "An array of Results")
    ])
    @RequestMapping(value="/exam-results/{resultsId}",produces = ["application/json"], method = RequestMethod.PUT)
    public ResponseEntity<ResultsDto> updateResultByPUT(
            @ApiParam(value = "The result Id to be updated" , required = true) @PathVariable ("resultsId") Integer resultId,
            @ApiParam(value = "The resultDto object that needs to be saved" , required = true) @RequestBody @Valid ResultsDto resultDto
    ){
        if(resultId!=resultDto.id){
            throw new InvalidDataException("Invalid Data");
        }
        Results resultToSave = resultService.findById(resultId)
        Results savedResults = resultService.saveResults(resultToSave, resultDto)
        return new ResponseEntity<ResultsDto>(new ResultsDto(savedResults), HttpStatus.OK)
    }
    
    
    
    /** getResultByIdGET method is to retrieve an instance of Results object based on the result_id
     * @return
     */
    @ApiOperation(value = "Get an instance of Result Entity based on  the resultId" , notes = " A GET request to get an instance of Result object", response = ResultsDto.class)
    @ApiResponses(value = [
        @ApiResponse(code = 200, message = "An array of Results")
    ])
    @RequestMapping(value = "/exam-results/{resultId}", produces = ["application/json"], method = RequestMethod.GET)
    public ResponseEntity<ResultsDto> getResultByIdGET(
            @ApiParam(value = "id of the Result object" , required = true ) @PathVariable (value = "resultId", required = true) Integer resultId
    ){
        Results result= resultService.findById(resultId)
        if (result == null){
            throw new NotFoundException("Result not found");
        }
        return new ResponseEntity<ResultsDto>(new ResultsDto(result), HttpStatus.OK)
    }
}






















