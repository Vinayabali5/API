package uk.ac.reigate.services.exam

import com.querydsl.core.types.dsl.BooleanExpression
import java.util.logging.Logger

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import org.springframework.security.access.prepost.PreAuthorize
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional



import uk.ac.reigate.domain.exam.ExamOption;
import uk.ac.reigate.domain.exam.ExamSeries
import uk.ac.reigate.domain.exam.QSyllabus
import uk.ac.reigate.domain.exam.Syllabus
import uk.ac.reigate.model.PageInfo
import uk.ac.reigate.model.SearchResult
import uk.ac.reigate.repositories.exam.ExamOptionRepository;
import uk.ac.reigate.repositories.exam.ExamSeriesRepository
import uk.ac.reigate.repositories.exam.SyllabusRepository
import uk.ac.reigate.services.ICoreDataService
import uk.ac.reigate.util.exception.BadRequestException


@Service
public class SyllabusService implements ICoreDataService<Syllabus, Integer>{
    
    private final static Logger log = Logger.getLogger(SyllabusService.class.getName())
    
    @Autowired
    SyllabusRepository syllabusRepository
    
    @Autowired
    ExamSeriesRepository examSeriesRepository
    
    @Autowired
    ExamOptionRepository examOptionRepository
    
    /**
     * Default NoArgs constructor
     */
    SyllabusService() {}
    
    /**
     * Autowired constructor
     * 
     * @param syllabusRepository
     */
    SyllabusService(SyllabusRepository syllabusRepository) {
        this.syllabusRepository = syllabusRepository;
    }
    
    /**
     * Find an individual exam basedata Syllabus by syllabusId
     * 
     *     @param syllabusId - the ID to search for
     *     @return Syllabus - the Syllabus object that matches the ID, or null if not found
     */
    @Override
    @Transactional(readOnly = true)
    public Syllabus findById(Integer syllabusId) {
        return syllabusRepository.findOne(syllabusId)
    }
    
    /**
     * Find all Syllabi    
     * 
     * @param examBoardId - OPTIONAL - the examBoardId on which to search
     * @param examYear - OPTIONAL - the year on which to search
     * @param _examSeries - OPTIONAL - the exam series on which to search
     * @return a List of Syllabi
     */
    //@Override
    @Transactional(readOnly = true)
    public List<Syllabus> findAll(
            Optional<Integer> examBoardId = null,
            Optional<String> syllabusCode = null,
            Optional<String> examYear = null,
            Optional<String> _examSeries = null) {
        
        log.info("******  findSyllabi(optional params)");
        List<Syllabus> syllabi;
        
        QSyllabus syllabus = QSyllabus.syllabus;
        BooleanExpression syllabusSearch;
        
        ExamSeries examSeries = new ExamSeries();
        
        if (examBoardId.isPresent()) {
            if (syllabusSearch == null) {
                syllabusSearch = (syllabus.examSeries.examBoard.id.eq(examBoardId.get()));
            } else {
                syllabusSearch = syllabusSearch.and(syllabus.examSeries.examBoard.id.eq(examBoardId.get()));
            }
        }
        
        if (syllabusCode.isPresent()) {
            if (syllabusSearch == null) {
                syllabusSearch = (syllabus.syllabus.code.eq(syllabusCode.get()));
            } else {
                syllabusSearch = syllabusSearch.and(syllabus.syllabus.code.eq(syllabusCode.get()));
            }
        }
        
        if (examYear.isPresent()) {
            
            if (syllabusSearch == null) {
                syllabusSearch = (syllabus.examSeries.examYear.eq(examYear.get()));
            } else {
                syllabusSearch = syllabusSearch.and(syllabus.examSeries.examYear.eq(examYear.get()));
            }
        }
        
        if (_examSeries.isPresent()) {
            
            if (syllabusSearch == null) {
                syllabusSearch = (syllabus.examSeries.examSeries.eq(_examSeries.get()));
            } else {
                syllabusSearch = syllabusSearch.and(syllabus.examSeries.examSeries.eq(_examSeries.get()));
            }
        }
        
        if (syllabusSearch == null) {
            syllabi = syllabusRepository.findAll();
        } else {
            syllabi = syllabusRepository.findAll(syllabusSearch);
        }
        
        return syllabi;
    }
    
