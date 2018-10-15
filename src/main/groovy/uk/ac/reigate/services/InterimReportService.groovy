package uk.ac.reigate.services

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

import uk.ac.reigate.domain.academic.AcademicYear
import uk.ac.reigate.domain.academic.InterimReport
import uk.ac.reigate.exceptions.InvalidOperationException
import uk.ac.reigate.repositories.academic.InterimReportRepository
import uk.ac.reigate.utils.ValidationUtils

@Service
class InterimReportService implements ICoreDataService<InterimReport, Integer>{
    
    @Autowired
    InterimReportRepository interimReportRepository
    
    /**
     * Default NoArgs constructor
     */
    InterimReportService() {}
    
    /**
     * Autowired Constructor
     *
     * @param interimReportRepository
     */
    InterimReportService(InterimReportRepository interimReportRepository) {
        this.interimReportRepository = interimReportRepository
    }
    
    /**
     * Find an individual interimReport using the interimReports ID fields
     *
     * @param id the ID fields to search for
     * @return the InterimReport object that matches the ID supplied, or null if not found
     */
    @Override
    @Transactional(readOnly = true)
    InterimReport findById(Integer id) {
        return interimReportRepository.findOne(id);
    }
    
    /**
     * Find a single page of InterimReport objects
     * @return a SearchResult set with the list of InterimReports
     */
    @Override
    @Transactional(readOnly = true)
    List<InterimReport> findAll() {
        return interimReportRepository.findAll();
    }
    
    /**
     *
     * @param id
     * @param code
     * @param description
     * @return
     */
    @Transactional
    public InterimReport saveInterimReport(Integer id, String code, String description, AcademicYear year, Date startDate, Date endDate, Date publishDate, Boolean active) {
        ValidationUtils.assertNotBlank(code, "code cannot be blank");
        ValidationUtils.assertNotNull(description, "description is mandatory");
        InterimReport interimReport = null;
        if (id != null) {
            interimReport = findById(id);
            interimReport.setCode(code);
            interimReport.setDescription(description);
            interimReport.setYear(year);
            interimReport.setStartDate(startDate);
            interimReport.setEndDate(endDate);
            interimReport.setPublishDate(publishDate);
            interimReport.setActive(active)
            save(interimReport);
        } else {
            interimReport = save(new InterimReport(code, description, year, startDate, endDate, publishDate, active));
        }
        return interimReport;
    }
    
    /**
     * This service method is used to save a complete InterimReport object in the database
     *
     * @param interimReport the new InterimReport object to be saved
     * @return the saved version of the InterimReport object
     */
    @Override
    @Transactional
    public InterimReport save(InterimReport interimReport) {
        return interimReportRepository.save(interimReport)
    }
    
    /**
     * This service method is used to update an InterimReport object in the database from a partial or complete InterimReport object.
     *
     * @param interimReport the partial or complete InterimReport object to be saved
     * @return the saved version of the InterimReport object
     */
    @Transactional
    public InterimReport updateInterimReport(InterimReport interimReport) {
        InterimReport interimReportToSave = findById(interimReport.id)
        interimReportToSave.code = interimReport.code != null ? interimReport.code : interimReportToSave.code
        interimReportToSave.description = interimReport.description != null ? interimReport.description : interimReportToSave.description
        interimReportToSave.year = interimReport.year != null ? interimReport.year : interimReportToSave.year
        interimReportToSave.startDate = interimReport.startDate != null ? interimReport.startDate : interimReportToSave.startDate
        interimReportToSave.endDate = interimReport.endDate != null ? interimReport.endDate : interimReportToSave.endDate
        interimReportToSave.publishDate = interimReport.publishDate != null ? interimReport.publishDate : interimReportToSave.publishDate
        interimReportToSave.active = interimReport.active != null ? interimReport.active : interimReportToSave.active
        return save(interimReportToSave)
    }
    
    /**
     * Saves a list of InterimReport objects to the database
     *
     * @param interimReports a list of InterimReports to be saved to the database
     * @return the list of save InterimReport objects
     */
    @Transactional
    public List<InterimReport> saveInterimReports(List<InterimReport> interimReports) {
        return interimReports.collect { interimReport -> save(interimReport) };
    }
    
    /**
     * This methods throws an InvalidOperationException when called. InterimReport should not be deleted.
     */
    @Override
    public void delete(InterimReport obj) {
        throw new InvalidOperationException("InterimReport should not be deleted")
    }
}
