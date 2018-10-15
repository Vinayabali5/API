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

import uk.ac.reigate.domain.ilr.AimType
import uk.ac.reigate.dto.AimTypeDto
import uk.ac.reigate.exceptions.InvalidDataException
import uk.ac.reigate.exceptions.NotFoundException
import uk.ac.reigate.model.SearchResult
import uk.ac.reigate.services.AimTypeService


@Controller
@RequestMapping(value = "/api/aimTypes", produces = [ APPLICATION_JSON_VALUE ])
@Api(value = "/api/aimTypes", description = "the aimTypes API")
public class AimTypesApi {
    
    private static final Logger LOGGER = Logger.getLogger(AimTypesApi.class);
    
    @Autowired
    private final AimTypeService aimTypeService;
    
    /**
     * Default NoArgs constructor
     */
    AimTypesApi() {}
    
    /**
     * Autowired constructor
     */
    AimTypesApi(AimTypeService aimTypeService) {
        this.aimTypeService = aimTypeService;
    }
    
    /**
     * The aimTypesGet method is used to retrieve a full list of all the AimTypeDto objects
     *
     * @return A ResponseEntity with the corresponding list of AimTypeDto objects
     * @throws NotFoundException if nothing is found then the the NotFoundException is thrown.
     */
    @ApiOperation(value = "Collection of AimType entities", notes = "A GET request to the AimTypes endpoint returns an array of all the aimTypes in the system.", response = AimTypeDto.class, responseContainer = "List")
    @ApiResponses(value = [
        @ApiResponse(code = 200, message = "An array of aimTypes")
    ])
    @RequestMapping(value = "", produces = ["application/json"], method = RequestMethod.GET)
    public ResponseEntity<List<AimTypeDto>> aimTypesGet() throws NotFoundException {
        LOGGER.info("** AimTypesApi - aimTypesGet");
        List<AimType> aimTypes = aimTypeService.findAll();
        return new ResponseEntity<List<AimTypeDto>>(AimTypeDto.mapFromAimTypesEntities(aimTypes), HttpStatus.OK);
    }
    
    /**
     * The aimTypesPost method is used to create a new instance of a AimType from the supplied AimTypeDto
     *
     * @param aimType the AimTypeDto to use to create the new AimType object
     * @return A ResponseEntity with the newly created AimType object
     * @throws InvalidDataException if there is an issue with the data provided in the RequestBody then an InvalidDataException is thrown
     */
    @ApiOperation(value = "Creates a new AimType entity", notes = "A POST request to the AimTypes endpoint with a AimType object in the request body will create a new AimType entity in the database.", response = AimTypeDto.class)
    @ApiResponses(value = [
        @ApiResponse(code = 201, message = "Returns a copy of the created AimType entity including the aimTypeId that has just been created."),
        @ApiResponse(code = 400, message = "Returns an Error object stating the request body does not match the expected input.")
    ])
    @RequestMapping(value = "", produces = ["application/json"], method = RequestMethod.POST)
    public ResponseEntity<AimTypeDto> aimTypesPost(
            @ApiParam(value = "The AimType object to be created, without the aimTypeId fields", required = true)
            @RequestBody @Valid AimTypeDto aimTypeDto
    ) throws NotFoundException, InvalidDataException {
        LOGGER.info("** AimTypesApi - aimTypesPOST");
        if (aimTypeDto.id != null) {
            throw new InvalidDataException(400, "No ID field should be provided when creating")
        }
        AimType aimTypeToSave = AimTypeDto.mapToAimTypeEntity(aimTypeDto)
        AimType aimTypeSaved = aimTypeService.save(aimTypeToSave)
        return new ResponseEntity<AimTypeDto>(AimTypeDto.mapFromAimTypeEntity(aimTypeSaved), HttpStatus.CREATED);
    }
    
    /**
     * The aimTypesAimTypeIdGet method is used to retrieve an instance of a AimTypeDto object as identified by the aimTypeId provided
     *
     * @param aimTypeId the aimType ID for the AimType object retrieve
     * @return A ResponseEntity with the corresponding AimTypeDto object
     * @throws NotFoundException if nothing is found then the the NotFoundException is thrown.
     */
    @ApiOperation(value = "Retrieves an indivdual instance of a AimType identified by the aimTypeId", notes = "A getGET request to the AimType instance endpoint will retrieve an instance of a AimType entity as identified by the aimTypeId provided in the URI.", response = AimTypeDto.class)
    @ApiResponses(value = [
        @ApiResponse(code = 200, message = "Returns a copy of the AimType as identified by the aimTypeId"),
        @ApiResponse(code = 404, message = "Returns an Error object stating that the resource could not be found. This happens if the specified id cannot be found in the database.")
    ])
    @RequestMapping(value = "/{aimTypeId}", produces = ["application/json"], method = RequestMethod.GET)
    public ResponseEntity<AimTypeDto> aimTypesAimTypeIdGet(
            @ApiParam(value = "The unique ID of the AimType to retrieve", required = true) @PathVariable("aimTypeId") Integer aimTypeId
    ) throws NotFoundException {
        LOGGER.info("** AimTypesApi - aimTypesAimTypeIdGet");
        AimType aimType = aimTypeService.findById(aimTypeId);
        if (aimType == null) {
            throw new NotFoundException();
        }
        return new ResponseEntity<AimTypeDto>(AimTypeDto.mapFromAimTypeEntity(aimType), HttpStatus.OK);
    }
    
    /**
     * The aimTypesSubjectIdPut is used to update
     *
     * @param aimTypeId the aimType ID for the AimType object to update
     * @param aimType the new data for the AimType object
     * @return the newly updated AimTypeDto object
     * @throws NotFoundException if nothing is found then the the NotFoundException is thrown.
     * @throws InvalidDataException if there is an issue with the data provided in the RequestBody then an InvalidDataException is thrown
     */
    @ApiOperation(value = "Used to update a AimType entity", notes = "A PUT request to the AimType instance endpoint with a AimType object in the request body will update an existing AimType entity in the database.", response = AimTypeDto.class)
    @ApiResponses(value = [
        @ApiResponse(code = 200, message = "Returns a copy of the newly updated AimType object."),
        @ApiResponse(code = 400, message = "Returns an Error object stating the request body does not match the expected input."),
        @ApiResponse(code = 404, message = "Returns an Error object stating that the resource could not be found. This happens if the specified id cannot be found in the database.")
    ])
    @RequestMapping(value = "/{aimTypeId}", produces = ["application/json"], method = RequestMethod.PUT)
    public ResponseEntity<AimTypeDto> aimTypesAimTypeIdPut(
            @ApiParam(value = "The unique ID of the AimType to retrieve", required = true) @PathVariable("aimTypeId") Integer aimTypeId,
            @ApiParam(value = "The AimType object to be created, without the aimTypeId fields", required = true) @RequestBody AimTypeDto aimType) throws NotFoundException, InvalidDataException {
        LOGGER.info("** AimTypesApi - aimTypePUT");
        if (aimTypeId != aimType.id) {
            throw new InvalidDataException()
        }
        AimType aimTypeToSave = AimTypeDto.mapToAimTypeEntity(aimType)
        AimType aimTypeSaved = aimTypeService.save(aimTypeToSave)
        return new ResponseEntity<AimTypeDto>(AimTypeDto.mapFromAimTypeEntity(aimTypeSaved), HttpStatus.OK);
    }
}
