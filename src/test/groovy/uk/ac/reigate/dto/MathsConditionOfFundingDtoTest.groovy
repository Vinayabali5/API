package uk.ac.reigate.dto;

import static org.junit.Assert.*

import org.junit.Before
import org.junit.Test

import uk.ac.reigate.domain.ilr.MathsConditionOfFunding

public class MathsConditionOfFundingDtoTest {
    
    private MathsConditionOfFunding mathsConditionOfFunding1
    
    private MathsConditionOfFunding mathsConditionOfFunding2
    
    private List<MathsConditionOfFunding> mathsConditionOfFundings
    
    @Before
    public void setup() {
        mathsConditionOfFunding1 = new MathsConditionOfFunding(
                id: 1,
                code: 'UK',
                description: 'United Kingdom',
                shortDescription:'United Kingdom',
                validFrom: new Date().parse('yyyy/MM/dd', '2011/07/09'),
                validTo: new Date().parse('yyyy/MM/dd', '2013/07/09')
                );
        mathsConditionOfFunding2 = new MathsConditionOfFunding(
                id: 2,
                code: 'EU',
                description: 'European Ecconomical Union',
                shortDescription:'European Union',
                validFrom: new Date().parse('yyyy/MM/dd', '2011/07/09'),
                validTo: new Date().parse('yyyy/MM/dd', '2013/07/09')
                );
        mathsConditionOfFundings = Arrays.asList(mathsConditionOfFunding1, mathsConditionOfFunding2);
    }
    
    @Test
    public void testMapFromMathsConditionOfFundingEntityTest() {
        MathsConditionOfFundingDto mathsConditionOfFundingTest = MathsConditionOfFundingDto.mapFromMathsConditionOfFundingEntity( mathsConditionOfFunding1 )
        assertEquals( mathsConditionOfFundingTest.id, mathsConditionOfFunding1.id);
        assertEquals( mathsConditionOfFundingTest.code, mathsConditionOfFunding1.code);
        assertEquals( mathsConditionOfFundingTest.description, mathsConditionOfFunding1.description);
        assertEquals( mathsConditionOfFundingTest.shortDescription, mathsConditionOfFunding1.shortDescription);
        assertEquals( mathsConditionOfFundingTest.validFrom, mathsConditionOfFunding1.validFrom);
        assertEquals( mathsConditionOfFundingTest.validTo, mathsConditionOfFunding1.validTo);
    }
    
    @Test
    public void testMapFromMathsConditionOfFundingsEntitiesTest(){
        List<MathsConditionOfFundingDto> mathsConditionOfFundingsDtoTest = MathsConditionOfFundingDto.mapFromMathsConditionOfFundingsEntities( this.mathsConditionOfFundings )
        assertEquals( mathsConditionOfFundingsDtoTest[0].id, mathsConditionOfFunding1.id );
        assertEquals( mathsConditionOfFundingsDtoTest[0].code, mathsConditionOfFunding1.code );
        assertEquals( mathsConditionOfFundingsDtoTest[0].description, mathsConditionOfFunding1.description);
        assertEquals( mathsConditionOfFundingsDtoTest[0].shortDescription, mathsConditionOfFunding1.shortDescription);
        assertEquals( mathsConditionOfFundingsDtoTest[0].validFrom, mathsConditionOfFunding1.validFrom);
        assertEquals( mathsConditionOfFundingsDtoTest[0].validTo, mathsConditionOfFunding1.validTo);
        assertEquals( mathsConditionOfFundingsDtoTest[1].id, mathsConditionOfFunding2.id );
        assertEquals( mathsConditionOfFundingsDtoTest[1].code, mathsConditionOfFunding2.code );
        assertEquals( mathsConditionOfFundingsDtoTest[1].description, mathsConditionOfFunding2.description);
        assertEquals( mathsConditionOfFundingsDtoTest[1].shortDescription, mathsConditionOfFunding2.shortDescription);
        assertEquals( mathsConditionOfFundingsDtoTest[1].validFrom, mathsConditionOfFunding2.validFrom);
        assertEquals( mathsConditionOfFundingsDtoTest[1].validTo, mathsConditionOfFunding2.validTo);
    }
    
    @Test
    public void testMapToMathsConditionOfFundingEntityTest(){
        MathsConditionOfFundingDto mathsConditionOfFundingDto = new MathsConditionOfFundingDto(mathsConditionOfFunding1.id, mathsConditionOfFunding1.code, mathsConditionOfFunding1.description, mathsConditionOfFunding1.shortDescription, mathsConditionOfFunding1.validFrom, mathsConditionOfFunding1.validTo)
        MathsConditionOfFunding mathsConditionOfFunding = MathsConditionOfFundingDto.mapToMathsConditionOfFundingEntity( mathsConditionOfFundingDto )
        assertEquals( mathsConditionOfFunding.id, mathsConditionOfFunding1.id );
        assertEquals( mathsConditionOfFunding.code, mathsConditionOfFunding1.code);
        assertEquals( mathsConditionOfFunding.description, mathsConditionOfFunding1.description);
        assertEquals( mathsConditionOfFunding.shortDescription, mathsConditionOfFunding1.shortDescription);
        assertEquals( mathsConditionOfFunding.validFrom, mathsConditionOfFunding1.validFrom);
        assertEquals( mathsConditionOfFunding.validTo, mathsConditionOfFunding1.validTo);
    }
    
