package uk.ac.reigate.services

import static org.junit.Assert.assertNull
import static org.junit.Assert.assertTrue
import static org.mockito.Mockito.*

import org.junit.Before
import org.junit.Test
import org.mockito.Mockito

import uk.ac.reigate.domain.academic.EntryQualification
import uk.ac.reigate.model.SearchResult
import uk.ac.reigate.repositories.academic.EntryQualificationRepository

class EntryQualificationServiceTest {
    
    private EntryQualificationRepository entryQualificationRepository
    
    private EntryQualificationService entryQualificationService;
    
    EntryQualification entryQualification1
    EntryQualification entryQualification2
    
    @Before
    public void setup() {
        this.entryQualificationRepository = mock(EntryQualificationRepository.class);
        this.entryQualificationService = new EntryQualificationService(entryQualificationRepository);
        
        entryQualification1 = new EntryQualification(id: 1, title: 'Maths', basicList: true, shortCourse: true, dataMatchCode: "MA", webLinkCode: 1)
        entryQualification2 = new EntryQualification(id: 2, title: 'Science', basicList: true, shortCourse: true, dataMatchCode: "Sc", webLinkCode:2)
        
        when(entryQualificationRepository.findAll()).thenReturn([
            entryQualification1,
            entryQualification2
        ]);
        when(entryQualificationRepository.findOne(1)).thenReturn(entryQualification1);
        when(entryQualificationRepository.findOne(null)).thenThrow(new IllegalArgumentException());
        when(entryQualificationRepository.save(any(EntryQualification.class))).thenReturn(entryQualification1);
    }
    
    @Test
    public void testFindEntryQualifications() {
        List<EntryQualification> result = entryQualificationService.findAll();
        verify(entryQualificationRepository, times(1)).findAll()
        verifyNoMoreInteractions(entryQualificationRepository)
    }
    
    @Test
    public void testFindEntryQualification() {
        EntryQualification result = entryQualificationService.findById(1);
        verify(entryQualificationRepository, times(1)).findOne(1)
        verifyNoMoreInteractions(entryQualificationRepository)
    }
    
    @Test
    public void testSaveNewEntryQualification() {
        EntryQualification savedEntryQualification = entryQualificationService.save(entryQualification1);
        verify(entryQualificationRepository, times(1)).save(any())
        verifyNoMoreInteractions(entryQualificationRepository)
    }
    
    @Test
    public void testSaveEntryQualifications() {
        List<EntryQualification> savedEntryQualifications = entryQualificationService.saveEntryQualifications([
            entryQualification1,
            entryQualification2
        ]);
        verify(entryQualificationRepository, times(2)).save(any(EntryQualification.class))
        verifyNoMoreInteractions(entryQualificationRepository)
    }
}

