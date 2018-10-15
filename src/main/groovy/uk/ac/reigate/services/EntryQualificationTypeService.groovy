package uk.ac.reigate.services

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

import uk.ac.reigate.domain.academic.EntryQualificationType
import uk.ac.reigate.exceptions.InvalidOperationException
import uk.ac.reigate.repositories.academic.EntryQualificationTypeRepository
import uk.ac.reigate.utils.ValidationUtils

@Service
class EntryQualificationTypeService implements ICoreDataService<EntryQualificationType, Integer> {
    
    @Autowired
    EntryQualificationTypeRepository entryQualificationTypeRepository
    
    /**
     * Default NoArgs constructor
     */
    EntryQualificationTypeService() {}
    
    /**
     * Autowired Constructor
     *
     * @param entryQualificationTypeRepository
     */
    EntryQualificationTypeService(EntryQualificationTypeRepository entryQualificationTypeRepository) {
        this.entryQualificationTypeRepository = entryQualificationTypeRepository;
    }
    
    /**
     * Find an individual entryQualificationType using the entryQualificationTypes ID fields
     *
     * @param id the ID fields to search for
     * @return the EntryQualificationType object that matches the ID supplied, or null if not found
     */
    @Override
    @Transactional(readOnly = true)
    EntryQualificationType findById(Integer id) {
        return entryQualificationTypeRepository.findOne(id);
    }
    
    /**
     * Find all entryQualificationTypes
     *
     * @return a SearchResult set with the list of EntryQualificationTypes
     */
    @Override
    @Transactional(readOnly = true)
    List<EntryQualificationType> findAll() {
        return entryQualificationTypeRepository.findAll();
    }
    
    /**
     * @param id
     * @param code
     * @param description
     * @param size
     * @return
     */
    @Transactional
    public EntryQualificationType saveEntryQualificationType(Integer id, String code, String description, float size) {
        ValidationUtils.assertNotBlank(code, "code cannot be blank");
        ValidationUtils.assertNotNull(description, "description is mandatory");
        
        EntryQualificationType entryQualificationType = null;
        
        if (id != null) {
            entryQualificationType = findById(id);
            
            entryQualificationType.setCode(code);
            entryQualificationType.setDescription(description);
            entryQualificationType.setSize(size);
            
            save(entryQualificationType);
        } else {
            entryQualificationType = save(new EntryQualificationType(code, description, size));
        }
        
        return entryQualificationType;
    }
    
    /**
     * This service method is used to save a complete EntryQualificationType object in the database
     *
     * @param entryQualificationType the new EntryQualificationType object to be saved
     * @return the saved version of the EntryQualificationType object
     */
    @Override
    @Transactional
    public EntryQualificationType save(EntryQualificationType entryQualificationType) {
        return entryQualificationTypeRepository.save(entryQualificationType)
    }
    
    /**
     * This service method is used to update an EntryQualificationType object in the database from a partial or complete EntryQualificationType object.
     *
     * @param entryQualificationType the partial or complete EntryQualificationType object to be saved
     * @return the saved version of the EntryQualificationType object
     */
    @Transactional
    public EntryQualificationType updateEntryQualificationType(EntryQualificationType entryQualificationType) {
        EntryQualificationType entryQualificationTypeToSave = findById(entryQualificationType.id);
        entryQualificationTypeToSave.code = entryQualificationType.code != null ? entryQualificationType.code : entryQualificationTypeToSave.code
        entryQualificationTypeToSave.description = entryQualificationType.description != null ? entryQualificationType.description : entryQualificationTypeToSave.description
        entryQualificationTypeToSave.size = entryQualificationType.size != null ? entryQualificationType.size : entryQualificationTypeToSave.size
        return save(entryQualificationTypeToSave)
    }
    
    /**
     * Saves a list of EntryQualificationType objects to the database
     *
     * @param entryQualificationTypes a list of EntryQualificationTypes to be saved to the database
     * @return the list of save EntryQualificationType objects
     */
    @Transactional
    public List<EntryQualificationType> saveEntryQualificationTypes(List<EntryQualificationType> entryQualificationTypes) {
        return entryQualificationTypes.collect { entryQualificationType -> save(entryQualificationType) };
    }
    
    /**
     * This methods throws an InvalidOperationException when called. EntryQualificationType should not be deleted.
     */
    @Override
    public void delete(EntryQualificationType obj) {
        throw new InvalidOperationException("EntryQualificationType should not be deleted")
    }
}
