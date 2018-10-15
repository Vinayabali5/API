package uk.ac.reigate.dto

import java.util.List

import com.fasterxml.jackson.annotation.JsonProperty

import uk.ac.reigate.domain.ilp.Letter
import uk.ac.reigate.domain.system.LetterTemplate

class LetterTemplateDto implements Serializable{
    
    @JsonProperty
    Integer id
    
    @JsonProperty
    String name
    
    @JsonProperty
    String description
    
    @JsonProperty
    String templateText
    
    @JsonProperty
    Boolean inUse
    
    
    LetterTemplateDto(){
    }
    
    LetterTemplateDto(LetterTemplate letterTemplate){
        this.id = letterTemplate.id
        this.name = letterTemplate.name
        this.description = letterTemplate.description
        this.templateText = letterTemplate.templateText
        this.inUse = letterTemplate.inUse
    }
    
    public static List<LetterTemplateDto> mapFromLettersTemplateEntities(List<LetterTemplate> letterTemplates) {
        return letterTemplates.collect { letterTemplate ->  new LetterTemplateDto(letterTemplate) };
    }
    
    public static LetterTemplate mapToLetterTemplate(LetterTemplateDto letterTemplateDto){
        return new LetterTemplate(letterTemplateDto.id, letterTemplateDto.name, letterTemplateDto.description, letterTemplateDto.templateText, letterTemplateDto.inUse)
    }
}
