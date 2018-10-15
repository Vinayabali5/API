package uk.ac.reigate.dto;

import static org.junit.Assert.*
import org.junit.Before
import org.junit.Test

import uk.ac.reigate.domain.academic.Course
import uk.ac.reigate.domain.academic.InterimReport
import uk.ac.reigate.domain.academic.Student;
import uk.ac.reigate.domain.academic.StudentInterimReport
import uk.ac.reigate.domain.lookup.PossibleGrade



public class StudentInterimReportDtoTest {
    
    private StudentInterimReport studentInterimReport1
    
    private StudentInterimReport studentInterimReport2
    
    private List<StudentInterimReport> studentInterimReports
    
    Student createStudent() {
        Student student = new Student()
    }
    
    InterimReport createInterimReport() {
        InterimReport interimReport = new InterimReport()
    }
    
    Course createCourse() {
        Course course = new Course()
    }
    
    PossibleGrade createCurrentPredictedGrade() {
        PossibleGrade currentPredictedGrade = new PossibleGrade()
    }
    
    PossibleGrade createKeyAssessment1() {
        PossibleGrade keyAssessment1 = new PossibleGrade()
    }
    
    PossibleGrade createKeyAssessment2() {
        PossibleGrade keyAssessment2 = new PossibleGrade()
    }
    
    @Before
    public void setup() {
        studentInterimReport1 = new StudentInterimReport(
                student: createStudent(),
                interimReport: createInterimReport(),
                course : createCourse(),
                motivation:3,
                classEthic: 2,
                timeManagement: 4,
                totalPossible :2,
                absence : 2,
                authorisedAbsence : 3,
                late : 2,
                currentPredictedGrade: createCurrentPredictedGrade(),
                keyAssessment1 :createKeyAssessment1(),
                keyAssessment2 :createKeyAssessment2()
                );
        studentInterimReport2 = new StudentInterimReport(
                student: createStudent(),
                interimReport: createInterimReport(),
                course : createCourse(),
                motivation:4,
                classEthic: 1,
                timeManagement: 3,
                totalPossible :1,
                absence : 1,
                authorisedAbsence : 2,
                late : 3,
                currentPredictedGrade: createCurrentPredictedGrade(),
                keyAssessment1 :createKeyAssessment1(),
                keyAssessment2 :createKeyAssessment2()
                );
        studentInterimReports = Arrays.asList(studentInterimReport1, studentInterimReport2);
    }
    
    @Test
    public void testMapFromStudentInterimReportEntity(){
        StudentInterimReportDto studentInterimReportTest = StudentInterimReportDto.mapFromStudentInterimReportEntity( studentInterimReport1 )
        assertEquals( studentInterimReportTest.studentId, studentInterimReport1.student.id );
        assertEquals( studentInterimReportTest.motivation, studentInterimReport1.motivation);
        assertEquals( studentInterimReportTest.classEthic, studentInterimReport1.classEthic);
        assertEquals( studentInterimReportTest.timeManagement, studentInterimReport1.timeManagement);
        assertEquals( studentInterimReportTest.absence, studentInterimReport1.absence);
        assertEquals( studentInterimReportTest.authorisedAbsence, studentInterimReport1.authorisedAbsence);
        assertEquals( studentInterimReportTest.late, studentInterimReport1.late);
        assertEquals( studentInterimReportTest.currentPredictedGradeId, studentInterimReport1.currentPredictedGrade.id);
        assertEquals( studentInterimReportTest.keyAssessment1Id, studentInterimReport1.keyAssessment1.id);
        assertEquals( studentInterimReportTest.keyAssessment2Id, studentInterimReport1.keyAssessment2.id);
    }
    
