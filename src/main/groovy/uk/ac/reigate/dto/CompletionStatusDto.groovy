package uk.ac.reigate.dto;


import java.util.Date

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.annotation.JsonFormat
import groovy.transform.EqualsAndHashCode;
import uk.ac.reigate.domain.ilr.CompletionStatus

/**
 *
 * JSON serializable DTO containing CompletionStatus data
 *
 */
@JsonSerialize
@EqualsAndHashCode(includeFields=true)
public class CompletionStatusDto implements Serializable {
    
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
    public CompletionStatusDto() {
    }
    
    /**
     * Constructor to create a CompletionStatusDto object
     *
     * @param id the Id for the CompletionStatus
     * @param code the code for the CompletionStatus
     * @param description the description for the CompletionStatus
     * @Param shortDescription the shortDescription of the CompletionStatus
     * @Param validFrom the validFrom Date for the CompletionStatus
     * @Param validTo the validTo Date for the CompletionStatus
     */
    public CompletionStatusDto(Integer id, String code, String description, String shortDescription, Date validFrom, Date validTo) {
        this.id = id;
        this.code = code;
        this.description = description;
        this.shortDescription = shortDescription;
        this.validFrom = validFrom;
        this.validTo = validTo;
    }
    
    /**
     * Constructor to create a CompletionStatusDto object from a CompletionStatus object
     *
     * @param completionStatus the CompletionStatus object to use for construction
     */
    CompletionStatusDto(CompletionStatus completionStatus) {
        this.id = completionStatus.id;
        this.code = completionStatus.code;
        this.description = completionStatus.description;
        this.shortDescription = completionStatus.shortDescription;
        this.validFrom = completionStatus.validFrom;
        this.validTo = completionStatus.validTo;
    }
    
    @Override
    public String toString() {
        return "CompletionStatusDto [id=" + id + ", code=" + code + ", description=" + description + ", shortDescription=" + shortDescription + ", validFrom=" + validFrom + ", validTo=" + validTo + "]";
    }
    
    public CompletionStatus toCompletionStatus() {
        return new CompletionStatus(this.id, this.code, this.description, this.shortDescription, this.validFrom, this.validTo)
    }
    
    public static CompletionStatusDto mapFromEntity(CompletionStatus completionStatus){
        return new CompletionStatusDto(completionStatus);
    }
    
    public static CompletionStatusDto mapFromCompletionStatusEntity(CompletionStatus completionStatus) {
        return new CompletionStatusDto(completionStatus);
    }
    
    public static List<CompletionStatusDto> mapFromCompletionStatusesEntities(List<CompletionStatus> completionStatuses) {
        List<CompletionStatusDto> output = completionStatuses.collect { completionStatus ->  new CompletionStatusDto(completionStatus) };
        return output
    }
    
    public static CompletionStatus mapToCompletionStatusEntity(CompletionStatusDto completionStatusDto) {
        return new CompletionStatus(completionStatusDto.id, completionStatusDto.code, completionStatusDto.description, completionStatusDto.shortDescription, completionStatusDto.validFrom, completionStatusDto.validTo)
    }
}
