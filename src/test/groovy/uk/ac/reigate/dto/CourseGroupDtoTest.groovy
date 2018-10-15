package uk.ac.reigate.dto;

import static org.junit.Assert.*

import org.junit.Test
import org.junit.Before
import uk.ac.reigate.domain.Address
import uk.ac.reigate.domain.Person
import uk.ac.reigate.domain.Staff
import uk.ac.reigate.domain.academic.AcademicYear
import uk.ac.reigate.domain.academic.Course
import uk.ac.reigate.domain.academic.CourseGroup
import uk.ac.reigate.domain.academic.Department
import uk.ac.reigate.domain.academic.Faculty
import uk.ac.reigate.domain.exam.ExamBoard
import uk.ac.reigate.domain.lookup.Gender
import uk.ac.reigate.domain.lookup.Level
import uk.ac.reigate.domain.lookup.PossibleGradeSet
import uk.ac.reigate.domain.lookup.StaffType
import uk.ac.reigate.domain.lookup.Subject
import uk.ac.reigate.domain.lookup.Title
import uk.ac.reigate.domain.lookup.YearGroup



public class CourseGroupDtoTest {
    
    private YearGroup yearGroup
    
    private Level level1
    
    private Subject maths
    
    private ExamBoard examBoard1
    
    private AcademicYear academicYear1
    
    private AcademicYear year
    
    private Gender female
    
    private Title mrs
    
    private Address address1
    
    private Person person1
    
    private Course course
    
    private Department department
    
    private StaffType staffType1
    
    private Staff staff
    
    private Faculty faculty
    
    private PossibleGradeSet possibleGradeSet
    
    private CourseGroup courseGroup1
    
    private CourseGroup courseGroup2
    
    private List<CourseGroup> courseGroups
    
    @Before
    public void setupTests() {
        this.yearGroup = new YearGroup(1, '15', '2015')
        this.level1 = new Level(1, 'H', 'AS Level', possibleGradeSet, 1, 'A')
        this.maths = new Subject(1, 'MA', 'Mathematics')
        this.examBoard1 = new ExamBoard(1, 'EDEXCEL', 'EXCEL Science', 'B', '10', '1', '1828932')
        this.academicYear1 = new AcademicYear(1, '11', '2011', new Date().parse('yyyy/MM/dd', '2011/11/11'), new Date().parse('yyyy/MM/dd', '2016/11/11'))
        this.year = new AcademicYear(1, '11', '2011', new Date().parse('yyyy/MM/dd', '2011/11/11'), new Date().parse('yyyy/MM/dd', '2016/11/11'))
        this.female= new Gender(1, 'F', 'Female')
        this.mrs = new Title(1, '2', 'Mrs')
        this.address1 = new Address(1, 'Flat D', 'Stag', 'Stanley', 'west', 'park', 'Wallington', '', 'E161FF', '', '', '', '')
        this.person1 = new Person(1, mrs, 'Vinaya', 'Vin', 'Vin', 'Bali', 'Mick', 'Uday', new Date().parse('yyyy/MM/dd', '1991/11/11'), female, address1, '07809817665', '0890788889', '08937737737', 'mbvinayabali@gmail.com', true, null, 'vbm')
        this.course = new Course(1, level1, maths, 11, 'learn', examBoard1, '1', academicYear1, academicYear1, 'E161FF', 'London', 'working', 'L-MAH')
        this.staffType1 = new StaffType(1, 'L', 'Lower', true)
        this.staff = new Staff(1, person1, staffType1, true, 'VBM', 'vin', 'vbm', new Date().parse('yyyy/MM/dd', '2011/11/11'), new Date().parse('yyyy/MM/dd', '2016/11/11'), 11, 11);
        this.faculty = new Faculty(1, 'm', 'maths', staff, staff, staff, staff, staff, new Date().parse('yyyy/MM/dd', '2011/11/11'), new Date().parse('yyyy/MM/dd', '2013/11/11'))
        this.department = new Department(1, 'maths', 'maths Departmenmt', faculty, staff, staff, true)
        this.courseGroup1 = new CourseGroup(
                id: 1,
                yearGroup: yearGroup,
                course: course,
                year: year,
                code: 'A',
                department: department,
                courseLeader: staff,
                displayOnTimetable: true,
                hasRegister: true,
                notes: 'Nothing',
                spec: 'L-MAH',
                plh: 1,
                peeph: 2
                );
        this.courseGroup2 = new CourseGroup(
                id: 2,
                yearGroup: yearGroup,
                course: course,
                year: year,
                code: 'A',
                department: department,
                courseLeader: staff,
                displayOnTimetable: true,
                hasRegister: true,
                notes: 'Nothing',
                spec: 'L-MAH',
                plh: 1,
                peeph: 2
                );
        this.courseGroups = Arrays.asList(courseGroup1, courseGroup2);
    }
    
