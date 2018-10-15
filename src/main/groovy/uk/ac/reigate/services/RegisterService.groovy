package uk.ac.reigate.services

//import static org.springframework.util.Assert

import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import org.springframework.security.access.prepost.PreAuthorize
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

import uk.ac.reigate.domain.Staff;
import uk.ac.reigate.domain.academic.CourseGroup
import uk.ac.reigate.domain.register.Register
import uk.ac.reigate.domain.register.RegisteredSession;
import uk.ac.reigate.exceptions.InvalidOperationException
import uk.ac.reigate.model.PageInfo
import uk.ac.reigate.model.SearchResult
import uk.ac.reigate.repositories.register.RegisterRepository
import uk.ac.reigate.utils.ValidationUtils;

@Service
class RegisterService implements ICoreDataService<Register, Integer>{
    
    @Autowired
    RegisterRepository registerRepository
    
    /**
     * Default NoArgs constructor
     */
    RegisterService() {}
    
    /**
     * Autowired Constructor
     *
     * @param registerRepository
     */
    RegisterService(RegisterRepository registerRepository) {
        this.registerRepository = registerRepository
    }
    
    /**
     * Find an individual register using the registers ID fields
     *
     * @param id the ID fields to search for
     * @return the Register object that matches the ID supplied, or null if not found
     */
    @Override
    @Transactional(readOnly = true)
    Register findById(Integer id) {
        return registerRepository.findOne(id);
    }
    
    /**
     * Find all registers
     *
     * @return a SearchResult set with the list of Registers
     */
    @Override
    @Transactional(readOnly = true)
    List<Register> findAll() {
        return registerRepository.findAll();
    }
    
    
    /**
     * @param id
     * @param session
     * @param courseGroup
     * @param completed
     * @param staffCompleted
     * @param dateCompleted
     * @return
     */
    @Transactional
    public Register saveRegister(Integer id, RegisteredSession session, CourseGroup courseGroup, Boolean completed, Staff staffCompleted, Date dateCompleted) {
        ValidationUtils.assertNotBlank(session, "session cannot be blank");
        ValidationUtils.assertNotNull(courseGroup, "courseGroup is mandatory");
        
        Register register = null;
        
        if (id != null) {
            register = findById(id);
            
            register.setSession(session);
            register.setCourseGroup(courseGroup);
            register.setCompleted(completed);
            register.setStaffCompleted(staffCompleted);
            register.setDateCompleted(dateCompleted);
            
            
            save(register);
        } else {
            register = save(new Register(session, courseGroup, completed, staffCompleted, dateCompleted));
        }
        
        return register;
    }
    
    /**
     * This service method is used to save a complete Register object in the database
     *
     * @param register the new Register object to be saved
     * @return the saved version of the Register object
     */
    @Override
    @Transactional
    public Register save(Register register) {
        return registerRepository.save(register)
    }
    
    /**
     * Saves a list of Register objects to the database
     *
     * @param registers a list of Registers to be saved to the database
     * @return the list of save Register objects
     */
    
    @Transactional
    public List<Register> saveRegisters(List<Register> registers) {
        return registers.collect { register -> save(register) };
    }
    
    /**
     * This methods throws an InvalidOperationException when called. QualificationAssessment should not be deleted.
     */
    @Override
    public void delete(Register obj) {
        throw new InvalidOperationException("Register should not be deleted")
        
    }
    
    
    
}
