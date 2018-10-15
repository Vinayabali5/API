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

import uk.ac.reigate.domain.ilr.RestrictedUseIndicator
import uk.ac.reigate.dto.RestrictedUseIndicatorDto
import uk.ac.reigate.exceptions.InvalidDataException
import uk.ac.reigate.exceptions.NotFoundException
import uk.ac.reigate.model.SearchResult
import uk.ac.reigate.services.RestrictedUseIndicatorService


@Controller
@RequestMapping(value = "/api/restrictedUseIndicators", produces = [ APPLICATION_JSON_VALUE ])
@Api(value = "/api/restrictedUseIndicators", description = "the restrictedUseIndicators API")
public class RestrictedUseIndicatorsApi {
    
    private static final Logger LOGGER = Logger.getLogger(RestrictedUseIndicatorsApi.class);
    
    @Autowired
    private final RestrictedUseIndicatorService restrictedUseIndicatorService;
    
    /**
     * Default No Args constructor
     */
    RestrictedUseIndicatorsApi() {}
    
    /**
     * Autowired constructor
     */
    RestrictedUseIndicatorsApi(RestrictedUseIndicatorService restrictedUseIndicatorService) {
        this.restrictedUseIndicatorService = restrictedUseIndicatorService;
    }
    
    /**
     * The restrictedUseIndicatorsGet method is used to retrieve a full list of all the RestrictedUseIndiactorDto objects
     *
     * @return A ResponseEntity with the corresponding list of RestrictedUseIndiactorDto objects
     * @throws NotFoundException if nothing is found then the the NotFoundException is thrown.
     */
    @ApiOperation(value = "Collection of RestrictedUseIndicator entities", notes = "A GET request to the RestrictedUseIndicators endpoint returns an array of all the restrictedUseIndicators in the system.", response = RestrictedUseIndicatorDto.class, responseContainer = "List")
    @ApiResponses(value = [
        @ApiResponse(code = 200, message = "An array of restrictedUseIndicators")
    ])
    @RequestMapping(value = "", produces = ["application/json"], method = RequestMethod.GET)
    public ResponseEntity<List<RestrictedUseIndicatorDto>> restrictedUseIndicatorsGet() throws NotFoundException {
        LOGGER.info("** RestrictedUseIndicatorsApi - restrictedUseIndicatorsGet");
        List<RestrictedUseIndicator> restrictedUseIndicators = restrictedUseIndicatorService.findAll();
        return new ResponseEntity<List<RestrictedUseIndicatorDto>>(RestrictedUseIndicatorDto.mapFromRestrictedUseIndicatorsEntities(restrictedUseIndicators.sort { it.code }) , HttpStatus.OK);
    }
    
    /**
     * The restrictedUseIndicatorsPost method is used to create a new instance of a RestrictedUseIndiactor from the supplied RestrictedUseIndiactorDto
     *
     * @param restrictedUseIndicator the RestrictedUseIndiactorDto to use to create the new RestrictedUseIndiactor object
     * @return A ResponseEntity with the newly created RestrictedUseIndiactor object
     * @throws InvalidDataException if there is an issue with the data provided in the RequestBody then an InvalidDataException is thrown
     */
    @ApiOperation(value = "Creates a new RestrictedUseIndicator entity", notes = "A POST request to the RestrictedUseIndicators endpoint with a RestrictedUseIndicator object in the request body will create a new RestrictedUseIndicator entity in the database.", response = RestrictedUseIndicatorDto.class)
    @ApiResponses(value = [
        @ApiResponse(code = 201, message = "Returns a copy of the created RestrictedUseIndicator entity including the restrictedUseIndicatorId that has just been created."),
        @ApiResponse(code = 400, message = "Returns an Error object stating the request body does not match the expected input.")
    ])
    @RequestMapping(value = "", produces = ["application/json"], method = RequestMethod.POST)
    public ResponseEntity<RestrictedUseIndicatorDto> restrictedUseIndicatorsPost(@ApiParam(value = "The RestrictedUseIndicator object to be created, without the restrictedUseIndicatorId fields", required = true) @RequestBody @Valid RestrictedUseIndicatorDto restrictedUseIndicator) throws NotFoundException, InvalidDataException {
        LOGGER.info("** RestrictedUseIndicatorsApi - restrictedUseIndicatorsPOST");
        if (restrictedUseIndicator.id != null) {
            throw new InvalidDataException(400, "No ID field should be provided when creating")
        }
        RestrictedUseIndicator restrictedUseIndicatorToSave = RestrictedUseIndicatorDto.mapToRestrictedUseIndicatorEntity(restrictedUseIndicator)
        RestrictedUseIndicator restrictedUseIndicatorSaved = restrictedUseIndicatorService.save(restrictedUseIndicatorToSave)
        return new ResponseEntity<RestrictedUseIndicatorDto>(RestrictedUseIndicatorDto.mapFromRestrictedUseIndicatorEntity(restrictedUseIndicatorSaved), HttpStatus.CREATED);
    }
    
