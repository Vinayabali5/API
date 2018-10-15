package uk.ac.reigate.api;

import javax.validation.Valid

import io.swagger.annotations.Api
import io.swagger.annotations.ApiOperation
import io.swagger.annotations.ApiParam
import io.swagger.annotations.ApiResponse
import io.swagger.annotations.ApiResponses

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

import uk.ac.reigate.domain.academic.AcademicYear
import uk.ac.reigate.dto.AcademicYearDto
import uk.ac.reigate.exceptions.DataAlreadyExistsException
import uk.ac.reigate.exceptions.InvalidDataException
import uk.ac.reigate.exceptions.NotFoundException
import uk.ac.reigate.services.AcademicYearService

import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE


@Controller
@RequestMapping(value = ["/api/academicYears", "/api/academic-years"], produces = [ APPLICATION_JSON_VALUE ])
@Api(value = "/api/academicYears", description = "the academicYears API")
public class AcademicYearsApi implements ICoreDataApi<AcademicYearDto, Integer> {
    
    private static final Logger LOGGER = Logger.getLogger(AcademicYearsApi.class);
    
    @Autowired
    private final AcademicYearService academicYearService;
    
    /**
     * Default NoArgs constructor
     */
    AcademicYearsApi() {}
    
    /**
     * Autowired constructor
     */
    AcademicYearsApi(AcademicYearService academicYearService) {
        this.academicYearService = academicYearService;
    }
    
    /**
     * The academicYearsGet method is used to retrieve a full list of all the AcademicYearDto objects
     *
     * @return A ResponseEntity with the corresponding list of AcademicYearDto objects.
     * @throws NotFoundException if nothing is found then the the NotFoundException is thrown.
     */
    @ApiOperation(value = "Collection of AcademicYear entities", notes = "A GET request to the AcademicYears endpoint returns an array of all the academicYears in the system.", response = AcademicYearDto.class, responseContainer = "List")
    @ApiResponses(value = [
        @ApiResponse(code = 200, message = "An array of academicYears")
    ])
    @RequestMapping(value = "", produces = ["application/json"], method = RequestMethod.GET)
    public ResponseEntity<List<AcademicYearDto>> getAll() throws NotFoundException {
        LOGGER.info("** AcademicYearsApi - academicYearsGet");
        List<AcademicYear> academicYears = academicYearService.findAll();
        return new ResponseEntity<List<AcademicYearDto>>(AcademicYearDto.mapFromEntityList(academicYears), HttpStatus.OK);
    }
    
    /**
     * The academicYearsPost method is used to create a new instance of a AcademicYear from the supplied AcademicYearDto
     *
     * @param academicYear the AcademicYearDto to use to create the new AcademicYear object
     * @return A ResponseEntity with the newly created AcademicYear object
     * @throws InvalidDataException if there is an issue with the data provided in the RequestBody then an InvalidDataException is thrown
     */
    @Secured([
        "ROLE_Core Data",
        "ROLE_Office Administration",
        "ROLE_Enrolment Manager",
        "ROLE_Administration",
        "ROLE_Student Services",
        "ROLE_Exams Officer"
    ])
    @ApiOperation(value = "Creates a new AcademicYear entity", notes = "A POST request to the AcademicYears endpoint with a AcademicYear object in the request body will create a new AcademicYear entity in the database.", response = AcademicYearDto.class)
    @ApiResponses(value = [
        @ApiResponse(code = 201, message = "Returns a copy of the created AcademicYear entity including the academicYearId that has just been created."),
        @ApiResponse(code = 400, message = "Returns an Error object stating the request body does not match the expected input.")
    ])
    @RequestMapping(value = "", produces = ["application/json"], method = RequestMethod.POST)
    public ResponseEntity<AcademicYearDto> create(
            @ApiParam(value = "The AcademicYear object to be created, without the academicYearId fields", required = true)
            @RequestBody @Valid AcademicYearDto academicYear) throws NotFoundException, InvalidDataException, DataAlreadyExistsException {
        LOGGER.info("** AcademicYearsApi - academicYearsPOST");
        if(academicYear.id == null){
            throw new NotFoundException();
        }
        AcademicYear academicYear1 = academicYearService.findById(academicYear.id);
        if(academicYear1 != null){
            throw new DataAlreadyExistsException("An Academic Year already exists with the supplied ID.");
        }
        AcademicYear academicYearToSave = academicYear.toAcademicYear() //AcademicYearDto.mapToEntity(academicYear)
        AcademicYear academicYearSaved = academicYearService.save(academicYearToSave)
        return new ResponseEntity<AcademicYearDto>(new AcademicYearDto(academicYearSaved), HttpStatus.CREATED);
    }
    
