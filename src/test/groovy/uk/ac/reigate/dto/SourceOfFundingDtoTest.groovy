package uk.ac.reigate.dto;

import static org.junit.Assert.*

import org.junit.Before
import org.junit.Test

import uk.ac.reigate.domain.ilr.SourceOfFunding

public class SourceOfFundingDtoTest {
    
    private SourceOfFunding sourceOfFunding1
    
    private SourceOfFunding sourceOfFunding2
    
    private List<SourceOfFunding> sourceOfFundings
    
    @Before
    public void setup() {
        this.sourceOfFunding1 = new SourceOfFunding(
                id: 1,
                code: 'UK',
                description: 'United Kingdom',
                shortDescription:'United Kingdom',
                validFrom: new Date().parse('yyyy/MM/dd', '2011/07/09'),
                validTo: new Date().parse('yyyy/MM/dd', '2013/07/09')
                );
        this.sourceOfFunding2 = new SourceOfFunding(
                id: 2,
                code: 'EU',
                description: 'European Ecconomical Union',
                shortDescription:'European Union',
                validFrom: new Date().parse('yyyy/MM/dd', '2011/07/09'),
                validTo: new Date().parse('yyyy/MM/dd', '2013/07/09')
                );
        this.sourceOfFundings = Arrays.asList(sourceOfFunding1, sourceOfFunding2);
    }
    
    @Test
    public void testMapFromSourceOfFundingEntityTest() {
        SourceOfFundingDto sourceOfFundingTest = SourceOfFundingDto.mapFromSourceOfFundingEntity( sourceOfFunding1 )
        assertEquals( sourceOfFundingTest.id, sourceOfFunding1.id);
        assertEquals( sourceOfFundingTest.code, sourceOfFunding1.code);
        assertEquals( sourceOfFundingTest.description, sourceOfFunding1.description);
        assertEquals( sourceOfFundingTest.shortDescription, sourceOfFunding1.shortDescription);
        assertEquals( sourceOfFundingTest.validFrom, sourceOfFunding1.validFrom);
        assertEquals( sourceOfFundingTest.validTo, sourceOfFunding1.validTo);
    }
    
    @Test
    public void testMapFromSourceOfFundingsEntitiesTest(){
        List<SourceOfFundingDto> sourceOfFundingsDtoTest = SourceOfFundingDto.mapFromSourceOfFundingsEntities( this.sourceOfFundings )
        assertEquals( sourceOfFundingsDtoTest[0].id, sourceOfFunding1.id );
        assertEquals( sourceOfFundingsDtoTest[0].code, sourceOfFunding1.code );
        assertEquals( sourceOfFundingsDtoTest[0].description, sourceOfFunding1.description);
        assertEquals( sourceOfFundingsDtoTest[0].shortDescription, sourceOfFunding1.shortDescription);
        assertEquals( sourceOfFundingsDtoTest[0].validFrom, sourceOfFunding1.validFrom);
        assertEquals( sourceOfFundingsDtoTest[0].validTo, sourceOfFunding1.validTo);
        assertEquals( sourceOfFundingsDtoTest[1].id, sourceOfFunding2.id );
        assertEquals( sourceOfFundingsDtoTest[1].code, sourceOfFunding2.code );
        assertEquals( sourceOfFundingsDtoTest[1].description, sourceOfFunding2.description);
        assertEquals( sourceOfFundingsDtoTest[1].shortDescription, sourceOfFunding2.shortDescription);
        assertEquals( sourceOfFundingsDtoTest[1].validFrom, sourceOfFunding2.validFrom);
        assertEquals( sourceOfFundingsDtoTest[1].validTo, sourceOfFunding2.validTo);
    }
    
    @Test
    public void testMapToSourceOfFundingEntityTest(){
        SourceOfFundingDto sourceOfFundingDto = new SourceOfFundingDto(sourceOfFunding1.id, sourceOfFunding1.code, sourceOfFunding1.description, sourceOfFunding1.shortDescription, sourceOfFunding1.validFrom, sourceOfFunding1.validTo)
        SourceOfFunding sourceOfFunding = SourceOfFundingDto.mapToSourceOfFundingEntity( sourceOfFundingDto )
        assertEquals( sourceOfFunding.id, sourceOfFunding1.id );
        assertEquals( sourceOfFunding.code, sourceOfFunding1.code);
        assertEquals( sourceOfFunding.description, sourceOfFunding1.description);
        assertEquals( sourceOfFunding.shortDescription, sourceOfFunding1.shortDescription);
        assertEquals( sourceOfFunding.validFrom, sourceOfFunding1.validFrom);
        assertEquals( sourceOfFunding.validTo, sourceOfFunding1.validTo);
    }
    
