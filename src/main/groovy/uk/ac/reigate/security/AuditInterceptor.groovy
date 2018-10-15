package uk.ac.reigate.security

import java.util.logging.Logger

import javax.servlet.http.HttpServletRequest
import javax.servlet.http.HttpServletResponse

import org.springframework.security.core.Authentication
import org.springframework.security.core.context.SecurityContext
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.security.core.userdetails.UserDetails
import org.springframework.stereotype.Component
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter

@Component
class AuditInterceptor extends HandlerInterceptorAdapter {
    
    private final static Logger LOGGER = Logger.getLogger("Audit Log");
    
    // TODO: Add a audit service to allow the saving of the audit log to the database
    
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) {
        String uri = request.requestURI
        String method = request.method
        String content = request.queryString
        Map<String, String[]> paramaters = request.parameterMap
        if (method == 'POST' || method == 'PUT') {
            SecurityContext context = SecurityContextHolder.getContext()
            Authentication auth = context.getAuthentication()
            UserDetails user = (UserDetails) auth.getPrincipal();
            LOGGER.info(user.username + ': ' + method + ' ' + uri)
        }
        return true;
    }
}
