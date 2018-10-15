package uk.ac.reigate.dto;


import java.util.Date

import javax.persistence.OneToOne;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;

import groovy.transform.EqualsAndHashCode
import uk.ac.reigate.domain.Staff;
import uk.ac.reigate.domain.academic.SpecialCategory;
import uk.ac.reigate.domain.academic.Student;
import uk.ac.reigate.domain.learning_support.StudentSpecialCategory

/**
 *
 * JSON serializable DTO containing StudentSpecialCategory data
 *
 */
@JsonSerialize
@EqualsAndHashCode(includeFields=true)
public class StudentSpecialCategoryDto implements Serializable {
    
    @JsonProperty
    private Integer id;
    
    @JsonProperty
    private Integer studentId;
    
    @JsonProperty
    private Date requestDate
    
    @JsonProperty
    private Integer specialCategoryId
    
    @JsonProperty
    private Boolean specialConfirmed
    
    @JsonProperty
    private Date classificationDate
    
    @JsonProperty
    private Date closedDate
    
    @JsonProperty
    private String monitoringNotes
    
    @JsonProperty
    private Integer staffRequestingId
    
    @JsonProperty
    private Boolean riskAssessmentRequired
    
    @JsonProperty
    private Integer riskAssessmentToBeCompletedById
    
    @JsonProperty
    private Boolean informationConfidential
    
    @JsonProperty
    private Boolean writtenEvidence
    
    @JsonProperty
    private Boolean passToStaffConcerned
    
    @JsonProperty
    private Integer staffConcernedId
    
    @JsonProperty
    private String riskToStudentOrOthers
    
    @JsonProperty
    private String emergencyContactNos
    
    @JsonProperty
    private String outsideAgenciesInvolved
    
    @JsonProperty
    private String toBeInformedPotentialRisks
    
    @JsonProperty
    private Integer riskAssessmentRaisedById
    
    @JsonProperty
    private Date riskAssessmentRaisedDate
    
    @JsonProperty
    private Integer riskAssessmentCarriedOutById
    
    @JsonProperty
    private Date riskAssessmentCarriedOutDate
    
    @JsonProperty
    private String inEventEmergency
    
    /**
     * Default No Args constructor
     */
    public StudentSpecialCategoryDto() {
    }
    
    /**
     * Constructor to create a StudentSpecialCategoryDto object from a StudentSpecialCategory object
     *
     * @param studentSpecialCategory the StudentSpecialCategory object to use for construction
     */
    StudentSpecialCategoryDto(StudentSpecialCategory studentSpecialCategory) {
        this.id = studentSpecialCategory.id;
        this.studentId = studentSpecialCategory.student != null ? studentSpecialCategory.student.id : null;
        this.requestDate = studentSpecialCategory.requestDate;
        this.specialCategoryId = studentSpecialCategory.specialCategory != null ? studentSpecialCategory.specialCategory.id : null;
        this.specialConfirmed = studentSpecialCategory.specialConfirmed;
        this.classificationDate = studentSpecialCategory.classificationDate;
        this.closedDate = studentSpecialCategory.closedDate;
        this.monitoringNotes = studentSpecialCategory.monitoringNotes;
        this.staffRequestingId = studentSpecialCategory.staffRequesting != null ? studentSpecialCategory.staffRequesting.id : null;
        this.riskAssessmentRequired = studentSpecialCategory.riskAssessmentRequired;
        this.riskAssessmentToBeCompletedById = studentSpecialCategory.riskAssessmentToBeCompletedBy != null ? studentSpecialCategory.riskAssessmentToBeCompletedBy.id : null;
        this.informationConfidential = studentSpecialCategory.informationConfidential;
        this.writtenEvidence = studentSpecialCategory.writtenEvidence;
        this.passToStaffConcerned = studentSpecialCategory.passToStaffConcerned;
        this.staffConcernedId = studentSpecialCategory.staffConcerned != null ? studentSpecialCategory.staffConcerned.id : null;;
        this.riskToStudentOrOthers = studentSpecialCategory.riskToStudentOrOthers;
        this.emergencyContactNos = studentSpecialCategory.emergencyContactNos;
        this.outsideAgenciesInvolved = studentSpecialCategory.outsideAgenciesInvolved;
        this.toBeInformedPotentialRisks = studentSpecialCategory.toBeInformedPotentialRisks;
        this.riskAssessmentRaisedById = studentSpecialCategory.riskAssessmentRaisedBy != null ? studentSpecialCategory.riskAssessmentRaisedBy.id : null;
        this.riskAssessmentRaisedDate = studentSpecialCategory.riskAssessmentRaisedDate;
        this.riskAssessmentCarriedOutById = studentSpecialCategory.riskAssessmentCarriedOutBy != null ? studentSpecialCategory.riskAssessmentCarriedOutBy.id : null;
        this.riskAssessmentCarriedOutDate = studentSpecialCategory.riskAssessmentCarriedOutDate;
        this.inEventEmergency = studentSpecialCategory.inEventEmergency;
    }
    
