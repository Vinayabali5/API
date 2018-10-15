package uk.ac.reigate.services

import static org.junit.Assert.assertNull
import static org.junit.Assert.assertTrue
import static org.mockito.Mockito.*

import org.junit.Before
import org.junit.Test
import org.mockito.Mockito

import uk.ac.reigate.domain.academic.Block;
import uk.ac.reigate.model.SearchResult
import uk.ac.reigate.repositories.academic.BlockRepository
import uk.ac.reigate.services.BlockService;


class BlockServiceTest {
    
    private BlockRepository blockRepository
    
    private BlockService blockService;
    
    Block block1
    Block block2
    
    @Before
    public void setup() {
        this.blockRepository = Mockito.mock(BlockRepository.class);
        this.blockService = new BlockService(blockRepository);
        
        block1 = new Block(id: 1, code: 'A', description: 'A Block', htmlColour: '#FAFA50')
        block2 = new Block(id: 2, code: 'C', description: 'C Block', htmlColour: '#FAFA50')
        
        when(blockRepository.findAll()).thenReturn([block1, block2]);
        when(blockRepository.findOne(1)).thenReturn(block1);
        when(blockRepository.findOne(null)).thenThrow(new IllegalArgumentException());
        when(blockRepository.save(any(Block.class))).thenReturn(block1);
    }
    
    @Test
    public void testFindBlocks() {
        List<Block> result = blockService.findAll();
        verify(blockRepository, times(1)).findAll()
        verifyNoMoreInteractions(blockRepository)
    }
    
    @Test
    public void testFindBlock() {
        Block result = blockService.findById(1);
        verify(blockRepository, times(1)).findOne(1)
        verifyNoMoreInteractions(blockRepository)
    }
    
    @Test
    public void testSaveNewBlock() {
        Block savedBlock = blockService.save(block1);
        verify(blockRepository, times(1)).save(any())
        verifyNoMoreInteractions(blockRepository)
    }
    
    @Test
    public void testSaveBlock() {
        Block savedBlock = blockService.save(block1);
        verify(blockRepository, times(1)).save(any())
        verifyNoMoreInteractions(blockRepository)
    }
    
    @Test
    public void testSaveBlocks() {
        List<Block> savedBlocks = blockService.saveBlocks([block1, block2]);
        verify(blockRepository, times(2)).save(any(Block.class))
        verifyNoMoreInteractions(blockRepository)
    }
    
    @Test
    public void testSaveBlockByFields_WithNullId() {
        Block savedBlock = blockService.saveBlock(null, block1.code, block1.description, block1.htmlColour);
        verify(blockRepository, times(1)).save(any())
        verifyNoMoreInteractions(blockRepository)
    }
    
    
    @Test
    public void testSaveBlockByFields_WithId() {
        Block savedBlock = blockService.saveBlock(1, block1.code, block1.description, block1.htmlColour);
        verify(blockRepository, times(1)).findOne(1)
        verify(blockRepository, times(1)).save(any())
        verifyNoMoreInteractions(blockRepository)
    }
}

