package uk.ac.reigate.services

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

import uk.ac.reigate.domain.Staff
import uk.ac.reigate.domain.academic.SpecialCategory
import uk.ac.reigate.domain.academic.Student
import uk.ac.reigate.domain.learning_support.StudentSpecialCategory
import uk.ac.reigate.exceptions.InvalidOperationException
import uk.ac.reigate.repositories.learning_support.StudentSpecialCategoryRepository
import uk.ac.reigate.utils.ValidationUtils

@Service
class StudentSpecialCategoryService implements ICoreDataService<StudentSpecialCategory, Integer>{
    
    @Autowired
    StudentSpecialCategoryRepository studentSpecialCategoryRepository
    
    /**
     * Default NoArgs constructor
     */
    StudentSpecialCategoryService() {}
    
    /**
     * Autowired Constructor
     *
     * @param studentSpecialCategoryRepository
     */
    StudentSpecialCategoryService(StudentSpecialCategoryRepository studentSpecialCategoryRepository) {
        this.studentSpecialCategoryRepository = studentSpecialCategoryRepository
    }
    
    /**
     * Find an individual studentSpecialCategory using the studentSpecialCategorys ID fields
     *
     * @param id the ID fields to search for
     * @return the StudentSpecialCategory object that matches the ID supplied, or null if not found
     */
    @Override
    @Transactional(readOnly = true)
    StudentSpecialCategory findById(Integer id) {
        return studentSpecialCategoryRepository.findOne(id);
    }
    
    /**
     * Find a single page of StudentSpecialCategory objects
     * @return a List of StudentSpecialCategorys
     */
    @Override
    @Transactional(readOnly = true)
    List<StudentSpecialCategory> findAll() {
        return studentSpecialCategoryRepository.findAll();
    }
    
    /**
     *
     * @param id
     * @param code
     * @param description
     * @return
     */
    @Transactional
    public StudentSpecialCategory saveStudentSpecialCategory(Integer id, Student student, Date requestDate, SpecialCategory specialCategory, Boolean specialConfirmed, Date classificationDate, Date closedDate, String monitoringNotes, Staff staffRequesting, Boolean riskAssessmentRequired, Staff riskAssessmentToBeCompletedBy, Boolean informationConfidential, Boolean writtenEvidence, Boolean passToStaffConcerned, Staff staffConcerned, String riskToStudentOrOthers, String emergencyContactNos, String outsideAgenciesInvolved, String toBeInformedPotentialRisks, Staff riskAssessmentRaisedBy, Date riskAssessmentRaisedDate, Staff riskAssessmentCarriedOutBy, Date riskAssessmentCarriedOutDate, String inEventEmergency) {
        ValidationUtils.assertNotBlank(student, "student cannot be blank");
        
        StudentSpecialCategory studentSpecialCategory = null;
        
        if (id != null) {
            studentSpecialCategory = findById(id);
            
            studentSpecialCategory.setStudent(student);
            
            studentSpecialCategory.setRequestDate(requestDate);
            studentSpecialCategory.setSpecialCategory(specialCategory);
            studentSpecialCategory.setSpecialConfirmed(specialConfirmed);
            studentSpecialCategory.setClassificationDate(classificationDate);
            studentSpecialCategory.setClosedDate(closedDate);
            studentSpecialCategory.setMonitoringNotes(monitoringNotes);
            studentSpecialCategory.setStaffRequesting(staffRequesting);
            studentSpecialCategory.setRiskAssessmentRequired(riskAssessmentRequired);
            studentSpecialCategory.setRiskAssessmentToBeCompletedBy(riskAssessmentToBeCompletedBy);
            studentSpecialCategory.setInformationConfidential(informationConfidential);
            studentSpecialCategory.setWrittenEvidence(writtenEvidence);
            studentSpecialCategory.setPassToStaffConcerned(passToStaffConcerned);
            studentSpecialCategory.setStaffConcerned(staffConcerned);
            studentSpecialCategory.setRiskToStudentOrOthers(riskToStudentOrOthers);
            studentSpecialCategory.setEmergencyContactNos(emergencyContactNos);
            studentSpecialCategory.setOutsideAgenciesInvolved(outsideAgenciesInvolved);
            studentSpecialCategory.setToBeInformedPotentialRisks(toBeInformedPotentialRisks);
            studentSpecialCategory.setRiskAssessmentRaisedBy(riskAssessmentRaisedBy);
            studentSpecialCategory.setRiskAssessmentRaisedDate(riskAssessmentRaisedDate);
            studentSpecialCategory.setRiskAssessmentCarriedOutBy(riskAssessmentCarriedOutBy);
            studentSpecialCategory.setRiskAssessmentCarriedOutDate(riskAssessmentCarriedOutDate);
            studentSpecialCategory.setInEventEmergency(inEventEmergency);
            save(studentSpecialCategory);
        } else {
            studentSpecialCategory = save(new StudentSpecialCategory(student, requestDate, specialCategory, specialConfirmed, classificationDate, closedDate,  monitoringNotes, staffRequesting , riskAssessmentRequired, riskAssessmentToBeCompletedBy,  informationConfidential,  writtenEvidence, passToStaffConcerned, staffConcerned ,  riskToStudentOrOthers, emergencyContactNos,  outsideAgenciesInvolved,  toBeInformedPotentialRisks, riskAssessmentRaisedBy, riskAssessmentRaisedDate, riskAssessmentCarriedOutBy, riskAssessmentCarriedOutDate, inEventEmergency ))
        }
        return studentSpecialCategory;
    }
    
    /**
     * This service method is used to save a complete StudentSpecialCategory object in the database
     *
     * @param studentSpecialCategory the new StudentSpecialCategory object to be saved
     * @return the saved version of the StudentSpecialCategory object
     */
    @Override
    @Transactional
    public StudentSpecialCategory save(StudentSpecialCategory studentSpecialCategory) {
        return studentSpecialCategoryRepository.save(studentSpecialCategory)
    }
    
    
    /**
     * Saves a list of StudentSpecialCategory objects to the database
     *
     * @param studentSpecialCategorys a list of StudentSpecialCategorys to be saved to the database
     * @return the list of save StudentSpecialCategory objects
     */
    @Transactional
    public List<StudentSpecialCategory> saveStudentSpecialCategorys(List<StudentSpecialCategory> studentSpecialCategories) {
        return studentSpecialCategories.collect { studentSpecialCategory -> save(studentSpecialCategory) };
    }
    
    /**
     * @param studentId
     * @return
     */
    public  List<StudentSpecialCategory> getByStudent(Integer studentId){
        return studentSpecialCategoryRepository.findByStudent_Id(studentId);
    }
    
    /**
     * This methods throws an InvalidOperationException when called. StudentSpecialCategory should not be deleted.
     */
    @Override
    public void delete(StudentSpecialCategory obj) {
        throw new InvalidOperationException("StudentSpecialCategory should not be deleted")
    }
}
