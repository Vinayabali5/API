package uk.ac.reigate.services

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

import uk.ac.reigate.domain.lookup.StudentType
import uk.ac.reigate.exceptions.InvalidOperationException
import uk.ac.reigate.repositories.lookup.StudentTypeRepository
import uk.ac.reigate.utils.ValidationUtils

@Service
class StudentTypeService implements ICoreDataService<StudentType, Integer>{
    
    @Autowired
    StudentTypeRepository studentTypeRepository
    
    /**
     * Default NoArgs constructor
     */
    StudentTypeService() {}
    
    /**
     * Autowired Constructor
     *
     * @param studentTypeRepository
     */
    StudentTypeService(StudentTypeRepository studentTypeRepository) {
        this.studentTypeRepository = studentTypeRepository;
    }
    
    /**
     * Find an individual studentType using the studentTypes ID fields
     *
     * @param id the ID fields to search for
     * @return the StudentType object that matches the ID supplied, or null if not found
     */
    @Override
    @Transactional(readOnly = true)
    StudentType findById(Integer id) {
        return studentTypeRepository.findOne(id);
    }
    
    /**
     * Find a single page of StudentType objects
     * @return a List of StudentTypes
     */
    @Override
    @Transactional(readOnly = true)
    List<StudentType> findAll() {
        return studentTypeRepository.findAll();
    }
    
    /**
     * @param id
     * @param code
     * @param description
     * @return
     */
    @Transactional
    public StudentType saveStudentType(Integer id, String code, String description) {
        ValidationUtils.assertNotBlank(code, "code cannot be blank");
        ValidationUtils.assertNotNull(description, "description is mandatory");
        
        StudentType studentType = null;
        
        if (id != null) {
            studentType = findById(id);
            
            studentType.setCode(code);
            studentType.setDescription(description);
            
            save(studentType);
        } else {
            studentType = save(new StudentType(code, description));
        }
        
        return studentType;
    }
    
    /**
     * This service method is used to save a complete StudentType object in the database
     *
     * @param studentType the new StudentType object to be saved
     * @return the saved version of the StudentType object
     */
    @Override
    @Transactional
    public StudentType save(StudentType studentType) {
        return studentTypeRepository.save(studentType)
    }
    
    /**
     * This service method is used to update an StudentType object in the database from a partial or complete StudentType object.
     *
     * @param studentType the partial or complete StudentType object to be saved
     * @return the saved version of the StudentType object
     */
    @Transactional
    public StudentType updateStudentType(StudentType studentType) {
        StudentType studentTypeToSave = findById(studentType.id)
        studentTypeToSave.code = studentType.code != null ? studentType.code : studentTypeToSave.code
        studentTypeToSave.description = studentType.description != null ? studentType.description : studentTypeToSave.description
        return save(studentTypeToSave)
    }
    
    /**
     * Saves a list of StudentType objects to the database
     *
     * @param studentTypes a list of StudentTypes to be saved to the database
     * @return the list of save StudentType objects
     */
    @Transactional
    public List<StudentType> saveStudentTypes(List<StudentType> studentTypes) {
        return studentTypes.collect { studentType -> save(studentType) };
    }
    
    /**
     * This methods throws an InvalidOperationException when called. StudentType should not be deleted.
     */
    @Override
    public void delete(StudentType obj) {
        throw new InvalidOperationException("StudentType should not be deleted")
    }
}
