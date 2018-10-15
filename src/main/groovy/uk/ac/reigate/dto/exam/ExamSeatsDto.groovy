package uk.ac.reigate.dto.exam;


import groovy.transform.EqualsAndHashCode
import uk.ac.reigate.domain.academic.Student
import uk.ac.reigate.domain.academic.StudentYear
import uk.ac.reigate.domain.exam.ExamComponent
import uk.ac.reigate.domain.exam.ExamSeat
import uk.ac.reigate.domain.exam.ExamSeatingPlan
import uk.ac.reigate.domain.learning_support.StudentConcessionType
import uk.ac.reigate.dto.StudentConcessionTypeDto;

import com.fasterxml.jackson.annotation.JsonProperty
import com.fasterxml.jackson.databind.annotation.JsonSerialize

/**
 *
 * JSON serializable DTO containing ReferralReason data
 *
 */
@JsonSerialize
@EqualsAndHashCode(includeFields=true)
public class ExamSeatsDto implements Serializable {
    
    @JsonProperty
    private Integer studentId;
    
    @JsonProperty
    private String _firstName;
    
    @JsonProperty
    private String _surname;
    
    @JsonProperty
    private Integer examComponentId
    
    @JsonProperty
    private String _examComponentCode
    
    @JsonProperty
    private String _examComponentTitle
    
    @JsonProperty
    private Integer examRoomId
    
    @JsonProperty
    private String _examRoomRoomCode
    
    @JsonProperty
    private String _examRoomRoomDescription
    
    @JsonProperty
    private Integer row;
    
    @JsonProperty
    private Integer col;
    
    @JsonProperty
    private Integer _candidateNo;
    
    @JsonProperty
    private List<StudentConcessionTypeDto> _concessions;
    
    /**
     * Default No Args constructor
     */
    public ExamSeatsDto() {}
    
    /**
     * Constructor to create a SeatingPlanDto object
     *
     * @param studentId the Id for the ExamSeat
     * @param examComponentId the Id for the ExamSeat
     * @param examRoomId the Id for the ExamSeat room
     * @param row the row for the student in the seating plan
     * @param column the column for the student in the seating plan
     */
    public ExamSeatsDto(Student student, ExamComponent examComponent, ExamSeatingPlan examSeatingPlan, Integer row, Integer col) {
        this.studentId = (student != null ? student.id : null);
        this._firstName = (student != null ? student.person.firstName : null);
        this._surname = (student != null ? student.person.surname : null);
        
        this.examComponentId = (examComponent != null ? examComponent.examComponentId : null);
        this._examComponentCode = (examComponent != null ? examComponent.code : null);
        this._examComponentTitle = (examComponent != null ? examComponent.title : null);
        
        this.examRoomId = (examSeatingPlan != null ? examSeatingPlan.id : null);
        this._examRoomRoomCode = ((examSeatingPlan != null && examSeatingPlan.room != null) ? examSeatingPlan.room.code : null);
        this._examRoomRoomDescription = ((examSeatingPlan != null && examSeatingPlan.room != null) ? examSeatingPlan.room.description : null);
        
        this.row = row != null ? row  : null;
        this.col = col != null ? col : null;
    }
    
    /**
     * Constructor to create a SeatingPlanDto object
     *
     * @param studentId the Id for the ExamSeat
     * @param examComponentId the Id for the ExamSeat
     * @param examRoomId the Id for the ExamSeat room
     * @param row the row for the student in the seating plan
     * @param column the column for the student in the seating plan
     * @param studentYear the student year information to use to populate the seating plan 
     */
    public ExamSeatsDto(Student student, ExamComponent examComponent, ExamSeatingPlan examSeatingPlan, Integer row, Integer col, StudentYear studentYear, List<StudentConcessionType> concessions) {
        this(student, examComponent, examSeatingPlan, row, col)
        this._candidateNo = studentYear.candidateNo
        this._concessions = StudentConcessionTypeDto.mapFromStudentConcessionTypesEntities(concessions);
    }
    
    @Override
    public String toString() {
        return "StudentOptionEntryDto [student=" + studentId + ", examComponent=" + examComponentId + ", examRoom=" + examRoomId + ", row=" + row + ", column=" + col +"]";
    }
    
    public ExamSeatsDto(ExamSeat examSeat) {
        this(examSeat.student, examSeat.examComponent, examSeat.examSeatingPlan, examSeat.row, examSeat.col);
    }
    
    public ExamSeatsDto(ExamSeat examSeat, StudentYear studentYear) {
        this(examSeat);
        this._candidateNo = studentYear.candidateNo
    }
    
    public static ExamSeatsDto mapFromSeatingPlanEntity(ExamSeat examSeat) {
        return new ExamSeatsDto(examSeat);
    }
    
    public static List<ExamSeatsDto> mapFromSeatingPlanEntities(List<ExamSeat> examSeats) {
        return examSeats.collect { it ->
            new ExamSeatsDto((ExamSeat) it)
        };
    }
    
    public static ExamSeat mapToSeatingPlanEntity(ExamSeatsDto seatingPlanDto, Student student, ExamComponent examComponent, ExamSeatingPlan examSeatingPlan) {
        return new ExamSeat(student, examComponent, examSeatingPlan, seatingPlanDto.row, seatingPlanDto.col);
    }
}
