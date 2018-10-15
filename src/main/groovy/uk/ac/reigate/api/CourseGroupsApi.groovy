package uk.ac.reigate.api;

import javax.validation.Valid

import io.swagger.annotations.Api
import io.swagger.annotations.ApiOperation
import io.swagger.annotations.ApiParam
import io.swagger.annotations.ApiResponse
import io.swagger.annotations.ApiResponses

import org.apache.log4j.Logger

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.stereotype.Controller
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestMethod
import org.springframework.web.bind.annotation.RequestParam

import uk.ac.reigate.domain.Staff
import uk.ac.reigate.domain.academic.AcademicYear
import uk.ac.reigate.domain.academic.Course
import uk.ac.reigate.domain.academic.CourseGroup
import uk.ac.reigate.domain.academic.Department
import uk.ac.reigate.domain.academic.Enrolment
import uk.ac.reigate.domain.academic.Timetable
import uk.ac.reigate.domain.lookup.YearGroup
import uk.ac.reigate.dto.CourseGroupDto
import uk.ac.reigate.dto.EnrolmentDto
import uk.ac.reigate.dto.TimetableDto
import uk.ac.reigate.exceptions.InvalidDataException
import uk.ac.reigate.exceptions.NotFoundException
import uk.ac.reigate.repositories.academic.CourseGroupRepository
import uk.ac.reigate.services.AcademicYearService
import uk.ac.reigate.services.CourseGroupService
import uk.ac.reigate.services.CourseService
import uk.ac.reigate.services.DepartmentService
import uk.ac.reigate.services.StaffService
import uk.ac.reigate.services.TimetableService
import uk.ac.reigate.services.YearGroupService
import uk.ac.reigate.services.enrolments.EnrolmentService;

import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE


@Controller
@RequestMapping(value = "/api/courseGroups", produces = [ APPLICATION_JSON_VALUE ])
@Api(value = "/api/courseGroups", description = "the courseGroups API")
public class CourseGroupsApi {
    
    private static final Logger LOGGER = Logger.getLogger(CourseGroupsApi.class);
    
    @Autowired
    private final CourseGroupService courseGroupService;
    
    @Autowired
    private final YearGroupService yearGroupService;
    
    @Autowired
    private final CourseService courseService;
    
    @Autowired
    private final AcademicYearService academicYearService;
    
    @Autowired
    private final DepartmentService departmentService;
    
    @Autowired
    private final StaffService staffService;
    
    @Autowired
    CourseGroupRepository courseGroupRepository;
    
    @Autowired
    private final EnrolmentService enrolmentService;
    
    @Autowired
    private final TimetableService timetableService;
    
    
    /**
     * Default NoArgs constructor
     */
    CourseGroupsApi() {}
    
    /**
     * Autowired constructor
     */
    CourseGroupsApi(AcademicYearService academicYearService, CourseGroupService courseGroupService, CourseGroupRepository courseGroupRepository) {
        this.courseGroupService = courseGroupService;
    }
    
    
    /**
     * The courseGroupsGet method is used to retrieve a full list of all the CourseGroupDto objects
     *
     * @return A ResponseEntity with the corresponding list of CourseGroupDto objects
     * @throws NotFoundException if nothing is found then the the NotFoundException is thrown.
     */
    @ApiOperation(value = "Collection of CourseGroup entities", notes = "A GET request to the CourseGroups endpoint returns an array of all the courseGroups in the system.", response = CourseGroupDto.class, responseContainer = "List")
    @ApiResponses(value = [
        @ApiResponse(code = 200, message = "An array of courseGroups")
    ])
    @RequestMapping(value = "", produces = ["application/json"], method = RequestMethod.GET)
    public ResponseEntity<List<CourseGroupDto>> getCourseGroups(
            @ApiParam(value="Year Id", required=false) @RequestParam(value="yearId", required= false) Integer yearId
    ) throws NotFoundException {
        LOGGER.info("** CourseGroupsApi - getCourseGroups - started");
        List<CourseGroup> courseGroupList = new ArrayList<CourseGroup>();
        if (yearId){
            LOGGER.info("** CourseGroupsApi - getCourseGroups - By Year");
            courseGroupList =  courseGroupService.getCourseGroupsByYearId(yearId)
        } else {
            LOGGER.info("** CourseGroupsApi - getCourseGroups - All");
            courseGroupList = courseGroupService.findAll()
        }
        if (courseGroupList == null){
            throw new NotFoundException()
        }
        List<CourseGroupDto> output = CourseGroupDto.mapFromList(courseGroupList)
        LOGGER.info("** CourseGroupsApi - getCourseGroups - finished");
        return new ResponseEntity<List<CourseGroupDto>>(output, HttpStatus.OK)
    }
    
