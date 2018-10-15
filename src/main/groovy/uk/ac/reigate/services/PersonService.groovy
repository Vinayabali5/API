package uk.ac.reigate.services

import org.apache.log4j.Logger

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import org.springframework.security.access.prepost.PreAuthorize
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

import uk.ac.reigate.domain.Address
import uk.ac.reigate.domain.Person
import uk.ac.reigate.domain.lookup.Gender;
import uk.ac.reigate.domain.lookup.Title;
import uk.ac.reigate.exceptions.InvalidDataException
import uk.ac.reigate.model.SearchResult
import uk.ac.reigate.repositories.ContactRepository
import uk.ac.reigate.repositories.PersonRepository

import static uk.ac.reigate.utils.ValidationUtils.*;

@Service
class PersonService implements ICoreDataService<Person, Integer> {
    
    private final static Logger LOGGER = Logger.getLogger(PersonService.class.getName());
    
    @Autowired
    PersonRepository personRepository
    
    @Autowired
    ContactRepository contactRepository
    
    
    /**
     * Default NoArgs constructor
     */
    PersonService() {}
    
    /**
     * Default autowired constructor
     * 
     * @param personRepository 
     */
    PersonService(PersonRepository personRepository) {
        this.personRepository = personRepository
    }
    
    /**
     * Find an individual person using the people ID fields
     *
     * @param id the ID fields to search for
     * @return the Person object that matches the ID supplied, or null if not found
     */
    @Override
    @Transactional(readOnly = true)
    Person findById(Integer id) {
        return personRepository.findOne(id);
    }
    
    /**
     * Find all people
     *
     * @return a SearchResult set with the list of People
     */
    @Override
    @Transactional(readOnly = true)
    List<Person> findAll() {
        return personRepository.findAll();
    }
    
    /**
     * This service method is used to retrieve a page of Person from the database.
     *
     * @param page The page information to be used for retrieval
     * @return A SearchResult object with the desired Person objects.
     */
    @Transactional(readOnly = true)
    SearchResult<Person> findPersonByPage(Pageable page) {
        Page<Person> results = personRepository.findAll(page);
        return new SearchResult<Person>(page.pageNumber, page.pageSize, results.content)
    }
    
    /**
     * This method is used to save person object to the database.
     * 
     * @param id
     * @param title
     * @param firstName
     * @param preferredName
     * @param surname
     * @param middleNames
     * @param previousSurname
     * @param dob
     * @param gender
     * @param address
     * @param home
     * @param mobile
     * @param work
     * @param email
     * @return
     */
    @Transactional
    public Person savePerson(Integer id, Title title, String firstName, String preferredName, String surname, String legalSurname, String middleNames, String previousSurname, Date dob, Gender gender, Address address, String home, String mobile, String work, String email) {
        assertNotBlank(surname, "surname cannot be blank");
        if (email) {
            assertEmail(email, "email is not in correct format");
        }
        
        Person person = null;
        if (id != null) {
            person = findById(id);
            person.setTitle(title);
            person.setFirstName(firstName);
            person.setSurname(surname);
            person.setLegalSurname(legalSurname);
            person.setDob(dob);
            person.setGender(gender);
            person.setAddress(address);
            person.setHome(home);
            person.setMobile(mobile);
            person.setWork(work);
            person.setEmail(email);
            save(person);
        } else {
            person = save(new Person(title, firstName, preferredName, surname, legalSurname, middleNames, previousSurname, dob, gender, address, home, mobile, work, email));
        }
        return person;
    }
    
    /**
     * This service method is used to save a complete Person object in the database
     *
     * @param person the new Person object to be saved
     * @return the saved version of the Person object
     */
    @Override
    @Transactional
    public Person save(Person person) {
        return personRepository.save(person)
    }
    
    /**
     * Saves a list of Person objects to the database
     *
     * @param people a list of People to be saved to the database
     * @return the list of save Person objects
     */
    @Transactional
    public List<Person> savePeople(List<Person> people) {
        return people.collect { person ->
            savePerson(person.title, person.firstName, person.preferredName, person.surname, person.legalSurname, person.middleNames, person.previousSurname, person.dob, person.gender, person.address, person.home, person.mobile, person.work, person.email, person.username)
        };
    }
    
    
    /**
     * @param username
     * @return Person by username
     */
    @PreAuthorize("@securityChecker.checkReader(authentication)")
    Person findByUsername(String username) {
        return personRepository.findByUsername(username)
    }
    
    /**
     * This methods throws an InvalidOperationException when called. Person should not be deleted.
     */
    @Override
    public void delete(Person obj) {
        throw new InvalidDataException("Person should not be deleted")
    }
    
    
    /**
     * The findByNamePart method is used to find a list of Person objects with part of their name containing the search parameter.
     *
     * @param name the search parameter to look for
     * @return a List<Person> or null if no records found
     */
    @PreAuthorize("@securityChecker.checkReader(authentication)")
    List<Person> findByNamePart(String name) {
        LOGGER.info("*** PersonService.findByNamePart")
        List<Person> people = new ArrayList<Person>()
        
        people.addAll(personRepository.findBySurnameContainingIgnoreCase(name))
        people.addAll(personRepository.findByFirstNameContainingIgnoreCase(name))
        people.addAll(personRepository.findByPreferredNameContainingIgnoreCase(name))
        people.addAll(personRepository.findByMiddleNamesContainingIgnoreCase(name))
        people.addAll(personRepository.findByPreviousSurnameContainingIgnoreCase(name))
        
        if (people.size() != 0) {
            LOGGER.info("* " + people.size() + " records found")
            LOGGER.info("** Return: List<Person>")
        } else {
            LOGGER.info("* No records found")
            LOGGER.info("** Return: null")
        }
        return people
    }
}
