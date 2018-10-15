package uk.ac.reigate.api;

import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE

import java.util.List;

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
import org.springframework.web.bind.annotation.RequestParam

import uk.ac.reigate.domain.academic.Student
import uk.ac.reigate.domain.admissions.CollegeFundPayment
import uk.ac.reigate.dto.CollegeFundPaymentDto
import uk.ac.reigate.exceptions.InvalidDataException
import uk.ac.reigate.exceptions.NotFoundException
import uk.ac.reigate.model.SearchResult
import uk.ac.reigate.services.CollegeFundPaymentService
import uk.ac.reigate.services.StudentService


@Controller
@RequestMapping(value = "/api", produces = [ APPLICATION_JSON_VALUE ])
@Api(value = "/api", description = "the collegeFundPayments API")
public class CollegeFundPaymentsApi {
    
    private static final Logger LOGGER = Logger.getLogger(CollegeFundPaymentsApi.class);
    
    @Autowired
    private final CollegeFundPaymentService collegeFundPaymentService;
    
    @Autowired
    private final StudentService studentService;
    
    /**
     * Default NoArgs constructor
     */
    CollegeFundPaymentsApi() {}
    
    /**
     * Autowired constructor
     */
    CollegeFundPaymentsApi(CollegeFundPaymentService collegeFundPaymentService) {
        this.collegeFundPaymentService = collegeFundPaymentService;
    }
    
    /**
     * The collegeFundPaymentsGet method is used to retrieve a full list of all the CollegeFundPaymentDto objects
     *
     * @return A ResponseEntity with the corresponding list of CollegeFundPaymentDto objects
     * @throws NotFoundException if nothing is found then the the NotFoundException is thrown.
     */
    @ApiOperation(value = "Collection of CollegeFundPayment entities", notes = "A GET request to the CollegeFundPayments endpoint returns an array of all the collegeFundPayments in the system.", response = CollegeFundPaymentDto.class, responseContainer = "List")
    @ApiResponses(value = [
        @ApiResponse(code = 200, message = "An array of collegeFundPayments")
    ])
    @RequestMapping(value = "/collegeFundPayments", produces = ["application/json"], method = RequestMethod.GET)
    public ResponseEntity<List<CollegeFundPaymentDto>> collegeFundPaymentsGet(@ApiParam(value = "The ID for the student to use to filter the list of collegeFundPayment.", required = false) @RequestParam(value = "studentId", required = false) Integer studentId ) throws NotFoundException {
        LOGGER.info("** CollegeFundPaymentsApi - collegeFundPaymentsGet");
        List<CollegeFundPayment> collegeFundPayments
        if (studentId != null) {
            collegeFundPayments = collegeFundPaymentService.findCollegeFundPaymentsByStudentId(studentId);
        } else {
            collegeFundPayments = collegeFundPaymentService.findAll();
        }
        return new ResponseEntity<List<CollegeFundPaymentDto>>(CollegeFundPaymentDto.mapFromCollegeFundPaymentsEntities(collegeFundPayments), HttpStatus.OK);
    }
    
