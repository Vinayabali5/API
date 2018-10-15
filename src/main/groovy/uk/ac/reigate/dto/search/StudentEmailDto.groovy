package uk.ac.reigate.dto.search

import com.fasterxml.jackson.annotation.JsonProperty


class StudentEmailDto {
    
    @JsonProperty
    Integer studentId
    
    @JsonProperty
    String description
}
