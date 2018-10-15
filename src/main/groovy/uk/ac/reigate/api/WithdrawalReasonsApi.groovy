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

import uk.ac.reigate.domain.ilr.WithdrawalReason
import uk.ac.reigate.dto.WithdrawalReasonDto
import uk.ac.reigate.exceptions.InvalidDataException
import uk.ac.reigate.exceptions.NotFoundException
import uk.ac.reigate.model.SearchResult
import uk.ac.reigate.services.WithdrawalReasonService


@Controller
@RequestMapping(value = "/api/withdrawalReasons", produces = [ APPLICATION_JSON_VALUE ])
@Api(value = "/api/withdrawalReasons", description = "the withdrawalReasons API")
public class WithdrawalReasonsApi {
    
    private static final Logger LOGGER = Logger.getLogger(WithdrawalReasonsApi.class);
    
    @Autowired
    private final WithdrawalReasonService withdrawalReasonService;
    
    /**
     * Default NoArgs constructor
     */
    WithdrawalReasonsApi() {}
    
    /**
     * Autowired constructor
     */
    WithdrawalReasonsApi(WithdrawalReasonService withdrawalReasonService) {
        this.withdrawalReasonService = withdrawalReasonService;
    }
    
    /**
     * The withdrawalReasonsGet method is used to retrieve a full list of all the WithdrawalReasonDto objects
     *
     * @return A ResponseEntity with the corresponding list of WithdrawalReasonDto objects
     * @throws NotFoundException if nothing is found then the the NotFoundException is thrown.
     */
    @ApiOperation(value = "Collection of WithdrawalReason entities", notes = "A GET request to the WithdrawalReasons endpoint returns an array of all the withdrawalReasons in the system.", response = WithdrawalReasonDto.class, responseContainer = "List")
    @ApiResponses(value = [
        @ApiResponse(code = 200, message = "An array of withdrawalReasons")
    ])
    @RequestMapping(value = "", produces = ["application/json"], method = RequestMethod.GET)
    public ResponseEntity<List<WithdrawalReasonDto>> withdrawalReasonsGet() throws NotFoundException {
        LOGGER.info("** WithdrawalReasonsApi - withdrawalReasonsGet");
        List<WithdrawalReason> withdrawalReasons = withdrawalReasonService.findAll();
        return new ResponseEntity<List<WithdrawalReasonDto>>(WithdrawalReasonDto.mapFromWithdrawalReasonsEntities(withdrawalReasons), HttpStatus.OK);
    }
    
    /**
     * The withdrawalReasonsPost method is used to create a new instance of a WithdrawalReason from the supplied WithdrawalReasonDto
     *
     * @param withdrawalReason the WithdrawalReasonDto to use to create the new WithdrawalReason object
     * @return A ResponseEntity with the newly created WithdrawalReason object
     * @throws InvalidDataException if there is an issue with the data provided in the RequestBody then an InvalidDataException is thrown
     */
    @ApiOperation(value = "Creates a new WithdrawalReason entity", notes = "A POST request to the WithdrawalReasons endpoint with a WithdrawalReason object in the request body will create a new WithdrawalReason entity in the database.", response = WithdrawalReasonDto.class)
    @ApiResponses(value = [
        @ApiResponse(code = 201, message = "Returns a copy of the created WithdrawalReason entity including the withdrawalReasonId that has just been created."),
        @ApiResponse(code = 400, message = "Returns an Error object stating the request body does not match the expected input.")
    ])
    @RequestMapping(value = "", produces = ["application/json"], method = RequestMethod.POST)
    public ResponseEntity<WithdrawalReasonDto> withdrawalReasonsPost(@ApiParam(value = "The WithdrawalReason object to be created, without the withdrawalReasonId fields", required = true) @RequestBody @Valid WithdrawalReasonDto withdrawalReason) throws NotFoundException, InvalidDataException {
        LOGGER.info("** WithdrawalReasonsApi - withdrawalReasonsPOST");
        if (withdrawalReason.id != null) {
            throw new InvalidDataException(400, "No ID field should be provided when creating")
        }
        WithdrawalReason withdrawalReasonToSave = WithdrawalReasonDto.mapToWithdrawalReasonEntity(withdrawalReason)
        WithdrawalReason withdrawalReasonSaved = withdrawalReasonService.save(withdrawalReasonToSave)
        return new ResponseEntity<WithdrawalReasonDto>(WithdrawalReasonDto.mapFromWithdrawalReasonEntity(withdrawalReasonSaved), HttpStatus.CREATED);
    }
    
