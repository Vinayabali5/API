package uk.ac.reigate.dto;

import static org.junit.Assert.*
import org.junit.Before
import org.junit.Test
import uk.ac.reigate.domain.academic.ExternalResultsArchive
import uk.ac.reigate.domain.academic.Student;



public class ExternalResultsArchiveDtoTest {
    
    private ExternalResultsArchive externalResultsArchive1
    
    private ExternalResultsArchive externalResultsArchive2
    
    private List<ExternalResultsArchive> externalResultsArchives
    
    Student createStudent() {
        Student student = new Student()
        student.id = 1
        return student
    }
    
    @Before
    public void setup() {
        externalResultsArchive1 = new ExternalResultsArchive(
                id: 1,
                student: createStudent(),
                courseSpec:'M',
                levelDescription:'MAth',
                subjectDescription:'maths',
                syllabus: 'sci',
                grade: 'A',
                mark: '1',
                maxMark : '100',
                dateAchieved : new Date().parse('yyyy/MM/dd', '2015/09/01'),
                series : 'A',
                year : '2016',
                examType : 'Btec'
                );
        externalResultsArchive2 = new ExternalResultsArchive(
                id: 2,
                student: createStudent(),
                courseSpec:'M',
                levelDescription:'MAth',
                subjectDescription:'maths',
                syllabus: 'sci',
                grade: 'A',
                mark: '1',
                maxMark : '100',
                dateAchieved : new Date().parse('yyyy/MM/dd', '2015/09/01'),
                series : 'A',
                year : '2016',
                examType : 'Btec'
                );
        externalResultsArchives = Arrays.asList(externalResultsArchive1, externalResultsArchive2);
    }
    
    @Test
    public void testMapFromExternalResultsArchiveEntity(){
        ExternalResultsArchiveDto externalResultsArchiveTest = ExternalResultsArchiveDto.mapFromExternalResultsArchiveEntity( externalResultsArchive1 )
        assertEquals( externalResultsArchiveTest.id, externalResultsArchive1.id );
        assertEquals( externalResultsArchiveTest.courseSpec, externalResultsArchive1.courseSpec);
        assertEquals( externalResultsArchiveTest.levelDescription, externalResultsArchive1.levelDescription);
        assertEquals( externalResultsArchiveTest.subjectDescription, externalResultsArchive1.subjectDescription)
        assertEquals( externalResultsArchiveTest.syllabus, externalResultsArchive1.syllabus)
        assertEquals( externalResultsArchiveTest.grade, externalResultsArchive1.grade)
        assertEquals( externalResultsArchiveTest.mark, externalResultsArchive1.mark)
        assertEquals( externalResultsArchiveTest.maxMark, externalResultsArchive1.maxMark)
        assertEquals( externalResultsArchiveTest.dateAchieved, externalResultsArchive1.dateAchieved)
        assertEquals( externalResultsArchiveTest.series, externalResultsArchive1.series)
        assertEquals( externalResultsArchiveTest.year, externalResultsArchive1.year)
        assertEquals( externalResultsArchiveTest.examType, externalResultsArchive1.examType)
    }
    
