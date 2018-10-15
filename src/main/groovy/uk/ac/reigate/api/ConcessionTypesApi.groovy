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

import uk.ac.reigate.domain.learning_support.ConcessionType
import uk.ac.reigate.dto.ConcessionTypeDto
import uk.ac.reigate.exceptions.DataAlreadyExistsException
import uk.ac.reigate.exceptions.InvalidDataException
import uk.ac.reigate.exceptions.NotFoundException
import uk.ac.reigate.model.SearchResult
import uk.ac.reigate.services.ConcessionTypeService


@Controller
@RequestMapping(value = "/api/concessionTypes", produces = [ APPLICATION_JSON_VALUE ])
@Api(value = "/api/concessionTypes", description = "the concessionTypes API")
public class ConcessionTypesApi {
    
    private static final Logger LOGGER = Logger.getLogger(ConcessionTypesApi.class);
    
    @Autowired
    private final ConcessionTypeService concessionTypeService;
    
    /**
     * Default NoArgs constructor
     */
    ConcessionTypesApi() {}
    
    /**
     * Autowired constructor
     */
    ConcessionTypesApi(ConcessionTypeService concessionTypeService) {
        this.concessionTypeService = concessionTypeService;
    }
    
    /**
     * The concessionTypesGet method is used to retrieve a full list of all the ConcessionTypeDto objects
     *
     * @return A ResponseEntity with the corresponding list of ConcessionTypeDto objects
     * @throws NotFoundException if nothing is found then the the NotFoundException is thrown.
     */
    @ApiOperation(value = "Collection of ConcessionType entities", notes = "A GET request to the ConcessionTypes endpoint returns an array of all the concessionTypes in the system.", response = ConcessionTypeDto.class, responseContainer = "List")
    @ApiResponses(value = [
        @ApiResponse(code = 200, message = "An array of concessionTypes")
    ])
    @RequestMapping(value = "", produces = ["application/json"], method = RequestMethod.GET)
    public ResponseEntity<List<ConcessionTypeDto>> concessionTypesGet() throws NotFoundException {
        LOGGER.info("** ConcessionTypesApi - concessionTypesGet");
        List<ConcessionType> concessionTypes = concessionTypeService.findAll();
        return new ResponseEntity<List<ConcessionTypeDto>>(ConcessionTypeDto.mapFromConcessionTypesEntities(concessionTypes), HttpStatus.OK);
    }
    
    /**
     * The concessionTypesPost method is used to create a new instance of a ConcessionType from the supplied ConcessionTypeDto
     *
     * @param concessionType the ConcessionTypeDto to use to create the new ConcessionType object
     * @return A ResponseEntity with the newly created ConcessionType object
     * @throws InvalidDataException if there is an issue with the data provided in the RequestBody then an InvalidDataException is thrown
     */
    @ApiOperation(value = "Creates a new ConcessionType entity", notes = "A POST request to the ConcessionTypes endpoint with a ConcessionType object in the request body will create a new ConcessionType entity in the database.", response = ConcessionTypeDto.class)
    @ApiResponses(value = [
        @ApiResponse(code = 201, message = "Returns a copy of the created ConcessionType entity including the concessionTypeId that has just been created."),
        @ApiResponse(code = 400, message = "Returns an Error object stating the request body does not match the expected input.")
    ])
    @RequestMapping(value = "", produces = ["application/json"], method = RequestMethod.POST)
    public ResponseEntity<ConcessionTypeDto> concessionTypesPost(
            @ApiParam(value = "The ConcessionType object to be created, without the concessionTypeId fields", required = true)
            @RequestBody @Valid ConcessionTypeDto concessionType) throws NotFoundException, InvalidDataException, DataAlreadyExistsException {
        LOGGER.info("** ConcessionTypesApi - concessionTypesPOST");
        if (concessionType.id == null) {
            throw new NotFoundException();
        }
        ConcessionType concessionType1 = concessionTypeService.findById(concessionType.id);
        if (concessionType1 != null) {
            throw new DataAlreadyExistsException("An ConcessionType already exists with the supplied ID.");
        }
        ConcessionType concessionTypeToSave = concessionType.toConcessionType() //ConcessionTypeDto.mapToConcessionTypeEntity(concessionType)
        ConcessionType concessionTypeSaved = concessionTypeService.save(concessionTypeToSave)
        return new ResponseEntity<ConcessionTypeDto>(new ConcessionTypeDto(concessionTypeSaved), HttpStatus.CREATED);
    }
    
