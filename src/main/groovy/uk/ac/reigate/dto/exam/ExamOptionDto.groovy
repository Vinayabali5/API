package uk.ac.reigate.dto.exam

import groovy.transform.EqualsAndHashCode
import uk.ac.reigate.domain.exam.ExamOption
import uk.ac.reigate.domain.exam.ExamType
import uk.ac.reigate.domain.exam.Syllabus

import com.fasterxml.jackson.annotation.JsonIgnore
import com.fasterxml.jackson.annotation.JsonProperty
import com.fasterxml.jackson.databind.annotation.JsonSerialize

@JsonSerialize
@EqualsAndHashCode(includeFields=true)
class ExamOptionDto {
    
    @JsonProperty
    private Integer examOptionId;
    
    @JsonProperty
    private String examTypeQualificationCert;
    
    @JsonProperty
    private String examTypeLevelCert;
    
    @JsonProperty
    private String examTypeQualificationUnit;
    
    @JsonProperty
    private String examTypeLevelUnit;
    //    private OptionComponent optionComponents
    
    @JsonProperty
    private List<ExamComponentDto> examComponentsDto;
    
    @JsonProperty
    private String optionEntryCode;
    
    @JsonProperty
    private SyllabusDto syllabusDto;
    
    @JsonProperty
    private String process;
    
    @JsonProperty
    private String qcaClassificationCode;
    
    @JsonProperty
    private String qcaAccreditationNo;
    
    @JsonProperty
    private String optionTitle;
    
    @JsonProperty
    private String feeDefined;
    
    @JsonProperty
    private Integer examinationFee;
    
    @JsonProperty
    private String firstForecastGradeGradeset;
    
    @JsonProperty
    private String secondForecastGradeGradeset;
    
    @JsonProperty
    private String resultType;
    
    @JsonProperty
    private String firstGradeResultGradeset;
    
    @JsonProperty
    private String secondGradeResultGradeset;
    
    @JsonProperty
    private String endorsementToFirstGradeResultGradeset;
    
    @JsonProperty
    private String endorsementToSecondGradeResultGradeset;
    
    @JsonProperty
    private Integer maxMarkUms;
    
    @JsonProperty
    private Integer noOfComponents;
    
    /**
     * Default No Args constructor    
     */
    public ExamOptionDto(){}
    
    /**
     * Constructor to create an Exam OptionDto object
     * 
     * @param examOptionId
     * @param examTypeQualificationCert
     * @param examTypeLevelCert
     * @param examTypeQualificationUnit
     * @param examTypeLevelUnit
     * @param examComponentsDto
     * @param optionEntryCode
     * @param syllabus
     * @param process
     * @param qcaClassificationCode
     * @param qcaAccreditationNo
     * @param optionTitle
     * @param feeDefined
     * @param examinationFee
     * @param firstForecastGradeGradeset
     * @param secondForecastGradeGradeset
     * @param resultType
     * @param firstGradeResultGradeset
     * @param secondGradeResultGradeset
     * @param endorsementToFirstGradeResultGradeset
     * @param endorsementToSecondGradeResultGradeset
     * @param maxMarkUms
     * @param noOfComponents
     */
    public ExamOptionDto(Integer examOptionId,
    String examTypeQualificationCert,
    String examTypeLevelCert,
    String examTypeQualificationUnit,
    String examTypeLevelUnit,
    List<ExamComponentDto> examComponentsDto,
    String optionEntryCode,
    Syllabus syllabus,
    String process,
    String qcaClassificationCode,
    String qcaAccreditationNo,
    String optionTitle,
    String feeDefined,
    Integer examinationFee,
    String firstForecastGradeGradeset,
    String secondForecastGradeGradeset,
    String resultType,
    String firstGradeResultGradeset,
    String secondGradeResultGradeset,
    String endorsementToFirstGradeResultGradeset,
    String endorsementToSecondGradeResultGradeset,
    Integer maxMarkUms,
    Integer noOfComponents) {
        this.examOptionId = examOptionId
        this.examTypeQualificationCert = examTypeQualificationCert
        this.examTypeLevelCert = examTypeLevelCert
        this.examTypeQualificationUnit = examTypeQualificationUnit
        this.examTypeLevelUnit = examTypeLevelUnit
        this.examComponentsDto = examComponentsDto
        this.optionEntryCode = optionEntryCode
        this.syllabusDto = SyllabusDto.mapFromSyllabusEntity(syllabus)
        this.process = process
        this.qcaClassificationCode = qcaClassificationCode
        this.qcaAccreditationNo = qcaAccreditationNo
        this.optionTitle = optionTitle
        this.feeDefined = feeDefined
        this.examinationFee = examinationFee
        this.firstForecastGradeGradeset = firstForecastGradeGradeset
        this.secondForecastGradeGradeset = secondForecastGradeGradeset
        this.resultType = resultType
        this.firstGradeResultGradeset = firstGradeResultGradeset
        this.secondGradeResultGradeset = secondGradeResultGradeset
        this.endorsementToFirstGradeResultGradeset = endorsementToFirstGradeResultGradeset
        this.endorsementToSecondGradeResultGradeset = endorsementToSecondGradeResultGradeset
        this.maxMarkUms = maxMarkUms
        this.noOfComponents = noOfComponents
    }
    
