package uk.ac.reigate.services

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

import uk.ac.reigate.domain.Room
import uk.ac.reigate.domain.RoomType
import uk.ac.reigate.exceptions.InvalidOperationException
import uk.ac.reigate.repositories.RoomRepository
import uk.ac.reigate.repositories.RoomTypeRepository
import uk.ac.reigate.utils.ValidationUtils

@Service
class RoomService implements ICoreDataService<Room, Integer>{
    
    @Autowired
    RoomRepository roomRepository
    
    @Autowired
    RoomTypeRepository roomTypeRepository
    
    /**
     * Default NoArgs constructor
     */
    RoomService() {}
    
    /**
     * Autowired Constructor
     *
     * @param roomRepository
     */
    RoomService(RoomRepository roomRepository) {
        this.roomRepository = roomRepository;
    }
    
    /**
     * Find an individual Room using the Room ID fields
     *
     * @param id the ID fields to search for
     * @return the Room object that matches the ID supplied, or null if not found
     */
    @Override
    @Transactional(readOnly = true)
    Room findById(Integer id) {
        return roomRepository.findOne(id);
    }
    
    /**
     * Find a single page of Room objects
     * @return a List of Rooms
     */
    @Override
    @Transactional(readOnly = true)
    List<Room> findAll() {
        return roomRepository.findAll();
    }
    
    /**
     * 
     * @param id
     * @param code
     * @param description
     * @param roomType
     * @param capacity
     * @param defaultRows
     * @param defaultCols
     * @return
     */
    @Transactional
    public Room saveRoom(Integer id, String code, String description, RoomType roomType, Integer capacity, Integer defaultRows, Integer defaultCols) {
        ValidationUtils.assertNotBlank(code, "code cannot be blank");
        ValidationUtils.assertNotNull(description, "description is mandatory");
        
        Room room = null;
        
        if (id != null) {
            room = findById(id);
            room.setCode(code);
            room.setDescription(description);
            room.setRoomType(roomType);
            room.setCapacity(capacity);
            room.setDefaultRows(defaultRows);
            room.setDefaultCols(defaultCols);
            save(room);
        } else {
            room = save(new Room(code, description, roomType, capacity, defaultRows, defaultCols));
        }
        
        return room;
    }
    
    /**
     *  Function call without defaultRows and defaultCols retained for backwards compatibility
     * @param id
     * @param code
     * @param description
     * @param roomType
     * @param capacity
     * @return
     */
    
    @Transactional
    public Room saveRoom(Integer id, String code, String description, RoomType roomType, Integer capacity) {
        return saveRoom(id, code, description, roomType, capacity, null, null);
    }
    
    /**
     * This service method is used to save a complete Room object in the database
     *
     * @param room the new Room object to be saved
     * @return the saved version of the Room object
     */
    @Override
    @Transactional
    public Room save(Room room) {
        return roomRepository.save(room)
    }
    
    /**
     * This service method is used to update an Room object in the database from a partial or complete Room object.
     *
     * @param room the partial or complete Room object to be saved
     * @return the saved version of the Room object
     */
    
    @Transactional
    public Room updateRoom(Room room) {
        Room roomToSave = findById(room.id)
        roomToSave.code = room.code != null ? room.code : roomToSave.code
        roomToSave.description = room.description != null ? room.description : roomToSave.description
        roomToSave.roomType = (room.roomType != null && room.roomType.id != null) ? roomTypeRepository.findOne(room.roomType.id) : roomToSave.roomType
        roomToSave.capacity = room.capacity != null ? room.capacity : roomToSave.capacity
        roomToSave.defaultRows = room.defaultRows != null ? room.defaultRows : room.defaultRows
        roomToSave.defaultCols = room.defaultCols != null ? room.defaultCols : room.defaultCols
        return save(roomToSave)
    }
    
    /**
     * Saves a list of Room objects to the database
     *
     * @param rooms a list of Rooms to be saved to the database
     * @return the list of save Room objects
     */
    
    @Transactional
    public List<Room> saveRooms(List<Room> rooms) {
        return rooms.collect { room -> save(room) };
    }
    
    /**
     * This methods throws an InvalidOperationException when called. Room should not be deleted.
     */
    @Override
    public void delete(Room obj) {
        throw new InvalidOperationException("Room should not be deleted")
    }
}
