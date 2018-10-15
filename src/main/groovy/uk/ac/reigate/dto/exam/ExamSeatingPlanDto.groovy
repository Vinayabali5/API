package uk.ac.reigate.dto.exam;


import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;

import groovy.transform.EqualsAndHashCode;
import uk.ac.reigate.domain.Room
import uk.ac.reigate.domain.RoomType
import uk.ac.reigate.domain.exam.ExamSeatingPlan
import uk.ac.reigate.domain.exam.ExamSession
import uk.ac.reigate.dto.RoomDto;;;;

/**
 *
 * JSON serializable DTO containing Room data
 *
 */
@JsonSerialize
@EqualsAndHashCode(includeFields=true)
public class ExamSeatingPlanDto implements Serializable {
    
    @JsonProperty
    private Integer id;
    
    @JsonProperty
    private ExamSessionDto examSession;
    
    @JsonProperty
    private RoomDto room;
    
    @JsonProperty
    private Integer rows;
    
    @JsonProperty
    private Integer cols;
    
    /**
     * Default No Args constructor
     */
    public ExamSeatingPlanDto() {
    }
    
    /**
     * Constructor to create a RoomDto object from a Room object
     *
     * @param room the Room object to use for construction
     */
    ExamSeatingPlanDto(ExamSeatingPlan examSeatingPlan) {
        this.id = examSeatingPlan.id;
        this.examSession = ExamSessionDto.mapFromExamSessionEntity(examSeatingPlan.examSession);
        this.room =  RoomDto.mapFromRoomEntity(examSeatingPlan.room);
        this.rows = examSeatingPlan.rows;
        this.cols = examSeatingPlan.cols;
    }
    
    public ExamSeatingPlanDto(Integer id, ExamSession examSession, Room room, Integer rows, Integer cols) {
        this.id = id;
        this.examSession = ExamSessionDto.mapFromExamSessionEntity(examSession);
        this.room = RoomDto.mapFromRoomEntity(room);
        this.rows = rows;
        this.cols = cols;
    }
    
    @Override
    public String toString() {
        return "ExamRoomDto [id=" + id + ", examSession=" + examSession + ", room=" + room + ", rows= " + rows + ", cols=" + cols + "]";
    }
    
    public static ExamSeatingPlanDto mapFromExamRoomEntity(ExamSeatingPlan examRoom) {
        return new ExamSeatingPlanDto(examRoom)
    }
    
    public static List<ExamSeatingPlanDto> mapFromExamRoomsEntities(List<ExamSeatingPlan> examRooms) {
        return examRooms.collect { examRoom ->  new ExamSeatingPlanDto((ExamSeatingPlan) examRoom) };
    }
    
    public static ExamSeatingPlan mapToExamRoomEntity(ExamSeatingPlanDto examRoomDto, ExamSession examSession, Room room ) {
        return new ExamSeatingPlan(examRoomDto.id, examSession, room, examRoomDto.rows, examRoomDto.cols)
    }
}
