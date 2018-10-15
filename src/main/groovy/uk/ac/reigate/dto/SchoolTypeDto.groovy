package uk.ac.reigate.dto;


import groovy.transform.EqualsAndHashCode
import uk.ac.reigate.domain.lookup.SchoolType

import com.fasterxml.jackson.annotation.JsonProperty
import com.fasterxml.jackson.databind.annotation.JsonSerialize

/**
 *
 * JSON serializable DTO containing SchoolType data
 *
 */
@JsonSerialize
@EqualsAndHashCode(includeFields=true)
public class SchoolTypeDto implements Serializable {
    
    @JsonProperty
    private Integer id;
    
    @JsonProperty
    private String code;
    
    @JsonProperty
    private String description;
    
    /**
     * Default No Args constructor
     */
    public SchoolTypeDto() {
    }
    
    /**
     * Constructor to create a SchoolTypeDto object
     *
     * @param id the Id for the SchoolType
     * @param code the code for the SchoolType
     * @param description the description for the SchoolType
     */
    public SchoolTypeDto(Integer id, String code, String description) {
        this.id = id;
        this.code = code;
        this.description = description;
    }
    
    /**
     * Constructor to create a SchoolTypeDto object from a SchoolType object
     *
     * @param schoolType the SchoolType object to use for construction
     */
    SchoolTypeDto(SchoolType schoolType) {
        this.id = schoolType.id;
        this.code = schoolType.code;
        this.description = schoolType.description;
    }
    
    @Override
    public String toString() {
        return "SchoolTypeDto [id=" + id + ", code=" + code + ", description=" + description + "]";
    }
    
    public static SchoolTypeDto mapFromSchoolTypeEntity(SchoolType schoolType) {
        return new SchoolTypeDto(schoolType);
    }
    
    public static List<SchoolTypeDto> mapFromSchoolTypesEntities(List<SchoolType> schoolTypes) {
        List<SchoolTypeDto> output = schoolTypes.collect { schoolType ->  new SchoolTypeDto(schoolType) };
        return output
    }
    
    public static SchoolType mapToSchoolTypeEntity(SchoolTypeDto schoolTypeDto) {
        return new SchoolType(schoolTypeDto.id, schoolTypeDto.code, schoolTypeDto.description)
    }
}
