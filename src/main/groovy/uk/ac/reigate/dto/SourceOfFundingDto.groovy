package uk.ac.reigate.dto;


import java.util.Date

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.annotation.JsonFormat
import groovy.transform.EqualsAndHashCode;
import uk.ac.reigate.domain.ilr.SourceOfFunding

/**
 *
 * JSON serializable DTO containing SourceOfFunding data
 *
 */
@JsonSerialize
@EqualsAndHashCode(includeFields=true)
public class SourceOfFundingDto implements Serializable {
    
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
    public SourceOfFundingDto() {
    }
    
    /**
     * Constructor to create a SourceOfFundingDto object
     *
     * @param id the Id for the SourceOfFunding
     * @param code the code for the SourceOfFunding
     * @param description the description for the SourceOfFunding
     * @Param shortDescription the shortDescription of the SourceOfFunding
     * @Param validFrom the validFrom Date for the SourceOfFunding
     * @Param validTo the validTo Date for the SourceOfFunding
     */
    public SourceOfFundingDto(Integer id, String code, String description, String shortDescription, Date validFrom, Date validTo) {
        this.id = id;
        this.code = code;
        this.description = description;
        this.shortDescription = shortDescription;
        this.validFrom = validFrom;
        this.validTo = validTo;
    }
    
    /**
     * Constructor to create a SourceOfFundingDto object from a SourceOfFunding object
     *
     * @param sourceOfFunding the SourceOfFunding object to use for construction
     */
    SourceOfFundingDto(SourceOfFunding sourceOfFunding) {
        this.id = sourceOfFunding.id;
        this.code = sourceOfFunding.code;
        this.description = sourceOfFunding.description;
        this.shortDescription = sourceOfFunding.shortDescription;
        this.validFrom = sourceOfFunding.validFrom;
        this.validTo = sourceOfFunding.validTo;
    }
    
    @Override
    public String toString() {
        return "SourceOfFundingDto [id=" + id + ", code=" + code + ", description=" + description + ", shortDescription=" + shortDescription + ", validFrom=" + validFrom + ", validTo=" + validTo + "]";
    }
    
    public static SourceOfFundingDto mapFromSourceOfFundingEntity(SourceOfFunding sourceOfFunding) {
        return new SourceOfFundingDto(sourceOfFunding);
    }
    
    public static List<SourceOfFundingDto> mapFromSourceOfFundingsEntities(List<SourceOfFunding> sourceOfFundings) {
        List<SourceOfFundingDto> output = sourceOfFundings.collect { sourceOfFunding ->  new SourceOfFundingDto(sourceOfFunding) };
        return output
    }
    
    public static SourceOfFunding mapToSourceOfFundingEntity(SourceOfFundingDto sourceOfFundingDto) {
        return new SourceOfFunding(sourceOfFundingDto.id, sourceOfFundingDto.code, sourceOfFundingDto.description, sourceOfFundingDto.shortDescription, sourceOfFundingDto.validFrom, sourceOfFundingDto.validTo)
    }
}
