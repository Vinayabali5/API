package uk.ac.reigate.services

//import static org.springframework.util.Assert

import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.security.access.prepost.PreAuthorize
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

import uk.ac.reigate.domain.Contact
import uk.ac.reigate.domain.academic.InterimReport;
import uk.ac.reigate.domain.academic.Student
import uk.ac.reigate.domain.academic.StudentInterimReport
import uk.ac.reigate.domain.academic.StudentInterimReportPk
import uk.ac.reigate.model.SearchResult;
import uk.ac.reigate.repositories.academic.InterimReportRepository
import uk.ac.reigate.repositories.academic.StudentInterimReportRepository
import uk.ac.reigate.repositories.academic.StudentRepository
import uk.ac.reigate.utils.ValidationUtils;

@Service
class StudentInterimReportService implements ICoreDataService<StudentInterimReport, Integer> {
    
    @Autowired
    StudentRepository studentRepository
    
    @Autowired
    StudentInterimReportRepository studentInterimReportRepository
    
    @Autowired
    InterimReportRepository interimReportRepository
    
    /**
     * Default NoArgs constructor
     */
    StudentInterimReportService() {}
    
    /**
     * Autowired Constructor
     *
     * @param studentInterimReportRepository
     */
    StudentInterimReportService(StudentInterimReportRepository studentInterimReportRepository) {
        this.studentInterimReportRepository = studentInterimReportRepository;
    }
    
    
    /**
     * Find an individual StudentInterimReport using the StudentInterimReport ID fields
     *
     * @param id the ID fields to search for
     * @return the StudentInterimReport object that matches the ID supplied, or null if not found
     *
     */
    @Override
    @Transactional(readOnly = true)
    StudentInterimReport findById(Integer id) {
        return studentInterimReportRepository.findOne(id);
    }
    
    
    /**
     * Find all StudentInterimReport
     *
     * @return a List of StudentInterimReport
     */
    @Override
    @Transactional(readOnly = true)
    List<StudentInterimReport> findAll() {
        return studentInterimReportRepository.findAll();
    }
    
    
    /**
     * This method is used to retrieve the student entry qualifications that a specific studentId and studentInterimReportId
     * @param studentId
     * @return
     */
    @PreAuthorize("@securityChecker.checkReader(authentication)")
    public  StudentInterimReport getByStudentInterimReports(Integer studentId, Integer interimReportId){
        return studentInterimReportRepository.findByStudent_IdAndInterimReport_Id(studentId, interimReportId);
    }
    
    /**
     * This method is used to retrieve the list of student entry qualifications that a specific studentId
     * @param studentId
     * @return
     */
    @PreAuthorize("@securityChecker.checkReader(authentication)")
    public  List<StudentInterimReport> getByStudent(Integer studentId){
        return studentInterimReportRepository.findByStudent_Id(studentId);
    }
    
    
    /**
     * @param studentId
     * @param yearId
     * @return
     */
    @PreAuthorize("@securityChecker.checkReader(authentication)")
    public List<StudentInterimReport> findByStudentAndYearId(Integer studentId, Integer yearId){
        return studentInterimReportRepository.findByStudentAndYearId(studentId, yearId)
    }
    
    
    /**
     * This service method is used to save a complete StudentInterimReport object in the database
     *
     * @param studentInterimReport the new StudentInterimReport object to be saved
     * @return the saved version of the StudentInterimReport object
     */
    @Override
    @Transactional
    public StudentInterimReport save(StudentInterimReport studentInterimReport) {
        return studentInterimReportRepository.save(studentInterimReport)
        
    }
    
    /**
     * This service method is used to delete a complete StudentInterimReport object in the database
     *
     * @param studentInterimReport the  StudentInterimReport object to be deleted
     * 
     */
    @Override
    public void delete(StudentInterimReport studentInterimReport) {
        studentInterimReportRepository.delete(studentInterimReport)
    }
    
}

