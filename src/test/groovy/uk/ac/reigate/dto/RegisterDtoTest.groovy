package uk.ac.reigate.dto


import static org.junit.Assert.*

import org.junit.Test
import org.junit.Before
import uk.ac.reigate.domain.Address;
import uk.ac.reigate.domain.Person;
import uk.ac.reigate.domain.Staff;
import uk.ac.reigate.domain.academic.AcademicYear;
import uk.ac.reigate.domain.academic.Block
import uk.ac.reigate.domain.academic.Course;
import uk.ac.reigate.domain.academic.CourseGroup
import uk.ac.reigate.domain.academic.Department;
import uk.ac.reigate.domain.academic.Faculty;
import uk.ac.reigate.domain.academic.Period
import uk.ac.reigate.domain.exam.ExamBoard;
import uk.ac.reigate.domain.lookup.Gender;
import uk.ac.reigate.domain.lookup.Level;
import uk.ac.reigate.domain.lookup.PossibleGradeSet
import uk.ac.reigate.domain.lookup.StaffType;
import uk.ac.reigate.domain.lookup.Subject;
import uk.ac.reigate.domain.lookup.Title;
import uk.ac.reigate.domain.lookup.YearGroup;
import uk.ac.reigate.domain.register.Register
import uk.ac.reigate.domain.register.RegisteredSession;

public class RegisterDtoTest {
    
    private RegisteredSession session;
    
    private Block block;
    
    private Period period;
    
    private CourseGroup courseGroup;
    
    private YearGroup yearGroup;
    
    private Level level1;
    
    private Subject maths;
    
    private ExamBoard examBoard1;
    
    private AcademicYear academicYear1;
    
    private AcademicYear year;
    
    private Gender female;
    
    private Title mrs;
    
    private Address address1;
    
    private Person person1;
    
    private Course course;
    
    private Department department;
    
    private StaffType staffType1;
    
    private Staff staff;
    
    private PossibleGradeSet possibleGradeSet
    
    private Faculty faculty;
    
    private Register register1;
    
    private Register register2;
    
    private List<Register> registers
    
    @Before
    public void setup() {
        this.block = new Block(1, 'A', 'BlockA', 'red')
        this.period = new Period(1, '1', 'period1', block, null, null, 10, 11, 4, 4)
        this.session = new RegisteredSession(1, new Date().parse('yyyy/MM/dd', '2011/07/09'), period)
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
        this.department = new Department(1,'maths', 'maths Departmenmt', faculty, staff, staff, true)
        this.courseGroup = new CourseGroup(1, yearGroup, course, year, 'A', department, staff, true, true, 'nothing', 'L-MAH', 1, 1)
        this.register1 = new Register(
                id: 1,
                session: session,
                courseGroup: courseGroup,
                completed: true,
                staffCompleted: staff,
                dateCompleted: new Date().parse('yyyy/MM/dd', '2013/07/09')
                );
        this.register2 = new Register(
                id: 2,
                session: session,
                courseGroup: courseGroup,
                completed: true,
                staffCompleted: staff,
                dateCompleted: new Date().parse('yyyy/MM/dd', '2013/07/09')
                );
        this.registers = Arrays.asList(this.register1, this.register2);
    }
    
    RegisterDto generateRegisterDto() {
        return generateRegister1Dto()
    }
    
    RegisterDto generateRegister1Dto() {
        return new RegisterDto(register1.id, register1.session.id, register1.courseGroup.id, register1.completed, register1.staffCompleted.id, register1.dateCompleted)
    }
    
    RegisterDto generateRegister2Dto() {
        return new RegisterDto(register2.id, register2.session.id, register2.courseGroup.id, register2.completed, register2.staffCompleted.id, register2.dateCompleted)
    }
    
