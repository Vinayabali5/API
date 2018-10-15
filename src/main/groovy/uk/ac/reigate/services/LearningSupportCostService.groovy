package uk.ac.reigate.services

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

import uk.ac.reigate.domain.Staff
import uk.ac.reigate.domain.academic.Student
import uk.ac.reigate.domain.learning_support.LearningSupportCost
import uk.ac.reigate.domain.learning_support.LearningSupportCostCategory
import uk.ac.reigate.exceptions.InvalidOperationException
import uk.ac.reigate.repositories.learning_support.LearningSupportCostRepository
import uk.ac.reigate.utils.ValidationUtils

@Service
class LearningSupportCostService implements ICoreDataService<LearningSupportCost, Integer>{
    
    @Autowired
    LearningSupportCostRepository learningSupportCostRepository
    
    /**
     * Default NoArgs constructor
     */
    LearningSupportCostService() {}
    
    /**
     * Autowired Constructor
     *
     * @param learningSupportCostRepository
     */
    LearningSupportCostService(LearningSupportCostRepository learningSupportCostRepository) {
        this.learningSupportCostRepository = learningSupportCostRepository
    }
    
    /**
     * Find an individual learningSupportCost using the learningSupportCosts ID fields
     *
     * @param id the ID fields to search for
     * @return the LearningSupportCost object that matches the ID supplied, or null if not found
     */
    @Override
    @Transactional(readOnly = true)
    LearningSupportCost findById(Integer id) {
        return learningSupportCostRepository.findOne(id);
    }
    
    @Transactional(readOnly = true)
    List<LearningSupportCost>  findByStudentId(Integer studentId) {
        return learningSupportCostRepository.findByStudent_Id(studentId);
    }
    
    /**
     * Find a single page of LearningSupportCost objects
     * @return a SearchResult set with the list of LearningSupportCosts
     */
    @Override
    @Transactional(readOnly = true)
    List<LearningSupportCost> findAll() {
        return learningSupportCostRepository.findAll();
    }
    
    /**
     *
     * @param id
     * @param code
     * @param description
     * @return
     */
    @Transactional
    public LearningSupportCost saveLearningSupportCost(Integer id, Student student, Date startDate, Date endDate, Double hoursPerWeek, Double weeks, Double rate, LearningSupportCostCategory costCategory, String description, String periodDescription, Staff staff) {
        ValidationUtils.assertNotBlank(student, "student cannot be blank");
        
        LearningSupportCost learningSupportCost = null;
        
        if (id != null) {
            learningSupportCost = findById(id);
            
            learningSupportCost.setStudent(student);
            
            learningSupportCost.setDescription(description);
            learningSupportCost.setStartDate(startDate);
            learningSupportCost.setEndDate(endDate);
            learningSupportCost.setHoursPerWeek(hoursPerWeek);
            learningSupportCost.setWeeks(weeks);
            learningSupportCost.setRate(rate);
            learningSupportCost.setCostCategory(costCategory);
            learningSupportCost.setDescription(description);
            learningSupportCost.setPeriodDescription(periodDescription);
            learningSupportCost.setStaff(staff);
            save(learningSupportCost);
        } else {
            learningSupportCost = save(new LearningSupportCost(student, startDate, endDate, hoursPerWeek, weeks, rate, costCategory, description, periodDescription, staff));
        }
        
        return learningSupportCost;
    }
    
    /**
     * This service method is used to save a complete LearningSupportCost object in the database
     *
     * @param learningSupportCost the new LearningSupportCost object to be saved
     * @return the saved version of the LearningSupportCost object
     */
    @Override
    @Transactional
    public LearningSupportCost save(LearningSupportCost learningSupportCost) {
        return learningSupportCostRepository.save(learningSupportCost)
    }
    
    
    /**
     * Saves a list of LearningSupportCost objects to the database
     *
     * @param learningSupportCosts a list of LearningSupportCosts to be saved to the database
     * @return the list of save LearningSupportCost objects
     */
    @Transactional
    public List<LearningSupportCost> saveLearningSupportCosts(List<LearningSupportCost> learningSupportCosts) {
        return learningSupportCosts.collect { learningSupportCost -> save(learningSupportCost) };
    }
    /**
     * This methods throws an InvalidOperationException when called. LearningSupportCost should not be deleted.
     */
    @Override
    public void delete(LearningSupportCost obj) {
        throw new InvalidOperationException("LearningSupportCost should not be deleted")
    }
}
