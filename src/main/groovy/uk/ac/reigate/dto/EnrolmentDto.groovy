package uk.ac.reigate.dto

import groovy.transform.EqualsAndHashCode

import com.fasterxml.jackson.annotation.JsonProperty
import com.fasterxml.jackson.databind.annotation.JsonSerialize

import uk.ac.reigate.domain.academic.AcademicYear;
import uk.ac.reigate.domain.academic.Course;
import uk.ac.reigate.domain.academic.CourseGroup;
import uk.ac.reigate.domain.academic.Enrolment
import uk.ac.reigate.domain.academic.Student;
import uk.ac.reigate.domain.ilr.AimType
import uk.ac.reigate.domain.ilr.CompletionStatus
import uk.ac.reigate.domain.ilr.FundingModel;
import uk.ac.reigate.domain.ilr.Outcome
import uk.ac.reigate.domain.ilr.SourceOfFunding;
import uk.ac.reigate.domain.ilr.WithdrawalReason
import uk.ac.reigate.domain.lookup.CentralMonitoring

@JsonSerialize
@EqualsAndHashCode(includeFields=true)
class EnrolmentDto {
    
    @JsonProperty
    Integer enrolmentId
    
    @JsonProperty
    Integer studentId
    
    @JsonProperty
    StudentSummaryDto student
    
    @JsonProperty
    Integer yearId
    
    @JsonProperty
    String _academicYearCode
    
    @JsonProperty
    Integer courseId
    
    @JsonProperty
    Integer courseGroupId
    
    
    /**
     * The course reference e.g. MAH
     */
    @JsonProperty
    String _courseReference
    
    /**
     * The course group reference e.g. 1B
     */
    @JsonProperty
    String _courseGroupReference
    
    @JsonProperty
    Date startDate
    
    @JsonProperty
    Date endDate
    
    /**
     * The qualification start date according to the ILR
     */
    @JsonProperty
    Date qualificationStartDate
    
    /**
     * The planned end date according to the ILR
     */
    @JsonProperty
    Date plannedEndDate
    
    @JsonProperty
    Integer aimTypeId
    
    @JsonProperty
    AimTypeDto _aimType;
    
    @JsonProperty
    Integer completionStatusId
    
    @JsonProperty
    CompletionStatusDto _completionStatus
    
    @JsonProperty
    Integer outcomeId
    
    @JsonProperty
    OutcomeDto _outcome
    
    @JsonProperty
    String grade
    
    @JsonProperty
    Integer withdrawalReasonId
    
    @JsonProperty
    WithdrawalReasonDto _withdrawalReason
    
    @JsonProperty
    Boolean ilr
    
    @JsonProperty
    Integer centralMonitoringId
    
    @JsonProperty
    CentralMonitoringDto _centralMonitoring
    
    @JsonProperty
    Integer plh
    
    @JsonProperty
    Integer peeph
    
    @JsonProperty
    Integer fundingModelId
    
    @JsonProperty
    String _fundingModelCode
    
    @JsonProperty
    String _fundingModelDescription
    
    @JsonProperty
    Integer sourceOfFundingId
    
    @JsonProperty
    String _sourceOfFundingCode
    
    @JsonProperty
    String _sourceOfFundingDescription
    
    @JsonProperty
    String _subjectDescription
    
    @JsonProperty
    String _levelDescription
    
    @JsonProperty
    String notes
    
    /**
     * Default No Args constructor
     */
    EnrolmentDto() {}
    
    /**
     * Constructor to create a EnrolmentDto object
     */
    @Deprecated
    EnrolmentDto(Integer id, Integer studentId, Integer yearId, Integer courseId, Integer courseGroupId,  String reference, String courseGroupReference, Date startDate, Date endDate, Date qualifiactionStartDate, Date expectedEndDate, AimType aimType, CompletionStatus completionStatus,  Outcome outcome, WithdrawalReason withdrawalReason, String grade, Boolean ilr, CentralMonitoring centralMonitoring, Integer plh, Integer peeph, Integer fundingModelId, Integer sourceOfFundingId, String notes) {
        this.enrolmentId = id;
        this.studentId = studentId;
        this.yearId = yearId;
        this.courseId = courseId;
        this.withdrawalReasonId = withdrawalReason != null ? withdrawalReason.id : null;
        this.courseGroupId = courseGroupId;
        this._courseReference = reference;
        this._courseGroupReference = courseGroupReference;
        this.startDate = startDate;
        this.endDate = endDate;
        this.qualificationStartDate = qualificationStartDate;
        this.plannedEndDate = plannedEndDate;
        this._aimType = aimType != null ? AimTypeDto.mapFromAimTypeEntity(aimType) : null;
        this.aimTypeId = aimType != null ? aimType.id : null;
        this._completionStatus = completionStatus != null ? CompletionStatusDto.mapFromCompletionStatusEntity(completionStatus) : null;
        this.completionStatusId = completionStatus != null ? completionStatus.id : null;
        this._outcome = outcome != null ? OutcomeDto.mapFromOutcomeEntity(outcome) : null;
        this.outcomeId = outcome != null ? outcome.id : null;
        this.centralMonitoringId = centralMonitoring != null ? centralMonitoring.id : null;
        this._centralMonitoring = centralMonitoring != null ? CentralMonitoringDto.mapFromCentralMonitoringEntity(centralMonitoring) : null;
        this.grade = grade;
        this._academicYearCode = academicYear != null ? AcademicYearDto.mapFromAcademicYearEntity(academicYear) : null;
        this._withdrawalReason = withdrawalReason != null ? WithdrawalReasonDto.mapFromWithdrawalReasonEntity(withdrawalReason) : null;
        this.ilr = ilr;
        this.plh = plh;
        this.peeph = peeph;
        this.fundingModelId = fundingModelId;
        this._fundingModelCode = fundingModelCode;
        this._fundingModelDescription = fundingModelDescription
        this.sourceOfFundingId = sourceOfFundingId
        this._sourceOfFundingCode = sourceOfFundingCode
        this._sourceOfFundingDescription = sourceOfFundingDescription
        this.notes = notes
    }
    
