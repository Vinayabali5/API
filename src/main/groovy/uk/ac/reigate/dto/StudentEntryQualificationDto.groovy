package uk.ac.reigate.dto;


import groovy.transform.EqualsAndHashCode

import javax.validation.constraints.NotNull

import uk.ac.reigate.domain.academic.EntryQualification
import uk.ac.reigate.domain.academic.Student
import uk.ac.reigate.domain.academic.StudentEntryQualification
import uk.ac.reigate.domain.exam.ExamBoard
import com.fasterxml.jackson.annotation.JsonFormat
import com.fasterxml.jackson.annotation.JsonProperty
import com.fasterxml.jackson.databind.annotation.JsonSerialize


/**
 *
 * JSON serializable DTO containing EntryQualification data
 *
 */
@JsonSerialize
@EqualsAndHashCode(includeFields=true)
public class StudentEntryQualificationDto implements Serializable {
    
    @JsonProperty
    private Integer studentEntryQualificationId;
    
    @NotNull
    @JsonProperty
    private Integer studentId;
    
    @NotNull
    @JsonProperty
    private Integer entryQualificationId;
    
    @NotNull
    @JsonProperty
    private Date date;
    
    @JsonProperty
    private String grade
    
    @JsonProperty
    private boolean checked;
    
    @NotNull
    @JsonProperty
    private Integer examBoardId;
    
    @JsonProperty
    private String _examBoardName;
    
    @JsonProperty
    private EntryQualificationDto _entryQualification;
    
    
    /**
     * Default No Args constructor
     */
    public StudentEntryQualificationDto() {
    }
    
    /**
     * Constructor to create a StudentEntryQualificationDto object from a StudentEntryQualification object
     *
     * @param studentEntryQualification the StudentEntryQualification object to use for construction
     */
    StudentEntryQualificationDto(StudentEntryQualification studentEntryQualification) {
        this.studentEntryQualificationId = studentEntryQualification.id
        this.studentId = studentEntryQualification.student != null ? studentEntryQualification.student.id : null;
        this.entryQualificationId = studentEntryQualification.qualification != null ? studentEntryQualification.qualification.id : null;
        this.date = studentEntryQualification.date;
        this.grade = studentEntryQualification.grade;
        this.checked = studentEntryQualification.checked;
        this.examBoardId = studentEntryQualification.examBoard != null ? studentEntryQualification.examBoard.id : null;
        this._examBoardName = studentEntryQualification.examBoard != null ? studentEntryQualification.examBoard.name : '';
        this._entryQualification = EntryQualificationDto.mapFromEntryQualificationEntity(studentEntryQualification.qualification)
    }
    /**
     * Constructor to create a StudentEntryQualificationDto object  with the basic data with no linked objects.
     *
     * @param studentEntryQualificationId
     * @param student
     * @param entryQualification
     * @param date
     * @param grade
     * @param checked
     */
    public StudentEntryQualificationDto(Integer studentEntryQualificationId, Integer studentId, Integer entryQualificationId, Date date, String grade, boolean checked, Integer examBoardId) {
        this.studentEntryQualificationId = studentEntryQualificationId;
        this.studentId = studentId;
        this.entryQualificationId = entryQualificationId;
        this.date = date;
        this.grade = grade;
        this.checked = checked;
        this.examBoardId = examBoardId;
    }
    
    /**
     * Constructor to create a StudentEntryQualificationDto object with the basic data with linked Student, EntryQualification objects. 
     * 
     * @param studentEntryQualificationId
     * @param student
     * @param entryQualification
     * @param date
     * @param grade
     * @param checked
     */
    public StudentEntryQualificationDto(Integer studentEntryQualificationId, Student student, EntryQualification entryQualification, Date date, String grade, boolean checked, ExamBoard examBoard) {
        this(studentEntryQualificationId, student != null ? student.id : null, entryQualification != null ? entryQualification.id : null, date, grade, checked, examBoard != null ? examBoard.id : null)
    }
    
    @Override
    public String toString() {
        return "StudentEntryQualificationDto [id=" + studentEntryQualificationId + ", student=" + studentId + ", entryQualification=" + entryQualificationId + ", date=" + date + ", grade=" + grade +", checked=" +checked + ", examBoard=" + examBoardId +"]";
    }
    
    public static StudentEntryQualificationDto mapFromStudentEntryQualificationEntity(StudentEntryQualification studentEntryQualification) {
        return new StudentEntryQualificationDto(studentEntryQualification)
    }
    
    public static List<StudentEntryQualificationDto> mapFromStudentEntryQualificationsEntities(List<StudentEntryQualification> studentEntryQualifications) {
        return studentEntryQualifications.collect { studentEntryQualification ->  new StudentEntryQualificationDto(studentEntryQualification) };
    }
    
    public static StudentEntryQualification mapToStudentEntryQualificationEntity(StudentEntryQualificationDto studentEntryQualificationDto, Student student, EntryQualification entryQualification, ExamBoard examBoard) {
        return new StudentEntryQualification(studentEntryQualificationDto.studentEntryQualificationId, student, entryQualification, studentEntryQualificationDto.date, studentEntryQualificationDto.grade, studentEntryQualificationDto.checked, examBoard)
    }
}
