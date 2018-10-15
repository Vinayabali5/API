package uk.ac.reigate.dto

import groovy.transform.EqualsAndHashCode
import uk.ac.reigate.domain.academic.Enrolment
import uk.ac.reigate.domain.academic.GCSEScore;
import uk.ac.reigate.domain.academic.SpecialCategory;
import uk.ac.reigate.domain.academic.Student
import uk.ac.reigate.domain.academic.StudentYear
import uk.ac.reigate.domain.learning_support.StudentReferralReason
import uk.ac.reigate.domain.lookup.TutorGroup;

import java.util.Date;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonFormat
import com.fasterxml.jackson.annotation.JsonProperty
import com.fasterxml.jackson.databind.annotation.JsonSerialize


/**
 *
 * JSON serializable DTO containing StudentYear data
 *
 */
@JsonSerialize
@EqualsAndHashCode(includeFields=true)
class TutorGroupRemarkPermissionDto {
    
    @JsonProperty
    Integer studentId
    
    @JsonProperty
    Integer studentRemarkPermissionId
    
    @JsonProperty
    Integer tutorGroupId
    
    @JsonProperty
    String _tutotGroupCode
    
    @JsonProperty
    String _firstName
    
    @JsonProperty
    String _surname
    
    @JsonProperty
    String _studentRemarkPermissionDecription
    
    @JsonProperty
    Integer candidateNo
    
    @JsonProperty
    Date _startDate
    
    @JsonProperty
    Date _endDate
    
    /**
     * Default NoArgs constructor
     */
    TutorGroupRemarkPermissionDto() {}
    
    /**
     * Constructor to create an TutorGroupRemarkPermissionDto object from an StudentYear object
     *
     * @param studentYear the StudentYear object to use for construction
     */
    TutorGroupRemarkPermissionDto(StudentYear studentYear) {
        this.studentId = studentYear.student.id
        this.candidateNo = studentYear.candidateNo
        this.studentRemarkPermissionId = studentYear.student.studentRemarkPermission != null ? studentYear.student.studentRemarkPermission.id : null
        this.tutorGroupId = studentYear.tutorGroup != null ? studentYear.tutorGroup.id : null
        this._tutotGroupCode = studentYear.tutorGroup != null ? studentYear.tutorGroup.code : null;
        this._firstName = studentYear.student.person != null ? studentYear.student.person.firstName : null;
        this._surname = studentYear.student.person != null ? studentYear.student.person.surname : null;
        this._studentRemarkPermissionDecription= studentYear.student.studentRemarkPermission != null ? studentYear.student.studentRemarkPermission.description : null;
        this._startDate = studentYear.startDate
        this._endDate = studentYear.endDate
    }
    
    public static TutorGroupRemarkPermissionDto mapFromStudentEntity(StudentYear studentYear) {
        return new TutorGroupRemarkPermissionDto(studentYear)
    }
    
    public static List<TutorGroupRemarkPermissionDto> mapFromStudentsEntities( List<StudentYear> studentYears) {
        List<TutorGroupRemarkPermissionDto> output = studentYears.collect { studentYear ->  new TutorGroupRemarkPermissionDto(studentYear) };
        return output
    }
}
