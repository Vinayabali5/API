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

import uk.ac.reigate.domain.academic.AcademicYear
import uk.ac.reigate.domain.academic.Student
import uk.ac.reigate.domain.admissions.Request
import uk.ac.reigate.dto.CourseDto;
import uk.ac.reigate.dto.RequestDto
import uk.ac.reigate.exceptions.InvalidDataException
import uk.ac.reigate.exceptions.NoSearchResultsFoundException;
import uk.ac.reigate.exceptions.NotFoundException
import uk.ac.reigate.model.SearchResult
import uk.ac.reigate.services.AcademicYearService
import uk.ac.reigate.services.RequestService
import uk.ac.reigate.services.StudentService


@Controller
@RequestMapping(value = "/api/requests", produces = [ APPLICATION_JSON_VALUE ])
@Api(value = "/api/requests", description = "the requests API")
public class RequestsApi {
    
    private static final Logger LOGGER = Logger.getLogger(RequestsApi.class);
    
    @Autowired
    private final RequestService requestService;
    
    @Autowired
    private final StudentService studentService;
    
    @Autowired
    private final AcademicYearService academicYearService;
    
    /**
     * Default NoArgs constructor
     */
    RequestsApi() {}
    
    /**
     * Autowired constructor
     */
    RequestsApi(RequestService requestService) {
        this.requestService = requestService;
    }
    
    /**
     * The RequestGetByYear method is used to retrieve a list by academic year
     * @throws NoSearchResultsFoundException if nothing is found then the the NoSearchResultsFoundException is thrown.
     */
    @ApiOperation(value = "Retrieves an indivdual instance of a Request identified by the academicYearId", notes = "A GET request to the Request instance endpoint will retrieve an instance of a Request entity as identified by the requestId provided in the URI.", response = RequestDto.class)
    @ApiResponses(value = [
        @ApiResponse(code = 200, message = "Returns a copy of the Request as identified by the Academic Year"),
        @ApiResponse(code = 404, message = "Returns an Error object stating that the resource could not be found. This happens if the specified id cannot be found in the database.")
    ])
    @RequestMapping(value = "/search", produces = ["application/json"], method = RequestMethod.GET)
    ResponseEntity<List<RequestDto>> searchRequest(
            @RequestParam(value = "studentId", required = false) Integer studentId,
            @RequestParam(value = "yearId", required = false) Integer academicYearId
    ) throws NoSearchResultsFoundException {
        LOGGER.info("** RequestsApi - requestsGetByAcademicYear");
        //        AcademicYear academicYear
        //        if (year) {
        //                LOGGER.info("RequestApi Searching for Year Code: " + year);
        //            academicYear = academicYearService.findByCode(year)
        //        }
        //        if (academicYear == null) {
        //            LOGGER.info("RequestApi No Year Found or Supplied - Using default")
        //            academicYear = academicYearService.getCurrentAcademicYear()
        //        }
        List<Request> requestList = requestService.searchByYearAndStudentId(academicYearId, studentId)
        
        if (requestList.size() != 0) {
            LOGGER.info("II Results found: " + requestList.size())
            List<RequestDto> requestSearchResults = new ArrayList<RequestDto>()
            return new ResponseEntity<List<RequestDto>>(RequestDto.mapFromRequestsEntities(requestService.searchByYearAndStudentId(academicYearId, studentId)), HttpStatus.OK)
        }
    }
    
    /**
     * The requestsGet method is used to retrieve a full list of all the RequestDto objects
     *
     * @return A ResponseEntity with the corresponding list of RequestDto objects
     * @throws NotFoundException if nothing is found then the the NotFoundException is thrown.
     */
    @ApiOperation(value = "Collection of Request entities", notes = "A GET request to the Requests endpoint returns an array of all the requests in the system.", response = RequestDto.class, responseContainer = "List")
    @ApiResponses(value = [
        @ApiResponse(code = 200, message = "An array of requests")
    ])
    @RequestMapping(value = "", produces = ["application/json"], method = RequestMethod.GET)
    public ResponseEntity<List<RequestDto>> requestsGet(@ApiParam(value = "The ID for the student to use to filter the list of request.", required = false) @RequestParam(value = "studentId", required = false) Integer studentId ) throws NotFoundException {
        LOGGER.info("** RequestsApi - requestsGet");
        List<Request> requests
        if (studentId != null) {
            requests = requestService.findRequestsByStudentId(studentId);
        } else {
            requests = requestService.findAll();
        }
        return new ResponseEntity<List<RequestDto>>(RequestDto.mapFromRequestsEntities(requests), HttpStatus.OK);
    }
    
