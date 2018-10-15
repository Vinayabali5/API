package uk.ac.reigate.dto;


import groovy.transform.EqualsAndHashCode
import uk.ac.reigate.domain.ilr.ProgrammeType

import com.fasterxml.jackson.annotation.JsonFormat
import com.fasterxml.jackson.annotation.JsonProperty
import com.fasterxml.jackson.databind.annotation.JsonSerialize

/**
 *
 * JSON serializable DTO containing ProgrammeType data
 *
 */
@JsonSerialize
@EqualsAndHashCode(includeFields=true)
public class ProgrammeTypeDto implements Serializable {
    
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
    public ProgrammeTypeDto() {
    }
    
    /**
     * Constructor to create a ProgrammeTypeDto object
     *
     * @param id the Id for the ProgrammeType
     * @param code the code for the ProgrammeType
     * @param description the description for the ProgrammeType
     * @Param shortDescription the shortDescription of the ProgrammeType
     * @Param validFrom the validFrom Date for the ProgrammeType
     * @Param validTo the validTo Date for the ProgrammeType
     */
    public ProgrammeTypeDto(Integer id, String code, String description, String shortDescription, Date validFrom, Date validTo) {
        this.id = id;
        this.code = code;
        this.description = description;
        this.shortDescription = shortDescription;
        this.validFrom = validFrom;
        this.validTo = validTo;
    }
    
    /**
     * Constructor to create a ProgrammeTypeDto object from a ProgrammeType object
     *
     * @param programmeType the ProgrammeType object to use for construction
     */
    ProgrammeTypeDto(ProgrammeType programmeType) {
        this.id = programmeType.id;
        this.code = programmeType.code;
        this.description = programmeType.description;
        this.shortDescription = programmeType.shortDescription;
        this.validFrom = programmeType.validFrom;
        this.validTo = programmeType.validTo;
    }
    
    @Override
    public String toString() {
        return "ProgrammeTypeDto [id=" + id + ", code=" + code + ", description=" + description + ", shortDescription=" + shortDescription + ", validFrom=" + validFrom + ", validTo=" + validTo + "]";
    }
    
    public static ProgrammeTypeDto mapFromProgrammeTypeEntity(ProgrammeType programmeType) {
        return new ProgrammeTypeDto(programmeType);
    }
    
    public static List<ProgrammeTypeDto> mapFromProgrammeTypesEntities(List<ProgrammeType> programmeTypes) {
        List<ProgrammeTypeDto> output = programmeTypes.collect { programmeType ->  new ProgrammeTypeDto(programmeType) };
        return output
    }
    
    public static ProgrammeType mapToProgrammeTypeEntity(ProgrammeTypeDto programmeTypeDto) {
        return new ProgrammeType(programmeTypeDto.id, programmeTypeDto.code, programmeTypeDto.description, programmeTypeDto.shortDescription, programmeTypeDto.validFrom, programmeTypeDto.validTo)
    }
}
