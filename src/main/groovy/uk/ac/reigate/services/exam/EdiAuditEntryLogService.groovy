package uk.ac.reigate.services.exam

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.security.access.prepost.PreAuthorize
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

import uk.ac.reigate.domain.academic.Student
import uk.ac.reigate.domain.exam.EdiAuditEntryLog
import uk.ac.reigate.domain.exam.EdiAuditEntryLogPk
import uk.ac.reigate.domain.exam.ExamOption
import uk.ac.reigate.exceptions.InvalidOperationException
import uk.ac.reigate.repositories.exam.EdiAuditEntryLogRepository
import uk.ac.reigate.services.ICoreDataService


@Service
class EdiAuditEntryLogService implements ICoreDataService<EdiAuditEntryLog, EdiAuditEntryLogPk>{
    
    @Autowired
    EdiAuditEntryLogRepository ediAuditEntryLogRepository
    
    /**
     * Default No Args constructor
     */
    EdiAuditEntryLogService() {}
    
    
    EdiAuditEntryLogService(EdiAuditEntryLogRepository ediAuditEntryLogRepository) {
        this.ediAuditEntryLogRepository = ediAuditEntryLogRepository;
    }
    
    /**
     * @param student
     * @param examOption
     * @return
     */
    @Transactional(readOnly = true)
    EdiAuditEntryLog findByStudentAndOption(Student student, ExamOption examOption){
        findById(new EdiAuditEntryLogPk(student, examOption))
    }
    
    /**
     * @param student
     * @return
     */
    @Transactional(readOnly = true)
    EdiAuditEntryLog findByStudent(Student student){
        findById(new EdiAuditEntryLogPk(student))
    }
    
    /**
     * @param studentId
     * @return
     */
    EdiAuditEntryLog findByStudent(Integer studentId){
        return ediAuditEntryLogRepository.findByStudent_Id(studentId)
    }
    
    /**
     * @return
     */
    @Transactional(readOnly = true)
    List<EdiAuditEntryLog> findAll() {
        return ediAuditEntryLogRepository.findAll();
    }
    
    /**
     * @param examYear
     * @return
     */
    @PreAuthorize("@securityChecker.checkReader(authentication)")
    public List<EdiAuditEntryLog> getByYear(String examYear){
        List<EdiAuditEntryLog> students = ediAuditEntryLogRepository.findByExamOption(examYear)
        return students
    }
    
    /**
     * @param studentId
     * @param examYear
     * @return
     */
    @PreAuthorize("@securityChecker.checkReader(authentication)")
    public List<EdiAuditEntryLog> getByStudentAndYear(Integer studentId, String examYear){
        List<EdiAuditEntryLog> students = ediAuditEntryLogRepository.findBystduent_IdAndExamOption(studentId, examYear)
        return students
    }
    
    
    /* (non-Javadoc)
     * @see uk.ac.reigate.services.ICoreDataService#save(java.lang.Object)
     */
    @Transactional
    public EdiAuditEntryLog save(EdiAuditEntryLog ediAuditEntryLog) {
        return ediAuditEntryLogRepository.save(ediAuditEntryLog)
    }
    
    
    /* (non-Javadoc)
     * @see uk.ac.reigate.services.ICoreDataService#findById(java.lang.Object)
     */
    @Override
    public EdiAuditEntryLog findById(EdiAuditEntryLogPk id) {
        return ediAuditEntryLogRepository.findOne(id);
    }
    
    
    /* (non-Javadoc)
     * @see uk.ac.reigate.services.ICoreDataService#delete(java.lang.Object)
     */
    @Override
    public void delete(EdiAuditEntryLog obj) {
        throw new InvalidOperationException("EdiAuditEntryLog should not be deleted")
    }
}
