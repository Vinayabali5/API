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

import uk.ac.reigate.domain.academic.AcademicYear
import uk.ac.reigate.domain.academic.Course
import uk.ac.reigate.domain.academic.CourseGroup
import uk.ac.reigate.domain.academic.Enrolment
import uk.ac.reigate.domain.exam.ExamBoard
import uk.ac.reigate.domain.lookup.Level
import uk.ac.reigate.domain.lookup.Subject
import uk.ac.reigate.dto.CourseDto
import uk.ac.reigate.dto.CourseGroupDto
import uk.ac.reigate.dto.EnrolmentDto
import uk.ac.reigate.exceptions.InvalidDataException
import uk.ac.reigate.exceptions.NotFoundException
import uk.ac.reigate.repositories.academic.CourseRepository
import uk.ac.reigate.services.AcademicYearService
import uk.ac.reigate.services.CourseGroupService
import uk.ac.reigate.services.CourseService
import uk.ac.reigate.services.LevelService
import uk.ac.reigate.services.SubjectService
import uk.ac.reigate.services.enrolments.EnrolmentService;
import uk.ac.reigate.services.exam.ExamBoardService
import uk.ac.reigate.util.exception.InvalidRequestException

import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE


@Controller
@RequestMapping(value = "/api/courses", produces = [ APPLICATION_JSON_VALUE ])
@Api(value = "/api/courses", description = "the courses API")
public class CoursesApi {
    private ExamBoard examBoard
    
    private static final Logger LOGGER = Logger.getLogger(CoursesApi.class);
    
    @Autowired
    private final CourseService courseService;
    
    @Autowired
    private final CourseGroupService courseGroupService;
    
    @Autowired
    private final LevelService levelService;
    
    @Autowired
    private final SubjectService subjectService;
    
    @Autowired
    private final ExamBoardService examBoardService;
    
    @Autowired
    private final AcademicYearService academicYearService;
    
    @Autowired
    CourseRepository courseRepository;
    
    @Autowired
    private final EnrolmentService enrolmentService;
    
    /**
     * Default NoArgs constructor
     */
    CoursesApi() {}
    
    /**
     * Autowired constructor
     */
    CoursesApi(CourseService courseService, AcademicYearService academicYearService, CourseGroupService courseGroupService) {
        this.courseService = courseService;
    }
    
    /**
     * The courseGroupsGet method is used to retrieve a full list of all the CourseDto objects
     *
     * @return A ResponseEntity with the corresponding list of CourseGroupDto objects
     * @throws NotFoundException if nothing is found then the the NotFoundException is thrown.
     */
    @ApiOperation(value = "Collection of Course entities", notes = "A GET request to the Courses endpoint returns an array of all the courses in the system.", response = CourseDto.class, responseContainer = "List")
    @ApiResponses(value = [
        @ApiResponse(code = 200, message = "An array of courses")
    ])
    @RequestMapping(value = "", produces = ["application/json"], method = RequestMethod.GET)
    public ResponseEntity<List<CourseDto>> coursesGet(
            @ApiParam(value = "The Id of the Year to retrieve", required = false) @RequestParam(value = "yearId", required = false) Integer yearId
    ) throws NotFoundException {
        LOGGER.info("** CoursesApi - CoursesByYearIdGet");
        AcademicYear academicYear
        List<Course> courseList = new ArrayList<Course>();
        if(yearId){
            courseList = courseService.findAllCoursesValidInYear(yearId)
        }else{
            courseList = courseService.findAll()
        }
        if(courseList == null){
            throw new NotFoundException()
        }
        LOGGER.info("Course List----" + courseList)
        return new ResponseEntity<List<CourseDto>>(CourseDto.mapFromCoursesEntities(courseList), HttpStatus.OK)
    }
    
    
    
