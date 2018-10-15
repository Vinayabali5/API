package uk.ac.reigate.dto;


import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;

import groovy.transform.EqualsAndHashCode;
import uk.ac.reigate.domain.lookup.SchoolPriority

/**
 *
 * JSON serializable DTO containing SchoolPriority data
 *
 */
@JsonSerialize
@EqualsAndHashCode(includeFields=true)
public class SchoolPriorityDto implements Serializable {
    
    @JsonProperty
    private Integer id;
    
    @JsonProperty
    private String code;
    
    @JsonProperty
    private String description;
    
    /**
     * Default No Args constructor
     */
    public SchoolPriorityDto() {
    }
    
    /**
     * Constructor to create a SchoolPriorityDto object
     *
     * @param id the Id for the SchoolPriority
     * @param code the code for the SchoolPriority
     * @param description the description for the SchoolPriority
     */
    public SchoolPriorityDto(Integer id, String code, String description) {
        this.id = id;
        this.code = code;
        this.description = description;
    }
    
    /**
     * Constructor to create a SchoolPriorityDto object from a SchoolPriority object
     *
     * @param schoolPriority the SchoolPriority object to use for construction
     */
    SchoolPriorityDto(SchoolPriority schoolPriority) {
        this.id = schoolPriority.id;
        this.code = schoolPriority.code;
        this.description = schoolPriority.description;
    }
    
    @Override
    public String toString() {
        return "SchoolPriorityDto [id=" + id + ", code=" + code + ", description=" + description + "]";
    }
    
    public static SchoolPriorityDto mapFromSchoolPriorityEntity(SchoolPriority schoolPriority) {
        return new SchoolPriorityDto(schoolPriority);
    }
    
    public static List<SchoolPriorityDto> mapFromSchoolPrioritiesEntities(List<SchoolPriority> schoolPriorities) {
        List<SchoolPriorityDto> output = schoolPriorities.collect { schoolPriority ->  new SchoolPriorityDto(schoolPriority) };
        return output
    }
    
    public static SchoolPriority mapToSchoolPriorityEntity(SchoolPriorityDto schoolPriorityDto) {
        return new SchoolPriority(schoolPriorityDto.id, schoolPriorityDto.code, schoolPriorityDto.description)
    }
}
