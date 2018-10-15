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

import uk.ac.reigate.domain.ilr.PriorAttainment
import uk.ac.reigate.dto.PriorAttainmentDto
import uk.ac.reigate.exceptions.InvalidDataException
import uk.ac.reigate.exceptions.NotFoundException
import uk.ac.reigate.model.SearchResult
import uk.ac.reigate.services.PriorAttainmentService


@Controller
@RequestMapping(value = "/api/priorAttainments", produces = [ APPLICATION_JSON_VALUE ])
@Api(value = "/api/priorAttainments", description = "the priorAttainments API")
public class PriorAttainmentsApi {
    
    private static final Logger LOGGER = Logger.getLogger(PriorAttainmentsApi.class);
    
    @Autowired
    private final PriorAttainmentService priorAttainmentService;
    
    /**
     * Default NoArgs constructor
     */
    PriorAttainmentsApi() {}
    
    /**
     * Autowired constructor
     */
    PriorAttainmentsApi(PriorAttainmentService priorAttainmentService) {
        this.priorAttainmentService = priorAttainmentService;
    }
    
    /**
     * The priorAttainmentsGet method is used to retrieve a full list of all the PriorAttainmentDto objects
     *
     * @return A ResponseEntity with the corresponding list of PriorAttainmentDto objects
     * @throws NotFoundException if nothing is found then the the NotFoundException is thrown.
     */
    @ApiOperation(value = "Collection of PriorAttainment entities", notes = "A GET request to the PriorAttainments endpoint returns an array of all the priorAttainments in the system.", response = PriorAttainmentDto.class, responseContainer = "List")
    @ApiResponses(value = [
        @ApiResponse(code = 200, message = "An array of priorAttainments")
    ])
    @RequestMapping(value = "", produces = ["application/json"], method = RequestMethod.GET)
    public ResponseEntity<List<PriorAttainmentDto>> priorAttainmentsGet() throws NotFoundException {
        LOGGER.info("** PriorAttainmentsApi - priorAttainmentsGet");
        List<PriorAttainment> priorAttainments = priorAttainmentService.findAll();
        return new ResponseEntity<List<PriorAttainmentDto>>(PriorAttainmentDto.mapFromPriorAttainmentsEntities(priorAttainments), HttpStatus.OK);
    }
    
    /**
     * The priorAttainmentsPost method is used to create a new instance of a PriorAttainment from the supplied PriorAttainmentDto
     *
     * @param priorAttainment the PriorAttainmentDto to use to create the new PriorAttainment object
     * @return A ResponseEntity with the newly created PriorAttainment object
     * @throws InvalidDataException if there is an issue with the data provided in the RequestBody then an InvalidDataException is thrown
     */
    @ApiOperation(value = "Creates a new PriorAttainment entity", notes = "A POST request to the PriorAttainments endpoint with a PriorAttainment object in the request body will create a new PriorAttainment entity in the database.", response = PriorAttainmentDto.class)
    @ApiResponses(value = [
        @ApiResponse(code = 201, message = "Returns a copy of the created PriorAttainment entity including the priorAttainmentId that has just been created."),
        @ApiResponse(code = 400, message = "Returns an Error object stating the request body does not match the expected input.")
    ])
    @RequestMapping(value = "", produces = ["application/json"], method = RequestMethod.POST)
    public ResponseEntity<PriorAttainmentDto> priorAttainmentsPost(@ApiParam(value = "The PriorAttainment object to be created, without the priorAttainmentId fields", required = true) @RequestBody @Valid PriorAttainmentDto priorAttainment) throws NotFoundException, InvalidDataException {
        LOGGER.info("** PriorAttainmentsApi - priorAttainmentsPOST");
        if (priorAttainment.id != null) {
            throw new InvalidDataException(400, "No ID field should be provided when creating")
        }
        PriorAttainment priorAttainmentToSave = PriorAttainmentDto.mapToPriorAttainmentEntity(priorAttainment)
        PriorAttainment priorAttainmentSaved = priorAttainmentService.save(priorAttainmentToSave)
        return new ResponseEntity<PriorAttainmentDto>(PriorAttainmentDto.mapFromPriorAttainmentEntity(priorAttainmentSaved), HttpStatus.CREATED);
    }
    
