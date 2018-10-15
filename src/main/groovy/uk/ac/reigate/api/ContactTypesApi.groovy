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

import uk.ac.reigate.domain.lookup.ContactType
import uk.ac.reigate.dto.ContactTypeDto
import uk.ac.reigate.exceptions.InvalidDataException
import uk.ac.reigate.exceptions.NotFoundException
import uk.ac.reigate.model.SearchResult
import uk.ac.reigate.services.ContactTypeService


@Controller
@RequestMapping(value = "/api/contactTypes", produces = [ APPLICATION_JSON_VALUE ])
@Api(value = "/api/contactTypes", description = "the contactTypes API")
public class ContactTypesApi {
    
    private static final Logger LOGGER = Logger.getLogger(ContactTypesApi.class);
    
    @Autowired
    private final ContactTypeService contactTypeService;
    
    /**
     * Default NoArgs constructor
     */
    ContactTypesApi() {}
    
    /**
     * Autowired constructor
     */
    ContactTypesApi(ContactTypeService contactTypeService) {
        this.contactTypeService = contactTypeService;
    }
    
    /**
     * The contactTypesGet method is used to retrieve a full list of all the ContactTypeDto objects
     *
     * @return A ResponseEntity with the corresponding list of ContactTypeDto objects
     * @throws NotFoundException if nothing is found then the the NotFoundException is thrown.
     */
    @ApiOperation(value = "Collection of ContactType entities", notes = "A GET request to the ContactTypes endpoint returns an array of all the contactTypes in the system.", response = ContactTypeDto.class, responseContainer = "List")
    @ApiResponses(value = [
        @ApiResponse(code = 200, message = "An array of contactTypes")
    ])
    @RequestMapping(value = "", produces = ["application/json"], method = RequestMethod.GET)
    public ResponseEntity<List<ContactTypeDto>> contactTypesGet() throws NotFoundException {
        LOGGER.info("** ContactTypesApi - contactTypesGet");
        List<ContactType> contactTypes = contactTypeService.findAll();
        return new ResponseEntity<List<ContactTypeDto>>(ContactTypeDto.mapFromContactTypesEntities(contactTypes), HttpStatus.OK);
    }
    
    /**
     * The contactTypesPost method is used to create a new instance of a ContactType from the supplied ContactTypeDto
     *
     * @param contactType the ContactTypeDto to use to create the new ContactType object
     * @return A ResponseEntity with the newly created ContactType object
     * @throws InvalidDataException if there is an issue with the data provided in the RequestBody then an InvalidDataException is thrown
     */
    @ApiOperation(value = "Creates a new ContactType entity", notes = "A POST request to the ContactTypes endpoint with a ContactType object in the request body will create a new ContactType entity in the database.", response = ContactTypeDto.class)
    @ApiResponses(value = [
        @ApiResponse(code = 201, message = "Returns a copy of the created ContactType entity including the contactTypeId that has just been created."),
        @ApiResponse(code = 400, message = "Returns an Error object stating the request body does not match the expected input.")
    ])
    @RequestMapping(value = "", produces = ["application/json"], method = RequestMethod.POST)
    public ResponseEntity<ContactTypeDto> contactTypesPost(@ApiParam(value = "The ContactType object to be created, without the contactTypeId fields", required = true) @RequestBody @Valid ContactTypeDto contactType) throws NotFoundException, InvalidDataException {
        LOGGER.info("** ContactTypesApi - contactTypesPOST");
        if (contactType.id != null) {
            throw new InvalidDataException(400, "No ID field should be provided when creating")
        }
        ContactType contactTypeToSave = ContactTypeDto.mapToContactTypeEntity(contactType)
        ContactType contactTypeSaved = contactTypeService.save(contactTypeToSave)
        return new ResponseEntity<ContactTypeDto>(ContactTypeDto.mapFromContactTypeEntity(contactTypeSaved), HttpStatus.CREATED);
    }
    
    /**
     * The contactTypesContactTypeIdGet method is used to retrieve an instance of a ContactTypeDto object as identified by the contactTypeId provided
     *
     * @param contactTypeId the contactType ID for the ContactType object retrieve
     * @return A ResponseEntity with the corresponding ContactTypeDto object
     * @throws NotFoundException if nothing is found then the the NotFoundException is thrown.
     */
    @ApiOperation(value = "Retrieves an indivdual instance of a ContactType identified by the contactTypeId", notes = "A getGET request to the ContactType instance endpoint will retrieve an instance of a ContactType entity as identified by the contactTypeId provided in the URI.", response = ContactTypeDto.class)
    @ApiResponses(value = [
        @ApiResponse(code = 200, message = "Returns a copy of the ContactType as identified by the contactTypeId"),
        @ApiResponse(code = 404, message = "Returns an Error object stating that the resource could not be found. This happens if the specified id cannot be found in the database.")
    ])
    @RequestMapping(value = "/{contactTypeId}", produces = ["application/json"], method = RequestMethod.GET)
    public ResponseEntity<ContactTypeDto> contactTypesContactTypeIdGet(@ApiParam(value = "The unique ID of the ContactType to retrieve", required = true) @PathVariable("contactTypeId") Integer contactTypeId) throws NotFoundException {
        LOGGER.info("** ContactTypesApi - contactTypesContactTypeIdGet");
        ContactType contactType = contactTypeService.findById(contactTypeId);
        if (contactType == null) {
            throw new NotFoundException();
        }
        return new ResponseEntity<ContactTypeDto>(ContactTypeDto.mapFromContactTypeEntity(contactType), HttpStatus.OK);
    }
    
    /**
     * The contactTypesContactTypeIdPut is used to update
     *
     * @param contactTypeId the contactType ID for the ContactType object to update
     * @param contactType the new data for the ContactType object
     * @return the newly updated ContactTypeDto object
     * @throws NotFoundException if nothing is found then the the NotFoundException is thrown.
     * @throws InvalidDataException if there is an issue with the data provided in the RequestBody then an InvalidDataException is thrown
     */
    @ApiOperation(value = "Used to update a ContactType entity", notes = "A PUT request to the ContactType instance endpoint with a ContactType object in the request body will update an existing ContactType entity in the database.", response = ContactTypeDto.class)
    @ApiResponses(value = [
        @ApiResponse(code = 200, message = "Returns a copy of the newly updated ContactType object."),
        @ApiResponse(code = 400, message = "Returns an Error object stating the request body does not match the expected input."),
        @ApiResponse(code = 404, message = "Returns an Error object stating that the resource could not be found. This happens if the specified id cannot be found in the database.")
    ])
    @RequestMapping(value = "/{contactTypeId}", produces = ["application/json"], method = RequestMethod.PUT)
    public ResponseEntity<ContactTypeDto> contactTypesContactTypeIdPut(
            @ApiParam(value = "The unique ID of the ContactType to retrieve", required = true) @PathVariable("contactTypeId") Integer contactTypeId,
            @ApiParam(value = "The ContactType object to be created, without the contactTypeId fields", required = true) @RequestBody ContactTypeDto contactType) throws NotFoundException, InvalidDataException {
        LOGGER.info("** ContactTypesApi - contactTypesPUT");
        if (contactTypeId != contactType.id) {
            throw new InvalidDataException()
        }
        ContactType contactTypeToSave = ContactTypeDto.mapToContactTypeEntity(contactType)
        ContactType contactTypeSaved = contactTypeService.save(contactTypeToSave)
        return new ResponseEntity<ContactTypeDto>(ContactTypeDto.mapFromContactTypeEntity(contactTypeSaved), HttpStatus.OK);
    }
}
