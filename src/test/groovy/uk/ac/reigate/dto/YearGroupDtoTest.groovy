package uk.ac.reigate.dto;

import static org.junit.Assert.*
import org.junit.Before

import org.junit.Test

import uk.ac.reigate.domain.lookup.YearGroup;



public class YearGroupDtoTest {
    
    
    private YearGroup yearGroup1
    
    private YearGroup yearGroup2
    
    private List<YearGroup> yearGroups
    
    @Before
    public void setup() {
        yearGroup1 = new YearGroup(
                id: 1,
                code:'L',
                description:'Lower Sixth'
                );
        yearGroup2 = new YearGroup(
                id: 2,
                code:'U',
                description:'Upper Sixth'
                );
        yearGroups = Arrays.asList(yearGroup1, yearGroup2);
    }
    
    @Test
    public void testMapFromYearGroupEntity(){
        YearGroupDto yearGroupTest = YearGroupDto.mapFromYearGroupEntity( yearGroup1 )
        assertEquals( yearGroupTest.id, yearGroup1.id );
        assertEquals( yearGroupTest.code, yearGroup1.code);
        assertEquals( yearGroupTest.description, yearGroup1.description);
    }
    
    @Test
    public void testMapFromYearGroupsEntities(){
        List<YearGroupDto> yearGroupsDtoTest = YearGroupDto.mapFromYearGroupsEntities( yearGroups )
        assertEquals( yearGroupsDtoTest[0].id, yearGroup1.id );
        assertEquals( yearGroupsDtoTest[0].code, yearGroup1.code);
        assertEquals( yearGroupsDtoTest[0].description, yearGroup1.description);
        assertEquals( yearGroupsDtoTest[1].id, yearGroup2.id );
        assertEquals( yearGroupsDtoTest[1].code, yearGroup2.code);
        assertEquals( yearGroupsDtoTest[1].description, yearGroup2.description);
    }
    
    @Test
    public void testMapToYearGroupEntity(){
        YearGroupDto yearGroupDto = new YearGroupDto(yearGroup1.id, yearGroup1.code, yearGroup1.description);
        YearGroup yearGroup = YearGroupDto.mapToYearGroupEntity( yearGroupDto );
        assertEquals( yearGroup.id, yearGroup1.id );
        assertEquals( yearGroup.code, yearGroup1.code);
        assertEquals( yearGroup.description, yearGroup1.description);
    }
    
    @Test
    public void testEquals_Same() {
        YearGroupDto yearGroupDto1 = new YearGroupDto(yearGroup1.id, yearGroup1.code, yearGroup1.description)
        YearGroupDto yearGroupDto2 = new YearGroupDto(yearGroup1.id, yearGroup1.code, yearGroup1.description)
        assertEquals(yearGroupDto1, yearGroupDto2)
    }
    
    @Test
    public void testEquals_Different() {
        YearGroupDto yearGroupDto1 = new YearGroupDto(yearGroup1.id, yearGroup1.code, yearGroup1.description)
        YearGroupDto yearGroupDto2 = new YearGroupDto(yearGroup2.id, yearGroup2.code, yearGroup2.description)
        assertNotEquals(yearGroupDto1, yearGroupDto2)
    }
    
    @Test
    public void testHashCode_Same() {
        YearGroupDto yearGroupDto1 = new YearGroupDto(yearGroup1.id, yearGroup1.code, yearGroup1.description)
        YearGroupDto yearGroupDto2 = new YearGroupDto(yearGroup1.id, yearGroup1.code, yearGroup1.description)
        assertEquals(yearGroupDto1.hashCode(), yearGroupDto2.hashCode())
    }
    
    @Test
    public void testHashCode_Different() {
        YearGroupDto yearGroupDto1 = new YearGroupDto(yearGroup1.id, yearGroup1.code, yearGroup1.description)
        YearGroupDto yearGroupDto2 = new YearGroupDto(yearGroup2.id, yearGroup2.code, yearGroup2.description)
        assertNotEquals(yearGroupDto1.hashCode(), yearGroupDto2.hashCode())
    }
    
    @Test
    public void testConstructor_YearGroup() {
        YearGroupDto yearGroupDto = new YearGroupDto(yearGroup1)
        assertEquals( yearGroupDto.code, yearGroup1.code )
        assertEquals( yearGroupDto.description, yearGroup1.description )
    }
}
