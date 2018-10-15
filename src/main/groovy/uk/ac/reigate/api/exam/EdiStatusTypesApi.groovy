package uk.ac.reigate.api.exam;

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

import uk.ac.reigate.domain.exam.EdiStatusType
import uk.ac.reigate.dto.exam.EdiStatusTypeDto
import uk.ac.reigate.exceptions.InvalidDataException
import uk.ac.reigate.exceptions.NotFoundException
import uk.ac.reigate.model.SearchResult
import uk.ac.reigate.services.EdiStatusTypeService


@Controller
@RequestMapping(value = "/api/ediStatusTypes", produces = [ APPLICATION_JSON_VALUE ])
@Api(value = "/api/ediStatusTypes", description = "the ediStatusTypes API")
public class EdiStatusTypesApi {
    
    private static final Logger LOGGER = Logger.getLogger(EdiStatusTypesApi.class);
    
    @Autowired
    private final EdiStatusTypeService ediStatusTypeService;
    
    /**
     * Default NoArgs constructor
     */
    EdiStatusTypesApi() {}
    
    /**
     * Autowired constructor
     */
    EdiStatusTypesApi(EdiStatusTypeService ediStatusTypeService) {
        this.ediStatusTypeService = ediStatusTypeService;
    }
    
    /**
     * The ediStatusTypesGet method is used to retrieve a full list of all the EdiStatusTypeDto objects
     *
     * @return A ResponseEntity with the corresponding list of EdiStatusTypeDto objects
     * @throws NotFoundException if nothing is found then the the NotFoundException is thrown.
     */
    @ApiOperation(value = "Collection of EdiStatusType entities", notes = "A GET request to the EdiStatusTypes endpoint returns an array of all the ediStatusTypes in the system.", response = EdiStatusTypeDto.class, responseContainer = "List")
    @ApiResponses(value = [
        @ApiResponse(code = 200, message = "An array of ediStatusTypes")
    ])
    @RequestMapping(value = "", produces = ["application/json"], method = RequestMethod.GET)
    public ResponseEntity<List<EdiStatusTypeDto>> ediStatusTypesGet() throws NotFoundException {
        LOGGER.info("** EdiStatusTypesApi - ediStatusTypesGet");
        List<EdiStatusType> ediStatusTypes = ediStatusTypeService.findAll();
        return new ResponseEntity<List<EdiStatusTypeDto>>(EdiStatusTypeDto.mapFromEdiStatusTypesEntities(ediStatusTypes), HttpStatus.OK);
    }
    
