package uk.ac.reigate.services

//import static org.springframework.util.Assert

import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.security.access.prepost.PreAuthorize
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

import uk.ac.reigate.domain.academic.Student
import uk.ac.reigate.exceptions.InvalidOperationException
import uk.ac.reigate.domain.academic.ExternalResultsArchive
import uk.ac.reigate.model.SearchResult;
import uk.ac.reigate.repositories.academic.ExternalResultsArchiveRepository
import uk.ac.reigate.repositories.academic.StudentRepository
import uk.ac.reigate.utils.ValidationUtils;

@Service
class ExternalResultsArchiveService implements ICoreDataService<ExternalResultsArchive, Integer>{
    
    @Autowired
    StudentRepository studentRepository
    
    @Autowired
    ExternalResultsArchiveRepository externalResultsArchiveRepository
    
    
    /**
     * Default NoArgs constructor
     */
    ExternalResultsArchiveService() {}
    
    /**
     * Autowired Constructor
     *
     * @param externalResultsArchiveRepository
     */
    ExternalResultsArchiveService(ExternalResultsArchiveRepository externalResultsArchiveRepository) {
        this.externalResultsArchiveRepository = externalResultsArchiveRepository;
    }
    /**
     * Find an individual ExternalResultsArchive using the ExternalResultsArchive ID fields
     *
     * @param id the ID fields to search for
     * @return the ExternalResultsArchive object that matches the ID supplied, or null if not found
     */
    @Override
    @Transactional(readOnly = true)
    ExternalResultsArchive findById(Integer id) {
        return externalResultsArchiveRepository.findOne(id);
    }
    
    /**
     * Find all ExternalResultsArchive
     *
     * @return a List of ExternalResultsArchive
     */
    @Override
    @Transactional(readOnly = true)
    List<ExternalResultsArchive> findAll() {
        return externalResultsArchiveRepository.findAll();
    }
    
    /**
     * This method is used to retrieve the student entry qualifications that a specific studentId and externalResultsArchiveId
     * @param studentId
     * @return
     */
    @PreAuthorize("@securityChecker.checkReader(authentication)")
    public  ExternalResultsArchive getByExternalResultsArchives(Integer studentId, Integer externalResultId){
        return externalResultsArchiveRepository.findByStudent_IdAndId(studentId, externalResultId);
    }
    
    /**
     * This method is used to retrieve the list of student entry qualifications that a specific studentId
     * @param studentId
     * @return
     */
    @PreAuthorize("@securityChecker.checkReader(authentication)")
    public  List<ExternalResultsArchive> getByStudent(Integer studentId){
        return externalResultsArchiveRepository.findByStudent_Id(studentId);
    }
    
    
    @Override
    public ExternalResultsArchive save(ExternalResultsArchive externalResultsArchive) {
        return externalResultsArchiveRepository.save(externalResultsArchive)
    }
    
    /**
     * This methods throws an InvalidOperationException when called. ExternalResultsArchive should not be deleted.
     */
    @Override
    public void delete(ExternalResultsArchive obj) {
        throw new InvalidOperationException("ExternalResultsArchive should not be deleted")
    }
    
    
}
