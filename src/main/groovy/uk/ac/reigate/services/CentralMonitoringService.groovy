package uk.ac.reigate.services

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

import uk.ac.reigate.domain.lookup.CentralMonitoring
import uk.ac.reigate.exceptions.InvalidOperationException
import uk.ac.reigate.repositories.lookup.CentralMonitoringRepository
import uk.ac.reigate.utils.ValidationUtils

@Service
class CentralMonitoringService implements ICoreDataService<CentralMonitoring, Integer>{
    
    @Autowired
    CentralMonitoringRepository centralMonitoringRepository
    
    /**
     * Default NoArgs constructor
     */
    CentralMonitoringService() {}
    
    /**
     * Autowired Constructor
     *
     * @param centralMonitoringRepository
     */
    CentralMonitoringService(CentralMonitoringRepository centralMonitoringRepository) {
        this.centralMonitoringRepository = centralMonitoringRepository;
    }
    
    /**
     * Find an individual centralMonitoring using the centralMonitorings ID fields
     *
     * @param id the ID fields to search for
     * @return the CentralMonitoring object that matches the ID supplied, or null if not found
     */
    @Override
    @Transactional(readOnly = true)
    CentralMonitoring findById(Integer id) {
        return centralMonitoringRepository.findOne(id);
    }
    
    /**
     * Find all centralMonitorings
     *
     * @return a List of CentralMonitorings
     */
    @Override
    @Transactional(readOnly = true)
    List<CentralMonitoring> findAll() {
        return centralMonitoringRepository.findAll();
    }
    
    
    /** This method saves to either existing object or creates a new object
     * @param id
     * @param code
     * @param description
     * @param warningColour
     * @return
     */
    @Transactional
    public CentralMonitoring saveCentralMonitoring(Integer id, String code, String description, String warningColour) {
        ValidationUtils.assertNotBlank(code, "code cannot be blank");
        ValidationUtils.assertNotNull(description, "description is mandatory");
        CentralMonitoring centralMonitoring = null;
        if (id != null) {
            centralMonitoring = findById(id);
            centralMonitoring.setCode(code);
            centralMonitoring.setDescription(description);
            centralMonitoring.setWarningColour(warningColour);
            save(centralMonitoring);
        } else {
            centralMonitoring = save(new CentralMonitoring(code, description, warningColour));
        }
        
        return centralMonitoring;
    }
    
    /**
     * This service method is used to save a complete CentralMonitoring object in the database
     *
     * @param centralMonitoring the new CentralMonitoring object to be saved
     * @return the saved version of the CentralMonitoring object
     */
    @Override
    @Transactional
    public CentralMonitoring save(CentralMonitoring centralMonitoring) {
        return centralMonitoringRepository.save(centralMonitoring)
    }
    
    /**
     * This service method is used to update an CentralMonitoring object in the database from a partial or complete CentralMonitoring object.
     *
     * @param centralMonitoring the partial or complete CentralMonitoring object to be saved
     * @return the saved version of the CentralMonitoring object
     */
    @Transactional
    public CentralMonitoring updateCentralMonitoring(CentralMonitoring centralMonitoring) {
        CentralMonitoring centralMonitoringToSave = findById(centralMonitoring.id);
        centralMonitoringToSave.code = centralMonitoring.code != null ? centralMonitoring.code : centralMonitoringToSave.code
        centralMonitoringToSave.description = centralMonitoring.description != null ? centralMonitoring.description : centralMonitoringToSave.description
        centralMonitoringToSave.warningColour = centralMonitoring.warningColour != null ? centralMonitoring.warningColour : centralMonitoringToSave.warningColour
        return save(centralMonitoringToSave)
    }
    
    /**
     * Saves a list of CentralMonitoring objects to the database
     *
     * @param centralMonitorings a list of CentralMonitorings to be saved to the database
     * @return the list of save CentralMonitoring objects
     */
    @Transactional
    public List<CentralMonitoring> saveCentralMonitorings(List<CentralMonitoring> centralMonitorings) {
        return centralMonitorings.collect { centralMonitoring -> save(centralMonitoring) };
    }
    
    /**
     * This methods throws an InvalidOperationException when called. CentralMonitoring should not be deleted.
     */
    @Override
    public void delete(CentralMonitoring obj) {
        throw new InvalidOperationException("CentralMonitoring should not be deleted.")
    }
}
