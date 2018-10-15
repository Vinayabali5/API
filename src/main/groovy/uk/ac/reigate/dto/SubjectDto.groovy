package uk.ac.reigate.dto;


import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;

import groovy.transform.EqualsAndHashCode;
import io.swagger.annotations.ApiModel
import io.swagger.annotations.ApiModelProperty;
import uk.ac.reigate.domain.lookup.Subject

/**
 *
 * JSON serializable DTO containing Subject data
 *
 */
@JsonSerialize
@EqualsAndHashCode(includeFields=true)
@ApiModel(description = "")
public class SubjectDto implements Serializable {
    
    @ApiModelProperty(value = "")
    @JsonProperty
    private Integer id;
    
    @ApiModelProperty(value = "")
    @JsonProperty
    private String code;
    
    @ApiModelProperty(value = "")
    @JsonProperty
    private String description;
    
    /**
     * Default No Args constructor
     */
    public SubjectDto() {
    }
    
    /**
     * Constructor to create a SubjectDto object
     *
     * @param id the Id for the Subject
     * @param code the code for the Subject
     * @param description the description for the Subject
     */
    public SubjectDto(Integer id, String code, String description) {
        this.id = id;
        this.code = code;
        this.description = description;
    }
    
    /**
     * Constructor to create a SubjectDto object from a Subject object
     *
     * @param subject the Subject object to use for construction
     */
    SubjectDto(Subject subject) {
        this.id = subject.id;
        this.code = subject.code;
        this.description = subject.description;
    }
    
    @Override
    public String toString() {
        return "SubjectDTO [id=" + id + ", code=" + code + ", description=" + description + "]";
    }
    
    public static SubjectDto mapFromSubjectEntity(Subject subject) {
        return new SubjectDto(subject);
    }
    
    public static List<SubjectDto> mapFromSubjectsEntities(List<Subject> subjects) {
        List<SubjectDto> output = subjects.collect { subject ->  new SubjectDto(subject) };
        return output
    }
    
    public static Subject mapToSubjectEntity(SubjectDto subjectDto) {
        return new Subject(subjectDto.id, subjectDto.code, subjectDto.description)
    }
}
