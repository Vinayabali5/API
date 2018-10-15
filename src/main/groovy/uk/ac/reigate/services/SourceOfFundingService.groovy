package uk.ac.reigate.services

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

import uk.ac.reigate.domain.ilr.SourceOfFunding
import uk.ac.reigate.exceptions.InvalidOperationException
import uk.ac.reigate.repositories.ilr.SourceOfFundingRepository
import uk.ac.reigate.utils.ValidationUtils

@Service
class SourceOfFundingService implements ICoreDataService<SourceOfFunding, Integer>{
    
    @Autowired
    SourceOfFundingRepository sourceOfFundingRepository
    
    /**
     * Default NoArgs constructor
     */
    SourceOfFundingService() {}
    
    /**
     * Autowired Constructor
     *
     * @param sourceOfFundingRepository
     */
    SourceOfFundingService(SourceOfFundingRepository sourceOfFundingRepository) {
        this.sourceOfFundingRepository = sourceOfFundingRepository;
    }
    
    /**
     * Find an individual sourceOfFunding using the sourceOfFundings ID fields
     *
     * @param id the ID fields to search for
     * @return the SourceOfFunding object that matches the ID supplied, or null if not found
     */
    @Override
    @Transactional(readOnly = true)
    SourceOfFunding findById(Integer id) {
        return sourceOfFundingRepository.findOne(id)
    }
    
    /**
     * Find all sourceOfFundings
     *
     * @return a SearchResult set with the list of SourceOfFundings
     */
    @Override
    @Transactional(readOnly = true)
    List<SourceOfFunding> findAll() {
        return sourceOfFundingRepository.findAll()
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
    public SourceOfFunding saveSourceOfFunding(Integer id, String code, String description, String shortDescription,  Date validFrom, Date validTo) {
        ValidationUtils.assertNotBlank(code, "code cannot be blank")
        ValidationUtils.assertNotNull(description, "description is mandatory")
        
        SourceOfFunding sourceOfFunding = null
        
        if (id != null) {
            sourceOfFunding = findById(id)
            sourceOfFunding.setCode(code)
            sourceOfFunding.setDescription(description)
            sourceOfFunding.setShortDescription(shortDescription)
            sourceOfFunding.setValidFrom(validFrom)
            sourceOfFunding.setValidTo(validTo)
            
            save(sourceOfFunding)
        } else {
            sourceOfFunding = save(new SourceOfFunding(code, description, shortDescription, validFrom, validTo))
        }
        
        return sourceOfFunding
    }
    
    /**
     * This service method is used to save a complete SourceOfFunding object in the database
     *
     * @param sourceOfFunding the new SourceOfFunding object to be saved
     * @return the saved version of the SourceOfFunding object
     */
    @Override
    @Transactional
    public SourceOfFunding save(SourceOfFunding sourceOfFunding) {
        return sourceOfFundingRepository.save(sourceOfFunding)
    }
    
    /**
     * This service method is used to update an SourceOfFunding object in the database from a partial or complete SourceOfFunding object.
     *
     * @param sourceOfFunding the partial or complete SourceOfFunding object to be saved
     * @return the saved version of the SourceOfFunding object
     */
    @Transactional
    public SourceOfFunding updateSourceOfFunding(SourceOfFunding sourceOfFunding) {
        SourceOfFunding sourceOfFundingToSave = findById(sourceOfFunding.id)
        sourceOfFundingToSave.code = sourceOfFunding.code != null ? sourceOfFunding.code : sourceOfFundingToSave.code
        sourceOfFundingToSave.description = sourceOfFunding.description != null ? sourceOfFunding.description : sourceOfFundingToSave.description
        sourceOfFundingToSave.shortDescription = sourceOfFunding.shortDescription != null ? sourceOfFunding.shortDescription : sourceOfFundingToSave.shortDescription
        sourceOfFundingToSave.validFrom = sourceOfFunding.validFrom != null ? sourceOfFunding.validFrom : sourceOfFundingToSave.validFrom
        sourceOfFundingToSave.validTo = sourceOfFunding.validTo != null ? sourceOfFunding.validTo : sourceOfFundingToSave.validTo
        
        return save(sourceOfFundingToSave)
    }
    
    /**
     * Saves a list of SourceOfFunding objects to the database
     *
     * @param sourceOfFundings a list of SourceOfFundings to be saved to the database
     * @return the list of save SourceOfFunding objects
     */
    @Transactional
    public List<SourceOfFunding> saveSourceOfFundings(List<SourceOfFunding> sourceOfFundings) {
        return sourceOfFundings.collect { sourceOfFunding -> save(sourceOfFunding) }
    }
    
    /**
     * This methods throws an InvalidOperationException when called. SourceOfFunding should not be deleted.
     */
    @Override
    public void delete(SourceOfFunding obj) {
        throw new InvalidOperationException("SourceOfFunding should not be deleted")
    }
}
