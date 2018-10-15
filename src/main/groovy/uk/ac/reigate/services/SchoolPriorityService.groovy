package uk.ac.reigate.services

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import org.springframework.security.access.prepost.PreAuthorize
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

import uk.ac.reigate.domain.lookup.SchoolPriority
import uk.ac.reigate.exceptions.InvalidOperationException
import uk.ac.reigate.model.PageInfo
import uk.ac.reigate.model.SearchResult
import uk.ac.reigate.repositories.lookup.SchoolPriorityRepository
import uk.ac.reigate.utils.ValidationUtils;

@Service
class SchoolPriorityService implements ICoreDataService<SchoolPriority, Integer> {
    
    @Autowired
    SchoolPriorityRepository schoolPriorityRepository
    
    /**
     * Default NoArgs constructor
     */
    SchoolPriorityService() {}
    
    /**
     * Autowired Constructor
     *
     * @param schoolPriorityRepository
     */
    SchoolPriorityService(SchoolPriorityRepository schoolPriorityRepository) {
        this.schoolPriorityRepository = schoolPriorityRepository;
    }
    
    /**
     * Find an individual schoolPriority using the schoolPriorities ID fields
     *
     * @param id the ID fields to search for
     * @return the SchoolPriority object that matches the ID supplied, or null if not found
     */
    @Override
    @Transactional(readOnly = true)
    SchoolPriority findById(Integer id) {
        return schoolPriorityRepository.findOne(id);
    }
    
    /**
     * Find all schoolPriorities
     * @return a SearchResult set with the list of SchoolPriorities
     */
    @Override
    @Transactional(readOnly = true)
    List<SchoolPriority> findAll() {
        return schoolPriorityRepository.findAll();
    }
    
    /**
     *
     * @param id
     * @param code
     * @param description
     * @return
     */
    @Transactional
    public SchoolPriority saveSchoolPriority(Integer id, String code, String description) {
        ValidationUtils.assertNotBlank(code, "code cannot be blank");
        ValidationUtils.assertNotNull(description, "description is mandatory");
        
        SchoolPriority schoolPriority = null;
        
        if (id != null) {
            schoolPriority = findById(id);
            
            schoolPriority.setCode(code);
            schoolPriority.setDescription(description);
            
            save(schoolPriority);
        } else {
            schoolPriority = save(new SchoolPriority(code, description));
        }
        
        return schoolPriority;
    }
    
    /**
     * This service method is used to save a complete SchoolPriority object in the database
     *
     * @param schoolPriority the new SchoolPriority object to be saved
     * @return the saved version of the SchoolPriority object
     */
    @Override
    @Transactional
    public SchoolPriority save(SchoolPriority schoolPriority) {
        return schoolPriorityRepository.save(schoolPriority)
    }
    
    /**
     * This service method is used to update an SchoolPriority object in the database from a partial or complete SchoolPriority object.
     *
     * @param schoolPriority the partial or complete SchoolPriority object to be saved
     * @return the saved version of the SchoolPriority object
     */
    @Transactional
    public SchoolPriority updateSchoolPriority(SchoolPriority schoolPriority) {
        SchoolPriority schoolPriorityToSave = findById(schoolPriority.id)
        schoolPriorityToSave.code = schoolPriority.code != null ? schoolPriority.code : schoolPriorityToSave.code
        schoolPriorityToSave.description = schoolPriority.description != null ? schoolPriority.description : schoolPriorityToSave.description
        return save(schoolPriorityToSave)
    }
    
    /**
     * Saves a list of SchoolPriority objects to the database
     *
     * @param schoolPrioritys a list of SchoolPrioritys to be saved to the database
     * @return the list of save SchoolPriority objects
     */
    @Transactional
    public List<SchoolPriority> saveSchoolPriorities(List<SchoolPriority> schoolPriorities) {
        return schoolPriorities.collect { schoolPriority -> save(schoolPriority) };
    }
    
    /**
     * This methods throws an InvalidOperationException when called. SchoolPriority should not be deleted.
     */
    @Override
    public void delete(SchoolPriority obj) {
        throw new InvalidOperationException("SchoolPriority should not be deleted")
    }
}
