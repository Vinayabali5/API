package uk.ac.reigate.services

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

import uk.ac.reigate.domain.ilr.ProgrammeType
import uk.ac.reigate.exceptions.InvalidOperationException
import uk.ac.reigate.repositories.ilr.ProgrammeTypeRepository
import uk.ac.reigate.utils.ValidationUtils

@Service
class ProgrammeTypeService implements ICoreDataService<ProgrammeType, Integer>{
    
    @Autowired
    ProgrammeTypeRepository programmeTypeRepository
    
    /**
     * Default NoArgs constructor
     */
    ProgrammeTypeService() {}
    
    /**
     * Autowired Constructor
     *
     * @param programmeTypeRepository
     */
    ProgrammeTypeService(ProgrammeTypeRepository programmeTypeRepository) {
        this.programmeTypeRepository = programmeTypeRepository;
    }
    
    /**
     * Find an individual programmeType using the programmeTypes ID fields
     *
     * @param id the ID fields to search for
     * @return the ProgrammeType object that matches the ID supplied, or null if not found
     */
    @Override
    @Transactional(readOnly = true)
    ProgrammeType findById(Integer id) {
        return programmeTypeRepository.findOne(id)
    }
    
    /**
     * Find all programmeTypes
     *
     * @return a SearchResult set with the list of ProgrammeTypes
     */
    @Override
    @Transactional(readOnly = true)
    List<ProgrammeType> findAll() {
        return programmeTypeRepository.findAll()
    }
    
    
    /**
     * @param id
     * @param code
     * @param description
     * @param shortDescription
     * @param validFrom
     * @param validTo
     * @return
     */
    @Transactional
    public ProgrammeType saveProgrammeType(Integer id, String code, String description, String shortDescription,  Date validFrom, Date validTo) {
        ValidationUtils.assertNotBlank(code, "code cannot be blank")
        ValidationUtils.assertNotNull(description, "description is mandatory")
        
        ProgrammeType programmeType = null
        
        if (id != null) {
            programmeType = findById(id)
            programmeType.setCode(code)
            programmeType.setDescription(description)
            programmeType.setShortDescription(shortDescription)
            programmeType.setValidFrom(validFrom)
            programmeType.setValidTo(validTo)
            
            
            save(programmeType)
        } else {
            programmeType = save(new ProgrammeType(code, description, shortDescription, validFrom, validTo))
        }
        
        return programmeType
    }
    
    /**
     * This service method is used to save a complete ProgrammeType object in the database
     *
     * @param programmeTyper the new ProgrammeType object to be saved
     * @return the saved version of the ProgrammeType object
     */
    @Override
    @Transactional
    public ProgrammeType save(ProgrammeType programmeType) {
        return programmeTypeRepository.save(programmeType)
    }
    
    /**
     * This service method is used to update an ProgrammeType object in the database from a partial or complete ProgrammeType object.
     *
     * @param programmeType the partial or complete ProgrammeType object to be saved
     * @return the saved version of the ProgrammeType object
     */
    
    @Transactional
    public ProgrammeType updateProgrammeType(ProgrammeType programmeType) {
        ProgrammeType programmeTypeToSave = findById(programmeType.id)
        programmeTypeToSave.code = programmeType.code != null ? programmeType.code : programmeTypeToSave.code
        programmeTypeToSave.description = programmeType.description != null ? programmeType.description : programmeTypeToSave.description
        programmeTypeToSave.shortDescription = programmeType.shortDescription != null ? programmeType.shortDescription : programmeTypeToSave.shortDescription
        programmeTypeToSave.validFrom = programmeType.validFrom != null ? programmeType.validFrom : programmeTypeToSave.validFrom
        programmeTypeToSave.validTo = programmeType.validTo != null ? programmeType.validTo : programmeTypeToSave.validTo
        return save(programmeTypeToSave)
    }
    
    /**
     * Saves a list of ProgrammeType objects to the database
     *
     * @param programmeTypes a list of ProgrammeTypes to be saved to the database
     * @return the list of save ProgrammeType objects
     */
    
    @Transactional
    public List<ProgrammeType> saveProgrammeTypes(List<ProgrammeType> programmeTypes) {
        return programmeTypes.collect { programmeType -> save(programmeType) }
    }
    
    /**
     * This methods throws an InvalidOperationException when called. ProgrammeType should not be deleted.
     */
    @Override
    public void delete(ProgrammeType obj) {
        throw new InvalidOperationException("ProgrammeType should not be deleted")
    }
}
