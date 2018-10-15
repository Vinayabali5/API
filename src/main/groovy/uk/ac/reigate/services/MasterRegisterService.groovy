package uk.ac.reigate.services

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.security.access.prepost.PreAuthorize
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

import uk.ac.reigate.domain.academic.Student
import uk.ac.reigate.domain.cristal.MasterRegister
import uk.ac.reigate.domain.register.AttendanceCode
import uk.ac.reigate.exceptions.InvalidOperationException
import uk.ac.reigate.repositories.academic.StudentRepository
import uk.ac.reigate.repositories.cristal.MasterRegisterRepository
import uk.ac.reigate.repositories.register.AttendanceCodeRepository
import uk.ac.reigate.utils.ValidationUtils

@Service
class MasterRegisterService implements ICoreDataService<MasterRegister, Integer>{
    
    @Autowired
    MasterRegisterRepository masterRegisterRepository
    
    @Autowired
    StudentRepository studentRepository
    
    @Autowired
    AttendanceCodeRepository attendanceCodeRepository
    
    /**
     * Default NoArgs constructor
     */
    MasterRegisterService() {}
    
    /**
     * Autowired Constructor
     *
     * @param masterRegisterRepository
     */
    MasterRegisterService(MasterRegisterRepository masterRegisterRepository) {
        this.masterRegisterRepository = masterRegisterRepository;
    }
    /**
     * Find an individual MasterRegister using the MasterRegister ID fields
     *
     * @param id the ID fields to search for
     * @return the MasterRegister object that matches the ID supplied, or null if not found
     */
    @Override
    @Transactional(readOnly = true)
    MasterRegister findById(Integer id) {
        return masterRegisterRepository.findOne(id);
    }
    
    /**
     * Find a single page of MasterRegister objects
     * @return a SearchResult set with the list of MasterRegisters
     */
    @Override
    @Transactional(readOnly = true)
    List<MasterRegister> findAll() {
        return masterRegisterRepository.findAll();
    }
    
    /**
     *
     * @param id
     * @param code
     * @param description
     * @return
     */
    @Transactional
    public MasterRegister saveMasterRegister(Integer id, Integer sessionRef, Student student, String subjectCode, String group, AttendanceCode attendance) {
        ValidationUtils.assertNotBlank(sessionRef, "sessionRef cannot be blank");
        MasterRegister masterRegister = null;
        if (id != null) {
            masterRegister = findById(id);
            masterRegister.setSessionRef(sessionRef);
            masterRegister.setStudent(student);
            masterRegister.setSubjectCode(subjectCode);
            masterRegister.setGroup(group);
            masterRegister.setAttendance(attendance);
            save(masterRegister);
        } else {
            masterRegister = save(new MasterRegister(sessionRef, student, subjectCode, group, attendance));
        }
        return masterRegister;
    }
    
    /**
     * This service method is used to save a complete MasterRegister object in the database
     *
     * @param masterRegister the new MasterRegister object to be saved
     * @return the saved version of the MasterRegister object
     */
    @Override
    @Transactional
    public MasterRegister save(MasterRegister masterRegister) {
        return masterRegisterRepository.save(masterRegister)
    }
    
    /**
     * This service method is used to update an MasterRegister object in the database from a partial or complete MasterRegister object.
     *
     * @param masterRegister the partial or complete MasterRegister object to be saved
     * @return the saved version of the MasterRegister object
     */
    @Transactional
    public MasterRegister updateMasterRegister(MasterRegister masterRegister) {
        MasterRegister masterRegisterToSave = findById(masterRegister.id)
        masterRegisterToSave.sessionRef = masterRegister.sessionRef != null ? masterRegister.sessionRef : masterRegisterToSave.sessionRef
        masterRegisterToSave.student = masterRegister.student.id != null ? studentRepository.findOne(masterRegister.student.id) : masterRegisterToSave.student
        masterRegisterToSave.subjectCode = masterRegister.subjectCode != null ? masterRegister.subjectCode : masterRegisterToSave.subjectCode
        masterRegisterToSave.group = masterRegister.group != null ? masterRegister.group : masterRegisterToSave.group
        masterRegisterToSave.attendance = masterRegister.attendance.id != null ? attendanceCodeRepository.findOne(masterRegister.attendance.id) : masterRegisterToSave.attendance
        return save(masterRegisterToSave)
    }
    
    /**
     * Saves a list of MasterRegister objects to the database
     *
     * @param masterRegisters a list of MasterRegisters to be saved to the database
     * @return the list of save MasterRegister objects
     */
    @Transactional
    public List<MasterRegister> saveMasterRegisters(List<MasterRegister> masterRegisters) {
        return masterRegisters.collect { masterRegister -> save(masterRegister) };
    }
    
    /**
     * @param studentId
     * @return
     */
    @PreAuthorize("@securityChecker.checkReader(authentication)")
    public  List<MasterRegister> getByStudent(Integer studentId){
        return masterRegisterRepository.findByStudent_Id(studentId);
    }
    
    
    /**
     * @param studentId
     * @return
     */
    @PreAuthorize("@securityChecker.checkReader(authentication)")
    public  List<MasterRegister> getByStudentAndYear(Integer studentId, Integer yearId){
        return masterRegisterRepository.findByStudentIdAndAcademicYearId(studentId, yearId);
    }
    
    /**
     * This methods throws an InvalidOperationException when called. MasterRegister should not be deleted.
     */
    @Override
    public void delete(MasterRegister obj) {
        throw new InvalidOperationException("MasterRegister should not be deleted")
    }
}
