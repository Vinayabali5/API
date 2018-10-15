package uk.ac.reigate.dto;

import static org.junit.Assert.*

import org.junit.Before;
import org.junit.Test

import uk.ac.reigate.domain.lookup.ContactType



public class ContactTypeDtoTest {
    
    private ContactType contactType1
    
    private ContactType contactType2
    
    private List<ContactType> contactTypes
    
    @Before
    public void setup() {
        this.contactType1 = new ContactType(
                id: 1,
                name:'M',
                description:'Mother'
                );
        this.contactType2 = new ContactType(
                id: 2,
                name:'F',
                description:'Father'
                );
        this.contactTypes = Arrays.asList(this.contactType1, this.contactType2);
    }
    
    @Test
    void testConstructor_contactType() {
        ContactTypeDto contactTypeTest = new ContactTypeDto( contactType1 )
        assertEquals( contactTypeTest.id, contactType1.id );
        assertEquals( contactTypeTest.name, contactType1.name);
        assertEquals( contactTypeTest.description, contactType1.description);
    }
    
    @Test
    public void testMapFromContactTypeEntity(){
        ContactTypeDto contactTypeTest = ContactTypeDto.mapFromContactTypeEntity( contactType1 )
        assertEquals( contactTypeTest.id, contactType1.id );
        assertEquals( contactTypeTest.name, contactType1.name);
        assertEquals( contactTypeTest.description, contactType1.description);
    }
    
    @Test
    public void testMapFromContactTypesEntities(){
        List<ContactTypeDto> contactTypeDtoTest = ContactTypeDto.mapFromContactTypesEntities( this.contactTypes )
        assertEquals( contactTypeDtoTest[0].id, contactType1.id );
        assertEquals( contactTypeDtoTest[0].name, contactType1.name);
        assertEquals( contactTypeDtoTest[0].description, contactType1.description);
        assertEquals( contactTypeDtoTest[1].id, contactType2.id);
        assertEquals( contactTypeDtoTest[1].name, contactType2.name );
        assertEquals( contactTypeDtoTest[1].description, contactType2.description);
    }
    
    @Test
    public void testMapToContactTypeEntity(){
        ContactTypeDto contactTypeDto = new ContactTypeDto(contactType1.id, contactType1.name, contactType1.description);
        ContactType contactType = ContactTypeDto.mapToContactTypeEntity( contactTypeDto );
        assertEquals( contactType.id, contactType1.id );
        assertEquals( contactType.name, contactType1.name);
        assertEquals( contactType.description, contactType1.description);
    }
    
    @Test
    public void testEquals_Same() {
        ContactTypeDto contactTypeDto1 = new ContactTypeDto(contactType1.id, contactType1.name, contactType1.description)
        ContactTypeDto contactTypeDto2 = new ContactTypeDto(contactType1.id, contactType1.name, contactType1.description)
        assertEquals(contactTypeDto1, contactTypeDto2)
    }
    
    @Test
    public void testEquals_Different() {
        ContactTypeDto contactTypeDto1 = new ContactTypeDto(contactType1.id, contactType1.name, contactType1.description)
        ContactTypeDto contactTypeDto2 = new ContactTypeDto(contactType2.id, contactType2.name, contactType2.description)
        assertNotEquals(contactTypeDto1, contactTypeDto2)
    }
    
    @Test
    public void testHashCode_Same() {
        ContactTypeDto contactTypeDto1 = new ContactTypeDto(contactType1.id, contactType1.name, contactType1.description)
        ContactTypeDto contactTypeDto2 = new ContactTypeDto(contactType1.id, contactType1.name, contactType1.description)
        assertEquals(contactTypeDto1.hashCode(), contactTypeDto2.hashCode())
    }
    
    @Test
    public void testHashCode_Different() {
        ContactTypeDto contactTypeDto1 = new ContactTypeDto(contactType1.id, contactType1.name, contactType1.description)
        ContactTypeDto contactTypeDto2 = new ContactTypeDto(contactType2.id, contactType2.name, contactType2.description)
        assertNotEquals(contactTypeDto1.hashCode(), contactTypeDto2.hashCode())
    }
    
    @Test
    public void testConstructor_ContactType() {
        ContactTypeDto contactTypeDto = new ContactTypeDto(contactType1)
        assertEquals( contactTypeDto.name, contactType1.name )
        assertEquals( contactTypeDto.description, contactType1.description )
    }
}
