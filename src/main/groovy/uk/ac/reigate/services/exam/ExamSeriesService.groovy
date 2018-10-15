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
import uk.ac.reigate.domain.academic.AcademicYear
import uk.ac.reigate.domain.exam.ExamBoard
import uk.ac.reigate.domain.exam.ExamSeries
import uk.ac.reigate.exceptions.InvalidOperationException
import uk.ac.reigate.model.PageInfo
import uk.ac.reigate.model.SearchResult
import uk.ac.reigate.repositories.academic.AcademicYearRepository
import uk.ac.reigate.repositories.exam.ExamBoardRepository
import uk.ac.reigate.repositories.exam.ExamSeriesRepository
import uk.ac.reigate.services.ICoreDataService
import uk.ac.reigate.util.exception.BadRequestException;



@Service
public class ExamSeriesService implements ICoreDataService<ExamSeries, Integer>{
    private final static Logger log = Logger.getLogger(ExamSeriesService.class.getName())
    
    @Autowired
    ExamSeriesRepository examSeriesRepository
    
    @Autowired
    ExamBoardRepository examBoardRepository
    
    @Autowired
    AcademicYearRepository academicYearRepository
    /**
     * findExamSeries
     * 
     * @param examSeriesId - the ID to search for
     * @return ExamSeries - the ExamSeries object that matches the ID, or null if not found
     */
    @Override
    @Transactional(readOnly = true)
    public ExamSeries findById(Integer examSeriesId) {
        return examSeriesRepository.findOne(examSeriesId);
    }
    
    /**
     * Find all ExamSeries
     *
     * @return a SearchResult set with the list of ExamSeries
     */
    @PreAuthorize("@securityChecker.checkReader(authentication)")
    @Transactional(readOnly = true)
    public List<ExamSeries> findExamSeriesList(Integer examBoardId, Integer yearId) {
        List<ExamSeries> examSeriesList;
        if (examBoardId!=null && yearId!=null) {
            examSeriesList = examSeriesRepository.findByExamBoard_IdAndAcademicYear_Id(examBoardId, yearId);
        }else if (examBoardId!=null){
            examSeriesList = examSeriesRepository.findByExamBoard_Id(examBoardId);
        }else if(yearId!=null){
            examSeriesList = examSeriesRepository.findByAcademicYear_Id(yearId);
        }else {
            examSeriesList = findAll();
        }
        return examSeriesList
    }
    
    /**
     * Find a single page of ExamSeries objects
     *
     * @param page - the page number to retrieve
     * @param size - the number of records on each page
     * @return a SearchResult set with the list of ExamSeries
     */
    @PreAuthorize("@securityChecker.checkReader(authentication)")
    @Transactional(readOnly = true)
    public SearchResult<ExamSeries> findExamSeriesListPageable(Pageable pageable, Integer examBoardId) {
        Page<ExamSeries> examSeriesList;
        if (examBoardId!=null) {
            examSeriesList = examSeriesRepository.findByExamBoard_Id(examBoardId, pageable);
        } else {
            examSeriesList = examSeriesRepository.findAll(pageable);
        }
        PageInfo pageInfo = new PageInfo(pageable.getPageNumber(), pageable.getPageSize(), '', (Long) examSeriesList.total);
        return new SearchResult<>(pageInfo, examSeriesList.toList());
    }
    
    /**
     * Save an ExamSeries object to the database
     * 
     * @param ExamSeries
     */
    
    public ExamSeries saveExamSeries(ExamSeries examSeries) {
        if (examSeries.examSeriesId != null && examSeries.examSeriesId)
            examSeries = update(examSeries);
        else
            examSeries = create(examSeries);
        return examSeries;
    }
    
    /**
     * Delete an ExamSeries from the database
     * 
     * @param examSeriesId
     */
    public delete(Integer examSeriesId) {
        ExamSeries deleteExamSeries = examSeriesRepository.findOne(examSeriesId)
        if (deleteExamSeries == null) {
            throw new BadRequestException("exam series ID does not exist.");
        }
        
        examSeriesRepository.delete(deleteExamSeries)
    }
    
