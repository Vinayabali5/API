package uk.ac.reigate.services

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

import uk.ac.reigate.domain.ilp.ILPInterview
import uk.ac.reigate.domain.ilp.Target
import uk.ac.reigate.exceptions.InvalidOperationException
import uk.ac.reigate.repositories.ilp.TargetRepository
import uk.ac.reigate.utils.ValidationUtils

@Service
class TargetService implements ICoreDataService<Target, Integer>{
    
    @Autowired
    TargetRepository targetRepository
    
    /**
     * Default NoArgs constructor    
     */
    TargetService() {}
    
    /**
     * Autowired constructor
     * 
     * @param targetRepository
     */
    TargetService(TargetRepository targetRepository) {
        this.targetRepository = targetRepository
    }
    
    /**
     * Find an individual target using the targets ID fields
     *
     * @param id the ID fields to search for
     * @return the Target object that matches the ID supplied, or null if not found
     */
    @Override
    @Transactional(readOnly = true)
    Target findById(Integer id) {
        return targetRepository.findOne(id);
    }
    
    /**
     * Find all targets
     *
     * @return a List of Targets
     */
    @Override
    @Transactional(readOnly = true)
    List<Target> findAll() {
        List<Target> targets = targetRepository.findAll();
        return targets
    }
    
    
    /**
     * @param id
     * @param target
     * @param byWhen
     * @param interview
     * @param sendLetter
     * @return
     */
    @Transactional
    public Target saveTarget(Integer id, String target, String byWhen, ILPInterview interview, Boolean sendLetter) {
        ValidationUtils.assertNotNull(target, "target is mandatory");
        
        Target targett = null;
        
        if (id != null) {
            target = findById(id);
            
            targett.setTarget(target);
            targett.setByWhen(byWhen);
            targett.setInterview(interview);
            targett.setSendLetter(sendLetter);
            
            save(targett);
        } else {
            targett = save(new Target(target, byWhen, interview, sendLetter));
        }
        
        return targett;
    }
    
    /**
     * This service method is used to save a complete Target object in the database
     *
     * @param target the new Target object to be saved
     * @return the saved version of the Target object
     */
    @Override
    @Transactional
    public Target save(Target target) {
        return targetRepository.save(target)
    }
    
    /**
     * Saves a list of Target objects to the database
     *
     * @param targets a list of Targets to be saved to the database
     * @return the list of save Target objects
     */
    @Transactional
    public List<Target> saveTargets(List<Target> targets) {
        return targets.collect { target -> save(target) };
    }
    
    /**
     * This methods throws an InvalidOperationException when called. Target should not be deleted.
     */
    @Override
    public void delete(Target obj) {
        throw new InvalidOperationException("Target should not be deleted")
    }
}
