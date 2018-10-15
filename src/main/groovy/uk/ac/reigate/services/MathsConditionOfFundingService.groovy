package uk.ac.reigate.services

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

import uk.ac.reigate.domain.ilr.MathsConditionOfFunding
import uk.ac.reigate.exceptions.InvalidOperationException
import uk.ac.reigate.repositories.ilr.MathsConditionOfFundingRepository
import uk.ac.reigate.utils.ValidationUtils

@Service
class MathsConditionOfFundingService implements ICoreDataService<MathsConditionOfFunding, Integer>{
    
    @Autowired
    MathsConditionOfFundingRepository mathsConditionOfFundingRepository
    
    /**
     * Default NoArgs constructor
     */
    MathsConditionOfFundingService() {}
    
    /**
     * Autowired Constructor
     *
     * @param mathsConditionOfFundingRepository
     */
    MathsConditionOfFundingService(MathsConditionOfFundingRepository mathsConditionOfFundingRepository) {
        this.mathsConditionOfFundingRepository = mathsConditionOfFundingRepository
    }
    
    /**
     * Find an individual mathsConditionOfFunding using the mathsConditionOfFundings ID fields
     *
     * @param id the ID fields to search for
     * @return the MathsConditionOfFunding object that matches the ID supplied, or null if not found
     */
    @Override
    @Transactional(readOnly = true)
    MathsConditionOfFunding findById(Integer id) {
        return mathsConditionOfFundingRepository.findOne(id)
    }
    
    /**
     * Find all mathsConditionOfFundings
     *
     * @return a SearchResult set with the list of MathsConditionOfFundings
     */
    @Override
    @Transactional(readOnly = true)
    List<MathsConditionOfFunding> findAll() {
        return mathsConditionOfFundingRepository.findAll()
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
    public MathsConditionOfFunding saveMathsConditionOfFunding(Integer id, String code, String description, String shortDescription,  Date validFrom, Date validTo) {
        ValidationUtils.assertNotBlank(code, "code cannot be blank")
        ValidationUtils.assertNotNull(description, "description is mandatory")
        MathsConditionOfFunding mathsConditionOfFunding = null
        if (id != null) {
            mathsConditionOfFunding = findById(id)
            mathsConditionOfFunding.setCode(code)
            mathsConditionOfFunding.setDescription(description)
            mathsConditionOfFunding.setShortDescription(shortDescription)
            mathsConditionOfFunding.setValidFrom(validFrom)
            mathsConditionOfFunding.setValidTo(validTo)
            save(mathsConditionOfFunding)
        } else {
            mathsConditionOfFunding = save(new MathsConditionOfFunding(code, description, shortDescription, validFrom, validTo))
        }
        
        return mathsConditionOfFunding;
    }
    
    /**
     * This service method is used to save a complete MathsConditionOfFunding object in the database
     *
     * @param mathsConditionOfFundingr the new MathsConditionOfFunding object to be saved
     * @return the saved version of the MathsConditionOfFunding object
     */
    @Override
    @Transactional
    public MathsConditionOfFunding save(MathsConditionOfFunding mathsConditionOfFunding) {
        return mathsConditionOfFundingRepository.save(mathsConditionOfFunding)
    }
    
    /**
     * This service method is used to update an MathsConditionOfFunding object in the database from a partial or complete MathsConditionOfFunding object.
     *
     * @param mathsConditionOfFunding the partial or complete MathsConditionOfFunding object to be saved
     * @return the saved version of the MathsConditionOfFunding object
     */
    @Transactional
    public MathsConditionOfFunding updateMathsConditionOfFunding(MathsConditionOfFunding mathsConditionOfFunding) {
        MathsConditionOfFunding mathsConditionOfFundingToSave = findById(mathsConditionOfFunding.id)
        mathsConditionOfFundingToSave.code = mathsConditionOfFunding.code != null ? mathsConditionOfFunding.code : mathsConditionOfFundingToSave.code
        mathsConditionOfFundingToSave.description = mathsConditionOfFunding.description != null ? mathsConditionOfFunding.description : mathsConditionOfFundingToSave.description
        mathsConditionOfFundingToSave.shortDescription = mathsConditionOfFunding.shortDescription != null ? mathsConditionOfFunding.shortDescription : mathsConditionOfFundingToSave.shortDescription
        mathsConditionOfFundingToSave.validFrom = mathsConditionOfFunding.validFrom != null ? mathsConditionOfFunding.validFrom : mathsConditionOfFundingToSave.validFrom
        mathsConditionOfFundingToSave.validTo = mathsConditionOfFunding.validTo != null ? mathsConditionOfFunding.validTo : mathsConditionOfFundingToSave.validTo
        return save(mathsConditionOfFundingToSave)
    }
    
    /**
     * Saves a list of MathsConditionOfFunding objects to the database
     *
     * @param mathsConditionOfFundings a list of MathsConditionOfFundings to be saved to the database
     * @return the list of save MathsConditionOfFunding objects
     */
    @Transactional
    public List<MathsConditionOfFunding> saveMathsConditionOfFundings(List<MathsConditionOfFunding> mathsConditionOfFundings) {
        return mathsConditionOfFundings.collect { mathsConditionOfFunding -> save(mathsConditionOfFunding) }
    }
    
    /**
     * This methods throws an InvalidOperationException when called. MathsConditionOfFunding should not be deleted.
     */
    @Override
    public void delete(MathsConditionOfFunding obj) {
        throw new InvalidOperationException("MathsConditionOfFunding should not be deleted")
    }
}
