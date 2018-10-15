package uk.ac.reigate.services

import static org.junit.Assert.assertNull
import static org.junit.Assert.assertTrue
import static org.mockito.Mockito.*

import org.junit.Before
import org.junit.Test
import org.mockito.Mockito

import uk.ac.reigate.domain.admissions.CollegeFundPayment
import uk.ac.reigate.model.SearchResult
import uk.ac.reigate.repositories.admissions.CollegeFundPaymentRepository

class CollegeFundPaymentServiceTest {
    
    private CollegeFundPaymentRepository collegeFundPaymentRepository
    
    private CollegeFundPaymentService collegeFundPaymentService;
    
    CollegeFundPayment collegeFundPayment1
    CollegeFundPayment collegeFundPayment2
    
    @Before
    public void setup() {
        this.collegeFundPaymentRepository = Mockito.mock(CollegeFundPaymentRepository.class);
        this.collegeFundPaymentService = new CollegeFundPaymentService(collegeFundPaymentRepository);
        
        collegeFundPayment1 = new CollegeFundPayment(id: 1, paymentDate: new Date(2015, 9, 1), payee: 'Vinaya', giftAid: false, cash: true, chequeDate: new Date(2015, 1, 1) )
        collegeFundPayment2 = new CollegeFundPayment(id: 2, paymentDate: new Date(2016, 1, 1), payee: 'Mich', giftAid: false, cash: true, chequeDate: new Date(2016, 1, 1))
        
        when(collegeFundPaymentRepository.findAll()).thenReturn([
            collegeFundPayment1,
            collegeFundPayment2
        ]);
        when(collegeFundPaymentRepository.findOne(1)).thenReturn(collegeFundPayment1);
        when(collegeFundPaymentRepository.save(any(CollegeFundPayment.class))).thenReturn(collegeFundPayment1);
    }
    
    @Test
    public void testFindCollegeFundPayments() {
        List<CollegeFundPayment> result = collegeFundPaymentService.findAll();
        assertTrue("results not expected, total " + result, result.size() == 2);
        verify(collegeFundPaymentRepository, times(1)).findAll()
        verifyNoMoreInteractions(collegeFundPaymentRepository)
    }
    
    @Test
    public void testFindCollegeFundPayment() {
        CollegeFundPayment result = collegeFundPaymentService.findById(1);
        verify(collegeFundPaymentRepository, times(1)).findOne(1)
        verifyNoMoreInteractions(collegeFundPaymentRepository)
    }
    
    @Test
    public void testSaveNewCollegeFundPayment() {
        collegeFundPayment1.id = null
        CollegeFundPayment savedCollegeFundPayment = collegeFundPaymentService.save(collegeFundPayment1);
        verify(collegeFundPaymentRepository, times(1)).save(any(CollegeFundPayment.class))
        verifyNoMoreInteractions(collegeFundPaymentRepository)
    }
    
    @Test
    public void testSaveCollegeFundPayment() {
        CollegeFundPayment savedCollegeFundPayment = collegeFundPaymentService.save(collegeFundPayment1);
        verify(collegeFundPaymentRepository, times(1)).save(any(CollegeFundPayment.class))
        verifyNoMoreInteractions(collegeFundPaymentRepository)
    }
}