    @Test
    public void testMapFromExternalResultsArchivesEntities(){
        List<ExternalResultsArchiveDto> externalResultsArchivesDtoTest = ExternalResultsArchiveDto.mapFromExternalResultsArchivesEntities( externalResultsArchives )
        assertEquals( externalResultsArchivesDtoTest[0].id, externalResultsArchive1.id );
        assertEquals( externalResultsArchivesDtoTest[0].courseSpec, externalResultsArchive1.courseSpec);
        assertEquals( externalResultsArchivesDtoTest[0].levelDescription, externalResultsArchive1.levelDescription);
        assertEquals( externalResultsArchivesDtoTest[0].subjectDescription, externalResultsArchive1.subjectDescription);
        assertEquals( externalResultsArchivesDtoTest[0].syllabus, externalResultsArchive1.syllabus);
        assertEquals( externalResultsArchivesDtoTest[0].grade, externalResultsArchive1.grade);
        assertEquals( externalResultsArchivesDtoTest[0].mark, externalResultsArchive1.mark);
        assertEquals( externalResultsArchivesDtoTest[0].maxMark, externalResultsArchive1.maxMark);
        assertEquals( externalResultsArchivesDtoTest[0].dateAchieved, externalResultsArchive1.dateAchieved);
        assertEquals( externalResultsArchivesDtoTest[0].series, externalResultsArchive1.series);
        assertEquals( externalResultsArchivesDtoTest[0].year, externalResultsArchive1.year);
        assertEquals( externalResultsArchivesDtoTest[0].examType, externalResultsArchive1.examType);
        assertEquals( externalResultsArchivesDtoTest[1].id, externalResultsArchive2.id );
        assertEquals( externalResultsArchivesDtoTest[1].courseSpec, externalResultsArchive2.courseSpec);
        assertEquals( externalResultsArchivesDtoTest[1].levelDescription, externalResultsArchive2.levelDescription);
        assertEquals( externalResultsArchivesDtoTest[1].subjectDescription, externalResultsArchive2.subjectDescription);
        assertEquals( externalResultsArchivesDtoTest[1].syllabus, externalResultsArchive2.syllabus);
        assertEquals( externalResultsArchivesDtoTest[1].grade, externalResultsArchive2.grade);
        assertEquals( externalResultsArchivesDtoTest[1].mark, externalResultsArchive2.mark);
        assertEquals( externalResultsArchivesDtoTest[1].maxMark, externalResultsArchive2.maxMark);
        assertEquals( externalResultsArchivesDtoTest[1].dateAchieved, externalResultsArchive2.dateAchieved);
        assertEquals( externalResultsArchivesDtoTest[1].series, externalResultsArchive2.series);
        assertEquals( externalResultsArchivesDtoTest[1].year, externalResultsArchive2.year);
        assertEquals( externalResultsArchivesDtoTest[1].examType, externalResultsArchive2.examType);
    }
    
    @Test
    public void testEquals_Same() {
        ExternalResultsArchiveDto externalResultsArchiveDto1 = new ExternalResultsArchiveDto(externalResultsArchive1.id, externalResultsArchive1.student, externalResultsArchive1.courseSpec, externalResultsArchive1.levelDescription, externalResultsArchive1.subjectDescription, externalResultsArchive1.syllabus, externalResultsArchive1.grade, externalResultsArchive1.mark, externalResultsArchive1.maxMark, externalResultsArchive1.dateAchieved, externalResultsArchive1.series, externalResultsArchive1.year, externalResultsArchive1.examType)
        ExternalResultsArchiveDto externalResultsArchiveDto2 = new ExternalResultsArchiveDto(externalResultsArchive1.id, externalResultsArchive1.student, externalResultsArchive1.courseSpec, externalResultsArchive1.levelDescription, externalResultsArchive1.subjectDescription, externalResultsArchive1.syllabus, externalResultsArchive1.grade, externalResultsArchive1.mark, externalResultsArchive1.maxMark, externalResultsArchive1.dateAchieved, externalResultsArchive1.series, externalResultsArchive1.year, externalResultsArchive1.examType)
        assertEquals(externalResultsArchiveDto1, externalResultsArchiveDto2)
    }
    
    @Test
    public void testEquals_Different() {
        ExternalResultsArchiveDto externalResultsArchiveDto1 = new ExternalResultsArchiveDto(externalResultsArchive1.id, externalResultsArchive1.student, externalResultsArchive1.courseSpec, externalResultsArchive1.levelDescription, externalResultsArchive1.subjectDescription, externalResultsArchive1.syllabus, externalResultsArchive1.grade, externalResultsArchive1.mark, externalResultsArchive1.maxMark, externalResultsArchive1.dateAchieved, externalResultsArchive1.series, externalResultsArchive1.year, externalResultsArchive1.examType)
        ExternalResultsArchiveDto externalResultsArchiveDto2 = new ExternalResultsArchiveDto(externalResultsArchive2.id, externalResultsArchive2.student, externalResultsArchive2.courseSpec, externalResultsArchive2.levelDescription, externalResultsArchive2.subjectDescription, externalResultsArchive2.syllabus, externalResultsArchive2.grade, externalResultsArchive2.mark, externalResultsArchive2.maxMark, externalResultsArchive2.dateAchieved, externalResultsArchive2.series, externalResultsArchive2.year, externalResultsArchive2.examType)
        assertNotEquals(externalResultsArchiveDto1, externalResultsArchiveDto2)
    }
    
