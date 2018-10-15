package uk.ac.reigate.services

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.security.access.prepost.PreAuthorize
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

import uk.ac.reigate.domain.lookup.Nationality
import uk.ac.reigate.exceptions.InvalidOperationException
import uk.ac.reigate.repositories.lookup.NationalityRepository
import uk.ac.reigate.utils.ValidationUtils

@Service
class NationalityService implements ICoreDataService<Nationality, Integer>{
    
    @Autowired
    NationalityRepository nationalityRepository
    
    /**
     * Default NoArgs constructor
     */
    NationalityService() {}
    
    /**
     * Autowired Constructor
     *
     * @param nationalityRepository
     */
    NationalityService(NationalityRepository nationalityRepository) {
        this.nationalityRepository = nationalityRepository;
    }
    
    /**
     * Autowired Constructor
     *
     * @param nationalityRepository
     */
    @Override
    @Transactional(readOnly = true)
    Nationality findById(Integer id) {
        return nationalityRepository.findOne(id);
    }
    
    /**
     * Find a single page of Nationality objects
     * @return a SearchResult set with the list of Nationalities
     */
    @Override
    @Transactional(readOnly = true)
    List<Nationality> findAll() {
        return nationalityRepository.findAll();
    }
    
    /**
     *
     * @param id
     * @param name
     * @param description
     * @return
     */
    
    @Transactional
    public Nationality saveNationality(Integer id, String name, String description) {
        ValidationUtils.assertNotBlank(name, "name cannot be blank");
        ValidationUtils.assertNotNull(description, "description is mandatory");
        Nationality nationality = null;
        if (id != null) {
            nationality = findById(id);
            nationality.setName(name);
            nationality.setDescription(description);
            save(nationality);
        } else {
            nationality = save(new Nationality(name, description));
        }
        return nationality;
    }
    
    /**
     * This service method is used to save a complete Nationality object in the database
     *
     * @param nationality the new Nationality object to be saved
     * @return the saved version of the Nationality object
     */
    @Override
    @Transactional
    public Nationality save(Nationality nationality) {
        return nationalityRepository.save(nationality)
    }
    
    /**
     * This service method is used to update an Nationality object in the database from a partial or complete Nationality object.
     *
     * @param nationality the partial or complete Nationality object to be saved
     * @return the saved version of the Nationality object
     */
    @Transactional
    public Nationality updateNationality(Nationality nationality) {
        Nationality nationalityToSave = findById(nationality.id)
        nationalityToSave.name = nationality.name != null ? nationality.name : nationalityToSave.name
        nationalityToSave.description = nationality.description != null ? nationality.description : nationalityToSave.description
        return  save(nationalityToSave)
    }
    
    /**
     * Saves a list of Nationality objects to the database
     *
     * @param nationalitys a list of Nationalities to be saved to the database
     * @return the list of save Nationality objects
     */
    @Transactional
    public List<Nationality> saveNationalities(List<Nationality> nationalities) {
        return nationalities.collect { nationality -> save(nationality) };
    }
    /**
     * This methods throws an InvalidOperationException when called. Nationality should not be deleted.
     */
    @Override
    public void delete(Nationality obj) {
        throw new InvalidOperationException("Nationality should not be deleted")
    }
    
    /**
     * This method is used to retrieve a Nationality by the description provided
     * 
     * @param description The full description of the Nationality
     * @return
     */
    @PreAuthorize("@securityChecker.checkReader(authentication)")
    public Nationality findByDescription(String description) {
        return nationalityRepository.findByDescription(description)
    }
}
