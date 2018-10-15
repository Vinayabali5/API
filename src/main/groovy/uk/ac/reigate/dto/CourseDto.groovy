package uk.ac.reigate.dto

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;

import groovy.transform.EqualsAndHashCode;
import uk.ac.reigate.domain.academic.AcademicYear
import uk.ac.reigate.domain.academic.Course
import uk.ac.reigate.domain.exam.ExamBoard
import uk.ac.reigate.domain.lookup.Level
import uk.ac.reigate.domain.lookup.Subject

/**
 *
 * JSON serializable DTO containing Course data
 *
 */

@JsonSerialize
@EqualsAndHashCode(includeFields=true)
public class CourseDto implements Serializable {
    
    @JsonProperty
    private Integer id;
    
    @JsonProperty
    private Integer levelId;
    
    @JsonProperty
    private String _levelDescription;
    
    @JsonProperty
    private Integer subjectId;
    
    @JsonProperty
    private String _subjectDescription;
    
    @JsonProperty
    private Integer glh;
    
    @JsonProperty
    private String learningAimReference
    
    @JsonProperty
    private Integer examBoardId;
    
    @JsonProperty
    private String _examBoardDescription;
    
    @JsonProperty
    private String syllabusCode;
    
    @JsonProperty
    private Integer validFromId;
    
    @JsonProperty
    private String _validFromCode
    
    @JsonProperty
    private String _validFromDescription
    
    @JsonProperty
    private Integer validToId;
    
    @JsonProperty
    private String _validToCode
    
    @JsonProperty
    private String _validToDescription
    
    @JsonProperty
    private String locationPostcode;
    
    @JsonProperty
    private String subjectSectorArea;
    
    @JsonProperty
    private String notes;
    
    @JsonProperty
    private String spec;
    
    /**
     * Default No Args constructor
     */
    public CourseDto() {
    }
    
    /**
     * Constructor to create a CourseDto object from a Course object
     *
     * @param course the Course object to use for construction
     */
    CourseDto(Course course) {
        this.id = course.id;
        this.levelId = course.level != null ? course.level.id : null;
        this._levelDescription = course.level != null ? course.level.description : ''
        this.subjectId = course.subject != null ? course.subject.id : null;
        this._subjectDescription = course.subject != null ? course.subject.description : ''
        this.glh = course.glh;
        this.learningAimReference = course.learningAimReference;
        this.examBoardId = course.examBoard != null ? course.examBoard.id : null;
        this._examBoardDescription = course.examBoard != null ? course.examBoard.description : ''
        this.syllabusCode = course.syllabusCode;
        this.validFromId = course.validFrom != null ? course.validFrom.id : null;
        this._validFromCode = course.validFrom != null ? course.validFrom.code : ''
        this._validFromDescription = course.validFrom != null ? course.validFrom.description : ''
        this.validToId = course.validTo != null ? course.validTo.id : null;
        this._validToCode = course.validTo != null ? course.validTo.code : ''
        this._validToDescription = course.validTo != null ? course.validTo.description : ''
        this.locationPostcode = course.locationPostcode;
        this.subjectSectorArea = course.subjectSectorArea;
        this.notes = course.notes;
        this.spec = course.spec;
    }
    
    /**
     * Constructor to create a CourseDto object
     *
     */
    public CourseDto(Integer id, Integer levelId, Integer subjectId, Integer glh, String learningAimReference, Integer examBoardId, String syllabusCode, Integer validFromId, Integer validToId, String locationPostcode, String subjectSectorArea, String notes, String spec){
        this.id = id;
        this.levelId = levelId;
        this.subjectId = subjectId;
        this.glh = glh;
        this.learningAimReference = learningAimReference;
        this.examBoardId = examBoardId;
        this.syllabusCode = syllabusCode;
        this.validFromId = validFromId;
        this.validToId = validToId;
        this.locationPostcode = locationPostcode;
        this.subjectSectorArea = subjectSectorArea;
        this.notes = notes;
        this.spec = spec;
    }
    
    /**
     * Constructor to create a CourseDto object
     * 
     * @param id The Id for the Course
     * @param level The Level object for the Course
     * @param subject The Subject object for the course
     * @param glh The glh of the Course
     * @param learningAimReference The learningAimReference of the Course
     * @param examBoard The ExamBoard object of the Course
     * @param syllabusCode The syllabusCode of the Course
     * @param validFrom The validFrom of the Course
     * @param validTo The validTo of the Course
     * @param locationPostcode The locationPostcode of the Course
     * @param subjectSectorArea The subjectSectorArea of the Course
     * @param notes The notes of the Course
     * @param spec The spec of the Course
     */
    public CourseDto(Integer id, Level level, Subject subject, Integer glh, String learningAimReference, ExamBoard examBoard, String syllabusCode, AcademicYear validFrom, AcademicYear validTo, String locationPostcode, String subjectSectorArea, String notes, String spec){
        this(id, level != null ? level.id : null, subject != null ? subject.id : null, glh, learningAimReference, examBoard != null ? examBoard.id : null, syllabusCode, validFrom != null ? validFrom.id : null, validTo != null ? validTo.id : null, locationPostcode, subjectSectorArea, notes, spec)
    }
    
    @Override
    public String toString() {
        return "CourseDto [id=" + id + ", level=" + levelId + ", subject=" + subjectId + ", glh=" + glh + ", learningAimReference=" + learningAimReference + ", examBoard=" + examBoardId + ", syllabusCode=" + syllabusCode + ", validFrom=" + validFromId + ", validTo=" + validToId + ", locationPostcode=" + locationPostcode + ", subjectSectorArea=" + subjectSectorArea + ", notes=" + notes + ", spec=" + spec + "]";
    }
    
    public static CourseDto mapFromCourseEntity(Course course) {
        return new CourseDto(course);
    }
    
    public static List<CourseDto> mapFromCoursesEntities(List<Course> courses) {
        return courses.collect { course ->  new CourseDto(course) };
    }
    
    public static Course mapToCourseEntity(CourseDto courseDto, Level level, Subject subject, ExamBoard examBoard, AcademicYear validFrom, AcademicYear validTo) {
        return new Course(courseDto.id, level, subject, courseDto.glh, courseDto.learningAimReference, examBoard, courseDto.syllabusCode, validFrom, validTo, courseDto.locationPostcode, courseDto.subjectSectorArea, courseDto.notes, courseDto.spec)
    }
}
