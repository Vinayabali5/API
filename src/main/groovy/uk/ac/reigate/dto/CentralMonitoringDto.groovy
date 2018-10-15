package uk.ac.reigate.dto;


import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;

import groovy.transform.EqualsAndHashCode;
import uk.ac.reigate.domain.lookup.CentralMonitoring

/**
 *
 * JSON serializable DTO containing CentralMonitoring data
 *
 */
@JsonSerialize
@EqualsAndHashCode(includeFields=true)
public class CentralMonitoringDto implements Serializable {
    
    @JsonProperty
    private Integer id;
    
    @JsonProperty
    private String code;
    
    @JsonProperty
    private String description;
    
    @JsonProperty
    private String warningColour
    
    /**
     * Default No Args constructor
     */
    public CentralMonitoringDto() {
    }
    
    /**
     * Constructor to create a CentralMonitoringDto object
     *
     * @param id the Id for the CentralMonitoring
     * @param code the code for the CentralMonitoring
     * @param description the description for the CentralMonitoring
     */
    public CentralMonitoringDto(Integer id, String code, String description, String warningColour) {
        this.id = id;
        this.code = code;
        this.description = description;
        this.warningColour = warningColour;
    }
    
    /**
     * Constructor to create a CentralMonitoringDto object from a CentralMonitoring object
     *
     * @param centralMonitoring the CentralMonitoring object to use for construction
     */
    CentralMonitoringDto(CentralMonitoring centralMonitoring) {
        this.id = centralMonitoring.id;
        this.code = centralMonitoring.code;
        this.description = centralMonitoring.description;
        this.warningColour = centralMonitoring.warningColour;
    }
    
    @Override
    public String toString() {
        return "CentralMonitoringDto [id=" + id + ", code=" + code + ", description=" + description + ", warningColour=" + warningColour +"]";
    }
    
    public CentralMonitoring toCentralMonitoring() {
        return new CentralMonitoring(this.id, this.code, this.description, this.warningColour)
    }
    
    public static CentralMonitoringDto mapFromEntity(CentralMonitoring centralMonitoring){
        return new CentralMonitoringDto(centralMonitoring)
    }
    
    public static CentralMonitoringDto mapFromCentralMonitoringEntity(CentralMonitoring centralMonitoring) {
        return new CentralMonitoringDto(centralMonitoring);
    }
    
    public static List<CentralMonitoringDto> mapFromCentralMonitoringsEntities(List<CentralMonitoring> centralMonitorings) {
        List<CentralMonitoringDto> output = centralMonitorings.collect { centralMonitoring ->  new CentralMonitoringDto(centralMonitoring) };
        return output
    }
    
    public static CentralMonitoring mapToCentralMonitoringEntity(CentralMonitoringDto centralMonitoringDto) {
        return new CentralMonitoring(centralMonitoringDto.id, centralMonitoringDto.code, centralMonitoringDto.description, centralMonitoringDto.warningColour)
    }
}
