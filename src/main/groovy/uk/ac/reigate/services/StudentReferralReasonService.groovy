package uk.ac.reigate.services

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

import uk.ac.reigate.domain.learning_support.StudentReferralReason
import uk.ac.reigate.domain.learning_support.StudentReferralReasonPk
import uk.ac.reigate.repositories.learning_support.StudentReferralReasonRepository


@Service
class StudentReferralReasonService implements ICoreDataService<StudentReferralReason, StudentReferralReasonPk>{
    
    @Autowired
    StudentReferralReasonRepository studentReferralReasonRepository
    
    /**
     * Default No Args constructor
     */
    StudentReferralReasonService() {}
    
    /**
     * @param studentId
     * @return
     */
    public List<StudentReferralReason> getByStudent(Integer studentId){
        List<StudentReferralReason> students = studentReferralReasonRepository.findByStudent_Id(studentId)
        return students
    }
    
    /**
     * This service method is used to save a complete instance of a StudentReferralReason object to the database.
     *
     * @param studentReferralReason a complete StudentReferralReason object to persist to the database
     * @return the saved version of the StudentReferralReason object
     */
    @Override
    @Transactional
    public StudentReferralReason save(StudentReferralReason studentReferralReason) {
        return studentReferralReasonRepository.save(studentReferralReason)
    }
    /**
     * 
     * @return a List of StudentTypes
     */
    @Override
    public List<StudentReferralReason> findAll() {
        return studentReferralReasonRepository.findAll()
    }
    
    /**
     * This methods throws an InvalidOperationException when called. StudentReferralReason should not be deleted.
     */
    @Override
    public void delete(StudentReferralReason obj) {
        studentReferralReasonRepository.delete(new StudentReferralReasonPk(obj))
    }
    
    @Override
    public StudentReferralReason findById(StudentReferralReasonPk studentReferralReasonPk) {
        return studentReferralReasonRepository.findOne(studentReferralReasonPk)
    }
    
    
    public deleteByStudent(Integer studentId, Integer referralReasonId){
        StudentReferralReason studentReferralReason= studentReferralReasonRepository.findByStudent_IdAndReferralReason_Id(studentId, referralReasonId)
        delete(studentReferralReason)
    }
}
