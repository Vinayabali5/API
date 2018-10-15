package uk.ac.reigate.dto;


import groovy.transform.EqualsAndHashCode

import com.fasterxml.jackson.annotation.JsonProperty
import com.fasterxml.jackson.databind.annotation.JsonSerialize

import uk.ac.reigate.domain.Person
import uk.ac.reigate.domain.Staff
import uk.ac.reigate.domain.lookup.StaffType

/**
 *
 * JSON serializable DTO containing Staff data
 *
 */
@JsonSerialize
@EqualsAndHashCode(includeFields=true)
public class StaffDto implements Serializable {
    
    @JsonProperty
    private Integer id;
    
    @JsonProperty
    private Integer personId;
    
    @JsonProperty
    private PersonDto person;
    
    @JsonProperty
    private Integer typeId;
    
    @JsonProperty
    private Boolean onTimetable;
    
    @JsonProperty
    private String initials;
    
    @JsonProperty
    private String knownAs;
    
    @JsonProperty
    private String networkLogin;
    
    @JsonProperty
    private Date startDate;
    
    @JsonProperty
    private Date endDate;
    
    @JsonProperty
    private Integer staffRef;
    
    @JsonProperty
    private Integer staffDetailsId;
    
    /**
     * Default No Args constructor
     */
    public StaffDto() {
    }
    
    /**
     * Constructor to create a StaffDto object from a Staff object
     *
     * @param staff the Staff object to use for construction
     */
    StaffDto(Staff staff){
        this.id = staff.id;
        this.personId = staff.person != null ? staff.person.id : null;
        this.person = new PersonDto(staff.person);
        this.typeId = staff.type != null ? staff.type.id : null;
        this.onTimetable = staff.onTimetable;
        this.initials = staff.initials;
        this.knownAs = staff.knownAs;
        this.networkLogin = staff.networkLogin
        this.startDate = staff.startDate;
        this.endDate = staff.endDate;
        this.staffRef = staff.staffRef;
        this.staffDetailsId = staff.staffDetailsId;
    }
    
    /**
     * This Constructor is used to create a StaffDto object with the basic data with no linked objects.
     * 
     * @param id
     * @param personId
     * @param typeId
     * @param onTimetable
     * @param initials
     * @param knownAs
     * @param networkLogin
     * @param startDate
     * @param endDate
     * @param staffRef
     * @param staffDetailsId
     */
    @Deprecated
    public StaffDto(Integer id, Integer personId, Integer typeId, Boolean onTimetable, String initials, String knownAs, String networkLogin, Date startDate, Date endDate, Integer staffRef, Integer staffDetailsId){
        this.id = id;
        this.personId = personId;
        this.person = null;
        this.typeId = typeId;
        this.onTimetable = onTimetable;
        this.initials = initials;
        this.knownAs = knownAs;
        this.networkLogin = networkLogin
        this.startDate = startDate;
        this.endDate = endDate;
        this.staffRef = staffRef;
        this.staffDetailsId = staffDetailsId;
    }
    
    /**
     * This Constructor is used to create a StaffDto object with the basic data and the linked Person object 
     * 
     * @param id
     * @param person
     * @param type
     * @param onTimetable
     * @param initials
     * @param knownAs
     * @param networkLogin
     * @param startDate
     * @param endDate
     * @param staffRef
     * @param staffDetailsId
     */
    @Deprecated
    public StaffDto(Integer id, PersonDto person, StaffType type, Boolean onTimetable, String initials, String knownAs, String networkLogin, Date startDate, Date endDate, Integer staffRef, Integer staffDetailsId){
        this(id, person != null ? person.id : null, type != null ? type.id : null, onTimetable, initials, knownAs, networkLogin, startDate, endDate, staffRef, staffDetailsId)
        this.person = person;
    }
    
    @Override
    public String toString() {
        return "StaffDto [id=" + id + ", person=" + personId + ", type=" + typeId + ", onTimetable=" + onTimetable + ", initials=" + initials + ", knownAs=" + knownAs + ", networkLogin=" + networkLogin + ", startDate=" + startDate + ", endDate=" + endDate + ", staffRef=" + staffRef + ", staffDetailsId=" + staffDetailsId + "]";
    }
    
    @Deprecated
    public static StaffDto mapFromStaffEntity(Staff staff) {
        return new StaffDto(staff);
    }
    
    @Deprecated
    public static List<StaffDto> mapFromStaffsEntities(List<Staff> staffList) {
        return staffList.collect { staff ->  mapFromStaffEntity(staff) };
    }
    
    @Deprecated
    public static Staff mapToStaffEntity(StaffDto staffDto, Person person, StaffType type) {
        return new Staff(staffDto.id, person, type, staffDto.onTimetable, staffDto.initials, staffDto.knownAs, staffDto.networkLogin, staffDto.startDate, staffDto.endDate, staffDto.staffRef, staffDto.staffDetailsId)
    }
    
    /**
     * This method is used to map a Staff object into a StaffDto object
     * 
     * @param staff a Staff object
     * @return the StaffDto object
     */
    public static StaffDto mapFromEntity(Staff staff) {
        return new StaffDto(staff);
    }
    
    /**
     * This method is used to map from a list of Staff objects into a list of StaffDto objects
     * 
     * @param staffList a list of Staff objects
     * @return the list of StaffDto objects
     */
    public static List<StaffDto> mapFromList(List<Staff> staffList) {
        return staffList.collect { staff ->  mapFromEntity(staff) };
    }
}
