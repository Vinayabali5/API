package uk.ac.reigate.dto;

import static org.junit.Assert.*
import org.junit.Before
import org.junit.Test

import uk.ac.reigate.domain.academic.Student;
import uk.ac.reigate.domain.learning_support.ConcessionType
import uk.ac.reigate.domain.learning_support.StudentConcessionType


public class StudentConcessionTypeDtoTest {
    
    private StudentConcessionType studentConcessionType1
    
    private StudentConcessionType studentConcessionType2
    
    private List<StudentConcessionType> studentConcessionTypes
    
    Student createStudent() {
        Student student = new Student()
        student.id = 1
        return student
    }
    
    ConcessionType createConcessionType() {
        ConcessionType concessionType = new ConcessionType()
    }
    
    @Before
    public void setup() {
        studentConcessionType1 = new StudentConcessionType(
                student: createStudent(),
                concessionType:createConcessionType(),
                extraTimePercentage:2
                );
        studentConcessionType2 = new StudentConcessionType(
                student: createStudent(),
                concessionType:createConcessionType(),
                extraTimePercentage:'F'
                );
        studentConcessionTypes = Arrays.asList(studentConcessionType1, studentConcessionType2);
    }
    
    @Test
    public void testMapFromStudentConcessionTypeEntity(){
        StudentConcessionTypeDto studentConcessionTypeTest = StudentConcessionTypeDto.mapFromStudentConcessionTypeEntity( studentConcessionType1 )
        assertEquals( studentConcessionTypeTest.studentId, studentConcessionType1.student.id );
        assertEquals( studentConcessionTypeTest.concessionTypeId, studentConcessionType1.concessionType.id);
        assertEquals( studentConcessionTypeTest.extraTimePercentage, studentConcessionType1.extraTimePercentage);
    }
    
    @Test
    public void testMapFromStudentConcessionTypesEntities(){
        List<StudentConcessionTypeDto> studentConcessionTypesDtoTest = StudentConcessionTypeDto.mapFromStudentConcessionTypesEntities( studentConcessionTypes )
        assertEquals( studentConcessionTypesDtoTest[0].studentId, studentConcessionType1.student.id );
        assertEquals( studentConcessionTypesDtoTest[0].concessionTypeId, studentConcessionType1.concessionType.id);
        assertEquals( studentConcessionTypesDtoTest[0].extraTimePercentage, studentConcessionType1.extraTimePercentage);
        assertEquals( studentConcessionTypesDtoTest[1].studentId, studentConcessionType2.student.id );
        assertEquals( studentConcessionTypesDtoTest[1].concessionTypeId, studentConcessionType2.concessionType.id);
        assertEquals( studentConcessionTypesDtoTest[1].extraTimePercentage, studentConcessionType2.extraTimePercentage);
    }
    
    
    @Test
    public void testEquals_Same() {
        StudentConcessionTypeDto studentConcessionTypeDto1 = new StudentConcessionTypeDto(studentConcessionType1.student, studentConcessionType1.concessionType, studentConcessionType1.extraTimePercentage)
        StudentConcessionTypeDto studentConcessionTypeDto2 = new StudentConcessionTypeDto(studentConcessionType1.student, studentConcessionType1.concessionType, studentConcessionType1.extraTimePercentage)
        assertEquals(studentConcessionTypeDto1, studentConcessionTypeDto2)
    }
    
    @Test
    public void testEquals_Different() {
        StudentConcessionTypeDto studentConcessionTypeDto1 = new StudentConcessionTypeDto(studentConcessionType1.student, studentConcessionType1.concessionType, studentConcessionType1.extraTimePercentage)
        StudentConcessionTypeDto studentConcessionTypeDto2 = new StudentConcessionTypeDto(studentConcessionType2.student, studentConcessionType2.concessionType, studentConcessionType2.extraTimePercentage)
        assertNotEquals(studentConcessionTypeDto1, studentConcessionTypeDto2)
    }
    
    @Test
    public void testHashCode_Same() {
        StudentConcessionTypeDto studentConcessionTypeDto1 = new StudentConcessionTypeDto(studentConcessionType1.student, studentConcessionType1.concessionType, studentConcessionType1.extraTimePercentage)
        StudentConcessionTypeDto studentConcessionTypeDto2 = new StudentConcessionTypeDto(studentConcessionType1.student, studentConcessionType1.concessionType, studentConcessionType1.extraTimePercentage)
        assertEquals(studentConcessionTypeDto1.hashCode(), studentConcessionTypeDto2.hashCode())
    }
    
    @Test
    public void testHashCode_Different() {
        StudentConcessionTypeDto studentConcessionTypeDto1 = new StudentConcessionTypeDto(studentConcessionType1.student, studentConcessionType1.concessionType, studentConcessionType1.extraTimePercentage)
        StudentConcessionTypeDto studentConcessionTypeDto2 = new StudentConcessionTypeDto(studentConcessionType2.student, studentConcessionType2.concessionType, studentConcessionType2.extraTimePercentage)
        assertNotEquals(studentConcessionTypeDto1.hashCode(), studentConcessionTypeDto2.hashCode())
    }
    
    @Test
    public void testConstructor_StudentConcessionType() {
        StudentConcessionTypeDto studentConcessionTypeDto = new StudentConcessionTypeDto(studentConcessionType1)
        assertEquals( studentConcessionTypeDto.concessionTypeId, studentConcessionType1.concessionType.id )
        assertEquals( studentConcessionTypeDto.extraTimePercentage, studentConcessionType1.extraTimePercentage )
    }
}
