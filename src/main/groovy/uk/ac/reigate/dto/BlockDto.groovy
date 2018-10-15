package uk.ac.reigate.dto;


import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;

import groovy.transform.EqualsAndHashCode;
import uk.ac.reigate.domain.academic.Block

/**
 *
 * JSON serializable DTO containing Block data
 *
 */
@JsonSerialize
@EqualsAndHashCode(includeFields=true)
public class BlockDto implements Serializable {
    
    @JsonProperty
    private Integer id;
    
    @JsonProperty
    private String code;
    
    @JsonProperty
    private String description;
    
    @JsonProperty
    private String htmlColour
    
    /**
     * Default No Args constructor
     */
    public BlockDto() {
    }
    
    /**
     * Constructor to create a BlockDto object
     *
     * @param id the Id for the Block
     * @param code the code for the Block
     * @param description the description for the Block
     */
    public BlockDto(Integer id, String code, String description, String htmlColour) {
        this.id = id;
        this.code = code;
        this.description = description;
        this.htmlColour = htmlColour;
    }
    
    /**
     * Constructor to create a BlockDto object from a Block object
     *
     * @param block the Block object to use for construction
     */
    BlockDto(Block block) {
        this.id = block.id;
        this.code = block.code;
        this.description = block.description;
        this.htmlColour = block.htmlColour;
    }
    
    @Override
    public String toString() {
        return "BlockDto [id=" + id + ", code=" + code + ", description=" + description + ", htmlColour=" + htmlColour + "]";
    }
    
    public static BlockDto mapFromBlockEntity(Block block) {
        return new BlockDto(block);
    }
    
    public static List<BlockDto> mapFromBlocksEntities(List<Block> blocks) {
        List<BlockDto> output = blocks.collect { block ->  new BlockDto(block) };
        return output
    }
    
    public static Block mapToBlockEntity(BlockDto blockDto) {
        return new Block(blockDto.id, blockDto.code, blockDto.description, blockDto.htmlColour)
    }
}
