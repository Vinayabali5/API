package uk.ac.reigate.config.security

import org.apache.log4j.Logger

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.beans.factory.annotation.Value
import org.springframework.context.annotation.Configuration
import org.springframework.context.annotation.Profile
import org.springframework.core.annotation.Order
import org.springframework.core.env.Environment
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter

import uk.ac.reigate.security.AuthoritiesPopulator


@Profile('secured')
@Configuration
@Order(50)
@EnableWebSecurity
class LdapConfiguration extends WebSecurityConfigurerAdapter {
    
    private final static Logger LOGGER = Logger.getLogger("Security Settings");
    
    @Autowired
    Environment env;
    
    @Value("\${ldap.enabled}")
    Boolean enabled
    
    @Value("\${ldap.url}")
    String url
    
    @Value("\${ldap.port}")
    Integer port
    
    @Value("\${ldap.bindUser}")
    String bindUser
    
    @Value("\${ldap.bindPassword}")
    String bindPassword
    
    @Value("\${ldap.userSearchBase}")
    String userSearchBase
    
    @Value("\${ldap.userSearchFilter}")
    String userSearchFilter
    
    @Autowired
    AuthoritiesPopulator authoritiesPopulator
    
    @Autowired
    public void configureGlobal(AuthenticationManagerBuilder auth) throws Exception {
        if (enabled) {
            LOGGER.info("II Adding LDAP Authentication");
            
            auth
                    .ldapAuthentication()
                    .userSearchBase(userSearchBase)
                    .userSearchFilter(userSearchFilter)
                    .ldapAuthoritiesPopulator(authoritiesPopulator)
                    .contextSource()
                    .managerDn(bindUser)
                    .managerPassword(bindPassword)
                    .url(url)
                    .port(port)
        } else {
            LOGGER.info("II LDAP Disabled");
        }
    }
}
