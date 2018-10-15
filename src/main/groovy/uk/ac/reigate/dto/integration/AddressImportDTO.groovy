package uk.ac.reigate.dto.integration

import com.fasterxml.jackson.annotation.JsonProperty
import com.fasterxml.jackson.databind.annotation.JsonSerialize

import uk.ac.reigate.dto.IAddressDTO

/**
 *
 * JSON serializable DTO containing Address data
 *
 */
@JsonSerialize
public class AddressImportDTO implements IAddressDTO, Serializable {
    
    @JsonProperty
    Integer id
    
    @JsonProperty
    String line1
    
    @JsonProperty
    String line2
    
    @JsonProperty
    String line3
    
    @JsonProperty
    String line4
    
    @JsonProperty
    String line5
    
    @JsonProperty
    String town
    
    @JsonProperty
    String county
    
    @JsonProperty
    String postcode
    
    /**
     * Default No Args constructor
     */
    AddressImportDTO() {
    }
    
    @Override
    public String toString() {
        return "AddressDTO [id=" + id + ", line1=" + line1 + ", line2=" + line2 + ", line3=" + line3 + ", line4=" + line4 + ", line5=" + line5 + ",county=" + county + ", postcode=" + postcode + "]"
    }
}
