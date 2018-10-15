package uk.ac.reigate.dto;


import groovy.transform.EqualsAndHashCode

import java.text.SimpleDateFormat

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;

import uk.ac.reigate.domain.academic.Student;
import uk.ac.reigate.domain.cristal.MasterRegister
import uk.ac.reigate.domain.register.AttendanceCode;


/**
 *
 * JSON serializable DTO containing MasterRegister data
 *
 */
@JsonSerialize
@EqualsAndHashCode(includeFields=true)
public class MasterRegisterDto implements Serializable {
    
    @JsonProperty
    private Integer id;
    
    @JsonProperty
    private Integer sessionRef
    
    @JsonProperty
    private Integer studentId
    
    @JsonProperty
    private String subjectCode;
    
    @JsonProperty
    private String group;
    
    @JsonProperty
    private Integer attendanceId;
    
    @JsonProperty
    private AttendanceCodeDto _attendance;
    
    
    @JsonProperty
    private Date _sessionDate;
    
    @JsonProperty
    private Integer _sessionPeriod;
    
    
    /**
     * Default No Args constructor
     */
    public MasterRegisterDto() {}
    
    /**
     * Constructor to create a MasterRegisterDto object from a MasterRegister object
     *
     * @param masterRegister the MasterRegister object to use for construction
     */
    MasterRegisterDto(MasterRegister masterRegister) {
        this.id = masterRegister.id;
        this.sessionRef = masterRegister.sessionRef;
        this.studentId = masterRegister.student != null ? masterRegister.student.id : null;
        this.subjectCode = masterRegister.subjectCode;
        this.group = masterRegister.group;
        this.attendanceId = masterRegister.attendance != null ? masterRegister.attendance.id : null;
        this._attendance = new AttendanceCodeDto(masterRegister.attendance)
        this._sessionPeriod = (masterRegister.sessionRef % 100L);
        SimpleDateFormat originalFormat = new SimpleDateFormat("yyyyMMdd")
        this._sessionDate = originalFormat.parse((masterRegister.sessionRef / 100L).toString());
    }
    
    /**
     * Constructor to create a MasterRegisterDto object with the basic data with no linked objects
     * 
     * @param id
     * @param sessionRef
     * @param studentId
     * @param subjectCode
     * @param group
     * @param attendanceId
     */
    public MasterRegisterDto(Integer id, Integer sessionRef, Integer studentId, String subjectCode, String group, Integer attendanceId) {
        this.id = id;
        this.sessionRef = sessionRef;
        this.studentId = studentId;
        this.subjectCode = subjectCode;
        this.group = group;
        this.attendanceId = attendanceId;
    }
    
    
    /**
     * Constructor to create a MasterRegisterDto object with the basic data and the linked MasterRegisterType objects
     * 
     * @param id
     * @param sessionRef
     * @param student
     * @param subjectCode
     * @param group
     * @param attendance
     */
    public MasterRegisterDto(Integer id, Integer sessionRef, Student student, String subjectCode, String group, AttendanceCode attendance){
        this(id, sessionRef, student != null ? student.id : null, subjectCode, group, attendance != null ? attendance.id : null)
    }
    
    @Override
    public String toString() {
        return "MasterRegisterDto [id=" + id + ", sessionRef=" + sessionRef + ", student=" + studentId + ", subjectCode=" + subjectCode + ", group=" + group +", attendance=" + attendanceId + "]";
    }
    
    public static MasterRegisterDto mapFromMasterRegisterEntity(MasterRegister masterRegister) {
        return new MasterRegisterDto(masterRegister);
    }
    
    public static List<MasterRegisterDto> mapFromMasterRegistersEntities(List<MasterRegister> masterRegisters) {
        List<MasterRegisterDto> output = masterRegisters.collect { masterRegister ->  new MasterRegisterDto(masterRegister) };
        return output
    }
    
    public static MasterRegister mapToMasterRegisterEntity(MasterRegisterDto masterRegisterDto, Student student, AttendanceCode attendance) {
        return new MasterRegister(masterRegisterDto.id, masterRegisterDto.sessionRef, student, masterRegisterDto.subjectCode, masterRegisterDto.group, attendance)
    }
}
