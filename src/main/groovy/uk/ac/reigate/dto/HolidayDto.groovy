package uk.ac.reigate.dto;


import groovy.transform.EqualsAndHashCode
import uk.ac.reigate.domain.academic.AcademicYear
import uk.ac.reigate.domain.academic.Holiday

import com.fasterxml.jackson.annotation.JsonFormat
import com.fasterxml.jackson.annotation.JsonProperty
import com.fasterxml.jackson.databind.annotation.JsonSerialize

/**
 *
 * JSON serializable DTO containing Holiday data
 *
 */
@JsonSerialize
@EqualsAndHashCode(includeFields=true)
public class HolidayDto implements Serializable {
    
    @JsonProperty
    private Integer id;
    
    @JsonProperty
    private Integer yearId;
    
    @JsonProperty
    private String description;
    
    @JsonProperty
    private Date startDate;
    
    @JsonProperty
    private Date endDate;
    
    @JsonProperty
    private Boolean halfTerm
    
    /**
     * Default No Args constructor
     */
    public HolidayDto() {
    }
    
    /**
     * Constructor to create a HolidayDto object from a Holiday object
     *
     * @param holiday the Holiday object to use for construction
     */
    HolidayDto(Holiday holiday) {
        this.id = holiday.id;
        this.yearId = holiday.year != null ? holiday.year.id : null;
        this.description = holiday.description;
        this.startDate = holiday.startDate;
        this.endDate = holiday.endDate;
        this.halfTerm = holiday.halfTerm;
    }
    
    /**
     * Constructor to create a HolidayDto object with the basic data with no linked objects.
     *
     * @param id the Id for the Holiday
     * @param academicYearId the academicYearId for the Holiday
     * @param description the description for the Holiday
     * @Param startDate the startDate of the Holiday
     * @Param endDate the endDate of the Holiday
     */
    public HolidayDto(Integer id, Integer yearId, String description, Date startDate, Date endDate, Boolean halfTerm) {
        this.id = id;
        this.yearId = yearId
        this.description = description;
        this.startDate = startDate;
        this.endDate = endDate;
        this.halfTerm = halfTerm;
    }
    
    /**
     * Constructor to create a HolidayDto object with the basic data and the linked AcademicYaer objects.
     *
     * @param id the Id for the Holiday
     * @param academicYear the academicYear for the Holiday
     * @param description the description for the Holiday
     * @Param startDate the startDate of the Holiday
     * @Param endDate the endDate of the Holiday
     */
    public HolidayDto(Integer id, AcademicYear year, String description, Date startDate, Date endDate, Boolean halfTerm) {
        this(id, year != null ? year.id : null,  description, startDate, endDate, halfTerm);
    }
    
    @Override
    public String toString() {
        return "HolidayDto [id=" + id + ", year=" + yearId + ", description=" + description + ", startDate=" + startDate + ", endDate=" + endDate + ", halfTerm=" + halfTerm +"]";
    }
    
    public static HolidayDto mapFromHolidayEntity(Holiday holiday) {
        return new HolidayDto(holiday);
    }
    
    public static List<HolidayDto> mapFromHolidaysEntities(List<Holiday> holidays) {
        return holidays.collect { holiday ->  new HolidayDto(holiday) };
    }
    
    public static Holiday mapToHolidayEntity(HolidayDto holidayDto, AcademicYear year) {
        return new Holiday(holidayDto.id,  year, holidayDto.description, holidayDto.startDate, holidayDto.endDate, holidayDto.halfTerm)
    }
}
