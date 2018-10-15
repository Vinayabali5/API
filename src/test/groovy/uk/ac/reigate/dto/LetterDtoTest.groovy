package uk.ac.reigate.dto;

import org.junit.Before
import org.junit.Test

import uk.ac.reigate.domain.academic.Student;
import uk.ac.reigate.domain.ilp.Letter
import uk.ac.reigate.domain.ilp.LetterType;

import static org.junit.Assert.*



public class LetterDtoTest {
    
    private Letter letter1
    
    private Letter letter2
    
    private List<Letter> letters
    
    Student createStudent() {
        Student student = new Student()
        student.id = 1
        return student
    }
    
    LetterType createLetterType() {
        LetterType letterType = new LetterType()
    }
    
    @Before
    public void setup() {
        letter1 = new Letter(
                id: 1,
                student: createStudent(),
                requestedDate: new Date().parse('yyyy/MM/dd', '2015/09/01'),
                letterDate: new Date().parse('yyyy/MM/dd', '2015/09/01'),
                subject : 'maths',
                re : 'Mr',
                reviewMeeting : true,
                attendance : true,
                behaviour : true,
                homework : true,
                punctuality : false,
                focus : true,
                deadlines : true,
                incompleteCoursework : true,
                nonEntryWarning : 2,
                letterType : createLetterType()
                );
        letter2 = new Letter(
                id: 2,
                student: createStudent(),
                requestedDate: new Date().parse('yyyy/MM/dd', '2015/09/01'),
                letterDate: new Date().parse('yyyy/MM/dd', '2015/09/01'),
                subject : 'maths',
                re : 'Mr',
                reviewMeeting : true,
                attendance : true,
                behaviour : true,
                homework : true,
                punctuality : false,
                focus : true,
                deadlines : true,
                incompleteCoursework : true,
                nonEntryWarning : 2,
                letterType : createLetterType()
                );
        letters = Arrays.asList(letter1, letter2);
    }
    
    @Test
    public void testMapFromLetterEntity(){
        LetterDto letterTest = LetterDto.mapFromLetterEntity( letter1 )
        assertEquals( letterTest.id, letter1.id );
        assertEquals( letterTest.requestedDate, letter1.requestedDate);
        assertEquals( letterTest.letterDate, letter1.letterDate);
    }
    
    @Test
    public void testMapFromLettersEntities(){
        List<LetterDto> lettersDtoTest = LetterDto.mapFromLettersEntities( letters )
        assertEquals( lettersDtoTest[0].id, letter1.id );
        assertEquals( lettersDtoTest[0].requestedDate, letter1.requestedDate);
        assertEquals( lettersDtoTest[0].letterDate, letter1.letterDate);
        assertEquals( lettersDtoTest[1].id, letter2.id );
        assertEquals( lettersDtoTest[1].requestedDate, letter2.requestedDate);
        assertEquals( lettersDtoTest[1].letterDate, letter2.letterDate);
    }
    
    @Test
    public void testEquals_Same() {
        LetterDto letterDto1 = new LetterDto(letter1)
        LetterDto letterDto2 = new LetterDto(letter1)
        assertEquals(letterDto1, letterDto2)
    }
    
    @Test
    public void testEquals_Different() {
        LetterDto letterDto1 = new LetterDto(letter1)
        LetterDto letterDto2 = new LetterDto(letter2)
        assertNotEquals(letterDto1, letterDto2)
    }
    
    @Test
    public void testHashCode_Same() {
        LetterDto letterDto1 = new LetterDto(letter1)
        LetterDto letterDto2 = new LetterDto(letter1)
        assertEquals(letterDto1.hashCode(), letterDto2.hashCode())
    }
    
    @Test
    public void testHashCode_Different() {
        LetterDto letterDto1 = new LetterDto(letter1)
        LetterDto letterDto2 = new LetterDto(letter2)
        assertNotEquals(letterDto1.hashCode(), letterDto2.hashCode())
    }
    
    @Test
    public void testConstructor_Letter() {
        LetterDto letterDto = new LetterDto(letter1)
        assertEquals( letterDto.requestedDate, letter1.requestedDate )
        assertEquals( letterDto.letterDate, letter1.letterDate )
    }
}
