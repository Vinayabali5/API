package uk.ac.reigate.api.exam;

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

import uk.ac.reigate.domain.Room
import uk.ac.reigate.domain.exam.ExamSeatingPlan
import uk.ac.reigate.domain.exam.ExamSession
import uk.ac.reigate.dto.exam.ExamSeatingPlanDto
import uk.ac.reigate.exceptions.InvalidDataException
import uk.ac.reigate.exceptions.NotFoundException
import uk.ac.reigate.model.SearchResult
import uk.ac.reigate.services.RoomService
import uk.ac.reigate.services.exam.ExamSeatingPlanService
import uk.ac.reigate.services.exam.ExamSessionService


@Controller
@RequestMapping(value = "/api/examRooms", produces = [ APPLICATION_JSON_VALUE ])
@Api(value = "/api/examRooms", description = "the examRooms API")
public class ExamSeatingPlansApi {
    
    private static final Logger LOGGER = Logger.getLogger(ExamSeatingPlansApi.class);
    
    @Autowired
    private final ExamSeatingPlanService examSeatingPlanService;
    
    @Autowired
    private final ExamSessionService examSessionService;
    
    @Autowired
    private final RoomService roomService;
    
    /**
     * Default NoArgs constructor
     */
    ExamSeatingPlansApi() {}
    
    /**
     * The examRoomsGet method is used to retrieve a full list of all the ExamRoomDto objects
     *
     * @return A ResponseEntity with the corresponding list of ExamRoomDto objects
     * @throws NotFoundException if nothing is found then the the NotFoundException is thrown.
     */
    @ApiOperation(value = "Collection of ExamSeatingPlan entities",
    notes = "A GET request to the ExamRooms endpoint returns an array of all the examRooms in the system.",
    response = ExamSeatingPlanDto.class, responseContainer = "List")
    @ApiResponses(value = [
        @ApiResponse(code = 200, message = "An array of examRooms")
    ])
    @RequestMapping(value = "", produces = ["application/json"], method = RequestMethod.GET)
    public ResponseEntity<List<ExamSeatingPlanDto>> examRoomsGet() throws NotFoundException {
        LOGGER.info("** ExamSeatingPlansApi - examRoomsGet");
        List<ExamSeatingPlan> examRooms = examSeatingPlanService.findAll();
        return new ResponseEntity<List<ExamSeatingPlanDto>>(ExamSeatingPlanDto.mapFromExamRoomsEntities(examRooms), HttpStatus.OK);
    }
    
    //Todo: Rewrite as a POST method with exam session date being passed in http body.
    /**
     * The examRoomsExamRoomsGetByDateAndSession method is used to retrieve a list of ExamRoomDto objects as identified by examDate and examSession
     *
     * @param examdate the date for the ExamSeatingPlan objects to retrieve
     * @param examSession the session (A or P) for the ExamSeatingPlan objects to retrieve
     * @return A ResponseEntity with the corresponding ExamRoomDto objects
     * @throws NotFoundException if nothing is found then the NotFoundException is thrown.
     */
    @ApiOperation(value = "Retrieve a collection of ExamSeatingPlan entities identified by the examDate and examSession",
    notes = "A GET request to the ExamSeatingPlan endpoint will retrieve a collection of ExamSeatingPlan entities as identified by the examDate and examSession provided in the URI.",
    response = ExamSeatingPlanDto.class)
    @ApiResponses(value = [
        @ApiResponse(code = 200, message = "Returns a list of ExamRooms as identified by the examDate and examSession"),
        @ApiResponse(code = 404, message = "?.")
    ])
    @RequestMapping(value = "/session/{examDate}/{examSession}", produces = ["application/json"], method = RequestMethod.GET)
    public ResponseEntity<List<ExamSeatingPlanDto>> examRoomsExamDateAndSessionGet(
            @ApiParam(value = "The examDate of the examRooms to retrieve.", required = true) @PathVariable("examDate") Long examTimestamp,
            @ApiParam(value = "The examSession of the ExamRooms to retrieve.", required = true) @PathVariable("examSession") String examSession) throws NotFoundException {
        LOGGER.info("** ExamSeatingPlansApi - examRoomsExamDateAndSessionGet");
        Date examDate = new Date((long)examTimestamp);
        examDate.clearTime();
        LOGGER.info("      examDate: " + examDate);
        List<ExamSeatingPlan> examRooms = examSeatingPlanService.findExamRoomsByDateAndSession(examDate, examSession);
        if (examRooms == null) {
            throw new NotFoundException();
        }
        return new ResponseEntity<List<ExamSeatingPlanDto>>(ExamSeatingPlanDto.mapFromExamRoomsEntities(examRooms), HttpStatus.OK);
    }
    
