package uk.ac.reigate.dto.exam

import uk.ac.reigate.domain.exam.ExamSession;

import com.fasterxml.jackson.annotation.JsonFormat
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;

import groovy.transform.EqualsAndHashCode;

@JsonSerialize
@EqualsAndHashCode(includeFields=true)
class ExamSessionDto {
    
    @JsonProperty
    private Integer id;
    
    @JsonProperty
    private Date date;
    
    @JsonProperty
    private String session;
    
    /**
     * default No Args constructor
     */
    public ExamSessionDto(){}
    
    /**
     * Constructor to create an ExamSessionDto object
     *     
     * @param id
     * @param date
     * @param session
     */
    public ExamSessionDto(Integer id, Date date, String session) {
        this.id = id;
        this.date = date;
        this.session = session;
    }
    
    @Override
    public String toString() {
        return "ExamSession: [" + "Id=" + String.valueOf(id) + ", date=" + date + ", session=" + session + "]";
    }
    
    public static ExamSessionDto mapFromExamSessionEntity(ExamSession examSession) {
        return new ExamSessionDto(examSession.id, examSession.date, examSession.session);
    }
    
    public static List<ExamSessionDto> mapFromExamSessionEntities(List<ExamSession> examSessions) {
        List<ExamSessionDto> output = examSessions.collect { examSession -> mapFromExamSessionEntity(examSession) };
        return output
    }
    
    public static ExamSession mapToExamSessionEntity(ExamSessionDto examSessionDto) {
        return new ExamSession(examSessionDto.id, examSessionDto.date, examSessionDto.session);
    }
    
    public static List<ExamSession> mapToExamSessionEntities(List<ExamSessionDto> examSessionDtos) {
        return examSessionDtos.collect { examSessionDto -> mapToExamSessionEntity(examSessionDto) } ;
    }
}
