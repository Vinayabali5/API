package uk.ac.reigate.services

import static org.junit.Assert.assertNull
import static org.junit.Assert.assertTrue
import static org.mockito.Mockito.*

import org.junit.Before
import org.junit.Test

import uk.ac.reigate.domain.Contact
import uk.ac.reigate.model.SearchResult
import uk.ac.reigate.repositories.ContactRepository


class ContactServiceTest {
    
    private ContactRepository contactRepository;
    
    private ContactService contactService;
    
    Contact contact1
    
    Contact contact2
    
    @Before
    public void setup() {
        this.contactRepository = mock(ContactRepository.class);
        this.contactService = new ContactService(contactRepository);
        
        contact1 = new Contact(id: 1,  contactable : true,  preferred : true)
        contact2 = new Contact(id: 2, contactable : true,  preferred : true)
        
        when(contactRepository.findAll()).thenReturn([contact1, contact2]);
        when(contactRepository.findOne(1)).thenReturn(contact1);
        when(contactRepository.findOne(null)).thenThrow(new IllegalArgumentException());
        when(contactRepository.save(any(Contact.class))).thenReturn(contact1);
    }
    
    
    @Test
    public void testFindContact() {
        List<Contact> result = contactService.findAll();
        verify(contactRepository, times(1)).findAll()
        verifyNoMoreInteractions(contactRepository)
    }
    
    @Test
    public void testFindContactById() {
        Contact result = contactService.findById(1);
        verify(contactRepository, times(1)).findOne(1)
        verifyNoMoreInteractions(contactRepository)
    }
    
    @Test
    public void testSaveNewContact() {
        contact1.id = null
        contactService.save(contact1);
        verify(contactRepository, times(1)).save(contact1)
        verifyNoMoreInteractions(contactRepository)
    }
    
    @Test
    public void testSaveContact() {
        contactService.save(contact1);
        verify(contactRepository, times(1)).save(contact1)
        verifyNoMoreInteractions(contactRepository)
    }
}