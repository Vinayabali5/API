package uk.ac.reigate.services

import static org.mockito.Mockito.*

import org.junit.Before
import org.junit.Test
import org.mockito.Mockito

import uk.ac.reigate.domain.admissions.CollegeFundPaid
import uk.ac.reigate.model.SearchResult
import uk.ac.reigate.repositories.admissions.CollegeFundPaidRepository


class CollegeFundPaidServiceTest {
    
    private CollegeFundPaidRepository collegeFundPaidRepository
    
    private CollegeFundPaidService collegeFundPaidService;
    
    CollegeFundPaid collegeFundPaid1
    CollegeFundPaid collegeFundPaid2
    
    @Before
    public void setup() {
        this.collegeFundPaidRepository = Mockito.mock(CollegeFundPaidRepository.class);
        this.collegeFundPaidService = new CollegeFundPaidService(collegeFundPaidRepository);
        
        collegeFundPaid1 = new CollegeFundPaid(id: 1, collegeFundPaid : 'Yes')
        collegeFundPaid2 = new CollegeFundPaid(id: 2, collegeFundPaid : 'No')
        
        when(collegeFundPaidRepository.findAll()).thenReturn([
            collegeFundPaid1,
            collegeFundPaid2
        ]);
        when(collegeFundPaidRepository.findOne(1)).thenReturn(collegeFundPaid1);
        when(collegeFundPaidRepository.findOne(null)).thenThrow(new IllegalArgumentException());
        when(collegeFundPaidRepository.save(any(CollegeFundPaid.class))).thenReturn(collegeFundPaid1);
    }
    
    @Test
    public void testFindCollegeFundPaids() {
        List<CollegeFundPaid> result = collegeFundPaidService.findAll();
        verify(collegeFundPaidRepository, times(1)).findAll()
        verifyNoMoreInteractions(collegeFundPaidRepository)
    }
    
    @Test
    public void testFindCollegeFundPaid() {
        CollegeFundPaid result = collegeFundPaidService.findById(1);
        verify(collegeFundPaidRepository, times(1)).findOne(1)
        verifyNoMoreInteractions(collegeFundPaidRepository)
    }
    
    @Test
    public void testSaveNewCollegeFundPaid() {
        CollegeFundPaid savedCollegeFundPaid = collegeFundPaidService.save(collegeFundPaid1);
        verify(collegeFundPaidRepository, times(1)).save(any())
        verifyNoMoreInteractions(collegeFundPaidRepository)
    }
    
    @Test
    public void testSaveCollegeFundPaid() {
        CollegeFundPaid savedCollegeFundPaid = collegeFundPaidService.save(collegeFundPaid1);
        verify(collegeFundPaidRepository, times(1)).save(any())
        verifyNoMoreInteractions(collegeFundPaidRepository)
    }
    
    @Test
    public void testSaveCollegeFundPaids() {
        List<CollegeFundPaid> savedCollegeFundPaids = collegeFundPaidService.saveCollegeFundPaids([
            collegeFundPaid1,
            collegeFundPaid2
        ]);
        verify(collegeFundPaidRepository, times(2)).save(any(CollegeFundPaid.class))
        verifyNoMoreInteractions(collegeFundPaidRepository)
    }
    
    @Test
    public void testSaveCollegeFundPaidByFields_WithNullId() {
        CollegeFundPaid savedCollegeFundPaid = collegeFundPaidService.saveCollegeFundPaid(null, collegeFundPaid1.collegeFundPaid);
        verify(collegeFundPaidRepository, times(1)).save(any())
        verifyNoMoreInteractions(collegeFundPaidRepository)
    }
    
    
    @Test
    public void testSaveCollegeFundPaidByFields_WithId() {
        CollegeFundPaid savedCollegeFundPaid = collegeFundPaidService.saveCollegeFundPaid(1, collegeFundPaid1.collegeFundPaid);
        verify(collegeFundPaidRepository, times(1)).findOne(1)
        verify(collegeFundPaidRepository, times(1)).save(any())
        verifyNoMoreInteractions(collegeFundPaidRepository)
    }
}

