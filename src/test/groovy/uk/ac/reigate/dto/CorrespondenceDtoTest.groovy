package uk.ac.reigate.dto;

import static org.junit.Assert.*
import org.junit.Before

import java.util.List;

import org.junit.Before;
import org.junit.Test

import uk.ac.reigate.domain.academic.AcademicYear;
import uk.ac.reigate.domain.academic.Course
import uk.ac.reigate.domain.academic.CourseGroup
import uk.ac.reigate.domain.academic.Student
import uk.ac.reigate.domain.exam.ExamBoard;
import uk.ac.reigate.domain.ilp.Correspondence
import uk.ac.reigate.domain.ilp.CorrespondenceType;
import uk.ac.reigate.domain.ilp.Letter
import uk.ac.reigate.domain.lookup.Level;
import uk.ac.reigate.domain.lookup.Subject;


public class CorrespondenceDtoTest {
    
    private Level level1
    
    private Subject maths
    
    private ExamBoard examBoard1
    
    private AcademicYear academicYear1
    
    private Course course
    
    private CorrespondenceType correspondenceType
    
    private Correspondence correspondence1
    
    private Correspondence correspondence2
    
    private List<Correspondence> correspondences
    
    Student createStudent() {
        Student student = new Student()
        student.id = 1
        return student
    }
    
    Student createStudent2() {
        Student student = new Student()
        student.id = 2
        return student
    }
    
    Letter createLetter() {
        Letter letter = new Letter()
        letter.id = 1
        return letter
    }
    
    CourseGroup createCourseGroup() {
        CourseGroup course = new CourseGroup()
        course.id = 1
        return course
    }
    
    @Before
    public void setup() {
        this.correspondenceType = new CorrespondenceType(1, 'A')
        correspondence1 = new Correspondence(
                id: 1,
                student: createStudent(),
                course: createCourseGroup(),
                correspondence: 't',
                title: 'vinaya',
                date : new Date().parse('yyyy/MM/dd', '2013/07/09'),
                from: 'college',
                to:'parents',
                letter: createLetter(),
                staffAdvised: new Date().parse('yyyy/MM/dd', '2013/07/09'),
                type: correspondenceType,
                producedBy : 'vin',
                privateEntry: true,
                processStage: 2,
                attachmentsSent : 'yes'
                );
        correspondence2 = new Correspondence(
                id: 2,
                student: createStudent(),
                course: createCourseGroup(),
                correspondence: 't',
                title: 'vinaya',
                date : new Date().parse('yyyy/MM/dd', '2013/07/09'),
                from: 'college',
                to:'parents',
                letter: createLetter(),
                staffAdvised: new Date().parse('yyyy/MM/dd', '2013/07/09'),
                type: correspondenceType,
                producedBy : 'vin',
                privateEntry: true,
                processStage: 2,
                attachmentsSent : 'yes'
                );
        correspondences = Arrays.asList(correspondence1, correspondence2);
    }
    
    @Test
    void testConstructor_correspondence() {
        CorrespondenceDto correspondenceTest = new CorrespondenceDto( correspondence1 )
        assertEquals( correspondenceTest.id, correspondence1.id );
        assertEquals( correspondenceTest.studentId, correspondence1.student.id);
        assertEquals( correspondenceTest.correspondence, correspondence1.correspondence);
        assertEquals( correspondenceTest.title, correspondence1.title);
        assertEquals( correspondenceTest.date, correspondence1.date);
        assertEquals( correspondenceTest.from, correspondence1.from);
        assertEquals( correspondenceTest.to, correspondence1.to);
        assertEquals( correspondenceTest.letterId, correspondence1.letter.id);
    }
    
    @Test
    public void testMapFromCorrespondenceEntity(){
        CorrespondenceDto correspondenceTest = CorrespondenceDto.mapFromCorrespondenceEntity( correspondence1 )
        assertEquals( correspondenceTest.id, correspondence1.id );
        assertEquals( correspondenceTest.studentId, correspondence1.student.id);
        assertEquals( correspondenceTest.correspondence, correspondence1.correspondence);
        assertEquals( correspondenceTest.title, correspondence1.title);
        assertEquals( correspondenceTest.date, correspondence1.date);
        assertEquals( correspondenceTest.from, correspondence1.from);
        assertEquals( correspondenceTest.to, correspondence1.to);
        assertEquals( correspondenceTest.letterId, correspondence1.letter.id);
    }
    
