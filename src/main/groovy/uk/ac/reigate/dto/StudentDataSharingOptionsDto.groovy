package uk.ac.reigate.dto

import com.fasterxml.jackson.annotation.JsonProperty
import com.fasterxml.jackson.databind.annotation.JsonSerialize

import groovy.transform.EqualsAndHashCode
import uk.ac.reigate.domain.academic.Student
import uk.ac.reigate.domain.ilr.RestrictedUseIndicator


@JsonSerialize
@EqualsAndHashCode(includeFields=true)
class StudentDataSharingOptionsDto {
    
    @JsonProperty
    Integer studentId
    
    @JsonProperty
    Integer restrictedUseIndicatorId
    
    @JsonProperty
    String _restrictedUseIndicatorCode
    
    @JsonProperty
    String _restrictedUseIndicatorDescription
    
    @JsonProperty
    Boolean contactByPost
    
    @JsonProperty
    Boolean contactByPhone
    
    @JsonProperty
    Boolean contactByEmail
    
    @JsonProperty
    Boolean lrsOptOut
    
    
    StudentDataSharingOptionsDto(){
    }
    
    
    StudentDataSharingOptionsDto(Integer StudentId, RestrictedUseIndicator restrictedUseIndicator, Boolean contactByPost, Boolean contactByPhone, Boolean contactByEmail, Boolean lrsOptOut ){
        this.studentId = studentId
        this.restrictedUseIndicatorId = restrictedUseIndicator.id
        this._restrictedUseIndicatorCode = restrictedUseIndicator.code
        this._restrictedUseIndicatorDescription = restrictedUseIndicator.description
        this.contactByPost = contactByPost
        this.contactByPhone = contactByPhone
        this.contactByEmail = contactByEmail
        this.lrsOptOut = lrsOptOut;
    }
    
    StudentDataSharingOptionsDto(Student student){
        this.studentId = student.id
        this.restrictedUseIndicatorId = student.restrictedUseIndicator !=null ? student.restrictedUseIndicator.id : null
        this._restrictedUseIndicatorCode = student.restrictedUseIndicator !=null ? student.restrictedUseIndicator.code: null
        this._restrictedUseIndicatorDescription = student.restrictedUseIndicator !=null? student.restrictedUseIndicator.description: null
        this.contactByPost = student.contactByPost
        this.contactByPhone = student.contactByPhone
        this.contactByEmail = student.contactByEmail
        this.lrsOptOut = student.lrsOptOut
    }
    
    public static Student updateStudentDataSharing(Student student, StudentDataSharingOptionsDto studentDataSharingOptionsDto, RestrictedUseIndicator restrictedUseIndicator){
        student.restrictedUseIndicator = restrictedUseIndicator
        student.contactByPost = studentDataSharingOptionsDto.contactByPost
        student.contactByPhone = studentDataSharingOptionsDto.contactByPhone
        student.contactByEmail = studentDataSharingOptionsDto.contactByEmail
        student.lrsOptOut = studentDataSharingOptionsDto.lrsOptOut
        return student
    }
}
