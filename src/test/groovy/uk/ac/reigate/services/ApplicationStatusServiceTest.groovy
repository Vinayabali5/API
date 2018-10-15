package uk.ac.reigate.services

import static org.mockito.Mockito.*

import org.junit.Before
import org.junit.Test
import org.mockito.Mockito

import uk.ac.reigate.domain.admissions.ApplicationStatus
import uk.ac.reigate.model.SearchResult
import uk.ac.reigate.repositories.admissions.ApplicationStatusRepository


class ApplicationStatusServiceTest {
    
    private ApplicationStatusRepository applicationStatusRepository;
    
    private ApplicationStatusService applicationStatusService;
    
    ApplicationStatus applicationStatus1
    ApplicationStatus applicationStatus2
    
    @Before
    public void setup() {
        this.applicationStatusRepository = Mockito.mock(ApplicationStatusRepository.class);
        this.applicationStatusService = new ApplicationStatusService(applicationStatusRepository);
        
        applicationStatus1 = new ApplicationStatus(id: 1, code: 'N', description: 'Normal ApplicationStatus', considerWithdrawn: false)
        applicationStatus2 = new ApplicationStatus(id: 2, code: 'P', description: 'Provisional ApplicationStatus', considerWithdrawn: false)
        
        when(applicationStatusRepository.findAll()).thenReturn([
            applicationStatus1,
            applicationStatus2
        ]);
        when(applicationStatusRepository.findOne(1)).thenReturn(applicationStatus1);
        when(applicationStatusRepository.findOne(null)).thenThrow(new IllegalArgumentException());
        when(applicationStatusRepository.save(any(ApplicationStatus.class))).thenReturn(applicationStatus1);
    }
    
    @Test
    public void testFindApplicationStatuses() {
        List<ApplicationStatus> result = applicationStatusService.findAll();
        verify(applicationStatusRepository, times(1)).findAll()
        verifyNoMoreInteractions(applicationStatusRepository)
    }
    
    @Test
    public void testFindApplicationStatus() {
        ApplicationStatus result = applicationStatusService.findById(1);
        verify(applicationStatusRepository, times(1)).findOne(1)
        verifyNoMoreInteractions(applicationStatusRepository)
    }
    
    @Test
    public void testSaveNewApplicationStatus() {
        ApplicationStatus savedApplicationStatus = applicationStatusService.save(applicationStatus1);
        verify(applicationStatusRepository, times(1)).save(any())
        verifyNoMoreInteractions(applicationStatusRepository)
    }
    
    @Test
    public void testSaveApplicationStatuses() {
        List<ApplicationStatus> savedApplicationStatuses = applicationStatusService.saveApplicationStatuses([
            applicationStatus1,
            applicationStatus2
        ]);
        verify(applicationStatusRepository, times(2)).save(any(ApplicationStatus.class))
        verifyNoMoreInteractions(applicationStatusRepository)
    }
    
    @Test
    public void testSaveApplicationStatusByFields_WithNullId() {
        ApplicationStatus savedApplicationStatus = applicationStatusService.saveApplicationStatus(null, applicationStatus1.code, applicationStatus1.description, applicationStatus1.considerWithdrawn);
        verify(applicationStatusRepository, times(1)).save(any())
        verifyNoMoreInteractions(applicationStatusRepository)
    }
    
    
    @Test
    public void testSaveApplicationStatusByFields_WithId() {
        ApplicationStatus savedApplicationStatus = applicationStatusService.saveApplicationStatus(1, applicationStatus1.code, applicationStatus1.description, applicationStatus1.considerWithdrawn);
        verify(applicationStatusRepository, times(1)).findOne(1)
        verify(applicationStatusRepository, times(1)).save(any())
        verifyNoMoreInteractions(applicationStatusRepository)
    }
}