    /**
     * The requestsPost method is used to create a new instance of a Request from the supplied RequestDto
     *
     * @param request the RequestDto to use to create the new Request object
     * @return A ResponseEntity with the newly created Request object
     * @throws InvalidDataException if there is an issue with the data provided in the RequestBody then an InvalidDataException is thrown
     */
    @ApiOperation(value = "Creates a new Request entity", notes = "A POST request to the Requests endpoint with a Request object in the request body will create a new Request entity in the database.", response = RequestDto.class)
    @ApiResponses(value = [
        @ApiResponse(code = 201, message = "Returns a copy of the created Request entity including the requestId that has just been created."),
        @ApiResponse(code = 400, message = "Returns an Error object stating the request body does not match the expected input.")
    ])
    @RequestMapping(value = "", produces = ["application/json"], method = RequestMethod.POST)
    public ResponseEntity<RequestDto> requestsPost(@ApiParam(value = "The Request object to be created, without the requestId fields", required = true) @RequestBody @Valid RequestDto request) throws NotFoundException, InvalidDataException {
        LOGGER.info("** RequestsApi - requestsPOST");
        if (request.id != null) {
            throw new InvalidDataException(400, "No ID field should be provided when creating")
        }
        Student student
        if (request.studentId != null) {
            student = studentService.findById(request.studentId)
        }
        AcademicYear academicYear
        if (request.academicYearId != null) {
            academicYear = academicYearService.findById(request.academicYearId)
        }
        Request requestToSave = RequestDto.mapToRequestEntity(request, student, academicYear)
        Request requestSaved = requestService.save(requestToSave)
        return new ResponseEntity<RequestDto>(RequestDto.mapFromRequestEntity(requestSaved), HttpStatus.CREATED);
    }
    
    /**
     * The requestsRequestIdGet method is used to retrieve an instance of a RequestDto object as identified by the requestId provided
     *
     * @param requestId the request ID for the Request object retrieve
     * @return A ResponseEntity with the corresponding RequestDto object
     * @throws NotFoundException if nothing is found then the the NotFoundException is thrown.
     */
    @ApiOperation(value = "Retrieves an indivdual instance of a Request identified by the requestId", notes = "A getGET request to the Request instance endpoint will retrieve an instance of a Request entity as identified by the requestId provided in the URI.", response = RequestDto.class)
    @ApiResponses(value = [
        @ApiResponse(code = 200, message = "Returns a copy of the Request as identified by the requestId"),
        @ApiResponse(code = 404, message = "Returns an Error object stating that the resource could not be found. This happens if the specified id cannot be found in the database.")
    ])
    @RequestMapping(value = "/{requestId}", produces = ["application/json"], method = RequestMethod.GET)
    public ResponseEntity<RequestDto> requestsRequestIdGet(@ApiParam(value = "The unique ID of the Request to retrieve", required = true) @PathVariable("requestId") Integer requestId) throws NotFoundException {
        LOGGER.info("** RequestsApi - requestsRequestIdGet");
        Request request = requestService.findById(requestId);
        if (request == null) {
            throw new NotFoundException();
        }
        return new ResponseEntity<RequestDto>(RequestDto.mapFromRequestEntity(request), HttpStatus.OK);
    }
    
    /**
     * The requestsRequestIdPut is used to update
     *
     * @param requestId the request ID for the Request object to update
     * @param request the new data for the Request object
     * @return the newly updated RequestDto object
     * @throws NotFoundException if nothing is found then the the NotFoundException is thrown.
     * @throws InvalidDataException if there is an issue with the data provided in the RequestBody then an InvalidDataException is thrown
     */
    @ApiOperation(value = "Used to update a Request entity", notes = "A PUT request to the Request instance endpoint with a Request object in the request body will update an existing Request entity in the database.", response = RequestDto.class)
    @ApiResponses(value = [
        @ApiResponse(code = 200, message = "Returns a copy of the newly updated Request object."),
        @ApiResponse(code = 400, message = "Returns an Error object stating the request body does not match the expected input."),
        @ApiResponse(code = 404, message = "Returns an Error object stating that the resource could not be found. This happens if the specified id cannot be found in the database.")
    ])
    @RequestMapping(value = "/{requestId}", produces = ["application/json"], method = RequestMethod.PUT)
    public ResponseEntity<RequestDto> requestsRequestIdPut(
            @ApiParam(value = "The unique ID of the Request to retrieve", required = true) @PathVariable("requestId") Integer requestId,
            @ApiParam(value = "The Request object to be created, without the requestId fields", required = true) @RequestBody RequestDto request) throws NotFoundException, InvalidDataException {
        LOGGER.info("** RequestsApi - requestsPUT");
        if (requestId != request.id) {
            throw new InvalidDataException()
        }
        Student student
        if (request.studentId != null) {
            student = studentService.findById(request.studentId)
        }
        AcademicYear academicYear
        if (request.academicYearId != null) {
            academicYear = academicYearService.findById(request.academicYearId)
        }
        Request requestToSave = RequestDto.mapToRequestEntity(request, student, academicYear)
        Request requestSaved = requestService.save(requestToSave)
        return new ResponseEntity<RequestDto>(RequestDto.mapFromRequestEntity(requestSaved), HttpStatus.OK);
    }
}
