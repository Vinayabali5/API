package uk.ac.reigate.dto;


import java.util.Date;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;

import groovy.transform.EqualsAndHashCode
import uk.ac.reigate.domain.Staff;
import uk.ac.reigate.domain.academic.Student;
import uk.ac.reigate.domain.learning_support.LearningSupportCost
import uk.ac.reigate.domain.learning_support.LearningSupportCostCategory;

/**
 *
 * JSON serializable DTO containing LearningSupportCost data
 *
 */
@JsonSerialize
@EqualsAndHashCode(includeFields=true)
public class LearningSupportCostDto implements Serializable {
    
    @JsonProperty
    private Integer id;
    
    @JsonProperty
    private Integer studentId;
    
    @JsonProperty
    private Date startDate;
    
    @JsonProperty
    private Date endDate;
    
    @JsonProperty
    private Double hoursPerWeek;
    
    @JsonProperty
    private Double weeks;
    
    @JsonProperty
    private Double rate;
    
    @JsonProperty
    private Integer costCategoryId;
    
    @JsonProperty
    private String description;
    
    @JsonProperty
    private String periodDescription
    
    @JsonProperty
    private Integer staffId
    
    @JsonProperty
    private String _staffInitials
    
    /**
     * Default No Args constructor
     */
    public LearningSupportCostDto() {
    }
    
    /**
     * Constructor to create a LearningSupportCostDto object from a LearningSupportCost object
     *
     * @param learningSupportCost the LearningSupportCost object to use for construction
     */
    LearningSupportCostDto(LearningSupportCost learningSupportCost) {
        this.id = learningSupportCost.id;
        this.studentId = learningSupportCost.student != null ? learningSupportCost.student.id : null;
        this.startDate = learningSupportCost.startDate;
        this.endDate = learningSupportCost.endDate;
        this.hoursPerWeek = learningSupportCost.hoursPerWeek;
        this.weeks = learningSupportCost.weeks;
        this.rate = learningSupportCost.rate;
        this.costCategoryId = learningSupportCost.costCategory != null ? learningSupportCost.costCategory.id : null;
        this.description = learningSupportCost.description;
        this.periodDescription = learningSupportCost.periodDescription;
        this.staffId = learningSupportCost.staff != null ? learningSupportCost.staff.id : null;
        this._staffInitials = learningSupportCost.staff != null ? learningSupportCost.staff.initials : ''
    }
    /**
     * Constructor to create a LearningSupportCostDto object with the basic data with no linked objects.
     *
     * @param id
     * @param student
     * @param startDate
     * @param endDate
     * @param hoursPerWeek
     * @param weeks
     * @param rate
     * @param costCategory
     * @param description
     * @param periodDescription
     * @param staff
     */
    public LearningSupportCostDto(Integer id, Integer studentId, Date startDate, Date endDate, Double hoursPerWeek, Double weeks, Double rate, Integer costCategoryId, String description, String periodDescription, Integer staffId) {
        this.id = id;
        this.studentId = studentId;
        this.startDate = startDate;
        this.endDate = endDate;
        this.hoursPerWeek = hoursPerWeek;
        this.weeks = weeks;
        this.rate = rate;
        this.costCategoryId = costCategoryId;
        this.description = description;
        this.periodDescription = periodDescription;
        this.staffId = staffId;
    }
    
    /**
     * Constructor to create a LearningSupportCostDto object with the basic data with linked LearningSupportCost objects.
     * 
     * @param id
     * @param student
     * @param startDate
     * @param endDate
     * @param hoursPerWeek
     * @param weeks
     * @param rate
     * @param costCategory
     * @param description
     * @param periodDescription
     * @param staff
     */
    public LearningSupportCostDto(Integer id, Student student, Date startDate, Date endDate, Double hoursPerWeek, Double weeks, Double rate, LearningSupportCostCategory costCategory, String description, String periodDescription, Staff staff) {
        this(id, student != null ? student.id : null, startDate,  endDate, hoursPerWeek, weeks, rate, costCategory != null ? costCategory.id : null, description, periodDescription, staff != null ? staff.id : null)
    }
    
    @Override
    public String toString() {
        return "LearningSupportCostDto [id=" + id + ", student=" + studentId + ", startDate=" + startDate + ", endDate=" + endDate +", hoursPerWeek=" + hoursPerWeek + ", weeks=" + weeks + ", rate=" + rate + ", costCategory=" + costCategoryId + ", description=" + description + ", periodDescription=" + periodDescription +", staff=" + staffId +"]";
    }
    
    public static LearningSupportCostDto mapFromLearningSupportCostEntity(LearningSupportCost learningSupportCost) {
        return new LearningSupportCostDto(learningSupportCost)
    }
    
    public static List<LearningSupportCostDto> mapFromLearningSupportCostsEntities(List<LearningSupportCost> learningSupportCosts) {
        List<LearningSupportCostDto> output = learningSupportCosts.collect { learningSupportCost ->  new LearningSupportCostDto(learningSupportCost) };
        return output
    }
    
    public static LearningSupportCost mapToLearningSupportCostEntity(LearningSupportCostDto learningSupportCostDto, Student student, LearningSupportCostCategory costCategory, Staff staff) {
        return new LearningSupportCost(learningSupportCostDto.id, student, learningSupportCostDto.startDate, learningSupportCostDto.endDate, learningSupportCostDto.hoursPerWeek, learningSupportCostDto.weeks, learningSupportCostDto.rate, costCategory, learningSupportCostDto.description, learningSupportCostDto.periodDescription, staff)
    }
}
