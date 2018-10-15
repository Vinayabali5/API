package uk.ac.reigate.dto;


import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;

import groovy.transform.EqualsAndHashCode
import uk.ac.reigate.domain.lookup.TextLookup

/**
 *
 * DTO object used for the Text Lookup data object
 *
 */
@JsonSerialize
@EqualsAndHashCode(includeFields=true)
public class TextLookUpDto  {
    
    @JsonProperty
    private Integer id;
    
    @JsonProperty
    private String name;
    
    @JsonProperty
    private String text
    
    @JsonProperty
    private String description
    
    /**
     * Default No Args constructor
     */
    public TextLookUpDto() {
    }
    
    /**
     * Constructor from the TextLookup data object
     * 
     * @param textLookUp a TextLookup data object
     */
    public TextLookUpDto(TextLookup textLookUp){
        this.id = textLookUp.id
        this.name = textLookUp.name
        this.text = textLookUp.text
        this.description = textLookUp.description
    }
    
    /**
     * This method is used to map a List of TextLookup data objects to a List of TextLookupDto objects
     * 
     * @param textLookUpList a List of TextLookup data objects
     * @return a List of TextLookupDto objects
     */
    public static  List<TextLookUpDto> mapFromEntityList(List<TextLookup> textLookUpList) {
        return textLookUpList.collect { textLookUp ->  new TextLookUpDto(textLookUp) };
    }
}
