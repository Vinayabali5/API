package uk.ac.reigate.dto;


import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;

import groovy.transform.EqualsAndHashCode;
import uk.ac.reigate.domain.lookup.Nationality

/**
 *
 * JSON serializable DTO containing Nationality data
 *
 */
@JsonSerialize
@EqualsAndHashCode(includeFields=true)
public class NationalityDto implements Serializable {
    
    @JsonProperty
    private Integer id;
    
    @JsonProperty
    private String name;
    
    @JsonProperty
    private String description;
    
    /**
     * Default No Args constructor
     */
    public NationalityDto() {
    }
    
    /**
     * Constructor to create a NationalityDto object
     *
     * @param id the Id for the Nationality
     * @param name the name for the Nationality
     * @param description the description for the Nationality
     */
    public NationalityDto(Integer id, String name, String description) {
        this.id = id;
        this.name = name;
        this.description = description;
    }
    
    /**
     * Constructor to create a NationalityDto object from a Nationality object
     *
     * @param nationality the Nationality object to use for construction
     */
    NationalityDto(Nationality nationality) {
        this.id = nationality.id;
        this.name = nationality.name;
        this.description = nationality.description;
    }
    
    @Override
    public String toString() {
        return "NationalityDto [id=" + id + ", name=" + name + ", description=" + description + "]";
    }
    
    public static NationalityDto mapFromNationalityEntity(Nationality nationality) {
        return new NationalityDto(nationality);
    }
    
    public static List<NationalityDto> mapFromNationalitiesEntities(List<Nationality> nationalities) {
        List<NationalityDto> output = nationalities.collect { nationality ->  new NationalityDto(nationality) };
        return output
    }
    
    public static Nationality mapToNationalityEntity(NationalityDto nationalityDto) {
        return new Nationality(nationalityDto.id, nationalityDto.name, nationalityDto.description)
    }
}
