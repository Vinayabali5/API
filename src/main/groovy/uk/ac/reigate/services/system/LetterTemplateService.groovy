package uk.ac.reigate.services.system

import java.util.List
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

import uk.ac.reigate.domain.system.LetterTemplate
import uk.ac.reigate.repositories.system.LetterTemplateRepository
import uk.ac.reigate.services.ICoreDataService

@Service
class LetterTemplateService implements ICoreDataService<LetterTemplate, Integer>{
    
    
    @Autowired
    LetterTemplateRepository letterTemplateRepository
    
    /**
     * Default NoArgs constructor
     */
    LetterTemplateService(){}
    
    /**
     * Autowired Constructor
     *
     * @param letterTemplateRepository
     */
    LetterTemplateService(LetterTemplateRepository letterTemplateRepository){
        this.letterTemplateRepository = letterTemplateRepository;
    }
    
    /**
     * Find an letterTemplate using the ID
     *
     * @param id the LetterTemplate id
     * @return the LetterTemplate object that matches the ID supplied, or null if not found
     */
    @Override
    @Transactional(readOnly = true)
    public LetterTemplate findById(Integer id) {
        return letterTemplateRepository.findOne(id);
    }
    
    @Override
    @Transactional(readOnly = true)
    public List<LetterTemplate> findAll() {
        return letterTemplateRepository.findAll()
    }
    
    @Override
    public LetterTemplate save(LetterTemplate letterTemplate) {
        return letterTemplateRepository.save(letterTemplate)
    }
    
    @Override
    //LetterTemplate cannot be deleted.
    public void delete(LetterTemplate obj) {
        
    }
    
    public List<LetterTemplate> findValidLetterTemplates(){
        return letterTemplateRepository.findValidTemplate()
    }
    
    
    
}
