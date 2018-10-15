package uk.ac.reigate.dto;


import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;

import groovy.transform.EqualsAndHashCode;
import uk.ac.reigate.domain.lookup.AttendanceMonitoring

/**
 *
 * JSON serializable DTO containing AttendanceMonitoring data
 *
 */
@JsonSerialize
@EqualsAndHashCode(includeFields=true)
public class AttendanceMonitoringDto implements Serializable {
    
    @JsonProperty
    private Integer id;
    
    @JsonProperty
    private String code;
    
    @JsonProperty
    private String description;
    
    @JsonProperty
    private String warningColour
    
    /**
     * Default No Args constructor
     */
    public AttendanceMonitoringDto() {
    }
    
    /**
     * Constructor to create a AttendanceMonitoringDto object
     *
     * @param id the Id for the AttendanceMonitoring
     * @param code the code for the AttendanceMonitoring
     * @param description the description for the AttendanceMonitoring
     */
    public AttendanceMonitoringDto(Integer id, String code, String description, String warningColour) {
        this.id = id;
        this.code = code;
        this.description = description;
        this.warningColour = warningColour;
    }
    
    /**
     * Constructor to create an AttendanceMonitoringDto object from an AttendanceMonitoring object
     *
     * @param attendanceMonitoring the AttendanceMonitoring object to use for construction
     */
    AttendanceMonitoringDto(AttendanceMonitoring attendanceMonitoring) {
        this.id = attendanceMonitoring.id;
        this.code = attendanceMonitoring.code;
        this.description = attendanceMonitoring.description;
        this.warningColour = attendanceMonitoring.warningColour;
    }
    
    @Override
    public String toString() {
        return "AttendanceMonitoringDto [id=" + id + ", code=" + code + ", description=" + description + ", warningColour=" + warningColour +"]";
    }
    
    public AttendanceMonitoring toAttendanceMonitoring() {
        return new AttendanceMonitoring(this.id, this.code, this.description, this.warningColour)
    }
    
    public static AttendanceMonitoring mapFromEntity(AttendanceMonitoring attendanceMonitoring){
        return new AttendanceMonitoringDto(attendanceMonitoring)
    }
    public static AttendanceMonitoringDto mapFromAttendanceMonitoringEntity(AttendanceMonitoring attendanceMonitoring) {
        return new AttendanceMonitoringDto(attendanceMonitoring);
    }
    
    public static List<AttendanceMonitoringDto> mapFromAttendanceMonitoringsEntities(List<AttendanceMonitoring> attendanceMonitorings) {
        List<AttendanceMonitoringDto> output = attendanceMonitorings.collect { attendanceMonitoring ->  new AttendanceMonitoringDto(attendanceMonitoring) };
        return output
    }
    
    public static AttendanceMonitoring mapToAttendanceMonitoringEntity(AttendanceMonitoringDto attendanceMonitoringDto) {
        return new AttendanceMonitoring(attendanceMonitoringDto.id, attendanceMonitoringDto.code, attendanceMonitoringDto.description, attendanceMonitoringDto.warningColour)
    }
}
