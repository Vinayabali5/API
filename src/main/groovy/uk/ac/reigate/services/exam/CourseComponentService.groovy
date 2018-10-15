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

import uk.ac.reigate.domain.academic.Course
import uk.ac.reigate.domain.exam.CourseComponent
import uk.ac.reigate.domain.exam.CourseComponentPk
import uk.ac.reigate.domain.exam.CourseOption
import uk.ac.reigate.domain.exam.CourseOptionPk
import uk.ac.reigate.domain.exam.ExamComponent
import uk.ac.reigate.domain.exam.ExamOption;
import uk.ac.reigate.model.PageInfo
import uk.ac.reigate.repositories.academic.CourseRepository;
import uk.ac.reigate.model.SearchResult
import uk.ac.reigate.repositories.exam.CourseComponentRepository
import uk.ac.reigate.repositories.exam.CourseOptionRepository
import uk.ac.reigate.repositories.exam.ExamComponentRepository
import uk.ac.reigate.repositories.exam.ExamOptionRepository;
import uk.ac.reigate.services.ICoreDataService
import uk.ac.reigate.utils.ValidationUtils;

@Service
public class CourseComponentService implements ICoreDataService<CourseComponent, CourseComponentPk>{
    private final static Logger log = Logger.getLogger(CourseComponentService.class.getName())
    
    @Autowired
    CourseComponentRepository courseComponentRepository
    
    @Autowired
    CourseOptionRepository courseOptionRepository
    
    @Autowired
    ExamComponentRepository examComponentRepository
    
    @Autowired
    CourseRepository courseRepository
    
    @Autowired
    ExamOptionRepository examOptionRepository
    
    /**
     * Default NoArgs constructor
     */
    CourseComponentService() {}
    
    /**
     * Autowired Constructor
     * 
     * @param courseComponentRepository
     */
    CourseComponentService(CourseComponentRepository courseComponentRepository) {
        this.courseComponentRepository = courseComponentRepository;
    }
    
    /**
     * Find an individual exam CourseComponent by courseId, optionId and componentId
     * 
     * @param courseId - }
     * @param optionId - } the ID to search for
     * @param componentId -}
     * @return CourseComponent - the CourseComponent object that matches the ID, or null if not found
     */
    @Transactional(readOnly = true)
    public CourseComponent findCourseComponent(Integer courseId, Integer examOptionId, Integer examComponentId) {
        CourseComponentPk courseComponentPk = new CourseComponentPk(courseId, examOptionId, examComponentId);
        return findById(courseComponentPk);
    }
    
    /**
     * Find an individual exam CourseComponent by CourseComponentPk
     * 
     * @param courseComponentId - the compound ID to search for
     * @return CourseComponent - the CourseComponent object that matches the ID, or null if not found
     */
    @Override
    @Transactional(readOnly = true)
    public CourseComponent findById(CourseComponentPk courseComponentPk) {
        return courseComponentRepository.findOne(courseComponentPk);
    }
    
    /**
     * Find all CourseComponent objects
     *
     * @return a SearchResult set with the list of CourseOptions
     */
    @Override
    @Transactional(readOnly = true)
    public List<CourseComponent> findAll() {
        return courseComponentRepository.findAll();
    }
    
    /**
     * Find a single page of CourseComponent objects
     *
     * @param page - the page number to retrieve
     * @param size - the number of records on each page
     * @return a SearchResult set with the list of CourseOptions
     */
    @PreAuthorize("@securityChecker.checkReader(authentication)")
    public SearchResult<CourseComponent> findCourseComponents(int page, int size) {
        PageRequest pageRequest = new PageRequest(page, size);
        Page<CourseComponent> courseComponents = courseComponentRepository.findAll(pageRequest);
        PageInfo pageInfo = new PageInfo(page, size, '', (Long) courseComponents.total);
        return new SearchResult<>(pageInfo, courseComponents.toList());
    }
    
    /**
     * Save an CourseComponent object to the database
     *     
     * @param CourseComponent - the CourseComponent object to save
     * @return the saved CourseComponent object
     */
    @Override
    public CourseComponent save(CourseComponent courseComponent) {
        log.info("******  service.CourseComponentService.saveComponentOption(courseComponent)");
        return courseComponentRepository.save(courseComponent)
    }
    
    /**
     * Save a CourseComponent object to the database
     * 
     * @param courseId
     * @param examOptionId
     * @param examComponentId
     * @return CourseComponent
     */
    public CourseComponent saveCourseComponent(Integer courseId, Integer examOptionId, Integer examComponentId) {
        log.info("******  service.CourseComponentService.saveCourseComponent(courseId, examOptionId, examComponentId)");
        ValidationUtils.assertNotNull(courseId, "course ID cannot be blank");
        ValidationUtils.assertNotNull(examOptionId, "option ID cannot be blank");
        ValidationUtils.assertNotNull(examComponentId, "component ID cannot be blank");
        
        ExamComponent examComponent = examComponentRepository.findOne(examComponentId);
        ValidationUtils.assertNotNull(examComponent, "examComponent does not exist in database")
        
        CourseOptionPk courseOptionPk = new CourseOptionPk(courseId, examOptionId);
        CourseOption courseOption = courseOptionRepository.findOne(courseOptionPk);
        ValidationUtils.assertNotNull(courseOption, "courseOption does not exist in database")
        
        CourseComponent saveCourseComponent = new CourseComponent(courseOption, examComponent);
        return save(saveCourseComponent);
    }
    
    /**
     * Delete an CourseOption object from the database
     * 
     * @param courseId -}
     * @param examOptionId -}
     * @param examComponentId -} the ID of the CourseOption object to delete    
     */
    public deleteCourseComponent(Integer courseId, Integer examOptionId, Integer examComponentId) {
        ValidationUtils.assertNotNull(courseId, "course ID cannot be blank");
        ValidationUtils.assertNotNull(examOptionId, "examOption ID cannot be blank");
        ValidationUtils.assertNotNull(examComponentId, "examComponent ID cannot be blank");
        
        ExamComponent examComponent = examComponentRepository.findOne(examComponentId);
        CourseOption courseOption = courseOptionRepository.findOne(new CourseOptionPk(courseId, examOptionId));
        delete(new CourseComponent(courseOption, examComponent));
    }
    
    
    /* (non-Javadoc)
     * @see uk.ac.reigate.services.ICoreDataService#delete(java.lang.Object)
     */
    @Override
    public void delete(CourseComponent courseComponenet) {
        CourseComponentPk courseComponentPk = new CourseComponentPk(courseComponenet.courseId, courseComponenet.examOptionId, courseComponenet.examComponentId);
        courseComponentRepository.delete(courseComponentPk);
    }
}