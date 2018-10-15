package uk.ac.reigate.dto;


import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;

import groovy.transform.EqualsAndHashCode;
import uk.ac.reigate.domain.learning_support.SupportType

/**
 *
 * JSON serializable DTO containing SupportType data
 *
 */
@JsonSerialize
@EqualsAndHashCode(includeFields=true)
public class SupportTypeDto implements Serializable {
    
    @JsonProperty
    private Integer id;
    
    @JsonProperty
    private String support;
    
    
    /**
     * Default No Args constructor
     */
    public SupportTypeDto() {
    }
    
    /**
     * Constructor to create a SupportTypeDto object with the basic data.
     *
     * @param id the Id for the SupportType
     * @param support the support for the SupportType
     */
    public SupportTypeDto(Integer id, String support) {
        this.id = id;
        this.support = support;
    }
    
    /**
     * Constructor to create a SupportTypeDto object from a SupportType object
     *
     * @param supportType the SupportType object to use for construction
     */
    SupportTypeDto(SupportType supportType) {
        this.id = supportType.id;
        this.support = supportType.support;
    }
    
    @Override
    public String toString() {
        return "SupportTypeDto [id=" + id + ", support=" + support + "]";
    }
    
    public SupportType toSupportType() {
        return new SupportType(this.id, this.support);
    }
    
    public static SupportTypeDto mapFromSupportTypeEntity(SupportType supportType) {
        return new SupportTypeDto(supportType);
    }
    
    public static List<SupportTypeDto> mapFromSupportTypesEntities(List<SupportType> supportTypes) {
        List<SupportTypeDto> output = supportTypes.collect { supportType ->  new SupportTypeDto(supportType) };
        return output
    }
    
    public static SupportType mapToSupportTypeEntity(SupportTypeDto supportTypeDto) {
        return new SupportType(supportTypeDto.id, supportTypeDto.support)
    }
}
