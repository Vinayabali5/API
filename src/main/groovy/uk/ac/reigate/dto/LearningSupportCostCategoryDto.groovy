package uk.ac.reigate.dto;


import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;

import groovy.transform.EqualsAndHashCode;
import uk.ac.reigate.domain.learning_support.LearningSupportCostCategory

/**
 *
 * JSON serializable DTO containing LearningSupportCostCategory data
 *
 */
@JsonSerialize
@EqualsAndHashCode(includeFields=true)
public class LearningSupportCostCategoryDto implements Serializable {
    
    @JsonProperty
    private Integer id;
    
    @JsonProperty
    private String category;
    
    /**
     * Default No Args constructor
     */
    public LearningSupportCostCategoryDto() {
    }
    
    /**
     * Constructor to create a LearningSupportCostCategoryDto object
     *
     * @param id the Id for the LearningSupportCostCategory
     * @param category the category for the LearningSupportCostCategory
     * @param description the description for the LearningSupportCostCategory
     */
    public LearningSupportCostCategoryDto(Integer id, String category) {
        this.id = id;
        this.category = category;
    }
    
    /**
     * Constructor to create a LearningSupportCostCategoryDto object from a LearningSupportCostCategory object
     *
     * @param learningSupportCostCategory the LearningSupportCostCategory object to use for construction
     */
    LearningSupportCostCategoryDto(LearningSupportCostCategory learningSupportCostCategory) {
        this.id = learningSupportCostCategory.id;
        this.category = learningSupportCostCategory.category;
    }
    
    @Override
    public String toString() {
        return "LearningSupportCostCategoryDto [id=" + id + ", category=" + category + "]";
    }
    
    public static LearningSupportCostCategoryDto mapFromLearningSupportCostCategoryEntity(LearningSupportCostCategory learningSupportCostCategory) {
        return new LearningSupportCostCategoryDto(learningSupportCostCategory);
    }
    
    public static List<LearningSupportCostCategoryDto> mapFromLearningSupportCostCategorysEntities(List<LearningSupportCostCategory> learningSupportCostCategories) {
        return learningSupportCostCategories.collect { learningSupportCostCategory ->  new LearningSupportCostCategoryDto(learningSupportCostCategory) };
    }
    
    public static LearningSupportCostCategory mapToLearningSupportCostCategoryEntity(LearningSupportCostCategoryDto learningSupportCostCategoryDto) {
        return new LearningSupportCostCategory(learningSupportCostCategoryDto.id, learningSupportCostCategoryDto.category)
    }
}
