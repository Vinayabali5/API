package uk.ac.reigate.dto;


import groovy.transform.EqualsAndHashCode
import uk.ac.reigate.domain.ilr.Outcome

import com.fasterxml.jackson.annotation.JsonFormat
import com.fasterxml.jackson.annotation.JsonProperty
import com.fasterxml.jackson.databind.annotation.JsonSerialize

/**
 *
 * JSON serializable DTO containing Outcome data
 *
 */
@JsonSerialize
@EqualsAndHashCode(includeFields=true)
public class OutcomeDto implements Serializable {
    
    @JsonProperty
    private Integer id;
    
    @JsonProperty
    private String code;
    
    @JsonProperty
    private String description;
    
    @JsonProperty
    private String shortDescription;
    
    @JsonProperty
    private Date validFrom;
    
    @JsonProperty
    private Date validTo;
    
    /**
     * Default No Args constructor
     */
    public OutcomeDto() {
    }
    
    /**
     * Constructor to create an OutcomeDto object
     *
     * @param id the Id for the Outcome
     * @param code the code for the Outcome
     * @param description the description for the Outcome
     * @Param shortDescription the shortDescription of the Outcome
     * @Param validFrom the validFrom Date for the Outcome
     * @Param validTo the validTo Date for the Outcome
     */
    public OutcomeDto(Integer id, String code, String description, String shortDescription, Date validFrom, Date validTo) {
        this.id = id;
        this.code = code;
        this.description = description;
        this.shortDescription = shortDescription;
        this.validFrom = validFrom;
        this.validTo = validTo;
    }
    
    /**
     * Constructor to create an OutcomeDto object from a Outcome object
     *
     * @param outcome the Outcome object to use for construction
     */
    OutcomeDto(Outcome outcome) {
        this.id = outcome.id;
        this.code = outcome.code;
        this.description = outcome.description;
        this.shortDescription = outcome.shortDescription;
        this.validFrom = outcome.validFrom;
        this.validTo = outcome.validTo;
    }
    
    @Override
    public String toString() {
        return "OutcomeDto [id=" + id + ", code=" + code + ", description=" + description + ", shortDescription=" + shortDescription + ", validFrom=" + validFrom + ", validTo=" + validTo + "]";
    }
    
    public static OutcomeDto mapFromOutcomeEntity(Outcome outcome) {
        return new OutcomeDto(outcome);
    }
    
    public static List<OutcomeDto> mapFromOutcomesEntities(List<Outcome> outcomes) {
        List<OutcomeDto> output = outcomes.collect { outcome ->  new OutcomeDto(outcome) };
        return output
    }
    
    public static Outcome mapToOutcomeEntity(OutcomeDto outcomeDto) {
        return new Outcome(outcomeDto.id, outcomeDto.code, outcomeDto.description, outcomeDto.shortDescription, outcomeDto.validFrom, outcomeDto.validTo)
    }
}
