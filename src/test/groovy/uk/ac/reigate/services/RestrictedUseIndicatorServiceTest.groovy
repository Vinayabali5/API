package uk.ac.reigate.services

import static org.junit.Assert.assertNull
import static org.junit.Assert.assertTrue
import static org.mockito.Mockito.*

import org.junit.Before
import org.junit.Test
import org.mockito.Mockito
import uk.ac.reigate.domain.ilr.RestrictedUseIndicator;
import uk.ac.reigate.domain.ilr.RestrictedUseIndicator
import uk.ac.reigate.model.SearchResult
import uk.ac.reigate.repositories.ilr.RestrictedUseIndicatorRepository


class RestrictedUseIndicatorServiceTest {
    
    private RestrictedUseIndicatorRepository restrictedUseIndicatorRepository;
    
    private RestrictedUseIndicatorService restrictedUseIndicatorService;
    
    RestrictedUseIndicator restrictedUseIndicator1;
    RestrictedUseIndicator restrictedUseIndicator2;
    
    @Before
    public void setup() {
        this.restrictedUseIndicatorRepository = Mockito.mock(RestrictedUseIndicatorRepository.class)
        this.restrictedUseIndicatorService = new RestrictedUseIndicatorService(restrictedUseIndicatorRepository);
        
        restrictedUseIndicator1 = new RestrictedUseIndicator(id: 1, code: 'N', description: 'New', shortDescription: 'New', validFrom: new Date().parse('yyyy/MM/dd', '2011/11/11'), validTo: new Date().parse('yyyy/MM/dd', '2016/11/11'))
        restrictedUseIndicator2 = new RestrictedUseIndicator(id: 2, code: 'C', description: 'Complete', shortDescription: 'Complete', validFrom: new Date().parse('yyyy/MM/dd', '2013/11/11'), validTo: new Date().parse('yyyy/MM/dd', '2015/11/11') )
        when(restrictedUseIndicatorRepository.findAll()).thenReturn([
            restrictedUseIndicator1,
            restrictedUseIndicator2
        ]);
        when(restrictedUseIndicatorRepository.findOne(1)).thenReturn(restrictedUseIndicator1);
        when(restrictedUseIndicatorRepository.findOne(null)).thenThrow(new IllegalArgumentException());
        when(restrictedUseIndicatorRepository.save(any(RestrictedUseIndicator.class))).thenReturn(restrictedUseIndicator1);
    }
    
    @Test
    public void testFindRestrictedUseIndicators() {
        List<RestrictedUseIndicator> result = restrictedUseIndicatorService.findAll();
        verify(restrictedUseIndicatorRepository, times(1)).findAll()
        verifyNoMoreInteractions(restrictedUseIndicatorRepository)
    }
    
    @Test
    public void testFindRestrictedUseIndicator() {
        RestrictedUseIndicator result = restrictedUseIndicatorService.findById(1);
        verify(restrictedUseIndicatorRepository, times(1)).findOne(1)
        verifyNoMoreInteractions(restrictedUseIndicatorRepository)
    }
    
    @Test
    public void testSaveNewRestrictedUseIndicator() {
        RestrictedUseIndicator savedRestrictedUseIndicator = restrictedUseIndicatorService.save(restrictedUseIndicator1);
        verify(restrictedUseIndicatorRepository, times(1)).save(any())
        verifyNoMoreInteractions(restrictedUseIndicatorRepository)
    }
    
    @Test
    public void testSaveRestrictedUseIndicator() {
        RestrictedUseIndicator savedRestrictedUseIndicator = restrictedUseIndicatorService.save(restrictedUseIndicator1);
        verify(restrictedUseIndicatorRepository, times(1)).save(any())
        verifyNoMoreInteractions(restrictedUseIndicatorRepository)
    }
    
    @Test
    public void testSaveRestrictedUseIndicators() {
        List<RestrictedUseIndicator> savedRestrictedUseIndicators = restrictedUseIndicatorService.saveRestrictedUseIndicators([
            restrictedUseIndicator1,
            restrictedUseIndicator2
        ]);
        verify(restrictedUseIndicatorRepository, times(2)).save(any(RestrictedUseIndicator.class))
        verifyNoMoreInteractions(restrictedUseIndicatorRepository)
    }
    
    @Test
    public void testSaveRestrictedUseIndicatorByFields_WithNullId() {
        RestrictedUseIndicator savedRestrictedUseIndicator = restrictedUseIndicatorService.saveRestrictedUseIndicator(null, restrictedUseIndicator1.code, restrictedUseIndicator1.description, restrictedUseIndicator1.shortDescription, restrictedUseIndicator1.validFrom, restrictedUseIndicator1.validTo);
        verify(restrictedUseIndicatorRepository, times(1)).save(any())
        verifyNoMoreInteractions(restrictedUseIndicatorRepository)
    }
    
    
    @Test
    public void testSaveRestrictedUseIndicatorByFields_WithId() {
        RestrictedUseIndicator savedRestrictedUseIndicator = restrictedUseIndicatorService.saveRestrictedUseIndicator(1, restrictedUseIndicator1.code, restrictedUseIndicator1.description, restrictedUseIndicator1.shortDescription, restrictedUseIndicator1.validFrom, restrictedUseIndicator1.validTo);
        verify(restrictedUseIndicatorRepository, times(1)).findOne(1)
        verify(restrictedUseIndicatorRepository, times(1)).save(any())
        verifyNoMoreInteractions(restrictedUseIndicatorRepository)
    }
}

