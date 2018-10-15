package uk.ac.reigate.services

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

import uk.ac.reigate.domain.academic.StudentYear
import uk.ac.reigate.domain.lookup.WarningCodeChange
import uk.ac.reigate.exceptions.InvalidOperationException
import uk.ac.reigate.repositories.lookup.WarningCodeChangeRepository
import uk.ac.reigate.utils.ValidationUtils

@Service
class WarningCodeChangeService implements ICoreDataService<WarningCodeChange, Integer>{
    
    @Autowired
    WarningCodeChangeRepository warningCodeChangeRepository
    
    /**
     * Default NoArgs constructor
     */
    WarningCodeChangeService() {}
    
    /**
     * Autowired Constructor
     *
     * @param warningCodeChangeRepository
     */
    WarningCodeChangeService(WarningCodeChangeRepository warningCodeChangeRepository) {
        this.warningCodeChangeRepository = warningCodeChangeRepository
    }
    
    /**
     * Find an individual warningCodeChange using the warningCodeChanges ID fields
     *
     * @param id the ID fields to search for
     * @return the WarningCodeChange object that matches the ID supplied, or null if not found
     */
    @Override
    @Transactional(readOnly = true)
    WarningCodeChange findById(Integer id) {
        return warningCodeChangeRepository.findOne(id)
    }
    
    @Transactional(readOnly = true)
    List<WarningCodeChange> findByStudentId(Integer studentId) {
        return warningCodeChangeRepository.findByStudent_Id(studentId)
    }
    
    /**
     * Find all warningCodeChanges
     *
     * @return a SearchResult set with the list of WarningCodeChanges
     */
    @Override
    @Transactional(readOnly = true)
    List<WarningCodeChange> findAll() {
        return warningCodeChangeRepository.findAll()
    }
    
    @Transactional(readOnly = true)
    List<WarningCodeChange> getByStudentAndYear(Integer studentId, Integer yearId){
        return warningCodeChangeRepository.findByStudent_IdAndYear_Id(studentId, yearId)
    }
    
    @Override
    public void delete(WarningCodeChange obj) {
        throw new InvalidOperationException("WarningCodeChange should not be deleted")
    }
    
    @Override
    @Transactional
    public WarningCodeChange save(WarningCodeChange warningCodeChange) {
        return warningCodeChangeRepository.save(warningCodeChange)
    }
}
