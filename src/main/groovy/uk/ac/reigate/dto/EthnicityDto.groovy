package uk.ac.reigate.dto;


import java.util.Date

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.annotation.JsonFormat
import groovy.transform.EqualsAndHashCode;
import uk.ac.reigate.domain.lookup.Ethnicity

/**
 *
 * JSON serializable DTO containing Ethnicity data
 *
 */
@JsonSerialize
@EqualsAndHashCode(includeFields=true)
public class EthnicityDto implements Serializable {
    
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
    public EthnicityDto() {
    }
    
    /**
     * Constructor to create a EthnicityDto object
     *
     * @param id the Id for the Ethnicity
     * @param code the code for the Ethnicity
     * @param description the description for the Ethnicity
     * @Param shortDescription the shortDescription of the Ethnicity
     * @Param validFrom the validFrom Date for the Ethnicity
     * @Param validTo the validTo Date for the Ethnicity
     */
    public EthnicityDto(Integer id, String code, String description, String shortDescription, Date validFrom, Date validTo) {
        this.id = id;
        this.code = code;
        this.description = description;
        this.shortDescription = shortDescription;
        this.validFrom = validFrom;
        this.validTo = validTo;
    }
    
    /**
     * Constructor to create an EthnicityDto object from a Ethnicity object
     *
     * @param ethnicity the Ethnicity object to use for construction
     */
    EthnicityDto(Ethnicity ethnicity) {
        this.id = ethnicity.id;
        this.code = ethnicity.code;
        this.description = ethnicity.description;
        this.shortDescription = ethnicity.shortDescription;
        this.validFrom = ethnicity.validFrom;
        this.validTo = ethnicity.validTo;
    }
    
    @Override
    public String toString() {
        return "EthnicityDto [id=" + id + ", code=" + code + ", description=" + description + ", shortDescription=" + shortDescription + ", validFrom=" + validFrom + ", validTo=" + validTo + "]";
    }
    
    public static EthnicityDto mapFromEthnicityEntity(Ethnicity ethnicity) {
        return new EthnicityDto(ethnicity);
    }
    
    public static List<EthnicityDto> mapFromEthnicitiesEntities(List<Ethnicity> ethnicities) {
        List<EthnicityDto> output = ethnicities.collect { ethnicity ->  new EthnicityDto(ethnicity) };
        return output
    }
    
    public static Ethnicity mapToEthnicityEntity(EthnicityDto ethnicityDto) {
        return new Ethnicity(ethnicityDto.id, ethnicityDto.code, ethnicityDto.description, ethnicityDto.shortDescription, ethnicityDto.validFrom, ethnicityDto.validTo)
    }
}