    CourseGroupDto generateCourseGroupDto() {
        return generateCourseGroup1Dto()
    }
    
    CourseGroupDto generateCourseGroup1Dto() {
        return new CourseGroupDto(courseGroup1.id, courseGroup1.yearGroup.id, courseGroup1.course.id, courseGroup1.year.id, courseGroup1.code, courseGroup1.department.id, courseGroup1.courseLeader.id, courseGroup1.displayOnTimetable, courseGroup1.hasRegister, courseGroup1.notes, courseGroup1.spec, courseGroup1.plh, courseGroup1.peeph)
    }
    
    CourseGroupDto generateCourseGroup2Dto() {
        return new CourseGroupDto(courseGroup2.id, courseGroup2.yearGroup.id, courseGroup2.course.id, courseGroup2.year.id, courseGroup2.code, courseGroup2.department.id, courseGroup2.courseLeader.id, courseGroup2.displayOnTimetable, courseGroup2.hasRegister, courseGroup2.notes, courseGroup2.spec, courseGroup2.plh, courseGroup2.peeph)
    }
    
    @Test
    public void testMapFromCourseGroupEntity() {
        
        CourseGroupDto courseGroup = CourseGroupDto.mapFromCourseGroupEntity( courseGroup1 )
        assertEquals( courseGroup.id, courseGroup1.id);
        assertEquals( courseGroup.yearGroupId,  courseGroup1.year.id);
        assertEquals( courseGroup.courseId, courseGroup1.course.id);
        assertEquals( courseGroup.yearId, courseGroup1.year.id);
        assertEquals( courseGroup.departmentId, courseGroup1.department.id);
        assertEquals( courseGroup.courseLeaderId, courseGroup1.courseLeader.id);
        assertEquals( courseGroup.displayOnTimetable, courseGroup1.displayOnTimetable);
        assertEquals( courseGroup.hasRegister, courseGroup1.hasRegister);
        assertEquals( courseGroup.notes, courseGroup1.notes);
    }
    
    @Test
    public void testMapFromCourseGroupsEntities(){
        List<CourseGroupDto> courseGroup = CourseGroupDto.mapFromCourseGroupsEntities( courseGroups )
        assertEquals( courseGroup[0].id, courseGroup1.id);
        assertEquals( courseGroup[0].yearGroupId, courseGroup1.yearGroup.id);
        assertEquals( courseGroup[0].courseId, courseGroup1.course.id);
        assertEquals( courseGroup[0].yearId, courseGroup1.year.id);
        assertEquals( courseGroup[0].departmentId, courseGroup1.department.id);
        assertEquals( courseGroup[0].courseLeaderId, courseGroup1.courseLeader.id);
        assertEquals( courseGroup[0].displayOnTimetable, courseGroup1.displayOnTimetable);
        assertEquals( courseGroup[0].hasRegister, courseGroup1.hasRegister);
        assertEquals( courseGroup[0].notes, courseGroup1.notes);
        assertEquals( courseGroup[1].id, courseGroup2.id);
        assertEquals( courseGroup[1].yearGroupId, courseGroup2.yearGroup.id);
        assertEquals( courseGroup[1].courseId, courseGroup2.course.id);
        assertEquals( courseGroup[1].yearId, courseGroup2.year.id);
        assertEquals( courseGroup[1].departmentId, courseGroup2.department.id);
        assertEquals( courseGroup[1].courseLeaderId, courseGroup2.courseLeader.id);
        assertEquals( courseGroup[1].displayOnTimetable, courseGroup2.displayOnTimetable);
        assertEquals( courseGroup[1].hasRegister, courseGroup2.hasRegister);
        assertEquals( courseGroup[1].notes, courseGroup2.notes);
    }
    
    @Test
    public void testMapToCourseGroupEntity(){
        CourseGroupDto courseGroupDto = generateCourseGroupDto()
        CourseGroup courseGroup = CourseGroupDto.mapToCourseGroupEntity( courseGroupDto, yearGroup, course, year, department, staff);
        assertEquals( courseGroup.id, courseGroup1.id);
        assertEquals( courseGroup.yearGroup, courseGroup1.yearGroup);
        assertEquals( courseGroup.course, courseGroup1.course);
        assertEquals( courseGroup.year, courseGroup1.year);
        assertEquals( courseGroup.department, courseGroup1.department);
        assertEquals( courseGroup.courseLeader, courseGroup1.courseLeader);
        assertEquals( courseGroup.displayOnTimetable, courseGroup1.displayOnTimetable);
        assertEquals( courseGroup.hasRegister, courseGroup1.hasRegister);
        assertEquals( courseGroup.notes, courseGroup1.notes);
    }
    
