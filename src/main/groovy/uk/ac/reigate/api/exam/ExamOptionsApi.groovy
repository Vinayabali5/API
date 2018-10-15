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
import uk.ac.reigate.domain.exam.ExamOption
import uk.ac.reigate.dto.exam.ExamOptionDto
import uk.ac.reigate.exceptions.InvalidDataException
import uk.ac.reigate.exceptions.NoSearchResultsFoundException
import uk.ac.reigate.exceptions.NotFoundException
import uk.ac.reigate.model.SearchResult
import uk.ac.reigate.services.exam.ExamOptionService


@Controller
@RequestMapping(value = "/api/examOptions", produces = [APPLICATION_JSON_VALUE])
@Api(value = "/api/examOptionApi", description = "The Exam basedata Option Resource API")
public class ExamOptionsApi {
    
    private final static Logger LOGGER = Logger.getLogger(ExamOptionsApi.class);
    
    @Autowired
    ExamOptionService examOptionService
    
    /**
     * Default No Args constructor
     */
    ExamOptionsApi() {}
    
    /**
     * Default Autowired constructor
     */
    ExamOptionsApi(ExamOptionService examOptionService) {
        this.examOptionService = examOptionService;
    }
    
    @RequestMapping(value = '/search', produces = ["application/json"], method = RequestMethod.GET)
    ResponseEntity<List<ExamOptionDto>> searchOptionEntryCode(
            @RequestParam(value= "optionEntryCode", required =  false)String optionEntryCode) throws NoSearchResultsFoundException {
        LOGGER.info("** ExamOptionApi - searchOptionEntryCode");
        SearchResult<ExamOption> examOptionsList
        examOptionsList = examOptionService.searchByOptionEntryCode('%' + optionEntryCode + '%')
        return new ResponseEntity<List<ExamOptionDto>>(ExamOptionDto.mapFromExamOptionEntities(examOptionsList.getResult()), HttpStatus.OK);
    }
    
    /**
     * The optionGetNonPageable method is used to retrieve a non paged collection of optionDto objects of the options entities
     * 
     * @param examBoardId ID of the examBoard to retrieve results for
     * @param examYear exam Year to retrieve results for
     * @param examSeries exam Series to retrieve results for
     * @param syllabusId syllabus ID to retrieve results for
     * @return A ResponseEntity with the corresponding list of optionDto objects
     * @throws NotFoundException If nothing is found then the NotFoundException is thrown
     */
    @ApiOperation(value = "Collection of All Option entities",
    notes = "A GET request to the Options endpoint returns an array of ALL the options in the system",
    response = ExamOptionDto.class, responseContainer = "List")
    @ApiResponses(value = [
        @ApiResponse(code = 200, message = "An array of exam options")
    ])
    @RequestMapping(value="", produces = ["application/json"], method = RequestMethod.GET)
    public ResponseEntity<List<ExamOptionDto>> examOptionGetNonPageable(
            @ApiParam(value = "exam Board Id", name = "examBoardId", required = false)
            @RequestParam(name = "examBoardId", required = false) Optional<Integer> examBoardId,
            @ApiParam(value = "exam Year", name = "examYear", required = false)
            @RequestParam(name = "examYear", required = false) Optional<String> examYear,
            @ApiParam(value = "exam Series", name = "examSeries", required = false)
            @RequestParam(name = "examSeries", required = false) Optional<String> examSeries
    ) throws NotFoundException {
        LOGGER.info("** ExamOptionApi - examOptionGetNonPageable");
        SearchResult<ExamOption> examOption;
        examOption = examOptionService.findAll();
        return new ResponseEntity<List<ExamOptionDto>>(ExamOptionDto.mapFromExamOptionEntities(examOption.getResult()), HttpStatus.OK);
    }
    
