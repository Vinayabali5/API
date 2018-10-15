package uk.ac.reigate.dto;


import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;

import groovy.transform.EqualsAndHashCode;
import uk.ac.reigate.domain.lookup.StudentType

/**
 *
 * JSON serializable DTO containing StudentType data
 *
 */
@JsonSerialize
@EqualsAndHashCode(includeFields=true)
public class StudentTypeDto implements Serializable {
    
    @JsonProperty
    private Integer id;
    
    @JsonProperty
    private String code;
    
    @JsonProperty
    private String description;
    
    /**
     * Default No Args constructor
     */
    public StudentTypeDto() {
    }
    
    /**
     * Constructor to create a StudentTypeDto object
     *
     * @param id the ID of the StudentType
     * @param code the code for the StudentType
     * @param description the description of the studentType 
     */
    public StudentTypeDto(Integer id, String code, String description) {
        this.id = id;
        this.code = code;
        this.description = description;
    }
    
    /**
     * Constructor to create a StudentTypeDto object from a StudentType object
     *
     * @param studentType the StudentType object to use for construction
     */
    StudentTypeDto(StudentType studentType) {
        this.id = studentType.id;
        this.code = studentType.code;
        this.description = studentType.description;
    }
    
    @Override
    public String toString() {
        return "StudentTypeDto [id=" + id + ", code=" + code + ", description=" + description + "]";
    }
    
    public static StudentTypeDto mapFromStudentTypeEntity(StudentType studentType) {
        return new StudentTypeDto(studentType);
    }
    
    public static List<StudentTypeDto> mapFromStudentTypesEntities(List<StudentType> studentTypes) {
        List<StudentTypeDto> output = studentTypes.collect { studentType ->  new StudentTypeDto(studentType) };
        return output
    }
    
    public static StudentType mapToStudentTypeEntity(StudentTypeDto studentTypeDto) {
        return new StudentType(studentTypeDto.id, studentTypeDto.code, studentTypeDto.description)
    }
}
