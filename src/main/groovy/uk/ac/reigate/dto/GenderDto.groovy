package uk.ac.reigate.dto;


import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;

import groovy.transform.EqualsAndHashCode;
import uk.ac.reigate.domain.lookup.Gender

/**
 *
 * JSON serializable DTO containing Gender data
 *
 */
@JsonSerialize
@EqualsAndHashCode(includeFields=true)
public class GenderDto implements Serializable {
    
    @JsonProperty
    private Integer id;
    
    @JsonProperty
    private String code;
    
    @JsonProperty
    private String description;
    
    /**
     * Default No Args constructor
     */
    public GenderDto() {
    }
    
    /**
     * Constructor to create a GenderDto object
     *
     * @param id the Id for the Gender
     * @param code the code for the Gender
     * @param description the description for the Gender
     */
    public GenderDto(Integer id, String code, String description) {
        this.id = id;
        this.code = code;
        this.description = description;
    }
    
    /**
     * Constructor to create a GenderDto object from a Gender object
     *
     * @param gender the Gender object to use for construction
     */
    GenderDto(Gender gender) {
        this.id = gender.id;
        this.code = gender.code;
        this.description = gender.description;
    }
    
    @Override
    public String toString() {
        return "GenderDto [id=" + id + ", code=" + code + ", description=" + description + "]";
    }
    
    public static GenderDto mapFromGenderEntity(Gender gender) {
        return new GenderDto(gender);
    }
    
    public static List<GenderDto> mapFromGendersEntities(List<Gender> genders) {
        List<GenderDto> output = genders.collect { gender ->  new GenderDto(gender) };
        return output
    }
    
    public static Gender mapToGenderEntity(GenderDto genderDto) {
        return new Gender(genderDto.id, genderDto.code, genderDto.description)
    }
}
