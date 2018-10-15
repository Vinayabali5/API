package uk.ac.reigate.dto;

import static org.junit.Assert.*
import org.junit.Before
import org.junit.Test

import uk.ac.reigate.domain.lookup.Gender


public class GenderDtoTest {
    
    private Gender gender1
    
    private Gender gender2
    
    private List<Gender> genders
    
    @Before
    public void setup() {
        gender1 = new Gender(
                id: 1,
                code:'M',
                description:'Male'
                );
        gender2 = new Gender(
                id: 2,
                code:'F',
                description:'Female'
                );
        genders = Arrays.asList(gender1, gender2);
    }
    
    @Test
    public void testMapFromGenderEntity(){
        GenderDto genderTest = GenderDto.mapFromGenderEntity( gender1 )
        assertEquals( genderTest.id, gender1.id );
        assertEquals( genderTest.code, gender1.code);
        assertEquals( genderTest.description, gender1.description);
    }
    
    @Test
    public void testMapFromGendersEntities(){
        List<GenderDto> gendersDtoTest = GenderDto.mapFromGendersEntities( genders )
        assertEquals( gendersDtoTest[0].id, gender1.id );
        assertEquals( gendersDtoTest[0].code, gender1.code);
        assertEquals( gendersDtoTest[0].description, gender1.description);
        assertEquals( gendersDtoTest[1].id, gender2.id );
        assertEquals( gendersDtoTest[1].code, gender2.code);
        assertEquals( gendersDtoTest[1].description, gender2.description);
    }
    
    @Test
    public void testMapToGenderEntity(){
        GenderDto genderDto = new GenderDto(gender1.id, gender1.code, gender1.description);
        Gender gender = GenderDto.mapToGenderEntity( genderDto );
        assertEquals( gender.id, gender1.id );
        assertEquals( gender.code, gender1.code);
        assertEquals( gender.description, gender1.description);
    }
    
    @Test
    public void testEquals_Same() {
        GenderDto genderDto1 = new GenderDto(gender1.id, gender1.code, gender1.description)
        GenderDto genderDto2 = new GenderDto(gender1.id, gender1.code, gender1.description)
        assertEquals(genderDto1, genderDto2)
    }
    
    @Test
    public void testEquals_Different() {
        GenderDto genderDto1 = new GenderDto(gender1.id, gender1.code, gender1.description)
        GenderDto genderDto2 = new GenderDto(gender2.id, gender2.code, gender2.description)
        assertNotEquals(genderDto1, genderDto2)
    }
    
    @Test
    public void testHashCode_Same() {
        GenderDto genderDto1 = new GenderDto(gender1.id, gender1.code, gender1.description)
        GenderDto genderDto2 = new GenderDto(gender1.id, gender1.code, gender1.description)
        assertEquals(genderDto1.hashCode(), genderDto2.hashCode())
    }
    
    @Test
    public void testHashCode_Different() {
        GenderDto genderDto1 = new GenderDto(gender1.id, gender1.code, gender1.description)
        GenderDto genderDto2 = new GenderDto(gender2.id, gender2.code, gender2.description)
        assertNotEquals(genderDto1.hashCode(), genderDto2.hashCode())
    }
    
    @Test
    public void testConstructor_Gender() {
        GenderDto genderDto = new GenderDto(gender1)
        assertEquals( genderDto.code, gender1.code )
        assertEquals( genderDto.description, gender1.description )
    }
}