    /**
     * The priorAttainmentsPriorAttainmentIdGet method is used to retrieve an instance of a PriorAttainmentDto object as identified by the priorAttainmentId provided
     *
     * @param priorAttainmentId the priorAttainment ID for the PriorAttainment object retrieve
     * @return A ResponseEntity with the corresponding PriorAttainmentDto object
     * @throws NotFoundException if nothing is found then the the NotFoundException is thrown.
     */
    @ApiOperation(value = "Retrieves an indivdual instance of a PriorAttainment identified by the priorAttainmentId", notes = "A getGET request to the PriorAttainment instance endpoint will retrieve an instance of a PriorAttainment entity as identified by the priorAttainmentId provided in the URI.", response = PriorAttainmentDto.class)
    @ApiResponses(value = [
        @ApiResponse(code = 200, message = "Returns a copy of the PriorAttainment as identified by the priorAttainmentId"),
        @ApiResponse(code = 404, message = "Returns an Error object stating that the resource could not be found. This happens if the specified id cannot be found in the database.")
    ])
    @RequestMapping(value = "/{priorAttainmentId}", produces = ["application/json"], method = RequestMethod.GET)
    public ResponseEntity<PriorAttainmentDto> priorAttainmentsPriorAttainmentIdGet(@ApiParam(value = "The unique ID of the PriorAttainment to retrieve", required = true) @PathVariable("priorAttainmentId") Integer priorAttainmentId) throws NotFoundException {
        LOGGER.info("** PriorAttainmentsApi - priorAttainmentsPriorAttainmentIdGet");
        PriorAttainment priorAttainment = priorAttainmentService.findById(priorAttainmentId);
        if (priorAttainment == null) {
            throw new NotFoundException();
        }
        return new ResponseEntity<PriorAttainmentDto>(PriorAttainmentDto.mapFromPriorAttainmentEntity(priorAttainment), HttpStatus.OK);
    }
    
    /**
     * The priorAttainmentsPriorAttainmentIdPut is used to update
     *
     * @param priorAttainmentId the priorAttainment ID for the PriorAttainment object to update
     * @param priorAttainment the new data for the PriorAttainment object
     * @return the newly updated PriorAttainmentDto object
     * @throws NotFoundException if nothing is found then the the NotFoundException is thrown.
     * @throws InvalidDataException if there is an issue with the data provided in the RequestBody then an InvalidDataException is thrown
     */
    @ApiOperation(value = "Used to update a PriorAttainment entity", notes = "A PUT request to the PriorAttainment instance endpoint with a PriorAttainment object in the request body will update an existing PriorAttainment entity in the database.", response = PriorAttainmentDto.class)
    @ApiResponses(value = [
        @ApiResponse(code = 200, message = "Returns a copy of the newly updated PriorAttainment object."),
        @ApiResponse(code = 400, message = "Returns an Error object stating the request body does not match the expected input."),
        @ApiResponse(code = 404, message = "Returns an Error object stating that the resource could not be found. This happens if the specified id cannot be found in the database.")
    ])
    @RequestMapping(value = "/{priorAttainmentId}", produces = ["application/json"], method = RequestMethod.PUT)
    public ResponseEntity<PriorAttainmentDto> priorAttainmentsPriorAttainmentIdPut(
            @ApiParam(value = "The unique ID of the PriorAttainment to retrieve", required = true) @PathVariable("priorAttainmentId") Integer priorAttainmentId,
            @ApiParam(value = "The PriorAttainment object to be created, without the priorAttainmentId fields", required = true) @RequestBody PriorAttainmentDto priorAttainment) throws NotFoundException, InvalidDataException {
        LOGGER.info("** PriorAttainmentsApi - priorAttainmentsPUT");
        if (priorAttainmentId != priorAttainment.id) {
            throw new InvalidDataException()
        }
        PriorAttainment priorAttainmentToSave = PriorAttainmentDto.mapToPriorAttainmentEntity(priorAttainment)
        PriorAttainment priorAttainmentSaved = priorAttainmentService.updatePriorAttainment(priorAttainmentToSave)
        return new ResponseEntity<PriorAttainmentDto>(PriorAttainmentDto.mapFromPriorAttainmentEntity(priorAttainmentSaved), HttpStatus.OK);
    }
}
