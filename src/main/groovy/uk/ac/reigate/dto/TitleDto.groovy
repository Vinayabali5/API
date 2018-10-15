package uk.ac.reigate.dto;


import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;

import groovy.transform.EqualsAndHashCode
import uk.ac.reigate.domain.lookup.Title

/**
 *
 * JSON serializable DTO containing Title data
 *
 */
@JsonSerialize
@EqualsAndHashCode(includeFields=true)
public class TitleDto implements Serializable {
    
    @JsonProperty
    private Integer id;
    
    @JsonProperty
    private String code;
    
    @JsonProperty
    private String description;
    
    /**
     * Default No Args constructor
     */
    public TitleDto() {
    }
    
    /**
     * Constructor to create a TitleDto object
     *
     * @param id the Id for the Title
     * @param code the code for the Title
     * @param description the description for the Title
     */
    public TitleDto(Integer id, String code, String description) {
        this.id = id;
        this.code = code;
        this.description = description;
    }
    
    /**
     * Constructor to create a TitleDto object from a Title object
     *
     * @param title the Title object to use for construction
     */
    TitleDto(Title title) {
        this.id = title.id;
        this.code = title.code;
        this.description = title.description;
    }
    
    @Override
    public String toString() {
        return "TitleDto [id=" + id + ", code=" + code + ", description=" + description + "]";
    }
    
    public static TitleDto mapFromTitleEntity(Title title) {
        return new TitleDto(title);
    }
    
    public static List<TitleDto> mapFromTitlesEntities(List<Title> titles) {
        List<TitleDto> output = titles.collect { title ->  new TitleDto(title) };
        return output
    }
    
    public static Title mapToTitleEntity(TitleDto titleDto) {
        return new Title(titleDto.id, titleDto.code, titleDto.description)
    }
}
