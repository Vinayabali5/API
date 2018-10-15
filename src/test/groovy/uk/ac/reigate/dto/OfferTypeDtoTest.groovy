package uk.ac.reigate.dto;

import static org.junit.Assert.*
import org.junit.Before

import java.util.List;

import org.junit.Before;
import org.junit.Test

import uk.ac.reigate.domain.admissions.OfferType




public class OfferTypeDtoTest {
    
    private OfferType offerType1
    
    private OfferType offerType2
    
    private List<OfferType> offerTypes
    
    @Before
    public void setup() {
        offerType1 = new OfferType(
                id: 1,
                code:'C',
                description:'Conditional'
                );
        offerType2 = new OfferType(
                id: 2,
                code:'N',
                description:'Normal'
                );
        offerTypes = Arrays.asList(offerType1, offerType2);
    }
    
    @Test
    public void testMapFromOfferTypeEntity(){
        OfferTypeDto offerTypeTest = OfferTypeDto.mapFromOfferTypeEntity( offerType1 )
        assertEquals( offerTypeTest.id, offerType1.id );
        assertEquals( offerTypeTest.code, offerType1.code);
        assertEquals( offerTypeTest.description, offerType1.description);
    }
    
    @Test
    public void testMapFromOfferTypesEntities(){
        List<OfferTypeDto> offerTypesDtoTest = OfferTypeDto.mapFromOfferTypesEntities( offerTypes )
        assertEquals( offerTypesDtoTest[0].id, offerType1.id );
        assertEquals( offerTypesDtoTest[0].code, offerType1.code);
        assertEquals( offerTypesDtoTest[0].description, offerType1.description);
        assertEquals( offerTypesDtoTest[1].id, offerType2.id );
        assertEquals( offerTypesDtoTest[1].code, offerType2.code);
        assertEquals( offerTypesDtoTest[1].description, offerType2.description);
    }
    
    @Test
    public void testMapToOfferTypeEntity(){
        OfferTypeDto offerTypeDto = new OfferTypeDto(offerType1.id, offerType1.code, offerType1.description);
        OfferType offerType = OfferTypeDto.mapToOfferTypeEntity( offerTypeDto );
        assertEquals( offerType.id, offerType1.id );
        assertEquals( offerType.code, offerType1.code);
        assertEquals( offerType.description, offerType1.description);
    }
    
    @Test
    public void testEquals_Same() {
        OfferTypeDto offerTypeDto1 = new OfferTypeDto(offerType1.id, offerType1.code, offerType1.description)
        OfferTypeDto offerTypeDto2 = new OfferTypeDto(offerType1.id, offerType1.code, offerType1.description)
        assertEquals(offerTypeDto1, offerTypeDto2)
    }
    
    @Test
    public void testEquals_Different() {
        OfferTypeDto offerTypeDto1 = new OfferTypeDto(offerType1.id, offerType1.code, offerType1.description)
        OfferTypeDto offerTypeDto2 = new OfferTypeDto(offerType2.id, offerType2.code, offerType2.description)
        assertNotEquals(offerTypeDto1, offerTypeDto2)
    }
    
    @Test
    public void testHashCode_Same() {
        OfferTypeDto offerTypeDto1 = new OfferTypeDto(offerType1.id, offerType1.code, offerType1.description)
        OfferTypeDto offerTypeDto2 = new OfferTypeDto(offerType1.id, offerType1.code, offerType1.description)
        assertEquals(offerTypeDto1.hashCode(), offerTypeDto2.hashCode())
    }
    
    @Test
    public void testHashCode_Different() {
        OfferTypeDto offerTypeDto1 = new OfferTypeDto(offerType1.id, offerType1.code, offerType1.description)
        OfferTypeDto offerTypeDto2 = new OfferTypeDto(offerType2.id, offerType2.code, offerType2.description)
        assertNotEquals(offerTypeDto1.hashCode(), offerTypeDto2.hashCode())
    }
    
    @Test
    public void testConstructor_OfferType() {
        OfferTypeDto offerTypeDto = new OfferTypeDto(offerType1)
        assertEquals( offerTypeDto.code, offerType1.code )
        assertEquals( offerTypeDto.description, offerType1.description )
    }
}
