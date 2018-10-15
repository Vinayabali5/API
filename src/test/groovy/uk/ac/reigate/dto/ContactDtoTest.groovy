package uk.ac.reigate.dto;

import org.junit.Before
import org.junit.Test

import uk.ac.reigate.domain.Address
import uk.ac.reigate.domain.Contact
import uk.ac.reigate.domain.Person
import uk.ac.reigate.domain.lookup.ContactType
import uk.ac.reigate.domain.lookup.Gender
import uk.ac.reigate.domain.lookup.Title

import static org.junit.Assert.*



public class ContactDtoTest {
    
    private Title mr
    
    private Title mrs
    
    private Gender male
    
    private Gender female
    
    private Address address
    
    private Person person
    
    private Person contact
    
    private ContactType father
    
    private ContactType mother
    
    private Contact contact1
    
    private Contact contact2
    
    private List<Contact> contacts
    
    @Before
    public void setupTests() {
        
        this.mrs = new Title(2, '2', 'Mrs')
        this.female =  new Gender(2, 'F', 'Female')
        this.address = new Address(1, 'Flat D', 'Stag', 'Stanley', 'west', 'park', 'Wallington', '', 'E161FF', '', '', '', '')
        this.person =  new Person(1, mrs,'Vinaya', 'Vin', 'Vin', 'Bali', 'Mick', 'Uday', null, female, address, '07809817665', '0890788889', '08937737737', 'mbvinayabali@gmail.com', true, null, 'vbm')
        this.contact =  new Person(2, mrs, 'Vinaya', 'Vin', 'Vin', 'Bali', 'Mick', 'Uday', null, female, address, '07809817665', '0890788889', '08937737737', 'mbvinayabali@gmail.com', true, null, 'vbm')
        this.mother = new ContactType(1, 'M', 'Mother')
        this.father = new ContactType(2, 'F', 'Father')
        this.contact1 = new Contact(
                id: 1,
                person: person,
                contact: contact,
                contactType : mother,
                contactable : true,
                preferred : true
                );
        this.contact2 = new Contact(
                id: 2,
                person: person,
                contact: contact,
                contactType : father,
                contactable : true,
                preferred : true
                );
        this.contacts = Arrays.asList(contact1, contact2);
    }
    
    ContactDto generateContactDto() {
        return generateContact1Dto()
    }
    
    ContactDto generateContact1Dto() {
        return new ContactDto(contact1)
    }
    
    ContactDto generateContact2Dto() {
        return new ContactDto(contact2)
    }
    
    //    @Test
    //    void testConstructor_contact() {
    //        ContactDto contactTest = new ContactDto( contact1 );
    //        assertEquals("failed: ids not equal", contactTest.id, contact1.id);
    //        assertEquals("failed: persons not equal", contactTest.personId, contact1.person.id);
    //        assertEquals("failed: contacts not equal", contactTest.contactId, contact1.contact.id);
    //        assertEquals("failed: contactTypes not equal", contactTest.contactTypeId, contact1.contactType.id)
    //        assertEquals("failed: contactable not equal", contactTest.contactable, contact1.contactable);
    //        assertEquals("failed: preferred not equal", contactTest.preferred, contact1.preferred);
    //    }
    
    //    @Test
    //    public void testMapFromContactEntity() {
    //        ContactDto contactTest = ContactDto.mapFromContactEntity( contact1 );
    //        assertEquals("failed: ids not equal", contactTest.id, contact1.id);
    //        assertEquals("failed: persons not equal", contactTest.personId, contact1.person.id);
    //        assertEquals("failed: contacts not equal", contactTest.contactId, contact1.contact.id);
    //        assertEquals("failed: contactTypes not equal", contactTest.contactTypeId, contact1.contactType.id)
    //        assertEquals("failed: contactable not equal", contactTest.contactable, contact1.contactable);
    //        assertEquals("failed: preferred not equal", contactTest.preferred, contact1.preferred);
    //    }
    
    
    //    @Test
    //    public void testMapFromContactsEntities(){
    //        List<ContactDto> contactTest = ContactDto.mapFromContactsEntities( contacts )
    //        assertEquals( contactTest[0].id, contact1.id );
    //        assertEquals( contactTest[0].personId, contact1.person.id);
    //        assertEquals( contactTest[0].contactId, contact1.contact.id);
    //        assertEquals( contactTest[0].contactTypeId, contact1.contactType.id);
    //        assertEquals( contactTest[0].contactable, contact1.contactable );
    //        assertEquals( contactTest[0].preferred, contact1.preferred );
    //        assertEquals( contactTest[1].id, contact2.id );
    //        assertEquals( contactTest[1].personId, contact2.person.id);
    //        assertEquals( contactTest[1].contactId, contact2.contact.id);
    //        assertEquals( contactTest[1].contactTypeId, contact2.contactType.id);
    //        assertEquals( contactTest[1].contactable, contact2.contactable );
    //        assertEquals( contactTest[1].preferred, contact2.preferred );
    //    }
    
    @Test
    public void testMapToContactEntity(){
        ContactDto contactDto = generateContactDto()
        Contact contactTest = contactDto.mapToContactEntity( contactDto, person, contact, mother );
        assertEquals( contactTest.id, contact1.id );
        assertEquals( "failed: person not equal", contactTest.person, contact1.person );
        assertEquals( "failed: contact not equal", contactTest.contact, contact1.contact );
        assertEquals( "failed: contactType not equal", contactTest.contactType, contact1.contactType);
        assertEquals( contactTest.contactable, contact1.contactable );
        assertEquals( contactTest.preferred, contact1.preferred );
    }
    
    @Test
    public void testEquals_Same() {
        ContactDto contactDto1 = new ContactDto(contact1)
        ContactDto contactDto2 = new ContactDto(contact1)
        assertEquals(contactDto1, contactDto2)
    }
    
    @Test
    public void testEquals_Different() {
        ContactDto contactDto1 = new ContactDto(contact1)
        ContactDto contactDto2 = new ContactDto(contact2)
        assertNotEquals(contactDto1, contactDto2)
    }
    
    @Test
    public void testHashCode_Same() {
        ContactDto contactDto1 = new ContactDto(contact1)
        ContactDto contactDto2 = new ContactDto(contact1)
        assertEquals(contactDto1.hashCode(), contactDto2.hashCode())
    }
    
    @Test
    public void testHashCode_Different() {
        ContactDto contactDto1 = new ContactDto(contact1)
        ContactDto contactDto2 = new ContactDto(contact2)
        assertNotEquals(contactDto1.hashCode(), contactDto2.hashCode())
    }
    
    //    @Test
    //    public void testConstructor_Contact() {
    //        ContactDto contact = new ContactDto(contact1)
    //        assertEquals( contact.id, contact1.id );
    //        assertEquals( contact.personId, contact1.person.id);
    //        assertEquals( contact.contactId, contact1.contact.id);
    //        assertEquals( contact.contactTypeId, contact1.contactType.id);
    //        assertEquals( contact.contactable, contact1.contactable );
    //        assertEquals( contact.preferred, contact1.preferred );
    //    }
}
