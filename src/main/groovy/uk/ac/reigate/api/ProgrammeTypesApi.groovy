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

import uk.ac.reigate.domain.ilr.ProgrammeType
import uk.ac.reigate.dto.ProgrammeTypeDto
import uk.ac.reigate.exceptions.InvalidDataException
import uk.ac.reigate.exceptions.NotFoundException
import uk.ac.reigate.model.SearchResult
import uk.ac.reigate.services.ProgrammeTypeService


@Controller
@RequestMapping(value = "/api/programmeTypes", produces = [ APPLICATION_JSON_VALUE ])
@Api(value = "/api/programmeTypes", description = "the programmeTypes API")
public class ProgrammeTypesApi {
    
    private static final Logger LOGGER = Logger.getLogger(ProgrammeTypesApi.class);
    
    @Autowired
    private final ProgrammeTypeService programmeTypeService;
    
    /**
     * Default NoArgs constructor
     */
    ProgrammeTypesApi() {}
    
    /**
     * Autowired constructor
     */
    ProgrammeTypesApi(ProgrammeTypeService programmeTypeService) {
        this.programmeTypeService = programmeTypeService;
    }
    
    /**
     * The programmeTypesGet method is used to retrieve a full list of all the ProgrammeTypeDto objects
     *
     * @return A ResponseEntity with the corresponding list of ProgrammeTypeDto objects
     * @throws NotFoundException if nothing is found then the the NotFoundException is thrown.
     */
    @ApiOperation(value = "Collection of ProgrammeType entities", notes = "A GET request to the ProgrammeTypes endpoint returns an array of all the programmeTypes in the system.", response = ProgrammeTypeDto.class, responseContainer = "List")
    @ApiResponses(value = [
        @ApiResponse(code = 200, message = "An array of programmeTypes")
    ])
    @RequestMapping(value = "", produces = ["application/json"], method = RequestMethod.GET)
    public ResponseEntity<List<ProgrammeTypeDto>> programmeTypesGet() throws NotFoundException {
        LOGGER.info("** ProgrammeTypesApi - programmeTypesGet");
        List<ProgrammeType> programmeTypes = programmeTypeService.findAll();
        return new ResponseEntity<List<ProgrammeTypeDto>>(ProgrammeTypeDto.mapFromProgrammeTypesEntities(programmeTypes), HttpStatus.OK);
    }
    
    /**
     * The programmeTypesPost method is used to create a new instance of a ProgrammeType from the supplied ProgrammeTypeDto
     *
     * @param programmeType the ProgrammeTypeDto to use to create the new ProgrammeType object
     * @return A ResponseEntity with the newly created ProgrammeType object
     * @throws InvalidDataException if there is an issue with the data provided in the RequestBody then an InvalidDataException is thrown
     */
    @ApiOperation(value = "Creates a new ProgrammeType entity", notes = "A POST request to the ProgrammeTypes endpoint with a ProgrammeType object in the request body will create a new ProgrammeType entity in the database.", response = ProgrammeTypeDto.class)
    @ApiResponses(value = [
        @ApiResponse(code = 201, message = "Returns a copy of the created ProgrammeType entity including the programmeTypeId that has just been created."),
        @ApiResponse(code = 400, message = "Returns an Error object stating the request body does not match the expected input.")
    ])
    @RequestMapping(value = "", produces = ["application/json"], method = RequestMethod.POST)
    public ResponseEntity<ProgrammeTypeDto> programmeTypesPost(@ApiParam(value = "The ProgrammeType object to be created, without the programmeTypeId fields", required = true) @RequestBody @Valid ProgrammeTypeDto programmeType) throws NotFoundException, InvalidDataException {
        LOGGER.info("** ProgrammeTypesApi - programmeTypesPOST");
        if (programmeType.id != null) {
            throw new InvalidDataException(400, "No ID field should be provided when creating")
        }
        ProgrammeType programmeTypeToSave = ProgrammeTypeDto.mapToProgrammeTypeEntity(programmeType)
        ProgrammeType programmeTypeSaved = programmeTypeService.save(programmeTypeToSave)
        return new ResponseEntity<ProgrammeTypeDto>(ProgrammeTypeDto.mapFromProgrammeTypeEntity(programmeTypeSaved), HttpStatus.CREATED);
    }
    
