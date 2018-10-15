package uk.ac.reigate.dto;


import groovy.transform.EqualsAndHashCode
import javax.websocket.Session
import uk.ac.reigate.domain.academic.Period
import uk.ac.reigate.domain.register.RegisteredSession;

import com.fasterxml.jackson.annotation.JsonFormat
import com.fasterxml.jackson.annotation.JsonProperty
import com.fasterxml.jackson.databind.annotation.JsonSerialize

/**
 *
 * JSON serializable DTO containing Session data
 *
 */
@JsonSerialize
@EqualsAndHashCode(includeFields=true)
public class SessionDto implements Serializable {
    
    @JsonProperty
    private Integer id;
    
    @JsonProperty
    private Date date;
    
    @JsonProperty
    private Integer periodId;
    
    /**
     * Default No Args constructor
     */
    public SessionDto() {
    }
    
    /**
     * Constructor to create a SessionDto object from a Session object
     *
     * @param session the Session object to use for construction
     */
    SessionDto(RegisteredSession session){
        this.id = session.id;
        this.date = session.date;
        this.periodId = session.period != null ? session.period.id : null;
    }
    /**
     * Constructor to create a SessionDto object with the basic data with no linked objects.
     *
     * @param id the Id for the Session
     * @param date the date for the Session
     * @param periodId the periodId for the Session
     */
    public SessionDto(Integer id, Date date, Integer periodId){
        this.id = id;
        this.date = date;
        this.periodId = periodId;
    }
    
    /**
     * Constructor to create a SessionDto object with the basic data and the linked Period object 
     *
     * @param id the Id for the Session
     * @param date the date for the Session
     * @param period the period for the Session
     */
    public SessionDto(Integer id, Date date, Period period){
        this(id, date, period != null ? period.id : null)
    }
    
    @Override
    public String toString() {
        return "SessionDto [id=" + id + ", date=" + date + ", period=" + periodId + "]";
    }
    
    public static SessionDto mapFromSessionEntity(RegisteredSession session) {
        return new SessionDto(session)
    }
    
    public static List<SessionDto> mapFromSessionsEntities(List<RegisteredSession> sessions) {
        return sessions.collect { session ->  new SessionDto(session) };
    }
    
    public static RegisteredSession mapToSessionEntity(SessionDto sessionDto, Period period) {
        return new RegisteredSession(sessionDto.id, sessionDto.date, period)
    }
}
