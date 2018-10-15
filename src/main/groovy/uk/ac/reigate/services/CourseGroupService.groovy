package uk.ac.reigate.services

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.security.access.prepost.PreAuthorize
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

import uk.ac.reigate.domain.Staff
import uk.ac.reigate.domain.academic.AcademicYear
import uk.ac.reigate.domain.academic.Course
import uk.ac.reigate.domain.academic.CourseGroup
import uk.ac.reigate.domain.academic.Department
import uk.ac.reigate.domain.academic.Enrolment
import uk.ac.reigate.domain.lookup.YearGroup
import uk.ac.reigate.exceptions.InvalidOperationException
import uk.ac.reigate.predicates.CourseGroupPredicates
import uk.ac.reigate.repositories.academic.CourseGroupRepository
import uk.ac.reigate.services.enrolments.EnrolmentService
import uk.ac.reigate.utils.ValidationUtils

@Service
class CourseGroupService implements ICoreDataService<CourseGroup, Integer> {
    
    @Autowired
    CourseGroupRepository courseGroupRepository
    
    @Autowired
    EnrolmentService enrolmentService
    
    /**
     * Default NoArgs constructor
     */
    CourseGroupService() {}
    
    /**
     * Autowired Constructor
     *
     * @param courseGroupRepository
     */
    CourseGroupService(CourseGroupRepository courseGroupRepository) {
        this.courseGroupRepository = courseGroupRepository;
    }
    
    /**
     * Find an individual courseGroup using the courseGroups ID fields
     *
     * @param id the ID fields to search for
     * @return the CourseGroup object that matches the ID supplied, or null if not found
     */
    @Override
    @Transactional(readOnly = true)
    CourseGroup findById(Integer id) {
        return courseGroupRepository.findOne(id);
    }
    
    @Transactional(readOnly = true)
    List<CourseGroup> findByStaffAndYear(Integer courseLeaderId, Integer yearId) {
        return courseGroupRepository.findByCourseLeader_IdAndYear_Id(courseLeaderId, yearId);
    }
    
    /**
     * @param year
     * @return
     */
    @PreAuthorize("@securityChecker.checkReader(authentication)")
    public CourseGroup getByAcademicYear(AcademicYear year) {
        return courseGroupRepository.findByYear(year.id)
    }
    
    /**
     * Find all courseGroups
     *
     * @return a List of CourseGroups
     */
    @Override
    @Transactional(readOnly = true)
    List<CourseGroup> findAll() {
        return courseGroupRepository.findAll();
    }
    
    
    /**
     * @param id
     * @param yearGroup
     * @param course
     * @param year
     * @param code
     * @param department
     * @param courseLeader
     * @param displayOnTimetable
     * @param hasRegister
     * @param notes
     * @param spec
     * @param plh
     * @param peeph
     * @return
     */
    @Transactional
    public CourseGroup saveCourseGroup(Integer id, YearGroup yearGroup, Course course, AcademicYear year, String code, Department department, Staff courseLeader, Boolean displayOnTimetable, Boolean hasRegister, String notes, String spec, Integer plh, Integer peeph) {
        ValidationUtils.assertNotBlank(yearGroup, "yearGroup cannot be blank");
        ValidationUtils.assertNotBlank(course, "course cannot be blank");
        ValidationUtils.assertNotBlank(year, "year cannot be blank");
        ValidationUtils.assertNotBlank(department, "department cannot be blank");
        ValidationUtils.assertNotBlank(courseLeader, "courseLeader cannot be blank");
        ValidationUtils.assertNotBlank(displayOnTimetable, "displayOnTimetable cannot be blank");
        ValidationUtils.assertNotBlank(hasRegister, "hasRegister cannot be blank");
        CourseGroup courseGroup = null;
        if (id != null) {
            courseGroup = findById(id);
            courseGroup.setYearGroup(yearGroup);
            courseGroup.setCourse(course);
            courseGroup.setYear(year);
            courseGroup.setCode(code);
            courseGroup.setDepartment(department);
            courseGroup.setCourseLeader(courseLeader);
            courseGroup.setDisplayOnTimetable(displayOnTimetable);
            courseGroup.setHasRegister(hasRegister);
            courseGroup.setNotes(notes);
            courseGroup.setSpec(spec);
            courseGroup.setPlh(plh);
            courseGroup.setPeeph(peeph);
            save(courseGroup);
        } else {
            courseGroup = save(new CourseGroup(yearGroup, course,year, code, department, courseLeader, displayOnTimetable, hasRegister, notes, spec, plh, peeph));
        }
        return courseGroup;
    }
    
