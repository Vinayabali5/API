package uk.ac.reigate.services.exam

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.data.domain.Page
import org.springframework.data.domain.PageRequest
import org.springframework.data.domain.Sort
import org.springframework.security.access.prepost.PreAuthorize
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

import uk.ac.reigate.domain.exam.ExamBoard
import uk.ac.reigate.exceptions.InvalidOperationException
import uk.ac.reigate.model.PageInfo
import uk.ac.reigate.model.SearchResult
import uk.ac.reigate.repositories.exam.ExamBoardRepository
import uk.ac.reigate.services.ICoreDataService
import uk.ac.reigate.utils.ValidationUtils

@Service
class ExamBoardService implements ICoreDataService<ExamBoard, Integer>{
    
    @Autowired
    ExamBoardRepository examBoardRepository
    
    /**
     * Default NoArgs constructor
     */
    ExamBoardService() {}
    
    /**
     * Autowired Constructor
     *
     * @param examBoardRepository
     */
    ExamBoardService(ExamBoardRepository examBoardRepository) {
        this.examBoardRepository = examBoardRepository;
    }
    
    /**
     * Find an individual examBoard using the examBoards ID fields
     *
     * @param id the ID fields to search for
     * @return the ExamBoard object that matches the ID supplied, or null if not found
     */
    @Override
    @Transactional(readOnly = true)
    ExamBoard findById(Integer id) {
        return examBoardRepository.findOne(id);
    }
    
    /**
     * Find all examBoards
     *
     * @return a SearchResult set with the list of examBoards
     */
    @Override
    @Transactional(readOnly = true)
    List<ExamBoard> findAll() {
        return examBoardRepository.findAll();
    }
    
    /**
     * Find a single page of Syllabus objects
     *
     * @param page - the page number to retrieve
     * @param size - the number of records on each page
     * @return a SearchResult set with the list of Syllabi
     */
    @PreAuthorize("@securityChecker.checkReader(authentication)")
    public SearchResult<ExamBoard> findExamBoards(int page, int size) {
        PageRequest pageRequest = new PageRequest(page, size);
        Page<ExamBoard> examBoards = examBoardRepository.findAll(pageRequest);
        PageInfo pageInfo = new PageInfo(page, size, '', (Long) examBoards.total);
        return new SearchResult<>(pageInfo, examBoards.toList());
    }
    
    /**
     * Find a single page of Syllabus objects
     *
     * @param page - the page number to retrieve
     * @param size - the number of records on each page
     * @param sortDir - the direction to sort
     * @return a SearchResult set with the list of Syllabi
     */
    @PreAuthorize("@securityChecker.checkReader(authentication)")
    public SearchResult<ExamBoard> findExamBoards(int page, int size, String sortDir) {
        PageRequest pageRequest = new PageRequest(page, size, Sort.Direction.ASC);
        Page<ExamBoard> examBoards = examBoardRepository.findAll(pageRequest);
        PageInfo pageInfo = new PageInfo(page, size, '', (Long) examBoards.total);
        return new SearchResult<>(pageInfo, examBoards.toList());
    }
    
    /**
     * Save an ExamBoard object to the database
     *
     * @param examBoard - the ExamBoard object to save
     * @return the saved ExamBoard object
     */
    @Transactional
    public ExamBoard saveExamBoard(Integer id, String name, String description, String boardCode, String boardCentreNumber, String boardIdentifier, String telephoneNo) {
        ValidationUtils.assertNotBlank(name, "name cannot be blank");
        ValidationUtils.assertNotNull(description, "description is mandatory");
        ValidationUtils.assertNotNull(boardCode, "boardCode is mandatory");
        
        ExamBoard examBoard = null;
        
        if (id != null) {
            examBoard = findById(id);
            
            examBoard.setName(name);
            examBoard.setDescription(description);
            examBoard.setBoardCode(boardCode);
            examBoard.setBoardCentreNumber(boardCentreNumber);
            examBoard.setBoardIdentifier(boardIdentifier);
            examBoard.setTelephoneNo(telephoneNo);
            
            save(examBoard);
        } else {
            examBoard = save(new ExamBoard(name, description, boardCode, boardCentreNumber, boardIdentifier, telephoneNo));
        }
        
        return examBoard;
    }
    
    /**
     * This service method is used to save a complete ExamBoard object in the database
     *
     * @param examBoard the new ExamBoard object to be saved
     * @return the saved version of the ExamBoard object
     */
    @Override
    @Transactional
    public ExamBoard save(ExamBoard examBoard) {
        return examBoardRepository.save(examBoard)
    }
    
    /**
     * This service method is used to update an ExamBoard object in the database from a partial or complete ExamBoard object.
     *
     * @param examBoard the partial or complete ExamBoard object to be saved
     * @return the saved version of the ExamBoard object
     */
    @Transactional
    public ExamBoard updateExamBoard(ExamBoard examBoard) {
        ExamBoard examBoardToSave = findById(examBoard.id)
        examBoardToSave.name = examBoard.name != null ? examBoard.name : examBoardToSave.name
        examBoardToSave.description = examBoard.description != null ? examBoard.description : examBoardToSave.description
        examBoardToSave.boardCode = examBoard.boardCode != null ? examBoard.boardCode : examBoardToSave.boardCode
        examBoardToSave.boardCentreNumber = examBoard.boardCentreNumber != null ? examBoard.boardCentreNumber : examBoardToSave.boardCentreNumber
        examBoardToSave.boardIdentifier = examBoard.boardIdentifier != null ? examBoard.boardIdentifier : examBoardToSave.boardIdentifier
        examBoardToSave.telephoneNo = examBoard.telephoneNo != null ? examBoard.telephoneNo : examBoardToSave.telephoneNo
        return save(examBoardToSave)
    }
    
    /**
     * Saves a list of ExamBoard objects to the database
     *
     * @param examBoards a list of ExamBoards to be saved to the database
     * @return the list of save ExamBoard objects
     */
    @Transactional
    public List<ExamBoard> saveExamBoards(List<ExamBoard> examBoards) {
        return examBoards.collect { examBoard -> save(examBoard) };
    }
    
    /* (non-Javadoc)
     * @see uk.ac.reigate.services.ICoreDataService#delete(java.lang.Object)
     */
    @Override
    public void delete(ExamBoard obj) {
        throw new InvalidOperationException("ExamBoard should not be deleted")
    }
}
