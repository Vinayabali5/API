package uk.ac.reigate.dto;


import java.util.Date;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;

import groovy.transform.EqualsAndHashCode
import uk.ac.reigate.domain.academic.AcademicYear;
import uk.ac.reigate.domain.academic.InterimReport

/**
 *
 * JSON serializable DTO containing InterimReport data
 *
 */
@JsonSerialize
@EqualsAndHashCode(includeFields=true)
public class InterimReportDto implements Serializable {
    
    @JsonProperty
    private Integer id;
    
    @JsonProperty
    private String code;
    
    @JsonProperty
    private String description;
    
    @JsonProperty
    private Integer yearId;
    
    @JsonProperty
    private Date startDate;
    
    @JsonProperty
    private Date endDate;
    
    @JsonProperty
    private Date publishDate;
    
    @JsonProperty
    private Boolean active;
    
    /**
     * Default No Args constructor
     */
    public InterimReportDto() {
    }
    
    /**
     * Constructor to create an InterimReportDto object from a InterimReport object
     *
     * @param interimReport the InterimReport object to use for construction
     */
    InterimReportDto(InterimReport interimReport) {
        this.id = interimReport.id;
        this.code = interimReport.code;
        this.description = interimReport.description;
        this.yearId = interimReport.year != null ? interimReport.year.id : null;
        this.startDate = interimReport.startDate;
        this.endDate = interimReport.endDate;
        this.publishDate = interimReport.publishDate;
        this.active = interimReport.active;
    }
    
    /**
     * Constructor to create a InterimReportDto object with the basic data with no linked objects.
     *
     * @param id the Id for the InterimReport
     * @param code the code for the InterimReport
     * @param description the description for the InterimReport
     * @param year the year for the InterimReport
     * @param startDate the startDate for the InterimReport
     * @param endDate the endDate for the InterimReport
     * @param publishDate the publishDate for the InterimReport
     * @param active the active  for the InterimReport
     */
    public InterimReportDto(Integer id, String code, String description, Integer yearId, Date startDate, Date endDate, Date publishDate, Boolean active) {
        this.id = id;
        this.code = code;
        this.description = description;
        this.yearId = yearId;
        this.startDate = startDate;
        this.endDate = endDate;
        this.publishDate = publishDate;
        this.active = active;
    }
    
    /**
     * Constructor to create a InterimReportDto object with the basic data with linked InterimReport objects.
     * 
     * @param id the Id for the InterimReport
     * @param code the code for the InterimReport
     * @param description the description for the InterimReport
     * @param year the year for the InterimReport
     * @param startDate the startDate for the InterimReport
     * @param endDate the endDate for the InterimReport
     * @param publishDate the publishDate for the InterimReport
     * @param active the active  for the InterimReport
     */
    public InterimReportDto(Integer id, String code, String description, AcademicYear year, Date startDate, Date endDate, Date publishDate, Boolean active) {
        this(id, code, description, year != null ? year.id : null, startDate, endDate, publishDate, active )
    }
    
    @Override
    public String toString() {
        return "InterimReportDto [id=" + id + ", code=" + code + ", description=" + description + ", yearId="+ yearId + ", startDate=" + startDate + ", endDate=" + endDate +", publishDate=" + publishDate + ", active=" + active + "]";
    }
    
    public static InterimReportDto mapFromInterimReportEntity(InterimReport interimReport) {
        return new InterimReportDto(interimReport)
    }
    
    public static List<InterimReportDto> mapFromInterimReportsEntities(List<InterimReport> interimReports) {
        return interimReports.collect { interimReport ->  new InterimReportDto(interimReport) };
    }
    
    public static InterimReport mapToInterimReportEntity(InterimReportDto interimReportDto, AcademicYear year) {
        return new InterimReport(interimReportDto.id, interimReportDto.code, interimReportDto.description, year, interimReportDto.startDate, interimReportDto.endDate, interimReportDto.publishDate, interimReportDto.active)
    }
}
