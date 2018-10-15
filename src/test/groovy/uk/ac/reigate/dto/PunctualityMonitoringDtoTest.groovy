package uk.ac.reigate.dto;

import static org.junit.Assert.*
import org.junit.Before
import org.junit.Test

import uk.ac.reigate.domain.lookup.PunctualityMonitoring


public class PunctualityMonitoringDtoTest {
    
    private PunctualityMonitoring punctualityMonitoring1
    
    private PunctualityMonitoring punctualityMonitoring2
    
    private List<PunctualityMonitoring> punctualityMonitorings
    
    @Before
    public void setup() {
        this.punctualityMonitoring1 = new PunctualityMonitoring(
                id: 1,
                code:'A',
                description:'Absent',
                warningColour: 'red'
                
                );
        this.punctualityMonitoring2 = new PunctualityMonitoring(
                id: 2,
                code:'P',
                description:'Present',
                warningColour: 'blue'
                );
        punctualityMonitorings = Arrays.asList(this.punctualityMonitoring1, this.punctualityMonitoring2);
    }
    
    @Test
    public void testMapFromPunctualityMonitoringEntity(){
        PunctualityMonitoringDto punctualityMonitoringTest = PunctualityMonitoringDto.mapFromPunctualityMonitoringEntity( punctualityMonitoring1 )
        assertEquals( punctualityMonitoringTest.id, punctualityMonitoring1.id );
        assertEquals( punctualityMonitoringTest.code, punctualityMonitoring1.code);
        assertEquals( punctualityMonitoringTest.description, punctualityMonitoring1.description);
    }
    
    @Test
    public void testMapFromPunctualityMonitoringsEntities(){
        List<PunctualityMonitoringDto> punctualityMonitoringsDtoTest = PunctualityMonitoringDto.mapFromPunctualityMonitoringsEntities( this.punctualityMonitorings )
        assertEquals( punctualityMonitoringsDtoTest[0].id, punctualityMonitoring1.id );
        assertEquals( punctualityMonitoringsDtoTest[0].code, punctualityMonitoring1.code);
        assertEquals( punctualityMonitoringsDtoTest[0].description, punctualityMonitoring1.description);
        assertEquals( punctualityMonitoringsDtoTest[1].id, punctualityMonitoring2.id );
        assertEquals( punctualityMonitoringsDtoTest[1].code, punctualityMonitoring2.code);
        assertEquals( punctualityMonitoringsDtoTest[1].description, punctualityMonitoring2.description);
    }
    
    @Test
    public void testMapToPunctualityMonitoringEntity(){
        PunctualityMonitoringDto punctualityMonitoringDto = new PunctualityMonitoringDto(punctualityMonitoring1.id, punctualityMonitoring1.code, punctualityMonitoring1.description, punctualityMonitoring1.warningColour);
        PunctualityMonitoring punctualityMonitoring = PunctualityMonitoringDto.mapToPunctualityMonitoringEntity( punctualityMonitoringDto );
        assertEquals( punctualityMonitoring.id, punctualityMonitoring1.id );
        assertEquals( punctualityMonitoring.code, punctualityMonitoring1.code);
        assertEquals( punctualityMonitoring.description, punctualityMonitoring1.description);
    }
    
    @Test
    public void testEquals_Same() {
        PunctualityMonitoringDto punctualityMonitoringDto1 = new PunctualityMonitoringDto(punctualityMonitoring1.id, punctualityMonitoring1.code, punctualityMonitoring1.description, punctualityMonitoring1.warningColour)
        PunctualityMonitoringDto punctualityMonitoringDto2 = new PunctualityMonitoringDto(punctualityMonitoring1.id, punctualityMonitoring1.code, punctualityMonitoring1.description, punctualityMonitoring1.warningColour)
        assertEquals( punctualityMonitoringDto1, punctualityMonitoringDto2)
    }
    
    @Test
    public void testEquals_Different() {
        PunctualityMonitoringDto punctualityMonitoringDto1 = new PunctualityMonitoringDto(punctualityMonitoring1.id, punctualityMonitoring1.code, punctualityMonitoring1.description, punctualityMonitoring1.warningColour)
        PunctualityMonitoringDto punctualityMonitoringDto2 = new PunctualityMonitoringDto(punctualityMonitoring2.id, punctualityMonitoring2.code, punctualityMonitoring2.description, punctualityMonitoring2.warningColour)
        assertNotEquals( punctualityMonitoringDto1, punctualityMonitoringDto2)
    }
    
    @Test
    public void testHashCode_Same() {
        PunctualityMonitoringDto punctualityMonitoringDto1 = new PunctualityMonitoringDto(punctualityMonitoring1.id, punctualityMonitoring1.code, punctualityMonitoring1.description, punctualityMonitoring1.warningColour)
        PunctualityMonitoringDto punctualityMonitoringDto2 = new PunctualityMonitoringDto(punctualityMonitoring1.id, punctualityMonitoring1.code, punctualityMonitoring1.description, punctualityMonitoring1.warningColour)
        assertEquals( punctualityMonitoringDto1.hashCode(), punctualityMonitoringDto2.hashCode())
    }
    
    @Test
    public void testHashCode_Different() {
        PunctualityMonitoringDto punctualityMonitoringDto1 = new PunctualityMonitoringDto(punctualityMonitoring1.id, punctualityMonitoring1.code, punctualityMonitoring1.description, punctualityMonitoring1.warningColour)
        PunctualityMonitoringDto punctualityMonitoringDto2 = new PunctualityMonitoringDto(punctualityMonitoring2.id, punctualityMonitoring2.code, punctualityMonitoring2.description, punctualityMonitoring2.warningColour)
        assertNotEquals( punctualityMonitoringDto1.hashCode(), punctualityMonitoringDto2.hashCode())
    }
    
    @Test
    public void testConstructor_PunctualityMonitoring() {
        PunctualityMonitoringDto punctualityMonitoringDto = new PunctualityMonitoringDto(punctualityMonitoring1)
        assertEquals( punctualityMonitoringDto.code, punctualityMonitoring1.code )
        assertEquals( punctualityMonitoringDto.description, punctualityMonitoring1.description )
        assertEquals( punctualityMonitoringDto.warningColour, punctualityMonitoring1.warningColour )
    }
}
