package uk.ac.reigate.dto;

import static org.junit.Assert.*
import org.junit.Before
import org.junit.Test
import uk.ac.reigate.domain.academic.EntryQualificationType



public class EntryQualificationTypeDtoTest {
    
    private EntryQualificationType entryQualificationType1
    
    private EntryQualificationType entryQualificationType2
    
    private List<EntryQualificationType> entryQualificationTypes
    
    @Before
    public void setup() {
        this.entryQualificationType1 = new EntryQualificationType(
                id: 1,
                code:'M',
                description:'M',
                size: 199.33F
                );
        this.entryQualificationType2 = new EntryQualificationType(
                id: 2,
                code:'F',
                description:'F',
                size: 198.33F
                );
        this.entryQualificationTypes = Arrays.asList(this.entryQualificationType1, this.entryQualificationType2);
    }
    
    @Test
    public void testMapFromEntryQualificationTypeEntity(){
        EntryQualificationTypeDto entryQualificationTypeTest = EntryQualificationTypeDto.mapFromEntryQualificationTypeEntity( entryQualificationType1 )
        assertEquals( entryQualificationTypeTest.id, entryQualificationType1.id );
        assertEquals( entryQualificationTypeTest.code, entryQualificationType1.code);
        assertEquals( entryQualificationTypeTest.description, entryQualificationType1.description);
    }
    
    @Test
    public void testMapFromEntryQualificationTypesEntities(){
        List<EntryQualificationTypeDto> entryQualificationTypesDtoTest = EntryQualificationTypeDto.mapFromEntryQualificationTypesEntities( this.entryQualificationTypes )
        assertEquals( entryQualificationTypesDtoTest[0].id, entryQualificationType1.id );
        assertEquals( entryQualificationTypesDtoTest[0].code, entryQualificationType1.code);
        assertEquals( entryQualificationTypesDtoTest[0].description, entryQualificationType1.description);
        assertEquals( entryQualificationTypesDtoTest[1].id, entryQualificationType2.id );
        assertEquals( entryQualificationTypesDtoTest[1].code, entryQualificationType2.code);
        assertEquals( entryQualificationTypesDtoTest[1].description, entryQualificationType2.description);
    }
    
    @Test
    public void testMapToEntryQualificationTypeEntity(){
        EntryQualificationTypeDto entryQualificationTypeDto = new EntryQualificationTypeDto(entryQualificationType1.id, entryQualificationType1.code, entryQualificationType1.description, entryQualificationType1.size);
        EntryQualificationType entryQualificationType = EntryQualificationTypeDto.mapToEntryQualificationTypeEntity( entryQualificationTypeDto );
        assertEquals( entryQualificationType.id, entryQualificationType1.id );
        assertEquals( entryQualificationType.code, entryQualificationType1.code);
        assertEquals( entryQualificationType.description, entryQualificationType1.description);
    }
    
    @Test
    public void testEquals_Same() {
        EntryQualificationTypeDto entryQualificationTypeDto1 = new EntryQualificationTypeDto(entryQualificationType1.id, entryQualificationType1.code, entryQualificationType1.description, entryQualificationType1.size)
        EntryQualificationTypeDto entryQualificationTypeDto2 = new EntryQualificationTypeDto(entryQualificationType1.id, entryQualificationType1.code, entryQualificationType1.description, entryQualificationType1.size)
        assertEquals(entryQualificationTypeDto1, entryQualificationTypeDto2)
    }
    
    @Test
    public void testEquals_Different() {
        EntryQualificationTypeDto entryQualificationTypeDto1 = new EntryQualificationTypeDto(entryQualificationType1.id, entryQualificationType1.code, entryQualificationType1.description, entryQualificationType1.size)
        EntryQualificationTypeDto entryQualificationTypeDto2 = new EntryQualificationTypeDto(entryQualificationType2.id, entryQualificationType2.code, entryQualificationType2.description, entryQualificationType2.size)
        assertNotEquals(entryQualificationTypeDto1, entryQualificationTypeDto2)
    }
    
    @Test
    public void testHashCode_Same() {
        EntryQualificationTypeDto entryQualificationTypeDto1 = new EntryQualificationTypeDto(entryQualificationType1.id, entryQualificationType1.code, entryQualificationType1.description, entryQualificationType1.size)
        EntryQualificationTypeDto entryQualificationTypeDto2 = new EntryQualificationTypeDto(entryQualificationType1.id, entryQualificationType1.code, entryQualificationType1.description, entryQualificationType1.size)
        assertEquals(entryQualificationTypeDto1.hashCode(), entryQualificationTypeDto2.hashCode())
    }
    
    @Test
    public void testHashCode_Different() {
        EntryQualificationTypeDto entryQualificationTypeDto1 = new EntryQualificationTypeDto(entryQualificationType1.id, entryQualificationType1.code, entryQualificationType1.description, entryQualificationType1.size)
        EntryQualificationTypeDto entryQualificationTypeDto2 = new EntryQualificationTypeDto(entryQualificationType2.id, entryQualificationType2.code, entryQualificationType2.description, entryQualificationType2.size)
        assertNotEquals(entryQualificationTypeDto1.hashCode(), entryQualificationTypeDto2.hashCode())
    }
    
    @Test
    public void testConstructor_EntryQualificationType() {
        EntryQualificationTypeDto entryQualificationTypeDto = new EntryQualificationTypeDto(entryQualificationType1)
        assertEquals( entryQualificationTypeDto.code, entryQualificationType1.code )
        assertEquals( entryQualificationTypeDto.description, entryQualificationType1.description )
    }
}
