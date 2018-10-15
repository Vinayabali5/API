package uk.ac.reigate.services

//import static org.springframework.util.Assert

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import org.springframework.security.access.prepost.PreAuthorize
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

import uk.ac.reigate.domain.RoomType
import uk.ac.reigate.exceptions.InvalidOperationException
import uk.ac.reigate.model.PageInfo
import uk.ac.reigate.model.SearchResult
import uk.ac.reigate.repositories.RoomTypeRepository
import uk.ac.reigate.utils.ValidationUtils;

@Service
class RoomTypeService implements ICoreDataService<RoomType, Integer>{
    
    @Autowired
    RoomTypeRepository roomTypeRepository
    
    /**
     * Default NoArgs constructor
     */
    RoomTypeService() {}
    
    /**
     * Autowired Constructor
     *
     * @param roomTypeRepository
     */
    RoomTypeService(RoomTypeRepository roomTypeRepository) {
        this.roomTypeRepository = roomTypeRepository;
    }
    
    /**
     * Find an individual RoomType using the RoomType ID fields
     *
     * @param id the ID fields to search for
     * @return the RoomType object that matches the ID supplied, or null if not found
     */
    @Override
    @Transactional(readOnly = true)
    RoomType findById(Integer id) {
        return roomTypeRepository.findOne(id);
    }
    
    /**
     * Find a single page of RoomType objects
     * @return a SearchResult set with the list of RoomTypes
     */
    @Override
    @Transactional(readOnly = true)
    List<RoomType> findAll() {
        return roomTypeRepository.findAll();
    }
    
    /**
     *
     * @param id
     * @param code
     * @param description
     * @return
     */
    @Transactional
    public RoomType saveRoomType(Integer id, String code, String description, Boolean timetableable) {
        ValidationUtils.assertNotBlank(code, "code cannot be blank");
        ValidationUtils.assertNotNull(description, "description is mandatory");
        
        RoomType roomType = null;
        
        if (id != null) {
            roomType = findById(id);
            
            roomType.setCode(code);
            roomType.setDescription(description);
            roomType.setTimetableable(timetableable);
            
            save(roomType);
        } else {
            roomType = save(new RoomType(code, description, timetableable));
        }
        
        return roomType;
    }
    
    /**
     * This service method is used to save a complete RoomType object in the database
     *
     * @param roomType the new RoomType object to be saved
     * @return the saved version of the RoomType object
     */
    @Override
    @Transactional
    public RoomType save(RoomType roomType) {
        return roomTypeRepository.save(roomType)
    }
    
    /**
     * This service method is used to update an RoomType object in the database from a partial or complete RoomType object.
     *
     * @param roomType the partial or complete RoomType object to be saved
     * @return the saved version of the RoomType object
     */
    @Transactional
    public RoomType updateRoomType(RoomType roomType) {
        RoomType roomTypeToSave = findById(roomType.id)
        roomTypeToSave.code = roomType.code != null ? roomType.code : roomTypeToSave.code
        roomTypeToSave.description = roomType.description != null ? roomType.description : roomTypeToSave.description
        roomTypeToSave.timetableable = roomType.timetableable != null ? roomType.timetableable : roomTypeToSave.timetableable
        return save(roomTypeToSave)
    }
    
    /**
     * Saves a list of RoomType objects to the database
     *
     * @param roomTypes a list of RoomTypes to be saved to the database
     * @return the list of save RoomType objects
     */
    @Transactional
    public List<RoomType> saveRoomTypes(List<RoomType> roomTypes) {
        return roomTypes.collect { roomType -> save(roomType) };
    }
    
    /**
     * This methods throws an InvalidOperationException when called. RoomType should not be deleted.
     */
    @Override
    public void delete(RoomType obj) {
        throw new InvalidOperationException("RoomType should not be deleted")
        
    }
    
    
    
}
