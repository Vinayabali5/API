package uk.ac.reigate.services

//import static org.springframework.util.Assert

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import org.springframework.security.access.prepost.PreAuthorize
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

import uk.ac.reigate.domain.academic.SpecialCategory
import uk.ac.reigate.exceptions.InvalidOperationException
import uk.ac.reigate.model.PageInfo
import uk.ac.reigate.model.SearchResult
import uk.ac.reigate.repositories.academic.SpecialCategoryRepository
import uk.ac.reigate.utils.ValidationUtils;

@Service
class SpecialCategoryService implements ICoreDataService<SpecialCategory, Integer>{
    
    @Autowired
    SpecialCategoryRepository specialCategoryRepository
    
    /**
     * Default NoArgs constructor
     */
    SpecialCategoryService() {}
    
    /**
     * Autowired Constructor
     *
     * @param specialCategoryRepository
     */
    SpecialCategoryService(SpecialCategoryRepository specialCategoryRepository) {
        this.specialCategoryRepository = specialCategoryRepository;
    }
    
    /**
     * Find an individual specialCategory using the specialCategories ID fields
     *
     * @param id the ID fields to search for
     * @return the SpecialCategory object that matches the ID supplied, or null if not found
     */
    @Override
    @Transactional(readOnly = true)
    SpecialCategory findById(Integer id) {
        return specialCategoryRepository.findOne(id);
    }
    
    /**
     * Find a single page of SpecialCategory objects
     * @return a SearchResult set with the list of SpecialCategories
     */
    @Override
    @Transactional(readOnly = true)
    List<SpecialCategory> findAll() {
        return specialCategoryRepository.findAll();
    }
    
    
    /**
     * @param id
     * @param code
     * @param description
     * @param details
     * @param priority
     * @return
     */
    @Transactional
    public SpecialCategory saveSpecialCategory(Integer id, String code, String description, String details, Integer priority) {
        ValidationUtils.assertNotBlank(code, "code cannot be blank");
        ValidationUtils.assertNotNull(description, "description is mandatory");
        
        SpecialCategory specialCategory = null;
        
        if (id != null) {
            specialCategory = findById(id);
            
            specialCategory.setCode(code);
            specialCategory.setDescription(description);
            specialCategory.setDetails(details);
            specialCategory.setPriority(priority);
            save(specialCategory);
        } else {
            specialCategory = save(new SpecialCategory(code, description, details, priority));
        }
        
        return specialCategory;
    }
    
    /**
     * This service method is used to save a complete SpecialCategory object in the database
     *
     * @param specialCategory the new SpecialCategory object to be saved
     * @return the saved version of the SpecialCategory object
     */
    @Override
    @Transactional
    public SpecialCategory save(SpecialCategory specialCategory) {
        return specialCategoryRepository.save(specialCategory)
    }
    
    /**
     * This service method is used to update an SpecialCategory object in the database from a partial or complete SpecialCategory object.
     *
     * @param specialCategory the partial or complete SpecialCategory object to be saved
     * @return the saved version of the SpecialCategory object
     */
    
    @Transactional
    public SpecialCategory updateSpecialCategory(SpecialCategory specialCategory) {
        SpecialCategory specialCategoryToSave = findById(specialCategory.id)
        specialCategoryToSave.code = specialCategory.code != null ? specialCategory.code : specialCategoryToSave.code
        specialCategoryToSave.description = specialCategory.description != null ? specialCategory.description : specialCategoryToSave.description
        specialCategoryToSave.details = specialCategory.details != null ? specialCategory.details : specialCategoryToSave.details
        specialCategoryToSave.priority = specialCategory.priority != null ? specialCategory.priority : specialCategoryToSave.priority
        return save(specialCategoryToSave)
    }
    
    /**
     * Saves a list of SpecialCategory objects to the database
     *
     * @param specialCategorys a list of SpecialCategorys to be saved to the database
     * @return the list of save SpecialCategory objects
     */
    
    @Transactional
    public List<SpecialCategory> saveSpecialCategories(List<SpecialCategory> specialCategories) {
        return specialCategories.collect { specialCategory -> save(specialCategory) };
    }
    
    /**
     * This methods throws an InvalidOperationException when called. SpecialCategory should not be deleted.
     */
    @Override
    public void delete(SpecialCategory obj) {
        throw new InvalidOperationException("SpecialCategory should not be deleted")
        
    }
    
    
}
