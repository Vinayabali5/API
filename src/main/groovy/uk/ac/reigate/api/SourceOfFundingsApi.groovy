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

import uk.ac.reigate.domain.ilr.SourceOfFunding
import uk.ac.reigate.dto.SourceOfFundingDto
import uk.ac.reigate.exceptions.InvalidDataException
import uk.ac.reigate.exceptions.NotFoundException
import uk.ac.reigate.model.SearchResult
import uk.ac.reigate.services.SourceOfFundingService


@Controller
@RequestMapping(value = "/api/sourceOfFundings", produces = [ APPLICATION_JSON_VALUE ])
@Api(value = "/api/sourceOfFundings", description = "the sourceOfFundings API")
public class SourceOfFundingsApi {
    
    private static final Logger LOGGER = Logger.getLogger(SourceOfFundingsApi.class);
    
    @Autowired
    private final SourceOfFundingService sourceOfFundingService;
    
    /**
     * Default NoArgs constructor
     */
    SourceOfFundingsApi() {}
    
    /**
     * Autowired constructor
     */
    SourceOfFundingsApi(SourceOfFundingService sourceOfFundingService) {
        this.sourceOfFundingService = sourceOfFundingService;
    }
    
    /**
     * The sourceOfFundingsGet method is used to retrieve a full list of all the SourceOfFundingDto objects
     *
     * @return A ResponseEntity with the corresponding list of SourceOfFundingDto objects
     * @throws NotFoundException if nothing is found then the the NotFoundException is thrown.
     */
    @ApiOperation(value = "Collection of SourceOfFunding entities", notes = "A GET request to the SourceOfFundings endpoint returns an array of all the sourceOfFundings in the system.", response = SourceOfFundingDto.class, responseContainer = "List")
    @ApiResponses(value = [
        @ApiResponse(code = 200, message = "An array of sourceOfFundings")
    ])
    @RequestMapping(value = "", produces = ["application/json"], method = RequestMethod.GET)
    public ResponseEntity<List<SourceOfFundingDto>> sourceOfFundingsGet() throws NotFoundException {
        LOGGER.info("** SourceOfFundingsApi - sourceOfFundingsGet");
        List<SourceOfFunding> sourceOfFundings = sourceOfFundingService.findAll();
        return new ResponseEntity<List<SourceOfFundingDto>>(SourceOfFundingDto.mapFromSourceOfFundingsEntities(sourceOfFundings), HttpStatus.OK);
    }
    
    /**
     * The sourceOfFundingsPost method is used to create a new instance of a SourceOfFunding from the supplied SourceOfFundingDto
     *
     * @param sourceOfFunding the SourceOfFundingDto to use to create the new SourceOfFunding object
     * @return A ResponseEntity with the newly created SourceOfFunding object
     * @throws InvalidDataException if there is an issue with the data provided in the RequestBody then an InvalidDataException is thrown
     */
    @ApiOperation(value = "Creates a new SourceOfFunding entity", notes = "A POST request to the SourceOfFundings endpoint with a SourceOfFunding object in the request body will create a new SourceOfFunding entity in the database.", response = SourceOfFundingDto.class)
    @ApiResponses(value = [
        @ApiResponse(code = 201, message = "Returns a copy of the created SourceOfFunding entity including the sourceOfFundingId that has just been created."),
        @ApiResponse(code = 400, message = "Returns an Error object stating the request body does not match the expected input.")
    ])
    @RequestMapping(value = "", produces = ["application/json"], method = RequestMethod.POST)
    public ResponseEntity<SourceOfFundingDto> sourceOfFundingsPost(@ApiParam(value = "The SourceOfFunding object to be created, without the sourceOfFundingId fields", required = true) @RequestBody @Valid SourceOfFundingDto sourceOfFunding) throws NotFoundException, InvalidDataException {
        LOGGER.info("** SourceOfFundingsApi - sourceOfFundingsPOST");
        if (sourceOfFunding.id != null) {
            throw new InvalidDataException(400, "No ID field should be provided when creating")
        }
        SourceOfFunding sourceOfFundingToSave = SourceOfFundingDto.mapToSourceOfFundingEntity(sourceOfFunding)
        SourceOfFunding sourceOfFundingSaved = sourceOfFundingService.save(sourceOfFundingToSave)
        return new ResponseEntity<SourceOfFundingDto>(SourceOfFundingDto.mapFromSourceOfFundingEntity(sourceOfFundingSaved), HttpStatus.CREATED);
    }
    
