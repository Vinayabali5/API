package uk.ac.reigate.dto.exam


import groovy.transform.EqualsAndHashCode

import com.fasterxml.jackson.annotation.JsonProperty
import com.fasterxml.jackson.databind.annotation.JsonSerialize;

import uk.ac.reigate.domain.academic.Student
import uk.ac.reigate.domain.exam.ExamBoard
import uk.ac.reigate.domain.exam.StudentAlternativeUci


@JsonSerialize
@EqualsAndHashCode(includeFields=true)

class StudentAlternativeUciDto {
    
    @JsonProperty
    Integer studentId
    
    @JsonProperty
    Integer examBoardId
    
    @JsonProperty
    String _examBoardIdentifier
    
    @JsonProperty
    String _examBoardName
    
    @JsonProperty
    String _examBoardDescription
    
    @JsonProperty
    String alternativeUci
    
    /**
     * Default No Args constructor
     */
    StudentAlternativeUciDto() {}
    
    StudentAlternativeUciDto(StudentAlternativeUci studentAlternativeUci){
        this.studentId = studentAlternativeUci.student.id
        this.examBoardId = studentAlternativeUci.examBoard.id
        this._examBoardIdentifier = studentAlternativeUci.examBoard.boardIdentifier
        this._examBoardName = studentAlternativeUci.examBoard.name
        this._examBoardDescription = studentAlternativeUci.examBoard.description
        this.alternativeUci = studentAlternativeUci.alternativeUci
    }
    
    public static List<StudentAlternativeUciDto> mapFromStudentAlternativeUciEntities(List<StudentAlternativeUciDto> studentAlternativeUcis) {
        return studentAlternativeUcis.collect { studentAlternativeUci ->  new StudentAlternativeUciDto(studentAlternativeUci) };
    }
    
    public static StudentAlternativeUciDto mapFromStudentAlternativeUciEntity(StudentAlternativeUci studentAlternativeUci) {
        return new StudentAlternativeUciDto(studentAlternativeUci)
    }
    
    public static StudentAlternativeUci mapFromStudentAlternativeUciDto(Student student,ExamBoard examBoard,StudentAlternativeUciDto studentAlternativeUciDto){
        return new StudentAlternativeUci(student,examBoard,studentAlternativeUciDto.alternativeUci)
    }
}
