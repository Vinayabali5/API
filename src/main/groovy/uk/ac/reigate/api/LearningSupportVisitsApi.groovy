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

import uk.ac.reigate.domain.academic.Student
import uk.ac.reigate.domain.learning_support.LearningSupportVisit
import uk.ac.reigate.dto.LearningSupportVisitDto
import uk.ac.reigate.exceptions.InvalidDataException
import uk.ac.reigate.exceptions.NotFoundException
import uk.ac.reigate.model.SearchResult
import uk.ac.reigate.services.LearningSupportVisitService
import uk.ac.reigate.services.StaffService
import uk.ac.reigate.services.StudentService;


@Controller
@RequestMapping(value = "/api/", produces = [ APPLICATION_JSON_VALUE ])
@Api(value = "/api/", description = "the learningSupportVisits API")
public class LearningSupportVisitsApi {
    
    private static final Logger LOGGER = Logger.getLogger(LearningSupportVisitsApi.class);
    
    @Autowired
    private final LearningSupportVisitService learningSupportVisitService;
    
    @Autowired
    private final StudentService studentService;
    
    
    @Autowired
    private final StaffService staffService;
    
    /**
     * Default NoArgs constructor
     */
    LearningSupportVisitsApi() {}
    
    /**
     * Autowired constructor
     */
    LearningSupportVisitsApi(LearningSupportVisitService learningSupportVisitService) {
        this.learningSupportVisitService = learningSupportVisitService;
    }
    
    /**
     * The learningSupportVisitsGet method is used to retrieve a full list of all the LearningSupportVisitDto objects
     *
     * @return A ResponseEntity with the corresponding list of LearningSupportVisitDto objects
     * @throws NotFoundException if nothing is found then the the NotFoundException is thrown.
     */
    @ApiOperation(value = "Collection of LearningSupportVisit entities", notes = "A GET request to the LearningSupportVisits endpoint returns an array of all the learningSupportVisits in the system.", response = LearningSupportVisitDto.class, responseContainer = "List")
    @ApiResponses(value = [
        @ApiResponse(code = 200, message = "An array of learningSupportVisits")
    ])
    @RequestMapping(value = "learningSupportVisits", produces = ["application/json"], method = RequestMethod.GET)
    public ResponseEntity<List<LearningSupportVisitDto>> learningSupportVisitsGet() throws NotFoundException {
        LOGGER.info("** LearningSupportVisitsApi - learningSupportVisitsGet");
        List<LearningSupportVisit> learningSupportVisits = learningSupportVisitService.findAll();
        return new ResponseEntity<List<LearningSupportVisitDto>>(LearningSupportVisitDto.mapFromLearningSupportVisitsEntities(learningSupportVisits), HttpStatus.OK);
    }
    
    /**
     * The learningSupportVisitsLearningSupportVisitIdGet method is used to retrieve an instance of a LearningSupportVisitDto object as identified by the learningSupportVisitId provided
     *
     * @param learningSupportVisitId the learningSupportVisit ID for the LearningSupportVisit object retrieve
     * @return A ResponseEntity with the corresponding LearningSupportVisitDto object
     * @throws NotFoundException if nothing is found then the the NotFoundException is thrown.
     */
    @ApiOperation(value = "Retrieves an indivdual instance of a LearningSupportVisit identified by the learningSupportVisitId", notes = "A getGET request to the LearningSupportVisit instance endpoint will retrieve an instance of a LearningSupportVisit entity as identified by the learningSupportVisitId provided in the URI.", response = LearningSupportVisitDto.class)
    @ApiResponses(value = [
        @ApiResponse(code = 200, message = "Returns a copy of the LearningSupportVisit as identified by the learningSupportVisitId"),
        @ApiResponse(code = 404, message = "Returns an Error object stating that the resource could not be found. This happens if the specified id cannot be found in the database.")
    ])
    @RequestMapping(value = "students/{studentId}/learningSupportVisits", produces = ["application/json"], method = RequestMethod.GET)
    public ResponseEntity<LearningSupportVisitDto> learningSupportVisitsgetByStudentId(@ApiParam(value = "The unique ID of the LearningSupportVisit to retrieve", required = true)
            @PathVariable("studentId") Integer studentId) throws NotFoundException {
        LOGGER.info("** LearningSupportVisitsApi - learningSupportVisitsLearningSupportVisitIdGet");
        List<LearningSupportVisit> learningSupportVisit = learningSupportVisitService.findByStudentId(studentId);
        if (learningSupportVisit == null) {
            throw new NotFoundException();
        }
        return new ResponseEntity<List<LearningSupportVisitDto>>(LearningSupportVisitDto.mapFromLearningSupportVisitsEntities(learningSupportVisit), HttpStatus.OK);
    }
    
