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

import uk.ac.reigate.domain.Room
import uk.ac.reigate.domain.Staff;
import uk.ac.reigate.domain.academic.CourseGroup
import uk.ac.reigate.domain.academic.Period;
import uk.ac.reigate.domain.academic.Timetable
import uk.ac.reigate.dto.TimetableDto
import uk.ac.reigate.exceptions.InvalidDataException
import uk.ac.reigate.exceptions.NotFoundException
import uk.ac.reigate.model.SearchResult
import uk.ac.reigate.services.CourseGroupService
import uk.ac.reigate.services.PeriodService
import uk.ac.reigate.services.RoomService
import uk.ac.reigate.services.StaffService
import uk.ac.reigate.services.TimetableService


@Controller
@RequestMapping(value = "/api/timetables", produces = [ APPLICATION_JSON_VALUE ])
@Api(value = "/api/timetables", description = "the timetables API")
public class TimetablesApi {
    
    private static final Logger LOGGER = Logger.getLogger(TimetablesApi.class);
    
    @Autowired
    private final TimetableService timetableService;
    
    @Autowired
    private final CourseGroupService courseGroupService;
    
    @Autowired
    private final PeriodService periodService;
    
    @Autowired
    private final RoomService roomService;
    
    @Autowired
    private final StaffService staffService;
    
    /**
     * Default NoArgs constructor
     */
    TimetablesApi() {}
    
    /**
     * Autowired constructor
     */
    TimetablesApi(TimetableService timetableService) {
        this.timetableService = timetableService;
    }
    
    /**
     * The timetablesGet method is used to retrieve a full list of all the TimetableDto objects
     *
     * @return A ResponseEntity with the corresponding list of TimetableDto objects
     * @throws NotFoundException if nothing is found then the the NotFoundException is thrown.
     */
    @ApiOperation(value = "Collection of Timetable entities", notes = "A GET request to the Timetables endpoint returns an array of all the timetables in the system.", response = TimetableDto.class, responseContainer = "List")
    @ApiResponses(value = [
        @ApiResponse(code = 200, message = "An array of timetables")
    ])
    @RequestMapping(value = "", produces = ["application/json"], method = RequestMethod.GET)
    public ResponseEntity<List<TimetableDto>> timetablesGet() throws NotFoundException {
        LOGGER.info("** TimetablesApi - timetablesGet");
        List<Timetable> timetables = timetableService.findAll();
        return new ResponseEntity<List<TimetableDto>>(TimetableDto.mapFromTimetablesEntities(timetables), HttpStatus.OK);
    }
    
    /**
     * The timetablesPost method is used to create a new instance of a Timetable from the supplied TimetableDto
     *
     * @param timetable the TimetableDto to use to create the new Timetable object
     * @return A ResponseEntity with the newly created Timetable object
     * @throws InvalidDataException if there is an issue with the data provided in the RequestBody then an InvalidDataException is thrown
     */
    @ApiOperation(value = "Creates a new Timetable entity", notes = "A POST request to the Timetables endpoint with a Timetable object in the request body will create a new Timetable entity in the database.", response = TimetableDto.class)
    @ApiResponses(value = [
        @ApiResponse(code = 201, message = "Returns a copy of the created Timetable entity including the timetableId that has just been created."),
        @ApiResponse(code = 400, message = "Returns an Error object stating the request body does not match the expected input.")
    ])
    @RequestMapping(value = "", produces = ["application/json"], method = RequestMethod.POST)
    public ResponseEntity<TimetableDto> timetablesPost(@ApiParam(value = "The Timetable object to be created, without the timetableId fields", required = true) @RequestBody @Valid TimetableDto timetable) throws NotFoundException, InvalidDataException {
        LOGGER.info("** TimetablesApi - timetablesPOST");
        if (timetable.id != null) {
            throw new InvalidDataException(400, "No ID field should be provided when creating")
        }
        CourseGroup courseGroup
        if (timetable.courseGroupId != null) {
            courseGroup = courseGroupService.findById(timetable.courseGroupId)
        }
        Period period
        if (timetable.periodId != null) {
            period = periodService.findById(timetable.periodId)
        }
        Room room
        if (timetable.roomId != null) {
            room = roomService.findById(timetable.roomId)
        }
        Staff teacher
        if (timetable.teachId != null) {
            teacher = staffService.findById(timetable.teachId)
        }
        Timetable timetableToSave = TimetableDto.mapToTimetableEntity(timetable, courseGroup, period, room, teacher)
        Timetable timetableSaved = timetableService.save(timetableToSave)
        return new ResponseEntity<TimetableDto>(TimetableDto.mapFromTimetableEntity(timetableSaved), HttpStatus.CREATED);
    }
    
