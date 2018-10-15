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

import uk.ac.reigate.domain.lookup.StudentRemarkPermission
import uk.ac.reigate.dto.StudentRemarkPermissionDto
import uk.ac.reigate.exceptions.InvalidDataException
import uk.ac.reigate.exceptions.NotFoundException
import uk.ac.reigate.model.SearchResult
import uk.ac.reigate.services.StudentRemarkPermissionService


@Controller
@RequestMapping(value = "/api/studentRemarkPermissions", produces = [ APPLICATION_JSON_VALUE ])
@Api(value = "/api/studentRemarkPermissions", description = "the studentRemarkPermissions API")
public class StudentRemarkPermissionsApi {
    
    private static final Logger LOGGER = Logger.getLogger(StudentRemarkPermissionsApi.class);
    
    @Autowired
    private final StudentRemarkPermissionService studentRemarkPermissionService;
    
    /**
     * Default NoArgs constructor
     */
    StudentRemarkPermissionsApi() {}
    
    /**
     * Autowired constructor
     */
    StudentRemarkPermissionsApi(StudentRemarkPermissionService studentRemarkPermissionService) {
        this.studentRemarkPermissionService = studentRemarkPermissionService;
    }
    
    /**
     * The studentRemarkPermissionsGet method is used to retrieve a full list of all the StudentRemarkPermissionDto objects
     *
     * @return A ResponseEntity with the corresponding list of StudentRemarkPermissionDto objects
     * @throws NotFoundException if nothing is found then the the NotFoundException is thrown.
     */
    @ApiOperation(value = "Collection of StudentRemarkPermission entities", notes = "A GET request to the StudentRemarkPermissions endpoint returns an array of all the studentRemarkPermissions in the system.", response = StudentRemarkPermissionDto.class, responseContainer = "List")
    @ApiResponses(value = [
        @ApiResponse(code = 200, message = "An array of studentRemarkPermissions")
    ])
    @RequestMapping(value = "", produces = ["application/json"], method = RequestMethod.GET)
    public ResponseEntity<List<StudentRemarkPermissionDto>> studentRemarkPermissionsGet() throws NotFoundException {
        LOGGER.info("** StudentRemarkPermissionsApi - studentRemarkPermissionsGet");
        List<StudentRemarkPermission> studentRemarkPermissions = studentRemarkPermissionService.findAll();
        return new ResponseEntity<List<StudentRemarkPermissionDto>>(StudentRemarkPermissionDto.mapFromStudentRemarkPermissionsEntities(studentRemarkPermissions), HttpStatus.OK);
    }
    
    /**
     * The studentRemarkPermissionsPost method is used to create a new instance of a StudentRemarkPermission from the supplied StudentRemarkPermissionDto
     *
     * @param studentRemarkPermission the StudentRemarkPermissionDto to use to create the new StudentRemarkPermission object
     * @return A ResponseEntity with the newly created StudentRemarkPermission object
     * @throws InvalidDataException if there is an issue with the data provided in the RequestBody then an InvalidDataException is thrown
     */
    @ApiOperation(value = "Creates a new StudentRemarkPermission entity", notes = "A POST request to the StudentRemarkPermissions endpoint with a StudentRemarkPermission object in the request body will create a new StudentRemarkPermission entity in the database.", response = StudentRemarkPermissionDto.class)
    @ApiResponses(value = [
        @ApiResponse(code = 201, message = "Returns a copy of the created StudentRemarkPermission entity including the studentRemarkPermissionId that has just been created."),
        @ApiResponse(code = 400, message = "Returns an Error object stating the request body does not match the expected input.")
    ])
    @RequestMapping(value = "", produces = ["application/json"], method = RequestMethod.POST)
    public ResponseEntity<StudentRemarkPermissionDto> studentRemarkPermissionsPost(@ApiParam(value = "The StudentRemarkPermission object to be created, without the studentRemarkPermissionId fields", required = true) @RequestBody @Valid StudentRemarkPermissionDto studentRemarkPermission) throws NotFoundException, InvalidDataException {
        LOGGER.info("** StudentRemarkPermissionsApi - studentRemarkPermissionsPOST");
        if (studentRemarkPermission.id != null) {
            throw new InvalidDataException(400, "No ID field should be provided when creating")
        }
        StudentRemarkPermission studentRemarkPermissionToSave = StudentRemarkPermissionDto.mapToStudentRemarkPermissionEntity(studentRemarkPermission)
        StudentRemarkPermission studentRemarkPermissionSaved = studentRemarkPermissionService.save(studentRemarkPermissionToSave)
        return new ResponseEntity<StudentRemarkPermissionDto>(StudentRemarkPermissionDto.mapFromStudentRemarkPermissionEntity(studentRemarkPermissionSaved), HttpStatus.CREATED);
    }
    
