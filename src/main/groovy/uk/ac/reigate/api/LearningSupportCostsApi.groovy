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
import uk.ac.reigate.domain.academic.Student
import uk.ac.reigate.domain.learning_support.LearningSupportCost
import uk.ac.reigate.domain.learning_support.LearningSupportCostCategory;
import uk.ac.reigate.dto.LearningSupportCostDto
import uk.ac.reigate.exceptions.InvalidDataException
import uk.ac.reigate.exceptions.NotFoundException
import uk.ac.reigate.model.SearchResult
import uk.ac.reigate.services.LearningSupportCostCategoryService
import uk.ac.reigate.services.LearningSupportCostService
import uk.ac.reigate.services.StaffService
import uk.ac.reigate.services.StudentService;


@Controller
@RequestMapping(value = "/api/", produces = [ APPLICATION_JSON_VALUE ])
@Api(value = "/api/", description = "the learningSupportCosts API")
public class LearningSupportCostsApi {
    
    private static final Logger LOGGER = Logger.getLogger(LearningSupportCostsApi.class);
    
    @Autowired
    private final LearningSupportCostService learningSupportCostService;
    
    @Autowired
    private final StudentService studentService;
    
    @Autowired
    private final LearningSupportCostCategoryService learningSupportCostCategoryService;
    
    @Autowired
    private final StaffService staffService;
    
    /**
     * Default NoArgs constructor
     */
    LearningSupportCostsApi() {}
    
    /**
     * Autowired constructor
     */
    LearningSupportCostsApi(LearningSupportCostService learningSupportCostService) {
        this.learningSupportCostService = learningSupportCostService;
    }
    
    /**
     * The learningSupportCostsGet method is used to retrieve a full list of all the LearningSupportCostDto objects
     *
     * @return A ResponseEntity with the corresponding list of LearningSupportCostDto objects
     * @throws NotFoundException if nothing is found then the the NotFoundException is thrown.
     */
    @ApiOperation(value = "Collection of LearningSupportCost entities", notes = "A GET request to the LearningSupportCosts endpoint returns an array of all the learningSupportCosts in the system.", response = LearningSupportCostDto.class, responseContainer = "List")
    @ApiResponses(value = [
        @ApiResponse(code = 200, message = "An array of learningSupportCosts")
    ])
    @RequestMapping(value = "learningSupportCosts", produces = ["application/json"], method = RequestMethod.GET)
    public ResponseEntity<List<LearningSupportCostDto>> learningSupportCostsGet() throws NotFoundException {
        LOGGER.info("** LearningSupportCostsApi - learningSupportCostsGet");
        List<LearningSupportCost> learningSupportCosts = learningSupportCostService.findAll();
        return new ResponseEntity<List<LearningSupportCostDto>>(LearningSupportCostDto.mapFromLearningSupportCostsEntities(learningSupportCosts), HttpStatus.OK);
    }
    
    @ApiOperation(value = "Retrieves an indivdual instance of a LearningSupportCost identified by the learningSupportCostId", notes = "A getGET request to the LearningSupportCost instance endpoint will retrieve an instance of a LearningSupportCost entity as identified by the learningSupportCostId provided in the URI.", response = LearningSupportCostDto.class)
    @ApiResponses(value = [
        @ApiResponse(code = 200, message = "Returns a copy of the LearningSupportCost as identified by the learningSupportCostId"),
        @ApiResponse(code = 404, message = "Returns an Error object stating that the resource could not be found. This happens if the specified id cannot be found in the database.")
    ])
    @RequestMapping(value = "students/{studentId}/learningSupportCosts", produces = ["application/json"], method = RequestMethod.GET)
    public ResponseEntity<LearningSupportCostDto> learningSupportCostsByStudentIdGet(@ApiParam(value = "The unique ID of the LearningSupportCost to retrieve", required = true)
            @PathVariable("studentId") Integer studentId) throws NotFoundException {
        LOGGER.info("** LearningSupportCostsApi - learningSupportCostsLearningSupportCostIdGet");
        List<LearningSupportCost> learningSupportCost = learningSupportCostService.findByStudentId(studentId);
        if (learningSupportCost == null) {
            throw new NotFoundException();
        }
        return new ResponseEntity<List<LearningSupportCostDto>>(LearningSupportCostDto.mapFromLearningSupportCostsEntities(learningSupportCost), HttpStatus.OK);
    }
    
