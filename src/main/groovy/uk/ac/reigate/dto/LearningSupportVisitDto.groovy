package uk.ac.reigate.dto;


import java.util.Date;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;

import groovy.transform.EqualsAndHashCode
import uk.ac.reigate.domain.academic.Student;
import uk.ac.reigate.domain.learning_support.LearningSupportVisit

/**
 *
 * JSON serializable DTO containing LearningSupportVisit data
 *
 */
@JsonSerialize
@EqualsAndHashCode(includeFields=true)
public class LearningSupportVisitDto implements Serializable {
    
    @JsonProperty
    private Integer id;
    
    @JsonProperty
    private Integer studentId;
    
    @JsonProperty
    private Date date;
    
    @JsonProperty
    private Integer duration
    
    @JsonProperty
    private String details
    
    @JsonProperty
    private Date time;
    
    
    /**
     * Default No Args constructor
     */
    public LearningSupportVisitDto() {
    }
    
    /**
     * Constructor to create a LearningSupportVisitDto object from a LearningSupportVisit object
     *
     * @param learningSupportVisit the LearningSupportVisit object to use for construction
     */
    LearningSupportVisitDto(LearningSupportVisit learningSupportVisit) {
        this.id = learningSupportVisit.id;
        this.studentId = learningSupportVisit.student != null ? learningSupportVisit.student.id : null;
        this.date = learningSupportVisit.date;
        this.duration = learningSupportVisit.duration;
        this.details = learningSupportVisit.details;
        this.time = learningSupportVisit.time;
    }
    /**
     * Constructor to create a LearningSupportVisitDto object with the basic data with no linked objects.
     *
     * @param id
     * @param student
     * @param date
     * @param duration
     * @param details
     * @param time
     */
    public LearningSupportVisitDto(Integer id, Integer studentId, Date date, Integer duration, String details, Date time) {
        this.id = id;
        this.studentId = studentId;
        this.date = date;
        this.duration = duration;
        this.details = details;
        this.time = time;
    }
    
    /**
     * Constructor to create a LearningSupportVisitDto object with the basic data with linked LearningSupportVisit objects.
     * 
     * @param id
     * @param student
     * @param date
     * @param duration
     * @param details
     * @param time
     */
    public LearningSupportVisitDto(Integer id, Student student, Date date, Integer duration, String details, Date time) {
        this(id, student != null ? student.id : null, date, duration, details, time)
    }
    
    @Override
    public String toString() {
        return "LearningSupportVisitDto [id=" + id + ", student=" + studentId + ", date=" + date + ", duration=" + duration +", details=" + details + ", time=" + time + "]";
    }
    
    public static LearningSupportVisitDto mapFromLearningSupportVisitEntity(LearningSupportVisit learningSupportVisit) {
        return new LearningSupportVisitDto(learningSupportVisit)
    }
    
    public static List<LearningSupportVisitDto> mapFromLearningSupportVisitsEntities(List<LearningSupportVisit> learningSupportVisits) {
        List<LearningSupportVisitDto> output = learningSupportVisits.collect { learningSupportVisit ->  new LearningSupportVisitDto(learningSupportVisit) };
        return output
    }
    
    public static LearningSupportVisit mapToLearningSupportVisitEntity(LearningSupportVisitDto learningSupportVisitDto, Student student) {
        return new LearningSupportVisit(learningSupportVisitDto.id, student, learningSupportVisitDto.date, learningSupportVisitDto.duration, learningSupportVisitDto.details, learningSupportVisitDto.time)
    }
}
