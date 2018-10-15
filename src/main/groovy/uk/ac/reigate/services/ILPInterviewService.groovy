package uk.ac.reigate.services

//import static org.springframework.util.Assert

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.security.access.prepost.PreAuthorize
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

import uk.ac.reigate.domain.Staff;
import uk.ac.reigate.domain.academic.CourseGroup
import uk.ac.reigate.domain.academic.Student
import uk.ac.reigate.domain.ilp.ILPInterview
import uk.ac.reigate.domain.ilp.ILPInterviewType
import uk.ac.reigate.exceptions.InvalidOperationException
import uk.ac.reigate.repositories.academic.StudentRepository
import uk.ac.reigate.repositories.ilp.ILPInterviewRepository
import uk.ac.reigate.utils.ValidationUtils;

@Service
class ILPInterviewService implements ICoreDataService<ILPInterview, Integer>{
    
    @Autowired
    ILPInterviewRepository iLPInterviewRepository
    
    @Autowired
    StudentRepository studentRepository
    
    /**
     * Default NoArgs constructor
     */
    ILPInterviewService() {}
    
    /**
     * Autowired Constructor
     *
     * @param iLPInterviewRepository
     */
    ILPInterviewService(ILPInterviewRepository iLPInterviewRepository) {
        this.iLPInterviewRepository = iLPInterviewRepository;
    }
    
    /**
     * Find an individual ILPInterview using the ILPInterview ID fields
     *
     * @param id the ID fields to search for
     * @return the ILPInterview object that matches the ID supplied, or null if not found
     */
    @Override
    @Transactional(readOnly = true)
    ILPInterview findById(Integer id) {
        return iLPInterviewRepository.findOne(id);
    }
    
    /**
     * Find a single page of ILPInterview objects
     * @return a List of ILPInterviews
     */
    @Override
    @Transactional(readOnly = true)
    List<ILPInterview> findAll() {
        return iLPInterviewRepository.findAll();
    }
    
    /**
     * @param id
     * @param student
     * @param type
     * @param courseGroup
     * @param staff
     * @param discussion
     * @param interviewDate
     * @param interviewTime
     * @param referLip
     * @param lipReferDate
     * @param privateEntry
     * @param officeAction
     * @param officeNotes
     * @param toFile
     * @return
     */
    @Transactional
    public ILPInterview saveILPInterview(Integer id, Student student, ILPInterviewType type, CourseGroup courseGroup, Staff staff, String discussion, Date interviewDate, Date interviewTime, Boolean referLip, Date lipReferDate, Boolean privateEntry, String officeAction, String officeNotes, Boolean toFile){
        ValidationUtils.assertNotBlank(student, "student cannot be blank");
        ILPInterview iLPInterview = null;
        
        if (id != null) {
            iLPInterview = findById(id);
            iLPInterview.setStudent(student);
            iLPInterview.setType(type);
            iLPInterview.setCourseGroup(courseGroup);
            iLPInterview.setStaff(staff);
            iLPInterview.setDiscussion(discussion);
            iLPInterview.setInterviewDate(interviewDate);
            iLPInterview.setInterviewTime(interviewTime);
            iLPInterview.setReferLip(referLip);
            iLPInterview.setLipReferDate(lipReferDate);
            iLPInterview.setPrivateEntry(privateEntry);
            
            iLPInterview.setOfficeAction(officeAction);
            iLPInterview.setPrivateEntry(privateEntry);
            iLPInterview.setOfficeNotes(officeNotes);
            iLPInterview.setToFile(toFile);
            save(iLPInterview);
        } else {
            iLPInterview = save(new ILPInterview(student,  type, courseGroup, staff, discussion, interviewDate,  interviewTime,  referLip,  lipReferDate,  privateEntry,  officeAction,  officeNotes,  toFile));
        }
        
        return iLPInterview;
    }
    
    /**
     * This service method is used to save a complete ILPInterview object in the database
     *
     * @param iLPInterview the new ILPInterview object to be saved
     * @return the saved version of the ILPInterview object
     */
    @Override
    @Transactional
    public ILPInterview save(ILPInterview iLPInterview) {
        return iLPInterviewRepository.save(iLPInterview)
    }
    
    /**
     * Saves a list of ILPInterview objects to the database
     *
     * @param iLPInterviews a list of ILPInterviews to be saved to the database
     * @return the list of save ILPInterview objects
     */
    @Transactional
    public List<ILPInterview> saveILPInterviews(List<ILPInterview> iLPInterviews) {
        return iLPInterviews.collect { iLPInterview -> save(iLPInterview) };
    }
    
    /** 
     * @param studentId
     * @return List of ILPInterview by studentId
     */
    @PreAuthorize("@securityChecker.checkReader(authentication)")
    public  List<ILPInterview> getByStudent(Integer studentId){
        return iLPInterviewRepository.findByStudent_IdAndPrivateEntry(studentId, false);
    }
    
    
    /** 
     * This method is used to retrieve all the students ILP interviews including those that are marked as private.
     * 
     * @param studentId
     * @return List of ILPInterview by studentId
     */
    @PreAuthorize("@securityChecker.checkReader(authentication) and hasRole('ROLE_Pastoral')")
    public  List<ILPInterview> getByStudentAll(Integer studentId){
        return iLPInterviewRepository.findByStudent_Id(studentId);
    }
    
    
    /**
     * @param studentId
     * @param iLPInterviewId
     * @return ILPInterview object for a studentId and iLPInterviewId
     */
    @PreAuthorize("@securityChecker.checkReader(authentication)")
    public  ILPInterview getByStudentAndILPInterview(Integer studentId, Integer iLPInterviewId){
        return iLPInterviewRepository.findByStudent_IdAndId(studentId, iLPInterviewId);
    }
    
    /**
     * This methods throws an InvalidOperationException when called. ILPInterview should not be deleted.
     */
    @Override
    public void delete(ILPInterview obj) {
        throw new InvalidOperationException("ILPInterview should not be deleted");
        
    }
    
}
