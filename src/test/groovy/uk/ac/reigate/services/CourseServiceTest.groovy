package uk.ac.reigate.services

import static org.mockito.Mockito.*

import org.junit.Before
import org.junit.Test

import uk.ac.reigate.domain.academic.Course
import uk.ac.reigate.model.SearchResult
import uk.ac.reigate.repositories.academic.CourseRepository;


class CourseServiceTest {
    
    private CourseRepository courseRepository;
    
    private CourseService courseService;
    
    Course course1
    
    Course course2
    
    @Before
    public void setup() {
        this.courseRepository = mock(CourseRepository.class);
        this.courseService = new CourseService(courseRepository);
        
        course1 = new Course(id: 1, glh: 11, learningAimReference: 'learn',  syllabusCode: '1', locationPostcode: 'E161FF', subjectSectorArea: 'London', notes: 'working')
        course2 = new Course(id: 2, glh: 13, learningAimReference: 'learn',  syllabusCode: '2', locationPostcode: 'E161DD', subjectSectorArea: 'London', notes: 'Nothing')
        
        when(courseRepository.findAll()).thenReturn([course1, course2]);
        when(courseRepository.findOne(1)).thenReturn(course1);
        when(courseRepository.save(any(Course.class))).thenReturn(course1);
    }
    
    
    @Test
    public void testFindCourse() {
        List<Course> result = courseService.findAll();
        verify(courseRepository, times(1)).findAll()
        verifyNoMoreInteractions(courseRepository)
    }
    
    @Test
    public void testFindCourseById() {
        Course result = courseService.findById(1);
        verify(courseRepository, times(1)).findOne(1)
        verifyNoMoreInteractions(courseRepository)
    }
    
    @Test
    public void testSaveNewCourse() {
        course1.id = null
        courseService.save(course1);
        verify(courseRepository, times(1)).save(course1)
        verifyNoMoreInteractions(courseRepository)
    }
    
    @Test
    public void testSaveCourse() {
        courseService.save(course1);
        verify(courseRepository, times(1)).save(course1)
        verifyNoMoreInteractions(courseRepository)
    }
}



