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

import uk.ac.reigate.domain.learning_support.InitialAssessmentLevel
import uk.ac.reigate.dto.InitialAssessmentLevelDto
import uk.ac.reigate.exceptions.InvalidDataException
import uk.ac.reigate.exceptions.NotFoundException
import uk.ac.reigate.model.SearchResult
import uk.ac.reigate.services.InitialAssessmentLevelService


@Controller
@RequestMapping(value = "/api/initialAssessmentLevels", produces = [ APPLICATION_JSON_VALUE ])
@Api(value = "/api/initialAssessmentLevels", description = "the initialAssessmentLevels API")
public class InitialAssessmentLevelsApi {
    
    private static final Logger LOGGER = Logger.getLogger(InitialAssessmentLevelsApi.class);
    
    @Autowired
    private final InitialAssessmentLevelService initialAssessmentLevelService;
    
    /**
     * Default NoArgs constructor
     */
    InitialAssessmentLevelsApi() {}
    
    /**
     * Autowired constructor
     */
    InitialAssessmentLevelsApi(InitialAssessmentLevelService initialAssessmentLevelService) {
        this.initialAssessmentLevelService = initialAssessmentLevelService;
    }
    
    /**
     * The initialAssessmentLevelsGet method is used to retrieve a full list of all the InitialAssessmentLevelDto objects
     *
     * @return A ResponseEntity with the corresponding list of InitialAssessmentLevelDto objects
     * @throws NotFoundException if nothing is found then the the NotFoundException is thrown.
     */
    @ApiOperation(value = "Collection of InitialAssessmentLevel entities", notes = "A GET request to the InitialAssessmentLevels endpoint returns an array of all the initialAssessmentLevels in the system.", response = InitialAssessmentLevelDto.class, responseContainer = "List")
    @ApiResponses(value = [
        @ApiResponse(code = 200, message = "An array of initialAssessmentLevels")
    ])
    @RequestMapping(value = "", produces = ["application/json"], method = RequestMethod.GET)
    public ResponseEntity<List<InitialAssessmentLevelDto>> initialAssessmentLevelsGet() throws NotFoundException {
        LOGGER.info("** InitialAssessmentLevelsApi - initialAssessmentLevelsGet");
        List<InitialAssessmentLevel> initialAssessmentLevels = initialAssessmentLevelService.findAll();
        return new ResponseEntity<List<InitialAssessmentLevelDto>>(InitialAssessmentLevelDto.mapFromInitialAssessmentLevelsEntities(initialAssessmentLevels), HttpStatus.OK);
    }
    
    /**
     * The initialAssessmentLevelsPost method is used to create a new instance of a InitialAssessmentLevel from the supplied InitialAssessmentLevelDto
     *
     * @param initialAssessmentLevel the InitialAssessmentLevelDto to use to create the new InitialAssessmentLevel object
     * @return A ResponseEntity with the newly created InitialAssessmentLevel object
     * @throws InvalidDataException if there is an issue with the data provided in the RequestBody then an InvalidDataException is thrown
     */
    @ApiOperation(value = "Creates a new InitialAssessmentLevel entity", notes = "A POST request to the InitialAssessmentLevels endpoint with a InitialAssessmentLevel object in the request body will create a new InitialAssessmentLevel entity in the database.", response = InitialAssessmentLevelDto.class)
    @ApiResponses(value = [
        @ApiResponse(code = 201, message = "Returns a copy of the created InitialAssessmentLevel entity including the initialAssessmentLevelId that has just been created."),
        @ApiResponse(code = 400, message = "Returns an Error object stating the request body does not match the expected input.")
    ])
    @RequestMapping(value = "", produces = ["application/json"], method = RequestMethod.POST)
    public ResponseEntity<InitialAssessmentLevelDto> initialAssessmentLevelsPost(@ApiParam(value = "The InitialAssessmentLevel object to be created, without the initialAssessmentLevelId fields", required = true) @RequestBody @Valid InitialAssessmentLevelDto initialAssessmentLevel) throws NotFoundException, InvalidDataException {
        LOGGER.info("** InitialAssessmentLevelsApi - initialAssessmentLevelsPOST");
        if (initialAssessmentLevel.id != null) {
            throw new InvalidDataException(400, "No ID field should be provided when creating")
        }
        InitialAssessmentLevel initialAssessmentLevelToSave = InitialAssessmentLevelDto.mapToInitialAssessmentLevelEntity(initialAssessmentLevel)
        InitialAssessmentLevel initialAssessmentLevelSaved = initialAssessmentLevelService.save(initialAssessmentLevelToSave)
        return new ResponseEntity<InitialAssessmentLevelDto>(InitialAssessmentLevelDto.mapFromInitialAssessmentLevelEntity(initialAssessmentLevelSaved), HttpStatus.CREATED);
    }
    
