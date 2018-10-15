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

import uk.ac.reigate.domain.ilr.MathsConditionOfFunding
import uk.ac.reigate.dto.MathsConditionOfFundingDto
import uk.ac.reigate.exceptions.InvalidDataException
import uk.ac.reigate.exceptions.NotFoundException
import uk.ac.reigate.model.SearchResult
import uk.ac.reigate.services.MathsConditionOfFundingService


@Controller
@RequestMapping(value = "/api/mathsConditionOfFundings", produces = [ APPLICATION_JSON_VALUE ])
@Api(value = "/api/mathsConditionOfFundings", description = "the mathsConditionOfFundings API")
public class MathsConditionOfFundingsApi {
    
    private static final Logger LOGGER = Logger.getLogger(MathsConditionOfFundingsApi.class);
    
    @Autowired
    private final MathsConditionOfFundingService mathsConditionOfFundingService;
    
    /**
     * Default NoArgs constructor
     */
    MathsConditionOfFundingsApi() {}
    
    /**
     * Autowired constructor
     */
    MathsConditionOfFundingsApi(MathsConditionOfFundingService mathsConditionOfFundingService) {
        this.mathsConditionOfFundingService = mathsConditionOfFundingService;
    }
    
    /**
     * The mathsConditionOfFundingsGet method is used to retrieve a full list of all the MathsConditionOfFundingDto objects
     *
     * @return A ResponseEntity with the corresponding list of MathsConditionOfFundingDto objects
     * @throws NotFoundException if nothing is found then the the NotFoundException is thrown.
     */
    @ApiOperation(value = "Collection of MathsConditionOfFunding entities", notes = "A GET request to the MathsConditionOfFundings endpoint returns an array of all the mathsConditionOfFundings in the system.", response = MathsConditionOfFundingDto.class, responseContainer = "List")
    @ApiResponses(value = [
        @ApiResponse(code = 200, message = "An array of mathsConditionOfFundings")
    ])
    @RequestMapping(value = "", produces = ["application/json"], method = RequestMethod.GET)
    public ResponseEntity<List<MathsConditionOfFundingDto>> mathsConditionOfFundingsGet() throws NotFoundException {
        LOGGER.info("** MathsConditionOfFundingsApi - mathsConditionOfFundingsGet");
        List<MathsConditionOfFunding> mathsConditionOfFundings = mathsConditionOfFundingService.findAll();
        return new ResponseEntity<List<MathsConditionOfFundingDto>>(MathsConditionOfFundingDto.mapFromMathsConditionOfFundingsEntities(mathsConditionOfFundings), HttpStatus.OK);
    }
    
    /**
     * The mathsConditionOfFundingsPost method is used to create a new instance of a MathsConditionOfFunding from the supplied MathsConditionOfFundingDto
     *
     * @param mathsConditionOfFunding the MathsConditionOfFundingDto to use to create the new MathsConditionOfFunding object
     * @return A ResponseEntity with the newly created MathsConditionOfFunding object
     * @throws InvalidDataException if there is an issue with the data provided in the RequestBody then an InvalidDataException is thrown
     */
    @ApiOperation(value = "Creates a new MathsConditionOfFunding entity", notes = "A POST request to the MathsConditionOfFundings endpoint with a MathsConditionOfFunding object in the request body will create a new MathsConditionOfFunding entity in the database.", response = MathsConditionOfFundingDto.class)
    @ApiResponses(value = [
        @ApiResponse(code = 201, message = "Returns a copy of the created MathsConditionOfFunding entity including the mathsConditionOfFundingId that has just been created."),
        @ApiResponse(code = 400, message = "Returns an Error object stating the request body does not match the expected input.")
    ])
    @RequestMapping(value = "", produces = ["application/json"], method = RequestMethod.POST)
    public ResponseEntity<MathsConditionOfFundingDto> mathsConditionOfFundingsPost(@ApiParam(value = "The MathsConditionOfFunding object to be created, without the mathsConditionOfFundingId fields", required = true) @RequestBody @Valid MathsConditionOfFundingDto mathsConditionOfFunding) throws NotFoundException, InvalidDataException {
        LOGGER.info("** MathsConditionOfFundingsApi - mathsConditionOfFundingsPOST");
        if (mathsConditionOfFunding.id != null) {
            throw new InvalidDataException(400, "No ID field should be provided when creating")
        }
        MathsConditionOfFunding mathsConditionOfFundingToSave = MathsConditionOfFundingDto.mapToMathsConditionOfFundingEntity(mathsConditionOfFunding)
        MathsConditionOfFunding mathsConditionOfFundingSaved = mathsConditionOfFundingService.save(mathsConditionOfFundingToSave)
        return new ResponseEntity<MathsConditionOfFundingDto>(MathsConditionOfFundingDto.mapFromMathsConditionOfFundingEntity(mathsConditionOfFundingSaved), HttpStatus.CREATED);
    }
    
