package uk.ac.reigate.config.security

import org.apache.log4j.Logger

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.context.annotation.Configuration
import org.springframework.context.annotation.Profile
import org.springframework.core.annotation.Order
import org.springframework.core.env.Environment
import org.springframework.http.HttpMethod
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity
import org.springframework.security.config.annotation.web.builders.HttpSecurity
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter
import org.springframework.security.web.authentication.www.BasicAuthenticationEntryPoint
import org.springframework.security.web.csrf.CsrfTokenRepository
import org.springframework.security.web.csrf.HttpSessionCsrfTokenRepository
import org.springframework.util.StringUtils


@Profile('secured')
@Configuration
@Order(1)
@EnableWebSecurity
@EnableGlobalMethodSecurity(securedEnabled=true, prePostEnabled = true)
class SecurityConfiguration extends WebSecurityConfigurerAdapter {
    
    private final static Logger LOGGER = Logger.getLogger("Security Settings");
    
    @Autowired
    Environment env
    
    private final List<String> DEFAULT_READERS = ["Staff"];
    private final List<String> DEFAULT_WRITERS = [
        "Core Data",
        "Office Administration",
        "Enrolment Manager",
        "Administration",
        "Student Services",
        "Exams Officer"
    ];
    private final List<String> DEFAULT_POWER_USER = ["Core Data", "Developer"];
    
    @Override
    protected void configure(HttpSecurity http) throws Exception {
        LOGGER.info("II Configuring HTTP Basic Security");
        
        String defaultReaders = StringUtils.collectionToDelimitedString(DEFAULT_READERS, ' or ', "hasRole('", "')");
        String defaultWriters = StringUtils.collectionToDelimitedString(DEFAULT_WRITERS, ' or ', "hasRole('", "')");
        String defaultPowerUsers = StringUtils.collectionToDelimitedString(DEFAULT_POWER_USER, ' or ', "hasRole('", "')");
        
        BasicAuthenticationEntryPoint entryPoint = new BasicAuthenticationEntryPoint()
        entryPoint.setRealmName("CID")
        
        if (env.acceptsProfiles("dev")) {
            LOGGER.info("II Configuring Development Security Settings");
            http.authorizeRequests()
                    .antMatchers("/swagger-ui.html", "/webjars/**", "/api-docs", "/swagger-resources", "/configuration/**").permitAll()
        } else {
            http.authorizeRequests()
                    .antMatchers("/swagger-ui.html", "/webjars/**", "/api-docs", "/swagger-resources", "/configuration/**").access(defaultPowerUsers)
        }
        
        http.authorizeRequests()
                .antMatchers(HttpMethod.GET, "/user").authenticated()
                //.antMatchers(HttpMethod.GET, "/api/academic-years/**").permitAll()
                //.antMatchers(HttpMethod.GET, "/**").permitAll()
                //.anyRequest().authenticated()
                .and()
                .httpBasic()
                .and()
                .logout().permitAll()
                .and()
                .csrf().disable()
    }
    
    private CsrfTokenRepository csrfTokenRepository() {
        HttpSessionCsrfTokenRepository repository = new HttpSessionCsrfTokenRepository();
        repository.setHeaderName("X-XSRF-TOKEN");
        return repository;
    }
}
