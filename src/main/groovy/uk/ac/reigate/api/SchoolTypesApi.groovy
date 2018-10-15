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

import uk.ac.reigate.domain.lookup.SchoolType
import uk.ac.reigate.dto.SchoolTypeDto
import uk.ac.reigate.exceptions.InvalidDataException
import uk.ac.reigate.exceptions.NotFoundException
import uk.ac.reigate.model.SearchResult
import uk.ac.reigate.services.SchoolTypeService


@Controller
@RequestMapping(value = "/api/schoolTypes", produces = [ APPLICATION_JSON_VALUE ])
@Api(value = "/api/schoolTypes", description = "the schoolTypes API")
public class SchoolTypesApi {
    
    private static final Logger LOGGER = Logger.getLogger(SchoolTypesApi.class);
    
    @Autowired
    private final SchoolTypeService schoolTypeService;
    
    /**
     * Default NoArgs constructor
     */
    SchoolTypesApi() {}
    
    /**
     * Autowired constructor
     */
    SchoolTypesApi(SchoolTypeService schoolTypeService) {
        this.schoolTypeService = schoolTypeService;
    }
    
    /**
     * The schoolTypesGet method is used to retrieve a full list of all the SchoolTypeDto objects
     *
     * @return A ResponseEntity with the corresponding list of SchoolTypeDto objects
     * @throws NotFoundException if nothing is found then the the NotFoundException is thrown.
     */
    @ApiOperation(value = "Collection of SchoolType entities", notes = "A GET request to the SchoolTypes endpoint returns an array of all the schoolTypes in the system.", response = SchoolTypeDto.class, responseContainer = "List")
    @ApiResponses(value = [
        @ApiResponse(code = 200, message = "An array of schoolTypes")
    ])
    @RequestMapping(value = "", produces = ["application/json"], method = RequestMethod.GET)
    public ResponseEntity<List<SchoolTypeDto>> schoolTypesGet() throws NotFoundException {
        LOGGER.info("** SchoolTypesApi - schoolTypesGet");
        List<SchoolType> schoolTypes = schoolTypeService.findAll();
        return new ResponseEntity<List<SchoolTypeDto>>(SchoolTypeDto.mapFromSchoolTypesEntities(schoolTypes), HttpStatus.OK);
    }
    
    /**
     * The schoolTypesPost method is used to create a new instance of a SchoolType from the supplied SchoolTypeDto
     *
     * @param schoolType the SchoolTypeDto to use to create the new SchoolType object
     * @return A ResponseEntity with the newly created SchoolType object
     * @throws InvalidDataException if there is an issue with the data provided in the RequestBody then an InvalidDataException is thrown
     */
    @ApiOperation(value = "Creates a new SchoolType entity", notes = "A POST request to the SchoolTypes endpoint with a SchoolType object in the request body will create a new SchoolType entity in the database.", response = SchoolTypeDto.class)
    @ApiResponses(value = [
        @ApiResponse(code = 201, message = "Returns a copy of the created SchoolType entity including the schoolTypeId that has just been created."),
        @ApiResponse(code = 400, message = "Returns an Error object stating the request body does not match the expected input.")
    ])
    @RequestMapping(value = "", produces = ["application/json"], method = RequestMethod.POST)
    public ResponseEntity<SchoolTypeDto> schoolTypesPost(@ApiParam(value = "The SchoolType object to be created, without the schoolTypeId fields", required = true) @RequestBody @Valid SchoolTypeDto schoolType) throws NotFoundException, InvalidDataException {
        LOGGER.info("** SchoolTypesApi - schoolTypesPOST");
        if (schoolType.id != null) {
            throw new InvalidDataException(400, "No ID field should be provided when creating")
        }
        SchoolType schoolTypeToSave = SchoolTypeDto.mapToSchoolTypeEntity(schoolType)
        SchoolType schoolTypeSaved = schoolTypeService.save(schoolTypeToSave)
        return new ResponseEntity<SchoolTypeDto>(SchoolTypeDto.mapFromSchoolTypeEntity(schoolTypeSaved), HttpStatus.CREATED);
    }
    
