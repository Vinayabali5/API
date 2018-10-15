package uk.ac.reigate.services

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.security.access.prepost.PreAuthorize
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

import uk.ac.reigate.domain.academic.AcademicYear
import uk.ac.reigate.domain.academic.Student
import uk.ac.reigate.domain.admissions.CollegeFundPaid
import uk.ac.reigate.domain.lookup.StudentRemarkPermission
import uk.ac.reigate.exceptions.InvalidOperationException
import uk.ac.reigate.repositories.academic.StudentRepository
import uk.ac.reigate.repositories.learning_support.StudentConcessionTypeRepository

@Service
class StudentService implements ICoreDataService<Student, Integer>{
    
    @Autowired
    StudentRepository studentRepository
    
    @Autowired
    StudentYearService studentYearService
    
    @Autowired
    AcademicYearService academicYearService
    
    @Autowired
    StudentTypeService studentTypeService
    
    @Autowired
    TutorGroupService tutorGroupService
    
    /**
     * Default NoArgs constructor
     */
    StudentService() {}
    
    /**
     * Autowired constructor
     * 
     * @param studentRepository
     */
    StudentService(StudentRepository studentRepository, StudentConcessionTypeRepository studentConcessionTypeRepository) {
        this.studentRepository = studentRepository
    }
    
    /**
     * This service method is used to retrieve an individual Student object from the database.
     * 
     * @param id the id of the member of student to retrieve
     * @return the Student object identified by the id
     */
    @Override
    @Transactional(readOnly = true)
    Student findById(Integer id) {
        return studentRepository.findOne(id);
    }
    
    /**
     * @param tutorGroupId
     * @return
     */
    @PreAuthorize("@securityChecker.checkReader(authentication)")
    @Transactional(readOnly = true)
    List<Student> findTutors(Integer tutorGroupId) {
        return studentRepository.findByTutorGroupId(tutorGroupId);
    }
    /**
     * This service method is used to retrieve all instances of the Student object from the database.
     * 
     * @return A List of Student objects
     */
    @Override
    @Transactional(readOnly = true)
    List<Student> findAll() {
        return studentRepository.findAll();
    }
    
    /**
     * This service method is used to save a complete instance of a Student object to the database.
     * 
     * @param student a complete Student object to persist to the database 
     * @return the saved version of the Student object 
     */
    @Override
    @PreAuthorize("@securityChecker.checkWriter(authentication) or hasRole('First Aid Coordinator') or hasRole('ROLE_Service User')")
    @Transactional
    public Student save(Student student) {
        return studentRepository.save(student)
    }
    
    /**
     * This service method is used to save a list of complete Student objects to the database. 
     * 
     * @param students a list of Student objects to persist to the database
     * @return the saved version of the list of Student objects
     */
    @Transactional
    public List<Student> saveStudents(List<Student> students) {
        return students.collect { student -> save( student ) };
    }
    
    /**
     * This service method is used to retrieve an individual Student object from the database.
     *
     * @param id the id of the member of student to retrieve
     * @return the Student object identified by the id
     */
    @PreAuthorize("@securityChecker.checkReader(authentication)")
    @Transactional(readOnly = true)
    List<Student> findCurrentStudents() {
        return studentRepository.findCurrent();
    }
    
    /**
     * @param year
     * @return
     */
    @PreAuthorize("@securityChecker.checkReader(authentication)")
    List<Student> findStudentsInYear(AcademicYear year) {
        List<Student> students = studentRepository.findAllByStudentYears_Year();
        return students;
    }
    
    
    /**
     * This method is design to update a single students remark permission 
     * 
     * @param student
     * @param remarkPermission
     * @return
     */
    Student updateStudentRemarkPermission(Student student, StudentRemarkPermission remarkPermission) {
        Student s = findById(student.id);
        s.studentRemarkPermission = remarkPermission;
        return save(s);
    }
    
    /**
     * This methods throws an InvalidOperationException when called. Student should not be deleted.
     */
    @Override
    public void delete(Student obj) {
        throw new InvalidOperationException("Student should not be deleted")
    }
    
    /** Uodated collegeFundPaid details for a student
     * @param student - student Id 
     * @param collegeFundPaid 
     * @param collegeFundPaidYears
     * @return
     */
    Student updateCollegeFundPaid(Student student, CollegeFundPaid collegeFundPaid, Integer collegeFundPaidYears){
        student.collegeFundPaid = collegeFundPaid
        student.collegeFundPaidYears = collegeFundPaidYears
        return save(student);
    }
}