    /**
     * The initialAssessmentLevelsInitialAssessmentLevelIdGet method is used to retrieve an instance of a InitialAssessmentLevelDto object as identified by the initialAssessmentLevelId provided
     *
     * @param initialAssessmentLevelId the initialAssessmentLevel ID for the InitialAssessmentLevel object retrieve
     * @return A ResponseEntity with the corresponding InitialAssessmentLevelDto object
     * @throws NotFoundException if nothing is found then the the NotFoundException is thrown.
     */
    @ApiOperation(value = "Retrieves an indivdual instance of a InitialAssessmentLevel identified by the initialAssessmentLevelId", notes = "A getGET request to the InitialAssessmentLevel instance endpoint will retrieve an instance of a InitialAssessmentLevel entity as identified by the initialAssessmentLevelId provided in the URI.", response = InitialAssessmentLevelDto.class)
    @ApiResponses(value = [
        @ApiResponse(code = 200, message = "Returns a copy of the InitialAssessmentLevel as identified by the initialAssessmentLevelId"),
        @ApiResponse(code = 404, message = "Returns an Error object stating that the resource could not be found. This happens if the specified id cannot be found in the database.")
    ])
    @RequestMapping(value = "/{initialAssessmentLevelId}", produces = ["application/json"], method = RequestMethod.GET)
    public ResponseEntity<InitialAssessmentLevelDto> initialAssessmentLevelsInitialAssessmentLevelIdGet(@ApiParam(value = "The unique ID of the InitialAssessmentLevel to retrieve", required = true) @PathVariable("initialAssessmentLevelId") Integer initialAssessmentLevelId) throws NotFoundException {
        LOGGER.info("** InitialAssessmentLevelsApi - initialAssessmentLevelsInitialAssessmentLevelIdGet");
        InitialAssessmentLevel initialAssessmentLevel = initialAssessmentLevelService.findById(initialAssessmentLevelId);
        if (initialAssessmentLevel == null) {
            throw new NotFoundException();
        }
        return new ResponseEntity<InitialAssessmentLevelDto>(InitialAssessmentLevelDto.mapFromInitialAssessmentLevelEntity(initialAssessmentLevel), HttpStatus.OK);
    }
    
    /**
     * The initialAssessmentLevelsInitialAssessmentLevelIdPut is used to update
     *
     * @param initialAssessmentLevelId the initialAssessmentLevel ID for the InitialAssessmentLevel object to update
     * @param initialAssessmentLevel the new data for the InitialAssessmentLevel object
     * @return the newly updated InitialAssessmentLevelDto object
     * @throws NotFoundException if nothing is found then the the NotFoundException is thrown.
     * @throws InvalidDataException if there is an issue with the data provided in the RequestBody then an InvalidDataException is thrown
     */
    @ApiOperation(value = "Used to update a InitialAssessmentLevel entity", notes = "A PUT request to the InitialAssessmentLevel instance endpoint with a InitialAssessmentLevel object in the request body will update an existing InitialAssessmentLevel entity in the database.", response = InitialAssessmentLevelDto.class)
    @ApiResponses(value = [
        @ApiResponse(code = 200, message = "Returns a copy of the newly updated InitialAssessmentLevel object."),
        @ApiResponse(code = 400, message = "Returns an Error object stating the request body does not match the expected input."),
        @ApiResponse(code = 404, message = "Returns an Error object stating that the resource could not be found. This happens if the specified id cannot be found in the database.")
    ])
    @RequestMapping(value = "/{initialAssessmentLevelId}", produces = ["application/json"], method = RequestMethod.PUT)
    public ResponseEntity<InitialAssessmentLevelDto> initialAssessmentLevelsInitialAssessmentLevelIdPut(
            @ApiParam(value = "The unique ID of the InitialAssessmentLevel to retrieve", required = true) @PathVariable("initialAssessmentLevelId") Integer initialAssessmentLevelId,
            @ApiParam(value = "The InitialAssessmentLevel object to be created, without the initialAssessmentLevelId fields", required = true) @RequestBody InitialAssessmentLevelDto initialAssessmentLevel) throws NotFoundException, InvalidDataException {
        LOGGER.info("** InitialAssessmentLevelsApi - initialAssessmentLevelsPUT");
        if (initialAssessmentLevelId != initialAssessmentLevel.id) {
            throw new InvalidDataException()
        }
        InitialAssessmentLevel initialAssessmentLevelToSave = InitialAssessmentLevelDto.mapToInitialAssessmentLevelEntity(initialAssessmentLevel)
        InitialAssessmentLevel initialAssessmentLevelSaved = initialAssessmentLevelService.updateInitialAssessmentLevel(initialAssessmentLevelToSave)
        return new ResponseEntity<InitialAssessmentLevelDto>(InitialAssessmentLevelDto.mapFromInitialAssessmentLevelEntity(initialAssessmentLevelSaved), HttpStatus.OK);
    }
}
