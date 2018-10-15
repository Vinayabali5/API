package uk.ac.reigate.dto;


import groovy.transform.EqualsAndHashCode

import com.fasterxml.jackson.annotation.JsonProperty
import com.fasterxml.jackson.databind.annotation.JsonSerialize

import uk.ac.reigate.domain.Room;
import uk.ac.reigate.domain.Staff;
import uk.ac.reigate.domain.academic.CourseGroup;
import uk.ac.reigate.domain.academic.Period;
import uk.ac.reigate.domain.academic.Timetable

/**
 *
 * JSON serializable DTO containing Timetable data
 *
 */
@JsonSerialize
@EqualsAndHashCode(includeFields=true)
public class TimetableDto implements Serializable {
    
    @JsonProperty
    private Integer id
    
    @JsonProperty
    private Integer courseGroupId
    
    @JsonProperty
    private String _courseGroupSpec
    
    @JsonProperty
    private Integer periodId
    
    @JsonProperty
    PeriodDto _period
    
    @JsonProperty
    private Integer roomId
    
    @JsonProperty
    private RoomDto _room
    
    @JsonProperty
    private Integer teacherId
    
    @JsonProperty
    private String _teacherInitials
    
    @JsonProperty
    private StaffSummaryDto teacher
    
    @JsonProperty
    private Date validFrom
    
    @JsonProperty
    private Date validTo
    
    /**
     * Default No Args constructor
     */
    public TimetableDto() {
    }
    
    /**
     * Constructor to create a TimetableDto object from a Timetable object
     *
     * @param timetable the Timetable object to use for construction
     */
    TimetableDto(Timetable timetable) {
        this.id = timetable.id
        this.courseGroupId = timetable.courseGroup != null ?  timetable.courseGroup.id : null
        this._courseGroupSpec = timetable.courseGroup != null ?  timetable.courseGroup.spec : null
        this.periodId = timetable.period != null ? timetable.period.id : null
        this._period = timetable.period != null ? new PeriodDto(timetable.period) : null
        this.roomId = timetable.room != null ? timetable.room.id : null
        this._room = timetable.room != null ? new RoomDto(timetable.room) : null
        this.teacherId = timetable.teacher != null ? timetable.teacher.id : null
        this._teacherInitials = timetable.teacher != null ? timetable.teacher.initials : null
        this.teacher = timetable.teacher != null ? new StaffSummaryDto(timetable.teacher) : null
        this.validFrom = timetable.validFrom;
        this.validTo = timetable.validTo;
    }
    
    @Override
    public String toString() {
        return "Timetable [id=" + id + ", courseGroup=" + courseGroupId + ", period=" + periodId + ", room=" + roomId + ", teacherId=" + teacherId + ", validFrom=" + validFrom + ", validTo=" + validTo + "]";
    }
    
    public static TimetableDto mapFromEntity(Timetable timetable) {
        return new TimetableDto(timetable)
    }
    
    public static List<TimetableDto> mapFromList(List<Timetable> timetables) {
        return  timetables.collect { timetable ->  new TimetableDto(timetable) };
    }
    
    @Deprecated
    public static Timetable mapToTimetableEntity(TimetableDto timetableDto, CourseGroup courseGroup, Period period, Room room, Staff teacher) {
        return new Timetable(timetableDto.id, courseGroup, period, room, teacher, timetableDto.validFrom, timetableDto.validTo)
    }
}
