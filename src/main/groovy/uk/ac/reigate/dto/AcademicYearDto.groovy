package uk.ac.reigate.dto;


import groovy.transform.EqualsAndHashCode

import com.fasterxml.jackson.annotation.JsonProperty
import com.fasterxml.jackson.databind.annotation.JsonSerialize

import uk.ac.reigate.domain.academic.AcademicYear

/**
 *
 * JSON serializable DTO containing AcademicYear data
 *
 */
@JsonSerialize
@EqualsAndHashCode(includeFields=true)
public class AcademicYearDto implements Serializable {
    
    @JsonProperty
    private Integer id;
    
    @JsonProperty
    private String code;
    
    @JsonProperty
    private String description;
    
    @JsonProperty
    private Date startDate;
    
    @JsonProperty
    private Date endDate;
    
    /**
     * Default No Args constructor
     */
    public AcademicYearDto() {}
    
    /**
     * Constructor to create an AcademicYearDto object from the components properties
     *
     * @param id the Id for the academicYear
     * @param code the code for the academicYear
     * @param description the description for the academicYear
     * @Param startDate the startDate of the academicYear
     * @Param endDate the endDate of the academicYear
     */
    public AcademicYearDto(Integer id, String code, String description, Date startDate, Date endDate) {
        this.id = id;
        this.code = code;
        this.description = description;
        this.startDate = startDate;
        this.endDate = endDate;
    }
    
    /**
     * Constructor to create an AcademicYearDto object from an AcademicYear object
     * 
     * @param academicYear the AcademicYear object to use for construction
     */
    public AcademicYearDto(AcademicYear academicYear) {
        this.id = academicYear.id;
        this.code = academicYear.code;
        this.description = academicYear.description;
        this.startDate = academicYear.startDate;
        this.endDate = academicYear.endDate;
    }
    
    @Override
    public String toString() {
        return "AcademicYearDto [id=" + id + ", code=" + code + ", description=" + description + ", startDate=" + startDate + ", endDate=" + endDate + "]";
    }
    
    /**
     * This method is used to convert an AcademicYearDto object into an AcademicYear object
     * @return
     */
    public AcademicYear toAcademicYear() {
        return new AcademicYear(this.id, this.code, this.description, this.startDate, this.endDate)
    }
    
    public static AcademicYearDto mapFromEntity(AcademicYear academicYear) {
        return new AcademicYearDto(academicYear);
    }
    
    @Deprecated
    public static AcademicYearDto mapFromAcademicYearEntity(AcademicYear academicYear) {
        return mapFromEntity(academicYear)
    }
    
    public static List<AcademicYearDto> mapFromEntityList(List<AcademicYear> academicYears) {
        List<AcademicYearDto> output = academicYears.collect { academicYear ->  mapFromEntity(academicYear) };
        return output
    }
    
    public static List<AcademicYearDto> mapFromList(List<AcademicYear> academicYears) {
        List<AcademicYearDto> output = academicYears.collect { academicYear ->  mapFromEntity(academicYear) };
        return output
    }
    
    @Deprecated
    public static List<AcademicYearDto> mapFromAcademicYearsEntities(List<AcademicYear> academicYears) {
        return mapFromEntityList(academicYears)
    }
    
    public static AcademicYear mapToEntity(AcademicYearDto academicYearDto) {
        return new AcademicYear(academicYearDto.id, academicYearDto.code, academicYearDto.description, academicYearDto.startDate, academicYearDto.endDate)
    }
    
    @Deprecated
    public static AcademicYear mapToAcademicYearEntity(AcademicYearDto academicYearDto) {
        return mapToEntity(academicYearDto)
    }
}
