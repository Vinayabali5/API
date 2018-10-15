package uk.ac.reigate.dto;


import groovy.transform.EqualsAndHashCode
import uk.ac.reigate.domain.Staff
import uk.ac.reigate.domain.academic.Course
import uk.ac.reigate.domain.academic.CourseGroup
import uk.ac.reigate.domain.academic.Student;
import uk.ac.reigate.domain.register.Register
import uk.ac.reigate.domain.register.AttendanceCode;
import uk.ac.reigate.domain.register.RegisterMark

import com.fasterxml.jackson.annotation.JsonFormat
import com.fasterxml.jackson.annotation.JsonProperty
import com.fasterxml.jackson.databind.annotation.JsonSerialize

/**
 *
 * JSON serializable DTO containing Register data
 *
 */
@JsonSerialize
@EqualsAndHashCode(includeFields=true)
public class RegisterMarkDto implements Serializable {
    
    @JsonProperty
    private Integer id;
    
    @JsonProperty
    private Integer studentId;
    
    @JsonProperty
    private Integer courseId;
    
    @JsonProperty
    private Integer courseGroupId;
    
    @JsonProperty
    private Integer registerId;
    
    @JsonProperty
    private Integer attendanceCodeId;
    
    
    /**
     * Default No Args constructor
     */
    public RegisterMarkDto() {
    }
    
    /**
     * Constructor to create a RegisterMarkDto object from a RegisterMark object
     *
     * @param registerMark the RegisterMark object to use for construction
     */
    RegisterMarkDto(RegisterMark registerMark){
        this.id = registerMark.id;
        this.courseId = registerMark.course != null ? registerMark.course.id : null;
        this.courseGroupId = registerMark.courseGroup != null ? registerMark.courseGroup.id : null;
        this.registerId = registerMark.register != null ? registerMark.register.id : null;
        this.studentId = registerMark.student != null ? registerMark.student.id : null;
        this.attendanceCodeId = registerMark.attendanceCode != null ? registerMark.attendanceCode.id : null;
    }
    
    /**
     * Constructor to create a RegisterDto object with the basic data with no linked objects.
     * 
     * @param id
     * @param courseId
     * @param courseGroupId
     * @param registerId
     * @param studentId
     * @param attendanceCodeId
     */
    public RegisterMarkDto(Integer id,  Integer courseId, Integer courseGroupId, Integer registerId, Integer studentId, Integer attendanceCodeId){
        this.id = id;
        this.courseId = courseId;
        this.courseGroupId = courseGroupId;
        this.registerId = registerId;
        this.studentId = studentId;
        this.attendanceCodeId = attendanceCodeId;
    }
    
    /**
     * Constructor to create a RegisterMarkDto object with the basic data and the linked course object
     * 
     * @param id
     * @param course
     * @param courseGroup
     * @param register
     * @param student
     * @param attendanceCode
     */
    public RegisterMarkDto(Integer id, Course course, CourseGroup courseGroup, Register register, Student student, AttendanceCode attendanceCode){
        this(id, course != null ? course.id : null, courseGroup != null ? courseGroup.id : null, register != null ? register.id : null, student != null ? student.id : null, attendanceCode != null ? attendanceCode.id : null)
    }
    
    @Override
    public String toString() {
        return "RegisterDto [id=" + id + ", course=" + courseId + ", courseGroup=" + courseGroupId + ", register=" + registerId + ", student=" + studentId + ", attendanceCode=" + attendanceCodeId + "]";
    }
    
    public static RegisterMarkDto mapFromRegisterMarkEntity(RegisterMark registerMark) {
        return new RegisterMarkDto(registerMark)
    }
    
    public static List<RegisterMarkDto> mapFromRegisterMarksEntities(List<Register> registerMarks) {
        return registerMarks.collect { registerMark ->  new RegisterMarkDto(registerMark) };
    }
    
    public static RegisterMark mapToRegisterMarkEntity(RegisterMarkDto registerMarkDto, Course course, CourseGroup courseGroup, Register register, Student student, AttendanceCode attendanceCode) {
        return new RegisterMark(registerMarkDto.id, course, courseGroup, register, student, attendanceCode)
    }
}
