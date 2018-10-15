package uk.ac.reigate.dto

import uk.ac.reigate.domain.academic.AcademicYear
import uk.ac.reigate.domain.academic.StudentYear

import java.util.List

import com.fasterxml.jackson.annotation.JsonFormat
import com.fasterxml.jackson.annotation.JsonProperty


class StudentYearDto {
    
    @JsonProperty
    Integer studentId
    
    @JsonProperty
    Integer yearId
    
    @JsonProperty
    String _yearCode;
    
    @JsonProperty
    String _yearApplied;
    
    @JsonProperty
    Integer typeId
    
    @JsonProperty
    String _studentTypeCode
    
    @JsonProperty
    String _studentTypeDescription
    
    @JsonProperty
    Integer tutorGroupId
    
    @JsonProperty
    String _tutorGroupCode
    
    @JsonProperty
    String _tutorGroupDescription
    
    @JsonProperty
    String _tutorInitials
    
    @JsonProperty
    String _tutorName
    
    @JsonProperty
    String _seniorTutorInitials
    
    @JsonProperty
    String _pastoralDirectorInitials
    
    @JsonProperty
    String _associatePastoralDirectorInitials
    
    @JsonProperty
    Date startDate
    
    @JsonProperty
    Date endDate
    
    @JsonProperty
    Boolean ilr
    
    @JsonProperty
    Integer plh
    
    @JsonProperty
    Integer peeph
    
    @JsonProperty
    String gcseMathsGrade
    
    @JsonProperty
    String gcseEnglishGrade
    
    @JsonProperty
    Integer candidateNo
    
    @JsonProperty
    Integer llddHealthProblemId
    
    @JsonProperty
    Boolean lda
    
    @JsonProperty
    Boolean ehc
    
    @JsonProperty
    Boolean hns
    
    @JsonProperty
    Boolean sen
    
    @JsonProperty
    Integer mathsConditionOfFundingId
    
    @JsonProperty
    Integer englishConditionOfFundingId
    
    @JsonProperty
    Integer learningSupportCost
    
    @JsonProperty
    Boolean onContract
    
    @JsonProperty
    String initialPostcode
    
    @JsonProperty
    Boolean externalCandidate
    
    @JsonProperty
    Integer destinationId
    
    @JsonProperty
    String destinationCollegeEmployer
    
    @JsonProperty
    String destinationCourseCareer
    
    @JsonProperty
    Boolean breakInLearning
    
    /**
     * Default No Args constructor
     */
    StudentYearDto() {}
    
    /**
     * Constructor to create a StudentYearDto object from a StudentYear object
     *
     * @param studentYear the StudentYear object to use for construction
     */
    StudentYearDto(StudentYear studentYear){
        this.studentId = studentYear.student != null ? studentYear.student.id : null;
        this.yearId = studentYear.year != null ? studentYear.year.id : null;
        this._yearCode = studentYear.year != null ? studentYear.year.code : ''
        this._yearApplied = studentYear.student != null && studentYear.student.academicYear != null ? studentYear.student.academicYear.code : null;
        this.typeId = studentYear.studentType != null ? studentYear.studentType.id : null;
        this._studentTypeCode = studentYear.studentType != null ? studentYear.studentType.code: null;
        this._studentTypeDescription = studentYear.studentType != null ? studentYear.studentType.description : null;
        this.tutorGroupId = studentYear.tutorGroup != null ? studentYear.tutorGroup.id : null;
        this._tutorGroupCode = studentYear.tutorGroup != null ? studentYear.tutorGroup.code : null;
        this._tutorGroupDescription = studentYear.tutorGroup != null ? studentYear.tutorGroup.description : null;
        this._tutorInitials = studentYear.tutorGroup != null && studentYear.tutorGroup.tutor != null ? studentYear.tutorGroup.tutor.initials : null;
        this._tutorName = studentYear.tutorGroup != null && studentYear.tutorGroup.tutor != null ? studentYear.tutorGroup.tutor.knownAs : null;
        this._seniorTutorInitials = studentYear.tutorGroup != null && studentYear.tutorGroup.seniorTutor != null ? studentYear.tutorGroup.seniorTutor.initials : null;
        this._pastoralDirectorInitials = studentYear.tutorGroup != null && studentYear.tutorGroup.faculty != null && studentYear.tutorGroup.faculty.pd != null ? studentYear.tutorGroup.faculty.pd.initials  : null;
        this._associatePastoralDirectorInitials = studentYear.tutorGroup != null && studentYear.tutorGroup.faculty != null && studentYear.tutorGroup.faculty.apd != null ? studentYear.tutorGroup.faculty.apd.initials  : null;
        this.startDate = studentYear.startDate != null ? studentYear.startDate : null;
        this.endDate = studentYear.endDate != null ? studentYear.endDate : null;
        this.ilr = studentYear.ilr != null ? studentYear.ilr : null;
        this.plh = studentYear.plh != null ? studentYear.plh : null;
        this.peeph = studentYear.peeph != null ? studentYear.peeph : null;
        this.gcseMathsGrade = studentYear.gcseMathsGrade != null ? studentYear.gcseMathsGrade : null;
        this.gcseEnglishGrade = studentYear.gcseEnglishGrade != null ? studentYear.gcseEnglishGrade : null;
        this.candidateNo = studentYear.candidateNo != null ? studentYear.candidateNo : null;
        this.llddHealthProblemId = studentYear.llddHealthProblem != null ? studentYear.llddHealthProblem.id : null;
        this.lda = studentYear.lda != null ? studentYear.lda : null;
        this.ehc = studentYear.ehc != null ? studentYear.ehc : null;
        this.hns = studentYear.hns != null ? studentYear.hns : null;
        this.sen = studentYear.sen != null ? studentYear.sen : null;
        this.mathsConditionOfFundingId = studentYear.mathsConditionOfFunding != null ? studentYear.mathsConditionOfFunding.id : null;
        this.englishConditionOfFundingId = studentYear.englishConditionOfFunding != null ? studentYear.englishConditionOfFunding.id : null;
        this.learningSupportCost = studentYear.learningSupportCost != null ? studentYear.learningSupportCost : null;
        this.onContract = studentYear.onContract != null ? studentYear.onContract : null;
        this.initialPostcode = studentYear.initialPostcode != null ? studentYear.initialPostcode : null;
        this.externalCandidate = studentYear.externalCandidate != null ? studentYear.externalCandidate : null;
        this.destinationId = studentYear.destination != null ? studentYear.destination.id : null;
        this.destinationCollegeEmployer = studentYear.destinationCollegeEmployer;
        this.destinationCourseCareer = studentYear.destinationCourseCareer;
        this.breakInLearning = studentYear.breakInLearning;
    }
    
    public static StudentYearDto mapFromStudentYearEntity(StudentYear studentYear) {
        return new StudentYearDto(studentYear);
    }
    
    public static List<StudentYearDto> mapFromStudentYearEntities(List<StudentYear> studentYears) {
        List<StudentYear> output = studentYears.collect { studentYear -> new StudentYearDto(studentYear) };
        return output
    }
}