    /**
     * The courseGroupsCourseGroupIdGet method is used to retrieve an instance of a CourseGroupDto object as identified by the courseGroupId provided. 
     * If a yearId is supplied then the course group will only be returned if it exists in the year specified.
     *
     * @param courseGroupId the courseGroup ID for the CourseGroup object to retrieve
     * @param yearId (optional) the year ID for the CourseGroup object to retrieve
     * @return A ResponseEntity with the corresponding CourseGroupDto object
     * @throws NotFoundException if nothing is found then the the NotFoundException is thrown.
     */
    @ApiOperation(value = "Retrieves an indivdual instance of a CourseGroup identified by the courseGroupId", notes = "A getGET request to the CourseGroup instance endpoint will retrieve an instance of a CourseGroup entity as identified by the courseGroupId provided in the URI.", response = CourseGroupDto.class)
    @ApiResponses(value = [
        @ApiResponse(code = 200, message = "Returns a copy of the CourseGroup as identified by the courseGroupId"),
        @ApiResponse(code = 404, message = "Returns an Error object stating that the resource could not be found. This happens if the specified id cannot be found in the database.")
    ])
    @RequestMapping(value = "/{courseGroupId}", produces = ["application/json"], method = RequestMethod.GET)
    public ResponseEntity<CourseGroupDto> getCourseGroupById(
            @ApiParam(value = "The unique ID of the CourseGroup to retrieve", required = true) @PathVariable("courseGroupId") Integer courseGroupId,
            @ApiParam(value="Academic YearId", required= false) @RequestParam(value="yearId", required=false) Integer yearId
    ) throws NotFoundException {
        LOGGER.info("** CourseGroupsApi - getCourseGroupById: $courseGroupId ");
        CourseGroup courseGroup
        if(yearId != null){
            courseGroup = courseGroupService.getCourseGroupByIdYearId(courseGroupId,yearId)
        } else {
            courseGroup = courseGroupService.findById(courseGroupId)
        }
        if(courseGroup == null){
            throw new NotFoundException()
        }
        return new ResponseEntity<CourseGroupDto>(CourseGroupDto.mapFromCourseGroupEntity(courseGroup), HttpStatus.OK)
    }
    
    @ApiOperation(value = "Retrieves an indivdual instance of a CourseGroup identified by the courseGroupId", notes = "A getGET request to the CourseGroup instance endpoint will retrieve an instance of a CourseGroup entity as identified by the courseGroupId provided in the URI.", response = CourseGroupDto.class)
    @ApiResponses(value = [
        @ApiResponse(code = 200, message = "Returns a copy of the CourseGroup as identified by the courseGroupId"),
        @ApiResponse(code = 404, message = "Returns an Error object stating that the resource could not be found. This happens if the specified id cannot be found in the database.")
    ])
    @RequestMapping(value = "/{courseGroupId}/timetable", produces = ["application/json"], method = RequestMethod.GET)
    public ResponseEntity<CourseGroupDto> getTimetableById(
            @ApiParam(value = "The unique ID of the CourseGroup to retrieve", required = true) @PathVariable("courseGroupId") Integer courseGroupId,
            @ApiParam(value="Academic YearId", required= false) @RequestParam(value="yearId", required=false) Integer yearId
    ) throws NotFoundException {
        LOGGER.info("** CourseGroupsApi - getCourseGroupById: $courseGroupId ");
        AcademicYear academicYear
        if (yearId == null) {
            yearId = academicYearService.getCurrentAcademicYear().id;
        }
        List<Timetable> timetable
        //  List<Timetable> timetable = new ArrayList<Timetable>();
        timetable = timetableService.getCourseGroupsByYearId(yearId, courseGroupId)
        
        return new ResponseEntity<List<TimetableDto>>(TimetableDto.mapFromList(timetable), HttpStatus.OK)
    }
    
