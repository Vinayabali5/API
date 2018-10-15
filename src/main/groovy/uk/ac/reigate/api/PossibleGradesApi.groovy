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
import uk.ac.reigate.domain.lookup.PossibleGrade
import uk.ac.reigate.domain.lookup.PossibleGradeSet
import uk.ac.reigate.dto.PossibleGradeDto
import uk.ac.reigate.exceptions.InvalidDataException
import uk.ac.reigate.exceptions.NotFoundException
import uk.ac.reigate.model.SearchResult
import uk.ac.reigate.services.LevelService
import uk.ac.reigate.services.PossibleGradeService
import uk.ac.reigate.services.PossibleGradeSetService


@Controller
@RequestMapping(value = "/api/possibleGrades", produces = [ APPLICATION_JSON_VALUE ])
@Api(value = "/api/possibleGrades", description = "the possibleGrades API")
public class PossibleGradesApi {
    
    private static final Logger LOGGER = Logger.getLogger(PossibleGradesApi.class);
    
    @Autowired
    private final PossibleGradeService possibleGradeService;
    
    @Autowired
    private final PossibleGradeSetService possibleGradeSetService;
    
    @Autowired
    private final LevelService levelService
    
    /**
     * Default NoArgs constructor
     */
    PossibleGradesApi() {}
    
    /**
     * Autowired constructor
     */
    PossibleGradesApi(PossibleGradeService possibleGradeService) {
        this.possibleGradeService = possibleGradeService;
    }
    
    /**
     * The possibleGradesGet method is used to retrieve a full list of all the PossibleGradeDto objects
     *
     * @return A ResponseEntity with the corresponding list of PossibleGradeDto objects
     * @throws NotFoundException if nothing is found then the the NotFoundException is thrown.
     */
    @ApiOperation(value = "Collection of PossibleGrade entities", notes = "A GET request to the PossibleGrades endpoint returns an array of all the possibleGrades in the system.", response = PossibleGradeDto.class, responseContainer = "List")
    @ApiResponses(value = [
        @ApiResponse(code = 200, message = "An array of possibleGrades")
    ])
    @RequestMapping(value = "", produces = ["application/json"], method = RequestMethod.GET)
    public ResponseEntity<List<PossibleGradeDto>> possibleGradesGet() throws NotFoundException {
        LOGGER.info("** PossibleGradesApi - possibleGradesGet");
        List<PossibleGrade> possibleGrades = possibleGradeService.findAll();
        return new ResponseEntity<List<PossibleGradeDto>>(PossibleGradeDto.mapFromPossibleGradesEntities(possibleGrades), HttpStatus.OK);
    }
    
    /**
     * The possibleGradesPost method is used to create a new instance of a PossibleGrade from the supplied PossibleGradeDto
     *
     * @param possibleGrade the PossibleGradeDto to use to create the new PossibleGrade object
     * @return A ResponseEntity with the newly created PossibleGrade object
     * @throws InvalidDataException if there is an issue with the data provided in the RequestBody then an InvalidDataException is thrown
     */
    @ApiOperation(value = "Creates a new PossibleGrade entity", notes = "A POST request to the PossibleGrades endpoint with a PossibleGrade object in the request body will create a new PossibleGrade entity in the database.", response = PossibleGradeDto.class)
    @ApiResponses(value = [
        @ApiResponse(code = 201, message = "Returns a copy of the created PossibleGrade entity including the possibleGradeId that has just been created."),
        @ApiResponse(code = 400, message = "Returns an Error object stating the request body does not match the expected input.")
    ])
    @RequestMapping(value = "", produces = ["application/json"], method = RequestMethod.POST)
    public ResponseEntity<PossibleGradeDto> possibleGradesPost(@ApiParam(value = "The PossibleGrade object to be created, without the possibleGradeId fields", required = true) @RequestBody @Valid PossibleGradeDto possibleGrade) throws NotFoundException, InvalidDataException {
        LOGGER.info("** PossibleGradesApi - possibleGradesPOST");
        if (possibleGrade.id != null) {
            throw new InvalidDataException(400, "No ID field should be provided when creating")
        }
        PossibleGradeSet gradeSet
        if(possibleGrade.gradeSetId != null){
            gradeSet = possibleGradeSetService.findById(possibleGrade.gradeSetId)
        }
        Level level
        if (possibleGrade.levelId != null) {
            level = levelService.findById(possibleGrade.levelId)
        }
        PossibleGrade possibleGradeToSave = PossibleGradeDto.mapToPossibleGradeEntity(possibleGrade, gradeSet, level)
        PossibleGrade possibleGradeSaved = possibleGradeService.save(possibleGradeToSave)
        return new ResponseEntity<PossibleGradeDto>(PossibleGradeDto.mapFromPossibleGradeEntity(possibleGradeSaved), HttpStatus.CREATED);
    }
    