    @Test
    public void testEquals_Same() {
        SourceOfFundingDto sourceOfFundingDto1 = new SourceOfFundingDto(sourceOfFunding1.id, sourceOfFunding1.code, sourceOfFunding1.description, sourceOfFunding1.shortDescription, sourceOfFunding1.validFrom, sourceOfFunding1.validTo)
        SourceOfFundingDto sourceOfFundingDto2 = new SourceOfFundingDto(sourceOfFunding1.id, sourceOfFunding1.code, sourceOfFunding1.description, sourceOfFunding1.shortDescription, sourceOfFunding1.validFrom, sourceOfFunding1.validTo)
        assertEquals( sourceOfFundingDto1, sourceOfFundingDto2)
    }
    
    @Test
    public void testEquals_Different() {
        SourceOfFundingDto sourceOfFundingDto1 = new SourceOfFundingDto(sourceOfFunding1.id, sourceOfFunding1.code, sourceOfFunding1.description, sourceOfFunding1.shortDescription, sourceOfFunding1.validFrom, sourceOfFunding1.validTo)
        SourceOfFundingDto sourceOfFundingDto2 = new SourceOfFundingDto(sourceOfFunding2.id, sourceOfFunding2.code, sourceOfFunding2.description, sourceOfFunding2.shortDescription, sourceOfFunding2.validFrom, sourceOfFunding2.validTo)
        assertNotEquals( sourceOfFundingDto1, sourceOfFundingDto2)
    }
    
    @Test
    public void testHashCode_Same() {
        SourceOfFundingDto sourceOfFundingDto1 = new SourceOfFundingDto(sourceOfFunding1.id, sourceOfFunding1.code, sourceOfFunding1.description, sourceOfFunding1.shortDescription, sourceOfFunding1.validFrom, sourceOfFunding1.validTo)
        SourceOfFundingDto sourceOfFundingDto2 = new SourceOfFundingDto(sourceOfFunding1.id, sourceOfFunding1.code, sourceOfFunding1.description, sourceOfFunding1.shortDescription, sourceOfFunding1.validFrom, sourceOfFunding1.validTo)
        assertEquals( sourceOfFundingDto1.hashCode(), sourceOfFundingDto2.hashCode())
    }
    
    @Test
    public void testHashCode_Different() {
        SourceOfFundingDto sourceOfFundingDto1 = new SourceOfFundingDto(sourceOfFunding1.id, sourceOfFunding1.code, sourceOfFunding1.description, sourceOfFunding1.shortDescription, sourceOfFunding1.validFrom, sourceOfFunding1.validTo)
        SourceOfFundingDto sourceOfFundingDto2 = new SourceOfFundingDto(sourceOfFunding2.id, sourceOfFunding2.code, sourceOfFunding2.description, sourceOfFunding2.shortDescription, sourceOfFunding2.validFrom, sourceOfFunding2.validTo)
        assertNotEquals( sourceOfFundingDto1.hashCode(), sourceOfFundingDto2.hashCode())
    }
    
    @Test
    public void testConstructor_SourceOfFunding() {
        SourceOfFundingDto sourceOfFundingDto = new SourceOfFundingDto(sourceOfFunding1)
        assertEquals( sourceOfFundingDto.id, sourceOfFunding1.id );
        assertEquals( sourceOfFundingDto.code, sourceOfFunding1.code);
        assertEquals( sourceOfFundingDto.description, sourceOfFunding1.description);
        assertEquals( sourceOfFundingDto.shortDescription, sourceOfFunding1.shortDescription);
        assertEquals( sourceOfFundingDto.validFrom, sourceOfFunding1.validFrom);
        assertEquals( sourceOfFundingDto.validTo, sourceOfFunding1.validTo);
    }
}
