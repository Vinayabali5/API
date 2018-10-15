package uk.ac.reigate.dto.exam;


import java.util.Date;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;

import groovy.transform.EqualsAndHashCode
import uk.ac.reigate.domain.academic.Student
import uk.ac.reigate.domain.exam.ExamOption
import uk.ac.reigate.domain.exam.EdiAuditEntryLog
import uk.ac.reigate.domain.learning_support.ReferralReason;
import uk.ac.reigate.domain.learning_support.StudentReferralReason

/**
 *
 * JSON serializable DTO containing ReferralReason data
 *
 */
@JsonSerialize
@EqualsAndHashCode(includeFields=true)
public class EdiFileDto implements Serializable {
    
    @JsonProperty
    Integer examBoardId;
    
    @JsonProperty
    String examYear;
    
    @JsonProperty
    String examSeries;
    
    /**
     * Default No Args constructor
     */
    public EdiFileDto() {}
    
    /**
     * Constructor to create a ReferralReasonDto object
     *
     * @param id the Id for the ReferralReason
     * @param code the code for the ReferralReason
     * @param description the description for the ReferralReason
     */
    public EdiFileDto(Integer examBoardId, String examYear, String examSeries) {
        this.examBoardId = examBoardid;
        this.examYear = examYear;
        this.examSeries = examSeries;
    }
    
    @Override
    public String toString() {
        return "EdiFileDto [examBoardId=" + examBoardId + ", examYear=" + examYear + ", examSeries=" + examSeries +"]";
    }
}
