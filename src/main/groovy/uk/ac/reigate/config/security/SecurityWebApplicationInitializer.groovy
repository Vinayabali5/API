package uk.ac.reigate.config.security;

import org.springframework.context.annotation.Profile
import org.springframework.core.annotation.Order
import org.springframework.security.web.context.AbstractSecurityWebApplicationInitializer

/**
 * Sets up the Spring Security filter chain
 *
 */
@Profile('secured')
@Order(2)
public class SecurityWebApplicationInitializer extends AbstractSecurityWebApplicationInitializer {
}
