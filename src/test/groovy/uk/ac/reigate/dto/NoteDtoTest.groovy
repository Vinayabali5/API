package uk.ac.reigate.dto;

import org.junit.Before
import org.junit.Test

import uk.ac.reigate.domain.Address
import uk.ac.reigate.domain.Note
import uk.ac.reigate.domain.NoteType
import uk.ac.reigate.domain.Person
import uk.ac.reigate.domain.lookup.Gender
import uk.ac.reigate.domain.lookup.Title

import static org.junit.Assert.*



public class NoteDtoTest {
    
    private Title mrs
    
    private Gender female
    
    private Address address1
    
    private Person person
    
    private NoteType type
    
    private Note note1
    
    private Note note2
    
    private List<Note> notes
    
    @Before
    public void setupTests() {
        this.mrs = new Title(1, '2', 'Mrs')
        this.female = new Gender(1, 'F', 'Female')
        this.address1 = new Address(1, 'Flat D', 'Stag', 'Stanley', 'west', 'park', 'Wallington', '', 'E161FF', '', '', '', '')
        this.person =  new Person(1, mrs, 'Vinaya', 'Vin', 'Vin', 'Bali', 'Mick', 'Uday', null, female, address1, '07809817665', '0890788889', '08937737737', 'mbvinayabali@gmail.com', true, null, 'vbm')
        this.type = new NoteType(1, 'GEN', 'General Notes')
        this.note1 = new Note(
                id: 1,
                person: person,
                note:'A',
                type: type
                );
        this.note2 = new Note(
                id: 2,
                person: person,
                note:'A',
                type: type
                );
        this.notes = Arrays.asList(note1, note2);
    }
    
    NoteDto generateNoteDto() {
        return generateNote1Dto()
    }
    
    NoteDto generateNote1Dto() {
        return new NoteDto(note1)
    }
    
    NoteDto generateNote2Dto() {
        return new NoteDto(note2)
    }
    
    @Test
    public void testMapFromNoteEntityTest(){
        NoteDto noteTest = NoteDto.mapFromEntity( note1 )
        assertEquals( noteTest.id, note1.id );
        assertEquals( noteTest.personId, note1.person.id );
        assertEquals( noteTest.note, note1.note);
        assertEquals( noteTest.typeId, note1.type.id);
    }
    
    @Test
    public void testMapFromNotesEntitiesTest(){
        List<NoteDto> noteTest = NoteDto.mapFromList( notes )
        assertEquals( noteTest[0].id, note1.id );
        assertEquals( noteTest[0].personId, note1.person.id);
        assertEquals( noteTest[0].note, note1.note);
        assertEquals( noteTest[0].typeId, note1.type.id);
        assertEquals( noteTest[1].id, note2.id );
        assertEquals( noteTest[1].personId, note2.person.id );
        assertEquals( noteTest[1].note, note2.note);
        assertEquals( noteTest[1].typeId, note2.type.id);
    }
    
    @Test
    public void testMapToNoteEntityTest(){
        NoteDto noteDto = generateNoteDto()
        Note noteTest = NoteDto.mapToNoteEntity( noteDto, person, type );
        assertEquals( noteTest.id, note1.id );
        assertEquals( noteTest.person, note1.person);
        assertEquals( noteTest.note, note1.note);
        assertEquals( noteTest.type, note1.type);
    }
    
    @Test
    public void testEquals_Same() {
        NoteDto noteDto1 = new NoteDto(note1)
        NoteDto noteDto2 = new NoteDto(note1)
        assertEquals(noteDto1, noteDto2)
    }
    
    @Test
    public void testEquals_Different() {
        NoteDto noteDto1 = new NoteDto(note1)
        NoteDto noteDto2 = new NoteDto(note2)
        assertNotEquals(noteDto1, noteDto2)
    }
    
    @Test
    public void testHashCode_Same() {
        NoteDto noteDto1 = new NoteDto(note1)
        NoteDto noteDto2 = new NoteDto(note1)
        assertEquals(noteDto1.hashCode(), noteDto2.hashCode())
    }
    
    @Test
    public void testHashCode_Different() {
        NoteDto noteDto1 = new NoteDto(note1)
        NoteDto noteDto2 = new NoteDto(note2)
        assertNotEquals(noteDto1.hashCode(), noteDto2.hashCode())
    }
}
