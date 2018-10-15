package uk.ac.reigate.services

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.data.domain.Page
import org.springframework.data.domain.PageRequest
import org.springframework.data.domain.Sort
import org.springframework.security.access.prepost.PreAuthorize
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

import uk.ac.reigate.domain.lookup.Level
import uk.ac.reigate.domain.lookup.PossibleGradeSet
import uk.ac.reigate.exceptions.InvalidOperationException
import uk.ac.reigate.model.PageInfo
import uk.ac.reigate.model.SearchResult
import uk.ac.reigate.repositories.lookup.LevelRepository
import uk.ac.reigate.utils.ValidationUtils

@Service
class LevelService implements ICoreDataService<Level, Integer>{
    
    private static String DEFAULT_SORT_FIELD = 'code'
    
    private static Sort.Direction DEFAULT_SORT_ORDER = Sort.Direction.ASC;
    
    @Autowired
    LevelRepository levelRepository
    
    /**
     * Default NoArgs constructor
     */
    LevelService() {}
    
    /**
     * Autowired Constructor
     *
     * @param levelRepository
     */
    LevelService(LevelRepository levelRepository) {
        this.levelRepository = levelRepository;
    }
    
    /**
     * Find an individual level using the levels ID fields
     *
     * @param id the ID fields to search for
     * @return the Level object that matches the ID supplied, or null if not found
     */
    @Override
    @Transactional(readOnly = true)
    Level findById(Integer id) {
        return levelRepository.findOne(id);
    }
    
    /**
     * Find all levels
     *
     * @return a SearchResult set with the list of Levels
     */
    @Override
    @Transactional(readOnly = true)
    List<Level> findAll() {
        return levelRepository.findAll();
    }
    
    /**
     * Find a single page of Level objects
     *
     * @param page the page number to retrieve
     * @param size the number of records on each page
     * @return a SearchResult set with the list of Levels
     */
    @PreAuthorize("@securityChecker.checkReader(authentication)")
    @Transactional(readOnly = true)
    SearchResult<Level> findLevels(int page, int size) {
        PageRequest pageRequest = new PageRequest(page, size) //, new Sort(DEFAULT_SORT_ORDER, DEFAULT_SORT_FIELD))
        Page<Level> levels = levelRepository.findAll(pageRequest);
        PageInfo pageInfo = new PageInfo(page, size, '', (Long) levels.total)
        return new SearchResult<>(pageInfo, levels.toList());
    }
    
    /**
     *
     * @param id
     * @param code
     * @param description
     * @return
     */
    @Transactional
    public Level saveLevel(Integer id, String code, String description, PossibleGradeSet possibleGradeSet, Integer progressionTo, String alisQualCode) {
        ValidationUtils.assertNotBlank(code, "code cannot be blank");
        ValidationUtils.assertNotNull(description, "description is mandatory");
        Level level = null;
        if (id != null) {
            level = findById(id);
            level.setCode(code);
            level.setDescription(description);
            level.setPossibleGradeSet(possibleGradeSet)
            level.setProgressionTo(progressionTo)
            level.setAlisQualCode(alisQualCode)
            save(level);
        } else {
            level = save(new Level(code, description, PossibleGradeSet, progressionTo, alisQualCode));
        }
        return level;
    }
    
    /**
     * Saves a Level object to the database
     *
     * @param level the Level object to save
     * @return the save Level object
     */
    @Override
    @Transactional
    public Level save(Level level) {
        return levelRepository.save(level)
    }
    
    /**
     * This service method is used to update an Level object in the database from a partial or complete Level object.
     *
     * @param level the partial or complete Level object to be saved
     * @return the saved version of the Level object
     */
    @Transactional
    public Level updateLevel(Level level) {
        Level levelToSave = findById(level.id)
        levelToSave.code = level.code != null ? level.code : levelToSave.code
        levelToSave.description = level.description != null ? level.description : levelToSave.description
        return save(levelToSave)
    }
    
    /**
     * Saves a list of Level objects to the database
     *
     * @param levels a list of Levels to be saved to the database
     * @return the list of save Level objects
     */
    @Transactional
    public List<Level> saveLevels(List<Level> levels) {
        return levels.collect { level -> save(level) };
    }
    
    /**
     * This methods throws an InvalidOperationException when called. Level should not be deleted.
     */
    @Override
    public void delete(Level obj) {
        throw new InvalidOperationException("Level should not be deleted")
    }
}
