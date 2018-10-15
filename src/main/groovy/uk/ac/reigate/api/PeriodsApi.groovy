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

import uk.ac.reigate.domain.academic.Block
import uk.ac.reigate.domain.academic.Period
import uk.ac.reigate.dto.PeriodDto
import uk.ac.reigate.exceptions.InvalidDataException
import uk.ac.reigate.exceptions.NotFoundException
import uk.ac.reigate.model.SearchResult
import uk.ac.reigate.services.BlockService
import uk.ac.reigate.services.PeriodService


@Controller
@RequestMapping(value = "/api/periods", produces = [ APPLICATION_JSON_VALUE ])
@Api(value = "/api/periods", description = "the periods API")
public class PeriodsApi {
    
    private static final Logger LOGGER = Logger.getLogger(PeriodsApi.class);
    
    @Autowired
    private final PeriodService periodService;
    
    @Autowired
    private final BlockService blockService;
    
    /**
     * Default NoArgs constructor
     */
    PeriodsApi() {}
    
    /**
     * Autowired constructor
     */
    PeriodsApi(PeriodService periodService) {
        this.periodService = periodService;
    }
    
    /**
     * The periodsGet method is used to retrieve a full list of all the PeriodDto objects
     *
     * @return A ResponseEntity with the corresponding list of PeriodDto objects
     * @throws NotFoundException if nothing is found then the the NotFoundException is thrown.
     */
    @ApiOperation(value = "Collection of Period entities", notes = "A GET request to the Periods endpoint returns an array of all the periods in the system.", response = PeriodDto.class, responseContainer = "List")
    @ApiResponses(value = [
        @ApiResponse(code = 200, message = "An array of periods")
    ])
    @RequestMapping(value = "", produces = ["application/json"], method = RequestMethod.GET)
    public ResponseEntity<List<PeriodDto>> periodsGet() throws NotFoundException {
        LOGGER.info("** PeriodsApi - periodsGet");
        List<Period> periods = periodService.findAll();
        return new ResponseEntity<List<PeriodDto>>(PeriodDto.mapFromPeriodsEntities(periods), HttpStatus.OK);
    }
    
    /**
     * The periodsPost method is used to create a new instance of a Period from the supplied PeriodDto
     *
     * @param period the PeriodDto to use to create the new Period object
     * @return A ResponseEntity with the newly created Period object
     * @throws InvalidDataException if there is an issue with the data provided in the RequestBody then an InvalidDataException is thrown
     */
    @ApiOperation(value = "Creates a new Period entity", notes = "A POST request to the Periods endpoint with a Period object in the request body will create a new Period entity in the database.", response = PeriodDto.class)
    @ApiResponses(value = [
        @ApiResponse(code = 201, message = "Returns a copy of the created Period entity including the periodId that has just been created."),
        @ApiResponse(code = 400, message = "Returns an Error object stating the request body does not match the expected input.")
    ])
    @RequestMapping(value = "", produces = ["application/json"], method = RequestMethod.POST)
    public ResponseEntity<PeriodDto> periodsPost(@ApiParam(value = "The Period object to be created, without the periodId fields", required = true) @RequestBody @Valid PeriodDto period) throws NotFoundException, InvalidDataException {
        LOGGER.info("** PeriodsApi - periodsPOST");
        if (period.id != null) {
            throw new InvalidDataException(400, "No ID field should be provided when creating")
        }
        Block block
        if(period.blockId != null) {
            block = blockService.findById(period.blockId)
        }
        Period periodToSave = PeriodDto.mapToPeriodEntity(period, block)
        Period periodSaved = periodService.save(periodToSave)
        return new ResponseEntity<PeriodDto>(PeriodDto.mapFromPeriodEntity(periodSaved), HttpStatus.CREATED);
    }
    
    /**
     * The periodsPeriodIdGet method is used to retrieve an instance of a PeriodDto object as identified by the periodId provided
     *
     * @param periodId the period ID for the Period object retrieve
     * @return A ResponseEntity with the corresponding PeriodDto object
     * @throws NotFoundException if nothing is found then the the NotFoundException is thrown.
     */
    @ApiOperation(value = "Retrieves an indivdual instance of a Period identified by the periodId", notes = "A getGET request to the Period instance endpoint will retrieve an instance of a Period entity as identified by the periodId provided in the URI.", response = PeriodDto.class)
    @ApiResponses(value = [
        @ApiResponse(code = 200, message = "Returns a copy of the Period as identified by the periodId"),
        @ApiResponse(code = 404, message = "Returns an Error object stating that the resource could not be found. This happens if the specified id cannot be found in the database.")
    ])
    @RequestMapping(value = "/{periodId}", produces = ["application/json"], method = RequestMethod.GET)
    public ResponseEntity<PeriodDto> periodsPeriodIdGet(@ApiParam(value = "The unique ID of the Period to retrieve", required = true) @PathVariable("periodId") Integer periodId) throws NotFoundException {
        LOGGER.info("** PeriodsApi - periodsPeriodIdGet");
        Period period = periodService.findById(periodId);
        if (period == null) {
            throw new NotFoundException();
        }
        return new ResponseEntity<PeriodDto>(PeriodDto.mapFromPeriodEntity(period), HttpStatus.OK);
    }
    
    /**
     * The periodsPeriodIdPut is used to update
     *
     * @param periodId the period ID for the Period object to update
     * @param period the new data for the Period object
     * @return the newly updated PeriodDto object
     * @throws NotFoundException if nothing is found then the the NotFoundException is thrown.
     * @throws InvalidDataException if there is an issue with the data provided in the RequestBody then an InvalidDataException is thrown
     */
    @ApiOperation(value = "Used to update a Period entity", notes = "A PUT request to the Period instance endpoint with a Period object in the request body will update an existing Period entity in the database.", response = PeriodDto.class)
    @ApiResponses(value = [
        @ApiResponse(code = 200, message = "Returns a copy of the newly updated Period object."),
        @ApiResponse(code = 400, message = "Returns an Error object stating the request body does not match the expected input."),
        @ApiResponse(code = 404, message = "Returns an Error object stating that the resource could not be found. This happens if the specified id cannot be found in the database.")
    ])
    @RequestMapping(value = "/{periodId}", produces = ["application/json"], method = RequestMethod.PUT)
    public ResponseEntity<PeriodDto> periodsPeriodIdPut(
            @ApiParam(value = "The unique ID of the Period to retrieve", required = true) @PathVariable("periodId") Integer periodId,
            @ApiParam(value = "The Period object to be created, without the periodId fields", required = true) @RequestBody PeriodDto period) throws NotFoundException, InvalidDataException {
        LOGGER.info("** PeriodsApi - periodsPUT");
        if (periodId != period.id) {
            throw new InvalidDataException()
        }
        Block block
        if(period.blockId != null) {
            block = blockService.findById(period.blockId)
        }
        Period periodToSave = PeriodDto.mapToPeriodEntity(period, block)
        Period periodSaved = periodService.save(periodToSave)
        return new ResponseEntity<PeriodDto>(PeriodDto.mapFromPeriodEntity(periodSaved), HttpStatus.OK);
    }
}