    /**
     * The mathsConditionOfFundingsMathsConditionOfFundingIdGet method is used to retrieve an instance of a MathsConditionOfFundingDto object as identified by the mathsConditionOfFundingId provided
     *
     * @param mathsConditionOfFundingId the mathsConditionOfFunding ID for the MathsConditionOfFunding object retrieve
     * @return A ResponseEntity with the corresponding MathsConditionOfFundingDto object
     * @throws NotFoundException if nothing is found then the the NotFoundException is thrown.
     */
    @ApiOperation(value = "Retrieves an indivdual instance of a MathsConditionOfFunding identified by the mathsConditionOfFundingId", notes = "A getGET request to the MathsConditionOfFunding instance endpoint will retrieve an instance of a MathsConditionOfFunding entity as identified by the mathsConditionOfFundingId provided in the URI.", response = MathsConditionOfFundingDto.class)
    @ApiResponses(value = [
        @ApiResponse(code = 200, message = "Returns a copy of the MathsConditionOfFunding as identified by the mathsConditionOfFundingId"),
        @ApiResponse(code = 404, message = "Returns an Error object stating that the resource could not be found. This happens if the specified id cannot be found in the database.")
    ])
    @RequestMapping(value = "/{mathsConditionOfFundingId}", produces = ["application/json"], method = RequestMethod.GET)
    public ResponseEntity<MathsConditionOfFundingDto> mathsConditionOfFundingsMathsConditionOfFundingIdGet(@ApiParam(value = "The unique ID of the MathsConditionOfFunding to retrieve", required = true) @PathVariable("mathsConditionOfFundingId") Integer mathsConditionOfFundingId) throws NotFoundException {
        LOGGER.info("** MathsConditionOfFundingsApi - mathsConditionOfFundingsMathsConditionOfFundingIdGet");
        MathsConditionOfFunding mathsConditionOfFunding = mathsConditionOfFundingService.findById(mathsConditionOfFundingId);
        if (mathsConditionOfFunding == null) {
            throw new NotFoundException();
        }
        return new ResponseEntity<MathsConditionOfFundingDto>(MathsConditionOfFundingDto.mapFromMathsConditionOfFundingEntity(mathsConditionOfFunding), HttpStatus.OK);
    }
    
    /**
     * The mathsConditionOfFundingsSubjectIdPut is used to update
     *
     * @param mathsConditionOfFundingId the mathsConditionOfFunding ID for the MathsConditionOfFunding object to update
     * @param mathsConditionOfFunding the new data for the MathsConditionOfFunding object
     * @return the newly updated MathsConditionOfFundingDto object
     * @throws NotFoundException if nothing is found then the the NotFoundException is thrown.
     * @throws InvalidDataException if there is an issue with the data provided in the RequestBody then an InvalidDataException is thrown
     */
    @ApiOperation(value = "Used to update a MathsConditionOfFunding entity", notes = "A PUT request to the MathsConditionOfFunding instance endpoint with a MathsConditionOfFunding object in the request body will update an existing MathsConditionOfFunding entity in the database.", response = MathsConditionOfFundingDto.class)
    @ApiResponses(value = [
        @ApiResponse(code = 200, message = "Returns a copy of the newly updated MathsConditionOfFunding object."),
        @ApiResponse(code = 400, message = "Returns an Error object stating the request body does not match the expected input."),
        @ApiResponse(code = 404, message = "Returns an Error object stating that the resource could not be found. This happens if the specified id cannot be found in the database.")
    ])
    @RequestMapping(value = "/{mathsConditionOfFundingId}", produces = ["application/json"], method = RequestMethod.PUT)
    public ResponseEntity<MathsConditionOfFundingDto> mathsConditionOfFundingsMathsConditionOfFundingIdPut(
            @ApiParam(value = "The unique ID of the MathsConditionOfFunding to retrieve", required = true) @PathVariable("mathsConditionOfFundingId") Integer mathsConditionOfFundingId,
            @ApiParam(value = "The MathsConditionOfFunding object to be created, without the mathsConditionOfFundingId fields", required = true) @RequestBody MathsConditionOfFundingDto mathsConditionOfFunding) throws NotFoundException, InvalidDataException {
        LOGGER.info("** MathsConditionOfFundingsApi - mathsConditionOfFundingPUT");
        if (mathsConditionOfFundingId != mathsConditionOfFunding.id) {
            throw new InvalidDataException()
        }
        MathsConditionOfFunding mathsConditionOfFundingToSave = MathsConditionOfFundingDto.mapToMathsConditionOfFundingEntity(mathsConditionOfFunding)
        MathsConditionOfFunding mathsConditionOfFundingSaved = mathsConditionOfFundingService.save(mathsConditionOfFundingToSave)
        return new ResponseEntity<MathsConditionOfFundingDto>(MathsConditionOfFundingDto.mapFromMathsConditionOfFundingEntity(mathsConditionOfFundingSaved), HttpStatus.OK);
    }
}
