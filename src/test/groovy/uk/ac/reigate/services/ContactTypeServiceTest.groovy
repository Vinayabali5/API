package uk.ac.reigate.services;

import static org.mockito.Mockito.*

import org.junit.Before
import org.junit.Test
import org.mockito.Mockito

import uk.ac.reigate.domain.lookup.ContactType
import uk.ac.reigate.model.SearchResult
import uk.ac.reigate.repositories.lookup.ContactTypeRepository


public class ContactTypeServiceTest {
    
    private ContactTypeRepository contactTypeRepository
    
    private ContactTypeService contactTypeService;
    
    ContactType contactType1
    ContactType contactType2
    
    @Before
    public void setup() {
        this.contactTypeRepository = Mockito.mock(ContactTypeRepository.class);
        this.contactTypeService = new ContactTypeService(contactTypeRepository);
        
        contactType1 = new ContactType(id: 1, name: 'Mother', description: 'Mother')
        contactType2 = new ContactType(id: 2, name: 'Father', description: 'Father')
        
        when(contactTypeRepository.findAll()).thenReturn([contactType1, contactType2]);
        when(contactTypeRepository.findOne(1)).thenReturn(contactType1);
        when(contactTypeRepository.findOne(null)).thenThrow(new IllegalArgumentException());
        when(contactTypeRepository.save(any(ContactType.class))).thenReturn(contactType1);
    }
    
    @Test
    public void testFindContactTypes() {
        List<ContactType> result = contactTypeService.findAll();
        verify(contactTypeRepository, times(1)).findAll()
        verifyNoMoreInteractions(contactTypeRepository)
    }
    
    @Test
    public void testFindContactType() {
        ContactType result = contactTypeService.findById(1);
        verify(contactTypeRepository, times(1)).findOne(1)
        verifyNoMoreInteractions(contactTypeRepository)
    }
    
    @Test
    public void testSaveNewContactType() {
        ContactType savedContactType = contactTypeService.save(contactType1);
        verify(contactTypeRepository, times(1)).save(any())
        verifyNoMoreInteractions(contactTypeRepository)
    }
    
    @Test
    public void testSaveContactType() {
        ContactType savedContactType = contactTypeService.save(contactType1);
        verify(contactTypeRepository, times(1)).save(any())
        verifyNoMoreInteractions(contactTypeRepository)
    }
    
    @Test
    public void testSaveContactTypes() {
        List<ContactType> savedContactTypes = contactTypeService.saveContactTypes([contactType1, contactType2]);
        verify(contactTypeRepository, times(2)).save(any(ContactType.class))
        verifyNoMoreInteractions(contactTypeRepository)
    }
    
    @Test
    public void testSaveContactTypeByFields_WithNullId() {
        ContactType savedContactType = contactTypeService.saveContactType(null, contactType1.name, contactType1.description);
        verify(contactTypeRepository, times(1)).save(any())
        verifyNoMoreInteractions(contactTypeRepository)
    }
    
    
    @Test
    public void testSaveContactTypeByFields_WithId() {
        ContactType savedContactType = contactTypeService.saveContactType(1, contactType1.name, contactType1.description);
        verify(contactTypeRepository, times(1)).findOne(1)
        verify(contactTypeRepository, times(1)).save(any())
        verifyNoMoreInteractions(contactTypeRepository)
    }
}
