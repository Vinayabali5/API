package uk.ac.reigate.services

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.security.access.prepost.PreAuthorize
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

import uk.ac.reigate.domain.Contact
import uk.ac.reigate.domain.Person
import uk.ac.reigate.domain.lookup.ContactType
import uk.ac.reigate.exceptions.NotFoundException
import uk.ac.reigate.repositories.ContactRepository
import uk.ac.reigate.repositories.PersonRepository
import uk.ac.reigate.repositories.lookup.ContactTypeRepository
import uk.ac.reigate.utils.ValidationUtils

@Service
class ContactService implements ICoreDataService<Contact, Integer>{
    
    @Autowired
    ContactRepository contactRepository
    
    @Autowired
    PersonRepository personRepository
    
    @Autowired
    ContactTypeRepository contactTypeRepository
    
    /**
     * Default NoArgs constructor
     */
    ContactService() {}
    
    /**
     * Autowired Constructor
     *
     * @param contactRepository
     */
    ContactService(ContactRepository contactRepository) {
        this.contactRepository = contactRepository
    }
    
    /**
     * Find an individual contact using the contacts ID fields
     *
     * @param id the ID fields to search for
     * @return the Contact object that matches the ID supplied, or null if not found
     */
    @Override
    @Transactional(readOnly = true)
    Contact findById(Integer id) {
        return contactRepository.findOne(id);
    }
    
    /**
     * Find all contacts
     *
     * @return a list of Contacts
     */
    @Override
    @Transactional(readOnly = true)
    List<Contact> findAll() {
        return contactRepository.findAll();
    }
    
    /**
     * @param id
     * @param person
     * @param contact
     * @param contactType
     * @param contactable
     * @param preferred
     * @return
     */
    @Transactional
    public Contact saveContact(Integer id, Person person, Person contact, ContactType contactType, Boolean contactable, Boolean preferred) {
        ValidationUtils.assertNotBlank(person, "person cannot be blank");
        ValidationUtils.assertNotBlank(contact, "contact cannot be blank");
        ValidationUtils.assertNotBlank(contactType, "contactType cannot be blank");
        ValidationUtils.assertNotBlank(contactable, "contactable can be mandatory");
        ValidationUtils.assertNotBlank(preferred, "preferred can be mandatory");
        
        Contact contactId = null;
        if (contactId != null) {
            contact = findById(contactId);
            
            contact.setPerson(person);
            contact.setContact(contact);
            contact.setContactType(contactType);
            contact.setContactable(contactable);
            contact.setPreferred(preferred);
            
            save(contact);
        } else {
            contact = save(new Contact(person, contact, contactType, contactable, preferred));
        }
        return contact;
    }
    
    /**
     * This service method is used to save a complete Contact object in the database
     *
     * @param contact the new Contact object to be saved
     * @return the saved version of the Contact object
     */
    @Override
    @Transactional
    public Contact save(Contact contact) {
        return contactRepository.save(contact)
    }
    
    /**
     * This service method is used to update an Contact object in the database from a partial or complete Contact object.
     *
     * @param contact the partial or complete Contact object to be saved
     * @return the saved version of the Contact object
     */
    @Transactional
    public Contact updateContact(Contact contact) {
        Contact contactToSave = findById(contact.id)
        contactToSave.person = contact.person.id != null ? personRepository.findOne(contact.person.id) : contactToSave.person
        contactToSave.contact = contact.contact.id != null ? personRepository.findOne(contact.contact.id) : contactToSave.contact
        contactToSave.contactType = contact.contactType.id != null ? contactTypeRepository.findOne(contact.contactType.id) : contactToSave.contactType
        contactToSave.contactable = contact.contactable != null ? contact.contactable : contactToSave.contactable
        contactToSave.preferred = contact.preferred != null ? contact.preferred : contactToSave.preferred
        return save(contactToSave)
    }
    
    /**
     * Saves a list of Contact objects to the database
     *
     * @param contacts a list of Contacts to be saved to the database
     * @return the list of save Contact objects
     */
    @Transactional
    public List<Contact> saveContacts(List<Contact> contacts) {
        return contacts.collect { contact -> save( contact ) };
    }
    
    /**
     * Find contact by using personId
     *
     * @param personId
     * @return the Contact object that matches the personId supplied, or null if not found
     */
    public List<Contact> searchByPersonId (Integer personId) {
        List<Contact> contacts = contactRepository.findByPerson(personRepository.findOne(personId))
        return contacts
    }
    
    /**
     * Delete a contact based on the ID supplied
     * 
     * @param contactId the ID of the contact to delete
     */
    @PreAuthorize("@securityChecker.checkWriter(authentication)")
    @Transactional
    public void delete(Integer contactId) {
        if(findById(contactId)!=null){
            contactRepository.delete(contactId)
        }else
            throw new NotFoundException("Contact does not exist")
    }
    
    /**
     * Delete a contact 
     *
     * @param contact object to be delete
     */
    @Override
    public void delete(Contact contact){
        contactRepository.delete(contact)
    }
}


