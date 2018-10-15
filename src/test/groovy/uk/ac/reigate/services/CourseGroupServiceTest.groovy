package uk.ac.reigate.services

import static org.mockito.Mockito.*

import org.junit.Before
import org.junit.Test

import uk.ac.reigate.domain.academic.CourseGroup
import uk.ac.reigate.model.SearchResult
import uk.ac.reigate.repositories.academic.CourseGroupRepository;


class CourseGroupServiceTest {
    
    private CourseGroupRepository courseGroupRepository;
    
    private CourseGroupService courseGroupService;
    
    CourseGroup courseGroup1
    
    CourseGroup courseGroup2
    
    @Before
    public void setup() {
        this.courseGroupRepository = mock(CourseGroupRepository.class);
        this.courseGroupService = new CourseGroupService(courseGroupRepository);
        
        courseGroup1 = new CourseGroup(id: 1, code: 'A',  displayOnTimetable: true, hasRegister: true, notes: 'Nothing', spec:'L-MAH')
        courseGroup2 = new CourseGroup(id: 2, code: 'B',  displayOnTimetable: false, hasRegister: true, notes: 'Nothing', spec:'L-MAH')
        
        when(courseGroupRepository.findAll()).thenReturn([courseGroup1, courseGroup2]);
        when(courseGroupRepository.findOne(1)).thenReturn(courseGroup1);
        when(courseGroupRepository.save(any(CourseGroup.class))).thenReturn(courseGroup1);
    }
    
    
    @Test
    public void testFindCourseGroup() {
        List<CourseGroup> result = courseGroupService.findAll();
        verify(courseGroupRepository, times(1)).findAll()
        verifyNoMoreInteractions(courseGroupRepository)
    }
    
    @Test
    public void testFindCourseGroupById() {
        CourseGroup result = courseGroupService.findById(1);
        verify(courseGroupRepository, times(1)).findOne(1)
        verifyNoMoreInteractions(courseGroupRepository)
    }
    
    @Test
    public void testSaveNewCourseGroup() {
        courseGroup1.id = null
        courseGroupService.save(courseGroup1);
        verify(courseGroupRepository, times(1)).save(courseGroup1)
        verifyNoMoreInteractions(courseGroupRepository)
    }
    
    @Test
    public void testSaveCourseGroup() {
        courseGroupService.save(courseGroup1);
        verify(courseGroupRepository, times(1)).save(courseGroup1)
        verifyNoMoreInteractions(courseGroupRepository)
    }
}

