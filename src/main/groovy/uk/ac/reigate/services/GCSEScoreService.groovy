package uk.ac.reigate.services

//import static org.springframework.util.Assert

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

import uk.ac.reigate.domain.academic.GCSEScore
import uk.ac.reigate.exceptions.InvalidOperationException
import uk.ac.reigate.model.PageInfo
import uk.ac.reigate.model.SearchResult
import uk.ac.reigate.repositories.academic.GCSEScoreRepository
import uk.ac.reigate.utils.ValidationUtils;

@Service
class GCSEScoreService implements ICoreDataService<GCSEScore, Integer>{
    
    @Autowired
    GCSEScoreRepository gCSEScoreRepository
    
    /**
     * Default NoArgs constructor
     */
    GCSEScoreService() {}
    
    /**
     * Autowired Constructor
     *
     * @param gCSEScoreRepository
     */
    GCSEScoreService(GCSEScoreRepository gCSEScoreRepository) {
        this.gCSEScoreRepository = gCSEScoreRepository
    }
    
    /**
     * Find an individual gCSEScore using the gCSEScores ID fields
     *
     * @param id the ID fields to search for
     * @return the GCSEScore object that matches the ID supplied, or null if not found
     */
    @Transactional(readOnly = true)
    GCSEScore findGCSEScore(Integer studentId) {
        return gCSEScoreRepository.findByStudentId(studentId);
    }
    
    /**
     * Find a single page of GCSEScore objects
     * @return a SearchResult set with the list of GCSEScores
     */
    @Transactional(readOnly = true)
    List<GCSEScore> findAll() {
        return gCSEScoreRepository.findAll();
        
    }
    
    /* (non-Javadoc)
     * @see uk.ac.reigate.services.ICoreDataService#findById(java.lang.Object)
     */
    @Override
    public GCSEScore findById(Integer id) {
        return gCSEScoreRepository.findOne(id)
    }
    
    /* (non-Javadoc)
     * @see uk.ac.reigate.services.ICoreDataService#save(java.lang.Object)
     */
    @Override
    public GCSEScore save(GCSEScore obj) {
        return gCSEScoreRepository.save(obj)
    }
    
    /* (non-Javadoc)
     * @see uk.ac.reigate.services.ICoreDataService#delete(java.lang.Object)
     */
    @Override
    public void delete(GCSEScore obj) {
        throw new InvalidOperationException("GCSEScore should not be deleted")
        
    }
    
    
}