    /**
     * This service method is used to save a complete CourseGroup object in the database
     *
     * @param courseGroup the new CourseGroup object to be saved
     * @return the saved version of the CourseGroup object
     */
    @Override
    @Transactional
    public CourseGroup save(CourseGroup courseGroup) {
        return courseGroupRepository.save(courseGroup)
    }
    
    /**
     * Saves a list of CourseGroup objects to the database
     *
     * @param courseGroups a list of CourseGroups to be saved to the database
     * @return the list of save CourseGroup objects
     */
    @Transactional
    public List<CourseGroup> saveCourseGroups(List<CourseGroup> courseGroups) {
        return courseGroups.collect { courseGroup -> save(courseGroup ) };
    }
    
    /** Retrieves CourseGroup by courseId and year
     * @param courseId
     * @param year
     * @return
     */
    @PreAuthorize("@securityChecker.checkReader(authentication)")
    public List<CourseGroup> searchBycourseAndYear(Integer courseId, AcademicYear year){
        List<CourseGroup> courseGroups = courseGroupRepository.findByCourse_IdAndYear(courseId, year)
        return courseGroups
    }
    
    /** Retrieves List of CourseGroup by courseId
     * @param courseId
     * @return
     */
    @PreAuthorize("@securityChecker.checkReader(authentication)")
    public List<CourseGroup> searchByCourse(Course courseId){
        List<CourseGroup> courseGroups = courseGroupRepository.findByCourse(courseId)
        return courseGroups
    }
    
    /**  Retrieves List of CourseGroup by year and spec
     * @param year
     * @param spec
     * @return
     */
    @PreAuthorize("@securityChecker.checkReader(authentication)")
    public List <CourseGroup> searchByYearAndSpec(AcademicYear year, String spec){
        List<CourseGroup> courseGroup = courseGroupRepository.findByYearAndSpecLike(year, spec);
        return courseGroup;
    }
    
    /**  Retrieves List of CourseGroup by yearId , courseId and spec
     * @param yearId
     * @param courseId
     * @param spec
     * @return
     */
    @PreAuthorize("@securityChecker.checkReader(authentication)")
    public List <CourseGroup> searchByYearAndCourseIdAndSpec(Integer yearId, Integer courseId, String spec){
        List<CourseGroup> courseGroup = courseGroupRepository.findByYear_IdAndCourse_IdAndSpec(yearId, courseId, spec);
        return courseGroup;
    }
    
    /**  Retrieves List of CourseGroup by year
     * @param year
     * @return
     */
    @PreAuthorize("@securityChecker.checkReader(authentication)")
    public List<CourseGroup> searchByYear(AcademicYear year) {
        List<CourseGroup> courseGroups = courseGroupRepository.findCourseGroupYear(year.id);
        return courseGroups;
    }
    
    /** Retrieves List of Enrolment by courseGroupId
     * @param courseGroupId
     * @return
     */
    @PreAuthorize("@securityChecker.checkReader(authentication)")
    public List<Enrolment> getCourseGroupEnrolments(Integer courseGroupId) {
        return enrolmentService.findByCourseGroup(courseGroupId);
    }
    
    /** Retrieves List of CourseGroup by yearId
     * @param yearId
     * @return
     */
    @PreAuthorize("@securityChecker.checkReader(authentication)")
    public List<CourseGroup> getCourseGroupsByYearId(Integer yearId){
        List<CourseGroup> courseGroups = courseGroupRepository.findAll(CourseGroupPredicates.courseGroupValidInYear(yearId))
        return courseGroups;
    }
    
    /** Retrieves CourseGroup by yearId and courseGroupId
     * @param courseGroupId
     * @param yearId
     * @return
     */
    @PreAuthorize("@securityChecker.checkReader(authentication)")
    public CourseGroup getCourseGroupByIdYearId(Integer courseGroupId , Integer yearId){
        CourseGroup courseGroup = courseGroupRepository.findCourseGroupByIdYearId(courseGroupId, yearId)
        return courseGroup;
    }
    /**
     * This methods throws an InvalidOperationException when called. CourseGroup should not be deleted.
     */
    @Override
    public void delete(CourseGroup obj) {
        throw new InvalidOperationException("CourseGroup should not be deleted")
    }
}
