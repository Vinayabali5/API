package uk.ac.reigate.services

import static org.junit.Assert.assertNull
import static org.junit.Assert.assertTrue
import static org.mockito.Mockito.*

import org.junit.Before
import org.junit.Test
import org.mockito.Mockito


import uk.ac.reigate.domain.learning_support.SupportType
import uk.ac.reigate.model.SearchResult
import uk.ac.reigate.repositories.learning_support.SupportTypeRepository
import uk.ac.reigate.services.SupportTypeService;


class SupportTypeServiceTest {
    
    private SupportTypeRepository supportTypeRepository;
    
    private SupportTypeService supportTypeService;
    
    SupportType supportType1
    SupportType supportType2
    
    @Before
    public void setup() {
        this.supportTypeRepository = Mockito.mock(SupportTypeRepository.class);
        this.supportTypeService = new SupportTypeService(supportTypeRepository);
        
        supportType1 = new SupportType(id: 1, support: 'M')
        supportType2 = new SupportType(id: 2, support: 'F')
        
        when(supportTypeRepository.findAll()).thenReturn([supportType1, supportType2]);
        when(supportTypeRepository.findOne(1)).thenReturn(supportType1);
        when(supportTypeRepository.findOne(null)).thenThrow(new IllegalArgumentException());
        when(supportTypeRepository.save(any(SupportType.class))).thenReturn(supportType1);
    }
    
    @Test
    public void testFindSupportTypes() {
        List<SupportType> result = supportTypeService.findAll();
        verify(supportTypeRepository, times(1)).findAll()
        verifyNoMoreInteractions(supportTypeRepository)
    }
    
    @Test
    public void testFindSupportType() {
        SupportType result = supportTypeService.findById(1);
        verify(supportTypeRepository, times(1)).findOne(1)
        verifyNoMoreInteractions(supportTypeRepository)
    }
    
    @Test
    public void testSaveNewSupportType() {
        SupportType savedSupportType = supportTypeService.save(supportType1);
        verify(supportTypeRepository, times(1)).save(any())
        verifyNoMoreInteractions(supportTypeRepository)
    }
    
    @Test
    public void testSaveSupportType() {
        SupportType savedSupportType = supportTypeService.save(supportType1);
        verify(supportTypeRepository, times(1)).save(any())
        verifyNoMoreInteractions(supportTypeRepository)
    }
    
    @Test
    public void testSaveSupportTypes() {
        List<SupportType> savedSupportTypes = supportTypeService.saveSupportTypes([supportType1, supportType2]);
        verify(supportTypeRepository, times(2)).save(any(SupportType.class))
        verifyNoMoreInteractions(supportTypeRepository)
    }
    
    @Test
    public void testSaveSupportTypeByFields_WithNullId() {
        SupportType savedSupportType = supportTypeService.saveSupportType(null, supportType1.support);
        verify(supportTypeRepository, times(1)).save(any())
        verifyNoMoreInteractions(supportTypeRepository)
    }
    
    
    @Test
    public void testSaveSupportTypeByFields_WithId() {
        SupportType savedSupportType = supportTypeService.saveSupportType(1, supportType1.support);
        verify(supportTypeRepository, times(1)).findOne(1)
        verify(supportTypeRepository, times(1)).save(any())
        verifyNoMoreInteractions(supportTypeRepository)
    }
}

