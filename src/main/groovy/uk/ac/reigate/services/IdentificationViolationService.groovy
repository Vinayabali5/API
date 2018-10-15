package uk.ac.reigate.services

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.security.access.prepost.PreAuthorize
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

import uk.ac.reigate.domain.academic.IdentificationViolation
import uk.ac.reigate.dto.IdentificationViolationDto
import uk.ac.reigate.repositories.academic.IdentificationViolationRepository

@Service
class IdentificationViolationService implements ICoreDataService<IdentificationViolation, Integer> {
    
    @Autowired
    IdentificationViolationRepository identificationViolationRepository
    
    @Autowired
    AcademicYearService academicYearService
    
    @Autowired
    StudentService studentService
    
    /**
     * Default NoArgs constructor
     */
    IdentificationViolationService() {}
    
    /**
     * Autowired Constructor
     *
     * @param departmentRepository
     */
    IdentificationViolationService(IdentificationViolationRepository identificationViolationRepository) {
        this.identificationViolationRepository = identificationViolationRepository;
    }
    
    /**
     * Find an individual department using the departments ID fields
     *
     * @param id the ID fields to search for
     * @return the IdentificationViolation object that matches the ID supplied, or null if not found
     */
    @Override
    @Transactional(readOnly = true)
    IdentificationViolation findById(Integer id) {
        return identificationViolationRepository.findOne(id);
    }
    
    /**
     * This method is used to find all id violations
     *
     * @return a SearchResult set with the list of IdentificationViolation
     */
    @Override
    @Transactional(readOnly = true)
    List<IdentificationViolation> findAll() {
        return identificationViolationRepository.findAll();
    }
    
    /**
     * This method is used to find all id violations by the student ID
     * 
     * @param studentId the ID of the student to search for
     * @return a List of IdentificationViolation objects for the given student
     */
    @Transactional(readOnly = true)
    List<IdentificationViolation> findByStudentId(Integer studentId) {
        return identificationViolationRepository.findByStudentId(studentId);
    }
    
    /**
     * This method is used to save a complete IdentificationViolation object in the database
     *
     * @param department the new IdentificationViolation object to be saved
     * @return the saved version of the IdentificationViolation object
     */
    @PreAuthorize("@securityChecker.checkWriter(authentication) or hasRole('ID Violation')")
    @Transactional
    public IdentificationViolation save(IdentificationViolation idViolation) {
        return identificationViolationRepository.save(idViolation)
    }
    
    /**
     * This method is used to save a list of IdentificationViolation objects to the database
     *
     * @param departments a list of IdentificationViolation to be saved to the database
     * @return the list of save IdentificationViolation objects
     */
    @Transactional
    public List<IdentificationViolation> saveIdentificationViolations(List<IdentificationViolation> idViolations) {
        return idViolations.collect { idViolation -> save(idViolation) };
    }
    
    
    /**
     * This method is used to convert a IdentificationViolationDto object into and IdentificationViolation object
     * @param dto the IdentificationViolationDto object to convert
     * @return a new IdentificationViolation 
     */
    public IdentificationViolation convert(IdentificationViolationDto dto) {
        IdentificationViolation out = new IdentificationViolation()
        out.id = dto.id
        out.year = dto.yearId != null ? academicYearService.findById(dto.yearId) : academicYearService.getCurrentAcademicYear()
        out.student = studentService.findById(dto.studentId)
        out.date = dto.date
        out.returned = dto.returned
        out.lost = dto.lost
        out.printed = dto.printed
        out.id_number = dto.id_number
        return out
    }
    
    /**
     * Delete a IdentificationViolation
     *
     * @param identificationViolation object to be delete
     */
    @Override
    @PreAuthorize("@securityChecker.checkDeleter(authentication) or hasRole('ID Violation')")
    public void delete(IdentificationViolation identificationViolation){
        identificationViolationRepository.delete(identificationViolation.id)
    }
}
