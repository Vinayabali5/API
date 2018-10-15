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
import uk.ac.reigate.domain.security.Role
import uk.ac.reigate.dto.RoleDto
import uk.ac.reigate.exceptions.InvalidDataException
import uk.ac.reigate.exceptions.NotFoundException
import uk.ac.reigate.model.SearchResult
import uk.ac.reigate.services.RoleService


@Controller
@RequestMapping(value = "/api/roles", produces = [ APPLICATION_JSON_VALUE ])
@Api(value = "/api/roles", description = "the roles API")
public class RolesApi {
    
    private static final Logger LOGGER = Logger.getLogger(RolesApi.class);
    
    @Autowired
    private final RoleService roleService;
    
    /**
     * Default NoArgs constructor
     */
    RolesApi() {}
    
    /**
     * Autowired constructor
     */
    RolesApi(RoleService roleService) {
        this.roleService = roleService;
    }
    
    /**
     * The rolesGet method is used to retrieve a full list of all the RoleDto objects
     *
     * @return A ResponseEntity with the corresponding list of RoleDto objects
     * @throws NotFoundException if nothing is found then the the NotFoundException is thrown.
     */
    @ApiOperation(value = "Collection of Role entities", notes = "A GET request to the Roles endpoint returns an array of all the roles in the system.", response = RoleDto.class, responseContainer = "List")
    @ApiResponses(value = [
        @ApiResponse(code = 200, message = "An array of roles")
    ])
    @RequestMapping(value = "", produces = ["application/json"], method = RequestMethod.GET)
    public ResponseEntity<List<RoleDto>> rolesGet() throws NotFoundException {
        LOGGER.info("** RolesApi - rolesGet");
        List<Role> roles = roleService.findAll();
        return new ResponseEntity<List<RoleDto>>(RoleDto.mapFromRolesEntities(roles), HttpStatus.OK);
    }
    
    /**
     * The rolesPost method is used to create a new instance of a Role from the supplied RoleDto
     *
     * @param role the RoleDto to use to create the new Role object
     * @return A ResponseEntity with the newly created Role object
     * @throws InvalidDataException if there is an issue with the data provided in the RequestBody then an InvalidDataException is thrown
     */
    @ApiOperation(value = "Creates a new Role entity", notes = "A POST request to the Roles endpoint with a Role object in the request body will create a new Role entity in the database.", response = RoleDto.class)
    @ApiResponses(value = [
        @ApiResponse(code = 201, message = "Returns a copy of the created Role entity including the roleId that has just been created."),
        @ApiResponse(code = 400, message = "Returns an Error object stating the request body does not match the expected input.")
    ])
    @RequestMapping(value = "", produces = ["application/json"], method = RequestMethod.POST)
    public ResponseEntity<RoleDto> rolesPost(@ApiParam(value = "The Role object to be created, without the roleId fields", required = true) @RequestBody @Valid RoleDto role) throws NotFoundException, InvalidDataException {
        LOGGER.info("** RolesApi - rolesPOST");
        if (role.id != null) {
            throw new InvalidDataException(400, "No ID field should be provided when creating")
        }
        Role roleToSave = RoleDto.mapToRoleEntity(role)
        Role roleSaved = roleService.save(roleToSave)
        return new ResponseEntity<RoleDto>(RoleDto.mapFromRoleEntity(roleSaved), HttpStatus.CREATED);
    }
    
    /**
     * The rolesRoleIdGet method is used to retrieve an instance of a RoleDto object as identified by the roleId provided
     *
     * @param roleId the role ID for the Role object retrieve
     * @return A ResponseEntity with the corresponding RoleDto object
     * @throws NotFoundException if nothing is found then the the NotFoundException is thrown.
     */
    @ApiOperation(value = "Retrieves an indivdual instance of a Role identified by the roleId", notes = "A getGET request to the Role instance endpoint will retrieve an instance of a Role entity as identified by the roleId provided in the URI.", response = RoleDto.class)
    @ApiResponses(value = [
        @ApiResponse(code = 200, message = "Returns a copy of the Role as identified by the roleId"),
        @ApiResponse(code = 404, message = "Returns an Error object stating that the resource could not be found. This happens if the specified id cannot be found in the database.")
    ])
    @RequestMapping(value = "/{roleId}", produces = ["application/json"], method = RequestMethod.GET)
    public ResponseEntity<RoleDto> rolesRoleIdGet(@ApiParam(value = "The unique ID of the Role to retrieve", required = true) @PathVariable("roleId") Integer roleId) throws NotFoundException {
        LOGGER.info("** RolesApi - rolesRoleIdGet");
        Role role = roleService.findById(roleId);
        if (role == null) {
            throw new NotFoundException();
        }
        return new ResponseEntity<RoleDto>(RoleDto.mapFromRoleEntity(role), HttpStatus.OK);
    }
    
    /**
     * The rolesRoleIdPut is used to update
     *
     * @param roleId the role ID for the Role object to update
     * @param role the new data for the Role object
     * @return the newly updated RoleDto object
     * @throws NotFoundException if nothing is found then the the NotFoundException is thrown.
     * @throws InvalidDataException if there is an issue with the data provided in the RequestBody then an InvalidDataException is thrown
     */
    @ApiOperation(value = "Used to update a Role entity", notes = "A PUT request to the Role instance endpoint with a Role object in the request body will update an existing Role entity in the database.", response = RoleDto.class)
    @ApiResponses(value = [
        @ApiResponse(code = 200, message = "Returns a copy of the newly updated Role object."),
        @ApiResponse(code = 400, message = "Returns an Error object stating the request body does not match the expected input."),
        @ApiResponse(code = 404, message = "Returns an Error object stating that the resource could not be found. This happens if the specified id cannot be found in the database.")
    ])
    @RequestMapping(value = "/{roleId}", produces = ["application/json"], method = RequestMethod.PUT)
    public ResponseEntity<RoleDto> rolesRoleIdPut(
            @ApiParam(value = "The unique ID of the Role to retrieve", required = true) @PathVariable("roleId") Integer roleId,
            @ApiParam(value = "The Role object to be created, without the roleId fields", required = true) @RequestBody RoleDto role) throws NotFoundException, InvalidDataException {
        LOGGER.info("** RolesApi - rolesPUT");
        if (roleId != role.id) {
            throw new InvalidDataException()
        }
        Role roleToSave = RoleDto.mapToRoleEntity(role)
        Role roleSaved = roleService.updateRole(roleToSave)
        return new ResponseEntity<RoleDto>(RoleDto.mapFromRoleEntity(roleSaved), HttpStatus.OK);
    }
}
