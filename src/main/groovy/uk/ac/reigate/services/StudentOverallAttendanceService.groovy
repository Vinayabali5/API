package uk.ac.reigate.services

//import static org.springframework.util.Assert

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

import uk.ac.reigate.domain.register.StudentOverallAttendance
import uk.ac.reigate.exceptions.InvalidOperationException
import uk.ac.reigate.model.PageInfo
import uk.ac.reigate.model.SearchResult
import uk.ac.reigate.repositories.register.StudentOverallAttendanceRepository
import uk.ac.reigate.utils.ValidationUtils;

@Service
class StudentOverallAttendanceService implements ICoreDataService<StudentOverallAttendance, Integer>{
    
    @Autowired
    StudentOverallAttendanceRepository studentOverallAttendanceRepository
    
    /**
     * Default NoArgs constructor
     */
    StudentOverallAttendanceService() {}
    
    /**
     * Autowired Constructor
     *
     * @param studentOverallAttendanceRepository
     */
    StudentOverallAttendanceService(StudentOverallAttendanceRepository studentOverallAttendanceRepository) {
        this.studentOverallAttendanceRepository = studentOverallAttendanceRepository
    }
    
    /**
     * Find an individual studentOverallAttendance using the studentOverallAttendances ID fields
     *
     * @param id the ID fields to search for
     * @return the StudentOverallAttendance object that matches the ID supplied, or null if not found
     */
    @Transactional(readOnly = true)
    StudentOverallAttendance findStudentOverallAttendanceByYear(Integer studentId, Integer yearId) {
        return studentOverallAttendanceRepository.findByStudent_IdAndYear_Id(studentId, yearId);
    }
    
    /**
     * Find a single page of StudentOverallAttendance objects
     * @return a SearchResult set with the list of StudentOverallAttendances
     */
    @Override
    @Transactional(readOnly = true)
    List<StudentOverallAttendance> findAll() {
        return studentOverallAttendanceRepository.findAll();
    }
    
    /**
     * This methods returns null as there is no id for StudentOverallAttendance object
     */
    @Override
    public StudentOverallAttendance findById(Integer id) {
        return null;
    }
    
    /**
     * This service method is used to save a complete instance of a StudentOverallAttendance object to the database.
     *
     * @param studentOverallAttendance a complete StudentOverallAttendance object to persist to the database
     * @return the saved version of the StudentOverallAttendance object
     */
    @Override
    public StudentOverallAttendance save(StudentOverallAttendance studentOverallAttendance) {
        return studentOverallAttendanceRepository.save(studentOverallAttendance)
    }
    
    /**
     * This methods throws an InvalidOperationException when called. StudentOverallAttendance should not be deleted.
     */
    @Override
    public void delete(StudentOverallAttendance obj) {
        throw new InvalidOperationException("StudentOverallAttendance should not be deleted")
        
    }
    
    
}
