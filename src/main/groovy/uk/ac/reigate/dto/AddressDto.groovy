package uk.ac.reigate.dto;


import groovy.transform.EqualsAndHashCode

import com.fasterxml.jackson.annotation.JsonProperty
import com.fasterxml.jackson.databind.annotation.JsonSerialize

import uk.ac.reigate.domain.Address

/**
 *
 * JSON serializable DTO containing Address data
 *
 */
@JsonSerialize
@EqualsAndHashCode(includeFields=true)
public class AddressDto implements Serializable {
    
    @JsonProperty
    private Integer id;
    
    @JsonProperty
    private String line1;
    
    @JsonProperty
    private String line2;
    
    @JsonProperty
    private String line3;
    
    @JsonProperty
    private String line4;
    
    @JsonProperty
    private String line5;
    
    @Deprecated
    @JsonProperty
    private String paon;
    
    @Deprecated
    @JsonProperty
    private String saon;
    
    @JsonProperty
    private String town;
    
    @JsonProperty
    private String county;
    
    @JsonProperty
    private String postcode;
    
    @JsonProperty
    private String buildingName;
    
    @JsonProperty
    private String subBuilding;
    
    @JsonProperty
    private String udprn;
    
    @JsonProperty
    private String _fullAddress
    
    /**
     * Default No Args constructor
     */
    public AddressDto() {
    }
    
    /**
     * Constructor to create an AddressDto object from an Address object
     *
     * @param address the Address object to use for construction
     */
    AddressDto(Address address) {
        this.id = address.id
        this.line1 = address.line1;
        this.line2 = address.line2;
        this.line3 = address.line3;
        this.line4 = address.line4;
        this.line5 = address.line5;
        this.town = address.town
        this.buildingName = address.buildingName
        this.subBuilding = address.subBuilding
        this.county = address.county
        this.postcode = address.postcode
        this.saon = address.saon
        this.paon = address.paon
        this._fullAddress = address.fullAddress
    }
    
    @Override
    public String toString() {
        return "AddressDto [id=" + id + ", line1=" + line1 + ", line2=" + line2 + ", line3=" + line3 + ", line4=" + line4 + ", line5=" + line5 + ", paon=" + paon + ", saon=" + saon + ", town=" + town + ", county=" + county + ", postcode=" + postcode + ", buildingName=" + buildingName + ", subBuilding=" + subBuilding + ", udprn=" + udprn + "]";
    }
    
    public static AddressDto mapFromAddressEntity(Address address) {
        return new AddressDto(address);
    }
    
    public static List<AddressDto> mapFromAddressesEntities(List<Address> addresses) {
        List<AddressDto> output = addresses.collect { address ->  new AddressDto(address) };
        return output
    }
    
    public static Address mapToAddressEntity(AddressDto addressDto) {
        return new Address(addressDto.id, addressDto.line1, addressDto.line2, addressDto.line3, addressDto.line4, addressDto.line5, addressDto.town, addressDto.county, addressDto.postcode, addressDto.buildingName, addressDto.subBuilding, addressDto.paon, addressDto.saon)
    }
}
