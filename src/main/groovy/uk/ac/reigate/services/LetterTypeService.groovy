package uk.ac.reigate.services

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

import uk.ac.reigate.domain.ilp.LetterType
import uk.ac.reigate.exceptions.InvalidOperationException
import uk.ac.reigate.repositories.ilp.LetterTypeRepository
import uk.ac.reigate.utils.ValidationUtils

@Service
class LetterTypeService implements ICoreDataService<LetterType, Integer>{
    
    @Autowired
    LetterTypeRepository letterTypeRepository
    
    /**
     * Default NoArgs constructor    
     */
    LetterTypeService() {}
    
    /**
     * Autowired constructor
     * 
     * @param letterTypeRepository
     */
    LetterTypeService(LetterTypeRepository letterTypeRepository) {
        this.letterTypeRepository = letterTypeRepository
    }
    
    /**
     * Find an individual letterType using the letterTypes ID fields
     *
     * @param id the ID fields to search for
     * @return the LetterType object that matches the ID supplied, or null if not found
     */
    @Override
    @Transactional(readOnly = true)
    LetterType findById(Integer id) {
        return letterTypeRepository.findOne(id);
    }
    
    /**
     * Find all letterTypes
     *
     * @return a SearchResult set with the list of LetterTypes
     */
    @Override
    @Transactional(readOnly = true)
    List<LetterType> findAll() {
        return letterTypeRepository.findAll();
    }
    
    /**
     * @param id
     * @param type
     * @return
     */
    @Transactional
    public LetterType saveLetterType(Integer id, String type) {
        ValidationUtils.assertNotNull(type, "letterType is mandatory");
        LetterType letterType = null;
        if (id != null) {
            letterType = findById(id);
            letterType.setType(type);
            save(letterType);
        } else {
            letterType = save(new LetterType(letterType));
        }
        return letterType;
    }
    
    /**
     * This service method is used to save a complete LetterType object in the database
     *
     * @param letterType the new LetterType object to be saved
     * @return the saved version of the LetterType object
     */
    @Override
    @Transactional
    public LetterType save(LetterType letterType) {
        return letterTypeRepository.save(letterType)
    }
    
    /**
     * This service method is used to update an LetterType object in the database from a partial or complete LetterType object.
     *
     * @param letterType the partial or complete LetterType object to be saved
     * @return the saved version of the LetterType object
     */
    @Transactional
    public LetterType updateLetterType(LetterType letterType) {
        LetterType letterTypeToSave = findById(letterType.id)
        letterTypeToSave.type = letterType.type != null ? letterType.type : letterTypeToSave.type
        return save(letterTypeToSave)
    }
    
    /**
     * Saves a list of LetterType objects to the database
     *
     * @param letterTypes a list of LetterTypes to be saved to the database
     * @return the list of save LetterType objects
     */
    @Transactional
    public List<LetterType> saveLetterTypes(List<LetterType> letterTypes) {
        return letterTypes.collect { letterType -> save(letterType) };
    }
    
    /**
     * This methods throws an InvalidOperationException when called. LetterType should not be deleted.
     */
    @Override
    public void delete(LetterType obj) {
        throw new InvalidOperationException("LetterType should not be deleted")
    }
}