    /**
     * The coursesPost method is used to create a new instance of a Course from the supplied CourseDto
     *
     * @param course the CourseDto to use to create the new Course object
     * @return A ResponseEntity with the newly created Course object
     * @throws InvalidDataException if there is an issue with the data provided in the RequestBody then an InvalidDataException is thrown
     */
    @ApiOperation(value = "Creates a new Course entity", notes = "A POST request to the Courses endpoint with a Course object in the request body will create a new Course entity in the database.", response = CourseDto.class)
    @ApiResponses(value = [
        @ApiResponse(code = 201, message = "Returns a copy of the created Course entity including the courseId that has just been created."),
        @ApiResponse(code = 400, message = "Returns an Error object stating the request body does not match the expected input.")
    ])
    @RequestMapping(value = "", produces = ["application/json"], method = RequestMethod.POST)
    public ResponseEntity<CourseDto> coursesPost(@ApiParam(value = "The Course object to be created, without the courseId fields", required = true) @RequestBody @Valid CourseDto course) throws NotFoundException, InvalidDataException {
        LOGGER.info("** CoursesApi - coursesPOST");
        if (course.id != null) {
            throw new InvalidDataException(400, "No ID field should be provided when creating")
        }
        Level level
        if(course.levelId != null){
            level = levelService.findById(course.levelId)
        }
        Subject subject
        if(course.subjectId != null){
            subject = subjectService.findById(course.subjectId)
        }
        ExamBoard examBoard
        if(course.examBoardId != null){
            examBoard = examBoardService.findById(course.examBoardId)
        }
        AcademicYear validFrom
        if(course.validFromId != null){
            validFrom = academicYearService.findById(course.validFromId)
        }
        AcademicYear validTo
        if(course.validToId != null){
            validTo = academicYearService.findById(course.validToId)
        }
        Course courseToSave = CourseDto.mapToCourseEntity(course, level, subject, examBoard, validFrom, validTo)
        Course courseSaved = courseService.save(courseToSave)
        return new ResponseEntity<CourseDto>(CourseDto.mapFromCourseEntity(courseSaved), HttpStatus.CREATED);
    }
    
    /**
     * The coursesCourseIdGet method is used to retrieve an instance of a CourseDto object as identified by the courseId provided
     *
     * @param courseId the course ID for the Course object to retrieve
     * @param yearId (optional) the year ID for the Course object to retrieve
     * @return A ResponseEntity with the corresponding CourseDto object
     * @throws NotFoundException if nothing is found then the the NotFoundException is thrown.
     */
    @ApiOperation(value = "Retrieves an indivdual instance of a Course identified by the courseId", notes = "A getGET request to the Course instance endpoint will retrieve an instance of a Course entity as identified by the courseId provided in the URI.", response = CourseDto.class)
    @ApiResponses(value = [
        @ApiResponse(code = 200, message = "Returns a copy of the Course as identified by the courseId"),
        @ApiResponse(code = 404, message = "Returns an Error object stating that the resource could not be found. This happens if the specified id cannot be found in the database.")
    ])
    @RequestMapping(value = "/{courseId}", produces = ["application/json"], method = RequestMethod.GET)
    public ResponseEntity<CourseDto> getCourseById(
            @ApiParam(value = "The unique ID of the Course to retrieve", required = true) @PathVariable("courseId") Integer courseId,
            @ApiParam(value = "The code of the AcademicYear to retrieve", required = false) @RequestParam(value = "yearId", required = false) Integer yearId
    ) throws NotFoundException {
        LOGGER.info("** CoursesApi - getCourseById: $courseId");
        Course course
        if (yearId != null) {
            course = courseService.getCourseByIdAndYearId(courseId, yearId)
        } else {
            course = courseService.findById(courseId)
        }
        
        if (course == null) {
            throw new NotFoundException()
        }
        return new ResponseEntity<CourseDto>(CourseDto.mapFromCourseEntity(course), HttpStatus.OK)
    }
    