    /**
     * Find 
     * 
     * @param yearId
     * @return
     */
    public SearchResult<Syllabus> findByYearId(Integer yearId){
        return syllabusRepository.findByYearId(yearId);
    }
    
    /**
     * Find a single page of Syllabus objects
     * 
     * @param pageable - the page and sorting required for the returned results
     * @param examBoardId - OPTIONAL - the examBoardId on which to search
     * @param examYear - OPTIONAL - the year on which to search
     * @param examSeries - OPTIONAL - the exam series on which to search
     * @return a SearchResult set with the list of Syllabi
     */
    @PreAuthorize("@securityChecker.checkReader(authentication)")
    public SearchResult<Syllabus> findSyllabi(
            Pageable pageable,
            Optional<Integer> examBoardId,
            Optional<String> syllabusCode,
            Optional<String> examYear,
            Optional<String> _examSeries) {
        
        log.info("******  findSyllabi(pageable, examBoardId (optional), examYear (optional), examSeries (optional))")
        Page<Syllabus> syllabi
        
        QSyllabus syllabus = QSyllabus.syllabus;
        BooleanExpression syllabusSearch;
        
        ExamSeries examSeries = new ExamSeries();
        
        if (examBoardId.isPresent()) {
            if (syllabusSearch == null) {
                syllabusSearch = (syllabus.examSeries.examBoard.id.eq(examBoardId.get()));
            } else {
                syllabusSearch = syllabusSearch.and(syllabus.examSeries.examBoard.id.eq(examBoardId.get()));
            }
        }
        
        if (syllabusCode.isPresent()) {
            if (syllabusSearch == null) {
                syllabusSearch = (syllabus.syllabus.code.eq(syllabusCode.get()));
            } else {
                syllabusSearch = syllabusSearch.and(syllabus.syllabus.code.eq(syllabusCode.get()));
            }
        }
        
        if (examYear.isPresent()) {
            if (syllabusSearch == null) {
                syllabusSearch = (syllabus.examSeries.examYear.eq(examYear.get()));
            } else {
                syllabusSearch = syllabusSearch.and(syllabus.examSeries.examYear.eq(examYear.get()));
            }
        }
        
        if (_examSeries.isPresent()) {
            if (syllabusSearch == null) {
                syllabusSearch = (syllabus.examSeries.examSeries.eq(_examSeries.get()));
            } else {
                syllabusSearch = syllabusSearch.and(syllabus.examSeries.examSeries.eq(_examSeries.get()));
            }
        }
        
        if (syllabusSearch == null) {
            syllabi = syllabusRepository.findAll(pageable);
        } else {
            syllabi = syllabusRepository.findAll(syllabusSearch, pageable);
        }
        
        PageInfo pageInfo = new PageInfo(pageable.getPageNumber(), pageable.getPageSize(), '', (Long) syllabi.total);
        
        return new SearchResult<>(pageInfo, syllabi.toList());
    }
    
    /**
     * Save a Syllabus object to the database
     * 
     * @param syllabus - the Syllabus object to save
     * @return the saved Syllabus object
     */
    @Override
    public Syllabus save(Syllabus syllabus) {
        log.info("**  Syllabus  save")
        if (syllabus.syllabusId != null && syllabus.syllabusId)
            syllabus = update(syllabus);
        else
            syllabus = create(syllabus);
        return syllabus
    }
    
    /**
     * Delete a Syllabus object from the database
     * 
     * @param syllabusId - the ID to be deleted.
     */
    public deleteSyllabus(Integer syllabusId) {
        Syllabus deleteSyllabus = syllabusRepository.findOne(syllabusId)
        delete(deleteSyllabus)
    }
    
    
    /* (non-Javadoc)
     * @see uk.ac.reigate.services.ICoreDataService#delete(java.lang.Object)
     */
    @Override
    public void delete(Syllabus syllabus){
        if (syllabus == null) {
            throw new BadRequestException("Specified syllabus is not found.");
        }
        syllabusRepository.delete(syllabus)
    }
    
