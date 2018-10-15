package uk.ac.reigate.services

//import static org.springframework.util.Assert

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.security.access.prepost.PreAuthorize
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

import uk.ac.reigate.domain.ilr.EnglishConditionOfFunding
import uk.ac.reigate.exceptions.InvalidOperationException
import uk.ac.reigate.model.SearchResult
import uk.ac.reigate.repositories.ilr.EnglishConditionOfFundingRepository
import uk.ac.reigate.utils.ValidationUtils

@Service
class EnglishConditionOfFundingService implements ICoreDataService<EnglishConditionOfFunding, Integer>{
    
    @Autowired
    EnglishConditionOfFundingRepository englishConditionOfFundingRepository
    
    /**
     * Default NoArgs constructor
     */
    EnglishConditionOfFundingService() {}
    
    /**
     * Autowired Constructor
     *
     * @param englishConditionOfFundingRepository
     */
    EnglishConditionOfFundingService(EnglishConditionOfFundingRepository englishConditionOfFundingRepository) {
        this.englishConditionOfFundingRepository = englishConditionOfFundingRepository
    }
    
    /**
     * Find an individual englishConditionOfFunding using the englishConditionOfFundings ID fields
     *
     * @param id the ID fields to search for
     * @return the EnglishConditionOfFunding object that matches the ID supplied, or null if not found
     */
    @Override
    @Transactional(readOnly = true)
    EnglishConditionOfFunding findById(Integer id) {
        return englishConditionOfFundingRepository.findOne(id)
    }
    
    /**
     * Find all englishConditionOfFundings
     *
     * @return a SearchResult set with the list of EnglishConditionOfFundings
     */
    @Override
    @Transactional(readOnly = true)
    List<EnglishConditionOfFunding> findAll() {
        return englishConditionOfFundingRepository.findAll()
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
    public EnglishConditionOfFunding saveEnglishConditionOfFunding(Integer id, String code, String description, String shortDescription,  Date validFrom, Date validTo) {
        ValidationUtils.assertNotBlank(code, "code cannot be blank")
        ValidationUtils.assertNotNull(description, "description is mandatory")
        EnglishConditionOfFunding englishConditionOfFunding = null
        
        if (id != null) {
            englishConditionOfFunding = findById(id)
            englishConditionOfFunding.setCode(code)
            englishConditionOfFunding.setDescription(description)
            englishConditionOfFunding.setShortDescription(shortDescription)
            englishConditionOfFunding.setValidFrom(validFrom)
            englishConditionOfFunding.setValidTo(validTo)
            save(englishConditionOfFunding)
        } else {
            englishConditionOfFunding = save(new EnglishConditionOfFunding(code, description, shortDescription, validFrom, validTo))
        }
        
        return englishConditionOfFunding;
    }
    
    /**
     * This service method is used to save a complete EnglishConditionOfFunding object in the database
     *
     * @param englishConditionOfFundingr the new EnglishConditionOfFunding object to be saved
     * @return the saved version of the EnglishConditionOfFunding object
     */
    @Override
    @Transactional
    public EnglishConditionOfFunding save(EnglishConditionOfFunding englishConditionOfFunding) {
        return englishConditionOfFundingRepository.save(englishConditionOfFunding)
    }
    
    /**
     * This service method is used to update an EnglishConditionOfFunding object in the database from a partial or complete EnglishConditionOfFunding object.
     *
     * @param englishConditionOfFunding the partial or complete EnglishConditionOfFunding object to be saved
     * @return the saved version of the EnglishConditionOfFunding object
     */
    @Transactional
    public EnglishConditionOfFunding updateEnglishConditionOfFunding(EnglishConditionOfFunding englishConditionOfFunding) {
        EnglishConditionOfFunding englishConditionOfFundingToSave = findById(englishConditionOfFunding.id)
        englishConditionOfFundingToSave.code = englishConditionOfFunding.code != null ? englishConditionOfFunding.code : englishConditionOfFundingToSave.code
        englishConditionOfFundingToSave.description = englishConditionOfFunding.description != null ? englishConditionOfFunding.description : englishConditionOfFundingToSave.description
        englishConditionOfFundingToSave.shortDescription = englishConditionOfFunding.shortDescription != null ? englishConditionOfFunding.shortDescription : englishConditionOfFundingToSave.shortDescription
        englishConditionOfFundingToSave.validFrom = englishConditionOfFunding.validFrom != null ? englishConditionOfFunding.validFrom : englishConditionOfFundingToSave.validFrom
        englishConditionOfFundingToSave.validTo = englishConditionOfFunding.validTo != null ? englishConditionOfFunding.validTo : englishConditionOfFundingToSave.validTo
        return save(englishConditionOfFundingToSave)
    }
    
    /**
     * Saves a list of EnglishConditionOfFunding objects to the database
     *
     * @param englishConditionOfFundings a list of EnglishConditionOfFundings to be saved to the database
     * @return the list of save EnglishConditionOfFunding objects
     */
    @Transactional
    public List<EnglishConditionOfFunding> saveEnglishConditionOfFundings(List<EnglishConditionOfFunding> englishConditionOfFundings) {
        return englishConditionOfFundings.collect { englishConditionOfFunding -> save(englishConditionOfFunding) }
    }
    /**
     * This methods throws an InvalidOperationException when called. EnglishConditionOfFunding should not be deleted.
     */
    @Override
    public void delete(EnglishConditionOfFunding obj) {
        throw new InvalidOperationException("EnglishConditionOfFunding cannot be deleted")
        
    }
    
    
    
    
}
