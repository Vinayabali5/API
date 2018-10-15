package uk.ac.reigate.dto;

import static org.junit.Assert.*
import org.junit.Before
import java.util.List;

import org.junit.Before;
import org.junit.Test

import uk.ac.reigate.domain.lookup.Nationality;


public class NationalityDtoTest {
    
    private Nationality nationality1
    
    private Nationality nationality2
    
    private List<Nationality> nationalitys
    
    @Before
    public void setup() {
        nationality1 = new Nationality(
                id: 1,
                name:'UK',
                description:'United kingdom'
                );
        nationality2 = new Nationality(
                id: 2,
                name:'IN',
                description:'India'
                );
        nationalitys = Arrays.asList(nationality1, nationality2);
    }
    
    @Test
    public void mapFromNationalityEntityTest(){
        NationalityDto nationalityTest = NationalityDto.mapFromNationalityEntity( nationality1 )
        assertEquals( nationalityTest.id, nationality1.id );
        assertEquals( nationalityTest.name, nationality1.name);
        assertEquals( nationalityTest.description, nationality1.description);
    }
    
    @Test
    public void mapFromNationalitiesEntitiesTest(){
        List<NationalityDto> nationalityTest = NationalityDto.mapFromNationalitiesEntities( nationalitys )
        assertEquals( nationalityTest[0].id, nationality1.id );
        assertEquals( nationalityTest[0].name, nationality1.name);
        assertEquals( nationalityTest[0].description, nationality1.description);
        assertEquals( nationalityTest[1].id, nationality2.id );
        assertEquals( nationalityTest[1].name, nationality2.name);
        assertEquals( nationalityTest[1].description, nationality2.description);
    }
    
    @Test
    public void mapToNationalityEntityTest(){
        NationalityDto nationalityDto = new NationalityDto(nationality1.id, nationality1.name, nationality1.description);
        Nationality nationality = NationalityDto.mapToNationalityEntity( nationalityDto );
        assertEquals( nationality.id, nationality1.id );
        assertEquals( nationality.name, nationality1.name);
        assertEquals( nationality.description, nationality1.description);
    }
    
    @Test
    public void testEquals_Same() {
        NationalityDto nationalityDto1 = new NationalityDto(nationality1.id, nationality1.name, nationality1.description)
        NationalityDto nationalityDto2 = new NationalityDto(nationality1.id, nationality1.name, nationality1.description)
        assertEquals(nationalityDto1, nationalityDto2)
    }
    
    @Test
    public void testEquals_Different() {
        NationalityDto nationalityDto1 = new NationalityDto(nationality1.id, nationality1.name, nationality1.description)
        NationalityDto nationalityDto2 = new NationalityDto(nationality2.id, nationality2.name, nationality2.description)
        assertNotEquals(nationalityDto1, nationalityDto2)
    }
    
    @Test
    public void testHashCode_Same() {
        NationalityDto nationalityDto1 = new NationalityDto(nationality1.id, nationality1.name, nationality1.description)
        NationalityDto nationalityDto2 = new NationalityDto(nationality1.id, nationality1.name, nationality1.description)
        assertEquals(nationalityDto1.hashCode(), nationalityDto2.hashCode())
    }
    
    @Test
    public void testHashCode_Different() {
        NationalityDto nationalityDto1 = new NationalityDto(nationality1.id, nationality1.name, nationality1.description)
        NationalityDto nationalityDto2 = new NationalityDto(nationality2.id, nationality2.name, nationality2.description)
        assertNotEquals(nationalityDto1.hashCode(), nationalityDto2.hashCode())
    }
    
    @Test
    public void testConstructor_Nationality() {
        NationalityDto nationalityDto = new NationalityDto(nationality1)
        assertEquals( nationalityDto.name, nationality1.name )
        assertEquals( nationalityDto.description, nationality1.description )
    }
}
