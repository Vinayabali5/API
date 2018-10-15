package uk.ac.reigate.dto.exam;


import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;

import groovy.transform.EqualsAndHashCode;
import uk.ac.reigate.domain.academic.AcademicYear
import uk.ac.reigate.domain.exam.ExamBoard
import uk.ac.reigate.dto.AcademicYearDto

/**
 *
 * JSON serializable DTO containing ExamBoard data
 *
 */
@JsonSerialize
@EqualsAndHashCode(includeFields=true)
public class ExamBoardDto implements Serializable {
    
    @JsonProperty
    private Integer id;
    
    @JsonProperty
    private String name;
    
    @JsonProperty
    private String description;
    
    @JsonProperty
    private String boardCode;
    
    @JsonProperty
    private String boardCentreNumber;
    
    @JsonProperty
    private String boardIdentifier;
    
    @JsonProperty
    private String telephoneNo
    
    /**
     * Default No Args constructor
     */
    ExamBoardDto() {}
    
    /**
     * Constructor that uses the ExamBoard object from the cis-data project
     * 
     * @param examBoard The ExamBoard object to use for the DTO 
     */
    ExamBoardDto(ExamBoard examBoard) {
        this.id = examBoard.id;
        this.name = examBoard.name;
        this.description = examBoard.description;
        this.boardCode = examBoard.boardCode;
        this.boardCentreNumber = examBoard.boardCentreNumber;
        this.boardIdentifier = examBoard.boardIdentifier;
        this.telephoneNo = examBoard.telephoneNo;
    }
    
    /**
     * Constructor to create a ExamBoardDto object
     *
     * @param id the Id for the ExamBoard
     * @param name the name for the ExamBoard
     * @param description the description for the ExamBoard
     * @Param boardCode the boardCode of the ExamBoard
     * @Param boardCentreNumber the boardCentreNumber Date for the ExamBoard
     * @Param boardIdentifier the boardIdentifier Date for the ExamBoard
     */
    public ExamBoardDto(Integer id, String name, String description, String boardCode, String boardCentreNumber, String boardIdentifier, String telephoneNo) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.boardCode = boardCode;
        this.boardCentreNumber = boardCentreNumber;
        this.boardIdentifier = boardIdentifier;
        this.telephoneNo = telephoneNo;
    }
    
    @Override
    public String toString() {
        return "ExamBoardDto [id=" + id + ", name=" + name + ", description=" + description + ", boardCode=" + boardCode + ", boardCentreNumber=" + boardCentreNumber + ", boardIdentifier=" + boardIdentifier + ", telephoneNo=" + telephoneNo +"]";
    }
    
    public ExamBoard toExamBoard() {
        return new ExamBoard(this.id, this.name, this.description, this.boardCode, this.boardCentreNumber, this.boardIdentifier, this.telephoneNo)
    }
    
    public static ExamBoardDto mapFromEntity(ExamBoard examBoard) {
        return new ExamBoardDto(examBoard);
    }
    
    public static ExamBoardDto mapFromExamBoardEntity(ExamBoard examBoard) {
        return new ExamBoardDto(examBoard);
    }
    
    public static List<ExamBoardDto> mapFromExamBoardsEntities(List<ExamBoard> examBoards) {
        return examBoards.collect { examBoard ->  new ExamBoardDto(examBoard) };
    }
    
    public static ExamBoard mapToExamBoardEntity(ExamBoardDto examBoardDto) {
        return new ExamBoard(examBoardDto.id, examBoardDto.name, examBoardDto.description, examBoardDto.boardCode, examBoardDto.boardCentreNumber, examBoardDto.boardIdentifier, examBoardDto.telephoneNo)
    }
}
