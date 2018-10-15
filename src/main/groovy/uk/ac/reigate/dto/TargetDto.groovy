package uk.ac.reigate.dto;


import javax.persistence.OneToOne;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;

import groovy.transform.EqualsAndHashCode
import uk.ac.reigate.domain.ilp.ILPInterview;
import uk.ac.reigate.domain.ilp.Target

/**
 *
 * JSON  DTO containing Target data
 *
 */
@JsonSerialize
@EqualsAndHashCode(includeFields=true)
public class TargetDto  {
    
    @JsonProperty
    private Integer id;
    
    @JsonProperty
    private String target;
    
    @JsonProperty
    private String byWhen
    
    @JsonProperty
    private Integer interviewId
    
    @JsonProperty
    private Boolean sendLetter
    /**
     * Default No Args constructor
     */
    public TargetDto() {
    }
    
    /**
     * Constructor to create a TargetDto object from a Target object
     *
     * @param target the Target object to use for construction
     */
    TargetDto(Target target) {
        this.id = target.id;
        this.target = target.target;
        this.byWhen = target.byWhen;
        this.interviewId = target.interview != null ? target.interview.id : null;
        this.sendLetter = target.sendLetter;
    }
    
    /**
     * Constructor to create a TargetDto object with the basic data with no linked objects.
     * 
     * @param id
     * @param target
     * @param byWhen
     * @param interviewId
     * @param sendLetter
     */
    public TargetDto(Integer id, String target, String byWhen, Integer interviewId, Boolean sendLetter) {
        this.id = id;
        this.target = target;
        this.byWhen = byWhen;
        this.interviewId = interviewId;
        this.sendLetter = sendLetter;
    }
    
    /**
     * Constructor to create a TargetDto object with the basic data with linked ILPInterview objects.
     * 
     * @param id
     * @param target
     * @param byWhen
     * @param interview
     * @param sendLetter
     */
    public TargetDto(Integer id, String target, String byWhen, ILPInterview interview, Boolean sendLetter) {
        this(id, target, byWhen, interview != null ? interview.id : null, sendLetter)
    }
    
    @Override
    public String toString() {
        return "TargetDto [id=" + id + ", target=" + target + ", byWhen=" + byWhen + ", interview=" + interviewId + ", sendLetter=" + sendLetter + "]";
    }
    
    public static TargetDto mapFromTargetEntity(Target target) {
        return new TargetDto(target)
    }
    
    public static List<TargetDto> mapFromTargetsEntities(List<Target> targets) {
        List<TargetDto> output = targets.collect { target ->  new TargetDto(target) };
        return output
    }
    
    public static Target mapToTargetEntity(TargetDto targetDto, ILPInterview interview) {
        return new Target(targetDto.id, targetDto.target, targetDto.byWhen, interview, targetDto.sendLetter)
    }
}
