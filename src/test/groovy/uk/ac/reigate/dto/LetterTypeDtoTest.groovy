package uk.ac.reigate.dto;

import static org.junit.Assert.*

import org.junit.Before
import org.junit.Test

import uk.ac.reigate.domain.ilp.LetterType

public class LetterTypeDtoTest {
    
    private LetterType letterType1
    
    private LetterType letterType2
    
    private List<LetterType> letterTypes
    
    @Before
    public void setup() {
        this.letterType1 = new LetterType(
                id: 1,
                type: 'A'
                );
        this.letterType2 = new LetterType(
                id: 2,
                type: 'B'
                );
        this.letterTypes = Arrays.asList(this.letterType1, this.letterType2);
    }
    
    @Test
    public void testMapFromLetterTypeEntity(){
        LetterTypeDto letterTypeTest = LetterTypeDto.mapFromLetterTypeEntity( letterType1 )
        assertEquals( letterTypeTest.id, letterType1.id );
        assertEquals( letterTypeTest.type, letterType1.type);
    }
    
    @Test
    public void testMapFromLetterTypesEntities(){
        List<LetterTypeDto> letterTypesTest = LetterTypeDto.mapFromLetterTypesEntities( this.letterTypes )
        assertEquals( letterTypesTest[0].id, letterType1.id );
        assertEquals( letterTypesTest[0].type, letterType1.type );
        assertEquals( letterTypesTest[1].id, letterType2.id );
        assertEquals( letterTypesTest[1].type, letterType2.type );
    }
    
    @Test
    public void testMapToLetterTypeEntity(){
        LetterTypeDto letterTypeDto = new LetterTypeDto(letterType1.id, letterType1.type);
        LetterType letterType = LetterTypeDto.mapToLetterTypeEntity( letterTypeDto );
        assertEquals( letterType.id, letterType1.id );
        assertEquals( letterType.type, letterType1.type);
    }
    
    @Test
    public void testEquals_Same() {
        LetterTypeDto letterTypeDto1 = new LetterTypeDto(letterType1.id, letterType1.type)
        LetterTypeDto letterTypeDto2 = new LetterTypeDto(letterType1.id, letterType1.type)
        assertEquals(letterTypeDto1, letterTypeDto2)
    }
    
    @Test
    public void testEquals_Different() {
        LetterTypeDto letterTypeDto1 = new LetterTypeDto(letterType1.id, letterType1.type)
        LetterTypeDto letterTypeDto2 = new LetterTypeDto(letterType2.id, letterType2.type)
        assertNotEquals(letterTypeDto1, letterTypeDto2)
    }
    
    @Test
    public void testHashCode_Same() {
        LetterTypeDto letterTypeDto1 = new LetterTypeDto(letterType1.id, letterType1.type)
        LetterTypeDto letterTypeDto2 = new LetterTypeDto(letterType1.id, letterType1.type)
        assertEquals(letterTypeDto1.hashCode(), letterTypeDto2.hashCode())
    }
    
    @Test
    public void testHashCode_Different() {
        LetterTypeDto letterTypeDto1 = new LetterTypeDto(letterType1.id, letterType1.type)
        LetterTypeDto letterTypeDto2 = new LetterTypeDto(letterType2.id, letterType2.type)
        assertNotEquals(letterTypeDto1.hashCode(), letterTypeDto2.hashCode())
    }
    
    @Test
    public void testConstructor_LetterType() {
        LetterTypeDto letterTypeDto = new LetterTypeDto(letterType1)
        assertEquals( letterTypeDto.type, letterType1.type )
    }
}
