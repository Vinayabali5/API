package uk.ac.reigate.services

//import static org.springframework.util.Assert

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import org.springframework.security.access.prepost.PreAuthorize
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

import uk.ac.reigate.domain.academic.AcademicYear
import uk.ac.reigate.domain.academic.Course;
import uk.ac.reigate.domain.academic.Student;
import uk.ac.reigate.domain.admissions.Request
import uk.ac.reigate.exceptions.InvalidOperationException
import uk.ac.reigate.model.PageInfo
import uk.ac.reigate.model.SearchResult
import uk.ac.reigate.repositories.admissions.RequestRepository
import uk.ac.reigate.utils.ValidationUtils;

@Service
class RequestService implements ICoreDataService<Request, Integer>{
    
    @Autowired
    RequestRepository requestRepository
    
    /**
     * Default NoArgs constructor
     */
    RequestService() {}
    
    /**
     * Autowired Constructor
     *
     * @param requestRepository
     */
    RequestService(RequestRepository requestRepository) {
        this.requestRepository = requestRepository;
    }
    
    /**
     * Find an individual request using the requests ID fields
     *
     * @param id the ID fields to search for
     * @return the Request object that matches the ID supplied, or null if not found
     */
    @Override
    @Transactional(readOnly = true)
    Request findById(Integer id) {
        return requestRepository.findOne(id);
    }
    /**
     * Find all requests
     *
     * @return a SearchResult set with the list of Requests
     */
    @Override
    @Transactional(readOnly = true)
    List<Request> findAll() {
        return requestRepository.findAll();
    }
    
    /**
     * @param id
     * @param student
     * @param request
     * @param academicYear
     * @param coreAim
     * @param broadeningSubject
     * @param chosenAgainstAdvice
     * @param allocated
     * @return
     */
    @Transactional
    public Request saveRequest(Integer id, Student student, String request, AcademicYear academicYear, Boolean coreAim, Boolean broadeningSubject, Boolean chosenAgainstAdvice, Boolean allocated) {
        ValidationUtils.assertNotBlank(request, "request cannot be blank");
        
        Request requestt = null;
        
        if (id != null) {
            requestt = findById(id);
            requestt.setStudent(student);
            requestt.setRequest(request);
            requestt.setAcademicYear(academicYear);
            requestt.setCoreAim(coreAim);
            requestt.setBroadeningSubject(broadeningSubject);
            requestt.setChosenAgainstAdvice(chosenAgainstAdvice);
            requestt.setAllocated(allocated);
            
            save(requestt);
        } else {
            requestt = save(new Request(requestt));
        }
        
        return requestt;
    }
    
    /**
     * This service method is used to save a complete Request object in the database
     *
     * @param request the new Request object to be saved
     * @return the saved version of the Request object
     */
    @Override
    @Transactional
    public Request save(Request request) {
        return requestRepository.save(request)
    }
    
    /**
     * Saves a list of Request objects to the database
     *
     * @param requests a list of Requests to be saved to the database
     * @return the list of save Request objects
     */
    
    @Transactional
    public List<Request> saveRequests(List<Request> requests) {
        return requests.collect { request ->
            saveRequest(request.id, request.student, request.request, request.academicYear, request.coreAim, request.broadeningSubject, request.chosenAgainstAdvice, request.allocated)
        };
    }
    
    /**
     * This service method is used to retrieve a list of requests for a specified student based on the provided studentId
     * 
     * @param studentId the studentId to use as a filter
     * @return a filtered list of request for the specified student
     */
    @PreAuthorize("@securityChecker.checkReader(authentication)")
    @Transactional(readOnly = true)
    List<Request> findRequestsByStudentId(Integer studentId) {
        List<Request> requests = requestRepository.findByStudent_Id(studentId);
        return requests
    }
    
    /**
     * @param year
     * @return List of Request by Year
     */
    @PreAuthorize("@securityChecker.checkReader(authentication)")
    public List<Request> searchByYear(AcademicYear year) {
        List<Request> requests = requestRepository.findByAcademicYear(year)
        return requests
    }
    
    /**
     * @param academicYearId
     * @param studentId
     * @return  List of Request by Year and Student
     */
    @PreAuthorize("@securityChecker.checkReader(authentication)")
    public List<Request> searchByYearAndStudentId(Integer academicYearId, Integer studentId){
        List<Request> requests = requestRepository.findByAcademicYear_IdAndStudent_Id(academicYearId, studentId)
    }
    
    /**
     * @param studentId
     * @param yearId
     * @return List of Request by YearId and StudentId
     */
    @PreAuthorize("@securityChecker.checkReader(authentication)")
    public List<Request> findByStudentIdYearId(Integer studentId, Integer yearId){
        List<Request> requests = requestRepository.findByStudent_IdAndAcademicYear_Id(studentId, yearId)
    }
    
    /**
     * @param studentId
     * @return List of Request
     */
    @PreAuthorize("@securityChecker.checkReader(authentication)")
    public List<Request> findByStudentId(Integer studentId){
        List<Request> requests = requestRepository.findByStudent_Id(studentId)
    }
    
    /**
     * This methods throws an InvalidOperationException when called. Request should not be deleted.
     */
    @Override
    public void delete(Request obj) {
        throw new InvalidOperationException("Request should not be deleted")
    }
    
}
