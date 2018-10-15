package uk.ac.reigate.services;

import org.springframework.security.access.prepost.PreAuthorize
import org.springframework.transaction.annotation.Transactional

import uk.ac.reigate.domain.academic.AcademicYear

public interface IAnnualDataService<T, ID> extends ICoreDataService<T, ID> {
    
    /**
     * This methods is used to retrieve all instances of the data object.
     *
     * Secured using @securityChecker.checkReader(authentication)
     */
    @PreAuthorize("@securityChecker.checkReader(authentication)")
    @Transactional(readOnly = true)
    List<T> findAllByYear(AcademicYear year);
}
