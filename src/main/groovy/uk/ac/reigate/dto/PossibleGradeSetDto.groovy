package uk.ac.reigate.dto;


import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;

import groovy.transform.EqualsAndHashCode;
import uk.ac.reigate.domain.lookup.PossibleGradeSet

/**
 *
 * JSON serializable DTO containing PossibleGradeSet data
 *
 */
@JsonSerialize
@EqualsAndHashCode(includeFields=true)
public class PossibleGradeSetDto implements Serializable {
    
    @JsonProperty
    private Integer id;
    
    @JsonProperty
    private String code;
    
    @JsonProperty
    private String description;
    
    /**
     * Default No Args constructor
     */
    public PossibleGradeSetDto() {
    }
    
    /**
     * Constructor to create a PossibleGradeSetDto object
     *
     * @param id the Id for the PossibleGradeSet
     * @param code the code for the PossibleGradeSet
     * @param description the description for the PossibleGradeSet
     */
    public PossibleGradeSetDto(Integer id, String code, String description) {
        this.id = id;
        this.code = code;
        this.description = description;
    }
    
    /**
     * Constructor to create a PossibleGradeSetDto object from a PossibleGradeSet object
     *
     * @param possibleGradeSet the PossibleGradeSet object to use for construction
     */
    PossibleGradeSetDto(PossibleGradeSet possibleGradeSet) {
        this.id = possibleGradeSet.id;
        this.code = possibleGradeSet.code;
        this.description = possibleGradeSet.description;
    }
    
    @Override
    public String toString() {
        return "PossibleGradeSetDto [id=" + id + ", code=" + code + ", description=" + description + "]";
    }
    
    public static PossibleGradeSetDto mapFromPossibleGradeSetEntity(PossibleGradeSet possibleGradeSet) {
        return new PossibleGradeSetDto(possibleGradeSet);
    }
    
    public static List<PossibleGradeSetDto> mapFromPossibleGradeSetsEntities(List<PossibleGradeSet> possibleGradeSets) {
        List<PossibleGradeSetDto> output = possibleGradeSets.collect { possibleGradeSet ->  new PossibleGradeSetDto(possibleGradeSet) };
        return output
    }
    
    public static PossibleGradeSet mapToPossibleGradeSetEntity(PossibleGradeSetDto possibleGradeSetDto) {
        return new PossibleGradeSet(possibleGradeSetDto.id, possibleGradeSetDto.code, possibleGradeSetDto.description)
    }
}