    @Test
    public void testEquals_Same() {
        CourseGroupDto courseGroupDto1 = new CourseGroupDto(courseGroup1.id, courseGroup1.yearGroup.id, courseGroup1.course.id, courseGroup1.year.id, courseGroup1.code, courseGroup1.department.id, courseGroup1.courseLeader.id, courseGroup1.displayOnTimetable, courseGroup1.hasRegister, courseGroup1.notes, courseGroup1.spec, courseGroup1.plh, courseGroup1.peeph)
        CourseGroupDto courseGroupDto2 = new CourseGroupDto(courseGroup1.id, courseGroup1.yearGroup.id, courseGroup1.course.id, courseGroup1.year.id, courseGroup1.code, courseGroup1.department.id, courseGroup1.courseLeader.id, courseGroup1.displayOnTimetable, courseGroup1.hasRegister, courseGroup1.notes, courseGroup1.spec, courseGroup1.plh, courseGroup1.peeph)
        assertEquals( courseGroupDto1, courseGroupDto2)
    }
    
    @Test
    public void testEquals_Different() {
        CourseGroupDto courseGroupDto1 = new CourseGroupDto(courseGroup1.id, courseGroup1.yearGroup.id, courseGroup1.course.id, courseGroup1.year.id, courseGroup1.code, courseGroup1.department.id, courseGroup1.courseLeader.id, courseGroup1.displayOnTimetable, courseGroup1.hasRegister, courseGroup1.notes, courseGroup1.spec, courseGroup1.plh, courseGroup1.peeph)
        CourseGroupDto courseGroupDto2 = new CourseGroupDto(courseGroup2.id, courseGroup2.yearGroup.id, courseGroup2.course.id, courseGroup2.year.id, courseGroup2.code, courseGroup2.department.id, courseGroup2.courseLeader.id, courseGroup2.displayOnTimetable, courseGroup2.hasRegister, courseGroup2.notes, courseGroup2.spec, courseGroup2.plh, courseGroup2.peeph)
        assertNotEquals( courseGroupDto1, courseGroupDto2)
    }
    
    @Test
    public void testHashCode_Same() {
        CourseGroupDto courseGroupDto1 = new CourseGroupDto(courseGroup1.id, courseGroup1.yearGroup.id, courseGroup1.course.id, courseGroup1.year.id, courseGroup1.code, courseGroup1.department.id, courseGroup1.courseLeader.id, courseGroup1.displayOnTimetable, courseGroup1.hasRegister, courseGroup1.notes, courseGroup1.spec, courseGroup1.plh, courseGroup1.peeph)
        CourseGroupDto courseGroupDto2 = new CourseGroupDto(courseGroup1.id, courseGroup1.yearGroup.id, courseGroup1.course.id, courseGroup1.year.id, courseGroup1.code, courseGroup1.department.id, courseGroup1.courseLeader.id, courseGroup1.displayOnTimetable, courseGroup1.hasRegister, courseGroup1.notes, courseGroup1.spec, courseGroup1.plh, courseGroup1.peeph)
        assertEquals( courseGroupDto1.hashCode(), courseGroupDto2.hashCode())
    }
    
    @Test
    public void testHashCode_Different() {
        CourseGroupDto courseGroupDto1 = new CourseGroupDto(courseGroup1.id, courseGroup1.yearGroup.id, courseGroup1.course.id, courseGroup1.year.id, courseGroup1.code, courseGroup1.department.id, courseGroup1.courseLeader.id, courseGroup1.displayOnTimetable, courseGroup1.hasRegister, courseGroup1.notes, courseGroup1.spec, courseGroup1.plh, courseGroup1.peeph)
        CourseGroupDto courseGroupDto2 = new CourseGroupDto(courseGroup2.id, courseGroup2.yearGroup.id, courseGroup2.course.id, courseGroup2.year.id, courseGroup2.code, courseGroup2.department.id, courseGroup2.courseLeader.id, courseGroup2.displayOnTimetable, courseGroup2.hasRegister, courseGroup2.notes, courseGroup2.spec, courseGroup2.plh, courseGroup2.peeph)
        assertNotEquals( courseGroupDto1.hashCode(), courseGroupDto2.hashCode())
    }
    
    @Test
    public void testConstructor_CourseGroup() {
        CourseGroupDto courseGroup = new CourseGroupDto(courseGroup1)
        assertEquals( courseGroup.id, courseGroup1.id);
        assertEquals( courseGroup.yearGroupId, courseGroup1.yearGroup.id);
        assertEquals( courseGroup.courseId, courseGroup1.course.id);
        assertEquals( courseGroup.yearId, courseGroup1.year.id);
        assertEquals( courseGroup.departmentId, courseGroup1.department.id);
        assertEquals( courseGroup.courseLeaderId, courseGroup1.courseLeader.id);
        assertEquals( courseGroup.displayOnTimetable, courseGroup1.displayOnTimetable);
        assertEquals( courseGroup.hasRegister, courseGroup1.hasRegister);
        assertEquals( courseGroup.notes, courseGroup1.notes);
    }
}
