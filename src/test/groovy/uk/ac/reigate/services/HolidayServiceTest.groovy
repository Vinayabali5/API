package uk.ac.reigate.services

import static org.mockito.Mockito.*

import org.junit.Before
import org.junit.Test

import uk.ac.reigate.domain.academic.Holiday
import uk.ac.reigate.model.SearchResult
import uk.ac.reigate.repositories.academic.HolidayRepository;


class HolidayServiceTest {
    
    private HolidayRepository holidayRepository;
    
    private HolidayService holidayService;
    
    Holiday holiday1
    
    Holiday holiday2
    
    @Before
    public void setup() {
        this.holidayRepository = mock(HolidayRepository.class);
        this.holidayService = new HolidayService(holidayRepository);
        
        holiday1 = new Holiday(id: 1, description: 'Academic Year 15/16', startDate: new Date().parse('yyyy/MM/dd', '2011/07/09'), endDate: new Date().parse('yyyy/MM/dd', '2013/07/09'), halfTerm: true)
        holiday2 = new Holiday(id: 2, description: 'Academic Year 12/16', startDate: new Date().parse('yyyy/MM/dd', '2015/07/09'), endDate: new Date().parse('yyyy/MM/dd', '2016/07/09'), halfTerm: true)
        
        when(holidayRepository.findAll()).thenReturn([holiday1, holiday2]);
        when(holidayRepository.findOne(1)).thenReturn(holiday1);
        when(holidayRepository.findOne(null)).thenThrow(new IllegalArgumentException());
        when(holidayRepository.save(any(Holiday.class))).thenReturn(holiday1);
    }
    
    
    @Test
    public void testFindHoliday() {
        List<Holiday> result = holidayService.findAll();
        verify(holidayRepository, times(1)).findAll()
        verifyNoMoreInteractions(holidayRepository)
    }
    
    @Test
    public void testFindHolidayById() {
        Holiday result = holidayService.findById(1);
        verify(holidayRepository, times(1)).findOne(1)
        verifyNoMoreInteractions(holidayRepository)
    }
    
    @Test
    public void testSaveNewHoliday() {
        holiday1.id = null
        holidayService.save(holiday1);
        verify(holidayRepository, times(1)).save(holiday1)
        verifyNoMoreInteractions(holidayRepository)
    }
    
    @Test
    public void testSaveHoliday() {
        holidayService.save(holiday1);
        verify(holidayRepository, times(1)).save(holiday1)
        verifyNoMoreInteractions(holidayRepository)
    }
}

