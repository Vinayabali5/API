package uk.ac.reigate.services

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.security.access.prepost.PreAuthorize
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

import uk.ac.reigate.domain.academic.EntryQualification
import uk.ac.reigate.domain.academic.Student
import uk.ac.reigate.domain.academic.StudentEntryQualification
import uk.ac.reigate.domain.exam.ExamBoard
import uk.ac.reigate.repositories.academic.EntryQualificationRepository
import uk.ac.reigate.repositories.academic.StudentEntryQualificationRepository
import uk.ac.reigate.repositories.academic.StudentRepository
import uk.ac.reigate.repositories.exam.ExamBoardRepository
import uk.ac.reigate.utils.ValidationUtils

@Service
class StudentEntryQualificationService implements ICoreDataService<StudentEntryQualification, Integer>{
    
    @Autowired
    StudentRepository studentRepository
    
    @Autowired
    StudentEntryQualificationRepository studentEntryQualificationRepository
    
    @Autowired
    EntryQualificationRepository entryQualificationRepository
    
    @Autowired
    ExamBoardRepository examBoardRepository
    
    /**
     * Default NoArgs constructor
     */
    StudentEntryQualificationService() {}
    
    /**
     * Autowired Constructor
     *
     * @param studentEntryQualificationRepository
     */
    StudentEntryQualificationService(StudentEntryQualificationRepository studentEntryQualificationRepository) {
        this.studentEntryQualificationRepository = studentEntryQualificationRepository;
    }
    
    /**
     * Find an individual StudentEntryQualification using the StudentEntryQualification ID fields
     *
     * @param id the ID fields to search for
     * @return the StudentEntryQualification object that matches the ID supplied, or null if not found
     */
    @Override
    @Transactional(readOnly = true)
    StudentEntryQualification findById(Integer id) {
        return studentEntryQualificationRepository.findOne(id);
    }
    
    /**
     * Find a single page of StudentEntryQualification objects
     * @return a List of StudentEntryQualification
     */
    @Override
    @Transactional(readOnly = true)
    List<StudentEntryQualification> findAll() {
        return studentEntryQualificationRepository.findAll();
    }
    
    
    /**
     * @param id
     * @param student
     * @param qualification
     * @param date
     * @param grade
     * @param checked
     * @return
     */
    @Transactional
    public StudentEntryQualification saveStudentEntryQualification(Integer id, Student student, EntryQualification qualification, Date date, String grade, boolean checked, ExamBoard examBoard){
        ValidationUtils.assertNotBlank(student, "student cannot be blank");
        
        StudentEntryQualification studentEntryQualification = null;
        if(id != null){
            studentEntryQualification = findById(id)
            
            studentEntryQualification.setStudent(student);
            studentEntryQualification.setQualification(qualification);
            studentEntryQualification.setDate(date);
            studentEntryQualification.setGrade(grade);
            studentEntryQualification.setChecked(checked);
            studentEntryQualification.setExamBoard(examBoard);
            
            save(studentEntryQualification);
        } else {
            studentEntryQualification = save(new StudentEntryQualification(student, qualification, date, grade, checked, examBoard))
        }
        return studentEntryQualification;
    }
    
    /**
     * This service method is used to save a complete StudentEntryQualification object in the database
     *
     * @param studentEntryQualification the new StudentEntryQualification object to be saved
     * @return the saved version of the StudentEntryQualification object
     */
    @Transactional
    public StudentEntryQualification save(StudentEntryQualification studentEntryQualification) {
        return studentEntryQualificationRepository.save(studentEntryQualification)
    }
    
    /**
     * This service method is used to update an StudentEntryQualification object in the database from a partial or complete StudentEntryQualification object.
     *
     * @param studentEntryQualification the partial or complete StudentEntryQualification object to be saved
     * @return the saved version of the StudentEntryQualification object
     */
    @Transactional
    public StudentEntryQualification updateStudentEntryQualification(StudentEntryQualification studentEntryQualification){
        StudentEntryQualification studentEntryQualificationToSave = studentEntryQualificationRepository.findByStudent_IdAndId(studentEntryQualification.student.id, studentEntryQualification.id)
        studentEntryQualificationToSave.student = studentEntryQualification.student.id != null ? studentRepository.findOne(studentEntryQualification.student.id) : studentEntryQualificationToSave.student
        studentEntryQualificationToSave.qualification = studentEntryQualification.qualification.id != null ? entryQualificationRepository.findOne(studentEntryQualification.qualification.id) : studentEntryQualificationToSave.qualification
        studentEntryQualificationToSave.date = studentEntryQualification.date != null ? studentEntryQualification.date : studentEntryQualificationToSave.date
        studentEntryQualificationToSave.grade = studentEntryQualification.grade != null ? studentEntryQualification.grade : studentEntryQualificationToSave.grade
        studentEntryQualificationToSave.checked = studentEntryQualification.checked != null ? studentEntryQualification.checked : studentEntryQualificationToSave.checked
        studentEntryQualificationToSave.examBoard = studentEntryQualification.examBoard.id != null ? examBoardRepository.findOne(studentEntryQualification.examBoard.id) : studentEntryQualificationToSave.examBoard
        return save(studentEntryQualificationToSave)
    }
    
    
    /**
     * @param studentEntryQualifications
     * @return
     */
    @Transactional
    public List<StudentEntryQualification> saveStudentEntryQualifications(List<StudentEntryQualification> studentEntryQualifications){
        return studentEntryQualifications.collect { studentEntryQualification -> save( studentEntryQualification )};
    }
    
    
    /**
     * This method is used to retrieve the student entry qualifications that a specific studentId and studentEntryQualificationId
     * @param studentId
     * @return
     */
    @PreAuthorize("@securityChecker.checkReader(authentication)")
    public  StudentEntryQualification getByStudentEntryQualifications(Integer studentId, Integer studentEntryQualificationId){
        return studentEntryQualificationRepository.findByStudent_IdAndId(studentId, studentEntryQualificationId);
    }
    
    /**
     * This method is used to retrieve the list of student entry qualifications that a specific studentId
     * @param studentId
     * @return
     */
    @PreAuthorize("@securityChecker.checkReader(authentication)")
    public  List<StudentEntryQualification> getByStudent(Integer studentId){
        return studentEntryQualificationRepository.findByStudent_Id(studentId);
    }
    
    
    /**
     * This service method is used to delete a complete StudentEntryQualification object in the database
     *
     * @param studentEntryQualificationId the object to be deleted   
     */
    @Transactional
    @PreAuthorize("@securityChecker.checkWriter(authentication)")
    public void delete(Integer studentEntryQualificationId){
        studentEntryQualificationRepository.delete(studentEntryQualificationId)
    }
    
    
    /* (non-Javadoc)
     * @see uk.ac.reigate.services.ICoreDataService#delete(java.lang.Object)
     */
    @Override
    public void delete(StudentEntryQualification studentEntryQualification) {
        // studentEntryQualificationRepository.delete(studentEntryQualification)
    }
}
