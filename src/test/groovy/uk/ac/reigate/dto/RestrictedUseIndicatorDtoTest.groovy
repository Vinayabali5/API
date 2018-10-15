package uk.ac.reigate.dto;

import static org.junit.Assert.*

import org.junit.Before
import org.junit.Test

import uk.ac.reigate.domain.ilr.RestrictedUseIndicator

public class RestrictedUseIndicatorDtoTest {
    
    private RestrictedUseIndicator restrictedUseIndicator1
    
    private RestrictedUseIndicator restrictedUseIndicator2
    
    private List<RestrictedUseIndicator> restrictedUseIndicators
    
    @Before
    public void setup() {
        restrictedUseIndicator1 = new RestrictedUseIndicator(
                id: 1,
                code: 'UK',
                description: 'United Kingdom',
                shortDescription:'United Kingdom',
                validFrom: new Date().parse('yyyy/MM/dd', '2011/07/09'),
                validTo: new Date().parse('yyyy/MM/dd', '2013/07/09')
                );
        restrictedUseIndicator2 = new RestrictedUseIndicator(
                id: 2,
                code: 'EU',
                description: 'European Ecconomical Union',
                shortDescription:'European Union',
                validFrom: new Date().parse('yyyy/MM/dd', '2011/07/09'),
                validTo: new Date().parse('yyyy/MM/dd', '2013/07/09')
                );
        restrictedUseIndicators = Arrays.asList(restrictedUseIndicator1, restrictedUseIndicator2);
    }
    
    @Test
    public void testMapFromRestrictedUseIndicatorEntityTest() {
        RestrictedUseIndicatorDto restrictedUseIndicatorTest = RestrictedUseIndicatorDto.mapFromRestrictedUseIndicatorEntity( restrictedUseIndicator1 )
        assertEquals( restrictedUseIndicatorTest.id, restrictedUseIndicator1.id);
        assertEquals( restrictedUseIndicatorTest.code, restrictedUseIndicator1.code);
        assertEquals( restrictedUseIndicatorTest.description, restrictedUseIndicator1.description);
        assertEquals( restrictedUseIndicatorTest.shortDescription, restrictedUseIndicator1.shortDescription);
        assertEquals( restrictedUseIndicatorTest.validFrom, restrictedUseIndicator1.validFrom);
        assertEquals( restrictedUseIndicatorTest.validTo, restrictedUseIndicator1.validTo);
    }
    
    @Test
    public void testMapFromRestrictedUseIndicatorsEntitiesTest(){
        List<RestrictedUseIndicatorDto> restrictedUseIndicatorsDtoTest = RestrictedUseIndicatorDto.mapFromRestrictedUseIndicatorsEntities(restrictedUseIndicators)
        assertEquals( restrictedUseIndicatorsDtoTest[0].id, restrictedUseIndicator1.id );
        assertEquals( restrictedUseIndicatorsDtoTest[0].code, restrictedUseIndicator1.code );
        assertEquals( restrictedUseIndicatorsDtoTest[0].description, restrictedUseIndicator1.description);
        assertEquals( restrictedUseIndicatorsDtoTest[0].shortDescription, restrictedUseIndicator1.shortDescription);
        assertEquals( restrictedUseIndicatorsDtoTest[0].validFrom, restrictedUseIndicator1.validFrom);
        assertEquals( restrictedUseIndicatorsDtoTest[0].validTo, restrictedUseIndicator1.validTo);
        assertEquals( restrictedUseIndicatorsDtoTest[1].id, restrictedUseIndicator2.id );
        assertEquals( restrictedUseIndicatorsDtoTest[1].code, restrictedUseIndicator2.code );
        assertEquals( restrictedUseIndicatorsDtoTest[1].description, restrictedUseIndicator2.description);
        assertEquals( restrictedUseIndicatorsDtoTest[1].shortDescription, restrictedUseIndicator2.shortDescription);
        assertEquals( restrictedUseIndicatorsDtoTest[1].validFrom, restrictedUseIndicator2.validFrom);
        assertEquals( restrictedUseIndicatorsDtoTest[1].validTo, restrictedUseIndicator2.validTo);
    }
    
    @Test
    public void testMapToRestrictedUseIndicatorEntityTest(){
        RestrictedUseIndicatorDto restrictedUseIndicatorDto = new RestrictedUseIndicatorDto(restrictedUseIndicator1.id, restrictedUseIndicator1.code, restrictedUseIndicator1.description, restrictedUseIndicator1.shortDescription, restrictedUseIndicator1.validFrom, restrictedUseIndicator1.validTo)
        RestrictedUseIndicator restrictedUseIndicator = RestrictedUseIndicatorDto.mapToRestrictedUseIndicatorEntity( restrictedUseIndicatorDto )
        assertEquals( restrictedUseIndicator.id, restrictedUseIndicator1.id );
        assertEquals( restrictedUseIndicator.code, restrictedUseIndicator1.code);
        assertEquals( restrictedUseIndicator.description, restrictedUseIndicator1.description);
        assertEquals( restrictedUseIndicator.shortDescription, restrictedUseIndicator1.shortDescription);
        assertEquals( restrictedUseIndicator.validFrom, restrictedUseIndicator1.validFrom);
        assertEquals( restrictedUseIndicator.validTo, restrictedUseIndicator1.validTo);
    }
    
