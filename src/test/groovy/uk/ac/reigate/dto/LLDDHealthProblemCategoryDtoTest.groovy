package uk.ac.reigate.dto;

import static org.junit.Assert.*

import org.junit.Before
import org.junit.Test

import uk.ac.reigate.domain.ilr.LLDDHealthProblemCategory

public class LLDDHealthProblemCategoryDtoTest {
    
    private LLDDHealthProblemCategory lLDDHealthProblemCategory1
    
    private LLDDHealthProblemCategory lLDDHealthProblemCategory2
    
    private List<LLDDHealthProblemCategory> lLDDHealthProblemCategorys
    
    @Before
    public void setup() {
        lLDDHealthProblemCategory1 = new LLDDHealthProblemCategory(1, 'UK', 'United Kingdom', 'United Kingdom', new Date().parse('yyyy/MM/dd', '2011/07/09'),new Date().parse('yyyy/MM/dd', '2013/07/09'));
        lLDDHealthProblemCategory2 = new LLDDHealthProblemCategory(2, 'EU', 'European Ecconomical Union', 'European Union', new Date().parse('yyyy/MM/dd', '2011/07/09'), new Date().parse('yyyy/MM/dd', '2013/07/09'));
        lLDDHealthProblemCategorys = Arrays.asList(lLDDHealthProblemCategory1, lLDDHealthProblemCategory2);
    }
    
    @Test
    public void testMapFromLLDDHealthProblemCategoryEntityTest() {
        LLDDHealthProblemCategoryDto lLDDHealthProblemCategoryTest = LLDDHealthProblemCategoryDto.mapFromLLDDHealthProblemCategoryEntity( lLDDHealthProblemCategory1 )
        assertEquals( lLDDHealthProblemCategoryTest.id, lLDDHealthProblemCategory1.id);
        assertEquals( lLDDHealthProblemCategoryTest.code, lLDDHealthProblemCategory1.code);
        assertEquals( lLDDHealthProblemCategoryTest.description, lLDDHealthProblemCategory1.description);
        assertEquals( lLDDHealthProblemCategoryTest.shortDescription, lLDDHealthProblemCategory1.shortDescription);
        assertEquals( lLDDHealthProblemCategoryTest.validFrom, lLDDHealthProblemCategory1.validFrom);
        assertEquals( lLDDHealthProblemCategoryTest.validTo, lLDDHealthProblemCategory1.validTo);
    }
    
    @Test
    public void testMapFromLLDDHealthProblemCategoriesEntitiesTest(){
        List<LLDDHealthProblemCategoryDto> lLDDHealthProblemCategorysDtoTest = LLDDHealthProblemCategoryDto.mapFromLLDDHealthProblemCategoriesEntities(lLDDHealthProblemCategorys)
        assertEquals( lLDDHealthProblemCategorysDtoTest[0].id, lLDDHealthProblemCategory1.id );
        assertEquals( lLDDHealthProblemCategorysDtoTest[0].code, lLDDHealthProblemCategory1.code );
        assertEquals( lLDDHealthProblemCategorysDtoTest[0].description, lLDDHealthProblemCategory1.description);
        assertEquals( lLDDHealthProblemCategorysDtoTest[0].shortDescription, lLDDHealthProblemCategory1.shortDescription);
        assertEquals( lLDDHealthProblemCategorysDtoTest[0].validFrom, lLDDHealthProblemCategory1.validFrom);
        assertEquals( lLDDHealthProblemCategorysDtoTest[0].validTo, lLDDHealthProblemCategory1.validTo);
        assertEquals( lLDDHealthProblemCategorysDtoTest[1].id, lLDDHealthProblemCategory2.id );
        assertEquals( lLDDHealthProblemCategorysDtoTest[1].code, lLDDHealthProblemCategory2.code );
        assertEquals( lLDDHealthProblemCategorysDtoTest[1].description, lLDDHealthProblemCategory2.description);
        assertEquals( lLDDHealthProblemCategorysDtoTest[1].shortDescription, lLDDHealthProblemCategory2.shortDescription);
        assertEquals( lLDDHealthProblemCategorysDtoTest[1].validFrom, lLDDHealthProblemCategory2.validFrom);
        assertEquals( lLDDHealthProblemCategorysDtoTest[1].validTo, lLDDHealthProblemCategory2.validTo);
    }
    
    @Test
    public void testMapToLLDDHealthProblemCategoryEntityTest(){
        LLDDHealthProblemCategoryDto lLDDHealthProblemCategoryDto = new LLDDHealthProblemCategoryDto(lLDDHealthProblemCategory1.id, lLDDHealthProblemCategory1.code, lLDDHealthProblemCategory1.description, lLDDHealthProblemCategory1.shortDescription, lLDDHealthProblemCategory1.validFrom, lLDDHealthProblemCategory1.validTo)
        LLDDHealthProblemCategory lLDDHealthProblemCategory = LLDDHealthProblemCategoryDto.mapToLLDDHealthProblemCategoryEntity( lLDDHealthProblemCategoryDto )
        assertEquals( lLDDHealthProblemCategory.id, lLDDHealthProblemCategory1.id );
        assertEquals( lLDDHealthProblemCategory.code, lLDDHealthProblemCategory1.code);
        assertEquals( lLDDHealthProblemCategory.description, lLDDHealthProblemCategory1.description);
        assertEquals( lLDDHealthProblemCategory.shortDescription, lLDDHealthProblemCategory1.shortDescription);
        assertEquals( lLDDHealthProblemCategory.validFrom, lLDDHealthProblemCategory1.validFrom);
        assertEquals( lLDDHealthProblemCategory.validTo, lLDDHealthProblemCategory1.validTo);
    }
    
