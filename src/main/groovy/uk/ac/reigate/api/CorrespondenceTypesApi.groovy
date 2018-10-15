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

import uk.ac.reigate.domain.ilp.CorrespondenceType
import uk.ac.reigate.dto.CorrespondenceTypeDto
import uk.ac.reigate.exceptions.InvalidDataException
import uk.ac.reigate.exceptions.NotFoundException
import uk.ac.reigate.model.SearchResult
import uk.ac.reigate.services.CorrespondenceTypeService


@Controller
@RequestMapping(value = "/api/correspondenceTypes", produces = [ APPLICATION_JSON_VALUE ])
@Api(value = "/api/correspondenceTypes", description = "the correspondenceTypes API")
public class CorrespondenceTypesApi {
    
    private static final Logger LOGGER = Logger.getLogger(CorrespondenceTypesApi.class);
    
    @Autowired
    private final CorrespondenceTypeService correspondenceTypeService;
    
    /**
     * Default NoArgs constructor
     */
    CorrespondenceTypesApi() {}
    
    /**
     * Autowired constructor
     */
    CorrespondenceTypesApi(CorrespondenceTypeService correspondenceTypeService) {
        this.correspondenceTypeService = correspondenceTypeService;
    }
    
    /**
     * The correspondenceTypesGet method is used to retrieve a full list of all the CorrespondenceTypeDto objects
     *
     * @return A ResponseEntity with the corresponding list of CorrespondenceTypeDto objects
     * @throws NotFoundException if nothing is found then the the NotFoundException is thrown.
     */
    @ApiOperation(value = "Collection of CorrespondenceType entities", notes = "A GET request to the CorrespondenceTypes endpoint returns an array of all the correspondenceTypes in the system.", response = CorrespondenceTypeDto.class, responseContainer = "List")
    @ApiResponses(value = [
        @ApiResponse(code = 200, message = "An array of correspondenceTypes")
    ])
    @RequestMapping(value = "", produces = ["application/json"], method = RequestMethod.GET)
    public ResponseEntity<List<CorrespondenceTypeDto>> correspondenceTypesGet() throws NotFoundException {
        LOGGER.info("** CorrespondenceTypesApi - correspondenceTypesGet");
        List<CorrespondenceType> correspondenceTypes = correspondenceTypeService.findAll();
        return new ResponseEntity<List<CorrespondenceTypeDto>>(CorrespondenceTypeDto.mapFromCorrespondenceTypesEntities(correspondenceTypes), HttpStatus.OK);
    }
    
    
    /**
     * The correspondenceTypesCorrespondenceTypeIdGet method is used to retrieve an instance of a CorrespondenceTypeDto object as identified by the correspondenceTypeId provided
     *
     * @param correspondenceTypeId the correspondenceType ID for the CorrespondenceType object retrieve
     * @return A ResponseEntity with the corresponding CorrespondenceTypeDto object
     * @throws NotFoundException if nothing is found then the the NotFoundException is thrown.
     */
    @ApiOperation(value = "Retrieves an indivdual instance of a CorrespondenceType identified by the correspondenceTypeId", notes = "A getGET request to the CorrespondenceType instance endpoint will retrieve an instance of a CorrespondenceType entity as identified by the correspondenceTypeId provided in the URI.", response = CorrespondenceTypeDto.class)
    @ApiResponses(value = [
        @ApiResponse(code = 200, message = "Returns a copy of the CorrespondenceType as identified by the correspondenceTypeId"),
        @ApiResponse(code = 404, message = "Returns an Error object stating that the resource could not be found. This happens if the specified id cannot be found in the database.")
    ])
    @RequestMapping(value = "/{correspondenceTypeId}", produces = ["application/json"], method = RequestMethod.GET)
    public ResponseEntity<CorrespondenceTypeDto> correspondenceTypesCorrespondenceTypeIdGet(@ApiParam(value = "The unique ID of the CorrespondenceType to retrieve", required = true) @PathVariable("correspondenceTypeId") Integer correspondenceTypeId) throws NotFoundException {
        LOGGER.info("** CorrespondenceTypesApi - correspondenceTypesCorrespondenceTypeIdGet");
        CorrespondenceType correspondenceType = correspondenceTypeService.findById(correspondenceTypeId);
        if (correspondenceType == null) {
            throw new NotFoundException();
        }
        return new ResponseEntity<CorrespondenceTypeDto>(CorrespondenceTypeDto.mapFromCorrespondenceTypeEntity(correspondenceType), HttpStatus.OK);
    }
    
    /**
     * The correspondenceTypesCorrespondenceTypeIdPut is used to update
     *
     * @param correspondenceTypeId the correspondenceType ID for the CorrespondenceType object to update
     * @param correspondenceType the new data for the CorrespondenceType object
     * @return the newly updated CorrespondenceTypeDto object
     * @throws NotFoundException if nothing is found then the the NotFoundException is thrown.
     * @throws InvalidDataException if there is an issue with the data provided in the RequestBody then an InvalidDataException is thrown
     */
    @ApiOperation(value = "Used to update a CorrespondenceType entity", notes = "A PUT request to the CorrespondenceType instance endpoint with a CorrespondenceType object in the request body will update an existing CorrespondenceType entity in the database.", response = CorrespondenceTypeDto.class)
    @ApiResponses(value = [
        @ApiResponse(code = 200, message = "Returns a copy of the newly updated CorrespondenceType object."),
        @ApiResponse(code = 400, message = "Returns an Error object stating the request body does not match the expected input."),
        @ApiResponse(code = 404, message = "Returns an Error object stating that the resource could not be found. This happens if the specified id cannot be found in the database.")
    ])
    @RequestMapping(value = "/{correspondenceTypeId}", produces = ["application/json"], method = RequestMethod.PUT)
    public ResponseEntity<CorrespondenceTypeDto> correspondenceTypesCorrespondenceTypeIdPut(
            @ApiParam(value = "The unique ID of the CorrespondenceType to retrieve", required = true) @PathVariable("correspondenceTypeId") Integer correspondenceTypeId,
            @ApiParam(value = "The CorrespondenceType object to be created, without the correspondenceTypeId fields", required = true) @RequestBody CorrespondenceTypeDto correspondenceType) throws NotFoundException, InvalidDataException {
        LOGGER.info("** CorrespondenceTypesApi - correspondenceTypesPUT");
        if (correspondenceTypeId != correspondenceType.id) {
            throw new InvalidDataException()
        }
        CorrespondenceType correspondenceTypeToSave = CorrespondenceTypeDto.mapToCorrespondenceTypeEntity(correspondenceType)
        CorrespondenceType correspondenceTypeSaved = correspondenceTypeService.updateCorrespondenceType(correspondenceTypeToSave)
        return new ResponseEntity<CorrespondenceTypeDto>(CorrespondenceTypeDto.mapFromCorrespondenceTypeEntity(correspondenceTypeSaved), HttpStatus.OK);
    }
}
