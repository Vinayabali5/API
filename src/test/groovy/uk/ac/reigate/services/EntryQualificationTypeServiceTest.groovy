package uk.ac.reigate.services

import static org.junit.Assert.assertNull
import static org.junit.Assert.assertTrue
import static org.mockito.Mockito.*

import org.junit.Before
import org.junit.Test
import org.mockito.Mockito

import uk.ac.reigate.domain.academic.EntryQualificationType
import uk.ac.reigate.model.SearchResult
import uk.ac.reigate.repositories.academic.EntryQualificationTypeRepository

class EntryQualificationTypeServiceTest {
    
    private EntryQualificationTypeRepository entryQualificationTypeRepository
    
    private EntryQualificationTypeService entryQualificationTypeService;
    
    EntryQualificationType entryQualificationType1
    EntryQualificationType entryQualificationType2
    
    @Before
    public void setup() {
        this.entryQualificationTypeRepository = mock(EntryQualificationTypeRepository.class);
        this.entryQualificationTypeService = new EntryQualificationTypeService(entryQualificationTypeRepository);
        
        entryQualificationType1 = new EntryQualificationType(id: 1, code: 'Ma', description: "Maths")
        entryQualificationType2 = new EntryQualificationType(id: 2, code: 'Sc', description: "Science")
        
        when(entryQualificationTypeRepository.findAll()).thenReturn([
            entryQualificationType1,
            entryQualificationType2
        ]);
        when(entryQualificationTypeRepository.findOne(1)).thenReturn(entryQualificationType1);
        when(entryQualificationTypeRepository.findOne(null)).thenThrow(new IllegalArgumentException());
        when(entryQualificationTypeRepository.save(any(EntryQualificationType.class))).thenReturn(entryQualificationType1);
    }
    
    @Test
    public void testFindEntryQualificationTypes() {
        List<EntryQualificationType> result = entryQualificationTypeService.findAll();
        verify(entryQualificationTypeRepository, times(1)).findAll()
        verifyNoMoreInteractions(entryQualificationTypeRepository)
    }
    
    @Test
    public void testFindEntryQualificationType() {
        EntryQualificationType result = entryQualificationTypeService.findById(1);
        verify(entryQualificationTypeRepository, times(1)).findOne(1)
        verifyNoMoreInteractions(entryQualificationTypeRepository)
    }
    
    @Test
    public void testSaveNewEntryQualificationType() {
        EntryQualificationType savedEntryQualificationType = entryQualificationTypeService.save(entryQualificationType1);
        verify(entryQualificationTypeRepository, times(1)).save(any())
        verifyNoMoreInteractions(entryQualificationTypeRepository)
    }
    
    @Test
    public void testSaveEntryQualificationTypes() {
        List<EntryQualificationType> savedEntryQualificationTypes = entryQualificationTypeService.saveEntryQualificationTypes([
            entryQualificationType1,
            entryQualificationType2
        ]);
        verify(entryQualificationTypeRepository, times(2)).save(any(EntryQualificationType.class))
        verifyNoMoreInteractions(entryQualificationTypeRepository)
    }
}

