package uk.ac.reigate.api;

import javax.validation.Valid

import io.swagger.annotations.Api
import io.swagger.annotations.ApiOperation
import io.swagger.annotations.ApiParam
import io.swagger.annotations.ApiResponse
import io.swagger.annotations.ApiResponses

import org.apache.log4j.Logger

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.stereotype.Controller
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestMethod

import uk.ac.reigate.domain.ilr.Destination
import uk.ac.reigate.dto.DestinationDto
import uk.ac.reigate.exceptions.NotFoundException
import uk.ac.reigate.services.DestinationService

import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE


@Controller
@RequestMapping(value = "/api/destinations", produces = [ APPLICATION_JSON_VALUE ])
@Api(value = "/api/destinations", description = "the destination API")
public class DestinationApi {
    
    private static final Logger LOGGER = Logger.getLogger(DestinationApi.class);
    
    @Autowired
    private final DestinationService destinationService;
    
    /**
     * Default NoArgs constructor
     */
    DestinationApi() {}
    
    /**
     * Autowired constructor
     */
    DestinationApi(DestinationService destinationService) {
        this.destinationService = destinationService;
    }
    
    /**
     * The destinationGet method is used to retrieve a full list of all the DestinationDto objects
     *
     * @return A ResponseEntity with the corresponding list of DestinationDto objects
     * @throws NotFoundException if nothing is found then the the NotFoundException is thrown.
     */
    @ApiOperation(value = "Retrieves a collection of Destination entities", notes = "A GET request to the Destinations endpoint returns an array of all the destination in the system.", response = DestinationDto.class, responseContainer = "List")
    @ApiResponses(value = [
        @ApiResponse(code = 200, message = "An array of destination")
    ])
    @RequestMapping(value = "", produces = ["application/json"], method = RequestMethod.GET)
    public ResponseEntity<List<DestinationDto>> getDestinations() throws NotFoundException {
        LOGGER.info("** DestinationsApi - destinationGet");
        List<Destination> destinations = destinationService.findAll();
        return new ResponseEntity<List<DestinationDto>>(DestinationDto.mapFromList(destinations), HttpStatus.OK);
    }
    
    /**
     * The destinationDestinationIdGet method is used to retrieve an instance of a DestinationDto object as identified by the destinationId provided
     *
     * @param destinationId the destination ID for the Destination object retrieve
     * @return A ResponseEntity with the corresponding DestinationDto object
     * @throws NotFoundException if nothing is found then the the NotFoundException is thrown.
     */
    @ApiOperation(value = "Retrieves an indivdual instance of a Destination identified by the destinationId", notes = "A getGET request to the Destination instance endpoint will retrieve an instance of a Destination entity as identified by the destinationId provided in the URI.", response = DestinationDto.class)
    @ApiResponses(value = [
        @ApiResponse(code = 200, message = "Returns a copy of the Destination as identified by the destinationId"),
        @ApiResponse(code = 404, message = "Returns an Error object stating that the resource could not be found. This happens if the specified id cannot be found in the database.")
    ])
    @RequestMapping(value = "/{destinationId}", produces = ["application/json"], method = RequestMethod.GET)
    public ResponseEntity<DestinationDto> getDestinationById(
            @ApiParam(value = "The unique ID of the Destination to retrieve", required = true)
            @PathVariable("destinationId") Integer destinationId
    ) throws NotFoundException {
        LOGGER.info("** DestinationsApi - destinationDestinationIdGet");
        Destination destination = destinationService.findById(destinationId);
        if (destination == null) {
            throw new NotFoundException();
        }
        return new ResponseEntity<DestinationDto>(new DestinationDto(destination), HttpStatus.OK);
    }
    
    
    /**
     * @param destinationDto - Requestbody of the Object dto to be created
     * @return created Destination object
     */
    @ApiOperation(value = "Creates a new Destination entity", notes = "A POST request to the Destination endpoint with a Destination object in the request body will create a new Destination entity in the database.", response = DestinationDto.class)
    @ApiResponses(value = [
        @ApiResponse(code = 201, message = "Returns a copy of the created Department entity including the destinationId that has just been created."),
        @ApiResponse(code = 400, message = "Returns an Error object stating the request body does not match the expected input.")
    ])
    @RequestMapping(value = "", produces = ["application/json"], method = RequestMethod.POST)
    public ResponseEntity<DestinationDto> destinationPost(
            @ApiParam(value= "", required = true) @Valid @RequestBody DestinationDto destination
    ) {
        Destination destinationToSave = destination.toDestination() //DestinationDto.mapToDestination(destinationDto)
        Destination destinationSaved = destinationService.save(destinationToSave)
        return new ResponseEntity<DestinationDto>(new DestinationDto(destinationSaved), HttpStatus.CREATED)
    }
}
