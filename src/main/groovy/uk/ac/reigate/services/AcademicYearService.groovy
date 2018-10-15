package uk.ac.reigate.services

import org.apache.log4j.Logger

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.security.access.prepost.PreAuthorize
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

import uk.ac.reigate.domain.academic.AcademicYear
import uk.ac.reigate.exceptions.InvalidOperationException
import uk.ac.reigate.repositories.academic.AcademicYearRepository

@Service
class AcademicYearService implements IAnonymousReadDataService<AcademicYear, Integer> {
    
    private static final Logger LOGGER = Logger.getLogger(AcademicYearService.class);
    
    @Autowired
    AcademicYearRepository academicYearRepository
    
    /**
     * Default NoArgs constructor
     */
    AcademicYearService() {
    }
    
    /**
     * Autowired Constructor
     * 
     * @param academicYearRepository 
     */
    AcademicYearService(AcademicYearRepository academicYearRepository) {
        this.academicYearRepository = academicYearRepository
    }
    
    /**
     * This service method is used to retrieve an individual AcademicYear object from the database.
     * 
     * @param id the id of the AcademicYear object to retrieve
     * @return the AcademicYear object identified by the id
     */
    @Override
    @Transactional(readOnly = true)
    AcademicYear findById(Integer id) {
        return academicYearRepository.findOne(id);
    }
    
    /**
     * This service method is used to retrieve all instances of the AcademicYear object from the database.
     * 
     * @return A List of AcademicYear objects
     */
    @Override
    @PreAuthorize("isFullyAuthenticated()")
    @Transactional(readOnly = true)
    List<AcademicYear> findAll() {
        List<AcademicYear> academicYears = academicYearRepository.findAll();
        return academicYears
    }
    
    /**
     * This service method is used to save a complete AcademicYear object in the database
     *  
     * @param academicYear the new AcademicYear object to be saved
     * @return the saved version of the AcademicYear object
     */
    @Override
    @Transactional
    public AcademicYear save(AcademicYear academicYear) {
        return academicYearRepository.save(academicYear)
    }
    
    /**
     * This methods throws an InvalidOperationException when called. AcademicYears should not be deleted.
     */
    @Override
    @Transactional
    public void delete(AcademicYear obj) {
        throw new InvalidOperationException("AcademicYears should not be deleted.")
    }
    
    
    /**
     * This service method is used to update an AcademicYear object in the database from a partial or complete AcademicYear object.
     * 
     * @param academicYear the partial or complete AcademicYear object to be saved
     * @return the saved version of the AcademicYear object
     */
    @Transactional
    public AcademicYear updateAcademicYear(AcademicYear academicYear) {
        AcademicYear academicYearToSave = academicYearRepository.findOne(academicYear.id)
        academicYearToSave.code = academicYear.code != null ? academicYear.code : academicYearToSave.code
        academicYearToSave.description = academicYear.description != null ? academicYear.description : academicYearToSave.description
        academicYearToSave.startDate = academicYear.startDate != null ? academicYear.startDate : academicYearToSave.startDate
        academicYearToSave.endDate = academicYear.endDate != null ? academicYear.endDate : academicYearToSave.endDate
        return save(academicYearToSave)
    }
    
    
    /**
     * This service method is used to save a list of complete AcademicYear objects to the database. 
     * 
     * @param academicYears a list of AcademicYear objects to persist to the database
     * @return the saved version of the list of AcademicYear objects
     */
    @Transactional
    public List<AcademicYear> saveAcademicYears(List<AcademicYear> academicYears) {
        return academicYears.collect { academicYear -> save(academicYear) };
    }
    
    /**
     * This service method is used to retrieve the AcademicYear as specified by the code  
     * 
     * @param code the code of the AcademicYear to retrieve
     * @return the AcademicYear with a matching code
     */
    public AcademicYear findByCode(String code) {
        return academicYearRepository.findByCode(code)
    }
    
    
    /**
     * This service method is used to retrieve the current AcademicYear
     * 
     * @return the current AcademicYear
     * @deprecated the getCurrentAcademicYear method should be used to retrieve the AcademicYear object
     *              from this you can then access the id field. 
     */
    @Deprecated
    public Integer getCurrentAcademicYearId() {
        AcademicYear acedemicYear = getCurrentAcademicYear()
        return acedemicYear.getId()
    }
    
    
    /**
     * This service method is used to retrieve the current AcademicYear
     *
     * @return the current AcademicYear
     */
    //@Secured(['IS_AUTHENTICATED_ANONYMOUSLY'])
    public AcademicYear getCurrentAcademicYear() {
        return academicYearRepository.findCurrent()
    }
    
    /**
     * This service method is used to retrieve the next AcademicYear
     * 
     * @return the next AcademicYear
     */
    public AcademicYear getNextAcademicYear() {
        return academicYearRepository.findNext()
    }
    
    /**
     * This service method is used to retrieve the previous AcademicYear
     *
     * @return the next AcademicYear
     */
    public AcademicYear getPreviousAcademicYear() {
        return academicYearRepository.findPrevious()
    }
}
