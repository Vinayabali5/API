package uk.ac.reigate.dto.exam;


import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;

import groovy.transform.EqualsAndHashCode;
import uk.ac.reigate.domain.exam.StatusType

/**
 *
 * JSON serializable DTO containing StatusType data
 *
 */
@JsonSerialize
@EqualsAndHashCode(includeFields=true)
public class StatusTypeDto implements Serializable {
    
    @JsonProperty
    private Integer id;
    
    @JsonProperty
    private String code;
    
    @JsonProperty
    private String description;
    
    /**
     * Default No Args constructor
     */
    public StatusTypeDto() {
    }
    
    /**
     * Constructor to create a StatusTypeDto object
     *
     * @param id the Id for the StatusType
     * @param code the code for the StatusType
     * @param description the description for the StatusType
     */
    public StatusTypeDto(Integer id, String code, String description) {
        this.id = id;
        this.code = code;
        this.description = description;
    }
    
    @Override
    public String toString() {
        return "StatusTypeDto [id=" + id + ", code=" + code + ", description=" + description + "]";
    }
    
    public static StatusTypeDto mapFromStatusTypeEntity(StatusType statusType) {
        StatusTypeDto output = new StatusTypeDto(statusType.id, statusType.code, statusType.description);
        return output
    }
    
    public static List<StatusTypeDto> mapFromStatusTypesEntities(List<StatusType> statusTypes) {
        List<StatusTypeDto> output = statusTypes.collect { statusType ->  mapFromStatusTypeEntity(statusType) };
        return output
    }
    
    public static StatusType mapToStatusTypeEntity(StatusTypeDto statusTypeDto) {
        return new StatusType(statusTypeDto.id, statusTypeDto.code, statusTypeDto.description)
    }
}
