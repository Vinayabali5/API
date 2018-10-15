package uk.ac.reigate.dto;


import static org.junit.Assert.*
import org.junit.Before
import org.junit.Test

import uk.ac.reigate.domain.admissions.CollegeFundPaid



public class CollegeFundPaidDtoTest {
    
    private CollegeFundPaid collegeFundPaid1
    
    private CollegeFundPaid collegeFundPaid2
    
    private List<CollegeFundPaid> collegeFundPaids
    
    @Before
    public void setup() {
        this.collegeFundPaid1 = new CollegeFundPaid(
                id: 1,
                collegeFundPaid : 'Yes'
                );
        this.collegeFundPaid2 = new CollegeFundPaid(
                id: 1,
                collegeFundPaid : 'No'
                );
        this.collegeFundPaids = Arrays.asList(collegeFundPaid1,collegeFundPaid2);
    }
    
    @Test
    void testConstructor_collegeFundPaid() {
        CollegeFundPaidDto collegeFundPaidTest = new CollegeFundPaidDto( collegeFundPaid1 )
        assertEquals( collegeFundPaidTest.id, collegeFundPaid1.id );
        assertEquals( collegeFundPaidTest.collegeFundPaid, collegeFundPaid1.collegeFundPaid);
    }
    
    @Test
    public void testMapFromCollegeFundPaidEntity(){
        CollegeFundPaidDto collegeFundPaidTest = CollegeFundPaidDto.mapFromCollegeFundPaidEntity( collegeFundPaid1 )
        assertEquals( collegeFundPaidTest.id, collegeFundPaid1.id );
        assertEquals( collegeFundPaidTest.collegeFundPaid, collegeFundPaid1.collegeFundPaid);
    }
    
    @Test
    public void testMapFromCollegeFundPaidsEntities(){
        List<CollegeFundPaidDto> collegeFundPaidTest = CollegeFundPaidDto.mapFromCollegeFundPaidsEntities( this.collegeFundPaids )
        assertEquals( collegeFundPaidTest[0].id, collegeFundPaid1.id );
        assertEquals( collegeFundPaidTest[0].collegeFundPaid, collegeFundPaid1.collegeFundPaid );
        assertEquals( collegeFundPaidTest[1].id, collegeFundPaid2.id );
        assertEquals( collegeFundPaidTest[1].collegeFundPaid, collegeFundPaid2.collegeFundPaid );
    }
    
    @Test
    public void testMapToCollegeFundPaidEntity(){
        CollegeFundPaidDto collegeFundPaidDto = new CollegeFundPaidDto(collegeFundPaid1.id, collegeFundPaid1.collegeFundPaid);
        CollegeFundPaid collegeFundPaid = CollegeFundPaidDto.mapToCollegeFundPaidEntity( collegeFundPaidDto );
        assertEquals( collegeFundPaid.id, collegeFundPaid1.id );
        assertEquals( collegeFundPaid.collegeFundPaid, collegeFundPaid1.collegeFundPaid);
    }
    
    @Test
    public void testEquals_Same() {
        CollegeFundPaidDto collegeFundPaidDto1 = new CollegeFundPaidDto(collegeFundPaid1.id, collegeFundPaid1.collegeFundPaid)
        CollegeFundPaidDto collegeFundPaidDto2 = new CollegeFundPaidDto(collegeFundPaid1.id, collegeFundPaid1.collegeFundPaid)
        assertEquals( collegeFundPaidDto1, collegeFundPaidDto2)
    }
    
    @Test
    public void testEquals_Different() {
        CollegeFundPaidDto collegeFundPaidDto1 = new CollegeFundPaidDto(collegeFundPaid1.id, collegeFundPaid1.collegeFundPaid)
        CollegeFundPaidDto collegeFundPaidDto2 = new CollegeFundPaidDto(collegeFundPaid2.id, collegeFundPaid2.collegeFundPaid)
        assertNotEquals( collegeFundPaidDto1, collegeFundPaidDto2)
    }
    
    @Test
    public void testHashCode_Same() {
        CollegeFundPaidDto collegeFundPaidDto1 = new CollegeFundPaidDto(collegeFundPaid1.id, collegeFundPaid1.collegeFundPaid)
        CollegeFundPaidDto collegeFundPaidDto2 = new CollegeFundPaidDto(collegeFundPaid1.id, collegeFundPaid1.collegeFundPaid)
        assertEquals( collegeFundPaidDto1.hashCode(), collegeFundPaidDto2.hashCode())
    }
    
    @Test
    public void testHashCode_Different() {
        CollegeFundPaidDto collegeFundPaidDto1 = new CollegeFundPaidDto(collegeFundPaid1.id, collegeFundPaid1.collegeFundPaid)
        CollegeFundPaidDto collegeFundPaidDto2 = new CollegeFundPaidDto(collegeFundPaid2.id, collegeFundPaid2.collegeFundPaid)
        assertNotEquals( collegeFundPaidDto1.hashCode(), collegeFundPaidDto2.hashCode())
    }
    
    @Test
    public void testConstructor_CollegeFundPaid() {
        CollegeFundPaidDto collegeFundPaidDto = new CollegeFundPaidDto(collegeFundPaid1)
        assertEquals( collegeFundPaidDto.id, collegeFundPaid1.id )
        assertEquals( collegeFundPaidDto.collegeFundPaid, collegeFundPaid1.collegeFundPaid)
    }
}
