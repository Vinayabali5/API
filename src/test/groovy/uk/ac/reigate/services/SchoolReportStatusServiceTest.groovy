package uk.ac.reigate.services

import static org.junit.Assert.assertNull
import static org.junit.Assert.assertTrue
import static org.mockito.Mockito.*

import org.junit.Before
import org.junit.Test
import org.mockito.Mockito

import uk.ac.reigate.domain.lookup.SchoolReportStatus
import uk.ac.reigate.model.SearchResult
import uk.ac.reigate.repositories.lookup.SchoolReportStatusRepository
import uk.ac.reigate.services.SchoolReportStatusService;


class SchoolReportStatusServiceTest {
    
    private SchoolReportStatusRepository schoolReportStatusRepository;
    
    private SchoolReportStatusService schoolReportStatusService;
    
    SchoolReportStatus schoolReportStatus1
    SchoolReportStatus schoolReportStatus2
    
    @Before
    public void setup() {
        this.schoolReportStatusRepository = Mockito.mock(SchoolReportStatusRepository.class);
        this.schoolReportStatusService = new SchoolReportStatusService(schoolReportStatusRepository);
        
        schoolReportStatus1 = new SchoolReportStatus(id: 1, code: 'M', description: 'Male')
        schoolReportStatus2 = new SchoolReportStatus(id: 2, code: 'F', description: 'Female')
        
        when(schoolReportStatusRepository.findAll()).thenReturn([
            schoolReportStatus1,
            schoolReportStatus2
        ]);
        when(schoolReportStatusRepository.findOne(1)).thenReturn(schoolReportStatus1);
        when(schoolReportStatusRepository.findOne(null)).thenThrow(new IllegalArgumentException());
        when(schoolReportStatusRepository.save(any(SchoolReportStatus.class))).thenReturn(schoolReportStatus1);
    }
    
    @Test
    public void testFindSchoolReportStatuss() {
        List<SchoolReportStatus> result = schoolReportStatusService.findAll();
        verify(schoolReportStatusRepository, times(1)).findAll()
        verifyNoMoreInteractions(schoolReportStatusRepository)
    }
    
    @Test
    public void testFindSchoolReportStatus() {
        SchoolReportStatus result = schoolReportStatusService.findById(1);
        verify(schoolReportStatusRepository, times(1)).findOne(1)
        verifyNoMoreInteractions(schoolReportStatusRepository)
    }
    
    @Test
    public void testSaveNewSchoolReportStatus() {
        SchoolReportStatus savedSchoolReportStatus = schoolReportStatusService.save(schoolReportStatus1);
        verify(schoolReportStatusRepository, times(1)).save(any())
        verifyNoMoreInteractions(schoolReportStatusRepository)
    }
    
    @Test
    public void testSaveSchoolReportStatus() {
        SchoolReportStatus savedSchoolReportStatus = schoolReportStatusService.save(schoolReportStatus1);
        verify(schoolReportStatusRepository, times(1)).save(any())
        verifyNoMoreInteractions(schoolReportStatusRepository)
    }
    
    @Test
    public void testSaveSchoolReportStatuss() {
        List<SchoolReportStatus> savedSchoolReportStatuss = schoolReportStatusService.saveSchoolReportStatuss([
            schoolReportStatus1,
            schoolReportStatus2
        ]);
        verify(schoolReportStatusRepository, times(2)).save(any(SchoolReportStatus.class))
        verifyNoMoreInteractions(schoolReportStatusRepository)
    }
    
    @Test
    public void testSaveSchoolReportStatusByFields_WithNullId() {
        SchoolReportStatus savedSchoolReportStatus = schoolReportStatusService.saveSchoolReportStatus(null, schoolReportStatus1.code, schoolReportStatus1.description);
        verify(schoolReportStatusRepository, times(1)).save(any())
        verifyNoMoreInteractions(schoolReportStatusRepository)
    }
    
    
    @Test
    public void testSaveSchoolReportStatusByFields_WithId() {
        SchoolReportStatus savedSchoolReportStatus = schoolReportStatusService.saveSchoolReportStatus(1, schoolReportStatus1.code, schoolReportStatus1.description);
        verify(schoolReportStatusRepository, times(1)).findOne(1)
        verify(schoolReportStatusRepository, times(1)).save(any())
        verifyNoMoreInteractions(schoolReportStatusRepository)
    }
}

