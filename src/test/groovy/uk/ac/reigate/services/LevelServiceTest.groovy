package uk.ac.reigate.services

import static org.mockito.Mockito.*

import org.junit.Before
import org.junit.Test
import org.mockito.Mockito

import uk.ac.reigate.domain.lookup.Level
import uk.ac.reigate.model.SearchResult
import uk.ac.reigate.repositories.lookup.LevelRepository


class LevelServiceTest {
    
    private LevelRepository levelRepository;
    
    private LevelService levelService;
    
    Level level1
    Level level2
    
    @Before
    public void setup() {
        this.levelRepository = Mockito.mock(LevelRepository.class);
        this.levelService = new LevelService(levelRepository);
        
        level1 = new Level(id: 1, code: 'A', description: 'A Level')
        level2 = new Level(id: 2, code: 'G', description: 'GCSE')
        
        when(levelRepository.findAll()).thenReturn([level1, level2]);
        when(levelRepository.findOne(1)).thenReturn(level1);
        when(levelRepository.findOne(null)).thenThrow(new IllegalArgumentException());
        when(levelRepository.save(any(Level.class))).thenReturn(level1);
    }
    
    @Test
    public void testFindLevels() {
        List<Level> result = levelService.findAll();
        verify(levelRepository, times(1)).findAll()
        verifyNoMoreInteractions(levelRepository)
    }
    
    @Test
    public void testFindLevel() {
        Level result = levelService.findById(1);
        verify(levelRepository, times(1)).findOne(1)
        verifyNoMoreInteractions(levelRepository)
    }
    
    @Test
    public void testSaveNewLevel() {
        Level savedLevel = levelService.save(level1);
        verify(levelRepository, times(1)).save(any())
        verifyNoMoreInteractions(levelRepository)
    }
    
    @Test
    public void testSaveLevel() {
        Level savedLevel = levelService.save(level1);
        verify(levelRepository, times(1)).save(any())
        verifyNoMoreInteractions(levelRepository)
    }
    
    @Test
    public void testSaveLevels() {
        List<Level> savedLevels = levelService.saveLevels([level1, level2]);
        verify(levelRepository, times(2)).save(any(Level.class))
        verifyNoMoreInteractions(levelRepository)
    }
}