    @Test
    public void testMapFromRegisterEntity() {
        RegisterDto registerTest = RegisterDto.mapFromRegisterEntity( register1 );
        assertEquals( registerTest.id, register1.id);
        assertEquals( registerTest.sessionId, register1.session.id);
        assertEquals( registerTest.courseGroupId, register1.courseGroup.id);
        assertEquals( registerTest.completed, register1.completed);
        assertEquals( registerTest.staffCompletedId, register1.staffCompleted.id);
        assertEquals( registerTest.dateCompleted, register1.dateCompleted);
    }
    
    @Test
    public void testMapFromRegistersEntities(){
        List<RegisterDto> registerTest = RegisterDto.mapFromRegistersEntities( registers )
        assertEquals( registerTest [0].id, register1.id);
        assertEquals( registerTest [0].sessionId, register1.session.id);
        assertEquals( registerTest [0].courseGroupId, register2.courseGroup.id);
        assertEquals( registerTest [0].completed, register1.completed);
        assertEquals( registerTest [0].staffCompletedId, register1.staffCompleted.id);
        assertEquals( registerTest [0].dateCompleted, register1.dateCompleted);
        assertEquals( registerTest [1].id, register2.id );
        assertEquals( registerTest [1].sessionId, register2.session.id);
        assertEquals( registerTest [1].courseGroupId, register2.courseGroup.id);
        assertEquals( registerTest [1].completed, register2.completed);
        assertEquals( registerTest [1].staffCompletedId, register2.staffCompleted.id);
        assertEquals( registerTest [1].dateCompleted, register2.dateCompleted);
    }
    
    @Test
    public void testMapToRegisterEntity(){
        RegisterDto registerDto = generateRegisterDto()
        Register register = RegisterDto.mapToRegisterEntity( registerDto, session, courseGroup, staff )
        assertEquals( register.id, register1.id );
        assertEquals( register.session, register1.session );
        assertEquals( register.courseGroup, register1.courseGroup );
        assertEquals( register.completed, register1.completed );
        assertEquals( register.staffCompleted, register1.staffCompleted );
        assertEquals( register.dateCompleted, register1.dateCompleted );
    }
    
    
    @Test
    public void testEquals_Same() {
        RegisterDto registerDto1 = new RegisterDto(register1.id, register1.session, register1.courseGroup, register1.completed, register1.staffCompleted, register1.dateCompleted)
        RegisterDto registerDto2 = new RegisterDto(register1.id, register1.session, register1.courseGroup, register1.completed, register1.staffCompleted, register1.dateCompleted)
        assertEquals( registerDto1, registerDto2 );
    }
    
    @Test
    public void testEquals_Different() {
        RegisterDto registerDto1 = new RegisterDto(register1.id, register1.session, register1.courseGroup, register1.completed, register1.staffCompleted, register1.dateCompleted)
        RegisterDto registerDto2 = new RegisterDto(register2.id, register2.session, register2.courseGroup, register2.completed, register2.staffCompleted, register2.dateCompleted)
        assertNotEquals( registerDto1, registerDto2 );
    }
    
    @Test
    public void testHashCode_Same() {
        RegisterDto registerDto1 = new RegisterDto(register1.id, register1.session, register1.courseGroup, register1.completed, register1.staffCompleted, register1.dateCompleted)
        RegisterDto registerDto2 = new RegisterDto(register1.id, register1.session, register1.courseGroup, register1.completed, register1.staffCompleted, register1.dateCompleted)
        assertEquals( registerDto1.hashCode(), registerDto2.hashCode() );
    }
    
    @Test
    public void testHashCode_Different() {
        RegisterDto registerDto1 = new RegisterDto(register1.id, register1.session, register1.courseGroup, register1.completed, register1.staffCompleted, register1.dateCompleted)
        RegisterDto registerDto2 = new RegisterDto(register2.id, register2.session, register2.courseGroup, register2.completed, register2.staffCompleted, register2.dateCompleted)
        assertNotEquals( registerDto1.hashCode(), registerDto2.hashCode() );
    }
}
