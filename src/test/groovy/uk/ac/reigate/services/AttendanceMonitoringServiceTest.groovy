package uk.ac.reigate.services

import static org.mockito.Mockito.*

import org.junit.Before
import org.junit.Test
import org.mockito.Mockito

import uk.ac.reigate.domain.lookup.AttendanceMonitoring
import uk.ac.reigate.model.SearchResult
import uk.ac.reigate.repositories.lookup.AttendanceMonitoringRepository


class AttendanceMonitoringServiceTest {
    
    private AttendanceMonitoringRepository attendanceMonitoringRepository;
    
    private AttendanceMonitoringService attendanceMonitoringService;
    
    AttendanceMonitoring attendanceMonitoring1
    AttendanceMonitoring attendanceMonitoring2
    
    @Before
    public void setup() {
        this.attendanceMonitoringRepository = Mockito.mock(AttendanceMonitoringRepository.class);
        this.attendanceMonitoringService = new AttendanceMonitoringService(attendanceMonitoringRepository);
        
        attendanceMonitoring1 = new AttendanceMonitoring(id: 1, code: 'G', description: 'Good', warningColour: 'red')
        attendanceMonitoring2 = new AttendanceMonitoring(id: 2, code: 'B', description: 'Bad', warningColour: 'red')
        
        when(attendanceMonitoringRepository.findAll()).thenReturn([
            attendanceMonitoring1,
            attendanceMonitoring2
        ]);
        when(attendanceMonitoringRepository.findOne(1)).thenReturn(attendanceMonitoring1);
        when(attendanceMonitoringRepository.findOne(null)).thenThrow(new IllegalArgumentException());
        when(attendanceMonitoringRepository.save(any(AttendanceMonitoring.class))).thenReturn(attendanceMonitoring1);
    }
    
    @Test
    public void testFindAttendanceMonitorings() {
        List<AttendanceMonitoring> result = attendanceMonitoringService.findAll();
        verify(attendanceMonitoringRepository, times(1)).findAll()
        verifyNoMoreInteractions(attendanceMonitoringRepository)
    }
    
    @Test
    public void testFindAttendanceMonitoring() {
        AttendanceMonitoring result = attendanceMonitoringService.findById(1);
        verify(attendanceMonitoringRepository, times(1)).findOne(1)
        verifyNoMoreInteractions(attendanceMonitoringRepository)
    }
    
    @Test
    public void testSaveNewAttendanceMonitoring() {
        AttendanceMonitoring savedAttendanceMonitoring = attendanceMonitoringService.save(attendanceMonitoring1);
        verify(attendanceMonitoringRepository, times(1)).save(any())
        verifyNoMoreInteractions(attendanceMonitoringRepository)
    }
    
    @Test
    public void testSaveAttendanceMonitoring() {
        AttendanceMonitoring savedAttendanceMonitoring = attendanceMonitoringService.save(attendanceMonitoring1);
        verify(attendanceMonitoringRepository, times(1)).save(any())
        verifyNoMoreInteractions(attendanceMonitoringRepository)
    }
    
    @Test
    public void testSaveAttendanceMonitorings() {
        List<AttendanceMonitoring> savedAttendanceMonitorings = attendanceMonitoringService.saveAttendanceMonitorings([
            attendanceMonitoring1,
            attendanceMonitoring2
        ]);
        verify(attendanceMonitoringRepository, times(2)).save(any(AttendanceMonitoring.class))
        verifyNoMoreInteractions(attendanceMonitoringRepository)
    }
    
    @Test
    public void testSaveAttendanceMonitoringByFields_WithNullId() {
        AttendanceMonitoring savedAttendanceMonitoring = attendanceMonitoringService.saveAttendanceMonitoring(null, attendanceMonitoring1.code, attendanceMonitoring1.description, attendanceMonitoring1.warningColour);
        verify(attendanceMonitoringRepository, times(1)).save(any())
        verifyNoMoreInteractions(attendanceMonitoringRepository)
    }
    
    
    @Test
    public void testSaveAttendanceMonitoringByFields_WithId() {
        AttendanceMonitoring savedAttendanceMonitoring = attendanceMonitoringService.saveAttendanceMonitoring(1, attendanceMonitoring1.code, attendanceMonitoring1.description, attendanceMonitoring1.warningColour);
        verify(attendanceMonitoringRepository, times(1)).findOne(1)
        verify(attendanceMonitoringRepository, times(1)).save(any())
        verifyNoMoreInteractions(attendanceMonitoringRepository)
    }
}

