package uk.ac.reigate.services

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

import uk.ac.reigate.domain.learning_support.SupportType
import uk.ac.reigate.exceptions.InvalidOperationException
import uk.ac.reigate.repositories.learning_support.SupportTypeRepository
import uk.ac.reigate.utils.ValidationUtils

@Service
class SupportTypeService implements ICoreDataService<SupportType, Integer>{
    
    @Autowired
    SupportTypeRepository supportTypeRepository
    
    /**
     * Default NoArgs constructor
     */
    SupportTypeService() {}
    
    /**
     * Autowired Constructor
     *
     * @param supportTypeRepository
     */
    SupportTypeService(SupportTypeRepository supportTypeRepository) {
        this.supportTypeRepository = supportTypeRepository
    }
    
    /**
     * Find an individual supportType using the supportTypes ID fields
     *
     * @param id the ID fields to search for
     * @return the SupportType object that matches the ID supplied, or null if not found
     */
    @Override
    @Transactional(readOnly = true)
    SupportType findById(Integer id) {
        return supportTypeRepository.findOne(id);
    }
    
    /**
     * Find a single page of SupportType objects
     * @return a List of SupportTypes
     */
    @Override
    @Transactional(readOnly = true)
    List<SupportType> findAll() {
        return supportTypeRepository.findAll();
    }
    
    /**
     *
     * @param id
     * @param support
     * @param description
     * @return
     */
    @Transactional
    public SupportType saveSupportType(Integer id, String support) {
        ValidationUtils.assertNotBlank(support, "support cannot be blank");
        
        SupportType supportType = null;
        
        if (id != null) {
            supportType = findById(id);
            
            supportType.setSupport(support);
            
            save(supportType);
        } else {
            supportType = save(new SupportType(support));
        }
        
        return supportType;
    }
    
    /**
     * This service method is used to save a complete SupportType object in the database
     *
     * @param supportType the new SupportType object to be saved
     * @return the saved version of the SupportType object
     */
    @Override
    @Transactional
    public SupportType save(SupportType supportType) {
        return supportTypeRepository.save(supportType)
    }
    
    /**
     * This service method is used to update an SupportType object in the database from a partial or complete SupportType object.
     *
     * @param supportType the partial or complete SupportType object to be saved
     * @return the saved version of the SupportType object
     */
    @Transactional
    public SupportType updateSupportType(SupportType supportType) {
        SupportType supportTypeToSave = findById(supportType.id)
        supportTypeToSave.support = supportType.support != null ? supportType.support : supportTypeToSave.support
        return save(supportTypeToSave)
    }
    
    /**
     * Saves a list of SupportType objects to the database
     *
     * @param supportTypes a list of SupportTypes to be saved to the database
     * @return the list of save SupportType objects
     */
    @Transactional
    public List<SupportType> saveSupportTypes(List<SupportType> supportTypes) {
        return supportTypes.collect { supportType -> save(supportType) };
    }
    
    /**
     * This methods throws an InvalidOperationException when called. SupportType should not be deleted.
     */
    @Override
    public void delete(SupportType obj) {
        throw new InvalidOperationException("SupportType should not be deleted")
    }
}
