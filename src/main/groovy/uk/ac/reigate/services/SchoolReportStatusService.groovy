package uk.ac.reigate.services

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

import uk.ac.reigate.domain.lookup.SchoolReportStatus
import uk.ac.reigate.exceptions.InvalidOperationException
import uk.ac.reigate.repositories.lookup.SchoolReportStatusRepository
import uk.ac.reigate.utils.ValidationUtils

@Service
class SchoolReportStatusService implements ICoreDataService<SchoolReportStatus, Integer>{
    
    @Autowired
    SchoolReportStatusRepository schoolReportStatusRepository
    
    /**
     * Default NoArgs constructor
     */
    SchoolReportStatusService() {}
    
    /**
     * Autowired Constructor
     *
     * @param schoolReportStatusRepository
     */
    SchoolReportStatusService(SchoolReportStatusRepository schoolReportStatusRepository) {
        this.schoolReportStatusRepository = schoolReportStatusRepository;
    }
    
    /**
     * Find an individual schoolReportStatus using the schoolReportStatuss ID fields
     *
     * @param id the ID fields to search for
     * @return the SchoolReportStatus object that matches the ID supplied, or null if not found
     */
    @Override
    @Transactional(readOnly = true)
    SchoolReportStatus findById(Integer id) {
        return schoolReportStatusRepository.findOne(id);
    }
    
    /**
     * Find all schoolReportStatuss
     *
     * @return a SearchResult set with the list of SchoolReportStatuss
     */
    @Override
    @Transactional(readOnly = true)
    List<SchoolReportStatus> findAll() {
        return schoolReportStatusRepository.findAll();
    }
    
    /**
     * @param id
     * @param code
     * @param description
     * @return
     */
    @Transactional
    public SchoolReportStatus saveSchoolReportStatus(Integer id, String code, String description) {
        ValidationUtils.assertNotBlank(code, "code cannot be blank");
        ValidationUtils.assertNotNull(description, "description is mandatory");
        
        SchoolReportStatus schoolReportStatus = null;
        
        if (id != null) {
            schoolReportStatus = findById(id);
            
            schoolReportStatus.setCode(code);
            schoolReportStatus.setDescription(description);
            
            save(schoolReportStatus);
        } else {
            schoolReportStatus = save(new SchoolReportStatus(code, description));
        }
        
        return schoolReportStatus;
    }
    
    /**
     * This service method is used to save a complete SchoolReportStatus object in the database
     *
     * @param schoolReportStatus the new SchoolReportStatus object to be saved
     * @return the saved version of the SchoolReportStatus object
     */
    @Override
    @Transactional
    public SchoolReportStatus save(SchoolReportStatus schoolReportStatus) {
        return schoolReportStatusRepository.save(schoolReportStatus)
    }
    
    /**
     * This service method is used to update an SchoolReportStatus object in the database from a partial or complete SchoolReportStatus object.
     *
     * @param schoolReportStatus the partial or complete SchoolReportStatus object to be saved
     * @return the saved version of the SchoolReportStatus object
     */
    @Transactional
    public SchoolReportStatus updateSchoolReportStatus(SchoolReportStatus schoolReportStatus) {
        SchoolReportStatus schoolReportStatusToSave = findById(schoolReportStatus.id);
        schoolReportStatusToSave.code = schoolReportStatus.code != null ? schoolReportStatus.code : schoolReportStatusToSave.code
        schoolReportStatusToSave.description = schoolReportStatus.description != null ? schoolReportStatus.description : schoolReportStatusToSave.description
        return save(schoolReportStatusToSave)
    }
    
    /**
     * Saves a list of SchoolReportStatus objects to the database
     *
     * @param schoolReportStatuss a list of SchoolReportStatuss to be saved to the database
     * @return the list of save SchoolReportStatus objects
     */
    @Transactional
    public List<SchoolReportStatus> saveSchoolReportStatuss(List<SchoolReportStatus> schoolReportStatuss) {
        return schoolReportStatuss.collect { schoolReportStatus -> save(schoolReportStatus) };
    }
    
    /**
     * This methods throws an InvalidOperationException when called. SchoolReportStatus should not be deleted.
     */
    @Override
    public void delete(SchoolReportStatus obj) {
        throw new InvalidOperationException("SchoolReportStatus should not be deleted")
    }
    
    public SchoolReportStatus findByDesc(String description){
        return schoolReportStatusRepository.findByDescription(description)
    }
}
