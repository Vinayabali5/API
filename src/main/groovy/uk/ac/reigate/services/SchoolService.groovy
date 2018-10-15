package uk.ac.reigate.services

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

import uk.ac.reigate.domain.academic.School
import uk.ac.reigate.domain.lookup.SchoolPriority
import uk.ac.reigate.domain.lookup.SchoolType
import uk.ac.reigate.exceptions.InvalidOperationException
import uk.ac.reigate.repositories.SchoolRepository
import uk.ac.reigate.utils.ValidationUtils


@Service
class SchoolService implements ICoreDataService<School, Integer>{
    
    @Autowired
    SchoolRepository schoolRepository
    
    /**
     * Default NoArgs constructor
     */
    SchoolService() {}
    
    /**
     * Autowired Constructor
     *
     * @param schoolRepository
     */
    SchoolService(SchoolRepository schoolRepository) {
        this.schoolRepository = schoolRepository;
    }
    
    /**
     * Find an individual school using the schools ID fields
     *
     * @param id the ID fields to search for
     * @return the School object that matches the ID supplied, or null if not found
     */
    @Override
    @Transactional(readOnly = true)
    School findById(Integer id) {
        return schoolRepository.findOne(id);
    }
    
    /**
     * Find all schools
     *
     * @return a List<School> set with the list of Schools
     */
    @Override
    @Transactional(readOnly = true)
    List<School> findAll() {
        return schoolRepository.findAll();
    }
    
    
    /**
     * @param id
     * @param name
     * @param type
     * @param priority
     * @param urn
     * @param line1
     * @param line2
     * @param line3
     * @param postcode
     * @return
     */
    @Transactional
    public School saveSchool(Integer id, String name, SchoolType type, SchoolPriority priority, String urn, String line1, String line2, String line3, String postcode) {
        ValidationUtils.assertNotBlank(name, "name cannot be blank");
        ValidationUtils.assertNotBlank(type, "type cannot be blank");
        ValidationUtils.assertNotBlank(priority, "priority cannot be blank");
        ValidationUtils.assertNotBlank(urn, "urn is mandatory");
        ValidationUtils.assertNotBlank(line1, "line1 is mandatory");
        ValidationUtils.assertNotBlank(line2, "line2 is mandatory");
        ValidationUtils.assertNotBlank(line3, "line3 is mandatory");
        ValidationUtils.assertNotBlank(postcode, "postcode is mandatory");
        
        School school = null;
        if (id != null) {
            school = findById(id);
            school.setName(name);
            school.setType(type);
            school.setPriority(priority)
            school.setUrn(urn);
            school.setLine1(line1);
            school.setLine2(line2);
            school.setLine3(line3);
            school.setPostcode(postcode);
            
            save(school);
        } else {
            school = save(new School(name, type, priority, urn, line1, line2, line3, postcode));
        }
        return school;
    }
    
    /**
     * This service method is used to save a complete School object in the database
     *
     * @param school the new School object to be saved
     * @return the saved version of the School object
     */
    @Override
    @Transactional
    public School save(School school) {
        return schoolRepository.save(school)
    }
    
    /**
     * Saves a list of School objects to the database
     *
     * @param schools a list of Schools to be saved to the database
     * @return the list of save School objects
     */
    @Transactional
    public List<School> saveSchools(List<School> schools) {
        return schools.collect { school -> save( school ) };
    }
    
    /**
     * This methods throws an InvalidOperationException when called. QualificationAssessment should not be deleted.
     */
    @Override
    public void delete(School obj) {
        throw new InvalidOperationException("School should not be deleted")
    }
    
    /**
     * This method is used to retrieve a School object from the schools name.
     * 
     * @param name The name of the school to search for.
     * @return A School object matching the name supplied.
     */
    public School findByName(String name) {
        return schoolRepository.findByName(name)
    }
    
    /**
     * This method is used to retrieve a school by it URN (Unique Reference Number).
     * 
     * @param urn The URN to use to perform the search.
     * @return A School object that match the URN supplied.
     */
    public School findByUkprn(String urn) {
        return schoolRepository.findByUrn(ukprn)
    }
}
