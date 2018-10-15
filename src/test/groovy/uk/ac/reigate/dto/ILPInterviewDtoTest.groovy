package uk.ac.reigate.dto;

import static org.junit.Assert.*
import org.junit.Before
import org.junit.Test
import uk.ac.reigate.domain.Staff
import uk.ac.reigate.domain.academic.AcademicYear
import uk.ac.reigate.domain.academic.CourseGroup
import uk.ac.reigate.domain.academic.Student;
import uk.ac.reigate.domain.ilp.ILPInterview
import uk.ac.reigate.domain.ilp.ILPInterviewType



public class ILPInterviewDtoTest {
    
    private ILPInterview iLPInterview1
    
    private ILPInterview iLPInterview2
    
    private List<ILPInterview> iLPInterviews
    
    Student createStudent() {
        Student student = new Student()
        student.id = 1
        return student
    }
    
    ILPInterviewType createType() {
        ILPInterviewType type = new ILPInterviewType()
        type.id = 1
        return type
    }
    
    CourseGroup createCourseGroup() {
        CourseGroup courseGroup = new CourseGroup()
    }
    
    Staff createStaff() {
        Staff staff = new Staff()
    }
    
    AcademicYear getAcademicYear(){
        AcademicYear academicYear = new AcademicYear()
    }
    
    @Before
    public void setup() {
        iLPInterview1 = new ILPInterview(
                id: 1,
                student: createStudent(),
                type: createType(),
                courseGroup: createCourseGroup(),
                staff:createStaff(),
                discussion:'M',
                interviewDate: new Date().parse('yyyy/MM/dd', '2015/09/01'),
                interviewTime: new Date().parse('yyyy/MM/dd', '2015/09/01'),
                referLip: true,
                lipReferDate: new Date().parse('yyyy/MM/dd', '2015/09/01'),
                privateEntry: true,
                officeAction : '100',
                officeNotes : 'nothing',
                toFile : true,
                target: 'Test',
                targetByWhen: 'OnGoing',
                year: getAcademicYear()
                );
        iLPInterview2 = new ILPInterview(
                id: 2,
                student: createStudent(),
                type: createType(),
                courseGroup: createCourseGroup(),
                staff:createStaff(),
                discussion:'M',
                interviewDate: new Date().parse('yyyy/MM/dd', '2015/09/01'),
                interviewTime: new Date().parse('yyyy/MM/dd', '2015/09/01'),
                referLip: true,
                lipReferDate: new Date().parse('yyyy/MM/dd', '2015/09/01'),
                privateEntry: true,
                officeAction : '100',
                officeNotes : 'nothing',
                toFile : true,
                target: 'Test',
                targetByWhen: 'OnGoing',
                year: getAcademicYear()
                );
        iLPInterviews = Arrays.asList(iLPInterview1, iLPInterview2);
    }
    
    @Test
    public void testMapFromILPInterviewEntity(){
        ILPInterviewDto iLPInterviewTest = ILPInterviewDto.mapFromILPInterviewEntity( iLPInterview1 )
        assertEquals( iLPInterviewTest.id, iLPInterview1.id );
        assertEquals( iLPInterviewTest.discussion, iLPInterview1.discussion);
        assertEquals( iLPInterviewTest.interviewDate, iLPInterview1.interviewDate);
        assertEquals( iLPInterviewTest.interviewTime, iLPInterview1.interviewTime)
        assertEquals( iLPInterviewTest.referLip, iLPInterview1.referLip)
        assertEquals( iLPInterviewTest.lipReferDate, iLPInterview1.lipReferDate)
        assertEquals( iLPInterviewTest.privateEntry, iLPInterview1.privateEntry)
        assertEquals( iLPInterviewTest.officeAction, iLPInterview1.officeAction)
        assertEquals( iLPInterviewTest.officeNotes, iLPInterview1.officeNotes)
        assertEquals( iLPInterviewTest.toFile, iLPInterview1.toFile)
        assertEquals( iLPInterviewTest.target, iLPInterview1.target)
        assertEquals( iLPInterviewTest.targetByWhen, iLPInterview1.targetByWhen)
        assertEquals( iLPInterviewTest.academicYearId, iLPInterview1.year.id)
    }
    