    /**
     * The sourceOfFundingsSourceOfFundingIdGet method is used to retrieve an instance of a SourceOfFundingDto object as identified by the sourceOfFundingId provided
     *
     * @param sourceOfFundingId the sourceOfFunding ID for the SourceOfFunding object retrieve
     * @return A ResponseEntity with the corresponding SourceOfFundingDto object
     * @throws NotFoundException if nothing is found then the the NotFoundException is thrown.
     */
    @ApiOperation(value = "Retrieves an indivdual instance of a SourceOfFunding identified by the sourceOfFundingId", notes = "A getGET request to the SourceOfFunding instance endpoint will retrieve an instance of a SourceOfFunding entity as identified by the sourceOfFundingId provided in the URI.", response = SourceOfFundingDto.class)
    @ApiResponses(value = [
        @ApiResponse(code = 200, message = "Returns a copy of the SourceOfFunding as identified by the sourceOfFundingId"),
        @ApiResponse(code = 404, message = "Returns an Error object stating that the resource could not be found. This happens if the specified id cannot be found in the database.")
    ])
    @RequestMapping(value = "/{sourceOfFundingId}", produces = ["application/json"], method = RequestMethod.GET)
    public ResponseEntity<SourceOfFundingDto> sourceOfFundingsSourceOfFundingIdGet(@ApiParam(value = "The unique ID of the SourceOfFunding to retrieve", required = true) @PathVariable("sourceOfFundingId") Integer sourceOfFundingId) throws NotFoundException {
        LOGGER.info("** SourceOfFundingsApi - sourceOfFundingsSourceOfFundingIdGet");
        SourceOfFunding sourceOfFunding = sourceOfFundingService.findById(sourceOfFundingId);
        if (sourceOfFunding == null) {
            throw new NotFoundException();
        }
        return new ResponseEntity<SourceOfFundingDto>(SourceOfFundingDto.mapFromSourceOfFundingEntity(sourceOfFunding), HttpStatus.OK);
    }
    
    /**
     * The sourceOfFundingsSourceOfFundingIdPut is used to update
     *
     * @param sourceOfFundingId the sourceOfFunding ID for the SourceOfFunding object to update
     * @param sourceOfFunding the new data for the SourceOfFunding object
     * @return the newly updated SourceOfFundingDto object
     * @throws NotFoundException if nothing is found then the the NotFoundException is thrown.
     * @throws InvalidDataException if there is an issue with the data provided in the RequestBody then an InvalidDataException is thrown
     */
    @ApiOperation(value = "Used to update a SourceOfFunding entity", notes = "A PUT request to the SourceOfFunding instance endpoint with a SourceOfFunding object in the request body will update an existing SourceOfFunding entity in the database.", response = SourceOfFundingDto.class)
    @ApiResponses(value = [
        @ApiResponse(code = 200, message = "Returns a copy of the newly updated SourceOfFunding object."),
        @ApiResponse(code = 400, message = "Returns an Error object stating the request body does not match the expected input."),
        @ApiResponse(code = 404, message = "Returns an Error object stating that the resource could not be found. This happens if the specified id cannot be found in the database.")
    ])
    @RequestMapping(value = "/{sourceOfFundingId}", produces = ["application/json"], method = RequestMethod.PUT)
    public ResponseEntity<SourceOfFundingDto> sourceOfFundingsSourceOfFundingIdPut(
            @ApiParam(value = "The unique ID of the SourceOfFunding to retrieve", required = true) @PathVariable("sourceOfFundingId") Integer sourceOfFundingId,
            @ApiParam(value = "The SourceOfFunding object to be created, without the sourceOfFundingId fields", required = true) @RequestBody SourceOfFundingDto sourceOfFunding) throws NotFoundException, InvalidDataException {
        LOGGER.info("** SourceOfFundingsApi - sourceOfFundingPUT");
        if (sourceOfFundingId != sourceOfFunding.id) {
            throw new InvalidDataException()
        }
        SourceOfFunding sourceOfFundingToSave = SourceOfFundingDto.mapToSourceOfFundingEntity(sourceOfFunding)
        SourceOfFunding sourceOfFundingSaved = sourceOfFundingService.updateSourceOfFunding(sourceOfFundingToSave)
        return new ResponseEntity<SourceOfFundingDto>(SourceOfFundingDto.mapFromSourceOfFundingEntity(sourceOfFundingSaved), HttpStatus.OK);
    }
}
