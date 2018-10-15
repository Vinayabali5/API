package uk.ac.reigate.services

import static org.mockito.Mockito.*

import org.junit.Before
import org.junit.Test
import org.mockito.Mockito

import uk.ac.reigate.domain.lookup.PunctualityMonitoring
import uk.ac.reigate.model.SearchResult
import uk.ac.reigate.repositories.lookup.PunctualityMonitoringRepository


class PunctualityMonitoringServiceTest {
    
    private PunctualityMonitoringRepository punctualityMonitoringRepository;
    
    private PunctualityMonitoringService punctualityMonitoringService;
    
    PunctualityMonitoring punctualityMonitoring1
    PunctualityMonitoring punctualityMonitoring2
    
    @Before
    public void setup() {
        this.punctualityMonitoringRepository = Mockito.mock(PunctualityMonitoringRepository.class);
        this.punctualityMonitoringService = new PunctualityMonitoringService(punctualityMonitoringRepository);
        
        punctualityMonitoring1 = new PunctualityMonitoring(id: 1, code: 'G', description: 'Good', warningColour: 'red')
        punctualityMonitoring2 = new PunctualityMonitoring(id: 2, code: 'B', description: 'Bad', warningColour: 'red')
        
        when(punctualityMonitoringRepository.findAll()).thenReturn([
            punctualityMonitoring1,
            punctualityMonitoring2
        ]);
        when(punctualityMonitoringRepository.findOne(1)).thenReturn(punctualityMonitoring1);
        when(punctualityMonitoringRepository.findOne(null)).thenThrow(new IllegalArgumentException());
        when(punctualityMonitoringRepository.save(any(PunctualityMonitoring.class))).thenReturn(punctualityMonitoring1);
    }
    
    @Test
    public void testFindPunctualityMonitorings() {
        List<PunctualityMonitoring> result = punctualityMonitoringService.findAll();
        verify(punctualityMonitoringRepository, times(1)).findAll()
        verifyNoMoreInteractions(punctualityMonitoringRepository)
    }
    
    @Test
    public void testFindPunctualityMonitoring() {
        PunctualityMonitoring result = punctualityMonitoringService.findById(1);
        verify(punctualityMonitoringRepository, times(1)).findOne(1)
        verifyNoMoreInteractions(punctualityMonitoringRepository)
    }
    
    @Test
    public void testSaveNewPunctualityMonitoring() {
        PunctualityMonitoring savedPunctualityMonitoring = punctualityMonitoringService.save(punctualityMonitoring1);
        verify(punctualityMonitoringRepository, times(1)).save(any())
        verifyNoMoreInteractions(punctualityMonitoringRepository)
    }
    
    @Test
    public void testSavePunctualityMonitoring() {
        PunctualityMonitoring savedPunctualityMonitoring = punctualityMonitoringService.save(punctualityMonitoring1);
        verify(punctualityMonitoringRepository, times(1)).save(any())
        verifyNoMoreInteractions(punctualityMonitoringRepository)
    }
    
    @Test
    public void testSavePunctualityMonitorings() {
        List<PunctualityMonitoring> savedPunctualityMonitorings = punctualityMonitoringService.savePunctualityMonitorings([
            punctualityMonitoring1,
            punctualityMonitoring2
        ]);
        verify(punctualityMonitoringRepository, times(2)).save(any(PunctualityMonitoring.class))
        verifyNoMoreInteractions(punctualityMonitoringRepository)
    }
    
    @Test
    public void testSavePunctualityMonitoringByFields_WithNullId() {
        PunctualityMonitoring savedPunctualityMonitoring = punctualityMonitoringService.savePunctualityMonitoring(null, punctualityMonitoring1.code, punctualityMonitoring1.description, punctualityMonitoring1.warningColour);
        verify(punctualityMonitoringRepository, times(1)).save(any())
        verifyNoMoreInteractions(punctualityMonitoringRepository)
    }
    
    
    @Test
    public void testSavePunctualityMonitoringByFields_WithId() {
        PunctualityMonitoring savedPunctualityMonitoring = punctualityMonitoringService.savePunctualityMonitoring(1, punctualityMonitoring1.code, punctualityMonitoring1.description, punctualityMonitoring1.warningColour);
        verify(punctualityMonitoringRepository, times(1)).findOne(1)
        verify(punctualityMonitoringRepository, times(1)).save(any())
        verifyNoMoreInteractions(punctualityMonitoringRepository)
    }
}

