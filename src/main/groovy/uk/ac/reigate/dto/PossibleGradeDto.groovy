package uk.ac.reigate.dto;


import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;

import groovy.transform.EqualsAndHashCode;
import uk.ac.reigate.domain.lookup.Level;
import uk.ac.reigate.domain.lookup.PossibleGrade
import uk.ac.reigate.domain.lookup.PossibleGradeSet

/**
 *
 * JSON serializable DTO containing PossibleGrades data
 *
 */
@JsonSerialize
@EqualsAndHashCode(includeFields=true)
public class PossibleGradeDto implements Serializable {
    
    @JsonProperty
    private Integer id;
    
    @JsonProperty
    private Integer gradeSetId;
    
    @JsonProperty
    private PossibleGradeSetDto _possibleGradeSet;
    
    @JsonProperty
    private String code;
    
    @JsonProperty
    private String description;
    
    @JsonProperty
    private Integer levelId;
    
    @JsonProperty
    private String grade;
    
    @JsonProperty
    private Integer ucasPoints;
    
    @JsonProperty
    private Boolean useForKeyAssessment;
    
    /**
     * Default No Args constructor
     */
    public PossibleGradeDto() {
    }
    
    /**
     * Constructor to create a PossibleGradeDto object from a PossibleGrade object
     *
     * @param possibleGrade the PossibleGrade object to use for construction
     */
    PossibleGradeDto(PossibleGrade possibleGrade) {
        this.id = possibleGrade.id;
        this.gradeSetId= possibleGrade.gradeSet != null ? possibleGrade.gradeSet.id : null;
        this.code = possibleGrade.code;
        this.description = possibleGrade.description;
        this.levelId = possibleGrade.level != null ? possibleGrade.level.id : null;
        this.grade = possibleGrade.grade;
        this.ucasPoints = possibleGrade.ucasPoints;
        this.useForKeyAssessment = possibleGrade.useForKeyAssessment;
        this._possibleGradeSet =  new PossibleGradeSetDto(possibleGrade.id, possibleGrade.code, possibleGrade.description)
    }
    /**
     * Constructor to create a PossibleGradeDto object with the basic data with no linked objects.
     *
     * @param id the Id for the PossibleGrade
     * @param code the code for the PossibleGrade
     * @param description the description for the PossibleGrade
     * @Param levelId the levelId of the PossibleGrade
     * @Param grade the grade for the PossibleGrade
     * @Param ucasPoints the ucasPoints for the PossibleGrade
     */
    public PossibleGradeDto(Integer id, Integer gradeSetId, String code, String description, Integer levelId, String grade, Integer ucasPoints, Boolean useForKeyAssessment) {
        this.id = id;
        this.gradeSetId= gradeSetId;
        this.code = code;
        this.description = description;
        this.levelId = levelId;
        this.grade = grade;
        this.ucasPoints = ucasPoints;
        this.useForKeyAssessment = useForKeyAssessment;
    }
    
    /**
     * Constructor to create a PossibleGradeDto object with the basic data with linked Level, PossibleGrade objects.
     *
     * @param id the Id for the PossibleGrade
     * @param code the code for the PossibleGrade
     * @param description the description for the PossibleGrade
     * @Param level the level of the PossibleGrade
     * @Param grade the grade for the PossibleGrade
     * @Param ucasPoints the ucasPoints for the PossibleGrade
     */
    public PossibleGradeDto(Integer id, PossibleGradeSet gradeSet, String code, String description, Level level, String grade, Integer ucasPoints, Boolean useForKeyAssessment){
        this(id, gradeSet != null ? gradeSet.id : null, code, description, level != null ? level.id : null, grade, ucasPoints, useForKeyAssessment)
    }
    
    @Override
    public String toString() {
        return "PossibleGradeDto [id=" + id + ", gradeSet=" + gradeSetId + ", code=" + code + ", description=" + description + ", level=" + levelId + ", grade=" + grade + ", ucasPoints=" + ucasPoints + ", useForKeyAssessment=" + useForKeyAssessment +"]";
    }
    
    public static PossibleGradeDto mapFromPossibleGradeEntity(PossibleGrade possibleGrade) {
        return new PossibleGradeDto(possibleGrade)
    }
    
    public static List<PossibleGradeDto> mapFromPossibleGradesEntities(List<PossibleGrade> possibleGrades) {
        List<PossibleGradeDto> output = possibleGrades.collect { possibleGrade ->  new PossibleGradeDto(possibleGrade) };
        return output
    }
    
    public static PossibleGrade mapToPossibleGradeEntity(PossibleGradeDto possibleGradeDto, PossibleGradeSet gradeSet, Level level) {
        return new PossibleGrade(possibleGradeDto.id, possibleGradeDto.code, possibleGradeDto.description, gradeSet, level, possibleGradeDto.grade, possibleGradeDto.ucasPoints, possibleGradeDto.useForKeyAssessment)
    }
}
