package uk.ac.reigate.dto;


import groovy.transform.EqualsAndHashCode
import io.swagger.annotations.ApiModel
import io.swagger.annotations.ApiModelProperty
import uk.ac.reigate.domain.ilr.EnglishConditionOfFunding

import com.fasterxml.jackson.annotation.JsonFormat
import com.fasterxml.jackson.annotation.JsonProperty
import com.fasterxml.jackson.databind.annotation.JsonSerialize

/**
 *
 * JSON serializable DTO containing EnglishConditionOfFunding data
 *
 */
@ApiModel
@JsonSerialize
@EqualsAndHashCode(includeFields=true)
public class EnglishConditionOfFundingDto implements Serializable {
    
    @ApiModelProperty(position = 1, required = false, value = "The ID of the EnglishConditionOfFunding object stored in the database. This must be provided for any PUT operations but omitted for POST opertions.")
    @JsonProperty
    private Integer id;
    
    @ApiModelProperty(position = 2, required = true, value = "The code of the EnglishConditionOfFunding object stored in the database")
    @JsonProperty
    private String code;
    
    @ApiModelProperty(position = 3, required = true, value = "The description of the EnglishConditionOfFunding object stored in the database")
    @JsonProperty
    private String description;
    
    @ApiModelProperty(position = 4, required = true, value = "The short description of the EnglishConditionOfFunding object stored in the database")
    @JsonProperty
    private String shortDescription;
    
    @ApiModelProperty(position = 5, required = true, value = "The valid from date of the EnglishConditionOfFunding object stored in the database", dataType = "string date")
    @JsonProperty
    private Date validFrom;
    
    @ApiModelProperty(position = 6, required = true, value = "The valid to date of the EnglishConditionOfFunding object stored in the database")
    @JsonProperty
    private Date validTo;
    
    /**
     * Default No Args constructor
     */
    public EnglishConditionOfFundingDto() {
    }
    
    /**
     * Constructor to create a EnglishConditionOfFundingDto object
     *
     * @param id the Id for the EnglishConditionOfFunding
     * @param code the code for the EnglishConditionOfFunding
     * @param description the description for the EnglishConditionOfFunding
     * @Param shortDescription the shortDescription of the EnglishConditionOfFunding
     * @Param validFrom the validFrom Date for the EnglishConditionOfFunding
     * @Param validTo the validTo Date for the EnglishConditionOfFunding
     */
    public EnglishConditionOfFundingDto(Integer id, String code, String description, String shortDescription, Date validFrom, Date validTo) {
        this.id = id;
        this.code = code;
        this.description = description;
        this.shortDescription = shortDescription;
        this.validFrom = validFrom;
        this.validTo = validTo;
    }
    
    /**
     * Constructor to create an EnglishConditionOfFundingDto object from a EnglishConditionOfFunding object
     *
     * @param englishConditionOfFunding the EnglishConditionOfFunding object to use for construction
     */
    EnglishConditionOfFundingDto(EnglishConditionOfFunding englishConditionOfFunding) {
        this.id = englishConditionOfFunding.id;
        this.code = englishConditionOfFunding.code;
        this.description = englishConditionOfFunding.description;
        this.shortDescription = englishConditionOfFunding.shortDescription;
        this.validFrom = englishConditionOfFunding.validFrom;
        this.validTo = englishConditionOfFunding.validTo;
    }
    
    @Override
    public String toString() {
        return "EnglishConditionOfFundingDto [id=" + id + ", code=" + code + ", description=" + description + ", shortDescription=" + shortDescription + ", validFrom=" + validFrom + ", validTo=" + validTo + "]";
    }
    
    public static EnglishConditionOfFundingDto mapFromEnglishConditionOfFundingEntity(EnglishConditionOfFunding englishConditionOfFunding) {
        return new EnglishConditionOfFundingDto(englishConditionOfFunding);
    }
    
    public static List<EnglishConditionOfFundingDto> mapFromEnglishConditionOfFundingsEntities(List<EnglishConditionOfFunding> englishConditionOfFundings) {
        List<EnglishConditionOfFundingDto> output = englishConditionOfFundings.collect { englishConditionOfFunding ->  new EnglishConditionOfFundingDto(englishConditionOfFunding) };
        return output
    }
    
    public static EnglishConditionOfFunding mapToEnglishConditionOfFundingEntity(EnglishConditionOfFundingDto englishConditionOfFundingDto) {
        return new EnglishConditionOfFunding(englishConditionOfFundingDto.id, englishConditionOfFundingDto.code, englishConditionOfFundingDto.description, englishConditionOfFundingDto.shortDescription, englishConditionOfFundingDto.validFrom, englishConditionOfFundingDto.validTo)
    }
}
