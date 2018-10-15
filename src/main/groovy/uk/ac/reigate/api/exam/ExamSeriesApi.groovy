package uk.ac.reigate.api.exam

import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE
import io.swagger.annotations.Api
import io.swagger.annotations.ApiImplicitParam
import io.swagger.annotations.ApiImplicitParams
import io.swagger.annotations.ApiOperation
import io.swagger.annotations.ApiParam
import io.swagger.annotations.ApiResponse
import io.swagger.annotations.ApiResponses

import javax.validation.Valid

import org.apache.log4j.Logger
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.data.domain.Pageable
import org.springframework.http.HttpHeaders
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.security.access.annotation.Secured
import org.springframework.stereotype.Controller
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestMethod
import org.springframework.web.bind.annotation.RequestParam

import springfox.documentation.annotations.ApiIgnore
import uk.ac.reigate.domain.academic.AcademicYear
import uk.ac.reigate.domain.exam.ExamSeries
import uk.ac.reigate.dto.exam.ExamSeriesDto
import uk.ac.reigate.exceptions.InvalidDataException
import uk.ac.reigate.exceptions.NotFoundException
import uk.ac.reigate.model.SearchResult
import uk.ac.reigate.services.AcademicYearService
import uk.ac.reigate.services.exam.ExamSeriesService

@Controller
@RequestMapping(value = '/api/exam-series', produces = [APPLICATION_JSON_VALUE])
@Api(value = "/api/ExamSeriesApi", description = "The Exam ExamSeries Resource API")
public class ExamSeriesApi {
    
    private final static Logger LOGGER = Logger.getLogger(ExamSeriesApi.class)
    
    @Autowired
    ExamSeriesService examSeriesService
    
    @Autowired
    AcademicYearService academicYearService
    
    /**
     * Default No Args constructor    
     */
    ExamSeriesApi() {}
    
    /**
     * Default Autowired constructor
     */
    ExamSeriesApi(ExamSeriesService examSeriesService) {
        this.examSeriesService = examSeriesService;
    }
    
    /**
     * The examSeriesGet method is used to retrieve a (non paged) collection of examSeries entities of the examSeriesDto objects
     * 
     * @param examBoardId ID of the examBoard to retrieve results for
     * @return A ResponseEntity with the corresponding list of examSeriesDto objects
     * @throws NotFoundException If nothing is found then the NotFoundException is thrown
     */
    @ApiOperation(value = "Collection of All examSeries entities",
    notes = "A GET request to the examSeries endpoint returns an array of ALL the examSeries in the system",
    response = ExamSeriesDto.class, responseContainer = "List")
    @ApiResponses(value = [
        @ApiResponse(code = 200, message = "An array of examSeries")
    ])
    @RequestMapping(value = "", produces = ["application/json"], method = RequestMethod.GET)
    public ResponseEntity<List<ExamSeriesDto>> examSeriesGetNonPageable(
            @ApiParam(value = "exam Board Id", name = "examBoardId", required = false)
            @RequestParam(value = "examBoardId", required = false) Integer examBoardId,
            @ApiParam(value ="yearId", name = "yearId", required=false)
            @RequestParam(value = "yearId", required= false) Integer yearId
    ) throws NotFoundException {
        LOGGER.info("** ExamBoardApi - examSeriesGetNonPageable");
        List<ExamSeries> examSeries;
        examSeries = examSeriesService.findExamSeriesList(examBoardId, yearId);
        return new ResponseEntity<List<ExamSeriesDto>>(ExamSeriesDto.mapFromExamSeriesEntities(examSeries), HttpStatus.OK);
    }
    
