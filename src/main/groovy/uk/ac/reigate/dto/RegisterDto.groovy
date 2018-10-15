package uk.ac.reigate.dto;


import groovy.transform.EqualsAndHashCode
import uk.ac.reigate.domain.Staff
import uk.ac.reigate.domain.academic.CourseGroup
import uk.ac.reigate.domain.register.Register
import uk.ac.reigate.domain.register.RegisteredSession;

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
public class RegisterDto implements Serializable {
    
    @JsonProperty
    private Integer id;
    
    @JsonProperty
    private Integer sessionId;
    
    @JsonProperty
    private Integer courseGroupId;
    
    @JsonProperty
    private Boolean completed;
    
    @JsonProperty
    private Integer staffCompletedId;
    
    @JsonProperty
    private Date dateCompleted;
    
    /**
     * Default No Args constructor
     */
    public RegisterDto() {
    }
    
    /**
     * Constructor to create a RegisterDto object from a Register object
     *
     * @param register the Register object to use for construction
     */
    RegisterDto(Register register){
        this.id = register.id;
        this.sessionId = register.session != null ? register.session.id : null;
        this.courseGroupId = register.courseGroup != null ? register.courseGroup.id : null;
        this.completed = register.completed;
        this.staffCompletedId = register.staffCompleted != null ? register.staffCompleted.id : null;
        this.dateCompleted = register.dateCompleted;
    }
    /**
     * Constructor to create a RegisterDto object with the basic data with no linked objects.
     *
     * @param id the Id for the Register
     * @param sessionId the sessionId for the Register
     * @param courseGroupId the courseGroupId for the Register
     * @Param completed the completed of the Register
     * @Param staffCompletedId the staffCompletedId of the Register
     * @Param dateCompleted the dateCompleted of the Register
     */
    public RegisterDto(Integer id,  Integer sessionId, Integer courseGroupId, Boolean completed, Integer staffCompletedId, Date dateCompleted){
        this.id = id;
        this.sessionId = sessionId;
        this.courseGroupId = courseGroupId;
        this.completed = completed;
        this.staffCompletedId = staffCompletedId;
        this.dateCompleted = dateCompleted;
    }
    
    /**
     * Constructor to create a RegisterDto object with the basic data and the linked session object
     *
     * @param id the Id for the Register
     * @param session the session for the Register
     * @param courseGroup the courseGroup for the Register
     * @Param completed the completed of the Register
     * @Param staffCompleted the staffCompleted of the Register
     * @Param dateCompleted the dateCompleted of the Register
     */
    public RegisterDto(Integer id,  RegisteredSession session, CourseGroup courseGroup, Boolean completed, Staff staffCompleted, Date dateCompleted){
        this(id, session != null ? session.id : null, courseGroup != null ? courseGroup.id : null, completed, staffCompleted != null ? staffCompleted.id : null, dateCompleted)
    }
    
    @Override
    public String toString() {
        return "RegisterDto [id=" + id + ", session=" + sessionId + ", courseGroup=" + courseGroupId + ", completed=" + completed + ", staffCompleted=" + staffCompletedId + ", dateCompleted=" + dateCompleted + "]";
    }
    
    public static RegisterDto mapFromRegisterEntity(Register register) {
        return new RegisterDto(register)
    }
    
    public static List<RegisterDto> mapFromRegistersEntities(List<Register> registers) {
        return registers.collect { register ->  new RegisterDto(register) };
    }
    
    public static Register mapToRegisterEntity(RegisterDto registerDto, session, courseGroup, staffCompleted) {
        return new Register(registerDto.id, session, courseGroup, registerDto.completed, staffCompleted, registerDto.dateCompleted)
    }
}
