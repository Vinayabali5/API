package uk.ac.reigate.services.exam

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.security.access.prepost.PreAuthorize
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional

import uk.ac.reigate.domain.exam.EdiAuditFileLog
import uk.ac.reigate.exceptions.InvalidOperationException
import uk.ac.reigate.model.SearchResult;
import uk.ac.reigate.repositories.exam.EdiAuditFileLogRepository
import uk.ac.reigate.services.ICoreDataService


@Service
class EdiAuditFileLogService implements ICoreDataService<EdiAuditFileLog, Integer>{
    
    @Autowired
    EdiAuditFileLogRepository ediAuditFileLogRepository
    
    /**
     * Default No Args constructor
     */
    EdiAuditFileLogService() {}
    
    
    EdiAuditFileLogService(EdiAuditFileLogRepository ediAuditFileLogRepository) {
        this.ediAuditFileLogRepository = ediAuditFileLogRepository;
    }
    
    
    /* (non-Javadoc)
     * @see uk.ac.reigate.services.ICoreDataService#findAll()
     */
    @Transactional(readOnly = true)
    List<EdiAuditFileLog> findAll() {
        return ediAuditFileLogRepository.findAll();
    }
    
    
    /* (non-Javadoc)
     * @see uk.ac.reigate.services.ICoreDataService#save(java.lang.Object)
     */
    @Transactional
    public EdiAuditFileLog save(EdiAuditFileLog ediAuditFileLog) {
        return ediAuditFileLogRepository.save(ediAuditFileLog)
    }
    
    
    /* (non-Javadoc)
     * @see uk.ac.reigate.services.ICoreDataService#findById(java.lang.Object)
     */
    @Override
    public EdiAuditFileLog findById(Integer id) {
        return ediAuditFileLogRepository.findOne(id)
    }
    
    
    /* (non-Javadoc)
     * @see uk.ac.reigate.services.ICoreDataService#delete(java.lang.Object)
     */
    @Override
    public void delete(EdiAuditFileLog obj) {
        throw new InvalidOperationException("EdiAuditFileLog should not be deleted")
    }
}
