package uk.ac.reigate.dto;

import static org.junit.Assert.*
import org.junit.Before

import java.util.List;

import org.junit.Before;
import org.junit.Test

import uk.ac.reigate.domain.Room
import uk.ac.reigate.domain.RoomType



public class RoomDtoTest {
    
    private Room room1
    
    private Room room2
    
    private RoomType roomType
    
    private List<Room> rooms
    
    @Before
    public void setup() {
        this.roomType = new RoomType(1, 's', 'small', null)
        room1 = new Room(
                id: 1,
                code:'1',
                description:'Room1',
                roomType: roomType,
                capacity: 10
                
                );
        room2 = new Room(
                id: 2,
                code:'2',
                description:'Room2',
                roomType: roomType,
                capacity: 10
                );
        rooms = Arrays.asList(room1, room2);
    }
    
    RoomDto generateRoomDto() {
        return generateRoom1Dto()
    }
    
    RoomDto generateRoom1Dto() {
        return new RoomDto(room1.id, room1.code, room1.description, room1.roomType.id, room1.capacity, room1.roomType.description)
    }
    
    RoomDto generateRoom2Dto() {
        return new RoomDto(room2.id, room2.code, room2.description, room2.roomType.id, room2.capacity,room2.roomType.description)
    }
    
    @Test
    public void testMapFromRoomEntity(){
        RoomDto roomTest = RoomDto.mapFromRoomEntity( room1 )
        assertEquals( roomTest.id, room1.id );
        assertEquals( roomTest.code, room1.code);
        assertEquals( roomTest.description, room1.description);
    }
    
    @Test
    public void testMapFromRoomsEntities(){
        List<RoomDto> roomsDtoTest = RoomDto.mapFromRoomsEntities( rooms )
        assertEquals( roomsDtoTest[0].id, room1.id );
        assertEquals( roomsDtoTest[0].code, room1.code);
        assertEquals( roomsDtoTest[0].description, room1.description);
        assertEquals( roomsDtoTest[1].id, room2.id );
        assertEquals( roomsDtoTest[1].code, room2.code);
        assertEquals( roomsDtoTest[1].description, room2.description);
    }
    
    @Test
    public void testMapToRoomEntity(){
        RoomDto roomDto = generateRoomDto();
        Room room = RoomDto.mapToRoomEntity( roomDto, roomType );
        assertEquals( room.id, room1.id );
        assertEquals( room.code, room1.code);
        assertEquals( room.description, room1.description);
    }
    
    @Test
    public void testEquals_Same() {
        RoomDto roomDto1 = new RoomDto(room1.id, room1.code, room1.description, room1.roomType.id, room1.capacity, room1.roomType.description)
        RoomDto roomDto2 = new RoomDto(room1.id, room1.code, room1.description, room1.roomType.id, room1.capacity, room1.roomType.description)
        assertEquals(roomDto1, roomDto2)
    }
    
    @Test
    public void testEquals_Different() {
        RoomDto roomDto1 = new RoomDto(room1.id, room1.code, room1.description, room1.roomType.id, room1.capacity, room1.roomType.description)
        RoomDto roomDto2 = new RoomDto(room2.id, room2.code, room2.description, room2.roomType.id, room2.capacity, room2.roomType.description)
        assertNotEquals(roomDto1, roomDto2)
    }
    
    @Test
    public void testHashCode_Same() {
        RoomDto roomDto1 = new RoomDto(room1.id, room1.code, room1.description, room1.roomType.id, room1.capacity, room1.roomType.description)
        RoomDto roomDto2 = new RoomDto(room1.id, room1.code, room1.description, room1.roomType.id, room1.capacity, room1.roomType.description)
        assertEquals(roomDto1.hashCode(), roomDto2.hashCode())
    }
    
    @Test
    public void testHashCode_Different() {
        RoomDto roomDto1 = new RoomDto(room1.id, room1.code, room1.description, room1.roomType.id, room1.capacity, room1.roomType.description)
        RoomDto roomDto2 = new RoomDto(room2.id, room2.code, room2.description, room2.roomType.id, room2.capacity, room2.roomType.description)
        assertNotEquals(roomDto1.hashCode(), roomDto2.hashCode())
    }
}
