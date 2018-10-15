package uk.ac.reigate.services;

import static org.mockito.Mockito.*

import org.junit.Before
import org.junit.Test
import org.mockito.Mockito

import uk.ac.reigate.domain.lookup.SchoolPriority
import uk.ac.reigate.model.SearchResult
import uk.ac.reigate.repositories.lookup.SchoolPriorityRepository


public class SchoolPriorityServiceTest {
    
    private SchoolPriorityRepository schoolPriorityRepository;
    
    private SchoolPriorityService schoolPriorityService;
    
    SchoolPriority schoolPriority1
    SchoolPriority schoolPriority2
    
    @Before
    public void setup() {
        this.schoolPriorityRepository = Mockito.mock(SchoolPriorityRepository.class);
        this.schoolPriorityService = new SchoolPriorityService(schoolPriorityRepository);
        
        schoolPriority1 = new SchoolPriority(id: 1, code: 'A', description: 'A SchoolPriority')
        schoolPriority2 = new SchoolPriority(id: 2, code: 'C', description: 'C SchoolPriority')
        
        when(schoolPriorityRepository.findAll()).thenReturn([
            schoolPriority1,
            schoolPriority2
        ]);
        when(schoolPriorityRepository.findOne(1)).thenReturn(schoolPriority1);
        when(schoolPriorityRepository.findOne(null)).thenThrow(new IllegalArgumentException());
        when(schoolPriorityRepository.save(any(SchoolPriority.class))).thenReturn(schoolPriority1);
    }
    
    @Test
    public void testFindSchoolPriorities() {
        List<SchoolPriority> result = schoolPriorityService.findAll();
        verify(schoolPriorityRepository, times(1)).findAll()
        verifyNoMoreInteractions(schoolPriorityRepository)
    }
    
    @Test
    public void testFindSchoolPriority() {
        SchoolPriority result = schoolPriorityService.findById(1);
        verify(schoolPriorityRepository, times(1)).findOne(1)
        verifyNoMoreInteractions(schoolPriorityRepository)
    }
    
    @Test
    public void testSaveNewSchoolPriority() {
        SchoolPriority savedSchoolPriority = schoolPriorityService.save(schoolPriority1);
        verify(schoolPriorityRepository, times(1)).save(any())
        verifyNoMoreInteractions(schoolPriorityRepository)
    }
    
    @Test
    public void testSaveSchoolPriority() {
        SchoolPriority savedSchoolPriority = schoolPriorityService.save(schoolPriority1);
        verify(schoolPriorityRepository, times(1)).save(any())
        verifyNoMoreInteractions(schoolPriorityRepository)
    }
    
    @Test
    public void testSaveSchoolPriorities() {
        List<SchoolPriority> savedSchoolPrioritys = schoolPriorityService.saveSchoolPriorities([
            schoolPriority1,
            schoolPriority2
        ]);
        verify(schoolPriorityRepository, times(2)).save(any(SchoolPriority.class))
        verifyNoMoreInteractions(schoolPriorityRepository)
    }
    
    @Test
    public void testSaveSchoolPriorityByFields_WithNullId() {
        SchoolPriority savedSchoolPriority = schoolPriorityService.saveSchoolPriority(null, schoolPriority1.code, schoolPriority1.description);
        verify(schoolPriorityRepository, times(1)).save(any())
        verifyNoMoreInteractions(schoolPriorityRepository)
    }
    
    
    @Test
    public void testSaveSchoolPriorityByFields_WithId() {
        SchoolPriority savedSchoolPriority = schoolPriorityService.saveSchoolPriority(1, schoolPriority1.code, schoolPriority1.description);
        verify(schoolPriorityRepository, times(1)).findOne(1)
        verify(schoolPriorityRepository, times(1)).save(any())
        verifyNoMoreInteractions(schoolPriorityRepository)
    }
}