    /**
     * The coursesCourseIdPut is used to update
     *
     * @param courseId the course ID for the Course object to update
     * @param course the new data for the Course object
     * @return the newly updated CourseDto object
     * @throws NotFoundException if nothing is found then the the NotFoundException is thrown.
     * @throws InvalidDataException if there is an issue with the data provided in the RequestBody then an InvalidDataException is thrown
     */
    @ApiOperation(value = "Used to update a Course entity", notes = "A PUT request to the Course instance endpoint with a Course object in the request body will update an existing Course entity in the database.", response = CourseDto.class)
    @ApiResponses(value = [
        @ApiResponse(code = 200, message = "Returns a copy of the newly updated Course object."),
        @ApiResponse(code = 400, message = "Returns an Error object stating the request body does not match the expected input."),
        @ApiResponse(code = 404, message = "Returns an Error object stating that the resource could not be found. This happens if the specified id cannot be found in the database.")
    ])
    @RequestMapping(value = "/{courseId}", produces = ["application/json"], method = RequestMethod.PUT)
    public ResponseEntity<CourseDto> coursesCourseIdPut(
            @ApiParam(value = "The unique ID of the Course to retrieve", required = true) @PathVariable("courseId") Integer courseId,
            @ApiParam(value = "The Course object to be created, without the courseId fields", required = true) @RequestBody CourseDto course) throws NotFoundException, InvalidDataException {
        LOGGER.info("** CoursesApi - coursePUT");
        if (courseId != course.id) {
            throw new InvalidDataException()
        }
        Level level
        if(course.levelId != null){
            level = levelService.findById(course.levelId)
        }
        Subject subject
        if(course.subjectId != null){
            subject = subjectService.findById(course.subjectId)
        }
        ExamBoard examBoard
        if(course.examBoardId != null){
            examBoard = examBoardService.findById(course.examBoardId)
        }
        AcademicYear validFrom
        if(course.validFromId != null){
            validFrom = academicYearService.findById(course.validFromId)
        }
        AcademicYear validTo
        if(course.validToId != null){
            validTo = academicYearService.findById(course.validToId)
        }
        Course courseToSave = CourseDto.mapToCourseEntity(course, level, subject, examBoard, validFrom, validTo)
        Course courseSaved = courseService.save(courseToSave)
        return new ResponseEntity<CourseDto>(CourseDto.mapFromCourseEntity(courseSaved), HttpStatus.OK);
    }
    
    /**
     * The CoursesGet method is used to retrieve a list of  the CourseDto objects with academicYear filter
     *
     * @return A ResponseEntity with the corresponding list of CourseDto objects
     * @throws NotFoundException if nothing is found then the the NotFoundException is thrown.
     */
    @ApiOperation(value = "Collection of Course entities", notes = "A GET request to the Courses endpoint returns an array of all the courses in the system.", response = CourseDto.class, responseContainer = "List")
    @ApiResponses(value = [
        @ApiResponse(code = 200, message = "An array of courses")
    ])
    @RequestMapping(value = "/search", produces = ["application/json"], method = RequestMethod.GET)
    public ResponseEntity<List<CourseDto>> coursesGet(
            @RequestParam(value = "year", required = false) String year,
            @RequestParam(value = "spec", required = true) String spec
    ) throws NotFoundException {
        LOGGER.info("** CoursesApi - coursesGet");
        AcademicYear academicYear
        if (year) {
            LOGGER.info("CourseApi Searching for Year Code: " + year);
            academicYear = academicYearService.findByCode(year)
        }
        if (academicYear == null) {
            LOGGER.info("CourseApi No Year Found or Supplied - Using default")
            academicYear = academicYearService.getCurrentAcademicYear()
        }
        List<Course> courseList = courseService.searchByYearAndSpec(academicYear, spec)
        
        if (courseList.size() != 0) {
            LOGGER.info("II Results found: " + courseList.size())
            List<CourseDto> courseSearchResults = new ArrayList<CourseDto>()
            return new ResponseEntity<List<CourseDto>>(CourseDto.mapFromCoursesEntities(courseList), HttpStatus.OK)
        }
    }
    
