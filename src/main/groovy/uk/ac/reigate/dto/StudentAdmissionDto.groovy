package uk.ac.reigate.dto

import groovy.transform.EqualsAndHashCode
import uk.ac.reigate.domain.Staff
import uk.ac.reigate.domain.academic.Enrolment
import uk.ac.reigate.domain.academic.GCSEScore;
import uk.ac.reigate.domain.academic.SpecialCategory;
import uk.ac.reigate.domain.academic.Student
import uk.ac.reigate.domain.academic.StudentYear
import uk.ac.reigate.domain.learning_support.StudentReferralReason
import uk.ac.reigate.domain.lookup.SchoolReportStatus
import uk.ac.reigate.services.StaffService
import java.util.Date;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired
import com.fasterxml.jackson.annotation.JsonFormat
import com.fasterxml.jackson.annotation.JsonProperty
import com.fasterxml.jackson.databind.annotation.JsonSerialize

@JsonSerialize
@EqualsAndHashCode(includeFields=true)
class StudentAdmissionDto {
    
    @JsonProperty
    Integer id
    
    @JsonProperty
    AcademicYearDto yearApplied
    
    @JsonProperty
    String admissionsNotes
    
    @JsonProperty
    Date refRequested
    
    @JsonProperty
    Date refReceived
    
    @JsonProperty
    Date interviewDate
    
    @JsonProperty
    Integer interviewerId
    
    @JsonProperty
    String _interviewerName
    
    @JsonProperty
    Date received
    
    @JsonProperty
    Date schoolReportReceived
    
    @JsonProperty
    Date schoolReportRequested
    
    
    @JsonProperty
    Integer schoolReportStatusId
    
    @JsonProperty
    String _schoolReportStatusDescription
    
    @JsonProperty
    Date blueCard
    
    @JsonProperty
    Boolean cannotAttendIntroDay
    
    @JsonProperty
    Boolean cannotAttendInductionDay
    
    @JsonProperty
    Date inductionDate
    
    @JsonProperty
    Date enrolmentInterviewDate
    
    @JsonProperty
    Date enrolmentInterviewTime
    /**
     * Default NoArgs constructor
     */
    StudentAdmissionDto() {}
    
    /**
     * Constructor to create a StudentAdmissionDto object from a Student and StudentYear object
     *
     * @param student, studentYear the Student, StudentYear object to use for construction
     */
    StudentAdmissionDto(Student student) {
        this.id = student.id
        this.yearApplied = AcademicYearDto.mapFromEntity(student.academicYear)
        this.admissionsNotes = student.admissionsNotes
        this.refRequested = student.refRequested
        this.refReceived = student.refReceived
        this.interviewDate = student.interviewDate
        this.interviewerId = student.interviewer != null ? student.interviewer.id : null
        this._interviewerName = student.interviewer != null ? student.interviewer.knownAs : null;
        this.received = student.received
        this.schoolReportReceived = student.reportReceived
        this.schoolReportRequested = student.reportRequested
        this.schoolReportStatusId = student.schoolReportStatus != null? student.schoolReportStatus.id : null
        this._schoolReportStatusDescription = student.schoolReportStatus != null? student.schoolReportStatus.description : null
        this.blueCard = student.blueCard
        this.cannotAttendIntroDay = student.cannotAttendIntro
        this.cannotAttendInductionDay = student.cannotAttendInduction
        this.inductionDate = student.inductionDate
        this.enrolmentInterviewDate = student.enrolmentInterviewDate
        this.enrolmentInterviewTime = student.enrolmentInterviewDate
    }
    
    public static Student updateStudentAdmissions(Student student, StudentAdmissionDto studentAdmissionsDto, Staff interviewer, SchoolReportStatus schoolReportStatus){
        
        student.admissionsNotes = studentAdmissionsDto.admissionsNotes
        student.refRequested = studentAdmissionsDto.refRequested
        student.refReceived = studentAdmissionsDto.refReceived
        student.interviewDate = studentAdmissionsDto.interviewDate
        student.interviewer = interviewer
        student.received = studentAdmissionsDto.received
        student.reportReceived = studentAdmissionsDto.schoolReportReceived
        student.reportRequested = studentAdmissionsDto.schoolReportRequested
        student.schoolReportStatus = schoolReportStatus
        student.blueCard = studentAdmissionsDto.blueCard
        student.cannotAttendIntro = studentAdmissionsDto.cannotAttendIntroDay
        student.cannotAttendInduction = studentAdmissionsDto.cannotAttendInductionDay
        student.inductionDate = studentAdmissionsDto.inductionDate
        student.enrolmentInterviewDate = studentAdmissionsDto.enrolmentInterviewDate
        student.enrolmentInterviewTime = studentAdmissionsDto.enrolmentInterviewDate
        return student;
    }
}
