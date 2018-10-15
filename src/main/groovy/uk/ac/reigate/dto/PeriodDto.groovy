package uk.ac.reigate.dto;


import java.sql.Time

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;

import groovy.transform.EqualsAndHashCode;
import uk.ac.reigate.domain.academic.Block
import uk.ac.reigate.domain.academic.Period

/**
 *
 * JSON serializable DTO containing Period data
 *
 */
@JsonSerialize
@EqualsAndHashCode(includeFields=true)
public class PeriodDto implements Serializable {
    
    @JsonProperty
    private Integer id;
    
    @JsonProperty
    private String code;
    
    @JsonProperty
    private String description;
    
    @JsonProperty
    private Integer blockId;
    
    @JsonProperty
    private Date startTime;
    
    @JsonProperty
    private Date endTime;
    
    @JsonProperty
    private Integer day;
    
    @JsonProperty
    private Integer dayPeriod;
    
    @JsonProperty
    private String _blockDescription
    
    @JsonProperty
    private Integer boxNo
    
    @JsonProperty
    private Integer cristalPeriod
    
    /**
     * Default No Args constructor
     */
    public PeriodDto() {
    }
    
    /**
     * Constructor to create a PeriodDto object from a Period object
     *
     * @param period the Period object to use for construction
     */
    PeriodDto(Period period) {
        this.id = period.id;
        this.code = period.code;
        this.description = period.description;
        this.blockId = period.block != null ? period.block.id : null;
        this.startTime = period.startTime;
        this.endTime = period.endTime;
        this.day = period.day;
        this.dayPeriod = period.dayPeriod;
        this._blockDescription = period.block.description;
        this.boxNo = period.boxNo;
        this.cristalPeriod = period.cristalPeriod;
    }
    /**
     * Constructor to create a PeriodDto object with the basic data with no linked objects.
     *
     * @param id the Id for the Period
     * @param code the code for the Period
     * @param description the description for the Period
     * @Param blockId the blockId of the Period
     * @Param startTime the startTime  for the Period
     * @Param endTime the endTime for the Period
     * @Param day the day for the Period
     * @Param dayPeriod the dayPeriod for the Period
     */
    public PeriodDto(Integer id, String code, String description, Integer blockId, Date startTime, Date endTime, Integer day, Integer dayPeriod, String blockDesc, Integer boxNo, Integer cristalPeriod) {
        this.id = id;
        this.code = code;
        this.description = description;
        this.blockId = blockId;
        this.startTime = startTime;
        this.endTime = endTime;
        this.day = day;
        this.dayPeriod = dayPeriod;
        this._blockDescription =  blockDesc;
        this.boxNo = boxNo;
        this.cristalPeriod = cristalPeriod;
    }
    
    /**
     * Constructor to create a PeriodDto object with the basic data and the linked Block objects.
     *
     * @param id the Id for the Period
     * @param code the code for the Period
     * @param description the description for the Period
     * @Param block the block of the Period
     * @Param startTime the startTime  for the Period
     * @Param endTime the endTime for the Period
     * @Param day the day for the Period
     * @Param dayPeriod the dayPeriod for the Period
     */
    public PeriodDto(Integer id, String code, String description, Block block, Date startTime, Date endTime, Integer day, Integer dayPeriod, Integer boxNo, Integer cristalPeriod) {
        this(id, code, description, block != null ? block.id : null, startTime, endTime, day, dayPeriod, block != null ? block.description : null, boxNo, cristalPeriod)
    }
    
    @Override
    public String toString() {
        return "PeriodDto [id=" + id + ", code=" + code + ", description=" + description + ", block=" + blockId + ", startTime=" + startTime + ", endTime=" + endTime + ", day=" + day + ", dayPeriod=" + dayPeriod + ", boxNo=" + boxNo +", cristalPeriod=" + cristalPeriod +"]";
    }
    
    public static PeriodDto mapFromPeriodEntity(Period period) {
        return new PeriodDto(period)
    }
    
    public static List<PeriodDto> mapFromPeriodsEntities(List<Period> periods) {
        return periods.collect { period ->  new PeriodDto(period) };
    }
    
    public static Period mapToPeriodEntity(PeriodDto periodDto, block) {
        return new Period(periodDto.id, periodDto.code, periodDto.description, block, periodDto.startTime, periodDto.endTime, periodDto.day, periodDto.dayPeriod, periodDto.boxNo, periodDto.cristalPeriod)
    }
}
