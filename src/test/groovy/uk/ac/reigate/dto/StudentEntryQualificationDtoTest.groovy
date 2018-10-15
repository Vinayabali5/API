package uk.ac.reigate.dto;

import org.junit.Before
import org.junit.Test

import uk.ac.reigate.domain.academic.EntryQualification
import uk.ac.reigate.domain.academic.Student;
import uk.ac.reigate.domain.academic.StudentEntryQualification

import static org.junit.Assert.*


public class StudentEntryQualificationDtoTest {
    
    private StudentEntryQualification studentEntryQualification1
    
    private StudentEntryQualification studentEntryQualification2
    
    private List<StudentEntryQualification> studentEntryQualifications
    
    Student createStudent() {
        Student student = new Student()
        student.id = 1
        return student
    }
    
    EntryQualification createEntryQualification() {
        EntryQualification qualification = new EntryQualification()
    }
    
    @Before
    public void setup() {
        studentEntryQualification1 = new StudentEntryQualification(
                id: 1,
                student: createStudent(),
                qualification:createEntryQualification(),
                date: new Date().parse('yyyy/MM/dd', '2015/09/01'),
                grade: 'A',
                checked : true
                );
        studentEntryQualification2 = new StudentEntryQualification(
                id: 2,
                student: createStudent(),
                qualification:createEntryQualification(),
                date: new Date().parse('yyyy/MM/dd', '2015/09/01'),
                grade: 'A',
                checked : true
                );
        studentEntryQualifications = Arrays.asList(studentEntryQualification1, studentEntryQualification2);
    }
    
    @Test
    public void testMapFromStudentEntryQualificationEntity(){
        StudentEntryQualificationDto studentEntryQualificationTest = StudentEntryQualificationDto.mapFromStudentEntryQualificationEntity( studentEntryQualification1 )
        assertEquals( studentEntryQualificationTest.studentId, studentEntryQualification1.student.id );
        assertEquals( studentEntryQualificationTest.entryQualificationId, studentEntryQualification1.qualification.id);
        assertEquals( studentEntryQualificationTest.date, studentEntryQualification1.date);
        assertEquals( studentEntryQualificationTest.grade, studentEntryQualification1.grade)
        assertEquals( studentEntryQualificationTest.checked, studentEntryQualification1.checked)
    }
    
    @Test
    public void testMapFromStudentEntryQualificationsEntities(){
        List<StudentEntryQualificationDto> studentEntryQualificationsDtoTest = StudentEntryQualificationDto.mapFromStudentEntryQualificationsEntities( studentEntryQualifications )
        assertEquals( studentEntryQualificationsDtoTest[0].studentId, studentEntryQualification1.student.id );
        assertEquals( studentEntryQualificationsDtoTest[0].entryQualificationId, studentEntryQualification1.qualification.id);
        assertEquals( studentEntryQualificationsDtoTest[0].date, studentEntryQualification1.date);
        assertEquals( studentEntryQualificationsDtoTest[1].studentId, studentEntryQualification2.student.id );
        assertEquals( studentEntryQualificationsDtoTest[1].entryQualificationId, studentEntryQualification2.qualification.id);
        assertEquals( studentEntryQualificationsDtoTest[1].date, studentEntryQualification2.date);
    }
    
    
    @Test
    public void testEquals_Same() {
        StudentEntryQualificationDto studentEntryQualificationDto1 = new StudentEntryQualificationDto(studentEntryQualification1)
        StudentEntryQualificationDto studentEntryQualificationDto2 = new StudentEntryQualificationDto(studentEntryQualification1)
        assertEquals(studentEntryQualificationDto1, studentEntryQualificationDto2)
    }
    
    @Test
    public void testEquals_Different() {
        StudentEntryQualificationDto studentEntryQualificationDto1 = new StudentEntryQualificationDto(studentEntryQualification1)
        StudentEntryQualificationDto studentEntryQualificationDto2 = new StudentEntryQualificationDto(studentEntryQualification2)
        assertNotEquals(studentEntryQualificationDto1, studentEntryQualificationDto2)
    }
    
    @Test
    public void testHashCode_Same() {
        StudentEntryQualificationDto studentEntryQualificationDto1 = new StudentEntryQualificationDto(studentEntryQualification1)
        StudentEntryQualificationDto studentEntryQualificationDto2 = new StudentEntryQualificationDto(studentEntryQualification1)
        assertEquals(studentEntryQualificationDto1.hashCode(), studentEntryQualificationDto2.hashCode())
    }
    
    @Test
    public void testHashCode_Different() {
        StudentEntryQualificationDto studentEntryQualificationDto1 = new StudentEntryQualificationDto(studentEntryQualification1)
        StudentEntryQualificationDto studentEntryQualificationDto2 = new StudentEntryQualificationDto(studentEntryQualification2)
        assertNotEquals(studentEntryQualificationDto1.hashCode(), studentEntryQualificationDto2.hashCode())
    }
    
    @Test
    public void testConstructor_StudentEntryQualification() {
        StudentEntryQualificationDto studentEntryQualificationDto = new StudentEntryQualificationDto(studentEntryQualification1)
        assertEquals( studentEntryQualificationDto.entryQualificationId, studentEntryQualification1.qualification.id )
        assertEquals( studentEntryQualificationDto.date, studentEntryQualification1.date )
        assertEquals( studentEntryQualificationDto.grade, studentEntryQualification1.grade)
        assertEquals( studentEntryQualificationDto.checked, studentEntryQualification1.checked)
    }
}
