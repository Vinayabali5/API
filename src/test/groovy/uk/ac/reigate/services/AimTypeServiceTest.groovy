package uk.ac.reigate.services

import static org.junit.Assert.assertNull
import static org.junit.Assert.assertTrue
import static org.mockito.Mockito.*

import org.junit.Before
import org.junit.Test
import org.mockito.Mockito

import uk.ac.reigate.domain.ilr.AimType
import uk.ac.reigate.model.SearchResult
import uk.ac.reigate.repositories.ilr.AimTypeRepository

class AimTypeServiceTest {
    
    private AimTypeRepository aimTypeRepository
    
    private AimTypeService aimTypeService;
    
    AimType aimType1;
    AimType aimType2;
    
    @Before
    public void setup() {
        this.aimTypeRepository = Mockito.mock(AimTypeRepository.class)
        this.aimTypeService = new AimTypeService(aimTypeRepository);
        
        aimType1 = new AimType(id: 1, code: 'N', description: 'New', shortDescription: 'New', validFrom: new Date().parse('yyyy/MM/dd', '2011/11/11'), validTo: new Date().parse('yyyy/MM/dd', '2016/11/11'))
        aimType2 = new AimType(id: 2, code: 'C', description: 'Complete', shortDescription: 'Complete', validFrom: new Date().parse('yyyy/MM/dd', '2013/11/11'), validTo: new Date().parse('yyyy/MM/dd', '2015/11/11') )
        
        when(aimTypeRepository.findAll()).thenReturn([aimType1, aimType2]);
        when(aimTypeRepository.findOne(1)).thenReturn(aimType1);
        when(aimTypeRepository.findOne(null)).thenThrow(new IllegalArgumentException());
        when(aimTypeRepository.save(any(AimType.class))).thenReturn(aimType1);
    }
    
    @Test
    public void testFindAimTypes() {
        List<AimType> result = aimTypeService.findAll();
        verify(aimTypeRepository, times(1)).findAll()
        verifyNoMoreInteractions(aimTypeRepository)
    }
    
    @Test
    public void testFindAimType() {
        AimType result = aimTypeService.findById(1);
        verify(aimTypeRepository, times(1)).findOne(1)
        verifyNoMoreInteractions(aimTypeRepository)
    }
    
    @Test
    public void testSaveNewAimType() {
        AimType savedAimType = aimTypeService.save(aimType1);
        verify(aimTypeRepository, times(1)).save(any())
        verifyNoMoreInteractions(aimTypeRepository)
    }
    
    @Test
    public void testSaveAimTypes() {
        List<AimType> savedAimTypes = aimTypeService.saveAimTypes([aimType1, aimType2]);
        verify(aimTypeRepository, times(2)).save(any(AimType.class))
        verifyNoMoreInteractions(aimTypeRepository)
    }
    
    @Test
    public void testSaveAimTypeByFields_WithNullId() {
        AimType savedAimType = aimTypeService.saveAimType(null, aimType1.code, aimType1.description, aimType1.shortDescription, aimType1.validFrom, aimType1.validTo);
        verify(aimTypeRepository, times(1)).save(any())
        verifyNoMoreInteractions(aimTypeRepository)
    }
    
    
    @Test
    public void testSaveAimTypeByFields_WithId() {
        AimType savedAimType = aimTypeService.saveAimType(1, aimType1.code, aimType1.description, aimType1.shortDescription, aimType1.validFrom, aimType1.validTo);
        verify(aimTypeRepository, times(1)).findOne(1)
        verify(aimTypeRepository, times(1)).save(any())
        verifyNoMoreInteractions(aimTypeRepository)
    }
}

