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

import uk.ac.reigate.domain.ilr.EnglishConditionOfFunding
import uk.ac.reigate.dto.EnglishConditionOfFundingDto
import uk.ac.reigate.exceptions.InvalidDataException
import uk.ac.reigate.exceptions.NotFoundException
import uk.ac.reigate.model.SearchResult
import uk.ac.reigate.services.EnglishConditionOfFundingService


@Controller
@RequestMapping(value = "/api/englishConditionOfFundings", produces = [ APPLICATION_JSON_VALUE ])
@Api(value = "/api/englishConditionOfFundings", description = "the englishConditionOfFundings API")
public class EnglishConditionOfFundingsApi {
    
    private static final Logger LOGGER = Logger.getLogger(EnglishConditionOfFundingsApi.class);
    
    @Autowired
    private final EnglishConditionOfFundingService englishConditionOfFundingService;
    
    /**
     * Default NoArgs constructor
     */
    EnglishConditionOfFundingsApi() {}
    
    /**
     * Autowired constructor
     */
    EnglishConditionOfFundingsApi(EnglishConditionOfFundingService englishConditionOfFundingService) {
        this.englishConditionOfFundingService = englishConditionOfFundingService;
    }
    
    /**
     * The englishConditionOfFundingsGet method is used to retrieve a full list of all the EnglishConditionOfFundingDto objects
     *
     * @return A ResponseEntity with the corresponding list of EnglishConditionOfFundingDto objects
     * @throws NotFoundException if nothing is found then the the NotFoundException is thrown.
     */
    @ApiOperation(value = "Collection of EnglishConditionOfFunding entities", notes = "A GET request to the EnglishConditionOfFundings endpoint returns an array of all the englishConditionOfFundings in the system.", response = EnglishConditionOfFundingDto.class, responseContainer = "List")
    @ApiResponses(value = [
        @ApiResponse(code = 200, message = "An array of englishConditionOfFundings")
    ])
    @RequestMapping(value = "", produces = ["application/json"], method = RequestMethod.GET)
    public ResponseEntity<List<EnglishConditionOfFundingDto>> englishConditionOfFundingsGet() throws NotFoundException {
        LOGGER.info("** EnglishConditionOfFundingsApi - englishConditionOfFundingsGet");
        List<EnglishConditionOfFunding> englishConditionOfFundings = englishConditionOfFundingService.findAll();
        return new ResponseEntity<List<EnglishConditionOfFundingDto>>(EnglishConditionOfFundingDto.mapFromEnglishConditionOfFundingsEntities(englishConditionOfFundings), HttpStatus.OK);
    }
    
    /**
     * The englishConditionOfFundingsPost method is used to create a new instance of a EnglishConditionOfFunding from the supplied EnglishConditionOfFundingDto
     *
     * @param englishConditionOfFunding the EnglishConditionOfFundingDto to use to create the new EnglishConditionOfFunding object
     * @return A ResponseEntity with the newly created EnglishConditionOfFunding object
     * @throws InvalidDataException if there is an issue with the data provided in the RequestBody then an InvalidDataException is thrown
     */
    @ApiOperation(value = "Creates a new EnglishConditionOfFunding entity", notes = "A POST request to the EnglishConditionOfFundings endpoint with a EnglishConditionOfFunding object in the request body will create a new EnglishConditionOfFunding entity in the database.", response = EnglishConditionOfFundingDto.class)
    @ApiResponses(value = [
        @ApiResponse(code = 201, message = "Returns a copy of the created EnglishConditionOfFunding entity including the englishConditionOfFundingId that has just been created."),
        @ApiResponse(code = 400, message = "Returns an Error object stating the request body does not match the expected input.")
    ])
    @RequestMapping(value = "", produces = ["application/json"], method = RequestMethod.POST)
    public ResponseEntity<EnglishConditionOfFundingDto> englishConditionOfFundingsPost(@ApiParam(value = "The EnglishConditionOfFunding object to be created, without the englishConditionOfFundingId fields", required = true) @RequestBody @Valid EnglishConditionOfFundingDto englishConditionOfFunding) throws NotFoundException, InvalidDataException {
        LOGGER.info("** EnglishConditionOfFundingsApi - englishConditionOfFundingsPOST");
        if (englishConditionOfFunding.id != null) {
            throw new InvalidDataException(400, "No ID field should be provided when creating")
        }
        EnglishConditionOfFunding englishConditionOfFundingToSave = EnglishConditionOfFundingDto.mapToEnglishConditionOfFundingEntity(englishConditionOfFunding)
        EnglishConditionOfFunding englishConditionOfFundingSaved = englishConditionOfFundingService.save(englishConditionOfFundingToSave)
        return new ResponseEntity<EnglishConditionOfFundingDto>(EnglishConditionOfFundingDto.mapFromEnglishConditionOfFundingEntity(englishConditionOfFundingSaved), HttpStatus.CREATED);
    }
    
