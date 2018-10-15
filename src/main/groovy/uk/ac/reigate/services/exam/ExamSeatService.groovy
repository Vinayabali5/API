package uk.ac.reigate.services.exam

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.security.access.prepost.PreAuthorize
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

import uk.ac.reigate.domain.academic.Student
import uk.ac.reigate.domain.exam.ExamComponent
import uk.ac.reigate.domain.exam.ExamSeat
import uk.ac.reigate.domain.exam.ExamSeatPk
import uk.ac.reigate.model.SearchResult
import uk.ac.reigate.repositories.exam.ExamComponentRepository
import uk.ac.reigate.repositories.exam.ExamSeatRepository
import uk.ac.reigate.services.AcademicYearService
import uk.ac.reigate.services.ICoreDataService
import uk.ac.reigate.services.StudentService


@Service
class ExamSeatService implements ICoreDataService<ExamSeat, ExamSeatPk>{
    
    @Autowired
    AcademicYearService academicYearService
    
    @Autowired
    StudentService studentService
    
    @Autowired
    ExamComponentService examComponentService
    
    @Autowired
    ExamSeatRepository examSeatRepository
    
    @Autowired
    ExamComponentRepository examComponentRepository
    
    /**
     * Default No Args constructor
     */
    ExamSeatService() {}
    
    
    /**
     * Find an individual seatingPlan using the seatingPlan ID fields
     *
     * @param id the ID fields to search for
     * @return the OptionEntry object that matches the ID supplied, or null if not found
     */
    @PreAuthorize("@securityChecker.checkReader(authentication)")
    @Transactional(readOnly = true)
    ExamSeat findSeatingPlan(Student student, ExamComponent examComponent){
        return examSeatRepository.findByStudent_IdAndExamComponent_ExamComponentId(student.id, examComponent.examComponentId)
    }
    
    /**
     * Find an individual seatingPlan using the seatingPlan ID fields
     *
     * @param id the ID fields to search for
     * @return the OptionEntry object that matches the ID supplied, or null if not found
     */
    @Transactional(readOnly = true)
    ExamSeat findSeatingPlan(Integer examSeatingPlanId, Integer row, Integer col) {
        return findById(new ExamSeatPk(examSeatingPlanId, row, col));
    }
    
    @PreAuthorize("@securityChecker.checkReader(authentication)")
    @Transactional(readOnly = true)
    List<ExamSeat> findByExamComponent(Integer examComponentId) {
        List<ExamSeat> seatingPlans = examSeatRepository.findByExamComponentId(examComponentId);
        return seatingPlans;
    }
    
    /**
     * Find all seatingPlans
     * @return a SearchResult set with the list of OptionEntries
     */
    @Override
    @Transactional(readOnly = true)
    List<ExamSeat> findAll() {
        return examSeatRepository.findAll();
    }
    
    /**
     * 
     * @param seatingPlan
     * @return
     */
    @Override
    @Transactional
    public ExamSeat save(ExamSeat seatingPlan) {
        return examSeatRepository.save(seatingPlan)
    }
    
    /* (non-Javadoc)
     * @see uk.ac.reigate.services.ICoreDataService#delete(java.lang.Object)
     */
    @Override
    @Transactional
    public void delete(ExamSeat examSeat) {
        examSeatRepository.delete(new ExamSeatPk(examSeat) )
    }
    
    
    /**
     * @param studentId
     * @param examComponentId
     * @return
     */
    @Transactional
    public Boolean deleteByStudentAndExamComponent(Integer studentId, Integer examComponentId){
        Student student = studentService.findById(studentId)
        ExamComponent examComponent = examComponentRepository.findOne(examComponentId)
        ExamSeat examSeat = findSeatingPlan(student, examComponent)
        deleteByRowAndCol(examSeat.examSeatingPlan.id, examSeat.row, examSeat.col)
    }
    
    
    /**
     * @param seatingPlanId
     * @param row
     * @param col
     * @return
     */
    @Transactional
    public Boolean deleteByRowAndCol(Integer seatingPlanId, Integer row, Integer col){
        delete(findSeatingPlan(seatingPlanId, row, col))
    }
    
    
    /* (non-Javadoc)
     * @see uk.ac.reigate.services.ICoreDataService#findById(java.lang.Object)
     */
    @Override
    public ExamSeat findById(ExamSeatPk id) {
        return examSeatRepository.findOne(id);
    }
}
