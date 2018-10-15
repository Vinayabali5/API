package uk.ac.reigate.dto;

import static org.junit.Assert.*

import org.junit.Before
import org.junit.Test

import uk.ac.reigate.domain.ilr.CompletionStatus

public class CompletionStatusDtoTest {
    
    private CompletionStatus completionStatus1
    
    private CompletionStatus completionStatus2
    
    private List<CompletionStatus> completionStatuses
    
    @Before
    public void setup() {
        this.completionStatus1 = new CompletionStatus(
                id: 1,
                code: 'UK',
                description: 'United Kingdom',
                shortDescription:'United Kingdom',
                validFrom: new Date().parse('yyyy/MM/dd', '2011/07/09'),
                validTo: new Date().parse('yyyy/MM/dd', '2013/07/09')
                );
        this.completionStatus2 = new CompletionStatus(
                id: 2,
                code: 'EU',
                description: 'European Ecconomical Union',
                shortDescription:'European Union',
                validFrom: new Date().parse('yyyy/MM/dd', '2011/07/09'),
                validTo: new Date().parse('yyyy/MM/dd', '2013/07/09')
                );
        this.completionStatuses = Arrays.asList(this.completionStatus1, this.completionStatus2);
    }
    
    @Test
    void testConstructor_completionStatus() {
        CompletionStatusDto completionStatusTest = new CompletionStatusDto( completionStatus1 )
        assertEquals( completionStatusTest.id, completionStatus1.id);
        assertEquals( completionStatusTest.code, completionStatus1.code);
        assertEquals( completionStatusTest.description, completionStatus1.description);
        assertEquals( completionStatusTest.shortDescription, completionStatus1.shortDescription);
        assertEquals( completionStatusTest.validFrom, completionStatus1.validFrom);
        assertEquals( completionStatusTest.validTo, completionStatus1.validTo);
    }
    
    @Test
    public void testMapFromCompletionStatusEntityTest() {
        CompletionStatusDto completionStatusTest = CompletionStatusDto.mapFromCompletionStatusEntity( completionStatus1 )
        assertEquals( completionStatusTest.id, completionStatus1.id);
        assertEquals( completionStatusTest.code, completionStatus1.code);
        assertEquals( completionStatusTest.description, completionStatus1.description);
        assertEquals( completionStatusTest.shortDescription, completionStatus1.shortDescription);
        assertEquals( completionStatusTest.validFrom, completionStatus1.validFrom);
        assertEquals( completionStatusTest.validTo, completionStatus1.validTo);
    }
    
    @Test
    public void testMapFromCompletionStatusesEntitiesTest(){
        List<CompletionStatusDto> completionStatusesDtoTest = CompletionStatusDto.mapFromCompletionStatusesEntities( this.completionStatuses)
        assertEquals( completionStatusesDtoTest[0].id, completionStatus1.id );
        assertEquals( completionStatusesDtoTest[0].code, completionStatus1.code );
        assertEquals( completionStatusesDtoTest[0].description, completionStatus1.description);
        assertEquals( completionStatusesDtoTest[0].shortDescription, completionStatus1.shortDescription);
        assertEquals( completionStatusesDtoTest[0].validFrom, completionStatus1.validFrom);
        assertEquals( completionStatusesDtoTest[0].validTo, completionStatus1.validTo);
        assertEquals( completionStatusesDtoTest[1].id, completionStatus2.id );
        assertEquals( completionStatusesDtoTest[1].code, completionStatus2.code );
        assertEquals( completionStatusesDtoTest[1].description, completionStatus2.description);
        assertEquals( completionStatusesDtoTest[1].shortDescription, completionStatus2.shortDescription);
        assertEquals( completionStatusesDtoTest[1].validFrom, completionStatus2.validFrom);
        assertEquals( completionStatusesDtoTest[1].validTo, completionStatus2.validTo);
    }
    
