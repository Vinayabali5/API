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

import uk.ac.reigate.domain.ilr.LLDDHealthProblem
import uk.ac.reigate.dto.LLDDHealthProblemDto
import uk.ac.reigate.exceptions.InvalidDataException
import uk.ac.reigate.exceptions.NotFoundException
import uk.ac.reigate.model.SearchResult
import uk.ac.reigate.services.LLDDHealthProblemService


@Controller
@RequestMapping(value = "/api/lLDDHealthProblems", produces = [ APPLICATION_JSON_VALUE ])
@Api(value = "/api/lLDDHealthProblems", description = "the lLDDHealthProblems API")
public class LLDDHealthProblemsApi {
    
    private static final Logger LOGGER = Logger.getLogger(LLDDHealthProblemsApi.class);
    
    @Autowired
    private final LLDDHealthProblemService lLDDHealthProblemService;
    
    /**
     * Default NoArgs constructor
     */
    LLDDHealthProblemsApi() {}
    
    /**
     * Autowired constructor
     */
    LLDDHealthProblemsApi(LLDDHealthProblemService lLDDHealthProblemService) {
        this.lLDDHealthProblemService = lLDDHealthProblemService;
    }
    
    /**
     * The lLDDHealthProblemsGet method is used to retrieve a full list of all the LLDDHealthProblemDto objects
     *
     * @return A ResponseEntity with the corresponding list of LLDDHealthProblemDto objects
     * @throws NotFoundException if nothing is found then the the NotFoundException is thrown.
     */
    @ApiOperation(value = "Collection of LLDDHealthProblem entities", notes = "A GET request to the LLDDHealthProblems endpoint returns an array of all the lLDDHealthProblems in the system.", response = LLDDHealthProblemDto.class, responseContainer = "List")
    @ApiResponses(value = [
        @ApiResponse(code = 200, message = "An array of lLDDHealthProblems")
    ])
    @RequestMapping(value = "", produces = ["application/json"], method = RequestMethod.GET)
    public ResponseEntity<List<LLDDHealthProblemDto>> lLDDHealthProblemsGet() throws NotFoundException {
        LOGGER.info("** LLDDHealthProblemsApi - lLDDHealthProblemsGet");
        List<LLDDHealthProblem> lLDDHealthProblems = lLDDHealthProblemService.findAll();
        return new ResponseEntity<List<LLDDHealthProblemDto>>(LLDDHealthProblemDto.mapFromLLDDHealthProblemsEntities(lLDDHealthProblems), HttpStatus.OK);
    }
    
    /**
     * The lLDDHealthProblemsPost method is used to create a new instance of a LLDDHealthProblem from the supplied LLDDHealthProblemDto
     *
     * @param lLDDHealthProblem the LLDDHealthProblemDto to use to create the new LLDDHealthProblem object
     * @return A ResponseEntity with the newly created LLDDHealthProblem object
     * @throws InvalidDataException if there is an issue with the data provided in the RequestBody then an InvalidDataException is thrown
     */
    @ApiOperation(value = "Creates a new LLDDHealthProblem entity", notes = "A POST request to the LLDDHealthProblems endpoint with a LLDDHealthProblem object in the request body will create a new LLDDHealthProblem entity in the database.", response = LLDDHealthProblemDto.class)
    @ApiResponses(value = [
        @ApiResponse(code = 201, message = "Returns a copy of the created LLDDHealthProblem entity including the lLDDHealthProblemId that has just been created."),
        @ApiResponse(code = 400, message = "Returns an Error object stating the request body does not match the expected input.")
    ])
    @RequestMapping(value = "", produces = ["application/json"], method = RequestMethod.POST)
    public ResponseEntity<LLDDHealthProblemDto> lLDDHealthProblemsPost(@ApiParam(value = "The LLDDHealthProblem object to be created, without the lLDDHealthProblemId fields", required = true) @RequestBody @Valid LLDDHealthProblemDto lLDDHealthProblem) throws NotFoundException, InvalidDataException {
        LOGGER.info("** LLDDHealthProblemsApi - lLDDHealthProblemsPOST");
        if (lLDDHealthProblem.id != null) {
            throw new InvalidDataException(400, "No ID field should be provided when creating")
        }
        LLDDHealthProblem lLDDHealthProblemToSave = LLDDHealthProblemDto.mapToLLDDHealthProblemEntity(lLDDHealthProblem)
        LLDDHealthProblem lLDDHealthProblemSaved = lLDDHealthProblemService.save(lLDDHealthProblemToSave)
        return new ResponseEntity<LLDDHealthProblemDto>(LLDDHealthProblemDto.mapFromLLDDHealthProblemEntity(lLDDHealthProblemSaved), HttpStatus.CREATED);
    }
    
