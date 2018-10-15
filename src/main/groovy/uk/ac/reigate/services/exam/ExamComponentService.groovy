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

import uk.ac.reigate.domain.exam.ExamComponent;
import uk.ac.reigate.domain.exam.ExamSeries
import uk.ac.reigate.model.PageInfo
import uk.ac.reigate.model.SearchResult
import uk.ac.reigate.repositories.exam.ExamComponentRepository;
import uk.ac.reigate.repositories.exam.ExamSeriesRepository
import uk.ac.reigate.services.ICoreDataService
import uk.ac.reigate.util.exception.BadRequestException
import uk.ac.reigate.utils.ValidationUtils;;

@Service
public class ExamComponentService implements ICoreDataService<ExamComponent, Integer>{
    private final static Logger log = Logger.getLogger(ExamComponentService.class.getName())
    
    @Autowired
    ExamComponentRepository examComponentRepository
    
    @Autowired
    ExamSeriesRepository examSeriesRepository
    
    /**
     * Default NoArgs constructor
     */
    ExamComponentService() {}
    
    /**
     * Autowired constructor
     * 
     * @param examComponentRepository
     */
    ExamComponentService(ExamComponentRepository examComponentRepository) {
        this.examComponentRepository = examComponentRepository;
    }
    
    /**
     * Find an individual exam basedata ExamComponent by examComponentId
     * 
     * @param examComponentId - the ID to search for
     * @return ExamComponent - the ExamComponent object that matches the ID, or null if not found
     */
    @Override
    @Transactional(readOnly = true)
    public ExamComponent findById(Integer examComponentId) {
        return examComponentRepository.findOne(examComponentId)
    }
    
    /**
     * Find all ExamComponent objects
     *         
     * @return a SearchResult set with the list of ExamComponents
     */
    /* (non-Javadoc)
     * @see uk.ac.reigate.services.ICoreDataService#findAll()
     */
    @Override
    @Transactional(readOnly = true)
    public List<ExamComponent> findAll() {
        return examComponentRepository.findAll();
    }
    
    /**
     * Find a single page of ExamComponents objects
     *
     * @param page - the page number to retrieve
     * @param size - the number of records on each page
     * @return a SearchResult set with the list of ExamComponents
     */
    @PreAuthorize("@securityChecker.checkReader(authentication)")
    public SearchResult<ExamComponent> findExamComponents(int page, int size) {
        PageRequest pageRequest = new PageRequest(page, size);
        Page<ExamComponent> examComponents = examComponentRepository.findAll(pageRequest);
        PageInfo pageInfo = new PageInfo(page, size, '', (Long) examComponents.total);
        return new SearchResult<>(pageInfo, examComponents.toList());
    }
    
    /**
     * Find ExamComponents by a specific timetabledDate and timetabledSession
     * 
     * @param timetableDate
     * @param timetableSession
     * @return
     */
    @PreAuthorize("@securityChecker.checkReader(authentication)")
    public List<ExamComponent> findExamComponentsByDateAndSession(Date timetableDate, String timetableSession) {
        return examComponentRepository.findByTimetableDateAndTimetableSession(timetableDate, timetableSession);
    }
    
    /**
     * Save an ExamComponent object to the database
     *     
     * @param examComponent - the ExamComponent object to save
     * @return the saved ExamComponent object
     */
    @Override
    public ExamComponent save(ExamComponent examComponent) {
        log.info("******  service.ExamComponentService.save")
        if (examComponent.examComponentId != null && examComponent.examComponentId)
            examComponent = update(examComponent);
        else
            examComponent = create(examComponent);
        return examComponent;
    }
    
    /**
     * Delete an ExamComponent object from the database
     * 
     * @param examComponentId - the ID of the ExamComponent object to delete    
     */
    public delete(Integer examComponentId) {
        delete(findById(examComponentId))
    }
    
