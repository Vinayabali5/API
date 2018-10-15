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

import uk.ac.reigate.domain.NoteType
import uk.ac.reigate.dto.NoteTypeDto
import uk.ac.reigate.exceptions.InvalidDataException
import uk.ac.reigate.exceptions.NotFoundException
import uk.ac.reigate.model.SearchResult
import uk.ac.reigate.services.NoteTypeService


@Controller
@RequestMapping(value = "/api/noteTypes", produces = [ APPLICATION_JSON_VALUE ])
@Api(value = "/api/noteTypes", description = "the noteTypes API")
public class NoteTypesApi {
    
    private static final Logger LOGGER = Logger.getLogger(NoteTypesApi.class);
    
    @Autowired
    private final NoteTypeService noteTypeService;
    
    /**
     * Default NoArgs constructor
     */
    NoteTypesApi() {}
    
    /**
     * Autowired constructor
     */
    NoteTypesApi(NoteTypeService noteTypeService) {
        this.noteTypeService = noteTypeService;
    }
    
    /**
     * The noteTypesGet method is used to retrieve a full list of all the NoteTypeDto objects
     *
     * @return A ResponseEntity with the corresponding list of NoteTypeDto objects
     * @throws NotFoundException if nothing is found then the the NotFoundException is thrown.
     */
    @ApiOperation(value = "Collection of NoteType entities", notes = "A GET request to the NoteTypes endpoint returns an array of all the noteTypes in the system.", response = NoteTypeDto.class, responseContainer = "List")
    @ApiResponses(value = [
        @ApiResponse(code = 200, message = "An array of noteTypes")
    ])
    @RequestMapping(value = "", produces = ["application/json"], method = RequestMethod.GET)
    public ResponseEntity<List<NoteTypeDto>> noteTypesGet() throws NotFoundException {
        LOGGER.info("** NoteTypesApi - noteTypesGet");
        List<NoteType> noteTypes = noteTypeService.findAll();
        return new ResponseEntity<List<NoteTypeDto>>(NoteTypeDto.mapFromNoteTypesEntities(noteTypes), HttpStatus.OK);
    }
    
    /**
     * The noteTypesPost method is used to create a new instance of a NoteType from the supplied NoteTypeDto
     *
     * @param noteType the NoteTypeDto to use to create the new NoteType object
     * @return A ResponseEntity with the newly created NoteType object
     * @throws InvalidDataException if there is an issue with the data provided in the RequestBody then an InvalidDataException is thrown
     */
    @ApiOperation(value = "Creates a new NoteType entity", notes = "A POST request to the NoteTypes endpoint with a NoteType object in the request body will create a new NoteType entity in the database.", response = NoteTypeDto.class)
    @ApiResponses(value = [
        @ApiResponse(code = 201, message = "Returns a copy of the created NoteType entity including the noteTypeId that has just been created."),
        @ApiResponse(code = 400, message = "Returns an Error object stating the request body does not match the expected input.")
    ])
    @RequestMapping(value = "", produces = ["application/json"], method = RequestMethod.POST)
    public ResponseEntity<NoteTypeDto> noteTypesPost(@ApiParam(value = "The NoteType object to be created, without the noteTypeId fields", required = true) @RequestBody @Valid NoteTypeDto noteType) throws NotFoundException, InvalidDataException {
        LOGGER.info("** NoteTypesApi - noteTypesPOST");
        if (noteType.id != null) {
            throw new InvalidDataException(400, "No ID field should be provided when creating")
        }
        NoteType noteTypeToSave = NoteTypeDto.mapToNoteTypeEntity(noteType)
        NoteType noteTypeSaved = noteTypeService.save(noteTypeToSave)
        return new ResponseEntity<NoteTypeDto>(NoteTypeDto.mapFromNoteTypeEntity(noteTypeSaved), HttpStatus.CREATED);
    }
    
    /**
     * The noteTypesNoteTypeIdGet method is used to retrieve an instance of a NoteTypeDto object as identified by the noteTypeId provided
     *
     * @param noteTypeId the noteType ID for the NoteType object retrieve
     * @return A ResponseEntity with the corresponding NoteTypeDto object
     * @throws NotFoundException if nothing is found then the the NotFoundException is thrown.
     */
    @ApiOperation(value = "Retrieves an indivdual instance of a NoteType identified by the noteTypeId", notes = "A getGET request to the NoteType instance endpoint will retrieve an instance of a NoteType entity as identified by the noteTypeId provided in the URI.", response = NoteTypeDto.class)
    @ApiResponses(value = [
        @ApiResponse(code = 200, message = "Returns a copy of the NoteType as identified by the noteTypeId"),
        @ApiResponse(code = 404, message = "Returns an Error object stating that the resource could not be found. This happens if the specified id cannot be found in the database.")
    ])
    @RequestMapping(value = "/{noteTypeId}", produces = ["application/json"], method = RequestMethod.GET)
    public ResponseEntity<NoteTypeDto> noteTypesNoteTypeIdGet(@ApiParam(value = "The unique ID of the NoteType to retrieve", required = true) @PathVariable("noteTypeId") Integer noteTypeId) throws NotFoundException {
        LOGGER.info("** NoteTypesApi - noteTypesNoteTypeIdGet");
        NoteType noteType = noteTypeService.findById(noteTypeId);
        if (noteType == null) {
            throw new NotFoundException();
        }
        return new ResponseEntity<NoteTypeDto>(NoteTypeDto.mapFromNoteTypeEntity(noteType), HttpStatus.OK);
    }
    
    
    /**
     * The noteTypesNoteTypeIdPut is used to update
     *
     * @param noteTypeId the noteType ID for the NoteType object to update
     * @param noteType the new data for the NoteType object
     * @return the newly updated NoteTypeDto object
     * @throws NotFoundException if nothing is found then the the NotFoundException is thrown.
     * @throws InvalidDataException if there is an issue with the data provided in the RequestBody then an InvalidDataException is thrown
     */
    @ApiOperation(value = "Used to update a NoteType entity", notes = "A PUT request to the NoteType instance endpoint with a NoteType object in the request body will update an existing NoteType entity in the database.", response = NoteTypeDto.class)
    @ApiResponses(value = [
        @ApiResponse(code = 200, message = "Returns a copy of the newly updated NoteType object."),
        @ApiResponse(code = 400, message = "Returns an Error object stating the request body does not match the expected input."),
        @ApiResponse(code = 404, message = "Returns an Error object stating that the resource could not be found. This happens if the specified id cannot be found in the database.")
    ])
    @RequestMapping(value = "/{noteTypeId}", produces = ["application/json"], method = RequestMethod.PUT)
    public ResponseEntity<NoteTypeDto> noteTypesNoteTypeIdPut(
            @ApiParam(value = "The unique ID of the NoteType to retrieve", required = true) @PathVariable("noteTypeId") Integer noteTypeId,
            @ApiParam(value = "The NoteType object to be created, without the noteTypeId fields", required = true) @RequestBody NoteTypeDto noteType) throws NotFoundException, InvalidDataException {
        LOGGER.info("** NoteTypesApi - noteTypesPUT");
        if (noteTypeId != noteType.id) {
            throw new InvalidDataException()
        }
        NoteType noteTypeToSave = NoteTypeDto.mapToNoteTypeEntity(noteType)
        NoteType noteTypeSaved = noteTypeService.updateNoteType(noteTypeToSave)
        return new ResponseEntity<NoteTypeDto>(NoteTypeDto.mapFromNoteTypeEntity(noteTypeSaved), HttpStatus.OK);
    }
}
