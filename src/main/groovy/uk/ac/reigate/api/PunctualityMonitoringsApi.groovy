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

import uk.ac.reigate.domain.lookup.PunctualityMonitoring
import uk.ac.reigate.dto.PunctualityMonitoringDto
import uk.ac.reigate.exceptions.DataAlreadyExistsException
import uk.ac.reigate.exceptions.InvalidDataException
import uk.ac.reigate.exceptions.NotFoundException
import uk.ac.reigate.model.SearchResult
import uk.ac.reigate.services.PunctualityMonitoringService


@Controller
@RequestMapping(value = "/api/punctualityMonitorings", produces = [ APPLICATION_JSON_VALUE ])
@Api(value = "/api/punctualityMonitorings", description = "the punctualityMonitorings API")
public class PunctualityMonitoringsApi {
    
    private static final Logger LOGGER = Logger.getLogger(PunctualityMonitoringsApi.class);
    
    @Autowired
    private final PunctualityMonitoringService punctualityMonitoringService;
    
    /**
     * Default NoArgs constructor
     */
    PunctualityMonitoringsApi() {}
    
    /**
     * Autowired constructor
     */
    PunctualityMonitoringsApi(PunctualityMonitoringService punctualityMonitoringService) {
        this.punctualityMonitoringService = punctualityMonitoringService;
    }
    
    /**
     * The punctualityMonitoringsGet method is used to retrieve a full list of all the PunctualityMonitoringDto objects
     *
     * @return A ResponseEntity with the corresponding list of PunctualityMonitoringDto objects
     * @throws NotFoundException if nothing is found then the the NotFoundException is thrown.
     */
    @ApiOperation(value = "Collection of PunctualityMonitoring entities", notes = "A GET request to the PunctualityMonitorings endpoint returns an array of all the punctualityMonitorings in the system.", response = PunctualityMonitoringDto.class, responseContainer = "List")
    @ApiResponses(value = [
        @ApiResponse(code = 200, message = "An array of punctualityMonitorings")
    ])
    @RequestMapping(value = "", produces = ["application/json"], method = RequestMethod.GET)
    public ResponseEntity<List<PunctualityMonitoringDto>> punctualityMonitoringsGet() throws NotFoundException {
        LOGGER.info("** PunctualityMonitoringsApi - punctualityMonitoringsGet");
        List<PunctualityMonitoring> punctualityMonitorings = punctualityMonitoringService.findAll();
        return new ResponseEntity<List<PunctualityMonitoringDto>>(PunctualityMonitoringDto.mapFromPunctualityMonitoringsEntities(punctualityMonitorings), HttpStatus.OK);
    }
    
    /**
     * The punctualityMonitoringsPost method is used to create a new instance of a PunctualityMonitoring from the supplied PunctualityMonitoringDto
     *
     * @param punctualityMonitoring the PunctualityMonitoringDto to use to create the new PunctualityMonitoring object
     * @return A ResponseEntity with the newly created PunctualityMonitoring object
     * @throws InvalidDataException if there is an issue with the data provided in the RequestBody then an InvalidDataException is thrown
     */
    @ApiOperation(value = "Creates a new PunctualityMonitoring entity", notes = "A POST request to the PunctualityMonitorings endpoint with a PunctualityMonitoring object in the request body will create a new PunctualityMonitoring entity in the database.", response = PunctualityMonitoringDto.class)
    @ApiResponses(value = [
        @ApiResponse(code = 201, message = "Returns a copy of the created PunctualityMonitoring entity including the punctualityMonitoringId that has just been created."),
        @ApiResponse(code = 400, message = "Returns an Error object stating the request body does not match the expected input.")
    ])
    @RequestMapping(value = "", produces = ["application/json"], method = RequestMethod.POST)
    public ResponseEntity<PunctualityMonitoringDto> punctualityMonitoringsPost(
            @ApiParam(value = "The PunctualityMonitoring object to be created, without the punctualityMonitoringId fields", required = true)
            @RequestBody @Valid PunctualityMonitoringDto punctualityMonitoring) throws NotFoundException, InvalidDataException, DataAlreadyExistsException {
        LOGGER.info("** PunctualityMonitoringsApi - punctualityMonitoringsPOST");
        if (punctualityMonitoring.id == null) {
            throw new NotFoundException();
        }
        PunctualityMonitoring punctualityMonitoring1 = punctualityMonitoringService.findById(punctualityMonitoring.id);
        if (punctualityMonitoring1 != null) {
            throw new DataAlreadyExistsException("An Punctuality Monitoring already exists with the supplied ID.");
        }
        PunctualityMonitoring punctualityMonitoringToSave = punctualityMonitoring.toPunctualityMonitoring()
        PunctualityMonitoring punctualityMonitoringSaved = punctualityMonitoringService.save(punctualityMonitoringToSave)
        return new ResponseEntity<PunctualityMonitoringDto>(new PunctualityMonitoringDto(punctualityMonitoringSaved), HttpStatus.CREATED);
    }
    
