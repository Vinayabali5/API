package uk.ac.reigate.dto;

import groovy.transform.EqualsAndHashCode;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;

import uk.ac.reigate.domain.Note

/**
 *
 * JSON serializable DTO containing Note data
 *
 */
@JsonSerialize
@EqualsAndHashCode(includeFields=true)
public class NoteDto implements Serializable {
    
    @JsonProperty
    private Integer id;
    
    @JsonProperty
    private Integer personId;
    
    @JsonProperty
    private String note;
    
    @JsonProperty
    private Integer typeId;
    
    @JsonProperty
    private String _typeDescription;
    
    /**
     * Default No Args constructor
     */
    public NoteDto() {
    }
    
    /**
     * Constructor to create a NoteDto object from a Note object
     *
     * @param note the Note object to use for construction
     */
    NoteDto(Note note){
        this.id = note.id;
        this.personId = note.person != null ? note.person.id : null;
        this.note = note.note;
        this.typeId = note.type != null ? note.type.id : null;
        this._typeDescription = note.type != null ? note.type.description : null;
    }
    
    @Override
    public String toString() {
        return "NoteDto [id=" + id + ", person=" + personId + ", note=" + note + ", type=" + typeId + "]";
    }
    
    public static NoteDto mapFromEntity(Note note) {
        return new NoteDto(note)
    }
    
    public static List<NoteDto> mapFromList(List<Note> notes) {
        return notes.collect { note ->  new NoteDto(note) };
    }
    
    public static Note mapToNoteEntity(NoteDto noteDto, person, type) {
        return new Note(noteDto.id, person, noteDto.note, type)
    }
}
