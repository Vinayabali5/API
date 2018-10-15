package uk.ac.reigate.dto;


import java.util.Date

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.annotation.JsonFormat
import groovy.transform.EqualsAndHashCode;
import uk.ac.reigate.domain.ilr.FundingModel

/**
 *
 * JSON serializable DTO containing FundingModel data
 *
 */
@JsonSerialize
@EqualsAndHashCode(includeFields=true)
public class FundingModelDto implements Serializable {
    
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
    public FundingModelDto() {
    }
    
    /**
     * Constructor to create a FundingModelDto object
     *
     * @param id the Id for the FundingModel
     * @param code the code for the FundingModel
     * @param description the description for the FundingModel
     * @Param shortDescription the shortDescription of the FundingModel
     * @Param validFrom the validFrom Date for the FundingModel
     * @Param validTo the validTo Date for the FundingModel
     */
    public FundingModelDto(Integer id, String code, String description, String shortDescription, Date validFrom, Date validTo) {
        this.id = id;
        this.code = code;
        this.description = description;
        this.shortDescription = shortDescription;
        this.validFrom = validFrom;
        this.validTo = validTo;
    }
    
    /**
     * Constructor to create an FundingModelDto object from a FundingModel object
     *
     * @param fundingModel the FundingModel object to use for construction
     */
    FundingModelDto(FundingModel fundingModel) {
        this.id = fundingModel.id;
        this.code = fundingModel.code;
        this.description = fundingModel.description;
        this.shortDescription = fundingModel.shortDescription;
        this.validFrom = fundingModel.validFrom;
        this.validTo = fundingModel.validTo;
    }
    
    @Override
    public String toString() {
        return "FundingModelDto [id=" + id + ", code=" + code + ", description=" + description + ", shortDescription=" + shortDescription + ", validFrom=" + validFrom + ", validTo=" + validTo + "]";
    }
    
    public static FundingModelDto mapFromFundingModelEntity(FundingModel fundingModel) {
        return new FundingModelDto(fundingModel);
    }
    
    public static List<FundingModelDto> mapFromFundingModelsEntities(List<FundingModel> fundingModels) {
        List<FundingModelDto> output = fundingModels.collect { fundingModel ->  new FundingModelDto(fundingModel) };
        return output
    }
    
    public static FundingModel mapToFundingModelEntity(FundingModelDto fundingModelDto) {
        return new FundingModel(fundingModelDto.id, fundingModelDto.code, fundingModelDto.description, fundingModelDto.shortDescription, fundingModelDto.validFrom, fundingModelDto.validTo)
    }
}
