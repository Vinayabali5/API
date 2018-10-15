package uk.ac.reigate.dto;

import static org.junit.Assert.*
import org.junit.Before
import org.junit.Test

import uk.ac.reigate.domain.learning_support.SupportType


public class SupportTypeDtoTest {
    
    private SupportType supportType1
    
    private SupportType supportType2
    
    private List<SupportType> supportTypes
    
    @Before
    public void setup() {
        supportType1 = new SupportType(
                id: 1,
                support:'M'
                );
        supportType2 = new SupportType(
                id: 2,
                support:'F'
                );
        supportTypes = Arrays.asList(supportType1, supportType2);
    }
    
    @Test
    public void testMapFromSupportTypeEntity(){
        SupportTypeDto supportTypeTest = SupportTypeDto.mapFromSupportTypeEntity( supportType1 )
        assertEquals( supportTypeTest.id, supportType1.id );
        assertEquals( supportTypeTest.support, supportType1.support);
    }
    
    @Test
    public void testMapFromSupportTypesEntities(){
        List<SupportTypeDto> supportTypesDtoTest = SupportTypeDto.mapFromSupportTypesEntities( supportTypes )
        assertEquals( supportTypesDtoTest[0].id, supportType1.id );
        assertEquals( supportTypesDtoTest[0].support, supportType1.support);
        assertEquals( supportTypesDtoTest[1].id, supportType2.id );
        assertEquals( supportTypesDtoTest[1].support, supportType2.support);
    }
    
    @Test
    public void testMapToSupportTypeEntity(){
        SupportTypeDto supportTypeDto = new SupportTypeDto(supportType1.id, supportType1.support);
        SupportType supportType = SupportTypeDto.mapToSupportTypeEntity( supportTypeDto );
        assertEquals( supportType.id, supportType1.id );
        assertEquals( supportType.support, supportType1.support);
    }
    
    @Test
    public void testEquals_Same() {
        SupportTypeDto supportTypeDto1 = new SupportTypeDto(supportType1.id, supportType1.support)
        SupportTypeDto supportTypeDto2 = new SupportTypeDto(supportType1.id, supportType1.support)
        assertEquals(supportTypeDto1, supportTypeDto2)
    }
    
    @Test
    public void testEquals_Different() {
        SupportTypeDto supportTypeDto1 = new SupportTypeDto(supportType1.id, supportType1.support)
        SupportTypeDto supportTypeDto2 = new SupportTypeDto(supportType2.id, supportType2.support)
        assertNotEquals(supportTypeDto1, supportTypeDto2)
    }
    
    @Test
    public void testHashCode_Same() {
        SupportTypeDto supportTypeDto1 = new SupportTypeDto(supportType1.id, supportType1.support)
        SupportTypeDto supportTypeDto2 = new SupportTypeDto(supportType1.id, supportType1.support)
        assertEquals(supportTypeDto1.hashCode(), supportTypeDto2.hashCode())
    }
    
    @Test
    public void testHashCode_Different() {
        SupportTypeDto supportTypeDto1 = new SupportTypeDto(supportType1.id, supportType1.support)
        SupportTypeDto supportTypeDto2 = new SupportTypeDto(supportType2.id, supportType2.support)
        assertNotEquals(supportTypeDto1.hashCode(), supportTypeDto2.hashCode())
    }
    
    @Test
    public void testConstructor_SupportType() {
        SupportTypeDto supportTypeDto = new SupportTypeDto(supportType1)
        assertEquals( supportTypeDto.support, supportType1.support )
    }
}