    /**
     * The learningSupportCostsPost method is used to create a new instance of a LearningSupportCost from the supplied LearningSupportCostDto
     *
     * @param learningSupportCost the LearningSupportCostDto to use to create the new LearningSupportCost object
     * @return A ResponseEntity with the newly created LearningSupportCost object
     * @throws InvalidDataException if there is an issue with the data provided in the RequestBody then an InvalidDataException is thrown
     */
    @ApiOperation(value = "Creates a new LearningSupportCost entity", notes = "A POST request to the LearningSupportCosts endpoint with a LearningSupportCost object in the request body will create a new LearningSupportCost entity in the database.", response = LearningSupportCostDto.class)
    @ApiResponses(value = [
        @ApiResponse(code = 201, message = "Returns a copy of the created LearningSupportCost entity including the learningSupportCostId that has just been created."),
        @ApiResponse(code = 400, message = "Returns an Error object stating the request body does not match the expected input.")
    ])
    @RequestMapping(value = "learningSupportCosts", produces = ["application/json"], method = RequestMethod.POST)
    public ResponseEntity<LearningSupportCostDto> learningSupportCostsPost(@ApiParam(value = "The LearningSupportCost object to be created, without the learningSupportCostId fields", required = true) @RequestBody @Valid LearningSupportCostDto learningSupportCost) throws NotFoundException, InvalidDataException {
        LOGGER.info("** LearningSupportCostsApi - learningSupportCostsPOST");
        if (learningSupportCost.id != null) {
            throw new InvalidDataException(400, "No ID field should be provided when creating")
        }
        Student student
        if (learningSupportCost.studentId != null) {
            student = studentService.findById(learningSupportCost.studentId)
        }
        LearningSupportCostCategory costCategory
        if (learningSupportCost.costCategoryId != null) {
            costCategory = learningSupportCostCategoryService.findById(learningSupportCost.costCategoryId)
        }
        Staff staff
        if (learningSupportCost.staffId != null) {
            staff = staffService.findById(learningSupportCost.staffId)
        }
        LearningSupportCost learningSupportCostToSave = LearningSupportCostDto.mapToLearningSupportCostEntity(learningSupportCost, student, costCategory, staff)
        LearningSupportCost learningSupportCostSaved = learningSupportCostService.save(learningSupportCostToSave)
        return new ResponseEntity<LearningSupportCostDto>(LearningSupportCostDto.mapFromLearningSupportCostEntity(learningSupportCostSaved), HttpStatus.CREATED);
    }
    
