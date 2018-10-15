package uk.ac.reigate.services

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

import uk.ac.reigate.domain.Person
import uk.ac.reigate.domain.Staff
import uk.ac.reigate.domain.lookup.StaffType
import uk.ac.reigate.exceptions.InvalidOperationException
import uk.ac.reigate.repositories.StaffRepository
import uk.ac.reigate.utils.ValidationUtils

@Service
class StaffService implements ICoreDataService<Staff, Integer>{
    
    @Autowired
    StaffRepository staffRepository
    
    /**
     * Default NoArgs constructor
     */
    StaffService() {}
    
    /**
     * Autowired constructor
     * 
     * @param staffRepository
     */
    StaffService(StaffRepository staffRepository) {
        this.staffRepository = staffRepository
    }
    
    /**
     * The findStaff(id) method is the singular form of the service method to retrieve an individual member of staff.
     * 
     * @param id the id of the member of staff to retrieve
     * @return the Staff object identified by the id
     */
    @Override
    @Transactional(readOnly = true)
    Staff findById(Integer id) {
        return staffRepository.findOne(id);
    }
    
    /**
     * The findStaff() method is the pluralised form of the service method to retrieve multiple members of staff.
     * 
     * @return A List of Staff objects
     */
    @Override
    @Transactional(readOnly = true)
    List<Staff> findAll() {
        List<Staff> staff = staffRepository.findByType_NeedInitialsTrue();
        return staff;
    }
    
    /**
     * This service method is used to retrieve a page of Staff from the database. 
     *     
     * @param page The page information to be used for retrieval
     * @return A SearchResult object with the desired Staff objects.
     */
    @Transactional(readOnly = true)
    Page<Staff> findStaffByPage(Pageable page) {
        Page<Staff> results = staffRepository.findAll(page);
        return results
    }
    
    
    /**
     * This service method is used to save a Staff object to the database. If the Staff object does not already exist 
     * in the database then one will be created. If the Staff object already exists in the database then the original 
     * data will be loaded then updated from the data supplied to this method. 
     *  
     * @param id
     * @param person
     * @param type
     * @param onTimetable
     * @param initials
     * @param knownAs
     * @param networkLogin
     * @param startDate
     * @param endDate
     * @param staffRef
     * @param staffDetailsId
     * @return
     */
    @Transactional
    public Staff saveStaff(Integer id, Person person, StaffType type, Boolean onTimetable, String initials, String knownAs, String networkLogin, Date startDate, Date endDate, Integer staffRef, Integer staffDetailsId) {
        ValidationUtils.assertNotBlank(person, "person cannot be blank");
        ValidationUtils.assertNotBlank(type, "type is mandatory");
        
        Staff staff = null;
        if (id != null) {
            staff = findById(id);
            staff.setPerson(person);
            staff.setType(type);
            staff.setOnTimetable(onTimetable);
            staff.setInitials(initials);
            staff.setKnownAs(knownAs);
            staff.setNetworkLogin(networkLogin);
            staff.setStartDate(startDate);
            staff.setEndDate(endDate);
            staff.setStaffRef(staffRef);
            staff.setStaffDetailsId(staffDetailsId);
            
            save(staff);
        } else {
            staff = save(new Staff(person, type, onTimetable, initials, knownAs, networkLogin, startDate, endDate, staffRef, staffDetailsId));
        }
        return staff;
    }
    
    /**
     * This service method is used to save a complete Staff object in the database
     *
     * @param staff the new Staff object to be saved
     * @return the saved version of the Staff object
     */
    @Override
    @Transactional
    public Staff save(Staff staff) {
        return staffRepository.save(staff)
    }
    
    /**
     * Saves a list of Staff objects to the database
     *
     * @param staffs a list of Staffs to be saved to the database
     * @return the list of save Staff objects
     */
    @Transactional
    public List<Staff> saveStaffs(List<Staff> staffs) {
        return staffs.collect { staff -> save( staff ) };
    }
    
    /**
     * This service method is used to retrieve all the Staff objects for those members of staff that are current. This 
     * means that their end date is set to null.
     * 
     * @param id 
     * @return
     */
    @Transactional(readOnly = true)
    List<Staff> findStaffCurrent() {
        return staffRepository.findAllCurrent();
    }
    
    /**
     * This service method is used to retrieve a pageable list of the Staff objects for those members of staff that 
     * are current. This means that their end date is set to null.
     * 
     * @param page The page information for the section of data to retrieve
     * @return 
     */
    @Transactional(readOnly = true)
    Page<Staff> findStaffCurrent(Pageable page) {
        return staffRepository.findAllCurrent(page);
    }
    
    
    /**
     * @param person
     * @return
     */
    Staff findByPerson(Person person) {
        return staffRepository.findByPerson(person);
    }
    
    /**
     * This methods throws an InvalidOperationException when called. QualificationAssessment should not be deleted.
     */
    @Override
    public void delete(Staff obj) {
        throw new InvalidOperationException("Staff should not be deleted")
    }
}
