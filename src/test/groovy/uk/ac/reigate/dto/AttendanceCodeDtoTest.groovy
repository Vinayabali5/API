package uk.ac.reigate.dto;
import org.junit.Before
import org.junit.Test
import static org.junit.Assert.*
import uk.ac.reigate.domain.register.AttendanceCode
import uk.ac.reigate.dto.AttendanceCodeDto
import static org.junit.Assert.assertEquals;


public class AttendanceCodeDtoTest {
    
    private AttendanceCode attendanceCode1
    
    private AttendanceCode attendanceCode2
    
    private List<AttendanceCode> attendanceCodes
    
    @Before
    public void setup() {
        this.attendanceCode1 = new AttendanceCode(
                id: 1,
                code: 'X',
                description: 'Absent',
                registerMark: 'X',
                absence: true,
                authorisedAbsence: false,
                late: false,
                included: true,
                authorisedLate: false,
                lastDateOfAttendance: false,
                htmlColour: "red",
                accessColour: "r"
                );
        this.attendanceCode2 = new AttendanceCode(
                id: 2,
                code: 'V',
                description: 'Authorised Absence',
                registerMark: 'B',
                absence: true,
                authorisedAbsence: true,
                late: false,
                included: true,
                authorisedLate: false,
                lastDateOfAttendance: false,
                htmlColour: "red",
                accessColour: "r"
                );
        this.attendanceCodes = Arrays.asList(this.attendanceCode1, this.attendanceCode2);
    }
    
    @Test
    void testConstructor_academicYear() {
        AttendanceCodeDto attendanceCodeTest = new AttendanceCodeDto( attendanceCode1 )
        assertEquals( attendanceCodeTest.id, attendanceCode1.id );
        assertEquals( attendanceCodeTest.code, attendanceCode1.code);
        assertEquals( attendanceCodeTest.description, attendanceCode1.description);
        assertEquals( attendanceCodeTest.absence, attendanceCode1.absence);
        assertEquals( attendanceCodeTest.authorisedAbsence, attendanceCode1.authorisedAbsence);
        assertEquals( attendanceCodeTest.late, attendanceCode1.late);
        assertEquals( attendanceCodeTest.authorisedLate, attendanceCode1.authorisedLate);
    }
    
    @Test
    public void testMapFromAttendanceCodeEntity(){
        AttendanceCodeDto attendanceCodeTest = AttendanceCodeDto.mapFromAttendanceCodeEntity( attendanceCode1 )
        assertEquals( attendanceCodeTest.id, attendanceCode1.id );
        assertEquals( attendanceCodeTest.code, attendanceCode1.code);
        assertEquals( attendanceCodeTest.description, attendanceCode1.description);
        assertEquals( attendanceCodeTest.absence, attendanceCode1.absence);
        assertEquals( attendanceCodeTest.authorisedAbsence, attendanceCode1.authorisedAbsence);
        assertEquals( attendanceCodeTest.late, attendanceCode1.late);
        assertEquals( attendanceCodeTest.authorisedLate, attendanceCode1.authorisedLate);
    }
    
    @Test
    public void testMapFromAttendanceCodesEntities(){
        List<AttendanceCodeDto> attendanceCodesTest = AttendanceCodeDto.mapFromAttendanceCodesEntities( this.attendanceCodes )
        assertEquals( attendanceCodesTest[0].id, attendanceCode1.id );
        assertEquals( attendanceCodesTest[0].code, attendanceCode1.code);
        assertEquals( attendanceCodesTest[0].description, attendanceCode1.description);
        assertEquals( attendanceCodesTest[0].absence, attendanceCode1.absence);
        assertEquals( attendanceCodesTest[0].authorisedAbsence, attendanceCode1.authorisedAbsence);
        assertEquals( attendanceCodesTest[0].late, attendanceCode1.late);
        assertEquals( attendanceCodesTest[0].authorisedLate,attendanceCode1.authorisedLate);
        assertEquals( attendanceCodesTest[1].id, attendanceCode2.id );
        assertEquals( attendanceCodesTest[1].code, attendanceCode2.code);
        assertEquals( attendanceCodesTest[1].description, attendanceCode2.description);
        assertEquals( attendanceCodesTest[1].absence, attendanceCode2.absence);
        assertEquals( attendanceCodesTest[1].authorisedAbsence, attendanceCode2.authorisedAbsence);
        assertEquals( attendanceCodesTest[1].late, attendanceCode2.late);
        assertEquals( attendanceCodesTest[1].authorisedLate, attendanceCode2.authorisedLate);
    }
    
