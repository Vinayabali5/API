package uk.ac.reigate.dto;

import static org.junit.Assert.*
import org.junit.Before
import org.junit.Test
import uk.ac.reigate.domain.academic.Student;
import uk.ac.reigate.domain.register.StudentOverallAttendance



public class StudentOverallAttendanceDtoTest {
    
    private StudentOverallAttendance studentOverallAttendance1
    
    private StudentOverallAttendance studentOverallAttendance2
    
    private List<StudentOverallAttendance> studentOverallAttendances
    
    Student createStudent() {
        Student student = new Student()
        student.id = 1
        return student
    }
    
    @Before
    public void setup() {
        studentOverallAttendance1 = new StudentOverallAttendance(
                student: createStudent(),
                totalSessions:1,
                totalAbsence: 2,
                totalAuthorisedAbsence:4,
                totalAdjusted: 5,
                totalLate: 5,
                totalAuthorisedLate: 5,
                attendance : 5.5f,
                adjustedAttendance : 5.5f,
                punctuality : 5.5f,
                adjustedPunctuality : 5.5f
                );
        studentOverallAttendance2 = new StudentOverallAttendance(
                student: createStudent(),
                totalSessions:1,
                totalAbsence: 2,
                totalAuthorisedAbsence:4,
                totalAdjusted: 5,
                totalLate: 5,
                totalAuthorisedLate: 5,
                attendance : 5.5f,
                adjustedAttendance : 5.5f,
                punctuality : 5.5f,
                adjustedPunctuality : 5.5f
                );
        studentOverallAttendances = Arrays.asList(studentOverallAttendance1, studentOverallAttendance2);
    }
    
    @Test
    public void testMapFromStudentOverallAttendanceEntity(){
        StudentOverallAttendanceDto studentOverallAttendanceTest = StudentOverallAttendanceDto.mapFromStudentOverallAttendanceEntity( studentOverallAttendance1 )
        assertEquals( studentOverallAttendanceTest.totalSessions, studentOverallAttendance1.totalSessions);
        assertEquals( studentOverallAttendanceTest.totalAbsence, studentOverallAttendance1.totalAbsence);
        assertEquals( studentOverallAttendanceTest.totalAuthorisedAbsence, studentOverallAttendance1.totalAuthorisedAbsence)
        assertEquals( studentOverallAttendanceTest.totalAdjusted, studentOverallAttendance1.totalAdjusted)
        assertEquals( studentOverallAttendanceTest.totalLate, studentOverallAttendance1.totalLate)
        assertEquals( studentOverallAttendanceTest.totalAuthorisedLate, studentOverallAttendance1.totalAuthorisedLate)
        assertEquals( studentOverallAttendanceTest.attendance, studentOverallAttendance1.attendance, 5.5f)
        assertEquals( studentOverallAttendanceTest.adjustedAttendance, studentOverallAttendance1.adjustedAttendance, 5.5f)
        assertEquals( studentOverallAttendanceTest.punctuality, studentOverallAttendance1.punctuality, 5.5f)
        assertEquals( studentOverallAttendanceTest.adjustedPunctuality, studentOverallAttendance1.adjustedPunctuality, 5.5f)
    }
    
    @Test
    public void testMapFromStudentOverallAttendancesEntities(){
        List<StudentOverallAttendanceDto> studentOverallAttendancesDtoTest = StudentOverallAttendanceDto.mapFromStudentOverallAttendancesEntities( studentOverallAttendances )
        assertEquals( studentOverallAttendancesDtoTest[0].totalSessions, studentOverallAttendance1.totalSessions);
        assertEquals( studentOverallAttendancesDtoTest[0].totalAbsence, studentOverallAttendance1.totalAbsence);
        assertEquals( studentOverallAttendancesDtoTest[0].totalAuthorisedAbsence, studentOverallAttendance1.totalAuthorisedAbsence);
        assertEquals( studentOverallAttendancesDtoTest[0].totalAdjusted, studentOverallAttendance1.totalAdjusted);
        assertEquals( studentOverallAttendancesDtoTest[0].totalLate, studentOverallAttendance1.totalLate);
        assertEquals( studentOverallAttendancesDtoTest[0].totalAuthorisedLate, studentOverallAttendance1.totalAuthorisedLate);
        assertEquals( studentOverallAttendancesDtoTest[0].attendance, studentOverallAttendance1.attendance, 5.5f);
        assertEquals( studentOverallAttendancesDtoTest[0].adjustedAttendance, studentOverallAttendance1.adjustedAttendance, 5.5f);
        assertEquals( studentOverallAttendancesDtoTest[0].punctuality, studentOverallAttendance1.punctuality, 5.5f);
        assertEquals( studentOverallAttendancesDtoTest[0].adjustedPunctuality, studentOverallAttendance1.adjustedPunctuality, 5.5f);
        assertEquals( studentOverallAttendancesDtoTest[1].totalSessions, studentOverallAttendance2.totalSessions);
        assertEquals( studentOverallAttendancesDtoTest[1].totalAbsence, studentOverallAttendance2.totalAbsence);
        assertEquals( studentOverallAttendancesDtoTest[1].totalAuthorisedAbsence, studentOverallAttendance2.totalAuthorisedAbsence);
        assertEquals( studentOverallAttendancesDtoTest[1].totalAdjusted, studentOverallAttendance2.totalAdjusted);
        assertEquals( studentOverallAttendancesDtoTest[1].totalLate, studentOverallAttendance2.totalLate);
        assertEquals( studentOverallAttendancesDtoTest[1].totalAuthorisedLate, studentOverallAttendance2.totalAuthorisedLate);
        assertEquals( studentOverallAttendancesDtoTest[1].attendance, studentOverallAttendance2.attendance, 5.5f);
        assertEquals( studentOverallAttendancesDtoTest[1].adjustedAttendance, studentOverallAttendance2.adjustedAttendance, 5.5f);
        assertEquals( studentOverallAttendancesDtoTest[1].punctuality, studentOverallAttendance2.punctuality, 5.5f);
        assertEquals( studentOverallAttendancesDtoTest[1].adjustedPunctuality, studentOverallAttendance2.adjustedPunctuality, 5.5f);
    }
    
    
    @Test
    public void testConstructor_StudentOverallAttendance() {
        StudentOverallAttendanceDto studentOverallAttendanceTest= new StudentOverallAttendanceDto(studentOverallAttendance1)
        assertEquals( studentOverallAttendanceTest.totalSessions, studentOverallAttendance1.totalSessions);
        assertEquals( studentOverallAttendanceTest.totalAbsence, studentOverallAttendance1.totalAbsence);
        assertEquals( studentOverallAttendanceTest.totalAuthorisedAbsence, studentOverallAttendance1.totalAuthorisedAbsence)
        assertEquals( studentOverallAttendanceTest.totalAdjusted, studentOverallAttendance1.totalAdjusted)
        assertEquals( studentOverallAttendanceTest.totalLate, studentOverallAttendance1.totalLate)
        assertEquals( studentOverallAttendanceTest.totalAuthorisedLate, studentOverallAttendance1.totalAuthorisedLate)
        assertEquals( studentOverallAttendanceTest.attendance, studentOverallAttendance1.attendance, 5.5f)
        assertEquals( studentOverallAttendanceTest.adjustedAttendance, studentOverallAttendance1.adjustedAttendance, 5.5f)
        assertEquals( studentOverallAttendanceTest.punctuality, studentOverallAttendance1.punctuality, 5.5f)
        assertEquals( studentOverallAttendanceTest.adjustedPunctuality, studentOverallAttendance1.adjustedPunctuality, 5.5f)
    }
}
