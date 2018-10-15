package uk.ac.reigate.services

import static org.junit.Assert.assertNull
import static org.junit.Assert.assertTrue
import static org.mockito.Mockito.*

import org.junit.Before
import org.junit.Test
import org.mockito.Mockito

import uk.ac.reigate.domain.ilr.Outcome;
import uk.ac.reigate.domain.ilr.Outcome
import uk.ac.reigate.model.SearchResult
import uk.ac.reigate.repositories.ilr.OutcomeRepository
import uk.ac.reigate.services.OutcomeService;


class OutcomeServiceTest {
    
    private OutcomeRepository outcomeRepository;
    
    private OutcomeService outcomeService;
    
    Outcome outcome1;
    Outcome outcome2;
    
    @Before
    public void setup() {
        this.outcomeRepository = Mockito.mock(OutcomeRepository.class)
        this.outcomeService = new OutcomeService(outcomeRepository);
        
        outcome1 = new Outcome(id: 1, code: 'N', description: 'New', shortDescription: 'New', validFrom: new Date().parse('yyyy/MM/dd', '2011/11/11'), validTo: new Date().parse('yyyy/MM/dd', '2016/11/11'))
        outcome2 = new Outcome(id: 2, code: 'C', description: 'Complete', shortDescription: 'Complete', validFrom: new Date().parse('yyyy/MM/dd', '2013/11/11'), validTo: new Date().parse('yyyy/MM/dd', '2015/11/11') )
        when(outcomeRepository.findAll()).thenReturn([outcome1, outcome2]);
        when(outcomeRepository.findOne(1)).thenReturn(outcome1);
        when(outcomeRepository.findOne(null)).thenThrow(new IllegalArgumentException());
        when(outcomeRepository.save(any(Outcome.class))).thenReturn(outcome1);
    }
    
    @Test
    public void testFindOutcomes() {
        List<Outcome> result = outcomeService.findAll();
        verify(outcomeRepository, times(1)).findAll()
        verifyNoMoreInteractions(outcomeRepository)
    }
    
    @Test
    public void testFindOutcome() {
        Outcome result = outcomeService.findById(1);
        verify(outcomeRepository, times(1)).findOne(1)
        verifyNoMoreInteractions(outcomeRepository)
    }
    
    @Test
    public void testSaveNewOutcome() {
        Outcome savedOutcome = outcomeService.save(outcome1);
        verify(outcomeRepository, times(1)).save(any())
        verifyNoMoreInteractions(outcomeRepository)
    }
    
    @Test
    public void testSaveOutcome() {
        Outcome savedOutcome = outcomeService.save(outcome1);
        verify(outcomeRepository, times(1)).save(any())
        verifyNoMoreInteractions(outcomeRepository)
    }
    
    @Test
    public void testSaveOutcomes() {
        List<Outcome> savedOutcomes = outcomeService.saveOutcomes([outcome1, outcome2]);
        verify(outcomeRepository, times(2)).save(any(Outcome.class))
        verifyNoMoreInteractions(outcomeRepository)
    }
    
    @Test
    public void testSaveOutcomeByFields_WithNullId() {
        Outcome savedOutcome = outcomeService.saveOutcome(null, outcome1.code, outcome1.description, outcome1.shortDescription, outcome1.validFrom, outcome1.validTo);
        verify(outcomeRepository, times(1)).save(any())
        verifyNoMoreInteractions(outcomeRepository)
    }
    
    
    @Test
    public void testSaveOutcomeByFields_WithId() {
        Outcome savedOutcome = outcomeService.saveOutcome(1, outcome1.code, outcome1.description, outcome1.shortDescription, outcome1.validFrom, outcome1.validTo);
        verify(outcomeRepository, times(1)).findOne(1)
        verify(outcomeRepository, times(1)).save(any())
        verifyNoMoreInteractions(outcomeRepository)
    }
}