    /**
     * The examRoomsExamRoomsGetByDateAndSession method is used to retrieve a list of ExamRoomDto objects as identified by examDate and examSession
     *
     * @param examdate the date for the ExamSeatingPlan objects to retrieve
     * @param examSession the session (A or P) for the ExamSeatingPlan objects to retrieve
     * @return A ResponseEntity with the corresponding ExamRoomDto objects
     * @throws NotFoundException if nothing is found then the NotFoundException is thrown.
     */
    @ApiOperation(value = "Retrieve a collection of ExamSeatingPlan entities identified by the examDate and examSession",
    notes = "A GET request to the ExamSeatingPlan endpoint will retrieve a collection of ExamSeatingPlan entities as identified by the examDate and examSession provided in the URI.",
    response = ExamSeatingPlanDto.class)
    @ApiResponses(value = [
        @ApiResponse(code = 200, message = "Returns a list of ExamRooms as identified by the examDate and examSession"),
        @ApiResponse(code = 404, message = "?.")
    ])
    @RequestMapping(value = "/session/{examYear}/{examMonth}/{examDay}/{examSession}", produces = ["application/json"], method = RequestMethod.GET)
    public ResponseEntity<List<ExamSeatingPlanDto>> examRoomsExamDateAndSessionGet(
            @ApiParam(value = "The examDate YEAR of the examRooms to retrieve.", required = true) @PathVariable("examYear") Long examYear,
            @ApiParam(value = "The examDate MONTH of the examRooms to retrieve.", required = true) @PathVariable("examMonth") Long examMonth,
            @ApiParam(value = "The examDate DAY of the examRooms to retrieve.", required = true) @PathVariable("examDay") Long examDay,
            @ApiParam(value = "The examSession of the ExamRooms to retrieve.", required = true) @PathVariable("examSession") String examSession) throws NotFoundException {
        LOGGER.info("** ExamSeatingPlansApi - examRoomsExamDateAndSessionGet");
        Date examDate = new Date().parse('yyyy-mm-dd', examYear + '-' + examMonth + '-' + examDay);
        LOGGER.info("      examDate: " + examDate);
        List<ExamSeatingPlan> examRooms = examSeatingPlanService.findExamRoomsByDateAndSession(examDate, examSession);
        if (examRooms == null) {
            throw new NotFoundException();
        }
        return new ResponseEntity<List<ExamSeatingPlanDto>>(ExamSeatingPlanDto.mapFromExamRoomsEntities(examRooms), HttpStatus.OK);
    }
    
    /**
     * The examRoomsExamRoomsGetBySessionIdAndRoomId method is used to retrieve a list of ExamRoomDto objects as identified by examSessionId and examRoomId
     *
     * @param examSessionId the exam session ID for the ExamSeatingPlan objects to retrieve
     * @param roomId the room Id for the ExamSeatingPlan objects to retrieve
     * @return A ResponseEntity with the corresponding ExamRoomDto objects
     * @throws NotFoundException if nothing is found then the NotFoundException is thrown.
     */
    @ApiOperation(value = "Retrieve an ExamSeatingPlan entity identified by the examSessionId and roomId",
    notes = "A GET request to the ExamSeatingPlan endpoint will retrieve a collection of ExamSeatingPlan entities as identified by the examSessionId and roomId provided in the URI.",
    response = ExamSeatingPlanDto.class)
    @ApiResponses(value = [
        @ApiResponse(code = 200, message = "Returns a list of ExamRooms as identified by the examSessionId and roomId"),
        @ApiResponse(code = 404, message = "?.")
    ])
    @RequestMapping(value = "/{examSessionId}/{roomId}", produces = ["application/json"], method = RequestMethod.GET)
    public ResponseEntity<ExamSeatingPlanDto> examRoomsExamSessionIdAndRoomIdGet(
            @ApiParam(value = "The examSessionId of the examRooms to retrieve.", required = true) @PathVariable("examSessionId") Integer examSessionId,
            @ApiParam(value = "The roomId of the ExamRooms to retrieve.", required = true) @PathVariable("roomId") Integer roomId) throws NotFoundException {
        LOGGER.info("** ExamSeatingPlansApi - examRoomsExamSessionIdAndRoomIdGet");
        ExamSeatingPlan examRoom = examSeatingPlanService.findExamRoomsByExamSessionIdAndRoomId(examSessionId, roomId);
        if (examRoom == null) {
            throw new NotFoundException();
        }
        return new ResponseEntity<ExamSeatingPlanDto>(ExamSeatingPlanDto.mapFromExamRoomEntity(examRoom), HttpStatus.OK);
    }
    
