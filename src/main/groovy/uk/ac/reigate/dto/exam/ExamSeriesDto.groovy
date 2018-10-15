package uk.ac.reigate.dto.exam

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonSerialize

import groovy.transform.EqualsAndHashCode;
import uk.ac.reigate.domain.academic.AcademicYear
import uk.ac.reigate.domain.exam.ExamBoard;
import uk.ac.reigate.domain.exam.ExamSeries;

@JsonSerialize
@EqualsAndHashCode(includeFields=true)
class ExamSeriesDto {
    
    @JsonProperty
    private Integer id;
    
    @JsonProperty
    private ExamBoardDto examBoard;
    
    @JsonProperty
    private String examYear;
    
    @JsonProperty
    private String examSeries;
    
    @JsonProperty
    private Boolean entrySubmitted;
    
    @JsonProperty
    private Integer nextSequenceNo;
    
    @JsonProperty
    private Integer academicYearId;
    
    @JsonProperty
    private String _academicYearDesc;
    
    
    
    /**
     * Default No Args constructor    
     */
    public ExamSeriesDto(){}
    
    /**
     * Constructor to create an Exam ExamSeriesDto object
     * 
     * @param examSeries
     */
    public ExamSeriesDto(ExamSeries examSeries) {
        this.id = examSeries.examSeriesId;
        this.examBoard = ExamBoardDto.mapFromExamBoardEntity(examSeries.examBoard);
        this.examYear = examSeries.examYear;
        this.examSeries = examSeries.examSeries;
        this.entrySubmitted = examSeries.entrySubmitted;
        this.academicYearId =  examSeries.academicYear.id;
        this._academicYearDesc = examSeries.academicYear.description;
        this.nextSequenceNo = examSeries.nextSequenceNo;
    }
    
    /**
     * Constructor to create an Exam ExamSeriesDto object
     *     
     * @param id
     * @param examBoard
     * @param examYear
     * @param examSeries
     * @param entrySubmitted
     */
    public ExamSeriesDto(Integer id, ExamBoard examBoard, String examYear, String examSeries, Boolean entrySubmitted, Integer academicYearId, String academicYearDesc) {
        this.id = id;
        this.examBoard = ExamBoardDto.mapFromExamBoardEntity(examBoard);
        this.examYear = examYear;
        this.examSeries = examSeries;
        this.entrySubmitted = entrySubmitted;
        this.academicYearId =  academicYearId;
        this._academicYearDesc = academicYearDesc;
    }
    
    /**
     * Constructor to create an Exam ExamSeriesDto object
     *
     * @param id
     * @param examBoard
     * @param examYear
     * @param examSeries
     * @param entrySubmitted
     * @param nextSequenceNo
     */
    public ExamSeriesDto(Integer id, ExamBoard examBoard, String examYear, String examSeries, Boolean entrySubmitted, Integer academicYearId, String academicYearDesc, Integer nextSequenceNo) {
        this(id, examBoard, examYear, examSeries, entrySubmitted, academicYearId, academicYearDesc);
        this.nextSequenceNo = nextSequenceNo;
    }
    
    public String toString() {
        return "ExamSeriesDto [" +
                "id: " + id +
                ", examBoard: " + examBoard +
                ", examYear: " + examYear +
                ", examSeries: " + examSeries +
                ", entrySubmitted: " + entrySubmitted + "]";
    }
    
    public static ExamSeriesDto mapFromExamSeriesEntity(ExamSeries examSeries) {
        ExamSeriesDto output = new ExamSeriesDto(examSeries.examSeriesId, examSeries.examBoard, examSeries.examYear, examSeries.examSeries, examSeries.entrySubmitted ,examSeries.academicYear.id, examSeries.academicYear.description, examSeries.nextSequenceNo);
        return output;
    }
    
    public static List<ExamSeriesDto> mapFromExamSeriesEntities(List<ExamSeries> examSeriesList) {
        List<ExamSeriesDto> output = examSeriesList.collect { examSeries -> mapFromExamSeriesEntity(examSeries) };
        return output;
    }
    
    public static ExamSeries mapToExamSeriesEntity(ExamSeriesDto examSeriesDto, AcademicYear academicYear) {
        
        return new ExamSeries.Builder()
                .examSeriesId(examSeriesDto.id)
                .examBoard((examSeriesDto.examBoard == null) ? new ExamBoard() : ExamBoardDto.mapToExamBoardEntity(examSeriesDto.examBoard))
                .examYear(examSeriesDto.examYear)
                .examSeries(examSeriesDto.examSeries)
                .entrySubmitted(examSeriesDto.entrySubmitted)
                .academicYear(academicYear)
                .build()
    }
    
    public static List<ExamSeries> mapToExamSeriesEntities(List<ExamSeriesDto> examSeriesDtos) {
        
        return examSeriesDtos.collect { examSeriesDto -> mapToExamSeriesEntity(examSeriesDto) };
    }
}
