package uk.ac.reigate.services

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

import uk.ac.reigate.domain.lookup.StaffType
import uk.ac.reigate.exceptions.InvalidOperationException
import uk.ac.reigate.repositories.lookup.StaffTypeRepository
import uk.ac.reigate.utils.ValidationUtils

@Service
class StaffTypeService implements ICoreDataService<StaffType, Integer>{
    
    @Autowired
    StaffTypeRepository staffTypeRepository
    
    /**
     * Default NoArgs constructor
     */
    StaffTypeService() {}
    
    /**
     * Autowired Constructor
     *
     * @param staffTypeRepository
     */
    StaffTypeService(StaffTypeRepository staffTypeRepository) {
        this.staffTypeRepository = staffTypeRepository;
    }
    
    /**
     * Find an individual staffType using the staffTypes ID fields
     *
     * @param id the ID fields to search for
     * @return the StaffType object that matches the ID supplied, or null if not found
     */
    @Override
    @Transactional(readOnly = true)
    StaffType findById(Integer id) {
        return staffTypeRepository.findOne(id);
    }
    
    /**
     * Find a single page of StaffType objects
     * @return a List of StaffTypes
     */
    @Override
    @Transactional(readOnly = true)
    List<StaffType> findAll() {
        return staffTypeRepository.findAll();
    }
    
    
    /**
     * @param id
     * @param code
     * @param description
     * @param needInitials
     * @return
     */
    @Transactional
    public StaffType saveStaffType(Integer id, String code, String description, Boolean needInitials) {
        ValidationUtils.assertNotBlank(code, "code cannot be blank");
        ValidationUtils.assertNotNull(description, "description is mandatory");
        
        StaffType staffType = null;
        
        if (id != null) {
            staffType = findById(id);
            
            staffType.setCode(code);
            staffType.setDescription(description);
            staffType.setNeedInitials(needInitials);
            
            save(staffType);
        } else {
            staffType = save(new StaffType(code, description, needInitials));
        }
        
        return staffType;
    }
    
    /**
     * This service method is used to save a complete StaffType object in the database
     *
     * @param staffType the new StaffType object to be saved
     * @return the saved version of the StaffType object
     */
    @Override
    @Transactional
    public StaffType save(StaffType staffType) {
        return staffTypeRepository.save(staffType)
    }
    
    /**
     * This service method is used to update an StaffType object in the database from a partial or complete StaffType object.
     *
     * @param staffType the partial or complete StaffType object to be saved
     * @return the saved version of the StaffType object
     */
    @Transactional
    public StaffType updateStaffType(StaffType staffType) {
        StaffType staffTypeToSave = findById(staffType.id)
        staffTypeToSave.code = staffType.code != null ? staffType.code : staffTypeToSave.code
        staffTypeToSave.description = staffType.description != null ? staffType.description : staffTypeToSave.description
        staffTypeToSave.needInitials = staffType.needInitials != null ? staffType.needInitials : staffTypeToSave.needInitials
        return save(staffTypeToSave)
    }
    
    /**
     * Saves a list of StaffType objects to the database
     *
     * @param staffTypes a list of StaffTypes to be saved to the database
     * @return the list of save StaffType objects
     */
    @Transactional
    public List<StaffType> saveStaffTypes(List<StaffType> staffTypes) {
        return staffTypes.collect { staffType -> save(staffType) };
    }
    
    /**
     * This methods throws an InvalidOperationException when called. StaffType should not be deleted.
     */
    @Override
    public void delete(StaffType obj) {
        throw new InvalidOperationException("StaffType should not be deleted")
    }
}
