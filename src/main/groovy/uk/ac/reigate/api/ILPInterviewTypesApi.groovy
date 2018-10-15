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

import uk.ac.reigate.domain.ilp.ILPInterviewType
import uk.ac.reigate.dto.ILPInterviewTypeDto
import uk.ac.reigate.exceptions.InvalidDataException
import uk.ac.reigate.exceptions.NotFoundException
import uk.ac.reigate.model.SearchResult
import uk.ac.reigate.services.ILPInterviewTypeService


@Controller
@RequestMapping(value = "/api/iLPInterviewTypes", produces = [ APPLICATION_JSON_VALUE ])
@Api(value = "/api/iLPInterviewTypes", description = "the iLPInterviewTypes API")
public class ILPInterviewTypesApi {
    
    private static final Logger LOGGER = Logger.getLogger(ILPInterviewTypesApi.class);
    
    @Autowired
    private final ILPInterviewTypeService iLPInterviewTypeService;
    
    /**
     * Default NoArgs constructor
     */
    ILPInterviewTypesApi() {}
    
    /**
     * Autowired constructor
     */
    ILPInterviewTypesApi(ILPInterviewTypeService iLPInterviewTypeService) {
        this.iLPInterviewTypeService = iLPInterviewTypeService;
    }
    
    /**
     * The iLPInterviewTypesGet method is used to retrieve a full list of all the ILPInterviewTypeDto objects
     *
     * @return A ResponseEntity with the corresponding list of ILPInterviewTypeDto objects
     * @throws NotFoundException if nothing is found then the the NotFoundException is thrown.
     */
    @ApiOperation(value = "Collection of ILPInterviewType entities", notes = "A GET request to the ILPInterviewTypes endpoint returns an array of all the iLPInterviewTypes in the system.", response = ILPInterviewTypeDto.class, responseContainer = "List")
    @ApiResponses(value = [
        @ApiResponse(code = 200, message = "An array of iLPInterviewTypes")
    ])
    @RequestMapping(value = "", produces = ["application/json"], method = RequestMethod.GET)
    public ResponseEntity<List<ILPInterviewTypeDto>> iLPInterviewTypesGet() throws NotFoundException {
        LOGGER.info("** ILPInterviewTypesApi - iLPInterviewTypesGet");
        List<ILPInterviewType> iLPInterviewTypes = iLPInterviewTypeService.findAll();
        return new ResponseEntity<List<ILPInterviewTypeDto>>(ILPInterviewTypeDto.mapFromILPInterviewTypesEntities(iLPInterviewTypes), HttpStatus.OK);
    }
    
    
    
    /**
     * The iLPInterviewTypesILPInterviewTypeIdGet method is used to retrieve an instance of a ILPInterviewTypeDto object as identified by the iLPInterviewTypeId provided
     *
     * @param iLPInterviewTypeId the iLPInterviewType ID for the ILPInterviewType object retrieve
     * @return A ResponseEntity with the corresponding ILPInterviewTypeDto object
     * @throws NotFoundException if nothing is found then the the NotFoundException is thrown.
     */
    @ApiOperation(value = "Retrieves an indivdual instance of a ILPInterviewType identified by the iLPInterviewTypeId", notes = "A getGET request to the ILPInterviewType instance endpoint will retrieve an instance of a ILPInterviewType entity as identified by the iLPInterviewTypeId provided in the URI.", response = ILPInterviewTypeDto.class)
    @ApiResponses(value = [
        @ApiResponse(code = 200, message = "Returns a copy of the ILPInterviewType as identified by the iLPInterviewTypeId"),
        @ApiResponse(code = 404, message = "Returns an Error object stating that the resource could not be found. This happens if the specified id cannot be found in the database.")
    ])
    @RequestMapping(value = "/{iLPInterviewTypeId}", produces = ["application/json"], method = RequestMethod.GET)
    public ResponseEntity<ILPInterviewTypeDto> iLPInterviewTypesILPInterviewTypeIdGet(@ApiParam(value = "The unique ID of the ILPInterviewType to retrieve", required = true) @PathVariable("iLPInterviewTypeId") Integer iLPInterviewTypeId) throws NotFoundException {
        LOGGER.info("** ILPInterviewTypesApi - iLPInterviewTypesILPInterviewTypeIdGet");
        ILPInterviewType iLPInterviewType = iLPInterviewTypeService.findById(iLPInterviewTypeId);
        if (iLPInterviewType == null) {
            throw new NotFoundException();
        }
        return new ResponseEntity<ILPInterviewTypeDto>(ILPInterviewTypeDto.mapFromILPInterviewTypeEntity(iLPInterviewType), HttpStatus.OK);
    }
    
    /**
     * The iLPInterviewTypesILPInterviewTypeIdPut is used to update
     *
     * @param iLPInterviewTypeId the iLPInterviewType ID for the ILPInterviewType object to update
     * @param iLPInterviewType the new data for the ILPInterviewType object
     * @return the newly updated ILPInterviewTypeDto object
     * @throws NotFoundException if nothing is found then the the NotFoundException is thrown.
     * @throws InvalidDataException if there is an issue with the data provided in the RequestBody then an InvalidDataException is thrown
     */
    @ApiOperation(value = "Used to update a ILPInterviewType entity", notes = "A PUT request to the ILPInterviewType instance endpoint with a ILPInterviewType object in the request body will update an existing ILPInterviewType entity in the database.", response = ILPInterviewTypeDto.class)
    @ApiResponses(value = [
        @ApiResponse(code = 200, message = "Returns a copy of the newly updated ILPInterviewType object."),
        @ApiResponse(code = 400, message = "Returns an Error object stating the request body does not match the expected input."),
        @ApiResponse(code = 404, message = "Returns an Error object stating that the resource could not be found. This happens if the specified id cannot be found in the database.")
    ])
    @RequestMapping(value = "/{iLPInterviewTypeId}", produces = ["application/json"], method = RequestMethod.PUT)
    public ResponseEntity<ILPInterviewTypeDto> iLPInterviewTypesILPInterviewTypeIdPut(
            @ApiParam(value = "The unique ID of the ILPInterviewType to retrieve", required = true) @PathVariable("iLPInterviewTypeId") Integer iLPInterviewTypeId,
            @ApiParam(value = "The ILPInterviewType object to be created, without the iLPInterviewTypeId fields", required = true) @RequestBody ILPInterviewTypeDto iLPInterviewType) throws NotFoundException, InvalidDataException {
        LOGGER.info("** ILPInterviewTypesApi - iLPInterviewTypesPUT");
        if (iLPInterviewTypeId != iLPInterviewType.id) {
            throw new InvalidDataException()
        }
        ILPInterviewType iLPInterviewTypeToSave = ILPInterviewTypeDto.mapToILPInterviewTypeEntity(iLPInterviewType)
        ILPInterviewType iLPInterviewTypeSaved = iLPInterviewTypeService.updateILPInterviewType(iLPInterviewTypeToSave)
        return new ResponseEntity<ILPInterviewTypeDto>(ILPInterviewTypeDto.mapFromILPInterviewTypeEntity(iLPInterviewTypeSaved), HttpStatus.OK);
    }
}
