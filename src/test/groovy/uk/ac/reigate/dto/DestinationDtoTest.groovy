package uk.ac.reigate.dto;

import static org.junit.Assert.*

import org.junit.Before
import org.junit.Test

import uk.ac.reigate.domain.ilr.Destination

public class DestinationDtoTest {
    
    private Destination destination1
    
    private Destination destination2
    
    private List<Destination> destinations
    
    @Before
    public void setup() {
        this.destination1 = new Destination(
                id: 1,
                type:'t',
                code: 'UK',
                description: 'United Kingdom',
                shortDescription:'United Kingdom',
                validFrom: new Date().parse('yyyy/MM/dd', '2011/07/09'),
                validTo: new Date().parse('yyyy/MM/dd', '2013/07/09')
                );
        this.destination2 = new Destination(
                id: 2,
                type:'t',
                code: 'EU',
                description: 'European Ecconomical Union',
                shortDescription:'European Union',
                validFrom: new Date().parse('yyyy/MM/dd', '2011/07/09'),
                validTo: new Date().parse('yyyy/MM/dd', '2013/07/09')
                );
        this.destinations = Arrays.asList(destination1, destination2);
    }
    
    
    
    @Test
    public void testEquals_Same() {
        DestinationDto destinationDto1 = new DestinationDto(destination1.id, destination1.type, destination1.code, destination1.description, destination1.shortDescription, destination1.validFrom, destination1.validTo)
        DestinationDto destinationDto2 = new DestinationDto(destination1.id, destination1.type, destination1.code, destination1.description, destination1.shortDescription, destination1.validFrom, destination1.validTo)
        assertEquals( destinationDto1, destinationDto2)
    }
    
    @Test
    public void testEquals_Different() {
        DestinationDto destinationDto1 = new DestinationDto(destination1.id, destination1.type, destination1.code, destination1.description, destination1.shortDescription, destination1.validFrom, destination1.validTo)
        DestinationDto destinationDto2 = new DestinationDto(destination2.id, destination2.type, destination2.code, destination2.description, destination2.shortDescription, destination2.validFrom, destination2.validTo)
        assertNotEquals( destinationDto1, destinationDto2)
    }
    
    @Test
    public void testHashCode_Same() {
        DestinationDto destinationDto1 = new DestinationDto(destination1.id, destination1.type, destination1.code, destination1.description, destination1.shortDescription, destination1.validFrom, destination1.validTo)
        DestinationDto destinationDto2 = new DestinationDto(destination1.id, destination1.type,destination1.code, destination1.description, destination1.shortDescription, destination1.validFrom, destination1.validTo)
        assertEquals( destinationDto1.hashCode(), destinationDto2.hashCode())
    }
    
    @Test
    public void testHashCode_Different() {
        DestinationDto destinationDto1 = new DestinationDto(destination1.id,destination1.type, destination1.code, destination1.description, destination1.shortDescription, destination1.validFrom, destination1.validTo)
        DestinationDto destinationDto2 = new DestinationDto(destination2.id,destination2.type, destination2.code, destination2.description, destination2.shortDescription, destination2.validFrom, destination2.validTo)
        assertNotEquals( destinationDto1.hashCode(), destinationDto2.hashCode())
    }
    
    @Test
    public void testConstructor_Destination() {
        DestinationDto destinationDto = new DestinationDto(destination1)
        assertEquals( destinationDto.id, destination1.id );
        assertEquals( destinationDto.code, destination1.code);
        assertEquals( destinationDto.description, destination1.description);
        assertEquals( destinationDto.shortDescription, destination1.shortDescription);
        assertEquals( destinationDto.validFrom, destination1.validFrom);
        assertEquals( destinationDto.validTo, destination1.validTo);
    }
}
