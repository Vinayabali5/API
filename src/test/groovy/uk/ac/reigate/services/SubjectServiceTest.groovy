package uk.ac.reigate.services;

import static org.mockito.Mockito.*

import org.junit.Before
import org.junit.Test
import org.mockito.Mockito

import uk.ac.reigate.domain.lookup.Subject
import uk.ac.reigate.model.SearchResult
import uk.ac.reigate.repositories.lookup.SubjectRepository


public class SubjectServiceTest {
    
    private SubjectRepository subjectRepository;
    
    private SubjectService subjectService;
    
    Subject subject1
    Subject subject2
    
    @Before
    public void setup() {
        this.subjectRepository = Mockito.mock(SubjectRepository.class);
        this.subjectService = new SubjectService(subjectRepository);
        
        subject1 = new Subject(id: 1, code: 'M', description: 'Mathematics')
        subject2 = new Subject(id: 2, code: 'C', description: 'Chemistry')
        
        when(subjectRepository.findAll()).thenReturn([subject1, subject2]);
        when(subjectRepository.findOne(1)).thenReturn(subject1);
        when(subjectRepository.findOne(null)).thenThrow(new IllegalArgumentException());
        when(subjectRepository.save(any(Subject.class))).thenReturn(subject1);
    }
    
    @Test
    public void testFindSubjects() {
        List<Subject> result = subjectService.findAll();
        verify(subjectRepository, times(1)).findAll()
        verifyNoMoreInteractions(subjectRepository)
    }
    
    @Test
    public void testFindSubject() {
        Subject result = subjectService.findById(1);
        verify(subjectRepository, times(1)).findOne(1)
        verifyNoMoreInteractions(subjectRepository)
    }
    
    @Test
    public void testSaveNewSubject() {
        Subject savedSubject = subjectService.save(subject1);
        verify(subjectRepository, times(1)).save(any())
        verifyNoMoreInteractions(subjectRepository)
    }
    
    @Test
    public void testSaveSubject() {
        Subject savedSubject = subjectService.save(subject1);
        verify(subjectRepository, times(1)).save(any())
        verifyNoMoreInteractions(subjectRepository)
    }
    
    @Test
    public void testSaveSubjects() {
        List<Subject> savedSubjects = subjectService.saveSubjects([subject1, subject2]);
        verify(subjectRepository, times(2)).save(any(Subject.class))
        verifyNoMoreInteractions(subjectRepository)
    }
    
    @Test
    public void testSaveSubjectByFields_WithNullId() {
        Subject savedSubject = subjectService.saveSubject(null, subject1.code, subject1.description);
        verify(subjectRepository, times(1)).save(any())
        verifyNoMoreInteractions(subjectRepository)
    }
    
    
    @Test
    public void testSaveSubjectByFields_WithId() {
        Subject savedSubject = subjectService.saveSubject(1, subject1.code, subject1.description);
        verify(subjectRepository, times(1)).findOne(1)
        verify(subjectRepository, times(1)).save(any())
        verifyNoMoreInteractions(subjectRepository)
    }
}

