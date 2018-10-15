package uk.ac.reigate.services

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

import uk.ac.reigate.domain.ilr.WithdrawalReason
import uk.ac.reigate.exceptions.InvalidOperationException
import uk.ac.reigate.repositories.ilr.WithdrawalReasonRepository
import uk.ac.reigate.utils.ValidationUtils

@Service
class WithdrawalReasonService implements ICoreDataService<WithdrawalReason, Integer> {
    
    @Autowired
    WithdrawalReasonRepository withdrawalReasonRepository
    
    /**
     * Default NoArgs constructor
     */
    WithdrawalReasonService() {}
    
    /**
     * Autowired Constructor
     *
     * @param withdrawalReasonRepository
     */
    WithdrawalReasonService(WithdrawalReasonRepository withdrawalReasonRepository) {
        this.withdrawalReasonRepository = withdrawalReasonRepository
    }
    
    /**
     * Find an individual withdrawalReason using the withdrawalReasons ID fields
     *
     * @param id the ID fields to search for
     * @return the WithdrawalReason object that matches the ID supplied, or null if not found
     */
    @Override
    @Transactional(readOnly = true)
    WithdrawalReason findById(Integer id) {
        return withdrawalReasonRepository.findOne(id)
    }
    
    /**
     * Find all withdrawalReasons
     *
     * @return a SearchResult set with the list of WithdrawalReasons
     */
    @Override
    @Transactional(readOnly = true)
    List<WithdrawalReason> findAll() {
        return withdrawalReasonRepository.findAll()
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
    public WithdrawalReason saveWithdrawalReason(Integer id, String code, String description, String shortDescription,  Date validFrom, Date validTo) {
        ValidationUtils.assertNotBlank(code, "code cannot be blank")
        ValidationUtils.assertNotNull(description, "description is mandatory")
        
        WithdrawalReason withdrawalReason = null
        
        if (id != null) {
            withdrawalReason = findById(id)
            withdrawalReason.setCode(code)
            withdrawalReason.setDescription(description)
            withdrawalReason.setShortDescription(shortDescription)
            withdrawalReason.setValidFrom(validFrom)
            withdrawalReason.setValidTo(validTo)
            
            save(withdrawalReason)
        } else {
            withdrawalReason = save(new WithdrawalReason(code, description, shortDescription, validFrom, validTo))
        }
        
        return withdrawalReason
    }
    
    /**
     * This service method is used to save a complete WithdrawalReason object in the database
     *
     * @param withdrawalReasonr the new WithdrawalReason object to be saved
     * @return the saved version of the WithdrawalReason object
     */
    @Override
    @Transactional
    public WithdrawalReason save(WithdrawalReason withdrawalReason) {
        return withdrawalReasonRepository.save(withdrawalReason)
    }
    
    /**
     * This service method is used to update an WithdrawalReason object in the database from a partial or complete WithdrawalReason object.
     *
     * @param withdrawalReason the partial or complete WithdrawalReason object to be saved
     * @return the saved version of the WithdrawalReason object
     */
    @Transactional
    public WithdrawalReason updateWithdrawalReason(WithdrawalReason withdrawalReason) {
        WithdrawalReason withdrawalReasonToSave = findById(withdrawalReason.id)
        withdrawalReasonToSave.code = withdrawalReason.code != null ? withdrawalReason.code : withdrawalReasonToSave.code
        withdrawalReasonToSave.description = withdrawalReason.description != null ? withdrawalReason.description : withdrawalReasonToSave.description
        withdrawalReasonToSave.shortDescription = withdrawalReason.shortDescription != null ? withdrawalReason.shortDescription : withdrawalReasonToSave.shortDescription
        withdrawalReasonToSave.validFrom = withdrawalReason.validFrom != null ? withdrawalReason.validFrom : withdrawalReasonToSave.validFrom
        withdrawalReasonToSave.validTo = withdrawalReason.validTo != null ? withdrawalReason.validTo : withdrawalReasonToSave.validTo
        return save(withdrawalReasonToSave)
    }
    
    /**
     * Saves a list of WithdrawalReason objects to the database
     *
     * @param withdrawalReasons a list of WithdrawalReasons to be saved to the database
     * @return the list of save WithdrawalReason objects
     */
    @Transactional
    public List<WithdrawalReason> saveWithdrawalReasons(List<WithdrawalReason> withdrawalReasons) {
        return withdrawalReasons.collect { withdrawalReason -> save(withdrawalReason) }
    }
    
    /**
     * This methods throws an InvalidOperationException when called. WithdrawalReason should not be deleted.
     */
    @Override
    public void delete(WithdrawalReason obj) {
        throw new InvalidOperationException("WithdrawalReason should not be deleted")
    }
}
