package uk.ac.reigate.services

import static org.junit.Assert.assertNull
import static org.junit.Assert.assertTrue
import static org.mockito.Mockito.*

import org.junit.Before
import org.junit.Test
import org.mockito.Mockito

import uk.ac.reigate.domain.ilr.EnglishConditionOfFunding
import uk.ac.reigate.model.SearchResult
import uk.ac.reigate.repositories.ilr.EnglishConditionOfFundingRepository

class EnglishConditionOfFundingServiceTest {
    
    private EnglishConditionOfFundingRepository englishConditionOfFundingRepository
    
    private EnglishConditionOfFundingService englishConditionOfFundingService;
    
    EnglishConditionOfFunding englishConditionOfFunding1;
    EnglishConditionOfFunding englishConditionOfFunding2;
    
    @Before
    public void setup() {
        this.englishConditionOfFundingRepository = Mockito.mock(EnglishConditionOfFundingRepository.class)
        this.englishConditionOfFundingService = new EnglishConditionOfFundingService(englishConditionOfFundingRepository);
        
        englishConditionOfFunding1 = new EnglishConditionOfFunding(id: 1, code: 'N', description: 'New', shortDescription: 'New', validFrom: new Date().parse('yyyy/MM/dd', '2011/11/11'), validTo: new Date().parse('yyyy/MM/dd', '2016/11/11'))
        englishConditionOfFunding2 = new EnglishConditionOfFunding(id: 2, code: 'C', description: 'Complete', shortDescription: 'Complete', validFrom: new Date().parse('yyyy/MM/dd', '2013/11/11'), validTo: new Date().parse('yyyy/MM/dd', '2015/11/11') )
        
        when(englishConditionOfFundingRepository.findAll()).thenReturn([
            englishConditionOfFunding1,
            englishConditionOfFunding2
        ]);
        when(englishConditionOfFundingRepository.findOne(1)).thenReturn(englishConditionOfFunding1);
        when(englishConditionOfFundingRepository.findOne(null)).thenThrow(new IllegalArgumentException());
        when(englishConditionOfFundingRepository.save(any(EnglishConditionOfFunding.class))).thenReturn(englishConditionOfFunding1);
    }
    
    @Test
    public void testFindEnglishConditionOfFundings() {
        List<EnglishConditionOfFunding> result = englishConditionOfFundingService.findAll();
        verify(englishConditionOfFundingRepository, times(1)).findAll()
        verifyNoMoreInteractions(englishConditionOfFundingRepository)
    }
    
    @Test
    public void testFindEnglishConditionOfFunding() {
        EnglishConditionOfFunding result = englishConditionOfFundingService.findById(1);
        verify(englishConditionOfFundingRepository, times(1)).findOne(1)
        verifyNoMoreInteractions(englishConditionOfFundingRepository)
    }
    
    @Test
    public void testSaveNewEnglishConditionOfFunding() {
        EnglishConditionOfFunding savedEnglishConditionOfFunding = englishConditionOfFundingService.save(englishConditionOfFunding1);
        verify(englishConditionOfFundingRepository, times(1)).save(any())
        verifyNoMoreInteractions(englishConditionOfFundingRepository)
    }
    
    @Test
    public void testSaveEnglishConditionOfFundings() {
        List<EnglishConditionOfFunding> savedEnglishConditionOfFundings = englishConditionOfFundingService.saveEnglishConditionOfFundings([
            englishConditionOfFunding1,
            englishConditionOfFunding2
        ]);
        verify(englishConditionOfFundingRepository, times(2)).save(any(EnglishConditionOfFunding.class))
        verifyNoMoreInteractions(englishConditionOfFundingRepository)
    }
    
    @Test
    public void testSaveEnglishConditionOfFundingByFields_WithNullId() {
        EnglishConditionOfFunding savedEnglishConditionOfFunding = englishConditionOfFundingService.saveEnglishConditionOfFunding(null, englishConditionOfFunding1.code, englishConditionOfFunding1.description, englishConditionOfFunding1.shortDescription, englishConditionOfFunding1.validFrom, englishConditionOfFunding1.validTo);
        verify(englishConditionOfFundingRepository, times(1)).save(any())
        verifyNoMoreInteractions(englishConditionOfFundingRepository)
    }
    
    
    @Test
    public void testSaveEnglishConditionOfFundingByFields_WithId() {
        EnglishConditionOfFunding savedEnglishConditionOfFunding = englishConditionOfFundingService.saveEnglishConditionOfFunding(1, englishConditionOfFunding1.code, englishConditionOfFunding1.description, englishConditionOfFunding1.shortDescription, englishConditionOfFunding1.validFrom, englishConditionOfFunding1.validTo);
        verify(englishConditionOfFundingRepository, times(1)).findOne(1)
        verify(englishConditionOfFundingRepository, times(1)).save(any())
        verifyNoMoreInteractions(englishConditionOfFundingRepository)
    }
}

