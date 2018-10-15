package uk.ac.reigate.config.security

import org.apache.log4j.Logger

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.beans.factory.annotation.Value
import org.springframework.context.annotation.Configuration
import org.springframework.core.annotation.Order
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity

//@Profile(['dev', 'demo'])
@Configuration
@Order(76)
@EnableWebSecurity
class ServiceUserAuthentication {
    
    private final static Logger LOGGER = Logger.getLogger("Security Settings");
    
    @Value("\${cid.serviceUser.username}")
    private String username
    
    @Value("\${cid.serviceUser.password}")
    private String password
    
    @Autowired
    public void globalUserDetails(AuthenticationManagerBuilder auth) throws Exception {
        LOGGER.info("II Adding Service User Authentication");
        
        auth.inMemoryAuthentication()
                .withUser(username).password(password).roles("Service User")
    }
}
