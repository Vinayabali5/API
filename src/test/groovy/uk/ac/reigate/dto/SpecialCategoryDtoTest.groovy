package uk.ac.reigate.dto;

import static org.junit.Assert.*
import org.junit.Before
import java.util.List;

import org.junit.Before;
import org.junit.Test

import uk.ac.reigate.domain.academic.SpecialCategory


public class SpecialCategoryDtoTest {
    
    private SpecialCategory specialCategory1
    
    private SpecialCategory specialCategory2
    
    private List<SpecialCategory> specialCategories
    
    @Before
    public void setup() {
        specialCategory1 = new SpecialCategory(
                id: 1,
                code:'s1',
                description:'Long Term',
                details:'Both mental and physical health including ME',
                priority: 1
                );
        specialCategory2 = new SpecialCategory(
                id: 2,
                code:'s2',
                description:'Short Term',
                details:'Such as child protection issues.',
                priority: 2
                );
        specialCategories = Arrays.asList(specialCategory1, specialCategory2);
    }
    
    @Test
    public void testMapFromSpecialCategoryEntity(){
        SpecialCategoryDto specialCategoryTest = SpecialCategoryDto.mapFromSpecialCategoryEntity( specialCategory1 )
        assertEquals( specialCategoryTest.id, specialCategory1.id );
        assertEquals( specialCategoryTest.code, specialCategory1.code);
        assertEquals( specialCategoryTest.description, specialCategory1.description);
        assertEquals( specialCategoryTest.details, specialCategory1.details);
    }
    
    @Test
    public void testMapFromSpecialCategorysEntities(){
        List<SpecialCategoryDto> specialCategorysDtoTest = SpecialCategoryDto.mapFromSpecialCategoriesEntities( specialCategories )
        assertEquals( specialCategorysDtoTest[0].id, specialCategory1.id );
        assertEquals( specialCategorysDtoTest[0].code, specialCategory1.code);
        assertEquals( specialCategorysDtoTest[0].description, specialCategory1.description);
        assertEquals( specialCategorysDtoTest[0].details, specialCategory1.details);
        assertEquals( specialCategorysDtoTest[1].id, specialCategory2.id );
        assertEquals( specialCategorysDtoTest[1].code, specialCategory2.code);
        assertEquals( specialCategorysDtoTest[1].description, specialCategory2.description);
        assertEquals( specialCategorysDtoTest[1].details, specialCategory2.details);
    }
    
    @Test
    public void testMapToSpecialCategoryEntity(){
        SpecialCategoryDto specialCategoryDto = new SpecialCategoryDto(specialCategory1.id, specialCategory1.code, specialCategory1.description, specialCategory1.details, specialCategory1.priority);
        SpecialCategory specialCategory = SpecialCategoryDto.mapToSpecialCategoryEntity( specialCategoryDto );
        assertEquals( specialCategory.id, specialCategory1.id );
        assertEquals( specialCategory.code, specialCategory1.code);
        assertEquals( specialCategory.description, specialCategory1.description);
        assertEquals( specialCategory.details, specialCategory1.details);
    }
    
    @Test
    public void testEquals_Same() {
        SpecialCategoryDto specialCategoryDto1 = new SpecialCategoryDto(specialCategory1.id, specialCategory1.code, specialCategory1.description, specialCategory1.details, specialCategory1.priority);
        SpecialCategoryDto specialCategoryDto2 = new SpecialCategoryDto(specialCategory1.id, specialCategory1.code, specialCategory1.description, specialCategory1.details, specialCategory1.priority);
        assertEquals(specialCategoryDto1, specialCategoryDto2)
    }
    
    @Test
    public void testEquals_Different() {
        SpecialCategoryDto specialCategoryDto1 = new SpecialCategoryDto(specialCategory1.id, specialCategory1.code, specialCategory1.description, specialCategory1.details, specialCategory1.priority)
        SpecialCategoryDto specialCategoryDto2 = new SpecialCategoryDto(specialCategory2.id, specialCategory2.code, specialCategory2.description, specialCategory2.details, specialCategory2.priority)
        assertNotEquals(specialCategoryDto1, specialCategoryDto2)
    }
    
    @Test
    public void testHashCode_Same() {
        SpecialCategoryDto specialCategoryDto1 = new SpecialCategoryDto(specialCategory1.id, specialCategory1.code, specialCategory1.description, specialCategory1.details, specialCategory1.priority)
        SpecialCategoryDto specialCategoryDto2 = new SpecialCategoryDto(specialCategory1.id, specialCategory1.code, specialCategory1.description, specialCategory1.details, specialCategory1.priority)
        assertEquals(specialCategoryDto1.hashCode(), specialCategoryDto2.hashCode())
    }
    
    @Test
    public void testHashCode_Different() {
        SpecialCategoryDto specialCategoryDto1 = new SpecialCategoryDto(specialCategory1.id, specialCategory1.code, specialCategory1.description, specialCategory1.details, specialCategory1.priority)
        SpecialCategoryDto specialCategoryDto2 = new SpecialCategoryDto(specialCategory2.id, specialCategory2.code, specialCategory2.description, specialCategory2.details, specialCategory2.priority)
        assertNotEquals(specialCategoryDto1.hashCode(), specialCategoryDto2.hashCode())
    }
    
    @Test
    public void testConstructor_SpecialCategory() {
        SpecialCategoryDto specialCategoryDto = new SpecialCategoryDto(specialCategory1)
        assertEquals( specialCategoryDto.code, specialCategory1.code )
        assertEquals( specialCategoryDto.description, specialCategory1.description )
        assertEquals( specialCategoryDto.description, specialCategory1.description);
        assertEquals( specialCategoryDto.details, specialCategory1.details);
    }
}
