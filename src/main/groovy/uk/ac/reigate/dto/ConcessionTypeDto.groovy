package uk.ac.reigate.dto;


import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;

import groovy.transform.EqualsAndHashCode;
import uk.ac.reigate.domain.learning_support.ConcessionType

/**
 *
 * JSON serializable DTO containing ConcessionType data
 *
 */
@JsonSerialize
@EqualsAndHashCode(includeFields=true)
public class ConcessionTypeDto implements Serializable {
    
    @JsonProperty
    private Integer id;
    
    @JsonProperty
    private String code;
    
    @JsonProperty
    private String description;
    
    /**
     * Default No Args constructor
     */
    public ConcessionTypeDto() {
    }
    
    /**
     * Constructor to create a ConcessionTypeDto object
     *
     * @param id the Id for the ConcessionType
     * @param code the code for the ConcessionType
     * @param description the description for the ConcessionType
     */
    public ConcessionTypeDto(Integer id, String code, String description) {
        this.id = id;
        this.code = code;
        this.description = description;
    }
    
    /**
     * Constructor to create a ConcessionTypeDto object from a ConcessionType object
     *
     * @param concessionType the ConcessionType object to use for construction
     */
    ConcessionTypeDto(ConcessionType concessionType) {
        this.id = concessionType.id;
        this.code = concessionType.code;
        this.description = concessionType.description;
    }
    
    @Override
    public String toString() {
        return "ConcessionTypeDto [id=" + id + ", code=" + code + ", description=" + description + "]";
    }
    
    public ConcessionType toConcessionType() {
        return new ConcessionType(this.id, this.code, this.description)
    }
    
    public static ConcessionTypeDto mapFromEntity(ConcessionType concessionType){
        return new ConcessionType(concessionType)
    }
    
    public static ConcessionTypeDto mapFromConcessionTypeEntity(ConcessionType concessionType) {
        return new ConcessionTypeDto(concessionType);
    }
    
    public static List<ConcessionTypeDto> mapFromConcessionTypesEntities(List<ConcessionType> concessionTypes) {
        List<ConcessionTypeDto> output = concessionTypes.collect { concessionType ->  new ConcessionTypeDto(concessionType) };
        return output
    }
    
    public static ConcessionType mapToConcessionTypeEntity(ConcessionTypeDto concessionTypeDto) {
        return new ConcessionType(concessionTypeDto.id, concessionTypeDto.code, concessionTypeDto.description)
    }
}
