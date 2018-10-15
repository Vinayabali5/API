package uk.ac.reigate.services

//import static org.springframework.util.Assert

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.security.access.prepost.PreAuthorize
import org.springframework.stereotype.Service

import uk.ac.reigate.domain.academic.StudentYear
import uk.ac.reigate.repositories.academic.StudentYearRepository

@Service
class StudentBursaryService {
    
    @Autowired
    StudentYearRepository studentYearRepository
    
    @Autowired
    StudentYearService studentYearService
    
    @Autowired
    AcademicYearService academicYearService
    
    @Autowired
    StudentService studentService
    
    /**
     * Default NoArgs constructor
     */
    StudentBursaryService() {}
    
    /**
     * Autowired constructor
     * 
     * @param studentBursaryRepository
     */
    StudentBursaryService(StudentYearService studentYearService) {
        this.studentYearService = studentYearService
    }
    
    @PreAuthorize("@securityChecker.checkWriter(authentication)")
    StudentYear updateStudentBursary(Integer studentId, Integer academicYearId, Boolean gb, Boolean db, Boolean freeMealsEligibility, Boolean receivingFreeMeals) {
        StudentYear studentYear = studentYearService.findByStudentAndYear(studentService.findById(studentId), academicYearService.findById(academicYearId));
        if (studentYear != null) {
            studentYear.gb = gb
            studentYear.db = db
            studentYear.freeMealsEligibility = freeMealsEligibility
            studentYear.receivingFreeMeals = receivingFreeMeals
            return studentYearService.save(studentYear);
        } else {
            throw new Exception('Could not locate the student/year combination');
        }
    }
    
}