    /**
     * The learningSupportVisitsPost method is used to create a new instance of a LearningSupportVisit from the supplied LearningSupportVisitDto
     *
     * @param learningSupportVisit the LearningSupportVisitDto to use to create the new LearningSupportVisit object
     * @return A ResponseEntity with the newly created LearningSupportVisit object
     * @throws InvalidDataException if there is an issue with the data provided in the RequestBody then an InvalidDataException is thrown
     */
    @ApiOperation(value = "Creates a new LearningSupportVisit entity", notes = "A POST request to the LearningSupportVisits endpoint with a LearningSupportVisit object in the request body will create a new LearningSupportVisit entity in the database.", response = LearningSupportVisitDto.class)
    @ApiResponses(value = [
        @ApiResponse(code = 201, message = "Returns a copy of the created LearningSupportVisit entity including the learningSupportVisitId that has just been created."),
        @ApiResponse(code = 400, message = "Returns an Error object stating the request body does not match the expected input.")
    ])
    @RequestMapping(value = "learningSupportVisits", produces = ["application/json"], method = RequestMethod.POST)
    public ResponseEntity<LearningSupportVisitDto> learningSupportVisitsPost(@ApiParam(value = "The LearningSupportVisit object to be created, without the learningSupportVisitId fields", required = true) @RequestBody @Valid LearningSupportVisitDto learningSupportVisit) throws NotFoundException, InvalidDataException {
        LOGGER.info("** LearningSupportVisitsApi - learningSupportVisitsPOST");
        if (learningSupportVisit.id != null) {
            throw new InvalidDataException(400, "No ID field should be provided when creating")
        }
        Student student
        if (learningSupportVisit.studentId != null) {
            student = studentService.findById(learningSupportVisit.studentId)
        }
        LearningSupportVisit learningSupportVisitToSave = LearningSupportVisitDto.mapToLearningSupportVisitEntity(learningSupportVisit, student)
        LearningSupportVisit learningSupportVisitSaved = learningSupportVisitService.save(learningSupportVisitToSave)
        return new ResponseEntity<LearningSupportVisitDto>(LearningSupportVisitDto.mapFromLearningSupportVisitEntity(learningSupportVisitSaved), HttpStatus.CREATED);
    }
    
