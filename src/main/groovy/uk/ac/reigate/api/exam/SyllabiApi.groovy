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
import org.springframework.web.bind.annotation.CrossOrigin
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestMethod
import org.springframework.web.bind.annotation.RequestParam

import springfox.documentation.annotations.ApiIgnore
import uk.ac.reigate.domain.exam.Syllabus
import uk.ac.reigate.dto.exam.SyllabusDto
import uk.ac.reigate.exceptions.InvalidDataException
import uk.ac.reigate.exceptions.NotFoundException
import uk.ac.reigate.model.SearchResult
import uk.ac.reigate.services.exam.SyllabusService


@Controller
@RequestMapping(value = "/api", produces = [APPLICATION_JSON_VALUE ])
@Api(value = "/api/syllabiApi", description = "The Exam basedata Syllabus Resource API")
public class SyllabiApi {
    
    private final static Logger LOGGER = Logger.getLogger(SyllabiApi.class);
    
    @Autowired
    SyllabusService syllabusService
    
    /**
     * Default No Args constructor
     */
    SyllabiApi() {}
    
    /**
     * Default Autowired constructor
     */
    SyllabiApi(SyllabusService syllabusService) {
        this.syllabusService = syllabusService;
    }
    
    /**
     * The syllabiGet method is used to retrieve a (non paged) collection of syllabi entities of the syllabusDto objects
     *
     * @param examBoardId ID of examBoard to retrieve results for
     * @param examYear exam year to retrieve results for
     * @param examSeries exam series to retrieve results for
     * @return A ResponseEntity with the corresponding list of syllabusDto objects
     * @throws NotFoundException If nothing is found then the NotFoundException is thrown
     */
    @CrossOrigin
    @ApiOperation(value = "Collection of All syllabus entities",
    notes = "A GET request to the Syllabi endpoint returns an array of ALL the syllabi in the system for the particular year Id",
    response = SyllabusDto.class, responseContainer = "List")
    @ApiResponses(value = [
        @ApiResponse(code = 200, message = "An array of syllabi")
    ])
    @RequestMapping(value = ["/syllabi"], produces = ["application/json"], method = RequestMethod.GET)
    public ResponseEntity<List<SyllabusDto>> syllabusGetByYearId(
            @ApiParam(value = "year Id", name = "yearId", required = false)
            @RequestParam(value = "yearId", required = false) Integer yearId
    ) throws NotFoundException {
        LOGGER.info("** SyllabiApi - syllabusGetByYearId");
        SearchResult<Syllabus> syllabus;
        //        List<Syllabus>
        syllabus = syllabusService.findByYearId(yearId);
        
        HttpHeaders responseHeaders = new HttpHeaders();
        responseHeaders.set("x-current-page", (String)syllabus.pageInfo.page);
        responseHeaders.set("x-total-pages", (String)syllabus.pageInfo.totalPages.value);
        responseHeaders.set("x-total-items", (String)syllabus.pageInfo.size);
        
        return new ResponseEntity<List<SyllabusDto>>(SyllabusDto.mapFromSyllabusEntities(syllabus.getResult()), responseHeaders, HttpStatus.OK);
    }
    
