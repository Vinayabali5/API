package uk.ac.reigate.dto;

import static org.junit.Assert.*

import org.junit.Test
import org.junit.Before
import uk.ac.reigate.domain.lookup.Level
import uk.ac.reigate.domain.lookup.PossibleGrade
import uk.ac.reigate.domain.lookup.PossibleGradeSet



public class PossibleGradeDtoTest {
    
    private Level level
    
    private PossibleGradeSet gradeSet
    
    private PossibleGrade possibleGrade1
    
    private PossibleGrade possibleGrade2
    
    private List<PossibleGrade> possibleGrades
    
    @Before
    public void setupTests() {
        this.level  = new Level(1, 'H', 'AS Level', gradeSet, 1, 'A')
        this.possibleGrade1 = new PossibleGrade(
                id: 1,
                code:'M',
                description:'Male',
                level: level,
                grade: 'A',
                ucasPoints: 10,
                useForKeyAssessment: true
                );
        this.possibleGrade2 = new PossibleGrade(
                id: 2,
                code:'F',
                description:'Female',
                level: level,
                grade: 'B',
                ucasPoints: 11,
                useForKeyAssessment: true
                );
        this.possibleGrades = Arrays.asList(possibleGrade1, possibleGrade2);
    }
    
    PossibleGradeDto generatePossibleGradeDto() {
        return generatePossibleGrade1Dto()
    }
    
    PossibleGradeDto generatePossibleGrade1Dto() {
        return new PossibleGradeDto(possibleGrade1.id, null, possibleGrade1.code, possibleGrade1.description, possibleGrade1.level.id, possibleGrade1.grade, possibleGrade1.ucasPoints, possibleGrade1.useForKeyAssessment)
    }
    
    PossibleGradeDto generatePossibleGrade2Dto() {
        return new PossibleGradeDto(possibleGrade2.id, null, possibleGrade2.code, possibleGrade2.description, possibleGrade2.level.id, possibleGrade2.grade, possibleGrade2.ucasPoints, possibleGrade2.useForKeyAssessment)
    }
    
    @Test
    public void testMapFromPossibleGradeEntityTest(){
        PossibleGradeDto possibleGradeTest = PossibleGradeDto.mapFromPossibleGradeEntity( possibleGrade1 )
        assertEquals( possibleGradeTest.id, possibleGrade1.id );
        assertEquals( possibleGradeTest.code, possibleGrade1.code);
        assertEquals( possibleGradeTest.description, possibleGrade1.description);
        assertEquals( possibleGradeTest.levelId, possibleGrade1.level.id);
        assertEquals( possibleGradeTest.grade, possibleGrade1.grade);
        assertEquals( possibleGradeTest.ucasPoints, possibleGrade1.ucasPoints);
    }
    
    @Test
    public void testMapFromPossibleGradesEntitiesTest(){
        List<PossibleGradeDto> possibleGradeTest = PossibleGradeDto.mapFromPossibleGradesEntities( possibleGrades )
        assertEquals( possibleGradeTest[0].id, possibleGrade1.id );
        assertEquals( possibleGradeTest[0].code, possibleGrade1.code);
        assertEquals( possibleGradeTest[0].description, possibleGrade1.description);
        assertEquals( possibleGradeTest[0].levelId, possibleGrade1.level.id);
        assertEquals( possibleGradeTest[0].grade, possibleGrade1.grade);
        assertEquals( possibleGradeTest[0].ucasPoints, possibleGrade1.ucasPoints);
        assertEquals( possibleGradeTest[1].id, possibleGrade2.id );
        assertEquals( possibleGradeTest[1].code, possibleGrade2.code);
        assertEquals( possibleGradeTest[1].description, possibleGrade2.description);
        assertEquals( possibleGradeTest[1].levelId, possibleGrade2.level.id);
        assertEquals( possibleGradeTest[1].grade, possibleGrade2.grade);
        assertEquals( possibleGradeTest[1].ucasPoints, possibleGrade2.ucasPoints);
    }
    