    public ExamOptionDto(ExamOption examOption) {
        List<ExamComponentDto> examComponentsDto = examOption.optionComponents.collect { optionComponent ->
            ExamComponentDto.mapFromExamComponentEntity(optionComponent.examComponent)
        };
        this.examOptionId = examOption.examOptionId
        this.examTypeQualificationCert = (examOption.examTypeCert != null ? examOption.examTypeCert.qualification : null)
        this.examTypeLevelCert = (examOption.examTypeCert != null ? examOption.examTypeCert.level : null)
        this.examTypeQualificationUnit = (examOption.examTypeUnit != null ? examOption.examTypeUnit.qualification : null)
        this.examTypeLevelUnit = (examOption.examTypeUnit != null ? examOption.examTypeUnit.level : null)
        this.examComponentsDto = examComponentsDto
        this.optionEntryCode = examOption.optionEntryCode
        this.syllabusDto = SyllabusDto.mapFromSyllabusEntity(examOption.syllabus)
        this.process = examOption.process
        this.qcaClassificationCode = examOption.qcaClassificationCode
        this.qcaAccreditationNo = examOption.qcaAccreditationNo
        this.optionTitle = examOption.optionTitle
        this.feeDefined = examOption.feeDefined
        this.examinationFee = examOption.examinationFee
        this.firstForecastGradeGradeset = examOption.firstForecastGradeGradeset
        this.secondForecastGradeGradeset = examOption.secondForecastGradeGradeset
        this.resultType = examOption.resultType
        this.firstGradeResultGradeset = examOption.firstGradeResultGradeset
        this.secondGradeResultGradeset = examOption.secondGradeResultGradeset
        this.endorsementToFirstGradeResultGradeset = examOption.endorsementToFirstGradeResultGradeset
        this.endorsementToSecondGradeResultGradeset = examOption.endorsementToSecondGradeResultGradeset
        this.maxMarkUms = examOption.maxMarkUms
        this.noOfComponents = examOption.noOfComponents
    }
    
    /**
     * toString() function to print contents of exam OptionDto object
     */
    public String toString() {
        return "ExamOptionDto [examOptionId: " + String.valueOf(examOptionId) +
                ", examTypeQualificationCert: " + examTypeQualificationCert +
                ", examTypeLevelCert: " + examTypeLevelCert +
                ", examTypeQualificationUnit: " + examTypeQualificationUnit +
                ", examTypeLevelUnit: " + examTypeLevelUnit +
                ", examComponents: " + examComponentsDto +
                ", optionEntryCode: " + optionEntryCode +
                ", syllabusDto: " + syllabusDto +
                ", process: " + process +
                ", qcaClassificationCode: " + qcaClassificationCode +
                ", qcaAccreditationNo: " + qcaAccreditationNo +
                ", optionTitle: " + optionTitle +
                ", feeDefined: " + feeDefined +
                ", examinationFee: " + String.valueOf(examinationFee) +
                ", firstForecastGradeGradeset: " + firstForecastGradeGradeset +
                ", secondForecastGradeGradeset: " + secondForecastGradeGradeset +
                ", resultType: " + resultType +
                ", firstGradeResultGradeset: " + firstGradeResultGradeset +
                ", secondGradeResultGradeset: " + secondGradeResultGradeset +
                ", endorsementToFirstGradeResultGradeset: " + endorsementToFirstGradeResultGradeset +
                ", endorsementToSecondGradeResultGradeset: " + endorsementToSecondGradeResultGradeset +
                ", maxMarkUms: " + String.valueOf(maxMarkUms) +
                ", noOfComponents: " + String.valueOf(noOfComponents) + "]";
    }
    
