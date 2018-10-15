package uk.ac.reigate.dto;


import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;

import groovy.transform.EqualsAndHashCode
import uk.ac.reigate.domain.academic.Student
import uk.ac.reigate.domain.learning_support.ReferralReason;
import uk.ac.reigate.domain.learning_support.StudentReferralReason

/**
 *
 * JSON serializable DTO containing ReferralReason data
 *
 */
@JsonSerialize
@EqualsAndHashCode(includeFields=true)
public class StudentReferralReasonDto implements Serializable {
    
    @JsonProperty
    private Integer studentId;
    
    @JsonProperty
    private Integer referralReasonId;
    
    @JsonProperty
    private String _referralReasonDescription;
    
    @JsonProperty
    private Boolean primary;
    
    /**
     * Default No Args constructor
     */
    public StudentReferralReasonDto() {
    }
    
    /**
     * Constructor to create a ReferralReasonDto object with the basic data with no linked objects.
     *
     * @param studentId
     * @param referralReasonId
     * @param primary
     */
    public StudentReferralReasonDto(Integer studentId, Integer referralReasonId, Boolean primary) {
        this.studentId = studentId;
        this.referralReasonId = referralReasonId;
        this.primary = primary;
    }
    
    /**
     * Constructor to create a ReferralReasonDto object with the basic data with linked Student, ReferralReason objects.
     * 
     * @param student
     * @param referralReason
     * @param primary
     */
    public StudentReferralReasonDto(Student student, ReferralReason referralReason, Boolean primary) {
        this(student != null ? student.id : null, referralReason != null ? referralReason.id : null, primary)
    }
    
    /**
     * Constructor to create a StudentReferralReasonDto object from a StudentReferralReason object
     *
     * @param studentReferralReason the StudentReferralReason object to use for construction
     */
    public StudentReferralReasonDto(StudentReferralReason studentReferralReason) {
        this.studentId = studentReferralReason.student != null ? studentReferralReason.student.id : null;
        this.referralReasonId = studentReferralReason.referralReason != null ? studentReferralReason.referralReason.id : null;
        this._referralReasonDescription = studentReferralReason.referralReason != null ? studentReferralReason.referralReason.reason : '';
        this.primary = studentReferralReason.primary;
    }
    
    @Override
    public String toString() {
        return "StudentReferralReasonDto [student=" + studentId + ", referralReason=" + referralReasonId + ", primary=" + primary + "]";
    }
    
    public static StudentReferralReasonDto mapFromStudentReferralReasonEntity(StudentReferralReason studentReferralReason) {
        return new StudentReferralReasonDto(studentReferralReason);
    }
    
    public static List<StudentReferralReasonDto> mapFromStudentReferralReasonsEntities(List<StudentReferralReason> studentReferralReasons) {
        return studentReferralReasons.collect { studentReferralReason ->  new StudentReferralReasonDto(studentReferralReason) };
    }
    
    public static StudentReferralReason mapToStudentReferralReasonEntity(StudentReferralReasonDto studentReferralReasonDto, Student student, ReferralReason referralReason) {
        return new StudentReferralReason(student, referralReason, studentReferralReasonDto.primary)
    }
}
