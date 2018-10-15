package uk.ac.reigate.services

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

import uk.ac.reigate.domain.ilr.Outcome
import uk.ac.reigate.exceptions.InvalidOperationException
import uk.ac.reigate.repositories.ilr.OutcomeRepository
import uk.ac.reigate.utils.ValidationUtils

@Service
class OutcomeService implements ICoreDataService<Outcome, Integer>{
    
    @Autowired
    OutcomeRepository outcomeRepository
    
    /**
     * Default NoArgs constructor
     */
    OutcomeService() {}
    
    /**
     * Autowired Constructor
     *
     * @param outcomeRepository
     */
    OutcomeService(OutcomeRepository outcomeRepository) {
        this.outcomeRepository = outcomeRepository;
    }
    
    /**
     * Find an individual outcome using the outcomes ID fields
     *
     * @param id the ID fields to search for
     * @return the Outcome object that matches the ID supplied, or null if not found
     */
    @Override
    @Transactional(readOnly = true)
    Outcome findById(Integer id) {
        return outcomeRepository.findOne(id)
    }
    
    /**
     * Find all outcomes
     *
     * @return a List of Outcomes
     */
    @Override
    @Transactional(readOnly = true)
    List<Outcome> findAll() {
        return outcomeRepository.findAll()
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
    public Outcome saveOutcome(Integer id, String code, String description, String shortDescription,  Date validFrom, Date validTo) {
        ValidationUtils.assertNotBlank(code, "code cannot be blank")
        ValidationUtils.assertNotNull(description, "description is mandatory")
        
        Outcome outcome = null
        
        if (id != null) {
            outcome = findById(id)
            outcome.setCode(code)
            outcome.setDescription(description)
            outcome.setShortDescription(shortDescription)
            outcome.setValidFrom(validFrom)
            outcome.setValidTo(validTo)
            
            save(outcome)
        } else {
            outcome = save(new Outcome(code, description, shortDescription, validFrom, validTo))
        }
        
        return outcome
    }
    
    /**
     * This service method is used to save a complete Outcome object in the database
     *
     * @param outcomer the new Outcome object to be saved
     * @return the saved version of the Outcome object
     */
    @Override
    @Transactional
    public Outcome save(Outcome outcome) {
        return outcomeRepository.save(outcome)
    }
    
    /**
     * This service method is used to update an Outcome object in the database from a partial or complete Outcome object.
     * 
     * @param outcome outcome the partial or complete Outcome object to be saved
     * @return the saved version of the Outcome object
     */
    @Transactional
    public Outcome updateOutcome(Outcome outcome) {
        Outcome outcomeToSave = findById(outcome.id)
        outcomeToSave.code = outcome.code != null ? outcome.code : outcomeToSave.code
        outcomeToSave.description = outcome.description != null ? outcome.description : outcomeToSave.description
        outcomeToSave.shortDescription = outcome.shortDescription != null ? outcome.shortDescription : outcomeToSave.shortDescription
        outcomeToSave.validFrom = outcome.validFrom != null ? outcome.validFrom : outcomeToSave.validFrom
        outcomeToSave.validTo = outcome.validTo != null ? outcome.validTo : outcomeToSave.validTo
        return save(outcomeToSave)
    }
    
    /**
     * Saves a list of Outcome objects to the database
     *
     * @param outcomes a list of Outcomes to be saved to the database
     * @return the list of save Outcome objects
     */
    @Transactional
    public List<Outcome> saveOutcomes(List<Outcome> outcomes) {
        return outcomes.collect { outcome -> save(outcome) }
    }
    
    /**
     * This methods throws an InvalidOperationException when called. Outcome should not be deleted.
     */
    @Override
    public void delete(Outcome obj) {
        throw new InvalidOperationException("Outcome should not be deleted")
    }
}
