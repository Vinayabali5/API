package uk.ac.reigate.services

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

import uk.ac.reigate.domain.ilp.CorrespondenceType
import uk.ac.reigate.exceptions.InvalidOperationException
import uk.ac.reigate.repositories.ilp.CorrespondenceTypeRepository
import uk.ac.reigate.utils.ValidationUtils

@Service
class CorrespondenceTypeService implements ICoreDataService<CorrespondenceType, Integer>{
    
    @Autowired
    CorrespondenceTypeRepository correspondenceTypeRepository
    
    /**
     * Default NoArgs constructor    
     */
    CorrespondenceTypeService() {}
    
    /**
     * Autowired constructor
     * 
     * @param correspondenceTypeRepository
     */
    CorrespondenceTypeService(CorrespondenceTypeRepository correspondenceTypeRepository) {
        this.correspondenceTypeRepository = correspondenceTypeRepository
    }
    
    /**
     * Find an individual correspondenceType using the correspondenceTypes ID fields
     *
     * @param id the ID fields to search for
     * @return the CorrespondenceType object that matches the ID supplied, or null if not found
     */
    @Override
    @Transactional(readOnly = true)
    CorrespondenceType findById(Integer id) {
        return correspondenceTypeRepository.findOne(id);
    }
    
    /**
     * Find all correspondenceTypes
     *
     * @return a SearchResult set with the list of CorrespondenceTypes
     */
    @Override
    @Transactional(readOnly = true)
    List<CorrespondenceType> findAll() {
        return correspondenceTypeRepository.findAll();
    }
    
    
    /**
     * @param id
     * @param type
     * @return
     */
    @Transactional
    public CorrespondenceType saveCorrespondenceType(Integer id, String type) {
        ValidationUtils.assertNotNull(type, "correspondenceType is mandatory");
        
        CorrespondenceType correspondenceType = null;
        
        if (id != null) {
            correspondenceType = findById(id);
            
            correspondenceType.setType(type);
            
            save(correspondenceType);
        } else {
            correspondenceType = save(new CorrespondenceType(correspondenceType));
        }
        
        return correspondenceType;
    }
    
    /**
     * This service method is used to save a complete CorrespondenceType object in the database
     *
     * @param correspondenceType the new CorrespondenceType object to be saved
     * @return the saved version of the CorrespondenceType object
     */
    @Override
    @Transactional
    public CorrespondenceType save(CorrespondenceType correspondenceType) {
        return correspondenceTypeRepository.save(correspondenceType)
    }
    
    /**
     * This service method is used to update an CorrespondenceType object in the database from a partial or complete CorrespondenceType object.
     *
     * @param correspondenceType the partial or complete CorrespondenceType object to be saved
     * @return the saved version of the CorrespondenceType object
     */
    @Transactional
    public CorrespondenceType updateCorrespondenceType(CorrespondenceType correspondenceType) {
        CorrespondenceType correspondenceTypeToSave = findById(correspondenceType.id)
        correspondenceTypeToSave.type = correspondenceType.type != null ? correspondenceType.type : correspondenceTypeToSave.type
        return save(correspondenceTypeToSave)
    }
    
    /**
     * Saves a list of CorrespondenceType objects to the database
     *
     * @param correspondenceTypes a list of CorrespondenceTypes to be saved to the database
     * @return the list of save CorrespondenceType objects
     */
    @Transactional
    public List<CorrespondenceType> saveCorrespondenceTypes(List<CorrespondenceType> correspondenceTypes) {
        return correspondenceTypes.collect { correspondenceType -> save(correspondenceType) };
    }
    
    /**
     * This methods throws an InvalidOperationException when called. CorrespondenceType should not be deleted.
     */
    @Override
    public void delete(CorrespondenceType obj) {
        throw new InvalidOperationException("CorrespondenceType should not be deleted")
    }
}