    @Test
    public void testMapFromCorrespondencesEntities(){
        List<CorrespondenceDto> correspondencesDtoTest = CorrespondenceDto.mapFromCorrespondencesEntities( this.correspondences )
        assertEquals( correspondencesDtoTest[0].id, correspondence1.id );
        assertEquals( correspondencesDtoTest[0].studentId, correspondence1.student.id);
        assertEquals( correspondencesDtoTest[0].correspondence, correspondence1.correspondence);
        assertEquals( correspondencesDtoTest[0].title, correspondence1.title);
        assertEquals( correspondencesDtoTest[0].date, correspondence1.date);
        assertEquals( correspondencesDtoTest[0].from, correspondence1.from);
        assertEquals( correspondencesDtoTest[0].to, correspondence1.to);
        assertEquals( correspondencesDtoTest[0].letterId, correspondence1.letter.id);
        assertEquals( correspondencesDtoTest[1].id, correspondence2.id );
        assertEquals( correspondencesDtoTest[1].studentId, correspondence2.student.id);
        assertEquals( correspondencesDtoTest[1].correspondence, correspondence1.correspondence);
        assertEquals( correspondencesDtoTest[1].title, correspondence1.title);
        assertEquals( correspondencesDtoTest[1].date, correspondence1.date);
        assertEquals( correspondencesDtoTest[1].from, correspondence1.from);
        assertEquals( correspondencesDtoTest[1].to, correspondence1.to);
        assertEquals( correspondencesDtoTest[1].letterId, correspondence1.letter.id);
    }
    
    @Test
    public void testEquals_Same() {
        CorrespondenceDto correspondenceDto1 = new CorrespondenceDto(correspondence1.id, correspondence1.student, correspondence1.course, correspondence1.correspondence, correspondence1.title, correspondence1.date, correspondence1.from, correspondence1.to, correspondence1.letter, correspondence1.staffAdvised, correspondence1.type, correspondence1.producedBy, correspondence1.privateEntry, correspondence1.processStage, correspondence1.attachmentsSent)
        CorrespondenceDto correspondenceDto2 = new CorrespondenceDto(correspondence1.id, correspondence1.student, correspondence1.course, correspondence1.correspondence, correspondence1.title, correspondence1.date, correspondence1.from, correspondence1.to, correspondence1.letter, correspondence1.staffAdvised, correspondence1.type, correspondence1.producedBy, correspondence1.privateEntry, correspondence1.processStage, correspondence1.attachmentsSent)
        assertEquals(correspondenceDto1, correspondenceDto2)
    }
    
    @Test
    public void testEquals_Different() {
        CorrespondenceDto correspondenceDto1 = new CorrespondenceDto(correspondence1.id, correspondence1.student, correspondence1.course, correspondence1.correspondence, correspondence1.title, correspondence1.date, correspondence1.from, correspondence1.to, correspondence1.letter, correspondence1.staffAdvised, correspondence1.type, correspondence1.producedBy, correspondence1.privateEntry, correspondence1.processStage, correspondence1.attachmentsSent)
        CorrespondenceDto correspondenceDto2 = new CorrespondenceDto(correspondence2.id, correspondence2.student, correspondence2.course, correspondence2.correspondence, correspondence2.title, correspondence2.date, correspondence2.from, correspondence2.to, correspondence2.letter, correspondence2.staffAdvised, correspondence2.type, correspondence2.producedBy, correspondence2.privateEntry, correspondence2.processStage, correspondence2.attachmentsSent)
        assertNotEquals(correspondenceDto1, correspondenceDto2)
    }
    
