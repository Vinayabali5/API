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

import uk.ac.reigate.domain.lookup.CentralMonitoring
import uk.ac.reigate.dto.CentralMonitoringDto
import uk.ac.reigate.exceptions.DataAlreadyExistsException
import uk.ac.reigate.exceptions.InvalidDataException
import uk.ac.reigate.exceptions.NotFoundException
import uk.ac.reigate.model.SearchResult
import uk.ac.reigate.services.CentralMonitoringService


@Controller
@RequestMapping(value = "/api/centralMonitorings", produces = [ APPLICATION_JSON_VALUE ])
@Api(value = "/api/centralMonitorings", description = "the centralMonitorings API")
public class CentralMonitoringsApi {
    
    private static final Logger LOGGER = Logger.getLogger(CentralMonitoringsApi.class);
    
    @Autowired
    private final CentralMonitoringService centralMonitoringService;
    
    /**
     * Default NoArgs constructor
     */
    CentralMonitoringsApi() {}
    
    /**
     * Autowired constructor
     */
    CentralMonitoringsApi(CentralMonitoringService centralMonitoringService) {
        this.centralMonitoringService = centralMonitoringService;
    }
    
    /**
     * The centralMonitoringsGet method is used to retrieve a full list of all the CentralMonitoringDto objects
     *
     * @return A ResponseEntity with the corresponding list of CentralMonitoringDto objects
     * @throws NotFoundException if nothing is found then the the NotFoundException is thrown.
     */
    @ApiOperation(value = "Collection of CentralMonitoring entities", notes = "A GET request to the CentralMonitorings endpoint returns an array of all the centralMonitorings in the system.", response = CentralMonitoringDto.class, responseContainer = "List")
    @ApiResponses(value = [
        @ApiResponse(code = 200, message = "An array of centralMonitorings")
    ])
    @RequestMapping(value = "", produces = ["application/json"], method = RequestMethod.GET)
    public ResponseEntity<List<CentralMonitoringDto>> centralMonitoringsGet() throws NotFoundException {
        LOGGER.info("** CentralMonitoringsApi - centralMonitoringsGet");
        List<CentralMonitoring> centralMonitorings = centralMonitoringService.findAll();
        return new ResponseEntity<List<CentralMonitoringDto>>(CentralMonitoringDto.mapFromCentralMonitoringsEntities(centralMonitorings), HttpStatus.OK);
    }
    
    /**
     * The centralMonitoringsPost method is used to create a new instance of a CentralMonitoring from the supplied CentralMonitoringDto
     *
     * @param centralMonitoring the CentralMonitoringDto to use to create the new CentralMonitoring object
     * @return A ResponseEntity with the newly created CentralMonitoring object
     * @throws InvalidDataException if there is an issue with the data provided in the RequestBody then an InvalidDataException is thrown
     */
    @ApiOperation(value = "Creates a new CentralMonitoring entity", notes = "A POST request to the CentralMonitorings endpoint with a CentralMonitoring object in the request body will create a new CentralMonitoring entity in the database.", response = CentralMonitoringDto.class)
    @ApiResponses(value = [
        @ApiResponse(code = 201, message = "Returns a copy of the created CentralMonitoring entity including the centralMonitoringId that has just been created."),
        @ApiResponse(code = 400, message = "Returns an Error object stating the request body does not match the expected input.")
    ])
    @RequestMapping(value = "", produces = ["application/json"], method = RequestMethod.POST)
    public ResponseEntity<CentralMonitoringDto> centralMonitoringsPost(
            @ApiParam(value = "The CentralMonitoring object to be created, without the centralMonitoringId fields", required = true)
            @RequestBody @Valid CentralMonitoringDto centralMonitoring) throws NotFoundException, InvalidDataException, DataAlreadyExistsException {
        LOGGER.info("** CentralMonitoringsApi - centralMonitoringsPOST");
        if (centralMonitoring.id == null) {
            throw new NotFoundException();
        }
        CentralMonitoring centralMonitoring1 = centralMonitoringService.findById(centralMonitoring.id);
        if (centralMonitoring1 != null) {
            throw new DataAlreadyExistsException("An Central Monitoring already exists with the supplied ID.");
        }
        CentralMonitoring centralMonitoringToSave = centralMonitoring.toCentralMonitoring() //CentralMonitoringDto.mapToCentralMonitoringEntity(centralMonitoring)
        CentralMonitoring centralMonitoringSaved = centralMonitoringService.save(centralMonitoringToSave)
        return new ResponseEntity<CentralMonitoringDto>(new CentralMonitoringDto(centralMonitoringSaved), HttpStatus.CREATED);
    }
    
