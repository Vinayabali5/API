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

import uk.ac.reigate.domain.exam.ExamComponent
import uk.ac.reigate.domain.exam.ExamOption
import uk.ac.reigate.domain.exam.OptionComponent
import uk.ac.reigate.domain.exam.OptionComponentPk;
import uk.ac.reigate.model.PageInfo
import uk.ac.reigate.model.SearchResult
import uk.ac.reigate.repositories.exam.ExamComponentRepository
import uk.ac.reigate.repositories.exam.OptionComponentRepository
import uk.ac.reigate.services.ICoreDataService
import uk.ac.reigate.repositories.exam.ExamOptionRepository;
import uk.ac.reigate.util.exception.BadRequestException;

@Service
public class OptionComponentService implements ICoreDataService<OptionComponent,OptionComponentPk>{
    private final static Logger log = Logger.getLogger(OptionComponentService.class.getName())
    
    @Autowired
    OptionComponentRepository optionComponentRepository
    
    @Autowired
    ExamOptionRepository examOptionRepository
    
    @Autowired
    ExamComponentRepository examComponentRepository
    
    /**
     * Default NoArgs constructor
     */
    OptionComponentService() {}
    
    /**
     * Autowired constructor
     * 
     * @param optionComponentRepository
     */
    OptionComponentService(OptionComponentRepository optionComponentRepository) {
        this.optionComponentRepository = optionComponentRepository;
    }
    
    /**
     * find an individual exam basedata OptionComponent by optionId and examComponentId
     * 
     * @param optionId
     * @param componentId   
     * @return OptionComponent
     */
    @Transactional(readOnly = true)
    public OptionComponent findOptionComponent(Integer examOptionId, Integer examComponentId) {
        findById(new OptionComponentPk(examOptionId, examComponentId));
    }
    
    
    /**
     * find an individual exam basedata OptionComponent by optionComponentId
     * @param optionComponentId
     * @return
     */
    @Override
    @Transactional(readOnly = true)
    public OptionComponent findById(OptionComponentPk optionComponentPk) {
        return optionComponentRepository.findOne(optionComponentPk);
    }
    
    /**
     * find all option components
     * 
     * @returns a SearchResult set with the list of option components
     */
    @Override
    @Transactional(readOnly = true)
    public List<OptionComponent> findAll() {
        return optionComponentRepository.findAll();
    }
    
    /**
     * Find a single page of OptionComponent objects
     * 
     * @param page - the page number to retrieve
     * @param size - the number of records on each page
     * @return a SearchResult set with the list of OptionComponents
     */
    @PreAuthorize("@securityChecker.checkReader(authentication)")
    public SearchResult<OptionComponent> findOptionComponents(int page, int size) {
        PageRequest pageresuest = new PageRequest(page, size);
        Page<OptionComponent> optionComponents = optionComponentRepository.findAll(pageRequest);
        PageInfo pageInfo = new PageInfo(page, size, '', (Long) optionComponents.total);
        return new SearchResult<>(pageInfo, optionComponents.toList());
    }
    
