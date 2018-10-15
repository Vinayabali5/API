package uk.ac.reigate.dto;


import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;

import groovy.transform.EqualsAndHashCode;
import uk.ac.reigate.domain.NoteType

/**
 *
 * JSON serializable DTO containing NoteType data
 *
 */
@JsonSerialize
@EqualsAndHashCode(includeFields=true)
public class NoteTypeDto implements Serializable {
    
    @JsonProperty
    private Integer id;
    
    @JsonProperty
    private String code;
    
    @JsonProperty
    private String description;
    
    /**
     * Default No Args constructor
     */
    public NoteTypeDto() {
    }
    
    /**
     * Constructor to create a NoteTypeDto object
     *
     * @param id the Id for the NoteType
     * @param code the code for the NoteType
     * @param description the description for the NoteType
     */
    public NoteTypeDto(Integer id, String code, String description) {
        this.id = id;
        this.code = code;
        this.description = description;
    }
    
    /**
     * Constructor to create a NoteTypeDto object from a NoteType object
     *
     * @param noteType the NoteType object to use for construction
     */
    NoteTypeDto(NoteType noteType) {
        this.id = noteType.id;
        this.code = noteType.code;
        this.description = noteType.description;
    }
    
    @Override
    public String toString() {
        return "NoteTypeDto [id=" + id + ", code=" + code + ", description=" + description + "]";
    }
    
    public static NoteTypeDto mapFromNoteTypeEntity(NoteType noteType) {
        return new NoteTypeDto(noteType);
    }
    
    public static List<NoteTypeDto> mapFromNoteTypesEntities(List<NoteType> noteTypes) {
        List<NoteTypeDto> output = noteTypes.collect { noteType ->  new NoteTypeDto(noteType) };
        return output
    }
    
    public static NoteType mapToNoteTypeEntity(NoteTypeDto noteTypeDto) {
        return new NoteType(noteTypeDto.id, noteTypeDto.code, noteTypeDto.description)
    }
}
