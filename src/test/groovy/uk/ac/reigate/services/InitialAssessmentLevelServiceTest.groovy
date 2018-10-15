package uk.ac.reigate.services

import static org.junit.Assert.assertNull
import static org.junit.Assert.assertTrue
import static org.mockito.Mockito.*

import org.junit.Before
import org.junit.Test
import org.mockito.Mockito


import uk.ac.reigate.domain.learning_support.InitialAssessmentLevel
import uk.ac.reigate.model.SearchResult
import uk.ac.reigate.repositories.learning_support.InitialAssessmentLevelRepository
import uk.ac.reigate.services.InitialAssessmentLevelService;


class InitialAssessmentLevelServiceTest {
    
    private InitialAssessmentLevelRepository initialAssessmentLevelRepository;
    
    private InitialAssessmentLevelService initialAssessmentLevelService;
    
    InitialAssessmentLevel initialAssessmentLevel1
    InitialAssessmentLevel initialAssessmentLevel2
    
    @Before
    public void setup() {
        this.initialAssessmentLevelRepository = Mockito.mock(InitialAssessmentLevelRepository.class);
        this.initialAssessmentLevelService = new InitialAssessmentLevelService(initialAssessmentLevelRepository);
        
        initialAssessmentLevel1 = new InitialAssessmentLevel(id: 1, initialAssessmentLevel: 'M', abbrv: 'M')
        initialAssessmentLevel2 = new InitialAssessmentLevel(id: 2, initialAssessmentLevel: 'F', abbrv: 'F')
        
        when(initialAssessmentLevelRepository.findAll()).thenReturn([
            initialAssessmentLevel1,
            initialAssessmentLevel2
        ]);
        when(initialAssessmentLevelRepository.findOne(1)).thenReturn(initialAssessmentLevel1);
        when(initialAssessmentLevelRepository.findOne(null)).thenThrow(new IllegalArgumentException());
        when(initialAssessmentLevelRepository.save(any(InitialAssessmentLevel.class))).thenReturn(initialAssessmentLevel1);
    }
    
    @Test
    public void testFindInitialAssessmentLevels() {
        List<InitialAssessmentLevel> result = initialAssessmentLevelService.findAll();
        verify(initialAssessmentLevelRepository, times(1)).findAll()
        verifyNoMoreInteractions(initialAssessmentLevelRepository)
    }
    
    @Test
    public void testFindInitialAssessmentLevel() {
        InitialAssessmentLevel result = initialAssessmentLevelService.findById(1);
        verify(initialAssessmentLevelRepository, times(1)).findOne(1)
        verifyNoMoreInteractions(initialAssessmentLevelRepository)
    }
    
    @Test
    public void testSaveNewInitialAssessmentLevel() {
        InitialAssessmentLevel savedInitialAssessmentLevel = initialAssessmentLevelService.save(initialAssessmentLevel1);
        verify(initialAssessmentLevelRepository, times(1)).save(any())
        verifyNoMoreInteractions(initialAssessmentLevelRepository)
    }
    
    @Test
    public void testSaveInitialAssessmentLevel() {
        InitialAssessmentLevel savedInitialAssessmentLevel = initialAssessmentLevelService.save(initialAssessmentLevel1);
        verify(initialAssessmentLevelRepository, times(1)).save(any())
        verifyNoMoreInteractions(initialAssessmentLevelRepository)
    }
    
    @Test
    public void testSaveInitialAssessmentLevels() {
        List<InitialAssessmentLevel> savedInitialAssessmentLevels = initialAssessmentLevelService.saveInitialAssessmentLevels([
            initialAssessmentLevel1,
            initialAssessmentLevel2
        ]);
        verify(initialAssessmentLevelRepository, times(2)).save(any(InitialAssessmentLevel.class))
        verifyNoMoreInteractions(initialAssessmentLevelRepository)
    }
    
    @Test
    public void testSaveInitialAssessmentLevelByFields_WithNullId() {
        InitialAssessmentLevel savedInitialAssessmentLevel = initialAssessmentLevelService.saveInitialAssessmentLevel(null, initialAssessmentLevel1.initialAssessmentLevel, initialAssessmentLevel1.abbrv);
        verify(initialAssessmentLevelRepository, times(1)).save(any())
        verifyNoMoreInteractions(initialAssessmentLevelRepository)
    }
    
    
    @Test
    public void testSaveInitialAssessmentLevelByFields_WithId() {
        InitialAssessmentLevel savedInitialAssessmentLevel = initialAssessmentLevelService.saveInitialAssessmentLevel(1, initialAssessmentLevel1.initialAssessmentLevel, initialAssessmentLevel1.abbrv);
        verify(initialAssessmentLevelRepository, times(1)).findOne(1)
        verify(initialAssessmentLevelRepository, times(1)).save(any())
        verifyNoMoreInteractions(initialAssessmentLevelRepository)
    }
}

