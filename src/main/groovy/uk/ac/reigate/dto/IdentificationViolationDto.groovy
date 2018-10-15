package uk.ac.reigate.dto;


import groovy.transform.EqualsAndHashCode;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;

import uk.ac.reigate.domain.academic.IdentificationViolation

/**
 *
 * JSON serializable DTO containing Department data
 *
 */
@JsonSerialize
@EqualsAndHashCode(includeFields=true)
public class IdentificationViolationDto implements Serializable {
    
    @JsonProperty
    private Integer id;
    
    //    @NotNull
    @JsonProperty
    private Integer studentId;
    
    @JsonProperty
    private Integer yearId;
    
    //    @NotNull
    @JsonProperty
    private Date date;
    
    @JsonProperty
    private Boolean returned;
    
    @JsonProperty
    private Boolean lost;
    
    @JsonProperty
    private Boolean printed;
    
    @JsonProperty
    private Integer id_number;
    
    
    
    /** 
     * Default No Args constructor
     */
    public IdentificationViolationDto() {
    }
    
    /**
     * Constructor to create a DepartmentDto object from a Department object
     *
     * @param department the Department object to use for construction
     */
    IdentificationViolationDto(IdentificationViolation idViolation) {
        this.id = idViolation.id;
        this.studentId = idViolation.student != null ? idViolation.student.id : null
        this.yearId = idViolation.year != null ? idViolation.year.id : null
        this.date = idViolation.date
        this.returned = idViolation.returned
        this.lost = idViolation.lost
        this.printed = idViolation.printed
        this.id_number = idViolation.id_number
    }
    
    @Override
    public String toString() {
        return "IdentificationViolationDto [id=" + id + ", studentId=" + studentId + ", date=" + date + ", returned=" + returned +"]";
    }
    
    public static IdentificationViolationDto mapFromEntity(IdentificationViolation idViolation) {
        return  new IdentificationViolationDto(idViolation)
    }
    
    public static List<IdentificationViolationDto> mapFromEntities(List<IdentificationViolation> idViolations) {
        return idViolations.collect { idViolation ->
            new IdentificationViolationDto(idViolation)
        };
    }
}
