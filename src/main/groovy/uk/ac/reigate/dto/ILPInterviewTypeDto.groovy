package uk.ac.reigate.dto;


import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;

import groovy.transform.EqualsAndHashCode;
import uk.ac.reigate.domain.ilp.ILPInterviewType

/**
 *
 * JSON  DTO containing ILPInterviewType data
 *
 */
@JsonSerialize
@EqualsAndHashCode(includeFields=true)
public class ILPInterviewTypeDto  {
    
    @JsonProperty
    private Integer id;
    
    @JsonProperty
    private String type;
    
    /**
     * Default No Args constructor
     */
    public ILPInterviewTypeDto() {
    }
    
    /**
     * Constructor to create a ILPInterviewTypeDto object
     *
     * @param id the Id for the ILPInterviewType
     * @param iLPInterviewType the ILPInterviewType for the ILPInterviewType
     */
    public ILPInterviewTypeDto(Integer id, String type) {
        this.id = id;
        this.type = type;
    }
    
    /**
     * Constructor to create an ILPInterviewTypeDto object from a ILPInterviewType object
     *
     * @param iLPInterviewType the ILPInterviewType object to use for construction
     */
    ILPInterviewTypeDto(ILPInterviewType iLPInterviewType) {
        this.id = iLPInterviewType.id;
        this.type = iLPInterviewType.type;
    }
    
    @Override
    public String toString() {
        return "ILPInterviewTypeDto [id=" + id + ", type=" + type + "]";
    }
    
    public static ILPInterviewTypeDto mapFromILPInterviewTypeEntity(ILPInterviewType iLPInterviewType) {
        return new ILPInterviewTypeDto(iLPInterviewType);
    }
    
    public static List<ILPInterviewTypeDto> mapFromILPInterviewTypesEntities(List<ILPInterviewType> iLPInterviewTypes) {
        List<ILPInterviewTypeDto> output = iLPInterviewTypes.collect { iLPInterviewType ->  new ILPInterviewTypeDto(iLPInterviewType) };
        return output
    }
    
    public static ILPInterviewType mapToILPInterviewTypeEntity(ILPInterviewTypeDto iLPInterviewTypeDto) {
        return new ILPInterviewType(iLPInterviewTypeDto.id, iLPInterviewTypeDto.type)
    }
}
