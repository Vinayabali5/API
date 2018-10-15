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

import uk.ac.reigate.domain.learning_support.SupportType
import uk.ac.reigate.dto.SupportTypeDto
import uk.ac.reigate.exceptions.DataAlreadyExistsException
import uk.ac.reigate.exceptions.InvalidDataException
import uk.ac.reigate.exceptions.NotFoundException
import uk.ac.reigate.model.SearchResult
import uk.ac.reigate.services.SupportTypeService


@Controller
@RequestMapping(value = "/api/supportTypes", produces = [ APPLICATION_JSON_VALUE ])
@Api(value = "/api/supportTypes", description = "the supportTypes API")
public class SupportTypesApi {
    
    private static final Logger LOGGER = Logger.getLogger(SupportTypesApi.class);
    
    @Autowired
    private final SupportTypeService supportTypeService;
    
    /**
     * Default NoArgs constructor
     */
    SupportTypesApi() {}
    
    /**
     * Autowired constructor
     */
    SupportTypesApi(SupportTypeService supportTypeService) {
        this.supportTypeService = supportTypeService;
    }
    
    /**
     * The supportTypesGet method is used to retrieve a full list of all the SupportTypeDto objects
     *
     * @return A ResponseEntity with the corresponding list of SupportTypeDto objects
     * @throws NotFoundException if nothing is found then the the NotFoundException is thrown.
     */
    @ApiOperation(value = "Collection of SupportType entities", notes = "A GET request to the SupportTypes endpoint returns an array of all the supportTypes in the system.", response = SupportTypeDto.class, responseContainer = "List")
    @ApiResponses(value = [
        @ApiResponse(code = 200, message = "An array of supportTypes")
    ])
    @RequestMapping(value = "", produces = ["application/json"], method = RequestMethod.GET)
    public ResponseEntity<List<SupportTypeDto>> supportTypesGet() throws NotFoundException {
        LOGGER.info("** SupportTypesApi - supportTypesGet");
        List<SupportType> supportTypes = supportTypeService.findAll();
        return new ResponseEntity<List<SupportTypeDto>>(SupportTypeDto.mapFromSupportTypesEntities(supportTypes), HttpStatus.OK);
    }
    
    /**
     * The supportTypesPost method is used to create a new instance of a SupportType from the supplied SupportTypeDto
     *
     * @param supportType the SupportTypeDto to use to create the new SupportType object
     * @return A ResponseEntity with the newly created SupportType object
     * @throws InvalidDataException if there is an issue with the data provided in the RequestBody then an InvalidDataException is thrown
     */
    @ApiOperation(value = "Creates a new SupportType entity", notes = "A POST request to the SupportTypes endpoint with a SupportType object in the request body will create a new SupportType entity in the database.", response = SupportTypeDto.class)
    @ApiResponses(value = [
        @ApiResponse(code = 201, message = "Returns a copy of the created SupportType entity including the supportTypeId that has just been created."),
        @ApiResponse(code = 400, message = "Returns an Error object stating the request body does not match the expected input.")
    ])
    @RequestMapping(value = "", produces = ["application/json"], method = RequestMethod.POST)
    public ResponseEntity<SupportTypeDto> supportTypesPost(
            @ApiParam(value = "The SupportType object to be created, without the supportTypeId fields", required = true)
            @RequestBody @Valid SupportTypeDto supportType) throws NotFoundException, InvalidDataException, DataAlreadyExistsException {
        LOGGER.info("** SupportTypesApi - supportTypesPOST");
        if (supportType.id == null) {
            throw new NotFoundException()
        }
        SupportType supportType1 = supportTypeService.findById(supportType.id);
        if (supportType1 != null) {
            throw new DataAlreadyExistsException("An SupportType already exists with the supplied ID.");
        }
        SupportType supportTypeToSave = supportType.toSupportType()
        SupportType supportTypeSaved = supportTypeService.save(supportTypeToSave)
        return new ResponseEntity<SupportTypeDto>(new SupportTypeDto(supportTypeSaved), HttpStatus.CREATED);
    }
    