    /**
     * The studentRemarkPermissionsStudentRemarkPermissionIdGet method is used to retrieve an instance of a StudentRemarkPermissionDto object as identified by the studentRemarkPermissionId provided
     *
     * @param studentRemarkPermissionId the studentRemarkPermission ID for the StudentRemarkPermission object retrieve
     * @return A ResponseEntity with the corresponding StudentRemarkPermissionDto object
     * @throws NotFoundException if nothing is found then the the NotFoundException is thrown.
     */
    @ApiOperation(value = "Retrieves an indivdual instance of a StudentRemarkPermission identified by the studentRemarkPermissionId", notes = "A getGET request to the StudentRemarkPermission instance endpoint will retrieve an instance of a StudentRemarkPermission entity as identified by the studentRemarkPermissionId provided in the URI.", response = StudentRemarkPermissionDto.class)
    @ApiResponses(value = [
        @ApiResponse(code = 200, message = "Returns a copy of the StudentRemarkPermission as identified by the studentRemarkPermissionId"),
        @ApiResponse(code = 404, message = "Returns an Error object stating that the resource could not be found. This happens if the specified id cannot be found in the database.")
    ])
    @RequestMapping(value = "/{studentRemarkPermissionId}", produces = ["application/json"], method = RequestMethod.GET)
    public ResponseEntity<StudentRemarkPermissionDto> studentRemarkPermissionsStudentRemarkPermissionIdGet(@ApiParam(value = "The unique ID of the StudentRemarkPermission to retrieve", required = true) @PathVariable("studentRemarkPermissionId") Integer studentRemarkPermissionId) throws NotFoundException {
        LOGGER.info("** StudentRemarkPermissionsApi - studentRemarkPermissionsStudentRemarkPermissionIdGet");
        StudentRemarkPermission studentRemarkPermission = studentRemarkPermissionService.findById(studentRemarkPermissionId);
        if (studentRemarkPermission == null) {
            throw new NotFoundException();
        }
        return new ResponseEntity<StudentRemarkPermissionDto>(StudentRemarkPermissionDto.mapFromStudentRemarkPermissionEntity(studentRemarkPermission), HttpStatus.OK);
    }
    
    /**
     * The studentRemarkPermissionsStudentRemarkPermissionIdPut is used to update
     *
     * @param studentRemarkPermissionId the studentRemarkPermission ID for the StudentRemarkPermission object to update
     * @param studentRemarkPermission the new data for the StudentRemarkPermission object
     * @return the newly updated StudentRemarkPermissionDto object
     * @throws NotFoundException if nothing is found then the the NotFoundException is thrown.
     * @throws InvalidDataException if there is an issue with the data provided in the RequestBody then an InvalidDataException is thrown
     */
    @ApiOperation(value = "Used to update a StudentRemarkPermission entity", notes = "A PUT request to the StudentRemarkPermission instance endpoint with a StudentRemarkPermission object in the request body will update an existing StudentRemarkPermission entity in the database.", response = StudentRemarkPermissionDto.class)
    @ApiResponses(value = [
        @ApiResponse(code = 200, message = "Returns a copy of the newly updated StudentRemarkPermission object."),
        @ApiResponse(code = 400, message = "Returns an Error object stating the request body does not match the expected input."),
        @ApiResponse(code = 404, message = "Returns an Error object stating that the resource could not be found. This happens if the specified id cannot be found in the database.")
    ])
    @RequestMapping(value = "/{studentRemarkPermissionId}", produces = ["application/json"], method = RequestMethod.PUT)
    public ResponseEntity<StudentRemarkPermissionDto> studentRemarkPermissionsStudentRemarkPermissionIdPut(
            @ApiParam(value = "The unique ID of the StudentRemarkPermission to retrieve", required = true) @PathVariable("studentRemarkPermissionId") Integer studentRemarkPermissionId,
            @ApiParam(value = "The StudentRemarkPermission object to be created, without the studentRemarkPermissionId fields", required = true) @RequestBody StudentRemarkPermissionDto studentRemarkPermission) throws NotFoundException, InvalidDataException {
        LOGGER.info("** StudentRemarkPermissionsApi - studentRemarkPermissionsPUT");
        if (studentRemarkPermissionId != studentRemarkPermission.id) {
            throw new InvalidDataException()
        }
        StudentRemarkPermission studentRemarkPermissionToSave = StudentRemarkPermissionDto.mapToStudentRemarkPermissionEntity(studentRemarkPermission)
        StudentRemarkPermission studentRemarkPermissionSaved = studentRemarkPermissionService.updateStudentRemarkPermission(studentRemarkPermissionToSave)
        return new ResponseEntity<StudentRemarkPermissionDto>(StudentRemarkPermissionDto.mapFromStudentRemarkPermissionEntity(studentRemarkPermissionSaved), HttpStatus.OK);
    }
}