    /**
     * The programmeTypesProgrammeTypeIdGet method is used to retrieve an instance of a ProgrammeTypeDto object as identified by the programmeTypeId provided
     *
     * @param programmeTypeId the programmeType ID for the ProgrammeType object retrieve
     * @return A ResponseEntity with the corresponding ProgrammeTypeDto object
     * @throws NotFoundException if nothing is found then the the NotFoundException is thrown.
     */
    @ApiOperation(value = "Retrieves an indivdual instance of a ProgrammeType identified by the programmeTypeId", notes = "A getGET request to the ProgrammeType instance endpoint will retrieve an instance of a ProgrammeType entity as identified by the programmeTypeId provided in the URI.", response = ProgrammeTypeDto.class)
    @ApiResponses(value = [
        @ApiResponse(code = 200, message = "Returns a copy of the ProgrammeType as identified by the programmeTypeId"),
        @ApiResponse(code = 404, message = "Returns an Error object stating that the resource could not be found. This happens if the specified id cannot be found in the database.")
    ])
    @RequestMapping(value = "/{programmeTypeId}", produces = ["application/json"], method = RequestMethod.GET)
    public ResponseEntity<ProgrammeTypeDto> programmeTypesProgrammeTypeIdGet(@ApiParam(value = "The unique ID of the ProgrammeType to retrieve", required = true) @PathVariable("programmeTypeId") Integer programmeTypeId) throws NotFoundException {
        LOGGER.info("** ProgrammeTypesApi - programmeTypesProgrammeTypeIdGet");
        ProgrammeType programmeType = programmeTypeService.findById(programmeTypeId);
        if (programmeType == null) {
            throw new NotFoundException();
        }
        return new ResponseEntity<ProgrammeTypeDto>(ProgrammeTypeDto.mapFromProgrammeTypeEntity(programmeType), HttpStatus.OK);
    }
    
    /**
     * The programmeTypesProgrammeTypeIdPut is used to update
     *
     * @param programmeTypeId the programmeType ID for the ProgrammeType object to update
     * @param programmeType the new data for the ProgrammeType object
     * @return the newly updated ProgrammeTypeDto object
     * @throws NotFoundException if nothing is found then the the NotFoundException is thrown.
     * @throws InvalidDataException if there is an issue with the data provided in the RequestBody then an InvalidDataException is thrown
     */
    @ApiOperation(value = "Used to update a ProgrammeType entity", notes = "A PUT request to the ProgrammeType instance endpoint with a ProgrammeType object in the request body will update an existing ProgrammeType entity in the database.", response = ProgrammeTypeDto.class)
    @ApiResponses(value = [
        @ApiResponse(code = 200, message = "Returns a copy of the newly updated ProgrammeType object."),
        @ApiResponse(code = 400, message = "Returns an Error object stating the request body does not match the expected input."),
        @ApiResponse(code = 404, message = "Returns an Error object stating that the resource could not be found. This happens if the specified id cannot be found in the database.")
    ])
    @RequestMapping(value = "/{programmeTypeId}", produces = ["application/json"], method = RequestMethod.PUT)
    public ResponseEntity<ProgrammeTypeDto> programmeTypesProgrammeTypeIdPut(
            @ApiParam(value = "The unique ID of the ProgrammeType to retrieve", required = true) @PathVariable("programmeTypeId") Integer programmeTypeId,
            @ApiParam(value = "The ProgrammeType object to be created, without the programmeTypeId fields", required = true) @RequestBody ProgrammeTypeDto programmeType) throws NotFoundException, InvalidDataException {
        LOGGER.info("** ProgrammeTypesApi - programmeTypesPUT");
        if (programmeTypeId != programmeType.id) {
            throw new InvalidDataException()
        }
        ProgrammeType programmeTypeToSave = ProgrammeTypeDto.mapToProgrammeTypeEntity(programmeType)
        ProgrammeType programmeTypeSaved = programmeTypeService.updateProgrammeType(programmeTypeToSave)
        return new ResponseEntity<ProgrammeTypeDto>(ProgrammeTypeDto.mapFromProgrammeTypeEntity(programmeTypeSaved), HttpStatus.OK);
    }
}
