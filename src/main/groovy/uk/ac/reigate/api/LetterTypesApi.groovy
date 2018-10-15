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

import uk.ac.reigate.domain.ilp.LetterType
import uk.ac.reigate.dto.LetterTypeDto
import uk.ac.reigate.exceptions.InvalidDataException
import uk.ac.reigate.exceptions.NotFoundException
import uk.ac.reigate.model.SearchResult
import uk.ac.reigate.services.LetterTypeService


@Controller
@RequestMapping(value = "/api/letterTypes", produces = [ APPLICATION_JSON_VALUE ])
@Api(value = "/api/letterTypes", description = "the letterTypes API")
public class LetterTypesApi {
    
    private static final Logger LOGGER = Logger.getLogger(LetterTypesApi.class);
    
    @Autowired
    private final LetterTypeService letterTypeService;
    
    /**
     * Default NoArgs constructor
     */
    LetterTypesApi() {}
    
    /**
     * Autowired constructor
     */
    LetterTypesApi(LetterTypeService letterTypeService) {
        this.letterTypeService = letterTypeService;
    }
    
    /**
     * The letterTypesGet method is used to retrieve a full list of all the LetterTypeDto objects
     *
     * @return A ResponseEntity with the corresponding list of LetterTypeDto objects
     * @throws NotFoundException if nothing is found then the the NotFoundException is thrown.
     */
    @ApiOperation(value = "Collection of LetterType entities", notes = "A GET request to the LetterTypes endpoint returns an array of all the letterTypes in the system.", response = LetterTypeDto.class, responseContainer = "List")
    @ApiResponses(value = [
        @ApiResponse(code = 200, message = "An array of letterTypes")
    ])
    @RequestMapping(value = "", produces = ["application/json"], method = RequestMethod.GET)
    public ResponseEntity<List<LetterTypeDto>> letterTypesGet() throws NotFoundException {
        LOGGER.info("** LetterTypesApi - letterTypesGet");
        List<LetterType> letterTypes = letterTypeService.findAll();
        return new ResponseEntity<List<LetterTypeDto>>(LetterTypeDto.mapFromLetterTypesEntities(letterTypes), HttpStatus.OK);
    }
    
    
    
    /**
     * The letterTypesLetterTypeIdGet method is used to retrieve an instance of a LetterTypeDto object as identified by the letterTypeId provided
     *
     * @param letterTypeId the letterType ID for the LetterType object retrieve
     * @return A ResponseEntity with the corresponding LetterTypeDto object
     * @throws NotFoundException if nothing is found then the the NotFoundException is thrown.
     */
    @ApiOperation(value = "Retrieves an indivdual instance of a LetterType identified by the letterTypeId", notes = "A getGET request to the LetterType instance endpoint will retrieve an instance of a LetterType entity as identified by the letterTypeId provided in the URI.", response = LetterTypeDto.class)
    @ApiResponses(value = [
        @ApiResponse(code = 200, message = "Returns a copy of the LetterType as identified by the letterTypeId"),
        @ApiResponse(code = 404, message = "Returns an Error object stating that the resource could not be found. This happens if the specified id cannot be found in the database.")
    ])
    @RequestMapping(value = "/{letterTypeId}", produces = ["application/json"], method = RequestMethod.GET)
    public ResponseEntity<LetterTypeDto> letterTypesLetterTypeIdGet(@ApiParam(value = "The unique ID of the LetterType to retrieve", required = true) @PathVariable("letterTypeId") Integer letterTypeId) throws NotFoundException {
        LOGGER.info("** LetterTypesApi - letterTypesLetterTypeIdGet");
        LetterType letterType = letterTypeService.findById(letterTypeId);
        if (letterType == null) {
            throw new NotFoundException();
        }
        return new ResponseEntity<LetterTypeDto>(LetterTypeDto.mapFromLetterTypeEntity(letterType), HttpStatus.OK);
    }
    
    /**
     * The letterTypesLetterTypeIdPut is used to update
     *
     * @param letterTypeId the letterType ID for the LetterType object to update
     * @param letterType the new data for the LetterType object
     * @return the newly updated LetterTypeDto object
     * @throws NotFoundException if nothing is found then the the NotFoundException is thrown.
     * @throws InvalidDataException if there is an issue with the data provided in the RequestBody then an InvalidDataException is thrown
     */
    @ApiOperation(value = "Used to update a LetterType entity", notes = "A PUT request to the LetterType instance endpoint with a LetterType object in the request body will update an existing LetterType entity in the database.", response = LetterTypeDto.class)
    @ApiResponses(value = [
        @ApiResponse(code = 200, message = "Returns a copy of the newly updated LetterType object."),
        @ApiResponse(code = 400, message = "Returns an Error object stating the request body does not match the expected input."),
        @ApiResponse(code = 404, message = "Returns an Error object stating that the resource could not be found. This happens if the specified id cannot be found in the database.")
    ])
    @RequestMapping(value = "/{letterTypeId}", produces = ["application/json"], method = RequestMethod.PUT)
    public ResponseEntity<LetterTypeDto> letterTypesLetterTypeIdPut(
            @ApiParam(value = "The unique ID of the LetterType to retrieve", required = true) @PathVariable("letterTypeId") Integer letterTypeId,
            @ApiParam(value = "The LetterType object to be created, without the letterTypeId fields", required = true) @RequestBody LetterTypeDto letterType) throws NotFoundException, InvalidDataException {
        LOGGER.info("** LetterTypesApi - letterTypesPUT");
        if (letterTypeId != letterType.id) {
            throw new InvalidDataException()
        }
        LetterType letterTypeToSave = LetterTypeDto.mapToLetterTypeEntity(letterType)
        LetterType letterTypeSaved = letterTypeService.updateLetterType(letterTypeToSave)
        return new ResponseEntity<LetterTypeDto>(LetterTypeDto.mapFromLetterTypeEntity(letterTypeSaved), HttpStatus.OK);
    }
}
