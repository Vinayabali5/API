package uk.ac.reigate.services

import static org.junit.Assert.assertNull
import static org.junit.Assert.assertTrue
import static org.mockito.Mockito.*

import org.junit.Before
import org.junit.Test
import org.mockito.Mockito
import uk.ac.reigate.domain.ilr.LLDDHealthProblemCategory;
import uk.ac.reigate.domain.ilr.LLDDHealthProblemCategory
import uk.ac.reigate.model.SearchResult
import uk.ac.reigate.repositories.ilr.LLDDHealthProblemCategoryRepository
import uk.ac.reigate.services.LLDDHealthProblemCategoryService;


class LLDDHealthProblemCategoryServiceTest {
    
    private LLDDHealthProblemCategoryRepository lLDDHealthProblemCategoryRepository;
    
    private LLDDHealthProblemCategoryService lLDDHealthProblemCategoryService;
    
    LLDDHealthProblemCategory lLDDHealthProblemCategory1;
    LLDDHealthProblemCategory lLDDHealthProblemCategory2;
    
    @Before
    public void setup() {
        this.lLDDHealthProblemCategoryRepository = Mockito.mock(LLDDHealthProblemCategoryRepository.class)
        this.lLDDHealthProblemCategoryService = new LLDDHealthProblemCategoryService(lLDDHealthProblemCategoryRepository);
        
        lLDDHealthProblemCategory1 = new LLDDHealthProblemCategory(id: 1, code: 'N', description: 'New', shortDescription: 'New', validFrom: new Date().parse('yyyy/MM/dd', '2011/11/11'), validTo: new Date().parse('yyyy/MM/dd', '2016/11/11'))
        lLDDHealthProblemCategory2 = new LLDDHealthProblemCategory(id: 2, code: 'C', description: 'Complete', shortDescription: 'Complete', validFrom: new Date().parse('yyyy/MM/dd', '2013/11/11'), validTo: new Date().parse('yyyy/MM/dd', '2015/11/11') )
        when(lLDDHealthProblemCategoryRepository.findAll()).thenReturn([
            lLDDHealthProblemCategory1,
            lLDDHealthProblemCategory2
        ]);
        when(lLDDHealthProblemCategoryRepository.findOne(1)).thenReturn(lLDDHealthProblemCategory1);
        when(lLDDHealthProblemCategoryRepository.findOne(null)).thenThrow(new IllegalArgumentException());
        when(lLDDHealthProblemCategoryRepository.save(any(LLDDHealthProblemCategory.class))).thenReturn(lLDDHealthProblemCategory1);
    }
    
    @Test
    public void testFindLLDDHealthProblemCategories() {
        List<LLDDHealthProblemCategory> result = lLDDHealthProblemCategoryService.findAll();
        verify(lLDDHealthProblemCategoryRepository, times(1)).findAll()
        verifyNoMoreInteractions(lLDDHealthProblemCategoryRepository)
    }
    
    @Test
    public void testFindLLDDHealthProblemCategory() {
        LLDDHealthProblemCategory result = lLDDHealthProblemCategoryService.findById(1);
        verify(lLDDHealthProblemCategoryRepository, times(1)).findOne(1)
        verifyNoMoreInteractions(lLDDHealthProblemCategoryRepository)
    }
    
    @Test
    public void testSaveNewLLDDHealthProblemCategory() {
        LLDDHealthProblemCategory savedLLDDHealthProblemCategory = lLDDHealthProblemCategoryService.save(lLDDHealthProblemCategory1);
        verify(lLDDHealthProblemCategoryRepository, times(1)).save(any())
        verifyNoMoreInteractions(lLDDHealthProblemCategoryRepository)
    }
    
    @Test
    public void testSaveLLDDHealthProblemCategory() {
        LLDDHealthProblemCategory savedLLDDHealthProblemCategory = lLDDHealthProblemCategoryService.save(lLDDHealthProblemCategory1);
        verify(lLDDHealthProblemCategoryRepository, times(1)).save(any())
        verifyNoMoreInteractions(lLDDHealthProblemCategoryRepository)
    }
    
    @Test
    public void testSaveLLDDHealthProblemCategories() {
        List<LLDDHealthProblemCategory> savedLLDDHealthProblemCategories = lLDDHealthProblemCategoryService.saveLLDDHealthProblemCategories([
            lLDDHealthProblemCategory1,
            lLDDHealthProblemCategory2
        ]);
        verify(lLDDHealthProblemCategoryRepository, times(2)).save(any(LLDDHealthProblemCategory.class))
        verifyNoMoreInteractions(lLDDHealthProblemCategoryRepository)
    }
    
    @Test
    public void testSaveLLDDHealthProblemCategoryByFields_WithNullId() {
        LLDDHealthProblemCategory savedLLDDHealthProblemCategory = lLDDHealthProblemCategoryService.saveLLDDHealthProblemCategory(null, lLDDHealthProblemCategory1.code, lLDDHealthProblemCategory1.description, lLDDHealthProblemCategory1.shortDescription, lLDDHealthProblemCategory1.validFrom, lLDDHealthProblemCategory1.validTo);
        verify(lLDDHealthProblemCategoryRepository, times(1)).save(any())
        verifyNoMoreInteractions(lLDDHealthProblemCategoryRepository)
    }
    
    
    @Test
    public void testSaveLLDDHealthProblemCategoryByFields_WithId() {
        LLDDHealthProblemCategory savedLLDDHealthProblemCategory = lLDDHealthProblemCategoryService.saveLLDDHealthProblemCategory(1, lLDDHealthProblemCategory1.code, lLDDHealthProblemCategory1.description, lLDDHealthProblemCategory1.shortDescription, lLDDHealthProblemCategory1.validFrom, lLDDHealthProblemCategory1.validTo);
        verify(lLDDHealthProblemCategoryRepository, times(1)).findOne(1)
        verify(lLDDHealthProblemCategoryRepository, times(1)).save(any())
        verifyNoMoreInteractions(lLDDHealthProblemCategoryRepository)
    }
}

