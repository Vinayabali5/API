package uk.ac.reigate.api.exam

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

import uk.ac.reigate.domain.exam.ExamSession
import uk.ac.reigate.dto.exam.ExamSessionDto
import uk.ac.reigate.exceptions.InvalidDataException
import uk.ac.reigate.exceptions.NotFoundException
import uk.ac.reigate.model.SearchResult
import uk.ac.reigate.services.exam.ExamSessionService


@Controller
@RequestMapping(value = "/api/examSessions", produces = [ APPLICATION_JSON_VALUE ])
@Api(value = "/api/examSessions", description = "the exam sessions basedata API")
public class ExamSessionsApi {
    
    private final static Logger LOGGER = Logger.getLogger(ExamSessionsApi.class);
    
    @Autowired
    private final ExamSessionService examSessionService;
    
    /**
     * Default NoArgs constructor
     */
    ExamSessionsApi() {}
    
    /**
     * Autowired constructor
     */
    ExamSessionsApi(ExamSessionService examSessionService) {
        this.examSessionService = examSessionService;
    }
    
    /**
     * The examSessionsGet method is used to retrieve a full list of all the ExamSessionDto objects
     * 
     * @return A responseEntity with the corresponding list of ExamSessionDto objects
     * @throws NotFoundException if nothing is found then the NotFoundException is thrown.
     */
    @ApiOperation(value = "Collection of ExamSession entities",
    notes = "A GET request to the ExamComponents endpoint returns an array of all the examComponents in the system.",
    response = ExamSessionDto.class,
    responseContainer = "List")
    @ApiResponses(value = [
        @ApiResponse(code = 200, message = "An array of examSessions")
    ])
    @RequestMapping(value = "", produces = ["application/json"], method = RequestMethod.GET)
    public ResponseEntity<List<ExamSessionDto>> examSessionsGet() throws NotFoundException {
        LOGGER.info("** ExamSessionsApi - examSessionsGet");
        List<ExamSession> examSessions = examSessionService.findAll();
        return new ResponseEntity<List<ExamSessionDto>>(ExamSessionDto.mapFromExamSessionEntities(examSessions), HttpStatus.OK);
    }
    
    /**
     * The examSessionsExamSessionIdGet method is used to retrieve an instance of an ExamSessionDto object as identified by the examSessionId provided
     *
     * @param examSessionId the examSession ID for the ExamSession object to retrieve
     * @return A ResponseEntity with the corresponding ExamSessionDto object
     * @throws NotFoundException if nothing is found then the NotFoundException is thrown.
     */
    @ApiOperation(value = "retrieves an individual instance of an ExamSession identified by the examSessionId",
    notes = "A GET request to the ExamSession endpoint will retrieve an instance of an ExamSession entity as identified by the examSessionId provided in the URI.",
    response = ExamSessionDto.class)
    @ApiResponses(value = [
        @ApiResponse(code = 200, message = "Returns a copy of the ExamSession as identified by the examSessionId"),
        @ApiResponse(code = 404, message = "Returns an Error object stating that the resource could not be found. This happens if the specified ID cannot be found in the database.")
    ])
    @RequestMapping(value = "/{examSessionId}", produces = ["application/json"], method = RequestMethod.GET)
    public ResponseEntity<ExamSessionDto> examSessionsExamSessionIdGet(@ApiParam(value = "The unique ID of the ExamSession to retrieve.", required = true) @PathVariable("examSessionId") Integer examSessionId) throws NotFoundException {
        LOGGER.info("** ExamSessionsApi - examSessionsExamSessionIdGet");
        ExamSession examSession = examSessionService.findById(examSessionId);
        if (examSession == null) {
            throw new NotFoundException();
        }
        return new ResponseEntity<ExamSessionDto>(ExamSessionDto.mapFromExamSessionEntity(examSession), HttpStatus.OK);
    }
    
    //Todo: Rewrite as a POST method with exam session date being passed in http body.
    /**
     * The examSessionsExamSessionGetByDateAndSession method is used to retrieve an ExamSessionDto object as identified by examDate and examSession
     *
     * @param examdate the date for the ExamSession object to retrieve
     * @param examSession the session (A or P) for the ExamSession object to retrieve
     * @return A ResponseEntity with the corresponding ExamSessionDto object
     * @throws NotFoundException if nothing is found then the NotFoundException is thrown.
     */
    @ApiOperation(value = "Retrieve an ExamSession entity identified by the examDate and examSession",
    notes = "A GET request to the ExamSession endpoint will retrieve an ExamSession entity as identified by the examDate and examSession provided in the URI.",
    response = ExamSessionDto.class)
    @ApiResponses(value = [
        @ApiResponse(code = 200, message = "Returns an ExamSession as identified by the examDate and examSession"),
        @ApiResponse(code = 404, message = "?.")
    ])
    @RequestMapping(value = "/{examDate}/{examSession}", produces = ["application/json"], method = RequestMethod.GET)
    public ResponseEntity<ExamSessionDto> examSessionExamDateAndSessionGet(
            @ApiParam(value = "The examDate of the examSession to retrieve.", required = true) @PathVariable("examDate") Long examTimestamp,
            @ApiParam(value = "The session of the ExamSession to retrieve.", required = true) @PathVariable("examSession") String session) throws NotFoundException {
        LOGGER.info("** ExamSessionApi - examSessionExamDateAndSessionGet");
        Date examDate = new Date((long)examTimestamp);
        examDate.clearTime();
        LOGGER.info("      examDate: " + examDate);
        ExamSession examSession = examSessionService.findExamSessionDateAndSession(examDate, session);
        if (examSession == null) {
            throw new NotFoundException();
        }
        return new ResponseEntity<ExamSessionDto>(ExamSessionDto.mapFromExamSessionEntity(examSession), HttpStatus.OK);
    }
    
