package uk.ac.reigate.dto;

import static org.junit.Assert.*
import org.junit.Before
import org.junit.Test

import uk.ac.reigate.domain.lookup.SchoolReportStatus


public class SchoolReportStatusDtoTest {
    
    private SchoolReportStatus schoolReportStatus1
    
    private SchoolReportStatus schoolReportStatus2
    
    private List<SchoolReportStatus> schoolReportStatuses
    
    @Before
    public void setup() {
        schoolReportStatus1 = new SchoolReportStatus(
                id: 1,
                code:'M',
                description:'Male'
                );
        schoolReportStatus2 = new SchoolReportStatus(
                id: 2,
                code:'F',
                description:'Female'
                );
        schoolReportStatuses = Arrays.asList(schoolReportStatus1, schoolReportStatus2);
    }
    
    @Test
    public void testMapFromSchoolReportStatusEntity(){
        SchoolReportStatusDto schoolReportStatusTest = SchoolReportStatusDto.mapFromSchoolReportStatusEntity( schoolReportStatus1 )
        assertEquals( schoolReportStatusTest.id, schoolReportStatus1.id );
        assertEquals( schoolReportStatusTest.code, schoolReportStatus1.code);
        assertEquals( schoolReportStatusTest.description, schoolReportStatus1.description);
    }
    
    @Test
    public void testMapFromSchoolReportStatusesEntities(){
        List<SchoolReportStatusDto> schoolReportStatussDtoTest = SchoolReportStatusDto.mapFromSchoolReportStatusesEntities( schoolReportStatuses )
        assertEquals( schoolReportStatussDtoTest[0].id, schoolReportStatus1.id );
        assertEquals( schoolReportStatussDtoTest[0].code, schoolReportStatus1.code);
        assertEquals( schoolReportStatussDtoTest[0].description, schoolReportStatus1.description);
        assertEquals( schoolReportStatussDtoTest[1].id, schoolReportStatus2.id );
        assertEquals( schoolReportStatussDtoTest[1].code, schoolReportStatus2.code);
        assertEquals( schoolReportStatussDtoTest[1].description, schoolReportStatus2.description);
    }
    
    @Test
    public void testMapToSchoolReportStatusEntity(){
        SchoolReportStatusDto schoolReportStatusDto = new SchoolReportStatusDto(schoolReportStatus1.id, schoolReportStatus1.code, schoolReportStatus1.description);
        SchoolReportStatus schoolReportStatus = SchoolReportStatusDto.mapToSchoolReportStatusEntity( schoolReportStatusDto );
        assertEquals( schoolReportStatus.id, schoolReportStatus1.id );
        assertEquals( schoolReportStatus.code, schoolReportStatus1.code);
        assertEquals( schoolReportStatus.description, schoolReportStatus1.description);
    }
    
    @Test
    public void testEquals_Same() {
        SchoolReportStatusDto schoolReportStatusDto1 = new SchoolReportStatusDto(schoolReportStatus1.id, schoolReportStatus1.code, schoolReportStatus1.description)
        SchoolReportStatusDto schoolReportStatusDto2 = new SchoolReportStatusDto(schoolReportStatus1.id, schoolReportStatus1.code, schoolReportStatus1.description)
        assertEquals(schoolReportStatusDto1, schoolReportStatusDto2)
    }
    
    @Test
    public void testEquals_Different() {
        SchoolReportStatusDto schoolReportStatusDto1 = new SchoolReportStatusDto(schoolReportStatus1.id, schoolReportStatus1.code, schoolReportStatus1.description)
        SchoolReportStatusDto schoolReportStatusDto2 = new SchoolReportStatusDto(schoolReportStatus2.id, schoolReportStatus2.code, schoolReportStatus2.description)
        assertNotEquals(schoolReportStatusDto1, schoolReportStatusDto2)
    }
    
    @Test
    public void testHashCode_Same() {
        SchoolReportStatusDto schoolReportStatusDto1 = new SchoolReportStatusDto(schoolReportStatus1.id, schoolReportStatus1.code, schoolReportStatus1.description)
        SchoolReportStatusDto schoolReportStatusDto2 = new SchoolReportStatusDto(schoolReportStatus1.id, schoolReportStatus1.code, schoolReportStatus1.description)
        assertEquals(schoolReportStatusDto1.hashCode(), schoolReportStatusDto2.hashCode())
    }
    
    @Test
    public void testHashCode_Different() {
        SchoolReportStatusDto schoolReportStatusDto1 = new SchoolReportStatusDto(schoolReportStatus1.id, schoolReportStatus1.code, schoolReportStatus1.description)
        SchoolReportStatusDto schoolReportStatusDto2 = new SchoolReportStatusDto(schoolReportStatus2.id, schoolReportStatus2.code, schoolReportStatus2.description)
        assertNotEquals(schoolReportStatusDto1.hashCode(), schoolReportStatusDto2.hashCode())
    }
    
    @Test
    public void testConstructor_SchoolReportStatus() {
        SchoolReportStatusDto schoolReportStatusDto = new SchoolReportStatusDto(schoolReportStatus1)
        assertEquals( schoolReportStatusDto.code, schoolReportStatus1.code )
        assertEquals( schoolReportStatusDto.description, schoolReportStatus1.description )
    }
}