    /**
     * Constructor to create a EnrolmentDto object
     */
    @Deprecated
    EnrolmentDto(Integer enrolmentId, Integer studentId, Integer yearId, Integer courseId, Integer courseGroupId, Date startDate, Date endDate, Date qualificationStartDate, Date plannedEndDate, Integer aimTypeId, Integer completionStatusId, Integer withdrawalReasonId, Integer outcomeId, String grade, Boolean ilr, Integer centralMonitoringId, Integer plh, Integer peeph, Integer fundingModelId, Integer sourceOfFundingId, String notes){
        this.enrolmentId = enrolmentId;
        this.studentId = studentId;
        this.yearId = yearId;
        this.courseId = courseId;
        this.courseGroupId = courseGroupId;
        this.startDate = startDate;
        this.endDate = endDate;
        this.qualificationStartDate = qualificationStartDate;
        this.plannedEndDate = plannedEndDate;
        this.aimTypeId = aimTypeId;
        this.completionStatusId = completionStatusId;
        this.withdrawalReasonId = withdrawalReasonId;
        this.outcomeId = outcomeId;
        this.grade = grade;
        this.ilr = ilr;
        this.centralMonitoringId = centralMonitoringId;
        this.plh = plh;
        this.peeph = peeph;
        this.fundingModelId = fundingModelId;
        this.sourceOfFundingId = sourceOfFundingId;
        this.notes = notes;
    }
    
    /**
     * Constructor to create an EnrolmentDto object from a Enrolment object
     *
     * @param enrolment the Enrolment object to use for construction
     */
    EnrolmentDto(Enrolment enrolment) {
        this.enrolmentId = enrolment.id;
        this.studentId = enrolment.student != null ? enrolment.student.id : null;
        this.student = new StudentSummaryDto(enrolment.student)
        this.yearId = enrolment.year != null ? enrolment.year.id : null
        this._academicYearCode = enrolment.year != null ? enrolment.year.code : null
        this.courseId = enrolment.course != null ? enrolment.course.id : null
        this.courseGroupId = enrolment.courseGroup != null ? enrolment.courseGroup.id : null
        
        this._courseReference = enrolment.course != null ? enrolment.course.spec : null
        this._courseGroupReference = enrolment.courseGroup != null ? enrolment.courseGroup.spec : null
        
        this.startDate = enrolment.startDate
        this.endDate = enrolment.endDate
        this.qualificationStartDate = enrolment.qualificationStartDate
        this.plannedEndDate = enrolment.plannedEndDate
        
        this._aimType = enrolment.aimType != null ? AimTypeDto.mapFromAimTypeEntity(enrolment.aimType) : null
        this.aimTypeId = enrolment.aimType != null ? enrolment.aimType.id : null
        this._completionStatus = enrolment.completionStatus != null ? CompletionStatusDto.mapFromCompletionStatusEntity(enrolment.completionStatus) : null
        this.completionStatusId = enrolment.completionStatus != null ? enrolment.completionStatus.id : null
        this._outcome = enrolment.outcome != null ? OutcomeDto.mapFromOutcomeEntity(enrolment.outcome) : null
        this.outcomeId = enrolment.outcome != null ? enrolment.outcome.id : null
        this._withdrawalReason = enrolment.withdrawalReason != null ? WithdrawalReasonDto.mapFromWithdrawalReasonEntity(enrolment.withdrawalReason) : null
        this.withdrawalReasonId = enrolment.withdrawalReason != null ? enrolment.withdrawalReason.id : null
        this._centralMonitoring = enrolment.centralMonitoring != null ? CentralMonitoringDto.mapFromCentralMonitoringEntity(enrolment.centralMonitoring) : null
        this.centralMonitoringId = enrolment.centralMonitoring != null ? enrolment.centralMonitoring.id : null
        
        this.grade = enrolment.grade
        this.ilr = enrolment.ilr
        this.plh = enrolment.plh
        this.peeph = enrolment.peeph
        this.fundingModelId = enrolment.fundingModel != null ? enrolment.fundingModel.id : null
        this._fundingModelCode = enrolment.fundingModel != null ? enrolment.fundingModel.code : null
        this._fundingModelDescription = enrolment.fundingModel != null ? enrolment.fundingModel.description : null
        this.sourceOfFundingId = enrolment.sourceOfFunding != null ? enrolment.sourceOfFunding.id : null
        this._sourceOfFundingCode = enrolment.sourceOfFunding != null ? enrolment.sourceOfFunding.code : null
        this._sourceOfFundingDescription = enrolment.sourceOfFunding != null ? enrolment.sourceOfFunding.description : null
        this._subjectDescription = enrolment.course != null && enrolment.course.subject != null ? enrolment.course.subject.description : ''
        this._levelDescription = enrolment.course != null && enrolment.course.level != null ? enrolment.course.level.description : ''
        this.notes = enrolment.notes
    }
    
