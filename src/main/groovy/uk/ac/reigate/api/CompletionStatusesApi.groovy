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

import uk.ac.reigate.domain.ilr.CompletionStatus
import uk.ac.reigate.dto.CompletionStatusDto
import uk.ac.reigate.exceptions.InvalidDataException
import uk.ac.reigate.exceptions.NotFoundException
import uk.ac.reigate.model.SearchResult
import uk.ac.reigate.services.CompletionStatusService


@Controller
@RequestMapping(value = "/api/completionStatuses", produces = [ APPLICATION_JSON_VALUE ])
@Api(value = "/api/completionStatuses", description = "the completionStatuses API")
public class CompletionStatusesApi {
    
    private static final Logger LOGGER = Logger.getLogger(CompletionStatusesApi.class);
    
    @Autowired
    private final CompletionStatusService completionStatusService;
    
    /**
     * Default NoArgs constructor
     */
    CompletionStatusesApi() {}
    
    /**
     * Autowired constructor
     */
    CompletionStatusesApi(CompletionStatusService completionStatusService) {
        this.completionStatusService = completionStatusService;
    }
    
    /**
     * The completionStatusesGet method is used to retrieve a full list of all the CompletionStatusDto objects
     *
     * @return A ResponseEntity with the corresponding list of CompletionStatusDto objects
     * @throws NotFoundException if nothing is found then the the NotFoundException is thrown.
     */
    @ApiOperation(value = "Collection of CompletionStatus entities", notes = "A GET request to the CompletionStatuses endpoint returns an array of all the completionStatuses in the system.", response = CompletionStatusDto.class, responseContainer = "List")
    @ApiResponses(value = [
        @ApiResponse(code = 200, message = "An array of completionStatuses")
    ])
    @RequestMapping(value = "", produces = ["application/json"], method = RequestMethod.GET)
    public ResponseEntity<List<CompletionStatusDto>> completionStatusesGet() throws NotFoundException {
        LOGGER.info("** CompletionStatusesApi - completionStatusesGet");
        List<CompletionStatus> completionStatuses = completionStatusService.findAll();
        return new ResponseEntity<List<CompletionStatusDto>>(CompletionStatusDto.mapFromCompletionStatusesEntities(completionStatuses), HttpStatus.OK);
    }
    
    /**
     * The completionStatusesPost method is used to create a new instance of a CompletionStatus from the supplied CompletionStatusDto
     *
     * @param completionStatus the CompletionStatusDto to use to create the new CompletionStatus object
     * @return A ResponseEntity with the newly created CompletionStatus object
     * @throws InvalidDataException if there is an issue with the data provided in the RequestBody then an InvalidDataException is thrown
     */
    @ApiOperation(value = "Creates a new CompletionStatus entity", notes = "A POST request to the CompletionStatuses endpoint with a CompletionStatus object in the request body will create a new CompletionStatus entity in the database.", response = CompletionStatusDto.class)
    @ApiResponses(value = [
        @ApiResponse(code = 201, message = "Returns a copy of the created CompletionStatus entity including the completionStatusId that has just been created."),
        @ApiResponse(code = 400, message = "Returns an Error object stating the request body does not match the expected input.")
    ])
    @RequestMapping(value = "", produces = ["application/json"], method = RequestMethod.POST)
    public ResponseEntity<CompletionStatusDto> completionStatussPost(@ApiParam(value = "The CompletionStatus object to be created, without the completionStatusId fields", required = true) @RequestBody @Valid CompletionStatusDto completionStatus) throws NotFoundException, InvalidDataException {
        LOGGER.info("** CompletionStatusesApi - completionStatusesPOST");
        CompletionStatus completionStatusToSave = completionStatus.toCompletionStatus() //CompletionStatusDto.mapToCompletionStatusEntity(completionStatus)
        CompletionStatus completionStatusSaved = completionStatusService.save(completionStatusToSave)
        return new ResponseEntity<CompletionStatusDto>(new CompletionStatusDto(completionStatusSaved), HttpStatus.CREATED);
    }
    
