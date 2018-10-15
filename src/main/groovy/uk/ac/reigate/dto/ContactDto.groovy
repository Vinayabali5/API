package uk.ac.reigate.dto

import groovy.transform.EqualsAndHashCode

import com.fasterxml.jackson.annotation.JsonProperty
import com.fasterxml.jackson.databind.annotation.JsonSerialize

import uk.ac.reigate.domain.Contact
import uk.ac.reigate.domain.Person;
import uk.ac.reigate.domain.lookup.ContactType
/**
 *
 * JSON serializable DTO containing Contact data
 *
 */
@JsonSerialize
@EqualsAndHashCode(includeFields=true)
public class ContactDto implements Serializable{
    
    @JsonProperty
    private Integer id;
    
    @JsonProperty
    private Integer personId;
    
    @JsonProperty
    private Integer contactId;
    
    @JsonProperty
    private Integer contactTypeId;
    
    @JsonProperty
    private String _contactTypeDescription
    
    @JsonProperty
    private Boolean contactable;
    
    @JsonProperty
    private Boolean preferred;
    
    @JsonProperty
    private PersonDto contact;
    
    /**
     * Default No Args constructor
     */
    public ContactDto() {
    }
    
    /**
     * Constructor to create a ContactDto object from a Contact object
     *
     * @param contact the Contact object to use for construction
     */
    ContactDto(Contact contact) {
        this.id = contact.id;
        this.personId = contact.person != null ? contact.person.id : null;
        this.contactId = contact.contact != null ? contact.contact.id : null;
        this.contactTypeId = contact.contactType != null ? contact.contactType.id : null;
        this.contactable = contact.contactable;
        this.preferred = contact.preferred;
        this.contact = new PersonDto(contact.contact)
        this._contactTypeDescription = contact.contactType != null ? contact.contactType.description : ''
        this.contact._titleDescription = contact.contact.title != null ? contact.contact.title.description : ''
        this.contact._genderCode = contact.contact.gender != null ? contact.contact.gender.code : ''
        this.contact.address = contact.contact.address != null ? AddressDto.mapFromAddressEntity(contact.contact.address) : null
    }
    
    @Override
    public String toString() {
        return "ContactDto [id=" + id + ", person=" + personId + ", contact=" + contactId + ", contactType=" + contactTypeId + ", contactable=" + contactable + ", preferred=" + preferred + "]";
    }
    
    public static ContactDto mapFromContactEntity(Contact contact) {
        return  new ContactDto(contact)
    }
    
    public static List<ContactDto> mapFromContactsEntities(List<Contact> contacts) {
        return contacts.collect { contact ->
            return  new ContactDto(contact)
        };
    }
    
    public static Contact mapToContactEntity(ContactDto contactDto, Person person, Person contact, ContactType contactType) {
        return new Contact(contactDto.id, person, contact, contactType, contactDto.contactable, contactDto.preferred)
    }
}
