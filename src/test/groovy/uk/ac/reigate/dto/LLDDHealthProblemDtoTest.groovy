package uk.ac.reigate.dto;

import static org.junit.Assert.*

import org.junit.Before
import org.junit.Test

import uk.ac.reigate.domain.ilr.LLDDHealthProblem

public class LLDDHealthProblemDtoTest {
    
    private LLDDHealthProblem lLDDHealthProblem1
    
    private LLDDHealthProblem lLDDHealthProblem2
    
    private List<LLDDHealthProblem> lLDDHealthProblems
    
    @Before
    public void setup() {
        lLDDHealthProblem1 = new LLDDHealthProblem(
                id: 1,
                code: 'UK',
                description: 'United Kingdom',
                shortDescription:'United Kingdom',
                validFrom: new Date().parse('yyyy/MM/dd', '2011/07/09'),
                validTo: new Date().parse('yyyy/MM/dd', '2013/07/09')
                );
        lLDDHealthProblem2 = new LLDDHealthProblem(
                id: 2,
                code: 'EU',
                description: 'European Ecconomical Union',
                shortDescription:'European Union',
                validFrom: new Date().parse('yyyy/MM/dd', '2011/07/09'),
                validTo: new Date().parse('yyyy/MM/dd', '2013/07/09')
                );
        lLDDHealthProblems = Arrays.asList(lLDDHealthProblem1, lLDDHealthProblem2);
    }
    
    @Test
    public void testMapFromLLDDHealthProblemEntityTest() {
        LLDDHealthProblemDto lLDDHealthProblemTest = LLDDHealthProblemDto.mapFromLLDDHealthProblemEntity( lLDDHealthProblem1 )
        assertEquals( lLDDHealthProblemTest.id, lLDDHealthProblem1.id);
        assertEquals( lLDDHealthProblemTest.code, lLDDHealthProblem1.code);
        assertEquals( lLDDHealthProblemTest.description, lLDDHealthProblem1.description);
        assertEquals( lLDDHealthProblemTest.shortDescription, lLDDHealthProblem1.shortDescription);
        assertEquals( lLDDHealthProblemTest.validFrom, lLDDHealthProblem1.validFrom);
        assertEquals( lLDDHealthProblemTest.validTo, lLDDHealthProblem1.validTo);
    }
    
    @Test
    public void testMapFromLLDDHealthProblemsEntitiesTest(){
        List<LLDDHealthProblemDto> lLDDHealthProblemsDtoTest = LLDDHealthProblemDto.mapFromLLDDHealthProblemsEntities(lLDDHealthProblems)
        assertEquals( lLDDHealthProblemsDtoTest[0].id, lLDDHealthProblem1.id );
        assertEquals( lLDDHealthProblemsDtoTest[0].code, lLDDHealthProblem1.code );
        assertEquals( lLDDHealthProblemsDtoTest[0].description, lLDDHealthProblem1.description);
        assertEquals( lLDDHealthProblemsDtoTest[0].shortDescription, lLDDHealthProblem1.shortDescription);
        assertEquals( lLDDHealthProblemsDtoTest[0].validFrom, lLDDHealthProblem1.validFrom);
        assertEquals( lLDDHealthProblemsDtoTest[0].validTo, lLDDHealthProblem1.validTo);
        assertEquals( lLDDHealthProblemsDtoTest[1].id, lLDDHealthProblem2.id );
        assertEquals( lLDDHealthProblemsDtoTest[1].code, lLDDHealthProblem2.code );
        assertEquals( lLDDHealthProblemsDtoTest[1].description, lLDDHealthProblem2.description);
        assertEquals( lLDDHealthProblemsDtoTest[1].shortDescription, lLDDHealthProblem2.shortDescription);
        assertEquals( lLDDHealthProblemsDtoTest[1].validFrom, lLDDHealthProblem2.validFrom);
        assertEquals( lLDDHealthProblemsDtoTest[1].validTo, lLDDHealthProblem2.validTo);
    }
    
    @Test
    public void testMapToLLDDHealthProblemEntityTest(){
        LLDDHealthProblemDto lLDDHealthProblemDto = new LLDDHealthProblemDto(lLDDHealthProblem1.id, lLDDHealthProblem1.code, lLDDHealthProblem1.description, lLDDHealthProblem1.shortDescription, lLDDHealthProblem1.validFrom, lLDDHealthProblem1.validTo)
        LLDDHealthProblem lLDDHealthProblem = LLDDHealthProblemDto.mapToLLDDHealthProblemEntity( lLDDHealthProblemDto )
        assertEquals( lLDDHealthProblem.id, lLDDHealthProblem1.id );
        assertEquals( lLDDHealthProblem.code, lLDDHealthProblem1.code);
        assertEquals( lLDDHealthProblem.description, lLDDHealthProblem1.description);
        assertEquals( lLDDHealthProblem.shortDescription, lLDDHealthProblem1.shortDescription);
        assertEquals( lLDDHealthProblem.validFrom, lLDDHealthProblem1.validFrom);
        assertEquals( lLDDHealthProblem.validTo, lLDDHealthProblem1.validTo);
    }
    
