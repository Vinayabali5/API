package uk.ac.reigate.dto

import groovy.transform.EqualsAndHashCode

import com.fasterxml.jackson.annotation.JsonProperty
import com.fasterxml.jackson.databind.annotation.JsonSerialize

import uk.ac.reigate.domain.academic.Student

@JsonSerialize
@EqualsAndHashCode(includeFields=true)
class StudentDto {
    
    @JsonProperty
    Integer id
    
    @JsonProperty
    String referenceNo
    
    @JsonProperty
    AcademicYearDto yearApplied
    
    @JsonProperty
    PersonDto person
    
    @JsonProperty
    Integer previousSchoolId
    
    @JsonProperty
    String _previousSchoolName
    
    @JsonProperty
    String uln
    
    @JsonProperty
    String uci
    
    @JsonProperty
    Integer ethnicityId
    
    @JsonProperty
    String _ethnicityDescription
    
    @JsonProperty
    Integer nationalityId
    
    @JsonProperty
    String _nationalityDescription
    
    @JsonProperty
    Integer llddHealthProblemId
    
    @JsonProperty
    List<LLDDHealthProblemCategoryDto> llddHealthProblemCategories
    
    @JsonProperty
    Integer studentTypeId
    
    @JsonProperty
    String _studentTypeCode
    
    @JsonProperty
    Integer tutorGroupId
    
    @JsonProperty
    String _tutorGroupCode
    
    @JsonProperty
    Date startDate
    
    @JsonProperty
    Date endDate
    
    @JsonProperty
    List<AcademicYearDto> _years
    
    @JsonProperty
    AcademicYearDto currentYear
    
    @JsonProperty
    Integer specialCategoryId
    
    @JsonProperty
    Boolean specialConfirmed
    
    @JsonProperty
    Integer readingInitialAssessmentId
    
    @JsonProperty
    String _readingInitialAssessmentDescirption
    
    @JsonProperty
    Integer writingInitialAssessmentId
    
    @JsonProperty
    Integer spellingInitialAssessmentId
    
    @JsonProperty
    List<StudentConcessionTypeDto> concessions
    
    @JsonProperty
    List<StudentReferralReasonDto> referralReasons
    
    @JsonProperty
    GCSEScoreDto gcseScore
    
    @JsonProperty
    String _specialCategoryCode
    
    @JsonProperty
    String _specialCategoryDescription
    
    @JsonProperty
    String _specialCategoryDetails
    
    @JsonProperty
    String medicalNote
    
    @JsonProperty
    String _studentEmail
    
    @JsonProperty
    Boolean monitorable
    
    @JsonProperty
    Boolean resident
    
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
    Integer studentRemarkPermissionId
    
    @JsonProperty
    Boolean mathsDisadvantageFunding
    
    @JsonProperty
    Boolean englishDisadvantageFunding
    
    @JsonProperty
    Integer destinationId
    
    @JsonProperty
    String destinationCollegeEmployer
    
    @JsonProperty
    String destinationCourseCareer
    
    @JsonProperty
    String _destinationDescription
    
    /**
     * Default NoArgs constructor
     */
    StudentDto() {}
    
    /**
     * Constructor to create a StudentDto object from a Student and StudentYear object
     *
     * @param student, studentYear the Student, StudentYear object to use for construction
     */
    StudentDto(Student student) {
        this.id = student.id
        this.referenceNo = student.referenceNo
        this.yearApplied = new AcademicYearDto(student.academicYear)
        this.person = new PersonDto(student.person)
        
        this.previousSchoolId = student.school != null ? student.school.id : null
        this._previousSchoolName = student.school != null ? student.school.name : ''
        this.specialConfirmed = student.specialConfirmed;
        this.uln = student.uln
        this.uci = student.uci
        this.specialConfirmed = student.specialConfirmed;
        this.ethnicityId = student.ethnicity != null ? student.ethnicity.id : null
        this._ethnicityDescription = student.ethnicity != null ? student.ethnicity.shortDescription : ''
        
        this.nationalityId = student.nationality != null ? student.nationality.id : null
        this._nationalityDescription = student.nationality != null ? student.nationality.description : ''
        
        this._years = student.studentYears.collect { it ->
            return AcademicYearDto.mapFromEntity(it.year)
        }
        
        this.llddHealthProblemId = student.llddHealthProblem != null ? student.llddHealthProblem.id : null
        this.llddHealthProblemCategories = student.llddHealthProblemCategory.collect { it ->
            return new LLDDHealthProblemCategoryDto(it)
        }
        
        this.concessions = student.concessions.collect { it ->
            return new StudentConcessionTypeDto(it)
        }
        
        this.referralReasons = student.referralReasons.collect { it ->
            return new StudentReferralReasonDto(it)
        }
        this.gcseScore = student.gcseScore != null ? new GCSEScoreDto(student.gcseScore) : null
        
        this.specialCategoryId = student.specialCategory != null ? student.specialCategory.id : null
        this._specialCategoryCode =  student.specialCategory != null ? student.specialCategory.code : ''
        this._specialCategoryDescription =  student.specialCategory != null ? student.specialCategory.description : ''
        this._specialCategoryDetails =  student.specialCategory != null ? student.specialCategory.details : ''
        this.readingInitialAssessmentId = student.readingInitialAssessment != null ? student.readingInitialAssessment.id : null
        this._readingInitialAssessmentDescirption = student.readingInitialAssessment != null ? student.readingInitialAssessment.initialAssessmentLevel : null
        this.writingInitialAssessmentId = student.writingInitialAssessment != null ? student.writingInitialAssessment.id : null
        this.spellingInitialAssessmentId = student.spellingInitialAssessment != null ? student.spellingInitialAssessment.id : null
        this.medicalNote = student.medicalNote;
        this._studentEmail = student.studentEmail;
        this.monitorable = student.monitorable;
        this.resident = student.resident;
        this.contactByPost = student.contactByPost;
        this.contactByPhone = student.contactByPhone;
        this.contactByEmail = student.contactByEmail;
        this.mathsDisadvantageFunding = student.mathsDisadvantageFunding;
        this.englishDisadvantageFunding = student.englishDisadvantageFunding;
        this.restrictedUseIndicatorId = student.restrictedUseIndicator != null ? student.restrictedUseIndicator.id : null
        this._restrictedUseIndicatorCode = student.restrictedUseIndicator != null ? student.restrictedUseIndicator.code : ''
        this._restrictedUseIndicatorDescription = student.restrictedUseIndicator != null ? student.restrictedUseIndicator.description : ''
        this.studentRemarkPermissionId = student.studentRemarkPermission != null ? student.studentRemarkPermission.id : null
        this.destinationId = student.destination != null ? student.destination.id : null
        this._destinationDescription = student.destination != null ? student.destination.description : ''
        this.destinationCollegeEmployer = student.destinationCollegeEmployer
        this.destinationCourseCareer = student.destinationCourseCareer
    }
}
