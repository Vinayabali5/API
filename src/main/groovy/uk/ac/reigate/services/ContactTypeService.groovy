package uk.ac.reigate.services

//import static org.springframework.util.Assert

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

import uk.ac.reigate.domain.lookup.ContactType
import uk.ac.reigate.exceptions.InvalidOperationException
import uk.ac.reigate.repositories.lookup.ContactTypeRepository
import uk.ac.reigate.utils.ValidationUtils;

@Service
class ContactTypeService implements ICoreDataService<ContactType, Integer>{
    
    @Autowired
    ContactTypeRepository contactTypeRepository
    
    /**
     * Default NoArgs constructor
     */
    ContactTypeService() {}
    
    /**
     * Autowired Constructor
     *
     * @param contactTypeRepository
     */
    ContactTypeService(ContactTypeRepository contactTypeRepository){
        this.contactTypeRepository = contactTypeRepository;
    }
    
    /**
     * Find an individual contactType using the contactTypes ID fields
     *
     * @param id the ID fields to search for
     * @return the ContactType object that matches the ID supplied, or null if not found
     */
    @Override
    @Transactional(readOnly = true)
    ContactType findById(Integer id) {
        return contactTypeRepository.findOne(id);
    }
    
    /**
     * Find all contactTypes
     *
     * @return a List of ContactTypes
     */
    @Override
    @Transactional(readOnly = true)
    List<ContactType> findAll() {
        return contactTypeRepository.findAll();
    }
    
    
    /**
     * @param id
     * @param name
     * @param description
     * @return
     */
    @Transactional
    public ContactType saveContactType(Integer id, String name, String description) {
        ValidationUtils.assertNotBlank(name, "name cannot be blank");
        ValidationUtils.assertNotNull(description, "description is mandatory");
        
        ContactType contactType = null;
        
        if (id != null) {
            contactType = findById(id);
            
            contactType.setName(name);
            contactType.setDescription(description);
            
            save(contactType);
        } else {
            contactType = save(new ContactType(name, description));
        }
        
        return contactType;
    }
    
    /**
     * This service method is used to save a complete ContactType object in the database
     *
     * @param contactType the new ContactType object to be saved
     * @return the saved version of the ContactType object
     */
    @Override
    @Transactional
    public ContactType save(ContactType contactType) {
        return contactTypeRepository.save(contactType)
    }
    
    /**
     * Saves a list of ContactType objects to the database
     *
     * @param contactTypes a list of ContactTypes to be saved to the database
     * @return the list of save ContactType objects
     */
    @Transactional
    public List<ContactType> saveContactTypes(List<ContactType> contactTypes) {
        return contactTypes.collect { contactType -> save(contactType) };
    }
    
    /**
     * This methods throws an InvalidOperationException when called. ContactType should not be deleted.
     */
    @Override
    public void delete(ContactType obj) {
        throw new InvalidOperationException("ContactType should not be deleted")
        
    }
    
    /**
     * Find all contactTypes
     *
     * @return a List of ContactTypes
     */
    @Transactional(readOnly = true)
    ContactType findByDescription(String description) {
        return contactTypeRepository.findByDescription(description)
    }
    
    
    
}
