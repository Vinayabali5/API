package uk.ac.reigate.services

//import static org.springframework.util.Assert

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

import uk.ac.reigate.domain.ilr.PriorAttainment;
import uk.ac.reigate.exceptions.InvalidOperationException
import uk.ac.reigate.repositories.ilr.PriorAttainmentRepository
import uk.ac.reigate.utils.ValidationUtils

@Service
class PriorAttainmentService implements ICoreDataService<PriorAttainment, Integer>{
    
    @Autowired
    PriorAttainmentRepository priorAttainmentRepository
    
    /**
     * Default NoArgs constructor
     */
    PriorAttainmentService() {}
    
    /**
     * Autowired Constructor
     *
     * @param priorAttainmentRepository
     */
    PriorAttainmentService(PriorAttainmentRepository priorAttainmentRepository) {
        this.priorAttainmentRepository = priorAttainmentRepository;
    }
    
    /**
     * Find an individual priorAttainment using the priorAttainments ID fields
     *
     * @param id the ID fields to search for
     * @return the PriorAttainment object that matches the ID supplied, or null if not found
     */
    @Override
    @Transactional(readOnly = true)
    PriorAttainment findById(Integer id) {
        return priorAttainmentRepository.findOne(id)
    }
    
    /**
     * Find all priorAttainments
     *
     * @return a SearchResult set with the list of PriorAttainments
     */
    @Override
    @Transactional(readOnly = true)
    List<PriorAttainment> findAll() {
        return priorAttainmentRepository.findAll()
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
    public PriorAttainment savePriorAttainment(Integer id, String code, String description, String shortDescription,  Date validFrom, Date validTo) {
        ValidationUtils.assertNotBlank(code, "code cannot be blank")
        ValidationUtils.assertNotNull(description, "description is mandatory")
        
        PriorAttainment priorAttainment = null
        
        if (id != null) {
            priorAttainment = findById(id)
            priorAttainment.setCode(code)
            priorAttainment.setDescription(description)
            priorAttainment.setShortDescription(shortDescription)
            priorAttainment.setValidFrom(validFrom)
            priorAttainment.setValidTo(validTo)
            
            
            save(priorAttainment)
        } else {
            priorAttainment = save(new PriorAttainment(code, description, shortDescription, validFrom, validTo))
        }
        
        return priorAttainment
    }
    
    /**
     * This service method is used to save a complete PriorAttainment object in the database
     *
     * @param priorAttainmentr the new PriorAttainment object to be saved
     * @return the saved version of the PriorAttainment object
     */
    @Override
    @Transactional
    public PriorAttainment save(PriorAttainment priorAttainment) {
        return priorAttainmentRepository.save(priorAttainment)
    }
    
    /**
     * This service method is used to update an PriorAttainment object in the database from a partial or complete PriorAttainment object.
     *
     * @param priorAttainment the partial or complete PriorAttainment object to be saved
     * @return the saved version of the PriorAttainment object
     */
    
    @Transactional
    public PriorAttainment updatePriorAttainment(PriorAttainment priorAttainment) {
        PriorAttainment priorAttainmentToSave = findById(priorAttainment.id)
        priorAttainmentToSave.code = priorAttainment.code != null ? priorAttainment.code : priorAttainmentToSave.code
        priorAttainmentToSave.description = priorAttainment.description != null ? priorAttainment.description : priorAttainmentToSave.description
        priorAttainmentToSave.shortDescription = priorAttainment.shortDescription != null ? priorAttainment.shortDescription : priorAttainmentToSave.shortDescription
        priorAttainmentToSave.validFrom = priorAttainment.validFrom != null ? priorAttainment.validFrom : priorAttainmentToSave.validFrom
        priorAttainmentToSave.validTo = priorAttainment.validTo != null ? priorAttainment.validTo : priorAttainmentToSave.validTo
        return save(priorAttainmentToSave)
    }
    
    /**
     * Saves a list of PriorAttainment objects to the database
     *
     * @param priorAttainments a list of PriorAttainments to be saved to the database
     * @return the list of save PriorAttainment objects
     */
    
    @Transactional
    public List<PriorAttainment> savePriorAttainments(List<PriorAttainment> priorAttainments) {
        return priorAttainments.collect { priorAttainment -> save(priorAttainment) }
    }
    
    /**
     * This methods throws an InvalidOperationException when called. PriorAttainment should not be deleted.
     */
    @Override
    public void delete(PriorAttainment obj) {
        throw new InvalidOperationException("PriorAttainment should not be deleted")
        
    }
    
}
