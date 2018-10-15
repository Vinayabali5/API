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

import uk.ac.reigate.domain.academic.School
import uk.ac.reigate.domain.lookup.SchoolPriority
import uk.ac.reigate.domain.lookup.SchoolType
import uk.ac.reigate.dto.SchoolDto
import uk.ac.reigate.exceptions.InvalidDataException
import uk.ac.reigate.exceptions.NotFoundException
import uk.ac.reigate.model.SearchResult
import uk.ac.reigate.services.SchoolPriorityService
import uk.ac.reigate.services.SchoolService
import uk.ac.reigate.services.SchoolTypeService


@Controller
@RequestMapping(value = "/api/schools", produces = [ APPLICATION_JSON_VALUE ])
@Api(value = "/api/schools", description = "the schools API")
public class SchoolsApi {
    
    private static final Logger LOGGER = Logger.getLogger(SchoolsApi.class);
    
    @Autowired
    private final SchoolService schoolService;
    
    @Autowired
    private final SchoolPriorityService schoolPriorityService;
    
    @Autowired
    private final SchoolTypeService schoolTypeService;
    
    /**
     * Default NoArgs constructor
     */
    SchoolsApi() {}
    
    /**
     * Autowired constructor
     */
    SchoolsApi(SchoolService schoolService) {
        this.schoolService = schoolService;
    }
    
    /**
     * The schoolsGet method is used to retrieve a full list of all the SchoolDto objects
     *
     * @return A ResponseEntity with the corresponding list of SchoolDto objects
     * @throws NotFoundException if nothing is found then the the NotFoundException is thrown.
     */
    @ApiOperation(value = "Collection of School entities", notes = "A GET request to the Schools endpoint returns an array of all the schools in the system.", response = SchoolDto.class, responseContainer = "List")
    @ApiResponses(value = [
        @ApiResponse(code = 200, message = "An array of schools")
    ])
    @RequestMapping(value = "", produces = ["application/json"], method = RequestMethod.GET)
    public ResponseEntity<List<SchoolDto>> schoolsGet() throws NotFoundException {
        LOGGER.info("** SchoolsApi - schoolsGet");
        List<School> schools = schoolService.findAll();
        return new ResponseEntity<List<SchoolDto>>(SchoolDto.mapFromSchoolsEntities(schools), HttpStatus.OK);
    }
    
    /**
     * The schoolsPost method is used to create a new instance of a School from the supplied SchoolDto
     *
     * @param school the SchoolDto to use to create the new School object
     * @return A ResponseEntity with the newly created School object
     * @throws InvalidDataException if there is an issue with the data provided in the RequestBody then an InvalidDataException is thrown
     */
    @ApiOperation(value = "Creates a new School entity", notes = "A POST request to the Schools endpoint with a School object in the request body will create a new School entity in the database.", response = SchoolDto.class)
    @ApiResponses(value = [
        @ApiResponse(code = 201, message = "Returns a copy of the created School entity including the schoolId that has just been created."),
        @ApiResponse(code = 400, message = "Returns an Error object stating the request body does not match the expected input.")
    ])
    @RequestMapping(value = "", produces = ["application/json"], method = RequestMethod.POST)
    public ResponseEntity<SchoolDto> schoolsPost(@ApiParam(value = "The School object to be created, without the schoolId fields", required = true) @RequestBody @Valid SchoolDto school) throws NotFoundException, InvalidDataException {
        LOGGER.info("** SchoolsApi - schoolsPOST");
        if (school.id != null) {
            throw new InvalidDataException(400, "No ID field should be provided when creating")
        }
        SchoolPriority priority
        if(school.priorityId != null) {
            priority = schoolPriorityService.findById(school.priorityId)
        }
        SchoolType type
        if(school.typeId != null) {
            type = schoolTypeService.findById(school.typeId)
        }
        School schoolToSave = SchoolDto.mapToSchoolEntity(school, priority, type)
        School schoolSaved = schoolService.save(schoolToSave)
        return new ResponseEntity<SchoolDto>(SchoolDto.mapFromSchoolEntity(schoolSaved), HttpStatus.CREATED);
    }
    
    /**
     * The schoolsSchoolIdGet method is used to retrieve an instance of a SchoolDto object as identified by the schoolId provided
     *
     * @param schoolId the school ID for the School object retrieve
     * @return A ResponseEntity with the corresponding SchoolDto object
     * @throws NotFoundException if nothing is found then the the NotFoundException is thrown.
     */
    @ApiOperation(value = "Retrieves an indivdual instance of a School identified by the schoolId", notes = "A getGET request to the School instance endpoint will retrieve an instance of a School entity as identified by the schoolId provided in the URI.", response = SchoolDto.class)
    @ApiResponses(value = [
        @ApiResponse(code = 200, message = "Returns a copy of the School as identified by the schoolId"),
        @ApiResponse(code = 404, message = "Returns an Error object stating that the resource could not be found. This happens if the specified id cannot be found in the database.")
    ])
    @RequestMapping(value = "/{schoolId}", produces = ["application/json"], method = RequestMethod.GET)
    public ResponseEntity<SchoolDto> schoolsSchoolIdGet(@ApiParam(value = "The unique ID of the School to retrieve", required = true) @PathVariable("schoolId") Integer schoolId) throws NotFoundException {
        LOGGER.info("** SchoolsApi - schoolsSchoolIdGet");
        School school = schoolService.findById(schoolId);
        if (school == null) {
            throw new NotFoundException();
        }
        return new ResponseEntity<SchoolDto>(SchoolDto.mapFromSchoolEntity(school), HttpStatus.OK);
    }
    
    /**
     * The schoolsSchoolIdPut is used to update
     *
     * @param schoolId the school ID for the School object to update
     * @param school the new data for the School object
     * @return the newly updated SchoolDto object
     * @throws NotFoundException if nothing is found then the the NotFoundException is thrown.
     * @throws InvalidDataException if there is an issue with the data provided in the RequestBody then an InvalidDataException is thrown
     */
    @ApiOperation(value = "Used to update a School entity", notes = "A PUT request to the School instance endpoint with a School object in the request body will update an existing School entity in the database.", response = SchoolDto.class)
    @ApiResponses(value = [
        @ApiResponse(code = 200, message = "Returns a copy of the newly updated School object."),
        @ApiResponse(code = 400, message = "Returns an Error object stating the request body does not match the expected input."),
        @ApiResponse(code = 404, message = "Returns an Error object stating that the resource could not be found. This happens if the specified id cannot be found in the database.")
    ])
    @RequestMapping(value = "/{schoolId}", produces = ["application/json"], method = RequestMethod.PUT)
    public ResponseEntity<SchoolDto> schoolsSchoolIdPut(
            @ApiParam(value = "The unique ID of the School to retrieve", required = true) @PathVariable("schoolId") Integer schoolId,
            @ApiParam(value = "The School object to be updated", required = true) @RequestBody SchoolDto school) throws NotFoundException, InvalidDataException {
        LOGGER.info("** SchoolsApi - schoolPUT");
        if (schoolId != school.id) {
            throw new InvalidDataException()
        }
        SchoolType type
        if(school.typeId != null) {
            type = schoolTypeService.findById(school.typeId)
        }
        SchoolPriority priority
        if(school.priorityId != null) {
            priority = schoolPriorityService.findById(school.priorityId)
        }
        
        School schoolSaved = schoolService.save(SchoolDto.mapToSchoolEntity(school,priority,type))
        return new ResponseEntity<SchoolDto>(new SchoolDto(schoolSaved), HttpStatus.OK);
    }
}