    @Test
    public void testMapFromStudentInterimReportsEntities(){
        List<StudentInterimReportDto> studentInterimReportsDtoTest = StudentInterimReportDto.mapFromStudentInterimReportEntities( studentInterimReports )
        assertEquals( studentInterimReportsDtoTest[0].studentId, studentInterimReport1.student.id );
        assertEquals( studentInterimReportsDtoTest[0].motivation, studentInterimReport1.motivation);
        assertEquals( studentInterimReportsDtoTest[0].classEthic, studentInterimReport1.classEthic);
        assertEquals( studentInterimReportsDtoTest[0].timeManagement, studentInterimReport1.timeManagement);
        assertEquals( studentInterimReportsDtoTest[0].absence, studentInterimReport1.absence);
        assertEquals( studentInterimReportsDtoTest[0].authorisedAbsence, studentInterimReport1.authorisedAbsence);
        assertEquals( studentInterimReportsDtoTest[0].late, studentInterimReport1.late);
        assertEquals( studentInterimReportsDtoTest[0].currentPredictedGradeId, studentInterimReport1.currentPredictedGrade.id);
        assertEquals( studentInterimReportsDtoTest[0].keyAssessment1Id, studentInterimReport1.keyAssessment1.id);
        assertEquals( studentInterimReportsDtoTest[0].keyAssessment2Id, studentInterimReport1.keyAssessment2.id);
        assertEquals( studentInterimReportsDtoTest[1].studentId, studentInterimReport2.student.id );
        assertEquals( studentInterimReportsDtoTest[1].motivation, studentInterimReport2.motivation);
        assertEquals( studentInterimReportsDtoTest[1].classEthic, studentInterimReport2.classEthic);
        assertEquals( studentInterimReportsDtoTest[1].timeManagement, studentInterimReport2.timeManagement);
        assertEquals( studentInterimReportsDtoTest[1].absence, studentInterimReport2.absence);
        assertEquals( studentInterimReportsDtoTest[1].authorisedAbsence, studentInterimReport2.authorisedAbsence);
        assertEquals( studentInterimReportsDtoTest[1].late, studentInterimReport2.late);
        assertEquals( studentInterimReportsDtoTest[1].currentPredictedGradeId, studentInterimReport2.currentPredictedGrade.id);
        assertEquals( studentInterimReportsDtoTest[1].keyAssessment1Id, studentInterimReport2.keyAssessment1.id);
        assertEquals( studentInterimReportsDtoTest[1].keyAssessment2Id, studentInterimReport2.keyAssessment2.id);
    }
    
    
    @Test
    public void testEquals_Different() {
        StudentInterimReportDto studentInterimReportDto1 = new StudentInterimReportDto(studentInterimReport1)
        StudentInterimReportDto studentInterimReportDto2 = new StudentInterimReportDto(studentInterimReport2)
        assertNotEquals(studentInterimReportDto1, studentInterimReportDto2)
    }
    
    
    @Test
    public void testHashCode_Different() {
        StudentInterimReportDto studentInterimReportDto1 = new StudentInterimReportDto(studentInterimReport1)
        StudentInterimReportDto studentInterimReportDto2 = new StudentInterimReportDto(studentInterimReport2)
        assertNotEquals(studentInterimReportDto1.hashCode(), studentInterimReportDto2.hashCode())
    }
    
    @Test
    public void testConstructor_StudentInterimReport() {
        StudentInterimReportDto studentInterimReportDto = new StudentInterimReportDto(studentInterimReport1)
        assertEquals( studentInterimReportDto.motivation, studentInterimReport1.motivation )
        assertEquals( studentInterimReportDto.classEthic, studentInterimReport1.classEthic )
        assertEquals( studentInterimReportDto.timeManagement, studentInterimReport1.timeManagement);
        assertEquals( studentInterimReportDto.absence, studentInterimReport1.absence);
        assertEquals( studentInterimReportDto.authorisedAbsence, studentInterimReport1.authorisedAbsence);
        assertEquals( studentInterimReportDto.late, studentInterimReport1.late);
        assertEquals( studentInterimReportDto.currentPredictedGradeId, studentInterimReport1.currentPredictedGrade.id);
        assertEquals( studentInterimReportDto.keyAssessment1Id, studentInterimReport1.keyAssessment1.id);
        assertEquals( studentInterimReportDto.keyAssessment2Id, studentInterimReport1.keyAssessment2.id);
    }
}
