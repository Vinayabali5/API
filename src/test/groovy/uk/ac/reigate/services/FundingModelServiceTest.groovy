package uk.ac.reigate.services

import static org.mockito.Mockito.*

import org.junit.Before
import org.junit.Test
import org.mockito.Mockito

import uk.ac.reigate.domain.ilr.FundingModel
import uk.ac.reigate.model.SearchResult
import uk.ac.reigate.repositories.ilr.FundingModelRepository


class FundingModelServiceTest {
    
    private FundingModelRepository fundingModelRepository;
    
    private FundingModelService fundingModelService;
    
    FundingModel fundingModel1
    FundingModel fundingModel2
    
    @Before
    public void setup() {
        this.fundingModelRepository = Mockito.mock(FundingModelRepository.class);
        this.fundingModelService = new FundingModelService(fundingModelRepository);
        
        fundingModel1 = new FundingModel(id: 1, code: '15/16', description: '2015/16 Academic Year', validFrom: new Date(2015, 9, 1), validTo: new Date(2016, 8, 30))
        fundingModel2 = new FundingModel(id: 2, code: '16/17', description: '2016/17 Academic Year', validFrom: new Date(2016, 9, 1), validTo: new Date(2017, 8, 30))
        
        when(fundingModelRepository.findAll()).thenReturn([fundingModel1, fundingModel2]);
        when(fundingModelRepository.findOne(1)).thenReturn(fundingModel1);
        when(fundingModelRepository.findOne(null)).thenThrow(new IllegalArgumentException());
        when(fundingModelRepository.save(any(FundingModel.class))).thenReturn(fundingModel1);
    }
    
    @Test
    public void testFindFundingModels() {
        List<FundingModel> result = fundingModelService.findAll();
        verify(fundingModelRepository, times(1)).findAll()
        verifyNoMoreInteractions(fundingModelRepository)
    }
    
    @Test
    public void testFindFundingModel() {
        FundingModel result = fundingModelService.findById(1);
        verify(fundingModelRepository, times(1)).findOne(1)
        verifyNoMoreInteractions(fundingModelRepository)
    }
    
    @Test
    public void testSaveNewFundingModel() {
        FundingModel savedFundingModel = fundingModelService.save(fundingModel1);
        verify(fundingModelRepository, times(1)).save(any())
        verifyNoMoreInteractions(fundingModelRepository)
    }
    
    @Test
    public void testSaveFundingModel() {
        FundingModel savedFundingModel = fundingModelService.save(fundingModel1);
        verify(fundingModelRepository, times(1)).save(any())
        verifyNoMoreInteractions(fundingModelRepository)
    }
    
    @Test
    public void testSaveFundingModels() {
        List<FundingModel> savedFundingModels = fundingModelService.saveFundingModels([fundingModel1, fundingModel2]);
        verify(fundingModelRepository, times(2)).save(any(FundingModel.class))
        verifyNoMoreInteractions(fundingModelRepository)
    }
    
    @Test
    public void testSaveFundingModelByFields_WithNullId() {
        FundingModel savedFundingModel = fundingModelService.saveFundingModel(null, fundingModel1.code, fundingModel1.description, fundingModel1.shortDescription, fundingModel1.validFrom, fundingModel1.validTo);
        verify(fundingModelRepository, times(1)).save(any())
        verifyNoMoreInteractions(fundingModelRepository)
    }
    
    
    @Test
    public void testSaveFundingModelByFields_WithId() {
        FundingModel savedFundingModel = fundingModelService.saveFundingModel(1, fundingModel1.code, fundingModel1.description, fundingModel1.shortDescription, fundingModel1.validFrom, fundingModel1.validTo);
        verify(fundingModelRepository, times(1)).findOne(1)
        verify(fundingModelRepository, times(1)).save(any())
        verifyNoMoreInteractions(fundingModelRepository)
    }
}