    /**
     * The syllabiGet method is used to retrieve a collection of syllabi entities of the syllabusDto objects
     *
     * @param examBoardId ID of examBoard to retrieve results for
     * @param examYear exam year to retrieve results for
     * @param examSeries exam series to retrieve results for
     * @param page Results page you want to retrieve
     * @param size The number of records per page
     * @param sort Sorting criteria in the format ASC/DESC
     * @return A ResponseEntity with the corresponding list of syllabusDto objects
     * @throws NotFoundException If nothing is found then the NotFoundException is thrown
     */
    @CrossOrigin
    @ApiOperation(value = "Collection of Syllabus entities",
    notes = "a GET request to the Syllabi endpoint returns an array of all the current syllabi in the system.",
    response = SyllabusDto.class, responseContainer = "List")
    @ApiImplicitParams([
        @ApiImplicitParam(name = "page", dataType = "Integer", paramType = "query", value = "Results page you want to retrieve (0..N)"),
        @ApiImplicitParam(name = "size", dataType = "Integer", paramType = "query", value = "Number of records per page."),
        @ApiImplicitParam(name = "sort", allowMultiple = true, dataType = "string", paramType = "query",
        value = " Sorting criteria in the format: property(,asc|desc). Default sort order is ascending. Multiple sort criteria are supported.")
    ])
    @ApiResponses(value = [
        @ApiResponse(code = 200, message = "An array of syllabi")
    ])
    @RequestMapping(value = "/syllabi/paged", produces = ["application/json"], method = RequestMethod.GET)
    public ResponseEntity<List<SyllabusDto>> syllabusGetPageable(
            @ApiParam(value = "exam Board Id", name = "examBoardId", required = false)
            @RequestParam(value = "examBoardId", required = false) Optional<Integer> examBoardId,
            @ApiParam(value = "syllabus Code", name = "syllabusCode", required = false)
            @RequestParam(value = "syllabusCode", required = false) Optional<String> syllabusCode,
            @ApiParam(value = "exam Year", name = "examYear", required = false)
            @RequestParam(value = "examYear", required = false) Optional<String> examYear,
            @ApiParam(value = "exam Series", name = "examSeries", required = false)
            @RequestParam(value = "examSeries", required = false) Optional<String> examSeries,
            @ApiParam(value = "Pageable parameters (page, size, sort).", required = false) @ApiIgnore Pageable pageable
    ) throws NotFoundException {
        LOGGER.info("** SyllabiApi - syllabusGetPageable");
        if (examBoardId != null) LOGGER.info("    examBoardId = " + examBoardId);
        if (syllabusCode != null) LOGGER.info("    syllabusCode = " + syllabusCode);
        if (examYear != null) LOGGER.info("    examYear = " + examYear);
        if (examSeries != null) LOGGER.info("    examSeries = " + examSeries);
        LOGGER.info("    pageable: " + pageable);
        SearchResult<Syllabus> syllabus;
        syllabus = syllabusService.findSyllabi(pageable, examBoardId, syllabusCode, examYear, examSeries);
        
        HttpHeaders responseHeaders = new HttpHeaders();
        responseHeaders.set("x-current-page", (String)syllabus.pageInfo.page);
        responseHeaders.set("x-total-pages", (String)syllabus.pageInfo.totalPages.value);
        responseHeaders.set("x-total-items", (String)syllabus.pageInfo.totalResults.value);
        
        return new ResponseEntity<List<SyllabusDto>>(SyllabusDto.mapFromSyllabusEntities(syllabus.getResult()), responseHeaders, HttpStatus.OK);
    }
    
    /**
     * The syllabusSyllabusIdGet method is used to retrieve an instance of a SyllabusDto object as identified by the syllabusId provided
     *
     * @param syllabusId The syllabus ID for the Syllabus object to retrieve
     * @return A ResponseEntity with the corresponding SyllabusDto object
     * @throws NotFoundException If nothing is found then the NotFoundException is thrown
     */
    @ApiOperation(value = "Retrieves an individual instance of a Syllabus identified by the syllabusId",
    notes = "A GET request to the Syllabus instance endpoint will retrieve an instance of a Syllabus entity as identified by the syllabusId provided in the URI.",
    response = SyllabusDto.class)
    @ApiResponses(value = [
        @ApiResponse(code = 200, message = "Returns a copy of the Syllabus as identified by the syllabusId"),
        @ApiResponse(code = 404, message = "Returns an Error object stating that the resource could not be found. This happens if the specified ID cannot be found in the database.")
    ])
    @RequestMapping(value = "/syllabi/{syllabusId}", produces = ["application/json"], method = RequestMethod.GET)
    public ResponseEntity<SyllabusDto> syllabusSyllabusIdGet(
            @ApiParam(value = "The unique ID of the Syllabus to retrieve", required = true) @PathVariable("syllabusId") Integer syllabusId
    ) throws NotFoundException {
        LOGGER.info("** SyllabiApi - syllabusSyllabusIdGet");
        Syllabus syllabus = syllabusService.findById(syllabusId);
        if (syllabus == null) {
            throw new NotFoundException();
        }
        return new ResponseEntity<SyllabusDto>(SyllabusDto.mapFromSyllabusEntity(syllabus), HttpStatus.OK);
    }
    