    @Test
    public void testMapFromILPInterviewsEntities(){
        List<ILPInterviewDto> iLPInterviewsDtoTest = ILPInterviewDto.mapFromILPInterviewsEntities( iLPInterviews )
        assertEquals( iLPInterviewsDtoTest[0].id, iLPInterview1.id );
        assertEquals( iLPInterviewsDtoTest[0].discussion, iLPInterview1.discussion);
        assertEquals( iLPInterviewsDtoTest[0].interviewDate, iLPInterview1.interviewDate);
        assertEquals( iLPInterviewsDtoTest[0].interviewTime, iLPInterview1.interviewTime);
        assertEquals( iLPInterviewsDtoTest[0].referLip, iLPInterview1.referLip);
        assertEquals( iLPInterviewsDtoTest[0].lipReferDate, iLPInterview1.lipReferDate);
        assertEquals( iLPInterviewsDtoTest[0].privateEntry, iLPInterview1.privateEntry);
        assertEquals( iLPInterviewsDtoTest[0].officeAction, iLPInterview1.officeAction);
        assertEquals( iLPInterviewsDtoTest[0].officeNotes, iLPInterview1.officeNotes);
        assertEquals( iLPInterviewsDtoTest[0].toFile, iLPInterview1.toFile);
        assertEquals( iLPInterviewsDtoTest[1].id, iLPInterview2.id );
        assertEquals( iLPInterviewsDtoTest[1].discussion, iLPInterview2.discussion);
        assertEquals( iLPInterviewsDtoTest[1].interviewDate, iLPInterview2.interviewDate);
        assertEquals( iLPInterviewsDtoTest[1].interviewTime, iLPInterview1.interviewTime);
        assertEquals( iLPInterviewsDtoTest[1].referLip, iLPInterview2.referLip);
        assertEquals( iLPInterviewsDtoTest[1].lipReferDate, iLPInterview2.lipReferDate);
        assertEquals( iLPInterviewsDtoTest[1].privateEntry, iLPInterview2.privateEntry);
        assertEquals( iLPInterviewsDtoTest[1].officeAction, iLPInterview2.officeAction);
        assertEquals( iLPInterviewsDtoTest[1].officeNotes, iLPInterview2.officeNotes);
        assertEquals( iLPInterviewsDtoTest[1].toFile, iLPInterview2.toFile);
        assertEquals( iLPInterviewsDtoTest[1].target, iLPInterview2.target);
        assertEquals( iLPInterviewsDtoTest[1].targetByWhen, iLPInterview2.targetByWhen);
        assertEquals( iLPInterviewsDtoTest[1].academicYearId, iLPInterview2.year.id);
    }
    
    @Test
    public void testEquals_Same() {
        ILPInterviewDto iLPInterviewDto1 = new ILPInterviewDto(iLPInterview1)
        ILPInterviewDto iLPInterviewDto2 = new ILPInterviewDto(iLPInterview1)
        assertEquals(iLPInterviewDto1, iLPInterviewDto2)
    }
    
    @Test
    public void testEquals_Different() {
        ILPInterviewDto iLPInterviewDto1 = new ILPInterviewDto(iLPInterview1)
        ILPInterviewDto iLPInterviewDto2 = new ILPInterviewDto(iLPInterview2)
        assertNotEquals(iLPInterviewDto1, iLPInterviewDto2)
    }
    
    @Test
    public void testHashCode_Same() {
        ILPInterviewDto iLPInterviewDto1 = new ILPInterviewDto(iLPInterview1)
        ILPInterviewDto iLPInterviewDto2 = new ILPInterviewDto(iLPInterview1)
        assertEquals(iLPInterviewDto1.hashCode(), iLPInterviewDto2.hashCode())
    }
    
    @Test
    public void testHashCode_Different() {
        ILPInterviewDto iLPInterviewDto1 = new ILPInterviewDto(iLPInterview1)
        ILPInterviewDto iLPInterviewDto2 = new ILPInterviewDto(iLPInterview2)
        assertNotEquals(iLPInterviewDto1.hashCode(), iLPInterviewDto2.hashCode())
    }
    
    @Test
    public void testConstructor_ILPInterview() {
        ILPInterviewDto iLPInterviewTest= new ILPInterviewDto(iLPInterview1)
        assertEquals( iLPInterviewTest.discussion, iLPInterview1.discussion);
        assertEquals( iLPInterviewTest.interviewDate, iLPInterview1.interviewDate);
        assertEquals( iLPInterviewTest.interviewTime, iLPInterview1.interviewTime)
        assertEquals( iLPInterviewTest.referLip, iLPInterview1.referLip)
        assertEquals( iLPInterviewTest.lipReferDate, iLPInterview1.lipReferDate)
        assertEquals( iLPInterviewTest.privateEntry, iLPInterview1.privateEntry)
        assertEquals( iLPInterviewTest.officeAction, iLPInterview1.officeAction)
        assertEquals( iLPInterviewTest.officeNotes, iLPInterview1.officeNotes)
        assertEquals( iLPInterviewTest.toFile, iLPInterview1.toFile)
        assertEquals( iLPInterviewTest.target, iLPInterview1.target)
        assertEquals( iLPInterviewTest.targetByWhen, iLPInterview1.targetByWhen)
        assertEquals( iLPInterviewTest.academicYearId, iLPInterview1.year.id)
    }
}
