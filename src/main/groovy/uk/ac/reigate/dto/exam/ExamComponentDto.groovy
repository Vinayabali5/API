package uk.ac.reigate.dto.exam

import uk.ac.reigate.domain.academic.AcademicYear
import uk.ac.reigate.domain.exam.ExamComponent;
import uk.ac.reigate.domain.exam.ExamSeries

import org.springframework.beans.factory.annotation.Autowired

import com.fasterxml.jackson.annotation.JsonFormat
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;

import groovy.transform.EqualsAndHashCode;

@JsonSerialize
@EqualsAndHashCode(includeFields=true)
class ExamComponentDto {
    
    
    @JsonProperty
    private Integer id;
    
    @JsonProperty
    
    private ExamSeriesDto examSeriesDto;
    //    private List<OptionComponent> optionComponents;
    
    @JsonProperty
    private String code;
    
    @JsonProperty
    private String title;
    
    @JsonProperty
    private String teacherMarks;
    
    @JsonProperty
    private Integer maximumMark;
    
    @JsonProperty
    private String gradeset;
    
    @JsonProperty
    // @JsonFormat(pattern="yyyy-MM-dd")
    private Date dueDate;
    
    @JsonProperty
    private String timetabled;
    
    @JsonProperty
    //@JsonFormat(pattern="yyyy-MM-dd")
    private Date timetableDate;
    
    @JsonProperty
    private String timetableSession;
    
    @JsonProperty
    private Integer timeAllowed;
    
    /**
     * default No Args constructor
     */
    public ExamComponentDto(){}
    
    /**
     * Constructor to create an ExamComponentDto object
     *     
     * @param id
     * @param examSeriesDto
     * @param code
     * @param title
     * @param teacherMarks
     * @param maximumMark
     * @param gradeset
     * @param dueDate
     * @param timetabled
     * @param timetableDate
     * @param timetableSession
     * @param timeAllowed
     */
    public ExamComponentDto(Integer id,
    ExamSeriesDto examSeriesDto,
    //List<OptionComponent> optionComponents,
    String code,
    String title,
    String teacherMarks,
    Integer maximumMark,
    String gradeset,
    Date dueDate,
    String timetabled,
    Date timetableDate,
    String timetableSession,
    Integer timeAllowed) {
        this.id = id;
        this.examSeriesDto = examSeriesDto;
        //        this.optionComponents = optionComponents;
        this.code = code;
        this.title = title;
        this.teacherMarks = teacherMarks;
        this.maximumMark = maximumMark;
        this.gradeset = gradeset;
        this.dueDate = dueDate;
        this.timetabled = timetabled;
        this.timetableDate = timetableDate;
        this.timetableSession = timetableSession;
        this.timeAllowed = timeAllowed;
    }
    
    @Override
    public String toString() {
        return "ExamComponent [" +
                "Id: " + String.valueOf(id) +
                ", examSeriesDto: " + examSeriesDto +
                //                ", optionComponents: " + optionComponents +
                ", code: " + code +
                ", title: " + title +
                ", teacherMarks: " + teacherMarks +
                ", maximumMark: " + String.valueOf(maximumMark) +
                ", gradeset: " + gradeset +
                ", dueDate: " + dueDate +
                ", timetabled: " + timetabled +
                ", timetableDate: " + timetableDate +
                ", timetableSession: " + timetableSession +
                ", timeAllowed: " + String.valueOf(timeAllowed) +"]";
    }
    
    public static ExamComponentDto mapFromExamComponentEntity(ExamComponent examComponent) {
        ExamComponentDto output = new ExamComponentDto(examComponent.examComponentId,
                ExamSeriesDto.mapFromExamSeriesEntity(examComponent.examSeries),
                //examComponent.optionComponents,
                examComponent.code,
                examComponent.title,
                examComponent.teacherMarks,
                examComponent.maximumMark,
                examComponent.gradeset,
                examComponent.dueDate,
                examComponent.timetabled,
                examComponent.timetableDate,
                examComponent.timetableSession,
                examComponent.timeAllowed);
        return output;
    }
    
    public static List<ExamComponentDto> mapFromExamComponentEntities(List<ExamComponent> examComponents) {
        List<ExamComponentDto> output = examComponents.collect { examComponent -> mapFromExamComponentEntity(examComponent) };
        return output
    }
    
    public static ExamComponent mapToExamComponentEntity(ExamComponentDto examComponentDto, AcademicYear academicYear) {
        
        return new ExamComponent.Builder()
                .examComponentId(examComponentDto.id)
                .examSeries(ExamSeriesDto.mapToExamSeriesEntity(examComponentDto.examSeriesDto, academicYear))
                //.optionComponents(examComponentDto.optionComponents)
                .code(examComponentDto.code)
                .title(examComponentDto.title)
                .teacherMarks(examComponentDto.teacherMarks)
                .maximumMark(examComponentDto.maximumMark)
                .gradeset(examComponentDto.gradeset)
                .dueDate(examComponentDto.dueDate)
                .timetabled(examComponentDto.timetabled)
                .timetableDate(examComponentDto.timetableDate)
                .timetableSession(examComponentDto.timetableSession)
                .timeAllowed(examComponentDto.timeAllowed)
                .build()
    }
    
    public static List<ExamComponent> mapToExamComponentEntities(List<ExamComponentDto> examComponentDtos) {
        return examComponentDtos.collect { examComponentDto -> mapToExamComponentEntity(examComponentDto) } ;
    }
}
