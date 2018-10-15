package uk.ac.reigate.services;

import static org.mockito.Mockito.*

import org.junit.Before
import org.junit.Test

import uk.ac.reigate.domain.academic.School
import uk.ac.reigate.model.SearchResult
import uk.ac.reigate.repositories.SchoolRepository;


public class SchoolServiceTest {
    
    private SchoolRepository schoolRepository;
    
    private SchoolService schoolService;
    
    School school1
    
    School school2
    
    @Before
    public void setup() {
        this.schoolRepository = mock(SchoolRepository.class);
        this.schoolService = new SchoolService(schoolRepository);
        
        school1 = new School(id: 1, name: 'Bishop', urn: '13', line1: 'Mercury', line2: 'Oxford', line3: 'London', postcode: 'E151DD')
        school2 = new School(id: 2, name: 'jude street', urn: '12', line1: 'Mercury', line2: 'victoria', line3: 'London', postcode: 'C161RR' )
        
        when(schoolRepository.findAll()).thenReturn([school1, school2]);
        when(schoolRepository.findOne(1)).thenReturn(school1);
        when(schoolRepository.findOne(null)).thenThrow(new IllegalArgumentException());
        when(schoolRepository.save(any(School.class))).thenReturn(school1);
    }
    
    
    @Test
    public void testFindSchool() {
        List<School> result = schoolService.findAll();
        verify(schoolRepository, times(1)).findAll()
        verifyNoMoreInteractions(schoolRepository)
    }
    
    @Test
    public void testFindSchoolById() {
        School result = schoolService.findById(1);
        verify(schoolRepository, times(1)).findOne(1)
        verifyNoMoreInteractions(schoolRepository)
    }
    
    @Test
    public void testSaveNewSchool() {
        school1.id = null
        schoolService.save(school1);
        verify(schoolRepository, times(1)).save(school1)
        verifyNoMoreInteractions(schoolRepository)
    }
    
    @Test
    public void testSaveSchool() {
        schoolService.save(school1);
        verify(schoolRepository, times(1)).save(school1)
        verifyNoMoreInteractions(schoolRepository)
    }
}