    /**
     * save an OptionComponent object to the database - got to deduce optionId and componentId
     * 
     * @param OptionComponent - the OptionComponent object to save
     * @return OptionComponent - the saved OptionComponent object
     */
    @Override
    public OptionComponent save(OptionComponent optionComponent) {
        
        if (!optionComponent.examOptionId) {
            log.info("About to load options for optionEntryCode: " + optionComponent.examOption.optionEntryCode)
            List<ExamOption> examOptions = examOptionRepository.findByOptionEntryCode(optionComponent.examOption.optionEntryCode);
            for (ExamOption optionToTest : examOptions) {
                if (optionComponent.examOption.syllabus.syllabusId != null &&
                optionComponent.examOption.syllabus.syllabusId == optionToTest.syllabus.syllabusId) {
                    optionComponent.examOptionId = optionToTest.examOptionId;
                    break;
                }
                if (optionComponent.examOption.syllabus.examSeries.examSeriesId != null &&
                optionComponent.examOption.syllabus.examSeries.examSeriesId == optionToTest.syllabus.examSeries.examSeriesId) {
                    optionComponent.examOptionId = optionToTest.examOptionId;
                    break;
                }
                if (optionComponent.examOption.syllabus.examSeries.examSeries != null &&
                optionComponent.examOption.syllabus.examSeries.examSeries == optionToTest.syllabus.examSeries.examSeries &&
                optionComponent.examOption.syllabus.examSeries.examYear != null &&
                optionComponent.examOption.syllabus.examSeries.examYear == optionToTest.syllabus.examSeries.examYear &&
                ((optionComponent.examOption.syllabus.examSeries.examBoard.id != null &&
                optionComponent.examOption.syllabus.examSeries.examBoard.id == optionToTest.syllabus.examSeries.examBoard.id) ||
                (optionComponent.examOption.syllabus.examSeries.examBoard.boardCode != null &&
                optionComponent.examOption.syllabus.examSeries.examBoard.boardCode == optionToTest.syllabus.examSeries.examBoard.boardCode))) {
                    optionComponent.examOptionId = optionToTest.examOptionId;
                    break;
                }
            }
        }
        
        if (!optionComponent.examComponentId) {
            log.info("About to load examComponents for code: " + optionComponent.examComponent.code);
            List<ExamComponent> examComponents = examComponentRepository.findByCode(optionComponent.examComponent.code);
            for (ExamComponent examComponent : examComponents) {
                if (optionComponent.examComponent.examSeries.examSeriesId != null &&
                optionComponent.examComponent.examSeries.examSeriesId == examComponent.examSeries.examSeriesId) {
                    optionComponent.examComponentId = examComponent.examComponentId;
                    break;
                }
                if (optionComponent.examComponent.examSeries.examSeries != null &&
                optionComponent.examComponent.examSeries.examSeries == examComponent.examSeries.examSeries &&
                optionComponent.examComponent.examSeries.examYear != null &&
                optionComponent.examComponent.examSeries.examYear == examComponent.examSeries.examYear &&
                ((optionComponent.examComponent.examSeries.examBoard.id != null &&
                optionComponent.examComponent.examSeries.examBoard.id == examComponent.examSeries.examBoard.id) ||
                (optionComponent.examComponent.examSeries.examBoard.boardCode != null &&
                optionComponent.examComponent.examSeries.examBoard.boardCode == examComponent.examSeries.examBoard.boardCode))) {
                    optionComponent.examComponentId = examComponent.examComponentId;
                    break;
                }
            }
        }
        
        if (optionComponent.examOptionId && optionComponent.examComponentId) {
            return saveOptionComponent(optionComponent.examOptionId, optionComponent.examComponentId);
        } else {
            throw new BadRequestException("critical information missing");
        }
    }
    
    /**
     * save and OptionComponent object to the database
     * 
     * @param examOptionId          }
     * @param examComponentId       } the examOptionId and examComponentId to save to the database
     * @return OptionComponent
     */
    public OptionComponent saveOptionComponent(Integer examOptionId, Integer examComponentId) {
        log.info("******  saveOptionComponent(examOptionId, examComponentId)")
        
        try {
            ExamOption examOption = examOptionRepository.findOne(examOptionId);
            if (examOption.examOptionId == null)
                throw new BadRequestException("Specified exam option ID: " + String.valueOf(examOptionId) + " not found.");
            
            log.info("**  exam option loaded - ID: " + String.valueOf(examOption.examOptionId) + ", optionEntryCode: " + examOption.optionEntryCode);
            
            ExamComponent examComponent = examComponentRepository.findOne(examComponentId);
            if (examComponent.examComponentId == null)
                throw new BadRequestException("Specified exam component Id: " + String.valueOf(examComponentId) + " not found.");
            
            log.info("**  exam component loaded - ID: " + String.valueOf(examComponent.examComponentId) + ", code: " + examComponent.code);
            
            OptionComponent saveOptionComponent = new OptionComponent(examOption, examComponent);
            optionComponentRepository.save(saveOptionComponent);
            return saveOptionComponent;
        }
        catch (ex) {
            log.info(">> " + ex)
        }
    }
    
    /**
     * delete
     * @param long examOptionId
     * @param long examComponentId	
     */
    public deleteOptionComponent(Integer examOptionId, Integer examComponentId) {
        OptionComponent optionComponent =  findOptionComponent(examOptionId, examComponentId);
        delete(optionComponent)
    }
    
    
    /* (non-Javadoc)
     * @see uk.ac.reigate.services.ICoreDataService#delete(java.lang.Object)
     */
    @Override
    public void delete(OptionComponent optionComponent) {
        
        if (optionComponent == null) {
            throw new BadRequestException("Specified Option Componenet not found.");
        } else {
            optionComponentRepository.delete(new OptionComponentPk(optionComponent.examOption,optionComponent.examComponent));
        }
    }
}
