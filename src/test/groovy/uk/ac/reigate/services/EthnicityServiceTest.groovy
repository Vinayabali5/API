package uk.ac.reigate.services;

import static org.mockito.Mockito.*

import org.junit.Before
import org.junit.Test
import org.mockito.Mockito

import uk.ac.reigate.domain.lookup.Ethnicity
import uk.ac.reigate.model.SearchResult
import uk.ac.reigate.repositories.lookup.EthnicityRepository


public class EthnicityServiceTest {
    
    private EthnicityRepository ethnicityRepository
    
    private EthnicityService ethnicityService;
    
    Ethnicity ethnicity1
    Ethnicity ethnicity2
    
    @Before
    public void setup() {
        this.ethnicityRepository = Mockito.mock(EthnicityRepository.class);
        this.ethnicityService = new EthnicityService(ethnicityRepository);
        
        ethnicity1 = new Ethnicity(id: 1, code: 'E', description: 'Europiean', shortDescription:'European Union', validFrom: new Date().parse('yyyy/MM/dd', '2011/07/09'), validTo: new Date().parse('yyyy/MM/dd', '2013/07/09'))
        ethnicity2 = new Ethnicity(id: 2, code: 'A', description: 'Asian', shortDescription:'Asian Union', validFrom: new Date().parse('yyyy/MM/dd', '2011/07/09'), validTo: new Date().parse('yyyy/MM/dd', '2013/07/09'))
        
        when(ethnicityRepository.findAll()).thenReturn([ethnicity1, ethnicity2]);
        when(ethnicityRepository.findOne(1)).thenReturn(ethnicity1);
        when(ethnicityRepository.findOne(null)).thenThrow(new IllegalArgumentException());
        when(ethnicityRepository.save(any(Ethnicity.class))).thenReturn(ethnicity1);
    }
    
    @Test
    public void testFindEthnicities() {
        List<Ethnicity> result = ethnicityService.findAll();
        verify(ethnicityRepository, times(1)).findAll()
        verifyNoMoreInteractions(ethnicityRepository)
    }
    
    @Test
    public void testFindEthnicity() {
        Ethnicity result = ethnicityService.findById(1);
        verify(ethnicityRepository, times(1)).findOne(1)
        verifyNoMoreInteractions(ethnicityRepository)
    }
    
    @Test
    public void testSaveNewEthnicity() {
        Ethnicity savedEthnicity = ethnicityService.save(ethnicity1);
        verify(ethnicityRepository, times(1)).save(any())
        verifyNoMoreInteractions(ethnicityRepository)
    }
    
    @Test
    public void testSaveEthnicity() {
        Ethnicity savedEthnicity = ethnicityService.save(ethnicity1);
        verify(ethnicityRepository, times(1)).save(any())
        verifyNoMoreInteractions(ethnicityRepository)
    }
    
    @Test
    public void testSaveEthnicities() {
        List<Ethnicity> savedEthnicities = ethnicityService.saveEthnicities([ethnicity1, ethnicity2]);
        verify(ethnicityRepository, times(2)).save(any(Ethnicity.class))
        verifyNoMoreInteractions(ethnicityRepository)
    }
    
    @Test
    public void testSaveEthnicityByFields_WithNullId() {
        Ethnicity savedEthnicity = ethnicityService.saveEthnicity(null, ethnicity1.code, ethnicity1.description, ethnicity1.shortDescription, ethnicity1.validFrom, ethnicity1.validTo);
        verify(ethnicityRepository, times(1)).save(any())
        verifyNoMoreInteractions(ethnicityRepository)
    }
    
    
    @Test
    public void testSaveEthnicityByFields_WithId() {
        Ethnicity savedEthnicity = ethnicityService.saveEthnicity(1, ethnicity1.code, ethnicity1.description, ethnicity1.shortDescription, ethnicity1.validFrom, ethnicity1.validTo);
        verify(ethnicityRepository, times(1)).findOne(1)
        verify(ethnicityRepository, times(1)).save(any())
        verifyNoMoreInteractions(ethnicityRepository)
    }
}
