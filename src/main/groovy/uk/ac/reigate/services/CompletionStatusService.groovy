package uk.ac.reigate.services

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

import uk.ac.reigate.domain.ilr.CompletionStatus
import uk.ac.reigate.exceptions.InvalidOperationException
import uk.ac.reigate.repositories.ilr.CompletionStatusRepository
import uk.ac.reigate.utils.ValidationUtils

@Service
class CompletionStatusService implements ICoreDataService<CompletionStatus, Integer> {
    
    @Autowired
    CompletionStatusRepository completionStatusRepository
    
    /**
     * Default NoArgs constructor
     */
    CompletionStatusService() {}
    
    /**
     * Autowired Constructor
     *
     * @param completionStatusRepository
     */
    CompletionStatusService(CompletionStatusRepository completionStatusRepository){
        this.completionStatusRepository = completionStatusRepository
    }
    
    /**
     * Find an individual completionStatus using the completionStatuses ID fields
     *
     * @param id the ID fields to search for
     * @return the CompletionStatus object that matches the ID supplied, or null if not found
     */
    @Override
    @Transactional(readOnly = true)
    CompletionStatus findById(Integer id) {
        return completionStatusRepository.findOne(id)
    }
    
    /**
     * Find all completionStatuses
     *
     * @return a SearchResult set with the list of CompletionStatuses
     */
    @Override
    @Transactional(readOnly = true)
    List<CompletionStatus> findAll() {
        return completionStatusRepository.findAll()
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
    public CompletionStatus saveCompletionStatus(Integer id, String code, String description, String shortDescription,  Date validFrom, Date validTo) {
        ValidationUtils.assertNotBlank(code, "code cannot be blank")
        ValidationUtils.assertNotNull(description, "description is mandatory")
        CompletionStatus completionStatus = null
        if (id != null) {
            completionStatus = findById(id)
            completionStatus.setCode(code)
            completionStatus.setDescription(description)
            completionStatus.setShortDescription(shortDescription)
            completionStatus.setValidFrom(validFrom)
            completionStatus.setValidTo(validTo)
            save(completionStatus)
        } else {
            completionStatus = save(new CompletionStatus(code, description, shortDescription, validFrom, validTo))
        }
        
        return completionStatus
    }
    
    /**
     * This service method is used to save a complete CompletionStatus object in the database
     *
     * @param completionStatus the new CompletionStatus object to be saved
     * @return the saved version of the CompletionStatus object
     */
    @Override
    @Transactional
    public CompletionStatus save(CompletionStatus completionStatus) {
        return completionStatusRepository.save(completionStatus)
    }
    
    /**
     * This service method is used to update an CompletionStatus object in the database from a partial or complete CompletionStatus object.
     *
     * @param completionStatus the partial or complete CompletionStatus object to be saved
     * @return the saved version of the CompletionStatus object
     */
    @Transactional
    public CompletionStatus updateCompletionStatus(CompletionStatus completionStatus) {
        CompletionStatus completionStatusToSave = findById(completionStatus.id)
        completionStatusToSave.code = completionStatus.code != null ? completionStatus.code : completionStatusToSave.code
        completionStatusToSave.description = completionStatus.description != null ? completionStatus.description : completionStatusToSave.description
        completionStatusToSave.shortDescription = completionStatus.shortDescription != null ? completionStatus.shortDescription : completionStatusToSave.shortDescription
        completionStatusToSave.validFrom = completionStatus.validFrom != null ? completionStatus.validFrom : completionStatusToSave.validFrom
        completionStatusToSave.validTo = completionStatus.validTo != null ? completionStatus.validTo : completionStatusToSave.validTo
        return save(completionStatusToSave)
    }
    
    /**
     * Saves a list of CompletionStatus objects to the database
     *
     * @param completionStatuses a list of CompletionStatuses to be saved to the database
     * @return the list of save CompletionStatus objects
     */
    @Transactional
    public List<CompletionStatus> saveCompletionStatuses(List<CompletionStatus> completionStatuses) {
        return completionStatuses.collect { completionStatus -> save(completionStatus) }
    }
    /**
     * This methods throws an InvalidOperationException when called. CompletionStatus should not be deleted.
     */
    @Override
    public void delete(CompletionStatus obj) {
        throw new InvalidOperationException("CompletionStatus should not be deleted")
    }
}
