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
import org.springframework.web.bind.annotation.RequestParam
import uk.ac.reigate.domain.academic.AcademicYear
import uk.ac.reigate.domain.lookup.ReportingPeriod
import uk.ac.reigate.dto.CourseGroupDto
import uk.ac.reigate.dto.ReportingPeriodDto
import uk.ac.reigate.exceptions.InvalidDataException
import uk.ac.reigate.exceptions.NotFoundException
import uk.ac.reigate.model.SearchResult
import uk.ac.reigate.services.AcademicYearService
import uk.ac.reigate.services.ReportingPeriodService


@Controller
@RequestMapping(value = "/api/reportingPeriods", produces = [ APPLICATION_JSON_VALUE ])
@Api(value = "/api/reportingPeriods", description = "the reportingPeriods API")
public class ReportingPeriodsApi {
    
    private static final Logger LOGGER = Logger.getLogger(ReportingPeriodsApi.class);
    
    @Autowired
    private final ReportingPeriodService reportingPeriodService;
    
    
    @Autowired
    private final AcademicYearService academicYearService
    
    /**
     * Default NoArgs constructor
     */
    ReportingPeriodsApi() {}
    
    /**
     * Autowired constructor
     */
    ReportingPeriodsApi(ReportingPeriodService reportingPeriodService) {
        this.reportingPeriodService = reportingPeriodService;
    }
    
    /**
     * The reportingPeriodsGet method is used to retrieve a full list of all the ReportingPeriodDto objects
     *
     * @return A ResponseEntity with the corresponding list of ReportingPeriodDto objects
     * @throws NotFoundException if nothing is found then the the NotFoundException is thrown.
     */
    @ApiOperation(value = "Collection of ReportingPeriod entities", notes = "A GET request to the ReportingPeriods endpoint returns an array of all the reportingPeriods in the system.", response = ReportingPeriodDto.class, responseContainer = "List")
    @ApiResponses(value = [
        @ApiResponse(code = 200, message = "An array of reportingPeriods")
    ])
    @RequestMapping(value = "", produces = ["application/json"], method = RequestMethod.GET)
    public ResponseEntity<List<ReportingPeriodDto>> reportingPeriodsGet() throws NotFoundException {
        LOGGER.info("** ReportingPeriodsApi - reportingPeriodsGet");
        List<ReportingPeriod> reportingPeriods = reportingPeriodService.findAll();
        return new ResponseEntity<List<ReportingPeriodDto>>(ReportingPeriodDto.mapFromReportingPeriodsEntities(reportingPeriods), HttpStatus.OK);
    }
    
    /**
     * The reportingPeriodsGet method is used to retrieve a full list of all the ReportingPeriodDto objects by current year
     *
     * @return A ResponseEntity with the corresponding list of ReportingPeriodDto objects
     * @throws NotFoundException if nothing is found then the the NotFoundException is thrown.
     */
    @RequestMapping(value = "/years", produces = ["application/json"], method = RequestMethod.GET)
    public ResponseEntity<ReportingPeriodDto> getReportingPeriods(
            @ApiParam(value = "The code of the AcademicYear to retrieve", required = false) @RequestParam(value = "academicYear", required = false) String year
    ) throws NotFoundException {
        LOGGER.info("** ReportingPeriodsApi - reportingPeriodsGetByYear");
        AcademicYear academicYear
        if (year) {
            LOGGER.info("ReportingPeriodsApi Searching for Year Code: " + year);
            academicYear = academicYearService.findByCode(year)
        }
        if (academicYear == null) {
            LOGGER.info("ReportingPeriodApi No Year Found or Supplied - Using default")
            academicYear = academicYearService.getCurrentAcademicYear()
        }
        List<ReportingPeriod> reportingPeriodsList = reportingPeriodService.searchByAcademicYear(academicYear);
        
        return new ResponseEntity<List<ReportingPeriodDto>>(ReportingPeriodDto.mapFromReportingPeriodsEntities(reportingPeriodsList), HttpStatus.OK)
    }
    
    /**
     * The reportingPeriodsPost method is used to create a new instance of a ReportingPeriod from the supplied ReportingPeriodDto
     *
     * @param reportingPeriod the ReportingPeriodDto to use to create the new ReportingPeriod object
     * @return A ResponseEntity with the newly created ReportingPeriod object
     * @throws InvalidDataException if there is an issue with the data provided in the RequestBody then an InvalidDataException is thrown
     */
    @ApiOperation(value = "Creates a new ReportingPeriod entity", notes = "A POST request to the ReportingPeriods endpoint with a ReportingPeriod object in the request body will create a new ReportingPeriod entity in the database.", response = ReportingPeriodDto.class)
    @ApiResponses(value = [
        @ApiResponse(code = 201, message = "Returns a copy of the created ReportingPeriod entity including the reportingPeriodId that has just been created."),
        @ApiResponse(code = 400, message = "Returns an Error object stating the request body does not match the expected input.")
    ])
    @RequestMapping(value = "", produces = ["application/json"], method = RequestMethod.POST)
    public ResponseEntity<ReportingPeriodDto> reportingPeriodsPost(@ApiParam(value = "The ReportingPeriod object to be created, without the reportingPeriodId fields", required = true) @RequestBody @Valid ReportingPeriodDto reportingPeriod) throws NotFoundException, InvalidDataException {
        LOGGER.info("** ReportingPeriodsApi - reportingPeriodsPOST");
        if (reportingPeriod.id != null) {
            throw new InvalidDataException(400, "No ID field should be provided when creating")
        }
        AcademicYear academicYear
        if(reportingPeriod.academicYearId != null){
            academicYear = academicYearService.findById(reportingPeriod.academicYearId)
        }
        ReportingPeriod reportingPeriodToSave = ReportingPeriodDto.mapToReportingPeriodEntity(reportingPeriod, academicYear)
        ReportingPeriod reportingPeriodSaved = reportingPeriodService.save(reportingPeriodToSave)
        return new ResponseEntity<ReportingPeriodDto>(ReportingPeriodDto.mapFromReportingPeriodEntity(reportingPeriodSaved), HttpStatus.CREATED);
    }
    