    /**
     * The courseGroupsPost method is used to create a new instance of a CourseGroup from the supplied CourseGroupDto
     *
     * @param courseGroup the CourseGroupDto to use to create the new CourseGroup object
     * @return A ResponseEntity with the newly created CourseGroup object
     * @throws InvalidDataException if there is an issue with the data provided in the RequestBody then an InvalidDataException is thrown
     */
    @ApiOperation(value = "Creates a new CourseGroup entity", notes = "A POST request to the CourseGroups endpoint with a CourseGroup object in the request body will create a new CourseGroup entity in the database.", response = CourseGroupDto.class)
    @ApiResponses(value = [
        @ApiResponse(code = 201, message = "Returns a copy of the created CourseGroup entity including the courseGroupId that has just been created."),
        @ApiResponse(code = 400, message = "Returns an Error object stating the request body does not match the expected input.")
    ])
    @RequestMapping(value = "", produces = ["application/json"], method = RequestMethod.POST)
    public ResponseEntity<CourseGroupDto> courseGroupsPost(@ApiParam(value = "The CourseGroup object to be created, without the courseGroupId fields", required = true) @RequestBody @Valid CourseGroupDto courseGroup) throws NotFoundException, InvalidDataException {
        LOGGER.info("** CourseGroupsApi - courseGroupsPOST");
        if (courseGroup.id != null) {
            throw new InvalidDataException(400, "No ID field should be provided when creating")
        }
        YearGroup yearGroup
        if(courseGroup.yearGroupId != null) {
            yearGroup = yearGroupService.findById(courseGroup.yearGroupId)
        }
        Course course
        if(courseGroup.courseId != null){
            course = courseService.findById(courseGroup.courseId)
        }
        AcademicYear year
        if(courseGroup.yearId != null){
            year = academicYearService.findById(courseGroup.yearId)
        }
        Department department
        if(courseGroup.departmentId != null){
            department = departmentService.findById(courseGroup.departmentId)
        }
        Staff courseLeader
        if(courseGroup.courseLeaderId != null){
            courseLeader = staffService.findById(courseGroup.courseLeaderId)
        }
        CourseGroup courseGroupToSave = CourseGroupDto.mapToCourseGroupEntity(courseGroup, yearGroup, course, year, department, courseLeader)
        CourseGroup courseGroupSaved = courseGroupService.save(courseGroupToSave)
        return new ResponseEntity<CourseGroupDto>(CourseGroupDto.mapFromCourseGroupEntity(courseGroupSaved), HttpStatus.CREATED);
    }
    
    
    /**
     * The courseGroupsCourseGroupIdPut is used to update
     *
     * @param courseGroupId the courseGroup ID for the CourseGroup object to update
     * @param courseGroup the new data for the CourseGroup object
     * @return the newly updated CourseGroupDto object
     * @throws NotFoundException if nothing is found then the the NotFoundException is thrown.
     * @throws InvalidDataException if there is an issue with the data provided in the RequestBody then an InvalidDataException is thrown
     */
    @ApiOperation(value = "Used to update a CourseGroup entity", notes = "A PUT request to the CourseGroup instance endpoint with a CourseGroup object in the request body will update an existing CourseGroup entity in the database.", response = CourseGroupDto.class)
    @ApiResponses(value = [
        @ApiResponse(code = 200, message = "Returns a copy of the newly updated CourseGroup object."),
        @ApiResponse(code = 400, message = "Returns an Error object stating the request body does not match the expected input."),
        @ApiResponse(code = 404, message = "Returns an Error object stating that the resource could not be found. This happens if the specified id cannot be found in the database.")
    ])
    @RequestMapping(value = "/{courseGroupId}", produces = ["application/json"], method = RequestMethod.PUT)
    public ResponseEntity<CourseGroupDto> courseGroupsCourseGroupIdPut(
            @ApiParam(value = "The unique ID of the CourseGroup to retrieve", required = true) @PathVariable("courseGroupId") Integer courseGroupId,
            @ApiParam(value = "The CourseGroup object to be created, without the courseGroupId fields", required = true) @RequestBody CourseGroupDto courseGroup) throws NotFoundException, InvalidDataException {
        LOGGER.info("** CourseGroupsApi - courseGroupsPUT");
        if (courseGroupId != courseGroup.id) {
            throw new InvalidDataException()
        }
        YearGroup yearGroup
        if(courseGroup.yearGroupId != null) {
            yearGroup = yearGroupService.findById(courseGroup.yearGroupId)
        }
        Course course
        if(courseGroup.courseId != null){
            course = courseService.findById(courseGroup.courseId)
        }
        AcademicYear year
        if(courseGroup.yearId != null){
            year = academicYearService.findById(courseGroup.yearId)
        }
        Department department
        if(courseGroup.departmentId != null){
            department = departmentService.findById(courseGroup.departmentId)
        }
        Staff courseLeader
        if(courseGroup.courseLeaderId != null){
            courseLeader = staffService.findById(courseGroup.courseLeaderId)
        }
        CourseGroup courseGroupToSave = CourseGroupDto.mapToCourseGroupEntity(courseGroup, yearGroup, course, year, department, courseLeader)
        CourseGroup courseGroupSaved = courseGroupService.save(courseGroupToSave)
        return new ResponseEntity<CourseGroupDto>(CourseGroupDto.mapFromCourseGroupEntity(courseGroupSaved), HttpStatus.OK);
    }
    
