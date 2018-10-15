package uk.ac.reigate.dto.integration;

import groovy.transform.EqualsAndHashCode

import javax.validation.constraints.NotNull

import com.fasterxml.jackson.annotation.JsonProperty
import com.fasterxml.jackson.databind.annotation.JsonSerialize

/**
 * This class is used to export a Contact object to the CID system.
 * 
 * @author Michael Horgan
 *
 */
@JsonSerialize
@EqualsAndHashCode(includeFields=true)
public class ContactImportDTO implements Serializable {
    
    @JsonProperty
    private Integer id;
    
    @NotNull
    @JsonProperty
    private String contactType;
    
    @JsonProperty
    private String title;
    
    @NotNull
    @JsonProperty
    private String firstName
    
    @NotNull
    @JsonProperty
    private String surname
    
    @JsonProperty
    private String home
    
    @JsonProperty
    private String mobile
    
    @JsonProperty
    private String email
    
    @JsonProperty
    private boolean contactable
    
    @JsonProperty
    private boolean preferred
    
    @JsonProperty
    private AddressImportDTO address
    
    @JsonProperty
    private Boolean _alternativeAddress
}