    @Test
    public void testEquals_Same() {
        AttendanceCodeDto attendanceCodeDto1 = new AttendanceCodeDto(attendanceCode1.id, attendanceCode1.code, attendanceCode1.description, attendanceCode1.registerMark, attendanceCode1.absence, attendanceCode1.authorisedAbsence, attendanceCode1.late, attendanceCode1.authorisedLate, attendanceCode1.included, attendanceCode1.lastDateOfAttendance, attendanceCode1.htmlColour, attendanceCode1.accessColour)
        AttendanceCodeDto attendanceCodeDto2 = new AttendanceCodeDto(attendanceCode1.id, attendanceCode1.code, attendanceCode1.description, attendanceCode1.registerMark, attendanceCode1.absence, attendanceCode1.authorisedAbsence, attendanceCode1.late, attendanceCode1.authorisedLate, attendanceCode1.included, attendanceCode1.lastDateOfAttendance, attendanceCode1.htmlColour, attendanceCode1.accessColour)
        assertEquals( attendanceCodeDto1, attendanceCodeDto2)
    }
    
    @Test
    public void testEquals_Different() {
        AttendanceCodeDto attendanceCodeDto1 = new AttendanceCodeDto(attendanceCode1.id, attendanceCode1.code, attendanceCode1.description, attendanceCode1.registerMark, attendanceCode1.absence, attendanceCode1.authorisedAbsence, attendanceCode1.late, attendanceCode1.authorisedLate, attendanceCode1.included, attendanceCode1.lastDateOfAttendance, attendanceCode1.htmlColour, attendanceCode1.accessColour)
        AttendanceCodeDto attendanceCodeDto2 = new AttendanceCodeDto(attendanceCode2.id, attendanceCode2.code, attendanceCode2.description, attendanceCode2.registerMark, attendanceCode2.absence, attendanceCode2.authorisedAbsence, attendanceCode2.late, attendanceCode2.authorisedLate, attendanceCode2.included, attendanceCode2.lastDateOfAttendance, attendanceCode2.htmlColour, attendanceCode2.accessColour)
        assertNotEquals( attendanceCodeDto1, attendanceCodeDto2)
    }
    
    @Test
    public void testHashCode_Same() {
        AttendanceCodeDto attendanceCodeDto1 = new AttendanceCodeDto(attendanceCode1.id, attendanceCode1.code, attendanceCode1.description, attendanceCode1.registerMark, attendanceCode1.absence, attendanceCode1.authorisedAbsence, attendanceCode1.late, attendanceCode1.authorisedLate, attendanceCode1.included, attendanceCode1.lastDateOfAttendance, attendanceCode1.htmlColour, attendanceCode1.accessColour)
        AttendanceCodeDto attendanceCodeDto2 = new AttendanceCodeDto(attendanceCode1.id, attendanceCode1.code, attendanceCode1.description, attendanceCode1.registerMark, attendanceCode1.absence, attendanceCode1.authorisedAbsence, attendanceCode1.late, attendanceCode1.authorisedLate, attendanceCode1.included, attendanceCode1.lastDateOfAttendance, attendanceCode1.htmlColour, attendanceCode1.accessColour)
        assertEquals( attendanceCodeDto1.hashCode(), attendanceCodeDto2.hashCode())
    }
    
