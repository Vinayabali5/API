package uk.ac.reigate.dto.exam

import java.io.Serializable;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonSerialize

import groovy.transform.EqualsAndHashCode
import uk.ac.reigate.domain.academic.Course;
import uk.ac.reigate.domain.exam.CourseOption
import uk.ac.reigate.domain.exam.ExamOption;
import uk.ac.reigate.dto.CourseDto;

@JsonSerialize
@EqualsAndHashCode(includeFields=true)
class CourseOptionDto implements Serializable {
    
    @JsonProperty
    private Integer courseId;
    
    @JsonProperty
    private Integer examOptionId;
    
    /**
     * Default No Args constructor
     */
    CourseOptionDto() {}
    
    /**
     * Constructor to create a CourseOption object
     * 
     * @param course
     * @param option
     */
    CourseOptionDto(Course course, ExamOption examOption) {
        this.courseId = course.id;
        this.examOptionId = examOption.examOptionId;
    }
    
    /**
     * Constructor to create a CourseOption object
     *
     * @param course
     * @param option
     */
    CourseOptionDto(Integer courseId, Integer examOptionId) {
        this.courseId = courseId;
        this.examOptionId = examOptionId;
    }
    
    String toString() {
        return "CourseOption: [course: " + this.courseId + ", examOption: " + this.examOptionId;
    }
    
    public static CourseOptionDto mapFromCourseOptionEntity(CourseOption courseOption) {
        CourseOptionDto output = new CourseOptionDto(courseOption.course, courseOption.examOption);
        return output;
    }
    
    public static List<CourseOptionDto> mapFromCourseOptionEntities(List<CourseOption> courseOptions) {
        List<CourseOptionDto> output = courseOptions.collect { courseOption -> mapFromCourseOptionEntity(courseOption) } ;
        return output;
    }
    
    //	public static CourseOption mapToCourseOptionEntity(CourseOptionDto courseOption) {
    //		return new CourseOption(CourseDto.mapToCourseEntity(courseOption.course, null, null, null, null, null), OptionDto.mapToOptionEntity(courseOption.examOption));
    //	}
    
    //	public static List<CourseOption> mapToCourseOptionEntities(List<CourseOptionDto> courseOptionDtos) {
    //		return courseOptionDtos.collect { courseOptionDto -> mapToCourseOptionEntity(CourseOptionDto) } ;
    //	}
}
