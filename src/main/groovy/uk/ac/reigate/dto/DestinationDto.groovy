package uk.ac.reigate.dto;


import groovy.transform.EqualsAndHashCode

import javax.validation.constraints.NotNull

import io.swagger.annotations.ApiModel
import io.swagger.annotations.ApiModelProperty

import com.fasterxml.jackson.annotation.JsonProperty
import com.fasterxml.jackson.databind.annotation.JsonSerialize

import uk.ac.reigate.domain.ilr.Destination

/**
 *
 * JSON serializable DTO containing Destination data
 *
 */
@ApiModel
@JsonSerialize
@EqualsAndHashCode(includeFields=true)
public class DestinationDto implements Serializable {
    
    @ApiModelProperty(position = 1, required = false, value = "The ID of the Destination object stored in the database. This must be provided for any PUT operations but omitted for POST opertions.")
    @JsonProperty
    private Integer id;
    
    @ApiModelProperty(position = 2, required = true, value = "The code of the Destination object stored in the database")
    @NotNull
    @JsonProperty
    private String type;
    
    @ApiModelProperty(position = 3, required = true, value = "The code of the Destination object stored in the database")
    @NotNull
    @JsonProperty
    private String code;
    
    @ApiModelProperty(position = 4, required = true, value = "The description of the Destination object stored in the database")
    @NotNull
    @JsonProperty
    private String description;
    
    @ApiModelProperty(position = 5, required = true, value = "The short description of the Destination object stored in the database")
    @NotNull
    @JsonProperty
    private String shortDescription;
    
    @ApiModelProperty(position = 6, required = true, value = "The valid from date of the Destination object stored in the database", dataType = "string date")
    @JsonProperty
    private Date validFrom;
    
    @ApiModelProperty(position = 7, required = true, value = "The valid to date of the Destination object stored in the database")
    @JsonProperty
    private Date validTo;
    
    /**
     * Default No Args constructor
     */
    public DestinationDto() {
    }
    
    /**
     * Constructor to create a DestinationDto object
     *
     * @param id the Id for the Destination
     * @param type the type for the Destination
     * @param code the code for the Destination
     * @param description the description for the Destination
     * @Param shortDescription the shortDescription of the Destination
     * @Param validFrom the validFrom Date for the Destination
     * @Param validTo the validTo Date for the Destination
     */
    @Deprecated
    public DestinationDto(Integer id, String type, String code, String description, String shortDescription, Date validFrom, Date validTo) {
        this.id = id;
        this.type = type;
        this.code = code;
        this.description = description;
        this.shortDescription = shortDescription;
        this.validFrom = validFrom;
        this.validTo = validTo;
    }
    
    /**
     * Constructor to create a DestinationDto object
     *
     * @param destination the Destination object to use to create the DestinationDto
     */
    public DestinationDto(Destination destination) {
        this.id = destination.id;
        this.type = destination.type;
        this.code = destination.code;
        this.description = destination.description;
        this.shortDescription = destination.shortDescription;
        this.validFrom = destination.validFrom;
        this.validTo = destination.validTo;
    }
    
    @Override
    public String toString() {
        return "DestinationDto [id=" + id + ", type=" + type + ", code=" + code + ", description=" + description + ", shortDescription=" + shortDescription + ", validFrom=" + validFrom + ", validTo=" + validTo + "]";
    }
    
    public Destination toDestination() {
        return new Destination(this.id, this.type, this.code, this.description, this.shortDescription, this.validFrom, this.validTo)
    }
    
    public static DestinationDto mapFromEntity(Destination destination){
        return new DestinationDto(destination);
    }
    
    public static List<DestinationDto> mapFromList(List<Destination> destinations) {
        return destinations.collect { destination ->  new DestinationDto(destination) };
    }
    
    public static mapToDestination(DestinationDto destinationDto){
        return new Destination(destinationDto.id, destinationDto.type, destinationDto.code, destinationDto.description, destinationDto.shortDescription, destinationDto.validFrom, destinationDto.validTo)
    }
}
