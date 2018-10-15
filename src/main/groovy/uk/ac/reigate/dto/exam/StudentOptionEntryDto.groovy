package uk.ac.reigate.dto.exam;


import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;

import groovy.transform.EqualsAndHashCode
import uk.ac.reigate.domain.academic.Student
import uk.ac.reigate.domain.exam.EdiStatusType
import uk.ac.reigate.domain.exam.ExamOption
import uk.ac.reigate.domain.exam.StatusType
import uk.ac.reigate.domain.exam.StudentOptionEntry
import uk.ac.reigate.domain.learning_support.ReferralReason;
import uk.ac.reigate.domain.learning_support.StudentReferralReason

/**
 *
 * JSON serializable DTO containing ReferralReason data
 *
 */
@JsonSerialize
@EqualsAndHashCode(includeFields=true)
public class StudentOptionEntryDto implements Serializable {
    
    @JsonProperty
    private Integer studentId;
    
    @JsonProperty
    private Integer examOptionId;
    
    @JsonProperty
    private String _optionEntryCode;
    
    @JsonProperty
    private String _optionTitle;
    
    @JsonProperty
    private Integer statusTypeId
    
    @JsonProperty
    private String _statusTypeDescription
    
    @JsonProperty
    private Integer ediStatusTypeId
    
    @JsonProperty
    private String _ediStatusTypeDescription
    
    @JsonProperty
    private Boolean resit;
    
    @JsonProperty
    private Boolean privateStudent
    
    @JsonProperty
    private ExamBoardDto _examBoard
    
    @JsonProperty
    private Integer _optionYearId
    
    @JsonProperty
    private ExamSeriesDto _examSeries
    
    /**
     * Default No Args constructor
     */
    public StudentOptionEntryDto() {
    }
    
    /**
     * Constructor to create a ReferralReasonDto object
     *
     * @param id the Id for the ReferralReason
     * @param code the code for the ReferralReason
     * @param description the description for the ReferralReason
     */
    public StudentOptionEntryDto(Integer studentId, Integer examOptionId, Integer statusTypeId, Integer ediStatusTypeId, Boolean resit, Boolean privateStudent) {
        this.studentId = studentId;
        this.examOptionId = examOptionId;
        this.statusTypeId = statusTypeId;
        this.ediStatusTypeId = ediStatusTypeId;
        this.resit = resit;
        this.privateStudent = privateStudent;
    }
    
    public StudentOptionEntryDto(Student student, ExamOption examOption, StatusType statusType, EdiStatusType ediStatusType, Boolean resit, Boolean privateStudent) {
        this(student != null ? student.id : null, examOption != null ? examOption.examOptionId : null, statusType != null ? statusType.id : null , ediStatusType != null ? ediStatusType.id : null, resit, privateStudent)
    }
    
    @Override
    public String toString() {
        return "StudentOptionEntryDto [student=" + studentId + ", examOption=" + examOptionId + ", statusType=" + statusTypeId + ", ediStatusType=" + ediStatusTypeId + ", resit=" + resit + ", privateStudent=" + privateStudent +"]";
    }
    
