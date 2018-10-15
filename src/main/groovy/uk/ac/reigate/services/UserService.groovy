package uk.ac.reigate.services

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

import uk.ac.reigate.domain.Person
import uk.ac.reigate.repositories.PersonRepository

@Service
class UserService {
    
    @Autowired
    PersonRepository personRepository
    
    /**
     * Default NoArgs constructor
     */
    UserService() {}
    
    /**
     * Autowired Constructor
     *
     * @param personRepository
     */
    UserService(PersonRepository personRepository) {
        this.personRepository = personRepository;
    }
    
    /**
     * Find an individual user using the username
     *
     * @param string the String fields to search for
     * @return the User object that matches the String supplied, or null if not found
     */
    @Transactional(readOnly = true)
    Person findUserByUsername(String username) {
        return personRepository.findByUsername(username);
    }
}
