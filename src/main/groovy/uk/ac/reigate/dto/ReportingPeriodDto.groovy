package uk.ac.reigate.dto;


import java.util.Date

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;

import groovy.transform.EqualsAndHashCode;
import uk.ac.reigate.domain.academic.AcademicYear
import uk.ac.reigate.domain.lookup.ReportingPeriod

/**
 *
 * JSON serializable DTO containing ReportingPeriod data
 *
 */
@JsonSerialize
@EqualsAndHashCode(includeFields=true)
public class ReportingPeriodDto implements Serializable {
    
    @JsonProperty
    private Integer id;
    
    @JsonProperty
    private Integer academicYearId;
    
    @JsonProperty
    private String name;
    
    @JsonProperty
    private Date startDate;
    
    @JsonProperty
    private Date endDate;
    
    /**
     * Default No Args constructor
     */
    public ReportingPeriodDto() {
    }
    
    /**
     * Constructor to create a ReportingPeriodDto object
     *
     * @param id the Id for the ReportingPeriod
     * @param code the code for the ReportingPeriod
     * @param description the description for the ReportingPeriod
     */
    public ReportingPeriodDto(Integer id, Integer academicYearId, String name, Date startDate, Date endDate) {
        this.id = id;
        this.academicYearId = academicYearId;
        this.name = name;
        this.startDate = startDate;
        this.endDate = endDate;
    }
    
    /**
     * Constructor to create a ReportingPeriodDto object from a ReportingPeriod object
     *
     * @param reportingPeriod the ReportingPeriod object to use for construction
     */
    ReportingPeriodDto(ReportingPeriod reportingPeriod) {
        this.id = reportingPeriod.id;
        this.academicYearId = reportingPeriod.academicYear != null ? reportingPeriod.academicYear.id : null;
        this.name = reportingPeriod.name;
        this.startDate = reportingPeriod.startDate;
        this.endDate = reportingPeriod.endDate;
    }
    
    ReportingPeriodDto(Integer id, AcademicYear academicYear, String name, Date startDate, Date endDate){
        this(id, academicYear != null ? academicYear.id : null, name, startDate, endDate);
    }
    
    @Override
    public String toString() {
        return "ReportingPeriodDto [id=" + id + ", name=" + name + ", startDate=" + startDate + ", endDate=" + endDate + "]";
    }
    
    public static ReportingPeriodDto mapFromReportingPeriodEntity(ReportingPeriod reportingPeriod) {
        return new ReportingPeriodDto(reportingPeriod);
    }
    
    public static List<ReportingPeriodDto> mapFromReportingPeriodsEntities(List<ReportingPeriod> reportingPeriods) {
        List<ReportingPeriodDto> output = reportingPeriods.collect { reportingPeriod ->  new ReportingPeriodDto(reportingPeriod) };
        return output
    }
    
    public static ReportingPeriod mapToReportingPeriodEntity(ReportingPeriodDto reportingPeriodDto, AcademicYear academicYear ) {
        return new ReportingPeriod(reportingPeriodDto.id, academicYear, reportingPeriodDto.name, reportingPeriodDto.startDate, reportingPeriodDto.endDate)
    }
}
