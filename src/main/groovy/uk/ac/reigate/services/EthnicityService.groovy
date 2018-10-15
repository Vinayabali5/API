package uk.ac.reigate.services

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

import uk.ac.reigate.domain.lookup.Ethnicity
import uk.ac.reigate.exceptions.InvalidOperationException
import uk.ac.reigate.repositories.lookup.EthnicityRepository
import uk.ac.reigate.utils.ValidationUtils

@Service
class EthnicityService implements ICoreDataService<Ethnicity, Integer> {
    
    @Autowired
    EthnicityRepository ethnicityRepository
    
    /**
     * Default NoArgs constructor
     */
    EthnicityService() {}
    
    /**
     * Autowired Constructor
     *
     * @param ethnicityRepository
     */
    EthnicityService(EthnicityRepository ethnicityRepository) {
        this.ethnicityRepository = ethnicityRepository;
    }
    
    /**
     * Find an individual ethnicity using the ethnicities ID fields
     *
     * @param id the ID fields to search for
     * @return the Ethnicity object that matches the ID supplied, or null if not found
     */
    @Override
    @Transactional(readOnly = true)
    Ethnicity findById(Integer id) {
        return ethnicityRepository.findOne(id);
    }
    
    /**
     * Find all ethnicities
     *
     * @return a List of Ethnicities
     */
    @Override
    @Transactional(readOnly = true)
    List<Ethnicity> findAll() {
        return ethnicityRepository.findAll();
    }
    
    /**
     * @param id
     * @param code
     * @param description
     * @param shortDescription
     * @param validFrom
     * @param validTo
     * @return
     */
    @Transactional
    public Ethnicity saveEthnicity(Integer id, String code, String description, String shortDescription, Date validFrom, Date validTo) {
        ValidationUtils.assertNotBlank(code, "code cannot be blank");
        ValidationUtils.assertNotNull(description, "description is mandatory");
        ValidationUtils.assertNotNull(shortDescription, "shortDescription is mandatory");
        ValidationUtils.assertNotNull(validFrom, "validFrom is mandatory");
        ValidationUtils.assertNotNull(validTo, "validTo is mandatory");
        Ethnicity ethnicity = null;
        if (id != null) {
            ethnicity = findById(id);
            ethnicity.setCode(code);
            ethnicity.setDescription(description);
            ethnicity.setShortDescription(shortDescription);
            ethnicity.setValidFrom(validFrom);
            ethnicity.setValidTo(validTo);
            save(ethnicity);
        } else {
            ethnicity = save(new Ethnicity(code, description, shortDescription, validFrom, validTo));
        }
        return ethnicity;
    }
    
    /**
     * This service method is used to save a complete Ethnicity object in the database
     *
     * @param ethnicity the new Ethnicity object to be saved
     * @return the saved version of the Ethnicity object
     */
    @Override
    @Transactional
    public Ethnicity save(Ethnicity ethnicity) {
        return ethnicityRepository.save(ethnicity)
    }
    
    /**
     * This service method is used to update an Ethnicity object in the database from a partial or complete Ethnicity object.
     *
     * @param ethnicity the partial or complete Ethnicity object to be saved
     * @return the saved version of the Ethnicity object
     */
    @Transactional
    public Ethnicity updateEthnicity(Ethnicity ethnicity) {
        Ethnicity ethnicityToSave = findById(ethnicity.id)
        ethnicityToSave.code = ethnicity.code != null ? ethnicity.code : ethnicityToSave.code
        ethnicityToSave.description = ethnicity.description != null ? ethnicity.description : ethnicityToSave.description
        ethnicityToSave.shortDescription = ethnicity.shortDescription != null ? ethnicity.shortDescription : ethnicityToSave.shortDescription
        ethnicityToSave.validFrom = ethnicity.validFrom != null ? ethnicity.validFrom : ethnicityToSave.validFrom
        ethnicityToSave.validTo = ethnicity.validTo != null ? ethnicity.validTo : ethnicityToSave.validTo
        return save(ethnicityToSave)
    }
    
    /**
     * Saves a list of Ethnicity objects to the database
     *
     * @param ethnicities a list of Ethnicities to be saved to the database
     * @return the list of save Ethnicity objects
     */
    @Transactional
    public List<Ethnicity> saveEthnicities(List<Ethnicity> ethnicities) {
        return ethnicities.collect { ethnicity -> save(ethnicity) };
    }
    /**
     * This methods throws an InvalidOperationException when called. Ethnicity should not be deleted.
     */
    @Override
    public void delete(Ethnicity obj) {
        throw new InvalidOperationException("Ethnicity should not be deleted");
    }
    
    /**
     * This method is used to retrieve an Ethnicity object from the supplied code.
     * 
     * @param code The ethnicity code to search for.
     * @return An Ethnicity object that matches the code supplied.
     */
    public Ethnicity findByCode(String code) {
        return ethnicityRepository.findByCode(code)
    }
}
