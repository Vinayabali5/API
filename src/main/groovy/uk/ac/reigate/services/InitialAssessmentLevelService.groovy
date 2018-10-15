package uk.ac.reigate.services

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

import uk.ac.reigate.domain.learning_support.InitialAssessmentLevel
import uk.ac.reigate.exceptions.InvalidOperationException
import uk.ac.reigate.repositories.learning_support.InitialAssessmentLevelRepository
import uk.ac.reigate.utils.ValidationUtils

@Service
class InitialAssessmentLevelService implements ICoreDataService<InitialAssessmentLevel, Integer>{
    
    @Autowired
    InitialAssessmentLevelRepository initialAssessmentLevelRepository
    
    /**
     * Default NoArgs constructor
     */
    InitialAssessmentLevelService() {}
    
    /**
     * Autowired Constructor
     *
     * @param initialAssessmentLevelRepository
     */
    InitialAssessmentLevelService(InitialAssessmentLevelRepository initialAssessmentLevelRepository) {
        this.initialAssessmentLevelRepository = initialAssessmentLevelRepository
    }
    
    /**
     * Find an individual initialAssessmentLevel using the initialAssessmentLevels ID fields
     *
     * @param id the ID fields to search for
     * @return the InitialAssessmentLevel object that matches the ID supplied, or null if not found
     */
    @Override
    @Transactional(readOnly = true)
    InitialAssessmentLevel findById(Integer id) {
        return initialAssessmentLevelRepository.findOne(id);
    }
    
    /**
     * Find a single page of InitialAssessmentLevel objects
     * @return a SearchResult set with the list of InitialAssessmentLevels
     */
    @Override
    @Transactional(readOnly = true)
    List<InitialAssessmentLevel> findAll() {
        return initialAssessmentLevelRepository.findAll();
    }
    
    /**
     *
     * @param id
     * @param initialAssessmentLevel
     * @param abbrv
     * @return
     */
    @Transactional
    public InitialAssessmentLevel saveInitialAssessmentLevel(Integer id, String initialAssessmentLevel, String abbrv) {
        ValidationUtils.assertNotBlank(initialAssessmentLevel, "initialAssessmentLevel cannot be blank");
        ValidationUtils.assertNotNull(abbrv, "abbrv is mandatory");
        InitialAssessmentLevel initialAssessmentLevels = null;
        if (id != null) {
            initialAssessmentLevels = findById(id);
            initialAssessmentLevels.setInitialAssessmentLevel(initialAssessmentLevel);
            initialAssessmentLevels.setAbbrv(abbrv);
            save(initialAssessmentLevels);
        } else {
            initialAssessmentLevels = save(new InitialAssessmentLevel(initialAssessmentLevel, abbrv));
        }
        return initialAssessmentLevels;
    }
    
    /**
     * This service method is used to save a complete InitialAssessmentLevel object in the database
     *
     * @param initialAssessmentLevel the new InitialAssessmentLevel object to be saved
     * @return the saved version of the InitialAssessmentLevel object
     */
    @Override
    @Transactional
    public InitialAssessmentLevel save(InitialAssessmentLevel initialAssessmentLevel) {
        return initialAssessmentLevelRepository.save(initialAssessmentLevel)
    }
    
    /**
     * This service method is used to update an InitialAssessmentLevel object in the database from a partial or complete InitialAssessmentLevel object.
     *
     * @param initialAssessmentLevel the partial or complete InitialAssessmentLevel object to be saved
     * @return the saved version of the InitialAssessmentLevel object
     */
    @Transactional
    public InitialAssessmentLevel updateInitialAssessmentLevel(InitialAssessmentLevel initialAssessmentLevel) {
        InitialAssessmentLevel initialAssessmentLevelToSave = findById(initialAssessmentLevel.id)
        initialAssessmentLevelToSave.initialAssessmentLevel = initialAssessmentLevel.initialAssessmentLevel != null ? initialAssessmentLevel.initialAssessmentLevel : initialAssessmentLevelToSave.initialAssessmentLevel
        initialAssessmentLevelToSave.abbrv = initialAssessmentLevel.abbrv != null ? initialAssessmentLevel.abbrv : initialAssessmentLevelToSave.abbrv
        return save(initialAssessmentLevelToSave)
    }
    
    /**
     * Saves a list of InitialAssessmentLevel objects to the database
     *
     * @param initialAssessmentLevels a list of InitialAssessmentLevels to be saved to the database
     * @return the list of save InitialAssessmentLevel objects
     */
    @Transactional
    public List<InitialAssessmentLevel> saveInitialAssessmentLevels(List<InitialAssessmentLevel> initialAssessmentLevels) {
        return initialAssessmentLevels.collect { initialAssessmentLevel -> save(initialAssessmentLevel) };
    }
    /**
     * This methods throws an InvalidOperationException when called. InitialAssessmentLevel should not be deleted.
     */
    @Override
    public void delete(InitialAssessmentLevel obj) {
        throw new InvalidOperationException("InitialAssessmentLevel should not be deleted")
    }
}