    /**
     * The possibleGradesPossibleGradeIdGet method is used to retrieve an instance of a PossibleGradeDto object as identified by the possibleGradeId provided
     *
     * @param possibleGradeId the possibleGrade ID for the PossibleGrade object retrieve
     * @return A ResponseEntity with the corresponding PossibleGradeDto object
     * @throws NotFoundException if nothing is found then the the NotFoundException is thrown.
     */
    @ApiOperation(value = "Retrieves an indivdual instance of a PossibleGrade identified by the possibleGradeId", notes = "A getGET request to the PossibleGrade instance endpoint will retrieve an instance of a PossibleGrade entity as identified by the possibleGradeId provided in the URI.", response = PossibleGradeDto.class)
    @ApiResponses(value = [
        @ApiResponse(code = 200, message = "Returns a copy of the PossibleGrade as identified by the possibleGradeId"),
        @ApiResponse(code = 404, message = "Returns an Error object stating that the resource could not be found. This happens if the specified id cannot be found in the database.")
    ])
    @RequestMapping(value = "/{possibleGradeId}", produces = ["application/json"], method = RequestMethod.GET)
    public ResponseEntity<PossibleGradeDto> possibleGradesPossibleGradeIdGet(@ApiParam(value = "The unique ID of the PossibleGrade to retrieve", required = true) @PathVariable("possibleGradeId") Integer possibleGradeId) throws NotFoundException {
        LOGGER.info("** PossibleGradesApi - possibleGradesPossibleGradeIdGet");
        PossibleGrade possibleGrade = possibleGradeService.findById(possibleGradeId);
        if (possibleGrade == null) {
            throw new NotFoundException();
        }
        return new ResponseEntity<PossibleGradeDto>(PossibleGradeDto.mapFromPossibleGradeEntity(possibleGrade), HttpStatus.OK);
    }
    
    /**
     * The possibleGradesPossibleGradeIdPut is used to update
     *
     * @param possibleGradeId the possibleGrade ID for the PossibleGrade object to update
     * @param possibleGrade the new data for the PossibleGrade object
     * @return the newly updated PossibleGradeDto object
     * @throws NotFoundException if nothing is found then the the NotFoundException is thrown.
     * @throws InvalidDataException if there is an issue with the data provided in the RequestBody then an InvalidDataException is thrown
     */
    @ApiOperation(value = "Used to update a PossibleGrade entity", notes = "A PUT request to the PossibleGrade instance endpoint with a PossibleGrade object in the request body will update an existing PossibleGrade entity in the database.", response = PossibleGradeDto.class)
    @ApiResponses(value = [
        @ApiResponse(code = 200, message = "Returns a copy of the newly updated PossibleGrade object."),
        @ApiResponse(code = 400, message = "Returns an Error object stating the request body does not match the expected input."),
        @ApiResponse(code = 404, message = "Returns an Error object stating that the resource could not be found. This happens if the specified id cannot be found in the database.")
    ])
    @RequestMapping(value = "/{possibleGradeId}", produces = ["application/json"], method = RequestMethod.PUT)
    public ResponseEntity<PossibleGradeDto> possibleGradesPossibleGradeIdPut(
            @ApiParam(value = "The unique ID of the PossibleGrade to retrieve", required = true) @PathVariable("possibleGradeId") Integer possibleGradeId,
            @ApiParam(value = "The PossibleGrade object to be created, without the possibleGradeId fields", required = true) @RequestBody PossibleGradeDto possibleGrade) throws NotFoundException, InvalidDataException {
        LOGGER.info("** PossibleGradesApi - possibleGradesPUT");
        if (possibleGradeId != possibleGrade.id) {
            LOGGER.error("EE - ID in URI does not match ID in RequestBody. ")
            throw new InvalidDataException()
        }
        PossibleGradeSet gradeSet
        if(possibleGrade.gradeSetId != null){
            gradeSet = possibleGradeSetService.findById(possibleGrade.gradeSetId)
        }
        Level level
        if (possibleGrade.levelId != null) {
            level = levelService.findById(possibleGrade.levelId)
        }
        PossibleGrade possibleGradeToSave = PossibleGradeDto.mapToPossibleGradeEntity(possibleGrade, gradeSet, level)
        PossibleGrade possibleGradeSaved = possibleGradeService.save(possibleGradeToSave)
        return new ResponseEntity<PossibleGradeDto>(PossibleGradeDto.mapFromPossibleGradeEntity(possibleGradeSaved), HttpStatus.OK);
    }
}
