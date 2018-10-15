package uk.ac.reigate.dto

import groovy.transform.EqualsAndHashCode
import uk.ac.reigate.domain.academic.CourseGroup;
import uk.ac.reigate.domain.academic.Student;
import uk.ac.reigate.domain.ilp.Correspondence
import uk.ac.reigate.domain.ilp.CorrespondenceType
import uk.ac.reigate.domain.ilp.Letter;

import java.io.Serializable
import java.util.Date;

import javax.persistence.OneToOne;

import com.fasterxml.jackson.annotation.JsonFormat
import com.fasterxml.jackson.annotation.JsonProperty
import com.fasterxml.jackson.databind.annotation.JsonSerialize
/**
 *
 * JSON serializable DTO containing Correspondence data
 *
 */
@JsonSerialize
@EqualsAndHashCode(includeFields=true)
public class CorrespondenceDto implements Serializable{
    
    @JsonProperty
    private Integer id;
    
    @JsonProperty
    private Integer studentId;
    
    @JsonProperty
    private Integer courseId;
    
    @JsonProperty
    private String _courseGroupSpec;
    
    @JsonProperty
    private String _courseGroupDescription;
    
    @JsonProperty
    private String correspondence
    
    @JsonProperty
    private String title
    
    @JsonProperty
    private Date date
    
    @JsonProperty
    private String from
    
    @JsonProperty
    private String to
    
    @JsonProperty
    private Integer letterId
    
    @JsonProperty
    private Date staffAdvised
    
    @JsonProperty
    private Integer typeId
    
    @JsonProperty
    private String _correspondenceTypeDescription
    
    @JsonProperty
    private String producedBy
    
    @JsonProperty
    private Boolean privateEntry
    
    @JsonProperty
    private Integer processStage
    
    @JsonProperty
    private String attachmentsSent
    
    /**
     * Default No Args constructor
     */
    public CorrespondenceDto() {
    }
    
    /**
     * Constructor to create a CorrespondenceDto object from a Correspondence object
     *
     * @param correspondence the Correspondence object to use for construction
     */
    CorrespondenceDto(Correspondence correspondence) {
        this.id = correspondence.id;
        this.studentId = correspondence.student != null ? correspondence.student.id : null;
        this.courseId = correspondence.course != null ? correspondence.course.id : null;
        this._courseGroupSpec = correspondence.course != null ? correspondence.course.spec : ''
        this._courseGroupDescription = correspondence.course != null ? correspondence.course.code : ''
        this.correspondence = correspondence.correspondence;
        this.title = correspondence.title;
        this.date = correspondence.date;
        this.from = correspondence.from;
        this.to = correspondence.to;
        this.letterId = correspondence.letter != null ? correspondence.letter.id : null;
        this.staffAdvised = correspondence.staffAdvised;
        this.typeId = correspondence.type != null ? correspondence.type.id : null;
        this._correspondenceTypeDescription = correspondence.type != null ? correspondence.type.type : ''
        this.producedBy = correspondence.producedBy;
        this.privateEntry = correspondence.privateEntry;
        this.processStage = correspondence.processStage;
        this.attachmentsSent = correspondence.attachmentsSent;
    }
    
    /**
     * Constructor to create a CorrespondenceDto object
     *
     * @param id the Id for the Correspondence
     * @param studentId the studentId for the Correspondence
     * @param correspondenceId the correspondenceId for the Correspondence
     * @Param correspondenceTypeId the correspondenceTypeId of the Correspondence
     * @Param correspondenceable the correspondenceable of the Correspondence
     * @Param preferred the preferred of the Correspondence
     */
    public CorrespondenceDto(Integer id, Integer studentId, Integer courseId, String correspondence, String title, Date date, String from, String to, Integer letterId, Date staffAdvised, Integer typeId, String producedBy, Boolean privateEntry, Integer processStage, String attachmentsSent) {
        this.id = id;
        this.studentId = studentId;
        this.courseId = courseId;
        this.correspondence = correspondence;
        this.title = title;
        this.date = date;
        this.from = from;
        this.to = to;
        this.letterId = letterId;
        this.staffAdvised = staffAdvised;
        this.typeId = typeId;
        this.producedBy = producedBy;
        this.privateEntry = privateEntry;
        this.processStage = processStage;
        this.attachmentsSent = attachmentsSent;
    }
    
    /**
     * Constructor to create a CorrespondenceDto object
     * 
     * @param id the Id for the Correspondence
     * @param student the Student object for the Correspondence
     * @param course the CourseGroup object for the Correspondence
     * @param correspondence the correspondence of the Correspondence
     * @param title the title of the Correspondence
     * @param date the date of the Correspondence
     * @param from the from of the Correspondence
     * @param to the to of the Correspondence
     * @param letter The letter object for the Correspondence
     * @param staffAdvised The staffAdvised of the Correspondence
     * @param type The CorrespondenceType object for theCorrespondence
     * @param producedBy the producedBy of the Correspondence
     * @param privateEntry The privateEntry of the Correspondence
     * @param processStage The processStage of the Correspondence
     * @param attachmentsSent The attachmentsSent of the Correspondence
     */
    public CorrespondenceDto(Integer id, Student student, CourseGroup course, String correspondence, String title, Date date, String from, String to, Letter letter, Date staffAdvised, CorrespondenceType type, String producedBy, Boolean privateEntry, Integer processStage, String attachmentsSent) {
        this(id, student != null ? student.id : null, course != null ? course.id : null, correspondence, title, date, from, to, letter != null ? letter.id : null, staffAdvised, type != null ? type.id : null, producedBy, privateEntry, processStage, attachmentsSent)
    }
    
    @Override
    public String toString() {
        return "CorrespondenceDto [id=" + id + ", student=" + studentId + ", course=" + courseId + ", correspondence=" + correspondence + ", title=" + title + ", date=" + date + ", from=" + from +", to=" + to + ", letter=" + letterId +", staffAdvised=" + staffAdvised+",  type=" + typeId + ", producedBy=" + producedBy + ", privateEntry=" + privateEntry+ ", processStage=" + processStage + ", attachmentsSent=" + attachmentsSent + "]";
    }
    
    public static CorrespondenceDto mapFromCorrespondenceEntity(Correspondence correspondence) {
        return  new CorrespondenceDto(correspondence)
    }
    
    public static List<CorrespondenceDto> mapFromCorrespondencesEntities(List<Correspondence> correspondences) {
        return correspondences.collect { correspondence ->  new CorrespondenceDto(correspondence) };
    }
    
    public static Correspondence mapToCorrespondenceEntity(CorrespondenceDto correspondenceDto, Student student, CourseGroup course, CorrespondenceType type, Letter letter) {
        return new Correspondence(correspondenceDto.id, student, course, correspondenceDto.correspondence, correspondenceDto.title, correspondenceDto.date, correspondenceDto.from, correspondenceDto.to, letter, correspondenceDto.staffAdvised, type, correspondenceDto.producedBy, correspondenceDto.privateEntry, correspondenceDto.processStage, correspondenceDto.attachmentsSent)
    }
}