    /**
     * The examSessionsPost method is used to create a new instance of an ExamSession from the supplied ExamSessionDto
     * 
     * @param examSession the ExamSessionDto to use to create the new ExamSession object
     * @return A ResponseEntity with the newly created ExamSession object
     * @throws InvalidDataException if there is an issue with the data provided in the requestBody then an InvalidDataException is thrown
     */
    @ApiOperation(value = "Creates a new ExamSession entity",
    notes = "A POST request to the ExamSessions endpoint with an ExamSession object in the request body will create a new ExamSession entity in the database.",
    response = ExamSessionDto.class)
    @ApiResponses(value = [
        @ApiResponse(code = 201, message = "Returns a copy of the created ExamSession entity including the examSessionId that has just been created."),
        @ApiResponse(code = 400, message = "Returns an Error object stating the request body does not match the expected input.")
    ])
    @RequestMapping(value = "", produces = ["application/json"], method = RequestMethod.POST)
    public ResponseEntity<ExamSessionDto> examSessionsPost(@ApiParam(value = "The ExamSession object to be created, without the examSessionId fields", required = true) @RequestBody @Valid ExamSessionDto examSession) throws NotFoundException, InvalidDataException {
        LOGGER.info("** ExamSessionsApi - examSessionPOST");
        if (examSession.id != null) {
            throw new InvalidDataException(400, "No ID field should be provided when creating");
        }
        ExamSession examSessionToSave = ExamSessionDto.mapToExamSessionEntity(examSession);
        ExamSession examSessionSaved = examSessionService.save(examSessionToSave);
        return new ResponseEntity<ExamSessionDto>(ExamSessionDto.mapFromExamSessionEntity(examSessionSaved), HttpStatus.CREATED);
    }
    
    
    /**
     * The examSessionsExamSessionIdPut is used to update an instance of an ExamSession from the supplied ExamSessionDto object
     * 
     * @param examSessionId the examSession ID for the ExamSession object to update
     * @param examSession the new data for the ExamSession object
     * @return the newly updated ExamSessionDto object
     * @throws NotFoundException if nothing is found then the NotFoundException is thrown.
     * @throws InvalidDataException if there is an issue with the data provided in the RequestBody then an InvalidDatException is thrown 
     */
    @ApiOperation(value = "Used to update an ExamSession entity",
    notes = "A PUT request to the ExamSession instance endpoint with an ExamSession object in the request body will update an existing ExamSession entity in the database",
    response = ExamSessionDto.class)
    @ApiResponses(value = [
        @ApiResponse(code = 200, message = "Returns a copy of the newly updated ExamSession object."),
        @ApiResponse(code = 400, message = "Returns an Error object stating that the request body does not match the expected input."),
        @ApiResponse(code = 404, message = "Returns an Error object stating that the resource could not be found. This happens if the specified ID cannot be found in the database.")
    ])
    @RequestMapping(value = "/{examSessionId}", produces = ["application/json"], method = RequestMethod.PUT)
    public ResponseEntity<ExamSessionDto> examSessionsExamSessionIdPut(
            @ApiParam(value = "The unique ID of the ExamSession to update", required = true) @PathVariable("examSessionId") Integer examSessionId,
            @ApiParam(value = "The ExamSession object to be created, without the examSessionId fields", required = true) @RequestBody ExamSessionDto examSession) throws NotFoundException, InvalidDataException {
        LOGGER.info("** ExamSessionsApi - examSessionsExamSessionIdPut");
        if (examSessionId != examSession.id) {
            throw new InvalidDataException();
        }
        ExamSession examSessionToSave = ExamSessionDto.mapToExamSessionEntity(examSession);
        ExamSession examSessionSaved = examSessionService.updateExamSession(examSessionToSave);
        return new ResponseEntity<ExamSessionDto>(ExamSessionDto.mapFromExamSessionEntity(examSessionSaved), HttpStatus.OK);
    }
}
