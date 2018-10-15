package uk.ac.reigate.services

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.security.access.prepost.PreAuthorize
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

import uk.ac.reigate.domain.academic.AcademicYear
import uk.ac.reigate.domain.academic.Course
import uk.ac.reigate.domain.exam.ExamBoard
import uk.ac.reigate.domain.lookup.Level
import uk.ac.reigate.domain.lookup.Subject
import uk.ac.reigate.exceptions.InvalidOperationException
import uk.ac.reigate.predicates.CoursePredicates
import uk.ac.reigate.repositories.academic.CourseRepository
import uk.ac.reigate.utils.ValidationUtils

@Service
class CourseService implements ICoreDataService<Course, Integer>{
    
    @Autowired
    CourseRepository courseRepository
    
    /**
     * Default NoArgs constructor
     */
    CourseService() {}
    
    /**
     * Autowired Constructor
     *
     * @param courseRepository
     */
    CourseService(CourseRepository courseRepository){
        this.courseRepository = courseRepository;
    }
    
    /**
     * Find an individual course using the courses ID fields
     *
     * @param id the ID fields to search for
     * @return the Course object that matches the ID supplied, or null if not found
     */
    @Override
    @Transactional(readOnly = true)
    Course findById(Integer id) {
        return courseRepository.findOne(id);
    }
    /**
     * Find all courses
     *
     * @return a List of Courses
     */
    @Override
    @Transactional(readOnly = true)
    List<Course> findAll() {
        return courseRepository.findAll();
    }
    
    /**
     * @param id
     * @param level
     * @param subject
     * @param glh
     * @param learningAimReference
     * @param examBoard
     * @param syllabusCode
     * @param validFrom
     * @param validTo
     * @param locationPostcode
     * @param subjectSectorArea
     * @param notes
     * @return
     */
    @Transactional
    public Course saveCourse(Integer id, Level level, Subject subject, Integer glh, String learningAimReference, ExamBoard examBoard, String syllabusCode, AcademicYear validFrom, AcademicYear validTo, String locationPostcode, String subjectSectorArea, String notes) {
        ValidationUtils.assertNotBlank(subject, "subject cannot be blank");
        Course course = null;
        if (id != null) {
            course = findById(id);
            course.setLevel(level);
            course.setSubject(subject);
            course.setGlh(glh);
            course.setLearningAimReference(learningAimReference);
            course.setExamBoard(examBoard);
            course.setSyllabusCode(syllabusCode);
            course.setValidFrom(validFrom);
            course.setValidTo(validTo);
            course.setLocationPostcode(locationPostcode);
            course.setSubjectSectorArea(subjectSectorArea);
            course.setNotes(notes);
            
            save(course);
        } else {
            course = save(new Course(level,subject,glh,learningAimReference,examBoard,syllabusCode,validFrom,validTo,locationPostcode,subjectSectorArea,notes));
        }
        return course;
    }
    
    /**
     * This service method is used to save a complete Course object in the database
     *
     * @param course the new Course object to be saved
     * @return the saved version of the Course object
     */
    @Override
    @Transactional
    public Course save(Course course) {
        return courseRepository.save(course)
    }
    
    /**
     * Saves a list of Course objects to the database
     *
     * @param courses a list of Courses to be saved to the database
     * @return the list of save Course objects
     */
    
    @Transactional
    public List<Course> saveCourses(List<Course> courses) {
        return courses.collect { course -> save( course ) };
    }
    
    /** Retrieves List of Course by Year and Spec
     * @param year
     * @param spec
     * @return
     */
    @PreAuthorize("@securityChecker.checkReader(authentication)")
    public List <Course> searchByYearAndSpec(AcademicYear year, String spec){
        List<Course> courses = courseRepository.findByValidFromAndSpecLike(year, spec)
        return courses
    }
    
    /** Retrieves List of Course by yearId
     * @param yearId
     * @return
     */
    @PreAuthorize("@securityChecker.checkReader(authentication)")
    public findAllCoursesValidInYear(Integer yearId) {
        List<Course> courses = courseRepository.findAll(CoursePredicates.courseValidInYear(yearId))
        return courses
    }
    
    @PreAuthorize("@securityChecker.checkReader(authentication) or hasRole('ROLE_Service User')")
    public findAllPublishedCoursesValidInYear(Integer yearId) {
        List<Course> courses = courseRepository.findAll(CoursePredicates.coursePublishedAndValidInYear(yearId))
        return courses
    }
    
    /** Retrieves List of Course by year
     * @param year
     * @return
     */
    @PreAuthorize("@securityChecker.checkReader(authentication)")
    public List<Course> searchByYear(AcademicYear year) {
        List<Course> courses = courseRepository.findCourseValidOnYear(year.id)
        return courses
    }
    
    /**Retrieves List of Course that is valid from year
     * @param year
     * @return
     */
    @PreAuthorize("@securityChecker.checkReader(authentication)")
    public Course getByAcademicYear(AcademicYear year) {
        return courseRepository.findByValidFrom(year)
    }
    
    /**
     * @param yearId
     * @return
     */
    @PreAuthorize("@securityChecker.checkReader(authentication)")
    public List<Course> getCourseByYearId(Integer yearId){
        return courseRepository.findCourseValidOnYear(yearId)
    }
    
    /**
     * @param courseId
     * @param yearId
     * @return
     */
    @PreAuthorize("@securityChecker.checkReader(authentication)")
    public Course getCourseByIdAndYearId(Integer courseId,Integer yearId){
        return courseRepository.findCourseByIDAndValidFrom(courseId,yearId)
    }
    
    /**
     * This methods throws an InvalidOperationException when called. CourseGroup should not be deleted.
     */
    @Override
    public void delete(Course obj) {
        throw new InvalidOperationException("CourseGroup should not be deleted");
    }
}
