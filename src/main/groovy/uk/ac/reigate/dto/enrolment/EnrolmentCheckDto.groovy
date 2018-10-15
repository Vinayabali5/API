package uk.ac.reigate.dto.enrolment

import com.fasterxml.jackson.annotation.JsonProperty

class EnrolmentOptionDto {
    
    @JsonProperty
    String[] combination
}

class EnrolmentCheckDto {
    
    @JsonProperty
    String[] specs
    
    EnrolmentCheckDto() {}
    
    EnrolmentCheckDto(String[] specs) {
        this.specs = specs
    }
}

class EnrolmentCheckResultsDto extends EnrolmentCheckDto {
    
    @JsonProperty
    String status
    
    @JsonProperty
    List<EnrolmentOptionDto> options
    
    EnrolmentCheckResultsDto() {}
    
    EnrolmentCheckResultsDto(String[] specs, String status, List<EnrolmentOptionDto> options) {
        super(specs)
        this.status = status
        this.options = options
    }
}
