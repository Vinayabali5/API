package uk.ac.reigate.services

//import static org.springframework.util.Assert

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

import uk.ac.reigate.domain.lookup.Title
import uk.ac.reigate.exceptions.InvalidOperationException
import uk.ac.reigate.repositories.lookup.TitleRepository
import uk.ac.reigate.utils.ValidationUtils;

@Service
class TitleService implements ICoreDataService<Title, Integer>{
    
    @Autowired
    TitleRepository titleRepository
    
    /**
     * Default NoArgs constructor
     */
    TitleService() {}
    
    /**
     * Autowired Constructor
     *
     * @param titleRepository
     */
    TitleService(TitleRepository titleRepository) {
        this.titleRepository = titleRepository;
    }
    
    /**
     * Find an individual title using the titles ID fields
     *
     * @param id the ID fields to search for
     * @return the Title object that matches the ID supplied, or null if not found
     */
    @Override
    @Transactional(readOnly = true)
    Title findById(Integer id) {
        return titleRepository.findOne(id);
    }
    
    /**
     * Find a single page of Title objects
     * @return a List of Titles
     */
    @Override
    @Transactional(readOnly = true)
    List<Title> findAll() {
        List<Title> titles = titleRepository.findAll();
        return titles
    }
    
    
    /**
     * @param id
     * @param code
     * @param description
     * @return
     */
    @Transactional
    public Title saveTitle(Integer id, String code, String description) {
        ValidationUtils.assertNotBlank(code, "code cannot be blank");
        ValidationUtils.assertNotNull(description, "description is mandatory");
        
        Title title = null;
        
        if (id != null) {
            title = findById(id);
            
            title.setCode(code);
            title.setDescription(description);
            
            save(title);
        } else {
            title = save(new Title(code, description));
        }
        
        return title;
    }
    
    /**
     * This service method is used to save a complete Title object in the database
     *
     * @param title the new Title object to be saved
     * @return the saved version of the Title object
     */
    @Override
    @Transactional
    public Title save(Title title) {
        return titleRepository.save(title)
    }
    
    /**
     * This service method is used to update an Title object in the database from a partial or complete Title object.
     *
     * @param title the partial or complete Title object to be saved
     * @return the saved version of the Title object
     */
    
    @Transactional
    public Title updateTitle(Title title) {
        Title titleToSave = findById(title.id)
        titleToSave.code = title.code != null ? title.code : titleToSave.code
        titleToSave.description = title.description != null ? title.description : titleToSave.description
        return save(titleToSave)
    }
    
    /**
     * Saves a list of Title objects to the database
     *
     * @param titles a list of Titles to be saved to the database
     * @return the list of save Title objects
     */
    
    @Transactional
    public List<Title> saveTitles(List<Title> titles) {
        return titles.collect { title -> save(title) };
    }
    
    /**
     * This methods throws an InvalidOperationException when called. Title should not be deleted.
     */
    @Override
    public void delete(Title obj) {
        throw new InvalidOperationException("Title should not be deleted")
        
    }
    
    
    @Transactional(readOnly = true)
    Title findByDescription(String description) {
        return titleRepository.findByDescription(description);
    }
    
    
}
