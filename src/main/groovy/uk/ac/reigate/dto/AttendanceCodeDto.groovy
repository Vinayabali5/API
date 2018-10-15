package uk.ac.reigate.dto;


import groovy.transform.EqualsAndHashCode
import uk.ac.reigate.domain.register.AttendanceCode

import com.fasterxml.jackson.annotation.JsonProperty
import com.fasterxml.jackson.databind.annotation.JsonSerialize

/**
 *
 * JSON serializable DTO containing AttendanceCode data
 *
 */
@JsonSerialize
@EqualsAndHashCode(includeFields=true)
public class AttendanceCodeDto implements Serializable {
    
    @JsonProperty
    private Integer id;
    
    @JsonProperty
    private String code;
    
    @JsonProperty
    private String description;
    
    @JsonProperty
    private String registerMark
    
    @JsonProperty
    private Boolean absence;
    
    @JsonProperty
    private Boolean authorisedAbsence;
    
    @JsonProperty
    private Boolean late;
    
    @JsonProperty
    private Boolean authorisedLate;
    
    @JsonProperty
    private Boolean included
    
    @JsonProperty
    private Boolean lastDateOfAttendance
    
    @JsonProperty
    private String htmlColour
    
    @JsonProperty
    private String accessColour
    
    /**
     * Default No Args constructor
     */
    public AttendanceCodeDto() {
    }
    
    /**
     * Constructor to create a AttendanceCodeDto object
     *
     * @param id of the attendanceCode
     * @param code of the attendanceCode
     * @param description of the attendanceCode
     * @Param absence of the attendanceCode
     * @Param authorisedAbsence of the attendanceCode
     * @Param late of the attendanceCode
     * @Param authorisedLate of the attendanceCode
     */
    public AttendanceCodeDto(Integer id, String code, String description, String registerMark, Boolean absence, Boolean authorisedAbsence, Boolean late, Boolean authorisedLate, Boolean included, Boolean lastDateOfAttendance, String htmlColour, String accessColour) {
        this.id = id;
        this.code = code;
        this.description = description;
        this.registerMark = registerMark;
        this.absence = absence;
        this.authorisedAbsence = authorisedAbsence;
        this.late = late;
        this.authorisedLate = authorisedLate;
        this.included = included;
        this.lastDateOfAttendance = lastDateOfAttendance;
        this.htmlColour = htmlColour;
        this.accessColour = accessColour;
    }
    
    /**
     * Constructor to create an AttendanceCodeDto object from an AttendanceCode object
     *
     * @param attendanceCode the AttendanceCode object to use for construction
     */
    AttendanceCodeDto(AttendanceCode attendanceCode) {
        this.id = attendanceCode.id;
        this.code = attendanceCode.code;
        this.description = attendanceCode.description;
        this.registerMark = attendanceCode.registerMark;
        this.absence = attendanceCode.absence;
        this.authorisedAbsence = attendanceCode.authorisedAbsence;
        this.late = attendanceCode.late;
        this.authorisedLate = attendanceCode.authorisedLate;
        this.included = attendanceCode.included;
        this.lastDateOfAttendance = attendanceCode.lastDateOfAttendance;
        this.htmlColour = attendanceCode.htmlColour;
        this.accessColour = attendanceCode.accessColour;
    }
    
    @Override
    public String toString() {
        return "AttendanceCodeDto [id=" + id + ", code=" + code + ", description=" + description + ", registerMark="+ registerMark +", absence=" + absence + ", authorisedAbsence=" + authorisedAbsence + ", late=" + late + ", authorisedLate=" + authorisedLate + ", included=" + included +", lastDateOfAttendance=" + lastDateOfAttendance +", htmlColour=" + htmlColour +", accessColour=" + accessColour +"]";
    }
    
    public AttendanceCode toAttendanceCode() {
        return new AttendanceCode(this.id, this.code, this.description, this.registerMark, this.absence, this.authorisedAbsence, this.late, this.authorisedLate, this.included, this.lastDateOfAttendance, this.htmlColour, this.accessColour)
    }
    
    public static AttendanceCodeDto mapFromEntity(AttendanceCode attendanceCode){
        return new AttendanceCode(attendanceCode);
    }
    
    public static AttendanceCodeDto mapFromAttendanceCodeEntity(AttendanceCode attendanceCode) {
        return new AttendanceCodeDto(attendanceCode);
    }
    
    public static List<AttendanceCodeDto> mapFromAttendanceCodesEntities(List<AttendanceCode> attendanceCodes) {
        return attendanceCodes.collect { attendanceCode ->  new AttendanceCodeDto(attendanceCode) };
    }
    
    public static AttendanceCode mapToAttendanceCodeEntity(AttendanceCodeDto attendanceCodeDto) {
        return new AttendanceCode(attendanceCodeDto.id, attendanceCodeDto.code, attendanceCodeDto.description, attendanceCodeDto.registerMark, attendanceCodeDto.absence, attendanceCodeDto.authorisedAbsence, attendanceCodeDto.late, attendanceCodeDto.authorisedLate, attendanceCodeDto.included, attendanceCodeDto.lastDateOfAttendance, attendanceCodeDto.htmlColour, attendanceCodeDto.accessColour)
    }
}
