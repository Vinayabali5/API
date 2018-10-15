package uk.ac.reigate.dto.search

import com.fasterxml.jackson.annotation.JsonProperty
import com.fasterxml.jackson.databind.annotation.JsonSerialize

import groovy.transform.EqualsAndHashCode
import uk.ac.reigate.domain.academic.Course
import uk.ac.reigate.dto.InitialAssessmentLevelDto
import uk.ac.reigate.dto.SubjectDto


/**
 *
 * JSON serializable DTO containing StudentSearch data
 *
 */
@JsonSerialize
@EqualsAndHashCode(includeFields=true)
class CourseSearchDto {
    
    @JsonProperty
    Integer courseId
    
    @JsonProperty
    String spec
    
    @JsonProperty
    String description
    
    @JsonProperty
    String _validFromDescription
    
    @JsonProperty
    String _validToDescription
    
    @JsonProperty
    String notes
    
    /** 
     * Default No Args constructor
     */
    CourseSearchDto() {
        this.courseId = null
        this.spec = ''
        this.description = ''
        this._validFromDescription = ''
        this._validToDescription = ''
        this.notes = ''
    }
    
    /**
     * Constructor the uses a Course object
     *     
     * @param course The Course object to create the return
     */
    CourseSearchDto(Course course) {
        this.courseId = course.id
        this.spec = course.spec
        this.description = (course.level != null ? course.level.description : '')
        this.description += ' - '
        this.description +=  (course.subject != null ? course.subject.description : '')
        this._validFromDescription = course.validFrom!= null?course.validFrom.description:''
        this._validToDescription = course.validTo!=null?course.validTo.description:''
        this.notes = course.notes
    }
}
