package uk.ac.reigate.dto;


import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;

import groovy.transform.EqualsAndHashCode;
import uk.ac.reigate.domain.lookup.SchoolReportStatus

/**
 *
 * JSON serializable DTO containing SchoolReportStatus data
 *
 */
@JsonSerialize
@EqualsAndHashCode(includeFields=true)
public class SchoolReportStatusDto implements Serializable {
    
    @JsonProperty
    private Integer id;
    
    @JsonProperty
    private String code;
    
    @JsonProperty
    private String description;
    
    /**
     * Default No Args constructor
     */
    public SchoolReportStatusDto() {
    }
    
    /**
     * Constructor to create a SchoolReportStatusDto object
     *
     * @param id the Id for the SchoolReportStatus
     * @param code the code for the SchoolReportStatus
     * @param description the description for the SchoolReportStatus
     */
    public SchoolReportStatusDto(Integer id, String code, String description) {
        this.id = id;
        this.code = code;
        this.description = description;
    }
    
    /**
     * Constructor to create a SchoolReportStatusDto object from a SchoolReportStatus object
     *
     * @param schoolReportStatus the SchoolReportStatus object to use for construction
     */
    SchoolReportStatusDto(SchoolReportStatus schoolReportStatus) {
        this.id = schoolReportStatus.id;
        this.code = schoolReportStatus.code;
        this.description = schoolReportStatus.description;
    }
    
    @Override
    public String toString() {
        return "SchoolReportStatusDto [id=" + id + ", code=" + code + ", description=" + description + "]";
    }
    
    public static SchoolReportStatusDto mapFromSchoolReportStatusEntity(SchoolReportStatus schoolReportStatus) {
        return new SchoolReportStatusDto(schoolReportStatus);
    }
    
    public static List<SchoolReportStatusDto> mapFromSchoolReportStatusesEntities(List<SchoolReportStatus> schoolReportStatuses) {
        List<SchoolReportStatusDto> output = schoolReportStatuses.collect { schoolReportStatus ->  new SchoolReportStatusDto(schoolReportStatus) };
        return output
    }
    
    public static SchoolReportStatus mapToSchoolReportStatusEntity(SchoolReportStatusDto schoolReportStatusDto) {
        return new SchoolReportStatus(schoolReportStatusDto.id, schoolReportStatusDto.code, schoolReportStatusDto.description)
    }
}
