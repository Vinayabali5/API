package uk.ac.reigate.dto.exam;


import java.util.Date;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;

import groovy.transform.EqualsAndHashCode
import uk.ac.reigate.domain.academic.Student
import uk.ac.reigate.domain.exam.ExamSeries
import uk.ac.reigate.domain.exam.EdiAuditFileLog

/**
 *
 * JSON serializable DTO containing ReferralReason data
 *
 */
@JsonSerialize
@EqualsAndHashCode(includeFields=true)
public class EdiAuditFileLogDto implements Serializable {
    
    @JsonProperty
    private Integer ediAuditFileLogId;
    
    @JsonProperty
    private ExamSeries examSeries;
    
    @JsonProperty
    private String ediFileName
    
    @JsonProperty
    private Date fileTimeStamp
    
    /**
     * Default No Args constructor
     */
    public EdiAuditFileLogDto() {}
    
    /**
     * Constructor to create a ReferralReasonDto object
     *
     * @param id the Id for the ReferralReason
     * @param code the code for the ReferralReason
     * @param description the description for the ReferralReason
     */
    public EdiAuditFileLogDto(Integer ediAuditFileLogId, ExamSeries examSeries, String ediFileName, Date fileTimeStamp) {
        this.ediAuditFileLogId = ediAuditFileLogId;
        this.examSeries = examSeries;
        this.ediFileName = ediFileName;
        this.fileTimeStamp = fileTimeStamp;
    }
    
    public EdiAuditFileLogDto(EdiAuditFileLog ediAuditFileLog) {
        this.ediAuditFileLogId = ediAuditFileLog.id
        this.examSeries = ediAuditFileLog.examSeries;
        this.ediFileName = ediAuditFileLog.ediFileName;
        this.fileTimeStamp = ediAuditFileLog.fileTimeStamp;
    }
    
    @Override
    public String toString() {
        return "EdiAuditFileLogDto [ediAuditFileLogId=" + this.ediAuditFileLogId.toString() +
                ", examSeries=" + this.examSeries.toString() +
                ", ediFileName=" + this.ediFileName +
                ", fileTimeStamp=" + this.fileTimeStamp.toString() +"]";
    }
    
    public static EdiAuditFileLogDto mapFromEdiAuditFileLogEntity(EdiAuditFileLog ediAuditFileLog) {
        EdiAuditFileLogDto output = new EdiAuditFileLogDto(ediAuditFileLog.Id, ediAuditFileLog.examSeries, ediAuditFileLog.ediFileName, ediAuditFileLog.fileTimeStamp);
        return output
    }
    
    public static List<EdiAuditFileLogDto> mapFromEdiAuditFileLogsEntities(List<EdiAuditFileLog> ediAuditFileLogs) {
        return ediAuditFileLogs.collect { ediAuditFileLog ->  new EdiAuditFileLogDto(ediAuditFileLog) };
    }
    
    public static EdiAuditFileLog mapToEdiAuditFileLogEntity(EdiAuditFileLogDto ediAuditFileLogDto, ExamSeries examSeries) {
        return new EdiAuditFileLog(ediAuditFileLogDto.ediFileName, examSeries, ediAuditFileLogDto.ediFileName, ediAuditFileLogDto.fileTimeStamp)
    }
}