    /**
     * Constructor to create a StudentSpecialCategoryDto object  with the basic data with no linked objects.
     * 
     * @param id
     * @param studentId
     * @param requestDate
     * @param specialCategoryId
     * @param specialConfirmed
     * @param classificationDate
     * @param closedDate
     * @param monitoringNotes
     * @param staffRequestingId
     * @param riskAssessmentRequired
     * @param riskAssessmentToBeCompletedById
     * @param informationConfidential
     * @param writtenEvidence
     * @param passToStaffConcerned
     * @param staffConcernedId
     * @param riskToStudentOrOthers
     * @param emergencyContactNos
     * @param outsideAgenciesInvolved
     * @param toBeInformedPotentialRisks
     * @param riskAssessmentRaisedById
     * @param riskAssessmentRaisedDate
     * @param riskAssessmentCarriedOutById
     * @param riskAssessmentCarriedOutDate
     * @param inEventEmergency
     */
    public StudentSpecialCategoryDto(Integer id, Integer studentId, Date requestDate, Integer specialCategoryId, Boolean specialConfirmed, Date classificationDate, Date closedDate, String monitoringNotes, Integer staffRequestingId, Boolean riskAssessmentRequired, Integer riskAssessmentToBeCompletedById, Boolean informationConfidential, Boolean writtenEvidence, Boolean passToStaffConcerned, Integer staffConcernedId, String riskToStudentOrOthers, String emergencyContactNos, String outsideAgenciesInvolved, String toBeInformedPotentialRisks, Integer riskAssessmentRaisedById, Date riskAssessmentRaisedDate, Integer riskAssessmentCarriedOutById, Date riskAssessmentCarriedOutDate, String inEventEmergency) {
        this.id = id;
        this.studentId = studentId;
        this.requestDate = requestDate;
        this.specialCategoryId = specialCategoryId;
        this.specialConfirmed = specialConfirmed;
        this.classificationDate = classificationDate;
        this.closedDate = closedDate;
        this.monitoringNotes = monitoringNotes;
        this.staffRequestingId = staffRequestingId;
        this.riskAssessmentRequired = riskAssessmentRequired;
        this.riskAssessmentToBeCompletedById = riskAssessmentToBeCompletedById;
        this.informationConfidential = informationConfidential;
        this.writtenEvidence = writtenEvidence;
        this.passToStaffConcerned = passToStaffConcerned;
        this.staffConcernedId = staffConcernedId;
        this.riskToStudentOrOthers = riskToStudentOrOthers;
        this.emergencyContactNos = emergencyContactNos;
        this.outsideAgenciesInvolved = outsideAgenciesInvolved;
        this.toBeInformedPotentialRisks = toBeInformedPotentialRisks;
        this.riskAssessmentRaisedById = riskAssessmentRaisedById;
        this.riskAssessmentRaisedDate = riskAssessmentRaisedDate;
        this.riskAssessmentCarriedOutById = riskAssessmentCarriedOutById;
        this.riskAssessmentCarriedOutDate = riskAssessmentCarriedOutDate;
        this.inEventEmergency = inEventEmergency;
    }
    
    /**
     * Constructor to create a StudentSpecialCategoryDto object with the basic data with linked objects.
     * 
     * @param id
     * @param student
     * @param requestDate
     * @param specialCategory
     * @param specialConfirmed
     * @param classificationDate
     * @param closedDate
     * @param monitoringNotes
     * @param staffRequesting
     * @param riskAssessmentRequired
     * @param riskAssessmentToBeCompletedBy
     * @param informationConfidential
     * @param writtenEvidence
     * @param passToStaffConcerned
     * @param staffConcerned
     * @param riskToStudentOrOthers
     * @param emergencyContactNos
     * @param outsideAgenciesInvolved
     * @param toBeInformedPotentialRisks
     * @param riskAssessmentRaisedBy
     * @param riskAssessmentRaisedDate
     * @param riskAssessmentCarriedOutBy
     * @param riskAssessmentCarriedOutDate
     * @param inEventEmergency
     */
    public StudentSpecialCategoryDto(Integer id, Student student, Date requestDate, SpecialCategory specialCategory, Boolean specialConfirmed, Date classificationDate, Date closedDate, String monitoringNotes, Staff staffRequesting, Boolean riskAssessmentRequired, Staff riskAssessmentToBeCompletedBy, Boolean informationConfidential, Boolean writtenEvidence, Boolean passToStaffConcerned, Staff staffConcerned, String riskToStudentOrOthers, String emergencyContactNos, String outsideAgenciesInvolved, String toBeInformedPotentialRisks, Staff riskAssessmentRaisedBy, Date riskAssessmentRaisedDate, Staff riskAssessmentCarriedOutBy, Date riskAssessmentCarriedOutDate, String inEventEmergency) {
        this(
        id, student != null ? student.id : null,
        requestDate,
        specialCategory != null ? specialCategory.id : null,
        specialConfirmed, classificationDate,
        closedDate,
        monitoringNotes,
        staffRequesting != null ? staffRequesting.id : null,
        riskAssessmentRequired,
        riskAssessmentToBeCompletedBy != null ? riskAssessmentToBeCompletedBy.id : null,
        informationConfidential,
        writtenEvidence,
        passToStaffConcerned,
        staffConcerned != null ? staffConcerned.id : null,
        riskToStudentOrOthers,
        emergencyContactNos,
        outsideAgenciesInvolved,
        toBeInformedPotentialRisks,
        riskAssessmentRaisedBy != null ? riskAssessmentRaisedBy.id : null,
        riskAssessmentRaisedDate,
        riskAssessmentCarriedOutBy != null ? riskAssessmentCarriedOutBy.id : null,
        riskAssessmentCarriedOutDate, inEventEmergency
        )
    }
    
