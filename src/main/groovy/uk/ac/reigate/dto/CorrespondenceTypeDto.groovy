package uk.ac.reigate.dto;


import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;

import groovy.transform.EqualsAndHashCode;
import uk.ac.reigate.domain.ilp.CorrespondenceType

/**
 *
 * JSON  DTO containing CorrespondenceType data
 *
 */
@JsonSerialize
@EqualsAndHashCode(includeFields=true)
public class CorrespondenceTypeDto  {
    
    @JsonProperty
    private Integer id;
    
    @JsonProperty
    private String type;
    
    /**
     * Default No Args constructor
     */
    public CorrespondenceTypeDto() {
    }
    
    /**
     * Constructor to create a CorrespondenceTypeDto object
     *
     * @param id the Id for the CorrespondenceType
     * @param correspondenceType the CorrespondenceType for the CorrespondenceType
     */
    public CorrespondenceTypeDto(Integer id, String type) {
        this.id = id;
        this.type = type;
    }
    
    /**
     * Constructor to create a CorrespondenceTypeDto object from a CorrespondenceType object
     *
     * @param correspondenceType the CorrespondenceType object to use for construction
     */
    CorrespondenceTypeDto(CorrespondenceType correspondenceType) {
        this.id = correspondenceType.id;
        this.type = correspondenceType.type;
    }
    
    @Override
    public String toString() {
        return "CorrespondenceTypeDto [id=" + id + ", type=" + type + "]";
    }
    
    public static CorrespondenceTypeDto mapFromCorrespondenceTypeEntity(CorrespondenceType correspondenceType) {
        return new CorrespondenceTypeDto(correspondenceType);
    }
    
    public static List<CorrespondenceTypeDto> mapFromCorrespondenceTypesEntities(List<CorrespondenceType> correspondenceTypes) {
        List<CorrespondenceTypeDto> output = correspondenceTypes.collect { correspondenceType ->  new CorrespondenceTypeDto(correspondenceType) };
        return output
    }
    
    public static CorrespondenceType mapToCorrespondenceTypeEntity(CorrespondenceTypeDto correspondenceTypeDto) {
        return new CorrespondenceType(correspondenceTypeDto.id, correspondenceTypeDto.type)
    }
}
