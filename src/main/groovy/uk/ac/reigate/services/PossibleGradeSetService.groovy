package uk.ac.reigate.services

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

import uk.ac.reigate.domain.lookup.PossibleGradeSet
import uk.ac.reigate.exceptions.InvalidOperationException
import uk.ac.reigate.repositories.lookup.PossibleGradeSetRepository
import uk.ac.reigate.utils.ValidationUtils

@Service
class PossibleGradeSetService implements ICoreDataService<PossibleGradeSet, Integer>{
    
    @Autowired
    PossibleGradeSetRepository possibleGradeSetRepository
    
    /**
     * Default NoArgs constructor
     */
    PossibleGradeSetService() {}
    
    /**
     * Autowired Constructor
     *
     * @param possibleGradeSetRepository
     */
    PossibleGradeSetService(PossibleGradeSetRepository possibleGradeSetRepository) {
        this.possibleGradeSetRepository = possibleGradeSetRepository;
    }
    
    /**
     * Find an individual possibleGradeSet using the possibleGradeSets ID fields
     *
     * @param id the ID fields to search for
     * @return the PossibleGradeSet object that matches the ID supplied, or null if not found
     */
    @Transactional(readOnly = true)
    PossibleGradeSet findById(Integer id) {
        return possibleGradeSetRepository.findOne(id);
    }
    
    /**
     * Find all possibleGradeSets
     *
     * @return a List of PossibleGradeSets
     */
    @Transactional(readOnly = true)
    List<PossibleGradeSet> findAll() {
        return possibleGradeSetRepository.findAll();
    }
    
    
    /**
     * @param id
     * @param code
     * @param description
     * @return
     */
    @Transactional
    public PossibleGradeSet savePossibleGradeSet(Integer id, String code, String description) {
        ValidationUtils.assertNotBlank(code, "code cannot be blank");
        ValidationUtils.assertNotNull(description, "description is mandatory");
        
        PossibleGradeSet possibleGradeSet = null;
        
        if (id != null) {
            possibleGradeSet = findById(id);
            
            possibleGradeSet.setCode(code);
            possibleGradeSet.setDescription(description);
            
            save(possibleGradeSet);
        } else {
            possibleGradeSet = save(new PossibleGradeSet(code, description));
        }
        
        return possibleGradeSet;
    }
    
    /**
     * This service method is used to save a complete PossibleGradeSet object in the database
     *
     * @param possibleGradeSet the new PossibleGradeSet object to be saved
     * @return the saved version of the PossibleGradeSet object
     */
    @Transactional
    public PossibleGradeSet save(PossibleGradeSet possibleGradeSet) {
        return possibleGradeSetRepository.save(possibleGradeSet)
    }
    
    /**
     * This service method is used to update an PossibleGradeSet object in the database from a partial or complete PossibleGradeSet object.
     *
     * @param possibleGradeSet the partial or complete PossibleGradeSet object to be saved
     * @return the saved version of the PossibleGradeSet object
     */
    @Transactional
    public PossibleGradeSet updatePossibleGradeSet(PossibleGradeSet possibleGradeSet) {
        PossibleGradeSet possibleGradeSetToSave = findById(possibleGradeSet.id);
        possibleGradeSetToSave.code = possibleGradeSet.code != null ? possibleGradeSet.code : possibleGradeSetToSave.code
        possibleGradeSetToSave.description = possibleGradeSet.description != null ? possibleGradeSet.description : possibleGradeSetToSave.description
        return save(possibleGradeSetToSave)
    }
    
    /**
     * Saves a list of PossibleGradeSet objects to the database
     *
     * @param possibleGradeSets a list of PossibleGradeSets to be saved to the database
     * @return the list of save PossibleGradeSet objects
     */
    @Transactional
    public List<PossibleGradeSet> savePossibleGradeSets(List<PossibleGradeSet> possibleGradeSets) {
        return possibleGradeSets.collect { possibleGradeSet -> save(possibleGradeSet) };
    }
    
    /**
     * This methods throws an InvalidOperationException when called. PossibleGradeSet should not be deleted.
     */
    @Override
    public void delete(PossibleGradeSet obj) {
        throw new InvalidOperationException("PossibleGradeSet should not be deleted")
    }
}
