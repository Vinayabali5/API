package uk.ac.reigate.dto;


import groovy.transform.EqualsAndHashCode

import com.fasterxml.jackson.annotation.JsonProperty
import com.fasterxml.jackson.databind.annotation.JsonSerialize

import uk.ac.reigate.domain.Person

/**
 * This class is a DTO for display a summary of a person object.  
 */
@JsonSerialize
@EqualsAndHashCode(includeFields=true)
public class PersonSummaryDto implements Serializable {
    
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
    
    /**
     * Default No Args constructor
     */
    PersonSummaryDto() {
    }
    
    /**
     * Constructor to create a PersonDto object from a Person object
     *
     * @param person the Person object to use for construction
     */
    PersonSummaryDto(Person person){
        this.id = person.id;
        this.titleId = person.title != null ? person.title.id : null;
        this.firstName = person.firstName;
        this.surname = person.surname;
        this.legalSurname = person.legalSurname;
        this.preferredName = person.preferredName;
        this.middleNames = person.middleNames;
        this.previousSurname = person.previousSurname;
        this._titleDescription = person.title != null ? person.title.description : ''
    }
    
    @Override
    public String toString() {
        return "PersonSummaryDto [id=" + id + ", titleId=" + titleId + ", _titleDescription=" + _titleDescription + ", firstName=" + firstName + ", preferredName=" + preferredName + ", surname=" + surname + ", legalSurname=" + legalSurname + ", middleNames=" + middleNames + ", previousSurname=" + previousSurname + ", address=" + address + "]";
    }
    
    public static PersonSummaryDto mapFromEntity(Person person) {
        return new PersonSummaryDto(person)
    }
    
    public static List<PersonSummaryDto> mapFromList(List<Person> people) {
        return people.collect { person ->  new PersonSummaryDto(person) };
    }
}
