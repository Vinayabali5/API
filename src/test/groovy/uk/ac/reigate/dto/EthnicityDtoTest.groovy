package uk.ac.reigate.dto;

import static org.junit.Assert.*

import org.junit.Before
import org.junit.Test

import uk.ac.reigate.domain.lookup.Ethnicity

public class EthnicityDtoTest {
    
    private Ethnicity ethnicity1
    
    private Ethnicity ethnicity2
    
    private List<Ethnicity> ethnicities
    
    @Before
    public void setup() {
        ethnicity1 = new Ethnicity(
                id: 1,
                code: 'UK',
                description: 'United Kingdom',
                shortDescription:'United Kingdom',
                validFrom: new Date().parse('yyyy/MM/dd', '2011/07/09'),
                validTo: new Date().parse('yyyy/MM/dd', '2013/07/09')
                );
        ethnicity2 = new Ethnicity(
                id: 2,
                code: 'EU',
                description: 'European Ecconomical Union',
                shortDescription:'European Union',
                validFrom: new Date().parse('yyyy/MM/dd', '2011/07/09'),
                validTo: new Date().parse('yyyy/MM/dd', '2013/07/09')
                );
        ethnicities = Arrays.asList(ethnicity1, ethnicity2);
    }
    
    @Test
    public void testMapFromEthnicityEntityTest() {
        EthnicityDto ethnicityTest = EthnicityDto.mapFromEthnicityEntity( ethnicity1 )
        assertEquals( ethnicityTest.id, ethnicity1.id);
        assertEquals( ethnicityTest.code, ethnicity1.code);
        assertEquals( ethnicityTest.description, ethnicity1.description);
        assertEquals( ethnicityTest.shortDescription, ethnicity1.shortDescription);
        assertEquals( ethnicityTest.validFrom, ethnicity1.validFrom);
        assertEquals( ethnicityTest.validTo, ethnicity1.validTo);
    }
    
    @Test
    public void testMapFromEthnicitysEntitiesTest(){
        List<EthnicityDto> ethnicitiesDtoTest = EthnicityDto.mapFromEthnicitiesEntities(ethnicities)
        assertEquals( ethnicitiesDtoTest[0].id, ethnicity1.id );
        assertEquals( ethnicitiesDtoTest[0].code, ethnicity1.code );
        assertEquals( ethnicitiesDtoTest[0].description, ethnicity1.description);
        assertEquals( ethnicitiesDtoTest[0].shortDescription, ethnicity1.shortDescription);
        assertEquals( ethnicitiesDtoTest[0].validFrom, ethnicity1.validFrom);
        assertEquals( ethnicitiesDtoTest[0].validTo, ethnicity1.validTo);
        assertEquals( ethnicitiesDtoTest[1].id, ethnicity2.id );
        assertEquals( ethnicitiesDtoTest[1].code, ethnicity2.code );
        assertEquals( ethnicitiesDtoTest[1].description, ethnicity2.description);
        assertEquals( ethnicitiesDtoTest[1].shortDescription, ethnicity2.shortDescription);
        assertEquals( ethnicitiesDtoTest[1].validFrom, ethnicity2.validFrom);
        assertEquals( ethnicitiesDtoTest[1].validTo, ethnicity2.validTo);
    }
    
    @Test
    public void testMapToEthnicityEntityTest(){
        EthnicityDto ethnicityDto = new EthnicityDto(ethnicity1.id, ethnicity1.code, ethnicity1.description, ethnicity1.shortDescription, ethnicity1.validFrom, ethnicity1.validTo)
        Ethnicity ethnicity = EthnicityDto.mapToEthnicityEntity( ethnicityDto )
        assertEquals( ethnicity.id, ethnicity1.id );
        assertEquals( ethnicity.code, ethnicity1.code);
        assertEquals( ethnicity.description, ethnicity1.description);
        assertEquals( ethnicity.shortDescription, ethnicity1.shortDescription);
        assertEquals( ethnicity.validFrom, ethnicity1.validFrom);
        assertEquals( ethnicity.validTo, ethnicity1.validTo);
    }
    
    @Test
    public void testEquals_Same() {
        EthnicityDto ethnicityDto1 = new EthnicityDto(ethnicity1.id, ethnicity1.code, ethnicity1.description, ethnicity1.shortDescription, ethnicity1.validFrom, ethnicity1.validTo)
        EthnicityDto ethnicityDto2 = new EthnicityDto(ethnicity1.id, ethnicity1.code, ethnicity1.description, ethnicity1.shortDescription, ethnicity1.validFrom, ethnicity1.validTo)
        assertEquals(ethnicityDto1, ethnicityDto2)
    }
    
    @Test
    public void testEquals_Different() {
        EthnicityDto ethnicityDto1 = new EthnicityDto(ethnicity1.id, ethnicity1.code, ethnicity1.description, ethnicity1.shortDescription, ethnicity1.validFrom, ethnicity1.validTo)
        EthnicityDto ethnicityDto2 = new EthnicityDto(ethnicity2.id, ethnicity2.code, ethnicity2.description, ethnicity2.shortDescription, ethnicity2.validFrom, ethnicity2.validTo)
        assertNotEquals(ethnicityDto1, ethnicityDto2)
    }
    
    @Test
    public void testHashCode_Same() {
        EthnicityDto ethnicityDto1 = new EthnicityDto(ethnicity1.id, ethnicity1.code, ethnicity1.description, ethnicity2.shortDescription, ethnicity1.validFrom, ethnicity1.validTo)
        EthnicityDto ethnicityDto2 = new EthnicityDto(ethnicity1.id, ethnicity1.code, ethnicity1.description, ethnicity2.shortDescription, ethnicity1.validFrom, ethnicity1.validTo)
        assertEquals(ethnicityDto1.hashCode(), ethnicityDto2.hashCode())
    }
    
    @Test
    public void testHashCode_Different() {
        EthnicityDto ethnicityDto1 = new EthnicityDto(ethnicity1.id, ethnicity1.code, ethnicity1.description, ethnicity1.shortDescription, ethnicity1.validFrom, ethnicity1.validTo)
        EthnicityDto ethnicityDto2 = new EthnicityDto(ethnicity2.id, ethnicity2.code, ethnicity2.description, ethnicity2.shortDescription, ethnicity2.validFrom, ethnicity2.validTo)
        assertNotEquals(ethnicityDto1.hashCode(), ethnicityDto2.hashCode())
    }
    
    @Test
    public void testConstructor_Ethnicity() {
        EthnicityDto ethnicityDto = new EthnicityDto(ethnicity1)
        assertEquals( ethnicityDto.code, ethnicity1.code )
        assertEquals( ethnicityDto.description, ethnicity1.description )
        assertEquals( ethnicityDto.shortDescription, ethnicity1.shortDescription )
        assertEquals( ethnicityDto.validFrom, ethnicity1.validFrom )
        assertEquals( ethnicityDto.validTo, ethnicity1.validTo )
    }
}
