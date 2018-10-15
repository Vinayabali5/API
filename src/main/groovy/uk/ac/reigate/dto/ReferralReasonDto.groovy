package uk.ac.reigate.dto;


import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;

import groovy.transform.EqualsAndHashCode
import uk.ac.reigate.domain.ilr.LLDDHealthProblemCategory;
import uk.ac.reigate.domain.learning_support.ReferralReason

/**
 *
 * JSON serializable DTO containing ReferralReason data
 *
 */
@JsonSerialize
@EqualsAndHashCode(includeFields=true)
public class ReferralReasonDto implements Serializable {
    
    @JsonProperty
    private Integer id;
    
    @JsonProperty
    private String reason;
    
    @JsonProperty
    private Integer llddHealthProblemCategoryId;
    
    @JsonProperty
    private String _llddHealthProblemCategoryDescription;
    
    /**
     * Default No Args constructor
     */
    public ReferralReasonDto() {
    }
    
    /**
     * Constructor to create a ReferralReasonDto object from a ReferralReason object
     *
     * @param referralReason the ReferralReason object to use for construction
     */
    ReferralReasonDto(ReferralReason referralReason) {
        this.id = referralReason.id;
        this.reason = referralReason.reason;
        this.llddHealthProblemCategoryId = referralReason.llddHealthProblemCategory != null ? referralReason.llddHealthProblemCategory.id : null;
        this._llddHealthProblemCategoryDescription =  referralReason.llddHealthProblemCategory != null ? referralReason.llddHealthProblemCategory.description :'';
    }
    
    /**
     * Constructor to create a ReferralReasonDto object with the basic data with no linked objects.
     *
     * @param id the Id for the ReferralReason
     * @param reason the reason for the ReferralReason
     * @param llddHealthProblemCategoryId the llddHealthProblemCategoryId for the ReferralReason
     */
    public ReferralReasonDto(Integer id, String reason, Integer llddHealthProblemCategoryId) {
        this.id = id;
        this.reason = reason;
        this.llddHealthProblemCategoryId = llddHealthProblemCategoryId;
    }
    
    /**
     * Constructor to create a ReferralReasonDto object with the basic data with linked ReferralReason objects.
     *
     * @param id the Id for the ReferralReason
     * @param reason the reason for the ReferralReason
     * @param llddHealthProblemCategory the llddHealthProblemCategory for the ReferralReason
     */
    public ReferralReasonDto(Integer id, String reason, LLDDHealthProblemCategory llddHealthProblemCategory) {
        this(id, reason, llddHealthProblemCategory != null ? llddHealthProblemCategory.id : null)
    }
    
    @Override
    public String toString() {
        return "ReferralReasonDto [id=" + id + ", reason=" + reason + ", llddHealthProblemCategory=" + llddHealthProblemCategoryId + "]";
    }
    
    public ReferralReason toReferralReason() {
        return new ReferralReason(this.id, this.reason, this.llddHealthProblemCategoryId);
    }
    
    public static ReferralReasonDto mapFromReferralReasonEntity(ReferralReason referralReason) {
        return new ReferralReasonDto(referralReason);
    }
    
    public static List<ReferralReasonDto> mapFromReferralReasonsEntities(List<ReferralReason> referralReasons) {
        return referralReasons.collect { referralReason ->  mapFromReferralReasonEntity(referralReason) };
    }
    
    public static ReferralReason mapToReferralReasonEntity(ReferralReasonDto referralReasonDto, LLDDHealthProblemCategory llddHealthProblemCategory) {
        return new ReferralReason(referralReasonDto.id, referralReasonDto.reason, llddHealthProblemCategory)
    }
}
