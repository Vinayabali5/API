package uk.ac.reigate.services;

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.security.access.prepost.PreAuthorize
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

import uk.ac.reigate.domain.register.AttendanceCode
import uk.ac.reigate.exceptions.InvalidOperationException
import uk.ac.reigate.model.SearchResult
import uk.ac.reigate.repositories.register.AttendanceCodeRepository
import uk.ac.reigate.utils.ValidationUtils

@Service
class AttendanceCodeService implements ICoreDataService<AttendanceCode,Integer> {
    
    @Autowired
    AttendanceCodeRepository attendanceCodeRepository
    
    /**
     * Default NoArgs constructor
     */
    AttendanceCodeService() {}
    
    /**
     * Autowired Constructor
     *
     * @param attendanceCodeRepository
     */
    AttendanceCodeService(AttendanceCodeRepository attendanceCodeRepository) {
        this.attendanceCodeRepository = attendanceCodeRepository
    }
    
    /**
     * Find an individual attendanceCode using the attendanceCodes ID fields
     *
     * @param id the ID fields to search for
     * @return the AttendanceCode object that matches the ID supplied, or null if not found
     */
    @Override
    @Transactional(readOnly = true)
    AttendanceCode findById(Integer id) {
        return attendanceCodeRepository.findOne(id);
    }
    
    /**
     * Find all attendanceCodes
     *
     * @return a SearchResult set with the list of AttendanceCodes
     */
    @Override
    @Transactional(readOnly = true)
    List<AttendanceCode> findAll() {
        return attendanceCodeRepository.findAll();
    }
    
    /** This method saves to either existing object or creates a new object
     * @param id
     * @param code
     * @param description
     * @param registerMark
     * @param absence
     * @param authorisedAbsence
     * @param late
     * @param authorisedLate
     * @param included
     * @param lastDateOfAttendance
     * @param htmlColour
     * @param accessColour
     * @return
     */
    @Transactional
    public AttendanceCode saveAttendanceCode(Integer id, String code, String description, String registerMark, Boolean absence, Boolean authorisedAbsence, Boolean late, Boolean authorisedLate, Boolean included, Boolean lastDateOfAttendance, String htmlColour, String accessColour) {
        ValidationUtils.assertNotBlank(code, "code cannot be blank");
        ValidationUtils.assertNotNull(description, "description is mandatory");
        AttendanceCode attendanceCode = null;
        if (id != null) {
            attendanceCode = findById(id);
            attendanceCode.setCode(code);
            attendanceCode.setDescription(description);
            attendanceCode.setRegisterMark(registerMark);
            attendanceCode.setAbsence(absence);
            attendanceCode.setAuthorisedAbsence(authorisedAbsence);
            attendanceCode.setLate(late);
            attendanceCode.setAuthorisedLate(authorisedLate);
            attendanceCode.setIncluded(included);
            attendanceCode.setLastDateOfAttendance(lastDateOfAttendance);
            attendanceCode.setHtmlColour(htmlColour);
            attendanceCode.setAccessColour(accessColour);
            save(attendanceCode);
        } else {
            attendanceCode = save(new AttendanceCode(code, description, registerMark, absence, authorisedAbsence, late, authorisedLate, included, lastDateOfAttendance, htmlColour, accessColour));
        }
        return attendanceCode;
    }
    
    /**
     * This service method is used to save a complete AttendanceCode object in the database
     *
     * @param attendanceCode the new AttendanceCode object to be saved
     * @return the saved version of the AttendanceCode object
     */
    @Override
    @Transactional
    public AttendanceCode save(AttendanceCode attendanceCode) {
        return attendanceCodeRepository.save(attendanceCode)
    }
    
    /**
     * This service method is used to update an AttendanceCode object in the database from a partial or complete AttendanceCode object.
     *
     * @param attendanceCode the partial or complete AttendanceCode object to be saved
     * @return the saved version of the AttendanceCode object
     */
    
    @Transactional
    public AttendanceCode updateAttendanceCode(AttendanceCode attendanceCode) {
        AttendanceCode attendanceCodeToSave = findById(attendanceCode.id)
        attendanceCodeToSave.code = attendanceCode.code != null ? attendanceCode.code : attendanceCodeToSave.code
        attendanceCodeToSave.description = attendanceCode.description != null ? attendanceCode.description : attendanceCodeToSave.description
        attendanceCodeToSave.registerMark = attendanceCode.registerMark != null ? attendanceCode.registerMark : attendanceCodeToSave.registerMark
        attendanceCodeToSave.absence = attendanceCode.absence != null ? attendanceCode.absence : attendanceCodeToSave.absence
        attendanceCodeToSave.authorisedAbsence = attendanceCode.authorisedAbsence != null ? attendanceCode.authorisedAbsence : attendanceCodeToSave.authorisedAbsence
        attendanceCodeToSave.late = attendanceCode.late != null ? attendanceCode.late : attendanceCodeToSave.late
        attendanceCodeToSave.authorisedLate = attendanceCode.authorisedLate != null ? attendanceCode.authorisedLate : attendanceCodeToSave.authorisedLate
        attendanceCodeToSave.included = attendanceCode.included != null ? attendanceCode.included : attendanceCodeToSave.included
        attendanceCodeToSave.lastDateOfAttendance = attendanceCode.lastDateOfAttendance != null ? attendanceCode.lastDateOfAttendance : attendanceCodeToSave.lastDateOfAttendance
        attendanceCodeToSave.htmlColour = attendanceCode.htmlColour != null ? attendanceCode.htmlColour : attendanceCodeToSave.htmlColour
        attendanceCodeToSave.accessColour = attendanceCode.accessColour != null ? attendanceCode.accessColour : attendanceCodeToSave.accessColour
        return save(attendanceCodeToSave)
    }
    
    /**
     * Saves a list of AttendanceCode objects to the database
     *
     * @param attendanceCodes a list of AttendanceCodes to be saved to the database
     * @return the list of save AttendanceCode objects
     */
    
    @Transactional
    public List<AttendanceCode> saveAttendanceCodes(List<AttendanceCode> attendanceCodes) {
        return attendanceCodes.collect { attendanceCode -> save(attendanceCode) };
    }
    
    /**
     * This methods throws an InvalidOperationException when called. AttendanceCode should not be deleted.
     */
    @Override
    public void delete(AttendanceCode obj) {
        throw new InvalidOperationException("AttendanceCode should not be deleted.")
    }
}