    /**
     * The courseGroupsGet method is used to retrieve a list of the CourseGroupDto objects by search By year and spec
     *
     * @return A ResponseEntity with the corresponding list of CourseGroupDto objects
     * @throws NotFoundException if nothing is found then the the NotFoundException is thrown.
     */
    @ApiOperation(value = "Collection of CourseGroup entities By Year and spec", notes = "A GET request to the CourseGroups endpoint returns an array of all the courseGroups in the system.", response = CourseGroupDto.class, responseContainer = "List")
    @ApiResponses(value = [
        @ApiResponse(code = 200, message = "An array of courseGroups")
    ])
    @RequestMapping(value = "/searchByYearAndSpec", produces = ["application/json"], method = RequestMethod.GET)
    public ResponseEntity<List<CourseGroupDto>> courseGroupsGet(
            @ApiParam(value = "The code of the AcademicYear to retrieve", required = false) @RequestParam(value = "year", required = false) String year,
            @ApiParam(value = "The code of the courseGroup spec to retrieve", required = false) @RequestParam(value = "spec", required = true) String spec
    ) throws NotFoundException {
        LOGGER.info("** CourseGroupsApi - courseGroupsGet");
        AcademicYear academicYear
        if (year) {
            LOGGER.info("CourseGroupApi Searching for Year Code: " + year);
            academicYear = academicYearService.findByCode(year)
        }
        if (academicYear == null) {
            LOGGER.info("CourseGroupApi No Year Found or Supplied - Using default")
            academicYear = academicYearService.getCurrentAcademicYear()
        }
        
        List<CourseGroup> courseGroupList = courseGroupService.searchByYearAndSpec(academicYear, spec)
        
        if (courseGroupList.size() != 0) {
            LOGGER.info("II Results found: " + courseGroupList.size())
            List<CourseGroupDto> courseGroupSearchResults = new ArrayList<CourseGroupDto>()
            return new ResponseEntity<List<CourseGroupDto>>(CourseGroupDto.mapFromCourseGroupsEntities(courseGroupList), HttpStatus.OK)
        } else {
            throw new NotFoundException()
        }
    }
    
    
    /**
     * The courseGroupEnrolmentsId method is used to retrieve a list of enrolment by CourseGroup objects.
     *
     * @return A ResponseEntity with the corresponding list of EnrolmentDto objects
     * @throws NotFoundException if nothing is found then the the NotFoundException is thrown.
     */
    @ApiOperation(value = "Retrieves an indivdual instance of a CourseGroup identified by the courseGroupId", notes = "A getGET request to the CourseGroup instance endpoint will retrieve an instance of a CourseGroup entity as identified by the courseGroupId provided in the URI.", response = CourseGroupDto.class)
    @ApiResponses(value = [
        @ApiResponse(code = 200, message = "Returns a copy of the CourseGroup as identified by the courseGroupId"),
        @ApiResponse(code = 404, message = "Returns an Error object stating that the resource could not be found. This happens if the specified id cannot be found in the database.")
    ])
    @RequestMapping(value = "/{courseGroupId}/enrolments", produces = ["application/json"], method = RequestMethod.GET)
    public ResponseEntity<EnrolmentDto> getEnrolments(
            @ApiParam(value = "The unique ID of the CourseGroup to retrieve", required = true)
            @PathVariable("courseGroupId") Integer courseGroupId,
            @ApiParam(value = "The code of the AcademicYear to retrieve", required = false) @RequestParam(value = "year", required = false) String year
    ) throws NotFoundException {
        LOGGER.info("** CourseGroupsApi - courseGroupsCourseGroupIdGetByEnrolment");
        AcademicYear academicYear
        List<Enrolment> enrolmentList
        if (year) {
            LOGGER.info("II Searching for Year Code: " + year);
            academicYear = academicYearService.findByCode(year)
        }
        if (academicYear == null) {
            LOGGER.info("II No Year Found or Supplied - Using default")
            academicYear = academicYearService.getNextAcademicYear()
            enrolmentList = enrolmentService.findByCourseGroup(courseGroupId)
        }else{
            enrolmentList = enrolmentService.findByCourseGroupAndYear(courseGroupId, academicYear)
        }
        
        
        return new ResponseEntity<List<EnrolmentDto>>(EnrolmentDto.mapFromEnrolmentsEntity(enrolmentList), HttpStatus.OK)
    }
    
