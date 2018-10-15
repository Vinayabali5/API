package uk.ac.reigate.services

import static org.junit.Assert.assertNull
import static org.junit.Assert.assertTrue
import static org.mockito.Mockito.*

import org.junit.Before
import org.junit.Test
import org.mockito.Mockito

import uk.ac.reigate.domain.ilr.PriorAttainment;
import uk.ac.reigate.domain.ilr.PriorAttainment
import uk.ac.reigate.model.SearchResult
import uk.ac.reigate.repositories.ilr.PriorAttainmentRepository


class PriorAttainmentServiceTest {
    
    private PriorAttainmentRepository priorAttainmentRepository;
    
    private PriorAttainmentService priorAttainmentService;
    
    PriorAttainment priorAttainment1;
    PriorAttainment priorAttainment2;
    
    @Before
    public void setup() {
        this.priorAttainmentRepository = Mockito.mock(PriorAttainmentRepository.class)
        this.priorAttainmentService = new PriorAttainmentService(priorAttainmentRepository);
        
        priorAttainment1 = new PriorAttainment(id: 1, code: 'N', description: 'New', shortDescription: 'New', validFrom: new Date().parse('yyyy/MM/dd', '2011/11/11'), validTo: new Date().parse('yyyy/MM/dd', '2016/11/11'))
        priorAttainment2 = new PriorAttainment(id: 2, code: 'C', description: 'Complete', shortDescription: 'Complete', validFrom: new Date().parse('yyyy/MM/dd', '2013/11/11'), validTo: new Date().parse('yyyy/MM/dd', '2015/11/11') )
        when(priorAttainmentRepository.findAll()).thenReturn([
            priorAttainment1,
            priorAttainment2
        ]);
        when(priorAttainmentRepository.findOne(1)).thenReturn(priorAttainment1);
        when(priorAttainmentRepository.findOne(null)).thenThrow(new IllegalArgumentException());
        when(priorAttainmentRepository.save(any(PriorAttainment.class))).thenReturn(priorAttainment1);
    }
    
    @Test
    public void testFindPriorAttainments() {
        List<PriorAttainment> result = priorAttainmentService.findAll();
        verify(priorAttainmentRepository, times(1)).findAll()
        verifyNoMoreInteractions(priorAttainmentRepository)
    }
    
    @Test
    public void testFindPriorAttainment() {
        PriorAttainment result = priorAttainmentService.findById(1);
        verify(priorAttainmentRepository, times(1)).findOne(1)
        verifyNoMoreInteractions(priorAttainmentRepository)
    }
    
    @Test
    public void testSaveNewPriorAttainment() {
        PriorAttainment savedPriorAttainment = priorAttainmentService.save(priorAttainment1);
        verify(priorAttainmentRepository, times(1)).save(any())
        verifyNoMoreInteractions(priorAttainmentRepository)
    }
    
    @Test
    public void testSavePriorAttainment() {
        PriorAttainment savedPriorAttainment = priorAttainmentService.save(priorAttainment1);
        verify(priorAttainmentRepository, times(1)).save(any())
        verifyNoMoreInteractions(priorAttainmentRepository)
    }
    
    @Test
    public void testSavePriorAttainments() {
        List<PriorAttainment> savedPriorAttainments = priorAttainmentService.savePriorAttainments([
            priorAttainment1,
            priorAttainment2
        ]);
        verify(priorAttainmentRepository, times(2)).save(any(PriorAttainment.class))
        verifyNoMoreInteractions(priorAttainmentRepository)
    }
    
    @Test
    public void testSavePriorAttainmentByFields_WithNullId() {
        PriorAttainment savedPriorAttainment = priorAttainmentService.savePriorAttainment(null, priorAttainment1.code, priorAttainment1.description, priorAttainment1.shortDescription, priorAttainment1.validFrom, priorAttainment1.validTo);
        verify(priorAttainmentRepository, times(1)).save(any())
        verifyNoMoreInteractions(priorAttainmentRepository)
    }
    
    
    @Test
    public void testSavePriorAttainmentByFields_WithId() {
        PriorAttainment savedPriorAttainment = priorAttainmentService.savePriorAttainment(1, priorAttainment1.code, priorAttainment1.description, priorAttainment1.shortDescription, priorAttainment1.validFrom, priorAttainment1.validTo);
        verify(priorAttainmentRepository, times(1)).findOne(1)
        verify(priorAttainmentRepository, times(1)).save(any())
        verifyNoMoreInteractions(priorAttainmentRepository)
    }
}