    @Test
    public void testHashCode_Same() {
        CorrespondenceDto correspondenceDto1 = new CorrespondenceDto(correspondence1.id, correspondence1.student, correspondence1.course, correspondence1.correspondence, correspondence1.title, correspondence1.date, correspondence1.from, correspondence1.to, correspondence1.letter, correspondence1.staffAdvised, correspondence1.type, correspondence1.producedBy, correspondence1.privateEntry, correspondence1.processStage, correspondence1.attachmentsSent)
        CorrespondenceDto correspondenceDto2 = new CorrespondenceDto(correspondence1.id, correspondence1.student, correspondence1.course, correspondence1.correspondence, correspondence1.title, correspondence1.date, correspondence1.from, correspondence1.to, correspondence1.letter, correspondence1.staffAdvised, correspondence1.type, correspondence1.producedBy, correspondence1.privateEntry, correspondence1.processStage, correspondence1.attachmentsSent)
        assertEquals(correspondenceDto1.hashCode(), correspondenceDto2.hashCode())
    }
    
    @Test
    public void testHashCode_Different() {
        CorrespondenceDto correspondenceDto1 = new CorrespondenceDto(correspondence1.id, correspondence1.student, correspondence1.course, correspondence1.correspondence, correspondence1.title, correspondence1.date, correspondence1.from, correspondence1.to, correspondence1.letter, correspondence1.staffAdvised, correspondence1.type, correspondence1.producedBy, correspondence1.privateEntry, correspondence1.processStage, correspondence1.attachmentsSent)
        CorrespondenceDto correspondenceDto2 = new CorrespondenceDto(correspondence2.id, correspondence2.student, correspondence2.course, correspondence2.correspondence, correspondence2.title, correspondence2.date, correspondence2.from, correspondence2.to, correspondence2.letter, correspondence2.staffAdvised, correspondence2.type, correspondence2.producedBy, correspondence2.privateEntry, correspondence2.processStage, correspondence2.attachmentsSent)
        assertNotEquals(correspondenceDto1.hashCode(), correspondenceDto2.hashCode())
    }
    
    @Test
    public void testMapToCorrespondenceEntity() {
        Student student = new Student()
        Letter letter = new Letter()
        CorrespondenceDto correspondenceDto = new CorrespondenceDto(correspondence1.id, correspondence1.student, correspondence1.course, correspondence1.correspondence, correspondence1.title, correspondence1.date, correspondence1.from, correspondence1.to, correspondence1.letter, correspondence1.staffAdvised, correspondence1.type, correspondence1.producedBy, correspondence1.privateEntry, correspondence1.processStage, correspondence1.attachmentsSent)
        Correspondence correspondence = CorrespondenceDto.mapToCorrespondenceEntity( correspondenceDto, student, course, correspondenceType, letter )
        assertEquals( correspondence.id, correspondence1.id );
        assertEquals( correspondence.correspondence, correspondence1.correspondence);
        assertEquals( correspondence.title, correspondence1.title);
        assertEquals( correspondence.date, correspondence1.date);
        assertEquals( correspondence.from, correspondence1.from);
        assertEquals( correspondence.to, correspondence1.to);
    }
    
    @Test
    public void testConstructor_Correspondence() {
        CorrespondenceDto correspondence = new CorrespondenceDto(correspondence1.id, correspondence1.student, correspondence1.course, correspondence1.correspondence, correspondence1.title, correspondence1.date, correspondence1.from, correspondence1.to, correspondence1.letter, correspondence1.staffAdvised, correspondence1.type, correspondence1.producedBy, correspondence1.privateEntry, correspondence1.processStage, correspondence1.attachmentsSent)
        assertEquals( correspondence.id, correspondence1.id );
        assertEquals( correspondence.studentId, correspondence1.student.id);
        assertEquals( correspondence.courseId, correspondence1.course.id);
        assertEquals( correspondence.correspondence, correspondence1.correspondence);
        assertEquals( correspondence.title, correspondence1.title);
        assertEquals( correspondence.date, correspondence1.date);
        assertEquals( correspondence.from, correspondence1.from);
        assertEquals( correspondence.to, correspondence1.to);
    }
}