    /**
     * The examSeriesGet method is used to retrieve a collection of examSeries entities of the examSeriesDto objects
     * 
     * @param examBoardId ID of the examBoard to retrieve results for
     * @param page Results page to retrieve results for
     * @param size The number of records per page
     * @param sort Sorting criteria in the format ASC/DESC
     * @return A ResponseEntity with the corresponding list of examSeriesDto objects
     * @throws NotFoundException If nothing is found then the NotFoundException is thrown
     */
    @ApiOperation(value = "Collection of ExamSeries entities",
    notes = "A GET request to the ExamSeries endpoint returns an array of all the current examSeries in the system.",
    response = ExamSeriesDto.class, responseContainer = "List")
    @ApiImplicitParams([
        @ApiImplicitParam(name = "page", dataType = "Integer", paramType = "query", value = "Results page you want to retrieve (0..N)"),
        @ApiImplicitParam(name = "size", dataType = "Integer", paramType = "query", value = "Number of records per page."),
        @ApiImplicitParam(name = "sort", allowMultiple = true, dataType = "string", paramType = "query",
        value = "Sorting criteria in the format: property(,asc|desc). Default sort order is ascending. Multiple sort criteria are supported.")
    ])
    @ApiResponses(value = [
        @ApiResponse(code = 200, message = "An array of examSeries")
    ])
    @RequestMapping(value = "/paged", produces = ["application/json"], method = RequestMethod.GET)
    public ResponseEntity<List<ExamSeriesDto>> examSeriesGetPageable(
            @ApiParam(value = "exam Board Id", name = "examBoardId", required = false)
            @RequestParam(value = "examBoardId", required = false) Optional<Integer> examBoardId,
            @ApiParam(value = "Pageable parameters (page, size, sort).", required = false) @ApiIgnore Pageable pageable
    ) throws NotFoundException {
        LOGGER.info("** ExamSeriesApi - examSeriesGetPageable");
        if (examBoardId != null) LOGGER.info("    examBoardId = " + examBoardId);
        LOGGER.info("    pageable: " + pageable);
        SearchResult<ExamSeries> examSeries;
        examSeries = examSeriesService.findExamSeriesListPageable(pageable, examBoardId);
        
        HttpHeaders responseHeaders = new HttpHeaders();
        responseHeaders.set("x-current-page", (String)examSeries.pageInfo.page);
        responseHeaders.set("x-total-pages", (String)examSeries.pageInfo.totalPages.value);
        responseHeaders.set("x-total-items", (String)examSeries.pageInfo.totalResults.value);
        
        return new ResponseEntity<List<ExamSeriesDto>>(ExamSeriesDto.mapFromExamSeriesEntities(examSeries.getResult()), responseHeaders, HttpStatus.OK);
    }
    
    /**
     * The examSeriesExamSeriesGet method is used to retrieve an instance of an ExamSeriesDto object as identified by the examSeriesId provided
     * 
     * @param examSeriesId The examSeriesId for the ExamSeriesDto object
     * @returns A ResponseEntity with the corresponding ExamSeriesDto object
     * @throws NotFoundException If nothing is found then the NotFoundException is thrown
     */
    @ApiOperation(value = "Retrieves an individual instance of an ExamSeries identified by the examSeriesId",
    notes = "A GET request to the ExamSeries instance endpoint will retrieve an instance of an ExamSeries entity as identified by the examSeriesId provided in the URI.",
    response = ExamSeriesDto.class)
    @ApiResponses(value = [
        @ApiResponse(code = 200, message = "Returns a copy of the ExamSeries as identified by the examSeriesId"),
        @ApiResponse(code = 404, message = "Returns an Error object stating that the resource could not be found. This happens if the specified ID cannot be found in the database.")
    ])
    @RequestMapping(value = "/{examSeriesId}", produces = ["application/json"], method = RequestMethod.GET)
    public ResponseEntity<ExamSeriesDto> examSeriesExamSeriesIdGet(
            @ApiParam(value = "This unique ID of the ExamSeries to retrieve", required = true) @PathVariable("examSeriesId") Integer examSeriesId
    ) throws NotFoundException {
        LOGGER.info("** ExamSeriesApi - examSeriesExamSeriesIdGet");
        ExamSeries examSeries = examSeriesService.findById(examSeriesId);
        if (examSeries == null) {
            throw new NotFoundException();
        }
        return new ResponseEntity<ExamSeriesDto>(ExamSeriesDto.mapFromExamSeriesEntity(examSeries), HttpStatus.OK);
    }
    
    
    /**
     * The examSeriesPost method is used to create a new instance of an ExamSeries object from the supplied ExamSeriesDto entity
     *
     *  @param examSeries The examSeriesDto to use to create the new ExamSeries object
     *  @return A ResponseEntity with the newly created ExamSeriesDto object
     *  @throws InvalidDataException If there is an issue with the data provided in the RequesetBody then an InvalidDataException is thrown
     */
    @ApiOperation(value = "Creates a new ExamSeries entity",
    notes = "A POST request to the ExamSeries endpoint with an ExamSeries object in the request body will create a new ExamSeries entity in the database.",
    response = ExamSeriesDto.class)
    @ApiResponses(value = [
        @ApiResponse(code = 201, message = "Returns a copy of the created ExamSeries entity including the examSeriesId that has just been created."),
        @ApiResponse(code = 400, message = "Returns an Error object stating that the request body does not match the expected input.")
    ])
    @RequestMapping(value = "", produces = ["application/json"], method = RequestMethod.POST)
    public ResponseEntity<ExamSeriesDto> examSeriesPost(
            @ApiParam(value = "The examSeries object to be created, without the examSeriesId field", required = true) @RequestBody @Valid ExamSeriesDto examSeries
    ) throws NotFoundException, InvalidDataException {
        LOGGER.info("** ExamSeriesApi - examSeriesPost");
        AcademicYear academicYear
        if (examSeries.id != null) {
            throw new InvalidDataException(400, "No ID field should be provided when created");
        }
        if(examSeries.academicYearId!=null){
            academicYear =  academicYearService.findById(examSeries.academicYearId)
        }else{
            throw new InvalidDataException("Provide Academic Year details")
        }
        ExamSeries toSave = ExamSeriesDto.mapToExamSeriesEntity(examSeries,academicYear);
        ExamSeries savedExamSeries = examSeriesService.saveExamSeries(toSave);
        return new ResponseEntity<ExamSeriesDto>(ExamSeriesDto.mapFromExamSeriesEntity(savedExamSeries), HttpStatus.CREATED);
    }
    
