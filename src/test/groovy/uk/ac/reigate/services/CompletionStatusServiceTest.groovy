package uk.ac.reigate.services

import static org.mockito.Mockito.*

import org.junit.Before
import org.junit.Test
import org.mockito.Mockito

import uk.ac.reigate.domain.ilr.CompletionStatus
import uk.ac.reigate.model.SearchResult
import uk.ac.reigate.repositories.ilr.CompletionStatusRepository


class CompletionStatusServiceTest {
    
    private CompletionStatusRepository completionStatusRepository
    
    private CompletionStatusService completionStatusService;
    
    CompletionStatus completionStatus1
    CompletionStatus completionStatus2
    
    @Before
    public void setup() {
        this.completionStatusRepository = Mockito.mock(CompletionStatusRepository.class);
        this.completionStatusService = new CompletionStatusService(completionStatusRepository);
        
        completionStatus1 = new CompletionStatus(id: 1, code: 'UK', description: 'United Kingdom', shortDescription:'United Kingdom', validFrom: new Date().parse('yyyy/MM/dd', '2011/07/09'), validTo: new Date().parse('yyyy/MM/dd', '2013/07/09'))
        completionStatus2 = new CompletionStatus(id: 2, code: 'EU', description: 'European Ecconomical Union', shortDescription:'European Union', validFrom: new Date().parse('yyyy/MM/dd', '2011/07/09'), validTo: new Date().parse('yyyy/MM/dd', '2013/07/09'))
        
        when(completionStatusRepository.findAll()).thenReturn([
            completionStatus1,
            completionStatus2
        ]);
        when(completionStatusRepository.findOne(1)).thenReturn(completionStatus1);
        when(completionStatusRepository.findOne(null)).thenThrow(new IllegalArgumentException());
        when(completionStatusRepository.save(any(CompletionStatus.class))).thenReturn(completionStatus1);
    }
    
    @Test
    public void testFindCompletionStatuses() {
        List<CompletionStatus> result = completionStatusService.findAll();
        verify(completionStatusRepository, times(1)).findAll()
        verifyNoMoreInteractions(completionStatusRepository)
    }
    
    @Test
    public void testFindCompletionStatus() {
        CompletionStatus result = completionStatusService.findById(1);
        verify(completionStatusRepository, times(1)).findOne(1)
        verifyNoMoreInteractions(completionStatusRepository)
    }
    
    @Test
    public void testSaveNewCompletionStatus() {
        CompletionStatus savedCompletionStatus = completionStatusService.save(completionStatus1);
        verify(completionStatusRepository, times(1)).save(any())
        verifyNoMoreInteractions(completionStatusRepository)
    }
    
    @Test
    public void testSaveCompletionStatus() {
        CompletionStatus savedCompletionStatus = completionStatusService.save(completionStatus1);
        verify(completionStatusRepository, times(1)).save(any())
        verifyNoMoreInteractions(completionStatusRepository)
    }
    
    @Test
    public void testSaveCompletionStatuses() {
        List<CompletionStatus> savedCompletionStatuses = completionStatusService.saveCompletionStatuses([
            completionStatus1,
            completionStatus2
        ]);
        verify(completionStatusRepository, times(2)).save(any(CompletionStatus.class))
        verifyNoMoreInteractions(completionStatusRepository)
    }
    
    @Test
    public void testSaveCompletionStatusByFields_WithNullId() {
        CompletionStatus savedCompletionStatus = completionStatusService.saveCompletionStatus(null,  completionStatus1.code, completionStatus1.description, completionStatus1.shortDescription, completionStatus1.validFrom, completionStatus1.validTo);
        verify(completionStatusRepository, times(1)).save(any())
        verifyNoMoreInteractions(completionStatusRepository)
    }
    
    
    @Test
    public void testSaveCompletionStatusByFields_WithId() {
        CompletionStatus savedCompletionStatus = completionStatusService.saveCompletionStatus(1,  completionStatus1.code, completionStatus1.description, completionStatus1.shortDescription, completionStatus1.validFrom, completionStatus1.validTo);
        verify(completionStatusRepository, times(1)).findOne(1)
        verify(completionStatusRepository, times(1)).save(any())
        verifyNoMoreInteractions(completionStatusRepository)
    }
}

