package uk.ac.reigate.dto.search

import com.fasterxml.jackson.annotation.JsonProperty
import com.fasterxml.jackson.databind.annotation.JsonSerialize

import groovy.transform.EqualsAndHashCode
import io.swagger.annotations.ApiModel
import io.swagger.annotations.ApiModelProperty
import uk.ac.reigate.domain.academic.Student
import uk.ac.reigate.domain.academic.StudentYear


/**
 *
 * JSON serializable DTO containing StudentSearch data
 *
 */
@ApiModel(value = "StudentSearch", description = "A student search object is a cut down student record that provides enough information to identify an individual student.")
@JsonSerialize
@EqualsAndHashCode(includeFields=true)
class StudentSearchDto {
    
    @ApiModelProperty(value = "the academic year ID")
    @JsonProperty
    Integer academicYearId
    
    @ApiModelProperty(value = "the student's ID")
    @JsonProperty
    Integer studentId
    
    @ApiModelProperty(value = "the student's reference number")
    @JsonProperty
    String studentReference
    
    @ApiModelProperty(value = "the student's name")
    @JsonProperty
    String studentName
    
    @ApiModelProperty(value = "the type of the student in the specified year")
    @JsonProperty
    String studentType
    
    @ApiModelProperty(value = "the student's previous school")
    @JsonProperty
    String studentSchool
    
    @ApiModelProperty(value = "the student's end date in the year specified")
    @JsonProperty
    Date endDate
    
    @ApiModelProperty(value = "the student's tutor group in the year specified")
    @JsonProperty
    String tutorGroup
    
    @ApiModelProperty(value = "the student's candidate number in the year specified")
    @JsonProperty
    Integer candidateNo
    
    @ApiModelProperty(value = "the student's application status")
    @JsonProperty
    String applicationStatus
    
    
    
    /** 
     * Default No Args constructor
     */
    StudentSearchDto() {
        this.academicYearId = null
        this.studentId = null
        this.studentReference = ''
        this.studentName = ''
        this.studentType = ''
        this.studentSchool = ''
        this.endDate = null
        this.tutorGroup = ''
    }
    
    /**
     * Constructor to create a StudentSearchDto object from the Student and StudentYear objects. 
     * 
     * @param student
     * @param studentYear
     */
    StudentSearchDto(Student student, StudentYear studentYear) {
        this.academicYearId = studentYear.year.id
        this.studentId = student.id
        this.studentReference = student.referenceNo
        this.candidateNo = studentYear.candidateNo
        this.studentName = student.person.surname + ', '
        String extraNameInfo = ''
        if (student.person.preferredName != null) {
            this.studentName += student.person.preferredName
            extraNameInfo += (extraNameInfo != '' ? ', ' : '') + "Legal First Name: $student.person.firstName"
        } else {
            this.studentName += student.person.firstName
        }
        if (student.person.middleNames != null) {
            this.studentName += ' ' + student.person.middleNames
        }
        if (student.person.previousSurname != null && student.person.previousSurname != '') {
            extraNameInfo += (extraNameInfo != '' ? ', ' : '') + "Previous Surname: $student.person.previousSurname"
        }
        if (student.person.legalSurname != null && student.person.legalSurname != '' && student.person.legalSurname != student.person.surname) {
            extraNameInfo += (extraNameInfo != '' ? ', ' : '') + "Legal Surname: $student.person.legalSurname"
        }
        if (extraNameInfo != '') {
            this.studentName += " ($extraNameInfo)"
        }
        
        this.studentType = studentYear.studentType
        this.studentSchool = student.school
        this.endDate = studentYear.endDate
        this.tutorGroup = studentYear.tutorGroup
        
        this.candidateNo = studentYear.candidateNo
        
        this.applicationStatus = student.status != null ? student.status.description : null
    }
}
