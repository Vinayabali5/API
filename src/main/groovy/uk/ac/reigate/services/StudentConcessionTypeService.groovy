package uk.ac.reigate.services

import java.util.List

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

import uk.ac.reigate.domain.academic.Student
import uk.ac.reigate.domain.learning_support.ConcessionType
import uk.ac.reigate.domain.learning_support.StudentConcessionType
import uk.ac.reigate.domain.learning_support.StudentConcessionTypePk
import uk.ac.reigate.repositories.learning_support.StudentConcessionTypeRepository;

@Service
class StudentConcessionTypeService implements ICoreDataService<StudentConcessionType, StudentConcessionTypePk>{
    
    @Autowired
    StudentConcessionTypeRepository studentConcessionTypeRepository
    
    /**
     * This service method is used to retrieve the list of studentConcessionType by studentId
     *
     * @param studentId
     * @return
     */
    public List<StudentConcessionType> getByStudent(Integer studentId){
        List<StudentConcessionType> students = studentConcessionTypeRepository.findByStudent_Id(studentId)
        return students
    }
    
    /**
     * This service method is used to save the studentConcessionType
     *
     * @param studentConcessionType
     * @return
     */
    @Override
    @Transactional
    public StudentConcessionType save(StudentConcessionType studentConcessionType) {
        return studentConcessionTypeRepository.save(studentConcessionType)
    }
    
    /**
     * This method is used to delete the studentConcessionType by studentId and ConcessionId
     *
     * @param studentId 
     * @param concessionTypeId
     * @return
     */
    public Boolean deleteByIds(Integer studentId, Integer concessionTypeId){
        StudentConcessionType studentConcessionType = studentConcessionTypeRepository.findByStudent_IdAndConcessionType_Id(studentId, concessionTypeId);
        if (studentConcessionType!=null) {
            delete(studentConcessionType);
            return true;
        }
        return false;
    }
    
    /**
     * @param student
     * @param concessionType
     * @return
     */
    public Boolean  deleteByStudentAndConcessionType(Student student, ConcessionType concessionType){
        return deleteByIds(student.id, concessionType.id);
    }
    
    
    /**
     * Find all StudentConcessionType
     *
     * @return a List of StudentConcessionType
     */
    @Override
    public List<StudentConcessionType> findAll() {
        return studentConcessionTypeRepository.findAll()
    }
    
    /**
     * Deletes StudentConcessionType object
     * 
     */
    @Override
    public void delete(StudentConcessionType studentConcessionType) {
        studentConcessionTypeRepository.delete(new StudentConcessionTypePk(studentConcessionType))
    }
    
    @Override
    public StudentConcessionType findById(StudentConcessionTypePk studentConcessionTypePk) {
        return studentConcessionTypeRepository.findOne(studentConcessionTypePk)
    }
}
