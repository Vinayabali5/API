package uk.ac.reigate.dto;

import static org.junit.Assert.*

import org.junit.Before
import org.junit.Test

import uk.ac.reigate.domain.ilp.CorrespondenceType

public class CorrespondenceTypeDtoTest {
    
    private CorrespondenceType correspondenceType1
    
    private CorrespondenceType correspondenceType2
    
    private List<CorrespondenceType> correspondenceTypes
    
    @Before
    public void setup() {
        this.correspondenceType1 = new CorrespondenceType(
                id: 1,
                type: 'A'
                );
        this.correspondenceType2 = new CorrespondenceType(
                id: 2,
                type: 'B'
                );
        this.correspondenceTypes = Arrays.asList(this.correspondenceType1, this.correspondenceType2);
    }
    
    @Test
    void testConstructor_correspondenceType() {
        CorrespondenceTypeDto correspondenceTypeTest = new CorrespondenceTypeDto( correspondenceType1 )
        assertEquals( correspondenceTypeTest.id, correspondenceType1.id );
        assertEquals( correspondenceTypeTest.type, correspondenceType1.type);
    }
    
    @Test
    public void testMapFromCorrespondenceTypeEntity(){
        CorrespondenceTypeDto correspondenceTypeTest = CorrespondenceTypeDto.mapFromCorrespondenceTypeEntity( correspondenceType1 )
        assertEquals( correspondenceTypeTest.id, correspondenceType1.id );
        assertEquals( correspondenceTypeTest.type, correspondenceType1.type);
    }
    
    @Test
    public void testMapFromCorrespondenceTypesEntities(){
        List<CorrespondenceTypeDto> correspondenceTypesTest = CorrespondenceTypeDto.mapFromCorrespondenceTypesEntities( this.correspondenceTypes )
        assertEquals( correspondenceTypesTest[0].id, correspondenceType1.id );
        assertEquals( correspondenceTypesTest[0].type, correspondenceType1.type );
        assertEquals( correspondenceTypesTest[1].id, correspondenceType2.id );
        assertEquals( correspondenceTypesTest[1].type, correspondenceType2.type );
    }
    
    @Test
    public void testMapToCorrespondenceTypeEntity(){
        CorrespondenceTypeDto correspondenceTypeDto = new CorrespondenceTypeDto(correspondenceType1.id, correspondenceType1.type);
        CorrespondenceType correspondenceType = CorrespondenceTypeDto.mapToCorrespondenceTypeEntity( correspondenceTypeDto );
        assertEquals( correspondenceType.id, correspondenceType1.id );
        assertEquals( correspondenceType.type, correspondenceType1.type);
    }
    
    @Test
    public void testEquals_Same() {
        CorrespondenceTypeDto correspondenceTypeDto1 = new CorrespondenceTypeDto(correspondenceType1.id, correspondenceType1.type)
        CorrespondenceTypeDto correspondenceTypeDto2 = new CorrespondenceTypeDto(correspondenceType1.id, correspondenceType1.type)
        assertEquals(correspondenceTypeDto1, correspondenceTypeDto2)
    }
    
    @Test
    public void testEquals_Different() {
        CorrespondenceTypeDto correspondenceTypeDto1 = new CorrespondenceTypeDto(correspondenceType1.id, correspondenceType1.type)
        CorrespondenceTypeDto correspondenceTypeDto2 = new CorrespondenceTypeDto(correspondenceType2.id, correspondenceType2.type)
        assertNotEquals(correspondenceTypeDto1, correspondenceTypeDto2)
    }
    
    @Test
    public void testHashCode_Same() {
        CorrespondenceTypeDto correspondenceTypeDto1 = new CorrespondenceTypeDto(correspondenceType1.id, correspondenceType1.type)
        CorrespondenceTypeDto correspondenceTypeDto2 = new CorrespondenceTypeDto(correspondenceType1.id, correspondenceType1.type)
        assertEquals(correspondenceTypeDto1.hashCode(), correspondenceTypeDto2.hashCode())
    }
    
    @Test
    public void testHashCode_Different() {
        CorrespondenceTypeDto correspondenceTypeDto1 = new CorrespondenceTypeDto(correspondenceType1.id, correspondenceType1.type)
        CorrespondenceTypeDto correspondenceTypeDto2 = new CorrespondenceTypeDto(correspondenceType2.id, correspondenceType2.type)
        assertNotEquals(correspondenceTypeDto1.hashCode(), correspondenceTypeDto2.hashCode())
    }
    
    @Test
    public void testConstructor_CorrespondenceType() {
        CorrespondenceTypeDto correspondenceTypeDto = new CorrespondenceTypeDto(correspondenceType1)
        assertEquals( correspondenceTypeDto.id, correspondenceType1.id )
        assertEquals( correspondenceTypeDto.type, correspondenceType1.type )
    }
}
