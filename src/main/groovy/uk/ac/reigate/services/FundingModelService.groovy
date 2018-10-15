package uk.ac.reigate.services

//import static org.springframework.util.Assert

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.security.access.prepost.PreAuthorize
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

import uk.ac.reigate.domain.ilr.FundingModel
import uk.ac.reigate.exceptions.InvalidOperationException
import uk.ac.reigate.model.SearchResult
import uk.ac.reigate.repositories.ilr.FundingModelRepository
import uk.ac.reigate.utils.ValidationUtils

@Service
class FundingModelService implements ICoreDataService<FundingModel, Integer>{
    
    @Autowired
    FundingModelRepository fundingModelRepository
    
    /**
     * Default NoArgs constructor
     */
    FundingModelService() {}
    
    /**
     * Autowired Constructor
     *
     * @param fundingModelRepository
     */
    FundingModelService(FundingModelRepository fundingModelRepository) {
        this.fundingModelRepository = fundingModelRepository;
    }
    
    /**
     * Find an individual fundingModel using the fundingModels ID fields
     *
     * @param id the ID fields to search for
     * @return the FundingModel object that matches the ID supplied, or null if not found
     */
    @Override
    @Transactional(readOnly = true)
    FundingModel findById(Integer id) {
        return fundingModelRepository.findOne(id)
    }
    
    /**
     * Find all fundingModels
     *
     * @return a SearchResult set with the list of FundingModels
     */
    @Override
    @Transactional(readOnly = true)
    List<FundingModel> findAll() {
        return fundingModelRepository.findAll()
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
    public FundingModel saveFundingModel(Integer id, String code, String description, String shortDescription,  Date validFrom, Date validTo) {
        ValidationUtils.assertNotBlank(code, "code cannot be blank")
        ValidationUtils.assertNotNull(description, "description is mandatory")
        
        FundingModel fundingModel = null
        
        if (id != null) {
            fundingModel = findById(id)
            fundingModel.setCode(code)
            fundingModel.setDescription(description)
            fundingModel.setShortDescription(shortDescription)
            fundingModel.setValidFrom(validFrom)
            fundingModel.setValidTo(validTo)
            
            save(fundingModel)
        } else {
            fundingModel = save(new FundingModel(code, description, shortDescription, validFrom, validTo))
        }
        
        return fundingModel
    }
    
    /**
     * This service method is used to save a complete FundingModel object in the database
     *
     * @param fundingModel the new FundingModel object to be saved
     * @return the saved version of the FundingModel object
     */
    @Override
    @Transactional
    public FundingModel save(FundingModel fundingModel) {
        return fundingModelRepository.save(fundingModel)
    }
    
    /**
     * This service method is used to update an FundingModel object in the database from a partial or complete FundingModel object.
     *
     * @param fundingModel the partial or complete FundingModel object to be saved
     * @return the saved version of the FundingModel object
     */
    @Transactional
    public FundingModel updateFundingModel(FundingModel fundingModel) {
        FundingModel fundingModelToSave = findById(fundingModel.id)
        fundingModelToSave.code = fundingModel.code != null ? fundingModel.code : fundingModelToSave.code
        fundingModelToSave.description = fundingModel.description != null ? fundingModel.description : fundingModelToSave.description
        fundingModelToSave.shortDescription = fundingModel.shortDescription != null ? fundingModel.shortDescription : fundingModelToSave.shortDescription
        fundingModelToSave.validFrom = fundingModel.validFrom != null ? fundingModel.validFrom : fundingModelToSave.validFrom
        fundingModelToSave.validTo = fundingModel.validTo != null ? fundingModel.validTo : fundingModelToSave.validTo
        
        return save(fundingModelToSave)
    }
    
    /**
     * Saves a list of FundingModel objects to the database
     *
     * @param fundingModels a list of FundingModels to be saved to the database
     * @return the list of save FundingModel objects
     */
    @Transactional
    public List<FundingModel> saveFundingModels(List<FundingModel> fundingModels) {
        return fundingModels.collect { fundingModel -> save(fundingModel) }
    }
    
    /**
     * This methods throws an InvalidOperationException when called. FundingModel should not be deleted.
     */
    @Override
    public void delete(FundingModel obj) {
        throw new InvalidOperationException("FundingModel should not be deleted")
        
    }
    
    
    
}
