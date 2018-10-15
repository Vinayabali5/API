package uk.ac.reigate.services

//import static org.springframework.util.Assert

import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import org.springframework.security.access.prepost.PreAuthorize
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

import uk.ac.reigate.domain.academic.AcademicYear
import uk.ac.reigate.domain.academic.Holiday
import uk.ac.reigate.exceptions.InvalidOperationException
import uk.ac.reigate.model.PageInfo
import uk.ac.reigate.model.SearchResult
import uk.ac.reigate.repositories.academic.HolidayRepository
import uk.ac.reigate.utils.ValidationUtils;

@Service
class HolidayService implements ICoreDataService<Holiday, Integer>{
    
    @Autowired
    HolidayRepository holidayRepository
    
    /**
     * Default NoArgs constructor
     */
    HolidayService() {}
    
    /**
     * Autowired Constructor
     *
     * @param holidayRepository
     */
    HolidayService(HolidayRepository holidayRepository) {
        this.holidayRepository = holidayRepository;
    }
    
    /**
     * Find an individual holiday using the holidays ID fields
     *
     * @param id the ID fields to search for
     * @return the Holiday object that matches the ID supplied, or null if not found
     */
    @Override
    @Transactional(readOnly = true)
    Holiday findById(Integer id) {
        return holidayRepository.findOne(id);
    }
    
    /**
     * Find all holidays
     *
     * @return a List of Holidays
     */
    @Override
    @Transactional(readOnly = true)
    List<Holiday> findAll() {
        return holidayRepository.findAll();
    }
    
    /**
     * @param id
     * @param year
     * @param description
     * @param startDate
     * @param endDate
     * @param halfTerm
     * @return
     */
    @Transactional
    public Holiday saveHoliday(Integer id, AcademicYear year, String description, Date startDate, Date endDate, Boolean halfTerm) {
        ValidationUtils.assertNotBlank(year, "year cannot be blank");
        ValidationUtils.assertNotNull(description, "description is mandatory");
        ValidationUtils.assertNotNull(startDate, "startDate is mandatory");
        ValidationUtils.assertNotNull(endDate, "endDate is mandatory");
        
        Holiday holiday = null;
        if (id != null) {
            holiday = findById(id);
            holiday.setYear(year);
            holiday.setDescription(description);
            holiday.setStartDate(startDate);
            holiday.setEndDate(endDate);
            holiday.setHalfTerm(halfTerm);
            
            save(holiday);
        } else {
            holiday = save(new Holiday(year, description, startDate, endDate, halfTerm));
        }
        
        return holiday;
    }
    
    /**
     * This service method is used to save a complete Holiday object in the database
     *
     * @param holiday the new Holiday object to be saved
     * @return the saved version of the Holiday object
     */
    @Override
    @Transactional
    public Holiday save(Holiday holiday) {
        return holidayRepository.save(holiday)
    }
    
    /**
     * Saves a list of Holiday objects to the database
     *
     * @param holidays a list of Holidays to be saved to the database
     * @return the list of save Holiday objects
     */
    @Transactional
    public List<Holiday> saveHolidays(List<Holiday> holidays) {
        return holidays.collect { holiday -> save(holiday) };
    }
    
    /**
     * This methods throws an InvalidOperationException when called. Holiday should not be deleted.
     */
    @Override
    public void delete(Holiday obj) {
        throw new InvalidOperationException("Holiday should not be deleted")
    }
    
}