    /**
     * The examSeriesExamSeriesIdPut is used to update an instance of a ExamSeriesDto as identified by the examSeriesId provided
     *
     * @param examSeriesId The examSeries ID for the ExamSeries object to update
     * @param examSeries The new data for the Syllabus object
     * @return The newly updated ExamSeriesDto object
     * @throws InvalidDataException if there is an issue with the data provided in the RequestBody then an InvalidDataException is thrown
     */
    @ApiOperation(value = "Used to update a ExamSeries entity",
    notes = "A PUT request to the ExamSeries instance endpoint with a ExamSeriesDto object in the request body will update an existing ExamSeries entity in the database",
    response = ExamSeriesDto.class)
    @ApiResponses(value = [
        @ApiResponse(code = 200, message = "Returns a copy of the newly updated ExamSeriesDto object"),
        @ApiResponse(code = 400, message = "Returns an Error object stating that the request body does not match the expected input."),
        @ApiResponse(code = 404, message = "Returns an Error object stating that the resource could not be found. This happens is the specified ID cannot be found in the database.")
    ])
    @RequestMapping(value = "/{examSeriesId}", produces = ["application/json"], method = RequestMethod.PUT)
    public ResponseEntity<ExamSeriesDto> examSeriesExamSeriesIdPut(
            @ApiParam(value = "The unique ID of the ExamSeries to retrieve", required = true) @PathVariable("examSeriesId") Integer examSeriesId,
            @ApiParam(value = "The ExamSeries object to be updated, with the examSeriesId field", required = true) @RequestBody ExamSeriesDto examSeries
    ) throws NotFoundException, InvalidDataException {
        LOGGER.info("** ExamSeriesApi - examSeriesExamSeriesIdPut");
        if (examSeriesId != examSeries.id) {
            throw new InvalidDataException();
        }
        AcademicYear academicYear
        if(examSeries.academicYearId!=null){
            academicYear =  academicYearService.findById(examSeries.academicYearId)
        }else{
            throw new InvalidDataException("Provide Academic Year details")
        }
        ExamSeries toSave = ExamSeriesDto.mapToExamSeriesEntity(examSeries,academicYear);
        ExamSeries savedExamSeries = examSeriesService.saveExamSeries(toSave);
        return new ResponseEntity<ExamSeriesDto>(ExamSeriesDto.mapFromExamSeriesEntity(savedExamSeries),HttpStatus.OK);
    }
}
