package uk.ac.reigate.dto;


import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;

import groovy.transform.EqualsAndHashCode;
import uk.ac.reigate.domain.admissions.CollegeFundPaid

/**
 *
 * JSON  DTO containing CollegeFundPaid data
 *
 */
@JsonSerialize
@EqualsAndHashCode(includeFields=true)
public class CollegeFundPaidDto  {
    
    @JsonProperty
    private Integer id;
    
    @JsonProperty
    private String collegeFundPaid;
    
    /**
     * Default No Args constructor
     */
    public CollegeFundPaidDto() {
    }
    
    /**
     * Constructor to create a CollegeFundPaidDto object
     *
     * @param id the Id for the CollegeFundPaid
     * @param collegeFundPaid the CollegeFundPaid for the CollegeFundPaid
     */
    public CollegeFundPaidDto(Integer id, String collegeFundPaid) {
        this.id = id;
        this.collegeFundPaid = collegeFundPaid;
    }
    
    /**
     * Constructor to create a CollegeFundPaidDto object from a CollegeFundPaid object
     *
     * @param collegeFundPaid the CollegeFundPaid object to use for construction
     */
    CollegeFundPaidDto(CollegeFundPaid collegeFundPaid) {
        this.id = collegeFundPaid.id;
        this.collegeFundPaid = collegeFundPaid.collegeFundPaid;
    }
    
    @Override
    public String toString() {
        return "CollegeFundPaidDto [id=" + id + ", collegeFundPaid=" + collegeFundPaid + "]";
    }
    
    public static CollegeFundPaidDto mapFromCollegeFundPaidEntity(CollegeFundPaid collegeFundPaid) {
        return new CollegeFundPaidDto(collegeFundPaid);
    }
    
    public static List<CollegeFundPaidDto> mapFromCollegeFundPaidsEntities(List<CollegeFundPaid> collegeFundPaids) {
        List<CollegeFundPaidDto> output = collegeFundPaids.collect { collegeFundPaid ->  new CollegeFundPaidDto(collegeFundPaid) };
        return output
    }
    
    public static CollegeFundPaid mapToCollegeFundPaidEntity(CollegeFundPaidDto collegeFundPaidDto) {
        return new CollegeFundPaid(collegeFundPaidDto.id, collegeFundPaidDto.collegeFundPaid)
    }
}
