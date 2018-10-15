package uk.ac.reigate.dto.exam

import com.fasterxml.jackson.annotation.JsonProperty
import com.fasterxml.jackson.databind.annotation.JsonSerialize

import groovy.transform.EqualsAndHashCode
import uk.ac.reigate.domain.exam.Results

@JsonSerialize
@EqualsAndHashCode(includeFields=true)
class ResultsDto {
    
    @JsonProperty
    Integer id
    
    @JsonProperty
    Integer studentId
    
    @JsonProperty
    Integer academicYearId
    
    @JsonProperty
    String _academicYearCode
    
    @JsonProperty
    String candidateNo
    
    @JsonProperty
    String boardIdentifier
    
    @JsonProperty
    String _boardName
    
    @JsonProperty
    String examSeries
    
    @JsonProperty
    String examYear
    
    @JsonProperty
    String resultCode
    
    @JsonProperty
    String _examOptionTitle
    
    @JsonProperty
    String _examComponentTitle
    
    @JsonProperty
    String score
    
    @JsonProperty
    String grade
    
    @JsonProperty
    Date importDate
    
    @JsonProperty
    Date examDate
    
    ResultsDto(){}
    
    
    /**Constructor to create ResultsDto
     * @param results - Result object 
     */
    ResultsDto(Results results){
        this.id = results.id
        this.studentId = results.student.id
        this.academicYearId = results.academicYear.id
        this._academicYearCode = results.academicYear.code
        this.candidateNo = results.candidateNo
        this.boardIdentifier= results.examBoard!=null?results.examBoard.boardIdentifier:null
        this._boardName= results.examBoard!=null?results.examBoard.name:null
        this.examSeries=results.examSeries!=null?results.examSeries:null
        this.examYear = results.examYear
        this.resultCode=results.resultsCode!=null?results.resultsCode:null
        this._examOptionTitle=results.examOption!=null?results.examOption.optionTitle:null
        this.score= results.score
        this.grade= results.grade
        this.examDate =  results.examDate
        this.importDate =  results.importDate
    }
    
    
    /**
     * @param resultList - List of Results to be converted to Result Dto objects
     * 
     * @return List of ResultDto objects
     */
    public static List<ResultsDto> mapFromResultEntities(List<Results> resultList){
        return resultList.collect { result ->  new ResultsDto(result) };
    }
}
