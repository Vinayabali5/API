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
import org.springframework.security.access.annotation.Secured
import org.springframework.stereotype.Controller
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestMethod

import uk.ac.reigate.domain.lookup.AttendanceMonitoring
import uk.ac.reigate.dto.AttendanceMonitoringDto
import uk.ac.reigate.exceptions.DataAlreadyExistsException
import uk.ac.reigate.exceptions.InvalidDataException
import uk.ac.reigate.exceptions.NotFoundException
import uk.ac.reigate.model.SearchResult
import uk.ac.reigate.services.AttendanceMonitoringService


@Controller
@RequestMapping(value = "/api/attendanceMonitorings", produces = [ APPLICATION_JSON_VALUE ])
@Api(value = "/api/attendanceMonitorings", description = "the attendanceMonitorings API")
public class AttendanceMonitoringsApi {
    
    private static final Logger LOGGER = Logger.getLogger(AttendanceMonitoringsApi.class);
    
    @Autowired
    private final AttendanceMonitoringService attendanceMonitoringService;
    
    /**
     * Default NoArgs constructor
     */
    AttendanceMonitoringsApi() {}
    
    /**
     * Autowired constructor
     */
    AttendanceMonitoringsApi(AttendanceMonitoringService attendanceMonitoringService) {
        this.attendanceMonitoringService = attendanceMonitoringService;
    }
    
    /**
     * The attendanceMonitoringsGet method is used to retrieve a full list of all the AttendanceMonitoringDto objects
     *
     * @return A ResponseEntity with the corresponding list of AttendanceMonitoringDto objects
     * @throws NotFoundException if nothing is found then the the NotFoundException is thrown.
     */
    @ApiOperation(value = "Collection of AttendanceMonitoring entities", notes = "A GET request to the AttendanceMonitorings endpoint returns an array of all the attendanceMonitorings in the system.", response = AttendanceMonitoringDto.class, responseContainer = "List")
    @ApiResponses(value = [
        @ApiResponse(code = 200, message = "An array of attendanceMonitorings")
    ])
    @RequestMapping(value = "", produces = ["application/json"], method = RequestMethod.GET)
    public ResponseEntity<List<AttendanceMonitoringDto>> attendanceMonitoringsGet() throws NotFoundException {
        LOGGER.info("** AttendanceMonitoringsApi - attendanceMonitoringsGet");
        List<AttendanceMonitoring> attendanceMonitorings = attendanceMonitoringService.findAll();
        return new ResponseEntity<List<AttendanceMonitoringDto>>(AttendanceMonitoringDto.mapFromAttendanceMonitoringsEntities(attendanceMonitorings), HttpStatus.OK);
    }
    
    /**
     * The attendanceMonitoringsPost method is used to create a new instance of a AttendanceMonitoring from the supplied AttendanceMonitoringDto
     *
     * @param attendanceMonitoring the AttendanceMonitoringDto to use to create the new AttendanceMonitoring object
     * @return A ResponseEntity with the newly created AttendanceMonitoring object
     * @throws InvalidDataException if there is an issue with the data provided in the RequestBody then an InvalidDataException is thrown
     */
    @ApiOperation(value = "Creates a new AttendanceMonitoring entity", notes = "A POST request to the AttendanceMonitorings endpoint with a AttendanceMonitoring object in the request body will create a new AttendanceMonitoring entity in the database.", response = AttendanceMonitoringDto.class)
    @ApiResponses(value = [
        @ApiResponse(code = 201, message = "Returns a copy of the created AttendanceMonitoring entity including the attendanceMonitoringId that has just been created."),
        @ApiResponse(code = 400, message = "Returns an Error object stating the request body does not match the expected input.")
    ])
    @RequestMapping(value = "", produces = ["application/json"], method = RequestMethod.POST)
    public ResponseEntity<AttendanceMonitoringDto> attendanceMonitoringsPost(
            @ApiParam(value = "The AttendanceMonitoring object to be created, without the attendanceMonitoringId fields", required = true)
            @RequestBody @Valid AttendanceMonitoringDto attendanceMonitoring) throws NotFoundException, InvalidDataException, DataAlreadyExistsException {
        LOGGER.info("** AttendanceMonitoringsApi - attendanceMonitoringsPOST");
        if (attendanceMonitoring.id == null) {
            throw new NotFoundException();
        }
        AttendanceMonitoring attendanceMonitoring1 = attendanceMonitoringService.findById(attendanceMonitoring.id);
        if (attendanceMonitoring1 != null) {
            throw new DataAlreadyExistsException("An Attendance Monitoring already exists with the supplied ID.");
        }
        AttendanceMonitoring attendanceMonitoringToSave = attendanceMonitoring.toAttendanceMonitoring() //AttendanceMonitoringDto.mapToAttendanceMonitoringEntity(attendanceMonitoring)
        AttendanceMonitoring attendanceMonitoringSaved = attendanceMonitoringService.save(attendanceMonitoringToSave)
        return new ResponseEntity<AttendanceMonitoringDto>(new AttendanceMonitoringDto(attendanceMonitoringSaved), HttpStatus.CREATED);
    }
    
