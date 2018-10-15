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

import uk.ac.reigate.domain.academic.EntryQualification
import uk.ac.reigate.domain.academic.EntryQualificationType
import uk.ac.reigate.dto.EntryQualificationDto
import uk.ac.reigate.exceptions.InvalidDataException
import uk.ac.reigate.exceptions.NotFoundException
import uk.ac.reigate.model.SearchResult
import uk.ac.reigate.services.EntryQualificationService
import uk.ac.reigate.services.EntryQualificationTypeService


@Controller
@RequestMapping(value = "/api/entryQualifications", produces = [ APPLICATION_JSON_VALUE ])
@Api(value = "/api/entryQualifications", description = "the entryQualifications API")
public class EntryQualificationsApi {
    
    private static final Logger LOGGER = Logger.getLogger(EntryQualificationsApi.class);
    
    @Autowired
    private final EntryQualificationService entryQualificationService;
    
    @Autowired
    private final EntryQualificationTypeService entryQualificationTypeService;
    
    /**
     * Default NoArgs constructor
     */
    EntryQualificationsApi() {}
    
    /**
     * Autowired constructor
     */
    EntryQualificationsApi(EntryQualificationService entryQualificationService) {
        this.entryQualificationService = entryQualificationService;
    }
    
    /**
     * The entryQualificationsGet method is used to retrieve a full list of all the EntryQualificationDto objects
     *
     * @return A ResponseEntity with the corresponding list of EntryQualificationDto objects
     * @throws NotFoundException if nothing is found then the the NotFoundException is thrown.
     */
    @ApiOperation(value = "Collection of EntryQualification entities", notes = "A GET request to the EntryQualifications endpoint returns an array of all the entryQualifications in the system.", response = EntryQualificationDto.class, responseContainer = "List")
    @ApiResponses(value = [
        @ApiResponse(code = 200, message = "An array of entryQualifications")
    ])
    @RequestMapping(value = "", produces = ["application/json"], method = RequestMethod.GET)
    public ResponseEntity<List<EntryQualificationDto>> entryQualificationsGet() throws NotFoundException {
        LOGGER.info("** EntryQualificationsApi - entryQualificationsGet");
        List<EntryQualification> entryQualifications = entryQualificationService.findAll();
        return new ResponseEntity<List<EntryQualificationDto>>(EntryQualificationDto.mapFromEntryQualificationsEntities(entryQualifications), HttpStatus.OK);
    }
    
    /**
     * The entryQualificationsPost method is used to create a new instance of a EntryQualification from the supplied EntryQualificationDto
     *
     * @param entryQualification the EntryQualificationDto to use to create the new EntryQualification object
     * @return A ResponseEntity with the newly created EntryQualification object
     * @throws InvalidDataException if there is an issue with the data provided in the RequestBody then an InvalidDataException is thrown
     */
    @ApiOperation(value = "Creates a new EntryQualification entity", notes = "A POST request to the EntryQualifications endpoint with a EntryQualification object in the request body will create a new EntryQualification entity in the database.", response = EntryQualificationDto.class)
    @ApiResponses(value = [
        @ApiResponse(code = 201, message = "Returns a copy of the created EntryQualification entity including the entryQualificationId that has just been created."),
        @ApiResponse(code = 400, message = "Returns an Error object stating the request body does not match the expected input.")
    ])
    @RequestMapping(value = "", produces = ["application/json"], method = RequestMethod.POST)
    public ResponseEntity<EntryQualificationDto> entryQualificationsPost(@ApiParam(value = "The EntryQualification object to be created, without the entryQualificationId fields", required = true) @RequestBody @Valid EntryQualificationDto entryQualification) throws NotFoundException, InvalidDataException {
        LOGGER.info("** EntryQualificationsApi - entryQualificationsPOST");
        if (entryQualification.id != null) {
            throw new InvalidDataException(400, "No ID field should be provided when creating")
        }
        EntryQualificationType entryQualificationType
        if(entryQualification.entryQualificationTypeId != null){
            entryQualificationType = entryQualificationTypeService.findById(entryQualification.entryQualificationTypeId)
        }
        EntryQualification entryQualificationToSave = EntryQualificationDto.mapToEntryQualificationEntity(entryQualification, entryQualificationType)
        EntryQualification entryQualificationSaved = entryQualificationService.save(entryQualificationToSave)
        return new ResponseEntity<EntryQualificationDto>(EntryQualificationDto.mapFromEntryQualificationEntity(entryQualificationSaved), HttpStatus.CREATED);
    }
    
