package uk.ac.reigate.dto;


import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;

import groovy.transform.EqualsAndHashCode;
import uk.ac.reigate.domain.lookup.YearGroup

/**
 *
 * JSON serializable DTO containing YearGroup data
 *
 */
@JsonSerialize
@EqualsAndHashCode(includeFields=true)
public class YearGroupDto implements Serializable {
    
    @JsonProperty
    private Integer id;
    
    @JsonProperty
    private String code;
    
    @JsonProperty
    private String description;
    
    /**
     * Default No Args constructor
     */
    public YearGroupDto() {
    }
    
    /**
     * Constructor to create a YearGroupDto object
     *
     * @param id the Id for the YearGroup
     * @param code the code for the YearGroup
     * @param description the description for the YearGroup
     */
    public YearGroupDto(Integer id, String code, String description) {
        this.id = id;
        this.code = code;
        this.description = description;
    }
    
    /**
     * Constructor to create a YearGroup Dto object from a YearGroup  object
     *
     * @param yearGroup  the YearGroup  object to use for construction
     */
    YearGroupDto(YearGroup yearGroup) {
        this.id = yearGroup.id;
        this.code = yearGroup.code;
        this.description = yearGroup.description;
    }
    
    @Override
    public String toString() {
        return "YearGroupDto [id=" + id + ", code=" + code + ", description=" + description + "]";
    }
    
    public static YearGroupDto mapFromYearGroupEntity(YearGroup yearGroup) {
        return new YearGroupDto(yearGroup);
    }
    
    public static List<YearGroupDto> mapFromYearGroupsEntities(List<YearGroup> yearGroups) {
        List<YearGroupDto> output = yearGroups.collect { yearGroup ->  new YearGroupDto(yearGroup) };
        return output
    }
    
    public static YearGroup mapToYearGroupEntity(YearGroupDto yearGroupDto) {
        return new YearGroup(yearGroupDto.id, yearGroupDto.code, yearGroupDto.description)
    }
}