    /**
     * The CourseGroupsGet method is used to retrieve a list of  the CourseGroupDto objects with academicYear filter
     *
     * @return A ResponseEntity with the corresponding list of CourseGroupDto objects
     * @throws NotFoundException if nothing is found then the the NotFoundException is thrown.
     */
    @ApiOperation(value = "Retrieves the collection of CourseGroup entities By the AcademicYearId", notes = "A GET request to the Courses endpoint returns an array of all the courses in the system.", response = CourseGroupDto.class, responseContainer = "List")
    @ApiResponses(value = [
        @ApiResponse(code = 200, message = "An array of courseGroups")
    ])
    @RequestMapping(value = "/year", produces = ["application/json"], method = RequestMethod.GET)
    public ResponseEntity<List<CourseGroupDto>> courseGroupsGetByYear(
            @RequestParam(value = "year", required = false) String year
    ) throws NotFoundException {
        LOGGER.info("** CourseGroupsApi - coursesGet");
        AcademicYear academicYear
        if (year) {
            LOGGER.info("CourseApi Searching for Year Code: " + year);
            academicYear = academicYearService.findByCode(year)
        }
        if (academicYear == null) {
            LOGGER.info("CourseApi No Year Found or Supplied - Using default")
            academicYear = academicYearService.getCurrentAcademicYear()
        }
        List<CourseGroup> courseGroupList = courseGroupService.searchByYear(academicYear)
        
        if (courseGroupList.size() != 0) {
            LOGGER.info("II Results found: " + courseGroupList.size())
            List<CourseGroupDto> courseGroupSearchResults = new ArrayList<CourseGroupDto>()
            return new ResponseEntity<List<CourseGroupDto>>(CourseGroupDto.mapFromCourseGroupsEntities(courseGroupList), HttpStatus.OK)
        }
    }
}