    /**
     * The collegeFundPaymentsPost method is used to create a new instance of a CollegeFundPayment from the supplied CollegeFundPaymentDto
     *
     * @param collegeFundPayment the CollegeFundPaymentDto to use to create the new CollegeFundPayment object
     * @return A ResponseEntity with the newly created CollegeFundPayment object
     * @throws InvalidDataException if there is an issue with the data provided in the RequestBody then an InvalidDataException is thrown
     */
    @ApiOperation(value = "Creates a new CollegeFundPayment entity", notes = "A POST request to the CollegeFundPayments endpoint with a CollegeFundPayment object in the request body will create a new CollegeFundPayment entity in the database.", response = CollegeFundPaymentDto.class)
    @ApiResponses(value = [
        @ApiResponse(code = 201, message = "Returns a copy of the created CollegeFundPayment entity including the collegeFundPaymentId that has just been created."),
        @ApiResponse(code = 400, message = "Returns an Error object stating the request body does not match the expected input.")
    ])
    @RequestMapping(value = "/collegeFundPayments", produces = ["application/json"], method = RequestMethod.POST)
    public ResponseEntity<CollegeFundPaymentDto> collegeFundPaymentsPost(@ApiParam(value = "The CollegeFundPayment object to be created, without the collegeFundPaymentId fields", required = true) @RequestBody @Valid CollegeFundPaymentDto collegeFundPayment) throws NotFoundException, InvalidDataException {
        LOGGER.info("** CollegeFundPaymentsApi - collegeFundPaymentsPOST");
        if (collegeFundPayment.id != null) {
            throw new InvalidDataException(400, "No ID field should be provided when creating")
        }
        Student student
        if (collegeFundPayment.studentId != null) {
            student= studentService.findById(collegeFundPayment.studentId)
        }
        CollegeFundPayment collegeFundPaymentToSave = CollegeFundPaymentDto.mapToCollegeFundPaymentEntity(collegeFundPayment, student)
        CollegeFundPayment collegeFundPaymentSaved = collegeFundPaymentService.save(collegeFundPaymentToSave)
        return new ResponseEntity<CollegeFundPaymentDto>(CollegeFundPaymentDto.mapFromCollegeFundPaymentEntity(collegeFundPaymentSaved), HttpStatus.CREATED);
    }
    
    /**
     * The collegeFundPaymentsCollegeFundPaymentIdGet method is used to retrieve an instance of a CollegeFundPaymentDto object as identified by the collegeFundPaymentId provided
     *
     * @param collegeFundPaymentId the collegeFundPayment ID for the CollegeFundPayment object retrieve
     * @return A ResponseEntity with the corresponding CollegeFundPaymentDto object
     * @throws NotFoundException if nothing is found then the the NotFoundException is thrown.
     */
    @ApiOperation(value = "Retrieves an individual instance of a CollegeFundPayment identified by the collegeFundPaymentId", notes = "A getGET request to the CollegeFundPayment instance endpoint will retrieve an instance of a CollegeFundPayment entity as identified by the collegeFundPaymentId provided in the URI.", response = CollegeFundPaymentDto.class)
    @ApiResponses(value = [
        @ApiResponse(code = 200, message = "Returns a copy of the CollegeFundPayment as identified by the collegeFundPaymentId"),
        @ApiResponse(code = 404, message = "Returns an Error object stating that the resource could not be found. This happens if the specified id cannot be found in the database.")
    ])
    @RequestMapping(value = "/collegeFundPayments/{collegeFundPaymentId}", produces = ["application/json"], method = RequestMethod.GET)
    public ResponseEntity<CollegeFundPaymentDto> collegeFundPaymentsRequestIdGet(@ApiParam(value = "The unique ID of the CollegeFundPayment to retrieve", required = true) @PathVariable("collegeFundPaymentId") Integer collegeFundPaymentId) throws NotFoundException {
        LOGGER.info("** CollegeFundPaymentsApi - collegeFundPaymentsRequestIdGet");
        CollegeFundPayment collegeFundPayment = collegeFundPaymentService.findById(collegeFundPaymentId);
        if (collegeFundPayment == null) {
            throw new NotFoundException();
        }
        return new ResponseEntity<CollegeFundPaymentDto>(CollegeFundPaymentDto.mapFromCollegeFundPaymentEntity(collegeFundPayment), HttpStatus.OK);
    }
    
