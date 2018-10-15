package uk.ac.reigate.dto;


import javax.persistence.Column
import uk.ac.reigate.domain.security.Role
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;

import groovy.transform.EqualsAndHashCode;

/**
 *
 * JSON serializable DTO containing Role data
 *
 */

@JsonSerialize
@EqualsAndHashCode(includeFields=true)
public class RoleDto implements Serializable {
    
    @JsonProperty
    private Integer id;
    
    @JsonProperty
    private String roleName;
    
    /**
     * Default No Args constructor
     */
    public RoleDto() {
    }
    
    /**
     * Constructor to create a RoleDto object
     *
     * @param id the Id for the Role
     * @param roleName the roleName for the Role
     * @param description the description for the Role
     */
    public RoleDto(Integer id, String roleName) {
        this.id = id;
        this.roleName = roleName;
    }
    
    /**
     * Constructor to create a RoleDto object from a Role object
     *
     * @param role the Role object to use for construction
     */
    RoleDto(Role role) {
        this.id = role.id;
        this.roleName = role.roleName;
    }
    
    @Override
    public String toString() {
        return "RoleDto [id=" + id + ", roleName=" + roleName + "]";
    }
    
    public static RoleDto mapFromRoleEntity(Role role) {
        return new RoleDto(role);
    }
    
    public static List<RoleDto> mapFromRolesEntities(List<Role> roles) {
        List<RoleDto> output = roles.collect { role ->  new RoleDto(role) };
        return output
    }
    
    public static Role mapToRoleEntity(RoleDto roleDto) {
        return new Role(roleDto.id, roleDto.roleName)
    }
}