    @Override
    public String toString() {
        return "StudentSpecialCategoryDto [id=" + id + ", student=" + studentId + ", requestDate=" + requestDate + ", specialCategory=" + specialCategoryId + ", specialConfirmed=" + specialConfirmed + ", classificationDate=" + classificationDate +", closedDate=" + closedDate +", monitoringNotes=" + monitoringNotes +", staffRequesting=" + staffRequestingId +", riskAssessmentRequired=" + riskAssessmentRequired +", riskAssessmentToBeCompletedBy=" + riskAssessmentToBeCompletedById +", informationConfidential=" + informationConfidential + ", writtenEvidence=" + writtenEvidence +", passToStaffConcerned=" + passToStaffConcerned +", staffConcerned=" + staffConcernedId +", riskToStudentOrOthers=" + riskToStudentOrOthers +", emergencyContactNos=" + emergencyContactNos + ", outsideAgenciesInvolved=" + outsideAgenciesInvolved +", toBeInformedPotentialRisks=" + toBeInformedPotentialRisks +", riskAssessmentRaisedBy=" + riskAssessmentRaisedById +", riskAssessmentRaisedDate=" + riskAssessmentRaisedDate +", riskAssessmentCarriedOutBy=" + riskAssessmentCarriedOutById +", riskAssessmentCarriedOutDate=" + riskAssessmentCarriedOutDate +", inEventEmergency=" + inEventEmergency+"]";
    }
    
    public static StudentSpecialCategoryDto mapFromStudentSpecialCategoryEntity(StudentSpecialCategory studentSpecialCategory) {
        return new StudentSpecialCategoryDto(studentSpecialCategory)
    }
    
    public static List<StudentSpecialCategoryDto> mapFromStudentSpecialCategoriesEntities(List<StudentSpecialCategory> studentSpecialCategories) {
        List<StudentSpecialCategoryDto> output = studentSpecialCategories.collect { studentSpecialCategory ->  new StudentSpecialCategoryDto(studentSpecialCategory) };
        return output
    }
    
    public static StudentSpecialCategory mapToStudentSpecialCategoryEntity(StudentSpecialCategoryDto studentSpecialCategory, Student student, SpecialCategory specialCategory, Staff staffRequesting, Staff riskAssessmentToBeCompletedBy, Staff staffConcerned, Staff riskAssessmentRaisedBy, Staff riskAssessmentCarriedOutBy ) {
        return new StudentSpecialCategory(studentSpecialCategory.id, student , studentSpecialCategory.requestDate, specialCategory, studentSpecialCategory.specialConfirmed, studentSpecialCategory.classificationDate, studentSpecialCategory.closedDate,  studentSpecialCategory.monitoringNotes, staffRequesting , studentSpecialCategory.riskAssessmentRequired, riskAssessmentToBeCompletedBy,  studentSpecialCategory.informationConfidential,  studentSpecialCategory.writtenEvidence, studentSpecialCategory.passToStaffConcerned, staffConcerned ,  studentSpecialCategory.riskToStudentOrOthers, studentSpecialCategory.emergencyContactNos,  studentSpecialCategory.outsideAgenciesInvolved,  studentSpecialCategory.toBeInformedPotentialRisks, riskAssessmentRaisedBy, studentSpecialCategory.riskAssessmentRaisedDate, riskAssessmentCarriedOutBy, studentSpecialCategory.riskAssessmentCarriedOutDate, studentSpecialCategory.inEventEmergency )
    }
}
