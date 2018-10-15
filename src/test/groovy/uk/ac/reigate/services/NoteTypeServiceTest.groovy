package uk.ac.reigate.services

import static org.junit.Assert.assertNull
import static org.junit.Assert.assertTrue
import static org.mockito.Mockito.*

import org.junit.Before
import org.junit.Test
import org.mockito.Mockito
import uk.ac.reigate.domain.NoteType
import uk.ac.reigate.domain.NoteType;
import uk.ac.reigate.model.SearchResult
import uk.ac.reigate.repositories.NoteTypeRepository
import uk.ac.reigate.services.NoteTypeService;


class NoteTypeServiceTest {
    
    private NoteTypeRepository noteTypeRepository;
    
    private NoteTypeService noteTypeService;
    
    NoteType noteType1
    NoteType noteType2
    
    @Before
    public void setup() {
        this.noteTypeRepository = Mockito.mock(NoteTypeRepository.class);
        this.noteTypeService = new NoteTypeService(noteTypeRepository);
        
        noteType1 = new NoteType(id: 1, code: 'A', description: 'A NoteType')
        noteType2 = new NoteType(id: 2, code: 'C', description: 'C NoteType')
        
        when(noteTypeRepository.findAll()).thenReturn([noteType1, noteType2]);
        when(noteTypeRepository.findOne(1)).thenReturn(noteType1);
        when(noteTypeRepository.findOne(null)).thenThrow(new IllegalArgumentException());
        when(noteTypeRepository.save(any(NoteType.class))).thenReturn(noteType1);
    }
    
    @Test
    public void testFindNoteTypes() {
        List<NoteType> result = noteTypeService.findAll();
        verify(noteTypeRepository, times(1)).findAll()
        verifyNoMoreInteractions(noteTypeRepository)
    }
    
    @Test
    public void testFindNoteType() {
        NoteType result = noteTypeService.findById(1);
        verify(noteTypeRepository, times(1)).findOne(1)
        verifyNoMoreInteractions(noteTypeRepository)
    }
    
    @Test
    public void testSaveNewNoteType() {
        NoteType savedNoteType = noteTypeService.save(noteType1);
        verify(noteTypeRepository, times(1)).save(any())
        verifyNoMoreInteractions(noteTypeRepository)
    }
    
    @Test
    public void testSaveNoteType() {
        NoteType savedNoteType = noteTypeService.save(noteType1);
        verify(noteTypeRepository, times(1)).save(any())
        verifyNoMoreInteractions(noteTypeRepository)
    }
    
    @Test
    public void testSaveNoteTypes() {
        List<NoteType> savedNoteTypes = noteTypeService.saveNoteTypes([noteType1, noteType2]);
        verify(noteTypeRepository, times(2)).save(any(NoteType.class))
        verifyNoMoreInteractions(noteTypeRepository)
    }
    
    @Test
    public void testSaveNoteTypeByFields_WithNullId() {
        NoteType savedNoteType = noteTypeService.saveNoteType(null, noteType1.code, noteType1.description);
        verify(noteTypeRepository, times(1)).save(any())
        verifyNoMoreInteractions(noteTypeRepository)
    }
    
    
    @Test
    public void testSaveNoteTypeByFields_WithId() {
        NoteType savedNoteType = noteTypeService.saveNoteType(1, noteType1.code, noteType1.description);
        verify(noteTypeRepository, times(1)).findOne(1)
        verify(noteTypeRepository, times(1)).save(any())
        verifyNoMoreInteractions(noteTypeRepository)
    }
}