    public StudentOptionEntryDto(StudentOptionEntry studentOptionEntry) {
        this.studentId = studentOptionEntry.student != null ? studentOptionEntry.student.id : null;
        this.examOptionId = studentOptionEntry.examOption != null ? studentOptionEntry.examOption.examOptionId : null;
        this._optionEntryCode = studentOptionEntry.examOption != null ? studentOptionEntry.examOption.optionEntryCode : ''
        this._optionTitle = studentOptionEntry.examOption != null ? studentOptionEntry.examOption.optionTitle : ''
        this._statusTypeDescription = studentOptionEntry.statusType != null ? studentOptionEntry.statusType.description : ''
        this.statusTypeId = studentOptionEntry.statusType != null ? studentOptionEntry.statusType.id : null
        this._ediStatusTypeDescription = studentOptionEntry.ediStatusType != null ? studentOptionEntry.ediStatusType.description : ''
        this.ediStatusTypeId = studentOptionEntry.ediStatusType != null ? studentOptionEntry.ediStatusType.id : null;
        this.resit = studentOptionEntry.resit;
        this.privateStudent = studentOptionEntry.privateStudent;
        this._examBoard = studentOptionEntry.examOption != null && studentOptionEntry.examOption.syllabus != null && studentOptionEntry.examOption.syllabus.examSeries != null && studentOptionEntry.examOption.syllabus.examSeries.examBoard != null ? ExamBoardDto.mapFromExamBoardEntity(studentOptionEntry.examOption.syllabus.examSeries.examBoard) : null;
        this._optionYearId = studentOptionEntry.examOption != null && studentOptionEntry.examOption.syllabus != null && studentOptionEntry.examOption.syllabus.examSeries != null && studentOptionEntry.examOption.syllabus.examSeries.academicYear != null ? studentOptionEntry.examOption.syllabus.examSeries.academicYear.id : null;
        this._examSeries = studentOptionEntry.examOption != null && studentOptionEntry.examOption.syllabus != null && studentOptionEntry.examOption.syllabus.examSeries != null ? ExamSeriesDto.mapFromExamSeriesEntity(studentOptionEntry.examOption.syllabus.examSeries) : null
    }
    
    
    public static StudentOptionEntryDto mapFromStudentOptionEntryEntity(StudentOptionEntry studentOptionEntry) {
        StudentOptionEntryDto studentOptionEntryDto = new StudentOptionEntryDto(studentOptionEntry.student, studentOptionEntry.examOption, studentOptionEntry.statusType, studentOptionEntry.ediStatusType, studentOptionEntry.resit, studentOptionEntry.privateStudent);
        studentOptionEntryDto._statusTypeDescription = studentOptionEntry.statusType != null ? studentOptionEntry.statusType.description : ''
        studentOptionEntryDto._ediStatusTypeDescription = studentOptionEntry.ediStatusType != null ? studentOptionEntry.ediStatusType.description : ''
        studentOptionEntryDto._optionEntryCode = studentOptionEntry.examOption != null ? studentOptionEntry.examOption.optionEntryCode : ''
        studentOptionEntryDto._optionTitle = studentOptionEntry.examOption != null ? studentOptionEntry.examOption.optionTitle : ''
        studentOptionEntryDto._examBoard = studentOptionEntry.examOption != null && studentOptionEntry.examOption.syllabus != null && studentOptionEntry.examOption.syllabus.examSeries != null && studentOptionEntry.examOption.syllabus.examSeries.examBoard != null ? ExamBoardDto.mapFromExamBoardEntity(studentOptionEntry.examOption.syllabus.examSeries.examBoard) : null;
        studentOptionEntryDto._optionYearId = studentOptionEntry.examOption != null && studentOptionEntry.examOption.syllabus != null && studentOptionEntry.examOption.syllabus.examSeries != null && studentOptionEntry.examOption.syllabus.examSeries.academicYear != null ? studentOptionEntry.examOption.syllabus.examSeries.academicYear.id: null;
        studentOptionEntryDto._examSeries = studentOptionEntry.examOption != null && studentOptionEntry.examOption.syllabus != null && studentOptionEntry.examOption.syllabus.examSeries != null ? ExamSeriesDto.mapFromExamSeriesEntity(studentOptionEntry.examOption.syllabus.examSeries) : null
        return studentOptionEntryDto
    }
    
    public static List<StudentOptionEntryDto> mapFromStudentOptionEntriesEntities(List<StudentOptionEntry> studentOptionEntries) {
        return studentOptionEntries.collect { studentOptionEntry ->  new StudentOptionEntryDto(studentOptionEntry) };
    }
    
    public static StudentOptionEntry mapToStudentOptionEntryEntity(StudentOptionEntryDto studentOptionEntryDto, Student student, ExamOption examOption, StatusType statusType, EdiStatusType ediStatusType) {
        return new StudentOptionEntry(student, examOption, statusType, ediStatusType, studentOptionEntryDto.resit, studentOptionEntryDto.privateStudent)
    }
}