    /**
     * create (PRIVATE)
     * *
     * @param examComponent
     * @return examComponent
     */
    private ExamComponent create(ExamComponent examComponent) {
        log.info("**  create new")
        
        if (examComponent.examSeries.examSeriesId != null && examComponent.examSeries.examSeriesId)
            // examSeries.examSeriesId passed
            examComponent.examSeries = examSeriesRepository.findOne(examComponent.examSeries.examSeriesId)
        else if (examComponent.examSeries.examSeries != null && examComponent.examSeries.examYear != null) {
            // examSeries and examYear passed
            List<ExamSeries> examSeriesList = examSeriesRepository.findByExamYearAndExamSeries(examComponent.examSeries.examYear, examComponent.examSeries.examSeries)
            for (ExamSeries examSeries : examSeriesList) {
                // scan through all matching examSeries, examYear to find matching examBoardId or matching examBoardCode
                if ((examComponent.examSeries.examBoard.id != null && examComponent.examSeries.examBoard.id == examSeries.examBoard.id) ||
                (examComponent.examSeries.examBoard.boardCode != null && examComponent.examSeries.examBoard.boardCode == examSeries.examBoard.boardCode)) {
                    examComponent.examSeries = examSeries;
                    break;
                }
            }
        } else
            // error - missing some info required to locate the correct examSeries details
            throw new BadRequestException("Missing parameters; Required: Either examSeriesId or examSeriesYear and examSeriesId and examBoard");
        
        // check if passed examComponent already exists (matching component code and examsSeries
        List<ExamComponent> examComponents = examComponentRepository.findByCode(examComponent.code);
        // scan through all found examComponents to check examSeries
        for (ExamComponent examComponentTest : examComponents) {
            if (examComponentTest.examSeries.examSeriesId == examComponent.examSeries.examSeriesId) {
                // found matching examComponent, so use it.
                examComponent.examComponentId = examComponentTest.examComponentId;
                break;
            }
        }
        
        examComponentRepository.save(examComponent);
        return examComponent;
    }
    
    
    private ExamComponent update(ExamComponent examComponent) {
        log.info("**  update: " + String.valueOf(examComponent.examComponentId))
        
        ExamComponent updateExamComponent = findById(examComponent.examComponentId)
        ValidationUtils.assertNotNull(updateExamComponent, "examComponent not found");
        
        examComponent.code = examComponent.code == null ? updateExamComponent.code : (updateExamComponent.code != examComponent.code ? examComponent.code : updateExamComponent.code)
        examComponent.title = examComponent.title == null ? updateExamComponent.title : (updateExamComponent.title != examComponent.title ? examComponent.title : updateExamComponent.title)
        examComponent.teacherMarks = examComponent.teacherMarks == null ? updateExamComponent.teacherMarks : (updateExamComponent.teacherMarks != examComponent.teacherMarks ? examComponent.teacherMarks : updateExamComponent.teacherMarks)
        examComponent.maximumMark = examComponent.maximumMark == null ? updateExamComponent.maximumMark : (updateExamComponent.maximumMark != examComponent.maximumMark ? examComponent.maximumMark : updateExamComponent.maximumMark)
        examComponent.gradeset = examComponent.gradeset == null ? updateExamComponent.gradeset : (updateExamComponent.gradeset != examComponent.gradeset ? examComponent.gradeset : updateExamComponent.gradeset)
        examComponent.dueDate = examComponent.dueDate == null ? updateExamComponent.dueDate : (updateExamComponent.dueDate != examComponent.dueDate ? examComponent.dueDate : updateExamComponent.dueDate)
        examComponent.timetabled = examComponent.timetabled == null ? updateExamComponent.timetabled : (updateExamComponent.timetabled != examComponent.timetabled ? examComponent.timetabled : updateExamComponent.timetabled)
        examComponent.timetableDate = examComponent.timetableDate == null ? updateExamComponent.timetableDate : (updateExamComponent.timetableDate != examComponent.timetableDate ? examComponent.timetableDate : updateExamComponent.timetableDate)
        examComponent.timetableSession = examComponent.timetableSession == null ? updateExamComponent.timetableSession : (updateExamComponent.timetableSession != examComponent.timetableSession ? examComponent.timetableSession : updateExamComponent.timetableSession)
        examComponent.timeAllowed = examComponent.timeAllowed == null ? updateExamComponent.timeAllowed : (updateExamComponent.timeAllowed != examComponent.timeAllowed ? examComponent.timeAllowed : updateExamComponent.timeAllowed)
        
        examComponentRepository.save(examComponent);
        return examComponent;
    }
    
    /* (non-Javadoc)
     * @see uk.ac.reigate.services.ICoreDataService#delete(java.lang.Object)
     */
    @Override
    public void delete(ExamComponent obj) {
        examComponentRepository.delete(obj)
        
    }
}
