package uk.ac.reigate.dto;

import static org.junit.Assert.*
import org.junit.Before
import org.junit.Test

import uk.ac.reigate.domain.admissions.ApplicationStatus



public class ApplicationStatusDtoTest {
    
    private ApplicationStatus applicationStatus1
    
    private ApplicationStatus applicationStatus2
    
    private List<ApplicationStatus> applicationStatuses
    
    @Before
    public void setup() {
        this.applicationStatus1 =  new ApplicationStatus(
                id: 1,
                code:'N',
                description:'New',
                considerWithdrawn: false
                );
        this.applicationStatus2 =  new ApplicationStatus(
                id: 2,
                code:'C',
                description:'Completed',
                considerWithdrawn: true
                );
        this.applicationStatuses = Arrays.asList(this.applicationStatus1, this.applicationStatus2);
    }
    
    @Test
    void testConstructor_academicYear() {
        ApplicationStatusDto applicationStatusTest = new ApplicationStatusDto( applicationStatus1 )
        assertEquals( applicationStatusTest.id, applicationStatus1.id );
        assertEquals( applicationStatusTest.code, applicationStatus1.code);
        assertEquals( applicationStatusTest.description, applicationStatus1.description);
        assertEquals( applicationStatusTest.considerWithdrawn, applicationStatus1.considerWithdrawn);
    }
    
    @Test
    public void testMapFromApplicationStatusEntity(){
        ApplicationStatusDto applicationStatusTest = ApplicationStatusDto.mapFromApplicationStatusEntity( applicationStatus1 )
        assertEquals( applicationStatusTest.id, applicationStatus1.id );
        assertEquals( applicationStatusTest.code, applicationStatus1.code);
        assertEquals( applicationStatusTest.description, applicationStatus1.description);
        assertEquals( applicationStatusTest.considerWithdrawn, applicationStatus1.considerWithdrawn);
    }
    
    @Test
    public void testMapFromApplicationStatusesEntities(){
        List<ApplicationStatusDto> applicationStatusesTest = ApplicationStatusDto.mapFromApplicationStatusesEntities( this.applicationStatuses )
        assertEquals( applicationStatusesTest[0].id, applicationStatus1.id );
        assertEquals( applicationStatusesTest[0].code, applicationStatus1.code );
        assertEquals( applicationStatusesTest[0].description, applicationStatus1.description);
        assertEquals( applicationStatusesTest[0].considerWithdrawn, applicationStatus1.considerWithdrawn);
        assertEquals( applicationStatusesTest[1].id, applicationStatus2.id );
        assertEquals( applicationStatusesTest[1].code, applicationStatus2.code );
        assertEquals( applicationStatusesTest[1].description, applicationStatus2.description);
        assertEquals( applicationStatusesTest[1].considerWithdrawn, applicationStatus2.considerWithdrawn);
    }
    
    @Test
    public void testMapToApplicationStatusEntity(){
        ApplicationStatusDto applicationStatusDto = new ApplicationStatusDto(applicationStatus1.id, applicationStatus1.code, applicationStatus1.description, applicationStatus1.considerWithdrawn);
        ApplicationStatus applicationStatus = ApplicationStatusDto.mapToApplicationStatusEntity( applicationStatusDto );
        assertEquals( applicationStatus.id, applicationStatus1.id );
        assertEquals( applicationStatus.code, applicationStatus1.code);
        assertEquals( applicationStatus.description, applicationStatus1.description);
        assertEquals( applicationStatus.considerWithdrawn, applicationStatus1.considerWithdrawn);
    }
    
    @Test
    public void testEquals_Same() {
        ApplicationStatusDto applicationStatusDto1 = new ApplicationStatusDto(applicationStatus1.id, applicationStatus1.code, applicationStatus1.description, applicationStatus1.considerWithdrawn)
        ApplicationStatusDto applicationStatusDto2 = new ApplicationStatusDto(applicationStatus1.id, applicationStatus1.code, applicationStatus1.description, applicationStatus1.considerWithdrawn)
        assertEquals(applicationStatusDto1, applicationStatusDto2)
    }
    
    @Test
    public void testEquals_Different() {
        ApplicationStatusDto applicationStatusDto1 = new ApplicationStatusDto(applicationStatus1.id, applicationStatus1.code, applicationStatus1.description, applicationStatus1.considerWithdrawn)
        ApplicationStatusDto applicationStatusDto2 = new ApplicationStatusDto(applicationStatus2.id, applicationStatus2.code, applicationStatus2.description, applicationStatus2.considerWithdrawn)
        assertNotEquals(applicationStatusDto1, applicationStatusDto2)
    }
    
    @Test
    public void testHashCode_Same() {
        ApplicationStatusDto applicationStatusDto1 = new ApplicationStatusDto(applicationStatus1.id, applicationStatus1.code, applicationStatus1.description, applicationStatus1.considerWithdrawn)
        ApplicationStatusDto applicationStatusDto2 = new ApplicationStatusDto(applicationStatus1.id, applicationStatus1.code, applicationStatus1.description, applicationStatus1.considerWithdrawn)
        assertEquals(applicationStatusDto1.hashCode(), applicationStatusDto2.hashCode())
    }
    
    @Test
    public void testHashCode_Different() {
        ApplicationStatusDto applicationStatusDto1 = new ApplicationStatusDto(applicationStatus1.id, applicationStatus1.code, applicationStatus1.description, applicationStatus1.considerWithdrawn)
        ApplicationStatusDto applicationStatusDto2 = new ApplicationStatusDto(applicationStatus2.id, applicationStatus2.code, applicationStatus2.description, applicationStatus2.considerWithdrawn)
        assertNotEquals(applicationStatusDto1.hashCode(), applicationStatusDto2.hashCode())
    }
    
    @Test
    public void testConstructor_ApplicationStatus() {
        ApplicationStatusDto applicationStatusDto = new ApplicationStatusDto(applicationStatus1)
        assertEquals( applicationStatusDto.code, applicationStatus1.code )
        assertEquals( applicationStatusDto.description, applicationStatus1.description )
        assertEquals( applicationStatusDto.considerWithdrawn, applicationStatus1.considerWithdrawn )
    }
}
