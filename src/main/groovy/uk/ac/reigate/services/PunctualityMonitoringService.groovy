package uk.ac.reigate.services

//import static org.springframework.util.Assert

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import org.springframework.data.domain.Sort;
import org.springframework.security.access.prepost.PreAuthorize
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

import uk.ac.reigate.domain.lookup.PunctualityMonitoring
import uk.ac.reigate.exceptions.InvalidOperationException
import uk.ac.reigate.model.PageInfo
import uk.ac.reigate.model.SearchResult
import uk.ac.reigate.repositories.lookup.PunctualityMonitoringRepository
import uk.ac.reigate.utils.ValidationUtils;

@Service
class PunctualityMonitoringService implements ICoreDataService<PunctualityMonitoring, Integer>{
    
    @Autowired
    PunctualityMonitoringRepository punctualityMonitoringRepository
    
    /**
     * Default NoArgs constructor
     */
    PunctualityMonitoringService() {}
    
    /**
     * Autowired Constructor
     *
     * @param punctualityMonitoringRepository
     */
    PunctualityMonitoringService(PunctualityMonitoringRepository punctualityMonitoringRepository) {
        this.punctualityMonitoringRepository = punctualityMonitoringRepository;
    }
    
    /**
     * Find an individual punctualityMonitoring using the punctualityMonitorings ID fields
     *
     * @param id the ID fields to search for
     * @return the PunctualityMonitoring object that matches the ID supplied, or null if not found
     */
    @Override
    @Transactional(readOnly = true)
    PunctualityMonitoring findById(Integer id) {
        return punctualityMonitoringRepository.findOne(id);
    }
    
    /**
     * Find all punctualityMonitorings
     *
     * @return a SearchResult set with the list of PunctualityMonitorings
     */
    @Override
    @Transactional(readOnly = true)
    List<PunctualityMonitoring> findAll() {
        return punctualityMonitoringRepository.findAll();
    }
    
    
    /**
     * @param id
     * @param code
     * @param description
     * @param warningColour
     * @return
     */
    @Transactional
    public PunctualityMonitoring savePunctualityMonitoring(Integer id, String code, String description, String warningColour) {
        ValidationUtils.assertNotBlank(code, "code cannot be blank");
        ValidationUtils.assertNotNull(description, "description is mandatory");
        
        PunctualityMonitoring punctualityMonitoring = null;
        
        if (id != null) {
            punctualityMonitoring = findById(id);
            
            punctualityMonitoring.setCode(code);
            punctualityMonitoring.setDescription(description);
            punctualityMonitoring.setWarningColour(warningColour);
            
            save(punctualityMonitoring);
        } else {
            punctualityMonitoring = save(new PunctualityMonitoring(code, description, warningColour));
        }
        
        return punctualityMonitoring;
    }
    
    /**
     * This service method is used to save a complete PunctualityMonitoring object in the database
     *
     * @param punctualityMonitoring the new PunctualityMonitoring object to be saved
     * @return the saved version of the PunctualityMonitoring object
     */
    @Override
    @Transactional
    public PunctualityMonitoring save(PunctualityMonitoring punctualityMonitoring) {
        return punctualityMonitoringRepository.save(punctualityMonitoring)
    }
    
    /**
     * This service method is used to update an PunctualityMonitoring object in the database from a partial or complete PunctualityMonitoring object.
     *
     * @param punctualityMonitoring the partial or complete PunctualityMonitoring object to be saved
     * @return the saved version of the PunctualityMonitoring object
     */
    
    @Transactional
    public PunctualityMonitoring updatePunctualityMonitoring(PunctualityMonitoring punctualityMonitoring) {
        PunctualityMonitoring punctualityMonitoringToSave = findById(punctualityMonitoring.id);
        punctualityMonitoringToSave.code = punctualityMonitoring.code != null ? punctualityMonitoring.code : punctualityMonitoringToSave.code
        punctualityMonitoringToSave.description = punctualityMonitoring.description != null ? punctualityMonitoring.description : punctualityMonitoringToSave.description
        punctualityMonitoringToSave.warningColour = punctualityMonitoring.warningColour != null ? punctualityMonitoring.warningColour : punctualityMonitoringToSave.warningColour
        return save(punctualityMonitoringToSave)
    }
    
    /**
     * Saves a list of PunctualityMonitoring objects to the database
     *
     * @param punctualityMonitorings a list of PunctualityMonitorings to be saved to the database
     * @return the list of save PunctualityMonitoring objects
     */
    
    @Transactional
    public List<PunctualityMonitoring> savePunctualityMonitorings(List<PunctualityMonitoring> punctualityMonitorings) {
        return punctualityMonitorings.collect { punctualityMonitoring -> save(punctualityMonitoring) };
    }
    
    
    /**
     * This methods throws an InvalidOperationException when called. PunctualityMonitoring should not be deleted.
     */
    @Override
    public void delete(PunctualityMonitoring obj) {
        throw new InvalidOperationException("PunctualityMonitoring should not be deleted")
        
    }
    
    
    
}
