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

import uk.ac.reigate.domain.lookup.PossibleGrade
import uk.ac.reigate.domain.lookup.PossibleGradeSet
import uk.ac.reigate.dto.PossibleGradeDto
import uk.ac.reigate.dto.PossibleGradeSetDto
import uk.ac.reigate.exceptions.InvalidDataException
import uk.ac.reigate.exceptions.NotFoundException
import uk.ac.reigate.model.SearchResult
import uk.ac.reigate.services.PossibleGradeService;
import uk.ac.reigate.services.PossibleGradeSetService


@Controller
@RequestMapping(value = "/api/possibleGradeSets", produces = [ APPLICATION_JSON_VALUE ])
@Api(value = "/api/possibleGradeSets", description = "the possibleGradeSets API")
public class PossibleGradeSetsApi {
    
    private static final Logger LOGGER = Logger.getLogger(PossibleGradeSetsApi.class);
    
    @Autowired
    private final PossibleGradeSetService possibleGradeSetService;
    
    @Autowired
    private final PossibleGradeService possibleGradeService;
    
    /**
     * Default NoArgs constructor
     */
    PossibleGradeSetsApi() {}
    
    /**
     * Autowired constructor
     */
    PossibleGradeSetsApi(PossibleGradeSetService possibleGradeSetService) {
        this.possibleGradeSetService = possibleGradeSetService;
    }
    
    /**
     * The possibleGradeSetsGet method is used to retrieve a full list of all the PossibleGradeSetDto objects
     *
     * @return A ResponseEntity with the corresponding list of PossibleGradeSetDto objects
     * @throws NotFoundException if nothing is found then the the NotFoundException is thrown.
     */
    @ApiOperation(value = "Collection of PossibleGradeSet entities", notes = "A GET request to the PossibleGradeSets endpoint returns an array of all the possibleGradeSets in the system.", response = PossibleGradeSetDto.class, responseContainer = "List")
    @ApiResponses(value = [
        @ApiResponse(code = 200, message = "An array of possibleGradeSets")
    ])
    @RequestMapping(value = "", produces = ["application/json"], method = RequestMethod.GET)
    public ResponseEntity<List<PossibleGradeSetDto>> possibleGradeSetsGet() throws NotFoundException {
        LOGGER.info("** PossibleGradeSetsApi - possibleGradeSetsGet");
        List<PossibleGradeSet> possibleGradeSets = possibleGradeSetService.findAll();
        return new ResponseEntity<List<PossibleGradeSetDto>>(PossibleGradeSetDto.mapFromPossibleGradeSetsEntities(possibleGradeSets), HttpStatus.OK);
    }
    
    /**
     * The possibleGradeSetsPost method is used to create a new instance of a PossibleGradeSet from the supplied PossibleGradeSetDto
     *
     * @param possibleGradeSet the PossibleGradeSetDto to use to create the new PossibleGradeSet object
     * @return A ResponseEntity with the newly created PossibleGradeSet object
     * @throws InvalidDataException if there is an issue with the data provided in the RequestBody then an InvalidDataException is thrown
     */
    @ApiOperation(value = "Creates a new PossibleGradeSet entity", notes = "A POST request to the PossibleGradeSets endpoint with a PossibleGradeSet object in the request body will create a new PossibleGradeSet entity in the database.", response = PossibleGradeSetDto.class)
    @ApiResponses(value = [
        @ApiResponse(code = 201, message = "Returns a copy of the created PossibleGradeSet entity including the possibleGradeSetId that has just been created."),
        @ApiResponse(code = 400, message = "Returns an Error object stating the request body does not match the expected input.")
    ])
    @RequestMapping(value = "", produces = ["application/json"], method = RequestMethod.POST)
    public ResponseEntity<PossibleGradeSetDto> possibleGradeSetsPost(@ApiParam(value = "The PossibleGradeSet object to be created, without the possibleGradeSetId fields", required = true) @RequestBody @Valid PossibleGradeSetDto possibleGradeSet) throws NotFoundException, InvalidDataException {
        LOGGER.info("** PossibleGradeSetsApi - possibleGradeSetsPOST");
        if (possibleGradeSet.id != null) {
            throw new InvalidDataException(400, "No ID field should be provided when creating")
        }
        PossibleGradeSet possibleGradeSetToSave = PossibleGradeSetDto.mapToPossibleGradeSetEntity(possibleGradeSet)
        PossibleGradeSet possibleGradeSetSaved = possibleGradeSetService.save(possibleGradeSetToSave)
        return new ResponseEntity<PossibleGradeSetDto>(PossibleGradeSetDto.mapFromPossibleGradeSetEntity(possibleGradeSetSaved), HttpStatus.CREATED);
    }
    