    /**
     * The englishConditionOfFundingsEnglishConditionOfFundingIdGet method is used to retrieve an instance of a EnglishConditionOfFundingDto object as identified by the englishConditionOfFundingId provided
     *
     * @param englishConditionOfFundingId the englishConditionOfFunding ID for the EnglishConditionOfFunding object retrieve
     * @return A ResponseEntity with the corresponding EnglishConditionOfFundingDto object
     * @throws NotFoundException if nothing is found then the the NotFoundException is thrown.
     */
    @ApiOperation(value = "Retrieves an indivdual instance of a EnglishConditionOfFunding identified by the englishConditionOfFundingId", notes = "A getGET request to the EnglishConditionOfFunding instance endpoint will retrieve an instance of a EnglishConditionOfFunding entity as identified by the englishConditionOfFundingId provided in the URI.", response = EnglishConditionOfFundingDto.class)
    @ApiResponses(value = [
        @ApiResponse(code = 200, message = "Returns a copy of the EnglishConditionOfFunding as identified by the englishConditionOfFundingId"),
        @ApiResponse(code = 404, message = "Returns an Error object stating that the resource could not be found. This happens if the specified id cannot be found in the database.")
    ])
    @RequestMapping(value = "/{englishConditionOfFundingId}", produces = ["application/json"], method = RequestMethod.GET)
    public ResponseEntity<EnglishConditionOfFundingDto> englishConditionOfFundingsEnglishConditionOfFundingIdGet(@ApiParam(value = "The unique ID of the EnglishConditionOfFunding to retrieve", required = true) @PathVariable("englishConditionOfFundingId") Integer englishConditionOfFundingId) throws NotFoundException {
        LOGGER.info("** EnglishConditionOfFundingsApi - englishConditionOfFundingsEnglishConditionOfFundingIdGet");
        EnglishConditionOfFunding englishConditionOfFunding = englishConditionOfFundingService.findById(englishConditionOfFundingId);
        if (englishConditionOfFunding == null) {
            throw new NotFoundException();
        }
        return new ResponseEntity<EnglishConditionOfFundingDto>(EnglishConditionOfFundingDto.mapFromEnglishConditionOfFundingEntity(englishConditionOfFunding), HttpStatus.OK);
    }
    
    /**
     * The englishConditionOfFundingsSubjectIdPut is used to update
     *
     * @param englishConditionOfFundingId the englishConditionOfFunding ID for the EnglishConditionOfFunding object to update
     * @param englishConditionOfFunding the new data for the EnglishConditionOfFunding object
     * @return the newly updated EnglishConditionOfFundingDto object
     * @throws NotFoundException if nothing is found then the the NotFoundException is thrown.
     * @throws InvalidDataException if there is an issue with the data provided in the RequestBody then an InvalidDataException is thrown
     */
    @ApiOperation(value = "Used to update a EnglishConditionOfFunding entity", notes = "A PUT request to the EnglishConditionOfFunding instance endpoint with a EnglishConditionOfFunding object in the request body will update an existing EnglishConditionOfFunding entity in the database.", response = EnglishConditionOfFundingDto.class)
    @ApiResponses(value = [
        @ApiResponse(code = 200, message = "Returns a copy of the newly updated EnglishConditionOfFunding object."),
        @ApiResponse(code = 400, message = "Returns an Error object stating the request body does not match the expected input."),
        @ApiResponse(code = 404, message = "Returns an Error object stating that the resource could not be found. This happens if the specified id cannot be found in the database.")
    ])
    @RequestMapping(value = "/{englishConditionOfFundingId}", produces = ["application/json"], method = RequestMethod.PUT)
    public ResponseEntity<EnglishConditionOfFundingDto> englishConditionOfFundingsEnglishConditionOfFundingIdPut(
            @ApiParam(value = "The unique ID of the EnglishConditionOfFunding to retrieve", required = true) @PathVariable("englishConditionOfFundingId") Integer englishConditionOfFundingId,
            @ApiParam(value = "The EnglishConditionOfFunding object to be created, without the englishConditionOfFundingId fields", required = true) @RequestBody EnglishConditionOfFundingDto englishConditionOfFunding) throws NotFoundException, InvalidDataException {
        LOGGER.info("** EnglishConditionOfFundingsApi - englishConditionOfFundingPUT");
        if (englishConditionOfFundingId != englishConditionOfFunding.id) {
            throw new InvalidDataException()
        }
        EnglishConditionOfFunding englishConditionOfFundingToSave = EnglishConditionOfFundingDto.mapToEnglishConditionOfFundingEntity(englishConditionOfFunding)
        EnglishConditionOfFunding englishConditionOfFundingSaved = englishConditionOfFundingService.save(englishConditionOfFundingToSave)
        return new ResponseEntity<EnglishConditionOfFundingDto>(EnglishConditionOfFundingDto.mapFromEnglishConditionOfFundingEntity(englishConditionOfFundingSaved), HttpStatus.OK);
    }
}
