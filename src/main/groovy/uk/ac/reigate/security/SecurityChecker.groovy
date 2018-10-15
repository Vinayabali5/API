package uk.ac.reigate.security

import java.util.List;

import org.apache.log4j.Logger
import org.springframework.security.core.Authentication
import org.springframework.stereotype.Component

import uk.ac.reigate.config.security.SecurityConfiguration

@Component
class SecurityChecker {
    
    private static final Logger LOGGER = Logger.getLogger(SecurityChecker.class);
    
    /**
     * The default list of user roles that are allowed to read data from the service layer and API endpoints.
     */
    static final List<String> DEFAULT_READER_ROLES = ["ROLE_Staff"];
    
    /**
     * The default list of user roles that are allowed to write data to the service layer and API endpoints.
     */
    static final List<String> DEFAULT_WRITER_ROLES = [
        "ROLE_Core Data",
        "ROLE_Office Administration",
        "ROLE_Enrolment Manager",
        "ROLE_Administration",
        "ROLE_Student Services",
        "ROLE_Exams Officer"
    ];
    
    /**
     * The default list of user roles that are allowed to delete data to the service layer and API endpoints.
     */
    static final List<String> DEFAULT_DELETER_ROLES = [
        "ROLE_Core Data",
        "ROLE_Office Administration",
        "ROLE_Administration",
        "ROLE_Student Services"
    ];
    
    /**
     * The default list of user roles that are power users.
     */
    static final List<String> DEFAULT_POWER_USER_ROLES = ["ROLE_Core Data"];
    
    /**
     * The specific list of user roles that are allowed to read and write to the enrolments service layers.
     */
    static final List<String> ENROLMENTS_READ_WRITE = [
        "ROLE_Office Administration",
        "ROLE_Enrolment Manager"
    ];
    
    /**
     * The specific list of user roles that are allowed to read the exams services layers.
     */
    static final List<String> EXAMS_READ_ONLY = ["ROLE_Exams Assistant"];
    
    /**
     * The specific list of user roles that are allowed to read and write to the exams services layers.
     */
    static final List<String> EXAMS_READ_WRITE = ["ROLE_Exams Officer"];
    
    public boolean checkReader(Authentication authentication) {
        return authentication.authorities.any {
            DEFAULT_READER_ROLES.contains(it.authority)
        }
    }
    
    public boolean checkWriter(Authentication authentication) {
        return authentication.authorities.any {
            DEFAULT_WRITER_ROLES.contains(it.authority)
        }
    }
    
    public boolean checkDeleter(Authentication authentication) {
        return authentication.authorities.any {
            DEFAULT_DELETER_ROLES.contains(it.authority)
        }
    }
    
    public boolean checkPowerUser(Authentication authentication) {
        return authentication.authorities.any {
            DEFAULT_POWER_USER_ROLES.contains(it.authority)
        }
    }
    
    public boolean checkEnrolmentsReadWrite(Authentication authentication) {
        return authentication.authorities.any {
            ENROLMENTS_READ_WRITE.contains(it.authority)
        }
    }
    
    public boolean checkExamsReadOnly(Authentication authentication) {
        return authentication.authorities.any {
            EXAMS_READ_ONLY.contains(it.authority)
        }
    }
    
    public boolean checkExamsReadWrite(Authentication authentication) {
        return authentication.authorities.any {
            EXAMS_READ_WRITE.contains(it.authority)
        }
    }
}
