package uk.ac.reigate.services

import static org.junit.Assert.assertNull
import static org.junit.Assert.assertTrue
import static org.mockito.Mockito.*

import org.junit.Before
import org.junit.Test
import org.mockito.Mockito


import uk.ac.reigate.domain.learning_support.ConcessionType
import uk.ac.reigate.model.SearchResult
import uk.ac.reigate.repositories.learning_support.ConcessionTypeRepository
import uk.ac.reigate.services.ConcessionTypeService;


class ConcessionTypeServiceTest {
    
    private ConcessionTypeRepository concessionTypeRepository;
    
    private ConcessionTypeService concessionTypeService;
    
    ConcessionType concessionType1
    ConcessionType concessionType2
    
    @Before
    public void setup() {
        this.concessionTypeRepository = Mockito.mock(ConcessionTypeRepository.class);
        this.concessionTypeService = new ConcessionTypeService(concessionTypeRepository);
        
        concessionType1 = new ConcessionType(id: 1, code: 'M', description: 'M')
        concessionType2 = new ConcessionType(id: 2, code: 'F', description: 'F')
        
        when(concessionTypeRepository.findAll()).thenReturn([
            concessionType1,
            concessionType2
        ]);
        when(concessionTypeRepository.findOne(1)).thenReturn(concessionType1);
        when(concessionTypeRepository.findOne(null)).thenThrow(new IllegalArgumentException());
        when(concessionTypeRepository.save(any(ConcessionType.class))).thenReturn(concessionType1);
    }
    
    @Test
    public void testFindConcessionTypes() {
        List<ConcessionType> result = concessionTypeService.findAll();
        verify(concessionTypeRepository, times(1)).findAll()
        verifyNoMoreInteractions(concessionTypeRepository)
    }
    
    @Test
    public void testFindConcessionType() {
        ConcessionType result = concessionTypeService.findById(1);
        verify(concessionTypeRepository, times(1)).findOne(1)
        verifyNoMoreInteractions(concessionTypeRepository)
    }
    
    @Test
    public void testSaveNewConcessionType() {
        ConcessionType savedConcessionType = concessionTypeService.save(concessionType1);
        verify(concessionTypeRepository, times(1)).save(any())
        verifyNoMoreInteractions(concessionTypeRepository)
    }
    
    @Test
    public void testSaveConcessionType() {
        ConcessionType savedConcessionType = concessionTypeService.save(concessionType1);
        verify(concessionTypeRepository, times(1)).save(any())
        verifyNoMoreInteractions(concessionTypeRepository)
    }
    
    @Test
    public void testSaveConcessionTypes() {
        List<ConcessionType> savedConcessionTypes = concessionTypeService.saveConcessionTypes([
            concessionType1,
            concessionType2
        ]);
        verify(concessionTypeRepository, times(2)).save(any(ConcessionType.class))
        verifyNoMoreInteractions(concessionTypeRepository)
    }
    
    @Test
    public void testSaveConcessionTypeByFields_WithNullId() {
        ConcessionType savedConcessionType = concessionTypeService.saveConcessionType(null, concessionType1.code, concessionType1.description);
        verify(concessionTypeRepository, times(1)).save(any())
        verifyNoMoreInteractions(concessionTypeRepository)
    }
    
    
    @Test
    public void testSaveConcessionTypeByFields_WithId() {
        ConcessionType savedConcessionType = concessionTypeService.saveConcessionType(1, concessionType1.code, concessionType1.description);
        verify(concessionTypeRepository, times(1)).findOne(1)
        verify(concessionTypeRepository, times(1)).save(any())
        verifyNoMoreInteractions(concessionTypeRepository)
    }
}

