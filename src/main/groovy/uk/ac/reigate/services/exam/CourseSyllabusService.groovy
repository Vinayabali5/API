package uk.ac.reigate.services.exam

import java.util.List
import java.util.logging.Logger

import javax.servlet.http.HttpServletRequest

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.data.domain.Page
import org.springframework.data.domain.PageRequest
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.security.access.prepost.PreAuthorize
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import org.springframework.web.bind.annotation.ExceptionHandler

import uk.ac.reigate.domain.exam.CourseSyllabusPk
import uk.ac.reigate.domain.exam.Syllabus;
import uk.ac.reigate.model.PageInfo;
import uk.ac.reigate.model.SearchResult
import uk.ac.reigate.domain.academic.Course;
import uk.ac.reigate.domain.exam.CourseSyllabus;
import uk.ac.reigate.repositories.academic.CourseRepository;
import uk.ac.reigate.repositories.exam.CourseSyllabusRepository
import uk.ac.reigate.repositories.exam.SyllabusRepository;
import uk.ac.reigate.services.ICoreDataService
import uk.ac.reigate.utils.ValidationUtils;;


@Service
public class CourseSyllabusService implements ICoreDataService<CourseSyllabus,CourseSyllabusPk>{
    private final static Logger log = Logger.getLogger(CourseSyllabusService.class.getName())
    
    @Autowired
    CourseSyllabusRepository courseSyllabusRepository
    
    @Autowired
    CourseRepository courseRepository
    
    @Autowired
    SyllabusRepository syllabusRepository
    
    /**
     * Default NoArgs constructor
     */
    CourseSyllabusService() {}
    
    /**
     * Autowired constructor
     * 
     * @param courseOptionRepository
     */
    CourseSyllabusService(CourseSyllabusRepository courseSyllabusRepository) {
        this.courseSyllabusRepository = courseSyllabusRepository;
    }
    
    /**
     * Find an individual exam basedata CourseSyllabus by CourseSyllabusPk
     * 
     * @param courseId - the ID to search for
     * @param syllabusId - 
     * @return CourseSyllabus - the CourseSyllabus object that matches the ID, or null if not found
     */
    @Transactional(readOnly = true)
    public CourseSyllabus findCourseSyllabus(Integer courseId, Integer syllabusId) {
        CourseSyllabusPk courseSyllabusPk = new CourseSyllabusPk(courseId, syllabusId);
        findById(courseSyllabusPk)
    }
    
    /**
     * Find all CourseSyllabus objects
     *         
     * @return a List set with the list of CourseSyllabi
     */
    @Transactional(readOnly = true)
    public List<CourseSyllabus> findAll() {
        return courseSyllabusRepository.findAll();
    }
    
    /**
     * Find a single page of CourseSyllabi objects
     *
     * @param page - the page number to retrieve
     * @param size - the number of records on each page
     * @return a SearchResult set with the list of CourseOptions
     */
    public SearchResult<CourseSyllabus> findCourseSyllabi(Pageable pageable) {
        Page<CourseSyllabus> courseSyllabi = courseSyllabusRepository.findAll(pageable);
        PageInfo pageInfo = new PageInfo(pageable.getPageNumber(), pageable.getPageSize(), '', (Long) courseSyllabi.total);
        return new SearchResult<>(pageInfo, courseSyllabi.toList());
    }
    
    /**
     * Save an CourseOption object to the database
     *     
     * @param CourseOption - the CourseOption object to save
     * @return the saved CourseOption object
     */
    @PreAuthorize("@securityChecker.checkWriter(authentication)")
    public CourseSyllabus save(CourseSyllabus courseSyllabus) {
        log.info("******  service.CourseSyllabusService.saveCourseSyllabus(courseSyllabus)");
        return courseSyllabusRepository.save(courseSyllabus);
    }
    
    /**
     * Save a CourseOption object to the database
     * 
     * @param courseId
     * @param optionId
     * @return
     */
    public CourseSyllabus saveCourseSyllabus(Integer courseId, Integer syllabusId) {
        log.info("******  service.CourseSyllabusService.saveCourseSyllabus(courseId, syllabusId)");
        ValidationUtils.assertNotNull(courseId, "course ID cannot be null");
        ValidationUtils.assertNotNull(syllabusId, "syllabus ID cannot be null");
        
        Course course = courseRepository.findOne(courseId);
        Syllabus syllabus = syllabusRepository.findOne(syllabusId);
        
        return save(new CourseSyllabus(course, syllabus));
    }
    
    /**
     * Delete an CourseSyllabus object from the database
     * 
     * @param courseId } - the ID of the CourseSyllabus object to delete
     * @param syllabusId }
     */
    @PreAuthorize("@securityChecker.checkWriter(authentication)")
    public deleteCourseSyllabus(Integer courseId, Integer syllabusId) {
        CourseSyllabusPk courseSyllabusPk = new CourseSyllabusPk(courseId, syllabusId);
        courseSyllabusRepository.delete(courseSyllabusPk);
    }
    
    /* (non-Javadoc)
     * @see uk.ac.reigate.services.ICoreDataService#findById(java.lang.Object)
     */
    @Override
    public CourseSyllabus findById(CourseSyllabusPk courseSyllabusPk) {
        return courseSyllabusRepository.findOne(courseSyllabusPk);
    }
    
    
    /* (non-Javadoc)
     * @see uk.ac.reigate.services.ICoreDataService#delete(java.lang.Object)
     */
    @Override
    public void delete(CourseSyllabus obj) {
        courseSyllabusRepository.delete(obj)
    }
}
