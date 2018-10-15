package uk.ac.reigate.dto;


import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;

import groovy.transform.EqualsAndHashCode;
import uk.ac.reigate.domain.ilp.LetterType

/**
 *
 * JSON  DTO containing LetterType data
 *
 */
@JsonSerialize
@EqualsAndHashCode(includeFields=true)
public class LetterTypeDto  {
    
    @JsonProperty
    private Integer id;
    
    @JsonProperty
    private String type;
    
    /**
     * Default No Args constructor
     */
    public LetterTypeDto() {
    }
    
    /**
     * Constructor to create a LetterTypeDto object
     *
     * @param id the Id for the LetterType
     * @param letterType the LetterType for the LetterType
     */
    public LetterTypeDto(Integer id, String type) {
        this.id = id;
        this.type = type;
    }
    
    /**
     * Constructor to create a LetterTypeDto object from a LetterType object
     *
     * @param letterType the LetterType object to use for construction
     */
    LetterTypeDto(LetterType letterType) {
        this.id = letterType.id;
        this.type = letterType.type;
    }
    
    @Override
    public String toString() {
        return "LetterTypeDto [id=" + id + ", type=" + type + "]";
    }
    
    public static LetterTypeDto mapFromLetterTypeEntity(LetterType letterType) {
        return new LetterTypeDto(letterType);
    }
    
    public static List<LetterTypeDto> mapFromLetterTypesEntities(List<LetterType> letterTypes) {
        List<LetterTypeDto> output = letterTypes.collect { letterType ->  new LetterTypeDto(letterType) };
        return output
    }
    
    public static LetterType mapToLetterTypeEntity(LetterTypeDto letterTypeDto) {
        return new LetterType(letterTypeDto.id, letterTypeDto.type)
    }
}
