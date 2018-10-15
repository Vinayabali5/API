package uk.ac.reigate.dto;


import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;

import groovy.transform.EqualsAndHashCode;
import uk.ac.reigate.domain.admissions.ApplicationStatus

/**
 *
 * JSON serializable DTO containing ApplicationStatus data
 *
 */
@JsonSerialize
@EqualsAndHashCode(includeFields=true)
public class ApplicationStatusDto implements Serializable {
    
    @JsonProperty
    private Integer id;
    
    @JsonProperty
    private String code;
    
    @JsonProperty
    private String description;
    
    @JsonProperty
    private Boolean considerWithdrawn
    
    /**
     * Default No Args constructor
     */
    public ApplicationStatusDto() {
    }
    
    /**
     * Constructor to create a ApplicationStatusDto object
     *
     * @param id the Id for the applicationStatus
     * @param code the code for the applicationStatus
     * @param description the description for the applicationStatus
     */
    public ApplicationStatusDto(Integer id, String code, String description, Boolean considerWithdrawn) {
        this.id = id;
        this.code = code;
        this.description = description;
        this.considerWithdrawn = considerWithdrawn;
    }
    
    /**
     * Constructor to create an ApplicationStatusDto object from an ApplicationStatus object
     *
     * @param applicationStatus the ApplicationStatus object to use for construction
     */
    ApplicationStatusDto(ApplicationStatus applicationStatus) {
        this.id = applicationStatus.id;
        this.code = applicationStatus.code;
        this.description = applicationStatus.description;
        this.considerWithdrawn = applicationStatus.considerWithdrawn;
    }
    
    @Override
    public String toString() {
        return "ApplicationStatus [id=" + id + ", code=" + code + ", description=" + description + ", considerWithdrawn=" + considerWithdrawn +"]";
    }
    
    public static ApplicationStatusDto mapFromApplicationStatusEntity(ApplicationStatus applicationStatus) {
        return new ApplicationStatusDto(applicationStatus);
    }
    
    public static List<ApplicationStatusDto> mapFromApplicationStatusesEntities(List<ApplicationStatus> applicationStatuses) {
        List<ApplicationStatusDto> output = applicationStatuses.collect { applicationStatus ->  new ApplicationStatusDto(applicationStatus) };
        return output
    }
    
    public static ApplicationStatus mapToApplicationStatusEntity(ApplicationStatusDto applicationStatusDto) {
        return new ApplicationStatus(applicationStatusDto.id, applicationStatusDto.code, applicationStatusDto.description, applicationStatusDto.considerWithdrawn)
    }
}