    /**
     * The examRoomsExamRoomIdGet method is used to retrieve an instance of an ExamRoomDto object as identified by the examRoomId provided
     *
     * @param examRoomId the examRoom ID for the ExamSeatingPlan object retrieve
     * @return A ResponseEntity with the corresponding ExamRoomDto object
     * @throws NotFoundException if nothing is found then the the NotFoundException is thrown.
     */
    @ApiOperation(value = "Retrieves an indivdual instance of an ExamSeatingPlan identified by the examRoomId",
    notes = "A GET request to the ExamSeatingPlan instance endpoint will retrieve an instance of a ExamSeatingPlan entity as identified by the examRoomId provided in the URI.",
    response = ExamSeatingPlanDto.class)
    @ApiResponses(value = [
        @ApiResponse(code = 200, message = "Returns a copy of the ExamSeatingPlan as identified by the examRoomId"),
        @ApiResponse(code = 404, message = "Returns an Error object stating that the resource could not be found. This happens if the specified id cannot be found in the database.")
    ])
    @RequestMapping(value = "/{examRoomId}", produces = ["application/json"], method = RequestMethod.GET)
    public ResponseEntity<ExamSeatingPlanDto> examRoomsExamRoomIdGet(@ApiParam(value = "The unique ID of the ExamSeatingPlan to retrieve", required = true) @PathVariable("examRoomId") Integer examRoomId) throws NotFoundException {
        LOGGER.info("** ExamSeatingPlansApi - examRoomsExamRoomIdGet");
        ExamSeatingPlan examRoom = examSeatingPlanService.findById(examRoomId);
        LOGGER.info(examRoom);
        if (examRoom == null) {
            throw new NotFoundException();
        }
        return new ResponseEntity<ExamSeatingPlanDto>(ExamSeatingPlanDto.mapFromExamRoomEntity(examRoom), HttpStatus.OK);
    }
    
    /**
     * The examRoomsPost method is used to create a new instance of a ExamSeatingPlan from the supplied ExamRoomDto
     *
     * @param examRoom the ExamRoomDto to use to create the new ExamSeatingPlan object
     * @return A ResponseEntity with the newly created ExamSeatingPlan object
     * @throws InvalidDataException if there is an issue with the data provided in the RequestBody then an InvalidDataException is thrown
     */
    @ApiOperation(value = "Creates a new ExamSeatingPlan entity",
    notes = "A POST request to the ExamRooms endpoint with an ExamSeatingPlan object in the request body will create a new ExamSeatingPlan entity in the database.",
    response = ExamSeatingPlanDto.class)
    @ApiResponses(value = [
        @ApiResponse(code = 201, message = "Returns a copy of the created ExamSeatingPlan entity including the examRoomId that has just been created."),
        @ApiResponse(code = 400, message = "Returns an Error object stating the request body does not match the expected input.")
    ])
    @RequestMapping(value = "", produces = ["application/json"], method = RequestMethod.POST)
    public ResponseEntity<ExamSeatingPlanDto> roomsPost(@ApiParam(value = "The ExamSeatingPlan object to be created, without the examRoomId fields", required = true) @RequestBody @Valid ExamSeatingPlanDto examRoom) throws NotFoundException, InvalidDataException {
        LOGGER.info("** ExamSeatingPlansApi - examRoomsPOST");
        if (examRoom.id != null) {
            throw new InvalidDataException(400, "No ID field should be provided when creating")
        }
        ExamSession examSession
        if (examRoom.examSession.id != null) {
            examSession = examSessionService.findById(examRoom.examSession.id)
        }
        
        Room room
        if (examRoom.room.id != null) {
            room = roomService.findById(examRoom.room.id);
        }
        
        ExamSeatingPlan examRoomToSave = ExamSeatingPlanDto.mapToExamRoomEntity(examRoom, examSession, room)
        ExamSeatingPlan examRoomSaved = examSeatingPlanService.save(examRoomToSave)
        return new ResponseEntity<ExamSeatingPlanDto>(ExamSeatingPlanDto.mapFromExamRoomEntity(examRoomSaved), HttpStatus.CREATED);
    }
    
