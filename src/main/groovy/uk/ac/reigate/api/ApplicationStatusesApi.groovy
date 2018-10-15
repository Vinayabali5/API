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
import org.springframework.security.access.annotation.Secured
import org.springframework.stereotype.Controller
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestMethod

import uk.ac.reigate.domain.admissions.ApplicationStatus
import uk.ac.reigate.dto.ApplicationStatusDto
import uk.ac.reigate.exceptions.InvalidDataException
import uk.ac.reigate.exceptions.NotFoundException
import uk.ac.reigate.model.SearchResult
import uk.ac.reigate.services.ApplicationStatusService


@Controller
@RequestMapping(value = "/api/applicationStatuses", produces = [ APPLICATION_JSON_VALUE ])
@Api(value = "/api/applicationStatuses", description = "the applicationStatuss API")
public class ApplicationStatusesApi {
    
    private static final Logger LOGGER = Logger.getLogger(ApplicationStatusesApi.class);
    
    @Autowired
    private final ApplicationStatusService applicationStatusService;
    
    /**
     * Default NoArgs constructor
     */
    ApplicationStatusesApi() {}
    
    /**
     * Autowired constructor
     */
    ApplicationStatusesApi(ApplicationStatusService applicationStatusService) {
        this.applicationStatusService = applicationStatusService;
    }
    
    /**
     * The applicationStatusesGet method is used to retrieve a full list of all the ApplicationStatusDto objects
     *
     * @return A ResponseEntity with the corresponding list of ApplicationStatusDto objects
     * @throws NotFoundException if nothing is found then the the NotFoundException is thrown.
     */
    @ApiOperation(value = "Collection of ApplicationStatus entities", notes = "A GET request to the ApplicationStatuses endpoint returns an array of all the applicationStatuses in the system.", response = ApplicationStatusDto.class, responseContainer = "List")
    @ApiResponses(value = [
        @ApiResponse(code = 200, message = "An array of applicationStatuses")
    ])
    @RequestMapping(value = "", produces = ["application/json"], method = RequestMethod.GET)
    public ResponseEntity<List<ApplicationStatusDto>> applicationStatusesGet() throws NotFoundException {
        LOGGER.info("** ApplicationStatusesApi - applicationStatusesGet");
        List<ApplicationStatus> applicationStatuses = applicationStatusService.findAll();
        return new ResponseEntity<List<ApplicationStatusDto>>(ApplicationStatusDto.mapFromApplicationStatusesEntities(applicationStatuses), HttpStatus.OK);
    }
    
    /**
     * The applicationStatusesPost method is used to create a new instance of a ApplicationStatus from the supplied ApplicationStatusDto
     *
     * @param applicationStatus the ApplicationStatusDto to use to create the new ApplicationStatus object
     * @return A ResponseEntity with the newly created ApplicationStatus object
     * @throws InvalidDataException if there is an issue with the data provided in the RequestBody then an InvalidDataException is thrown
     */
    @ApiOperation(value = "Creates a new ApplicationStatus entity", notes = "A POST request to the ApplicationStatuses endpoint with a ApplicationStatus object in the request body will create a new ApplicationStatus entity in the database.", response = ApplicationStatusDto.class)
    @ApiResponses(value = [
        @ApiResponse(code = 201, message = "Returns a copy of the created ApplicationStatus entity including the applicationStatusId that has just been created."),
        @ApiResponse(code = 400, message = "Returns an Error object stating the request body does not match the expected input.")
    ])
    @RequestMapping(value = "", produces = ["application/json"], method = RequestMethod.POST)
    public ResponseEntity<ApplicationStatusDto> applicationStatusesPost(@ApiParam(value = "The ApplicationStatus object to be created, without the applicationStatusId fields", required = true) @RequestBody @Valid ApplicationStatusDto applicationStatus) throws NotFoundException, InvalidDataException {
        LOGGER.info("** ApplicationStatusesApi - applicationStatusesPOST");
        if (applicationStatus.id != null) {
            throw new InvalidDataException(400, "No ID field should be provided when creating")
        }
        ApplicationStatus applicationStatusToSave = ApplicationStatusDto.mapToApplicationStatusEntity(applicationStatus)
        ApplicationStatus applicationStatusSaved = applicationStatusService.save(applicationStatusToSave)
        return new ResponseEntity<ApplicationStatusDto>(ApplicationStatusDto.mapFromApplicationStatusEntity(applicationStatusSaved), HttpStatus.CREATED);
    }
    