    /**
     * The supportTypesSupportTypeIdGet method is used to retrieve an instance of a SupportTypeDto object as identified by the supportTypeId provided
     *
     * @param supportTypeId the supportType ID for the SupportType object retrieve
     * @return A ResponseEntity with the corresponding SupportTypeDto object
     * @throws NotFoundException if nothing is found then the the NotFoundException is thrown.
     */
    @ApiOperation(value = "Retrieves an indivdual instance of a SupportType identified by the supportTypeId", notes = "A getGET request to the SupportType instance endpoint will retrieve an instance of a SupportType entity as identified by the supportTypeId provided in the URI.", response = SupportTypeDto.class)
    @ApiResponses(value = [
        @ApiResponse(code = 200, message = "Returns a copy of the SupportType as identified by the supportTypeId"),
        @ApiResponse(code = 404, message = "Returns an Error object stating that the resource could not be found. This happens if the specified id cannot be found in the database.")
    ])
    @RequestMapping(value = "/{supportTypeId}", produces = ["application/json"], method = RequestMethod.GET)
    public ResponseEntity<SupportTypeDto> supportTypesSupportTypeIdGet(@ApiParam(value = "The unique ID of the SupportType to retrieve", required = true) @PathVariable("supportTypeId") Integer supportTypeId) throws NotFoundException {
        LOGGER.info("** SupportTypesApi - supportTypesSupportTypeIdGet");
        SupportType supportType = supportTypeService.findById(supportTypeId);
        if (supportType == null) {
            throw new NotFoundException();
        }
        return new ResponseEntity<SupportTypeDto>(SupportTypeDto.mapFromSupportTypeEntity(supportType), HttpStatus.OK);
    }
    
    /**
     * The supportTypesSupportTypeIdPut is used to update
     *
     * @param supportTypeId the supportType ID for the SupportType object to update
     * @param supportType the new data for the SupportType object
     * @return the newly updated SupportTypeDto object
     * @throws NotFoundException if nothing is found then the the NotFoundException is thrown.
     * @throws InvalidDataException if there is an issue with the data provided in the RequestBody then an InvalidDataException is thrown
     */
    @ApiOperation(value = "Used to update a SupportType entity", notes = "A PUT request to the SupportType instance endpoint with a SupportType object in the request body will update an existing SupportType entity in the database.", response = SupportTypeDto.class)
    @ApiResponses(value = [
        @ApiResponse(code = 200, message = "Returns a copy of the newly updated SupportType object."),
        @ApiResponse(code = 400, message = "Returns an Error object stating the request body does not match the expected input."),
        @ApiResponse(code = 404, message = "Returns an Error object stating that the resource could not be found. This happens if the specified id cannot be found in the database.")
    ])
    @RequestMapping(value = "/{supportTypeId}", produces = ["application/json"], method = RequestMethod.PUT)
    public ResponseEntity<SupportTypeDto> supportTypesSupportTypeIdPut(
            @ApiParam(value = "The unique ID of the SupportType to retrieve", required = true) @PathVariable("supportTypeId") Integer supportTypeId,
            @ApiParam(value = "The SupportType object to be created, without the supportTypeId fields", required = true) @RequestBody SupportTypeDto supportType) throws NotFoundException, InvalidDataException {
        LOGGER.info("** SupportTypesApi - supportTypesPUT");
        if (supportTypeId != supportType.id) {
            throw new InvalidDataException()
        }
        SupportType supportTypeToSave = SupportTypeDto.mapToSupportTypeEntity(supportType)
        SupportType supportTypeSaved = supportTypeService.updateSupportType(supportTypeToSave)
        return new ResponseEntity<SupportTypeDto>(SupportTypeDto.mapFromSupportTypeEntity(supportTypeSaved), HttpStatus.OK);
    }
}
