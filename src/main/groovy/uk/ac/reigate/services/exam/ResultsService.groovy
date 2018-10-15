package uk.ac.reigate.services.exam

import java.util.List

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.security.access.prepost.PreAuthorize
import org.springframework.stereotype.Service

import uk.ac.reigate.domain.exam.Results
import uk.ac.reigate.dto.exam.ResultsDto
import uk.ac.reigate.exceptions.InvalidOperationException
import uk.ac.reigate.repositories.exam.ResultsRepository
import uk.ac.reigate.services.ICoreDataService
@Service
class ResultsService implements ICoreDataService<Results, Integer> {
    
    @Autowired
    ResultsRepository resultsRepository
    
    
    /**
     * @param studentId - student id
     * @return List of Results of a given studentId
     */
    @PreAuthorize("@securityChecker.checkReader(authentication)")
    List <Results> getByStudentId(Integer studentId){
        resultsRepository.findByStudent_Id(studentId)
    }
    
    
    /* (non-Javadoc)
     * @see uk.ac.reigate.services.ICoreDataService#findById(java.lang.Object)
     */
    @Override
    public Results findById(Integer id) {
        return resultsRepository.findOne(id)
    }
    
    
    /* (non-Javadoc)
     * @see uk.ac.reigate.services.ICoreDataService#findAll()
     */
    @Override
    public List<Results> findAll() {
        return resultsRepository.findAll()
    }
    
    
    /* (non-Javadoc)
     * @see uk.ac.reigate.services.ICoreDataService#save(java.lang.Object)
     */
    @Override
    public Results save(Results result) {
        return resultsRepository.save(result)
    }
    
    
    /* (non-Javadoc)
     * @see uk.ac.reigate.services.ICoreDataService#delete(java.lang.Object)
     */
    @Override
    public void delete(Results result) {
        throw new InvalidOperationException("Result should not be deleted")
    }
    
    
    /** Saves score and grade to the existing Result object 
     * @param result
     * @param resultDto
     * @return
     */
    public Results saveResults(Results result, ResultsDto resultDto){
        result.setGrade(resultDto.grade)
        result.setScore(resultDto.score)
        return save(result)
    }
}
