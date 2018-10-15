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
import uk.ac.reigate.domain.academic.InterimReport
import uk.ac.reigate.dto.InterimReportDto
import uk.ac.reigate.exceptions.InvalidDataException
import uk.ac.reigate.exceptions.NotFoundException
import uk.ac.reigate.services.AcademicYearService;
import uk.ac.reigate.model.SearchResult
import uk.ac.reigate.services.InterimReportService


@Controller
@RequestMapping(value = "/api/interimReports", produces = [ APPLICATION_JSON_VALUE ])
@Api(value = "/api/interimReports", description = "the interimReports API")
public class InterimReportsApi {
    
    private static final Logger LOGGER = Logger.getLogger(InterimReportsApi.class);
    
    @Autowired
    private final InterimReportService interimReportService;
    
    @Autowired
    private final AcademicYearService academicYearService;
    
    /**
     * Default NoArgs constructor
     */
    InterimReportsApi() {}
    
    /**
     * Autowired constructor
     */
    InterimReportsApi(InterimReportService interimReportService) {
        this.interimReportService = interimReportService;
    }
    
    /**
     * The interimReportsGet method is used to retrieve a full list of all the InterimReportDto objects
     *
     * @return A ResponseEntity with the corresponding list of InterimReportDto objects
     * @throws NotFoundException if nothing is found then the the NotFoundException is thrown.
     */
    @ApiOperation(value = "Collection of InterimReport entities", notes = "A GET request to the InterimReports endpoint returns an array of all the interimReports in the system.", response = InterimReportDto.class, responseContainer = "List")
    @ApiResponses(value = [
        @ApiResponse(code = 200, message = "An array of interimReports")
    ])
    @RequestMapping(value = "", produces = ["application/json"], method = RequestMethod.GET)
    public ResponseEntity<List<InterimReportDto>> interimReportsGet() throws NotFoundException {
        LOGGER.info("** InterimReportsApi - interimReportsGet");
        List<InterimReport> interimReports = interimReportService.findAll();
        return new ResponseEntity<List<InterimReportDto>>(InterimReportDto.mapFromInterimReportsEntities(interimReports), HttpStatus.OK);
    }
    
    /**
     * The interimReportsPost method is used to create a new instance of a InterimReport from the supplied InterimReportDto
     *
     * @param interimReport the InterimReportDto to use to create the new InterimReport object
     * @return A ResponseEntity with the newly created InterimReport object
     * @throws InvalidDataException if there is an issue with the data provided in the RequestBody then an InvalidDataException is thrown
     */
    @ApiOperation(value = "Creates a new InterimReport entity", notes = "A POST request to the InterimReports endpoint with a InterimReport object in the request body will create a new InterimReport entity in the database.", response = InterimReportDto.class)
    @ApiResponses(value = [
        @ApiResponse(code = 201, message = "Returns a copy of the created InterimReport entity including the interimReportId that has just been created."),
        @ApiResponse(code = 400, message = "Returns an Error object stating the request body does not match the expected input.")
    ])
    @RequestMapping(value = "", produces = ["application/json"], method = RequestMethod.POST)
    public ResponseEntity<InterimReportDto> interimReportsPost(@ApiParam(value = "The InterimReport object to be created, without the interimReportId fields", required = true) @RequestBody @Valid InterimReportDto interimReport) throws NotFoundException, InvalidDataException {
        LOGGER.info("** InterimReportsApi - interimReportsPOST");
        if (interimReport.id != null) {
            throw new InvalidDataException(400, "No ID field should be provided when creating")
        }
        AcademicYear year
        if(interimReport.yearId != null){
            year = academicYearService.findById(interimReport.yearId)
        }
        InterimReport interimReportToSave = InterimReportDto.mapToInterimReportEntity(interimReport, year)
        InterimReport interimReportSaved = interimReportService.save(interimReportToSave)
        return new ResponseEntity<InterimReportDto>(InterimReportDto.mapFromInterimReportEntity(interimReportSaved), HttpStatus.CREATED);
    }
    