    /**
     * The withdrawalReasonsWithdrawalReasonIdGet method is used to retrieve an instance of a WithdrawalReasonDto object as identified by the withdrawalReasonId provided
     *
     * @param withdrawalReasonId the withdrawalReason ID for the WithdrawalReason object retrieve
     * @return A ResponseEntity with the corresponding WithdrawalReasonDto object
     * @throws NotFoundException if nothing is found then the the NotFoundException is thrown.
     */
    @ApiOperation(value = "Retrieves an indivdual instance of a WithdrawalReason identified by the withdrawalReasonId", notes = "A getGET request to the WithdrawalReason instance endpoint will retrieve an instance of a WithdrawalReason entity as identified by the withdrawalReasonId provided in the URI.", response = WithdrawalReasonDto.class)
    @ApiResponses(value = [
        @ApiResponse(code = 200, message = "Returns a copy of the WithdrawalReason as identified by the withdrawalReasonId"),
        @ApiResponse(code = 404, message = "Returns an Error object stating that the resource could not be found. This happens if the specified id cannot be found in the database.")
    ])
    @RequestMapping(value = "/{withdrawalReasonId}", produces = ["application/json"], method = RequestMethod.GET)
    public ResponseEntity<WithdrawalReasonDto> withdrawalReasonsWithdrawalReasonIdGet(@ApiParam(value = "The unique ID of the WithdrawalReason to retrieve", required = true) @PathVariable("withdrawalReasonId") Integer withdrawalReasonId) throws NotFoundException {
        LOGGER.info("** WithdrawalReasonsApi - withdrawalReasonsWithdrawalReasonIdGet");
        WithdrawalReason withdrawalReason = withdrawalReasonService.findById(withdrawalReasonId);
        if (withdrawalReason == null) {
            throw new NotFoundException();
        }
        return new ResponseEntity<WithdrawalReasonDto>(WithdrawalReasonDto.mapFromWithdrawalReasonEntity(withdrawalReason), HttpStatus.OK);
    }
    
    /**
     * The withdrawalReasonsWithdrawalReasonIdPut is used to update
     *
     * @param withdrawalReasonId the withdrawalReason ID for the WithdrawalReason object to update
     * @param withdrawalReason the new data for the WithdrawalReason object
     * @return the newly updated WithdrawalReasonDto object
     * @throws NotFoundException if nothing is found then the the NotFoundException is thrown.
     * @throws InvalidDataException if there is an issue with the data provided in the RequestBody then an InvalidDataException is thrown
     */
    @ApiOperation(value = "Used to update a WithdrawalReason entity", notes = "A PUT request to the WithdrawalReason instance endpoint with a WithdrawalReason object in the request body will update an existing WithdrawalReason entity in the database.", response = WithdrawalReasonDto.class)
    @ApiResponses(value = [
        @ApiResponse(code = 200, message = "Returns a copy of the newly updated WithdrawalReason object."),
        @ApiResponse(code = 400, message = "Returns an Error object stating the request body does not match the expected input."),
        @ApiResponse(code = 404, message = "Returns an Error object stating that the resource could not be found. This happens if the specified id cannot be found in the database.")
    ])
    @RequestMapping(value = "/{withdrawalReasonId}", produces = ["application/json"], method = RequestMethod.PUT)
    public ResponseEntity<WithdrawalReasonDto> withdrawalReasonsWithdrawalReasonIdPut(
            @ApiParam(value = "The unique ID of the WithdrawalReason to retrieve", required = true) @PathVariable("withdrawalReasonId") Integer withdrawalReasonId,
            @ApiParam(value = "The WithdrawalReason object to be created, without the withdrawalReasonId fields", required = true) @RequestBody WithdrawalReasonDto withdrawalReason) throws NotFoundException, InvalidDataException {
        LOGGER.info("** WithdrawalReasonsApi - withdrawalReasonsPUT");
        if (withdrawalReasonId != withdrawalReason.id) {
            throw new InvalidDataException()
        }
        WithdrawalReason withdrawalReasonToSave = WithdrawalReasonDto.mapToWithdrawalReasonEntity(withdrawalReason)
        WithdrawalReason withdrawalReasonSaved = withdrawalReasonService.updateWithdrawalReason(withdrawalReasonToSave)
        return new ResponseEntity<WithdrawalReasonDto>(WithdrawalReasonDto.mapFromWithdrawalReasonEntity(withdrawalReasonSaved), HttpStatus.OK);
    }
}