    /**
     * The completionStatusesCompletionStatusIdGet method is used to retrieve an instance of a CompletionStatusDto object as identified by the completionStatusId provided
     *
     * @param completionStatusId the completionStatus ID for the CompletionStatus object retrieve
     * @return A ResponseEntity with the corresponding CompletionStatusDto object
     * @throws NotFoundException if nothing is found then the the NotFoundException is thrown.
     */
    @ApiOperation(value = "Retrieves an indivdual instance of a CompletionStatus identified by the completionStatusId", notes = "A getGET request to the CompletionStatus instance endpoint will retrieve an instance of a CompletionStatus entity as identified by the completionStatusId provided in the URI.", response = CompletionStatusDto.class)
    @ApiResponses(value = [
        @ApiResponse(code = 200, message = "Returns a copy of the CompletionStatus as identified by the completionStatusId"),
        @ApiResponse(code = 404, message = "Returns an Error object stating that the resource could not be found. This happens if the specified id cannot be found in the database.")
    ])
    @RequestMapping(value = "/{completionStatusId}", produces = ["application/json"], method = RequestMethod.GET)
    public ResponseEntity<CompletionStatusDto> completionStatussCompletionStatusIdGet(@ApiParam(value = "The unique ID of the CompletionStatus to retrieve", required = true) @PathVariable("completionStatusId") Integer completionStatusId) throws NotFoundException {
        LOGGER.info("** CompletionStatusesApi - completionStatussCompletionStatusIdGet");
        CompletionStatus completionStatus = completionStatusService.findById(completionStatusId);
        if (completionStatus == null) {
            throw new NotFoundException();
        }
        return new ResponseEntity<CompletionStatusDto>(CompletionStatusDto.mapFromCompletionStatusEntity(completionStatus), HttpStatus.OK);
    }
    
    /**
     * The completionStatusesCompletionStatusIdPut is used to update
     *
     * @param completionStatusId the completionStatus ID for the CompletionStatus object to update
     * @param completionStatus the new data for the CompletionStatus object
     * @return the newly updated CompletionStatusDto object
     * @throws NotFoundException if nothing is found then the the NotFoundException is thrown.
     * @throws InvalidDataException if there is an issue with the data provided in the RequestBody then an InvalidDataException is thrown
     */
    @ApiOperation(value = "Used to update a CompletionStatus entity", notes = "A PUT request to the CompletionStatus instance endpoint with a CompletionStatus object in the request body will update an existing CompletionStatus entity in the database.", response = CompletionStatusDto.class)
    @ApiResponses(value = [
        @ApiResponse(code = 200, message = "Returns a copy of the newly updated CompletionStatus object."),
        @ApiResponse(code = 400, message = "Returns an Error object stating the request body does not match the expected input."),
        @ApiResponse(code = 404, message = "Returns an Error object stating that the resource could not be found. This happens if the specified id cannot be found in the database.")
    ])
    @RequestMapping(value = "/{completionStatusId}", produces = ["application/json"], method = RequestMethod.PUT)
    public ResponseEntity<CompletionStatusDto> completionStatusesCompletionStatusIdPut(
            @ApiParam(value = "The unique ID of the CompletionStatus to retrieve", required = true) @PathVariable("completionStatusId") Integer completionStatusId,
            @ApiParam(value = "The CompletionStatus object to be created, without the completionStatusId fields", required = true) @RequestBody CompletionStatusDto completionStatus) throws NotFoundException, InvalidDataException {
        LOGGER.info("** CompletionStatusesApi - completionStatusPUT");
        if (completionStatusId != completionStatus.id) {
            throw new InvalidDataException()
        }
        CompletionStatus completionStatusToSave = CompletionStatusDto.mapToCompletionStatusEntity(completionStatus)
        CompletionStatus completionStatusSaved = completionStatusService.updateCompletionStatus(completionStatusToSave)
        return new ResponseEntity<CompletionStatusDto>(CompletionStatusDto.mapFromCompletionStatusEntity(completionStatusSaved), HttpStatus.OK);
    }
}
