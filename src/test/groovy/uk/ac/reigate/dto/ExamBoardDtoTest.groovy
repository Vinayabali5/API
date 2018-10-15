package uk.ac.reigate.dto;

import static org.junit.Assert.*

import java.util.List;

import org.junit.Before
import org.junit.Test

import uk.ac.reigate.domain.exam.ExamBoard
import uk.ac.reigate.domain.lookup.Ethnicity
import uk.ac.reigate.dto.exam.ExamBoardDto;;



public class ExamBoardDtoTest {
    
    private ExamBoard examBoard1
    
    private ExamBoard examBoard2
    
    private List<ExamBoard> examBoards
    
    @Before
    public void setup() {
        examBoard1 = new ExamBoard(
                id: 1,
                name: 'Excel',
                description: 'ExcelBtec',
                boardCode: '111',
                boardCentreNumber: '1',
                boardIdentifier: '1',
                telephoneNo:  '1828932'
                );
        examBoard2 = new ExamBoard(
                id: 2,
                name: 'Excel',
                description: 'ExcelMath',
                boardCode: '113',
                boardCentreNumber: '2',
                boardIdentifier: '2',
                telephoneNo:  '1828932'
                );
        examBoards = Arrays.asList(examBoard1, examBoard2);
    }
    
    @Test
    public void testMapFromExamBoardEntity(){
        ExamBoardDto examBoardTest = ExamBoardDto.mapFromExamBoardEntity( examBoard1 )
        assertEquals( examBoardTest.id, examBoard1.id );
        assertEquals( examBoardTest.name, examBoard1.name);
        assertEquals( examBoardTest.description, examBoard1.description);
        assertEquals( examBoardTest.boardCode, examBoard1.boardCode);
        assertEquals( examBoardTest.boardCentreNumber, examBoard1.boardCentreNumber);
    }
    
    @Test
    public void testMapFromExamBoardsEntities(){
        List<ExamBoardDto> examBoardTest = ExamBoardDto.mapFromExamBoardsEntities( examBoards )
        assertEquals( examBoardTest[0].id, examBoard1.id );
        assertEquals( examBoardTest[0].name, examBoard1.name);
        assertEquals( examBoardTest[0].description, examBoard1.description);
        assertEquals( examBoardTest[0].boardCode, examBoard1.boardCode);
        assertEquals( examBoardTest[0].boardCentreNumber, examBoard1.boardCentreNumber);
        assertEquals( examBoardTest[1].id, examBoard2.id );
        assertEquals( examBoardTest[1].name, examBoard2.name);
        assertEquals( examBoardTest[1].description, examBoard2.description);
        assertEquals( examBoardTest[1].boardCode, examBoard2.boardCode);
        assertEquals( examBoardTest[1].boardCentreNumber, examBoard2.boardCentreNumber);
    }
    
    @Test
    public void testMapToExamBoardEntity(){
        ExamBoardDto examBoardDto = new ExamBoardDto(examBoard1.id, examBoard1.name, examBoard1.description, examBoard1.boardCode, examBoard1.boardCentreNumber, examBoard1.boardIdentifier, examBoard1.telephoneNo);
        ExamBoard examBoard = ExamBoardDto.mapToExamBoardEntity( examBoardDto );
        assertEquals( examBoard.id, examBoard1.id );
        assertEquals( examBoard.name, examBoard1.name);
        assertEquals( examBoard.description, examBoard1.description);
        assertEquals( examBoard.boardCode, examBoard1.boardCode);
        assertEquals( examBoard.boardCentreNumber, examBoard1.boardCentreNumber);
    }
    
    @Test
    public void testConstructor_ExamBoard() {
        ExamBoardDto examBoardDto = new ExamBoardDto(examBoard1)
        assertEquals( examBoardDto.id, examBoard1.id );
        assertEquals( examBoardDto.name, examBoard1.name);
        assertEquals( examBoardDto.description, examBoard1.description);
        assertEquals( examBoardDto.boardCode, examBoard1.boardCode);
        assertEquals( examBoardDto.boardCentreNumber, examBoard1.boardCentreNumber);
        assertEquals( examBoardDto.boardCentreNumber, examBoard1.boardCentreNumber);
        assertEquals( examBoardDto.telephoneNo, examBoard1.telephoneNo);
    }
    
    @Test
    public void testEquals_Same() {
        ExamBoardDto examBoardDto1 = new ExamBoardDto(examBoard1.id, examBoard1.name, examBoard1.description, examBoard1.boardCode, examBoard1.boardCentreNumber, examBoard1.boardIdentifier, examBoard1.telephoneNo);
        ExamBoardDto examBoardDto2 = new ExamBoardDto(examBoard1.id, examBoard1.name, examBoard1.description, examBoard1.boardCode, examBoard1.boardCentreNumber, examBoard1.boardIdentifier, examBoard1.telephoneNo);
        assertEquals( examBoardDto1, examBoardDto2)
    }
    
    @Test
    public void testEquals_Different() {
        ExamBoardDto examBoardDto1 = new ExamBoardDto(examBoard1.id, examBoard1.name, examBoard1.description, examBoard1.boardCode, examBoard1.boardCentreNumber, examBoard1.boardIdentifier, examBoard1.telephoneNo);
        ExamBoardDto examBoardDto2 = new ExamBoardDto(examBoard2.id, examBoard2.name, examBoard2.description, examBoard2.boardCode, examBoard2.boardCentreNumber, examBoard2.boardIdentifier, examBoard2.telephoneNo);
        assertNotEquals( examBoardDto1, examBoardDto2)
    }
    
    @Test
    public void testHashCode_Same() {
        ExamBoardDto examBoardDto1 = new ExamBoardDto(examBoard1.id, examBoard1.name, examBoard1.description, examBoard1.boardCode, examBoard1.boardCentreNumber, examBoard1.boardIdentifier, examBoard1.telephoneNo);
        ExamBoardDto examBoardDto2 = new ExamBoardDto(examBoard1.id, examBoard1.name, examBoard1.description, examBoard1.boardCode, examBoard1.boardCentreNumber, examBoard1.boardIdentifier, examBoard1.telephoneNo);
        assertEquals( examBoardDto1.hashCode(), examBoardDto2.hashCode())
    }
    
    @Test
    public void testHashCode_Different() {
        ExamBoardDto examBoardDto1 = new ExamBoardDto(examBoard1.id, examBoard1.name, examBoard1.description, examBoard1.boardCode, examBoard1.boardCentreNumber, examBoard1.boardIdentifier, examBoard1.telephoneNo);
        ExamBoardDto examBoardDto2 = new ExamBoardDto(examBoard2.id, examBoard2.name, examBoard2.description, examBoard2.boardCode, examBoard2.boardCentreNumber, examBoard2.boardIdentifier, examBoard2.telephoneNo);
        assertNotEquals( examBoardDto1.hashCode(), examBoardDto2.hashCode())
    }
}
