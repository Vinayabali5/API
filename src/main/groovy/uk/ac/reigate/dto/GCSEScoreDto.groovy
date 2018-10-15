package uk.ac.reigate.dto

import com.fasterxml.jackson.annotation.JsonProperty
import com.fasterxml.jackson.databind.annotation.JsonSerialize
import groovy.transform.EqualsAndHashCode
import uk.ac.reigate.domain.academic.GCSEScore
import uk.ac.reigate.domain.academic.Student

/**
 *
 * JSON serializable DTO containing GCSEScore data
 *
 */
@JsonSerialize
@EqualsAndHashCode(includeFields=true)
public class GCSEScoreDto implements Serializable {
    
    @JsonProperty
    private Integer studentId;
    
    @JsonProperty
    private Integer countOfQualifications;
    
    @JsonProperty
    private Integer countOfGCSEs;
    
    @JsonProperty
    private Integer passes;
    
    @JsonProperty
    private Integer passesAToC;
    
    @JsonProperty
    private Double score;
    
    @JsonProperty
    private Double average
    
    /**
     * Default No Args constructor
     */
    public GCSEScoreDto() {
    }
    
    /**
     * Constructor to create a GCSEScoreDto object from a GCSEScore object
     *
     * @param gCSEScore the GCSEScore object to use for construction
     */
    GCSEScoreDto(GCSEScore gCSEScore) {
        this.studentId = gCSEScore.student != null ? gCSEScore.student.id : null;
        this.countOfQualifications = gCSEScore.countOfQualifications;
        this.countOfGCSEs = gCSEScore.countOfGCSEs;
        this.passes = gCSEScore.passes;
        this.score = gCSEScore.score;
        this.average = gCSEScore.average;
        this.passesAToC = gCSEScore.passesAToC;
    }
    
    /**
     * Constructor to create a GCSEScoreDto object
     *
     * @param studentId the studentId for the GCSEScore
     * @param code the code for the GCSEScore
     * @param description the description for the GCSEScore
     */
    public GCSEScoreDto(Integer studentId, Integer countOfQualifications, Integer countOfGCSEs, Integer passes, Integer passesAToC,  Double score, Double average) {
        this.studentId = studentId;
        this.countOfQualifications = countOfQualifications;
        this.countOfGCSEs = countOfGCSEs;
        this.passes = passes;
        this.score = score;
        this.average = average;
        this.passesAToC = passesAToC;
    }
    
    /**
     * Constructor to create a GCSEScoreDto object
     * 
     * @param student the student object for the GCSEScore
     * @param countOfQualifications the countOfQualifications of the GCSEScore
     * @param countOfGCSEs the countOfGCSEs of the GCSEScore
     * @param passes the passes of the GCSEScore
     * @param passesAToC the passesAToC of the GCSEScore
     * @param score the score of the GCSEScore
     * @param average the average of the GCSEScore
     */
    public GCSEScoreDto(Student student, Integer countOfQualifications, Integer countOfGCSEs, Integer passes, Integer passesAToC,  Double score, Double average) {
        this(student != null ? student.id : null, countOfQualifications, countOfGCSEs, passes, score, average, passesAToC)
    }
    
    @Override
    public String toString() {
        return "GCSEScoreDto [studentId=" + studentId + ", countOfQualifications=" + countOfQualifications + ", countOfGCSEs=" + countOfGCSEs + ", passes=" + passes + ", passesAToC=" + passesAToC + ", score=" + score + ", average=" + average+"]";
    }
    
    public static GCSEScoreDto mapFromGCSEScoreEntity(GCSEScore gCSEScore) {
        GCSEScoreDto output
        if (gCSEScore != null) {
            output = new GCSEScoreDto(gCSEScore);
        } else {
            output = null
        }
        return output
    }
    
    public static List<GCSEScoreDto> mapFromGCSEScoresEntities(List<GCSEScore> gCSEScores) {
        List<GCSEScoreDto> output = gCSEScores.collect { gCSEScore ->  new GCSEScoreDto(gCSEScore) };
        return output
    }
}