    /**
     * The lLDDHealthProblemsLLDDHealthProblemIdGet method is used to retrieve an instance of a LLDDHealthProblemDto object as identified by the lLDDHealthProblemId provided
     *
     * @param lLDDHealthProblemId the lLDDHealthProblem ID for the LLDDHealthProblem object retrieve
     * @return A ResponseEntity with the corresponding LLDDHealthProblemDto object
     * @throws NotFoundException if nothing is found then the the NotFoundException is thrown.
     */
    @ApiOperation(value = "Retrieves an indivdual instance of a LLDDHealthProblem identified by the lLDDHealthProblemId", notes = "A getGET request to the LLDDHealthProblem instance endpoint will retrieve an instance of a LLDDHealthProblem entity as identified by the lLDDHealthProblemId provided in the URI.", response = LLDDHealthProblemDto.class)
    @ApiResponses(value = [
        @ApiResponse(code = 200, message = "Returns a copy of the LLDDHealthProblem as identified by the lLDDHealthProblemId"),
        @ApiResponse(code = 404, message = "Returns an Error object stating that the resource could not be found. This happens if the specified id cannot be found in the database.")
    ])
    @RequestMapping(value = "/{lLDDHealthProblemId}", produces = ["application/json"], method = RequestMethod.GET)
    public ResponseEntity<LLDDHealthProblemDto> lLDDHealthProblemsLLDDHealthProblemIdGet(@ApiParam(value = "The unique ID of the LLDDHealthProblem to retrieve", required = true) @PathVariable("lLDDHealthProblemId") Integer lLDDHealthProblemId) throws NotFoundException {
        LOGGER.info("** LLDDHealthProblemsApi - lLDDHealthProblemsLLDDHealthProblemIdGet");
        LLDDHealthProblem lLDDHealthProblem = lLDDHealthProblemService.findById(lLDDHealthProblemId);
        if (lLDDHealthProblem == null) {
            throw new NotFoundException();
        }
        return new ResponseEntity<LLDDHealthProblemDto>(LLDDHealthProblemDto.mapFromLLDDHealthProblemEntity(lLDDHealthProblem), HttpStatus.OK);
    }
    
    /**
     * The lLDDHealthProblemsLLDDHealthProblemIdPut is used to update
     *
     * @param lLDDHealthProblemId the lLDDHealthProblem ID for the LLDDHealthProblem object to update
     * @param lLDDHealthProblem the new data for the LLDDHealthProblem object
     * @return the newly updated LLDDHealthProblemDto object
     * @throws NotFoundException if nothing is found then the the NotFoundException is thrown.
     * @throws InvalidDataException if there is an issue with the data provided in the RequestBody then an InvalidDataException is thrown
     */
    @ApiOperation(value = "Used to update a LLDDHealthProblem entity", notes = "A PUT request to the LLDDHealthProblem instance endpoint with a LLDDHealthProblem object in the request body will update an existing LLDDHealthProblem entity in the database.", response = LLDDHealthProblemDto.class)
    @ApiResponses(value = [
        @ApiResponse(code = 200, message = "Returns a copy of the newly updated LLDDHealthProblem object."),
        @ApiResponse(code = 400, message = "Returns an Error object stating the request body does not match the expected input."),
        @ApiResponse(code = 404, message = "Returns an Error object stating that the resource could not be found. This happens if the specified id cannot be found in the database.")
    ])
    @RequestMapping(value = "/{lLDDHealthProblemId}", produces = ["application/json"], method = RequestMethod.PUT)
    public ResponseEntity<LLDDHealthProblemDto> lLDDHealthProblemsLLDDHealthProblemIdPut(
            @ApiParam(value = "The unique ID of the LLDDHealthProblem to retrieve", required = true) @PathVariable("lLDDHealthProblemId") Integer lLDDHealthProblemId,
            @ApiParam(value = "The LLDDHealthProblem object to be created, without the lLDDHealthProblemId fields", required = true) @RequestBody LLDDHealthProblemDto lLDDHealthProblem) throws NotFoundException, InvalidDataException {
        LOGGER.info("** LLDDHealthProblemsApi - lLDDHealthProblemsPUT");
        if (lLDDHealthProblemId != lLDDHealthProblem.id) {
            throw new InvalidDataException()
        }
        LLDDHealthProblem lLDDHealthProblemToSave = LLDDHealthProblemDto.mapToLLDDHealthProblemEntity(lLDDHealthProblem)
        LLDDHealthProblem lLDDHealthProblemSaved = lLDDHealthProblemService.updateLLDDHealthProblem(lLDDHealthProblemToSave)
        return new ResponseEntity<LLDDHealthProblemDto>(LLDDHealthProblemDto.mapFromLLDDHealthProblemEntity(lLDDHealthProblemSaved), HttpStatus.OK);
    }
}
