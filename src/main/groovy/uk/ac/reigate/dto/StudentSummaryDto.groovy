package uk.ac.reigate.dto

import groovy.transform.EqualsAndHashCode

import com.fasterxml.jackson.annotation.JsonProperty
import com.fasterxml.jackson.databind.annotation.JsonSerialize

import uk.ac.reigate.domain.academic.Student

/**
 * This class is a DTO for display a summary of a student object.  
 */
@JsonSerialize
@EqualsAndHashCode(includeFields=true)
class StudentSummaryDto  {
    
    @JsonProperty
    Integer id
    
    @JsonProperty
    String referenceNo
    
    @JsonProperty
    AcademicYearDto yearApplied
    
    @JsonProperty
    PersonSummaryDto person
    
    @JsonProperty
    Integer previousSchoolId
    
    @JsonProperty
    String _previousSchoolName
    
    @JsonProperty
    String uln
    
    @JsonProperty
    String uci
    
    @JsonProperty
    Integer specialCategoryId
    
    @JsonProperty
    Boolean specialConfirmed
    
    @JsonProperty
    String _specialCategoryCode
    
    @JsonProperty
    String _specialCategoryDescription
    
    @JsonProperty
    String _specialCategoryDetails
    
    @JsonProperty
    String _studentEmail
    
    @JsonProperty
    Boolean monitorable
    
    /**
     * Default No Args constructor  
     */
    StudentSummaryDto() {
    }
    
    /**
     * Constructor to create a StudentDto object from a Student and StudentYear object
     *
     * @param student, studentYear the Student, StudentYear object to use for construction
     */
    StudentSummaryDto (Student student) {
        this.id = student.id
        this.referenceNo = student.referenceNo
        this.yearApplied = student.academicYear != null ? new AcademicYearDto(student.academicYear) : null
        this.person = student.person != null ? new PersonSummaryDto(student.person) : null
        this.previousSchoolId = student.school != null ? student.school.id : null
        this._previousSchoolName = student.school != null ? student.school.name : ''
        this.specialConfirmed = student.specialConfirmed;
        this.uln = student.uln
        this.uci = student.uci
        this.specialConfirmed = student.specialConfirmed;
        this.specialCategoryId = student.specialCategory != null ? student.specialCategory.id : null
        this._specialCategoryCode =  student.specialCategory != null ? student.specialCategory.code : ''
        this._specialCategoryDescription =  student.specialCategory != null ? student.specialCategory.description : ''
        this._specialCategoryDetails =  student.specialCategory != null ? student.specialCategory.details : ''
        this._studentEmail = student.studentEmail;
        this.monitorable = student.monitorable;
    }
    
    @Override
    public String toString() {
        return "StudentSummaryDto [id=" + id + ", referenceNo=" + referenceNo + ", yearApplied=" + yearApplied + ", person=" + person + ", previousSchoolId=" + previousSchoolId + ", _previousSchoolName=" + _previousSchoolName + ", uln=" + uln + ", uci=" + uci + ", specialCategoryId=" + specialCategoryId + ", specialConfirmed=" + specialConfirmed + ", _specialCategoryCode=" + _specialCategoryCode + ", _specialCategoryDescription=" + _specialCategoryDescription + ", _specialCategoryDetails=" + _specialCategoryDetails + ", _studentEmail=" + _studentEmail + ", monitorable=" + monitorable + "]";
    }
}