    /**
     * The centralMonitoringsCentralMonitoringIdGet method is used to retrieve an instance of a CentralMonitoringDto object as identified by the centralMonitoringId provided
     *
     * @param centralMonitoringId the centralMonitoring ID for the CentralMonitoring object retrieve
     * @return A ResponseEntity with the corresponding CentralMonitoringDto object
     * @throws NotFoundException if nothing is found then the the NotFoundException is thrown.
     */
    @ApiOperation(value = "Retrieves an indivdual instance of a CentralMonitoring identified by the centralMonitoringId", notes = "A getGET request to the CentralMonitoring instance endpoint will retrieve an instance of a CentralMonitoring entity as identified by the centralMonitoringId provided in the URI.", response = CentralMonitoringDto.class)
    @ApiResponses(value = [
        @ApiResponse(code = 200, message = "Returns a copy of the CentralMonitoring as identified by the centralMonitoringId"),
        @ApiResponse(code = 404, message = "Returns an Error object stating that the resource could not be found. This happens if the specified id cannot be found in the database.")
    ])
    @RequestMapping(value = "/{centralMonitoringId}", produces = ["application/json"], method = RequestMethod.GET)
    public ResponseEntity<CentralMonitoringDto> centralMonitoringsCentralMonitoringIdGet(@ApiParam(value = "The unique ID of the CentralMonitoring to retrieve", required = true) @PathVariable("centralMonitoringId") Integer centralMonitoringId) throws NotFoundException {
        LOGGER.info("** CentralMonitoringsApi - centralMonitoringsCentralMonitoringIdGet");
        CentralMonitoring centralMonitoring = centralMonitoringService.findById(centralMonitoringId);
        if (centralMonitoring == null) {
            throw new NotFoundException();
        }
        return new ResponseEntity<CentralMonitoringDto>(CentralMonitoringDto.mapFromCentralMonitoringEntity(centralMonitoring), HttpStatus.OK);
    }
    
    /**
     * The centralMonitoringsCentralMonitoringIdPut is used to update
     *
     * @param centralMonitoringId the centralMonitoring ID for the CentralMonitoring object to update
     * @param centralMonitoring the new data for the CentralMonitoring object
     * @return the newly updated CentralMonitoringDto object
     * @throws NotFoundException if nothing is found then the the NotFoundException is thrown.
     * @throws InvalidDataException if there is an issue with the data provided in the RequestBody then an InvalidDataException is thrown
     */
    @ApiOperation(value = "Used to update a CentralMonitoring entity", notes = "A PUT request to the CentralMonitoring instance endpoint with a CentralMonitoring object in the request body will update an existing CentralMonitoring entity in the database.", response = CentralMonitoringDto.class)
    @ApiResponses(value = [
        @ApiResponse(code = 200, message = "Returns a copy of the newly updated CentralMonitoring object."),
        @ApiResponse(code = 400, message = "Returns an Error object stating the request body does not match the expected input."),
        @ApiResponse(code = 404, message = "Returns an Error object stating that the resource could not be found. This happens if the specified id cannot be found in the database.")
    ])
    @RequestMapping(value = "/{centralMonitoringId}", produces = ["application/json"], method = RequestMethod.PUT)
    public ResponseEntity<CentralMonitoringDto> centralMonitoringsCentralMonitoringIdPut(
            @ApiParam(value = "The unique ID of the CentralMonitoring to retrieve", required = true) @PathVariable("centralMonitoringId") Integer centralMonitoringId,
            @ApiParam(value = "The CentralMonitoring object to be created, without the centralMonitoringId fields", required = true) @RequestBody CentralMonitoringDto centralMonitoring) throws NotFoundException, InvalidDataException {
        LOGGER.info("** CentralMonitoringsApi - centralMonitoringsPUT");
        if (centralMonitoringId != centralMonitoring.id) {
            throw new InvalidDataException()
        }
        CentralMonitoring centralMonitoringToSave = CentralMonitoringDto.mapToCentralMonitoringEntity(centralMonitoring)
        CentralMonitoring centralMonitoringSaved = centralMonitoringService.updateCentralMonitoring(centralMonitoringToSave)
        return new ResponseEntity<CentralMonitoringDto>(CentralMonitoringDto.mapFromCentralMonitoringEntity(centralMonitoringSaved), HttpStatus.OK);
    }
}
