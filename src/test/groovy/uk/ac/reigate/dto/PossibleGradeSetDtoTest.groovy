package uk.ac.reigate.dto;

import static org.junit.Assert.*
import org.junit.Before
import org.junit.Test

import uk.ac.reigate.domain.lookup.PossibleGradeSet


public class PossibleGradeSetDtoTest {
    
    private PossibleGradeSet possibleGradeSet1
    
    private PossibleGradeSet possibleGradeSet2
    
    private List<PossibleGradeSet> possibleGradeSets
    
    @Before
    public void setup() {
        possibleGradeSet1 = new PossibleGradeSet(
                id: 1,
                code:'M',
                description:'M'
                );
        possibleGradeSet2 = new PossibleGradeSet(
                id: 2,
                code:'F',
                description:'F'
                );
        possibleGradeSets = Arrays.asList(possibleGradeSet1, possibleGradeSet2);
    }
    
    @Test
    public void testMapFromPossibleGradeSetEntity(){
        PossibleGradeSetDto possibleGradeSetTest = PossibleGradeSetDto.mapFromPossibleGradeSetEntity( possibleGradeSet1 )
        assertEquals( possibleGradeSetTest.id, possibleGradeSet1.id );
        assertEquals( possibleGradeSetTest.code, possibleGradeSet1.code);
        assertEquals( possibleGradeSetTest.description, possibleGradeSet1.description);
    }
    
    @Test
    public void testMapFromPossibleGradeSetsEntities(){
        List<PossibleGradeSetDto> possibleGradeSetsDtoTest = PossibleGradeSetDto.mapFromPossibleGradeSetsEntities( possibleGradeSets )
        assertEquals( possibleGradeSetsDtoTest[0].id, possibleGradeSet1.id );
        assertEquals( possibleGradeSetsDtoTest[0].code, possibleGradeSet1.code);
        assertEquals( possibleGradeSetsDtoTest[0].description, possibleGradeSet1.description);
        assertEquals( possibleGradeSetsDtoTest[1].id, possibleGradeSet2.id );
        assertEquals( possibleGradeSetsDtoTest[1].code, possibleGradeSet2.code);
        assertEquals( possibleGradeSetsDtoTest[1].description, possibleGradeSet2.description);
    }
    
    @Test
    public void testMapToPossibleGradeSetEntity(){
        PossibleGradeSetDto possibleGradeSetDto = new PossibleGradeSetDto(possibleGradeSet1.id, possibleGradeSet1.code, possibleGradeSet1.description);
        PossibleGradeSet possibleGradeSet = PossibleGradeSetDto.mapToPossibleGradeSetEntity( possibleGradeSetDto );
        assertEquals( possibleGradeSet.id, possibleGradeSet1.id );
        assertEquals( possibleGradeSet.code, possibleGradeSet1.code);
        assertEquals( possibleGradeSet.description, possibleGradeSet1.description);
    }
    
    @Test
    public void testEquals_Same() {
        PossibleGradeSetDto possibleGradeSetDto1 = new PossibleGradeSetDto(possibleGradeSet1.id, possibleGradeSet1.code, possibleGradeSet1.description)
        PossibleGradeSetDto possibleGradeSetDto2 = new PossibleGradeSetDto(possibleGradeSet1.id, possibleGradeSet1.code, possibleGradeSet1.description)
        assertEquals(possibleGradeSetDto1, possibleGradeSetDto2)
    }
    
    @Test
    public void testEquals_Different() {
        PossibleGradeSetDto possibleGradeSetDto1 = new PossibleGradeSetDto(possibleGradeSet1.id, possibleGradeSet1.code, possibleGradeSet1.description)
        PossibleGradeSetDto possibleGradeSetDto2 = new PossibleGradeSetDto(possibleGradeSet2.id, possibleGradeSet2.code, possibleGradeSet2.description)
        assertNotEquals(possibleGradeSetDto1, possibleGradeSetDto2)
    }
    
    @Test
    public void testHashCode_Same() {
        PossibleGradeSetDto possibleGradeSetDto1 = new PossibleGradeSetDto(possibleGradeSet1.id, possibleGradeSet1.code, possibleGradeSet1.description)
        PossibleGradeSetDto possibleGradeSetDto2 = new PossibleGradeSetDto(possibleGradeSet1.id, possibleGradeSet1.code, possibleGradeSet1.description)
        assertEquals(possibleGradeSetDto1.hashCode(), possibleGradeSetDto2.hashCode())
    }
    
    @Test
    public void testHashCode_Different() {
        PossibleGradeSetDto possibleGradeSetDto1 = new PossibleGradeSetDto(possibleGradeSet1.id, possibleGradeSet1.code, possibleGradeSet1.description)
        PossibleGradeSetDto possibleGradeSetDto2 = new PossibleGradeSetDto(possibleGradeSet2.id, possibleGradeSet2.code, possibleGradeSet2.description)
        assertNotEquals(possibleGradeSetDto1.hashCode(), possibleGradeSetDto2.hashCode())
    }
    
    @Test
    public void testConstructor_PossibleGradeSet() {
        PossibleGradeSetDto possibleGradeSetDto = new PossibleGradeSetDto(possibleGradeSet1)
        assertEquals( possibleGradeSetDto.code, possibleGradeSet1.code )
        assertEquals( possibleGradeSetDto.description, possibleGradeSet1.description )
    }
}
