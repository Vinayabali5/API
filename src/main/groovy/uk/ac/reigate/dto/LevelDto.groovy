package uk.ac.reigate.dto;


import javax.persistence.Column

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;

import groovy.transform.EqualsAndHashCode;
import uk.ac.reigate.domain.lookup.Level
import uk.ac.reigate.domain.lookup.PossibleGradeSet

/**
 *
 * JSON serializable DTO containing Level data
 *
 */

@JsonSerialize
@EqualsAndHashCode(includeFields=true)
public class LevelDto implements Serializable {
    
    @JsonProperty
    private Integer id;
    
    @JsonProperty
    private String code;
    
    @JsonProperty
    private String description;
    
    @JsonProperty
    private Integer possibleGradeSetId;
    
    @JsonProperty
    private Integer progressionTo
    
    @JsonProperty
    private String alisQualCode
    
    /**
     * Default No Args constructor
     */
    public LevelDto() {
    }
    
    /**
     * Constructor to create a LevelDto object
     *
     * @param id the Id for the Level
     * @param code the code for the Level
     * @param description the description for the Level
     */
    public LevelDto(Integer id, String code, String description, Integer possibleGradeSetId, Integer progressionTo, String alisQualCode) {
        this.id = id;
        this.code = code;
        this.description = description;
        this.possibleGradeSetId= possibleGradeSetId;
        this.progressionTo = progressionTo;
        this.alisQualCode = alisQualCode;
    }
    
    /**
     * Constructor to create a LevelDto object from a Level object
     *
     * @param level the Level object to use for construction
     */
    LevelDto(Level level) {
        this.id = level.id;
        this.code = level.code;
        this.description = level.description;
        this.possibleGradeSetId = level.possibleGradeSet != null ? level.possibleGradeSet.id : null;
        this.progressionTo = level.progressionTo;
        this.alisQualCode = level.alisQualCode;
    }
    
    public LevelDto(Integer id, String code, String description, PossibleGradeSet possibleGradeSet, Integer progressionTo, String alisQualCode) {
        this(id, code, description, possibleGradeSet != null ? possibleGradeSet.id : null, progressionTo, alisQualCode)
    }
    
    @Override
    public String toString() {
        return "LevelDto [id=" + id + ", code=" + code + ", description=" + description + ", possibleGradeSet=" + possibleGradeSetId +", progressionTo=" + progressionTo + ", alisQualCode=" + alisQualCode +"]";
    }
    
    public static LevelDto mapFromLevelEntity(Level level) {
        return new LevelDto(level);
    }
    
    public static List<LevelDto> mapFromLevelsEntities(List<Level> levels) {
        List<LevelDto> output = levels.collect { level ->  new LevelDto(level) };
        return output
    }
    
    public static Level mapToLevelEntity(LevelDto levelDto, possibleGradeSet) {
        return new Level(levelDto.id, levelDto.code, levelDto.description, possibleGradeSet, levelDto.progressionTo, levelDto.alisQualCode)
    }
}
