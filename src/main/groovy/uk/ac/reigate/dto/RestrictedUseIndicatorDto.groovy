package uk.ac.reigate.dto;


import groovy.transform.EqualsAndHashCode
import uk.ac.reigate.domain.ilr.RestrictedUseIndicator

import com.fasterxml.jackson.annotation.JsonFormat
import com.fasterxml.jackson.annotation.JsonProperty
import com.fasterxml.jackson.databind.annotation.JsonSerialize

/**
 *
 * JSON serializable DTO containing RestrictedUseIndicator data
 *
 */
@JsonSerialize
@EqualsAndHashCode(includeFields=true)
public class RestrictedUseIndicatorDto implements Serializable {
    
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
    public RestrictedUseIndicatorDto() {
    }
    
    /**
     * Constructor to create a RestrictedUseIndicatorDto object
     *
     * @param id the Id for the RestrictedUseIndicator
     * @param code the code for the RestrictedUseIndicator
     * @param description the description for the RestrictedUseIndicator
     * @Param shortDescription the shortDescription of the RestrictedUseIndicator
     * @Param validFrom the validFrom Date for the RestrictedUseIndicator
     * @Param validTo the validTo Date for the RestrictedUseIndicator
     */
    public RestrictedUseIndicatorDto(Integer id, String code, String description, String shortDescription, Date validFrom, Date validTo) {
        this.id = id;
        this.code = code;
        this.description = description;
        this.shortDescription = shortDescription;
        this.validFrom = validFrom;
        this.validTo = validTo;
    }
    
    /**
     * Constructor to create a RestrictedUseIndicatorDto object from a RestrictedUseIndicator object
     *
     * @param restrictedUseIndicator the RestrictedUseIndicator object to use for construction
     */
    RestrictedUseIndicatorDto(RestrictedUseIndicator restrictedUseIndicator) {
        this.id = restrictedUseIndicator.id;
        this.code = restrictedUseIndicator.code;
        this.description = restrictedUseIndicator.description;
        this.shortDescription = restrictedUseIndicator.shortDescription;
        this.validFrom = restrictedUseIndicator.validFrom;
        this.validTo = restrictedUseIndicator.validTo;
    }
    
    @Override
    public String toString() {
        return "RestrictedUseIndicatorDto [id=" + id + ", code=" + code + ", description=" + description + ", shortDescription=" + shortDescription + ", validFrom=" + validFrom + ", validTo=" + validTo + "]";
    }
    
    public static RestrictedUseIndicatorDto mapFromRestrictedUseIndicatorEntity(RestrictedUseIndicator restrictedUseIndicator) {
        return new RestrictedUseIndicatorDto(restrictedUseIndicator);
    }
    
    public static List<RestrictedUseIndicatorDto> mapFromRestrictedUseIndicatorsEntities(List<RestrictedUseIndicator> restrictedUseIndicators) {
        List<RestrictedUseIndicatorDto> output = restrictedUseIndicators.collect { restrictedUseIndicator ->  new RestrictedUseIndicatorDto(restrictedUseIndicator) };
        return output
    }
    
    public static RestrictedUseIndicator mapToRestrictedUseIndicatorEntity(RestrictedUseIndicatorDto restrictedUseIndicatorDto) {
        return new RestrictedUseIndicator(restrictedUseIndicatorDto.id, restrictedUseIndicatorDto.code, restrictedUseIndicatorDto.description, restrictedUseIndicatorDto.shortDescription, restrictedUseIndicatorDto.validFrom, restrictedUseIndicatorDto.validTo)
    }
}