    /**
     * The concessionTypesConcessionTypeIdGet method is used to retrieve an instance of a ConcessionTypeDto object as identified by the concessionTypeId provided
     *
     * @param concessionTypeId the concessionType ID for the ConcessionType object retrieve
     * @return A ResponseEntity with the corresponding ConcessionTypeDto object
     * @throws NotFoundException if nothing is found then the the NotFoundException is thrown.
     */
    @ApiOperation(value = "Retrieves an indivdual instance of a ConcessionType identified by the concessionTypeId", notes = "A getGET request to the ConcessionType instance endpoint will retrieve an instance of a ConcessionType entity as identified by the concessionTypeId provided in the URI.", response = ConcessionTypeDto.class)
    @ApiResponses(value = [
        @ApiResponse(code = 200, message = "Returns a copy of the ConcessionType as identified by the concessionTypeId"),
        @ApiResponse(code = 404, message = "Returns an Error object stating that the resource could not be found. This happens if the specified id cannot be found in the database.")
    ])
    @RequestMapping(value = "/{concessionTypeId}", produces = ["application/json"], method = RequestMethod.GET)
    public ResponseEntity<ConcessionTypeDto> concessionTypesConcessionTypeIdGet(@ApiParam(value = "The unique ID of the ConcessionType to retrieve", required = true) @PathVariable("concessionTypeId") Integer concessionTypeId) throws NotFoundException {
        LOGGER.info("** ConcessionTypesApi - concessionTypesConcessionTypeIdGet");
        ConcessionType concessionType = concessionTypeService.findById(concessionTypeId);
        if (concessionType == null) {
            throw new NotFoundException();
        }
        return new ResponseEntity<ConcessionTypeDto>(ConcessionTypeDto.mapFromConcessionTypeEntity(concessionType), HttpStatus.OK);
    }
    
    /**
     * The concessionTypesConcessionTypeIdPut is used to update
     *
     * @param concessionTypeId the concessionType ID for the ConcessionType object to update
     * @param concessionType the new data for the ConcessionType object
     * @return the newly updated ConcessionTypeDto object
     * @throws NotFoundException if nothing is found then the the NotFoundException is thrown.
     * @throws InvalidDataException if there is an issue with the data provided in the RequestBody then an InvalidDataException is thrown
     */
    @ApiOperation(value = "Used to update a ConcessionType entity", notes = "A PUT request to the ConcessionType instance endpoint with a ConcessionType object in the request body will update an existing ConcessionType entity in the database.", response = ConcessionTypeDto.class)
    @ApiResponses(value = [
        @ApiResponse(code = 200, message = "Returns a copy of the newly updated ConcessionType object."),
        @ApiResponse(code = 400, message = "Returns an Error object stating the request body does not match the expected input."),
        @ApiResponse(code = 404, message = "Returns an Error object stating that the resource could not be found. This happens if the specified id cannot be found in the database.")
    ])
    @RequestMapping(value = "/{concessionTypeId}", produces = ["application/json"], method = RequestMethod.PUT)
    public ResponseEntity<ConcessionTypeDto> concessionTypesConcessionTypeIdPut(
            @ApiParam(value = "The unique ID of the ConcessionType to retrieve", required = true) @PathVariable("concessionTypeId") Integer concessionTypeId,
            @ApiParam(value = "The ConcessionType object to be created, without the concessionTypeId fields", required = true) @RequestBody ConcessionTypeDto concessionType) throws NotFoundException, InvalidDataException {
        LOGGER.info("** ConcessionTypesApi - concessionTypesPUT");
        if (concessionTypeId != concessionType.id) {
            throw new InvalidDataException()
        }
        ConcessionType concessionTypeToSave = ConcessionTypeDto.mapToConcessionTypeEntity(concessionType)
        ConcessionType concessionTypeSaved = concessionTypeService.updateConcessionType(concessionTypeToSave)
        return new ResponseEntity<ConcessionTypeDto>(ConcessionTypeDto.mapFromConcessionTypeEntity(concessionTypeSaved), HttpStatus.OK);
    }
}
