package uk.ac.reigate.dto

import groovy.transform.EqualsAndHashCode

import com.fasterxml.jackson.annotation.JsonProperty
import com.fasterxml.jackson.databind.annotation.JsonSerialize

import uk.ac.reigate.domain.Staff
import uk.ac.reigate.domain.academic.AcademicYear
import uk.ac.reigate.domain.academic.Course
import uk.ac.reigate.domain.academic.CourseGroup
import uk.ac.reigate.domain.academic.Department
import uk.ac.reigate.domain.lookup.YearGroup

/**
 *
 * JSON serializable DTO containing CourseGroup data
 *
 */
@JsonSerialize
@EqualsAndHashCode(includeFields=true)
public class CourseGroupDto implements Serializable {
    
    @JsonProperty
    private Integer id;
    
    @JsonProperty
    private Integer yearGroupId;
    
    @JsonProperty
    private String _yearGroupDescription;
    
    @JsonProperty
    private Integer courseId;
    
    @JsonProperty
    private CourseDto _course
    
    @JsonProperty
    private Integer yearId;
    
    @JsonProperty
    private String _yearDescription;
    
    @JsonProperty
    private String code;
    
    @JsonProperty
    //@JsonFormat(pattern = "dd/MM/yyyy")
    private Date startDate;
    
    @JsonProperty
    //@JsonFormat(pattern = "dd/MM/yyyy")
    private Date endDate;
    
    @JsonProperty
    private Integer departmentId;
    
    @JsonProperty
    private String _departmentDescription;
    
    @JsonProperty
    private Integer courseLeaderId;
    
    @JsonProperty
    private String _courseLeaderInitials;
    
    @JsonProperty
    private Boolean displayOnTimetable;
    
    @JsonProperty
    private Boolean hasRegister;
    
    @JsonProperty
    private String notes;
    
    @JsonProperty
    private String spec;
    
    @JsonProperty
    private Integer plh;
    
    @JsonProperty
    private Integer peeph;
    
    /**
     * Default No Args constructor
     */
    public CourseGroupDto() {
    }
    
    /**
     * Constructor to create a CourseGroupDto object from a CourseGroup object
     *
     * @param courseGroup the CourseGroup object to use for construction
     */
    public CourseGroupDto(CourseGroup courseGroup) {
        this.id = courseGroup.id;
        this.yearGroupId = courseGroup.yearGroup ? courseGroup.yearGroup.id : null;
        this.courseId = courseGroup.course.id;
        this._course = new CourseDto(courseGroup.course);
        this.yearId = courseGroup.year ? courseGroup.year.id : null;
        this.code = courseGroup.code;
        this.startDate = courseGroup.startDate;
        this.endDate = courseGroup.endDate;
        this.departmentId = courseGroup.department ? courseGroup.department.id : null;
        this.courseLeaderId = courseGroup.courseLeader ? courseGroup.courseLeader.id : null;
        this.displayOnTimetable = courseGroup.displayOnTimetable;
        this.hasRegister = courseGroup.hasRegister;
        this.notes = courseGroup.notes;
        this.spec = courseGroup.spec;
        this.plh = courseGroup.plh;
        this.peeph = courseGroup.peeph;
        
        this._courseLeaderInitials = courseGroup.courseLeader != null ? courseGroup.courseLeader.initials : ''
        this._departmentDescription = courseGroup.department != null ? courseGroup.department.description : ''
        this._yearDescription = courseGroup.year != null ? courseGroup.year.description : ''
        this._yearGroupDescription = courseGroup.yearGroup != null ? courseGroup.yearGroup.description : ''
    }
    
