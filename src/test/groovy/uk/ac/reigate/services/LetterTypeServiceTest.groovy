package uk.ac.reigate.services

import static org.junit.Assert.assertNull
import static org.junit.Assert.assertTrue
import static org.mockito.Mockito.*

import org.junit.Before
import org.junit.Test
import org.mockito.Mockito

import uk.ac.reigate.domain.ilp.LetterType;
import uk.ac.reigate.model.SearchResult
import uk.ac.reigate.repositories.ilp.LetterTypeRepository
import uk.ac.reigate.services.LetterTypeService;


class LetterTypeServiceTest {
    
    private LetterTypeRepository letterTypeRepository
    
    private LetterTypeService letterTypeService;
    
    LetterType letterType1
    LetterType letterType2
    
    @Before
    public void setup() {
        this.letterTypeRepository = Mockito.mock(LetterTypeRepository.class);
        this.letterTypeService = new LetterTypeService(letterTypeRepository);
        
        letterType1 = new LetterType(id: 1, type: 'A')
        letterType2 = new LetterType(id: 2, type: 'C')
        
        when(letterTypeRepository.findAll()).thenReturn([letterType1, letterType2]);
        when(letterTypeRepository.findOne(1)).thenReturn(letterType1);
        when(letterTypeRepository.findOne(null)).thenThrow(new IllegalArgumentException());
        when(letterTypeRepository.save(any(LetterType.class))).thenReturn(letterType1);
    }
    
    @Test
    public void testFindLetterTypes() {
        List<LetterType> result = letterTypeService.findAll();
        verify(letterTypeRepository, times(1)).findAll()
        verifyNoMoreInteractions(letterTypeRepository)
    }
    
    @Test
    public void testFindLetterType() {
        LetterType result = letterTypeService.findById(1);
        verify(letterTypeRepository, times(1)).findOne(1)
        verifyNoMoreInteractions(letterTypeRepository)
    }
    
    @Test
    public void testSaveNewLetterType() {
        LetterType savedLetterType = letterTypeService.save(letterType1);
        verify(letterTypeRepository, times(1)).save(any())
        verifyNoMoreInteractions(letterTypeRepository)
    }
    
    @Test
    public void testSaveLetterType() {
        LetterType savedLetterType = letterTypeService.save(letterType1);
        verify(letterTypeRepository, times(1)).save(any())
        verifyNoMoreInteractions(letterTypeRepository)
    }
    
    @Test
    public void testSaveLetterTypes() {
        List<LetterType> savedLetterTypes = letterTypeService.saveLetterTypes([letterType1, letterType2]);
        verify(letterTypeRepository, times(2)).save(any(LetterType.class))
        verifyNoMoreInteractions(letterTypeRepository)
    }
    
    @Test
    public void testSaveLetterTypeByFields_WithNullId() {
        LetterType savedLetterType = letterTypeService.saveLetterType(null, letterType1.type);
        verify(letterTypeRepository, times(1)).save(any())
        verifyNoMoreInteractions(letterTypeRepository)
    }
    
    
    @Test
    public void testSaveLetterTypeByFields_WithId() {
        LetterType savedLetterType = letterTypeService.saveLetterType(1, letterType1.type);
        verify(letterTypeRepository, times(1)).findOne(1)
        verify(letterTypeRepository, times(1)).save(any())
        verifyNoMoreInteractions(letterTypeRepository)
    }
}

