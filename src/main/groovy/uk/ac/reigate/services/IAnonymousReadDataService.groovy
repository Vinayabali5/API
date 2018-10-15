package uk.ac.reigate.services;

import org.springframework.security.access.annotation.Secured
import org.springframework.security.access.prepost.PreAuthorize
import org.springframework.transaction.annotation.Transactional

/**
 * This interface is used to ensure that services used for retrieving data have a standard set of methods, 
 * it is also used to ensure that basic security is applied to those methods. To change the security settings
 * on any of the methods you need to override the @PreAuthorize annotation.
 * 
 * @author Michael Horgan
 *
 * @param <T> the data object to be retrieved by the service
 * @param <ID> the ID type of the data object
 */
@Secured(['IS_AUTHENTICATED_ANONYMOUSLY'])
public interface IAnonymousReadDataService<T, ID> {
    
    /**
     * This method is used to retrieve an individual instance of the data object using the ID provided.
     * 
     * Secured using @securityChecker.checkReader(authentication)
     */
    @Transactional(readOnly = true)
    T findById(ID id);
    
    /**
     * This methods is used to retrieve all instances of the data object.
     * 
     * Secured using @securityChecker.checkReader(authentication)
     */
    @Transactional(readOnly = true)
    List<T> findAll();
    
    /**
     * This method is used to save an instance of the data object.
     * 
     * Secured using @securityChecker.checkWriter(authentication)
     */
    @PreAuthorize("@securityChecker.checkWriter(authentication)")
    @Transactional
    T save(T obj);
    
    /**
     * This methods is used to delete an instance of the data object.
     * 
     * Secured using @securityChecker.checkDeleter(authentication)
     */
    @PreAuthorize("@securityChecker.checkDeleter(authentication)")
    @Transactional
    void delete(T obj);
}
