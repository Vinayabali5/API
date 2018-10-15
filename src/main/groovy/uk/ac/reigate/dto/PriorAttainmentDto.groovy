package uk.ac.reigate.dto;


import groovy.transform.EqualsAndHashCode
import uk.ac.reigate.domain.ilr.PriorAttainment

import com.fasterxml.jackson.annotation.JsonFormat
import com.fasterxml.jackson.annotation.JsonProperty
import com.fasterxml.jackson.databind.annotation.JsonSerialize

/**
 *
 * JSON serializable DTO containing PriorAttainment data
 *
 */
@JsonSerialize
@EqualsAndHashCode(includeFields=true)
public class PriorAttainmentDto implements Serializable {
    
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
    public PriorAttainmentDto() {
    }
    
    /**
     * Constructor to create a PriorAttainmentDto object
     *
     * @param id the Id for the PriorAttainment
     * @param code the code for the PriorAttainment
     * @param description the description for the PriorAttainment
     * @Param shortDescription the shortDescription of the PriorAttainment
     * @Param validFrom the validFrom Date for the PriorAttainment
     * @Param validTo the validTo Date for the PriorAttainment
     */
    public PriorAttainmentDto(Integer id, String code, String description, String shortDescription, Date validFrom, Date validTo) {
        this.id = id;
        this.code = code;
        this.description = description;
        this.shortDescription = shortDescription;
        this.validFrom = validFrom;
        this.validTo = validTo;
    }
    
    /**
     * Constructor to create a PriorAttainmentDto object from a PriorAttainment object
     *
     * @param priorAttainment the PriorAttainment object to use for construction
     */
    PriorAttainmentDto(PriorAttainment priorAttainment) {
        this.id = priorAttainment.id;
        this.code = priorAttainment.code;
        this.description = priorAttainment.description;
        this.shortDescription = priorAttainment.shortDescription;
        this.validFrom = priorAttainment.validFrom;
        this.validTo = priorAttainment.validTo;
    }
    
    @Override
    public String toString() {
        return "PriorAttainmentDto [id=" + id + ", code=" + code + ", description=" + description + ", shortDescription=" + shortDescription + ", validFrom=" + validFrom + ", validTo=" + validTo + "]";
    }
    
    public static PriorAttainmentDto mapFromPriorAttainmentEntity(PriorAttainment priorAttainment) {
        return new PriorAttainmentDto(priorAttainment);
    }
    
    public static List<PriorAttainmentDto> mapFromPriorAttainmentsEntities(List<PriorAttainment> priorAttainments) {
        List<PriorAttainmentDto> output = priorAttainments.collect { priorAttainment ->  new PriorAttainmentDto(priorAttainment) };
        return output
    }
    
    public static PriorAttainment mapToPriorAttainmentEntity(PriorAttainmentDto priorAttainmentDto) {
        return new PriorAttainment(priorAttainmentDto.id, priorAttainmentDto.code, priorAttainmentDto.description, priorAttainmentDto.shortDescription, priorAttainmentDto.validFrom, priorAttainmentDto.validTo)
    }
}
