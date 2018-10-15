package uk.ac.reigate.services

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.security.access.prepost.PreAuthorize
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional


import uk.ac.reigate.domain.learning_support.StudentLearningSupport
import uk.ac.reigate.exceptions.InvalidOperationException
import uk.ac.reigate.model.SearchResult;
import uk.ac.reigate.repositories.learning_support.StudentLearningSupportRepository


@Service
class StudentLearningSupportService implements ICoreDataService<StudentLearningSupport, Integer>{
    
    @Autowired
    StudentLearningSupportRepository studentLearningSupportRepository
    
    /**
     * Default No Args constructor
     */
    StudentLearningSupportService() {}
    
    StudentLearningSupportService(StudentLearningSupportRepository studentLearningSupportRepository) {
        this.studentLearningSupportRepository = studentLearningSupportRepository
    }
    
    
    /**
     * @param studentId
     * @return
     */
    @Transactional(readOnly = true)
    StudentLearningSupport findStudentLearningSupport(Integer studentId) {
        return studentLearningSupportRepository.findByStudent_Id(studentId);
    }
    
    
    /**
     * Find all StudentLearningSupport
     *
     * @return a List of StudentLearningSupport
     */
    @Override
    @Transactional(readOnly = true)
    List<StudentLearningSupport> findAll() {
        return studentLearningSupportRepository.findAll();
    }
    
    /**
     * @param studentId
     * @return
     */
    @PreAuthorize("@securityChecker.checkReader(authentication)")
    public StudentLearningSupport findByStudent(Integer studentId){
        StudentLearningSupport students = studentLearningSupportRepository.findByStudent_Id(studentId)
        return students
    }
    
    /**
     * This service method is used to save a complete StudentLearningSupport object in the database
     *
     * @param studentLearningSupport the new StudentLearningSupport object to be saved
     * @return the saved version of the StudentLearningSupport object
     */
    @Override
    @Transactional
    public StudentLearningSupport save(StudentLearningSupport studentLearningSupport) {
        return studentLearningSupportRepository.save(studentLearningSupport)
    }
    
    /**
     * There is no id for StudentLearningSupport object , therefore returning null     
     *
     */
    @Override
    public StudentLearningSupport findById(Integer id) {
        return null
    }
    
    /**
     * This service method is used to delete a complete StudentInterimReport object in the database
     *
     * @param studentInterimReport the  StudentInterimReport object to be deleted
     *
     */
    @Override
    public void delete(StudentLearningSupport obj) {
        throw new InvalidOperationException("StudentLearningSupport should not be deleted")
    }
}
