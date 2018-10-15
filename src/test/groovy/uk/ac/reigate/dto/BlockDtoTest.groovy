package uk.ac.reigate.dto;

import static org.junit.Assert.*

import org.junit.Before
import org.junit.Test

import uk.ac.reigate.domain.academic.Block

public class BlockDtoTest {
    
    private Block block1
    
    private Block block2
    
    private List<Block> blocks
    
    @Before
    public void setup() {
        this.block1 = new Block(
                id: 1,
                code: 'A',
                description: 'A Block',
                htmlColour: '#FAFA50'
                );
        this.block2 = new Block(
                id: 2,
                code: 'B',
                description: 'B Block',
                htmlColour: '#FAFA50'
                );
        this.blocks = Arrays.asList(this.block1, this.block2);
    }
    
    @Test
    void testConstructor_block() {
        BlockDto blockTest = new BlockDto( block1 )
        assertEquals( blockTest.id, block1.id );
        assertEquals( blockTest.code, block1.code);
        assertEquals( blockTest.description, block1.description);
    }
    
    @Test
    public void testMapFromBlockEntity(){
        BlockDto blockTest = BlockDto.mapFromBlockEntity( block1 )
        assertEquals( blockTest.id, block1.id );
        assertEquals( blockTest.code, block1.code);
        assertEquals( blockTest.description, block1.description);
    }
    
    @Test
    public void testMapFromBlocksEntities(){
        List<BlockDto> blocksTest = BlockDto.mapFromBlocksEntities( this.blocks )
        assertEquals( blocksTest[0].id, block1.id );
        assertEquals( blocksTest[0].code, block1.code );
        assertEquals( blocksTest[0].description, block1.description);
        assertEquals( blocksTest[1].id, block2.id );
        assertEquals( blocksTest[1].code, block2.code );
        assertEquals( blocksTest[1].description, block2.description);
    }
    
    @Test
    public void testMapToBlockEntity(){
        BlockDto blockDto = new BlockDto(block1.id, block1.code, block1.description, block1.htmlColour);
        Block block = BlockDto.mapToBlockEntity( blockDto );
        assertEquals( block.id, block1.id );
        assertEquals( block.code, block1.code);
        assertEquals( block.description, block1.description);
    }
    
    @Test
    public void testEquals_Same() {
        BlockDto blockDto1 = new BlockDto(block1.id, block1.code, block1.description, block1.htmlColour)
        BlockDto blockDto2 = new BlockDto(block1.id, block1.code, block1.description, block1.htmlColour)
        assertEquals( blockDto1, blockDto2)
    }
    
    @Test
    public void testEquals_Different() {
        BlockDto blockDto1 = new BlockDto(block1.id, block1.code, block1.description, block1.htmlColour)
        BlockDto blockDto2 = new BlockDto(block2.id, block2.code, block2.description, block2.htmlColour)
        assertNotEquals( blockDto1, blockDto2)
    }
    
    @Test
    public void testHashCode_Same() {
        BlockDto blockDto1 = new BlockDto(block1.id, block1.code, block1.description, block1.htmlColour)
        BlockDto blockDto2 = new BlockDto(block1.id, block1.code, block1.description, block1.htmlColour)
        assertEquals( blockDto1.hashCode(), blockDto2.hashCode())
    }
    
    @Test
    public void testHashCode_Different() {
        BlockDto blockDto1 = new BlockDto(block1.id, block1.code, block1.description, block1.htmlColour)
        BlockDto blockDto2 = new BlockDto(block2.id, block2.code, block2.description, block2.htmlColour)
        assertNotEquals( blockDto1.hashCode(), blockDto2.hashCode())
    }
    
    @Test
    public void testConstructor_Block() {
        BlockDto blockDto = new BlockDto(block1)
        assertEquals( blockDto.code, block1.code )
        assertEquals( blockDto.description, block1.description )
        assertEquals( blockDto.htmlColour, block1.htmlColour )
    }
}
