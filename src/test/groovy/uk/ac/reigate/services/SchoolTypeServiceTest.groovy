package uk.ac.reigate.services;

import static org.mockito.Mockito.*

import org.junit.Before
import org.junit.Test
import org.mockito.Mockito

import uk.ac.reigate.domain.lookup.SchoolType
import uk.ac.reigate.model.SearchResult
import uk.ac.reigate.repositories.lookup.SchoolTypeRepository


public class SchoolTypeServiceTest {
    
    private SchoolTypeRepository schoolTypeRepository;
    
    private SchoolTypeService schoolTypeService;
    
    SchoolType schoolType1
    SchoolType schoolType2
    
    @Before
    public void setup() {
        this.schoolTypeRepository = Mockito.mock(SchoolTypeRepository.class);
        this.schoolTypeService = new SchoolTypeService(schoolTypeRepository);
        
        schoolType1 = new SchoolType(id: 1, code: 'CR', description: 'croydon')
        schoolType2 = new SchoolType(id: 2, code: 'C', description: 'Coulsdon')
        
        when(schoolTypeRepository.findAll()).thenReturn([schoolType1, schoolType2]);
        when(schoolTypeRepository.findOne(1)).thenReturn(schoolType1);
        when(schoolTypeRepository.findOne(null)).thenThrow(new IllegalArgumentException());
        when(schoolTypeRepository.save(any(SchoolType.class))).thenReturn(schoolType1);
    }
    
    @Test
    public void testFindSchoolTypes() {
        List<SchoolType> result = schoolTypeService.findAll();
        verify(schoolTypeRepository, times(1)).findAll()
        verifyNoMoreInteractions(schoolTypeRepository)
    }
    
    @Test
    public void testFindSchoolType() {
        SchoolType result = schoolTypeService.findById(1);
        verify(schoolTypeRepository, times(1)).findOne(1)
        verifyNoMoreInteractions(schoolTypeRepository)
    }
    
    @Test
    public void testSaveNewSchoolType() {
        SchoolType savedSchoolType = schoolTypeService.save(schoolType1);
        verify(schoolTypeRepository, times(1)).save(any())
        verifyNoMoreInteractions(schoolTypeRepository)
    }
    
    @Test
    public void testSaveSchoolType() {
        SchoolType savedSchoolType = schoolTypeService.save(schoolType1);
        verify(schoolTypeRepository, times(1)).save(any())
        verifyNoMoreInteractions(schoolTypeRepository)
    }
    
    @Test
    public void testSaveSchoolTypes() {
        List<SchoolType> savedSchoolTypes = schoolTypeService.saveSchoolTypes([schoolType1, schoolType2]);
        verify(schoolTypeRepository, times(2)).save(any(SchoolType.class))
        verifyNoMoreInteractions(schoolTypeRepository)
    }
    
    @Test
    public void testSaveSchoolTypeByFields_WithNullId() {
        SchoolType savedSchoolType = schoolTypeService.saveSchoolType(null, schoolType1.code, schoolType1.description);
        verify(schoolTypeRepository, times(1)).save(any())
        verifyNoMoreInteractions(schoolTypeRepository)
    }
    
    
    @Test
    public void testSaveSchoolTypeByFields_WithId() {
        SchoolType savedSchoolType = schoolTypeService.saveSchoolType(1, schoolType1.code, schoolType1.description);
        verify(schoolTypeRepository, times(1)).findOne(1)
        verify(schoolTypeRepository, times(1)).save(any())
        verifyNoMoreInteractions(schoolTypeRepository)
    }
}

