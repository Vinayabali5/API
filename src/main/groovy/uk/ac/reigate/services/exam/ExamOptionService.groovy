package uk.ac.reigate.services.exam

import java.util.List;
import java.util.logging.Logger

import javax.servlet.http.HttpServletRequest

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.security.access.prepost.PreAuthorize
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import org.springframework.web.bind.annotation.ExceptionHandler

import uk.ac.reigate.domain.academic.AcademicYear;
import uk.ac.reigate.domain.academic.Student;
import uk.ac.reigate.domain.exam.ExamOption
import uk.ac.reigate.domain.exam.ExamType
import uk.ac.reigate.domain.exam.Syllabus
import uk.ac.reigate.model.PageInfo
import uk.ac.reigate.model.SearchResult
import uk.ac.reigate.repositories.exam.ExamOptionRepository
import uk.ac.reigate.repositories.exam.ExamTypeRepository
import uk.ac.reigate.repositories.exam.SyllabusRepository
import uk.ac.reigate.services.ICoreDataService
import uk.ac.reigate.util.exception.BadRequestException

@Service
public class ExamOptionService implements ICoreDataService<ExamOption, Integer>{
    private final static Logger log = Logger.getLogger(ExamOptionService.class.getName())
    
    @Autowired
    ExamOptionRepository examOptionRepository
    
    @Autowired
    ExamTypeRepository examTypeRepository
    
    @Autowired
    SyllabusRepository syllabusRepository
    
    /**
     * Default NoArgs constructor
     */
    ExamOptionService() {}
    
    /**
     * Autowired constructor
     * 
     * @param optionService
     */
    ExamOptionService(ExamOptionRepository examOptionRepository) {
        this.examOptionRepository = examOptionRepository;
    }
    
    
    public List<ExamOption> searchByOptionEntryCode(String optionEntryCode) {
        List<ExamOption> examOptions = examOptionRepository.findByOptionEntryCodeContaining(optionEntryCode)
        return examOptions
    }
    
    /**
     * Find an individual exam basedata Exam Option by examOptionId
     *
     *     @param optionId - the ID to search for
     *     @return Option - the Option object that matches the ID, or null if not found
     */
    @Override
    @Transactional(readOnly = true)
    public ExamOption findById(Integer examOptionId) {
        return examOptionRepository.findOne(examOptionId)
    }
    
    /**
     * Find all Exam Options
     *
     * @return a SearchResult set with the list of Exam Options
     */
    @Override
    @Transactional(readOnly = true)
    public List<ExamOption> findAll() {
        log.info("******  findExamOptions(examOptional params)");
        return examOptionRepository.findAll();
    }
    
    /**
     * Find a single page of Syllabus objects
     *
     * @param pageable - the page details to retrieve
     * @param examBoardId - the examBoardId to retrieve data for
     * @param examYear - the examYear to retrieve data for
     * @param examSeries - the examSeries to retrieve data for
     * @param syllabusId - the specific syllabusId to retrieve option data for
     * @return a SearchResult set with the list of Syllabi
     */
    public SearchResult<ExamOption> findExamOptions(
            Pageable pageable,
            Optional<Integer> examBoardId,
            Optional<String> examYear,
            Optional<String> examSeries) {
        
        log.info("******  findExamOptions(pageable, examBoardid (examOptional), examYear (examOptional), examSeries (examOptional))");
        Page<ExamOption> examOptions
        
        examOptions = examOptionRepository.findAll(pageable);
        PageInfo pageInfo = new PageInfo(page, size, '', (Long) examOptions.total);
        return new SearchResult<>(pageInfo, examOptions.toList());
    }
    
    /**
     * Find all Exam Options for a specified syllabusId
     *
     * @param syllabusId - the specified syllabusId to retrieve exam option data for
     * @return a SearchResult set with the list of Exam Options
     */
    @Transactional(readOnly = true)
    public List<ExamOption> findExamOptions(Integer syllabusId) {
        log.info("******  findExamOptions(Integer syllabusId)");
        List<ExamOption> examOptions
        Syllabus syllabus = new Syllabus();
        syllabus.syllabusId = syllabusId;
        examOptions = examOptionRepository.findBySyllabus(syllabus);
        return examOptions
    }
    
    /**
     * Save an Exam Option to the database
     * 
     * @param ExamOption
     */
    public ExamOption saveExamOption(ExamOption examOption) {
        log.info("******  service.ExamOptionService.save")
        if (examOption.examOptionId != null && examOption.examOptionId)
            examOption = update(examOption);
        else
            examOption = create(examOption);
        return examOption
    }
    
