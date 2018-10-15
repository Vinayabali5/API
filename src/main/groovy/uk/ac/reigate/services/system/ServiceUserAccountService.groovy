package uk.ac.reigate.services.system

import org.springframework.beans.factory.annotation.Value
import org.springframework.stereotype.Service

import jcifs.smb.NtlmPasswordAuthentication

/**
 * This service object is used to retrieve and store information about the systems Service User Account  
 * 
 * @author Michael Horgan
 *
 */
@Service
class ServiceUserAccountService {
    
    @Value("\${cid.serviceUser.domainName}")
    private String domainName
    
    @Value("\${cid.serviceUser.username}")
    private String username
    
    @Value("\${cid.serviceUser.password}")
    private String password
    
    NtlmPasswordAuthentication getNtlmPasswordAuthentication() {
        return new NtlmPasswordAuthentication(domainName, username, password);
    }
}
