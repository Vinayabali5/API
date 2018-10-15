package uk.ac.reigate.services

import static org.junit.Assert.assertNull
import static org.junit.Assert.assertTrue
import static org.mockito.Mockito.*

import org.junit.Before
import org.junit.Test
import org.mockito.Mockito

import uk.ac.reigate.domain.academic.Period
import uk.ac.reigate.model.SearchResult
import uk.ac.reigate.repositories.academic.PeriodRepository;
import uk.ac.reigate.services.PeriodService;


class PeriodServiceTest {
    
    private PeriodRepository periodRepository;
    
    private PeriodService periodService;
    
    Period period1
    
    Period period2
    
    @Before
    public void setup() {
        this.periodRepository = mock(PeriodRepository.class);
        this.periodService = new PeriodService(periodRepository);
        
        period1 = new Period(id: 1, code:'A', description:'A Period', startTime: null,  endTime: null, day: 11, dayPeriod: 1)
        period2 = new Period(id: 2, code:'B', description:'B Period', startTime: null,  endTime: null, day: 12, dayPeriod: 2)
        
        when(periodRepository.findAll()).thenReturn([period1, period2]);
        when(periodRepository.findOne(1)).thenReturn(period1);
        when(periodRepository.findOne(null)).thenThrow(new IllegalArgumentException());
        when(periodRepository.save(any(Period.class))).thenReturn(period1);
    }
    
    
    @Test
    public void testFindPeriod() {
        List<Period> result = periodService.findAll();
        verify(periodRepository, times(1)).findAll()
        verifyNoMoreInteractions(periodRepository)
    }
    
    @Test
    public void testFindPeriodById() {
        Period result = periodService.findById(1);
        verify(periodRepository, times(1)).findOne(1)
        verifyNoMoreInteractions(periodRepository)
    }
    
    @Test
    public void testSaveNewPeriod() {
        period1.id = null
        periodService.save(period1);
        verify(periodRepository, times(1)).save(period1)
        verifyNoMoreInteractions(periodRepository)
    }
    
    @Test
    public void testSavePeriod() {
        periodService.save(period1);
        verify(periodRepository, times(1)).save(period1)
        verifyNoMoreInteractions(periodRepository)
    }
}