    @Override
    public ExamOption save(ExamOption examOption){
        return examOptionRepository.save(examOption);
    }
    /**
     * Delete an Exam Option from the database
     * 
     * @param long examOptionId
     */
    public deleteExamOption(long examOptionId) {
        ExamOption deleteExamOption = findById(examOptionId)
        if (deleteExamOption == null) {
            throw new BadRequestException("Specified exam option ID: " + String.valueOf(examOptionId) + " not found.");
        }
        delete(deleteExamOption)
    }
    
    /**
     * create  (PRIVATE)
     * @param Exam Option
     * @return Exam Option
     */
    private ExamOption create(ExamOption examOption) {
        log.info("**  create new")
        
        if (examOption.syllabus.syllabusId != null && examOption.syllabus.syllabusId)
            // examOption.syllabus.syllabusId passed
            examOption.syllabus = syllabusRepository.findOne(examOption.syllabus.syllabusId)
        else if (examOption.syllabus.code != null) {
            // examOption.syllabus.code passed
            List<Syllabus> syllabusList = syllabusRepository.findByCode(examOption.syllabus.code)
            // scan through all matching syllabus.codes
            for (Syllabus syllabus : syllabusList) {
                if ((examOption.syllabus.examSeries.examSeriesId != null && examOption.syllabus.examSeries.examSeriesId == syllabus.examSeries.examSeriesId) ||
                (examOption.syllabus.examSeries.examYear != null && examOption.syllabus.examSeries.examSeries != null &&
                examOption.syllabus.examSeries.examYear == syllabus.examSeries.examYear && examOption.syllabus.examSeries.examSeries == syllabus.examSeries.examSeries &&
                ((examOption.syllabus.examSeries.examBoard.id != null && examOption.syllabus.examSeries.examBoard.id == syllabus.examSeries.examBoard.id) ||
                (examOption.syllabus.examSeries.examBoard.boardCode != null && examOption.syllabus.examSeries.examBoard.boardCode == syllabus.examSeries.examBoard.boardCode)))) {
                    // matching examOption.syllabus found, so use it.
                    examOption.syllabus = syllabus;
                    break;
                }
            }
        } else {
            // Insufficient syllabus details passed to locate syllabus.
            throw new BadRequestException("Neither syllabusId nor code passed");
        }
        
        if (examOption.syllabus.syllabusId == null) {
            // Matching syllabus not located, so cannot save the examOption.
            throw new BadRequestException("No match syllabus found.");
        }
        
        // check if passed option already exists (matching examOption.optionEntryCode and examOption.syllabus.syllabusId
        List<ExamOption> examOptions = examOptionRepository.findByOptionEntryCode(examOption.optionEntryCode);
        for (ExamOption examOptionTest : examOptions) {
            if (examOptionTest.syllabus.syllabusId == examOption.syllabus.syllabusId) {
                // found matching option, so use it
                examOption.examOptionId = examOptionTest.examOptionId;
                break;
            }
        }
        
        updateExamType(examOption.examTypeCert);
        updateExamType(examOption.examTypeUnit);
        
        return  save(examOption);
    }
    
