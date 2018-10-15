package uk.ac.reigate.dto;


import groovy.transform.EqualsAndHashCode
import uk.ac.reigate.domain.ilr.WithdrawalReason

import com.fasterxml.jackson.annotation.JsonFormat
import com.fasterxml.jackson.annotation.JsonProperty
import com.fasterxml.jackson.databind.annotation.JsonSerialize

/**
 *
 * JSON serializable DTO containing WithdrawalReason data
 *
 */
@JsonSerialize
@EqualsAndHashCode(includeFields=true)
public class WithdrawalReasonDto implements Serializable {
    
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
    public WithdrawalReasonDto() {
    }
    
    /**
     * Constructor to create a WithdrawalReasonDto object
     *
     * @param id the Id for the WithdrawalReason
     * @param code the code for the WithdrawalReason
     * @param description the description for the WithdrawalReason
     * @Param shortDescription the shortDescription of the WithdrawalReason
     * @Param validFrom the validFrom Date for the WithdrawalReason
     * @Param validTo the validTo Date for the WithdrawalReason
     */
    public WithdrawalReasonDto(Integer id, String code, String description, String shortDescription, Date validFrom, Date validTo) {
        this.id = id;
        this.code = code;
        this.description = description;
        this.shortDescription = shortDescription;
        this.validFrom = validFrom;
        this.validTo = validTo;
    }
    
    /**
     * Constructor to create a WithdrawalReasonDto object from a WithdrawalReason object
     *
     * @param withdrawalReason the WithdrawalReason object to use for construction
     */
    WithdrawalReasonDto(WithdrawalReason withdrawalReason) {
        this.id = withdrawalReason.id;
        this.code = withdrawalReason.code;
        this.description = withdrawalReason.description;
        this.shortDescription = withdrawalReason.shortDescription;
        this.validFrom = withdrawalReason.validFrom;
        this.validTo = withdrawalReason.validTo;
    }
    
    @Override
    public String toString() {
        return "WithdrawalReasonDto [id=" + id + ", code=" + code + ", description=" + description + ", shortDescription=" + shortDescription + ", validFrom=" + validFrom + ", validTo=" + validTo + "]";
    }
    
    public static WithdrawalReasonDto mapFromWithdrawalReasonEntity(WithdrawalReason withdrawalReason) {
        return new WithdrawalReasonDto(withdrawalReason);
    }
    
    public static List<WithdrawalReasonDto> mapFromWithdrawalReasonsEntities(List<WithdrawalReason> withdrawalReasons) {
        List<WithdrawalReasonDto> output = withdrawalReasons.collect { withdrawalReason ->  new WithdrawalReasonDto(withdrawalReason) };
        return output
    }
    
    public static WithdrawalReason mapToWithdrawalReasonEntity(WithdrawalReasonDto withdrawalReasonDto) {
        return new WithdrawalReason(withdrawalReasonDto.id, withdrawalReasonDto.code, withdrawalReasonDto.description, withdrawalReasonDto.shortDescription, withdrawalReasonDto.validFrom, withdrawalReasonDto.validTo)
    }
}
