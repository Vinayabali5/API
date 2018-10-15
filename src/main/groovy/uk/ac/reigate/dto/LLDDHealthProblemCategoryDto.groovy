package uk.ac.reigate.dto;


import groovy.transform.EqualsAndHashCode
import uk.ac.reigate.domain.ilr.LLDDHealthProblemCategory

import com.fasterxml.jackson.annotation.JsonFormat
import com.fasterxml.jackson.annotation.JsonProperty
import com.fasterxml.jackson.databind.annotation.JsonSerialize

/**
 *
 * JSON serializable DTO containing LLDDHealthProblemCategory data
 *
 */
@JsonSerialize
@EqualsAndHashCode(includeFields=true)
public class LLDDHealthProblemCategoryDto implements Serializable {
    
    @JsonProperty
    private Integer id;
    
    @JsonProperty
    private String code;
    
    @JsonProperty
    private String description;
    
    @JsonProperty
    private String shortDescription;
    
    @JsonProperty
    private Date validFrom;
    
    @JsonProperty
    private Date validTo;
    
    /**
     * Default No Args constructor
     */
    public LLDDHealthProblemCategoryDto() {
    }
    
    /**
     * Constructor to create a LLDDHealthProblemCategoryDto object
     *
     * @param id the Id for the LLDDHealthProblemCategory
     * @param code the code for the LLDDHealthProblemCategory
     * @param description the description for the LLDDHealthProblemCategory
     * @Param shortDescription the shortDescription of the LLDDHealthProblemCategory
     * @Param validFrom the validFrom Date for the LLDDHealthProblemCategory
     * @Param validTo the validTo Date for the LLDDHealthProblemCategory
     */
    public LLDDHealthProblemCategoryDto(Integer id, String code, String description, String shortDescription, Date validFrom, Date validTo) {
        this.id = id;
        this.code = code;
        this.description = description;
        this.shortDescription = shortDescription;
        this.validFrom = validFrom;
        this.validTo = validTo;
    }
    
    /**
     * Constructor to create a LLDDHealthProblemCategoryDto object from a LLDDHealthProblemCategory object
     *
     * @param lLDDHealthProblemCategory the LLDDHealthProblemCategory object to use for construction
     */
    LLDDHealthProblemCategoryDto(LLDDHealthProblemCategory lLDDHealthProblemCategory) {
        this.id = lLDDHealthProblemCategory.id;
        this.code = lLDDHealthProblemCategory.code;
        this.description = lLDDHealthProblemCategory.description;
        this.shortDescription = lLDDHealthProblemCategory.shortDescription;
        this.validFrom = lLDDHealthProblemCategory.validFrom;
        this.validTo = lLDDHealthProblemCategory.validTo;
    }
    
    @Override
    public String toString() {
        return "LLDDHealthProblemCategoryDto [id=" + id + ", code=" + code + ", description=" + description + ", shortDescription=" + shortDescription + ", validFrom=" + validFrom + ", validTo=" + validTo + "]";
    }
    
    public static LLDDHealthProblemCategoryDto mapFromLLDDHealthProblemCategoryEntity(LLDDHealthProblemCategory lLDDHealthProblemCategory) {
        return new LLDDHealthProblemCategoryDto(lLDDHealthProblemCategory);
    }
    
    public static List<LLDDHealthProblemCategoryDto> mapFromLLDDHealthProblemCategoriesEntities(List<LLDDHealthProblemCategory> lLDDHealthProblemCategories) {
        List<LLDDHealthProblemCategoryDto> output = lLDDHealthProblemCategories.collect { lLDDHealthProblemCategory ->  new LLDDHealthProblemCategoryDto(lLDDHealthProblemCategory) };
        return output
    }
    
    public static LLDDHealthProblemCategory mapToLLDDHealthProblemCategoryEntity(LLDDHealthProblemCategoryDto lLDDHealthProblemCategoryDto) {
        return new LLDDHealthProblemCategory(lLDDHealthProblemCategoryDto.id, lLDDHealthProblemCategoryDto.code, lLDDHealthProblemCategoryDto.description, lLDDHealthProblemCategoryDto.shortDescription, lLDDHealthProblemCategoryDto.validFrom, lLDDHealthProblemCategoryDto.validTo)
    }
}
