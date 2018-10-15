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

import uk.ac.reigate.domain.exam.ExamSession;
import uk.ac.reigate.model.PageInfo
import uk.ac.reigate.model.SearchResult
import uk.ac.reigate.repositories.exam.ExamSessionRepository;
import uk.ac.reigate.services.ICoreDataService
import uk.ac.reigate.util.exception.BadRequestException
import uk.ac.reigate.utils.ValidationUtils;;

@Service
public class ExamSessionService implements ICoreDataService<ExamSession, Integer>{
    private final static Logger log = Logger.getLogger(ExamSessionService.class.getName())
    
    @Autowired
    ExamSessionRepository examSessionRepository
    
    /**
     * Default NoArgs constructor
     */
    ExamSessionService() {}
    
    /**
     * Autowired constructor
     * 
     * @param examSessionRepository
     */
    ExamSessionService(ExamSessionRepository examSessionRepository) {
        this.examSessionRepository = examSessionRepository;
    }
    
    /**
     * Find an individual ExamSession by examSessionId
     * 
     * @param examSessionId - the ID to search for
     * @return ExamSession - the ExamSession object that matches the ID, or null if not found
     */
    @Override
    @Transactional(readOnly = true)
    public ExamSession findById(Integer id) {
        return examSessionRepository.findOne(id);
    }
    
    /**
     * Find an individual ExamSession by date and session
     * 
     * @param date
     * @param session
     * @return
     */
    @PreAuthorize("@securityChecker.checkReader(authentication)")
    @Transactional(readOnly = true)
    public findExamSessionDateAndSession(Date date, String session) {
        return examSessionRepository.findByDateAndSession(date, session);
    }
    
    /**
     * Find all ExamSession objects
     *         
     * @return a SearchResult set with the list of ExamSessions
     */
    @Override
    @Transactional(readOnly = true)
    public List<ExamSession> findAll() {
        return examSessionRepository.findAll();
    }
    
    /**
     * Find a single page of ExamSessions objects
     *
     * @param page - the page number to retrieve
     * @param size - the number of records on each page
     * @return a SearchResult set with the list of ExamSessions
     */
    @PreAuthorize("@securityChecker.checkReader(authentication)")
    @Transactional
    public SearchResult<ExamSession> findExamSessions(int page, int size) {
        PageRequest pageRequest = new PageRequest(page, size);
        Page<ExamSession> examSessions = examSessionRepository.findAll(pageRequest);
        PageInfo pageInfo = new PageInfo(page, size, '', (Long) examSessions.total);
        return new SearchResult<>(pageInfo, examSessions.toList());
    }
    
    /**
     * @param date
     * @param session
     * @return
     */
    @Transactional
    ExamSession saveExamSession(Date date, String session) {
        ValidationUtils.assertNotNull(date, "date cannot be blank");
        ValidationUtils.assertNotBlank(session, "session cannot be blank");
        return save(new ExamSession(date, session));
    }
    
    /**
     * Save an ExamSession object to the database
     *     
     * @param examSession - the ExamSession object to save
     * @return the saved ExamSession object
     */
    @Override
    @Transactional
    public ExamSession save(ExamSession examSession) {
        return examSessionRepository.save(examSession);
    }
    
    
    /**
     * @param examSession
     * @return
     */
    @Transactional
    public ExamSession updateExamSession(ExamSession examSession) {
        ExamSession examSessionToSave = findById(examSession.id)
        examSessionToSave.date = examSession.date != null ? examSession.date : examSessionToSave.date
        examSessionToSave.session = examSession.session != null ? examSession.session : examSessionToSave.session
        return save(examSessionToSave);
    }
    
    /**
     * Delete an ExamSession object from the database
     * 
     * @param examSessionId - the ID of the ExamSession object to delete    
     */
    @Transactional
    public deleteById(Integer id) {
        ExamSession examSession = findById(id)
        delete(examSession)
    }
    
    /**
     * @param ExamSession
     */
    @Override
    public void delete(ExamSession ExamSession) {
        examSessionRepository.delete(ExamSession)
    }
}
