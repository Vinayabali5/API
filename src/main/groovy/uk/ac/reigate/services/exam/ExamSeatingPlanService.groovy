package uk.ac.reigate.services.exam

import java.util.logging.Logger

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.security.access.prepost.PreAuthorize
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

import uk.ac.reigate.domain.Room
import uk.ac.reigate.domain.exam.ExamSeatingPlan
import uk.ac.reigate.domain.exam.ExamSession
import uk.ac.reigate.repositories.RoomRepository
import uk.ac.reigate.repositories.exam.ExamSeatingPlanRepository
import uk.ac.reigate.repositories.exam.ExamSessionRepository
import uk.ac.reigate.services.ICoreDataService

@Service
class ExamSeatingPlanService implements ICoreDataService<ExamSeatingPlan, Integer>{
    private final static Logger log = Logger.getLogger(ExamSeatingPlanService.class.getName())
    
    @Autowired
    ExamSeatingPlanRepository examSeatingPlanRepository
    
    @Autowired
    ExamSessionRepository examSessionRepository
    
    @Autowired
    RoomRepository roomRepository
    
    /**
     * Default NoArgs constructor
     */
    ExamSeatingPlanService() {}
    
    /**
     * Autowired Constructor
     *
     * @param roomRepository
     */
    ExamSeatingPlanService(ExamSeatingPlanRepository examSeatingPlanRepository) {
        this.examSeatingPlanRepository = examSeatingPlanRepository;
    }
    
    /* (non-Javadoc)
     * @see uk.ac.reigate.services.ICoreDataService#findById(java.lang.Object)
     */
    @Override
    @Transactional(readOnly = true)
    ExamSeatingPlan findById(Integer id) {
        return examSeatingPlanRepository.findOne(id);
    }
    
    /**
     * 
     * @return a List of ExamRooms
     */
    @Override
    @Transactional(readOnly = true)
    List<ExamSeatingPlan> findAll() {
        return examSeatingPlanRepository.findAll();
    }
    
    /**
     * Find all ExamSeatingPlan objects where timetableDate and timetableSession match 
     * 
     * @param timetableDate
     * @param timetableSession
     * @return
     */
    @PreAuthorize("@securityChecker.checkReader(authentication)")
    @Transactional(readOnly = true)
    public List<ExamSeatingPlan> findExamRoomsByDateAndSession(Date timetableDate, String timetableSession) {
        return examSeatingPlanRepository.findByExamSession_DateAndExamSession_Session(timetableDate, timetableSession);
    }
    
    /**
     * Find an ExamSeatingPlan object where examSessionId and RoomId match
     * 
     * @param examSessionId
     * @param roomId
     * @return
     */
    @PreAuthorize("@securityChecker.checkReader(authentication)")
    @Transactional(readOnly = true)
    public ExamSeatingPlan findExamRoomsByExamSessionIdAndRoomId(Integer examSessionId, Integer roomId) {
        return examSeatingPlanRepository.findByExamSession_IdAndRoom_Id(examSessionId, roomId);
    }
    
    /**
     * 
     * @param id
     * @param examSession
     * @param room
     * @param rows
     * @param cols
     * @return
     */
    @Transactional
    public ExamSeatingPlan saveExamRoom(Integer id, ExamSession examSession, Room room, Integer rows, Integer cols) {
        ExamSeatingPlan examRoom = null;
        
        if (id != null) {
            examRoom = findById(id);
            examRoom.setExamSession(examSession);
            examRoom.setRoom(room);
            examRoom.setRows(rows);
            examRoom.setCols(cols);
            save(examRoom);
        } else {
            examRoom = save(new ExamSeatingPlan(examSession, room, rows, cols));
        }
        
        return examRoom;
    }
    
    /**
     * This service method is used to save an ExamSeatingPlan object in the database from an ExamSeatingPlan object
     * 
     * @param examRoom
     * @return
     */
    @Override
    @Transactional
    public ExamSeatingPlan save(ExamSeatingPlan examRoom) {
        return examSeatingPlanRepository.save(examRoom);
    }
    
    
    /**
     * This service method is used to update an Room object in the database from a partial or complete Room object.
     *
     * @param room the partial or complete Room object to be saved
     * @return the saved version of the Room object
     */
    @Transactional
    public ExamSeatingPlan updateExamRoom(ExamSeatingPlan examRoom) {
        ExamSeatingPlan examRoomToSave = findById(examRoom.id)
        examRoomToSave.examSession = examRoom.examSession.id != null ? examSessionRepository.findOne(examRoom.examSession.id) : examRoomToSave.examSession
        examRoomToSave.room = examRoom.room.id != null ? roomRepository.findOne(examRoom.room.id) : examRoomToSave.room
        examRoomToSave.rows = examRoom.rows != null ? examRoom.rows : examRoom.rows
        examRoomToSave.cols = examRoom.cols != null ? examRoom.cols : examRoom.cols
        return save(examRoomToSave)
    }
    
    /**
     * Saves a list of Room objects to the database
     *
     * @param rooms a list of Rooms to be saved to the database
     * @return the list of save Room objects
     */
    @Transactional
    public List<ExamSeatingPlan> saveExamRooms(List<ExamSeatingPlan> examRooms) {
        return examRooms.collect { examRoom -> save(examRoom) };
    }
    
    
    /**
     * @param examRoomId
     * @return
     */
    @Transactional
    public deleteExamRoom(Integer examRoomId) {
        examSeatingPlanRepository.delete(examRoomId);
    }
    
    
    /* (non-Javadoc)
     * @see uk.ac.reigate.services.ICoreDataService#delete(java.lang.Object)
     */
    @Override
    @Transactional
    public void delete(ExamSeatingPlan examSeatingPlan){
        log.info("******  Deleting in Delete method-----"+examSeatingPlan.id);
        examSeatingPlanRepository.delete(examSeatingPlan)
    }
}
