package uk.ac.reigate.services

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

import uk.ac.reigate.domain.lookup.SchoolType
import uk.ac.reigate.exceptions.InvalidOperationException
import uk.ac.reigate.repositories.lookup.SchoolTypeRepository
import uk.ac.reigate.utils.ValidationUtils

@Service
class SchoolTypeService implements ICoreDataService<SchoolType, Integer>{
    
    @Autowired
    SchoolTypeRepository schoolTypeRepository
    
    /**
     * Default NoArgs constructor
     */
    SchoolTypeService() {}
    
    /**
     * Autowired Constructor
     *
     * @param schoolTypeRepository
     */
    SchoolTypeService(SchoolTypeRepository schoolTypeRepository) {
        this.schoolTypeRepository = schoolTypeRepository;
    }
    
    /**
     * Find an individual schoolType using the schoolTypes ID fields
     *
     * @param id the ID fields to search for
     * @return the SchoolType object that matches the ID supplied, or null if not found
     */
    @Override
    @Transactional(readOnly = true)
    SchoolType findById(Integer id) {
        return schoolTypeRepository.findOne(id);
    }
    
    /**
     * Find a single page of SchoolType objects
     * @return a SearchResult set with the list of SchoolTypes
     */
    @Override
    @Transactional(readOnly = true)
    List<SchoolType> findAll() {
        return schoolTypeRepository.findAll();
    }
    
    /**
     *
     * @param id
     * @param code
     * @param description
     * @return
     */
    @Transactional
    public SchoolType saveSchoolType(Integer id, String code, String description) {
        ValidationUtils.assertNotBlank(code, "code cannot be blank");
        ValidationUtils.assertNotNull(description, "description is mandatory");
        
        SchoolType schoolType = null;
        
        if (id != null) {
            schoolType = findById(id);
            
            schoolType.setCode(code);
            schoolType.setDescription(description);
            
            save(schoolType);
        } else {
            schoolType = save(new SchoolType(code, description));
        }
        
        return schoolType;
    }
    
    /**
     * This service method is used to save a complete SchoolType object in the database
     *
     * @param schoolType the new SchoolType object to be saved
     * @return the saved version of the SchoolType object
     */
    @Override
    @Transactional
    public SchoolType save(SchoolType schoolType) {
        return schoolTypeRepository.save(schoolType)
    }
    
    /**
     * This service method is used to update an SchoolType object in the database from a partial or complete SchoolType object.
     *
     * @param schoolType the partial or complete SchoolType object to be saved
     * @return the saved version of the SchoolType object
     */
    @Transactional
    public SchoolType updateSchoolType(SchoolType schoolType) {
        SchoolType schoolTypeToSave = findById(schoolType.id)
        schoolTypeToSave.code = schoolType.code != null ? schoolType.code : schoolTypeToSave.code
        schoolTypeToSave.description = schoolType.description != null ? schoolType.description : schoolTypeToSave.description
        return save(schoolTypeToSave)
    }
    
    /**
     * Saves a list of SchoolType objects to the database
     *
     * @param schoolTypes a list of SchoolTypes to be saved to the database
     * @return the list of save SchoolType objects
     */
    @Transactional
    public List<SchoolType> saveSchoolTypes(List<SchoolType> schoolTypes) {
        return schoolTypes.collect { schoolType -> save(schoolType) };
    }
    
    /**
     * This methods throws an InvalidOperationException when called. SchoolType should not be deleted.
     */
    @Override
    public void delete(SchoolType obj) {
        throw new InvalidOperationException("SchoolType should not be deleted")
    }
}
