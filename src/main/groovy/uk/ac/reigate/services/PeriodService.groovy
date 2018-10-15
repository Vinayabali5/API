package uk.ac.reigate.services

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

import uk.ac.reigate.domain.academic.Block
import uk.ac.reigate.domain.academic.Period
import uk.ac.reigate.exceptions.InvalidOperationException
import uk.ac.reigate.repositories.academic.PeriodRepository
import uk.ac.reigate.utils.ValidationUtils

@Service
class PeriodService implements ICoreDataService<Period, Integer>{
    
    @Autowired
    PeriodRepository periodRepository
    
    /**
     * Default NoArgs constructor
     */
    PeriodService() {}
    
    /**
     * Autowired Constructor
     *
     * @param periodRepository
     */
    PeriodService(PeriodRepository periodRepository){
        this.periodRepository = periodRepository;
    }
    
    /**
     * Find an individual period using the periods ID fields
     *
     * @param id the ID fields to search for
     * @return the Period object that matches the ID supplied, or null if not found
     */
    @Override
    @Transactional(readOnly = true)
    Period findById(Integer id) {
        return periodRepository.findOne(id);
    }
    
    /**
     * Find all periods
     *
     * @return a SearchResult set with the list of Periods
     */
    @Override
    @Transactional(readOnly = true)
    List<Period> findAll() {
        return periodRepository.findAll();
    }
    
    
    /**
     * @param id
     * @param code
     * @param description
     * @param block
     * @param startTime
     * @param endTime
     * @param day
     * @param dayPeriod
     * @return
     */
    @Transactional
    public Period savePeriod(Integer id, String code, String description, Block block, Date startTime, Date endTime, Integer day, Integer dayPeriod, Integer boxNo, Integer cristalPeriod) {
        ValidationUtils.assertNotBlank(code, "code cannot be blank");
        ValidationUtils.assertNotNull(description, "description is mandatory");
        ValidationUtils.assertNotNull(block, "block is mandatory");
        ValidationUtils.assertNotNull(startTime, "startTime is mandatory");
        ValidationUtils.assertNotNull(endTime, "endTime is mandatory");
        ValidationUtils.assertNotNull(day, "day is mandatory");
        ValidationUtils.assertNotNull(dayPeriod, "dayPeriod is mandatory");
        Period period = null;
        
        if (id != null) {
            period = findById(id);
            
            period.setCode(code);
            period.setDescription(description);
            period.setBlock(block);
            period.setStartTime(startTime);
            period.setEndTime(endTime);
            period.setDay(day);
            period.setDayPeriod(dayPeriod);
            period.setBoxNo(boxNo);
            period.setCristalPeriod(cristalPeriod);
            
            save(period);
        } else {
            period = save(new Period(code, description, block, startTime, endTime, day, dayPeriod, boxNo, cristalPeriod));
        }
        
        return period;
    }
    
    /**
     * This service method is used to save a complete Period object in the database
     *
     * @param period the new Period object to be saved
     * @return the saved version of the Period object
     */
    @Override
    @Transactional
    public Period save(Period period) {
        return periodRepository.save(period)
    }
    
    /**
     * Saves a list of Period objects to the database
     *
     * @param periods a list of Periods to be saved to the database
     * @return the list of save Period objects
     */
    @Transactional
    public List<Period> savePeriods(List<Period> periods) {
        return periods.collect { period -> save(period) };
    }
    
    /**
     * This methods throws an InvalidOperationException when called. Period should not be deleted.
     */
    @Override
    public void delete(Period obj) {
        throw new InvalidOperationException("Period should not be deleted")
    }
}
