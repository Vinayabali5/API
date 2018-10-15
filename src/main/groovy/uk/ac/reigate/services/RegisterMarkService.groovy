package uk.ac.reigate.services

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

import uk.ac.reigate.domain.academic.Course
import uk.ac.reigate.domain.academic.CourseGroup
import uk.ac.reigate.domain.academic.Student
import uk.ac.reigate.domain.register.AttendanceCode
import uk.ac.reigate.domain.register.Register
import uk.ac.reigate.domain.register.RegisterMark
import uk.ac.reigate.exceptions.InvalidOperationException
import uk.ac.reigate.repositories.register.RegisterMarkRepository
import uk.ac.reigate.utils.ValidationUtils

@Service
class RegisterMarkService implements ICoreDataService<RegisterMark, Integer>{
    
    @Autowired
    RegisterMarkRepository registerMarkRepository
    
    /**
     * Default NoArgs constructor
     */
    RegisterMarkService() {}
    
    /**
     * Autowired Constructor
     *
     * @param registerMarkRepository
     */
    RegisterMarkService(RegisterMarkRepository registerMarkRepository) {
        this.registerMarkRepository = registerMarkRepository
    }
    
    /**
     * Find an individual registerMark using the registerMarks ID fields
     *
     * @param id the ID fields to search for
     * @return the RegisterMark object that matches the ID supplied, or null if not found
     */
    @Override
    @Transactional(readOnly = true)
    RegisterMark findById(Integer id) {
        return registerMarkRepository.findOne(id);
    }
    
    /**
     * Find all registerMarks
     *
     * @return a List of RegisterMarks
     */
    @Override
    @Transactional(readOnly = true)
    List<RegisterMark> findAll() {
        return registerMarkRepository.findAll();
    }
    
    
    /**
     * @param id
     * @param course
     * @param courseGroup
     * @param register
     * @param student
     * @param attendanceCode
     * @return
     */
    @Transactional
    public RegisterMark saveRegisterMark(Integer id, Course course, CourseGroup courseGroup, Register register, Student student, AttendanceCode attendanceCode) {
        ValidationUtils.assertNotBlank(course, "course cannot be blank");
        ValidationUtils.assertNotNull(courseGroup, "courseGroup is mandatory");
        
        RegisterMark registerMark = null;
        
        if (id != null) {
            registerMark = findById(id);
            
            registerMark.setCourse(course);
            registerMark.setCourseGroup(courseGroup);
            registerMark.setRegister(register);
            registerMark.setStudent(student);
            registerMark.setAttendanceCode(attendanceCode);
            
            
            save(registerMark);
        } else {
            registerMark = save(new RegisterMark(course, courseGroup, register, student, attendanceCode));
        }
        
        return registerMark;
    }
    
    /**
     * This service method is used to save a complete RegisterMark object in the database
     *
     * @param registerMark the new RegisterMark object to be saved
     * @return the saved version of the RegisterMark object
     */
    @Override
    @Transactional
    public RegisterMark save(RegisterMark registerMark) {
        return registerMarkRepository.save(registerMark)
    }
    
    /**
     * Saves a list of RegisterMark objects to the database
     *
     * @param registerMarks a list of RegisterMarks to be saved to the database
     * @return the list of save RegisterMark objects
     */
    @Transactional
    public List<RegisterMark> saveRegisterMarks(List<RegisterMark> registerMarks) {
        return registerMarks.collect { registerMark -> save(registerMark) };
    }
    
    /**
     * This methods throws an InvalidOperationException when called. QualificationAssessment should not be deleted.
     */
    @Override
    public void delete(RegisterMark obj) {
        throw new InvalidOperationException("RegisterMark should not be deleted")
    }
}
