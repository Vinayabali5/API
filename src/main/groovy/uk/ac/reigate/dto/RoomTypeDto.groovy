package uk.ac.reigate.dto

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;

import groovy.transform.EqualsAndHashCode;
import uk.ac.reigate.domain.RoomType
/**
 *
 * JSON serializable DTO containing RoomType data
 *
 */
@JsonSerialize
@EqualsAndHashCode(includeFields=true)
public class RoomTypeDto implements Serializable {
    
    @JsonProperty
    private Integer id;
    
    @JsonProperty
    private String code;
    
    @JsonProperty
    private String description;
    
    @JsonProperty
    private Boolean timetableable
    
    /**
     * Default No Args constructor
     */
    public RoomTypeDto() {
    }
    
    /**
     * Constructor to create a RoomTypeDto object
     *
     * @param id the Id for the RoomType
     * @param code the code for the RoomType
     * @param description the description for the RoomType
     * @Param timetableable the timetableable of the RoomType
     */
    public RoomTypeDto(Integer id, String code, String description, Boolean timetableable) {
        this.id = id;
        this.code = code;
        this.description = description;
        this.timetableable = timetableable;
    }
    
    /**
     * Constructor to create a RoomTypeDto object from a RoomType object
     *
     * @param roomType the RoomType object to use for construction
     */
    RoomTypeDto(RoomType roomType) {
        this.id = roomType.id;
        this.code = roomType.code;
        this.description = roomType.description;
        this.timetableable = roomType.timetableable;
    }
    
    @Override
    public String toString() {
        return "RoomTypeDto [id=" + id + ", code=" + code + ", description=" + description + ", timetableable=" + timetableable +"]";
    }
    
    public static RoomTypeDto mapFromRoomTypeEntity(RoomType roomType) {
        return new RoomTypeDto(roomType);
    }
    
    public static List<RoomTypeDto> mapFromRoomTypesEntities(List<RoomType> roomTypes) {
        List<RoomTypeDto> output = roomTypes.collect { roomType ->  new RoomTypeDto(roomType) };
        return output
    }
    
    public static RoomType mapToRoomTypeEntity(RoomTypeDto roomTypeDto) {
        return new RoomType(roomTypeDto.id, roomTypeDto.code, roomTypeDto.description, roomTypeDto.timetableable)
    }
}
