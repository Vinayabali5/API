package uk.ac.reigate.services

import java.util.Date

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

import uk.ac.reigate.domain.Address
import uk.ac.reigate.domain.academic.AcademicYear
import uk.ac.reigate.domain.lookup.ReportingPeriod
import uk.ac.reigate.exceptions.InvalidOperationException
import uk.ac.reigate.repositories.lookup.ReportingPeriodRepository
import uk.ac.reigate.utils.ValidationUtils

@Service
class ReportingPeriodService implements ICoreDataService<ReportingPeriod, Integer>{
    
    @Autowired
    ReportingPeriodRepository reportingPeriodRepository
    
    /**
     * Default NoArgs constructor
     */
    ReportingPeriodService() {}
    
    /**
     * Autowired Constructor
     *
     * @param reportingPeriodRepository
     */
    ReportingPeriodService(ReportingPeriodRepository reportingPeriodRepository) {
        this.reportingPeriodRepository = reportingPeriodRepository;
    }
    
    /**
     * Find an individual reportingPeriod using the reportingPeriods ID fields
     *
     * @param id the ID fields to search for
     * @return the ReportingPeriod object that matches the ID supplied, or null if not found
     */
    @Override
    @Transactional(readOnly = true)
    ReportingPeriod findById(Integer id) {
        return reportingPeriodRepository.findOne(id);
    }
    
    /**
     * Find all reportingPeriods
     *
     * @return a SearchResult set with the list of ReportingPeriods
     */
    @Override
    @Transactional(readOnly = true)
    List<ReportingPeriod> findAll() {
        return reportingPeriodRepository.findAll();
    }
    
    @Transactional(readOnly = true)
    List<ReportingPeriod> searchByAcademicYear(AcademicYear academicYear) {
        return reportingPeriodRepository.findByAcademicYear(academicYear);
    }
    
    /**
     * @param id
     * @param code
     * @param description
     * @return
     */
    @Transactional
    public ReportingPeriod saveReportingPeriod(Integer id, AcademicYear academicYear, String name, Date startDate, Date endDate) {
        ValidationUtils.assertNotBlank(name, "name cannot be blank");
        
        ReportingPeriod reportingPeriod = null;
        
        if (id != null) {
            reportingPeriod = findById(id);
            
            reportingPeriod.setAcademicYear(academicYear);
            reportingPeriod.setName(name);
            reportingPeriod.setStartDate(startDate);
            reportingPeriod.setEndDate(endDate);
            
            save(reportingPeriod);
        } else {
            reportingPeriod = save(new ReportingPeriod(academicYear, name, startDate, endDate));
        }
        
        return reportingPeriod;
    }
    
    /**
     * This service method is used to save a complete ReportingPeriod object in the database
     *
     * @param reportingPeriod the new ReportingPeriod object to be saved
     * @return the saved version of the ReportingPeriod object
     */
    @Override
    @Transactional
    public ReportingPeriod save(ReportingPeriod reportingPeriod) {
        return reportingPeriodRepository.save(reportingPeriod)
    }
    
    @Override
    public void delete(ReportingPeriod obj) {
        throw new InvalidOperationException("ReportingPeriod should not be deleted.")
    }
}