    /**
     * The schoolTypesSchoolTypeIdGet method is used to retrieve an instance of a SchoolTypeDto object as identified by the schoolTypeId provided
     *
     * @param schoolTypeId the schoolType ID for the SchoolType object retrieve
     * @return A ResponseEntity with the corresponding SchoolTypeDto object
     * @throws NotFoundException if nothing is found then the the NotFoundException is thrown.
     */
    @ApiOperation(value = "Retrieves an indivdual instance of a SchoolType identified by the schoolTypeId", notes = "A getGET request to the SchoolType instance endpoint will retrieve an instance of a SchoolType entity as identified by the schoolTypeId provided in the URI.", response = SchoolTypeDto.class)
    @ApiResponses(value = [
        @ApiResponse(code = 200, message = "Returns a copy of the SchoolType as identified by the schoolTypeId"),
        @ApiResponse(code = 404, message = "Returns an Error object stating that the resource could not be found. This happens if the specified id cannot be found in the database.")
    ])
    @RequestMapping(value = "/{schoolTypeId}", produces = ["application/json"], method = RequestMethod.GET)
    public ResponseEntity<SchoolTypeDto> schoolTypesSchoolTypeIdGet(@ApiParam(value = "The unique ID of the SchoolType to retrieve", required = true) @PathVariable("schoolTypeId") Integer schoolTypeId) throws NotFoundException {
        LOGGER.info("** SchoolTypesApi - schoolTypesSchoolTypeIdGet");
        SchoolType schoolType = schoolTypeService.findById(schoolTypeId);
        if (schoolType == null) {
            throw new NotFoundException();
        }
        return new ResponseEntity<SchoolTypeDto>(SchoolTypeDto.mapFromSchoolTypeEntity(schoolType), HttpStatus.OK);
    }
    
    /**
     * The schoolTypesSchoolTypeIdPut is used to update
     *
     * @param schoolTypeId the schoolType ID for the SchoolType object to update
     * @param schoolType the new data for the SchoolType object
     * @return the newly updated SchoolTypeDto object
     * @throws NotFoundException if nothing is found then the the NotFoundException is thrown.
     * @throws InvalidDataException if there is an issue with the data provided in the RequestBody then an InvalidDataException is thrown
     */
    @ApiOperation(value = "Used to update a SchoolType entity", notes = "A PUT request to the SchoolType instance endpoint with a SchoolType object in the request body will update an existing SchoolType entity in the database.", response = SchoolTypeDto.class)
    @ApiResponses(value = [
        @ApiResponse(code = 200, message = "Returns a copy of the newly updated SchoolType object."),
        @ApiResponse(code = 400, message = "Returns an Error object stating the request body does not match the expected input."),
        @ApiResponse(code = 404, message = "Returns an Error object stating that the resource could not be found. This happens if the specified id cannot be found in the database.")
    ])
    @RequestMapping(value = "/{schoolTypeId}", produces = ["application/json"], method = RequestMethod.PUT)
    public ResponseEntity<SchoolTypeDto> schoolTypesSchoolTypeIdPut(
            @ApiParam(value = "The unique ID of the SchoolType to retrieve", required = true) @PathVariable("schoolTypeId") Integer schoolTypeId,
            @ApiParam(value = "The SchoolType object to be created, without the schoolTypeId fields", required = true) @RequestBody SchoolTypeDto schoolType) throws NotFoundException, InvalidDataException {
        LOGGER.info("** SchoolTypesApi - schoolTypesPUT");
        if (schoolTypeId != schoolType.id) {
            throw new InvalidDataException()
        }
        SchoolType schoolTypeToSave = SchoolTypeDto.mapToSchoolTypeEntity(schoolType)
        SchoolType schoolTypeSaved = schoolTypeService.updateSchoolType(schoolTypeToSave)
        return new ResponseEntity<SchoolTypeDto>(SchoolTypeDto.mapFromSchoolTypeEntity(schoolTypeSaved), HttpStatus.OK);
    }
}
