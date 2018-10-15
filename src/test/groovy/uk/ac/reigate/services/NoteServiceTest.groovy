package uk.ac.reigate.services

import static org.junit.Assert.assertNull
import static org.junit.Assert.assertTrue
import static org.mockito.Mockito.*

import org.junit.Before
import org.junit.Test
import org.mockito.Mockito

import uk.ac.reigate.domain.Address
import uk.ac.reigate.domain.Note
import uk.ac.reigate.domain.NoteType
import uk.ac.reigate.domain.Person
import uk.ac.reigate.domain.Note;
import uk.ac.reigate.domain.lookup.Gender
import uk.ac.reigate.domain.lookup.Title
import uk.ac.reigate.model.SearchResult
import uk.ac.reigate.repositories.NoteRepository;
import uk.ac.reigate.services.NoteService;


class NoteServiceTest {
    
    private NoteRepository noteRepository;
    
    private NoteService noteService;
    
    Note note1
    
    Note note2
    
    @Before
    public void setup() {
        this.noteRepository = mock(NoteRepository.class);
        this.noteService = new NoteService(noteRepository);
        
        note1 = new Note(id: 1, note:'A')
        note2 = new Note(id: 2, note:'B')
        
        when(noteRepository.findAll()).thenReturn([note1, note2]);
        when(noteRepository.findOne(1)).thenReturn(note1);
        when(noteRepository.findOne(null)).thenThrow(new IllegalArgumentException());
        when(noteRepository.save(any(Note.class))).thenReturn(note1);
    }
    
    
    @Test
    public void testFindNote() {
        List<Note> result = noteService.findAll();
        verify(noteRepository, times(1)).findAll()
        verifyNoMoreInteractions(noteRepository)
    }
    
    @Test
    public void testFindNoteById() {
        Note result = noteService.findById(1);
        verify(noteRepository, times(1)).findOne(1)
        verifyNoMoreInteractions(noteRepository)
    }
    
    @Test
    public void testSaveNewNote() {
        note1.id = null
        noteService.save(note1);
        verify(noteRepository, times(1)).save(note1)
        verifyNoMoreInteractions(noteRepository)
    }
    
    @Test
    public void testSaveNote() {
        noteService.save(note1);
        verify(noteRepository, times(1)).save(note1)
        verifyNoMoreInteractions(noteRepository)
    }
}

