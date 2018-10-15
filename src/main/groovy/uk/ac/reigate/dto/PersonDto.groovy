package uk.ac.reigate.dto;


import groovy.transform.EqualsAndHashCode

import com.fasterxml.jackson.annotation.JsonProperty
import com.fasterxml.jackson.databind.annotation.JsonSerialize

import uk.ac.reigate.domain.Address
import uk.ac.reigate.domain.Person
import uk.ac.reigate.domain.lookup.Gender
import uk.ac.reigate.domain.lookup.Title
import uk.ac.reigate.domain.security.Role

/**
 *
 * JSON serializable DTO containing Person data
 *
 */
@JsonSerialize
@EqualsAndHashCode(includeFields=true)
public class PersonDto implements Serializable {
    
    @JsonProperty
    private Integer id;
    
    @JsonProperty
    private Integer titleId;
    
    @JsonProperty
    private String _titleDescription
    
    @JsonProperty
    private String firstName;
    
    @JsonProperty
    private String preferredName;
    
    @JsonProperty
    private String surname;
    
    @JsonProperty
    private String legalSurname
    
    @JsonProperty
    private String middleNames;
    
    @JsonProperty
    private String previousSurname;
    
    @JsonProperty
    private Date dob;
    
    @JsonProperty
    private Integer genderId;
    
    @JsonProperty
    private String _genderCode
    
    @JsonProperty
    private Integer addressId;
    
    @JsonProperty
    private String home;
    
    @JsonProperty
    private String mobile;
    
    @JsonProperty
    private String work;
    
    @JsonProperty
    private String email;
    
    @JsonProperty
    private Boolean enabled;
    
    @JsonProperty
    private AddressDto address;
    
    @JsonProperty
    private Set<Role> roles;
    
    @JsonProperty
    private String username;
    
    /**
     * Default No Args constructor
     */
    public PersonDto() {
    }
    
    /**
     * Constructor to create a PersonDto object from a Person object
     *
     * @param person the Person object to use for construction
     */
    PersonDto(Person person){
        this.id = person.id;
        this.titleId = person.title != null ? person.title.id : null;
        this.firstName = person.firstName;
        this.surname = person.surname;
        this.legalSurname = person.legalSurname;
        this.preferredName = person.preferredName;
        this.middleNames = person.middleNames;
        this.previousSurname = person.previousSurname;
        this.dob = person.dob;
        this.genderId = person.gender != null ? person.gender.id : null;
        this.address = person.address != null ?  AddressDto.mapFromAddressEntity(person.address) : null;
        this.addressId = person.address != null ? person.address.id : null;
        this.home = person.home;
        this.mobile = person.mobile;
        this.work = person.work;
        this.email = person.email;
        this.enabled = person.enabled;
        this._genderCode = person.gender != null ? person.gender.code : ''
        this._titleDescription = person.title != null ? person.title.description : ''
        this.roles = person.roles;
        this.username = person.username;
    }
    
    @Override
    public String toString() {
        return "PersonDto [id=" + id + ", title=" + titleId + ", firstName=" + firstName + ", preferredName=" + preferredName + ", surname=" + surname + ", legalSurname=" + legalSurname + ", middleNames=" + middleNames + ", previousSurname=" + previousSurname + ", dob=" + dob + ", gender=" + genderId + ", address=" + addressId + ", home=" + home + ", mobile=" + mobile + ", work=" + work + ", email=" + email + ", enabled=" + enabled + ", roles=" + roles +", username=" + username +"]";
    }
    
    public static PersonDto mapFromPersonEntity(Person person) {
        return new PersonDto(person)
    }
    
    public static List<PersonDto> mapFromPeopleEntities(List<Person> people) {
        return people.collect { person ->  new PersonDto(person) };
    }
    
    public static Person mapToPersonEntity(PersonDto personDto, Title title, Gender gender, Address address) {
        return new Person(personDto.id, title, personDto.firstName, personDto.preferredName, personDto.surname, personDto.legalSurname, personDto.middleNames, personDto.previousSurname, personDto.dob, gender, address, personDto.home, personDto.mobile, personDto.work, personDto.email, personDto.enabled, personDto.roles, personDto.username)
    }
}