    @Test
    public void testEquals_Same() {
        MathsConditionOfFundingDto mathsConditionOfFundingDto1 = new MathsConditionOfFundingDto(mathsConditionOfFunding1.id, mathsConditionOfFunding1.code, mathsConditionOfFunding1.description, mathsConditionOfFunding1.shortDescription, mathsConditionOfFunding1.validFrom, mathsConditionOfFunding1.validTo)
        MathsConditionOfFundingDto mathsConditionOfFundingDto2 = new MathsConditionOfFundingDto(mathsConditionOfFunding1.id, mathsConditionOfFunding1.code, mathsConditionOfFunding1.description, mathsConditionOfFunding1.shortDescription, mathsConditionOfFunding1.validFrom, mathsConditionOfFunding1.validTo)
        assertEquals(mathsConditionOfFundingDto1, mathsConditionOfFundingDto2)
    }
    
    @Test
    public void testEquals_Different() {
        MathsConditionOfFundingDto mathsConditionOfFundingDto1 = new MathsConditionOfFundingDto(mathsConditionOfFunding1.id, mathsConditionOfFunding1.code, mathsConditionOfFunding1.description, mathsConditionOfFunding1.shortDescription, mathsConditionOfFunding1.validFrom, mathsConditionOfFunding1.validTo)
        MathsConditionOfFundingDto mathsConditionOfFundingDto2 = new MathsConditionOfFundingDto(mathsConditionOfFunding2.id, mathsConditionOfFunding2.code, mathsConditionOfFunding2.description, mathsConditionOfFunding2.shortDescription, mathsConditionOfFunding2.validFrom, mathsConditionOfFunding2.validTo)
        assertNotEquals(mathsConditionOfFundingDto1, mathsConditionOfFundingDto2)
    }
    
    @Test
    public void testHashCode_Same() {
        MathsConditionOfFundingDto mathsConditionOfFundingDto1 = new MathsConditionOfFundingDto(mathsConditionOfFunding1.id, mathsConditionOfFunding1.code, mathsConditionOfFunding1.description, mathsConditionOfFunding2.shortDescription, mathsConditionOfFunding1.validFrom, mathsConditionOfFunding1.validTo)
        MathsConditionOfFundingDto mathsConditionOfFundingDto2 = new MathsConditionOfFundingDto(mathsConditionOfFunding1.id, mathsConditionOfFunding1.code, mathsConditionOfFunding1.description, mathsConditionOfFunding2.shortDescription, mathsConditionOfFunding1.validFrom, mathsConditionOfFunding1.validTo)
        assertEquals(mathsConditionOfFundingDto1.hashCode(), mathsConditionOfFundingDto2.hashCode())
    }
    
    @Test
    public void testHashCode_Different() {
        MathsConditionOfFundingDto mathsConditionOfFundingDto1 = new MathsConditionOfFundingDto(mathsConditionOfFunding1.id, mathsConditionOfFunding1.code, mathsConditionOfFunding1.description, mathsConditionOfFunding1.shortDescription, mathsConditionOfFunding1.validFrom, mathsConditionOfFunding1.validTo)
        MathsConditionOfFundingDto mathsConditionOfFundingDto2 = new MathsConditionOfFundingDto(mathsConditionOfFunding2.id, mathsConditionOfFunding2.code, mathsConditionOfFunding2.description, mathsConditionOfFunding2.shortDescription, mathsConditionOfFunding2.validFrom, mathsConditionOfFunding2.validTo)
        assertNotEquals(mathsConditionOfFundingDto1.hashCode(), mathsConditionOfFundingDto2.hashCode())
    }
    
    @Test
    public void testConstructor_MathsConditionOfFunding() {
        MathsConditionOfFundingDto mathsConditionOfFundingDto = new MathsConditionOfFundingDto(mathsConditionOfFunding1)
        assertEquals( mathsConditionOfFundingDto.code, mathsConditionOfFunding1.code )
        assertEquals( mathsConditionOfFundingDto.description, mathsConditionOfFunding1.description )
        assertEquals( mathsConditionOfFundingDto.shortDescription, mathsConditionOfFunding1.shortDescription )
        assertEquals( mathsConditionOfFundingDto.validFrom, mathsConditionOfFunding1.validFrom )
        assertEquals( mathsConditionOfFundingDto.validTo, mathsConditionOfFunding1.validTo )
    }
}