    @Test
    public void testEquals_Same() {
        LLDDHealthProblemDto lLDDHealthProblemDto1 = new LLDDHealthProblemDto(lLDDHealthProblem1.id, lLDDHealthProblem1.code, lLDDHealthProblem1.description, lLDDHealthProblem1.shortDescription, lLDDHealthProblem1.validFrom, lLDDHealthProblem1.validTo)
        LLDDHealthProblemDto lLDDHealthProblemDto2 = new LLDDHealthProblemDto(lLDDHealthProblem1.id, lLDDHealthProblem1.code, lLDDHealthProblem1.description, lLDDHealthProblem1.shortDescription, lLDDHealthProblem1.validFrom, lLDDHealthProblem1.validTo)
        assertEquals(lLDDHealthProblemDto1, lLDDHealthProblemDto2)
    }
    
    @Test
    public void testEquals_Different() {
        LLDDHealthProblemDto lLDDHealthProblemDto1 = new LLDDHealthProblemDto(lLDDHealthProblem1.id, lLDDHealthProblem1.code, lLDDHealthProblem1.description, lLDDHealthProblem1.shortDescription, lLDDHealthProblem1.validFrom, lLDDHealthProblem1.validTo)
        LLDDHealthProblemDto lLDDHealthProblemDto2 = new LLDDHealthProblemDto(lLDDHealthProblem2.id, lLDDHealthProblem2.code, lLDDHealthProblem2.description, lLDDHealthProblem2.shortDescription, lLDDHealthProblem2.validFrom, lLDDHealthProblem2.validTo)
        assertNotEquals(lLDDHealthProblemDto1, lLDDHealthProblemDto2)
    }
    
    @Test
    public void testHashCode_Same() {
        LLDDHealthProblemDto lLDDHealthProblemDto1 = new LLDDHealthProblemDto(lLDDHealthProblem1.id, lLDDHealthProblem1.code, lLDDHealthProblem1.description, lLDDHealthProblem2.shortDescription, lLDDHealthProblem1.validFrom, lLDDHealthProblem1.validTo)
        LLDDHealthProblemDto lLDDHealthProblemDto2 = new LLDDHealthProblemDto(lLDDHealthProblem1.id, lLDDHealthProblem1.code, lLDDHealthProblem1.description, lLDDHealthProblem2.shortDescription, lLDDHealthProblem1.validFrom, lLDDHealthProblem1.validTo)
        assertEquals(lLDDHealthProblemDto1.hashCode(), lLDDHealthProblemDto2.hashCode())
    }
    
    @Test
    public void testHashCode_Different() {
        LLDDHealthProblemDto lLDDHealthProblemDto1 = new LLDDHealthProblemDto(lLDDHealthProblem1.id, lLDDHealthProblem1.code, lLDDHealthProblem1.description, lLDDHealthProblem1.shortDescription, lLDDHealthProblem1.validFrom, lLDDHealthProblem1.validTo)
        LLDDHealthProblemDto lLDDHealthProblemDto2 = new LLDDHealthProblemDto(lLDDHealthProblem2.id, lLDDHealthProblem2.code, lLDDHealthProblem2.description, lLDDHealthProblem2.shortDescription, lLDDHealthProblem2.validFrom, lLDDHealthProblem2.validTo)
        assertNotEquals(lLDDHealthProblemDto1.hashCode(), lLDDHealthProblemDto2.hashCode())
    }
    
    @Test
    public void testConstructor_LLDDHealthProblem() {
        LLDDHealthProblemDto lLDDHealthProblemDto = new LLDDHealthProblemDto(lLDDHealthProblem1)
        assertEquals( lLDDHealthProblemDto.code, lLDDHealthProblem1.code )
        assertEquals( lLDDHealthProblemDto.description, lLDDHealthProblem1.description )
        assertEquals( lLDDHealthProblemDto.shortDescription, lLDDHealthProblem1.shortDescription )
        assertEquals( lLDDHealthProblemDto.validFrom, lLDDHealthProblem1.validFrom )
        assertEquals( lLDDHealthProblemDto.validTo, lLDDHealthProblem1.validTo )
    }
}
