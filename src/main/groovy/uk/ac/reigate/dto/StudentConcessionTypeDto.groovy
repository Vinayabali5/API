package uk.ac.reigate.dto;


import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;

import groovy.transform.EqualsAndHashCode
import uk.ac.reigate.domain.academic.Student
import uk.ac.reigate.domain.learning_support.ConcessionType;
import uk.ac.reigate.domain.learning_support.StudentConcessionType

/**
 *
 * JSON serializable DTO containing ConcessionType data
 *
 */
@JsonSerialize
@EqualsAndHashCode(includeFields=true)
public class StudentConcessionTypeDto implements Serializable {
    
    @JsonProperty
    private Integer studentId;
    
    @JsonProperty
    private Integer concessionTypeId;
    
    @JsonProperty
    private String _concessionCode;
    
    @JsonProperty
    private String _concessionDescription;
    
    @JsonProperty
    private Integer extraTimePercentage;
    
    /**
     * Default No Args constructor
     */
    public StudentConcessionTypeDto() {
    }
    
    /**
     * Constructor to create a ConcessionTypeDto object
     *
     * @param student
     * @param concessionType
     * @param extraTimePercentage
     */
    public StudentConcessionTypeDto(Integer studentId, Integer concessionTypeId, Integer extraTimePercentage) {
        this.studentId = studentId;
        this.concessionTypeId = concessionTypeId;
        this.extraTimePercentage = extraTimePercentage;
    }
    
    /**
     * Constructor to create a ConcessionTypeDto object with the basic data with linked Student, ConcessionType objects.
     * 
     * @param student
     * @param concessionType
     * @param extraTimePercentage
     */
    public StudentConcessionTypeDto(Student student, ConcessionType concessionType, Integer extraTimePercentage) {
        this(student != null ? student.id : null, concessionType != null ? concessionType.id : null, extraTimePercentage)
    }
    
    /**
     * Constructor to create a StudentConcessionTypeDto object from a StudentConcessionType object
     *
     * @param studentConcessionType the StudentConcessionType object to use for construction
     */
    public StudentConcessionTypeDto(StudentConcessionType studentConcessionType) {
        this.studentId = studentConcessionType.student != null ? studentConcessionType.student.id : null;
        this.concessionTypeId = studentConcessionType.concessionType != null ?studentConcessionType.concessionType.id : null;
        this._concessionCode = studentConcessionType.concessionType != null ? studentConcessionType.concessionType.code : '';
        this._concessionDescription = studentConcessionType.concessionType != null ? studentConcessionType.concessionType.description : '';
        this.extraTimePercentage = studentConcessionType.extraTimePercentage;
    }
    
    @Override
    public String toString() {
        return "StudentConcessionTypeDto [student=" + studentId + ", concessionType=" + concessionTypeId + ", extraTimePercentage=" + extraTimePercentage + "]";
    }
    
    public static StudentConcessionTypeDto mapFromStudentConcessionTypeEntity(StudentConcessionType studentConcessionType) {
        return new StudentConcessionTypeDto(studentConcessionType);
    }
    
    public static List<StudentConcessionTypeDto> mapFromStudentConcessionTypesEntities(List<StudentConcessionType> studentConcessionTypes) {
        return studentConcessionTypes.collect { studentConcessionType ->  new StudentConcessionTypeDto(studentConcessionType) };
    }
    
    public static StudentConcessionType mapToStudentConcessionTypeEntity(StudentConcessionTypeDto studentConcessionTypeDto, Student student, ConcessionType concessionType) {
        return new StudentConcessionType(student, concessionType, studentConcessionTypeDto.extraTimePercentage)
    }
}
