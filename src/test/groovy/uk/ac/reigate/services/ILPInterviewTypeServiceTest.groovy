package uk.ac.reigate.services

import static org.junit.Assert.assertNull
import static org.junit.Assert.assertTrue
import static org.mockito.Mockito.*

import org.junit.Before
import org.junit.Test
import org.mockito.Mockito

import uk.ac.reigate.domain.ilp.ILPInterviewType;
import uk.ac.reigate.model.SearchResult
import uk.ac.reigate.repositories.ilp.ILPInterviewTypeRepository
import uk.ac.reigate.services.ILPInterviewTypeService;


class ILPInterviewTypeServiceTest {
    
    private ILPInterviewTypeRepository iLPInterviewTypeRepository
    
    private ILPInterviewTypeService iLPInterviewTypeService;
    
    ILPInterviewType iLPInterviewType1
    ILPInterviewType iLPInterviewType2
    
    @Before
    public void setup() {
        this.iLPInterviewTypeRepository = Mockito.mock(ILPInterviewTypeRepository.class);
        this.iLPInterviewTypeService = new ILPInterviewTypeService(iLPInterviewTypeRepository);
        
        iLPInterviewType1 = new ILPInterviewType(id: 1, type: 'A')
        iLPInterviewType2 = new ILPInterviewType(id: 2, type: 'C')
        
        when(iLPInterviewTypeRepository.findAll()).thenReturn([
            iLPInterviewType1,
            iLPInterviewType2
        ]);
        when(iLPInterviewTypeRepository.findOne(1)).thenReturn(iLPInterviewType1);
        when(iLPInterviewTypeRepository.findOne(null)).thenThrow(new IllegalArgumentException());
        when(iLPInterviewTypeRepository.save(any(ILPInterviewType.class))).thenReturn(iLPInterviewType1);
    }
    
    @Test
    public void testFindILPInterviewTypes() {
        List<ILPInterviewType> result = iLPInterviewTypeService.findAll();
        verify(iLPInterviewTypeRepository, times(1)).findAll()
        verifyNoMoreInteractions(iLPInterviewTypeRepository)
    }
    
    @Test
    public void testFindILPInterviewType() {
        ILPInterviewType result = iLPInterviewTypeService.findById(1);
        verify(iLPInterviewTypeRepository, times(1)).findOne(1)
        verifyNoMoreInteractions(iLPInterviewTypeRepository)
    }
    
    @Test
    public void testSaveNewILPInterviewType() {
        ILPInterviewType savedILPInterviewType = iLPInterviewTypeService.save(iLPInterviewType1);
        verify(iLPInterviewTypeRepository, times(1)).save(any())
        verifyNoMoreInteractions(iLPInterviewTypeRepository)
    }
    
    @Test
    public void testSaveILPInterviewType() {
        ILPInterviewType savedILPInterviewType = iLPInterviewTypeService.save(iLPInterviewType1);
        verify(iLPInterviewTypeRepository, times(1)).save(any())
        verifyNoMoreInteractions(iLPInterviewTypeRepository)
    }
    
    @Test
    public void testSaveILPInterviewTypes() {
        List<ILPInterviewType> savedILPInterviewTypes = iLPInterviewTypeService.saveILPInterviewTypes([
            iLPInterviewType1,
            iLPInterviewType2
        ]);
        verify(iLPInterviewTypeRepository, times(2)).save(any(ILPInterviewType.class))
        verifyNoMoreInteractions(iLPInterviewTypeRepository)
    }
    
    @Test
    public void testSaveILPInterviewTypeByFields_WithNullId() {
        ILPInterviewType savedILPInterviewType = iLPInterviewTypeService.saveILPInterviewType(null, iLPInterviewType1.type);
        verify(iLPInterviewTypeRepository, times(1)).save(any())
        verifyNoMoreInteractions(iLPInterviewTypeRepository)
    }
    
    
    @Test
    public void testSaveILPInterviewTypeByFields_WithId() {
        ILPInterviewType savedILPInterviewType = iLPInterviewTypeService.saveILPInterviewType(1, iLPInterviewType1.type);
        verify(iLPInterviewTypeRepository, times(1)).findOne(1)
        verify(iLPInterviewTypeRepository, times(1)).save(any())
        verifyNoMoreInteractions(iLPInterviewTypeRepository)
    }
}