    public static ExamOptionDto mapFromExamOptionEntity(ExamOption examOption) {
        
        List<ExamComponentDto> examComponentsDto = examOption.optionComponents.collect
        {optionComponent -> ExamComponentDto.mapFromExamComponentEntity(optionComponent.examComponent)} ;
        
        ExamOptionDto output = new ExamOptionDto(examOption.examOptionId,
                (examOption.examTypeCert != null ? examOption.examTypeCert.qualification : null),
                (examOption.examTypeCert != null ? examOption.examTypeCert.level : null),
                (examOption.examTypeUnit != null ? examOption.examTypeUnit.qualification : null),
                (examOption.examTypeUnit != null ? examOption.examTypeUnit.level : null),
                //option.optionComponents,  ? what to do with this?
                examComponentsDto,             //? I think this will do it.
                
                examOption.optionEntryCode,
                examOption.syllabus,
                examOption.process,
                examOption.qcaClassificationCode,
                examOption.qcaAccreditationNo,
                examOption.optionTitle,
                examOption.feeDefined,
                examOption.examinationFee,
                examOption.firstForecastGradeGradeset,
                examOption.secondForecastGradeGradeset,
                examOption.resultType,
                examOption.firstGradeResultGradeset,
                examOption.secondGradeResultGradeset,
                examOption.endorsementToFirstGradeResultGradeset,
                examOption.endorsementToSecondGradeResultGradeset,
                examOption.maxMarkUms,
                examOption.noOfComponents)
        return output;
    }
    
    public static List<ExamOptionDto> mapFromExamOptionEntities(List<ExamOption> examOptions) {
        List<ExamOptionDto> output = examOptions.collect {examOption -> mapFromExamOptionEntity(examOption) };
        return output;
    }
    
    public static ExamOption mapToExamOptionEntity(ExamOptionDto examOptionDto) {
        return new ExamOption.Builder()
                .examOptionId(examOptionDto.examOptionId)
                .examTypeCert(new ExamType.Builder().qualification(examOptionDto.examTypeQualificationCert).level(examOptionDto.examTypeLevelCert).build())
                .examTypeUnit(new ExamType.Builder().qualification(examOptionDto.examTypeQualificationUnit).level(examOptionDto.examTypeLevelUnit).build())
                //.optionComponents(optionDto.optionComponents) ? what to do with this?
                
                .optionEntryCode(examOptionDto.optionEntryCode)
                .syllabus(SyllabusDto.mapToSyllabusEntity(examOptionDto.syllabusDto))
                .process(examOptionDto.process)
                .qcaClassificationCode(examOptionDto.qcaClassificationCode)
                .qcaAccreditationNo(examOptionDto.qcaAccreditationNo)
                .optionTitle(examOptionDto.optionTitle)
                .feeDefined(examOptionDto.feeDefined)
                .examinationFee(examOptionDto.examinationFee)
                .firstForecastGradeGradeset(examOptionDto.firstForecastGradeGradeset)
                .secondForecastGradeGradeset(examOptionDto.secondForecastGradeGradeset)
                .resultType(examOptionDto.resultType)
                .firstGradeResultGradeset(examOptionDto.firstGradeResultGradeset)
                .secondGradeResultGradeset(examOptionDto.secondGradeResultGradeset)
                .endorsementToFirstGradeResultGradeset(examOptionDto.endorsementToFirstGradeResultGradeset)
                .endorsementToSecondGradeResultGradeset(examOptionDto.endorsementToSecondGradeResultGradeset)
                .maxMarkUms(examOptionDto.maxMarkUms)
                .noOfComponents(examOptionDto.noOfComponents)
                .build()
    }
    
    public static List<ExamOption> mapToExamOptionEntities(List<ExamOptionDto> examOptionDtos) {
        return examOptionDtos.collect { examOption -> mapToExamOptionEntity(examOption) };
    }
}