    @Test
    public void testHashCode_Same() {
        ExternalResultsArchiveDto externalResultsArchiveDto1 = new ExternalResultsArchiveDto(externalResultsArchive1.id, externalResultsArchive1.student, externalResultsArchive1.courseSpec, externalResultsArchive1.levelDescription, externalResultsArchive1.subjectDescription, externalResultsArchive1.syllabus, externalResultsArchive1.grade, externalResultsArchive1.mark, externalResultsArchive1.maxMark, externalResultsArchive1.dateAchieved, externalResultsArchive1.series, externalResultsArchive1.year, externalResultsArchive1.examType)
        ExternalResultsArchiveDto externalResultsArchiveDto2 = new ExternalResultsArchiveDto(externalResultsArchive1.id, externalResultsArchive1.student, externalResultsArchive1.courseSpec, externalResultsArchive1.levelDescription, externalResultsArchive1.subjectDescription, externalResultsArchive1.syllabus, externalResultsArchive1.grade, externalResultsArchive1.mark, externalResultsArchive1.maxMark, externalResultsArchive1.dateAchieved, externalResultsArchive1.series, externalResultsArchive1.year, externalResultsArchive1.examType)
        assertEquals(externalResultsArchiveDto1.hashCode(), externalResultsArchiveDto2.hashCode())
    }
    
    @Test
    public void testHashCode_Different() {
        ExternalResultsArchiveDto externalResultsArchiveDto1 = new ExternalResultsArchiveDto(externalResultsArchive1.id, externalResultsArchive1.student, externalResultsArchive1.courseSpec, externalResultsArchive1.levelDescription, externalResultsArchive1.subjectDescription, externalResultsArchive1.syllabus, externalResultsArchive1.grade, externalResultsArchive1.mark, externalResultsArchive1.maxMark, externalResultsArchive1.dateAchieved, externalResultsArchive1.series, externalResultsArchive1.year, externalResultsArchive1.examType)
        ExternalResultsArchiveDto externalResultsArchiveDto2 = new ExternalResultsArchiveDto(externalResultsArchive2.id, externalResultsArchive2.student, externalResultsArchive2.courseSpec, externalResultsArchive2.levelDescription, externalResultsArchive2.subjectDescription, externalResultsArchive2.syllabus, externalResultsArchive2.grade, externalResultsArchive2.mark, externalResultsArchive2.maxMark, externalResultsArchive2.dateAchieved, externalResultsArchive2.series, externalResultsArchive2.year, externalResultsArchive2.examType)
        assertNotEquals(externalResultsArchiveDto1.hashCode(), externalResultsArchiveDto2.hashCode())
    }
    
    @Test
    public void testConstructor_ExternalResultsArchive() {
        ExternalResultsArchiveDto externalResultsArchiveTest= new ExternalResultsArchiveDto(externalResultsArchive1)
        assertEquals( externalResultsArchiveTest.courseSpec, externalResultsArchive1.courseSpec);
        assertEquals( externalResultsArchiveTest.levelDescription, externalResultsArchive1.levelDescription);
        assertEquals( externalResultsArchiveTest.subjectDescription, externalResultsArchive1.subjectDescription)
        assertEquals( externalResultsArchiveTest.syllabus, externalResultsArchive1.syllabus)
        assertEquals( externalResultsArchiveTest.grade, externalResultsArchive1.grade)
        assertEquals( externalResultsArchiveTest.mark, externalResultsArchive1.mark)
        assertEquals( externalResultsArchiveTest.maxMark, externalResultsArchive1.maxMark)
        assertEquals( externalResultsArchiveTest.dateAchieved, externalResultsArchive1.dateAchieved)
        assertEquals( externalResultsArchiveTest.series, externalResultsArchive1.series)
        assertEquals( externalResultsArchiveTest.year, externalResultsArchive1.year)
        assertEquals( externalResultsArchiveTest.examType, externalResultsArchive1.examType)
    }
}
