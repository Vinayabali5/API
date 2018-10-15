package uk.ac.reigate.dto;

import static org.junit.Assert.*

import org.junit.Before
import org.junit.Test

import uk.ac.reigate.domain.academic.EntryQualification
import uk.ac.reigate.domain.academic.EntryQualificationType
import uk.ac.reigate.domain.academic.Student;

public class EntryQualificationDtoTest {
    
    private EntryQualification entryQualification1
    
    private EntryQualification entryQualification2
    
    private List<EntryQualification> entryQualifications
    
    EntryQualificationType createEntryQualificationType() {
        EntryQualificationType type = new EntryQualificationType()
        type.id = 1
        return type
    }
    
    @Before
    public void setup() {
        this.entryQualification1 = new EntryQualification(
                id: 1,
                title: 'MM',
                type: createEntryQualificationType(),
                basicList: true,
                shortCourse: true,
                dataMatchCode: 'M',
                webLinkCode:2
                );
        this.entryQualification2 = new EntryQualification(
                id: 2,
                title: 'SS',
                type: createEntryQualificationType(),
                basicList: true,
                shortCourse: true,
                dataMatchCode: 'S',
                webLinkCode:1
                );
        this.entryQualifications = Arrays.asList(this.entryQualification1, this.entryQualification2);
    }
    
    
    @Test
    public void testMapFromEntryQualificationEntity() {
        EntryQualificationDto entryQualificationTest = EntryQualificationDto.mapFromEntryQualificationEntity( entryQualification1 );
        assertEquals( entryQualificationTest.id, entryQualification1.id);
        assertEquals( entryQualificationTest.title, entryQualification1.title);
        assertEquals( entryQualificationTest.basicList, entryQualification1.basicList);
        assertEquals( entryQualificationTest.shortCourse, entryQualification1.shortCourse);
        assertEquals( entryQualificationTest.dataMatchCode, entryQualification1.dataMatchCode);
    }
    
    @Test
    public void testMapFromEntryQualificationsEntities(){
        List<EntryQualificationDto> entryQualificationTest = EntryQualificationDto.mapFromEntryQualificationsEntities( this.entryQualifications )
        assertEquals( entryQualificationTest [0].id, entryQualification1.id);
        assertEquals( entryQualificationTest [0].title, entryQualification1.title);
        assertEquals( entryQualificationTest [0].basicList, entryQualification1.basicList);
        assertEquals( entryQualificationTest [0].shortCourse, entryQualification1.shortCourse);
        assertEquals( entryQualificationTest [0].dataMatchCode, entryQualification1.dataMatchCode);
        assertEquals( entryQualificationTest [1].id, entryQualification2.id );
        assertEquals( entryQualificationTest [1].title, entryQualification2.title );
        assertEquals( entryQualificationTest [1].basicList, entryQualification2.basicList );
        assertEquals( entryQualificationTest [1].shortCourse, entryQualification2.shortCourse);
        assertEquals( entryQualificationTest [1].dataMatchCode, entryQualification2.dataMatchCode);
    }
    
    @Test
    public void testMapToEntryQualificationEntity(){
        EntryQualificationDto entryQualificationDto = new EntryQualificationDto( entryQualification1.id, entryQualification1.title, entryQualification1.type, entryQualification1.basicList, entryQualification1.shortCourse, entryQualification1.dataMatchCode, entryQualification1.webLinkCode
                )
        EntryQualificationType type = new EntryQualificationType()
        EntryQualification entryQualification = EntryQualificationDto.mapToEntryQualificationEntity( entryQualificationDto, type )
        assertEquals( entryQualification.id, entryQualification1.id );
        assertEquals( entryQualification.title, entryQualification1.title );
        assertEquals( entryQualification.basicList, entryQualification1.basicList );
        assertEquals( entryQualification.shortCourse, entryQualification1.shortCourse );
        assertEquals( entryQualification.dataMatchCode, entryQualification1.dataMatchCode );
    }
    
    @Test
    public void testEquals_Same() {
        EntryQualificationDto entryQualificationDto1 = new EntryQualificationDto( entryQualification1.id, entryQualification1.title, entryQualification1.type, entryQualification1.basicList, entryQualification1.shortCourse, entryQualification1.dataMatchCode, entryQualification1.webLinkCode)
        EntryQualificationDto entryQualificationDto2 = new EntryQualificationDto( entryQualification1.id, entryQualification1.title, entryQualification1.type, entryQualification1.basicList, entryQualification1.shortCourse, entryQualification1.dataMatchCode, entryQualification1.webLinkCode)
        assertEquals( entryQualificationDto1, entryQualificationDto2)
    }
    
    @Test
    public void testEquals_Different() {
        EntryQualificationDto entryQualificationDto1 = new EntryQualificationDto( entryQualification1.id, entryQualification1.title, entryQualification1.type, entryQualification1.basicList, entryQualification1.shortCourse, entryQualification1.dataMatchCode, entryQualification1.webLinkCode)
        EntryQualificationDto entryQualificationDto2 = new EntryQualificationDto( entryQualification2.id, entryQualification2.title, entryQualification2.type, entryQualification2.basicList, entryQualification2.shortCourse, entryQualification2.dataMatchCode, entryQualification2.webLinkCode)
        assertNotEquals( entryQualificationDto1, entryQualificationDto2)
    }
    
    @Test
    public void testHashCode_Same() {
        EntryQualificationDto entryQualificationDto1 = new EntryQualificationDto( entryQualification1.id, entryQualification1.title, entryQualification1.type, entryQualification1.basicList, entryQualification1.shortCourse, entryQualification1.dataMatchCode, entryQualification1.webLinkCode)
        EntryQualificationDto entryQualificationDto2 = new EntryQualificationDto( entryQualification1.id, entryQualification1.title, entryQualification1.type, entryQualification1.basicList, entryQualification1.shortCourse, entryQualification1.dataMatchCode, entryQualification1.webLinkCode)
        assertEquals( entryQualificationDto1.hashCode(), entryQualificationDto2.hashCode())
    }
    
    @Test
    public void testHashCode_Different() {
        EntryQualificationDto entryQualificationDto1 = new EntryQualificationDto( entryQualification1.id, entryQualification1.title, entryQualification1.type, entryQualification1.basicList, entryQualification1.shortCourse, entryQualification1.dataMatchCode, entryQualification1.webLinkCode)
        EntryQualificationDto entryQualificationDto2 = new EntryQualificationDto( entryQualification2.id, entryQualification2.title, entryQualification2.type, entryQualification2.basicList, entryQualification2.shortCourse, entryQualification2.dataMatchCode, entryQualification2.webLinkCode)
        assertNotEquals( entryQualificationDto1.hashCode(), entryQualificationDto2.hashCode())
    }
    
    @Test
    public void testConstructor_EntryQualification() {
        EntryQualificationDto entryQualification = new EntryQualificationDto( entryQualification1.id, entryQualification1.title, entryQualification1.type, entryQualification1.basicList, entryQualification1.shortCourse, entryQualification1.dataMatchCode, entryQualification1.webLinkCode)
        assertEquals( entryQualification.id, entryQualification1.id );
        assertEquals( entryQualification.title, entryQualification1.title );
        assertEquals( entryQualification.basicList, entryQualification1.basicList );
        assertEquals( entryQualification.shortCourse, entryQualification1.shortCourse );
        assertEquals( entryQualification.dataMatchCode, entryQualification1.dataMatchCode );
    }
}
