package uk.ac.reigate.dto;


import groovy.transform.EqualsAndHashCode
import uk.ac.reigate.domain.admissions.OfferType

import com.fasterxml.jackson.annotation.JsonProperty
import com.fasterxml.jackson.databind.annotation.JsonSerialize

/**
 *
 * JSON  DTO containing OfferType data
 *
 */
@JsonSerialize
@EqualsAndHashCode(includeFields=true)
public class OfferTypeDto {
    
    @JsonProperty
    private Integer id;
    
    @JsonProperty
    private String code;
    
    @JsonProperty
    private String description;
    
    /**
     * Default No Args constructor
     */
    public OfferTypeDto() {
    }
    
    /**
     * Constructor to create an OfferTypeDto object
     *
     * @param id the Id for the OfferType
     * @param code the code for the OfferType
     * @param description the description for the OfferType
     */
    public OfferTypeDto(Integer id, String code, String description) {
        this.id = id;
        this.code = code;
        this.description = description;
    }
    
    /**
     * Constructor to create an OfferTypeDto object from a OfferType object
     *
     * @param offerType the OfferType object to use for construction
     */
    OfferTypeDto(OfferType offerType) {
        this.id = offerType.id;
        this.code = offerType.code;
        this.description = offerType.description;
    }
    
    @Override
    public String toString() {
        return "OfferTypeDto [id=" + id + ", code=" + code + ", description=" + description + "]";
    }
    
    public static OfferTypeDto mapFromOfferTypeEntity(OfferType offerType) {
        return new OfferTypeDto(offerType);
    }
    
    public static List<OfferTypeDto> mapFromOfferTypesEntities(List<OfferType> offerTypes) {
        List<OfferTypeDto> output = offerTypes.collect { offerType ->  new OfferTypeDto(offerType) };
        return output
    }
    
    public static OfferType mapToOfferTypeEntity(OfferTypeDto offerTypeDto) {
        return new OfferType(offerTypeDto.id, offerTypeDto.code, offerTypeDto.description)
    }
}
