package uk.ac.reigate.services.exam

import java.util.logging.Logger

import javax.servlet.http.HttpServletRequest

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.security.access.prepost.PreAuthorize
import org.springframework.stereotype.Service
import org.springframework.web.bind.annotation.ExceptionHandler

import uk.ac.reigate.domain.exam.ExamType
import uk.ac.reigate.repositories.exam.ExamTypeRepository;
import uk.ac.reigate.services.ICoreDataService
import uk.ac.reigate.util.exception.BadRequestException;

@Service
public class ExamTypeService implements ICoreDataService<ExamType, Long>{
    private final static Logger log = Logger.getLogger(ExamTypeService.class.getName())
    
    @Autowired
    ExamTypeRepository examTypeRepository
    
    /* (non-Javadoc)
     * @see uk.ac.reigate.services.ICoreDataService#findAll()
     */
    @Override
    public List<ExamType> findAll() {
        List<ExamType> examTypes = examTypeRepository.findAll();
        return examTypes;
    }
    
    /* (non-Javadoc)
     * @see uk.ac.reigate.services.ICoreDataService#findById(java.lang.Object)
     */
    @Override
    public ExamType findById(Long examTypeId) {
        log.info("******  ExamTypeService.findOne");
        ExamType examType = examTypeRepository.findOne(examTypeId)
        if (examType == null) {
            log.info("** Warning: examType " + String.valueOf(examTypeId) + " doesn't exist");
            throw new BadRequestException("Specified exam type ID: " + String.valueOf(examTypeId) + " does not exist.");
        }
        return examType
    }
    
    /* (non-Javadoc)
     * @see uk.ac.reigate.services.ICoreDataService#save(java.lang.Object)
     */
    @Override
    public ExamType save(ExamType examType) {
        if (examType.examTypeId != null && examType.examTypeId)
            examType = update(examType);
        else
            examType = create(examType);
        return examType
    }
    
    /** Deletes the ExamType Object of examTypeId
     * @param examTypeId
     * @return
     */
    public deleteById(long examTypeId) {
        ExamType deleteExamType = examTypeRepository.findOne(examTypeId)
        delete(deleteExamType)
    }
    
    /* (non-Javadoc)
     * @see uk.ac.reigate.services.ICoreDataService#delete(java.lang.Object)
     */
    @Override
    public void delete(ExamType examType){
        if (examType == null) {
            throw new BadRequestException("Specified exam type  does not exist.");
        }
        examTypeRepository.delete(examType)
    }
    
    /**
     * @param examType
     * @return
     */
    private ExamType create(ExamType examType) {
        examTypeRepository.save(examType);
        log.info("**  id    : " + examType.examTypeId);
        return examType;
    }
    
    /** Updates ExamType object
     * @param examType
     * @return
     */
    private ExamType update(ExamType examType) {
        ExamType updateExamType = examTypeRepository.findOne(examType.examTypeId)
        if (updateExamType == null) {
            throw new BadRequestException("Specified exam type ID: " + String.valueOf(examType.examTypeId) + " does not exist.");
        }
        
        examType.qualification = examType.qualification == null ? updateExamType.qualification : (updateExamType.qualification != examType.qualification ? examType.qualification : updateExamType.qualification)
        examType.level = examType.level == null ? updateExamType.level : (updateExamType.level != examType.level ? examType.level : updateExamType.level)
        
        examTypeRepository.save(examType);
        return examType;
    }
}