    @Test
    public void testEquals_Same() {
        RestrictedUseIndicatorDto restrictedUseIndicatorDto1 = new RestrictedUseIndicatorDto(restrictedUseIndicator1.id, restrictedUseIndicator1.code, restrictedUseIndicator1.description, restrictedUseIndicator1.shortDescription, restrictedUseIndicator1.validFrom, restrictedUseIndicator1.validTo)
        RestrictedUseIndicatorDto restrictedUseIndicatorDto2 = new RestrictedUseIndicatorDto(restrictedUseIndicator1.id, restrictedUseIndicator1.code, restrictedUseIndicator1.description, restrictedUseIndicator1.shortDescription, restrictedUseIndicator1.validFrom, restrictedUseIndicator1.validTo)
        assertEquals(restrictedUseIndicatorDto1, restrictedUseIndicatorDto2)
    }
    
    @Test
    public void testEquals_Different() {
        RestrictedUseIndicatorDto restrictedUseIndicatorDto1 = new RestrictedUseIndicatorDto(restrictedUseIndicator1.id, restrictedUseIndicator1.code, restrictedUseIndicator1.description, restrictedUseIndicator1.shortDescription, restrictedUseIndicator1.validFrom, restrictedUseIndicator1.validTo)
        RestrictedUseIndicatorDto restrictedUseIndicatorDto2 = new RestrictedUseIndicatorDto(restrictedUseIndicator2.id, restrictedUseIndicator2.code, restrictedUseIndicator2.description, restrictedUseIndicator2.shortDescription, restrictedUseIndicator2.validFrom, restrictedUseIndicator2.validTo)
        assertNotEquals(restrictedUseIndicatorDto1, restrictedUseIndicatorDto2)
    }
    
    @Test
    public void testHashCode_Same() {
        RestrictedUseIndicatorDto restrictedUseIndicatorDto1 = new RestrictedUseIndicatorDto(restrictedUseIndicator1.id, restrictedUseIndicator1.code, restrictedUseIndicator1.description, restrictedUseIndicator2.shortDescription, restrictedUseIndicator1.validFrom, restrictedUseIndicator1.validTo)
        RestrictedUseIndicatorDto restrictedUseIndicatorDto2 = new RestrictedUseIndicatorDto(restrictedUseIndicator1.id, restrictedUseIndicator1.code, restrictedUseIndicator1.description, restrictedUseIndicator2.shortDescription, restrictedUseIndicator1.validFrom, restrictedUseIndicator1.validTo)
        assertEquals(restrictedUseIndicatorDto1.hashCode(), restrictedUseIndicatorDto2.hashCode())
    }
    
    @Test
    public void testHashCode_Different() {
        RestrictedUseIndicatorDto restrictedUseIndicatorDto1 = new RestrictedUseIndicatorDto(restrictedUseIndicator1.id, restrictedUseIndicator1.code, restrictedUseIndicator1.description, restrictedUseIndicator1.shortDescription, restrictedUseIndicator1.validFrom, restrictedUseIndicator1.validTo)
        RestrictedUseIndicatorDto restrictedUseIndicatorDto2 = new RestrictedUseIndicatorDto(restrictedUseIndicator2.id, restrictedUseIndicator2.code, restrictedUseIndicator2.description, restrictedUseIndicator2.shortDescription, restrictedUseIndicator2.validFrom, restrictedUseIndicator2.validTo)
        assertNotEquals(restrictedUseIndicatorDto1.hashCode(), restrictedUseIndicatorDto2.hashCode())
    }
    
    @Test
    public void testConstructor_RestrictedUseIndicator() {
        RestrictedUseIndicatorDto restrictedUseIndicatorDto = new RestrictedUseIndicatorDto(restrictedUseIndicator1)
        assertEquals( restrictedUseIndicatorDto.code, restrictedUseIndicator1.code )
        assertEquals( restrictedUseIndicatorDto.description, restrictedUseIndicator1.description )
        assertEquals( restrictedUseIndicatorDto.shortDescription, restrictedUseIndicator1.shortDescription )
        assertEquals( restrictedUseIndicatorDto.validFrom, restrictedUseIndicator1.validFrom )
        assertEquals( restrictedUseIndicatorDto.validTo, restrictedUseIndicator1.validTo )
    }
}
