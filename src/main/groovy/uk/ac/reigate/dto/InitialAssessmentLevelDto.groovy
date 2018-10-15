package uk.ac.reigate.dto;


import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;

import groovy.transform.EqualsAndHashCode;
import uk.ac.reigate.domain.learning_support.InitialAssessmentLevel

/**
 *
 * JSON serializable DTO containing InitialAssessmentLevel data
 *
 */
@JsonSerialize
@EqualsAndHashCode(includeFields=true)
public class InitialAssessmentLevelDto implements Serializable {
    
    @JsonProperty
    private Integer id;
    
    @JsonProperty
    private String initialAssessmentLevel;
    
    @JsonProperty
    private String abbrv;
    
    /**
     * Default No Args constructor
     */
    public InitialAssessmentLevelDto() {
    }
    
    /**
     * Constructor to create a InitialAssessmentLevelDto object
     *
     * @param id the Id for the InitialAssessmentLevel
     * @param initialAssessmentLevel the initialAssessmentLevel for the InitialAssessmentLevel
     * @param abbrv the abbrv for the InitialAssessmentLevel
     */
    public InitialAssessmentLevelDto(Integer id, String initialAssessmentLevel, String abbrv) {
        this.id = id;
        this.initialAssessmentLevel = initialAssessmentLevel;
        this.abbrv = abbrv;
    }
    
    /**
     * Constructor to create an InitialAssessmentLevelDto object from a InitialAssessmentLevel object
     *
     * @param initialAssessmentLevel the InitialAssessmentLevel object to use for construction
     */
    InitialAssessmentLevelDto(InitialAssessmentLevel initialAssessmentLevel) {
        this.id = initialAssessmentLevel.id;
        this.initialAssessmentLevel = initialAssessmentLevel.initialAssessmentLevel;
        this.abbrv = initialAssessmentLevel.abbrv;
    }
    
    @Override
    public String toString() {
        return "InitialAssessmentLevelDto [id=" + id + ", initialAssessmentLevel=" + initialAssessmentLevel + ", abbrv=" + abbrv + "]";
    }
    
    public static InitialAssessmentLevelDto mapFromInitialAssessmentLevelEntity(InitialAssessmentLevel initialAssessmentLevel) {
        return new InitialAssessmentLevelDto(initialAssessmentLevel);
    }
    
    public static List<InitialAssessmentLevelDto> mapFromInitialAssessmentLevelsEntities(List<InitialAssessmentLevel> initialAssessmentLevels) {
        List<InitialAssessmentLevelDto> output = initialAssessmentLevels.collect { initialAssessmentLevel ->  new InitialAssessmentLevelDto(initialAssessmentLevel) };
        return output
    }
    
    public static InitialAssessmentLevel mapToInitialAssessmentLevelEntity(InitialAssessmentLevelDto initialAssessmentLevelDto) {
        return new InitialAssessmentLevel(initialAssessmentLevelDto.id, initialAssessmentLevelDto.initialAssessmentLevel, initialAssessmentLevelDto.abbrv)
    }
}
