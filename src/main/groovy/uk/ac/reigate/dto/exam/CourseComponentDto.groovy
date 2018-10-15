package uk.ac.reigate.dto.exam

import java.io.Serializable;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonSerialize

import groovy.transform.EqualsAndHashCode
import uk.ac.reigate.domain.exam.CourseComponent
import uk.ac.reigate.domain.exam.CourseOption
import uk.ac.reigate.domain.exam.ExamComponent;;;;

@JsonSerialize
@EqualsAndHashCode(includeFields=true)
class CourseComponentDto implements Serializable {
    
    @JsonProperty
    private CourseOptionDto courseOption;
    
    @JsonProperty
    private Integer examComponentId;
    
    /**
     * Default No Args constructor
     */
    CourseComponentDto() {}
    
    /**
     * Constructor to create a CourseOption object
     * 
     * @param course
     * @param option
     */
    CourseComponentDto(CourseOption courseOption, ExamComponent examComponent) {
        this.courseOption = CourseOptionDto.mapFromCourseOptionEntity(courseOption);
        this.examComponentId = examComponent.examComponentId;
    }
    
    CourseComponentDto(Integer courseId, Integer examOptionId, Integer examComponentId) {
        this.courseOption = CourseOptionDto(courseId, examOptionId);
        this.examComponentId = examComponentId;
    }
    
    String toString() {
        return "CourseComponent: [courseOption: " + this.courseOption + ", examComponent: " + this.examComponentId;
    }
    
    public static CourseComponentDto mapFromCourseComponentEntity(CourseComponent courseComponent) {
        CourseComponentDto output = new CourseComponentDto(courseComponent.courseOption, courseComponent.examComponent);
        return output;
    }
    
    public static List<CourseComponentDto> mapFromCourseComponentEntities(List<CourseComponent> courseComponents) {
        List<CourseComponentDto> output = courseComponents.collect { courseComponent -> mapFromCourseComponentEntity(courseComponent) } ;
        return output;
    }
    
    //	public static CourseComponent mapToCourseComponentEntity(CourseComponentDto courseComponent) {
    //		CourseOption courseOption = CourseOptionDto.mapToCourseOptionEntity(courseComponent.courseOption);
    //		ExamComponent examComponent = ExamComponentDto.mapToExamComponentEntity(courseComponent.examComponent);
    //
    //		return new CourseComponent(courseOption, examComponent);
    //	}
    //
    //	public static List<CourseComponent> mapToCourseOptionEntities(List<CourseComponentDto> courseComponentDtos) {
    //		return courseComponentDtos.collect { courseComponentDto -> mapToCourseComponentEntity(CourseComponentDto) } ;
    //	}
}
