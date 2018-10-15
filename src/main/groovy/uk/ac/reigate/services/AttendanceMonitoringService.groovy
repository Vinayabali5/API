package uk.ac.reigate.services

//import static org.springframework.util.Assert

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import org.springframework.data.domain.Sort;
import org.springframework.security.access.prepost.PreAuthorize
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

import uk.ac.reigate.domain.lookup.AttendanceMonitoring
import uk.ac.reigate.exceptions.InvalidOperationException
import uk.ac.reigate.model.PageInfo
import uk.ac.reigate.model.SearchResult
import uk.ac.reigate.repositories.lookup.AttendanceMonitoringRepository
import uk.ac.reigate.utils.ValidationUtils;

@Service
class AttendanceMonitoringService implements ICoreDataService<AttendanceMonitoring, Integer>{
    
    @Autowired
    AttendanceMonitoringRepository attendanceMonitoringRepository
    
    /**
     * Default NoArgs constructor
     */
    AttendanceMonitoringService() {}
    
    /**
     * Autowired Constructor
     *
     * @param attendanceMonitoringRepository
     */
    AttendanceMonitoringService(AttendanceMonitoringRepository attendanceMonitoringRepository) {
        this.attendanceMonitoringRepository = attendanceMonitoringRepository;
    }
    
    /**
     * Find an individual attendanceMonitoring using the attendanceMonitorings ID fields
     *
     * @param id the ID fields to search for
     * @return the AttendanceMonitoring object that matches the ID supplied, or null if not found
     */
    @Override
    @Transactional(readOnly = true)
    AttendanceMonitoring findById(Integer id) {
        return attendanceMonitoringRepository.findOne(id);
    }
    
    /**
     * Find all attendanceMonitorings
     *
     * @return a List set with the list of AttendanceMonitorings
     */
    @Override
    @Transactional(readOnly = true)
    List<AttendanceMonitoring> findAll() {
        return attendanceMonitoringRepository.findAll();
    }
    
    /** This method saves to either existing object or creates a new object
     * @param id 
     * @param code
     * @param description
     * @param warningColour
     * @return
     */
    @Transactional
    public AttendanceMonitoring saveAttendanceMonitoring(Integer id, String code, String description, String warningColour) {
        ValidationUtils.assertNotBlank(code, "code cannot be blank");
        ValidationUtils.assertNotNull(description, "description is mandatory");
        AttendanceMonitoring attendanceMonitoring = null;
        if (id != null) {
            attendanceMonitoring = findById(id);
            attendanceMonitoring.setCode(code);
            attendanceMonitoring.setDescription(description);
            attendanceMonitoring.setWarningColour(warningColour);
            save(attendanceMonitoring);
        } else {
            attendanceMonitoring = save(new AttendanceMonitoring(code, description, warningColour));
        }
        return attendanceMonitoring;
    }
    
    /**
     * This service method is used to save a complete AttendanceMonitoring object in the database
     *
     * @param attendanceMonitoring the new AttendanceMonitoring object to be saved
     * @return the saved version of the AttendanceMonitoring object
     */
    @Override
    @Transactional
    public AttendanceMonitoring save(AttendanceMonitoring attendanceMonitoring) {
        return attendanceMonitoringRepository.save(attendanceMonitoring)
    }
    
    /**
     * This service method is used to update an AttendanceMonitoring object in the database from a partial or complete AttendanceMonitoring object.
     *
     * @param attendanceMonitoring the partial or complete AttendanceMonitoring object to be saved
     * @return the saved version of the AttendanceMonitoring object
     */
    
    @Transactional
    public AttendanceMonitoring updateAttendanceMonitoring(AttendanceMonitoring attendanceMonitoring) {
        AttendanceMonitoring attendanceMonitoringToSave = findById(attendanceMonitoring.id);
        attendanceMonitoringToSave.code = attendanceMonitoring.code != null ? attendanceMonitoring.code : attendanceMonitoringToSave.code
        attendanceMonitoringToSave.description = attendanceMonitoring.description != null ? attendanceMonitoring.description : attendanceMonitoringToSave.description
        attendanceMonitoringToSave.warningColour = attendanceMonitoring.warningColour != null ? attendanceMonitoring.warningColour : attendanceMonitoringToSave.warningColour
        return save(attendanceMonitoringToSave)
    }
    
    /**
     * Saves a list of AttendanceMonitoring objects to the database
     *
     * @param attendanceMonitorings a list of AttendanceMonitorings to be saved to the database
     * @return the list of save AttendanceMonitoring objects
     */
    
    @Transactional
    public List<AttendanceMonitoring> saveAttendanceMonitorings(List<AttendanceMonitoring> attendanceMonitorings) {
        return attendanceMonitorings.collect { attendanceMonitoring -> save(attendanceMonitoring) };
    }
    
    /**
     * This methods throws an InvalidOperationException when called. AttendanceMonitoring should not be deleted.
     */
    @Override
    public void delete(AttendanceMonitoring obj) {
        throw new InvalidOperationException("AttendanceMonitoring should not be deleted.")
        
    }
    
    
    
}
