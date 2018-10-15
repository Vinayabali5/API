package uk.ac.reigate.dto;


import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;

import groovy.transform.EqualsAndHashCode;
import uk.ac.reigate.domain.lookup.StudentRemarkPermission

/**
 *
 * JSON serializable DTO containing StudentRemarkPermission data
 *
 */
@JsonSerialize
@EqualsAndHashCode(includeFields=true)
public class StudentRemarkPermissionDto implements Serializable {
    
    @JsonProperty
    private Integer id;
    
    @JsonProperty
    private String code;
    
    @JsonProperty
    private String description;
    
    /**
     * Default No Args constructor
     */
    public StudentRemarkPermissionDto() {
    }
    
    /**
     * Constructor to create a StudentRemarkPermissionDto object
     *
     * @param id the Id for the StudentRemarkPermission
     * @param code the code for the StudentRemarkPermission
     * @param description the description for the StudentRemarkPermission
     */
    public StudentRemarkPermissionDto(Integer id, String code, String description) {
        this.id = id;
        this.code = code;
        this.description = description;
    }
    
    /**
     * Constructor to create a StudentRemarkPermissionDto object from a StudentRemarkPermission object
     *
     * @param studentRemarkPermission the StudentRemarkPermission object to use for construction
     */
    StudentRemarkPermissionDto(StudentRemarkPermission studentRemarkPermission) {
        this.id = studentRemarkPermission.id;
        this.code = studentRemarkPermission.code;
        this.description = studentRemarkPermission.description;
    }
    
    @Override
    public String toString() {
        return "StudentRemarkPermissionDto [id=" + id + ", code=" + code + ", description=" + description + "]";
    }
    
    public static StudentRemarkPermissionDto mapFromStudentRemarkPermissionEntity(StudentRemarkPermission studentRemarkPermission) {
        return new StudentRemarkPermissionDto(studentRemarkPermission);
    }
    
    public static List<StudentRemarkPermissionDto> mapFromStudentRemarkPermissionsEntities(List<StudentRemarkPermission> studentRemarkPermissions) {
        List<StudentRemarkPermissionDto> output = studentRemarkPermissions.collect { studentRemarkPermission ->  new StudentRemarkPermissionDto(studentRemarkPermission) };
        return output
    }
    
    public static StudentRemarkPermission mapToStudentRemarkPermissionEntity(StudentRemarkPermissionDto studentRemarkPermissionDto) {
        return new StudentRemarkPermission(studentRemarkPermissionDto.id, studentRemarkPermissionDto.code, studentRemarkPermissionDto.description)
    }
}
