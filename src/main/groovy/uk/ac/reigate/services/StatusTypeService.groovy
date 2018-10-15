package uk.ac.reigate.services

//import static org.springframework.util.Assert

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import org.springframework.data.domain.Sort;
import org.springframework.security.access.prepost.PreAuthorize
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

import uk.ac.reigate.domain.exam.StatusType
import uk.ac.reigate.exceptions.InvalidOperationException
import uk.ac.reigate.model.PageInfo
import uk.ac.reigate.model.SearchResult
import uk.ac.reigate.repositories.exam.StatusTypeRepository
import uk.ac.reigate.utils.ValidationUtils;

@Service
class StatusTypeService implements ICoreDataService<StatusType, Integer>{
    
    @Autowired
    StatusTypeRepository statusTypeRepository
    
    /**
     * Default NoArgs constructor
     */
    StatusTypeService() {}
    
    /**
     * Autowired Constructor
     *
     * @param statusTypeRepository
     */
    StatusTypeService(StatusTypeRepository statusTypeRepository) {
        this.statusTypeRepository = statusTypeRepository;
    }
    
    /**
     * Find an individual statusType using the statusTypes ID fields
     *
     * @param id the ID fields to search for
     * @return the StatusType object that matches the ID supplied, or null if not found
     */
    @Override
    @Transactional(readOnly = true)
    StatusType findById(Integer id) {
        return statusTypeRepository.findOne(id);
    }
    
    /**
     * Find all statusTypes
     *
     * @return a List of StatusTypes
     */
    @Override
    @Transactional(readOnly = true)
    List<StatusType> findAll() {
        return statusTypeRepository.findAll();
    }
    
    
    /**
     * @param id
     * @param code
     * @param description
     * @return
     */
    @Transactional
    public StatusType saveStatusType(Integer id, String code, String description) {
        ValidationUtils.assertNotBlank(code, "code cannot be blank");
        ValidationUtils.assertNotNull(description, "description is mandatory");
        
        StatusType statusType = null;
        
        if (id != null) {
            statusType = findById(id);
            statusType.setCode(code);
            statusType.setDescription(description);
            save(statusType);
        } else {
            statusType = save(new StatusType(code, description));
        }
        
        return statusType;
    }
    
    /**
     * This service method is used to save a complete StatusType object in the database
     *
     * @param statusType the new StatusType object to be saved
     * @return the saved version of the StatusType object
     */
    @Override
    @Transactional
    public StatusType save(StatusType statusType) {
        return statusTypeRepository.save(statusType)
    }
    
    /**
     * This service method is used to update an StatusType object in the database from a partial or complete StatusType object.
     *
     * @param statusType the partial or complete StatusType object to be saved
     * @return the saved version of the StatusType object
     */
    @Transactional
    public StatusType updateStatusType(StatusType statusType) {
        StatusType statusTypeToSave = findById(statusType.id);
        statusTypeToSave.code = statusType.code != null ? statusType.code : statusTypeToSave.code
        statusTypeToSave.description = statusType.description != null ? statusType.description : statusTypeToSave.description
        return save(statusTypeToSave)
    }
    
    /**
     * Saves a list of StatusType objects to the database
     *
     * @param statusTypes a list of StatusTypes to be saved to the database
     * @return the list of save StatusType objects
     */
    @Transactional
    public List<StatusType> saveStatusTypes(List<StatusType> statusTypes) {
        return statusTypes.collect { statusType -> save(statusType) };
    }
    
    /**
     * This methods throws an InvalidOperationException when called. StatusType should not be deleted.
     */
    @Override
    public void delete(StatusType obj) {
        throw new InvalidOperationException("StatusType should not be deleted")
        
    }
    
    
    
}
