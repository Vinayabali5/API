package uk.ac.reigate.dto;


import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;

import groovy.transform.EqualsAndHashCode;
import uk.ac.reigate.domain.Room
import uk.ac.reigate.domain.RoomType;

/**
 *
 * JSON serializable DTO containing Room data
 *
 */
@JsonSerialize
@EqualsAndHashCode(includeFields=true)
public class RoomDto implements Serializable {
    
    @JsonProperty
    private Integer id;
    
    @JsonProperty
    private String code;
    
    @JsonProperty
    private String description;
    
    @JsonProperty
    private Integer roomTypeId;
    
    @JsonProperty
    private Integer capacity;
    
    @JsonProperty
    private Integer defaultRows;
    
    @JsonProperty
    private Integer defaultCols;
    
    @JsonProperty
    private String _roomTypeDescription;
    
    /**
     * Default No Args constructor
     */
    public RoomDto() {
    }
    
    /**
     * Constructor to create a RoomDto object from a Room object
     *
     * @param room the Room object to use for construction
     */
    RoomDto(Room room) {
        this.id = room.id;
        this.code = room.code;
        this.description = room.description;
        this.roomTypeId = room.roomType != null ? room.roomType.id : null;
        this.capacity = room.capacity;
        this.defaultRows = room.defaultRows;
        this.defaultCols = room.defaultCols;
        this._roomTypeDescription = room.roomType != null ? room.roomType.description : null;
    }
    
    /**
     * Constructor to create a RoomDto object with the basic data with no linked objects
     *
     * @param id the Id for the Room
     * @param code the code for the Room
     * @param description the description for the Room
     * @Param roomTypeId the roomTypeId of the Room
     * @Param capacity the capacity for the Room
     * @Param defaultRows the default number of rows for exam purposes
     * @Param defaultCols the default number of columns for exam purposes
     */
    public RoomDto(Integer id, String code, String description, Integer roomTypeId, Integer capacity, Integer defaultRows, Integer defaultCols, String roomTypeDescription) {
        this.id = id;
        this.code = code;
        this.description = description;
        this.roomTypeId = roomTypeId;
        this.capacity = capacity;
        this.defaultRows = defaultRows;
        this.defaultCols = defaultCols;
        this._roomTypeDescription = roomTypeDescription
    }
    
    /**
     * Constructor to create a RoomDto object with the basic data with no linked objects
     * (Constructor without defaultRows and defaultCols retained for backward compatibility)
     *
     * @param id the Id for the Room
     * @param code the code for the Room
     * @param description the description for the Room
     * @Param roomTypeId the roomTypeId of the Room
     * @Param capacity the capacity for the Room
     */
    public RoomDto(Integer id, String code, String description, Integer roomTypeId, Integer capacity, String roomTypeDesc) {
        this(id, code, description, roomTypeId, capacity,null,null, roomTypeDesc)
    }
    
    /**
     * 
     * @param id the Id for the Room
     * @param code the code for the Room
     * @param description the description for the Room
     * @Param roomType the roomType of the Room
     * @Param capacity the capacity for the Room
     * @Param defaultRows the default number of rows for exam purposes
     * @Param defaultCols the default number of columns for exam purposes
     */
    public RoomDto(Integer id, String code, String description, RoomType roomType, Integer capacity, Integer defaultRows, Integer defaultCols){
        this(id, code, description, roomType != null ? roomType.id : null, capacity, defaultRows, defaultCols ,roomType != null ? roomType.description : null )
    }
    
    /**
     * Constructor to create a RoomDto object with the basic data and the linked RoomType objects
     * (Constructor without defaultRows and defaultCols retained for backward compatibility)
     *
     * @param id the Id for the Room
     * @param code the code for the Room
     * @param description the description for the Room
     * @Param roomType the roomType of the Room
     * @Param capacity the capacity for the Room
     */
    public RoomDto(Integer id, String code, String description, RoomType roomType, Integer capacity){
        this(id, code, description, roomType != null ? roomType.id : null, capacity, null, null,  roomType != null ? roomType.description : null)
    }
    
    @Override
    public String toString() {
        return "RoomDto [id=" + id + ", code=" + code + ", description=" + description + ", roomType=" + roomTypeId + ", capacity=" + capacity +", defaultRows= " + defaultRows + ", defaultCols=" + defaultCols + "]";
    }
    
    public static RoomDto mapFromRoomEntity(Room room) {
        return new RoomDto(room)
    }
    
    public static List<RoomDto> mapFromRoomsEntities(List<Room> rooms) {
        return rooms.collect { room ->  new RoomDto(room) };
    }
    
    public static Room mapToRoomEntity(RoomDto roomDto, RoomType roomType) {
        return new Room(roomDto.id, roomDto.code, roomDto.description, roomType, roomDto.capacity, roomDto.defaultRows, roomDto.defaultCols)
    }
}