    /**
     * The academicYearsAcademicYearIdGet method is used to retrieve an instance of a AcademicYearDto object as identified by the academicYearId provided
     *
     * @param academicYearId the academicYear ID for the AcademicYear object retrieve
     * @return A ResponseEntity with the corresponding AcademicYearDto object
     * @throws NotFoundException if nothing is found then the the NotFoundException is thrown.
     */
    @ApiOperation(value = "Retrieves an indivdual instance of a AcademicYear identified by the academicYearId", notes = "A getGET request to the AcademicYear instance endpoint will retrieve an instance of a AcademicYear entity as identified by the academicYearId provided in the URI.", response = AcademicYearDto.class)
    @ApiResponses(value = [
        @ApiResponse(code = 200, message = "Returns a copy of the AcademicYear as identified by the academicYearId"),
        @ApiResponse(code = 404, message = "Returns an Error object stating that the resource could not be found. This happens if the specified id cannot be found in the database.")
    ])
    @RequestMapping(value = "/{academicYearId}", produces = ["application/json"], method = RequestMethod.GET)
    public ResponseEntity<AcademicYearDto> getById(
            @ApiParam(value = "The unique ID of the AcademicYear to retrieve", required = true)
            @PathVariable("academicYearId") Integer academicYearId
    ) throws NotFoundException {
        LOGGER.info("** AcademicYearsApi - academicYearsAcademicYearIdGet");
        AcademicYear academicYear = academicYearService.findById(academicYearId);
        if (academicYear == null) {
            throw new NotFoundException();
        }
        return new ResponseEntity<AcademicYearDto>(new AcademicYearDto(academicYear), HttpStatus.OK);
    }
    
    /**
     * The academicYearsAcademicYearIdPut is used to update
     *
     * @param academicYearId the academicYear ID for the AcademicYear object to update
     * @param academicYear the new data for the AcademicYear object
     * @return the newly updated AcademicYearDto object
     * @throws NotFoundException if nothing is found then the the NotFoundException is thrown.
     * @throws InvalidDataException if there is an issue with the data provided in the RequestBody then an InvalidDataException is thrown
     */
    @Secured([
        "ROLE_Core Data",
        "ROLE_Office Administration",
        "ROLE_Enrolment Manager",
        "ROLE_Administration",
        "ROLE_Student Services",
        "ROLE_Exams Officer"
    ])
    @ApiOperation(value = "Used to update a AcademicYear entity", notes = "A PUT request to the AcademicYear instance endpoint with a AcademicYear object in the request body will update an existing AcademicYear entity in the database.", response = AcademicYearDto.class)
    @ApiResponses(value = [
        @ApiResponse(code = 200, message = "Returns a copy of the newly updated AcademicYear object."),
        @ApiResponse(code = 400, message = "Returns an Error object stating the request body does not match the expected input."),
        @ApiResponse(code = 404, message = "Returns an Error object stating that the resource could not be found. This happens if the specified id cannot be found in the database.")
    ])
    @RequestMapping(value = "/{academicYearId}", produces = ["application/json"], method = RequestMethod.PUT)
    public ResponseEntity<AcademicYearDto> update(
            @ApiParam(value = "The unique ID of the AcademicYear to retrieve", required = true)
            @PathVariable("academicYearId") Integer academicYearId,
            @ApiParam(value = "The AcademicYear object to be created, without the academicYearId fields", required = true)
            @RequestBody AcademicYearDto academicYear) throws NotFoundException, InvalidDataException {
        LOGGER.info("** AcademicYearsApi - academicYearPUT");
        if (academicYearId != academicYear.id) {
            throw new InvalidDataException()
        }
        AcademicYear academicYearToSave = AcademicYearDto.mapToAcademicYearEntity(academicYear)
        AcademicYear academicYearSaved = academicYearService.updateAcademicYear(academicYearToSave)
        return new ResponseEntity<AcademicYearDto>(new AcademicYearDto(academicYearSaved), HttpStatus.OK);
    }
    
