package uk.ac.reigate.services

import static org.junit.Assert.assertNull
import static org.junit.Assert.assertTrue
import static org.mockito.Mockito.*

import org.junit.Before
import org.junit.Test
import org.mockito.Mockito

import uk.ac.reigate.domain.ilr.MathsConditionOfFunding
import uk.ac.reigate.model.SearchResult
import uk.ac.reigate.repositories.ilr.MathsConditionOfFundingRepository

class MathsConditionOfFundingServiceTest {
    
    private MathsConditionOfFundingRepository mathsConditionOfFundingRepository
    
    private MathsConditionOfFundingService mathsConditionOfFundingService;
    
    MathsConditionOfFunding mathsConditionOfFunding1;
    MathsConditionOfFunding mathsConditionOfFunding2;
    
    @Before
    public void setup() {
        this.mathsConditionOfFundingRepository = Mockito.mock(MathsConditionOfFundingRepository.class)
        this.mathsConditionOfFundingService = new MathsConditionOfFundingService(mathsConditionOfFundingRepository);
        
        mathsConditionOfFunding1 = new MathsConditionOfFunding(id: 1, code: 'N', description: 'New', shortDescription: 'New', validFrom: new Date().parse('yyyy/MM/dd', '2011/11/11'), validTo: new Date().parse('yyyy/MM/dd', '2016/11/11'))
        mathsConditionOfFunding2 = new MathsConditionOfFunding(id: 2, code: 'C', description: 'Complete', shortDescription: 'Complete', validFrom: new Date().parse('yyyy/MM/dd', '2013/11/11'), validTo: new Date().parse('yyyy/MM/dd', '2015/11/11') )
        
        when(mathsConditionOfFundingRepository.findAll()).thenReturn([
            mathsConditionOfFunding1,
            mathsConditionOfFunding2
        ]);
        when(mathsConditionOfFundingRepository.findOne(1)).thenReturn(mathsConditionOfFunding1);
        when(mathsConditionOfFundingRepository.findOne(null)).thenThrow(new IllegalArgumentException());
        when(mathsConditionOfFundingRepository.save(any(MathsConditionOfFunding.class))).thenReturn(mathsConditionOfFunding1);
    }
    
    @Test
    public void testFindMathsConditionOfFundings() {
        List<MathsConditionOfFunding> result = mathsConditionOfFundingService.findAll();
        verify(mathsConditionOfFundingRepository, times(1)).findAll()
        verifyNoMoreInteractions(mathsConditionOfFundingRepository)
    }
    
    @Test
    public void testFindMathsConditionOfFunding() {
        MathsConditionOfFunding result = mathsConditionOfFundingService.findById(1);
        verify(mathsConditionOfFundingRepository, times(1)).findOne(1)
        verifyNoMoreInteractions(mathsConditionOfFundingRepository)
    }
    
    @Test
    public void testSaveNewMathsConditionOfFunding() {
        MathsConditionOfFunding savedMathsConditionOfFunding = mathsConditionOfFundingService.save(mathsConditionOfFunding1);
        verify(mathsConditionOfFundingRepository, times(1)).save(any())
        verifyNoMoreInteractions(mathsConditionOfFundingRepository)
    }
    
    @Test
    public void testSaveMathsConditionOfFundings() {
        List<MathsConditionOfFunding> savedMathsConditionOfFundings = mathsConditionOfFundingService.saveMathsConditionOfFundings([
            mathsConditionOfFunding1,
            mathsConditionOfFunding2
        ]);
        verify(mathsConditionOfFundingRepository, times(2)).save(any(MathsConditionOfFunding.class))
        verifyNoMoreInteractions(mathsConditionOfFundingRepository)
    }
    
    @Test
    public void testSaveMathsConditionOfFundingByFields_WithNullId() {
        MathsConditionOfFunding savedMathsConditionOfFunding = mathsConditionOfFundingService.saveMathsConditionOfFunding(null, mathsConditionOfFunding1.code, mathsConditionOfFunding1.description, mathsConditionOfFunding1.shortDescription, mathsConditionOfFunding1.validFrom, mathsConditionOfFunding1.validTo);
        verify(mathsConditionOfFundingRepository, times(1)).save(any())
        verifyNoMoreInteractions(mathsConditionOfFundingRepository)
    }
    
    
    @Test
    public void testSaveMathsConditionOfFundingByFields_WithId() {
        MathsConditionOfFunding savedMathsConditionOfFunding = mathsConditionOfFundingService.saveMathsConditionOfFunding(1, mathsConditionOfFunding1.code, mathsConditionOfFunding1.description, mathsConditionOfFunding1.shortDescription, mathsConditionOfFunding1.validFrom, mathsConditionOfFunding1.validTo);
        verify(mathsConditionOfFundingRepository, times(1)).findOne(1)
        verify(mathsConditionOfFundingRepository, times(1)).save(any())
        verifyNoMoreInteractions(mathsConditionOfFundingRepository)
    }
}