    /**
     * The possibleGradeSetsPossibleGradeSetIdGet method is used to retrieve an instance of a PossibleGradeSetDto object as identified by the possibleGradeSetId provided
     *
     * @param possibleGradeSetId the possibleGradeSet ID for the PossibleGradeSet object retrieve
     * @return A ResponseEntity with the corresponding PossibleGradeSetDto object
     * @throws NotFoundException if nothing is found then the the NotFoundException is thrown.
     */
    @ApiOperation(value = "Retrieves an indivdual instance of a PossibleGradeSet identified by the possibleGradeSetId", notes = "A getGET request to the PossibleGradeSet instance endpoint will retrieve an instance of a PossibleGradeSet entity as identified by the possibleGradeSetId provided in the URI.", response = PossibleGradeSetDto.class)
    @ApiResponses(value = [
        @ApiResponse(code = 200, message = "Returns a copy of the PossibleGradeSet as identified by the possibleGradeSetId"),
        @ApiResponse(code = 404, message = "Returns an Error object stating that the resource could not be found. This happens if the specified id cannot be found in the database.")
    ])
    @RequestMapping(value = "/{possibleGradeSetId}", produces = ["application/json"], method = RequestMethod.GET)
    public ResponseEntity<PossibleGradeSetDto> possibleGradeSetsPossibleGradeSetIdGet(@ApiParam(value = "The unique ID of the PossibleGradeSet to retrieve", required = true) @PathVariable("possibleGradeSetId") Integer possibleGradeSetId) throws NotFoundException {
        LOGGER.info("** PossibleGradeSetsApi - possibleGradeSetsPossibleGradeSetIdGet");
        PossibleGradeSet possibleGradeSet = possibleGradeSetService.findById(possibleGradeSetId);
        if (possibleGradeSet == null) {
            throw new NotFoundException();
        }
        return new ResponseEntity<PossibleGradeSetDto>(PossibleGradeSetDto.mapFromPossibleGradeSetEntity(possibleGradeSet), HttpStatus.OK);
    }
    
    /**
     * The possibleGradeSetsPossibleGradeSetIdPut is used to update
     *
     * @param possibleGradeSetId the possibleGradeSet ID for the PossibleGradeSet object to update
     * @param possibleGradeSet the new data for the PossibleGradeSet object
     * @return the newly updated PossibleGradeSetDto object
     * @throws NotFoundException if nothing is found then the the NotFoundException is thrown.
     * @throws InvalidDataException if there is an issue with the data provided in the RequestBody then an InvalidDataException is thrown
     */
    @ApiOperation(value = "Used to update a PossibleGradeSet entity", notes = "A PUT request to the PossibleGradeSet instance endpoint with a PossibleGradeSet object in the request body will update an existing PossibleGradeSet entity in the database.", response = PossibleGradeSetDto.class)
    @ApiResponses(value = [
        @ApiResponse(code = 200, message = "Returns a copy of the newly updated PossibleGradeSet object."),
        @ApiResponse(code = 400, message = "Returns an Error object stating the request body does not match the expected input."),
        @ApiResponse(code = 404, message = "Returns an Error object stating that the resource could not be found. This happens if the specified id cannot be found in the database.")
    ])
    @RequestMapping(value = "/{possibleGradeSetId}", produces = ["application/json"], method = RequestMethod.PUT)
    public ResponseEntity<PossibleGradeSetDto> possibleGradeSetsPossibleGradeSetIdPut(
            @ApiParam(value = "The unique ID of the PossibleGradeSet to retrieve", required = true) @PathVariable("possibleGradeSetId") Integer possibleGradeSetId,
            @ApiParam(value = "The PossibleGradeSet object to be created, without the possibleGradeSetId fields", required = true) @RequestBody PossibleGradeSetDto possibleGradeSet) throws NotFoundException, InvalidDataException {
        LOGGER.info("** PossibleGradeSetsApi - possibleGradeSetsPUT");
        if (possibleGradeSetId != possibleGradeSet.id) {
            throw new InvalidDataException()
        }
        PossibleGradeSet possibleGradeSetToSave = PossibleGradeSetDto.mapToPossibleGradeSetEntity(possibleGradeSet)
        PossibleGradeSet possibleGradeSetSaved = possibleGradeSetService.updatePossibleGradeSet(possibleGradeSetToSave)
        return new ResponseEntity<PossibleGradeSetDto>(PossibleGradeSetDto.mapFromPossibleGradeSetEntity(possibleGradeSetSaved), HttpStatus.OK);
    }
    
    /**
     * The possibleGradesPossibleGradeSetIdGet method is used to retrieve an instance of a PossibleGrades object as identified by the possibleGradeSetId provided
     *
     * @param possibleGradeSetId the possibleGradeSet ID for the PossibleGradeSet object retrieve
     * @return A ResponseEntity with the corresponding PossibleGradeSetDto object
     * @throws NotFoundException if nothing is found then the the NotFoundException is thrown.
     */
    @ApiOperation(value = "Retrieves a collection of PossibleGrades identified by the possibleGradeSetId", notes = "A getGET request to the PossibleGradeSet instance endpoint will retrieve an instance of a PossibleGradeSet entity as identified by the possibleGradeSetId provided in the URI.", response = PossibleGradeSetDto.class)
    @ApiResponses(value = [
        @ApiResponse(code = 200, message = "Returns a copy of the PossibleGradeSet as identified by the possibleGradeSetId"),
        @ApiResponse(code = 404, message = "Returns an Error object stating that the resource could not be found. This happens if the specified id cannot be found in the database.")
    ])
    @RequestMapping(value = "/{possibleGradeSetId}/possibleGrades", produces = ["application/json"], method = RequestMethod.GET)
    public ResponseEntity<PossibleGradeSetDto> possibleGradeSetsPossibleGradesId(
            @ApiParam(value = "The unique ID of the PossibleGradeSet to retrieve collection of PossibleGrades", required = true)
            @PathVariable("possibleGradeSetId") Integer possibleGradeSetId) throws NotFoundException {
        LOGGER.info("** PossibleGradeSetsApi - possibleGradeSetsPossibleGradesId");
        List<PossibleGrade> possibleGradesList = possibleGradeService.findByPossibleGradeSet(possibleGradeSetId)
        return new ResponseEntity<List<PossibleGradeDto>>(PossibleGradeDto.mapFromPossibleGradesEntities(possibleGradesList), HttpStatus.OK);
    }
}
