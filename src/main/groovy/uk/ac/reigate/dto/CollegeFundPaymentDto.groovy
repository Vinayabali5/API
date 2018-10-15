package uk.ac.reigate.dto;


import groovy.transform.EqualsAndHashCode
import uk.ac.reigate.domain.academic.Student
import uk.ac.reigate.domain.admissions.CollegeFundPayment

import com.fasterxml.jackson.annotation.JsonFormat
import com.fasterxml.jackson.annotation.JsonProperty
import com.fasterxml.jackson.databind.annotation.JsonSerialize

/**
 *
 * JSON serializable DTO containing CollegeFundPayment data
 *
 */
@JsonSerialize
@EqualsAndHashCode(includeFields=true)
public class CollegeFundPaymentDto implements Serializable {
    
    @JsonProperty
    private Integer id;
    
    @JsonProperty
    private Integer studentId;
    
    @JsonProperty
    //@JsonFormat(pattern="dd/MM/yyyy")
    private Date paymentDate;
    
    @JsonProperty
    private float amount;
    
    @JsonProperty
    private String payee;
    
    @JsonProperty
    private boolean giftAid;
    
    @JsonProperty
    private boolean cash
    
    @JsonProperty
    //@JsonFormat(pattern="dd/MM/yyyy")
    private Date chequeDate
    
    /**
     * Default No Args constructor
     */
    public CollegeFundPaymentDto() {
    }
    
    /**
     * Constructor to create a CollegeFundPaymentDto object from an CollegeFundPayment object
     *
     * @param collegeFundPayment the CollegeFundPayment object to use for construction
     */
    CollegeFundPaymentDto(CollegeFundPayment collegeFundPayment) {
        this.id= collegeFundPayment.id;
        this.studentId = collegeFundPayment.student != null ? collegeFundPayment.student.id : null;
        this.paymentDate = collegeFundPayment.paymentDate;
        this.amount = collegeFundPayment.amount;
        this.payee = collegeFundPayment.payee;
        this.giftAid = collegeFundPayment.giftAid;
        this.cash = collegeFundPayment.cash;
        this.chequeDate = collegeFundPayment.chequeDate;
    }
    
    /**
     * Constructor to create a CollegeFundPaymentDto object
     *
     * @param id the Id for the CollegeFundPayment
     * @param studentId the studentId for the CollegeFundPayment
     * @param paymentDate the paymentDate for the CollegeFundPayment
     * @Param amount the amount of the CollegeFundPayment
     * @Param payee the payee of the CollegeFundPayment
     * @Param giftAid the giftAid of the CollegeFundPayment
     * @Param cash the cash of the CollegeFundPayment
     * @Param chequeDate the chequeDate of the CollegeFundPayment
     */
    public CollegeFundPaymentDto(Integer id, Integer studentId, Date paymentDate, float amount, String payee, boolean giftAid, boolean cash, Date chequeDate) {
        this.id= id;
        this.studentId = studentId;
        this.paymentDate = paymentDate;
        this.amount = amount;
        this.payee = payee;
        this.giftAid = giftAid;
        this.cash = cash;
        this.chequeDate = chequeDate;
    }
    
    /**
     * Constructor to create a CollegeFundPaymentDto object
     * 
     * @param id the Id for the CollegeFundPayment
     * @param student the Student object for the CollegeFundPayment
     * @param paymentDate the paymentDate for the CollegeFundPayment
     * @param amount the amount of the CollegeFundPayment
     * @param payee the payee of the CollegeFundPayment
     * @param giftAid the giftAid of the CollegeFundPayment
     * @param cash the cash of the CollegeFundPayment
     * @param chequeDate the chequeDate of the CollegeFundPayment
     */
    public CollegeFundPaymentDto(Integer id, Student student, Date paymentDate, float amount, String payee, boolean giftAid, boolean cash, Date chequeDate) {
        this(id, student != null ? student.id : null, paymentDate, amount, payee, giftAid, cash, chequeDate)
    }
    
    @Override
    public String toString() {
        return "CollegeFundPayment [id=" + id + ", student=" + studentId + ", paymentDate=" + paymentDate + ", amount=" + amount + ", payee=" + payee + ", giftAid=" + giftAid + ", cash=" + cash + ", chequeDate=" + chequeDate + "]";
    }
    
    public static CollegeFundPaymentDto mapFromCollegeFundPaymentEntity(CollegeFundPayment collegeFundPayment) {
        return new CollegeFundPaymentDto(collegeFundPayment)
    }
    
    public static List<CollegeFundPaymentDto> mapFromCollegeFundPaymentsEntities(List<CollegeFundPayment> collegeFundPayments) {
        return collegeFundPayments.collect { collegeFundPayment ->  new CollegeFundPaymentDto(collegeFundPayment) };
    }
    
    public static CollegeFundPayment mapToCollegeFundPaymentEntity(CollegeFundPaymentDto collegeFundPaymentDto, Student student) {
        return new CollegeFundPayment(collegeFundPaymentDto.id, student, collegeFundPaymentDto.paymentDate, collegeFundPaymentDto.amount, collegeFundPaymentDto.payee, collegeFundPaymentDto.giftAid, collegeFundPaymentDto.cash, collegeFundPaymentDto.chequeDate)
    }
    
}
