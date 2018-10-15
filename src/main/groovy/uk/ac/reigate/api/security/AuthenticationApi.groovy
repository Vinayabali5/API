package uk.ac.reigate.api.security

import java.security.Principal

import org.apache.log4j.Logger

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.core.env.Environment
import org.springframework.http.HttpStatus
import org.springframework.http.MediaType
import org.springframework.http.ResponseEntity
import org.springframework.security.core.Authentication
import org.springframework.security.core.authority.AuthorityUtils
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

import uk.ac.reigate.domain.Person
import uk.ac.reigate.repositories.PersonRepository
import uk.ac.reigate.repositories.StaffRepository
import uk.ac.reigate.repositories.academic.StudentRepository

@RestController
class AuthenticationApi {
    
    @Autowired
    PersonRepository personRepository
    
    @Autowired
    StaffRepository staffRepository
    
    @Autowired
    StudentRepository studentRepository
    
    @Autowired
    Environment env
    
    private static final Logger LOGGER = Logger.getLogger(AuthenticationApi.class);
    
    @RequestMapping(value = "/user", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Map<String, Object>> getUserInfo(Principal user) {
        Map<String, Object> map = new LinkedHashMap<String, Object>()
        if (user != null) {
            LOGGER.info('II User: ' + user.getName())
            map.put("username", user.getName());
            Set<String> roles = AuthorityUtils.authorityListToSet(((Authentication) user).getAuthorities())
            
            Person activeUser = personRepository.findByUsername(user.getName())
            if (activeUser != null) {
                map.put("personId", activeUser.id)
                map.put("name", activeUser.surname + ', ' + activeUser.firstName);
                if (activeUser.staff != null) {
                    map.put("staffId", activeUser.staff.id)
                    map.put("initials", activeUser.staff.initials)
                    map.put("isStaff", true)
                }
                if (activeUser.student != null) {
                    map.put("studentId", activeUser.student.id)
                    roles.add("ROLE_Student")
                    map.compute("isStudent", true)
                }
            }
            map.put("roles", roles);
        }
        return new ResponseEntity<?>(map, HttpStatus.OK);
    }
}
