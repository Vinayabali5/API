package uk.ac.reigate.dto.exam

import java.io.Serializable;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonSerialize

import groovy.transform.EqualsAndHashCode
import uk.ac.reigate.domain.academic.Course;
import uk.ac.reigate.domain.exam.CourseSyllabus
import uk.ac.reigate.domain.exam.Syllabus;
import uk.ac.reigate.dto.CourseDto;;

@JsonSerialize
@EqualsAndHashCode(includeFields=true)
class CourseSyllabusDto implements Serializable {
    
    @JsonProperty
    private Integer courseId;
    
    @JsonProperty
    private Integer syllabusId;
    
    /**
     * Default No Args constructor
     */
    CourseSyllabusDto() {}
    
    /**
     * Constructor to create a CourseSyllabus object
     * 
     * @param courseId
     * @param syllabusId
     */
    CourseSyllabusDto(Course course, Syllabus syllabus) {
        this.courseId = course.id;
        this.syllabusId = syllabus.syllabusId;
    }
    
    /**
     * Constructor to create a CourseSyllabus object
     *
     * @param courseId
     * @param syllabusId
     */
    CourseSyllabusDto(Integer courseId, Integer syllabusId) {
        this.courseId = courseId;
        this.syllabusId = syllabusId;
    }
    
    String toString() {
        return "CourseSyllabus: [course: " + this.courseId + ", syllabus: " + this.syllabusId;
    }
    
    public static CourseSyllabusDto mapFromCourseSyllabusEntity(CourseSyllabus courseSyllabus) {
        CourseSyllabusDto output = new CourseSyllabusDto(courseSyllabus.course, courseSyllabus.syllabus);
        return output;
    }
    
    public static List<CourseSyllabusDto> mapFromCourseSyllabusEntities(List<CourseSyllabus> courseSyllabi) {
        List<CourseSyllabusDto> output = courseSyllabi.collect { courseSyllabus -> mapFromCourseSyllabusEntity(courseSyllabus) } ;
        return output;
    }
    
    //	public static CourseSyllabus mapToCourseSyllabusEntity(CourseSyllabusDto courseSyllabi) {
    //		return new CourseSyllabus(CourseDto.mapToCourseEntity(courseSyllabi.course, null, null, null, null, null), SyllabusDto.mapToSyllabusEntity(courseSyllabi.syllabus));
    //	}
    //
    //	public static List<CourseSyllabus> mapToCourseSyllabusEntities(List<CourseSyllabusDto> courseSyllabusDtos) {
    //		return courseSyllabusDtos.collect { courseSyllabusDto -> mapToCourseSyllabusEntity(CourseSyllabusDto) } ;
    //	}
}
