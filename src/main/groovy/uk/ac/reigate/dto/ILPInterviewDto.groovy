package uk.ac.reigate.dto

import groovy.transform.EqualsAndHashCode

import com.fasterxml.jackson.annotation.JsonProperty
import com.fasterxml.jackson.databind.annotation.JsonSerialize

import uk.ac.reigate.domain.Staff;
import uk.ac.reigate.domain.academic.AcademicYear
import uk.ac.reigate.domain.academic.CourseGroup;
import uk.ac.reigate.domain.academic.Student;
import uk.ac.reigate.domain.ilp.ILPInterview
import uk.ac.reigate.domain.ilp.ILPInterviewType;

/**
 *
 * JSON serializable DTO containing ILPInterview data
 *
 */
@JsonSerialize
@EqualsAndHashCode(includeFields=true)
public class ILPInterviewDto implements Serializable{
    
    @JsonProperty
    private Integer id;
    
    @JsonProperty
    private Integer studentId;
    
    @JsonProperty
    private Integer typeId;
    
    @JsonProperty
    private String _ilpInterviewTypeDescription
    
    @JsonProperty
    private Integer courseGroupId
    
    @JsonProperty
    private String _courseDescription
    
    @JsonProperty
    private String _courseSpec
    
    @JsonProperty
    private String _courseGroupDescription
    
    @JsonProperty
    private String _courseGroupSpec
    
    @JsonProperty
    private Integer staffId;
    
    @JsonProperty
    private String _staffName;
    
    @JsonProperty
    private String discussion;
    
    @JsonProperty
    private Date interviewDate
    
    @JsonProperty
    private Date interviewTime
    
    @JsonProperty
    private Boolean referLip
    
    @JsonProperty
    private Date lipReferDate
    
    @JsonProperty
    private Boolean privateEntry
    
    @JsonProperty
    private String officeAction
    
    @JsonProperty
    private String officeNotes
    
    @JsonProperty
    private Boolean toFile
    
    @JsonProperty
    private String target
    
    @JsonProperty
    private String targetByWhen
    
    @JsonProperty
    private Integer academicYearId
    
    /**
     * This fields is a boolean value that looks to see if there was a letter linked to the ILP Interview. If so 
     * then this will return as true else it will be false
     */
    @JsonProperty
    private Boolean _letterSentHome
    
    /**
     * Default No Args constructor
     */
    public ILPInterviewDto() {
    }
    
    /**
     * Constructor to create an ILPInterviewDto object from a ILPInterview object
     *
     * @param iLPInterview the ILPInterview object to use for construction
     */
    ILPInterviewDto(ILPInterview iLPInterview) {
        this.id = iLPInterview.id;
        this.studentId = iLPInterview.student != null ? iLPInterview.student.id : null;
        this.typeId = iLPInterview.type != null ? iLPInterview.type.id : null;
        this.courseGroupId = iLPInterview.courseGroup != null ? iLPInterview.courseGroup.id : null;
        this.staffId = iLPInterview.staff != null ? iLPInterview.staff.id : null;
        this.discussion = iLPInterview.discussion;
        this.interviewDate = iLPInterview.interviewDate;
        this.interviewTime = iLPInterview.interviewTime;
        this.referLip = iLPInterview.referLip;
        this.lipReferDate = iLPInterview.lipReferDate;
        this.privateEntry = iLPInterview.privateEntry;
        this.officeAction = iLPInterview.officeAction;
        this.officeNotes= iLPInterview.officeNotes;
        this.toFile = iLPInterview.toFile;
        this._ilpInterviewTypeDescription = iLPInterview.type != null ? iLPInterview.type.type : ''
        this._courseDescription = iLPInterview.courseGroup != null && iLPInterview.courseGroup.course != null && iLPInterview.courseGroup.course.level != null ? iLPInterview.courseGroup.course.level.description + ' - ' + iLPInterview.courseGroup.course.subject.description : ''
        this._courseSpec = iLPInterview.courseGroup != null && iLPInterview.courseGroup.course != null ? iLPInterview.courseGroup.course.spec : ''
        this._courseGroupDescription = iLPInterview.courseGroup != null ? iLPInterview.courseGroup.code : ''
        this._courseGroupSpec = iLPInterview.courseGroup != null ? iLPInterview.courseGroup.spec : ''
        this._staffName = iLPInterview.staff != null && iLPInterview.staff.person != null ? iLPInterview.staff.person.surname + '-' + iLPInterview.staff.person.firstName : ''
        this._letterSentHome = iLPInterview.letterSent
        this.target = iLPInterview.target !=null ? iLPInterview.target : ''
        this.targetByWhen =  iLPInterview.targetByWhen!=null ?  iLPInterview.targetByWhen : ''
        this.academicYearId =  iLPInterview.year!= null? iLPInterview.year.id: ''
    }
    
    @Override
    public String toString() {
        return "ILPInterviewDto [id=" + id + ", student=" + studentId + ", type=" + typeId + ", courseGroup=" + courseGroupId + ", staff=" + staffId + ", discussion=" + discussion + ", interviewDate=" + interviewDate +", interviewTime=" + interviewTime + ", referLip=" + referLip +", lipReferDate=" + lipReferDate+",  privateEntry=" + privateEntry + ", officeNotes=" + officeNotes + ", toFile=" + toFile+ "]";
    }
    
    public static ILPInterviewDto mapFromILPInterviewEntity(ILPInterview iLPInterview) {
        return new ILPInterviewDto(iLPInterview)
    }
    
    public static List<ILPInterviewDto> mapFromILPInterviewsEntities(List<ILPInterview> iLPInterviews) {
        return iLPInterviews.collect { iLPInterview ->  new ILPInterviewDto(iLPInterview) };
    }
    
    public static ILPInterview mapToILPInterviewEntity(ILPInterviewDto iLPInterviewDto, Student student, CourseGroup courseGroup, ILPInterviewType type, Staff staff, AcademicYear academicYear) {
        return new ILPInterview(iLPInterviewDto.id, student,  type, courseGroup, staff, iLPInterviewDto.discussion, iLPInterviewDto.interviewDate,  iLPInterviewDto.interviewTime,  iLPInterviewDto.referLip,  iLPInterviewDto.lipReferDate,  iLPInterviewDto.privateEntry,  iLPInterviewDto.officeAction,  iLPInterviewDto.officeNotes,  iLPInterviewDto.toFile , iLPInterviewDto.target , iLPInterviewDto.targetByWhen , academicYear)
    }
}
