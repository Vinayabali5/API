package uk.ac.reigate.dto;

import static org.junit.Assert.*
import org.junit.Before
import org.junit.Test

import uk.ac.reigate.domain.learning_support.InitialAssessmentLevel


public class InitialAssessmentLevelDtoTest {
    
    private InitialAssessmentLevel initialAssessmentLevel1
    
    private InitialAssessmentLevel initialAssessmentLevel2
    
    private List<InitialAssessmentLevel> initialAssessmentLevels
    
    @Before
    public void setup() {
        initialAssessmentLevel1 = new InitialAssessmentLevel(
                id: 1,
                initialAssessmentLevel:'M',
                abbrv:'M'
                );
        initialAssessmentLevel2 = new InitialAssessmentLevel(
                id: 2,
                initialAssessmentLevel:'F',
                abbrv:'F'
                );
        initialAssessmentLevels = Arrays.asList(initialAssessmentLevel1, initialAssessmentLevel2);
    }
    
    @Test
    public void testMapFromInitialAssessmentLevelEntity(){
        InitialAssessmentLevelDto initialAssessmentLevelTest = InitialAssessmentLevelDto.mapFromInitialAssessmentLevelEntity( initialAssessmentLevel1 )
        assertEquals( initialAssessmentLevelTest.id, initialAssessmentLevel1.id );
        assertEquals( initialAssessmentLevelTest.initialAssessmentLevel, initialAssessmentLevel1.initialAssessmentLevel);
        assertEquals( initialAssessmentLevelTest.abbrv, initialAssessmentLevel1.abbrv);
    }
    
    @Test
    public void testMapFromInitialAssessmentLevelsEntities(){
        List<InitialAssessmentLevelDto> initialAssessmentLevelsDtoTest = InitialAssessmentLevelDto.mapFromInitialAssessmentLevelsEntities( initialAssessmentLevels )
        assertEquals( initialAssessmentLevelsDtoTest[0].id, initialAssessmentLevel1.id );
        assertEquals( initialAssessmentLevelsDtoTest[0].initialAssessmentLevel, initialAssessmentLevel1.initialAssessmentLevel);
        assertEquals( initialAssessmentLevelsDtoTest[0].abbrv, initialAssessmentLevel1.abbrv);
        assertEquals( initialAssessmentLevelsDtoTest[1].id, initialAssessmentLevel2.id );
        assertEquals( initialAssessmentLevelsDtoTest[1].initialAssessmentLevel, initialAssessmentLevel2.initialAssessmentLevel);
        assertEquals( initialAssessmentLevelsDtoTest[1].abbrv, initialAssessmentLevel2.abbrv);
    }
    
    @Test
    public void testMapToInitialAssessmentLevelEntity(){
        InitialAssessmentLevelDto initialAssessmentLevelDto = new InitialAssessmentLevelDto(initialAssessmentLevel1.id, initialAssessmentLevel1.initialAssessmentLevel, initialAssessmentLevel1.abbrv);
        InitialAssessmentLevel initialAssessmentLevel = InitialAssessmentLevelDto.mapToInitialAssessmentLevelEntity( initialAssessmentLevelDto );
        assertEquals( initialAssessmentLevel.id, initialAssessmentLevel1.id );
        assertEquals( initialAssessmentLevel.initialAssessmentLevel, initialAssessmentLevel1.initialAssessmentLevel);
        assertEquals( initialAssessmentLevel.abbrv, initialAssessmentLevel1.abbrv);
    }
    
    @Test
    public void testEquals_Same() {
        InitialAssessmentLevelDto initialAssessmentLevelDto1 = new InitialAssessmentLevelDto(initialAssessmentLevel1.id, initialAssessmentLevel1.initialAssessmentLevel, initialAssessmentLevel1.abbrv)
        InitialAssessmentLevelDto initialAssessmentLevelDto2 = new InitialAssessmentLevelDto(initialAssessmentLevel1.id, initialAssessmentLevel1.initialAssessmentLevel, initialAssessmentLevel1.abbrv)
        assertEquals(initialAssessmentLevelDto1, initialAssessmentLevelDto2)
    }
    
    @Test
    public void testEquals_Different() {
        InitialAssessmentLevelDto initialAssessmentLevelDto1 = new InitialAssessmentLevelDto(initialAssessmentLevel1.id, initialAssessmentLevel1.initialAssessmentLevel, initialAssessmentLevel1.abbrv)
        InitialAssessmentLevelDto initialAssessmentLevelDto2 = new InitialAssessmentLevelDto(initialAssessmentLevel2.id, initialAssessmentLevel2.initialAssessmentLevel, initialAssessmentLevel2.abbrv)
        assertNotEquals(initialAssessmentLevelDto1, initialAssessmentLevelDto2)
    }
    
    @Test
    public void testHashCode_Same() {
        InitialAssessmentLevelDto initialAssessmentLevelDto1 = new InitialAssessmentLevelDto(initialAssessmentLevel1.id, initialAssessmentLevel1.initialAssessmentLevel, initialAssessmentLevel1.abbrv)
        InitialAssessmentLevelDto initialAssessmentLevelDto2 = new InitialAssessmentLevelDto(initialAssessmentLevel1.id, initialAssessmentLevel1.initialAssessmentLevel, initialAssessmentLevel1.abbrv)
        assertEquals(initialAssessmentLevelDto1.hashCode(), initialAssessmentLevelDto2.hashCode())
    }
    
    @Test
    public void testHashCode_Different() {
        InitialAssessmentLevelDto initialAssessmentLevelDto1 = new InitialAssessmentLevelDto(initialAssessmentLevel1.id, initialAssessmentLevel1.initialAssessmentLevel, initialAssessmentLevel1.abbrv)
        InitialAssessmentLevelDto initialAssessmentLevelDto2 = new InitialAssessmentLevelDto(initialAssessmentLevel2.id, initialAssessmentLevel2.initialAssessmentLevel, initialAssessmentLevel2.abbrv)
        assertNotEquals(initialAssessmentLevelDto1.hashCode(), initialAssessmentLevelDto2.hashCode())
    }
    
    @Test
    public void testConstructor_InitialAssessmentLevel() {
        InitialAssessmentLevelDto initialAssessmentLevelDto = new InitialAssessmentLevelDto(initialAssessmentLevel1)
        assertEquals( initialAssessmentLevelDto.initialAssessmentLevel, initialAssessmentLevel1.initialAssessmentLevel )
        assertEquals( initialAssessmentLevelDto.abbrv, initialAssessmentLevel1.abbrv )
    }
}
