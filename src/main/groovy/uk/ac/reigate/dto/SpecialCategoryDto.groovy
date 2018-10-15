package uk.ac.reigate.dto;


import groovy.transform.EqualsAndHashCode
import uk.ac.reigate.domain.academic.SpecialCategory

import com.fasterxml.jackson.annotation.JsonProperty
import com.fasterxml.jackson.databind.annotation.JsonSerialize

/**
 *
 * JSON serializable DTO containing SpecialCategory data
 *
 */
@JsonSerialize
@EqualsAndHashCode(includeFields=true)
public class SpecialCategoryDto implements Serializable {
    
    @JsonProperty
    private Integer id;
    
    @JsonProperty
    private String code;
    
    @JsonProperty
    private String description;
    
    @JsonProperty
    private String details;
    
    @JsonProperty
    private Integer priority
    
    /**
     * Default No Args constructor
     */
    public SpecialCategoryDto() {
    }
    
    /**
     * Constructor to create a SpecialCategoryDto object
     * 
     * @param id
     * @param code
     * @param description
     * @param details
     * @param priority
     */
    public SpecialCategoryDto(Integer id, String code, String description, String details, Integer priority) {
        this.id = id;
        this.code = code;
        this.description = description;
        this.details = details;
        this.priority = priority;
    }
    
    /**
     * Constructor to create a SpecialCategoryDto object from a SpecialCategory object
     *
     * @param specialCategory the SpecialCategory object to use for construction
     */
    public SpecialCategoryDto(SpecialCategory specialCategory) {
        this.id = specialCategory.id;
        this.code = specialCategory.code;
        this.description = specialCategory.description;
        this.details = specialCategory.details;
        this.priority = specialCategory.priority;
    }
    
    @Override
    public String toString() {
        return "SpecialCategoryDto [id=" + id + ", code=" + code + ", description=" + description + ", details=" + details + ", priority=" + priority + "]";
    }
    
    public static SpecialCategoryDto mapFromSpecialCategoryEntity(SpecialCategory specialCategory) {
        return new SpecialCategoryDto(specialCategory);
    }
    
    public static List<SpecialCategoryDto> mapFromSpecialCategoriesEntities(List<SpecialCategory> specialCategories) {
        return specialCategories.collect { specialCategory ->  new SpecialCategoryDto(specialCategory) };
    }
    
    public static SpecialCategory mapToSpecialCategoryEntity(SpecialCategoryDto specialCategoryDto) {
        return new SpecialCategory(specialCategoryDto.id, specialCategoryDto.code, specialCategoryDto.description, specialCategoryDto.details, specialCategoryDto.priority)
    }
}