    /**
     * The applicationStatusesApplicationStatusIdGet method is used to retrieve an instance of a ApplicationStatusDto object as identified by the applicationStatusId provided
     *
     * @param applicationStatusId the applicationStatus ID for the ApplicationStatus object retrieve
     * @return A ResponseEntity with the corresponding ApplicationStatusDto object
     * @throws NotFoundException if nothing is found then the the NotFoundException is thrown.
     */
    @ApiOperation(value = "Retrieves an indivdual instance of a ApplicationStatus identified by the applicationStatusId", notes = "A getGET request to the ApplicationStatus instance endpoint will retrieve an instance of a ApplicationStatus entity as identified by the applicationStatusId provided in the URI.", response = ApplicationStatusDto.class)
    @ApiResponses(value = [
        @ApiResponse(code = 200, message = "Returns a copy of the ApplicationStatus as identified by the applicationStatusId"),
        @ApiResponse(code = 404, message = "Returns an Error object stating that the resource could not be found. This happens if the specified id cannot be found in the database.")
    ])
    @RequestMapping(value = "/{applicationStatusId}", produces = ["application/json"], method = RequestMethod.GET)
    public ResponseEntity<ApplicationStatusDto> applicationStatusesApplicationStatusIdGet(@ApiParam(value = "The unique ID of the ApplicationStatus to retrieve", required = true) @PathVariable("applicationStatusId") Integer applicationStatusId) throws NotFoundException {
        LOGGER.info("** ApplicationStatusesApi - applicationStatusesApplicationStatusIdGet");
        ApplicationStatus applicationStatus = applicationStatusService.findById(applicationStatusId);
        if (applicationStatus == null) {
            throw new NotFoundException();
        }
        return new ResponseEntity<ApplicationStatusDto>(ApplicationStatusDto.mapFromApplicationStatusEntity(applicationStatus), HttpStatus.OK);
    }
    
    /**
     * The applicationStatusesApplicationStatusIdPut is used to update
     *
     * @param applicationStatusId the applicationStatus ID for the ApplicationStatus object to update
     * @param applicationStatus the new data for the ApplicationStatus object
     * @return the newly updated ApplicationStatusDto object
     * @throws NotFoundException if nothing is found then the the NotFoundException is thrown.
     * @throws InvalidDataException if there is an issue with the data provided in the RequestBody then an InvalidDataException is thrown
     */
    @ApiOperation(value = "Used to update a ApplicationStatus entity", notes = "A PUT request to the ApplicationStatus instance endpoint with a ApplicationStatus object in the request body will update an existing ApplicationStatus entity in the database.", response = ApplicationStatusDto.class)
    @ApiResponses(value = [
        @ApiResponse(code = 200, message = "Returns a copy of the newly updated ApplicationStatus object."),
        @ApiResponse(code = 400, message = "Returns an Error object stating the request body does not match the expected input."),
        @ApiResponse(code = 404, message = "Returns an Error object stating that the resource could not be found. This happens if the specified id cannot be found in the database.")
    ])
    @RequestMapping(value = "/{applicationStatusId}", produces = ["application/json"], method = RequestMethod.PUT)
    public ResponseEntity<ApplicationStatusDto> applicationStatusesApplicationStatusIdPut(
            @ApiParam(value = "The unique ID of the ApplicationStatus to retrieve", required = true) @PathVariable("applicationStatusId") Integer applicationStatusId,
            @ApiParam(value = "The ApplicationStatus object to be created, without the applicationStatusId fields", required = true) @RequestBody ApplicationStatusDto applicationStatus) throws NotFoundException, InvalidDataException {
        LOGGER.info("** ApplicationStatusesApi - applicationStatusesPUT");
        if (applicationStatusId != applicationStatus.id) {
            throw new InvalidDataException()
        }
        ApplicationStatus applicationStatusToSave = ApplicationStatusDto.mapToApplicationStatusEntity(applicationStatus)
        ApplicationStatus applicationStatusSaved = applicationStatusService.updateApplicationStatus(applicationStatusToSave)
        return new ResponseEntity<ApplicationStatusDto>(ApplicationStatusDto.mapFromApplicationStatusEntity(applicationStatusSaved), HttpStatus.OK);
    }
}
