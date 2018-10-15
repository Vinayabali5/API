package uk.ac.reigate.services

import static org.junit.Assert.assertNull
import static org.junit.Assert.assertTrue
import static org.mockito.Mockito.*

import org.junit.Before
import org.junit.Test
import org.mockito.Mockito

import uk.ac.reigate.domain.academic.Timetable
import uk.ac.reigate.model.SearchResult
import uk.ac.reigate.repositories.academic.TimetableRepository

class TimetableServiceTest {
    
    private TimetableRepository timetableRepository
    
    private TimetableService timetableService;
    
    Timetable timetable1
    Timetable timetable2
    
    @Before
    public void setup() {
        this.timetableRepository = Mockito.mock(TimetableRepository.class);
        this.timetableService = new TimetableService(timetableRepository);
        
        timetable1 = new Timetable(id: 1, validFrom: new Date(2014, 1, 1), validTo: new Date(2016, 1, 1))
        timetable2 = new Timetable(id: 2, validFrom: new Date(2014, 4, 4), validTo: new Date(2016, 4, 4))
        
        when(timetableRepository.findAll()).thenReturn([timetable1, timetable2]);
        when(timetableRepository.findOne(1)).thenReturn(timetable1);
        when(timetableRepository.save(any(Timetable.class))).thenReturn(timetable1);
    }
    
    @Test
    public void testFindTimetables() {
        List<Timetable> result = timetableService.findAll();
        assertTrue("results not expected, total " + result.size(), result.size() == 2);
        verify(timetableRepository, times(1)).findAll()
        verifyNoMoreInteractions(timetableRepository)
    }
    
    @Test
    public void testFindTimetable() {
        Timetable result = timetableService.findById(1);
        verify(timetableRepository, times(1)).findOne(1)
        verifyNoMoreInteractions(timetableRepository)
    }
    
    @Test
    public void testSaveNewTimetable() {
        timetable1.id = null
        Timetable savedTimetable = timetableService.save(timetable1);
        verify(timetableRepository, times(1)).save(any(Timetable.class))
        verifyNoMoreInteractions(timetableRepository)
    }
    
    @Test
    public void testSaveTimetable() {
        Timetable savedTimetable = timetableService.save(timetable1);
        verify(timetableRepository, times(1)).save(any(Timetable.class))
        verifyNoMoreInteractions(timetableRepository)
    }
}

