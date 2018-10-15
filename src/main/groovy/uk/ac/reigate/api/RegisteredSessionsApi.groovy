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

import uk.ac.reigate.domain.academic.Period
import uk.ac.reigate.domain.register.RegisteredSession
import uk.ac.reigate.dto.SessionDto
import uk.ac.reigate.exceptions.InvalidDataException
import uk.ac.reigate.exceptions.NotFoundException
import uk.ac.reigate.model.SearchResult
import uk.ac.reigate.services.PeriodService
import uk.ac.reigate.services.RegisteredSessionService



@Controller
@RequestMapping(value = "/api/sessions", produces = [ APPLICATION_JSON_VALUE ])
@Api(value = "/api/sessions", description = "the sessions API")
public class RegisteredSessionsApi {
    
    private static final Logger LOGGER = Logger.getLogger(RegisteredSessionsApi.class);
    
    @Autowired
    private final RegisteredSessionService registeredSessionService;
    
    @Autowired
    private final PeriodService periodService;
    
    /**
     * Default NoArgs constructor
     */
    RegisteredSessionsApi() {}
    
    /**
     * Autowired constructor
     */
    RegisteredSessionsApi(RegisteredSessionService registeredSessionService) {
        this.registeredSessionService = registeredSessionService;
    }
    
    /**
     * The sessionsGet method is used to retrieve a full list of all the SessionDto objects
     *
     * @return A ResponseEntity with the corresponding list of SessionDto objects
     * @throws NotFoundException if nothing is found then the the NotFoundException is thrown.
     */
    @ApiOperation(value = "Collection of Session entities", notes = "A GET request to the Sessions endpoint returns an array of all the sessions in the system.", response = SessionDto.class, responseContainer = "List")
    @ApiResponses(value = [
        @ApiResponse(code = 200, message = "An array of sessions")
    ])
    @RequestMapping(value = "", produces = ["application/json"], method = RequestMethod.GET)
    public ResponseEntity<List<SessionDto>> sessionsGet() throws NotFoundException {
        LOGGER.info("** SessionsApi - sessionsGet");
        List<RegisteredSession> sessions = registeredSessionService.findAll();
        return new ResponseEntity<List<SessionDto>>(SessionDto.mapFromSessionsEntities(sessions), HttpStatus.OK);
    }
    
    /**
     * The sessionsPost method is used to create a new instance of a Session from the supplied SessionDto
     *
     * @param session the SessionDto to use to create the new Session object
     * @return A ResponseEntity with the newly created Session object
     * @throws InvalidDataException if there is an issue with the data provided in the RequestBody then an InvalidDataException is thrown
     */
    @ApiOperation(value = "Creates a new Session entity", notes = "A POST request to the Sessions endpoint with a Session object in the request body will create a new Session entity in the database.", response = SessionDto.class)
    @ApiResponses(value = [
        @ApiResponse(code = 201, message = "Returns a copy of the created Session entity including the sessionId that has just been created."),
        @ApiResponse(code = 400, message = "Returns an Error object stating the request body does not match the expected input.")
    ])
    @RequestMapping(value = "", produces = ["application/json"], method = RequestMethod.POST)
    public ResponseEntity<SessionDto> sessionsPost(@ApiParam(value = "The Session object to be created, without the sessionId fields", required = true) @RequestBody @Valid SessionDto session) throws NotFoundException, InvalidDataException {
        LOGGER.info("** SessionsApi - sessionsPOST");
        if (session.id != null) {
            throw new InvalidDataException(400, "No ID field should be provided when creating")
        }
        Period period
        if (session.periodId != null) {
            period = periodService.findById(session.periodId)
        }
        RegisteredSession sessionToSave = SessionDto.mapToSessionEntity(session, period)
        RegisteredSession sessionSaved = registeredSessionService.save(sessionToSave)
        return new ResponseEntity<SessionDto>(SessionDto.mapFromSessionEntity(sessionSaved), HttpStatus.CREATED);
    }
    
    /**
     * The sessionsSessionIdGet method is used to retrieve an instance of a SessionDto object as identified by the sessionId provided
     *
     * @param sessionId the session ID for the Session object retrieve
     * @return A ResponseEntity with the corresponding SessionDto object
     * @throws NotFoundException if nothing is found then the the NotFoundException is thrown.
     */
    @ApiOperation(value = "Retrieves an indivdual instance of a Session identified by the sessionId", notes = "A getGET request to the Session instance endpoint will retrieve an instance of a Session entity as identified by the sessionId provided in the URI.", response = SessionDto.class)
    @ApiResponses(value = [
        @ApiResponse(code = 200, message = "Returns a copy of the Session as identified by the sessionId"),
        @ApiResponse(code = 404, message = "Returns an Error object stating that the resource could not be found. This happens if the specified id cannot be found in the database.")
    ])
    @RequestMapping(value = "/{sessionId}", produces = ["application/json"], method = RequestMethod.GET)
    public ResponseEntity<SessionDto> sessionsSessionIdGet(@ApiParam(value = "The unique ID of the Session to retrieve", required = true) @PathVariable("sessionId") Integer sessionId) throws NotFoundException {
        LOGGER.info("** SessionsApi - sessionsSessionIdGet");
        RegisteredSession session = registeredSessionService.findById(sessionId);
        if (session == null) {
            throw new NotFoundException();
        }
        return new ResponseEntity<SessionDto>(SessionDto.mapFromSessionEntity(session), HttpStatus.OK);
    }
    
    /**
     * The sessionsSessionIdPut is used to update
     *
     * @param sessionId the session ID for the Session object to update
     * @param session the new data for the Session object
     * @return the newly updated SessionDto object
     * @throws NotFoundException if nothing is found then the the NotFoundException is thrown.
     * @throws InvalidDataException if there is an issue with the data provided in the RequestBody then an InvalidDataException is thrown
     */
    @ApiOperation(value = "Used to update a Session entity", notes = "A PUT request to the Session instance endpoint with a Session object in the request body will update an existing Session entity in the database.", response = SessionDto.class)
    @ApiResponses(value = [
        @ApiResponse(code = 200, message = "Returns a copy of the newly updated Session object."),
        @ApiResponse(code = 400, message = "Returns an Error object stating the request body does not match the expected input."),
        @ApiResponse(code = 404, message = "Returns an Error object stating that the resource could not be found. This happens if the specified id cannot be found in the database.")
    ])
    @RequestMapping(value = "/{sessionId}", produces = ["application/json"], method = RequestMethod.PUT)
    public ResponseEntity<SessionDto> sessionsSessionIdPut(
            @ApiParam(value = "The unique ID of the Session to retrieve", required = true) @PathVariable("sessionId") Integer sessionId,
            @ApiParam(value = "The Session object to be created, without the sessionId fields", required = true) @RequestBody SessionDto session) throws NotFoundException, InvalidDataException {
        LOGGER.info("** SessionsApi - sessionsPUT");
        if (sessionId != session.id) {
            throw new InvalidDataException()
        }
        Period period
        if (session.periodId != null) {
            period = periodService.findById(session.periodId)
        }
        RegisteredSession sessionToSave = SessionDto.mapToSessionEntity(session, period)
        RegisteredSession sessionSaved = registeredSessionService.save(sessionToSave)
        return new ResponseEntity<SessionDto>(SessionDto.mapFromSessionEntity(sessionSaved), HttpStatus.OK);
    }
}
