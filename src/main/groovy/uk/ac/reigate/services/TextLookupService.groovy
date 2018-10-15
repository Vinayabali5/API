package uk.ac.reigate.services

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service

import uk.ac.reigate.domain.lookup.TextLookup
import uk.ac.reigate.dto.TextLookUpDto
import uk.ac.reigate.exceptions.InvalidOperationException
import uk.ac.reigate.repositories.lookup.TextLookupRepository

@Service
class TextLookupService implements ICoreDataService<TextLookup, Integer>{
    
    @Autowired
    TextLookupRepository textLookupRepository
    
    /**
     * This method is used to find an instance of a TextLookup by its ID 
     */
    @Override
    public TextLookup findById(Integer id) {
        return textLookupRepository.findOne(id);
    }
    
    /**
     * This method is used to retrieve all instances of TextLookup
     */
    @Override
    public List<TextLookup> findAll() {
        return textLookupRepository.findAll();
    }
    
    /**
     * This method is used to save an instance of a TextLookup
     */
    @Override
    public TextLookup save(TextLookup obj) {
        return textLookupRepository.save(obj);
    }
    
    /**
     * This method is used to delete an instance of TextLookup. 
     * Note: this method is not support and will throw an exception if called.
     */
    @Override
    public void delete(TextLookup obj) {
        throw new InvalidOperationException("TextLookups should not be deleted.")
    }
    /**
     * This method is used to create a new instance of a TextLookup
     */
    TextLookup create(TextLookup textLookUp){
        return save(textLookUp)
    }
    
    /**
     * This methods is used to update an instance of a TextLookup. 
     */
    TextLookup updateTextLookUp(Integer id ,TextLookUpDto textLookUpDto){
        TextLookup textLookUp = findById(id)
        textLookUp.name = textLookUpDto.name
        textLookUp.text = textLookUpDto.text
        textLookUp.description = textLookUpDto.description
        return save(textLookUp)
    }
}
