package uk.ac.reigate.services

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

import uk.ac.reigate.domain.ilr.AimType
import uk.ac.reigate.exceptions.InvalidOperationException
import uk.ac.reigate.repositories.ilr.AimTypeRepository
import uk.ac.reigate.utils.ValidationUtils

@Service
class AimTypeService implements ICoreDataService<AimType, Integer>{
    
    @Autowired
    AimTypeRepository aimTypeRepository
    
    /**
     * Default NoArgs constructor
     */
    AimTypeService() {}
    
    /**
     * Autowired Constructor
     *
     * @param aimTypeRepository
     */
    AimTypeService(AimTypeRepository aimTypeRepository) {
        this.aimTypeRepository = aimTypeRepository
    }
    
    /**
     * Find an individual aimType using the aimTypes ID fields
     *
     * @param id the ID fields to search for
     * @return the AimType object that matches the ID supplied, or null if not found
     */
    @Override
    @Transactional(readOnly = true)
    AimType findById(Integer id) {
        return aimTypeRepository.findOne(id)
    }
    
    /**
     * Find all aimTypes
     *
     * @return a SearchResult set with the list of AimTypes
     */
    @Override
    @Transactional(readOnly = true)
    List<AimType> findAll() {
        return aimTypeRepository.findAll()
    }
    
    /** This method saves to either existing object or creates a new object
     * @param id
     * @param code
     * @param description
     * @param shortDescription
     * @param validFrom
     * @param validTo
     * @return
     */
    @Transactional
    public AimType saveAimType(Integer id, String code, String description, String shortDescription,  Date validFrom, Date validTo) {
        ValidationUtils.assertNotBlank(code, "code cannot be blank")
        ValidationUtils.assertNotNull(description, "description is mandatory")
        
        AimType aimType = null
        
        if (id != null) {
            aimType = findById(id)
            aimType.setCode(code)
            aimType.setDescription(description)
            aimType.setShortDescription(shortDescription)
            aimType.setValidFrom(validFrom)
            aimType.setValidTo(validTo)
            save(aimType)
        } else {
            aimType = save(new AimType(code, description, shortDescription, validFrom, validTo))
        }
        
        return aimType;
    }
    
    /**
     * This service method is used to save a complete AimType object in the database
     *
     * @param aimTyper the new AimType object to be saved
     * @return the saved version of the AimType object
     */
    @Override
    @Transactional
    public AimType save(AimType aimType) {
        return aimTypeRepository.save(aimType)
    }
    
    /**
     * This service method is used to update an AimType object in the database from a partial or complete AimType object.
     *
     * @param aimType the partial or complete AimType object to be saved
     * @return the saved version of the AimType object
     */
    @Transactional
    public AimType updateAimType(AimType aimType) {
        AimType aimTypeToSave = findById(aimType.id)
        aimTypeToSave.code = aimType.code != null ? aimType.code : aimTypeToSave.code
        aimTypeToSave.description = aimType.description != null ? aimType.description : aimTypeToSave.description
        aimTypeToSave.shortDescription = aimType.shortDescription != null ? aimType.shortDescription : aimTypeToSave.shortDescription
        aimTypeToSave.validFrom = aimType.validFrom != null ? aimType.validFrom : aimTypeToSave.validFrom
        aimTypeToSave.validTo = aimType.validTo != null ? aimType.validTo : aimTypeToSave.validTo
        return save(aimTypeToSave)
    }
    
    /**
     * Saves a list of AimType objects to the database
     *
     * @param aimTypes a list of AimTypes to be saved to the database
     * @return the list of save AimType objects
     */
    @Transactional
    public List<AimType> saveAimTypes(List<AimType> aimTypes) {
        return aimTypes.collect { aimType -> save(aimType) }
    }
    
    
    /**
     * This methods throws an InvalidOperationException when called. AimType should not be deleted.
     */
    @Override
    @Transactional
    public void delete(AimType obj) {
        throw new InvalidOperationException("AimType should not be deleted.")
    }
}