    /**
     * update  (PRIVATE)
     * @param ExamOption
     */
    private ExamOption update(ExamOption examOption) {
        log.info("**  update: " + String.valueOf(examOption.examOptionId))
        
        ExamOption updateExamOption = findById(examOption.examOptionId)
        if (updateExamOption == null) {
            throw new BadRequestException("Specified exam option ID: " + String.valueOf(examOption.examOptionId) + " not found.");
        }
        
        if (examOption.examTypeCert == null && updateExamOption.examTypeCert != null) {
            examOption.examTypeCert = updateExamOption.examTypeCert
        } else {
            updateExamType(examOption.examTypeCert)
        }
        
        if (examOption.examTypeUnit == null && updateExamOption.examTypeUnit != null) {
            examOption.examTypeUnit = updateExamOption.examTypeUnit
        } else {
            updateExamType(examOption.examTypeUnit)
        }
        
        examOption.optionEntryCode = examOption.optionEntryCode == null ? updateExamOption.optionEntryCode : (updateExamOption.optionEntryCode != examOption.optionEntryCode ? examOption.optionEntryCode : updateExamOption.optionEntryCode)
        examOption.syllabus = examOption.syllabus == null ? updateExamOption.syllabus : (updateExamOption.syllabus != examOption.syllabus ? examOption.syllabus : updateExamOption.syllabus)
        examOption.process = examOption.process == null ? updateExamOption.process : (updateExamOption.process != examOption.process ? examOption.process : updateExamOption.process)
        examOption.qcaClassificationCode = examOption.qcaClassificationCode == null ? updateExamOption.qcaClassificationCode : (updateExamOption.qcaClassificationCode != examOption.qcaClassificationCode ? examOption.qcaClassificationCode : updateExamOption.qcaClassificationCode)
        examOption.qcaAccreditationNo = examOption.qcaAccreditationNo == null ? updateExamOption.qcaAccreditationNo : (updateExamOption.qcaAccreditationNo != examOption.qcaAccreditationNo ? examOption.qcaAccreditationNo : updateExamOption.qcaAccreditationNo)
        examOption.optionTitle = examOption.optionTitle == null ? updateExamOption.optionTitle : (updateExamOption.optionTitle != examOption.optionTitle ? examOption.optionTitle : updateExamOption.optionTitle)
        examOption.feeDefined = examOption.feeDefined == null ? updateExamOption.feeDefined : (updateExamOption.feeDefined != examOption.feeDefined ? examOption.feeDefined : updateExamOption.feeDefined)
        examOption.examinationFee = examOption.examinationFee == null ? updateExamOption.examinationFee : (updateExamOption.examinationFee != examOption.examinationFee ? examOption.examinationFee : updateExamOption.examinationFee)
        examOption.firstForecastGradeGradeset = examOption.firstForecastGradeGradeset == null ? updateExamOption.firstForecastGradeGradeset : (updateExamOption.firstForecastGradeGradeset != examOption.firstForecastGradeGradeset ? examOption.firstForecastGradeGradeset : updateExamOption.firstForecastGradeGradeset)
        examOption.secondForecastGradeGradeset = examOption.secondForecastGradeGradeset == null ? updateExamOption.secondForecastGradeGradeset : (updateExamOption.secondForecastGradeGradeset != examOption.secondForecastGradeGradeset ? examOption.secondForecastGradeGradeset : updateExamOption.secondForecastGradeGradeset)
        examOption.resultType = examOption.resultType == null ? updateExamOption.resultType : (updateExamOption.resultType != examOption.resultType ? examOption.resultType : updateExamOption.resultType)
        examOption.firstGradeResultGradeset = examOption.firstGradeResultGradeset == null ? updateExamOption.firstGradeResultGradeset : (updateExamOption.firstGradeResultGradeset != examOption.firstGradeResultGradeset ? examOption.firstGradeResultGradeset : updateExamOption.firstGradeResultGradeset)
        examOption.secondGradeResultGradeset = examOption.secondGradeResultGradeset == null ? updateExamOption.secondGradeResultGradeset : (updateExamOption.secondGradeResultGradeset != examOption.secondGradeResultGradeset ? examOption.secondGradeResultGradeset : updateExamOption.secondGradeResultGradeset)
        examOption.endorsementToFirstGradeResultGradeset = examOption.endorsementToFirstGradeResultGradeset == null ? updateExamOption.endorsementToFirstGradeResultGradeset : (updateExamOption.endorsementToFirstGradeResultGradeset != examOption.endorsementToFirstGradeResultGradeset ? examOption.endorsementToFirstGradeResultGradeset : updateExamOption.endorsementToFirstGradeResultGradeset)
        examOption.endorsementToSecondGradeResultGradeset = examOption.endorsementToSecondGradeResultGradeset == null ? updateExamOption.endorsementToSecondGradeResultGradeset : (updateExamOption.endorsementToSecondGradeResultGradeset != examOption.endorsementToSecondGradeResultGradeset ? examOption.endorsementToSecondGradeResultGradeset : updateExamOption.endorsementToSecondGradeResultGradeset)
        examOption.maxMarkUms = examOption.maxMarkUms == null ? updateExamOption.maxMarkUms : (updateExamOption.maxMarkUms != examOption.maxMarkUms ? examOption.maxMarkUms : updateExamOption.maxMarkUms)
        examOption.noOfComponents = examOption.noOfComponents == null ? updateExamOption.noOfComponents : (updateExamOption.noOfComponents != examOption.noOfComponents ? examOption.noOfComponents : updateExamOption.noOfComponents)
        
        return save(examOption);
    }
    
    
    /**
     * updateExamType  (PRIVATE)
     * @param ExamType	
     */
    
    private updateExamType(ExamType examType) {
        log.info("******  updateExamType")
        if (examType != null) {
            List<ExamType> examTypes = examTypeRepository.findByQualificationAndLevel(examType.qualification, examType.level)
            if (examTypes.size()) {
                examType.examTypeId = examTypes.get(0).getExamTypeId();
                log.info("**  using examTypeId: " + String.valueOf(examType.examTypeId));
            } else {
                examTypeRepository.save(examType);
                log.info("**  create new examTypeId: " + String.valueOf(examType.examTypeId));
            }
        }
    }
    
    /**
     * handleException
     * @param HttpServletRequest
     * @param Exception
     * @return ResponseEntity<string>
     */
    @ExceptionHandler(Exception.class)
    public ResponseEntity<String> handleException(HttpServletRequest req, Exception ex) {
        log.info("****** handleException: " + ex)
        return new ResponseEntity<String>("Error: " + ex, HttpStatus.NOT_FOUND)
    }
    
    @Override
    public void delete(ExamOption obj) {
        examOptionRepository.delete(obj)
        
    }
}
