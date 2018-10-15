package uk.ac.reigate.services

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.security.access.prepost.PreAuthorize
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

import uk.ac.reigate.domain.academic.CourseGroup
import uk.ac.reigate.domain.academic.Student
import uk.ac.reigate.domain.ilp.Correspondence
import uk.ac.reigate.domain.ilp.CorrespondenceType
import uk.ac.reigate.domain.ilp.Letter
import uk.ac.reigate.exceptions.InvalidOperationException
import uk.ac.reigate.repositories.academic.StudentRepository
import uk.ac.reigate.repositories.ilp.CorrespondenceRepository
import uk.ac.reigate.repositories.register.AttendanceCodeRepository
import uk.ac.reigate.utils.ValidationUtils

@Service
class CorrespondenceService implements ICoreDataService<Correspondence, Integer>{
    
    @Autowired
    CorrespondenceRepository correspondenceRepository
    
    @Autowired
    StudentRepository studentRepository
    
    @Autowired
    AttendanceCodeRepository attendanceCodeRepository
    
    
    /**
     * Default NoArgs constructor
     */
    CorrespondenceService() {}
    
    /**
     * Autowired Constructor
     *
     * @param correspondenceRepository
     */
    CorrespondenceService(CorrespondenceRepository correspondenceRepository) {
        this.correspondenceRepository = correspondenceRepository;
    }
    
    /**
     * Find an individual contactType using the Correspondence ID fields
     *
     * @param id the ID fields to search for
     * @return the Correspondence object that matches the ID supplied, or null if not found
     */
    @Override
    @Transactional(readOnly = true)
    Correspondence findById(Integer id) {
        return correspondenceRepository.findOne(id);
    }
    
    /**
     * Find a single page of Correspondence objects
     * @return a List of Correspondences
     */
    @Override
    @Transactional(readOnly = true)
    List<Correspondence> findAll() {
        return correspondenceRepository.findAll();
    }
    
    /**
     *
     * @param id
     * @param code
     * @param description
     * @return
     */
    @Transactional
    public Correspondence saveCorrespondence(Integer id, Student student, CourseGroup course, String correspondence, String title, Date date, String from, String to, Letter letter, Date staffAdvised, CorrespondenceType type, String producedBy, Boolean privateEntry, Integer processStage, String attachmentsSent){
        ValidationUtils.assertNotBlank(student, "student cannot be blank");
        
        Correspondence correspondenceId = null;
        
        if (id != null) {
            correspondenceId = findById(id);
            correspondenceId.setStudent(student);
            correspondenceId.setCourse(course);
            correspondenceId.setCorrespondence(correspondence);
            correspondenceId.setTitle(title);
            correspondenceId.setDate(date);
            correspondenceId.setFrom(from);
            correspondenceId.setTo(to);
            correspondenceId.setLetter(letter);
            correspondenceId.setStaffAdvised(staffAdvised);
            correspondenceId.setType(type);
            correspondenceId.setProducedBy(producedBy);
            correspondenceId.setPrivateEntry(privateEntry);
            correspondenceId.setProcessStage(processStage);
            correspondenceId.setAttachmentsSent(attachmentsSent);
            save(correspondenceId);
        } else {
            correspondenceId = save(new Correspondence(student, course, correspondence, title, date, from, to, letter, staffAdvised, type, producedBy, privateEntry, processStage,  attachmentsSent));
        }
        
        return correspondenceId;
    }
    
    /**
     * This service method is used to save a complete Correspondence object in the database
     *
     * @param correspondence the new Correspondence object to be saved
     * @return the saved version of the Correspondence object
     */
    @Override
    @Transactional
    public Correspondence save(Correspondence correspondence) {
        return correspondenceRepository.save(correspondence)
    }
    
    /**
     * Saves a list of Correspondence objects to the database
     *
     * @param correspondences a list of Correspondences to be saved to the database
     * @return the list of save Correspondence objects
     */
    @Transactional
    public List<Correspondence> saveCorrespondences(List<Correspondence> correspondences) {
        return correspondences.collect { correspondence -> save(correspondence) };
    }
    
    /**
     * @param studentId
     * @return
     */
    @PreAuthorize("@securityChecker.checkReader(authentication)")
    public  List<Correspondence> getByStudent(Integer studentId){
        return correspondenceRepository.findByStudent_Id(studentId);
    }
    
    /**
     * @param studentId
     * @param correspondanceId
     * @return
     */
    @PreAuthorize("@securityChecker.checkReader(authentication)")
    public  Correspondence getByStudentAndCorrespondence(Integer studentId, Integer correspondanceId){
        return correspondenceRepository.findByStudent_IdAndId(studentId, correspondanceId);
    }
    
    /**
     * This methods throws an InvalidOperationException when called. Correspondence should not be deleted.
     */
    @Override
    public void delete(Correspondence obj) {
        throw new InvalidOperationException("Correspondence Objects should not be deleted")
    }
}
