package uk.ac.reigate.services

import static org.junit.Assert.assertNull
import static org.junit.Assert.assertTrue
import static org.mockito.Mockito.*

import org.junit.Before
import org.junit.Test
import org.mockito.Mockito
import uk.ac.reigate.domain.Address
import uk.ac.reigate.domain.Person
import uk.ac.reigate.domain.academic.Faculty
import uk.ac.reigate.domain.lookup.Gender
import uk.ac.reigate.domain.lookup.Title
import uk.ac.reigate.model.SearchResult
import uk.ac.reigate.repositories.academic.FacultyRepository;
import uk.ac.reigate.services.FacultyService;


class FacultyServiceTest {
    
    private FacultyRepository facultyRepository;
    
    private FacultyService facultyService;
    
    Faculty faculty1
    
    Faculty faculty2
    
    @Before
    public void setup() {
        this.facultyRepository = mock(FacultyRepository.class);
        this.facultyService = new FacultyService(facultyRepository);
        
        faculty1 = new Faculty(id: 1, code:'A', description:'A Faculty', validFrom: new Date().parse('yyyy/MM/dd', '2011/07/09'), validTo: new Date().parse('yyyy/MM/dd', '2013/07/09'))
        faculty2 = new Faculty(id: 2, code:'B', description:'B Faculty', validFrom: new Date().parse('yyyy/MM/dd', '2013/07/09'), validTo: new Date().parse('yyyy/MM/dd', '2016/07/09'))
        
        when(facultyRepository.findAll()).thenReturn([faculty1, faculty2]);
        when(facultyRepository.findOne(1)).thenReturn(faculty1);
        when(facultyRepository.findOne(null)).thenThrow(new IllegalArgumentException());
        when(facultyRepository.save(any(Faculty.class))).thenReturn(faculty1);
    }
    
    
    @Test
    public void testFindFaculty() {
        List<Faculty> result = facultyService.findAll();
        verify(facultyRepository, times(1)).findAll()
        verifyNoMoreInteractions(facultyRepository)
    }
    
    @Test
    public void testFindFacultyById() {
        Faculty result = facultyService.findById(1);
        verify(facultyRepository, times(1)).findOne(1)
        verifyNoMoreInteractions(facultyRepository)
    }
    
    @Test
    public void testSaveNewFaculty() {
        faculty1.id = null
        facultyService.save(faculty1);
        verify(facultyRepository, times(1)).save(faculty1)
        verifyNoMoreInteractions(facultyRepository)
    }
    
    @Test
    public void testSaveFaculty() {
        facultyService.save(faculty1);
        verify(facultyRepository, times(1)).save(faculty1)
        verifyNoMoreInteractions(facultyRepository)
    }
}

