package uk.ac.reigate.services

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

import uk.ac.reigate.domain.Staff
import uk.ac.reigate.domain.academic.Faculty
import uk.ac.reigate.exceptions.InvalidOperationException
import uk.ac.reigate.repositories.academic.FacultyRepository
import uk.ac.reigate.utils.ValidationUtils

@Service
class FacultyService implements ICoreDataService<Faculty, Integer> {
    
    @Autowired
    FacultyRepository facultyRepository
    
    /**
     * Default NoArgs constructor
     */
    FacultyService() {}
    
    /**
     * Autowired Constructor
     *
     * @param facultyRepository
     */
    FacultyService(FacultyRepository facultyRepository) {
        this.facultyRepository = facultyRepository;
    }
    
    /**
     * Find an individual faculty using the faculties ID fields
     *
     * @param id the ID fields to search for
     * @return the Faculty object that matches the ID supplied, or null if not found
     */
    @Override
    @Transactional(readOnly = true)
    Faculty findById(Integer id) {
        return facultyRepository.findOne(id);
    }
    
    /**
     * Find all faculties
     *
     * @return a SearchResult set with the list of Faculties
     */
    @Override
    @Transactional(readOnly = true)
    List<Faculty> findAll() {
        return facultyRepository.findAll();
    }
    
    /**
     * @param id
     * @param code
     * @param description
     * @param hof
     * @param dol
     * @param adol
     * @param pd
     * @param apd
     * @return
     */
    @Transactional
    public Faculty saveFaculty(Integer id, String code, String description, Staff hof, Staff dol, Staff adol, Staff pd, Staff apd) {
        ValidationUtils.assertNotBlank(code, "code cannot be blank");
        ValidationUtils.assertNotNull(description, "description is mandatory");
        
        Faculty faculty = null;
        
        if (id != null) {
            faculty = findById(id);
            
            faculty.setCode(code);
            faculty.setDescription(description);
            faculty.setHof(hof);
            faculty.setDol(dol);
            faculty.setAdol(adol);
            faculty.setPd(pd);
            faculty.setApd(apd);
            
            save(faculty);
        } else {
            faculty = save(new Faculty(code, description, hof, dol, adol, pd, apd));
        }
        
        return faculty;
    }
    
    /**
     * This service method is used to save a complete Faculty object in the database
     *
     * @param faculty the new Faculty object to be saved
     * @return the saved version of the Faculty object
     */
    @Override
    @Transactional
    public Faculty save(Faculty faculty) {
        return facultyRepository.save(faculty)
    }
    
    /**
     * Saves a list of Faculty objects to the database
     *
     * @param faculties a list of Faculties to be saved to the database
     * @return the list of save Faculty objects
     */
    @Transactional
    public List<Faculty> saveFaculties(List<Faculty> faculties) {
        return faculties.collect { faculty -> save(faculty) };
    }
    
    /**
     * This methods throws an InvalidOperationException when called. Faculty should not be deleted.
     */
    @Override
    public void delete(Faculty obj) {
        throw new InvalidOperationException("Faculty should not be deleted")
    }
}
