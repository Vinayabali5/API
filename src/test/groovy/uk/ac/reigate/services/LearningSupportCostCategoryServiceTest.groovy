package uk.ac.reigate.services

import static org.junit.Assert.assertNull
import static org.junit.Assert.assertTrue
import static org.mockito.Mockito.*

import org.junit.Before
import org.junit.Test
import org.mockito.Mockito


import uk.ac.reigate.domain.learning_support.LearningSupportCostCategory
import uk.ac.reigate.model.SearchResult
import uk.ac.reigate.repositories.learning_support.LearningSupportCostCategoryRepository
import uk.ac.reigate.services.LearningSupportCostCategoryService;


class LearningSupportCostCategoryServiceTest {
    
    private LearningSupportCostCategoryRepository learningSupportCostCategoryRepository;
    
    private LearningSupportCostCategoryService learningSupportCostCategoryService;
    
    LearningSupportCostCategory learningSupportCostCategory1
    LearningSupportCostCategory learningSupportCostCategory2
    
    @Before
    public void setup() {
        this.learningSupportCostCategoryRepository = Mockito.mock(LearningSupportCostCategoryRepository.class);
        this.learningSupportCostCategoryService = new LearningSupportCostCategoryService(learningSupportCostCategoryRepository);
        
        learningSupportCostCategory1 = new LearningSupportCostCategory(id: 1, category: 'M')
        learningSupportCostCategory2 = new LearningSupportCostCategory(id: 2, category: 'F')
        
        when(learningSupportCostCategoryRepository.findAll()).thenReturn([
            learningSupportCostCategory1,
            learningSupportCostCategory2
        ]);
        when(learningSupportCostCategoryRepository.findOne(1)).thenReturn(learningSupportCostCategory1);
        when(learningSupportCostCategoryRepository.findOne(null)).thenThrow(new IllegalArgumentException());
        when(learningSupportCostCategoryRepository.save(any(LearningSupportCostCategory.class))).thenReturn(learningSupportCostCategory1);
    }
    
    @Test
    public void testFindLearningSupportCostCategorys() {
        List<LearningSupportCostCategory> result = learningSupportCostCategoryService.findAll();
        verify(learningSupportCostCategoryRepository, times(1)).findAll()
        verifyNoMoreInteractions(learningSupportCostCategoryRepository)
    }
    
    @Test
    public void testFindLearningSupportCostCategory() {
        LearningSupportCostCategory result = learningSupportCostCategoryService.findById(1);
        verify(learningSupportCostCategoryRepository, times(1)).findOne(1)
        verifyNoMoreInteractions(learningSupportCostCategoryRepository)
    }
    
    @Test
    public void testSaveNewLearningSupportCostCategory() {
        LearningSupportCostCategory savedLearningSupportCostCategory = learningSupportCostCategoryService.save(learningSupportCostCategory1);
        verify(learningSupportCostCategoryRepository, times(1)).save(any())
        verifyNoMoreInteractions(learningSupportCostCategoryRepository)
    }
    
    @Test
    public void testSaveLearningSupportCostCategory() {
        LearningSupportCostCategory savedLearningSupportCostCategory = learningSupportCostCategoryService.save(learningSupportCostCategory1);
        verify(learningSupportCostCategoryRepository, times(1)).save(any())
        verifyNoMoreInteractions(learningSupportCostCategoryRepository)
    }
    
    @Test
    public void testSaveLearningSupportCostCategorys() {
        List<LearningSupportCostCategory> savedLearningSupportCostCategorys = learningSupportCostCategoryService.saveLearningSupportCostCategorys([
            learningSupportCostCategory1,
            learningSupportCostCategory2
        ]);
        verify(learningSupportCostCategoryRepository, times(2)).save(any(LearningSupportCostCategory.class))
        verifyNoMoreInteractions(learningSupportCostCategoryRepository)
    }
    
    @Test
    public void testSaveLearningSupportCostCategoryByFields_WithNullId() {
        LearningSupportCostCategory savedLearningSupportCostCategory = learningSupportCostCategoryService.saveLearningSupportCostCategory(null, learningSupportCostCategory1.category);
        verify(learningSupportCostCategoryRepository, times(1)).save(any())
        verifyNoMoreInteractions(learningSupportCostCategoryRepository)
    }
    
    
    @Test
    public void testSaveLearningSupportCostCategoryByFields_WithId() {
        LearningSupportCostCategory savedLearningSupportCostCategory = learningSupportCostCategoryService.saveLearningSupportCostCategory(1, learningSupportCostCategory1.category);
        verify(learningSupportCostCategoryRepository, times(1)).findOne(1)
        verify(learningSupportCostCategoryRepository, times(1)).save(any())
        verifyNoMoreInteractions(learningSupportCostCategoryRepository)
    }
}

