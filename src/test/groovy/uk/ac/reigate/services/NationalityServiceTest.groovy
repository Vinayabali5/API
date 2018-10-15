package uk.ac.reigate.services;

import static org.mockito.Mockito.*

import org.junit.Before
import org.junit.Test
import org.mockito.Mockito

import uk.ac.reigate.domain.lookup.Nationality
import uk.ac.reigate.model.SearchResult
import uk.ac.reigate.repositories.lookup.NationalityRepository


public class NationalityServiceTest {
    
    private NationalityRepository nationalityRepository;
    
    private NationalityService nationalityService;
    
    Nationality nationality1
    Nationality nationality2
    
    @Before
    public void setup() {
        this.nationalityRepository = Mockito.mock(NationalityRepository.class);
        this.nationalityService = new NationalityService(nationalityRepository);
        
        nationality1 = new Nationality(id: 1, name: 'A', description: 'A Nationality')
        nationality2 = new Nationality(id: 2, name: 'C', description: 'C Nationality')
        
        when(nationalityRepository.findAll()).thenReturn([nationality1, nationality2]);
        when(nationalityRepository.findOne(1)).thenReturn(nationality1);
        when(nationalityRepository.findOne(null)).thenThrow(new IllegalArgumentException());
        when(nationalityRepository.save(any(Nationality.class))).thenReturn(nationality1);
    }
    
    @Test
    public void testFindNationalities() {
        List<Nationality> result = nationalityService.findAll();
        verify(nationalityRepository, times(1)).findAll()
        verifyNoMoreInteractions(nationalityRepository)
    }
    
    @Test
    public void testFindNationality() {
        Nationality result = nationalityService.findById(1);
        verify(nationalityRepository, times(1)).findOne(1)
        verifyNoMoreInteractions(nationalityRepository)
    }
    
    @Test
    public void testSaveNewNationality() {
        Nationality savedNationality = nationalityService.save(nationality1);
        verify(nationalityRepository, times(1)).save(any())
        verifyNoMoreInteractions(nationalityRepository)
    }
    
    @Test
    public void testSaveNationality() {
        Nationality savedNationality = nationalityService.save(nationality1);
        verify(nationalityRepository, times(1)).save(any())
        verifyNoMoreInteractions(nationalityRepository)
    }
    
    @Test
    public void testSaveNationalities() {
        List<Nationality> savedNationalities = nationalityService.saveNationalities([nationality1, nationality2]);
        verify(nationalityRepository, times(2)).save(any(Nationality.class))
        verifyNoMoreInteractions(nationalityRepository)
    }
    
    @Test
    public void testSaveNationalityByFields_WithNullId() {
        Nationality savedNationality = nationalityService.saveNationality(null, nationality1.name, nationality1.description);
        verify(nationalityRepository, times(1)).save(any())
        verifyNoMoreInteractions(nationalityRepository)
    }
    
    
    @Test
    public void testSaveNationalityByFields_WithId() {
        Nationality savedNationality = nationalityService.saveNationality(1, nationality1.name, nationality1.description);
        verify(nationalityRepository, times(1)).findOne(1)
        verify(nationalityRepository, times(1)).save(any())
        verifyNoMoreInteractions(nationalityRepository)
    }
}

