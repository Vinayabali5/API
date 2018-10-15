package uk.ac.reigate.services

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.security.access.prepost.PreAuthorize
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

import uk.ac.reigate.domain.academic.AcademicYear
import uk.ac.reigate.domain.academic.Student
import uk.ac.reigate.domain.academic.StudentYear
import uk.ac.reigate.domain.academic.StudentYearPk
import uk.ac.reigate.domain.lookup.AttendanceMonitoring
import uk.ac.reigate.domain.lookup.PunctualityMonitoring
import uk.ac.reigate.domain.lookup.TutorGroup
import uk.ac.reigate.exceptions.InvalidOperationException
import uk.ac.reigate.repositories.academic.StudentYearRepository

@Service
class StudentYearService implements ICoreDataService<StudentYear, StudentYearPk>{
    
    @Autowired
    StudentTypeService studentTypeService
    
    @Autowired
    TutorGroupService tutorGroupService
    
    @Autowired
    AcademicYearService academicYearService
    
    @Autowired
    StudentService studentService
    
    @Autowired
    StudentYearRepository studentYearRepository
    
    @Autowired
    AttendanceMonitoringService attendanceMonitoringService
    
    @Autowired
    PunctualityMonitoringService punctualityMonitoringService
    
    @Autowired
    DestinationService destinationService
    
    /**
     * Default NoArgs constructor
     */
    StudentYearService() {}
    
    /**
     * Autowired constructor
     * 
     * @param studentYearRepository
     */
    StudentYearService(StudentYearRepository studentYearRepository) {
        this.studentYearRepository = studentYearRepository
    }
    
    /**
     * @param tutorGroupId
     * @param year
     * @return List of StudentYear objects
     */
    @PreAuthorize("@securityChecker.checkReader(authentication)")
    @Transactional(readOnly = true)
    List<StudentYear> findByTutorGroupAndYear(Integer tutorGroupId, AcademicYear year){
        List<StudentYear> studentYears = studentYearRepository.findByTutorGroup_IdAndYear(tutorGroupId, year)
        return studentYears
    }
    
    List<StudentYear> findByTutorGroupAndYearId(Integer tutorGroupId, Integer yearId){
        List<StudentYear> studentYears = studentYearRepository.findByTutorGroup_IdAndYear_Id(tutorGroupId, yearId)
        return studentYears
    }
    /**
     * @param tutorGroupId
     * @return List of StudentYear objects
     */
    @PreAuthorize("@securityChecker.checkReader(authentication)")
    @Transactional(readOnly = true)
    List<StudentYear> findByTutorGroup(Integer tutorGroupId){
        List<StudentYear> output = studentYearRepository.findByTutorGroup_Id(tutorGroupId)
        return output
    }
    /**
     * This service is used to retrieve a specified student year for the given Student and Year
     * @param student
     * @param year
     * @return
     */
    @PreAuthorize("@securityChecker.checkReader(authentication)")
    @Transactional(readOnly = true)
    StudentYear findByStudentAndYear(Student student, AcademicYear year){
        return studentYearRepository.findOne(new StudentYearPk(student, year))
    }
    
    /**
     * This service method is used to retrieve a specified student year for the given studentId and yearId
     * 
     * @param studentId the studentId to retrieve data for
     * @param yearId the yearId to retrieve data for
     * @return a StudentYear object that matches the studentId and yearId or null if no record found
     */
    @PreAuthorize("@securityChecker.checkReader(authentication)")
    @Transactional(readOnly = true)
    StudentYear findByStudentAndYear(Integer studentId, Integer yearId){
        return studentYearRepository.findByStudent_IdAndYear_Id(studentId, yearId)
    }
    
    /**
     * This service is used to retrieve a list of StudentYears objects for the given Student
     * @param student
     * @param year
     * @return
     */
    @PreAuthorize("@securityChecker.checkReader(authentication)")
    @Transactional(readOnly = true)
    List<StudentYear> findByStudent(Student student){
        List<StudentYear> studentYears = studentYearRepository.findByStudent(student)
        return studentYears
    }
    
    /**
     * This service method is used to retrieve all instances of the StudentYear object from the database.
     * 
     * @return A List of StudentYear objects
     */
    @Override
    @Transactional(readOnly = true)
    List<StudentYear> findAll() {
        return studentYearRepository.findAll();
    }
    
    /**
     * This service method is used to save a complete instance of a StudentYear object to the database.
     * 
     * @param studentYear a complete StudentYear object to persist to the database 
     * @return the saved version of the StudentYear object 
     */
    @Override
    @Transactional
    public StudentYear save(StudentYear studentYear) {
        return studentYearRepository.save(studentYear)
    }
    
