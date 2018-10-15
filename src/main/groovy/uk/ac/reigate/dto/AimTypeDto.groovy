package uk.ac.reigate.dto;


import groovy.transform.EqualsAndHashCode
import io.swagger.annotations.ApiModel
import io.swagger.annotations.ApiModelProperty

import javax.validation.constraints.NotNull

import uk.ac.reigate.domain.ilr.AimType

import com.fasterxml.jackson.annotation.JsonFormat
import com.fasterxml.jackson.annotation.JsonProperty
import com.fasterxml.jackson.databind.annotation.JsonSerialize

/**
 *
 * JSON serializable DTO containing AimType data
 *
 */
@ApiModel
@JsonSerialize
@EqualsAndHashCode(includeFields=true)
public class AimTypeDto implements Serializable {
    
    @ApiModelProperty(position = 1, required = false, value = "The ID of the AimType object stored in the database. This must be provided for any PUT operations but omitted for POST opertions.")
    @JsonProperty
    private Integer id;
    
    @ApiModelProperty(position = 2, required = true, value = "The code of the AimType object stored in the database")
    @NotNull
    @JsonProperty
    private String code;
    
    @ApiModelProperty(position = 3, required = true, value = "The description of the AimType object stored in the database")
    @NotNull
    @JsonProperty
    private String description;
    
    @ApiModelProperty(position = 4, required = true, value = "The short description of the AimType object stored in the database")
    @NotNull
    @JsonProperty
    private String shortDescription;
    
    @ApiModelProperty(position = 5, required = true, value = "The valid from date of the AimType object stored in the database", dataType = "string date")
    @JsonProperty
    private Date validFrom;
    
    @ApiModelProperty(position = 6, required = true, value = "The valid to date of the AimType object stored in the database")
    @JsonProperty
    private Date validTo;
    
    /**
     * Default No Args constructor
     */
    public AimTypeDto() {
    }
    
    /**
     * Constructor to create a AimTypeDto object
     *
     * @param id the Id for the AimType
     * @param code the code for the AimType
     * @param description the description for the AimType
     * @Param shortDescription the shortDescription of the AimType
     * @Param validFrom the validFrom Date for the AimType
     * @Param validTo the validTo Date for the AimType
     */
    public AimTypeDto(Integer id, String code, String description, String shortDescription, Date validFrom, Date validTo) {
        this.id = id;
        this.code = code;
        this.description = description;
        this.shortDescription = shortDescription;
        this.validFrom = validFrom;
        this.validTo = validTo;
    }
    
    /**
     * Constructor to create an AimTypeDto object from an AimType object
     *
     * @param aimType the AimType object to use for construction
     */
    public AimTypeDto(AimType aimType) {
        this.id = aimType.id;
        this.code = aimType.code;
        this.description = aimType.description;
        this.shortDescription = aimType.shortDescription;
        this.validFrom = aimType.validFrom;
        this.validTo = aimType.validTo;
    }
    
    @Override
    public String toString() {
        return "AimTypeDto [id=" + id + ", code=" + code + ", description=" + description + ", shortDescription=" + shortDescription + ", validFrom=" + validFrom + ", validTo=" + validTo + "]";
    }
    
    public static AimTypeDto mapFromAimTypeEntity(AimType aimType) {
        return new AimTypeDto(aimType);
    }
    
    public static List<AimTypeDto> mapFromAimTypesEntities(List<AimType> aimTypes) {
        List<AimTypeDto> output = aimTypes.collect { aimType ->  new AimTypeDto(aimType) };
        return output
    }
    
    public static AimType mapToAimTypeEntity(AimTypeDto aimTypeDto) {
        return new AimType(aimTypeDto.id, aimTypeDto.code, aimTypeDto.description, aimTypeDto.shortDescription, aimTypeDto.validFrom, aimTypeDto.validTo)
    }
}