    /**
     * The examRoomsExamRoomIdPut is used to update
     *
     * @param roomId the room ID for the Room object to update
     * @param room the new data for the Room object
     * @return the newly updated RoomDto object
     * @throws NotFoundException if nothing is found then the the NotFoundException is thrown.
     * @throws InvalidDataException if there is an issue with the data provided in the RequestBody then an InvalidDataException is thrown
     */
    @ApiOperation(value = "Used to update an ExamSeatingPlan entity", notes = "A PUT request to the ExamSeatingPlan instance endpoint with an ExamSeatingPlan object in the request body will update an existing ExamSeatingPlan entity in the database.", response = ExamSeatingPlanDto.class)
    @ApiResponses(value = [
        @ApiResponse(code = 200, message = "Returns a copy of the newly updated ExamSeatingPlan object."),
        @ApiResponse(code = 400, message = "Returns an Error object stating the request body does not match the expected input."),
        @ApiResponse(code = 404, message = "Returns an Error object stating that the resource could not be found. This happens if the specified id cannot be found in the database.")
    ])
    @RequestMapping(value = "/{examRoomId}", produces = ["application/json"], method = RequestMethod.PUT)
    public ResponseEntity<ExamSeatingPlanDto> examRoomsExamRoomIdPut(
            @ApiParam(value = "The unique ID of the ExamSeatingPlan to retrieve", required = true) @PathVariable("examRoomId") Integer examRoomId,
            @ApiParam(value = "The ExamSeatingPlan object to be created, without the examRoomId fields", required = true) @RequestBody ExamSeatingPlanDto examRoom) throws NotFoundException, InvalidDataException {
        LOGGER.info("** ExamSeatingPlansApi - examRoomsPUT");
        if (examRoomId != examRoom.id) {
            throw new InvalidDataException()
        }
        ExamSession examSession
        if(examRoom.id != null){
            examSession = examSessionService.findById(examRoom.examSession.id)
        }
        Room room
        if(examRoom.room.id != null){
            room = roomService.findById(examRoom.room.id)
        }
        ExamSeatingPlan examRoomToSave = ExamSeatingPlanDto.mapToExamRoomEntity(examRoom, examSession, room)
        ExamSeatingPlan examRoomSaved = examSeatingPlanService.updateExamRoom(examRoomToSave)
        return new ResponseEntity<ExamSeatingPlanDto>(ExamSeatingPlanDto.mapFromExamRoomEntity(examRoomSaved), HttpStatus.OK);
    }
    
    
    /** examRoomsDelete is to delete a ExamSeatingPlan object on examRoomId
     * @param examRoomId
     * @return
     * @throws NotFoundException
     */
    @ApiOperation(value = "Deletes an examRoom entity",
    notes = "A DELETE request to the ExamRooms endpoint with an ExamRoom object in the request body will delete an ExamRoom entity from the database.")
    @ApiResponses(value = [
        @ApiResponse(code = 201, message = "Returns an OK object stating that the ExamRoom entity has just been deleted")
    ])
    @RequestMapping(value = "/{examRoomId}", produces = ["application/json"], method = RequestMethod.DELETE)
    public ResponseEntity<String> examRoomsDelete(
            @ApiParam(value = "The id element of the ExamRoom to delete", required = true) @PathVariable("examRoomId") Integer examRoomId
    ) throws NotFoundException {
        LOGGER.info("** ExamRoomsApi - examRoomsDELETE");
        try {
            examSeatingPlanService.deleteExamRoom(examRoomId);
        } catch (Exception e) {
            throw new NotFoundException();
        }
        return new ResponseEntity<String>(HttpStatus.OK);
    }
}
