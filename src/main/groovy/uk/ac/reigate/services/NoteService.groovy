package uk.ac.reigate.services

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.security.access.prepost.PreAuthorize
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

import uk.ac.reigate.domain.Note
import uk.ac.reigate.domain.NoteType
import uk.ac.reigate.domain.Person
import uk.ac.reigate.dto.NoteDto
import uk.ac.reigate.exceptions.InvalidOperationException
import uk.ac.reigate.repositories.NoteRepository
import uk.ac.reigate.utils.ValidationUtils

@Service
class NoteService implements ICoreDataService<Note, Integer>{
    
    @Autowired
    NoteRepository noteRepository
    
    @Autowired
    PersonService personService
    
    @Autowired
    NoteTypeService noteTypeService
    
    /**
     * Default NoArgs constructor
     */
    NoteService() {}
    
    /**
     * Autowired Constructor
     *
     * @param noteRepository
     */
    NoteService(NoteRepository noteRepository) {
        this.noteRepository = noteRepository;
    }
    
    /**
     * Find an individual note using the notes ID fields
     *
     * @param id the ID fields to search for
     * @return the Note object that matches the ID supplied, or null if not found
     */
    @Override
    @Transactional(readOnly = true)
    Note findById(Integer id) {
        return noteRepository.findOne(id);
    }
    
    /**
     * Find all notes
     *
     * @return a SearchResult set with the list of Notes
     */
    @Override
    @Transactional(readOnly = true)
    List<Note> findAll() {
        return noteRepository.findAll();
    }
    
    /**
     * @param id
     * @param person
     * @param note
     * @param type
     * @return
     */
    @Transactional
    public Note saveNote(Integer id, Person person, String note, NoteType type) {
        ValidationUtils.assertNotBlank(person, "person cannot be blank");
        ValidationUtils.assertNotBlank(note, "note is mandatory");
        ValidationUtils.assertNotBlank(type, "type is mandatory");
        Note notee = null;
        if (notee != null) {
            notee = findById(id);
            notee.setPerson(person);
            notee.setNote(note);
            notee.setType(type);
            save(notee);
        } else {
            notee = save(new Note(person,note,type));
        }
        return notee;
    }
    
    /**
     * This service method is used to save a complete Note object in the database
     *
     * @param note the new Note object to be saved
     * @return the saved version of the Note object
     */
    @Override
    @Transactional
    public Note save(Note note) {
        return noteRepository.save(note)
    }
    
    /**
     * Saves a list of Note objects to the database
     *
     * @param notes a list of Notes to be saved to the database
     * @return the list of save Note objects
     */
    @Transactional
    public List<Note> saveNotes(List<Note> notes) {
        return notes.collect { note -> save( note) };
    }
    
    /**
     * This methods throws an InvalidOperationException when called. Note should not be deleted.
     */
    @Override
    public void delete(Note obj) {
        throw new InvalidOperationException("Note should not be deleted")
    }
    
    
    /** Returns Notes for a given personId
     * @param personId
     * @return
     */
    @PreAuthorize("@securityChecker.checkReader(authentication)")
    public List<Note> getNotesByPersonId(Integer personId){
        return noteRepository.findByPerson_Id(personId)
    }
    
    /**
     * This method is used to create a new Note object from a NoteDto object. 
     * 
     * @param noteDto a NoteDto object to create a new Note from 
     * @return the save Note object
     */
    @PreAuthorize("@securityChecker.checkWriter(authentication)")
    public Note createNote(NoteDto noteDto){
        Note note = new Note()
        note.person = personService.findById(noteDto.personId)
        note.note = noteDto.note
        note.type = noteTypeService.findById(noteDto.typeId)
        return save(note)
    }
}