    /**
     * The learningSupportVisitsLearningSupportVisitIdGet method is used to retrieve an instance of a LearningSupportVisitDto object as identified by the learningSupportVisitId provided
     *
     * @param learningSupportVisitId the learningSupportVisit ID for the LearningSupportVisit object retrieve
     * @return A ResponseEntity with the corresponding LearningSupportVisitDto object
     * @throws NotFoundException if nothing is found then the the NotFoundException is thrown.
     */
    @ApiOperation(value = "Retrieves an indivdual instance of a LearningSupportVisit identified by the learningSupportVisitId", notes = "A getGET request to the LearningSupportVisit instance endpoint will retrieve an instance of a LearningSupportVisit entity as identified by the learningSupportVisitId provided in the URI.", response = LearningSupportVisitDto.class)
    @ApiResponses(value = [
        @ApiResponse(code = 200, message = "Returns a copy of the LearningSupportVisit as identified by the learningSupportVisitId"),
        @ApiResponse(code = 404, message = "Returns an Error object stating that the resource could not be found. This happens if the specified id cannot be found in the database.")
    ])
    @RequestMapping(value = "learningSupportVisits/{learningSupportVisitId}", produces = ["application/json"], method = RequestMethod.GET)
    public ResponseEntity<LearningSupportVisitDto> learningSupportVisitsLearningSupportVisitIdGet(@ApiParam(value = "The unique ID of the LearningSupportVisit to retrieve", required = true) @PathVariable("learningSupportVisitId") Integer learningSupportVisitId) throws NotFoundException {
        LOGGER.info("** LearningSupportVisitsApi - learningSupportVisitsLearningSupportVisitIdGet");
        LearningSupportVisit learningSupportVisit = learningSupportVisitService.findById(learningSupportVisitId);
        if (learningSupportVisit == null) {
            throw new NotFoundException();
        }
        return new ResponseEntity<LearningSupportVisitDto>(LearningSupportVisitDto.mapFromLearningSupportVisitEntity(learningSupportVisit), HttpStatus.OK);
    }
    
    /**
     * The learningSupportVisitsLearningSupportVisitIdPut is used to update
     *
     * @param learningSupportVisitId the learningSupportVisit ID for the LearningSupportVisit object to update
     * @param learningSupportVisit the new data for the LearningSupportVisit object
     * @return the newly updated LearningSupportVisitDto object
     * @throws NotFoundException if nothing is found then the the NotFoundException is thrown.
     * @throws InvalidDataException if there is an issue with the data provided in the RequestBody then an InvalidDataException is thrown
     */
    @ApiOperation(value = "Used to update a LearningSupportVisit entity", notes = "A PUT request to the LearningSupportVisit instance endpoint with a LearningSupportVisit object in the request body will update an existing LearningSupportVisit entity in the database.", response = LearningSupportVisitDto.class)
    @ApiResponses(value = [
        @ApiResponse(code = 200, message = "Returns a copy of the newly updated LearningSupportVisit object."),
        @ApiResponse(code = 400, message = "Returns an Error object stating the request body does not match the expected input."),
        @ApiResponse(code = 404, message = "Returns an Error object stating that the resource could not be found. This happens if the specified id cannot be found in the database.")
    ])
    @RequestMapping(value = "learningSupportVisits/{learningSupportVisitId}", produces = ["application/json"], method = RequestMethod.PUT)
    public ResponseEntity<LearningSupportVisitDto> learningSupportVisitsLearningSupportVisitIdPut(
            @ApiParam(value = "The unique ID of the LearningSupportVisit to retrieve", required = true) @PathVariable("learningSupportVisitId") Integer learningSupportVisitId,
            @ApiParam(value = "The LearningSupportVisit object to be created, without the learningSupportVisitId fields", required = true) @RequestBody LearningSupportVisitDto learningSupportVisit) throws NotFoundException, InvalidDataException {
        LOGGER.info("** LearningSupportVisitsApi - learningSupportVisitsPUT");
        if (learningSupportVisitId != learningSupportVisit.id) {
            throw new InvalidDataException()
        }
        Student student
        if (learningSupportVisit.studentId != null) {
            student = studentService.findById(learningSupportVisit.studentId)
        }
        LearningSupportVisit learningSupportVisitToSave = LearningSupportVisitDto.mapToLearningSupportVisitEntity(learningSupportVisit, student)
        LearningSupportVisit learningSupportVisitSaved = learningSupportVisitService.save(learningSupportVisitToSave)
        return new ResponseEntity<LearningSupportVisitDto>(LearningSupportVisitDto.mapFromLearningSupportVisitEntity(learningSupportVisitSaved), HttpStatus.OK);
    }
}
