package uk.ac.reigate.dto;


import java.util.List

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;

import groovy.transform.EqualsAndHashCode;
import uk.ac.reigate.domain.lookup.PunctualityMonitoring
import uk.ac.reigate.domain.lookup.PunctualityMonitoring

/**
 *
 * JSON serializable DTO containing PunctualityMonitoring data
 *
 */
@JsonSerialize
@EqualsAndHashCode(includeFields=true)
public class PunctualityMonitoringDto implements Serializable {
    
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
    public PunctualityMonitoringDto() {
    }
    
    /**
     * Constructor to create a PunctualityMonitoringDto object
     *
     * @param id the Id for the PunctualityMonitoring
     * @param code the code for the PunctualityMonitoring
     * @param description the description for the PunctualityMonitoring
     */
    public PunctualityMonitoringDto(Integer id, String code, String description,String warningColour) {
        this.id = id;
        this.code = code;
        this.description = description;
        this.warningColour = warningColour;
    }
    
    /**
     * Constructor to create a PunctualityMonitoringDto object from a PunctualityMonitoring object
     *
     * @param punctualityMonitoring the PunctualityMonitoring object to use for construction
     */
    PunctualityMonitoringDto(PunctualityMonitoring punctualityMonitoring) {
        this.id = punctualityMonitoring.id;
        this.code = punctualityMonitoring.code;
        this.description = punctualityMonitoring.description;
        this.warningColour = punctualityMonitoring.warningColour;
    }
    
    @Override
    public String toString() {
        return "PunctualityMonitoringDto [id=" + id + ", code=" + code + ", description=" + description + ", warningColour=" + warningColour +"]";
    }
    
    public PunctualityMonitoring toPunctualityMonitoring() {
        return new PunctualityMonitoring(this.id, this.code, this.description, this.warningColour)
    }
    
    public static PunctualityMonitoringDto mapFromEntity(PunctualityMonitoring punctualityMonitoring){
        return new PunctualityMonitoringDto(punctualityMonitoring)
    }
    
    public static PunctualityMonitoringDto mapFromPunctualityMonitoringEntity(PunctualityMonitoring punctualityMonitoring) {
        return new PunctualityMonitoringDto(punctualityMonitoring);
    }
    
    public static List<PunctualityMonitoringDto> mapFromPunctualityMonitoringsEntities(List<PunctualityMonitoring> punctualityMonitorings) {
        List<PunctualityMonitoringDto> output = punctualityMonitorings.collect { punctualityMonitoring ->  new PunctualityMonitoringDto(punctualityMonitoring) };
        return output
    }
    
    public static PunctualityMonitoring mapToPunctualityMonitoringEntity(PunctualityMonitoringDto punctualityMonitoringDto) {
        return new PunctualityMonitoring(punctualityMonitoringDto.id, punctualityMonitoringDto.code, punctualityMonitoringDto.description, punctualityMonitoringDto.warningColour)
    }
}
