package uk.ac.reigate.dto;

import static org.junit.Assert.*
import org.junit.Before
import org.junit.Test

import uk.ac.reigate.domain.academic.Student;
import uk.ac.reigate.domain.learning_support.ReferralReason
import uk.ac.reigate.domain.learning_support.StudentReferralReason


public class StudentReferralReasonDtoTest {
    
    private StudentReferralReason studentReferralReason1
    
    private StudentReferralReason studentReferralReason2
    
    private List<StudentReferralReason> studentReferralReasons
    
    Student createStudent() {
        Student student = new Student()
        student.id = 1
        return student
    }
    
    ReferralReason createReferralReason() {
        ReferralReason referralReason = new ReferralReason()
    }
    
    @Before
    public void setup() {
        studentReferralReason1 = new StudentReferralReason(
                student: createStudent(),
                referralReason:createReferralReason(),
                primary:true
                );
        studentReferralReason2 = new StudentReferralReason(
                student: createStudent(),
                referralReason:createReferralReason(),
                primary:true
                );
        studentReferralReasons = Arrays.asList(studentReferralReason1, studentReferralReason2);
    }
    
    @Test
    public void testMapFromStudentReferralReasonEntity(){
        StudentReferralReasonDto studentReferralReasonTest = StudentReferralReasonDto.mapFromStudentReferralReasonEntity( studentReferralReason1 )
        assertEquals( studentReferralReasonTest.studentId, studentReferralReason1.student.id );
        assertEquals( studentReferralReasonTest.referralReasonId, studentReferralReason1.referralReason.id);
        assertEquals( studentReferralReasonTest.primary, studentReferralReason1.primary);
    }
    
    @Test
    public void testMapFromStudentReferralReasonsEntities(){
        List<StudentReferralReasonDto> studentReferralReasonsDtoTest = StudentReferralReasonDto.mapFromStudentReferralReasonsEntities( studentReferralReasons )
        assertEquals( studentReferralReasonsDtoTest[0].studentId, studentReferralReason1.student.id );
        assertEquals( studentReferralReasonsDtoTest[0].referralReasonId, studentReferralReason1.referralReason.id);
        assertEquals( studentReferralReasonsDtoTest[0].primary, studentReferralReason1.primary);
        assertEquals( studentReferralReasonsDtoTest[1].studentId, studentReferralReason2.student.id );
        assertEquals( studentReferralReasonsDtoTest[1].referralReasonId, studentReferralReason2.referralReason.id);
        assertEquals( studentReferralReasonsDtoTest[1].primary, studentReferralReason2.primary);
    }
    
    
    @Test
    public void testEquals_Same() {
        StudentReferralReasonDto studentReferralReasonDto1 = new StudentReferralReasonDto(studentReferralReason1.student, studentReferralReason1.referralReason, studentReferralReason1.primary)
        StudentReferralReasonDto studentReferralReasonDto2 = new StudentReferralReasonDto(studentReferralReason1.student, studentReferralReason1.referralReason, studentReferralReason1.primary)
        assertEquals(studentReferralReasonDto1, studentReferralReasonDto2)
    }
    
    
    @Test
    public void testHashCode_Same() {
        StudentReferralReasonDto studentReferralReasonDto1 = new StudentReferralReasonDto(studentReferralReason1.student, studentReferralReason1.referralReason, studentReferralReason1.primary)
        StudentReferralReasonDto studentReferralReasonDto2 = new StudentReferralReasonDto(studentReferralReason1.student, studentReferralReason1.referralReason, studentReferralReason1.primary)
        assertEquals(studentReferralReasonDto1.hashCode(), studentReferralReasonDto2.hashCode())
    }
    
    
    @Test
    public void testConstructor_StudentReferralReason() {
        StudentReferralReasonDto studentReferralReasonDto = new StudentReferralReasonDto(studentReferralReason1)
        assertEquals( studentReferralReasonDto.referralReasonId, studentReferralReason1.referralReason.id )
        assertEquals( studentReferralReasonDto.primary, studentReferralReason1.primary )
    }
}
