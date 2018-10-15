package uk.ac.reigate.services

import static org.mockito.Mockito.*

import org.junit.Before
import org.junit.Test
import org.mockito.Mockito

import uk.ac.reigate.domain.lookup.Gender
import uk.ac.reigate.model.SearchResult
import uk.ac.reigate.repositories.lookup.GenderRepository


class GenderServiceTest {
    
    private GenderRepository genderRepository;
    
    private GenderService genderService;
    
    Gender gender1
    Gender gender2
    
    @Before
    public void setup() {
        this.genderRepository = Mockito.mock(GenderRepository.class);
        this.genderService = new GenderService(genderRepository);
        
        gender1 = new Gender(id: 1, code: 'M', description: 'Male')
        gender2 = new Gender(id: 2, code: 'F', description: 'Female')
        
        when(genderRepository.findAll()).thenReturn([gender1, gender2]);
        when(genderRepository.findOne(1)).thenReturn(gender1);
        when(genderRepository.findOne(null)).thenThrow(new IllegalArgumentException());
        when(genderRepository.save(any(Gender.class))).thenReturn(gender1);
    }
    
    @Test
    public void testFindGenders() {
        List<Gender> result = genderService.findAll();
        verify(genderRepository, times(1)).findAll()
        verifyNoMoreInteractions(genderRepository)
    }
    
    @Test
    public void testFindGender() {
        Gender result = genderService.findById(1);
        verify(genderRepository, times(1)).findOne(1)
        verifyNoMoreInteractions(genderRepository)
    }
    
    @Test
    public void testSaveNewGender() {
        Gender savedGender = genderService.save(gender1);
        verify(genderRepository, times(1)).save(any())
        verifyNoMoreInteractions(genderRepository)
    }
    
    @Test
    public void testSaveGender() {
        Gender savedGender = genderService.save(gender1);
        verify(genderRepository, times(1)).save(any())
        verifyNoMoreInteractions(genderRepository)
    }
    
    @Test
    public void testSaveGenders() {
        List<Gender> savedGenders = genderService.saveGenders([gender1, gender2]);
        verify(genderRepository, times(2)).save(any(Gender.class))
        verifyNoMoreInteractions(genderRepository)
    }
    
    @Test
    public void testSaveGenderByFields_WithNullId() {
        Gender savedGender = genderService.saveGender(null, gender1.code, gender1.description);
        verify(genderRepository, times(1)).save(any())
        verifyNoMoreInteractions(genderRepository)
    }
    
    
    @Test
    public void testSaveGenderByFields_WithId() {
        Gender savedGender = genderService.saveGender(1, gender1.code, gender1.description);
        verify(genderRepository, times(1)).findOne(1)
        verify(genderRepository, times(1)).save(any())
        verifyNoMoreInteractions(genderRepository)
    }
}

