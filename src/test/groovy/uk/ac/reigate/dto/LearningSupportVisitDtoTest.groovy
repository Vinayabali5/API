package uk.ac.reigate.dto;

import static org.junit.Assert.*
import org.junit.Before
import org.junit.Test
import uk.ac.reigate.domain.Staff
import uk.ac.reigate.domain.academic.Student;
import uk.ac.reigate.domain.learning_support.LearningSupportVisit



public class LearningSupportVisitDtoTest {
    
    private LearningSupportVisit learningSupportVisit1
    
    private LearningSupportVisit learningSupportVisit2
    
    private List<LearningSupportVisit> learningSupportVisits
    
    Student createStudent() {
        Student student = new Student()
        student.id = 1
        return student
    }
    
    
    @Before
    public void setup() {
        learningSupportVisit1 = new LearningSupportVisit(
                id: 1,
                student: createStudent(),
                date : new Date().parse('yyyy/MM/dd', '2015/09/01'),
                duration : 2,
                details : 'vcb',
                time : new Date().parse('yyyy/MM/dd', '2015/09/01')
                );
        learningSupportVisit2 = new LearningSupportVisit(
                id: 2,
                student: createStudent(),
                date : new Date().parse('yyyy/MM/dd', '2015/09/01'),
                duration : 2,
                details : 'vcb',
                time : new Date().parse('yyyy/MM/dd', '2015/09/01')
                );
        learningSupportVisits = Arrays.asList(learningSupportVisit1, learningSupportVisit2);
    }
    
    @Test
    public void testMapFromLearningSupportVisitEntity(){
        LearningSupportVisitDto learningSupportVisitTest = LearningSupportVisitDto.mapFromLearningSupportVisitEntity( learningSupportVisit1 )
        assertEquals( learningSupportVisitTest.id, learningSupportVisit1.id );
        assertEquals( learningSupportVisitTest.details, learningSupportVisit1.details);
        assertEquals( learningSupportVisitTest.date, learningSupportVisit1.date)
        assertEquals( learningSupportVisitTest.duration, learningSupportVisit1.duration)
        assertEquals( learningSupportVisitTest.time, learningSupportVisit1.time)
    }
    
    @Test
    public void testMapFromLearningSupportVisitsEntities(){
        List<LearningSupportVisitDto> learningSupportVisitsDtoTest = LearningSupportVisitDto.mapFromLearningSupportVisitsEntities( learningSupportVisits )
        assertEquals( learningSupportVisitsDtoTest[0].id, learningSupportVisit1.id );
        assertEquals( learningSupportVisitsDtoTest[0].studentId, learningSupportVisit1.student.id);
        assertEquals( learningSupportVisitsDtoTest[0].details, learningSupportVisit1.details);
        assertEquals( learningSupportVisitsDtoTest[0].date, learningSupportVisit1.date);
        assertEquals( learningSupportVisitsDtoTest[0].duration, learningSupportVisit1.duration);
        assertEquals( learningSupportVisitsDtoTest[1].id, learningSupportVisit2.id );
        assertEquals( learningSupportVisitsDtoTest[1].time, learningSupportVisit2.time);
        assertEquals( learningSupportVisitsDtoTest[1].details, learningSupportVisit2.details);
        assertEquals( learningSupportVisitsDtoTest[1].date, learningSupportVisit2.date);
        assertEquals( learningSupportVisitsDtoTest[1].duration, learningSupportVisit2.duration);
    }
    
    @Test
    public void testEquals_Same() {
        LearningSupportVisitDto learningSupportVisitDto1 = new LearningSupportVisitDto(learningSupportVisit1.id, learningSupportVisit1.student, learningSupportVisit1.date,  learningSupportVisit1.duration, learningSupportVisit1.details, learningSupportVisit1.time )
        LearningSupportVisitDto learningSupportVisitDto2 = new LearningSupportVisitDto(learningSupportVisit1.id, learningSupportVisit1.student, learningSupportVisit1.date,  learningSupportVisit1.duration, learningSupportVisit1.details, learningSupportVisit1.time )
        assertEquals(learningSupportVisitDto1, learningSupportVisitDto2)
    }
    
    @Test
    public void testEquals_Different() {
        LearningSupportVisitDto learningSupportVisitDto1 = new LearningSupportVisitDto(learningSupportVisit1.id, learningSupportVisit1.student, learningSupportVisit1.date,  learningSupportVisit1.duration, learningSupportVisit1.details, learningSupportVisit1.time)
        LearningSupportVisitDto learningSupportVisitDto2 = new LearningSupportVisitDto(learningSupportVisit2.id, learningSupportVisit2.student, learningSupportVisit2.date,  learningSupportVisit2.duration, learningSupportVisit2.details, learningSupportVisit2.time )
        assertNotEquals(learningSupportVisitDto1, learningSupportVisitDto2)
    }
    
    @Test
    public void testHashCode_Same() {
        LearningSupportVisitDto learningSupportVisitDto1 = new LearningSupportVisitDto(learningSupportVisit1.id, learningSupportVisit1.student, learningSupportVisit1.date,  learningSupportVisit1.duration, learningSupportVisit1.details, learningSupportVisit1.time )
        LearningSupportVisitDto learningSupportVisitDto2 = new LearningSupportVisitDto(learningSupportVisit1.id, learningSupportVisit1.student, learningSupportVisit1.date,  learningSupportVisit1.duration, learningSupportVisit1.details, learningSupportVisit1.time )
        assertEquals(learningSupportVisitDto1.hashCode(), learningSupportVisitDto2.hashCode())
    }
    
    @Test
    public void testHashCode_Different() {
        LearningSupportVisitDto learningSupportVisitDto1 = new LearningSupportVisitDto(learningSupportVisit1.id, learningSupportVisit1.student, learningSupportVisit1.date,  learningSupportVisit1.duration, learningSupportVisit1.details, learningSupportVisit1.time )
        LearningSupportVisitDto learningSupportVisitDto2 = new LearningSupportVisitDto(learningSupportVisit2.id, learningSupportVisit2.student, learningSupportVisit2.date,  learningSupportVisit2.duration, learningSupportVisit2.details, learningSupportVisit2.time )
        assertNotEquals(learningSupportVisitDto1.hashCode(), learningSupportVisitDto2.hashCode())
    }
    
    @Test
    public void testConstructor_LearningSupportVisit() {
        LearningSupportVisitDto learningSupportVisitTest= new LearningSupportVisitDto(learningSupportVisit1)
        assertEquals( learningSupportVisitTest.time, learningSupportVisit1.time);
        assertEquals( learningSupportVisitTest.details, learningSupportVisit1.details);
        assertEquals( learningSupportVisitTest.date, learningSupportVisit1.date)
        assertEquals( learningSupportVisitTest.duration, learningSupportVisit1.duration)
    }
}
