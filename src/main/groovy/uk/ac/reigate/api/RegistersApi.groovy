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

import uk.ac.reigate.domain.Staff
import uk.ac.reigate.domain.academic.CourseGroup
import uk.ac.reigate.domain.register.Register
import uk.ac.reigate.domain.register.RegisteredSession;
import uk.ac.reigate.dto.RegisterDto
import uk.ac.reigate.exceptions.InvalidDataException
import uk.ac.reigate.exceptions.NotFoundException
import uk.ac.reigate.model.SearchResult
import uk.ac.reigate.services.CourseGroupService
import uk.ac.reigate.services.RegisterService
import uk.ac.reigate.services.RegisteredSessionService
import uk.ac.reigate.services.StaffService


@Controller
@RequestMapping(value = "/api/registers", produces = [ APPLICATION_JSON_VALUE ])
@Api(value = "/api/registers", description = "the registers API")
public class RegistersApi {
    
    private static final Logger LOGGER = Logger.getLogger(RegistersApi.class);
    
    @Autowired
    private final RegisterService registerService;
    
    @Autowired
    private final RegisteredSessionService registeredSessionService;
    
    @Autowired
    private final CourseGroupService courseGroupService;
    
    @Autowired
    private final StaffService staffService;
    
    /**
     * Default NoArgs constructor
     */
    RegistersApi() {}
    
    /**
     * Autowired constructor
     */
    RegistersApi(RegisterService registerService) {
        this.registerService = registerService;
    }
    
    /**
     * The registersGet method is used to retrieve a full list of all the RegisterDto objects
     *
     * @return A ResponseEntity with the corresponding list of RegisterDto objects
     * @throws NotFoundException if nothing is found then the the NotFoundException is thrown.
     */
    @ApiOperation(value = "Collection of Register entities", notes = "A GET request to the Registers endpoint returns an array of all the registers in the system.", response = RegisterDto.class, responseContainer = "List")
    @ApiResponses(value = [
        @ApiResponse(code = 200, message = "An array of registers")
    ])
    @RequestMapping(value = "", produces = ["application/json"], method = RequestMethod.GET)
    public ResponseEntity<List<RegisterDto>> registersGet() throws NotFoundException {
        LOGGER.info("** RegistersApi - registersGet");
        List<Register> registers = registerService.findAll();
        return new ResponseEntity<List<RegisterDto>>(RegisterDto.mapFromRegistersEntities(registers), HttpStatus.OK);
    }
    
    /**
     * The registersPost method is used to create a new instance of a Register from the supplied RegisterDto
     *
     * @param register the RegisterDto to use to create the new Register object
     * @return A ResponseEntity with the newly created Register object
     * @throws InvalidDataException if there is an issue with the data provided in the RequestBody then an InvalidDataException is thrown
     */
    @ApiOperation(value = "Creates a new Register entity", notes = "A POST request to the Registers endpoint with a Register object in the request body will create a new Register entity in the database.", response = RegisterDto.class)
    @ApiResponses(value = [
        @ApiResponse(code = 201, message = "Returns a copy of the created Register entity including the registerId that has just been created."),
        @ApiResponse(code = 400, message = "Returns an Error object stating the request body does not match the expected input.")
    ])
    @RequestMapping(value = "", produces = ["application/json"], method = RequestMethod.POST)
    public ResponseEntity<RegisterDto> registersPost(@ApiParam(value = "The Register object to be created, without the registerId fields", required = true) @RequestBody @Valid RegisterDto register) throws NotFoundException, InvalidDataException {
        LOGGER.info("** RegistersApi - registersPOST");
        if (register.id != null) {
            throw new InvalidDataException(400, "No ID field should be provided when creating")
        }
        RegisteredSession session
        if(register.sessionId != null) {
            session = registeredSessionService.findById(register.sessionId)
        }
        CourseGroup courseGroup
        if(register.courseGroupId != null) {
            courseGroup = courseGroupService.findById(register.courseGroupId)
        }
        Staff staffCompleted
        if(register.staffCompletedId != null) {
            staffCompleted = staffService.findById(register.staffCompletedId)
        }
        Register registerToSave = RegisterDto.mapToRegisterEntity(register, session, courseGroup, staffCompleted)
        Register registerSaved = registerService.save(registerToSave)
        return new ResponseEntity<RegisterDto>(RegisterDto.mapFromRegisterEntity(registerSaved), HttpStatus.CREATED);
    }
    
