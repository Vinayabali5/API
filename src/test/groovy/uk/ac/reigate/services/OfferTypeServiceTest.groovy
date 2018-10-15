package uk.ac.reigate.services

import static org.mockito.Mockito.*

import org.junit.Before
import org.junit.Test
import org.mockito.Mockito

import uk.ac.reigate.domain.admissions.OfferType
import uk.ac.reigate.model.SearchResult
import uk.ac.reigate.repositories.admissions.OfferTypeRepository


class OfferTypeServiceTest {
    
    private OfferTypeRepository offerTypeRepository;
    
    private OfferTypeService offerTypeService;
    
    OfferType offerType1
    OfferType offerType2
    
    @Before
    public void setup() {
        this.offerTypeRepository = Mockito.mock(OfferTypeRepository.class);
        this.offerTypeService = new OfferTypeService(offerTypeRepository);
        
        offerType1 = new OfferType(id: 1, code: 'N', description: 'Normal OfferType')
        offerType2 = new OfferType(id: 2, code: 'P', description: 'Provisional OfferType')
        
        when(offerTypeRepository.findAll()).thenReturn([offerType1, offerType2]);
        when(offerTypeRepository.findOne(1)).thenReturn(offerType1);
        when(offerTypeRepository.findOne(null)).thenThrow(new IllegalArgumentException());
        when(offerTypeRepository.save(any(OfferType.class))).thenReturn(offerType1);
    }
    
    @Test
    public void testFindOfferTypes() {
        List<OfferType> result = offerTypeService.findAll();
        verify(offerTypeRepository, times(1)).findAll()
        verifyNoMoreInteractions(offerTypeRepository)
    }
    
    @Test
    public void testFindOfferType() {
        OfferType result = offerTypeService.findById(1);
        verify(offerTypeRepository, times(1)).findOne(1)
        verifyNoMoreInteractions(offerTypeRepository)
    }
    
    @Test
    public void testSaveNewOfferType() {
        OfferType savedOfferType = offerTypeService.save(offerType1);
        verify(offerTypeRepository, times(1)).save(any())
        verifyNoMoreInteractions(offerTypeRepository)
    }
    
    @Test
    public void testSaveOfferType() {
        OfferType savedOfferType = offerTypeService.save(offerType1);
        verify(offerTypeRepository, times(1)).save(any())
        verifyNoMoreInteractions(offerTypeRepository)
    }
    
    @Test
    public void testSaveOfferTypes() {
        List<OfferType> savedOfferTypes = offerTypeService.saveOfferTypes([offerType1, offerType2]);
        verify(offerTypeRepository, times(2)).save(any(OfferType.class))
        verifyNoMoreInteractions(offerTypeRepository)
    }
    
    @Test
    public void testSaveOfferTypeByFields_WithNullId() {
        OfferType savedOfferType = offerTypeService.saveOfferType(null, offerType1.code, offerType1.description);
        verify(offerTypeRepository, times(1)).save(any())
        verifyNoMoreInteractions(offerTypeRepository)
    }
    
    
    @Test
    public void testSaveOfferTypeByFields_WithId() {
        OfferType savedOfferType = offerTypeService.saveOfferType(1, offerType1.code, offerType1.description);
        verify(offerTypeRepository, times(1)).findOne(1)
        verify(offerTypeRepository, times(1)).save(any())
        verifyNoMoreInteractions(offerTypeRepository)
    }
}