    /**
     * The syllabusPost method is used to create a new instance of a Syllabus object from the supplied SyllabusDto entity
     *
     *  @param syllabus The SyllabusDto to use to create the new Syllabus object
     *  @return A ResponseEntity with the newly created SyllabusDto object
     *  @throws InvalidDataException If there is an issue with the data provided in the RequesetBody then an InvalidDataException is thrown
     */
    @ApiOperation(value = "Creates a new Syllabus entity",
    notes = "A POST request to the Syllabi endpoint with a Syllabus object in the request body will create a new Syllabus entity in the database.",
    response = SyllabusDto.class)
    @ApiResponses(value = [
        @ApiResponse(code = 201, message = "Returns a copy of the created Syllabus entity including the syllabusId that has just been created."),
        @ApiResponse(code = 400, message = "Returns an Error object stating that the request body does not match the expected input.")
    ])
    @RequestMapping(value = "/syllabi", produces = ["application/json"], method = RequestMethod.POST)
    public ResponseEntity<SyllabusDto> syllabusPost(
            @ApiParam(value = "The syllabus object to be created, without the syllabusId field", required = true) @RequestBody @Valid SyllabusDto syllabus
    ) throws NotFoundException, InvalidDataException {
        LOGGER.info("** SyllabusApi - syllabusPost");
        if (syllabus.id != null) {
            throw new InvalidDataException(400, "No ID field should be provided when created");
        }
        Syllabus toSave = SyllabusDto.mapToSyllabusEntity(syllabus);
        Syllabus savedSyllabus = syllabusService.save(toSave);
        return new ResponseEntity<SyllabusDto>(SyllabusDto.mapFromSyllabusEntity(savedSyllabus), HttpStatus.CREATED);
    }
    
    /**
     * The syllabusSyllabusIdPut is used to update an instance of a SyllabusDto as identified by the syllabusId provided
     *
     * @param syllabusId The syllabus ID for the Syllabus object to update
     * @param syllabus The new daya for the Syllabus object
     * @return The newly updated SyllabusDto object
     * @throws InvalidDataException if there is an issue with the data provided in the RequestBody  then an InvalidDataException is thrown
     */
    @ApiOperation(value = "Used to update a Syllabus entity",
    notes = "A PUT request to the Syllabus instance endpoint with a SyllabusDto object in the request body will update an existing Syllabus entity in the database",
    response = SyllabusDto.class)
    @ApiResponses(value = [
        @ApiResponse(code = 200, message = "Returns a copy of the newly updated SyllabusDto object"),
        @ApiResponse(code = 400, message = "Returns an Error object stating that the request body does not match the expected input."),
        @ApiResponse(code = 404, message = "Returns an Error object stating that the resource could not be found. This happens is the specified ID cannot be found in the database.")
    ])
    @RequestMapping(value = "/syllabi/{syllabusId}", produces = ["application/json"], method = RequestMethod.PUT)
    public ResponseEntity<SyllabusDto> syllabusSyllabusIdPut(
            @ApiParam(value = "The unique ID of the Syllabus to retrieve", required = true) @PathVariable("syllabusId") Integer syllabusId,
            @ApiParam(value = "The Syllabus object to be updated, with the syllabusId field", required = true) @RequestBody SyllabusDto syllabus
    ) throws NotFoundException, InvalidDataException {
        LOGGER.info("** SyllabusApi - syllabusSyllabusIdPut");
        if (syllabusId != syllabus.id) {
            throw new InvalidDataException();
        }
        Syllabus toSave = SyllabusDto.mapToSyllabusEntity(syllabus);
        Syllabus savedSyllabus = syllabusService.save(toSave);
        return new ResponseEntity<SyllabusDto>(SyllabusDto.mapFromSyllabusEntity(savedSyllabus), HttpStatus.OK);
    }
}