    /**
     * The timetablesTimetableIdGet method is used to retrieve an instance of a TimetableDto object as identified by the timetableId provided
     *
     * @param timetableId the timetable ID for the Timetable object retrieve
     * @return A ResponseEntity with the corresponding TimetableDto object
     * @throws NotFoundException if nothing is found then the the NotFoundException is thrown.
     */
    @ApiOperation(value = "Retrieves an indivdual instance of a Timetable identified by the timetableId", notes = "A getGET request to the Timetable instance endpoint will retrieve an instance of a Timetable entity as identified by the timetableId provided in the URI.", response = TimetableDto.class)
    @ApiResponses(value = [
        @ApiResponse(code = 200, message = "Returns a copy of the Timetable as identified by the timetableId"),
        @ApiResponse(code = 404, message = "Returns an Error object stating that the resource could not be found. This happens if the specified id cannot be found in the database.")
    ])
    @RequestMapping(value = "/{timetableId}", produces = ["application/json"], method = RequestMethod.GET)
    public ResponseEntity<TimetableDto> timetablesTimetableIdGet(@ApiParam(value = "The unique ID of the Timetable to retrieve", required = true) @PathVariable("timetableId") Integer timetableId) throws NotFoundException {
        LOGGER.info("** TimetablesApi - timetablesTimetableIdGet");
        Timetable timetable = timetableService.findById(timetableId);
        if (timetable == null) {
            throw new NotFoundException();
        }
        return new ResponseEntity<TimetableDto>(TimetableDto.mapFromTimetableEntity(timetable), HttpStatus.OK);
    }
    
    /**
     * The timetablesTimetableIdPut is used to update
     *
     * @param timetableId the timetable ID for the Timetable object to update
     * @param timetable the new data for the Timetable object
     * @return the newly updated TimetableDto object
     * @throws NotFoundException if nothing is found then the the NotFoundException is thrown.
     * @throws InvalidDataException if there is an issue with the data provided in the RequestBody then an InvalidDataException is thrown
     */
    @ApiOperation(value = "Used to update a Timetable entity", notes = "A PUT request to the Timetable instance endpoint with a Timetable object in the request body will update an existing Timetable entity in the database.", response = TimetableDto.class)
    @ApiResponses(value = [
        @ApiResponse(code = 200, message = "Returns a copy of the newly updated Timetable object."),
        @ApiResponse(code = 400, message = "Returns an Error object stating the request body does not match the expected input."),
        @ApiResponse(code = 404, message = "Returns an Error object stating that the resource could not be found. This happens if the specified id cannot be found in the database.")
    ])
    @RequestMapping(value = "/{timetableId}", produces = ["application/json"], method = RequestMethod.PUT)
    public ResponseEntity<TimetableDto> timetablesTimetableIdPut(
            @ApiParam(value = "The unique ID of the Timetable to retrieve", required = true) @PathVariable("timetableId") Integer timetableId,
            @ApiParam(value = "The Timetable object to be created, without the timetableId fields", required = true) @RequestBody TimetableDto timetable) throws NotFoundException, InvalidDataException {
        LOGGER.info("** TimetablesApi - timetablePUT");
        if (timetableId != timetable.id) {
            throw new InvalidDataException()
        }
        CourseGroup courseGroup
        if (timetable.courseGroupId != null) {
            courseGroup = courseGroupService.findById(timetable.courseGroupId)
        }
        Period period
        if (timetable.periodId != null) {
            period = periodService.findById(timetable.periodId)
        }
        Room room
        if (timetable.roomId != null) {
            room = roomService.findById(timetable.roomId)
        }
        Staff teacher
        if (timetable.teachId != null) {
            teacher = staffService.findById(timetable.teachId)
        }
        Timetable timetableToSave = TimetableDto.mapToTimetableEntity(timetable, courseGroup, period, room, teacher)
        Timetable timetableSaved = timetableService.save(timetableToSave)
        return new ResponseEntity<TimetableDto>(TimetableDto.mapFromTimetableEntity(timetableSaved), HttpStatus.OK);
    }
}
