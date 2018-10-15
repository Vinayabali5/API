package uk.ac.reigate.dto;

import static org.junit.Assert.*
import org.junit.Before


import org.junit.Test

import uk.ac.reigate.domain.lookup.Title



public class TitleDtoTest {
    
    private Title title1
    
    private Title title2
    
    private List<Title> titles
    
    @Before
    public void setup() {
        title1 = new Title(
                id: 1,
                code:'Mr',
                description:'Mr'
                );
        title2 = new Title(
                id: 2,
                code:'Mrs',
                description:'Mrs'
                );
        titles = Arrays.asList(title1, title2);
    }
    
    @Test
    public void testMapFromTitleEntity(){
        TitleDto titleTest = TitleDto.mapFromTitleEntity( title1 )
        assertEquals( titleTest.id, title1.id );
        assertEquals( titleTest.code, title1.code);
        assertEquals( titleTest.description, title1.description);
    }
    
    @Test
    public void testMapFromTitlesEntities(){
        List<TitleDto> titlesDtoTest = TitleDto.mapFromTitlesEntities( titles )
        assertEquals( titlesDtoTest[0].id, title1.id );
        assertEquals( titlesDtoTest[0].code, title1.code);
        assertEquals( titlesDtoTest[0].description, title1.description);
        assertEquals( titlesDtoTest[1].id, title2.id );
        assertEquals( titlesDtoTest[1].code, title2.code);
        assertEquals( titlesDtoTest[1].description, title2.description);
    }
    
    @Test
    public void testMapToTitleEntity(){
        TitleDto titleDto = new TitleDto(title1.id, title1.code, title1.description);
        Title title = TitleDto.mapToTitleEntity( titleDto );
        assertEquals( title.id, title1.id );
        assertEquals( title.code, title1.code);
        assertEquals( title.description, title1.description);
    }
    
    @Test
    public void testEquals_Same() {
        TitleDto titleDto1 = new TitleDto(title1.id, title1.code, title1.description)
        TitleDto titleDto2 = new TitleDto(title1.id, title1.code, title1.description)
        assertEquals(titleDto1, titleDto2)
    }
    
    @Test
    public void testEquals_Different() {
        TitleDto titleDto1 = new TitleDto(title1.id, title1.code, title1.description)
        TitleDto titleDto2 = new TitleDto(title2.id, title2.code, title2.description)
        assertNotEquals(titleDto1, titleDto2)
    }
    
    @Test
    public void testHashCode_Same() {
        TitleDto titleDto1 = new TitleDto(title1.id, title1.code, title1.description)
        TitleDto titleDto2 = new TitleDto(title1.id, title1.code, title1.description)
        assertEquals(titleDto1.hashCode(), titleDto2.hashCode())
    }
    
    @Test
    public void testHashCode_Different() {
        TitleDto titleDto1 = new TitleDto(title1.id, title1.code, title1.description)
        TitleDto titleDto2 = new TitleDto(title2.id, title2.code, title2.description)
        assertNotEquals(titleDto1.hashCode(), titleDto2.hashCode())
    }
    
    @Test
    public void testConstructor_Title() {
        TitleDto titleDto = new TitleDto(title1)
        assertEquals( titleDto.code, title1.code )
        assertEquals( titleDto.description, title1.description )
    }
}