    @Test
    public void testMapToCompletionStatusEntityTest(){
        CompletionStatusDto completionStatusDto = new CompletionStatusDto(completionStatus1.id, completionStatus1.code, completionStatus1.description, completionStatus1.shortDescription, completionStatus1.validFrom, completionStatus1.validTo)
        CompletionStatus completionStatus = CompletionStatusDto.mapToCompletionStatusEntity( completionStatusDto )
        assertEquals( completionStatus.id, completionStatus1.id );
        assertEquals( completionStatus.code, completionStatus1.code);
        assertEquals( completionStatus.description, completionStatus1.description);
        assertEquals( completionStatus.shortDescription, completionStatus1.shortDescription);
        assertEquals( completionStatus.validFrom, completionStatus1.validFrom);
        assertEquals( completionStatus.validTo, completionStatus1.validTo);
    }
    
    @Test
    public void testEquals_Same() {
        CompletionStatusDto completionStatusDto1 = new CompletionStatusDto(completionStatus1.id, completionStatus1.code, completionStatus1.description, completionStatus1.shortDescription, completionStatus1.validFrom, completionStatus1.validTo)
        CompletionStatusDto completionStatusDto2 = new CompletionStatusDto(completionStatus1.id, completionStatus1.code, completionStatus1.description, completionStatus1.shortDescription, completionStatus1.validFrom, completionStatus1.validTo)
        assertEquals(completionStatusDto1, completionStatusDto2)
    }
    
    @Test
    public void testEquals_Different() {
        CompletionStatusDto completionStatusDto1 = new CompletionStatusDto(completionStatus1.id, completionStatus1.code, completionStatus1.description, completionStatus1.shortDescription, completionStatus1.validFrom, completionStatus1.validTo)
        CompletionStatusDto completionStatusDto2 = new CompletionStatusDto(completionStatus2.id, completionStatus2.code, completionStatus2.description, completionStatus2.shortDescription, completionStatus2.validFrom, completionStatus2.validTo)
        assertNotEquals(completionStatusDto1, completionStatusDto2)
    }
    
    @Test
    public void testHashCode_Same() {
        CompletionStatusDto completionStatusDto1 = new CompletionStatusDto(completionStatus1.id, completionStatus1.code, completionStatus1.description, completionStatus2.shortDescription, completionStatus1.validFrom, completionStatus1.validTo)
        CompletionStatusDto completionStatusDto2 = new CompletionStatusDto(completionStatus1.id, completionStatus1.code, completionStatus1.description, completionStatus2.shortDescription, completionStatus1.validFrom, completionStatus1.validTo)
        assertEquals(completionStatusDto1.hashCode(), completionStatusDto2.hashCode())
    }
    
    @Test
    public void testHashCode_Different() {
        CompletionStatusDto completionStatusDto1 = new CompletionStatusDto(completionStatus1.id, completionStatus1.code, completionStatus1.description, completionStatus1.shortDescription, completionStatus1.validFrom, completionStatus1.validTo)
        CompletionStatusDto completionStatusDto2 = new CompletionStatusDto(completionStatus2.id, completionStatus2.code, completionStatus2.description, completionStatus2.shortDescription, completionStatus2.validFrom, completionStatus2.validTo)
        assertNotEquals(completionStatusDto1.hashCode(), completionStatusDto2.hashCode())
    }
    
    @Test
    public void testConstructor_CompletionStatus() {
        CompletionStatusDto completionStatusDto = new CompletionStatusDto(completionStatus1)
        assertEquals( completionStatusDto.code, completionStatus1.code )
        assertEquals( completionStatusDto.description, completionStatus1.description )
        assertEquals( completionStatusDto.shortDescription, completionStatus1.shortDescription )
        assertEquals( completionStatusDto.validFrom, completionStatus1.validFrom )
        assertEquals( completionStatusDto.validTo, completionStatus1.validTo )
    }
}
