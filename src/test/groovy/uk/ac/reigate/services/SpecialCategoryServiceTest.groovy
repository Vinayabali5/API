package uk.ac.reigate.services

import static org.junit.Assert.assertNull
import static org.junit.Assert.assertTrue
import static org.mockito.Mockito.*

import org.junit.Before
import org.junit.Test
import org.mockito.Mockito

import uk.ac.reigate.domain.academic.SpecialCategory;
import uk.ac.reigate.domain.academic.SpecialCategory
import uk.ac.reigate.model.SearchResult
import uk.ac.reigate.repositories.academic.SpecialCategoryRepository
import uk.ac.reigate.services.SpecialCategoryService;


class SpecialCategoryServiceTest {
    
    private SpecialCategoryRepository specialCategoryRepository;
    
    private SpecialCategoryService specialCategoryService;
    
    SpecialCategory specialCategory1
    SpecialCategory specialCategory2
    
    @Before
    public void setup() {
        this.specialCategoryRepository = Mockito.mock(SpecialCategoryRepository.class);
        this.specialCategoryService = new SpecialCategoryService(specialCategoryRepository);
        
        specialCategory1 = new SpecialCategory(id: 1, code: 'L', description: 'Long Term Problem', details:'Both mental and physical health including ME')
        specialCategory2 = new SpecialCategory(id: 2, code: 'S', description: 'Short Term Problem', details:'Such as child protection issues.')
        
        when(specialCategoryRepository.findAll()).thenReturn([
            specialCategory1,
            specialCategory2
        ]);
        when(specialCategoryRepository.findOne(1)).thenReturn(specialCategory1);
        when(specialCategoryRepository.findOne(null)).thenThrow(new IllegalArgumentException());
        when(specialCategoryRepository.save(any(SpecialCategory.class))).thenReturn(specialCategory1);
    }
    
    @Test
    public void testFindSpecialCategories() {
        List<SpecialCategory> result = specialCategoryService.findAll();
        verify(specialCategoryRepository, times(1)).findAll()
        verifyNoMoreInteractions(specialCategoryRepository)
    }
    
    @Test
    public void testFindSpecialCategory() {
        SpecialCategory result = specialCategoryService.findById(1);
        verify(specialCategoryRepository, times(1)).findOne(1)
        verifyNoMoreInteractions(specialCategoryRepository)
    }
    
    @Test
    public void testSaveNewSpecialCategory() {
        SpecialCategory savedSpecialCategory = specialCategoryService.save(specialCategory1);
        verify(specialCategoryRepository, times(1)).save(any())
        verifyNoMoreInteractions(specialCategoryRepository)
    }
    
    @Test
    public void testSaveSpecialCategory() {
        SpecialCategory savedSpecialCategory = specialCategoryService.save(specialCategory1);
        verify(specialCategoryRepository, times(1)).save(any())
        verifyNoMoreInteractions(specialCategoryRepository)
    }
    
    @Test
    public void testSaveSpecialCategories() {
        List<SpecialCategory> savedSpecialCategories = specialCategoryService.saveSpecialCategories([
            specialCategory1,
            specialCategory2
        ]);
        verify(specialCategoryRepository, times(2)).save(any(SpecialCategory.class))
        verifyNoMoreInteractions(specialCategoryRepository)
    }
    
    @Test
    public void testSaveSpecialCategoryByFields_WithNullId() {
        SpecialCategory savedSpecialCategory = specialCategoryService.saveSpecialCategory(null, specialCategory1.code, specialCategory1.description, specialCategory1.details, specialCategory1.priority);
        verify(specialCategoryRepository, times(1)).save(any())
        verifyNoMoreInteractions(specialCategoryRepository)
    }
    
    
    @Test
    public void testSaveSpecialCategoryByFields_WithId() {
        SpecialCategory savedSpecialCategory = specialCategoryService.saveSpecialCategory(1, specialCategory1.code, specialCategory1.description, specialCategory1.details, specialCategory1.priority);
        verify(specialCategoryRepository, times(1)).findOne(1)
        verify(specialCategoryRepository, times(1)).save(any())
        verifyNoMoreInteractions(specialCategoryRepository)
    }
}

