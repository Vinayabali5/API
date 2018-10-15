package uk.ac.reigate.services

import static org.junit.Assert.assertNull
import static org.junit.Assert.assertTrue
import static org.mockito.Mockito.*

import org.junit.Before
import org.junit.Test
import org.mockito.Mockito

import uk.ac.reigate.domain.academic.AcademicYear
import uk.ac.reigate.model.SearchResult
import uk.ac.reigate.repositories.academic.AcademicYearRepository

class AcademicYearServiceTest {
    
    private AcademicYearRepository academicYearRepository
    
    private AcademicYearService academicYearService;
    
    AcademicYear academicYear1
    AcademicYear academicYear2
    
    @Before
    public void setup() {
        this.academicYearRepository = mock(AcademicYearRepository.class);
        this.academicYearService = new AcademicYearService(academicYearRepository);
        
        academicYear1 = new AcademicYear(id: 1, code: '15/16', description: '2015/16 Academic Year', startDate: new Date(2015, 9, 1), endDate: new Date(2016, 8, 30))
        academicYear2 = new AcademicYear(id: 2, code: '16/17', description: '2016/17 Academic Year', startDate: new Date(2016, 9, 1), endDate: new Date(2017, 8, 30))
        
        when(academicYearRepository.findAll()).thenReturn([academicYear1, academicYear2]);
        when(academicYearRepository.findOne(1)).thenReturn(academicYear1);
        when(academicYearRepository.findOne(null)).thenThrow(new IllegalArgumentException());
        when(academicYearRepository.save(any(AcademicYear.class))).thenReturn(academicYear1);
    }
    
    @Test
    public void testFindAcademicYears() {
        List<AcademicYear> result = academicYearService.findAll();
        verify(academicYearRepository, times(1)).findAll()
        verifyNoMoreInteractions(academicYearRepository)
    }
    
    @Test
    public void testFindAcademicYear() {
        AcademicYear result = academicYearService.findById(1);
        verify(academicYearRepository, times(1)).findOne(1)
        verifyNoMoreInteractions(academicYearRepository)
    }
    
    @Test
    public void testSaveNewAcademicYear() {
        AcademicYear savedAcademicYear = academicYearService.save(academicYear1);
        verify(academicYearRepository, times(1)).save(any())
        verifyNoMoreInteractions(academicYearRepository)
    }
    
    @Test
    public void testSaveAcademicYears() {
        List<AcademicYear> savedAcademicYears = academicYearService.saveAcademicYears([academicYear1, academicYear2]);
        verify(academicYearRepository, times(2)).save(any(AcademicYear.class))
        verifyNoMoreInteractions(academicYearRepository)
    }
}