    /**
     * This service method is used to save a list of complete StudentYear objects to the database. 
     * 
     * @param studentYears a list of StudentYear objects to persist to the database
     * @return the saved version of the list of StudentYear objects
     */
    @Transactional
    public List<StudentYear> saveStudentYears(List<StudentYear> studentYears) {
        return studentYears.collect { studentYear -> save( studentYear ) };
    }
    
    /**
     * This method is used to update the student type of a specified student and year combination
     *  
     * @param studentId The ID for the student to update
     * @param yearId The ID for the year to update
     * @param typeId The ID for the new student type
     * @return
     */
    StudentYear updateStudentType(Integer studentId, Integer yearId, Integer typeId) {
        StudentYear studentYear = studentYearRepository.findByStudentAndYear(studentService.findById(studentId), academicYearService.findById(yearId));
        if (studentYear != null) {
            studentYear.studentType = typeId != null ? studentTypeService.findById(typeId) : null;
            return save(studentYear);
        } else {
            throw new Exception('Could not locate the student/year combination');
        }
    }
    
    /**
     * This method is used to update the tutor group of a specified student and year combination
     *
     * @param studentId The ID for the student to update
     * @param yearId The ID for the year to update
     * @param tutorGroupId The ID for the new tutor group
     * @return
     */
    StudentYear updateTutorGroup(Integer studentId, Integer yearId, Integer tutorGroupId) {
        StudentYear studentYear = findByStudentAndYear(studentId, yearId);
        if (studentYear != null) {
            TutorGroup newTutorGroup = tutorGroupService.findById(tutorGroupId);
            studentYear.tutorGroup = newTutorGroup;
            return save(studentYear);
        } else {
            throw new Exception('Could not locate the student/year combination');
        }
    }
    
    
    /**
     * This service method is
     * @param studentId
     * @param academicYearId
     * @param attendanceMonitoringId
     * @param punctualityMonitoringId
     * @return
     */
    StudentYear updateStudentWarning(Integer studentId, Integer academicYearId, Integer attendanceMonitoringId, Integer punctualityMonitoringId) {
        StudentYear studentYear = findByStudentAndYear(studentId, academicYearId);
        if (studentYear != null) {
            if (attendanceMonitoringId != null) {
                AttendanceMonitoring att = attendanceMonitoringService.findById(attendanceMonitoringId);
                studentYear.attendanceMonitoring = att;
            }
            if (punctualityMonitoringId != null) {
                PunctualityMonitoring punct = punctualityMonitoringService.findById(punctualityMonitoringId);
                studentYear.punctualityMonitoring = punct;
            }
            return save(studentYear);
        } else {
            throw new Exception('Could not locate the student/year combination');
        }
    }
    
    /**
     * 
     * This method is used to withdraw an individual student from the database. This will set the students end date for the given year and if provided will also 
     * set the destination information.
     * 
     * @param studentId The ID for the student to withdraw
     * @param yearId The ID for the academic year to withdraw the student from 
     * @param withdrawalDate The date to use for the withdrawal of the student
     * @param destinationId The ID for the destination of the student
     * @param destinationCollegeEmployer The text description of the college or employer that the student has gone on to
     * @param destinationCourseCareer The text description of the course or career that the student has gone on to
     * @return The update version of the studentYear
     * @throws Exception This method will throw an Exception if the student year combination is not found in the database.
     */
    StudentYear withdrawStudent(Integer studentId, Integer yearId, Date withdrawalDate, Integer destinationId, String destinationCollegeEmployer, String destinationCourseCareer) throws Exception {
        StudentYear studentYear = findByStudentAndYear(studentId, yearId);
        if (studentYear != null) {
            studentYear.destination = (destinationId!=null)?destinationService.findById(destinationId):null;
            studentYear.endDate = withdrawalDate;
            studentYear.destinationCollegeEmployer = destinationCollegeEmployer;
            studentYear.destinationCourseCareer = destinationCourseCareer;
            return save(studentYear);
        } else {
            throw new Exception('Could not locate the student/year combination');
        }
    }
    
    /**
     * This methods throws an InvalidOperationException when called. StudentYear should not be deleted.
     */
    @Override
    public void delete(StudentYear obj) {
        throw new InvalidOperationException("StudentYear should not be deleted")
    }
    
    @Override
    public StudentYear findById(StudentYearPk studentYearPk) {
        return studentYearRepository.findOne(studentYearPk)
    }
}
