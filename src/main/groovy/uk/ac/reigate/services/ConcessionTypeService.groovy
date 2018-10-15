package uk.ac.reigate.services

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

import uk.ac.reigate.domain.learning_support.ConcessionType
import uk.ac.reigate.exceptions.InvalidOperationException
import uk.ac.reigate.repositories.learning_support.ConcessionTypeRepository
import uk.ac.reigate.utils.ValidationUtils

@Service
class ConcessionTypeService implements ICoreDataService<ConcessionType, Integer>{
    
    @Autowired
    ConcessionTypeRepository concessionTypeRepository
    
    /**
     * Default NoArgs constructor
     */
    ConcessionTypeService() {}
    
    /**
     * Autowired Constructor
     *
     * @param concessionTypeRepository
     */
    ConcessionTypeService(ConcessionTypeRepository concessionTypeRepository) {
        this.concessionTypeRepository = concessionTypeRepository
    }
    
    /**
     * Find an individual concessionType using the concessionTypes ID fields
     *
     * @param id the ID fields to search for
     * @return the ConcessionType object that matches the ID supplied, or null if not found
     */
    @Override
    @Transactional(readOnly = true)
    ConcessionType findById(Integer id) {
        return concessionTypeRepository.findOne(id);
    }
    
    /**
     * Find a single page of ConcessionType objects
     * @return a SearchResult set with the list of ConcessionTypes
     */
    @Override
    @Transactional(readOnly = true)
    List<ConcessionType> findAll() {
        return concessionTypeRepository.findAll();
    }
    
    /**
     *
     * @param id
     * @param code
     * @param description
     * @return
     */
    @Transactional
    public ConcessionType saveConcessionType(Integer id, String code, String description) {
        ValidationUtils.assertNotBlank(code, "code cannot be blank");
        ValidationUtils.assertNotNull(description, "description is mandatory");
        
        ConcessionType concessionType = null;
        
        if (id != null) {
            concessionType = findById(id);
            concessionType.setCode(code);
            concessionType.setDescription(description);
            save(concessionType);
        } else {
            concessionType = save(new ConcessionType(code, description));
        }
        
        return concessionType;
    }
    
    /**
     * This service method is used to save a complete ConcessionType object in the database
     *
     * @param concessionType the new ConcessionType object to be saved
     * @return the saved version of the ConcessionType object
     */
    @Override
    @Transactional
    public ConcessionType save(ConcessionType concessionType) {
        return concessionTypeRepository.save(concessionType)
    }
    
    /**
     * This service method is used to update an ConcessionType object in the database from a partial or complete ConcessionType object.
     *
     * @param concessionType the partial or complete ConcessionType object to be saved
     * @return the saved version of the ConcessionType object
     */
    @Transactional
    public ConcessionType updateConcessionType(ConcessionType concessionType) {
        ConcessionType concessionTypeToSave = concessionTypeRepository.findOne(concessionType.id)
        concessionTypeToSave.code = concessionType.code != null ? concessionType.code : concessionTypeToSave.code
        concessionTypeToSave.description = concessionType.description != null ? concessionType.description : concessionTypeToSave.description
        return save(concessionTypeToSave)
    }
    
    /**
     * Saves a list of ConcessionType objects to the database
     *
     * @param concessionTypes a list of ConcessionTypes to be saved to the database
     * @return the list of save ConcessionType objects
     */
    @Transactional
    public List<ConcessionType> saveConcessionTypes(List<ConcessionType> concessionTypes) {
        return concessionTypes.collect { concessionType -> save(concessionType) };
    }
    
    /**
     * This methods throws an InvalidOperationException when called. ConcessionType should not be deleted.
     */
    @Override
    public void delete(ConcessionType obj) {
        throw new InvalidOperationException("ConcessionType should not be deleted")
    }
}
