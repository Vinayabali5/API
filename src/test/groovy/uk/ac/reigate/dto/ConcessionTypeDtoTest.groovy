package uk.ac.reigate.dto;

import static org.junit.Assert.*
import org.junit.Before
import org.junit.Test

import uk.ac.reigate.domain.learning_support.ConcessionType


public class ConcessionTypeDtoTest {
    
    private ConcessionType concessionType1
    
    private ConcessionType concessionType2
    
    private List<ConcessionType> concessionTypes
    
    @Before
    public void setup() {
        this.concessionType1 = new ConcessionType(
                id: 1,
                code:'M',
                description:'M'
                );
        this.concessionType2 = new ConcessionType(
                id: 2,
                code:'F',
                description:'F'
                );
        this.concessionTypes = Arrays.asList(this.concessionType1, this.concessionType2);
    }
    
    @Test
    void testConstructor_concessionType() {
        ConcessionTypeDto concessionTypeTest = new ConcessionTypeDto( concessionType1 )
        assertEquals( concessionTypeTest.id, concessionType1.id );
        assertEquals( concessionTypeTest.code, concessionType1.code);
        assertEquals( concessionTypeTest.description, concessionType1.description);
    }
    
    @Test
    public void testMapFromConcessionTypeEntity(){
        ConcessionTypeDto concessionTypeTest = ConcessionTypeDto.mapFromConcessionTypeEntity( concessionType1 )
        assertEquals( concessionTypeTest.id, concessionType1.id );
        assertEquals( concessionTypeTest.code, concessionType1.code);
        assertEquals( concessionTypeTest.description, concessionType1.description);
    }
    
    @Test
    public void testMapFromConcessionTypesEntities(){
        List<ConcessionTypeDto> concessionTypesDtoTest = ConcessionTypeDto.mapFromConcessionTypesEntities( this.concessionTypes )
        assertEquals( concessionTypesDtoTest[0].id, concessionType1.id );
        assertEquals( concessionTypesDtoTest[0].code, concessionType1.code);
        assertEquals( concessionTypesDtoTest[0].description, concessionType1.description);
        assertEquals( concessionTypesDtoTest[1].id, concessionType2.id );
        assertEquals( concessionTypesDtoTest[1].code, concessionType2.code);
        assertEquals( concessionTypesDtoTest[1].description, concessionType2.description);
    }
    
    @Test
    public void testMapToConcessionTypeEntity(){
        ConcessionTypeDto concessionTypeDto = new ConcessionTypeDto(concessionType1.id, concessionType1.code, concessionType1.description);
        ConcessionType concessionType = ConcessionTypeDto.mapToConcessionTypeEntity( concessionTypeDto );
        assertEquals( concessionType.id, concessionType1.id );
        assertEquals( concessionType.code, concessionType1.code);
        assertEquals( concessionType.description, concessionType1.description);
    }
    
    @Test
    public void testEquals_Same() {
        ConcessionTypeDto concessionTypeDto1 = new ConcessionTypeDto(concessionType1.id, concessionType1.code, concessionType1.description)
        ConcessionTypeDto concessionTypeDto2 = new ConcessionTypeDto(concessionType1.id, concessionType1.code, concessionType1.description)
        assertEquals(concessionTypeDto1, concessionTypeDto2)
    }
    
    @Test
    public void testEquals_Different() {
        ConcessionTypeDto concessionTypeDto1 = new ConcessionTypeDto(concessionType1.id, concessionType1.code, concessionType1.description)
        ConcessionTypeDto concessionTypeDto2 = new ConcessionTypeDto(concessionType2.id, concessionType2.code, concessionType2.description)
        assertNotEquals(concessionTypeDto1, concessionTypeDto2)
    }
    
    @Test
    public void testHashCode_Same() {
        ConcessionTypeDto concessionTypeDto1 = new ConcessionTypeDto(concessionType1.id, concessionType1.code, concessionType1.description)
        ConcessionTypeDto concessionTypeDto2 = new ConcessionTypeDto(concessionType1.id, concessionType1.code, concessionType1.description)
        assertEquals(concessionTypeDto1.hashCode(), concessionTypeDto2.hashCode())
    }
    
    @Test
    public void testHashCode_Different() {
        ConcessionTypeDto concessionTypeDto1 = new ConcessionTypeDto(concessionType1.id, concessionType1.code, concessionType1.description)
        ConcessionTypeDto concessionTypeDto2 = new ConcessionTypeDto(concessionType2.id, concessionType2.code, concessionType2.description)
        assertNotEquals(concessionTypeDto1.hashCode(), concessionTypeDto2.hashCode())
    }
    
    @Test
    public void testConstructor_ConcessionType() {
        ConcessionTypeDto concessionTypeDto = new ConcessionTypeDto(concessionType1)
        assertEquals( concessionTypeDto.code, concessionType1.code )
        assertEquals( concessionTypeDto.description, concessionType1.description )
    }
}