    /**
     * The acdemicYearsGet method is used to retrieve the current AcademicYear entity of the AcademicYearDto objects
     *
     * @return A ResponseEntity with the corresponding AcademicYearDto objects.
     */
    @ApiOperation(value = "Used to retrieve the current AcademicYear entity", notes = "A GET request to the retrieve the current AcademicYear.", response = AcademicYearDto.class)
    @ApiResponses(value = [
        @ApiResponse(code = 200, message = "Returns the current AcademicYear object."),
        @ApiResponse(code = 400, message = "Returns an Error object stating the request body does not match the expected input."),
        @ApiResponse(code = 404, message = "Returns an Error object stating that the resource could not be found. This happens if the specified id cannot be found in the database.")
    ])
    @RequestMapping(value = "/current", produces = ["application/json"], method = RequestMethod.GET)
    public ResponseEntity<AcademicYearDto> getCurrentAcademicYear() {
        LOGGER.info("** AcademicYearsApi - getCurrentAcademicYear");
        return new ResponseEntity<AcademicYearDto>(new AcademicYearDto(academicYearService.getCurrentAcademicYear()), HttpStatus.OK)
    }
    
    /**
     * The acdemicYearsGet method is used to retrieve the next AcademicYear entity of the AcademicYearDto objects
     *
     * @return A ResponseEntity with the corresponding AcademicYearDto objects.
     */
    @ApiOperation(value = "Used to retrieve the next AcademicYear entity", notes = "A GET request to the retrieve the current AcademicYear.", response = AcademicYearDto.class)
    @ApiResponses(value = [
        @ApiResponse(code = 200, message = "Returns the next AcademicYear object."),
        @ApiResponse(code = 400, message = "Returns an Error object stating the request body does not match the expected input."),
        @ApiResponse(code = 404, message = "Returns an Error object stating that the resource could not be found. This happens if the specified id cannot be found in the database.")
    ])
    @RequestMapping(value = "/next", produces = ["application/json"], method = RequestMethod.GET)
    public ResponseEntity<AcademicYearDto> getNextAcademicYear() {
        LOGGER.info("** AcademicYearsApi - getNextAcademicYear");
        return new ResponseEntity<AcademicYearDto>(new AcademicYearDto(academicYearService.getNextAcademicYear()), HttpStatus.OK)
    }
    
    /**
     * The acdemicYearsGet method is used to retrieve the previous AcademicYear entity of the AcademicYearDto objects
     *
     * @return A ResponseEntity with the corresponding AcademicYearDto objects.
     */
    @ApiOperation(value = "Used to retrieve the previous AcademicYear entity", notes = "A GET request to the retrieve the previous AcademicYear.", response = AcademicYearDto.class)
    @ApiResponses(value = [
        @ApiResponse(code = 200, message = "Returns the previous AcademicYear object."),
        @ApiResponse(code = 400, message = "Returns an Error object stating the request body does not match the expected input."),
        @ApiResponse(code = 404, message = "Returns an Error object stating that the resource could not be found. This happens if the specified id cannot be found in the database.")
    ])
    @RequestMapping(value = "/previous", produces = ["application/json"], method = RequestMethod.GET)
    public ResponseEntity<AcademicYearDto> getPreviousAcademicYear() {
        LOGGER.info("** AcademicYearsApi - getPreviousAcademicYear");
        return new ResponseEntity<AcademicYearDto>(new AcademicYearDto(academicYearService.getPreviousAcademicYear()), HttpStatus.OK)
    }
}

