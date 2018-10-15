package uk.ac.reigate.services;

import static org.junit.Assert.assertNull
import static org.junit.Assert.assertTrue
import static org.mockito.Mockito.*

import org.junit.Before
import org.junit.Test
import org.mockito.Mockito

import uk.ac.reigate.domain.exam.ExamBoard
import uk.ac.reigate.model.SearchResult
import uk.ac.reigate.repositories.exam.ExamBoardRepository
import uk.ac.reigate.services.exam.ExamBoardService


public class ExamBoardServiceTest {
    
    private ExamBoardRepository examBoardRepository
    
    private ExamBoardService examBoardService;
    
    ExamBoard examBoard1
    ExamBoard examBoard2
    
    @Before
    public void setup() {
        this.examBoardRepository = Mockito.mock(ExamBoardRepository.class);
        this.examBoardService = new ExamBoardService(examBoardRepository);
        
        examBoard1 = new ExamBoard(id: 1,  name: 'Excel', description: 'ExcelBtec', boardCode: '111', boardCentreNumber: '1', boardIdentifier: '1', telephoneNo:  '1828932')
        examBoard2 = new ExamBoard(id: 2,  name: 'Excel', description: 'ExcelMath', boardCode: '113', boardCentreNumber: '2', boardIdentifier: '2', telephoneNo:  '1828933')
        
        when(examBoardRepository.findAll()).thenReturn([examBoard1, examBoard2]);
        when(examBoardRepository.findOne(1)).thenReturn(examBoard1);
        when(examBoardRepository.findOne(null)).thenThrow(new IllegalArgumentException());
        when(examBoardRepository.save(any(ExamBoard.class))).thenReturn(examBoard1);
    }
    
    @Test
    public void testFindExamBoards() {
        List<ExamBoard> result = examBoardService.findAll();
        verify(examBoardRepository, times(1)).findAll()
        verifyNoMoreInteractions(examBoardRepository)
    }
    
    @Test
    public void testFindExamBoard() {
        ExamBoard result = examBoardService.findById(1);
        verify(examBoardRepository, times(1)).findOne(1)
        verifyNoMoreInteractions(examBoardRepository)
    }
    
    @Test
    public void testSaveNewExamBoard() {
        ExamBoard savedExamBoard = examBoardService.save(examBoard1);
        verify(examBoardRepository, times(1)).save(any())
        verifyNoMoreInteractions(examBoardRepository)
    }
    
    @Test
    public void testSaveExamBoard() {
        ExamBoard savedExamBoard = examBoardService.save(examBoard1);
        verify(examBoardRepository, times(1)).save(any())
        verifyNoMoreInteractions(examBoardRepository)
    }
    
    @Test
    public void testSaveExamBoards() {
        List<ExamBoard> savedExamBoards = examBoardService.saveExamBoards([examBoard1, examBoard2]);
        verify(examBoardRepository, times(2)).save(any(ExamBoard.class))
        verifyNoMoreInteractions(examBoardRepository)
    }
    
    @Test
    public void testSaveExamBoardByFields_WithNullId() {
        ExamBoard savedExamBoard = examBoardService.saveExamBoard(null, examBoard1.name, examBoard1.description, examBoard1.boardCode, examBoard1.boardCentreNumber, examBoard1.boardIdentifier, examBoard1.telephoneNo);
        verify(examBoardRepository, times(1)).save(any())
        verifyNoMoreInteractions(examBoardRepository)
    }
    
    
    @Test
    public void testSaveExamBoardByFields_WithId() {
        ExamBoard savedExamBoard = examBoardService.saveExamBoard(1, examBoard1.name, examBoard1.description, examBoard1.boardCode, examBoard1.boardCentreNumber, examBoard1.boardIdentifier, examBoard1.telephoneNo);
        verify(examBoardRepository, times(1)).findOne(1)
        verify(examBoardRepository, times(1)).save(any())
        verifyNoMoreInteractions(examBoardRepository)
    }
}