    /**
     * The examOptionGet method is used to retrieve a collection of examOptionDto objects of the exam options entities
     * 
     * @param examBoardId ID of the examBoard to retrieve
     * @param examYear exam year to retrieve results for
     * @param examSeries exam series to retrieve results for
     * @param page Results page you want to retrieve
     * @param size The number of records per page
     * @param sort Sorting criteria in the format ASC/DeSC
     * @return A ResponseEntity with the corresponding list of examOptionDto objects
     * @throws NotFoundException If nothing is found then the NotFoundException is thrown
     */
    @ApiOperation(value = "Collection of Exam Option entities",
    notes = "A GET request to the Options endpoint returns a paged array of the exam options in the system",
    response = ExamOptionDto.class, responseContainer = "List")
    @ApiImplicitParams([
        @ApiImplicitParam(name = "page", dataType = "Integer", paramType = "query", value = "Results page you want to retrieve (0..N)"),
        @ApiImplicitParam(name = "size", dataType = "Integer", paramType = "query", value = "Number of records per page."),
        @ApiImplicitParam(name = "sort", allowMultiple = true, dataType = "string", paramType = "query",
        value = "Sorting criteria in the format: property(,asc|desc). Default sort order is ascending. Multiple sort criteria are supported.")
    ])
    @ApiResponses(value = [
        @ApiResponse(code = 200, message = "An array of exam options")
    ])
    @RequestMapping(value = "/paged", produces = ["application/json"], method = RequestMethod.GET)
    public ResponseEntity<List<ExamOptionDto>> examOptionGetPageable(
            @ApiParam(value = "exam Board Id", name = "examBoardId", required = false)
            @RequestParam(name = "examBoardId", required = false) Optional<Integer> examBoardId,
            @ApiParam(value = "exam Year", name = "examYear", required = false)
            @RequestParam(name = "examYear", required = false) Optional<String> examYear,
            @ApiParam(value = "exam Series", name = "examSeries", required = false)
            @RequestParam(name = "examSeries", required = false) Optional<String> examSeries,
            @ApiParam(value = "Pageable parameters (page, size, sort).", required = false) @ApiIgnore Pageable pageable
    ) throws NotFoundException {
        LOGGER.info("** ExamOptionsApi - examOptionGetPageable");
        SearchResult<ExamOption> examOption;
        
        HttpHeaders responseHeaders = new HttpHeaders();
        responseHeaders.set("x-current-page", (String)examOption.pageInfo.page);
        responseHeaders.set("x-total-pages", (String)examOption.pageInfo.totalPages.value);
        responseHeaders.set("x-total-items", (String)examOption.pageInfo.totalResults.value);
        
        examOption = examOptionService.findExamOptions(pageable, examBoardId, examYear, examSeries);
        
        return new ResponseEntity<List<ExamOptionDto>>(ExamOptionDto.mapFromExamOptionEntities(examOption.getResult()), responseHeaders, HttpStatus.OK);
    }
    
    /**
     * The examOptionGetBySyllabusId method is used to retrieve a non paged collection of examOptionDto objects of the options entities for a specified syllabusId
     *
     * @param syllabusId syllabus ID to retrieve results for
     * @return A ResponseEntity with the corresponding list of optionDto objects
     * @throws NotFoundException If nothing is found then the NotFoundException is thrown
     */
    @ApiOperation(value = "Retrieves a collection of ExamOption entities for a specified syllabusId",
    notes = "A GET request to the ExamOptions endpoint returns an array of the examOptions for a specified syllabusId in the system",
    response = ExamOptionDto.class)
    @ApiResponses(value = [
        @ApiResponse(code = 200, message = "Returns an array of options"),
        @ApiResponse(code = 404, message = "Returns an Error object stating that no resources could be found. This hapens if the specified syllabusId cannot be found in the database.")
    ])
    @RequestMapping(value = "/syllabus", produces = ["application/json"], method = RequestMethod.GET)
    public ResponseEntity<List<ExamOptionDto>> examOptionGetBySyllabusId(
            @ApiParam(value = "syllabus Id", name = "syllabusId", required = true)
            @RequestParam(name = "syllabusId", required = true) Integer syllabusId
    ) throws NotFoundException {
        LOGGER.info("** ExamOptionApi - examOptionGetBySyllabusId");
        List<ExamOption> examOption;
        examOption = examOptionService.findExamOptions(syllabusId);
        return new ResponseEntity<List<ExamOptionDto>>(ExamOptionDto.mapFromExamOptionEntities(examOption), HttpStatus.OK);
    }
    