    /**
     * create  (PRIVATE)
     * @param ExamSeries		
     */
    private ExamSeries create(ExamSeries examSeries) {
        log.info("**  ExamSeries  create")
        if(examSeries.academicYear.id!=null && examSeries.academicYear.id){
            AcademicYear academicYear = academicYearRepository.findOne(examSeries.academicYear.id)
            if(academicYear!= null && academicYear.id == examSeries.academicYear.id){
                examSeries.academicYear = academicYear;
            }else
                throw new BadRequestException("Specified Academic Year does not exist")
        }
        if (examSeries.examBoard.id != null && examSeries.examBoard.id) {
            // examBoardId passed, so check examBoardId exists
            ExamBoard examBoard = examBoardRepository.findOne(examSeries.examBoard.id)
            if (examBoard != null && examBoard.id == examSeries.examBoard.id) {
                // examBoardId exists, so use it.
                examSeries.examBoard = examBoard;
            } else {
                // examBoardId doesn't exist.
                throw new BadRequestException("specified exam Board ID does not exist");
            }
        } else {
            // examBoardCode should be passed instead, so check examBoardCode exists
            List<ExamBoard> examBoards = examBoardRepository.findByBoardIdentifier(examSeries.examBoard.boardIdentifier)
            // if found, should be first element
            if (examBoards != null && examBoards[0].boardIdentifier == examSeries.examBoard.boardIdentifier) {
                // examBoardCode found, so use it
                examSeries.examBoard = examBoards[0];
            } else {
                // examBoardCode doesn't exist, so create it.
                ExamBoard newExamBoard = new ExamBoard.Builder().boardIdentifier(examSeries.examBoard.boardIdentifier).build();
                examBoardRepository.save(newExamBoard);
                examSeries.examBoard = newExamBoard;
            }
        }
        
        // check if passed examSeries already exists (matching examBoard, examYear and examSeries(
        List<ExamSeries> examSeriesList = examSeriesRepository.findByExamYearAndExamSeries(examSeries.examYear, examSeries.examSeries);
        // scan through all matching examYear, examSeries for matching examBoard.
        for (ExamSeries examSeriesTest : examSeriesList) {
            if ((examSeries.examBoard.id != null && examSeries.examBoard.id == examSeriesTest.examBoard.id) ||
            (examSeries.examBoard.boardIdentifier != null && examSeries.examBoard.boardIdentifier == examSeriesTest.examBoard.boardIdentifier)) {
                //matching examBoard found, so use it
                examSeries.examSeriesId = examSeriesTest.examSeriesId;
                break;
            }
        }
        
        log.info("**  id    : " + examSeries.examSeriesId);
        return save(examSeries);
    }
    
    /**
     * update  (PRIVATE)
     * @param ExamSeries	
     */
    private ExamSeries update(ExamSeries examSeries) {
        log.info("**  examSeries  upadte")
        ExamSeries updateExamSeries =findById(examSeries.examSeriesId)
        if (updateExamSeries == null) {
            throw new BadRequestException("Specified exam series ID: " + String.valueOf(examSeries.examSeriesId) + " does not exist")
        }
        examSeries.examYear = examSeries.examYear == null ? updateExamSeries.examYear : (updateExamSeries.examYear != examSeries.examYear ? examSeries.examYear : updateExamSeries.examYear)
        examSeries.examSeries = examSeries.examSeries == null ? updateExamSeries.examSeries : (updateExamSeries.examSeries != examSeries.examSeries ? examSeries.examSeries : updateExamSeries.examSeries)
        examSeries.entrySubmitted = examSeries.entrySubmitted == null ? updateExamSeries.entrySubmitted : (updateExamSeries.entrySubmitted != examSeries.entrySubmitted ? examSeries.entrySubmitted : updateExamSeries.entrySubmitted)
        examSeries.academicYear = examSeries.academicYear ==  null ? updateExamSeries.academicYear : examSeries.academicYear
        return save(examSeries);
    }
    
    /**
     * handleException
     * @param HttpServletRequest
     * @param Exception
     * @return ResponseEntity<String>	
     */
    @ExceptionHandler(Exception.class)
    public ResponseEntity<String> handleException(HttpServletRequest req, Exception ex) {
        log.info("****** handleException: " + ex)
        return new ResponseEntity<String>("Error: " + ex, HttpStatus.NOT_FOUND)
    }
    
    /* (non-Javadoc)
     * @see uk.ac.reigate.services.ICoreDataService#findAll()
     */
    @Override
    public List<ExamSeries> findAll() {
        return examSeriesRepository.findAll()
    }
    
    /* (non-Javadoc)
     * @see uk.ac.reigate.services.ICoreDataService#delete(java.lang.Object)
     */
    @Override
    public void delete(ExamSeries obj) {
        throw new InvalidOperationException("ExamSeries should not be deleted")
        
    }
    
    @Override
    public ExamSeries save(ExamSeries examSeries) {
        return examSeriesRepository.save(examSeries);
    }
}
