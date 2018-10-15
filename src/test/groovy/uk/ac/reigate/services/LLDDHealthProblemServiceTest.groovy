package uk.ac.reigate.services

import static org.junit.Assert.assertNull
import static org.junit.Assert.assertTrue
import static org.mockito.Mockito.*

import org.junit.Before
import org.junit.Test
import org.mockito.Mockito

import uk.ac.reigate.domain.ilr.LLDDHealthProblem;
import uk.ac.reigate.domain.ilr.LLDDHealthProblem
import uk.ac.reigate.model.SearchResult
import uk.ac.reigate.repositories.ilr.LLDDHealthProblemRepository
import uk.ac.reigate.services.LLDDHealthProblemService;


class LLDDHealthProblemServiceTest {
    
    private LLDDHealthProblemRepository lLDDHealthProblemRepository;
    
    private LLDDHealthProblemService lLDDHealthProblemService;
    
    LLDDHealthProblem lLDDHealthProblem1;
    LLDDHealthProblem lLDDHealthProblem2;
    
    @Before
    public void setup() {
        this.lLDDHealthProblemRepository = Mockito.mock(LLDDHealthProblemRepository.class)
        this.lLDDHealthProblemService = new LLDDHealthProblemService(lLDDHealthProblemRepository);
        
        lLDDHealthProblem1 = new LLDDHealthProblem(id: 1, code: 'N', description: 'New', shortDescription: 'New', validFrom: new Date().parse('yyyy/MM/dd', '2011/11/11'), validTo: new Date().parse('yyyy/MM/dd', '2016/11/11'))
        lLDDHealthProblem2 = new LLDDHealthProblem(id: 2, code: 'C', description: 'Complete', shortDescription: 'Complete', validFrom: new Date().parse('yyyy/MM/dd', '2013/11/11'), validTo: new Date().parse('yyyy/MM/dd', '2015/11/11') )
        when(lLDDHealthProblemRepository.findAll()).thenReturn([
            lLDDHealthProblem1,
            lLDDHealthProblem2
        ]);
        when(lLDDHealthProblemRepository.findOne(1)).thenReturn(lLDDHealthProblem1);
        when(lLDDHealthProblemRepository.findOne(null)).thenThrow(new IllegalArgumentException());
        when(lLDDHealthProblemRepository.save(any(LLDDHealthProblem.class))).thenReturn(lLDDHealthProblem1);
    }
    
    @Test
    public void testFindLLDDHealthProblems() {
        List<LLDDHealthProblem> result = lLDDHealthProblemService.findAll();
        verify(lLDDHealthProblemRepository, times(1)).findAll()
        verifyNoMoreInteractions(lLDDHealthProblemRepository)
    }
    
    @Test
    public void testFindLLDDHealthProblem() {
        LLDDHealthProblem result = lLDDHealthProblemService.findById(1);
        verify(lLDDHealthProblemRepository, times(1)).findOne(1)
        verifyNoMoreInteractions(lLDDHealthProblemRepository)
    }
    
    @Test
    public void testSaveNewLLDDHealthProblem() {
        LLDDHealthProblem savedLLDDHealthProblem = lLDDHealthProblemService.save(lLDDHealthProblem1);
        verify(lLDDHealthProblemRepository, times(1)).save(any())
        verifyNoMoreInteractions(lLDDHealthProblemRepository)
    }
    
    @Test
    public void testSaveLLDDHealthProblem() {
        LLDDHealthProblem savedLLDDHealthProblem = lLDDHealthProblemService.save(lLDDHealthProblem1);
        verify(lLDDHealthProblemRepository, times(1)).save(any())
        verifyNoMoreInteractions(lLDDHealthProblemRepository)
    }
    
    @Test
    public void testSaveLLDDHealthProblems() {
        List<LLDDHealthProblem> savedLLDDHealthProblems = lLDDHealthProblemService.saveLLDDHealthProblems([
            lLDDHealthProblem1,
            lLDDHealthProblem2
        ]);
        verify(lLDDHealthProblemRepository, times(2)).save(any(LLDDHealthProblem.class))
        verifyNoMoreInteractions(lLDDHealthProblemRepository)
    }
    
    @Test
    public void testSaveLLDDHealthProblemByFields_WithNullId() {
        LLDDHealthProblem savedLLDDHealthProblem = lLDDHealthProblemService.saveLLDDHealthProblem(null, lLDDHealthProblem1.code, lLDDHealthProblem1.description, lLDDHealthProblem1.shortDescription, lLDDHealthProblem1.validFrom, lLDDHealthProblem1.validTo);
        verify(lLDDHealthProblemRepository, times(1)).save(any())
        verifyNoMoreInteractions(lLDDHealthProblemRepository)
    }
    
    
    @Test
    public void testSaveLLDDHealthProblemByFields_WithId() {
        LLDDHealthProblem savedLLDDHealthProblem = lLDDHealthProblemService.saveLLDDHealthProblem(1, lLDDHealthProblem1.code, lLDDHealthProblem1.description, lLDDHealthProblem1.shortDescription, lLDDHealthProblem1.validFrom, lLDDHealthProblem1.validTo);
        verify(lLDDHealthProblemRepository, times(1)).findOne(1)
        verify(lLDDHealthProblemRepository, times(1)).save(any())
        verifyNoMoreInteractions(lLDDHealthProblemRepository)
    }
}

