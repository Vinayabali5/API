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

import uk.ac.reigate.domain.lookup.Level
import uk.ac.reigate.domain.lookup.PossibleGradeSet
import uk.ac.reigate.dto.LevelDto
import uk.ac.reigate.exceptions.InvalidDataException
import uk.ac.reigate.exceptions.NotFoundException
import uk.ac.reigate.model.SearchResult
import uk.ac.reigate.services.LevelService
import uk.ac.reigate.services.PossibleGradeSetService


@Controller
@RequestMapping(value = "/api/levels", produces = [ APPLICATION_JSON_VALUE ])
@Api(value = "/api/levels", description = "the levels API")
public class LevelsApi {
    
    private static final Logger LOGGER = Logger.getLogger(LevelsApi.class);
    
    @Autowired
    private final LevelService levelService;
    
    @Autowired
    private final PossibleGradeSetService possibleGradeSetService;
    
    /**
     * Default NoArgs constructor
     */
    LevelsApi() {}
    
    /**
     * Autowired constructor
     */
    LevelsApi(LevelService levelService) {
        this.levelService = levelService;
    }
    
    /**
     * The levelsGet method is used to retrieve a full list of all the LevelDto objects
     *
     * @return A ResponseEntity with the corresponding list of LevelDto objects
     * @throws NotFoundException if nothing is found then the the NotFoundException is thrown.
     */
    @ApiOperation(value = "Collection of Level entities", notes = "A GET request to the Levels endpoint returns an array of all the levels in the system.", response = LevelDto.class, responseContainer = "List")
    @ApiResponses(value = [
        @ApiResponse(code = 200, message = "An array of levels")
    ])
    @RequestMapping(value = "", produces = ["application/json"], method = RequestMethod.GET)
    public ResponseEntity<List<LevelDto>> levelsGet() throws NotFoundException {
        LOGGER.info("** LevelsApi - levelsGet");
        List<Level> levels = levelService.findAll();
        return new ResponseEntity<List<LevelDto>>(LevelDto.mapFromLevelsEntities(levels), HttpStatus.OK);
    }
    
    /**
     * The levelsPost method is used to create a new instance of a Level from the supplied LevelDto
     *
     * @param level the LevelDto to use to create the new Level object
     * @return A ResponseEntity with the newly created Level object
     * @throws InvalidDataException if there is an issue with the data provided in the RequestBody then an InvalidDataException is thrown
     */
    @ApiOperation(value = "Creates a new Level entity", notes = "A POST request to the Levels endpoint with a Level object in the request body will create a new Level entity in the database.", response = LevelDto.class)
    @ApiResponses(value = [
        @ApiResponse(code = 201, message = "Returns a copy of the created Level entity including the levelId that has just been created."),
        @ApiResponse(code = 400, message = "Returns an Error object stating the request body does not match the expected input.")
    ])
    @RequestMapping(value = "", produces = ["application/json"], method = RequestMethod.POST)
    public ResponseEntity<LevelDto> levelsPost(@ApiParam(value = "The Level object to be created, without the levelId fields", required = true) @RequestBody @Valid LevelDto level) throws NotFoundException, InvalidDataException {
        LOGGER.info("** LevelsApi - levelsPOST");
        if (level.id != null) {
            throw new InvalidDataException(400, "No ID field should be provided when creating")
        }
        PossibleGradeSet possibleGradeSet
        if(level.possibleGradeSetId != null){
            possibleGradeSet = possibleGradeSetService.findById(level.possibleGradeSetId)
        }
        Level levelToSave = LevelDto.mapToLevelEntity(level, possibleGradeSet)
        Level levelSaved = levelService.save(levelToSave)
        return new ResponseEntity<LevelDto>(LevelDto.mapFromLevelEntity(levelSaved), HttpStatus.CREATED);
    }
    
    /**
     * The levelsLevelIdGet method is used to retrieve an instance of a LevelDto object as identified by the levelId provided
     *
     * @param levelId the level ID for the Level object retrieve
     * @return A ResponseEntity with the corresponding LevelDto object
     * @throws NotFoundException if nothing is found then the the NotFoundException is thrown.
     */
    @ApiOperation(value = "Retrieves an indivdual instance of a Level identified by the levelId", notes = "A getGET request to the Level instance endpoint will retrieve an instance of a Level entity as identified by the levelId provided in the URI.", response = LevelDto.class)
    @ApiResponses(value = [
        @ApiResponse(code = 200, message = "Returns a copy of the Level as identified by the levelId"),
        @ApiResponse(code = 404, message = "Returns an Error object stating that the resource could not be found. This happens if the specified id cannot be found in the database.")
    ])
    @RequestMapping(value = "/{levelId}", produces = ["application/json"], method = RequestMethod.GET)
    public ResponseEntity<LevelDto> levelsLevelIdGet(@ApiParam(value = "The unique ID of the Level to retrieve", required = true) @PathVariable("levelId") Integer levelId) throws NotFoundException {
        LOGGER.info("** LevelsApi - levelsLevelIdGet");
        Level level = levelService.findById(levelId);
        if (level == null) {
            throw new NotFoundException();
        }
        return new ResponseEntity<LevelDto>(LevelDto.mapFromLevelEntity(level), HttpStatus.OK);
    }
    
    /**
     * The levelsLevelIdPut is used to update
     *
     * @param levelId the level ID for the Level object to update
     * @param level the new data for the Level object
     * @return the newly updated LevelDto object
     * @throws NotFoundException if nothing is found then the the NotFoundException is thrown.
     * @throws InvalidDataException if there is an issue with the data provided in the RequestBody then an InvalidDataException is thrown
     */
    @ApiOperation(value = "Used to update a Level entity", notes = "A PUT request to the Level instance endpoint with a Level object in the request body will update an existing Level entity in the database.", response = LevelDto.class)
    @ApiResponses(value = [
        @ApiResponse(code = 200, message = "Returns a copy of the newly updated Level object."),
        @ApiResponse(code = 400, message = "Returns an Error object stating the request body does not match the expected input."),
        @ApiResponse(code = 404, message = "Returns an Error object stating that the resource could not be found. This happens if the specified id cannot be found in the database.")
    ])
    @RequestMapping(value = "/{levelId}", produces = ["application/json"], method = RequestMethod.PUT)
    public ResponseEntity<LevelDto> levelsLevelIdPut(
            @ApiParam(value = "The unique ID of the Level to retrieve", required = true) @PathVariable("levelId") Integer levelId,
            @ApiParam(value = "The Level object to be created, without the levelId fields", required = true) @RequestBody LevelDto level) throws NotFoundException, InvalidDataException {
        LOGGER.info("** LevelsApi - levelPUT");
        if (levelId != level.id) {
            throw new InvalidDataException()
        }
        PossibleGradeSet possibleGradeSet
        if(level.possibleGradeSetId != null){
            possibleGradeSet = possibleGradeSetService.findById(level.possibleGradeSetId)
        }
        Level levelToSave = LevelDto.mapToLevelEntity(level, possibleGradeSet)
        Level levelSaved = levelService.save(levelToSave)
        return new ResponseEntity<LevelDto>(LevelDto.mapFromLevelEntity(levelSaved), HttpStatus.OK);
    }
}
