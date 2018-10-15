package uk.ac.reigate.services

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.data.domain.Page
import org.springframework.data.domain.PageRequest
import org.springframework.data.domain.Sort
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

import uk.ac.reigate.domain.lookup.Subject
import uk.ac.reigate.exceptions.InvalidOperationException
import uk.ac.reigate.model.PageInfo
import uk.ac.reigate.model.SearchResult
import uk.ac.reigate.repositories.lookup.SubjectRepository
import uk.ac.reigate.utils.ValidationUtils

@Service
class SubjectService implements ICoreDataService<Subject, Integer>{
    
    private static String DEFAULT_SORT_FIELD = 'code'
    
    private static Sort.Direction DEFAULT_SORT_ORDER = Sort.Direction.ASC;
    
    @Autowired
    SubjectRepository subjectRepository
    
    /**
     * Default NoArgs constructor
     */
    SubjectService() {}
    
    /**
     * Autowired Constructor
     *
     * @param subjectRepository
     */
    SubjectService(SubjectRepository subjectRepository) {
        this.subjectRepository = subjectRepository;
    }
    
    /**
     * Find an individual subject using the subjects ID fields
     * 
     * @param id the ID fields to search for 
     * @return the Subject object that matches the ID supplied, or null if not found
     */
    @Override
    @Transactional(readOnly = true)
    Subject findById(Integer id) {
        return subjectRepository.findOne(id);
    }
    
    /**
     * Find all subjects 
     * 
     * @return a List of Subjects
     */
    @Override
    @Transactional(readOnly = true)
    List<Subject> findAll() {
        return subjectRepository.findAll();
    }
    
    /**
     * Find a single page of Subject objects  
     * 
     * @param page the page number to retrieve 
     * @param size the number of records on each page
     * @return a SearchResult set with the list of Subjects
     */
    @Transactional(readOnly = true)
    SearchResult<Subject> findSubjects(int page, int size) {
        PageRequest pageRequest = new PageRequest(page, size) //, new Sort(DEFAULT_SORT_ORDER, DEFAULT_SORT_FIELD))
        Page<Subject> subjects = subjectRepository.findAll(pageRequest);
        PageInfo pageInfo = new PageInfo(page, size, '', (Integer) subjects.total)
        return new SearchResult<>(pageInfo, subjects.toList());
    }
    
    /**
     * 
     * @param id
     * @param code
     * @param description
     * @return
     */
    @Transactional
    public Subject saveSubject(Integer id, String code, String description) {
        ValidationUtils.assertNotBlank(code, "code cannot be blank");
        ValidationUtils.assertNotNull(description, "description is mandatory");
        
        Subject subject = null;
        
        if (id != null) {
            subject = findById(id);
            
            subject.setCode(code);
            subject.setDescription(description);
            
            save(subject);
        } else {
            subject = save(new Subject(code, description));
        }
        
        return subject;
    }
    
    /**
     * Saves a Subject object to the database 
     * 
     * @param subject the Subject object to save
     * @return the save Subject object
     */
    @Override
    @Transactional
    public Subject save(Subject subject) {
        return subjectRepository.save(subject)
    }
    
    /**
     * This service method is used to update an Subject object in the database from a partial or complete Subject object.
     *
     * @param subject the partial or complete Subject object to be saved
     * @return the saved version of the Subject object
     */
    @Transactional
    public Subject updateSubject(Subject subject) {
        Subject subjectToSave = findById(subject.id)
        subjectToSave.code = subject.code != null ? subject.code : subjectToSave.code
        subjectToSave.description = subject.description != null ? subject.description : subjectToSave.description
        return save(subjectToSave)
    }
    
    /**
     * Saves a list of Subject objects to the database
     *  
     * @param subjects a list of Subjects to be saved to the database
     * @return the list of save Subject objects
     */
    @Transactional
    public List<Subject> saveSubjects(List<Subject> subjects) {
        return subjects.collect { subject -> save(subject) };
    }
    
    /**
     * This methods throws an InvalidOperationException when called. Subject should not be deleted.
     */
    @Override
    public void delete(Subject obj) {
        throw new InvalidOperationException("Subject should not be deleted")
    }
}