    /**
     * The entryQualificationsEntryQualificationIdGet method is used to retrieve an instance of a EntryQualificationDto object as identified by the entryQualificationId provided
     *
     * @param entryQualificationId the entryQualification ID for the EntryQualification object retrieve
     * @return A ResponseEntity with the corresponding EntryQualificationDto object
     * @throws NotFoundException if nothing is found then the the NotFoundException is thrown.
     */
    @ApiOperation(value = "Retrieves an indivdual instance of a EntryQualification identified by the entryQualificationId", notes = "A getGET request to the EntryQualification instance endpoint will retrieve an instance of a EntryQualification entity as identified by the entryQualificationId provided in the URI.", response = EntryQualificationDto.class)
    @ApiResponses(value = [
        @ApiResponse(code = 200, message = "Returns a copy of the EntryQualification as identified by the entryQualificationId"),
        @ApiResponse(code = 404, message = "Returns an Error object stating that the resource could not be found. This happens if the specified id cannot be found in the database.")
    ])
    @RequestMapping(value = "/{entryQualificationId}", produces = ["application/json"], method = RequestMethod.GET)
    public ResponseEntity<EntryQualificationDto> entryQualificationsEntryQualificationIdGet(@ApiParam(value = "The unique ID of the EntryQualification to retrieve", required = true) @PathVariable("entryQualificationId") Integer entryQualificationId) throws NotFoundException {
        LOGGER.info("** EntryQualificationsApi - entryQualificationsEntryQualificationIdGet");
        EntryQualification entryQualification = entryQualificationService.findById(entryQualificationId);
        if (entryQualification == null) {
            throw new NotFoundException();
        }
        return new ResponseEntity<EntryQualificationDto>(EntryQualificationDto.mapFromEntryQualificationEntity(entryQualification), HttpStatus.OK);
    }
    
    /**
     * The entryQualificationsEntryQualificationIdPut is used to update
     *
     * @param entryQualificationId the entryQualification ID for the EntryQualification object to update
     * @param entryQualification the new data for the EntryQualification object
     * @return the newly updated EntryQualificationDto object
     * @throws NotFoundException if nothing is found then the the NotFoundException is thrown.
     * @throws InvalidDataException if there is an issue with the data provided in the RequestBody then an InvalidDataException is thrown
     */
    @ApiOperation(value = "Used to update a EntryQualification entity", notes = "A PUT request to the EntryQualification instance endpoint with a EntryQualification object in the request body will update an existing EntryQualification entity in the database.", response = EntryQualificationDto.class)
    @ApiResponses(value = [
        @ApiResponse(code = 200, message = "Returns a copy of the newly updated EntryQualification object."),
        @ApiResponse(code = 400, message = "Returns an Error object stating the request body does not match the expected input."),
        @ApiResponse(code = 404, message = "Returns an Error object stating that the resource could not be found. This happens if the specified id cannot be found in the database.")
    ])
    @RequestMapping(value = "/{entryQualificationId}", produces = ["application/json"], method = RequestMethod.PUT)
    public ResponseEntity<EntryQualificationDto> entryQualificationsEntryQualificationIdPut(
            @ApiParam(value = "The unique ID of the EntryQualification to retrieve", required = true) @PathVariable("entryQualificationId") Integer entryQualificationId,
            @ApiParam(value = "The EntryQualification object to be created, without the entryQualificationId fields", required = true) @RequestBody EntryQualificationDto entryQualification) throws NotFoundException, InvalidDataException {
        LOGGER.info("** EntryQualificationsApi - entryQualificationsPUT");
        if (entryQualificationId != entryQualification.id) {
            throw new InvalidDataException()
        }
        EntryQualificationType entryQualificationType
        if(entryQualification.entryQualificationTypeId != null){
            entryQualificationType = entryQualificationTypeService.findById(entryQualification.entryQualificationTypeId)
        }
        EntryQualification entryQualificationToSave = EntryQualificationDto.mapToEntryQualificationEntity(entryQualification, entryQualificationType)
        EntryQualification entryQualificationSaved = entryQualificationService.save(entryQualificationToSave)
        return new ResponseEntity<EntryQualificationDto>(EntryQualificationDto.mapFromEntryQualificationEntity(entryQualificationSaved), HttpStatus.OK);
    }
}
