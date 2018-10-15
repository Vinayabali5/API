package uk.ac.reigate.dto;


import groovy.transform.EqualsAndHashCode

import com.fasterxml.jackson.annotation.JsonProperty
import com.fasterxml.jackson.databind.annotation.JsonSerialize

import uk.ac.reigate.domain.Staff

/**
 * This class is a DTO for display a summary of a staff object.  
 */
@JsonSerialize
@EqualsAndHashCode(includeFields=true)
public class StaffSummaryDto implements Serializable {
    
    @JsonProperty
    private Integer id;
    
    @JsonProperty
    private Integer personId;
    
    @JsonProperty
    private PersonSummaryDto person;
    
    @JsonProperty
    private Integer typeId;
    
    @JsonProperty
    private String initials;
    
    /**
     * Constructor to create a StaffDto object from a Staff object
     *
     * @param staff the Staff object to use for construction
     */
    StaffSummaryDto(Staff staff){
        this.id = staff.id;
        this.personId = staff.person != null ? staff.person.id : null;
        this.person = new PersonSummaryDto(staff.person);
        this.typeId = staff.type != null ? staff.type.id : null;
        this.initials = staff.initials;
    }
    
    @Override
    public String toString() {
        return "StaffSummaryDto [id=" + id + ", personId=" + personId + ", person=" + person + ", typeId=" + typeId + ", initials=" + initials + "]";
    }
    
    /**
     * This method is used to map a Staff object into a StaffDto object
     * 
     * @param staff a Staff object
     * @return the StaffDto object
     */
    public static StaffSummaryDto mapFromEntity(Staff staff) {
        return new StaffSummaryDto(staff);
    }
    
    /**
     * This method is used to map from a list of Staff objects into a list of StaffDto objects
     * 
     * @param staffList a list of Staff objects
     * @return the list of StaffDto objects
     */
    public static List<StaffSummaryDto> mapFromList(List<Staff> staffList) {
        return staffList.collect { staff ->  new StaffSummaryDto(staff) };
    }
}