    /** Creates a new Syllabus object in the database
     * @param syllabus
     * @return Syllabus
     */
    private Syllabus create(Syllabus syllabus) {
        log.info("**  Syllabus  create")
        
        if (syllabus.examSeries.examSeriesId != null && syllabus.examSeries.examSeriesId) {
            // examSeries.examSeriesId passed
            syllabus.examSeries = examSeriesRepository.findOne(syllabus.examSeries.examSeriesId)
        } else if (syllabus.examSeries.examYear != null && syllabus.examSeries.examYear && syllabus.examSeries.examSeries != null && syllabus.examSeries.examSeries) {
            // examSeries.examYear and examSeries.examSeries passed
            //			int itemIndex
            List<ExamSeries> examSeriesList = examSeriesRepository.findByExamYearAndExamSeries(syllabus.examSeries.examYear, syllabus.examSeries.examSeries)
            if (syllabus.examSeries.examBoard.id != null && syllabus.examSeries.examBoard.id)		// and syllabus.examSeries.examBoard.id
                for (ExamSeries examSeries : examSeriesList) {
                    if (examSeries.examBoard.id == syllabus.examSeries.examBoard.id) {
                        syllabus.examSeries = examSeries
                        break;
                    }
                }
            
            //				itemIndex = examSeriesList.findIndexOf { examSeries -> examSeries.examBoard.id ==  syllabus.examSeries.examBoard.id }
            else if (syllabus.examSeries.examBoard.boardCode != null)											// and syllabus.examSeries.examBoard.boardCode
                for (ExamSeries examSeries : examSeriesList) {
                    if (examSeries.examBoard.boardCode == syllabus.examSeries.examBoard.boardCode) {
                        syllabus.examSeries = examSeries;
                        break;
                    }
                }
            
            //				itemIndex = examSeriesList.findIndexOf { examSeries -> examSeries.examBoard.boardCode == syllabus.examSeries.examBoard.boardCode }
            else
                throw new BadRequestException("invalid syllabus.examSeries options specified.")
            //			syllabus.examSeries = examSeriesList[itemIndex]
            if (syllabus.examSeries.examSeriesId == null)
                throw new BadRequestException("no matching syllabus.examSeries options found.")
        }
        
        // check if passed syllabus already exists (matching syllabus code and examSeries)
        List<Syllabus> syllabi = syllabusRepository.findByCode(syllabus.code);
        for (Syllabus syllabusTest : syllabi) {
            if (syllabusTest.examSeries.examSeriesId == syllabus.examSeries.examSeriesId) {
                // found matching syllabus, so use it
                syllabus.syllabusId = syllabusTest.syllabusId;
                break;
            }
        }
        
        syllabusRepository.save(syllabus);
        log.info("**  id    : " + syllabus.syllabusId);
        return syllabus;
    }
    
    
    /** Updates the existing Syllabus
     * @param syllabus
     * @return
     */
    private Syllabus update(Syllabus syllabus) {
        log.info("**  Syllabus  update")
        Syllabus updateSyllabus = syllabusRepository.findOne(syllabus.syllabusId)
        if (updateSyllabus == null) {
            throw new BadRequestException("Specified syllabus ID: " + String.valueOf(syllabus.syllabusId) + " not found.");
        }
        syllabus.examSeries = syllabus.examSeries == null ? updateSyllabus.examSeries : (updateSyllabus.examSeries != syllabus.examSeries ? syllabus.examSeries : updateSyllabus.examSeries)
        syllabus.code = syllabus.code == null ? updateSyllabus.code : (updateSyllabus.code != syllabus.code ? syllabus.code : updateSyllabus.code)
        syllabus.title = syllabus.title == null ? updateSyllabus.title : (updateSyllabus.title != syllabus.title ? syllabus.title : updateSyllabus.title)
        syllabusRepository.save(syllabus);
        return syllabus;
    }
    
    /** 
     * @param entryCode
     * @return List of Syllabus 
     */
    @PreAuthorize("@securityChecker.checkReader(authentication)")
    public List<Syllabus> findByEntryCode(String entryCode) {
        List<Syllabus> syllabi = [];
        List<ExamOption> examOptions = examOptionRepository.findByOptionEntryCode(entryCode);
        for (ExamOption examOption : examOptions) {
            syllabi.push(syllabusRepository.findOne(examOption.syllabus.syllabusId));
        }
        return syllabi;
    }
}
