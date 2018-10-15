package uk.ac.reigate.services

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

import uk.ac.reigate.domain.Room
import uk.ac.reigate.domain.Staff
import uk.ac.reigate.domain.academic.AcademicYear
import uk.ac.reigate.domain.academic.CourseGroup
import uk.ac.reigate.domain.academic.Period
import uk.ac.reigate.domain.academic.Timetable
import uk.ac.reigate.exceptions.InvalidOperationException
import uk.ac.reigate.repositories.academic.TimetableRepository
import uk.ac.reigate.utils.ValidationUtils

@Service
class TimetableService implements ICoreDataService<Timetable, Integer>{
    
    @Autowired
    TimetableRepository timetableRepository
    
    /**
     * Default NoArgs constructor
     */
    TimetableService() {}
    
    /**
     * Autowired Constructor
     *
     * @param timetableRepository
     */
    TimetableService(TimetableRepository timetableRepository) {
        this.timetableRepository = timetableRepository;
    }
    
    /**
     * Find an individual timetable using the timetables ID fields
     *
     * @param id the ID fields to search for
     * @return the Timetable object that matches the ID supplied, or null if not found
     */
    @Override
    @Transactional(readOnly = true)
    Timetable findById(Integer id) {
        return timetableRepository.findOne(id);
    }
    
    /**
     * Find all timetables
     *
     * @return a List of Timetables
     */
    @Override
    @Transactional(readOnly = true)
    List<Timetable> findAll() {
        List<Timetable> timetables = timetableRepository.findAll();
        return timetables
    }
    
    
    /**
     * @param id
     * @param courseGroup
     * @param period
     * @param room
     * @param teacher
     * @param validFrom
     * @param validTo
     * @return
     */
    @Transactional
    public Timetable saveTimetable(Integer id, CourseGroup courseGroup, Period period, Room room, Staff teacher, Date validFrom, Date validTo) {
        ValidationUtils.assertNotBlank(courseGroup, "courseGroup cannot be blank");
        Timetable timetable = null;
        if (id != null) {
            timetable = findById(id);
            timetable.setCourseGroup(courseGroup)
            timetable.setPeriod(period)
            timetable.setRoom(room)
            timetable.setTeacher(teacher)
            timetable.setValidFrom(validFrom)
            timetable.setValidTo(validTo)
            save(timetable);
        } else {
            timetable = save(new Timetable(timetable));
        }
        
        return timetable;
    }
    
    /**
     * This service method is used to save a complete Timetable object in the database
     *
     * @param timetable the new Timetable object to be saved
     * @return the saved version of the Timetable object
     */
    @Override
    @Transactional
    public Timetable save(Timetable timetable) {
        return timetableRepository.save(timetable)
    }
    
    /**
     * Saves a list of Timetable objects to the database
     *
     * @param timetables a list of Timetables to be saved to the database
     * @return the list of save Timetable objects
     */
    @Transactional
    public List<Timetable> saveTimetables(List<Timetable> timetables) {
        return timetables.collect { timetable ->
            saveTimetable(timetable.id, timetable.courseGroup, timetable.period, timetable.room, timetable.teacher, timetable.validFrom, timetable.validTo)
        };
    }
    
    /**
     * This methods throws an InvalidOperationException when called. Timetable should not be deleted.
     */
    @Override
    public void delete(Timetable obj) {
        throw new InvalidOperationException("Timetable should not be deleted")
    }
    
    public List<Timetable> getStaffTimetable(AcademicYear year, Staff staff) {
        return timetableRepository.findByCourseGroup_YearAndTeacher(year, staff)
    }
    
    public List<Timetable> getStaffTimetableCurrent(AcademicYear year, Staff staff) {
        return timetableRepository.findByYearAndTeacherCurrent(year, staff)
    }
    
    public List<Timetable> getStaffTimetableOnDate(AcademicYear year, Staff staff, Date date) {
        return timetableRepository.findByYearAndTeacherOnDate(year, staff, date)
    }
    
    public List<Timetable> getCourseGroupsByYearId(Integer yearId, Integer courseGroupId) {
        return timetableRepository.findByCourseGroup_Year_IdAndCourseGroup_Id(yearId, courseGroupId)
    }
}
