package uk.ac.reigate.dto

import java.util.List;

import com.fasterxml.jackson.annotation.JsonProperty
import com.fasterxml.jackson.databind.annotation.JsonSerialize
import groovy.transform.EqualsAndHashCode
import uk.ac.reigate.domain.academic.Student
import uk.ac.reigate.domain.learning_support.StudentLearningSupport
import uk.ac.reigate.domain.learning_support.SupportType

/**
 *
 * JSON serializable DTO containing LearningSupport data
 *
 */
@JsonSerialize
@EqualsAndHashCode(includeFields=true)
public class StudentLearningSupportDto implements Serializable {
    
    @JsonProperty
    private Integer studentId;
    
    @JsonProperty
    private String _readingInitialAssessmentDescirption;
    
    @JsonProperty
    private String _writingInitialAssessmentDescirption
    
    @JsonProperty
    private String _spellingInitialAssessmentDescirption;
    
    @JsonProperty
    List<StudentConcessionTypeDto> concessions;
    
    @JsonProperty
    List<StudentReferralReasonDto> referralReasons;
    
    @JsonProperty
    private String referralNotes
    
    @JsonProperty
    private Integer supportTypeId;
    
    @JsonProperty
    private String _supportTypeDescription;
    
    @JsonProperty
    private String supportNotes
    
    @JsonProperty
    private Date assessmentDate
    
    @JsonProperty
    private String previousConcession
    
    @JsonProperty
    private Boolean concessionApplication
    
    @JsonProperty
    private Boolean examConcessionApproved
    
    @JsonProperty
    private Boolean btecConcessionApproved
    
    @JsonProperty
    private Boolean fsConcessionApproved
    
    
    /**
     * Default No Args constructor
     */
    public StudentLearningSupportDto() {
    }
    
    /**
     * Constructor to create a StudentLearningSupportDto object from a Student, StudentLearningSupport object
     *
     * @param student, studentLearningSupport the Student, StudentLearningSupport object to use for construction
     */
    public StudentLearningSupportDto(Student student, StudentLearningSupport studentLearningSupport) {
        this.studentId = student.id
        this._readingInitialAssessmentDescirption = student.readingInitialAssessment != null ? student.readingInitialAssessment.initialAssessmentLevel : ''
        this._writingInitialAssessmentDescirption = student.writingInitialAssessment != null ? student.writingInitialAssessment.initialAssessmentLevel : ''
        this._spellingInitialAssessmentDescirption = student.spellingInitialAssessment != null ? student.spellingInitialAssessment.initialAssessmentLevel : ''
        
        this.concessions = student.concessions.collect { it ->
            return new StudentConcessionTypeDto(it)
        }
        
        this.referralReasons = student.referralReasons.collect { it ->
            return new StudentReferralReasonDto(it)
        }
        
        if (studentLearningSupport != null) {
            this.referralNotes = studentLearningSupport.referralNotes;
            this.supportTypeId = studentLearningSupport.supportType != null ? studentLearningSupport.supportType.id : null;
            this._supportTypeDescription = studentLearningSupport.supportType != null ? studentLearningSupport.supportType.support : null;
            this.supportNotes = studentLearningSupport.supportNotes;
            this.assessmentDate = studentLearningSupport.assessmentDate;
            this.previousConcession = studentLearningSupport.previousConcession;
            this.concessionApplication = studentLearningSupport.concessionApplication;
            this.examConcessionApproved = studentLearningSupport.examConcessionApproved;
            this.btecConcessionApproved = studentLearningSupport.btecConcessionApproved;
            this.fsConcessionApproved = studentLearningSupport.fsConcessionApproved;
        }
    }
}

