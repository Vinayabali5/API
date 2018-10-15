package uk.ac.reigate.config.security

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.context.annotation.Configuration
import org.springframework.web.servlet.config.annotation.InterceptorRegistry
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter

import uk.ac.reigate.security.AuditInterceptor

@Configuration
class AuditConfiguration extends WebMvcConfigurerAdapter {
    
    @Autowired
    private AuditInterceptor auditInterceptor;
    
    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(auditInterceptor).addPathPatterns("/api/**");
    }
}
