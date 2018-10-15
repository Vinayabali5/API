package uk.ac.reigate

import org.apache.log4j.Logger
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.SpringApplication
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.builder.SpringApplicationBuilder
import org.springframework.boot.system.ApplicationPidFileWriter
import org.springframework.boot.system.EmbeddedServerPortFileWriter
import org.springframework.boot.web.support.SpringBootServletInitializer
import org.springframework.context.annotation.ComponentScan
import org.springframework.core.env.Environment


@SpringBootApplication
@ComponentScan(value="uk.ac.reigate")
class ApiApplication extends SpringBootServletInitializer {
    
    private static final Logger LOGGER = Logger.getLogger(ApiApplication.class);
    
    @Autowired
    private Environment env;
    
    static main(args) {
        SpringApplication ctx = new SpringApplication(ApiApplication.class)
        ctx.addListeners(new ApplicationPidFileWriter());
        ctx.addListeners(new EmbeddedServerPortFileWriter());
        Environment env = ctx.run(args).getEnvironment();
        
        String activeProfile = env.getProperty("spring.profiles.active") != null ? env.getProperty("spring.profiles.active") : 'none selected'
        String port = env.getProperty("server.port")
        String servletPath = env.getProperty("server.servletPath") != null ? env.getProperty("server.servletPath") : '/'
        String contextPath = env.getProperty("server.contextPath") != null ? env.getProperty("server.contextPath") : '/'
        
        LOGGER.info("\n" +
                "----------------------------------------------------------------------------------------------------------\n" +
                "Application '" + env.getProperty("app.name") + "' is running! \n" +
                "----------------------------------------------------------------------------------------------------------\n" +
                "Profile:\n" +
                "Run Profile: \t\t" + env.getProperty("spring.profiles.active") + "\n" +
                "Active Profiles: \t" + env.getActiveProfiles() + "\n" +
                "----------------------------------------------------------------------------------------------------------\n" +
                "Access URLs:\n" +
                "Local:\t\t\thttp://127.0.0.1:" + port + contextPath + "\n" +
                "External:\t\thttp://" + InetAddress.getLocalHost().getHostAddress() + ":" + port + contextPath + "\n" +
                "----------------------------------------------------------------------------------------------------------\n" +
                "Database:\n" +
                "Database URL:\t\t" + env.getProperty("spring.datasource.url") + "\n" +
                "Show SQL:\t\t" + env.getProperty("spring.jpa.show_sql") + "\n" +
                "----------------------------------------------------------------------------------------------------------"
                )
    }
    
    
    @Override
    protected SpringApplicationBuilder configure(SpringApplicationBuilder application) {
        return application.sources(applicationClass);
    }
    
    private static Class<ApiApplication> applicationClass = ApiApplication.class;
}
