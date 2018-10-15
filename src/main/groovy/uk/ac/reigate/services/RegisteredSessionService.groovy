package uk.ac.reigate.services

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

import uk.ac.reigate.domain.academic.Period
import uk.ac.reigate.domain.register.RegisteredSession
import uk.ac.reigate.exceptions.InvalidOperationException
import uk.ac.reigate.repositories.academic.RegisteredSessionRepository
import uk.ac.reigate.utils.ValidationUtils

@Service
class RegisteredSessionService implements ICoreDataService<RegisteredSession, Integer>{
    
    @Autowired
    RegisteredSessionRepository registeredSessionRepository
    
    /**
     * Default NoArgs constructor
     */
    RegisteredSessionService() {}
    
    /**
     * Autowired Constructor
     *
     * @param sessionRepository
     */
    RegisteredSessionService(RegisteredSessionRepository registeredSessionRepository) {
        this.registeredSessionRepository = registeredSessionRepository
    }
    
    /**
     * Find an individual session using the sessions ID fields
     *
     * @param id the ID fields to search for
     * @return the Session object that matches the ID supplied, or null if not found
     */
    @Override
    @Transactional(readOnly = true)
    RegisteredSession findById(Integer id) {
        return registeredSessionRepository.findOne(id);
    }
    
    /**
     * Find all sessions
     *
     * @return a SearchResult set with the list of Sessions
     */
    @Override
    @Transactional(readOnly = true)
    List<RegisteredSession> findAll() {
        return registeredSessionRepository.findAll();
    }
    
    /**
     * @param id
     * @param date
     * @param period
     * @return
     */
    @Transactional
    public RegisteredSession saveSession(Integer id, Date date, Period period) {
        ValidationUtils.assertNotBlank(date, "date cannot be blank");
        ValidationUtils.assertNotNull(period, "period is mandatory");
        
        RegisteredSession session = null;
        
        if (id != null) {
            session = findById(id);
            
            session.setDate(date);
            session.setPeriod(period);
            
            save(session);
        } else {
            session = save(new RegisteredSession(date, period));
        }
        
        return session;
    }
    
    /**
     * This service method is used to save a complete Session object in the database
     *
     * @param session the new Session object to be saved
     * @return the saved version of the Session object
     */
    @Override
    @Transactional
    public RegisteredSession save(RegisteredSession session) {
        return registeredSessionRepository.save(session)
    }
    
    /**
     * Saves a list of Session objects to the database
     *
     * @param sessions a list of Sessions to be saved to the database
     * @return the list of save Session objects
     */
    @Transactional
    public List<RegisteredSession> saveSessions(List<RegisteredSession> sessions) {
        return sessions.collect { session -> save(session) };
    }
    
    /**
     * This methods throws an InvalidOperationException when called. RegisteredSession should not be deleted.
     */
    @Override
    public void delete(RegisteredSession obj) {
        throw new InvalidOperationException("RegisteredSession should not be deleted")
    }
}
