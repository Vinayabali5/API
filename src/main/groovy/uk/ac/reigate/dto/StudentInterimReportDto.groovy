package uk.ac.reigate.dto

import uk.ac.reigate.domain.academic.AcademicYear
import uk.ac.reigate.domain.academic.StudentEntryQualification;
import uk.ac.reigate.domain.academic.StudentInterimReport
import uk.ac.reigate.domain.lookup.PossibleGrade

import groovy.transform.EqualsAndHashCode
import java.util.List;

import javax.persistence.OneToOne;

import com.fasterxml.jackson.annotation.JsonFormat
import com.fasterxml.jackson.annotation.JsonProperty
import com.fasterxml.jackson.databind.annotation.JsonSerialize;


/**
 *
 * JSON serializable DTO containing Holiday data
 *
 */
@JsonSerialize
@EqualsAndHashCode(includeFields=true)
class StudentInterimReportDto {
    
    @JsonProperty
    Integer studentId
    
    @JsonProperty
    Integer interimReportId
    
    @JsonProperty
    String _interimReportCode;
    
    @JsonProperty
    String _interimReportDescription;
    
    @JsonProperty
    String _interimReportYear;
    
    @JsonProperty
    Integer courseId
    
    @JsonProperty
    String _courseDescription
    
    @JsonProperty
    Integer motivation
    
    @JsonProperty
    Integer classEthic
    
    @JsonProperty
    Integer timeManagement
    
    @JsonProperty
    Float _averageEffort
    
    @JsonProperty
    Integer totalPossible
    
    @JsonProperty
    Integer absence
    
    @JsonProperty
    Integer authorisedAbsence
    
    @JsonProperty
    Integer late
    
    @JsonProperty
    Float _attendancePercentage
    
    @JsonProperty
    Float _adjustedAttendancePercentage
    
    @JsonProperty
    Float _punctualityPercentage
    
    @JsonProperty
    Integer currentPredictedGradeId
    
    @JsonProperty
    String  _currentPredictedGrade
    
    @JsonProperty
    Integer keyAssessment1Id
    
    @JsonProperty
    String _keyAssessment1Grade
    
    @JsonProperty
    Integer keyAssessment2Id
    
    @JsonProperty
    String _keyAssessment2Grade
    
    /**
     * Default No Args constructor
     */
    StudentInterimReportDto() {}
    
    /**
     * Constructor to create a StudentInterimReportDto object from a StudentInterimReport object
     *
     * @param studentInterimReport the StudentInterimReport object to use for construction
     */
    StudentInterimReportDto(StudentInterimReport studentInterimReport){
        this.studentId = studentInterimReport.student != null ? studentInterimReport.student.id : null;
        this.interimReportId = studentInterimReport.interimReport != null ? studentInterimReport.interimReport.id : null;
        this._interimReportCode = studentInterimReport.interimReport != null ? studentInterimReport.interimReport.code : ''
        this._interimReportDescription = studentInterimReport.interimReport != null ? studentInterimReport.interimReport.description : ''
        this._interimReportYear = studentInterimReport.interimReport != null && studentInterimReport.interimReport.year != null ? studentInterimReport.interimReport.year.code : null;
        
        this.courseId = studentInterimReport.course != null ? studentInterimReport.course.id : null;
        this._courseDescription = studentInterimReport.course != null ? studentInterimReport.course.spec : null;
        
        this.motivation = studentInterimReport.motivation != null ? studentInterimReport.motivation : null;
        this.classEthic = studentInterimReport.classEthic != null ? studentInterimReport.classEthic : null;
        this.timeManagement = studentInterimReport.timeManagement != null ? studentInterimReport.timeManagement : null;
        
        // Calculate the average effort grade
        if (studentInterimReport.motivation != null && studentInterimReport.classEthic != null && studentInterimReport.timeManagement != null) {
            this._averageEffort = (studentInterimReport.motivation + studentInterimReport.classEthic + studentInterimReport.timeManagement) / 3
        }
        
        this.totalPossible = studentInterimReport.totalPossible != null ? studentInterimReport.totalPossible : null;
        this.absence = studentInterimReport.absence != null ? studentInterimReport.absence : null;
        this.authorisedAbsence = studentInterimReport.authorisedAbsence != null ? studentInterimReport.authorisedAbsence : null;
        this.late = studentInterimReport.late != null ? studentInterimReport.late : null;
        
        // Calculate the attendance percentage and authorised attendance percentage
        if (studentInterimReport.totalPossible != null && studentInterimReport.absence != null && studentInterimReport.authorisedAbsence != null) {
            if (studentInterimReport.totalPossible != 0) {
                this._attendancePercentage = (studentInterimReport.totalPossible - studentInterimReport.absence) / studentInterimReport.totalPossible
                this._adjustedAttendancePercentage = (studentInterimReport.totalPossible - (studentInterimReport.absence - studentInterimReport.authorisedAbsence)) / studentInterimReport.totalPossible
            } else {
                this._attendancePercentage = null
                this._adjustedAttendancePercentage = null
            }
        }
        
        // Calculate the punctuality percentage
        if (studentInterimReport.totalPossible != null && studentInterimReport.absence != null && studentInterimReport.late != null) {
            if (studentInterimReport.totalPossible - studentInterimReport.absence != 0) {
                this._punctualityPercentage = ((studentInterimReport.totalPossible - studentInterimReport.absence) - studentInterimReport.late) / (studentInterimReport.totalPossible - studentInterimReport.absence)
            } else {
                this._punctualityPercentage = null
            }
        }
        
        this.currentPredictedGradeId = studentInterimReport.currentPredictedGrade != null ? studentInterimReport.currentPredictedGrade.id : null;
        this._currentPredictedGrade = studentInterimReport.currentPredictedGrade != null ? studentInterimReport.currentPredictedGrade.grade : ''
        this.keyAssessment1Id = studentInterimReport.keyAssessment1 != null ? studentInterimReport.keyAssessment1.id : null;
        this._keyAssessment1Grade = studentInterimReport.keyAssessment1 != null ? studentInterimReport.keyAssessment1.grade : ''
        this.keyAssessment2Id = studentInterimReport.keyAssessment2 != null ? studentInterimReport.keyAssessment2.id : null;
        this._keyAssessment2Grade = studentInterimReport.keyAssessment2 != null ? studentInterimReport.keyAssessment2.grade : ''
    }
    
    public static StudentInterimReportDto mapFromStudentInterimReportEntity(StudentInterimReport studentInterimReport) {
        StudentInterimReportDto output = new StudentInterimReportDto(studentInterimReport);
        return output
    }
    
    public static List<StudentInterimReportDto> mapFromStudentInterimReportEntities(List<StudentInterimReport> studentInterimReports) {
        return studentInterimReports.collect { studentInterimReport ->  new StudentInterimReportDto(studentInterimReport) };
    }
}

