package uk.ac.reigate.services

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

import uk.ac.reigate.domain.NoteType
import uk.ac.reigate.exceptions.InvalidOperationException
import uk.ac.reigate.repositories.NoteTypeRepository
import uk.ac.reigate.utils.ValidationUtils

@Service
class NoteTypeService implements ICoreDataService<NoteType, Integer>{
    
    @Autowired
    NoteTypeRepository noteTypeRepository
    
    /**
     * Default NoArgs constructor
     */
    NoteTypeService() {}
    
    /**
     * Autowired Constructor
     *
     * @param noteTypeRepository
     */
    NoteTypeService(NoteTypeRepository noteTypeRepository) {
        this.noteTypeRepository = noteTypeRepository;
    }
    
    /**
     * Find an individual NoteType using the NoteType ID fields
     *
     * @param id the ID fields to search for
     * @return the NoteType object that matches the ID supplied, or null if not found
     */
    @Override
    @Transactional(readOnly = true)
    NoteType findById(Integer id) {
        return noteTypeRepository.findOne(id);
    }
    
    /**
     * Find a single page of NoteType objects
     * @return a List of NoteTypes
     */
    @Override
    @Transactional(readOnly = true)
    List<NoteType> findAll() {
        return noteTypeRepository.findAll();
    }
    
    /**
     *
     * @param id
     * @param code
     * @param description
     * @return
     */
    @Transactional
    public NoteType saveNoteType(Integer id, String code, String description) {
        ValidationUtils.assertNotBlank(code, "code cannot be blank");
        ValidationUtils.assertNotNull(description, "description is mandatory");
        
        NoteType noteType = null;
        
        if (id != null) {
            noteType = findById(id);
            
            noteType.setCode(code);
            noteType.setDescription(description);
            
            save(noteType);
        } else {
            noteType = save(new NoteType(code, description));
        }
        
        return noteType;
    }
    
    /**
     * This service method is used to save a complete NoteType object in the database
     *
     * @param noteType the new NoteType object to be saved
     * @return the saved version of the NoteType object
     */
    @Override
    @Transactional
    public NoteType save(NoteType noteType) {
        return noteTypeRepository.save(noteType)
    }
    
    /**
     * This service method is used to update an NoteType object in the database from a partial or complete NoteType object.
     *
     * @param noteType the partial or complete NoteType object to be saved
     * @return the saved version of the NoteType object
     */
    @Transactional
    public NoteType updateNoteType(NoteType noteType) {
        NoteType noteTypeToSave = findById(noteType.id)
        noteTypeToSave.code = noteType.code != null ? noteType.code : noteTypeToSave.code
        noteTypeToSave.description = noteType.description != null ? noteType.description : noteTypeToSave.description
        return save(noteTypeToSave)
    }
    
    /**
     * Saves a list of NoteType objects to the database
     *
     * @param noteTypes a list of NoteTypes to be saved to the database
     * @return the list of save NoteType objects
     */
    @Transactional
    public List<NoteType> saveNoteTypes(List<NoteType> noteTypes) {
        return noteTypes.collect { noteType -> save(noteType) };
    }
    
    /**
     * This methods throws an InvalidOperationException when called. NoteType should not be deleted.
     */
    @Override
    public void delete(NoteType obj) {
        throw new InvalidOperationException("NoteType should not be deleted")
    }
}
