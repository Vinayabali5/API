package uk.ac.reigate.security

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.context.annotation.Profile
import org.springframework.ldap.core.DirContextOperations
import org.springframework.security.core.GrantedAuthority
import org.springframework.security.core.authority.SimpleGrantedAuthority
import org.springframework.security.ldap.userdetails.LdapAuthoritiesPopulator
import org.springframework.stereotype.Component

import uk.ac.reigate.domain.Person
import uk.ac.reigate.domain.academic.Student
import uk.ac.reigate.domain.security.Role
import uk.ac.reigate.repositories.PersonRepository
import uk.ac.reigate.repositories.academic.StudentRepository
import uk.ac.reigate.services.PersonService

@Profile('secured')
@Component
class AuthoritiesPopulator implements LdapAuthoritiesPopulator {
    
    @Autowired
    PersonRepository userRepository;
    
    @Autowired
    StudentRepository studentRepository
    
    /**
     * Default NoArgs constructor
     */
    AuthoritiesPopulator() {}
    
    /**
     * Default Autowired constructor	
     * @param userService
     */
    AuthoritiesPopulator(PersonRepository userRepository){
        this.userRepository = userRepository;
    }
    
    @Override
    public Collection<? extends GrantedAuthority> getGrantedAuthorities(DirContextOperations userData, String username) {
        Set userRoles = new HashSet();
        
        //get users permissions from repository
        Person person = userRepository.findByUsername(username);
        
        if (person != null) {
            for (Role perm : person.roles) {
                userRoles.add(new SimpleGrantedAuthority(perm.roleName));
            }
            // Check to see if the person is a student
            if (person.student != null) {
                // Add Student role if user is a student
                userRoles.add(new SimpleGrantedAuthority("Student"))
            }
        }
        return userRoles.toArray(new GrantedAuthority[userRoles.size()] );
    }
    
}