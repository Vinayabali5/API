package uk.ac.reigate.services

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

import uk.ac.reigate.domain.academic.EntryQualification
import uk.ac.reigate.domain.academic.EntryQualificationType
import uk.ac.reigate.exceptions.InvalidOperationException
import uk.ac.reigate.repositories.academic.EntryQualificationRepository
import uk.ac.reigate.utils.ValidationUtils

@Service
class EntryQualificationService implements ICoreDataService<EntryQualification, Integer>{
    
    @Autowired
    EntryQualificationRepository entryQualificationRepository
    
    /**
     * Default NoArgs constructor
     */
    EntryQualificationService() {}
    
    /**
     * Autowired Constructor
     *
     * @param entryQualificationRepository
     */
    EntryQualificationService(EntryQualificationRepository entryQualificationRepository) {
        this.entryQualificationRepository = entryQualificationRepository;
    }
    
    /**
     * Find an individual entryQualification using the entryQualifications ID fields
     *
     * @param id the ID fields to search for
     * @return the EntryQualification object that matches the ID supplied, or null if not found
     */
    @Override
    @Transactional(readOnly = true)
    EntryQualification findById(Integer id) {
        return entryQualificationRepository.findOne(id);
    }
    
    /**
     * Find all entryQualifications
     *
     * @return a List of EntryQualifications
     */
    @Override
    @Transactional(readOnly = true)
    List<EntryQualification> findAll() {
        return entryQualificationRepository.findAll();
    }
    
    
    /**
     * @param id
     * @param title
     * @param entryQualificationType
     * @param basicList
     * @param shortCourse
     * @param dataMatchCode
     * @param webLinkCode
     * @return
     */
    @Transactional
    public EntryQualification saveEntryQualification(Integer id, String title, EntryQualificationType entryQualificationType, boolean basicList, boolean shortCourse, String dataMatchCode, Integer webLinkCode) {
        ValidationUtils.assertNotBlank(title, "title cannot be blank");
        EntryQualification entryQualification = null;
        
        if (id != null) {
            entryQualification = findById(id);
            entryQualification.setTitle(title);
            entryQualification.setEntryQualificationType(entryQualificationType);
            entryQualification.setBasicList(basicList);
            entryQualification.setShortCourse(shortCourse);
            entryQualification.setDataMatchCode(dataMatchCode);
            entryQualification.setWebLinkCode(webLinkCode);
            save(entryQualification);
        } else {
            entryQualification = save(new EntryQualification(title, entryQualificationType, basicList, shortCourse, dataMatchCode, webLinkCode));
        }
        
        return entryQualification;
    }
    
    /**
     * This service method is used to save a complete EntryQualification object in the database
     *
     * @param entryQualification the new EntryQualification object to be saved
     * @return the saved version of the EntryQualification object
     */
    @Override
    @Transactional
    public EntryQualification save(EntryQualification entryQualification) {
        return entryQualificationRepository.save(entryQualification)
    }
    
    /**
     * Saves a list of EntryQualification objects to the database
     *
     * @param entryQualifications a list of EntryQualifications to be saved to the database
     * @return the list of save EntryQualification objects
     */
    @Transactional
    public List<EntryQualification> saveEntryQualifications(List<EntryQualification> entryQualifications) {
        return entryQualifications.collect { entryQualification -> save(entryQualification) };
    }
    
    /**
     * This methods throws an InvalidOperationException when called. EntryQualification should not be deleted.
     */
    @Override
    public void delete(EntryQualification obj) {
        throw new InvalidOperationException("EntryQualification should not be deleted")
    }
}