    @Test
    public void testEquals_Same() {
        LLDDHealthProblemCategoryDto lLDDHealthProblemCategoryDto1 = new LLDDHealthProblemCategoryDto(lLDDHealthProblemCategory1.id, lLDDHealthProblemCategory1.code, lLDDHealthProblemCategory1.description, lLDDHealthProblemCategory1.shortDescription, lLDDHealthProblemCategory1.validFrom, lLDDHealthProblemCategory1.validTo)
        LLDDHealthProblemCategoryDto lLDDHealthProblemCategoryDto2 = new LLDDHealthProblemCategoryDto(lLDDHealthProblemCategory1.id, lLDDHealthProblemCategory1.code, lLDDHealthProblemCategory1.description, lLDDHealthProblemCategory1.shortDescription, lLDDHealthProblemCategory1.validFrom, lLDDHealthProblemCategory1.validTo)
        assertEquals(lLDDHealthProblemCategoryDto1, lLDDHealthProblemCategoryDto2)
    }
    
    @Test
    public void testEquals_Different() {
        LLDDHealthProblemCategoryDto lLDDHealthProblemCategoryDto1 = new LLDDHealthProblemCategoryDto(lLDDHealthProblemCategory1.id, lLDDHealthProblemCategory1.code, lLDDHealthProblemCategory1.description, lLDDHealthProblemCategory1.shortDescription, lLDDHealthProblemCategory1.validFrom, lLDDHealthProblemCategory1.validTo)
        LLDDHealthProblemCategoryDto lLDDHealthProblemCategoryDto2 = new LLDDHealthProblemCategoryDto(lLDDHealthProblemCategory2.id, lLDDHealthProblemCategory2.code, lLDDHealthProblemCategory2.description, lLDDHealthProblemCategory2.shortDescription, lLDDHealthProblemCategory2.validFrom, lLDDHealthProblemCategory2.validTo)
        assertNotEquals(lLDDHealthProblemCategoryDto1, lLDDHealthProblemCategoryDto2)
    }
    
    @Test
    public void testHashCode_Same() {
        LLDDHealthProblemCategoryDto lLDDHealthProblemCategoryDto1 = new LLDDHealthProblemCategoryDto(lLDDHealthProblemCategory1.id, lLDDHealthProblemCategory1.code, lLDDHealthProblemCategory1.description, lLDDHealthProblemCategory2.shortDescription, lLDDHealthProblemCategory1.validFrom, lLDDHealthProblemCategory1.validTo)
        LLDDHealthProblemCategoryDto lLDDHealthProblemCategoryDto2 = new LLDDHealthProblemCategoryDto(lLDDHealthProblemCategory1.id, lLDDHealthProblemCategory1.code, lLDDHealthProblemCategory1.description, lLDDHealthProblemCategory2.shortDescription, lLDDHealthProblemCategory1.validFrom, lLDDHealthProblemCategory1.validTo)
        assertEquals(lLDDHealthProblemCategoryDto1.hashCode(), lLDDHealthProblemCategoryDto2.hashCode())
    }
    
    @Test
    public void testHashCode_Different() {
        LLDDHealthProblemCategoryDto lLDDHealthProblemCategoryDto1 = new LLDDHealthProblemCategoryDto(lLDDHealthProblemCategory1.id, lLDDHealthProblemCategory1.code, lLDDHealthProblemCategory1.description, lLDDHealthProblemCategory1.shortDescription, lLDDHealthProblemCategory1.validFrom, lLDDHealthProblemCategory1.validTo)
        LLDDHealthProblemCategoryDto lLDDHealthProblemCategoryDto2 = new LLDDHealthProblemCategoryDto(lLDDHealthProblemCategory2.id, lLDDHealthProblemCategory2.code, lLDDHealthProblemCategory2.description, lLDDHealthProblemCategory2.shortDescription, lLDDHealthProblemCategory2.validFrom, lLDDHealthProblemCategory2.validTo)
        assertNotEquals(lLDDHealthProblemCategoryDto1.hashCode(), lLDDHealthProblemCategoryDto2.hashCode())
    }
    
    @Test
    public void testConstructor_LLDDHealthProblemCategory() {
        LLDDHealthProblemCategoryDto lLDDHealthProblemCategoryDto = new LLDDHealthProblemCategoryDto(lLDDHealthProblemCategory1)
        assertEquals( lLDDHealthProblemCategoryDto.code, lLDDHealthProblemCategory1.code )
        assertEquals( lLDDHealthProblemCategoryDto.description, lLDDHealthProblemCategory1.description )
        assertEquals( lLDDHealthProblemCategoryDto.shortDescription, lLDDHealthProblemCategory1.shortDescription )
        assertEquals( lLDDHealthProblemCategoryDto.validFrom, lLDDHealthProblemCategory1.validFrom )
        assertEquals( lLDDHealthProblemCategoryDto.validTo, lLDDHealthProblemCategory1.validTo )
    }
}