    /**
     * Constructor to create a CourseGroupDto object
     * 
     * @param id The Id for the CourseGroup
     * @param yearGroupId The yearGroupId for the CourseGroup
     * @param courseId The courseId for the CourseGroup
     * @param yearId The yaerId for the CourseGroup
     * @param code The code for the CourseGroup
     * @param department The Department object for the CourseGroup
     * @param courseLeaderId The courseLeaderId for the CourseGroup
     * @param displayOnTimetable The displayOnTimetable of the CourseGroup
     * @param hasRegister The hasRegister of for the CourseGroup
     * @param notes The notes of the CourseGroup
     * @param spec The spec of the CourseGroup
     * @param plh The plh of the CourseGroup
     * @param peeph The peeph of the CourseGroup
     */
    public CourseGroupDto(Integer id, Integer yearGroupId, Integer courseId, Integer yearId, String code, Integer departmentId, Integer courseLeaderId, Boolean displayOnTimetable, Boolean hasRegister, String notes, String spec, Integer plh, Integer peeph){
        this.id = id;
        this.yearGroupId = yearGroupId;
        this.courseId = courseId;
        this.yearId = yearId;
        this.code = code;
        this.departmentId = departmentId;
        this.courseLeaderId = courseLeaderId;
        this.displayOnTimetable = displayOnTimetable;
        this.hasRegister = hasRegister;
        this.notes = notes;
        this.spec = spec;
        this.plh = plh;
        this.peeph = peeph;
    }
    
    /**
     * Constructor to create a CourseGroupDto object
     * 
     * @param id The Id for the CourseGroup
     * @param yearGroup The YearGroup object for the CourseGroup
     * @param course The Course object for the CourseGroup
     * @param year The AcademicYear object for the CourseGroup
     * @param code The code for the CourseGroup
     * @param department The Department object for the CourseGroup
     * @param courseLeader The CourseLeader object for the CourseGroup
     * @param displayOnTimetable The displayOnTimetable of the CourseGroup
     * @param hasRegister The hasRegister of for the CourseGroup
     * @param notes The notes of the CourseGroup
     * @param spec The spec of the CourseGroup
     * @param plh The plh of the CourseGroup
     * @param peeph The peeph of the CourseGroup
     */
    public CourseGroupDto(Integer id, YearGroup yearGroup, Course course, AcademicYear year, String code, Department department, Staff courseLeader, Boolean displayOnTimetable, Boolean hasRegister, String notes, String spec, Integer plh, Integer peeph){
        this(id, yearGroup != null ? yearGroup.id : null, course != null ? course.id : null, year != null ? year.id : null, code, department != null ? department.id : null, courseLeader != null ? courseLeader.id : null, displayOnTimetable, hasRegister, notes, spec, plh, peeph)
    }
    
    @Override
    public String toString() {
        return "CourseGroupDto [id=" + id + ", yearGroup=" + yearGroupId + ", course=" + courseId + ", year=" + yearId + ", code=" + code + ", department=" + departmentId + ", courseLeader=" + courseLeaderId + ", displayOnTimetable=" + displayOnTimetable + ", hasRegister=" + hasRegister + ", notes=" + notes + ", spec=" + spec + ", plh=" + plh + ", peeph=" + peeph + "]";
    }
    
    public static CourseGroupDto mapFromEntity(CourseGroup courseGroup) {
        return new CourseGroupDto(courseGroup)
    }
    
    public static List<CourseGroupDto> mapFromList(List<CourseGroup> courseGroups) {
        return courseGroups.collect { courseGroup ->  new CourseGroupDto(courseGroup) };
    }
    
    @Deprecated
    public static CourseGroupDto mapFromCourseGroupEntity(CourseGroup courseGroup) {
        return new CourseGroupDto(courseGroup)
    }
    
    @Deprecated
    public static List<CourseGroupDto> mapFromCourseGroupsEntities(List<CourseGroup> courseGroups) {
        return courseGroups.collect { courseGroup ->  new CourseGroupDto(courseGroup) };
    }
    
    @Deprecated
    public static CourseGroup mapToCourseGroupEntity(CourseGroupDto courseGroupDto, YearGroup yearGroup, Course course, AcademicYear year, Department department, Staff courseLeader) {
        return new CourseGroup(courseGroupDto.id, yearGroup, course, year, courseGroupDto.code, department, courseLeader, courseGroupDto.displayOnTimetable, courseGroupDto.hasRegister, courseGroupDto.notes, courseGroupDto.spec, courseGroupDto.plh, courseGroupDto.peeph)
    }
}
