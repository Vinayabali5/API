package uk.ac.reigate.services

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.security.access.prepost.PreAuthorize
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

import uk.ac.reigate.domain.lookup.Level
import uk.ac.reigate.domain.lookup.PossibleGrade
import uk.ac.reigate.domain.lookup.PossibleGradeSet
import uk.ac.reigate.exceptions.InvalidOperationException
import uk.ac.reigate.repositories.lookup.LevelRepository
import uk.ac.reigate.repositories.lookup.PossibleGradeRepository
import uk.ac.reigate.utils.ValidationUtils

@Service
class PossibleGradeService implements ICoreDataService<PossibleGrade, Integer>{
    
    @Autowired
    PossibleGradeRepository possibleGradeRepository
    
    @Autowired
    LevelRepository levelRepository
    
    /**
     * Default NoArgs constructor
     */
    PossibleGradeService() {}
    
    /**
     * Autowired Constructor
     *
     * @param possibleGradeRepository
     */
    PossibleGradeService(PossibleGradeRepository possibleGradeRepository) {
        this.possibleGradeRepository = possibleGradeRepository;
    }
    
    /**
     * Find an individual possibleGrade using the possibleGrades ID fields
     *
     * @param id the ID fields to search for
     * @return the PossibleGrade object that matches the ID supplied, or null if not found
     */
    @Override
    @Transactional(readOnly = true)
    PossibleGrade findById(Integer id) {
        return possibleGradeRepository.findOne(id);
    }
    
    /**
     * Find all possibleGrades
     *
     * @return a SearchResult set with the list of PossibleGrades
     */
    @Override
    @Transactional(readOnly = true)
    List<PossibleGrade> findAll() {
        return possibleGradeRepository.findAll();
    }
    
    
    /**
     * @param id
     * @param code
     * @param description
     * @param level
     * @param grade
     * @param ucasPoints
     * @param useForKeyAssessment
     * @return
     */
    @Transactional
    public PossibleGrade savePossibleGrade(Integer id, String code, String description, PossibleGradeSet gradeSet, Level level, String grade, Integer ucasPoints, Boolean useForKeyAssessment) {
        ValidationUtils.assertNotBlank(code, "code cannot be blank");
        ValidationUtils.assertNotNull(description, "description is mandatory");
        
        PossibleGrade possibleGrade = null;
        
        if (id != null) {
            possibleGrade = findById(id);
            
            possibleGrade.setCode(code);
            possibleGrade.setDescription(description);
            possibleGrade.setGradeSet(gradeSet);
            possibleGrade.setLevel(level);
            possibleGrade.setGrade(grade);
            possibleGrade.setUcasPoints(ucasPoints);
            possibleGrade.setUseForKeyAssessment(useForKeyAssessment);
            
            save(possibleGrade);
        } else {
            possibleGrade = save(new PossibleGrade(code, description, gradeSet, level, grade, ucasPoints, useForKeyAssessment));
        }
        
        return possibleGrade;
    }
    
    /**
     * This service method is used to save a complete PossibleGrade object in the database
     *
     * @param possibleGrade the new PossibleGrade object to be saved
     * @return the saved version of the PossibleGrade object
     */
    @Override
    @Transactional
    public PossibleGrade save(PossibleGrade possibleGrade) {
        return possibleGradeRepository.save(possibleGrade)
    }
    
    /**
     * Saves a list of PossibleGrade objects to the database
     *
     * @param possibleGrades a list of PossibleGrades to be saved to the database
     * @return the list of save PossibleGrade objects
     */
    @Transactional
    public List<PossibleGrade> savePossibleGrades(List<PossibleGrade> possibleGrades) {
        return possibleGrades.collect { possibleGrade -> save(possibleGrade) };
    }
    
    /**
     * @param possibleGradeSetId
     * @return
     */
    @PreAuthorize("@securityChecker.checkReader(authentication)")
    public List<PossibleGrade> findByPossibleGradeSet(Integer possibleGradeSetId){
        List<PossibleGrade> possibleGrade = possibleGradeRepository.findByGradeSet_Id(possibleGradeSetId)
        return possibleGrade
    }
    
    /**
     * This methods throws an InvalidOperationException when called. Period should not be deleted.
     */
    @Override
    public void delete(PossibleGrade obj) {
        throw new InvalidOperationException("PossibleGrade should not be deleted")
    }
}
