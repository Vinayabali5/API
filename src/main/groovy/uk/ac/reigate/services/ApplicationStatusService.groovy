package uk.ac.reigate.services

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

import uk.ac.reigate.domain.admissions.ApplicationStatus
import uk.ac.reigate.exceptions.InvalidOperationException
import uk.ac.reigate.repositories.admissions.ApplicationStatusRepository
import uk.ac.reigate.utils.ValidationUtils

@Service
class ApplicationStatusService implements ICoreDataService<ApplicationStatus, Integer> {
    
    @Autowired
    ApplicationStatusRepository applicationStatusRepository
    
    /**
     * Default NoArgs constructor
     */
    ApplicationStatusService() {}
    
    /**
     * Autowired Constructor
     *
     * @param applicationStatusRepository
     */
    ApplicationStatusService(ApplicationStatusRepository applicationStatusRepository) {
        this.applicationStatusRepository = applicationStatusRepository
    }
    
    /**
     * Find an individual applicationStatus using the applicationStatuses ID fields
     *
     * @param id the ID fields to search for
     * @return the ApplicationStatus object that matches the ID supplied, or null if not found
     */
    @Override
    @Transactional(readOnly = true)
    ApplicationStatus findById(Integer id) {
        return applicationStatusRepository.findOne(id);
    }
    
    /**
     * Find all applicationStatuses
     *
     * @return a List set with the list of ApplicationStatuses
     */
    @Override
    @Transactional(readOnly = true)
    List<ApplicationStatus> findAll() {
        return applicationStatusRepository.findAll();
    }
    
    
    /** This method saves to either existing object or creates a new object
     * @param id
     * @param code
     * @param description
     * @param considerWithdrawn
     * @return
     */
    @Transactional
    public ApplicationStatus saveApplicationStatus(Integer id, String code, String description, Boolean considerWithdrawn) {
        ValidationUtils.assertNotNull(code, "code is mandatory");
        ApplicationStatus applicationStatus = null;
        if (id != null) {
            applicationStatus = findById(id);
            applicationStatus.setCode(code);
            applicationStatus.setDescription(description);
            applicationStatus.setConsiderWithdrawn(considerWithdrawn)
            save(applicationStatus);
        } else {
            applicationStatus = save(new ApplicationStatus(code, description, considerWithdrawn));
        }
        return applicationStatus;
    }
    
    /**
     * This service method is used to save a complete ApplicationStatus object in the database
     *
     * @param applicationStatus the new ApplicationStatus object to be saved
     * @return the saved version of the ApplicationStatus object
     */
    @Override
    @Transactional
    public ApplicationStatus save(ApplicationStatus applicationStatus) {
        return applicationStatusRepository.save(applicationStatus)
    }
    
    /**
     * This service method is used to update an ApplicationStatus object in the database from a partial or complete ApplicationStatus object.
     *
     * @param applicationStatus the partial or complete ApplicationStatus object to be saved
     * @return the saved version of the ApplicationStatus object
     */
    @Transactional
    public ApplicationStatus updateApplicationStatus(ApplicationStatus applicationStatus) {
        ApplicationStatus applicationStatusToSave = findById(applicationStatus.id)
        applicationStatusToSave.code = applicationStatus.code != null ? applicationStatus.code : applicationStatusToSave.code
        applicationStatusToSave.description = applicationStatus.description != null ? applicationStatus.description : applicationStatusToSave.description
        applicationStatusToSave.considerWithdrawn = applicationStatus.considerWithdrawn != null ? applicationStatus.considerWithdrawn : applicationStatusToSave.considerWithdrawn
        return save(applicationStatusToSave)
    }
    
    /**
     * Saves a list of ApplicationStatus objects to the database
     *
     * @param applicationStatuses a list of ApplicationStatuses to be saved to the database
     * @return the list of save ApplicationStatus objects
     */
    @Transactional
    public List<ApplicationStatus> saveApplicationStatuses(List<ApplicationStatus> applicationStatuses) {
        return applicationStatuses.collect { applicationStatus -> save(applicationStatus) };
    }
    
    /**
     * This methods throws an InvalidOperationException when called. ApplicationStatus should not be deleted.
     */
    @Override
    @Transactional
    public void delete(ApplicationStatus obj) {
        throw new InvalidOperationException("ApplicationStatus should not be deleted.")
    }
}
