package uk.ac.reigate.services

import static org.junit.Assert.assertNull
import static org.junit.Assert.assertTrue
import static org.mockito.Mockito.*

import org.junit.Before
import org.junit.Test
import org.mockito.Mockito

import uk.ac.reigate.domain.ilr.WithdrawalReason;
import uk.ac.reigate.domain.ilr.WithdrawalReason
import uk.ac.reigate.model.SearchResult
import uk.ac.reigate.repositories.ilr.WithdrawalReasonRepository


class WithdrawalReasonServiceTest {
    
    private WithdrawalReasonRepository withdrawalReasonRepository;
    
    private WithdrawalReasonService withdrawalReasonService;
    
    WithdrawalReason withdrawalReason1;
    WithdrawalReason withdrawalReason2;
    
    @Before
    public void setup() {
        this.withdrawalReasonRepository = Mockito.mock(WithdrawalReasonRepository.class)
        this.withdrawalReasonService = new WithdrawalReasonService(withdrawalReasonRepository);
        
        withdrawalReason1 = new WithdrawalReason(id: 1, code: 'N', description: 'New', shortDescription: 'New', validFrom: new Date().parse('yyyy/MM/dd', '2011/11/11'), validTo: new Date().parse('yyyy/MM/dd', '2016/11/11'))
        withdrawalReason2 = new WithdrawalReason(id: 2, code: 'C', description: 'Complete', shortDescription: 'Complete', validFrom: new Date().parse('yyyy/MM/dd', '2013/11/11'), validTo: new Date().parse('yyyy/MM/dd', '2015/11/11') )
        when(withdrawalReasonRepository.findAll()).thenReturn([
            withdrawalReason1,
            withdrawalReason2
        ]);
        when(withdrawalReasonRepository.findOne(1)).thenReturn(withdrawalReason1);
        when(withdrawalReasonRepository.findOne(null)).thenThrow(new IllegalArgumentException());
        when(withdrawalReasonRepository.save(any(WithdrawalReason.class))).thenReturn(withdrawalReason1);
    }
    
    @Test
    public void testFindWithdrawalReasons() {
        List<WithdrawalReason> result = withdrawalReasonService.findAll();
        verify(withdrawalReasonRepository, times(1)).findAll()
        verifyNoMoreInteractions(withdrawalReasonRepository)
    }
    
    @Test
    public void testFindWithdrawalReason() {
        WithdrawalReason result = withdrawalReasonService.findById(1);
        verify(withdrawalReasonRepository, times(1)).findOne(1)
        verifyNoMoreInteractions(withdrawalReasonRepository)
    }
    
    @Test
    public void testSaveNewWithdrawalReason() {
        WithdrawalReason savedWithdrawalReason = withdrawalReasonService.save(withdrawalReason1);
        verify(withdrawalReasonRepository, times(1)).save(any())
        verifyNoMoreInteractions(withdrawalReasonRepository)
    }
    
    @Test
    public void testSaveWithdrawalReason() {
        WithdrawalReason savedWithdrawalReason = withdrawalReasonService.save(withdrawalReason1);
        verify(withdrawalReasonRepository, times(1)).save(any())
        verifyNoMoreInteractions(withdrawalReasonRepository)
    }
    
    @Test
    public void testSaveWithdrawalReasons() {
        List<WithdrawalReason> savedWithdrawalReasons = withdrawalReasonService.saveWithdrawalReasons([
            withdrawalReason1,
            withdrawalReason2
        ]);
        verify(withdrawalReasonRepository, times(2)).save(any(WithdrawalReason.class))
        verifyNoMoreInteractions(withdrawalReasonRepository)
    }
    
    @Test
    public void testSaveWithdrawalReasonByFields_WithNullId() {
        WithdrawalReason savedWithdrawalReason = withdrawalReasonService.saveWithdrawalReason(null, withdrawalReason1.code, withdrawalReason1.description, withdrawalReason1.shortDescription, withdrawalReason1.validFrom, withdrawalReason1.validTo);
        verify(withdrawalReasonRepository, times(1)).save(any())
        verifyNoMoreInteractions(withdrawalReasonRepository)
    }
    
    
    @Test
    public void testSaveWithdrawalReasonByFields_WithId() {
        WithdrawalReason savedWithdrawalReason = withdrawalReasonService.saveWithdrawalReason(1, withdrawalReason1.code, withdrawalReason1.description, withdrawalReason1.shortDescription, withdrawalReason1.validFrom, withdrawalReason1.validTo);
        verify(withdrawalReasonRepository, times(1)).findOne(1)
        verify(withdrawalReasonRepository, times(1)).save(any())
        verifyNoMoreInteractions(withdrawalReasonRepository)
    }
}