    /**
     * The punctualityMonitoringsPunctualityMonitoringIdGet method is used to retrieve an instance of a PunctualityMonitoringDto object as identified by the punctualityMonitoringId provided
     *
     * @param punctualityMonitoringId the punctualityMonitoring ID for the PunctualityMonitoring object retrieve
     * @return A ResponseEntity with the corresponding PunctualityMonitoringDto object
     * @throws NotFoundException if nothing is found then the the NotFoundException is thrown.
     */
    @ApiOperation(value = "Retrieves an indivdual instance of a PunctualityMonitoring identified by the punctualityMonitoringId", notes = "A getGET request to the PunctualityMonitoring instance endpoint will retrieve an instance of a PunctualityMonitoring entity as identified by the punctualityMonitoringId provided in the URI.", response = PunctualityMonitoringDto.class)
    @ApiResponses(value = [
        @ApiResponse(code = 200, message = "Returns a copy of the PunctualityMonitoring as identified by the punctualityMonitoringId"),
        @ApiResponse(code = 404, message = "Returns an Error object stating that the resource could not be found. This happens if the specified id cannot be found in the database.")
    ])
    @RequestMapping(value = "/{punctualityMonitoringId}", produces = ["application/json"], method = RequestMethod.GET)
    public ResponseEntity<PunctualityMonitoringDto> punctualityMonitoringsPunctualityMonitoringIdGet(@ApiParam(value = "The unique ID of the PunctualityMonitoring to retrieve", required = true) @PathVariable("punctualityMonitoringId") Integer punctualityMonitoringId) throws NotFoundException {
        LOGGER.info("** PunctualityMonitoringsApi - punctualityMonitoringsPunctualityMonitoringIdGet");
        PunctualityMonitoring punctualityMonitoring = punctualityMonitoringService.findById(punctualityMonitoringId);
        if (punctualityMonitoring == null) {
            throw new NotFoundException();
        }
        return new ResponseEntity<PunctualityMonitoringDto>(PunctualityMonitoringDto.mapFromPunctualityMonitoringEntity(punctualityMonitoring), HttpStatus.OK);
    }
    
    /**
     * The punctualityMonitoringsPunctualityMonitoringIdPut is used to update
     *
     * @param punctualityMonitoringId the punctualityMonitoring ID for the PunctualityMonitoring object to update
     * @param punctualityMonitoring the new data for the PunctualityMonitoring object
     * @return the newly updated PunctualityMonitoringDto object
     * @throws NotFoundException if nothing is found then the the NotFoundException is thrown.
     * @throws InvalidDataException if there is an issue with the data provided in the RequestBody then an InvalidDataException is thrown
     */
    @ApiOperation(value = "Used to update a PunctualityMonitoring entity", notes = "A PUT request to the PunctualityMonitoring instance endpoint with a PunctualityMonitoring object in the request body will update an existing PunctualityMonitoring entity in the database.", response = PunctualityMonitoringDto.class)
    @ApiResponses(value = [
        @ApiResponse(code = 200, message = "Returns a copy of the newly updated PunctualityMonitoring object."),
        @ApiResponse(code = 400, message = "Returns an Error object stating the request body does not match the expected input."),
        @ApiResponse(code = 404, message = "Returns an Error object stating that the resource could not be found. This happens if the specified id cannot be found in the database.")
    ])
    @RequestMapping(value = "/{punctualityMonitoringId}", produces = ["application/json"], method = RequestMethod.PUT)
    public ResponseEntity<PunctualityMonitoringDto> punctualityMonitoringsPunctualityMonitoringIdPut(
            @ApiParam(value = "The unique ID of the PunctualityMonitoring to retrieve", required = true) @PathVariable("punctualityMonitoringId") Integer punctualityMonitoringId,
            @ApiParam(value = "The PunctualityMonitoring object to be created, without the punctualityMonitoringId fields", required = true) @RequestBody PunctualityMonitoringDto punctualityMonitoring) throws NotFoundException, InvalidDataException {
        LOGGER.info("** PunctualityMonitoringsApi - punctualityMonitoringsPUT");
        if (punctualityMonitoringId != punctualityMonitoring.id) {
            throw new InvalidDataException()
        }
        PunctualityMonitoring punctualityMonitoringToSave = PunctualityMonitoringDto.mapToPunctualityMonitoringEntity(punctualityMonitoring)
        PunctualityMonitoring punctualityMonitoringSaved = punctualityMonitoringService.updatePunctualityMonitoring(punctualityMonitoringToSave)
        return new ResponseEntity<PunctualityMonitoringDto>(PunctualityMonitoringDto.mapFromPunctualityMonitoringEntity(punctualityMonitoringSaved), HttpStatus.OK);
    }
}
