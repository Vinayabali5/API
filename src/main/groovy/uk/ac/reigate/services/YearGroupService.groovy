package uk.ac.reigate.services

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

import uk.ac.reigate.domain.lookup.YearGroup
import uk.ac.reigate.exceptions.InvalidOperationException
import uk.ac.reigate.repositories.lookup.YearGroupRepository
import uk.ac.reigate.utils.ValidationUtils

@Service
class YearGroupService implements ICoreDataService<YearGroup, Integer>{
    
    @Autowired
    YearGroupRepository yearGroupRepository
    
    /**
     * Default NoArgs constructor
     */
    YearGroupService() {}
    
    /**
     * Autowired Constructor
     *
     * @param yearGroupRepository
     */
    YearGroupService(YearGroupRepository yearGroupRepository) {
        this.yearGroupRepository = yearGroupRepository;
    }
    
    /**
     * Find an individual yearGroup using the yearGroups ID fields
     *
     * @param id the ID fields to search for
     * @return the YearGroup object that matches the ID supplied, or null if not found
     */
    @Override
    @Transactional(readOnly = true)
    YearGroup findById(Integer id) {
        return yearGroupRepository.findOne(id);
    }
    
    /**
     * Find a single page of YearGroup objects
     * @return a SearchResult set with the list of YearGroups
     */
    @Override
    @Transactional(readOnly = true)
    List<YearGroup> findAll() {
        return yearGroupRepository.findAll();
    }
    
    /**
     * @param id
     * @param code
     * @param description
     * @return
     */
    @Transactional
    public YearGroup saveYearGroup(Integer id, String code, String description) {
        ValidationUtils.assertNotBlank(code, "code cannot be blank");
        ValidationUtils.assertNotNull(description, "description is mandatory");
        
        YearGroup yearGroup = null;
        
        if (id != null) {
            yearGroup = findById(id);
            
            yearGroup.setCode(code);
            yearGroup.setDescription(description);
            
            save(yearGroup);
        } else {
            yearGroup = save(new YearGroup(code, description));
        }
        
        return yearGroup;
    }
    
    /**
     * This service method is used to save a complete YearGroup object in the database
     *
     * @param yearGroup the new YearGroup object to be saved
     * @return the saved version of the YearGroup object
     */
    @Override
    @Transactional
    public YearGroup save(YearGroup yearGroup) {
        return yearGroupRepository.save(yearGroup)
    }
    
    /**
     * This service method is used to update an YearGroup object in the database from a partial or complete YearGroup object.
     *
     * @param yearGroup the partial or complete YearGroup object to be saved
     * @return the saved version of the YearGroup object
     */
    @Transactional
    public YearGroup updateYearGroup(YearGroup yearGroup) {
        YearGroup yearGroupToSave = findById(yearGroup.id)
        yearGroupToSave.code = yearGroup.code != null ? yearGroup.code : yearGroupToSave.code
        yearGroupToSave.description = yearGroup.description != null ? yearGroup.description : yearGroupToSave.description
        return save(yearGroupToSave)
    }
    
    /**
     * Saves a list of YearGroup objects to the database
     *
     * @param yearGroups a list of YearGroups to be saved to the database
     * @return the list of save YearGroup objects
     */
    @Transactional
    public List<YearGroup> saveYearGroups(List<YearGroup> yearGroups) {
        return yearGroups.collect { yearGroup -> save(yearGroup) };
    }
    
    /**
     * This methods throws an InvalidOperationException when called. YearGroup should not be deleted.
     */
    @Override
    public void delete(YearGroup obj) {
        throw new InvalidOperationException("YearGroup should not be deleted")
    }
}
