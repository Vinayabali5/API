package uk.ac.reigate.services

//import static org.springframework.util.Assert

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.security.access.prepost.PreAuthorize
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

import uk.ac.reigate.domain.ilr.RestrictedUseIndicator
import uk.ac.reigate.exceptions.InvalidOperationException
import uk.ac.reigate.model.SearchResult
import uk.ac.reigate.repositories.ilr.RestrictedUseIndicatorRepository
import uk.ac.reigate.utils.ValidationUtils

@Service
class RestrictedUseIndicatorService implements ICoreDataService<RestrictedUseIndicator, Integer>{
    
    @Autowired
    RestrictedUseIndicatorRepository restrictedUseIndicatorRepository
    
    /**
     * Default NoArgs constructor
     */
    RestrictedUseIndicatorService() {}
    
    /**
     * Autowired Constructor
     *
     * @param restrictedUseIndicatorRepository
     */
    RestrictedUseIndicatorService(RestrictedUseIndicatorRepository restrictedUseIndicatorRepository) {
        this.restrictedUseIndicatorRepository = restrictedUseIndicatorRepository;
    }
    
    /**
     * Find an individual restrictedUseIndicator using the restrictedUseIndicators ID fields
     *
     * @param id the ID fields to search for
     * @return the RestrictedUseIndicator object that matches the ID supplied, or null if not found
     */
    @Override
    @Transactional(readOnly = true)
    RestrictedUseIndicator findById(Integer id) {
        return restrictedUseIndicatorRepository.findOne(id)
    }
    
    /**
     * Find all restrictedUseIndicators
     *
     * @return a SearchResult set with the list of RestrictedUseIndicators
     */
    @Override
    @Transactional(readOnly = true)
    List<RestrictedUseIndicator> findAll() {
        return restrictedUseIndicatorRepository.findAll()
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
    public RestrictedUseIndicator saveRestrictedUseIndicator(Integer id, String code, String description, String shortDescription,  Date validFrom, Date validTo) {
        ValidationUtils.assertNotBlank(code, "code cannot be blank")
        ValidationUtils.assertNotNull(description, "description is mandatory")
        
        RestrictedUseIndicator restrictedUseIndicator = null
        
        if (id != null) {
            restrictedUseIndicator = findById(id)
            restrictedUseIndicator.setCode(code)
            restrictedUseIndicator.setDescription(description)
            restrictedUseIndicator.setShortDescription(shortDescription)
            restrictedUseIndicator.setValidFrom(validFrom)
            restrictedUseIndicator.setValidTo(validTo)
            
            
            save(restrictedUseIndicator)
        } else {
            restrictedUseIndicator = save(new RestrictedUseIndicator(code, description, shortDescription, validFrom, validTo))
        }
        
        return restrictedUseIndicator
    }
    
    /**
     * This service method is used to save a complete RestrictedUseIndicator object in the database
     *
     * @param restrictedUseIndicatorr the new RestrictedUseIndicator object to be saved
     * @return the saved version of the RestrictedUseIndicator object
     */
    @Override
    @Transactional
    public RestrictedUseIndicator save(RestrictedUseIndicator restrictedUseIndicator) {
        return restrictedUseIndicatorRepository.save(restrictedUseIndicator)
    }
    
    /**
     * This service method is used to update an RestrictedUseIndicator object in the database from a partial or complete RestrictedUseIndicator object.
     *
     * @param restrictedUseIndicator the partial or complete RestrictedUseIndicator object to be saved
     * @return the saved version of the RestrictedUseIndicator object
     */
    
    @Transactional
    public RestrictedUseIndicator updateRestrictedUseIndicator(RestrictedUseIndicator restrictedUseIndicator) {
        RestrictedUseIndicator restrictedUseIndicatorToSave = findById(restrictedUseIndicator.id)
        restrictedUseIndicatorToSave.code = restrictedUseIndicator.code != null ? restrictedUseIndicator.code : restrictedUseIndicatorToSave.code
        restrictedUseIndicatorToSave.description = restrictedUseIndicator.description != null ? restrictedUseIndicator.description : restrictedUseIndicatorToSave.description
        restrictedUseIndicatorToSave.shortDescription = restrictedUseIndicator.shortDescription != null ? restrictedUseIndicator.shortDescription : restrictedUseIndicatorToSave.shortDescription
        restrictedUseIndicatorToSave.validFrom = restrictedUseIndicator.validFrom != null ? restrictedUseIndicator.validFrom : restrictedUseIndicatorToSave.validFrom
        restrictedUseIndicatorToSave.validTo = restrictedUseIndicator.validTo != null ? restrictedUseIndicator.validTo : restrictedUseIndicatorToSave.validTo
        return save(restrictedUseIndicatorToSave)
    }
    
    /**
     * Saves a list of RestrictedUseIndicator objects to the database
     *
     * @param restrictedUseIndicators a list of RestrictedUseIndicators to be saved to the database
     * @return the list of save RestrictedUseIndicator objects
     */
    
    @Transactional
    public List<RestrictedUseIndicator> saveRestrictedUseIndicators(List<RestrictedUseIndicator> restrictedUseIndicators) {
        return restrictedUseIndicators.collect { restrictedUseIndicator -> save(restrictedUseIndicator) }
    }
    
    /**
     * This methods throws an InvalidOperationException when called. QualificationAssessment should not be deleted.
     */
    @Override
    public void delete(RestrictedUseIndicator obj) {
        throw new InvalidOperationException("RestrictedUseIndicator should not be deleted")
    }
    
    
    
}
