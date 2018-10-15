package uk.ac.reigate.dto

import com.fasterxml.jackson.annotation.JsonProperty
import com.fasterxml.jackson.databind.annotation.JsonSerialize
import groovy.transform.EqualsAndHashCode
import uk.ac.reigate.domain.register.StudentOverallAttendance

/**
 *
 * JSON serializable DTO containing StudentOverallAttendance data
 *
 */
@JsonSerialize
@EqualsAndHashCode(includeFields=true)
public class StudentOverallAttendanceDto implements Serializable {
    
    @JsonProperty
    private Integer studentId;
    
    @JsonProperty
    private Integer totalSessions
    
    @JsonProperty
    private Integer totalAbsence
    
    @JsonProperty
    private Integer totalAuthorisedAbsence
    
    @JsonProperty
    private Integer totalAdjusted
    
    @JsonProperty
    private Integer totalLate
    
    @JsonProperty
    private Integer totalAuthorisedLate
    
    @JsonProperty
    private Double attendance
    
    @JsonProperty
    private Double adjustedAttendance
    
    @JsonProperty
    private Double punctuality
    
    @JsonProperty
    private Double adjustedPunctuality
    
    /**
     * Default No Args constructor
     */
    public StudentOverallAttendanceDto() {
    }
    
    /**
     * Constructor to create a StudentOverallAttendanceDto object
     *
     * @param studentId the studentId for the StudentOverallAttendance
     * @param code the code for the StudentOverallAttendance
     * @param description the description for the StudentOverallAttendance
     */
    public StudentOverallAttendanceDto(Integer studentId, Integer totalSessions, Integer totalAbsence, Integer totalAuthorisedAbsence, Integer totalAdjusted, Integer totalLate, Integer totalAuthorisedLate, Double attendance, Double adjustedAttendance, Double punctuality, Double adjustedPunctuality) {
        this.studentId = studentId;
        this.totalSessions = totalSessions;
        this.totalAbsence = totalAbsence;
        this.totalAuthorisedAbsence = totalAuthorisedAbsence;
        this.totalAdjusted = totalAdjusted;
        this.totalLate = totalLate;
        this.totalAuthorisedLate = totalAuthorisedLate;
        this.attendance = attendance;
        this.adjustedAttendance = adjustedAttendance;
        this.punctuality = punctuality;
        this.adjustedPunctuality = adjustedPunctuality;
    }
    
    /**
     * Constructor to create a StudentOverallAttendanceDto object from a StudentOverallAttendance object
     *
     * @param studentOverallAttendance the StudentOverallAttendance object to use for construction
     */
    public StudentOverallAttendanceDto(StudentOverallAttendance studentOverallAttendance) {
        this.studentId = studentOverallAttendance.student.id;
        this.totalSessions = studentOverallAttendance.totalSessions;
        this.totalAbsence = studentOverallAttendance.totalAbsence;
        this.totalAuthorisedAbsence = studentOverallAttendance.totalAuthorisedAbsence;
        this.totalAdjusted = studentOverallAttendance.totalAdjusted;
        this.totalLate = studentOverallAttendance.totalLate;
        this.totalAuthorisedLate = studentOverallAttendance.totalAuthorisedLate;
        this.attendance = studentOverallAttendance.attendance;
        this.adjustedAttendance = studentOverallAttendance.adjustedAttendance;
        this.punctuality = studentOverallAttendance.punctuality;
        this.adjustedPunctuality = studentOverallAttendance.adjustedPunctuality;
    }
    
    @Override
    public String toString() {
        return "StudentOverallAttendanceDto [studentId=" + studentId + ", totalSessions=" + totalSessions + ", totalAbsence=" + totalAbsence + ", totalAuthorisedAbsence=" + totalAuthorisedAbsence + ", totalAdjusted=" + totalAdjusted + ", totalLate=" + totalLate + ", totalAuthorisedLate=" + totalAuthorisedLate+", attendance=" + attendance +", adjustedAttendance=" + adjustedAttendance +", punctuality=" + punctuality +", adjustedPunctuality=" + adjustedPunctuality +"]";
    }
    
    public static StudentOverallAttendanceDto mapFromStudentOverallAttendanceEntity(StudentOverallAttendance studentOverallAttendance) {
        StudentOverallAttendanceDto output
        if (studentOverallAttendance != null) {
            output = new StudentOverallAttendanceDto(studentOverallAttendance);
        } else {
            output = null
        }
        return output
    }
    
    public static List<StudentOverallAttendanceDto> mapFromStudentOverallAttendancesEntities(List<StudentOverallAttendance> studentOverallAttendances) {
        List<StudentOverallAttendanceDto> output = studentOverallAttendances.collect { studentOverallAttendance ->  mapFromStudentOverallAttendanceEntity(studentOverallAttendance) };
        return output
    }
}