    @Test
    public void testHashCode_Different() {
        AttendanceCodeDto attendanceCodeDto1 = new AttendanceCodeDto(attendanceCode1.id, attendanceCode1.code, attendanceCode1.description, attendanceCode1.registerMark, attendanceCode1.absence, attendanceCode1.authorisedAbsence, attendanceCode1.late, attendanceCode1.authorisedLate, attendanceCode1.included, attendanceCode1.lastDateOfAttendance, attendanceCode1.htmlColour, attendanceCode1.accessColour)
        AttendanceCodeDto attendanceCodeDto2 = new AttendanceCodeDto(attendanceCode2.id, attendanceCode2.code, attendanceCode2.description, attendanceCode2.registerMark, attendanceCode2.absence, attendanceCode2.authorisedAbsence, attendanceCode2.late, attendanceCode2.authorisedLate, attendanceCode2.included, attendanceCode2.lastDateOfAttendance, attendanceCode2.htmlColour, attendanceCode2.accessColour)
        assertNotEquals( attendanceCodeDto1.hashCode(), attendanceCodeDto2.hashCode())
    }
    
    @Test
    public void testMapToAttendanceCodeEntity(){
        AttendanceCodeDto attendanceCodeDto = new AttendanceCodeDto(attendanceCode1.id, attendanceCode1.code, attendanceCode1.description, attendanceCode1.registerMark, attendanceCode1.absence, attendanceCode1.authorisedAbsence, attendanceCode1.late, attendanceCode1.authorisedLate, attendanceCode1.included, attendanceCode1.lastDateOfAttendance, attendanceCode1.htmlColour, attendanceCode1.accessColour);
        AttendanceCode attendanceCode = AttendanceCodeDto.mapToAttendanceCodeEntity( attendanceCodeDto );
        assertEquals( attendanceCode.id, attendanceCode1.id );
        assertEquals( attendanceCode.code, attendanceCode1.code);
        assertEquals( attendanceCode.description, attendanceCode1.description);
        assertEquals( attendanceCode.registerMark, attendanceCode1.registerMark);
        assertEquals( attendanceCode.absence, attendanceCode1.absence);
        assertEquals( attendanceCode.authorisedAbsence, attendanceCode1.authorisedAbsence);
        assertEquals( attendanceCode.late, attendanceCode1.late);
        assertEquals( attendanceCode.included, attendanceCode1.included);
        assertEquals( attendanceCode.lastDateOfAttendance, attendanceCode1.lastDateOfAttendance);
        assertEquals( attendanceCode.htmlColour, attendanceCode1.htmlColour);
        assertEquals( attendanceCode.accessColour, attendanceCode1.accessColour);
    }
    
    @Test
    public void testConstructor_AttendanceCode() {
        AttendanceCodeDto attendanceCodeDto = new AttendanceCodeDto(attendanceCode1);
        assertEquals( attendanceCodeDto.id, attendanceCode1.id );
        assertEquals( attendanceCodeDto.code, attendanceCode1.code);
        assertEquals( attendanceCodeDto.description, attendanceCode1.description);
        assertEquals( attendanceCodeDto.registerMark, attendanceCode1.registerMark);
        assertEquals( attendanceCodeDto.absence, attendanceCode1.absence);
        assertEquals( attendanceCodeDto.authorisedAbsence, attendanceCode1.authorisedAbsence);
        assertEquals( attendanceCodeDto.late, attendanceCode1.late);
        assertEquals( attendanceCodeDto.included, attendanceCode1.included);
        assertEquals( attendanceCodeDto.lastDateOfAttendance, attendanceCode1.lastDateOfAttendance);
        assertEquals( attendanceCodeDto.htmlColour, attendanceCode1.htmlColour);
        assertEquals( attendanceCodeDto.accessColour, attendanceCode1.accessColour);
    }
}
