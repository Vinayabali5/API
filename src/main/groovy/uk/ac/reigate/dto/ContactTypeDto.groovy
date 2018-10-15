package uk.ac.reigate.dto;


import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;

import groovy.transform.EqualsAndHashCode;
import uk.ac.reigate.domain.lookup.ContactType

/**
 *
 * JSON serializable DTO containing ContactType data
 *
 */
@JsonSerialize
@EqualsAndHashCode(includeFields=true)
public class ContactTypeDto implements Serializable {
    
    @JsonProperty
    private Integer id;
    
    @JsonProperty
    private String name;
    
    @JsonProperty
    private String description;
    
    /**
     * Default No Args constructor
     */
    public ContactTypeDto() {
    }
    
    /**
     * Constructor to create a ContactTypeDto object
     *
     * @param id the Id for the ContactType
     * @param code the code for the ContactType
     * @param description the description for the ContactType
     */
    public ContactTypeDto(Integer id, String name, String description) {
        this.id = id;
        this.name = name;
        this.description = description;
    }
    
    /**
     * Constructor to create a ContactTypeDto object from a ContactType object
     *
     * @param contactType the ContactType object to use for construction
     */
    ContactTypeDto(ContactType contactType) {
        this.id = contactType.id;
        this.name = contactType.name;
        this.description = contactType.description;
    }
    
    @Override
    public String toString() {
        return "ContactTypeDto [id=" + id + ", name=" + name + ", description=" + description + "]";
    }
    
    public static ContactTypeDto mapFromContactTypeEntity(ContactType contactType) {
        return new ContactTypeDto(contactType);
    }
    
    public static List<ContactTypeDto> mapFromContactTypesEntities(List<ContactType> contactTypes) {
        List<ContactTypeDto> output = contactTypes.collect { contactType ->  new ContactTypeDto(contactType) };
        return output
    }
    
    public static ContactType mapToContactTypeEntity(ContactTypeDto contactTypeDto) {
        return new ContactType(contactTypeDto.id, contactTypeDto.name, contactTypeDto.description)
    }
}
