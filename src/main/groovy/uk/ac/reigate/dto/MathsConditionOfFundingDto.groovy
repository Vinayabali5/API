package uk.ac.reigate.dto;


import groovy.transform.EqualsAndHashCode
import io.swagger.annotations.ApiModel
import io.swagger.annotations.ApiModelProperty
import uk.ac.reigate.domain.ilr.MathsConditionOfFunding

import com.fasterxml.jackson.annotation.JsonFormat
import com.fasterxml.jackson.annotation.JsonProperty
import com.fasterxml.jackson.databind.annotation.JsonSerialize

/**
 *
 * JSON serializable DTO containing MathsConditionOfFunding data
 *
 */
@ApiModel
@JsonSerialize
@EqualsAndHashCode(includeFields=true)
public class MathsConditionOfFundingDto implements Serializable {
    
    @ApiModelProperty(position = 1, required = false, value = "The ID of the MathsConditionOfFunding object stored in the database. This must be provided for any PUT operations but omitted for POST opertions.")
    @JsonProperty
    private Integer id;
    
    @ApiModelProperty(position = 2, required = true, value = "The code of the MathsConditionOfFunding object stored in the database")
    @JsonProperty
    private String code;
    
    @ApiModelProperty(position = 3, required = true, value = "The description of the MathsConditionOfFunding object stored in the database")
    @JsonProperty
    private String description;
    
    @ApiModelProperty(position = 4, required = true, value = "The short description of the MathsConditionOfFunding object stored in the database")
    @JsonProperty
    private String shortDescription;
    
    @ApiModelProperty(position = 5, required = true, value = "The valid from date of the MathsConditionOfFunding object stored in the database", dataType = "string date")
    @JsonProperty
    private Date validFrom;
    
    @ApiModelProperty(position = 6, required = true, value = "The valid to date of the MathsConditionOfFunding object stored in the database")
    @JsonProperty
    private Date validTo;
    
    /**
     * Default No Args constructor
     */
    public MathsConditionOfFundingDto() {
    }
    
    /**
     * Constructor to create a MathsConditionOfFundingDto object
     *
     * @param id the Id for the MathsConditionOfFunding
     * @param code the code for the MathsConditionOfFunding
     * @param description the description for the MathsConditionOfFunding
     * @Param shortDescription the shortDescription of the MathsConditionOfFunding
     * @Param validFrom the validFrom Date for the MathsConditionOfFunding
     * @Param validTo the validTo Date for the MathsConditionOfFunding
     */
    public MathsConditionOfFundingDto(Integer id, String code, String description, String shortDescription, Date validFrom, Date validTo) {
        this.id = id;
        this.code = code;
        this.description = description;
        this.shortDescription = shortDescription;
        this.validFrom = validFrom;
        this.validTo = validTo;
    }
    
    /**
     * Constructor to create a MathsConditionOfFundingDto object from a MathsConditionOfFunding object
     *
     * @param mathsConditionOfFunding the MathsConditionOfFunding object to use for construction
     */
    MathsConditionOfFundingDto(MathsConditionOfFunding mathsConditionOfFunding) {
        this.id = mathsConditionOfFunding.id;
        this.code = mathsConditionOfFunding.code;
        this.description = mathsConditionOfFunding.description;
        this.shortDescription = mathsConditionOfFunding.shortDescription;
        this.validFrom = mathsConditionOfFunding.validFrom;
        this.validTo = mathsConditionOfFunding.validTo;
    }
    
    @Override
    public String toString() {
        return "MathsConditionOfFundingDto [id=" + id + ", code=" + code + ", description=" + description + ", shortDescription=" + shortDescription + ", validFrom=" + validFrom + ", validTo=" + validTo + "]";
    }
    
    public static MathsConditionOfFundingDto mapFromMathsConditionOfFundingEntity(MathsConditionOfFunding mathsConditionOfFunding) {
        return new MathsConditionOfFundingDto(mathsConditionOfFunding);
    }
    
    public static List<MathsConditionOfFundingDto> mapFromMathsConditionOfFundingsEntities(List<MathsConditionOfFunding> mathsConditionOfFundings) {
        List<MathsConditionOfFundingDto> output = mathsConditionOfFundings.collect { mathsConditionOfFunding ->  new MathsConditionOfFundingDto(mathsConditionOfFunding) };
        return output
    }
    
    public static MathsConditionOfFunding mapToMathsConditionOfFundingEntity(MathsConditionOfFundingDto mathsConditionOfFundingDto) {
        return new MathsConditionOfFunding(mathsConditionOfFundingDto.id, mathsConditionOfFundingDto.code, mathsConditionOfFundingDto.description, mathsConditionOfFundingDto.shortDescription, mathsConditionOfFundingDto.validFrom, mathsConditionOfFundingDto.validTo)
    }
}