    /**
     * The collegeFundPaymentsCollegeFundPaymentIdPut is used to update
     *
     * @param collegeFundPaymentId the collegeFundPayment ID for the CollegeFundPayment object to update
     * @param collegeFundPayment the new data for the CollegeFundPayment object
     * @return the newly updated CollegeFundPaymentDto object
     * @throws NotFoundException if nothing is found then the the NotFoundException is thrown.
     * @throws InvalidDataException if there is an issue with the data provided in the RequestBody then an InvalidDataException is thrown
     */
    @ApiOperation(value = "Used to update a CollegeFundPayment entity", notes = "A PUT request to the CollegeFundPayment instance endpoint with a CollegeFundPayment object in the request body will update an existing CollegeFundPayment entity in the database.", response = CollegeFundPaymentDto.class)
    @ApiResponses(value = [
        @ApiResponse(code = 200, message = "Returns a copy of the newly updated CollegeFundPayment object."),
        @ApiResponse(code = 400, message = "Returns an Error object stating the request body does not match the expected input."),
        @ApiResponse(code = 404, message = "Returns an Error object stating that the resource could not be found. This happens if the specified id cannot be found in the database.")
    ])
    @RequestMapping(value = "/collegeFundPayments/{collegeFundPaymentId}", produces = ["application/json"], method = RequestMethod.PUT)
    public ResponseEntity<CollegeFundPaymentDto> collegeFundPaymentsCollegeFundPaymentIdPut(
            @ApiParam(value = "The unique ID of the CollegeFundPayment to retrieve", required = true) @PathVariable("collegeFundPaymentId") Integer collegeFundPaymentId,
            @ApiParam(value = "The CollegeFundPayment object to be created, without the collegeFundPaymentId fields", required = true) @RequestBody CollegeFundPaymentDto collegeFundPayment) throws NotFoundException, InvalidDataException {
        LOGGER.info("** CollegeFundPaymentsApi - collegeFundPaymentsPUT");
        if (collegeFundPaymentId != collegeFundPayment.id) {
            throw new InvalidDataException()
        }
        Student student
        if (collegeFundPayment.studentId != null) {
            student= studentService.findById(collegeFundPayment.studentId)
        }
        CollegeFundPayment collegeFundPaymentToSave = CollegeFundPaymentDto.mapToCollegeFundPaymentEntity(collegeFundPayment, student)
        CollegeFundPayment collegeFundPaymentSaved = collegeFundPaymentService.save(collegeFundPaymentToSave)
        return new ResponseEntity<CollegeFundPaymentDto>(CollegeFundPaymentDto.mapFromCollegeFundPaymentEntity(collegeFundPaymentSaved), HttpStatus.OK);
    }
    
    /**
     * The studentIdGet method is used to retrieve an instance of a CollegeFundPaymentDto object as identified by the studentId provided
     *
     * @param studentId the student ID for the CollegeFundPayment object retrieve
     * @return A ResponseEntity with the corresponding List of the CollegeFundPaymentDto object
     * @throws NotFoundException if nothing is found then the the NotFoundException is thrown.
     */
    @ApiOperation(value = "Retrieves an individual instance of a CollegeFundPayment identified by the collegeFundPaymentId", notes = "A getGET request to the CollegeFundPayment instance endpoint will retrieve an instance of a CollegeFundPayment entity as identified by the collegeFundPaymentId provided in the URI.", response = CollegeFundPaymentDto.class)
    @ApiResponses(value = [
        @ApiResponse(code = 200, message = "Returns a copy of the CollegeFundPayment as identified by the collegeFundPaymentId"),
        @ApiResponse(code = 404, message = "Returns an Error object stating that the resource could not be found. This happens if the specified id cannot be found in the database.")
    ])
    @RequestMapping(value = "/students/{studentId}/collegeFundPayments", produces = ["application/json"], method = RequestMethod.GET)
    public ResponseEntity<List<CollegeFundPaymentDto>> studentIdGet(@ApiParam(value = "The unique ID of the CollegeFundPayment to retrieve", required = true) @PathVariable("studentId") Integer studentId) throws NotFoundException {
        LOGGER.info("** CollegeFundPaymentsApi - collegeFundPaymentsRequestIdGet");
        List<CollegeFundPayment>  collegeFundPayment = collegeFundPaymentService.getByStudent(studentId);
        if (collegeFundPayment == null) {
            throw new NotFoundException();
        }
        return new ResponseEntity<List<CollegeFundPaymentDto>>(CollegeFundPaymentDto.mapFromCollegeFundPaymentsEntities(collegeFundPayment), HttpStatus.OK);
    }
}