    /**
     * The examOptionExamOptionIdGet method is used to retrieve an instance of an ExamOptionDto object as identified by the examOptionId provided
     * 
     * @param examOptionId The exam option ID for the ExamOption object to retrieve
     * @return A ResponseEntity with the corresponding ExamOptionDto object
     * @throws NotFoundException If nothing is found then the NotfoundException is thrown
     */
    @ApiOperation(value = "Retrieves an individual instance of an ExamOption identified by the examOptionId",
    notes = "A GET request to the Exam Option instance endpoint will retrieve an instance of an Exam Option entity as identified by the examOptionId provided in the URI.",
    response = ExamOptionDto.class)
    @ApiResponses(value = [
        @ApiResponse(code = 200, message = "Returns a copy of the Exam Option as identified by the optionId"),
        @ApiResponse(code = 404, message = "Returns an Error object stating that the resource could not be found. This happens if the specified ID cannot be found in the database.")
    ])
    @RequestMapping(value = "/{examOptionId}", produces = ["application/json"], method = RequestMethod.GET)
    public ResponseEntity<ExamOptionDto> examOptionExamOptionIdGet(
            @ApiParam(value = "The unique ID of the Exam Option to retrieve", required = true) @PathVariable("examOptionId") Integer examOptionId
    ) throws NotFoundException {
        LOGGER.info("** ExamOptionApi - examOptionExamOptionIdGet");
        ExamOption examOption = examOptionService.findById(examOptionId);
        if (examOption == null) {
            throw new NotFoundException();
        }
        return new ResponseEntity<ExamOptionDto>(ExamOptionDto.mapFromExamOptionEntity(examOption), HttpStatus.OK);
    }
    
    /**
     * The examOptionPost method is used to create a new instance of an exam option object from the supplied ExamOptionDto entity
     * 
     * @param examOption The ExamOptionDto object to use to create the new Exam Option entity
     * @return A ResponseEntity with the newly created ExamOptionDto object
     * @throws InvalidDataException If there is an issue with the data provided in the RequestBody then an InvalidDataException is thrown
     */
    @ApiOperation(value = "Creates a new ExamOption entity",
    notes = "A POST request to the Options endpoint with an ExamOption object in the request body will create a new ExamOption entity in the database.",
    response = ExamOptionDto.class)
    @ApiResponses(value = [
        @ApiResponse(code = 201, message = "Returns a copy of the created ExamOption entity including the optionId that has just been created."),
        @ApiResponse(code = 400, message = "Returns an Error object stating that the request body does not match the expected input.")
    ])
    @RequestMapping(value = "", produces = ["application/json"], method = RequestMethod.POST)
    public ResponseEntity<ExamOptionDto> examOptionPost(
            @ApiParam(value = "The examOption object to be created, without the examOptionId field", required = true) @RequestBody @Valid ExamOptionDto examOption
    ) throws NotFoundException, InvalidDataException {
        LOGGER.info("** ExamOptionApi - examOptionPost");
        if (examOption.examOptionId != null) {
            throw new InvalidDataException(400, "No ID field should be provided when creating");
        }
        ExamOption toSave = ExamOptionDto.mapToExamOptionEntity(examOption);
        ExamOption savedExamOption = examOptionService.saveExamOption(toSave);
        return new ResponseEntity<ExamOptionDto>(ExamOptionDto.mapFromExamOptionEntity(savedExamOption), HttpStatus.CREATED);
    }
    
    
    /**
     * @param examOptionId
     * @param examOption
     * @return
     * @throws NotFoundException
     * @throws InvalidDataException
     */
    @ApiOperation(value = "Used to update an ExamOption entity",
    notes = "A PUT request to the ExamOption instance endpoint with an ExamOptionDto object in the request body will update an existing ExamOption entity in the database",
    response = ExamOptionDto.class)
    @ApiResponses(value = [
        @ApiResponse(code = 200, message = "Returns a copy of the newly updated ExamOptionDto object"),
        @ApiResponse(code = 400, message = "Returns an Error object stating that the request body does not match the expected input."),
        @ApiResponse(code = 404, message = "Returns an Error object stating that the resource could not be found. This happens if the specified ID cannot be found in the database.")
    ])
    @RequestMapping(value = "/{examOptionId}", produces = ["application/json"], method = RequestMethod.PUT)
    public ResponseEntity<ExamOptionDto> examOptionExamOptionIdPut(
            @ApiParam(value = "The unique ID of the Exam Option to retrieve", required = true) @PathVariable("examOptionId") Integer examOptionId,
            @ApiParam(value = "The Exam Option object to be updated, with the examOptionId field", required = true) @RequestBody ExamOptionDto examOption
    ) throws NotFoundException, InvalidDataException {
        LOGGER.info("** ExamOptionApi - examOptionExamOptionIdPut");
        if (examOptionId != examOption.examOptionId) {
            throw new InvalidDataException();
        }
        ExamOption toSave = ExamOptionDto.mapToExamOptionEntity(examOption);
        ExamOption savedExamOption = examOptionService.saveExamOption(toSave);
        return new ResponseEntity<ExamOptionDto>(ExamOptionDto.mapFromExamOptionEntity(savedExamOption), HttpStatus.OK);
    }
}
