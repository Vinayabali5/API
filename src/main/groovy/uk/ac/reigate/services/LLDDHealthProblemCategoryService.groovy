package uk.ac.reigate.services

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

import uk.ac.reigate.domain.ilr.LLDDHealthProblemCategory
import uk.ac.reigate.exceptions.InvalidOperationException
import uk.ac.reigate.repositories.ilr.LLDDHealthProblemCategoryRepository
import uk.ac.reigate.utils.ValidationUtils

@Service
class LLDDHealthProblemCategoryService implements ICoreDataService<LLDDHealthProblemCategory, Integer>{
    
    @Autowired
    LLDDHealthProblemCategoryRepository lLDDHealthProblemCategoryRepository
    
    /**
     * Default NoArgs constructor
     */
    LLDDHealthProblemCategoryService() {}
    
    /**
     * Autowired Constructor
     *
     * @param lLDDHealthProblemCategoryRepository
     */
    LLDDHealthProblemCategoryService(LLDDHealthProblemCategoryRepository lLDDHealthProblemCategoryRepository) {
        this.lLDDHealthProblemCategoryRepository = lLDDHealthProblemCategoryRepository;
    }
    
    /**
     * Find an individual lLDDHealthProblemCategory using the lLDDHealthProblemCategorys ID fields
     *
     * @param id the ID fields to search for
     * @return the LLDDHealthProblemCategory object that matches the ID supplied, or null if not found
     */
    @Override
    @Transactional(readOnly = true)
    LLDDHealthProblemCategory findById(Integer id) {
        return lLDDHealthProblemCategoryRepository.findOne(id)
    }
    
    /**
     * Find all lLDDHealthProblemCategorys
     *
     * @return a SearchResult set with the list of LLDDHealthProblemCategorys
     */
    @Override
    @Transactional(readOnly = true)
    List<LLDDHealthProblemCategory> findAll() {
        return lLDDHealthProblemCategoryRepository.findAll()
    }
    
    /**
     * @param id
     * @param code
     * @param description
     * @param shortDescription
     * @param validFrom
     * @param validTo
     * @return
     */
    @Transactional
    public LLDDHealthProblemCategory saveLLDDHealthProblemCategory(Integer id, String code, String description, String shortDescription,  Date validFrom, Date validTo) {
        ValidationUtils.assertNotBlank(code, "code cannot be blank")
        ValidationUtils.assertNotNull(description, "description is mandatory")
        LLDDHealthProblemCategory lLDDHealthProblemCategory = null
        if (id != null) {
            lLDDHealthProblemCategory = findById(id)
            lLDDHealthProblemCategory.setCode(code)
            lLDDHealthProblemCategory.setDescription(description)
            lLDDHealthProblemCategory.setShortDescription(shortDescription)
            lLDDHealthProblemCategory.setValidFrom(validFrom)
            lLDDHealthProblemCategory.setValidTo(validTo)
            save(lLDDHealthProblemCategory)
        } else {
            lLDDHealthProblemCategory = save(new LLDDHealthProblemCategory(code, description, shortDescription, validFrom, validTo))
        }
        return lLDDHealthProblemCategory
    }
    
    /**
     * This service method is used to save a complete LLDDHealthProblemCategory object in the database
     *
     * @param lLDDHealthProblemCategory the new LLDDHealthProblemCategory object to be saved
     * @return the saved version of the LLDDHealthProblemCategory object
     */
    @Override
    @Transactional
    public LLDDHealthProblemCategory save(LLDDHealthProblemCategory lLDDHealthProblemCategory) {
        return lLDDHealthProblemCategoryRepository.save(lLDDHealthProblemCategory)
    }
    
    /**
     * This service method is used to update an LLDDHealthProblemCategory object in the database from a partial or complete LLDDHealthProblemCategory object.
     *
     * @param lLDDHealthProblemCategory the partial or complete LLDDHealthProblemCategory object to be saved
     * @return the saved version of the LLDDHealthProblemCategory object
     */
    @Transactional
    public LLDDHealthProblemCategory updateLLDDHealthProblemCategory(LLDDHealthProblemCategory lLDDHealthProblemCategory) {
        LLDDHealthProblemCategory lLDDHealthProblemCategoryToSave = findById(lLDDHealthProblemCategory.id)
        lLDDHealthProblemCategoryToSave.code = lLDDHealthProblemCategory.code != null ? lLDDHealthProblemCategory.code : lLDDHealthProblemCategoryToSave.code
        lLDDHealthProblemCategoryToSave.description = lLDDHealthProblemCategory.description != null ? lLDDHealthProblemCategory.description : lLDDHealthProblemCategoryToSave.description
        lLDDHealthProblemCategoryToSave.shortDescription = lLDDHealthProblemCategory.shortDescription != null ? lLDDHealthProblemCategory.shortDescription : lLDDHealthProblemCategoryToSave.shortDescription
        lLDDHealthProblemCategoryToSave.validFrom = lLDDHealthProblemCategory.validFrom != null ? lLDDHealthProblemCategory.validFrom : lLDDHealthProblemCategoryToSave.validFrom
        lLDDHealthProblemCategoryToSave.validTo = lLDDHealthProblemCategory.validTo != null ? lLDDHealthProblemCategory.validTo : lLDDHealthProblemCategoryToSave.validTo
        return save(lLDDHealthProblemCategoryToSave)
    }
    
    /**
     * Saves a list of LLDDHealthProblemCategory objects to the database
     *
     * @param lLDDHealthProblemCategorys a list of LLDDHealthProblemCategorys to be saved to the database
     * @return the list of save LLDDHealthProblemCategory objects
     */
    @Transactional
    public List<LLDDHealthProblemCategory> saveLLDDHealthProblemCategories(List<LLDDHealthProblemCategory> lLDDHealthProblemCategories) {
        return lLDDHealthProblemCategories.collect { lLDDHealthProblemCategory -> save(lLDDHealthProblemCategory) }
    }
    
    /**
     * This methods throws an InvalidOperationException when called. LLDDHealthProblemCategory should not be deleted.
     */
    @Override
    public void delete(LLDDHealthProblemCategory obj) {
        throw new InvalidOperationException("LLDDHealthProblemCategory should not be deleted")
    }
}