    /**
     * Constructor to create a EnrolmentDto object
     */
    @Deprecated
    EnrolmentDto(Integer enrolmentId, Student student, AcademicYear year, Course course, CourseGroup courseGroup, Date startDate, Date endDate, Date qualificationStartDate, Date plannedEndDate, AimType aimType, CompletionStatus completionStatus, WithdrawalReason withdrawalReason, Outcome outcome, String grade, Boolean ilr, CentralMonitoring centralMonitoring, Integer plh, Integer peeph, FundingModel fundingModel, SourceOfFunding sourceOfFunding, String notes){
        this(enrolmentId, student != null ? student.id : null, year != null ? year.id : null, course != null ? course.id : null, courseGroup != null ? courseGroup.id : null, startDate, endDate, qualificationStartDate, plannedEndDate, aimType != null ? aimType.id : null, completionStatus != null ? completionStatus.id : null, withdrawalReason != null ? withdrawalReason.id : null, outcome != null ? outcome.id : null, grade, ilr, centralMonitoring != null ? centralMonitoring.id : null, plh, peeph, fundingModel != null ? fundingModel.id : null, sourceOfFunding != null ? sourceOfFunding.id : null, notes != null ? notes : '')
    }
    
    @Override
    public String toString() {
        return "EnrolmentDto [enrolmentId=" + enrolmentId + ", student=" + studentId + ", academicYear="
        + yearId + ", course=" + courseId
        + ", courseGroup=" + courseGroupId + ", startDate=" + startDate + ", endDate="
        + endDate + ", qualificationStartDate=" + qualificationStartDate + ", plannedEndDate=" + plannedEndDate
        + ", aimType=" + aimTypeId + ", completionStatus=" + completionStatusId
        + ", outcome=" + outcomeId + ", grade=" + grade + ", withdrawalReason=" + withdrawalReasonId + ", ilr=" + ilr + ", centralMonitoring=" + centralMonitoringId + ", plh=" + plh +", peeph=" + peeph +", fundingModel=" + fundingModelId +", sourceOfFunding=" + sourceOfFundingId +"notes="+ notes +"]";
    }
    
    /**
     * This method is used to map an Enrolment object to an EnrolmentDto object
     * 
     * @param enrolment an Enrolment object
     * @return an EnrolmentDto object
     */
    public static EnrolmentDto mapFromEntity(Enrolment enrolment){
        return new EnrolmentDto(enrolment)
    }
    
    @Deprecated
    public static EnrolmentDto mapFromEnrolmentEntity(Enrolment enrolment){
        return this.mapFromEntity(enrolment)
    }
    
    @Deprecated
    public static List<EnrolmentDto> mapFromEnrolmentsEntity(List<Enrolment> enrolments) {
        return enrolments.collect { enrolment -> mapFromEnrolmentEntity(enrolment) };
    }
    
    @Deprecated
    public static Enrolment mapToEnrolmentEntity(EnrolmentDto enrolmentDto, Student student, AcademicYear year, Course course, CourseGroup courseGroup, AimType aimType, CompletionStatus completionStatus, WithdrawalReason withdrawalReason, Outcome outcome, CentralMonitoring centralMonitoring, FundingModel fundingModel, SourceOfFunding sourceOfFunding){
        return new Enrolment(enrolmentDto.enrolmentId, student, year, course, courseGroup, enrolmentDto.startDate, enrolmentDto.endDate, enrolmentDto.qualificationStartDate, enrolmentDto.plannedEndDate, aimType, completionStatus, withdrawalReason, outcome, enrolmentDto.grade, enrolmentDto.ilr, centralMonitoring, enrolmentDto.plh, enrolmentDto.peeph, fundingModel, sourceOfFunding , enrolmentDto.notes)
    }
    
    @Deprecated
    public static List<EnrolmentDto> mapFromEnrolmentListEntities(List<Enrolment> enrolmentList) {
        return enrolmentList.collect { enrolment ->  new EnrolmentDto(enrolment) };
    }
    
    /**
     * This method is used to map a List of Enrolment objects to a List of EnrolmentDto objects.
     * 
     * @param enrolmentList a List of Enrolment objects
     * @return a List of EnrolmentDto objects
     */
    public static List<EnrolmentDto> mapFromList(List<Enrolment> enrolmentList) {
        return enrolmentList.collect { enrolment ->  new EnrolmentDto(enrolment) };
    }
}
