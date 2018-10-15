package uk.ac.reigate.dto;

import static org.junit.Assert.*
import org.junit.Before
import org.junit.Test

import uk.ac.reigate.domain.lookup.CentralMonitoring


public class CentralMonitoringDtoTest {
    
    private CentralMonitoring centralMonitoring1
    
    private CentralMonitoring centralMonitoring2
    
    private List<CentralMonitoring> centralMonitorings
    
    @Before
    public void setup() {
        this.centralMonitoring1 = new CentralMonitoring(
                id: 1,
                code:'G',
                description:'Good',
                warningColour: 'red'
                );
        this.centralMonitoring2 = new CentralMonitoring(
                id: 2,
                code:'B',
                description:'Bad',
                warningColour: 'red'
                );
        this.centralMonitorings = Arrays.asList(centralMonitoring1, centralMonitoring2);
    }
    
    @Test
    void testConstructor_centralMonitoring() {
        CentralMonitoringDto centralMonitoringTest = new CentralMonitoringDto( centralMonitoring1 )
        assertEquals( centralMonitoringTest.id, centralMonitoring1.id );
        assertEquals( centralMonitoringTest.code, centralMonitoring1.code);
        assertEquals( centralMonitoringTest.description, centralMonitoring1.description);
        assertEquals( centralMonitoringTest.warningColour, centralMonitoring1.warningColour);
    }
    
    @Test
    public void testMapFromCentralMonitoringEntity(){
        CentralMonitoringDto centralMonitoringTest = CentralMonitoringDto.mapFromCentralMonitoringEntity( centralMonitoring1 )
        assertEquals( centralMonitoringTest.id, centralMonitoring1.id );
        assertEquals( centralMonitoringTest.code, centralMonitoring1.code);
        assertEquals( centralMonitoringTest.description, centralMonitoring1.description);
        assertEquals( centralMonitoringTest.warningColour, centralMonitoring1.warningColour);
    }
    
    @Test
    public void testMapFromCentralMonitoringsEntities(){
        List<CentralMonitoringDto> centralMonitoringsDtoTest = CentralMonitoringDto.mapFromCentralMonitoringsEntities( centralMonitorings )
        assertEquals( centralMonitoringsDtoTest[0].id, centralMonitoring1.id );
        assertEquals( centralMonitoringsDtoTest[0].code, centralMonitoring1.code);
        assertEquals( centralMonitoringsDtoTest[0].description, centralMonitoring1.description);
        assertEquals( centralMonitoringsDtoTest[1].id, centralMonitoring2.id );
        assertEquals( centralMonitoringsDtoTest[1].code, centralMonitoring2.code);
        assertEquals( centralMonitoringsDtoTest[1].description, centralMonitoring2.description);
    }
    
    @Test
    public void testMapToCentralMonitoringEntity(){
        CentralMonitoringDto centralMonitoringDto = new CentralMonitoringDto(centralMonitoring1.id, centralMonitoring1.code, centralMonitoring1.description, centralMonitoring1.warningColour);
        CentralMonitoring centralMonitoring = CentralMonitoringDto.mapToCentralMonitoringEntity( centralMonitoringDto );
        assertEquals( centralMonitoring.id, centralMonitoring1.id );
        assertEquals( centralMonitoring.code, centralMonitoring1.code);
        assertEquals( centralMonitoring.description, centralMonitoring1.description);
    }
    
    @Test
    public void testEquals_Same() {
        CentralMonitoringDto centralMonitoringDto1 = new CentralMonitoringDto(centralMonitoring1.id, centralMonitoring1.code, centralMonitoring1.description, centralMonitoring1.warningColour)
        CentralMonitoringDto centralMonitoringDto2 = new CentralMonitoringDto(centralMonitoring1.id, centralMonitoring1.code, centralMonitoring1.description, centralMonitoring1.warningColour)
        assertEquals( centralMonitoringDto1, centralMonitoringDto2)
    }
    
    @Test
    public void testEquals_Different() {
        CentralMonitoringDto centralMonitoringDto1 = new CentralMonitoringDto(centralMonitoring1.id, centralMonitoring1.code, centralMonitoring1.description, centralMonitoring1.warningColour)
        CentralMonitoringDto centralMonitoringDto2 = new CentralMonitoringDto(centralMonitoring2.id, centralMonitoring2.code, centralMonitoring2.description, centralMonitoring2.warningColour)
        assertNotEquals( centralMonitoringDto1, centralMonitoringDto2)
    }
    
    @Test
    public void testHashCode_Same() {
        CentralMonitoringDto centralMonitoringDto1 = new CentralMonitoringDto(centralMonitoring1.id, centralMonitoring1.code, centralMonitoring1.description, centralMonitoring1.warningColour)
        CentralMonitoringDto centralMonitoringDto2 = new CentralMonitoringDto(centralMonitoring1.id, centralMonitoring1.code, centralMonitoring1.description, centralMonitoring1.warningColour)
        assertEquals( centralMonitoringDto1.hashCode(), centralMonitoringDto2.hashCode())
    }
    
    @Test
    public void testHashCode_Different() {
        CentralMonitoringDto centralMonitoringDto1 = new CentralMonitoringDto(centralMonitoring1.id, centralMonitoring1.code, centralMonitoring1.description, centralMonitoring1.warningColour)
        CentralMonitoringDto centralMonitoringDto2 = new CentralMonitoringDto(centralMonitoring2.id, centralMonitoring2.code, centralMonitoring2.description, centralMonitoring2.warningColour)
        assertNotEquals( centralMonitoringDto1.hashCode(), centralMonitoringDto2.hashCode())
    }
    
    @Test
    public void testConstructor_CentralMonitoring() {
        CentralMonitoringDto centralMonitoringDto = new CentralMonitoringDto(centralMonitoring1)
        assertEquals( centralMonitoringDto.code, centralMonitoring1.code )
        assertEquals( centralMonitoringDto.description, centralMonitoring1.description )
        assertEquals( centralMonitoringDto.warningColour, centralMonitoring1.warningColour )
    }
}
