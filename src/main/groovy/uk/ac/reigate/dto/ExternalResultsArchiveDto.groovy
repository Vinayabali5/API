package uk.ac.reigate.dto;


import java.util.Date;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;

import groovy.transform.EqualsAndHashCode;
import uk.ac.reigate.domain.academic.ExternalResultsArchive
import uk.ac.reigate.domain.academic.Student;

/**
 *
 * JSON serializable DTO containing ExternalResultsArchive data
 *
 */
@JsonSerialize
@EqualsAndHashCode(includeFields=true)
public class ExternalResultsArchiveDto implements Serializable {
    
    @JsonProperty
    private Integer id;
    
    @JsonProperty
    private Integer studentId
    
    @JsonProperty
    private String courseSpec
    
    @JsonProperty
    private String levelDescription
    
    @JsonProperty
    private String subjectDescription
    
    @JsonProperty
    private String syllabus
    
    @JsonProperty
    private String grade
    
    @JsonProperty
    private String mark
    
    @JsonProperty
    private String maxMark
    
    @JsonProperty
    private Date dateAchieved
    
    @JsonProperty
    private String series
    
    @JsonProperty
    private String year
    
    @JsonProperty
    private String examType
    
    /**
     * Default No Args constructor
     */
    public ExternalResultsArchiveDto() {
    }
    
    /**
     * Constructor to create an ExternalResultsArchiveDto object from a ExternalResultsArchive object
     *
     * @param externalResultsArchive the ExternalResultsArchive object to use for construction
     */
    ExternalResultsArchiveDto(ExternalResultsArchive externalResultsArchive) {
        this.id = externalResultsArchive.id;
        this.studentId = externalResultsArchive.student != null ? externalResultsArchive.student.id : null;
        this.courseSpec = externalResultsArchive.courseSpec;
        this.levelDescription = externalResultsArchive.levelDescription;
        this.subjectDescription = externalResultsArchive.subjectDescription;
        this.syllabus = externalResultsArchive.syllabus;
        this.grade = externalResultsArchive.grade;
        this.mark = externalResultsArchive.mark;
        this.maxMark = externalResultsArchive.maxMark;
        this.dateAchieved = externalResultsArchive.dateAchieved;
        this.series = externalResultsArchive.series;
        this.year = externalResultsArchive.year;
        this.examType = externalResultsArchive.examType;
    }
    
    /**
     * Constructor to create a ExternalResultsArchiveDto object
     *
     * @param id the Id for the ExternalResultsArchive
     * @param code the code for the ExternalResultsArchive
     * @param description the description for the ExternalResultsArchive
     */
    public ExternalResultsArchiveDto(Integer id, Integer studentId, String courseSpec, String levelDescription, String subjectDescription, String syllabus, String grade, String mark, String maxMark, Date dateAchieved, String series, String year, String examType) {
        this.id = id;
        this.studentId = studentId;
        this.courseSpec = courseSpec;
        this.levelDescription = levelDescription;
        this.subjectDescription = subjectDescription;
        this.syllabus = syllabus;
        this.grade = grade;
        this.mark = mark;
        this.maxMark = maxMark;
        this.dateAchieved = dateAchieved;
        this.series = series;
        this.year = year;
        this.examType = examType;
    }
    
    /**
     * Constructor to create a ExternalResultsArchiveDto object
     * 
     * @param id the Id for the ExternalResultsArchive
     * @param student the Student object for the ExternalResultsArchive
     * @param courseSpec the Id for the ExternalResultsArchive
     * @param levelDescription the Id for the ExternalResultsArchive
     * @param subjectDescription the Id for the ExternalResultsArchive
     * @param syllabus the Id for the ExternalResultsArchive
     * @param grade the Id for the ExternalResultsArchive
     * @param mark the Id for the ExternalResultsArchive
     * @param maxMark the Id for the ExternalResultsArchive
     * @param dateAchieved the Id for the ExternalResultsArchive
     * @param series the Id for the ExternalResultsArchive
     * @param year the Id for the ExternalResultsArchive
     * @param examType the Id for the ExternalResultsArchive
     */
    public ExternalResultsArchiveDto(Integer id, Student student, String courseSpec, String levelDescription, String subjectDescription, String syllabus, String grade, String mark, String maxMark, Date dateAchieved, String series, String year, String examType) {
        this(id, student !=  null ? student.id : null,  courseSpec, levelDescription, subjectDescription, syllabus, grade, mark, maxMark, dateAchieved, series, year, examType)
    }
    
    @Override
    public String toString() {
        return "ExternalResultsArchiveDto [id=" + id + ", student=" + studentId + ", courseSpec=" + courseSpec + ", levelDescription=" + levelDescription + ", subjectDescription=" + subjectDescription + ", syllabus= " + syllabus + ", grade= " + grade + ", mark= " + mark + ", maxMark= " + maxMark + ", dateAchieved= " + dateAchieved + ", series= " + series + ", year= " + year + ", examType= " + examType + "]";
    }
    
    public static ExternalResultsArchiveDto mapFromExternalResultsArchiveEntity(ExternalResultsArchive externalResultsArchive) {
        return new ExternalResultsArchiveDto(externalResultsArchive)
    }
    
    public static List<ExternalResultsArchiveDto> mapFromExternalResultsArchivesEntities(List<ExternalResultsArchive> externalResultsArchives) {
        List<ExternalResultsArchiveDto> output = externalResultsArchives.collect { externalResultsArchive ->  new ExternalResultsArchiveDto(externalResultsArchive) };
        return output
    }
}