    /**
     * The learningSupportCostsLearningSupportCostIdGet method is used to retrieve an instance of a LearningSupportCostDto object as identified by the learningSupportCostId provided
     *
     * @param learningSupportCostId the learningSupportCost ID for the LearningSupportCost object retrieve
     * @return A ResponseEntity with the corresponding LearningSupportCostDto object
     * @throws NotFoundException if nothing is found then the the NotFoundException is thrown.
     */
    @ApiOperation(value = "Retrieves an indivdual instance of a LearningSupportCost identified by the learningSupportCostId", notes = "A getGET request to the LearningSupportCost instance endpoint will retrieve an instance of a LearningSupportCost entity as identified by the learningSupportCostId provided in the URI.", response = LearningSupportCostDto.class)
    @ApiResponses(value = [
        @ApiResponse(code = 200, message = "Returns a copy of the LearningSupportCost as identified by the learningSupportCostId"),
        @ApiResponse(code = 404, message = "Returns an Error object stating that the resource could not be found. This happens if the specified id cannot be found in the database.")
    ])
    @RequestMapping(value = "learningSupportCosts/{learningSupportCostId}", produces = ["application/json"], method = RequestMethod.GET)
    public ResponseEntity<LearningSupportCostDto> learningSupportCostsLearningSupportCostIdGet(@ApiParam(value = "The unique ID of the LearningSupportCost to retrieve", required = true) @PathVariable("learningSupportCostId") Integer learningSupportCostId) throws NotFoundException {
        LOGGER.info("** LearningSupportCostsApi - learningSupportCostsLearningSupportCostIdGet");
        LearningSupportCost learningSupportCost = learningSupportCostService.findById(learningSupportCostId);
        if (learningSupportCost == null) {
            throw new NotFoundException();
        }
        return new ResponseEntity<LearningSupportCostDto>(LearningSupportCostDto.mapFromLearningSupportCostEntity(learningSupportCost), HttpStatus.OK);
    }
    
    /**
     * The learningSupportCostsLearningSupportCostIdPut is used to update
     *
     * @param learningSupportCostId the learningSupportCost ID for the LearningSupportCost object to update
     * @param learningSupportCost the new data for the LearningSupportCost object
     * @return the newly updated LearningSupportCostDto object
     * @throws NotFoundException if nothing is found then the the NotFoundException is thrown.
     * @throws InvalidDataException if there is an issue with the data provided in the RequestBody then an InvalidDataException is thrown
     */
    @ApiOperation(value = "Used to update a LearningSupportCost entity", notes = "A PUT request to the LearningSupportCost instance endpoint with a LearningSupportCost object in the request body will update an existing LearningSupportCost entity in the database.", response = LearningSupportCostDto.class)
    @ApiResponses(value = [
        @ApiResponse(code = 200, message = "Returns a copy of the newly updated LearningSupportCost object."),
        @ApiResponse(code = 400, message = "Returns an Error object stating the request body does not match the expected input."),
        @ApiResponse(code = 404, message = "Returns an Error object stating that the resource could not be found. This happens if the specified id cannot be found in the database.")
    ])
    @RequestMapping(value = "learningSupportCosts/{learningSupportCostId}", produces = ["application/json"], method = RequestMethod.PUT)
    public ResponseEntity<LearningSupportCostDto> learningSupportCostsLearningSupportCostIdPut(
            @ApiParam(value = "The unique ID of the LearningSupportCost to retrieve", required = true) @PathVariable("learningSupportCostId") Integer learningSupportCostId,
            @ApiParam(value = "The LearningSupportCost object to be created, without the learningSupportCostId fields", required = true) @RequestBody LearningSupportCostDto learningSupportCost) throws NotFoundException, InvalidDataException {
        LOGGER.info("** LearningSupportCostsApi - learningSupportCostsPUT");
        if (learningSupportCostId != learningSupportCost.id) {
            throw new InvalidDataException()
        }
        Student student
        if (learningSupportCost.studentId != null) {
            student = studentService.findById(learningSupportCost.studentId)
        }
        LearningSupportCostCategory costCategory
        if (learningSupportCost.costCategoryId != null) {
            costCategory = learningSupportCostCategoryService.findById(learningSupportCost.costCategoryId)
        }
        Staff staff
        if (learningSupportCost.staffId != null) {
            staff = staffService.findById(learningSupportCost.staffId)
        }
        LearningSupportCost learningSupportCostToSave = LearningSupportCostDto.mapToLearningSupportCostEntity(learningSupportCost, student, costCategory, staff)
        LearningSupportCost learningSupportCostSaved = learningSupportCostService.save(learningSupportCostToSave)
        return new ResponseEntity<LearningSupportCostDto>(LearningSupportCostDto.mapFromLearningSupportCostEntity(learningSupportCostSaved), HttpStatus.OK);
    }
}
