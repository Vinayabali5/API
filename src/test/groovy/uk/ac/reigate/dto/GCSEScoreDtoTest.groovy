package uk.ac.reigate.dto;

import static org.junit.Assert.*
import org.junit.Before
import org.junit.Test
import uk.ac.reigate.domain.academic.GCSEScore
import uk.ac.reigate.domain.academic.Student;



public class GCSEScoreDtoTest {
    
    private GCSEScore gCSEScore1
    
    private GCSEScore gCSEScore2
    
    private List<GCSEScore> gCSEScores
    
    Student createStudent() {
        Student student = new Student()
        student.id = 1
        return student
    }
    
    @Before
    public void setup() {
        gCSEScore1 = new GCSEScore(
                student: createStudent(),
                countOfQualifications: 1,
                countOfGCSEs:2,
                passes:2,
                passesAToC: 3,
                score: 1.0f,
                average: 1.1f
                );
        gCSEScore2 = new GCSEScore(
                student: createStudent(),
                countOfQualifications: 1,
                countOfGCSEs:1,
                passes:1,
                passesAToC: 1,
                score: 3.0f,
                average: 2.1f
                );
        gCSEScores = Arrays.asList(gCSEScore1, gCSEScore2);
    }
    
    @Test
    public void testMapFromGCSEScoreEntity(){
        GCSEScoreDto gCSEScoreTest = GCSEScoreDto.mapFromGCSEScoreEntity( gCSEScore1 )
        assertEquals( gCSEScoreTest.countOfQualifications, gCSEScore1.countOfQualifications);
        assertEquals( gCSEScoreTest.countOfGCSEs, gCSEScore1.countOfGCSEs);
        assertEquals( gCSEScoreTest.passes, gCSEScore1.passes)
        assertEquals( gCSEScoreTest.passesAToC, gCSEScore1.passesAToC)
        assertEquals( gCSEScoreTest.score, gCSEScore1.score, 0.0f)
        assertEquals( gCSEScoreTest.average, gCSEScore1.average,  0.0f)
    }
    
    @Test
    public void testMapFromGCSEScoresEntities(){
        List<GCSEScoreDto> gCSEScoresDtoTest = GCSEScoreDto.mapFromGCSEScoresEntities( gCSEScores )
        assertEquals( gCSEScoresDtoTest[0].countOfQualifications, gCSEScore1.countOfQualifications);
        assertEquals( gCSEScoresDtoTest[0].countOfGCSEs, gCSEScore1.countOfGCSEs);
        assertEquals( gCSEScoresDtoTest[0].passes, gCSEScore1.passes);
        assertEquals( gCSEScoresDtoTest[0].passesAToC, gCSEScore1.passesAToC);
        assertEquals( gCSEScoresDtoTest[0].score, gCSEScore1.score, 0.0f);
        assertEquals( gCSEScoresDtoTest[0].average, gCSEScore1.average, 0.0f);
        
        assertEquals( gCSEScoresDtoTest[1].countOfQualifications, gCSEScore2.countOfQualifications);
        assertEquals( gCSEScoresDtoTest[1].countOfGCSEs, gCSEScore2.countOfGCSEs);
        assertEquals( gCSEScoresDtoTest[1].passes, gCSEScore2.passes);
        assertEquals( gCSEScoresDtoTest[1].passesAToC, gCSEScore2.passesAToC);
        assertEquals( gCSEScoresDtoTest[1].score, gCSEScore2.score, 0.0f);
        assertEquals( gCSEScoresDtoTest[1].average, gCSEScore2.average, 0.0f);
    }
    
    @Test
    public void testEquals_Same() {
        GCSEScoreDto gCSEScoreDto1 = new GCSEScoreDto(gCSEScore1)
        GCSEScoreDto gCSEScoreDto2 = new GCSEScoreDto(gCSEScore1)
        assertEquals(gCSEScoreDto1, gCSEScoreDto2)
    }
    
    @Test
    public void testEquals_Different() {
        GCSEScoreDto gCSEScoreDto1 = new GCSEScoreDto(gCSEScore1)
        GCSEScoreDto gCSEScoreDto2 = new GCSEScoreDto(gCSEScore2)
        assertNotEquals(gCSEScoreDto1, gCSEScoreDto2)
    }
    
    @Test
    public void testHashCode_Same() {
        GCSEScoreDto gCSEScoreDto1 = new GCSEScoreDto(gCSEScore1)
        GCSEScoreDto gCSEScoreDto2 = new GCSEScoreDto(gCSEScore1)
        assertEquals(gCSEScoreDto1.hashCode(), gCSEScoreDto2.hashCode())
    }
    
    @Test
    public void testHashCode_Different() {
        GCSEScoreDto gCSEScoreDto1 = new GCSEScoreDto(gCSEScore1)
        GCSEScoreDto gCSEScoreDto2 = new GCSEScoreDto(gCSEScore2)
        assertNotEquals(gCSEScoreDto1.hashCode(), gCSEScoreDto2.hashCode())
    }
    
    @Test
    public void testConstructor_GCSEScore() {
        GCSEScoreDto gCSEScoreTest= new GCSEScoreDto(gCSEScore1)
        assertEquals( gCSEScoreTest.countOfQualifications, gCSEScore1.countOfQualifications);
        assertEquals( gCSEScoreTest.countOfGCSEs, gCSEScore1.countOfGCSEs);
        assertEquals( gCSEScoreTest.passes, gCSEScore1.passes)
        assertEquals( gCSEScoreTest.passesAToC, gCSEScore1.passesAToC)
        assertEquals( gCSEScoreTest.score, gCSEScore1.score, 0.0f)
        assertEquals( gCSEScoreTest.average, gCSEScore1.average, 0.0f)
    }
}
