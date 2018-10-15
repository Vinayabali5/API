package uk.ac.reigate.dto;


import groovy.transform.EqualsAndHashCode
import uk.ac.reigate.domain.ilr.LLDDHealthProblem

import com.fasterxml.jackson.annotation.JsonFormat
import com.fasterxml.jackson.annotation.JsonProperty
import com.fasterxml.jackson.databind.annotation.JsonSerialize

/**
 *
 * JSON serializable DTO containing LLDDHealthProblem data
 *
 */
@JsonSerialize
@EqualsAndHashCode(includeFields=true)
public class LLDDHealthProblemDto implements Serializable {
    
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
    public LLDDHealthProblemDto() {
    }
    
    /**
     * Constructor to create a LLDDHealthProblemDto object
     *
     * @param id the Id for the LLDDHealthProblem
     * @param code the code for the LLDDHealthProblem
     * @param description the description for the LLDDHealthProblem
     * @Param shortDescription the shortDescription of the LLDDHealthProblem
     * @Param validFrom the validFrom Date for the LLDDHealthProblem
     * @Param validTo the validTo Date for the LLDDHealthProblem
     */
    public LLDDHealthProblemDto(Integer id, String code, String description, String shortDescription, Date validFrom, Date validTo) {
        this.id = id;
        this.code = code;
        this.description = description;
        this.shortDescription = shortDescription;
        this.validFrom = validFrom;
        this.validTo = validTo;
    }
    
    /**
     * Constructor to create a LLDDHealthProblemDto object from a LLDDHealthProblem object
     *
     * @param lLDDHealthProblem the LLDDHealthProblem object to use for construction
     */
    LLDDHealthProblemDto(LLDDHealthProblem lLDDHealthProblem) {
        this.id = lLDDHealthProblem.id;
        this.code = lLDDHealthProblem.code;
        this.description = lLDDHealthProblem.description;
        this.shortDescription = lLDDHealthProblem.shortDescription;
        this.validFrom = lLDDHealthProblem.validFrom;
        this.validTo = lLDDHealthProblem.validTo;
    }
    
    @Override
    public String toString() {
        return "LLDDHealthProblemDto [id=" + id + ", code=" + code + ", description=" + description + ", shortDescription=" + shortDescription + ", validFrom=" + validFrom + ", validTo=" + validTo + "]";
    }
    
    public static LLDDHealthProblemDto mapFromLLDDHealthProblemEntity(LLDDHealthProblem lLDDHealthProblem) {
        return new LLDDHealthProblemDto(lLDDHealthProblem);
    }
    
    public static List<LLDDHealthProblemDto> mapFromLLDDHealthProblemsEntities(List<LLDDHealthProblem> lLDDHealthProblems) {
        List<LLDDHealthProblemDto> output = lLDDHealthProblems.collect { lLDDHealthProblem ->  new LLDDHealthProblemDto(lLDDHealthProblem) };
        return output
    }
    
    public static LLDDHealthProblem mapToLLDDHealthProblemEntity(LLDDHealthProblemDto lLDDHealthProblemDto) {
        return new LLDDHealthProblem(lLDDHealthProblemDto.id, lLDDHealthProblemDto.code, lLDDHealthProblemDto.description, lLDDHealthProblemDto.shortDescription, lLDDHealthProblemDto.validFrom, lLDDHealthProblemDto.validTo)
    }
}
