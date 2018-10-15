package uk.ac.reigate.dto.exam;


import java.util.Date;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;

import groovy.transform.EqualsAndHashCode
import uk.ac.reigate.domain.academic.Student
import uk.ac.reigate.domain.exam.ExamOption
import uk.ac.reigate.domain.exam.EdiAuditEntryLog
import uk.ac.reigate.domain.exam.EdiAuditFileLog;
import uk.ac.reigate.domain.learning_support.ReferralReason;
import uk.ac.reigate.domain.learning_support.StudentReferralReason

/**
 *
 * JSON serializable DTO containing ReferralReason data
 *
 */
@JsonSerialize
@EqualsAndHashCode(includeFields=true)
public class EdiAuditEntryLogDto implements Serializable {
    
    @JsonProperty
    private Student student;
    
    @JsonProperty
    private ExamOption examOption;
    
    @JsonProperty
    private EdiAuditFileLog ediAuditFileLog
    
    
    /**
     * Default No Args constructor
     */
    public EdiAuditEntryLogDto() {
    }
    
    /**
     * Constructor to create a ReferralReasonDto object
     *
     * @param id the Id for the ReferralReason
     * @param code the code for the ReferralReason
     * @param description the description for the ReferralReason
     */
    public EdiAuditEntryLogDto(Student student, ExamOption examOption, EdiAuditFileLog ediAuditFileLog) {
        this.student = student;
        this.examOption = examOption;
        this.ediAuditFileLog = ediAuditFileLog;
    }
    
    public EdiAuditEntryLogDto(EdiAuditEntryLog ediAuditEntryLog) {
        this.student = ediAuditEntryLog.student;
        this.examOption = ediAuditEntryLog.examOption;
        this.ediAuditFileLog = ediAuditEntryLog.ediAuditFileLog;
    }
    
    @Override
    public String toString() {
        return "EdiAuditLogDto [student=" + student.toString() +
                ", examOption=" + examOption.toString() +
                ", ediAuditFileLog=" + ediAuditFileLog.toString() +"]";
    }
    
    public static EdiAuditEntryLogDto mapFromEdiAuditEntryLogEntity(EdiAuditEntryLog ediAuditEntryLog) {
        EdiAuditEntryLogDto output = new EdiAuditEntryLogDto(ediAuditEntryLog.student, ediAuditEntryLog.examOption, ediAuditEntryLog.ediAuditFileLog);
        return output
    }
    
    public static List<EdiAuditEntryLogDto> mapFromEdiAuditEntryLogsEntities(List<EdiAuditEntryLog> ediAuditEntryLogs) {
        return ediAuditEntryLogs.collect { ediAuditEntryLog ->  new EdiAuditEntryLogDto(ediAuditEntryLog) };
    }
    
    public static EdiAuditEntryLog mapToEdiAuditEntryLogEntity(EdiAuditEntryLogDto ediAuditEntryLogDto) {
        return new EdiAuditEntryLog(ediAuditEntryLogDto.student, ediAuditEntryLogDto.examOption, ediAuditEntryLogDto.ediAuditFileLog)
    }
}
