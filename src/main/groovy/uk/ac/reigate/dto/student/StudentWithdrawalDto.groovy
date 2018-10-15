package uk.ac.reigate.dto.student

import javax.validation.constraints.NotNull

import com.fasterxml.jackson.annotation.JsonFormat
import com.fasterxml.jackson.annotation.JsonProperty

class StudentWithdrawalDto {
    
    /**
     * The ID for the student 
     */
    @NotNull
    @JsonProperty
    Integer studentId
    
    /**
     * The ID for the academic year
     */
    @NotNull
    @JsonProperty
    Integer yearId
    
    /**
     * The ID for the students destination after leaving
     */
    @JsonProperty
    Integer destinationId
    
    @JsonProperty
    Integer withdrawalResonId
    
    @JsonProperty
    Date withdrawalDate
    
    @JsonProperty
    String collegeEmployer
    
    @JsonProperty
    String courseCareer
}
