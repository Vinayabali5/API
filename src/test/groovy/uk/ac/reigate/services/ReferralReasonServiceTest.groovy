package uk.ac.reigate.services

import static org.junit.Assert.assertNull
import static org.junit.Assert.assertTrue
import static org.mockito.Mockito.*

import org.junit.Before
import org.junit.Test
import org.mockito.Mockito


import uk.ac.reigate.domain.learning_support.ReferralReason
import uk.ac.reigate.model.SearchResult
import uk.ac.reigate.repositories.learning_support.ReferralReasonRepository
import uk.ac.reigate.services.ReferralReasonService;


class ReferralReasonServiceTest {
    
    private ReferralReasonRepository referralReasonRepository;
    
    private ReferralReasonService referralReasonService;
    
    ReferralReason referralReason1
    ReferralReason referralReason2
    
    @Before
    public void setup() {
        this.referralReasonRepository = Mockito.mock(ReferralReasonRepository.class);
        this.referralReasonService = new ReferralReasonService(referralReasonRepository);
        
        referralReason1 = new ReferralReason(id: 1, reason: 'M')
        referralReason2 = new ReferralReason(id: 2, reason: 'F')
        
        when(referralReasonRepository.findAll()).thenReturn([
            referralReason1,
            referralReason2
        ]);
        when(referralReasonRepository.findOne(1)).thenReturn(referralReason1);
        when(referralReasonRepository.findOne(null)).thenThrow(new IllegalArgumentException());
        when(referralReasonRepository.save(any(ReferralReason.class))).thenReturn(referralReason1);
    }
    
    @Test
    public void testFindReferralReasons() {
        List<ReferralReason> result = referralReasonService.findAll();
        verify(referralReasonRepository, times(1)).findAll()
        verifyNoMoreInteractions(referralReasonRepository)
    }
    
    @Test
    public void testFindReferralReason() {
        ReferralReason result = referralReasonService.findById(1);
        verify(referralReasonRepository, times(1)).findOne(1)
        verifyNoMoreInteractions(referralReasonRepository)
    }
    
    @Test
    public void testSaveNewReferralReason() {
        ReferralReason savedReferralReason = referralReasonService.save(referralReason1);
        verify(referralReasonRepository, times(1)).save(any())
        verifyNoMoreInteractions(referralReasonRepository)
    }
    
    @Test
    public void testSaveReferralReason() {
        ReferralReason savedReferralReason = referralReasonService.save(referralReason1);
        verify(referralReasonRepository, times(1)).save(any())
        verifyNoMoreInteractions(referralReasonRepository)
    }
    
    @Test
    public void testSaveReferralReasons() {
        List<ReferralReason> savedReferralReasons = referralReasonService.saveReferralReasons([
            referralReason1,
            referralReason2
        ]);
        verify(referralReasonRepository, times(2)).save(any(ReferralReason.class))
        verifyNoMoreInteractions(referralReasonRepository)
    }
}