    @Test
    public void testMapToPossibleGradeEntityTest(){
        PossibleGradeDto possibleGradeDto = generatePossibleGradeDto()
        PossibleGrade possibleGradeTest = PossibleGradeDto.mapToPossibleGradeEntity( possibleGradeDto, gradeSet, level );
        assertEquals( possibleGradeTest.id, possibleGrade1.id );
        assertEquals( possibleGradeTest.code, possibleGrade1.code);
        assertEquals( possibleGradeTest.description, possibleGrade1.description);
        assertEquals( possibleGradeTest.level, possibleGrade1.level);
        assertEquals( possibleGradeTest.grade, possibleGrade1.grade);
        assertEquals( possibleGradeTest.ucasPoints, possibleGrade1.ucasPoints);
    }
    
    @Test
    public void testEquals_Same() {
        PossibleGradeDto possibleGradeDto1 = new PossibleGradeDto(possibleGrade1.id, null, possibleGrade1.code, possibleGrade1.description, possibleGrade1.level.id, possibleGrade1.grade, possibleGrade1.ucasPoints, possibleGrade1.useForKeyAssessment)
        PossibleGradeDto possibleGradeDto2 = new PossibleGradeDto(possibleGrade1.id, null, possibleGrade1.code, possibleGrade1.description, possibleGrade1.level.id, possibleGrade1.grade, possibleGrade1.ucasPoints, possibleGrade1.useForKeyAssessment)
        assertEquals(possibleGradeDto1, possibleGradeDto2)
    }
    
    @Test
    public void testEquals_Different() {
        PossibleGradeDto possibleGradeDto1 = new PossibleGradeDto(possibleGrade1.id, null, possibleGrade1.code, possibleGrade1.description, possibleGrade1.level.id, possibleGrade1.grade, possibleGrade1.ucasPoints, possibleGrade1.useForKeyAssessment)
        PossibleGradeDto possibleGradeDto2 = new PossibleGradeDto(possibleGrade2.id, null, possibleGrade2.code, possibleGrade2.description, possibleGrade2.level.id, possibleGrade2.grade, possibleGrade2.ucasPoints, possibleGrade2.useForKeyAssessment)
        assertNotEquals(possibleGradeDto1, possibleGradeDto2)
    }
    
    @Test
    public void testHashCode_Same() {
        PossibleGradeDto possibleGradeDto1 = new PossibleGradeDto(possibleGrade1.id, null, possibleGrade1.code, possibleGrade1.description, possibleGrade1.level.id, possibleGrade1.grade, possibleGrade1.ucasPoints, possibleGrade1.useForKeyAssessment)
        PossibleGradeDto possibleGradeDto2 = new PossibleGradeDto(possibleGrade1.id, null, possibleGrade1.code, possibleGrade1.description, possibleGrade1.level.id, possibleGrade1.grade, possibleGrade1.ucasPoints, possibleGrade1.useForKeyAssessment)
        assertEquals(possibleGradeDto1.hashCode(), possibleGradeDto2.hashCode())
    }
    
    @Test
    public void testHashCode_Different() {
        PossibleGradeDto possibleGradeDto1 = new PossibleGradeDto(possibleGrade1.id, null, possibleGrade1.code, possibleGrade1.description, possibleGrade1.level.id, possibleGrade1.grade, possibleGrade1.ucasPoints, possibleGrade1.useForKeyAssessment)
        PossibleGradeDto possibleGradeDto2 = new PossibleGradeDto(possibleGrade2.id, null, possibleGrade2.code, possibleGrade2.description, possibleGrade2.level.id, possibleGrade2.grade, possibleGrade2.ucasPoints, possibleGrade2.useForKeyAssessment)
        assertNotEquals(possibleGradeDto1.hashCode(), possibleGradeDto2.hashCode())
    }
    
    @Test
    public void testConstructor_PossibleGrade() {
        PossibleGradeDto possibleGradeTest = new PossibleGradeDto(possibleGrade1)
        assertEquals( possibleGradeTest.id, possibleGrade1.id );
        assertEquals( possibleGradeTest.code, possibleGrade1.code);
        assertEquals( possibleGradeTest.description, possibleGrade1.description);
        assertEquals( possibleGradeTest.levelId, possibleGrade1.level.id);
        assertEquals( possibleGradeTest.grade, possibleGrade1.grade);
        assertEquals( possibleGradeTest.ucasPoints, possibleGrade1.ucasPoints)
    }
}