    /**
     * The courseEnrolmentsId method is used to retrieve a list of enrolment by Course objects.
     *
     * @return A ResponseEntity with the corresponding list of EnrolmentDto objects
     * @throws NotFoundException if nothing is found then the the NotFoundException is thrown.
     */
    @ApiOperation(value = "Retrieves an indivdual instance of a Course identified by the courseGroupId", notes = "A getGET request to the Course instance endpoint will retrieve an instance of a Course entity as identified by the courseId provided in the URI.", response = CourseDto.class)
    @ApiResponses(value = [
        @ApiResponse(code = 200, message = "Returns a copy of the Course as identified by the courseId"),
        @ApiResponse(code = 404, message = "Returns an Error object stating that the resource could not be found. This happens if the specified id cannot be found in the database.")
    ])
    @RequestMapping(value = "/{courseId}/enrolments", produces = ["application/json"], method = RequestMethod.GET)
    public ResponseEntity<CourseGroupDto> courseGroupEnrolmentsId(
            @ApiParam(value = "The unique ID of the Course to retrieve", required = true)
            @PathVariable("courseId") Integer courseId,
            @ApiParam(value = "The code of the AcademicYear to retrieve", required = false)
            @RequestParam(value = "year", required = false) String year,
            @ApiParam(value = "The code of the AcademicYear to retrieve", required = false)
            @RequestParam(value = "yearId", required = false) Integer yearId
    ) throws NotFoundException {
        LOGGER.info("** CoursesApi - coursesCourseIdGetByEnrolment");
        AcademicYear academicYear
        List<Enrolment> enrolmentList
        if (year != null && yearId != null) {
            throw new InvalidRequestException("You cannot specify a year code and a yearId in the same request.")
        }
        if (year != null) {
            LOGGER.info("II Searching for Year Code: " + year);
            academicYear = academicYearService.findByCode(year)
            if (academicYear == null) {
                throw new InvalidRequestException("Cannot find a year matching the supplied year code.")
            }
        }
        if (yearId != null) {
            LOGGER.info("II Searching for Year ID: " + yearId);
            academicYear = academicYearService.findById(yearId)
        }
        if (academicYear == null) {
            LOGGER.info("II No Year Found or Supplied - Using default")
            academicYear = academicYearService.getCurrentAcademicYear()
            enrolmentList = enrolmentService.findByCourseId(courseId)
        } else {
            enrolmentList = enrolmentService.findByCourseAndYear(courseId, academicYear)
        }
        
        
        return new ResponseEntity<List<EnrolmentDto>>(EnrolmentDto.mapFromEnrolmentsEntity(enrolmentList), HttpStatus.OK)
    }
    
    /**
     * The coursesGetByCourseGroup method is used to retrieve a list of courseGroup by Course objects.
     *
     * @return A ResponseEntity with the corresponding list of CourseGroupDto objects
     * @throws NotFoundException if nothing is found then the the NotFoundException is thrown.
     */
    @ApiOperation(value = "Retrieves an indivdual instance of a Course identified by the courseGroupId", notes = "A getGET request to the Course instance endpoint will retrieve an instance of a Course entity as identified by the courseId provided in the URI.", response = CourseDto.class)
    @ApiResponses(value = [
        @ApiResponse(code = 200, message = "Returns a copy of the Course as identified by the courseId"),
        @ApiResponse(code = 404, message = "Returns an Error object stating that the resource could not be found. This happens if the specified id cannot be found in the database.")
    ])
    @RequestMapping(value = "/{courseId}/courseGroups", produces = ["application/json"], method = RequestMethod.GET)
    public ResponseEntity<List<CourseGroupDto>> coursesGetByCourseGroup(
            @ApiParam(value = "The unique ID of the Course to retrieve", required = true)
            @PathVariable("courseId") Integer courseId,
            @ApiParam(value = "The Id of the AcademicYear to retrieve", required = false) @RequestParam(value = "yearId", required = false) Integer yearId
    ) throws NotFoundException {
        LOGGER.info("** CoursesApi - coursesGet");
        AcademicYear academicYear
        if (yearId) {
            LOGGER.info("CourseApi Searching for Year Code: " + yearId);
            academicYear = academicYearService.findById(yearId)
        }else{
            academicYear = academicYearService.getCurrentAcademicYear()
        }
        List<CourseGroup> courseByCourseGroup = courseGroupService.searchBycourseAndYear(courseId, academicYear)
        
        return new ResponseEntity<List<CourseGroupDto>>(CourseGroupDto.mapFromCourseGroupsEntities(courseByCourseGroup), HttpStatus.OK)
    }
}