    /**
     * The attendanceMonitoringsAttendanceMonitoringIdGet method is used to retrieve an instance of a AttendanceMonitoringDto object as identified by the attendanceMonitoringId provided
     *
     * @param attendanceMonitoringId the attendanceMonitoring ID for the AttendanceMonitoring object retrieve
     * @return A ResponseEntity with the corresponding AttendanceMonitoringDto object
     * @throws NotFoundException if nothing is found then the the NotFoundException is thrown.
     */
    @ApiOperation(value = "Retrieves an indivdual instance of a AttendanceMonitoring identified by the attendanceMonitoringId", notes = "A getGET request to the AttendanceMonitoring instance endpoint will retrieve an instance of a AttendanceMonitoring entity as identified by the attendanceMonitoringId provided in the URI.", response = AttendanceMonitoringDto.class)
    @ApiResponses(value = [
        @ApiResponse(code = 200, message = "Returns a copy of the AttendanceMonitoring as identified by the attendanceMonitoringId"),
        @ApiResponse(code = 404, message = "Returns an Error object stating that the resource could not be found. This happens if the specified id cannot be found in the database.")
    ])
    @RequestMapping(value = "/{attendanceMonitoringId}", produces = ["application/json"], method = RequestMethod.GET)
    public ResponseEntity<AttendanceMonitoringDto> attendanceMonitoringsAttendanceMonitoringIdGet(@ApiParam(value = "The unique ID of the AttendanceMonitoring to retrieve", required = true) @PathVariable("attendanceMonitoringId") Integer attendanceMonitoringId) throws NotFoundException {
        LOGGER.info("** AttendanceMonitoringsApi - attendanceMonitoringsAttendanceMonitoringIdGet");
        AttendanceMonitoring attendanceMonitoring = attendanceMonitoringService.findById(attendanceMonitoringId);
        if (attendanceMonitoring == null) {
            throw new NotFoundException();
        }
        return new ResponseEntity<AttendanceMonitoringDto>(AttendanceMonitoringDto.mapFromAttendanceMonitoringEntity(attendanceMonitoring), HttpStatus.OK);
    }
    
    /**
     * The attendanceMonitoringsAttendanceMonitoringIdPut is used to update
     *
     * @param attendanceMonitoringId the attendanceMonitoring ID for the AttendanceMonitoring object to update
     * @param attendanceMonitoring the new data for the AttendanceMonitoring object
     * @return the newly updated AttendanceMonitoringDto object
     * @throws NotFoundException if nothing is found then the the NotFoundException is thrown.
     * @throws InvalidDataException if there is an issue with the data provided in the RequestBody then an InvalidDataException is thrown
     */
    @ApiOperation(value = "Used to update a AttendanceMonitoring entity", notes = "A PUT request to the AttendanceMonitoring instance endpoint with a AttendanceMonitoring object in the request body will update an existing AttendanceMonitoring entity in the database.", response = AttendanceMonitoringDto.class)
    @ApiResponses(value = [
        @ApiResponse(code = 200, message = "Returns a copy of the newly updated AttendanceMonitoring object."),
        @ApiResponse(code = 400, message = "Returns an Error object stating the request body does not match the expected input."),
        @ApiResponse(code = 404, message = "Returns an Error object stating that the resource could not be found. This happens if the specified id cannot be found in the database.")
    ])
    @RequestMapping(value = "/{attendanceMonitoringId}", produces = ["application/json"], method = RequestMethod.PUT)
    public ResponseEntity<AttendanceMonitoringDto> attendanceMonitoringsAttendanceMonitoringIdPut(
            @ApiParam(value = "The unique ID of the AttendanceMonitoring to retrieve", required = true) @PathVariable("attendanceMonitoringId") Integer attendanceMonitoringId,
            @ApiParam(value = "The AttendanceMonitoring object to be created, without the attendanceMonitoringId fields", required = true) @RequestBody AttendanceMonitoringDto attendanceMonitoring) throws NotFoundException, InvalidDataException {
        LOGGER.info("** AttendanceMonitoringsApi - attendanceMonitoringsPUT");
        if (attendanceMonitoringId != attendanceMonitoring.id) {
            throw new InvalidDataException()
        }
        AttendanceMonitoring attendanceMonitoringToSave = AttendanceMonitoringDto.mapToAttendanceMonitoringEntity(attendanceMonitoring)
        AttendanceMonitoring attendanceMonitoringSaved = attendanceMonitoringService.updateAttendanceMonitoring(attendanceMonitoringToSave)
        return new ResponseEntity<AttendanceMonitoringDto>(AttendanceMonitoringDto.mapFromAttendanceMonitoringEntity(attendanceMonitoringSaved), HttpStatus.OK);
    }
}
