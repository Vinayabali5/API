package uk.ac.reigate.dto.exam;


import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;

import groovy.transform.EqualsAndHashCode;
import uk.ac.reigate.domain.exam.EdiStatusType

/**
 *
 * JSON serializable DTO containing EdiStatusType data
 *
 */
@JsonSerialize
@EqualsAndHashCode(includeFields=true)
public class EdiStatusTypeDto implements Serializable {
    
    @JsonProperty
    private Integer id;
    
    @JsonProperty
    private String code;
    
    @JsonProperty
    private String description;
    
    /**
     * Default No Args constructor
     */
    public EdiStatusTypeDto() {
    }
    
    /**
     * Constructor to create a EdiStatusTypeDto object
     *
     * @param id the Id for the EdiStatusType
     * @param code the code for the EdiStatusType
     * @param description the description for the EdiStatusType
     */
    public EdiStatusTypeDto(Integer id, String code, String description) {
        this.id = id;
        this.code = code;
        this.description = description;
    }
    
    @Override
    public String toString() {
        return "EdiStatusTypeDto [id=" + id + ", code=" + code + ", description=" + description + "]";
    }
    
    public static EdiStatusTypeDto mapFromEdiStatusTypeEntity(EdiStatusType ediStatusType) {
        EdiStatusTypeDto output = new EdiStatusTypeDto(ediStatusType.id, ediStatusType.code, ediStatusType.description);
        return output
    }
    
    public static List<EdiStatusTypeDto> mapFromEdiStatusTypesEntities(List<EdiStatusType> ediStatusTypes) {
        List<EdiStatusTypeDto> output = ediStatusTypes.collect { ediStatusType ->  mapFromEdiStatusTypeEntity(ediStatusType) };
        return output
    }
    
    public static EdiStatusType mapToEdiStatusTypeEntity(EdiStatusTypeDto ediStatusTypeDto) {
        return new EdiStatusType(ediStatusTypeDto.id, ediStatusTypeDto.code, ediStatusTypeDto.description)
    }
}
