package uk.ac.reigate.services.exam

import java.util.logging.Logger

import javax.servlet.http.HttpServletRequest

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.data.domain.Page
import org.springframework.data.domain.PageRequest
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.security.access.prepost.PreAuthorize
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import org.springframework.web.bind.annotation.ExceptionHandler

//import uk.ac.reigate.exceptions.NotFoundException;
import uk.ac.reigate.domain.academic.Course;
import uk.ac.reigate.domain.exam.CourseOption
import uk.ac.reigate.domain.exam.CourseOptionPk;
import uk.ac.reigate.domain.exam.ExamSeries
import uk.ac.reigate.domain.exam.ExamOption;
import uk.ac.reigate.model.PageInfo
import uk.ac.reigate.repositories.academic.CourseRepository;
import uk.ac.reigate.model.SearchResult
import uk.ac.reigate.repositories.exam.CourseOptionRepository
import uk.ac.reigate.repositories.exam.ExamOptionRepository;
import uk.ac.reigate.services.ICoreDataService
import uk.ac.reigate.utils.ValidationUtils;;

@Service
public class CourseOptionService implements ICoreDataService<CourseOption,CourseOptionPk>{
    private final static Logger log = Logger.getLogger(CourseOptionService.class.getName())
    
    @Autowired
    CourseOptionRepository courseOptionRepository
    
    @Autowired
    CourseRepository courseRepository
    
    @Autowired
    ExamOptionRepository examOptionRepository
    
    /**
     * Default NoArgs constructor
     */
    CourseOptionService() {}
    
    /**
     * Autowired constructor
     *
     * @param courseOptionRepository
     */
    CourseOptionService(CourseOptionRepository courseOptionRepository) {
        this.courseOptionRepository = courseOptionRepository;
    }
    
    /**
     * Find an individual exam basedata CourseOption by CourseOptionPk
     *
     * @param componentId - the ID to search for
     * @return CourseOption - the CourseOption object that matches the ID, or null if not found
     */
    @Transactional(readOnly = true)
    public CourseOption findCourseOption(Integer courseId, Integer examOptionId) {
        CourseOptionPk courseOptionPk = new CourseOptionPk(courseId, examOptionId);
        findById(courseOptionPk);
    }
    
    /**
     * Find all CourseOption objects
     *
     * @return a SearchResult set with the list of CourseOptions
     */
    @Override
    @Transactional(readOnly = true)
    public List<CourseOption> findAll() {
        return courseOptionRepository.findAll();
    }
    
    /**
     * Find a single page of CourseOptions objects
     *
     * @param page - the page number to retrieve
     * @param size - the number of records on each page
     * @return a SearchResult set with the list of CourseOptions
     */
    @PreAuthorize("@securityChecker.checkReader(authentication)")
    public SearchResult<CourseOption> findCourseOptions(int page, int size) {
        PageRequest pageRequest = new PageRequest(page, size);
        Page<CourseOption> CourseOptions = courseOptionRepository.findAll(pageRequest);
        PageInfo pageInfo = new PageInfo(page, size, '', (Long) CourseOptions.total);
        return new SearchResult<>(pageInfo, CourseOptions.toList());
    }
    
    /**
     * Save an CourseOption object to the database
     *
     * @param CourseOption - the CourseOption object to save
     * @return the saved CourseOption object
     */
    @Override
    public CourseOption save(CourseOption courseOption) {
        log.info("******  service.CourseOptionService.saveCourseOption(courseOption)");
        return courseOptionRepository.save(courseOption)
    }
    
    /**
     * Save a CourseOption object to the database
     *
     * @param courseId
     * @param examOptionId
     * @return
     */
    public CourseOption saveCourseOption(Integer courseId, Integer examOptionId) {
        log.info("******  service.CourseOptionService.saveCourseOption(courseId, examOptionId)");
        ValidationUtils.assertNotNull(courseId, "course ID cannot be null");
        ValidationUtils.assertNotNull(examOptionId, "examOption ID cannot be null");
        Course course = courseRepository.findOne(courseId);
        ExamOption examOption = examOptionRepository.findOne(examOptionId);
        save(new CourseOption(course, examOption));
        
    }
    
    /**
     * Delete an CourseOption object from the database
     *
     * @param courseId     } - the ID of the CourseOption object to delete
     * @param examOptionId }
     */
    @PreAuthorize("@securityChecker.checkWriter(authentication)")
    public deleteCourseOption(Integer courseId, Integer examOptionId) {
        CourseOptionPk courseOptionPk = new CourseOptionPk(courseId, examOptionId);
        courseOptionRepository.delete(courseOptionPk);
    }
    
    /**
     * @param courseOptionPk
     * @return
     */
    @Override
    public CourseOption findById(CourseOptionPk courseOptionPk) {
        return courseOptionRepository.findOne(courseOptionPk);
    }
    
    
    /**
     * @param courseObject
     */
    @Override
    public void delete(CourseOption courseObject) {
        courseOptionRepository.delete(courseObject)
    }
}
