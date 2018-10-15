package uk.ac.reigate.dto.exam

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonSerialize

import groovy.transform.EqualsAndHashCode
import uk.ac.reigate.domain.exam.ExamComponent
import uk.ac.reigate.domain.exam.ExamOption
import uk.ac.reigate.domain.exam.OptionComponent;;

@JsonSerialize
@EqualsAndHashCode(includeFields=true)
class OptionComponentDto {
    
    @JsonProperty
    private ExamOption examOption;
    
    @JsonProperty
    private ExamComponent examComponent;
    
    /**
     * Default No Args constructor
     */
    OptionComponentDto(){}
    
    /**
     * Constructor ro create an Exam OptionComponent object
     *     
     * @param option
     * @param examComponent
     */
    OptionComponentDto(ExamOption examOption, ExamComponent examComponent) {
        this.examOption = examOption;
        this.examComponent = examComponent;
    }
    
    String toString() {
        return "OptionComponent [examOption: " + this.examOption +
                ", examComponent: " + this.examComponent + "]";
    }
    
    public static OptionComponentDto mapFromOptionComponentEntity(OptionComponent optionComponent) {
        OptionComponentDto output = new OptionComponentDto(optionComponent.examOption, optionComponent.examComponent);
        return output;
    }
    
    public static List<OptionComponentDto> mapFromOptionComponentEntities(List<OptionComponent> optionComponents) {
        List<OptionComponentDto> output = optionComponents.collect { optionComponent -> mapFromOptionComponentEntity(optionComponent) } ;
        return output;
    }
    
    public static OptionComponent mapToOptionComponentEntity(OptionComponentDto optionComponentDto) {
        return new OptionComponent(optionComponentDto.examOption, optionComponentDto.examComponent);
    }
    
    public static List<OptionComponent> mapToOptionComponentEntities(List<OptionComponentDto> optionComponentDtos) {
        return optionComponentDtos.collect { optionComponentDto -> mapToOptionComponentEntity(optionComponentDto) } ;
    }
}