    /**
     * The reportingPeriodsReportingPeriodIdGet method is used to retrieve an instance of a ReportingPeriodDto object as identified by the reportingPeriodId provided
     *
     * @param reportingPeriodId the reportingPeriod ID for the ReportingPeriod object retrieve
     * @return A ResponseEntity with the corresponding ReportingPeriodDto object
     * @throws NotFoundException if nothing is found then the the NotFoundException is thrown.
     */
    @ApiOperation(value = "Retrieves an indivdual instance of a ReportingPeriod identified by the reportingPeriodId", notes = "A getGET request to the ReportingPeriod instance endpoint will retrieve an instance of a ReportingPeriod entity as identified by the reportingPeriodId provided in the URI.", response = ReportingPeriodDto.class)
    @ApiResponses(value = [
        @ApiResponse(code = 200, message = "Returns a copy of the ReportingPeriod as identified by the reportingPeriodId"),
        @ApiResponse(code = 404, message = "Returns an Error object stating that the resource could not be found. This happens if the specified id cannot be found in the database.")
    ])
    @RequestMapping(value = "/{reportingPeriodId}", produces = ["application/json"], method = RequestMethod.GET)
    public ResponseEntity<ReportingPeriodDto> reportingPeriodsReportingPeriodIdGet(@ApiParam(value = "The unique ID of the ReportingPeriod to retrieve", required = true) @PathVariable("reportingPeriodId") Integer reportingPeriodId) throws NotFoundException {
        LOGGER.info("** ReportingPeriodsApi - reportingPeriodsReportingPeriodIdGet");
        ReportingPeriod reportingPeriod = reportingPeriodService.findById(reportingPeriodId);
        if (reportingPeriod == null) {
            throw new NotFoundException();
        }
        return new ResponseEntity<ReportingPeriodDto>(ReportingPeriodDto.mapFromReportingPeriodEntity(reportingPeriod), HttpStatus.OK);
    }
    
    /**
     * The reportingPeriodsReportingPeriodIdPut is used to update
     *
     * @param reportingPeriodId the reportingPeriod ID for the ReportingPeriod object to update
     * @param reportingPeriod the new data for the ReportingPeriod object
     * @return the newly updated ReportingPeriodDto object
     * @throws NotFoundException if nothing is found then the the NotFoundException is thrown.
     * @throws InvalidDataException if there is an issue with the data provided in the RequestBody then an InvalidDataException is thrown
     */
    @ApiOperation(value = "Used to update a ReportingPeriod entity", notes = "A PUT request to the ReportingPeriod instance endpoint with a ReportingPeriod object in the request body will update an existing ReportingPeriod entity in the database.", response = ReportingPeriodDto.class)
    @ApiResponses(value = [
        @ApiResponse(code = 200, message = "Returns a copy of the newly updated ReportingPeriod object."),
        @ApiResponse(code = 400, message = "Returns an Error object stating the request body does not match the expected input."),
        @ApiResponse(code = 404, message = "Returns an Error object stating that the resource could not be found. This happens if the specified id cannot be found in the database.")
    ])
    @RequestMapping(value = "/{reportingPeriodId}", produces = ["application/json"], method = RequestMethod.PUT)
    public ResponseEntity<ReportingPeriodDto> reportingPeriodsReportingPeriodIdPut(
            @ApiParam(value = "The unique ID of the ReportingPeriod to retrieve", required = true) @PathVariable("reportingPeriodId") Integer reportingPeriodId,
            @ApiParam(value = "The ReportingPeriod object to be created, without the reportingPeriodId fields", required = true) @RequestBody ReportingPeriodDto reportingPeriod) throws NotFoundException, InvalidDataException {
        LOGGER.info("** ReportingPeriodsApi - reportingPeriodsPUT");
        if (reportingPeriodId != reportingPeriod.id) {
            throw new InvalidDataException()
        }
        AcademicYear academicYear
        if(reportingPeriod.academicYearId != null){
            academicYear = academicYearService.findById(reportingPeriod.academicYearId)
        }
        ReportingPeriod reportingPeriodToSave = ReportingPeriodDto.mapToReportingPeriodEntity(reportingPeriod, academicYear)
        ReportingPeriod reportingPeriodSaved = reportingPeriodService.save(reportingPeriodToSave)
        return new ResponseEntity<ReportingPeriodDto>(ReportingPeriodDto.mapFromReportingPeriodEntity(reportingPeriodSaved), HttpStatus.OK);
    }
}
