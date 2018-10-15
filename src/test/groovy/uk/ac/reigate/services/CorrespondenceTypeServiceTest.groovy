package uk.ac.reigate.services

import static org.junit.Assert.assertNull
import static org.junit.Assert.assertTrue
import static org.mockito.Mockito.*

import org.junit.Before
import org.junit.Test
import org.mockito.Mockito

import uk.ac.reigate.domain.ilp.CorrespondenceType;
import uk.ac.reigate.model.SearchResult
import uk.ac.reigate.repositories.ilp.CorrespondenceTypeRepository
import uk.ac.reigate.services.CorrespondenceTypeService;


class CorrespondenceTypeServiceTest {
    
    private CorrespondenceTypeRepository correspondenceTypeRepository
    
    private CorrespondenceTypeService correspondenceTypeService;
    
    CorrespondenceType correspondenceType1
    CorrespondenceType correspondenceType2
    
    @Before
    public void setup() {
        this.correspondenceTypeRepository = Mockito.mock(CorrespondenceTypeRepository.class);
        this.correspondenceTypeService = new CorrespondenceTypeService(correspondenceTypeRepository);
        
        correspondenceType1 = new CorrespondenceType(id: 1, type: 'A')
        correspondenceType2 = new CorrespondenceType(id: 2, type: 'C')
        
        when(correspondenceTypeRepository.findAll()).thenReturn([
            correspondenceType1,
            correspondenceType2
        ]);
        when(correspondenceTypeRepository.findOne(1)).thenReturn(correspondenceType1);
        when(correspondenceTypeRepository.findOne(null)).thenThrow(new IllegalArgumentException());
        when(correspondenceTypeRepository.save(any(CorrespondenceType.class))).thenReturn(correspondenceType1);
    }
    
    @Test
    public void testFindCorrespondenceTypes() {
        List<CorrespondenceType> result = correspondenceTypeService.findAll();
        verify(correspondenceTypeRepository, times(1)).findAll()
        verifyNoMoreInteractions(correspondenceTypeRepository)
    }
    
    @Test
    public void testFindCorrespondenceType() {
        CorrespondenceType result = correspondenceTypeService.findById(1);
        verify(correspondenceTypeRepository, times(1)).findOne(1)
        verifyNoMoreInteractions(correspondenceTypeRepository)
    }
    
    @Test
    public void testSaveNewCorrespondenceType() {
        CorrespondenceType savedCorrespondenceType = correspondenceTypeService.save(correspondenceType1);
        verify(correspondenceTypeRepository, times(1)).save(any())
        verifyNoMoreInteractions(correspondenceTypeRepository)
    }
    
    @Test
    public void testSaveCorrespondenceType() {
        CorrespondenceType savedCorrespondenceType = correspondenceTypeService.save(correspondenceType1);
        verify(correspondenceTypeRepository, times(1)).save(any())
        verifyNoMoreInteractions(correspondenceTypeRepository)
    }
    
    @Test
    public void testSaveCorrespondenceTypes() {
        List<CorrespondenceType> savedCorrespondenceTypes = correspondenceTypeService.saveCorrespondenceTypes([
            correspondenceType1,
            correspondenceType2
        ]);
        verify(correspondenceTypeRepository, times(2)).save(any(CorrespondenceType.class))
        verifyNoMoreInteractions(correspondenceTypeRepository)
    }
    
    @Test
    public void testSaveCorrespondenceTypeByFields_WithNullId() {
        CorrespondenceType savedCorrespondenceType = correspondenceTypeService.saveCorrespondenceType(null, correspondenceType1.type);
        verify(correspondenceTypeRepository, times(1)).save(any())
        verifyNoMoreInteractions(correspondenceTypeRepository)
    }
    
    
    @Test
    public void testSaveCorrespondenceTypeByFields_WithId() {
        CorrespondenceType savedCorrespondenceType = correspondenceTypeService.saveCorrespondenceType(1, correspondenceType1.type);
        verify(correspondenceTypeRepository, times(1)).findOne(1)
        verify(correspondenceTypeRepository, times(1)).save(any())
        verifyNoMoreInteractions(correspondenceTypeRepository)
    }
}