    /**
     * The registersRegisterIdGet method is used to retrieve an instance of a RegisterDto object as identified by the registerId provided
     *
     * @param registerId the register ID for the Register object retrieve
     * @return A ResponseEntity with the corresponding RegisterDto object
     * @throws NotFoundException if nothing is found then the the NotFoundException is thrown.
     */
    @ApiOperation(value = "Retrieves an indivdual instance of a Register identified by the registerId", notes = "A getGET request to the Register instance endpoint will retrieve an instance of a Register entity as identified by the registerId provided in the URI.", response = RegisterDto.class)
    @ApiResponses(value = [
        @ApiResponse(code = 200, message = "Returns a copy of the Register as identified by the registerId"),
        @ApiResponse(code = 404, message = "Returns an Error object stating that the resource could not be found. This happens if the specified id cannot be found in the database.")
    ])
    @RequestMapping(value = "/{registerId}", produces = ["application/json"], method = RequestMethod.GET)
    public ResponseEntity<RegisterDto> registersRegisterIdGet(@ApiParam(value = "The unique ID of the Register to retrieve", required = true) @PathVariable("registerId") Integer registerId) throws NotFoundException {
        LOGGER.info("** RegistersApi - registersRegisterIdGet");
        Register register = registerService.findById(registerId);
        if (register == null) {
            throw new NotFoundException();
        }
        return new ResponseEntity<RegisterDto>(RegisterDto.mapFromRegisterEntity(register), HttpStatus.OK);
    }
    
    /**
     * The registersRegisterIdPut is used to update
     *
     * @param registerId the register ID for the Register object to update
     * @param register the new data for the Register object
     * @return the newly updated RegisterDto object
     * @throws NotFoundException if nothing is found then the the NotFoundException is thrown.
     * @throws InvalidDataException if there is an issue with the data provided in the RequestBody then an InvalidDataException is thrown
     */
    @ApiOperation(value = "Used to update a Register entity", notes = "A PUT request to the Register instance endpoint with a Register object in the request body will update an existing Register entity in the database.", response = RegisterDto.class)
    @ApiResponses(value = [
        @ApiResponse(code = 200, message = "Returns a copy of the newly updated Register object."),
        @ApiResponse(code = 400, message = "Returns an Error object stating the request body does not match the expected input."),
        @ApiResponse(code = 404, message = "Returns an Error object stating that the resource could not be found. This happens if the specified id cannot be found in the database.")
    ])
    @RequestMapping(value = "/{registerId}", produces = ["application/json"], method = RequestMethod.PUT)
    public ResponseEntity<RegisterDto> registersRegisterIdPut(
            @ApiParam(value = "The unique ID of the Register to retrieve", required = true) @PathVariable("registerId") Integer registerId,
            @ApiParam(value = "The Register object to be created, without the registerId fields", required = true) @RequestBody RegisterDto register) throws NotFoundException, InvalidDataException {
        LOGGER.info("** RegistersApi - registersPUT");
        if (registerId != register.id) {
            throw new InvalidDataException()
        }
        RegisteredSession session
        if(register.sessionId != null) {
            session = registeredSessionService.findById(register.sessionId)
        }
        CourseGroup courseGroup
        if(register.courseGroupId != null) {
            courseGroup = courseGroupService.findById(register.courseGroupId)
        }
        Staff staffCompleted
        if(register.staffCompletedId != null) {
            staffCompleted = staffService.findById(register.staffCompletedId)
        }
        Register registerToSave = RegisterDto.mapToRegisterEntity(register, session, courseGroup, staffCompleted)
        Register registerSaved = registerService.save(registerToSave)
        return new ResponseEntity<RegisterDto>(RegisterDto.mapFromRegisterEntity(registerSaved), HttpStatus.OK);
    }
}
