package uk.ac.reigate.services

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

import uk.ac.reigate.domain.academic.Block
import uk.ac.reigate.exceptions.InvalidOperationException
import uk.ac.reigate.repositories.academic.BlockRepository
import uk.ac.reigate.utils.ValidationUtils

@Service
class BlockService implements ICoreDataService<Block, Integer> {
    
    @Autowired
    BlockRepository blockRepository
    
    /**
     * Default NoArgs constructor
     */
    BlockService() {}
    
    /**
     * Autowired Constructor
     *
     * @param blockRepository
     */
    BlockService(BlockRepository blockRepository) {
        this.blockRepository = blockRepository
    }
    
    /**
     * Find an individual block using the blocks ID fields
     *
     * @param id the ID fields to search for
     * @return the Block object that matches the ID supplied, or null if not found
     */
    @Override
    @Transactional(readOnly = true)
    Block findById(Integer id) {
        return blockRepository.findOne(id);
    }
    
    /**
     * Find a single page of Block objects
     * @return a SearchResult set with the list of Blocks
     */
    @Override
    @Transactional(readOnly = true)
    List<Block> findAll() {
        return blockRepository.findAll();
    }
    
    /** This method saves to either existing object or creates a new object
     *
     * @param id
     * @param code
     * @param description
     * @return
     */
    
    @Transactional
    public Block saveBlock(Integer id, String code, String description, String htmlColour) {
        ValidationUtils.assertNotBlank(code, "code cannot be blank");
        ValidationUtils.assertNotNull(description, "description is mandatory");
        Block block = null;
        if (id != null) {
            block = findById(id);
            block.setCode(code);
            block.setDescription(description);
            block.setHtmlColour(htmlColour)
            save(block);
        } else {
            block = save(new Block(code, description, htmlColour));
        }
        return block;
    }
    
    /**
     * This service method is used to save a complete Block object in the database
     *
     * @param block the new Block object to be saved
     * @return the saved version of the Block object
     */
    @Override
    @Transactional
    public Block save(Block block) {
        return blockRepository.save(block)
    }
    
    /**
     * This service method is used to update an Block object in the database from a partial or complete Block object.
     *
     * @param block the partial or complete Block object to be saved
     * @return the saved version of the Block object
     */
    @Transactional
    public Block updateBlock(Block block) {
        Block blockToSave = findById(block.id)
        blockToSave.code = block.code != null ? block.code : blockToSave.code
        blockToSave.description = block.description != null ? block.description : blockToSave.description
        blockToSave.htmlColour = block.htmlColour != null ? block.htmlColour : blockToSave.htmlColour
        return save(blockToSave)
    }
    
    /**
     * Saves a list of Block objects to the database
     *
     * @param blocks a list of Blocks to be saved to the database
     * @return the list of save Block objects
     */
    @Transactional
    public List<Block> saveBlocks(List<Block> blocks) {
        return blocks.collect { block -> save(block) };
    }
    
    /**
     * This methods throws an InvalidOperationException when called. Block should not be deleted.
     */
    @Override
    public void delete(Block obj) {
        throw new InvalidOperationException("Block should not be deleted.")
    }
}