    /**
     * The restrictedUseIndicatorsRestrictedUseIndiactorIdGet method is used to retrieve an instance of a RestrictedUseIndiactorDto object as identified by the restrictedUseIndicatorId provided
     *
     * @param restrictedUseIndicatorId the restrictedUseIndicator ID for the RestrictedUseIndiactor object retrieve
     * @return A ResponseEntity with the corresponding RestrictedUseIndiactorDto object
     * @throws NotFoundException if nothing is found then the the NotFoundException is thrown.
     */
    @ApiOperation(value = "Retrieves an indivdual instance of a RestrictedUseIndicator identified by the restrictedUseIndicatorId", notes = "A getGET request to the RestrictedUseIndicator instance endpoint will retrieve an instance of a RestrictedUseIndicator entity as identified by the restrictedUseIndicatorId provided in the URI.", response = RestrictedUseIndicatorDto.class)
    @ApiResponses(value = [
        @ApiResponse(code = 200, message = "Returns a copy of the RestrictedUseIndicator as identified by the restrictedUseIndicatorId"),
        @ApiResponse(code = 404, message = "Returns an Error object stating that the resource could not be found. This happens if the specified id cannot be found in the database.")
    ])
    @RequestMapping(value = "/{restrictedUseIndicatorId}", produces = ["application/json"], method = RequestMethod.GET)
    public ResponseEntity<RestrictedUseIndicatorDto> restrictedUseIndicatorsRestrictedUseIndicatorIdGet(@ApiParam(value = "The unique ID of the RestrictedUseIndicator to retrieve", required = true) @PathVariable("restrictedUseIndicatorId") Integer restrictedUseIndicatorId) throws NotFoundException {
        LOGGER.info("** RestrictedUseIndicatorsApi - restrictedUseIndicatorsRestrictedUseIndicatorIdGet");
        RestrictedUseIndicator restrictedUseIndicator = restrictedUseIndicatorService.findById(restrictedUseIndicatorId);
        if (restrictedUseIndicator == null) {
            throw new NotFoundException();
        }
        return new ResponseEntity<RestrictedUseIndicatorDto>(RestrictedUseIndicatorDto.mapFromRestrictedUseIndicatorEntity(restrictedUseIndicator), HttpStatus.OK);
    }
    
    /**
     * The restrictedUseIndicatorsRestrictedUseIndiactorIdPut is used to update
     *
     * @param restrictedUseIndicatorId the restrictedUseIndicator ID for the RestrictedUseIndiactor object to update
     * @param restrictedUseIndicator the new data for the RestrictedUseIndiactor object
     * @return the newly updated RestrictedUseIndiactorDto object
     * @throws NotFoundException if nothing is found then the the NotFoundException is thrown.
     * @throws InvalidDataException if there is an issue with the data provided in the RequestBody then an InvalidDataException is thrown
     */
    @ApiOperation(value = "Used to update a RestrictedUseIndicator entity", notes = "A PUT request to the RestrictedUseIndicator instance endpoint with a RestrictedUseIndicator object in the request body will update an existing RestrictedUseIndicator entity in the database.", response = RestrictedUseIndicatorDto.class)
    @ApiResponses(value = [
        @ApiResponse(code = 200, message = "Returns a copy of the newly updated RestrictedUseIndicator object."),
        @ApiResponse(code = 400, message = "Returns an Error object stating the request body does not match the expected input."),
        @ApiResponse(code = 404, message = "Returns an Error object stating that the resource could not be found. This happens if the specified id cannot be found in the database.")
    ])
    @RequestMapping(value = "/{restrictedUseIndicatorId}", produces = ["application/json"], method = RequestMethod.PUT)
    public ResponseEntity<RestrictedUseIndicatorDto> restrictedUseIndicatorsRestrictedUseIndicatorIdPut(
            @ApiParam(value = "The unique ID of the RestrictedUseIndicator to retrieve", required = true) @PathVariable("restrictedUseIndicatorId") Integer restrictedUseIndicatorId,
            @ApiParam(value = "The RestrictedUseIndicator object to be created, without the restrictedUseIndicatorId fields", required = true) @RequestBody RestrictedUseIndicatorDto restrictedUseIndicator) throws NotFoundException, InvalidDataException {
        LOGGER.info("** RestrictedUseIndicatorsApi - restrictedUseIndicatorsPUT");
        if (restrictedUseIndicatorId != restrictedUseIndicator.id) {
            throw new InvalidDataException()
        }
        RestrictedUseIndicator restrictedUseIndicatorToSave = RestrictedUseIndicatorDto.mapToRestrictedUseIndicatorEntity(restrictedUseIndicator)
        RestrictedUseIndicator restrictedUseIndicatorSaved = restrictedUseIndicatorService.updateRestrictedUseIndicator(restrictedUseIndicatorToSave)
        return new ResponseEntity<RestrictedUseIndicatorDto>(RestrictedUseIndicatorDto.mapFromRestrictedUseIndicatorEntity(restrictedUseIndicatorSaved), HttpStatus.OK);
    }
}
