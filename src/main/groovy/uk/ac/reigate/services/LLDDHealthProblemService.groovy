package uk.ac.reigate.services

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

import uk.ac.reigate.domain.ilr.LLDDHealthProblem
import uk.ac.reigate.exceptions.InvalidDataException
import uk.ac.reigate.repositories.ilr.LLDDHealthProblemRepository
import uk.ac.reigate.utils.ValidationUtils

@Service
class LLDDHealthProblemService implements ICoreDataService<LLDDHealthProblem, Integer>{
    
    @Autowired
    LLDDHealthProblemRepository lLDDHealthProblemRepository
    
    /**
     * Default NoArgs constructor
     */
    LLDDHealthProblemService() {}
    
    /**
     * Autowired Constructor
     *
     * @param lLDDHealthProblemRepository
     */
    LLDDHealthProblemService(LLDDHealthProblemRepository lLDDHealthProblemRepository) {
        this.lLDDHealthProblemRepository = lLDDHealthProblemRepository;
    }
    
    /**
     * Find an individual lLDDHealthProblem using the lLDDHealthProblems ID fields
     *
     * @param id the ID fields to search for
     * @return the LLDDHealthProblem object that matches the ID supplied, or null if not found
     */
    @Override
    @Transactional(readOnly = true)
    LLDDHealthProblem findById(Integer id) {
        return lLDDHealthProblemRepository.findOne(id)
    }
    
    /**
     * Find all lLDDHealthProblems
     *
     * @return a SearchResult set with the list of LLDDHealthProblems
     */
    @Override
    @Transactional(readOnly = true)
    List<LLDDHealthProblem> findAll() {
        return lLDDHealthProblemRepository.findAll()
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
    public LLDDHealthProblem saveLLDDHealthProblem(Integer id, String code, String description, String shortDescription,  Date validFrom, Date validTo) {
        ValidationUtils.assertNotBlank(code, "code cannot be blank")
        ValidationUtils.assertNotNull(description, "description is mandatory")
        
        LLDDHealthProblem lLDDHealthProblem = null
        
        if (id != null) {
            lLDDHealthProblem = findById(id)
            lLDDHealthProblem.setCode(code)
            lLDDHealthProblem.setDescription(description)
            lLDDHealthProblem.setShortDescription(shortDescription)
            lLDDHealthProblem.setValidFrom(validFrom)
            lLDDHealthProblem.setValidTo(validTo)
            
            
            save(lLDDHealthProblem)
        } else {
            lLDDHealthProblem = save(new LLDDHealthProblem(code, description, shortDescription, validFrom, validTo))
        }
        
        return lLDDHealthProblem
    }
    
    /**
     * This service method is used to save a complete LLDDHealthProblem object in the database
     *
     * @param lLDDHealthProblem the new LLDDHealthProblem object to be saved
     * @return the saved version of the LLDDHealthProblem object
     */
    @Override
    @Transactional
    public LLDDHealthProblem save(LLDDHealthProblem lLDDHealthProblem) {
        return lLDDHealthProblemRepository.save(lLDDHealthProblem)
    }
    
    /**
     * This service method is used to update an LLDDHealthProblem object in the database from a partial or complete LLDDHealthProblem object.
     *
     * @param lLDDHealthProblem the partial or complete LLDDHealthProblem object to be saved
     * @return the saved version of the LLDDHealthProblem object
     */
    @Transactional
    public LLDDHealthProblem updateLLDDHealthProblem(LLDDHealthProblem lLDDHealthProblem) {
        LLDDHealthProblem lLDDHealthProblemToSave = findById(lLDDHealthProblem.id)
        lLDDHealthProblemToSave.code = lLDDHealthProblem.code != null ? lLDDHealthProblem.code : lLDDHealthProblemToSave.code
        lLDDHealthProblemToSave.description = lLDDHealthProblem.description != null ? lLDDHealthProblem.description : lLDDHealthProblemToSave.description
        lLDDHealthProblemToSave.shortDescription = lLDDHealthProblem.shortDescription != null ? lLDDHealthProblem.shortDescription : lLDDHealthProblemToSave.shortDescription
        lLDDHealthProblemToSave.validFrom = lLDDHealthProblem.validFrom != null ? lLDDHealthProblem.validFrom : lLDDHealthProblemToSave.validFrom
        lLDDHealthProblemToSave.validTo = lLDDHealthProblem.validTo != null ? lLDDHealthProblem.validTo : lLDDHealthProblemToSave.validTo
        return save(lLDDHealthProblemToSave)
    }
    
    /**
     * Saves a list of LLDDHealthProblem objects to the database
     * 
     * @param lLDDHealthProblems a list of LLDDHealthProblems to be saved to the database
     * @return the list of save LLDDHealthProblem objects
     */
    @Transactional
    public List<LLDDHealthProblem> saveLLDDHealthProblems(List<LLDDHealthProblem> lLDDHealthProblems) {
        return lLDDHealthProblems.collect { lLDDHealthProblem -> save(lLDDHealthProblem) }
    }
    
    /**
     * This methods throws an InvalidOperationException when called. LLDDHealthProblem should not be deleted.
     */
    @Override
    public void delete(LLDDHealthProblem obj) {
        throw new InvalidDataException("LLDDHealthProblem should not be deleted")
    }
}
