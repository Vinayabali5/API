package uk.ac.reigate.services

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

import uk.ac.reigate.domain.academic.Student
import uk.ac.reigate.domain.learning_support.LearningSupportCost
import uk.ac.reigate.domain.learning_support.LearningSupportVisit
import uk.ac.reigate.exceptions.InvalidOperationException
import uk.ac.reigate.repositories.learning_support.LearningSupportVisitRepository
import uk.ac.reigate.utils.ValidationUtils

@Service
class LearningSupportVisitService implements ICoreDataService<LearningSupportVisit, Integer>{
    
    @Autowired
    LearningSupportVisitRepository learningSupportVisitRepository
    
    /**
     * Default NoArgs constructor
     */
    LearningSupportVisitService() {}
    
    /**
     * Autowired Constructor
     *
     * @param learningSupportVisitRepository
     */
    LearningSupportVisitService(LearningSupportVisitRepository learningSupportVisitRepository) {
        this.learningSupportVisitRepository = learningSupportVisitRepository
    }
    
    /**
     * Find an individual learningSupportVisit using the learningSupportVisits ID fields
     *
     * @param id the ID fields to search for
     * @return the LearningSupportVisit object that matches the ID supplied, or null if not found
     */
    @Override
    @Transactional(readOnly = true)
    LearningSupportVisit findById(Integer id) {
        return learningSupportVisitRepository.findOne(id);
    }
    
    @Transactional(readOnly = true)
    List<LearningSupportVisit> findByStudentId(Integer studentId) {
        return learningSupportVisitRepository.findByStudent_Id(studentId);
    }
    
    /**
     * Find a single page of LearningSupportVisit objects
     * @return a SearchResult set with the list of LearningSupportVisits
     */
    @Override
    @Transactional(readOnly = true)
    List<LearningSupportVisit> findAll() {
        return learningSupportVisitRepository.findAll();
    }
    
    /**
     *
     * @param id
     * @param code
     * @param description
     * @return
     */
    @Transactional
    public LearningSupportVisit saveLearningSupportVisit(Integer id, Student student, Date date, Integer duration, String details, Date time) {
        ValidationUtils.assertNotBlank(student, "student cannot be blank");
        LearningSupportVisit learningSupportVisit = null;
        if (id != null) {
            learningSupportVisit = findById(id);
            learningSupportVisit.setStudent(student);
            learningSupportVisit.setDate(date);
            learningSupportVisit.setDuration(duration);
            learningSupportVisit.setDetails(details);
            learningSupportVisit.setTime(time);
            save(learningSupportVisit);
        } else {
            learningSupportVisit = save(new LearningSupportVisit(student, date, duration, details, time));
        }
        return learningSupportVisit;
    }
    
    /**
     * This service method is used to save a complete LearningSupportVisit object in the database
     *
     * @param learningSupportVisit the new LearningSupportVisit object to be saved
     * @return the saved version of the LearningSupportVisit object
     */
    @Override
    @Transactional
    public LearningSupportVisit save(LearningSupportVisit learningSupportVisit) {
        return learningSupportVisitRepository.save(learningSupportVisit)
    }
    
    
    /**
     * Saves a list of LearningSupportVisit objects to the database
     *
     * @param learningSupportVisits a list of LearningSupportVisits to be saved to the database
     * @return the list of save LearningSupportVisit objects
     */
    @Transactional
    public List<LearningSupportVisit> saveLearningSupportVisits(List<LearningSupportVisit> learningSupportVisits) {
        return learningSupportVisits.collect { learningSupportVisit -> save(learningSupportVisit) };
    }
    
    /**
     * This methods throws an InvalidOperationException when called. LearningSupportVisit should not be deleted.
     */
    @Override
    public void delete(LearningSupportVisit obj) {
        throw new InvalidOperationException("LearningSupportVisit should not be deleted")
    }
}