    /**
     * The interimReportsInterimReportIdGet method is used to retrieve an instance of a InterimReportDto object as identified by the interimReportId provided
     *
     * @param interimReportId the interimReport ID for the InterimReport object retrieve
     * @return A ResponseEntity with the corresponding InterimReportDto object
     * @throws NotFoundException if nothing is found then the the NotFoundException is thrown.
     */
    @ApiOperation(value = "Retrieves an indivdual instance of a InterimReport identified by the interimReportId", notes = "A getGET request to the InterimReport instance endpoint will retrieve an instance of a InterimReport entity as identified by the interimReportId provided in the URI.", response = InterimReportDto.class)
    @ApiResponses(value = [
        @ApiResponse(code = 200, message = "Returns a copy of the InterimReport as identified by the interimReportId"),
        @ApiResponse(code = 404, message = "Returns an Error object stating that the resource could not be found. This happens if the specified id cannot be found in the database.")
    ])
    @RequestMapping(value = "/{interimReportId}", produces = ["application/json"], method = RequestMethod.GET)
    public ResponseEntity<InterimReportDto> interimReportsInterimReportIdGet(@ApiParam(value = "The unique ID of the InterimReport to retrieve", required = true) @PathVariable("interimReportId") Integer interimReportId) throws NotFoundException {
        LOGGER.info("** InterimReportsApi - interimReportsInterimReportIdGet");
        InterimReport interimReport = interimReportService.findById(interimReportId);
        if (interimReport == null) {
            throw new NotFoundException();
        }
        return new ResponseEntity<InterimReportDto>(InterimReportDto.mapFromInterimReportEntity(interimReport), HttpStatus.OK);
    }
    
    /**
     * The interimReportsInterimReportIdPut is used to update
     *
     * @param interimReportId the interimReport ID for the InterimReport object to update
     * @param interimReport the new data for the InterimReport object
     * @return the newly updated InterimReportDto object
     * @throws NotFoundException if nothing is found then the the NotFoundException is thrown.
     * @throws InvalidDataException if there is an issue with the data provided in the RequestBody then an InvalidDataException is thrown
     */
    @ApiOperation(value = "Used to update a InterimReport entity", notes = "A PUT request to the InterimReport instance endpoint with a InterimReport object in the request body will update an existing InterimReport entity in the database.", response = InterimReportDto.class)
    @ApiResponses(value = [
        @ApiResponse(code = 200, message = "Returns a copy of the newly updated InterimReport object."),
        @ApiResponse(code = 400, message = "Returns an Error object stating the request body does not match the expected input."),
        @ApiResponse(code = 404, message = "Returns an Error object stating that the resource could not be found. This happens if the specified id cannot be found in the database.")
    ])
    @RequestMapping(value = "/{interimReportId}", produces = ["application/json"], method = RequestMethod.PUT)
    public ResponseEntity<InterimReportDto> interimReportsInterimReportIdPut(
            @ApiParam(value = "The unique ID of the InterimReport to retrieve", required = true) @PathVariable("interimReportId") Integer interimReportId,
            @ApiParam(value = "The InterimReport object to be created, without the interimReportId fields", required = true) @RequestBody InterimReportDto interimReport) throws NotFoundException, InvalidDataException {
        LOGGER.info("** InterimReportsApi - interimReportsPUT");
        if (interimReportId != interimReport.id) {
            throw new InvalidDataException()
        }
        AcademicYear year
        if(interimReport.yearId != null){
            year = academicYearService.findById(interimReport.yearId)
        }
        InterimReport interimReportToSave = InterimReportDto.mapToInterimReportEntity(interimReport, year)
        InterimReport interimReportSaved = interimReportService.save(interimReportToSave)
        return new ResponseEntity<InterimReportDto>(InterimReportDto.mapFromInterimReportEntity(interimReportSaved), HttpStatus.OK);
    }
}
