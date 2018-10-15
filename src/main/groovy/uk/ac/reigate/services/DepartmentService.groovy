package uk.ac.reigate.services

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

import uk.ac.reigate.domain.Staff
import uk.ac.reigate.domain.academic.Department
import uk.ac.reigate.domain.academic.Faculty
import uk.ac.reigate.exceptions.InvalidOperationException
import uk.ac.reigate.repositories.StaffRepository
import uk.ac.reigate.repositories.academic.DepartmentRepository
import uk.ac.reigate.repositories.academic.FacultyRepository
import uk.ac.reigate.utils.ValidationUtils

@Service
class DepartmentService implements ICoreDataService<Department, Integer> {
    
    @Autowired
    DepartmentRepository departmentRepository
    
    @Autowired
    FacultyRepository facultyRepository
    
    @Autowired
    StaffRepository staffRepository
    
    /**
     * Default NoArgs constructor
     */
    DepartmentService() {}
    
    /**
     * Autowired Constructor
     *
     * @param departmentRepository
     */
    DepartmentService(DepartmentRepository departmentRepository) {
        this.departmentRepository = departmentRepository;
    }
    
    /**
     * Find an individual department using the departments ID fields
     *
     * @param id the ID fields to search for
     * @return the Department object that matches the ID supplied, or null if not found
     */
    @Override
    @Transactional(readOnly = true)
    Department findById(Integer id) {
        return departmentRepository.findOne(id);
    }
    
    /**
     * Find all departments
     *
     * @return a SearchResult set with the list of Departments
     */
    @Override
    @Transactional(readOnly = true)
    List<Department> findAll() {
        return departmentRepository.findAll();
    }
    
    /**
     * @param id
     * @param name
     * @param description
     * @param faculty
     * @param hod
     * @param hod2
     * @return
     */
    @Transactional
    public Department saveDepartment(Integer id, String name, String description, Faculty faculty, Staff hod, Staff hod2, Boolean academic) {
        
        ValidationUtils.assertNotBlank(name, "name cannot be blank");
        ValidationUtils.assertNotNull(description, "description is mandatory");
        ValidationUtils.assertNotBlank(faculty, "faculty cannot be blank");
        ValidationUtils.assertNotBlank(hod, "hod cannot be blank");
        
        Department department = null;
        
        if (id != null) {
            department = findById(id);
            
            department.setName(name);
            department.setDescription(description);
            department.setFaculty(faculty);
            department.setHod(hod);
            department.setHod2(hod2);
            department.setAcademic(academic);
            
            save(department);
        } else {
            department = save(new Department(name, description, faculty, hod, hod2, academic));
        }
        
        return department;
    }
    
    /**
     * This service method is used to save a complete Department object in the database
     *
     * @param department the new Department object to be saved
     * @return the saved version of the Department object
     */
    @Override
    @Transactional
    public Department save(Department department) {
        return departmentRepository.save(department)
    }
    
    /**
     * Saves a list of Department objects to the database
     *
     * @param departments a list of Departments to be saved to the database
     * @return the list of save Department objects
     */
    @Transactional
    public List<Department> saveDepartments(List<Department> departments) {
        return departments.collect { department -> save(department) };
    }
    
    /**
     * This methods throws an InvalidOperationException when called. Department should not be deleted.
     */
    @Override
    public void delete(Department obj) {
        throw new InvalidOperationException("Department should not be deleted")
    }
    
    public List<Department> findByfacultyId(Integer facultyId){
        return departmentRepository.findByFaculty_Id(facultyId)
    }
}
