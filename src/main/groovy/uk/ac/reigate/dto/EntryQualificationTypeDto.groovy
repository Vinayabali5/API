package uk.ac.reigate.dto;


import groovy.transform.EqualsAndHashCode
import uk.ac.reigate.domain.academic.EntryQualificationType

import com.fasterxml.jackson.annotation.JsonProperty
import com.fasterxml.jackson.databind.annotation.JsonSerialize

/**
 *
 * JSON serializable DTO containing EntryQualificationType data
 *
 */
@JsonSerialize
@EqualsAndHashCode(includeFields=true)
public class EntryQualificationTypeDto implements Serializable {
    
    @JsonProperty
    private Integer id;
    
    @JsonProperty
    private String code;
    
    @JsonProperty
    private String description;
    
    @JsonProperty
    private float size;
    
    /**
     * Default No Args constructor
     */
    public EntryQualificationTypeDto() {
    }
    
    /**
     * Constructor to create a EntryQualificationTypeDto object
     *
     * @param id the Id for the EntryQualificationType
     * @param code the code for the EntryQualificationType
     * @param description the description for the EntryQualificationType
     */
    public EntryQualificationTypeDto(Integer id, String code, String description, float size) {
        this.id = id;
        this.code = code;
        this.description = description;
        this.size = size;
    }
    
    /**
     * Constructor to create an EntryQualificationTypeDto object from a EntryQualificationType object
     *
     * @param entryQualificationType the EntryQualificationType object to use for construction
     */
    EntryQualificationTypeDto(EntryQualificationType entryQualificationType) {
        this.id = entryQualificationType.id;
        this.code = entryQualificationType.code;
        this.description = entryQualificationType.description;
        this.size = entryQualificationType.size;
    }
    
    @Override
    public String toString() {
        return "EntryQualificationTypeDto [id=" + id + ", code=" + code + ", description=" + description + ", size=" + size +"]";
    }
    
    public static EntryQualificationTypeDto mapFromEntryQualificationTypeEntity(EntryQualificationType entryQualificationType) {
        return new EntryQualificationTypeDto(entryQualificationType);
    }
    
    public static List<EntryQualificationTypeDto> mapFromEntryQualificationTypesEntities(List<EntryQualificationType> entryQualificationTypes) {
        List<EntryQualificationTypeDto> output = entryQualificationTypes.collect { entryQualificationType ->  new EntryQualificationTypeDto(entryQualificationType) };
        return output
    }
    
    public static EntryQualificationType mapToEntryQualificationTypeEntity(EntryQualificationTypeDto entryQualificationTypeDto) {
        return new EntryQualificationType(entryQualificationTypeDto.id, entryQualificationTypeDto.code, entryQualificationTypeDto.description, entryQualificationTypeDto.size)
    }
}
