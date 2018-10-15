package uk.ac.reigate.services

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.security.access.prepost.PreAuthorize
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

import uk.ac.reigate.domain.academic.Student
import uk.ac.reigate.domain.admissions.CollegeFundPayment
import uk.ac.reigate.exceptions.InvalidOperationException
import uk.ac.reigate.repositories.admissions.CollegeFundPaymentRepository
import uk.ac.reigate.utils.ValidationUtils

@Service
class CollegeFundPaymentService implements ICoreDataService<CollegeFundPayment, Integer>{
    
    @Autowired
    CollegeFundPaymentRepository collegeFundPaymentRepository
    
    /**
     * Default NoArgs constructor
     */
    CollegeFundPaymentService() {}
    
    /**
     * Autowired Constructor
     *
     * @param collegeFundPaymentRepository
     */
    CollegeFundPaymentService(CollegeFundPaymentRepository collegeFundPaymentRepository) {
        this.collegeFundPaymentRepository = collegeFundPaymentRepository;
    }
    
    /**
     * Find an individual collegeFundPayment using the collegeFundPayments ID fields
     *
     * @param id the ID fields to search for
     * @return the CollegeFundPayment object that matches the ID supplied, or null if not found
     */
    @Override
    @Transactional(readOnly = true)
    CollegeFundPayment findById(Integer id) {
        return collegeFundPaymentRepository.findOne(id);
    }
    
    /**
     * Find all collegeFundPayments
     *
     * @return a SearchResult set with the list of CollegeFundPayments
     */
    @Override
    @Transactional(readOnly = true)
    List<CollegeFundPayment> findAll() {
        return collegeFundPaymentRepository.findAll();
    }
    
    
    /** This method saves to either existing object or creates a new object
     * @param id
     * @param student
     * @param paymentDate
     * @param amount
     * @param payee
     * @param giftAid
     * @param cash
     * @param chequeDate
     * @return
     */
    @Transactional
    public CollegeFundPayment saveCollegeFundPayment(Integer id, Student student, Date paymentDate, float amount, String payee, boolean giftAid, boolean cash, Date chequeDate) {
        ValidationUtils.assertNotBlank(paymentDate, "paymentDate cannot be blank");
        CollegeFundPayment collegeFundPayment = null;
        if (id != null) {
            collegeFundPayment = findById(id);
            collegeFundPayment.setStudent(student);
            collegeFundPayment.setPaymentDate(paymentDate);
            collegeFundPayment.setAmount(amount);
            collegeFundPayment.setPayee(payee);
            collegeFundPayment.setGiftAid(giftAid);
            collegeFundPayment.setCash(cash);
            collegeFundPayment.setChequeDate(chequeDate);
            save(collegeFundPayment);
        } else {
            collegeFundPayment = save(new CollegeFundPayment(collegeFundPayment));
        }
        return collegeFundPayment;
    }
    
    /**
     * This service method is used to save a complete CollegeFundPayment object in the database
     *
     * @param collegeFundPayment the new CollegeFundPayment object to be saved
     * @return the saved version of the CollegeFundPayment object
     */
    @Override
    @Transactional
    public CollegeFundPayment save(CollegeFundPayment collegeFundPayment) {
        return collegeFundPaymentRepository.save(collegeFundPayment)
    }
    
    /**
     * Saves a list of CollegeFundPayment objects to the database
     *
     * @param collegeFundPayments a list of CollegeFundPayments to be saved to the database
     * @return the list of save CollegeFundPayment objects
     */
    @Transactional
    public List<CollegeFundPayment> saveCollegeFundPayments(List<CollegeFundPayment> collegeFundPayments) {
        return collegeFundPayments.collect { collegeFundPayment ->
            saveCollegeFundPayment(collegeFundPayment.id, collegeFundPayment.student, collegeFundPayment.paymentDate, collegeFundPayment.amount, collegeFundPayment.payee, collegeFundPayment.giftAid, collegeFundPayment.cash, collegeFundPayment.chequeDate)
        };
    }
    
    /**
     * This service method is used to retrieve a list of collegeFundPayments for a specified student based on the provided studentId
     * 
     * @param studentId the studentId to use as a filter
     * @return a filtered list of collegeFundPayment for the specified student
     */
    @PreAuthorize("@securityChecker.checkReader(authentication)")
    @Transactional(readOnly = true)
    List<CollegeFundPayment> findCollegeFundPaymentsByStudentId(Integer studentId) {
        return collegeFundPaymentRepository.findByStudent_Id(studentId);
    }
    
    /**
     * @param studentId
     * @return List of CollegeFundPayment of a studentId
     */
    @PreAuthorize("@securityChecker.checkReader(authentication)")
    @Transactional
    public  List<CollegeFundPayment> getByStudent(Integer studentId){
        return collegeFundPaymentRepository.findByStudent_Id(studentId);
    }
    
    /**
     * This methods throws an InvalidOperationException when called. CollegeFundPayment should not be deleted.
     */
    @Override
    public void delete(CollegeFundPayment obj) {
        throw new InvalidOperationException("CollegeFundPayment should not be deleted.")
    }
}
