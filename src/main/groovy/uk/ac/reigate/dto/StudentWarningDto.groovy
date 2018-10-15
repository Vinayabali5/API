package uk.ac.reigate.dto

import groovy.transform.EqualsAndHashCode

import java.security.InvalidParameterException

import uk.ac.reigate.domain.academic.StudentYear

import com.fasterxml.jackson.annotation.JsonProperty
import com.fasterxml.jackson.databind.annotation.JsonSerialize


/**
 *
 * JSON serializable DTO containing StudentWarning data
 *
 */
@JsonSerialize
@EqualsAndHashCode(includeFields=true)
public class StudentWarningDto implements Serializable{
    
    
    @JsonProperty
    private Integer studentId;
    
    @JsonProperty
    private Integer yearId;
    
    @JsonProperty
    private Integer attendanceMonitoringId;
    
    @JsonProperty
    private String _attendanceMonitoringCode;
    
    @JsonProperty
    private String _attendanceMonitoringDescription;
    
    @JsonProperty
    private Integer punctualityMonitoringId;
    
    @JsonProperty
    private String _punctualityMonitoringCode;
    
    @JsonProperty
    private String _punctualityMonitoringDescription;
    
    
    /**
     * Default No Args constructor
     */
    public StudentWarningDto() {}
    
    /**
     * Constructor to create a StudentWarningDto object from a StudentYear object
     *
     * @param studentYear the StudentYear object to use for construction
     */
    public StudentWarningDto(StudentYear studentYear) {
        this.studentId = studentYear.student != null ? studentYear.student.id : null;
        this.yearId = studentYear.year != null ? studentYear.year.id : null;
        this.attendanceMonitoringId = studentYear.attendanceMonitoring != null ? studentYear.attendanceMonitoring.id : null;
        this.punctualityMonitoringId = studentYear.punctualityMonitoring != null ? studentYear.punctualityMonitoring.id : null;
        this._attendanceMonitoringCode = studentYear.attendanceMonitoring != null ? studentYear.attendanceMonitoring.code : '';
        this._attendanceMonitoringDescription = studentYear.attendanceMonitoring != null ? studentYear.attendanceMonitoring.description : '';
        this._punctualityMonitoringCode = studentYear.punctualityMonitoring != null ? studentYear.punctualityMonitoring.code : '';
        this._punctualityMonitoringDescription = studentYear.punctualityMonitoring != null ? studentYear.punctualityMonitoring.description : '';
    }
    
    /**
     * Constructor to create a StudentWarningDto object with the basic data with no linked objects.
     * 
     * @param studentId
     * @param yearId
     * @param attendanceMonitoringId
     * @param punctualityMonitoringId
     */
    public StudentWarningDto(Integer studentId, Integer yearId, Integer attendanceMonitoringId, Integer punctualityMonitoringId) {
        this.studentId = studentYear.studentId;
        this.yearId = studentYear.yearId;
        this.attendanceMonitoringId = studentYear.attendanceMonitoringId;
        this.punctualityMonitoringId = studentYear.punctualityMonitoringId;
    }
    
    public static StudentWarningDto mapFromStudentWarningEntity(StudentYear studentYear) {
        StudentWarningDto output = new StudentWarningDto(studentYear);
        output._attendanceMonitoringCode = studentYear.attendanceMonitoring != null ? studentYear.attendanceMonitoring.code : '';
        output._attendanceMonitoringDescription = studentYear.attendanceMonitoring != null ? studentYear.attendanceMonitoring.description : '';
        output._punctualityMonitoringCode = studentYear.punctualityMonitoring != null ? studentYear.punctualityMonitoring.code : '';
        output._punctualityMonitoringDescription = studentYear.punctualityMonitoring != null ? studentYear.punctualityMonitoring.description : '';
        return output;
    }
    
    public static StudentYear mapToStudentWarningEntity(StudentWarningDto studentWarningDto) {
        return new StudentYear(studentWarningDto);
    }
}
