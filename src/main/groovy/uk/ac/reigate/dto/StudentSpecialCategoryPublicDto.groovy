package uk.ac.reigate.dto;


import java.util.Date

import javax.persistence.OneToOne;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;

import groovy.transform.EqualsAndHashCode
import uk.ac.reigate.domain.Staff;
import uk.ac.reigate.domain.academic.SpecialCategory;
import uk.ac.reigate.domain.academic.Student;
import uk.ac.reigate.domain.learning_support.StudentSpecialCategory

/**
 *
 * JSON serializable DTO containing StudentSpecialCategoryPublic data
 *
 */
@JsonSerialize
@EqualsAndHashCode(includeFields=true)
public class StudentSpecialCategoryPublicDto implements Serializable {
    
    @JsonProperty
    private Integer id;
    
    @JsonProperty
    private Integer studentId;
    
    @JsonProperty
    private Integer specialCategoryId
    
    @JsonProperty
    String _specialCategoryCode
    
    @JsonProperty
    private Boolean specialConfirmed
    
    @JsonProperty
    private Date classificationDate
    
    @JsonProperty
    private Date closedDate
    
    @JsonProperty
    private String monitoringNotes
    
    
    /**
     * Default No Args constructor
     */
    public StudentSpecialCategoryPublicDto() {
    }
    
    /**
     * Constructor to create a StudentSpecialCategoryDto object from a StudentSpecialCategory object
     *
     * @param studentSpecialCategory the StudentSpecialCategory object to use for construction
     */
    public StudentSpecialCategoryPublicDto(StudentSpecialCategory studentSpecialCategory) {
        this.id = studentSpecialCategory.id
        this.studentId = studentSpecialCategory.student.id
        this.specialCategoryId =  studentSpecialCategory.specialCategory != null ? studentSpecialCategory.specialCategory.id : null
        this._specialCategoryCode =  studentSpecialCategory.specialCategory != null ? studentSpecialCategory.specialCategory.code : null
        this.specialConfirmed = studentSpecialCategory.specialConfirmed;
        this.classificationDate= studentSpecialCategory.classificationDate;
        this.closedDate = studentSpecialCategory.closedDate;
        this.monitoringNotes = studentSpecialCategory.monitoringNotes;
    }
    
    /**
     * Constructor to create a StudentSpecialCategoryPublicDto object
     * 
     * @param id
     * @param studentId
     * @param specialCategoryId
     * @param specialConfirmed
     * @param classificationDate
     * @param closedDate
     * @param monitoringNotes
     */
    public StudentSpecialCategoryPublicDto(Integer id, Integer studentId,  Integer specialCategoryId, Boolean specialConfirmed, Date classificationDate, Date closedDate, String monitoringNotes) {
        this.id = id;
        this.studentId = studentId;
        this.specialCategoryId = specialCategoryId;
        this.specialConfirmed = specialConfirmed;
        this.classificationDate = classificationDate;
        this.closedDate = closedDate;
        this.monitoringNotes = monitoringNotes;
    }
    
    
    public static StudentSpecialCategoryPublicDto mapFromStudentSpecialCategoryPublicEntity(StudentSpecialCategory studentSpecialCategory) {
        StudentSpecialCategoryPublicDto output = new StudentSpecialCategoryPublicDto(studentSpecialCategory)
        return output
    }
    
    public static List<StudentSpecialCategoryPublicDto> mapFromStudentSpecialCategoriesPublicEntities(List<StudentSpecialCategory> studentSpecialCategories) {
        List<StudentSpecialCategoryPublicDto> output = studentSpecialCategories.collect { studentSpecialCategory ->  new StudentSpecialCategoryPublicDto(studentSpecialCategory) };
        return output
    }
}
