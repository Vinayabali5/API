package uk.ac.reigate.dto

import groovy.transform.EqualsAndHashCode

import java.security.InvalidParameterException

import uk.ac.reigate.domain.academic.StudentYear

import com.fasterxml.jackson.annotation.JsonProperty
import com.fasterxml.jackson.databind.annotation.JsonSerialize



@JsonSerialize
@EqualsAndHashCode(includeFields=true)
public class StudentBursaryDto implements Serializable{
    
    
    @JsonProperty
    private Integer studentId;
    
    @JsonProperty
    private Integer yearId;
    
    @JsonProperty
    private Boolean gb;
    
    @JsonProperty
    private Boolean db
    
    @JsonProperty
    private Boolean freeMealsEligibility
    
    @JsonProperty
    private Boolean receivingFreeMeals
    
    /**
     * Default No Args constructor
     */
    public StudentBursaryDto() {}
    
    /**
     * Constructor to create a StudentBursaryDto object from a StudentYear object
     *
     * @param studentYear the StudentYear object to use for construction
     */
    public StudentBursaryDto(StudentYear studentYear) {
        if (studentYear) {
            this.studentId = studentYear.student != null ? studentYear.student.id : null;
            this.yearId = studentYear.year != null ? studentYear.year.id : null;
            this.gb = studentYear.gb != null ? studentYear.gb : null;
            this.db = studentYear.db != null ? studentYear.db : null;
            this.freeMealsEligibility = studentYear.freeMealsEligibility!= null ? studentYear.freeMealsEligibility : null;
            this.receivingFreeMeals = studentYear.receivingFreeMeals != null ? studentYear.receivingFreeMeals : null;
        } else {
            throw new InvalidParameterException("You are unable to construct a StudentBursaryDto from a null StudentYear.");
        }
    }
    
    /**
     * Constructor to create a StudentBursaryDto object with the basic data with no linked objects.
     * 
     * @param studentId
     * @param yearId
     * @param gb
     * @param db
     * @param freeMealsEligibility
     * @param receivingFreeMeals
     */
    public StudentBursaryDto(Integer studentId, Integer yearId, Boolean gb, Boolean db, Boolean freeMealsEligibility, Boolean receivingFreeMeals) {
        this.studentId = studentYear.studentId;
        this.yearId = studentYear.yearId;
        this.gb = studentYear.gb;
        this.db = studentYear.db;
        this.freeMealsEligibility = studentYear.freeMealsEligibility;
        this.receivingFreeMeals =  studentYear.receivingFreeMeals;
    }
    
    public static StudentBursaryDto mapFromStudentBursaryEntity(StudentYear studentYear) {
        return new StudentBursaryDto(studentYear);
    }
}
