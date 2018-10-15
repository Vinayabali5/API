package uk.ac.reigate.dto.exam

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonSerialize

import groovy.transform.EqualsAndHashCode;
import uk.ac.reigate.domain.academic.AcademicYear
import uk.ac.reigate.domain.exam.ExamSeries
import uk.ac.reigate.domain.exam.Syllabus;;

@JsonSerialize
@EqualsAndHashCode(includeFields=true)
class SyllabusDto implements Serializable {
    
    @JsonProperty
    private Integer id;
    
    @JsonProperty
    private ExamSeriesDto examSeries;
    
    @JsonProperty
    private String code;
    
    @JsonProperty
    private String title;
    
    /**
     * Default No Args constructor    
     */
    public SyllabusDto(){}
    
    /**
     * Constructor to create an Exam SyllabusDto object
     * 
     * @param id
     * @param examSeries
     * @param code
     * @param title
     */
    public SyllabusDto(Integer id, ExamSeries examSeries, String code, String title) {
        this.id = id;
        this.examSeries = ExamSeriesDto.mapFromExamSeriesEntity(examSeries);
        this.code = code;
        this.title = title;
    }
    
    public SyllabusDto(Syllabus syllabus) {
        this.id = syllabus.syllabusId
        this.examSeries = ExamSeriesDto.mapFromExamSeriesEntity(syllabus.examSeries);
        this.code = syllabus.code;
        this.title = syllabus.title;
        /*        this.examOptions = syllabus.examOptions.collect {
         examOption -> new ExamOptionDto(examOption)
         }*/
    }
    
    public String toString() {
        return "Syllabus [" +
                "id: " + id +
                ", examSeriesDto: " + examSeries +
                ", code: " + code +
                ", title: " + title + "]";
    }
    
    public static SyllabusDto mapFromSyllabusEntity(Syllabus syllabus) {
        return new SyllabusDto(syllabus)
    }
    
    public static List<SyllabusDto> mapFromSyllabusEntities(List<Syllabus> syllabi) {
        return syllabi.collect { syllabus -> new SyllabusDto(syllabus) } ;
    }
    
    public static Syllabus mapToSyllabusEntity(SyllabusDto syllabusDto) {
        return new Syllabus.Builder()
                .syllabusId(syllabusDto.id)
                .examSeries(ExamSeriesDto.mapToExamSeriesEntity(syllabusDto.examSeries, new AcademicYear(syllabusDto.examSeries.academicYearId, null, null, null, null)))
                .code(syllabusDto.code)
                .title(syllabusDto.title)
                .build()
    }
    
    public static List<Syllabus> mapToSyllabusEntities(List<SyllabusDto> syllabusDtos) {
        return syllabusDtos.collect { syllabusDto -> mapToSyllabusEntity(syllabusDto) };
    }
}
