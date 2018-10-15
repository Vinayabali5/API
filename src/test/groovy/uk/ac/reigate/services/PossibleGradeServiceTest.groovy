package uk.ac.reigate.services

import static org.mockito.Mockito.*

import org.junit.Before
import org.junit.Test
import org.mockito.Mockito

import uk.ac.reigate.domain.lookup.PossibleGrade
import uk.ac.reigate.model.SearchResult
import uk.ac.reigate.repositories.lookup.PossibleGradeRepository


class PossibleGradeServiceTest {
    
    private PossibleGradeRepository possibleGradeRepository;
    
    private PossibleGradeService possibleGradeService;
    
    PossibleGrade possibleGrade1
    PossibleGrade possibleGrade2
    
    @Before
    public void setup() {
        this.possibleGradeRepository = Mockito.mock(PossibleGradeRepository.class);
        this.possibleGradeService = new PossibleGradeService(possibleGradeRepository);
        
        possibleGrade1 = new PossibleGrade(id: 1, code: 'M', description: 'Male', grade: 'A', ucasPoints: 10, useForKeyAssessment: true)
        possibleGrade2 = new PossibleGrade(id: 2, code: 'F', description: 'Female', grade: 'B', ucasPoints: 11, useForKeyAssessment: true)
        
        when(possibleGradeRepository.findAll()).thenReturn([
            possibleGrade1,
            possibleGrade2
        ]);
        when(possibleGradeRepository.findOne(1)).thenReturn(possibleGrade1);
        when(possibleGradeRepository.findOne(null)).thenThrow(new IllegalArgumentException());
        when(possibleGradeRepository.save(any(PossibleGrade.class))).thenReturn(possibleGrade1);
    }
    
    @Test
    public void testFindPossibleGrade() {
        List<PossibleGrade> result = possibleGradeService.findAll();
        verify(possibleGradeRepository, times(1)).findAll()
        verifyNoMoreInteractions(possibleGradeRepository)
    }
    
    @Test
    public void testFindPossibleGradeById() {
        PossibleGrade result = possibleGradeService.findById(1);
        verify(possibleGradeRepository, times(1)).findOne(1)
        verifyNoMoreInteractions(possibleGradeRepository)
    }
    
    @Test
    public void testSaveNewPossibleGrade() {
        possibleGrade1.id = null
        possibleGradeService.save(possibleGrade1);
        verify(possibleGradeRepository, times(1)).save(possibleGrade1)
        verifyNoMoreInteractions(possibleGradeRepository)
    }
    
    @Test
    public void testSavePossibleGrade() {
        possibleGradeService.save(possibleGrade1);
        verify(possibleGradeRepository, times(1)).save(possibleGrade1)
        verifyNoMoreInteractions(possibleGradeRepository)
    }
}

