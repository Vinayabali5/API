package uk.ac.reigate.dto;


import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;

import groovy.transform.EqualsAndHashCode;
import uk.ac.reigate.domain.lookup.StaffType

/**
 *
 * JSON serializable DTO containing StaffType data
 *
 */
@JsonSerialize
@EqualsAndHashCode(includeFields=true)
public class StaffTypeDto implements Serializable {
    
    @JsonProperty
    private Integer id;
    
    @JsonProperty
    private String code;
    
    @JsonProperty
    private String description;
    
    @JsonProperty
    private Boolean needInitials
    
    /**
     * Default No Args constructor
     */
    public StaffTypeDto() {
    }
    
    /**
     * Constructor to create a StaffTypeDto object
     *
     * @param id the Id for the StaffType
     * @param code the code for the StaffType
     * @param description the description for the StaffType
     */
    public StaffTypeDto(Integer id, String code, String description, Boolean needInitials) {
        this.id = id;
        this.code = code;
        this.description = description;
        this.needInitials = needInitials;
    }
    
    /**
     * Constructor to create a StaffTypeDto object from a StaffType object
     *
     * @param staffType the StaffType object to use for construction
     */
    StaffTypeDto(StaffType staffType) {
        this.id = staffType.id;
        this.code = staffType.code;
        this.description = staffType.description;
        this.needInitials = staffType.needInitials;
    }
    
    @Override
    public String toString() {
        return "StaffTypeDto [id=" + id + ", code=" + code + ", description=" + description + ", needInitials=" + needInitials +"]";
    }
    
    public static StaffTypeDto mapFromStaffTypeEntity(StaffType staffType) {
        return new StaffTypeDto(staffType);
    }
    
    public static List<StaffTypeDto> mapFromStaffTypesEntities(List<StaffType> staffTypes) {
        List<StaffTypeDto> output = staffTypes.collect { staffType ->  new StaffTypeDto(staffType) };
        return output
    }
    
    public static StaffType mapToStaffTypeEntity(StaffTypeDto staffTypeDto) {
        return new StaffType(staffTypeDto.id, staffTypeDto.code, staffTypeDto.description, staffTypeDto.needInitials)
    }
}
