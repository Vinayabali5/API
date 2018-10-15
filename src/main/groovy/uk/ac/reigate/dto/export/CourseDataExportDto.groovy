package uk.ac.reigate.dto.export

import com.fasterxml.jackson.annotation.JsonProperty

import uk.ac.reigate.domain.academic.Course

class CourseDataExportDto {
    
    @JsonProperty
    String id
    
    @JsonProperty
    String level
    
    @JsonProperty
    String code
    
    @JsonProperty
    String title
    
    @JsonProperty
    String description
    
    @JsonProperty
    String entryRequirements
    
    @JsonProperty
    String specialRequirements
    
    @JsonProperty
    String link
    
    /**
     * Default NoArgs Constructor
     */
    CourseDataExportDto() {
    }
    
    /**
     * Constructor using a Course data object
     * 
     * @param course a Course data object to use to create the export data
     */
    CourseDataExportDto(Course course) {
        this.id = course.id
        this.level = course.level.description
        this.code = course.spec
        this.title = course.level.description + ' ' + course.subject.description
        this.description = course.courseSummary
        this.entryRequirements = course.entryRequirements
        this.link = course.externalLink
    }
}
