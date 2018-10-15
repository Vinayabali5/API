package uk.ac.reigate.services

import static org.junit.Assert.assertNull
import static org.junit.Assert.assertTrue
import static org.mockito.Mockito.*

import org.junit.Before
import org.junit.Test
import org.mockito.Mockito


import uk.ac.reigate.domain.lookup.PossibleGradeSet
import uk.ac.reigate.model.SearchResult
import uk.ac.reigate.repositories.lookup.PossibleGradeSetRepository
import uk.ac.reigate.services.PossibleGradeSetService;


class PossibleGradeSetServiceTest {
    
    private PossibleGradeSetRepository possibleGradeSetRepository;
    
    private PossibleGradeSetService possibleGradeSetService;
    
    PossibleGradeSet possibleGradeSet1
    PossibleGradeSet possibleGradeSet2
    
    @Before
    public void setup() {
        this.possibleGradeSetRepository = Mockito.mock(PossibleGradeSetRepository.class);
        this.possibleGradeSetService = new PossibleGradeSetService(possibleGradeSetRepository);
        
        possibleGradeSet1 = new PossibleGradeSet(id: 1, code: 'M', description: 'M')
        possibleGradeSet2 = new PossibleGradeSet(id: 2, code: 'F', description: 'F')
        
        when(possibleGradeSetRepository.findAll()).thenReturn([
            possibleGradeSet1,
            possibleGradeSet2
        ]);
        when(possibleGradeSetRepository.findOne(1)).thenReturn(possibleGradeSet1);
        when(possibleGradeSetRepository.findOne(null)).thenThrow(new IllegalArgumentException());
        when(possibleGradeSetRepository.save(any(PossibleGradeSet.class))).thenReturn(possibleGradeSet1);
    }
    
    @Test
    public void testFindPossibleGradeSets() {
        List<PossibleGradeSet> result = possibleGradeSetService.findAll();
        verify(possibleGradeSetRepository, times(1)).findAll()
        verifyNoMoreInteractions(possibleGradeSetRepository)
    }
    
    @Test
    public void testFindPossibleGradeSet() {
        PossibleGradeSet result = possibleGradeSetService.findById(1);
        verify(possibleGradeSetRepository, times(1)).findOne(1)
        verifyNoMoreInteractions(possibleGradeSetRepository)
    }
    
    @Test
    public void testSaveNewPossibleGradeSet() {
        PossibleGradeSet savedPossibleGradeSet = possibleGradeSetService.save(possibleGradeSet1);
        verify(possibleGradeSetRepository, times(1)).save(any())
        verifyNoMoreInteractions(possibleGradeSetRepository)
    }
    
    @Test
    public void testSavePossibleGradeSet() {
        PossibleGradeSet savedPossibleGradeSet = possibleGradeSetService.save(possibleGradeSet1);
        verify(possibleGradeSetRepository, times(1)).save(any())
        verifyNoMoreInteractions(possibleGradeSetRepository)
    }
    
    @Test
    public void testSavePossibleGradeSets() {
        List<PossibleGradeSet> savedPossibleGradeSets = possibleGradeSetService.savePossibleGradeSets([
            possibleGradeSet1,
            possibleGradeSet2
        ]);
        verify(possibleGradeSetRepository, times(2)).save(any(PossibleGradeSet.class))
        verifyNoMoreInteractions(possibleGradeSetRepository)
    }
    
    @Test
    public void testSavePossibleGradeSetByFields_WithNullId() {
        PossibleGradeSet savedPossibleGradeSet = possibleGradeSetService.savePossibleGradeSet(null, possibleGradeSet1.code, possibleGradeSet1.description);
        verify(possibleGradeSetRepository, times(1)).save(any())
        verifyNoMoreInteractions(possibleGradeSetRepository)
    }
    
    
    @Test
    public void testSavePossibleGradeSetByFields_WithId() {
        PossibleGradeSet savedPossibleGradeSet = possibleGradeSetService.savePossibleGradeSet(1, possibleGradeSet1.code, possibleGradeSet1.description);
        verify(possibleGradeSetRepository, times(1)).findOne(1)
        verify(possibleGradeSetRepository, times(1)).save(any())
        verifyNoMoreInteractions(possibleGradeSetRepository)
    }
}

