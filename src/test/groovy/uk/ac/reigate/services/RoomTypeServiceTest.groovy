package uk.ac.reigate.services

import static org.junit.Assert.assertNull
import static org.junit.Assert.assertTrue
import static org.mockito.Mockito.*

import org.junit.Before
import org.junit.Test
import org.mockito.Mockito

import uk.ac.reigate.domain.RoomType;
import uk.ac.reigate.model.SearchResult
import uk.ac.reigate.repositories.RoomTypeRepository
import uk.ac.reigate.services.RoomTypeService;


class RoomTypeServiceTest {
    
    private RoomTypeRepository roomTypeRepository;
    
    private RoomTypeService roomTypeService;
    
    RoomType roomType1
    RoomType roomType2
    
    @Before
    public void setup() {
        this.roomTypeRepository = Mockito.mock(RoomTypeRepository.class);
        this.roomTypeService = new RoomTypeService(roomTypeRepository);
        
        roomType1 = new RoomType(id: 1, code: 'A', description: 'A RoomType')
        roomType2 = new RoomType(id: 2, code: 'C', description: 'C RoomType')
        
        when(roomTypeRepository.findAll()).thenReturn([roomType1, roomType2]);
        when(roomTypeRepository.findOne(1)).thenReturn(roomType1);
        when(roomTypeRepository.findOne(null)).thenThrow(new IllegalArgumentException());
        when(roomTypeRepository.save(any(RoomType.class))).thenReturn(roomType1);
    }
    
    @Test
    public void testFindRoomTypes() {
        List<RoomType> result = roomTypeService.findAll();
        verify(roomTypeRepository, times(1)).findAll()
        verifyNoMoreInteractions(roomTypeRepository)
    }
    
    @Test
    public void testFindRoomTypeById() {
        RoomType result = roomTypeService.findById(1);
        verify(roomTypeRepository, times(1)).findOne(1)
        verifyNoMoreInteractions(roomTypeRepository)
    }
    
    @Test
    public void testSaveNewRoomType() {
        roomType1.id = null
        roomTypeService.save(roomType1);
        verify(roomTypeRepository, times(1)).save(roomType1)
        verifyNoMoreInteractions(roomTypeRepository)
    }
    
    @Test
    public void testSaveRoomType() {
        roomTypeService.save(roomType1);
        verify(roomTypeRepository, times(1)).save(roomType1)
        verifyNoMoreInteractions(roomTypeRepository)
    }
    
    @Test
    public void testSaveRoomTypes() {
        List<RoomType> savedRoomTypes = roomTypeService.saveRoomTypes([roomType1, roomType2]);
        verify(roomTypeRepository, times(2)).save(any(RoomType.class))
        verifyNoMoreInteractions(roomTypeRepository)
    }
    
    @Test
    public void testSaveRoomTypeByFields_WithNullId() {
        RoomType savedRoomType = roomTypeService.saveRoomType(null, roomType1.code, roomType1.description, roomType1.timetableable);
        verify(roomTypeRepository, times(1)).save(any())
        verifyNoMoreInteractions(roomTypeRepository)
    }
    
    
    @Test
    public void testSaveRoomTypeByFields_WithId() {
        RoomType savedRoomType = roomTypeService.saveRoomType(1, roomType1.code, roomType1.description, roomType1.timetableable);
        verify(roomTypeRepository, times(1)).findOne(1)
        verify(roomTypeRepository, times(1)).save(any())
        verifyNoMoreInteractions(roomTypeRepository)
    }
}

