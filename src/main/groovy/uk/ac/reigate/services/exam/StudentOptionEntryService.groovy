package uk.ac.reigate.services.exam

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.security.access.prepost.PreAuthorize
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional

import uk.ac.reigate.domain.academic.Student;
import uk.ac.reigate.domain.exam.ExamOption;
import uk.ac.reigate.domain.exam.StudentOptionEntry
import uk.ac.reigate.domain.exam.StudentOptionEntryPk
import uk.ac.reigate.domain.learning_support.ConcessionType;
import uk.ac.reigate.model.SearchResult;
import uk.ac.reigate.repositories.exam.StudentOptionEntryRepository
import uk.ac.reigate.services.ICoreDataService


@Service
class StudentOptionEntryService implements ICoreDataService<StudentOptionEntry,StudentOptionEntryPk>{
    
    @Autowired
    StudentOptionEntryRepository studentOptionEntryRepository
    
    /**
     * Default No Args constructor
     */
    StudentOptionEntryService() {}
    
    
    StudentOptionEntryService(StudentOptionEntryRepository studentOptionEntryRepository) {
        this.studentOptionEntryRepository = studentOptionEntryRepository;
    }
    
    /**
     * Find all StudentOptionEntries
     * @return a SearchResult set with the list of OptionEntries
     */
    @Override
    @Transactional(readOnly = true)
    List<StudentOptionEntry> findAll() {
        return studentOptionEntryRepository.findAll();
    }
    
    /**
     * Saves a StudentOptionEntry object to the database
     * @return StudentOptionEntry
     */
    @Override
    @Transactional
    public StudentOptionEntry save(StudentOptionEntry studentOptionEntry) {
        return studentOptionEntryRepository.save(studentOptionEntry)
    }
    
    /**
     * Finds individual studentOptionEntryPk
     * @return StudentOptionEntry
     */
    @Override
    public StudentOptionEntry findById(StudentOptionEntryPk studentOptionEntryPk) {
        return studentOptionEntryRepository.findOne(studentOptionEntryPk)
    }
    
    
    /** Deletes StudentOptionEntry object     * 
     * @return StudentOptionEntry
     */
    @Override
    public void delete(StudentOptionEntry studentOptionEntry) {
        studentOptionEntryRepository.delete(new StudentOptionEntryPk(studentOptionEntry))
    }
    
    /**
     * Find an individual optionEntry using Student and ExamOption
     *
     * @param student
     * @param examOption
     * @return the OptionEntry object that matches student and examOption
     */
    @Transactional(readOnly = true)
    StudentOptionEntry findByStudentAndExamOption(Student student, ExamOption examOption){
        findById(new StudentOptionEntryPk(student, examOption))
    }
    
    /**
     * Find an individual optionEntry using StudentId and ExamOptionId
     *
     * @param studentId
     * @param examOptionId
     * @return the OptionEntry object that matches studentId and examOptionID
     */
    @PreAuthorize("@securityChecker.checkReader(authentication)")
    @Transactional(readOnly = true)
    StudentOptionEntry findByStudentAndOption(Integer studentId, Integer optionId){
        return studentOptionEntryRepository.findByStudent_IdAndExamOption_ExamOptionId(studentId, optionId)
    }
    
    
    /** List of StudentOptionEntry of a particular studentId
     * @param studentId
     * @return List of StudentOptionEntry
     */
    @PreAuthorize("@securityChecker.checkReader(authentication)")
    public List<StudentOptionEntry> getByStudent(Integer studentId){
        List<StudentOptionEntry> students = studentOptionEntryRepository.findByStudent_Id(studentId)
        return students
    }
    
    
    /** Returns StudentOptionEntry instance of a examOptionId
     * @param examOptionId
     * @return
     */
    @PreAuthorize("@securityChecker.checkReader(authentication)")
    public StudentOptionEntry getByOption(Integer examOptionId){
        StudentOptionEntry examOptions = studentOptionEntryRepository.findByExamOption_ExamOptionId(examOptionId)
        return examOptions
    }
    
    /**
     * This method is used to retrieve the student option entry information for a given exam component. This will return all student 
     * option entry information including those that are no long live.
     *  
     * @param componentId The exam component ID 
     * @return 
     */
    @PreAuthorize("@securityChecker.checkReader(authentication)")
    public List<StudentOptionEntry> getByComponentId(Integer componentId){
        List<StudentOptionEntry> examOptions = studentOptionEntryRepository.findByExamOption_OptionComponents_ExamComponentId(componentId)
        return examOptions
    }
    
    /**
     * This method is used to retrieve the student option entry information for a given exam component where the entry is live. This will 
     * only return student option entry information for the entries that are still live.
     * 
     * @param componentId The exam component ID 
     * @return 
     */
    @PreAuthorize("@securityChecker.checkReader(authentication)")
    public List<StudentOptionEntry> getByComponentIdAndLive(Integer componentId){
        List<StudentOptionEntry> examOptions = studentOptionEntryRepository.findByExamOption_OptionComponents_ExamComponentIdAndStatusType_Id(componentId, 1)
        return examOptions
    }
    
    
    /** Deleted StudentOptionEntry of StudentId and examOptionId
     * @param studentId
     * @param examOptionId
     * @return
     */
    public Boolean deleteByIds(Integer studentId, Integer examOptionId){
        StudentOptionEntry studentOptionEntry  = findByStudentAndOption(studentId,examOptionId)
        delete(studentOptionEntry)
    }
    
    /**
     * @param student
     * @param examOption
     * @return
     */
    public Boolean deleteByStudentAndExamOption(Student student, ExamOption examOption){
        return deleteByIds(student.id, examOption.examOptionId);
    }
    
    
    public List<StudentOptionEntry> getByOptionYearId(Integer optionYearId, Integer studentId){
        return studentOptionEntryRepository.findByOptionYearIdAndStudentId(optionYearId, studentId)
    }
}
