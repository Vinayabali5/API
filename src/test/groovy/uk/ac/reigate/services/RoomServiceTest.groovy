package uk.ac.reigate.services

import static org.junit.Assert.assertNull
import static org.junit.Assert.assertTrue
import static org.mockito.Mockito.*

import org.junit.Before
import org.junit.Test
import org.mockito.Mockito

import uk.ac.reigate.domain.Room
import uk.ac.reigate.domain.Room;
import uk.ac.reigate.model.SearchResult
import uk.ac.reigate.repositories.RoomRepository
import uk.ac.reigate.services.RoomService;


class RoomServiceTest {
    
    private RoomRepository roomRepository;
    
    private RoomService roomService;
    
    Room room1
    Room room2
    
    @Before
    public void setup() {
        this.roomRepository = Mockito.mock(RoomRepository.class);
        this.roomService = new RoomService(roomRepository);
        
        room1 = new Room(id: 1, code: 'A', description: 'A Room')
        room2 = new Room(id: 2, code: 'C', description: 'C Room')
        
        when(roomRepository.findAll()).thenReturn([room1, room2]);
        when(roomRepository.findOne(1)).thenReturn(room1);
        when(roomRepository.findOne(null)).thenThrow(new IllegalArgumentException());
        when(roomRepository.save(any(Room.class))).thenReturn(room1);
    }
    
    @Test
    public void testFindRooms() {
        List<Room> result = roomService.findAll();
        verify(roomRepository, times(1)).findAll()
        verifyNoMoreInteractions(roomRepository)
    }
    
    @Test
    public void testFindRoomById() {
        Room result = roomService.findById(1);
        verify(roomRepository, times(1)).findOne(1)
        verifyNoMoreInteractions(roomRepository)
    }
    
    @Test
    public void testSaveNewRoom() {
        room1.id = null
        roomService.save(room1);
        verify(roomRepository, times(1)).save(room1)
        verifyNoMoreInteractions(roomRepository)
    }
    
    @Test
    public void testSaveRoom() {
        roomService.save(room1);
        verify(roomRepository, times(1)).save(room1)
        verifyNoMoreInteractions(roomRepository)
    }
}

