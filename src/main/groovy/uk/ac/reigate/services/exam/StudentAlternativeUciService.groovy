package uk.ac.reigate.services.exam

import java.util.List

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.security.access.prepost.PreAuthorize
import org.springframework.stereotype.Service
import uk.ac.reigate.domain.exam.StudentAlternativeUci
import uk.ac.reigate.domain.exam.StudentAlternativeUciPk
import uk.ac.reigate.repositories.exam.StudentAlternativeUciRepository
import uk.ac.reigate.services.ICoreDataService

@Service
class StudentAlternativeUciService implements ICoreDataService<StudentAlternativeUci,StudentAlternativeUciPk>{
    
    @Autowired
    StudentAlternativeUciRepository studentAlternativeUciRepository
    
    StudentAlternativeUciService(){}
    
    
    /**
     * @param studentId
     * @return
     */
    @PreAuthorize("@securityChecker.checkReader(authentication)")
    public List<StudentAlternativeUci> getByStudent(Integer studentId){
        return studentAlternativeUciRepository.findByStudent_Id(studentId)
    }
    
    /** Returns a individual StudentAlternativeUci 
     * @param studentId
     * @param examBoardId
     * @return 
     */
    public StudentAlternativeUci getByStudentAndExamBoardId(Integer studentId,Integer examBoardId){
        findById(new StudentAlternativeUciPk(studentId,examBoardId))
    }
    
    /* (non-Javadoc)
     * @see uk.ac.reigate.services.ICoreDataService#save(java.lang.Object)     *   
     */
    @Override
    public StudentAlternativeUci save(StudentAlternativeUci studentAlternativeUci){
        studentAlternativeUciRepository.save(studentAlternativeUci)
    }
    
    /** Deletes an instance of StudentAlterntativeUci of a particular studentId and examBoardId
     * @param studentId
     * @param examBoardId
     * @return
     */
    public boolean deleteAltenativeUci(Integer studentId,Integer examBoardId){
        StudentAlternativeUci studentAlternativeUci = getByStudentAndExamBoardId(studentId, examBoardId)
        delete(studentAlternativeUci);
    }
    
    /* (non-Javadoc)
     * @see uk.ac.reigate.services.ICoreDataService#findById(java.lang.Object)
     */
    @Override
    public StudentAlternativeUci findById(StudentAlternativeUciPk studentAlternativeUciPk) {
        return studentAlternativeUciRepository.findOne(studentAlternativeUciPk)
    }
    
    /* (non-Javadoc)
     * @see uk.ac.reigate.services.ICoreDataService#findAll()
     */
    @Override
    public List<StudentAlternativeUci> findAll() {
        return studentAlternativeUciRepository.findAll();
    }
    
    /* (non-Javadoc)
     * @see uk.ac.reigate.services.ICoreDataService#delete(java.lang.Object)
     */
    @Override
    public void delete(StudentAlternativeUci studentAlternativeUci) {
        studentAlternativeUciRepository.delete(new StudentAlternativeUciPk(studentAlternativeUci))
    }
}