    /**
     * The ediStatusTypesPost method is used to create a new instance of a EdiStatusType from the supplied EdiStatusTypeDto
     *
     * @param ediStatusType the EdiStatusTypeDto to use to create the new EdiStatusType object
     * @return A ResponseEntity with the newly created EdiStatusType object
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
    @ApiOperation(value = "Creates a new EdiStatusType entity", notes = "A POST request to the EdiStatusTypes endpoint with a EdiStatusType object in the request body will create a new EdiStatusType entity in the database.", response = EdiStatusTypeDto.class)
    @ApiResponses(value = [
        @ApiResponse(code = 201, message = "Returns a copy of the created EdiStatusType entity including the ediStatusTypeId that has just been created."),
        @ApiResponse(code = 400, message = "Returns an Error object stating the request body does not match the expected input.")
    ])
    @RequestMapping(value = "", produces = ["application/json"], method = RequestMethod.POST)
    public ResponseEntity<EdiStatusTypeDto> ediStatusTypesPost(@ApiParam(value = "The EdiStatusType object to be created, without the ediStatusTypeId fields", required = true) @RequestBody @Valid EdiStatusTypeDto ediStatusType) throws NotFoundException, InvalidDataException {
        LOGGER.info("** EdiStatusTypesApi - ediStatusTypesPOST");
        if (ediStatusType.id != null) {
            throw new InvalidDataException(400, "No ID field should be provided when creating")
        }
        EdiStatusType ediStatusTypeToSave = EdiStatusTypeDto.mapToEdiStatusTypeEntity(ediStatusType)
        EdiStatusType ediStatusTypeSaved = ediStatusTypeService.save(ediStatusTypeToSave)
        return new ResponseEntity<EdiStatusTypeDto>(EdiStatusTypeDto.mapFromEdiStatusTypeEntity(ediStatusTypeSaved), HttpStatus.CREATED);
    }
    
    /**
     * The ediStatusTypesEdiStatusTypeIdGet method is used to retrieve an instance of a EdiStatusTypeDto object as identified by the ediStatusTypeId provided
     *
     * @param ediStatusTypeId the ediStatusType ID for the EdiStatusType object retrieve
     * @return A ResponseEntity with the corresponding EdiStatusTypeDto object
     * @throws NotFoundException if nothing is found then the the NotFoundException is thrown.
     */
    @ApiOperation(value = "Retrieves an indivdual instance of a EdiStatusType identified by the ediStatusTypeId", notes = "A getGET request to the EdiStatusType instance endpoint will retrieve an instance of a EdiStatusType entity as identified by the ediStatusTypeId provided in the URI.", response = EdiStatusTypeDto.class)
    @ApiResponses(value = [
        @ApiResponse(code = 200, message = "Returns a copy of the EdiStatusType as identified by the ediStatusTypeId"),
        @ApiResponse(code = 404, message = "Returns an Error object stating that the resource could not be found. This happens if the specified id cannot be found in the database.")
    ])
    @RequestMapping(value = "/{ediStatusTypeId}", produces = ["application/json"], method = RequestMethod.GET)
    public ResponseEntity<EdiStatusTypeDto> ediStatusTypesEdiStatusTypeIdGet(@ApiParam(value = "The unique ID of the EdiStatusType to retrieve", required = true) @PathVariable("ediStatusTypeId") Integer ediStatusTypeId) throws NotFoundException {
        LOGGER.info("** EdiStatusTypesApi - ediStatusTypesEdiStatusTypeIdGet");
        EdiStatusType ediStatusType = ediStatusTypeService.findById(ediStatusTypeId);
        if (ediStatusType == null) {
            throw new NotFoundException();
        }
        return new ResponseEntity<EdiStatusTypeDto>(EdiStatusTypeDto.mapFromEdiStatusTypeEntity(ediStatusType), HttpStatus.OK);
    }
    
    /**
     * The ediStatusTypesEdiStatusTypeIdPut is used to update
     *
     * @param ediStatusTypeId the ediStatusType ID for the EdiStatusType object to update
     * @param ediStatusType the new data for the EdiStatusType object
     * @return the newly updated EdiStatusTypeDto object
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
    @ApiOperation(value = "Used to update a EdiStatusType entity", notes = "A PUT request to the EdiStatusType instance endpoint with a EdiStatusType object in the request body will update an existing EdiStatusType entity in the database.", response = EdiStatusTypeDto.class)
    @ApiResponses(value = [
        @ApiResponse(code = 200, message = "Returns a copy of the newly updated EdiStatusType object."),
        @ApiResponse(code = 400, message = "Returns an Error object stating the request body does not match the expected input."),
        @ApiResponse(code = 404, message = "Returns an Error object stating that the resource could not be found. This happens if the specified id cannot be found in the database.")
    ])
    @RequestMapping(value = "/{ediStatusTypeId}", produces = ["application/json"], method = RequestMethod.PUT)
    public ResponseEntity<EdiStatusTypeDto> ediStatusTypesEdiStatusTypeIdPut(
            @ApiParam(value = "The unique ID of the EdiStatusType to retrieve", required = true) @PathVariable("ediStatusTypeId") Integer ediStatusTypeId,
            @ApiParam(value = "The EdiStatusType object to be created, without the ediStatusTypeId fields", required = true) @RequestBody EdiStatusTypeDto ediStatusType) throws NotFoundException, InvalidDataException {
        LOGGER.info("** EdiStatusTypesApi - ediStatusTypesPUT");
        if (ediStatusTypeId != ediStatusType.id) {
            throw new InvalidDataException()
        }
        EdiStatusType ediStatusTypeToSave = EdiStatusTypeDto.mapToEdiStatusTypeEntity(ediStatusType)
        EdiStatusType ediStatusTypeSaved = ediStatusTypeService.updateEdiStatusType(ediStatusTypeToSave)
        return new ResponseEntity<EdiStatusTypeDto>(EdiStatusTypeDto.mapFromEdiStatusTypeEntity(ediStatusTypeSaved), HttpStatus.OK);
    }
}
