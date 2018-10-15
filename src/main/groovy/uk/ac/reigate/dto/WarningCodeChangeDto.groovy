package uk.ac.reigate.dto;


import java.util.Date

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;

import groovy.transform.EqualsAndHashCode;
import uk.ac.reigate.domain.academic.AcademicYear
import uk.ac.reigate.domain.academic.Student
import uk.ac.reigate.domain.lookup.AttendanceMonitoring
import uk.ac.reigate.domain.lookup.PunctualityMonitoring
import uk.ac.reigate.domain.lookup.WarningCodeChange

/**
 *
 * JSON serializable DTO containing WarningCodeChange data
 *
 */
@JsonSerialize
@EqualsAndHashCode(includeFields=true)
public class WarningCodeChangeDto implements Serializable {
    
    @JsonProperty
    private Integer id;
    
    @JsonProperty
    private Integer studentId;
    
    @JsonProperty
    private Integer yearId;
    
    @JsonProperty
    private Integer previousAmId;
    
    @JsonProperty
    private String _previousAmDescription
    
    @JsonProperty
    private Integer currentAmId;
    
    @JsonProperty
    private String _currentAmDescription
    
    @JsonProperty
    private Integer previousPmId;
    
    @JsonProperty
    private String _previousPmDescription
    
    @JsonProperty
    private Integer currentPmId;
    
    @JsonProperty
    private String _currentPmDescription
    
    @JsonProperty
    private Date changeDate;
    
    /**
     * Default No Args constructor
     */
    public WarningCodeChangeDto() {
    }
    
    /**
     * Constructor to create an WarningCodeChangeDto object from an WarningCodeChange object
     *
     * @param warningCodeChange the WarningCodeChange object to use for construction
     */
    WarningCodeChangeDto(WarningCodeChange warningCodeChange) {
        this.id = warningCodeChange.id;
        this.studentId = warningCodeChange.student != null ? warningCodeChange.student.id : null;
        this.yearId = warningCodeChange.year != null ? warningCodeChange.year.id : null;
        this.previousAmId = warningCodeChange.previousAm != null ? warningCodeChange.previousAm.id : null;
        this._previousAmDescription = warningCodeChange.previousAm != null ? warningCodeChange.previousAm.description : '';
        this.currentAmId = warningCodeChange.currentAm != null ? warningCodeChange.currentAm.id : null;
        this._currentAmDescription = warningCodeChange.currentAm != null ? warningCodeChange.currentAm.description : '';
        this.previousPmId = warningCodeChange.previousPm != null ? warningCodeChange.previousPm.id : null;
        this._previousPmDescription = warningCodeChange.previousPm != null ? warningCodeChange.previousPm.description : '';
        this.currentPmId = warningCodeChange.currentPm != null ? warningCodeChange.currentPm.id : null;
        this._currentPmDescription = warningCodeChange.currentPm != null ? warningCodeChange.currentPm.description : '';
        this.changeDate = warningCodeChange.changeDate;
    }
    
    /**
     * Constructor to create a WarningCodeChangeDto object
     *
     * @param id the Id for the WarningCodeChange
     * @param code the code for the WarningCodeChange
     * @param description the description for the WarningCodeChange
     */
    public WarningCodeChangeDto(Integer id, Integer studentId, Integer yearId, Integer previousAmId, Integer currentAmId, Integer previousPmId, Integer currentPmId, Date changeDate) {
        this.id = id;
        this.studentId = studentId;
        this.yearId = yearId;
        this.previousAmId = previousAmId;
        this.currentAmId = currentAmId;
        this.previousPmId = previousPmId;
        this.currentPmId =  currentPmId;
        this.changeDate = changeDate;
    }
    
    public WarningCodeChangeDto(Integer id, Student student, AcademicYear year, AttendanceMonitoring previousAm, AttendanceMonitoring currentAm, PunctualityMonitoring previousPm, PunctualityMonitoring currentPm, Date changeDate) {
        this(id, student != null ? student.id : null, year != null ? year.id : null, previousAm != null ? previousAm.id : null, currentAm != null ? currentAm.id : null, previousAm != null ? previousPm.id : null, currentPm != null ? currentPm.id : null)
    }
    
    
    @Override
    public String toString() {
        return "WarningCodeChangeDto [id=" + id + ", student=" + studentId + ", year=" + yearId + ", previousAm=" + previousAmId + ", currentAm= " + currentAmId + ", previousPm=" + previousPmId +", currentPm=" + currentPmId +", changeDate=" + changeDate + "]";
    }
    
    
    public static WarningCodeChangeDto mapFromWarningCodeChangeEntity(WarningCodeChange warningCodeChange){
        return new WarningCodeChangeDto(warningCodeChange.id, warningCodeChange.student, warningCodeChange.year, warningCodeChange.previousAm, warningCodeChange.currentAm, warningCodeChange.previousPm, warningCodeChange.currentPm, warningCodeChange.changeDate)
    }
    
    public static List<WarningCodeChangeDto> mapFromWarningCodeChangesEntities(List<WarningCodeChange> warningCodeChanges) {
        List<WarningCodeChangeDto> output = warningCodeChanges.collect { warningCodeChange ->  new WarningCodeChangeDto(warningCodeChange) };
        return output
    }
    
    public static WarningCodeChange mapToWarningCodeChangeEntity(WarningCodeChangeDto warningCodeChangeDto, Student student, AcademicYear year, AttendanceMonitoring previousAm, AttendanceMonitoring currentAm, PunctualityMonitoring previousPm, PunctualityMonitoring currentPm) {
        return new WarningCodeChange(warningCodeChangeDto.id, student, year, previousAm, currentAm, previousPm, currentPm, warningCodeChangeDto.changeDate)
    }
}
