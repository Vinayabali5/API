package uk.ac.reigate.config.security

import org.apache.log4j.Logger

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.context.annotation.Configuration
import org.springframework.context.annotation.Profile
import org.springframework.core.annotation.Order
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity

@Profile(['dev', 'demo'])
@Configuration
@Order(75)
@EnableWebSecurity
class InMemoryAuthentication {
    
    private final static Logger LOGGER = Logger.getLogger("Security Settings");
    
    @Autowired
    public void globalUserDetails(AuthenticationManagerBuilder auth) throws Exception {
        LOGGER.info("II Adding In Memory Authentication");
        
        auth.inMemoryAuthentication()
                .withUser("user").password("password").roles("User").and()
                .withUser("staff").password("password").roles("Staff").and()
                .withUser("enrolment").password("password").roles("Staff", "Enrolment Manager").and()
                .withUser("timetables").password("password").roles("Staff", "Timetabling").and()
                .withUser("student-services").password("password").roles("Staff", "Office Administration").and()
                .withUser("admissions").password("password").roles("Staff", "Admissions").and()
                .withUser("exams").password("password").roles("Staff", "Exams Officer").and()
                .withUser("first-aid").password("password").roles("Staff", "First Aid Coordinator").and()
                .withUser("qoe").password("password").roles("Staff", "Quals on Entry").and()
                .withUser("careers").password("password").roles("Staff", "Careers").and()
                .withUser("id").password("password").roles("Staff", "ID Violation").and()
                .withUser("admin").password("admin").roles("Staff", "Administration", "Core Data", "System Admin")
    }
}
