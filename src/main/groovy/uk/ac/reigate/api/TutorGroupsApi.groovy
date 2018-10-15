package uk.ac.reigate.api;

import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE
import io.swagger.annotations.Api
import io.swagger.annotations.ApiOperation
import io.swagger.annotations.ApiParam
import io.swagger.annotations.ApiResponse
import io.swagger.annotations.ApiResponses

import javax.validation.Valid

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

import uk.ac.reigate.domain.Room
import uk.ac.reigate.domain.Staff
import uk.ac.reigate.domain.academic.AcademicYear
import uk.ac.reigate.domain.academic.Faculty
import uk.ac.reigate.domain.academic.Student
import uk.ac.reigate.domain.academic.StudentYear
import uk.ac.reigate.domain.lookup.StudentRemarkPermission
import uk.ac.reigate.domain.lookup.TutorGroup
import uk.ac.reigate.dto.StudentDto
import uk.ac.reigate.dto.StudentYearDto
import uk.ac.reigate.dto.TutorGroupDto
import uk.ac.reigate.dto.TutorGroupRemarkPermissionDto
import uk.ac.reigate.exceptions.InvalidDataException
import uk.ac.reigate.exceptions.NotFoundException
import uk.ac.reigate.model.SearchResult
import uk.ac.reigate.services.AcademicYearService
import uk.ac.reigate.services.FacultyService
import uk.ac.reigate.services.RoomService
import uk.ac.reigate.services.StaffService
import uk.ac.reigate.services.StudentRemarkPermissionService
import uk.ac.reigate.services.StudentService
import uk.ac.reigate.services.StudentYearService
import uk.ac.reigate.services.TutorGroupService


@Controller
@RequestMapping(value = "/api/tutorGroups", produces = [ APPLICATION_JSON_VALUE ])
@Api(value = "/api/tutorGroups", description = "the tutorGroups API")
public class TutorGroupsApi {
    
    private static final Logger LOGGER = Logger.getLogger(TutorGroupsApi.class);
    
    @Autowired
    private final TutorGroupService tutorGroupService;
    
    @Autowired
    private final FacultyService facultyService;
    
    @Autowired
    private final StaffService staffService;
    
    @Autowired
    private final RoomService roomService;
    
    @Autowired
    StudentService studentService
    
    @Autowired
    StudentYearService studentYearService
    
    @Autowired
    AcademicYearService academicYearService
    
    @Autowired
    StudentRemarkPermissionService studentRemarkPermissionService
    
    
    /**
     * Default NoArgs constructor
     */
    TutorGroupsApi() {}
    
    /**
     * Autowired constructor
     */
    TutorGroupsApi(TutorGroupService tutorGroupService) {
        this.tutorGroupService = tutorGroupService;
    }
    
    /**
     * The tutorGroupsGet method is used to retrieve a full list of all the TutorGroupDto objects
     *
     * @return A ResponseEntity with the corresponding list of TutorGroupDto objects
     * @throws NotFoundException if nothing is found then the the NotFoundException is thrown.
     */
    @ApiOperation(value = "Collection of TutorGroup entities", notes = "A GET request to the TutorGroups endpoint returns an array of all the tutorGroups in the system.", response = TutorGroupDto.class, responseContainer = "List")
    @ApiResponses(value = [
        @ApiResponse(code = 200, message = "An array of tutorGroups")
    ])
    @RequestMapping(value = "", produces = ["application/json"], method = RequestMethod.GET)
    public ResponseEntity<List<TutorGroupDto>> tutorGroupsGet(
            @ApiParam(value = "Valid TutorGroup or not", required = false)
            @RequestParam(value= "inUse", required=false) Boolean inUse) throws NotFoundException {
        LOGGER.info("** TutorGroupsApi - tutorGroupsGet");
        List<TutorGroup> tutorGroups;
        if(inUse!=null){
            tutorGroups = tutorGroupService.getValidTutorGroups(inUse);
        }else{
            tutorGroups = tutorGroupService.findAll();
        }
        return new ResponseEntity<List<TutorGroupDto>>(TutorGroupDto.mapFromTutorGroupsEntities(tutorGroups), HttpStatus.OK);
    }
    
    
    
    /**
     * The tutorGroupRemarkGet method will return an array of student remark permissions objects which will have the students name, candidate number, tutor group description and remark permission ID
     *
     * @param tutorGroupId the tutorGroupId ID for the StudentYear object retrieve
     * @param year the year code for the AcademicYear object retrieve
     * @return A ResponseEntity with the corresponding list of TutorGroupRemarkPermissionDto objects
     * @throws NotFoundException if nothing is found then the the NotFoundException is thrown.
     */
    @ApiOperation(value = "Retrieves an indivdual instance of a TutorGroup identified by the tutorGroupId", notes = "A getGET request to the TutorGroup instance endpoint will retrieve an instance of a TutorGroup entity as identified by the tutorGroupId provided in the URI.", response = TutorGroupRemarkPermissionDto.class, responseContainer = "List")
    @ApiResponses(value = [
        @ApiResponse(code = 200, message = "Returns a copy of the TutorGroup as identified by the tutorGroupId"),
        @ApiResponse(code = 404, message = "Returns an Error object stating that the resource could not be found. This happens if the specified id cannot be found in the database.")
    ])
    @RequestMapping(value = "/{tutorGroupId}/remarkPermissions", produces = ["application/json"], method = RequestMethod.GET)
    public ResponseEntity<List<TutorGroupRemarkPermissionDto>> tutorGroupRemarkGet(
            @ApiParam(value = "The unique Id of the tutor Group", required = true)
            @PathVariable("tutorGroupId") Integer tutorGroupId,
            @ApiParam(value = "The code of the AcademicYear to retrieve", required = false)
            @RequestParam(value = "year", required = false) String year
    ) throws NotFoundException {
        LOGGER.info("remark permission testing")
        AcademicYear academicYear
        if (year) {
            LOGGER.info("II Searching for Year Code: " + year);
            academicYear = academicYearService.findByCode(year)
        }
        if (academicYear == null) {
            LOGGER.info("II No Year Found or Supplied - Using default")
            academicYear = academicYearService.getCurrentAcademicYear()
        }
        List<StudentYear> tutors = studentYearService.findByTutorGroupAndYear(tutorGroupId, academicYear)
        return new ResponseEntity<List<TutorGroupRemarkPermissionDto>>(TutorGroupRemarkPermissionDto.mapFromStudentsEntities(tutors), HttpStatus.OK);
    }
    
    /**
     * The tutorGroupsRemarkPermissionPost method is used to update the studentRemarkPermission from the supplied TutorGroupRemarkPermissionDto
     *
     * @param tutorGroupId the tutorGroupId ID for the StudentYear object retrieve
     * @param tutorGroupRemarkPermission the TutorGroupRemarkPermissionDto is used to update the Student object.
     * @return A ResponseEntity with the HttpStatus.OK status.
     */
    @ApiOperation(value = "Creates a new TutorGroup entity", notes = "A POST request to the TutorGroups endpoint with a Student object in the request body will create a new Student entity in the database.", response = TutorGroupRemarkPermissionDto.class)
    @ApiResponses(value = [
        @ApiResponse(code = 201, message = "Returns a copy of the updated Student entity including the studentRemarkPermissionId that has just been created."),
        @ApiResponse(code = 400, message = "Returns an Error object stating the request body does not match the expected input.")
    ])
    @RequestMapping(value = "/{tutorGroupId}/remarkPermissions", produces = ["application/json"], method = RequestMethod.POST)
    public ResponseEntity<TutorGroupRemarkPermissionDto> tutorGroupsRemarkPermissionPost(
            @ApiParam(value = "The unique Id of the tutor Group", required = true)
            @PathVariable("tutorGroupId") Integer tutorGroupId,
            @ApiParam(value = "The TutorGroup object to be created, without the tutorGroupId fields", required = true)
            @RequestBody @Valid List<TutorGroupRemarkPermissionDto> tutorGroups) throws NotFoundException, InvalidDataException {
        LOGGER.info("** TutorGroupsApi - tutorGroupsPOST");
        tutorGroups.each { TutorGroupRemarkPermissionDto it ->
            Student student = studentService.findById(it.studentId);
            StudentRemarkPermission remarkPermission = it.studentRemarkPermissionId != null ? studentRemarkPermissionService.findById(it.studentRemarkPermissionId) : null
            studentService.updateStudentRemarkPermission(student, remarkPermission)
        }
        
        return new ResponseEntity<?>(HttpStatus.OK)
    }
    
    /**
     * The tutorGroupsPost method is used to create a new instance of a TutorGroup from the supplied TutorGroupDto
     *
     * @param tutorGroup the TutorGroupDto to use to create the new TutorGroup object
     * @return A ResponseEntity with the newly created TutorGroup object
     * @throws InvalidDataException if there is an issue with the data provided in the RequestBody then an InvalidDataException is thrown
     */
    @ApiOperation(value = "Creates a new TutorGroup entity", notes = "A POST request to the TutorGroups endpoint with a TutorGroup object in the request body will create a new TutorGroup entity in the database.", response = TutorGroupDto.class)
    @ApiResponses(value = [
        @ApiResponse(code = 201, message = "Returns a copy of the created TutorGroup entity including the tutorGroupId that has just been created."),
        @ApiResponse(code = 400, message = "Returns an Error object stating the request body does not match the expected input.")
    ])
    @RequestMapping(value = "", produces = ["application/json"], method = RequestMethod.POST)
    public ResponseEntity<TutorGroupDto> tutorGroupsPost(@ApiParam(value = "The TutorGroup object to be created, without the tutorGroupId fields", required = true)
            @RequestBody @Valid TutorGroupDto tutorGroup) throws NotFoundException, InvalidDataException {
        LOGGER.info("** TutorGroupsApi - tutorGroupsPOST");
        if (tutorGroup.id != null) {
            throw new InvalidDataException(400, "No ID field should be provided when creating")
        }
        Faculty faculty
        if(tutorGroup.facultyId != null){
            faculty = facultyService.findById(tutorGroup.facultyId)
        }
        Staff tutor
        if(tutorGroup.tutorId != null) {
            tutor = staffService.findById(tutorGroup.tutorId)
        }
        Staff seniorTutor
        if(tutorGroup.seniorTutorId != null) {
            seniorTutor = staffService.findById(tutorGroup.seniorTutorId)
        }
        Room room
        if(tutorGroup.roomId != null){
            room = roomService.findById(tutorGroup.roomId)
        }
        TutorGroup tutorGroupToSave = TutorGroupDto.mapToTutorGroupEntity(tutorGroup, faculty, tutor, seniorTutor, room)
        TutorGroup tutorGroupSaved = tutorGroupService.save(tutorGroupToSave)
        return new ResponseEntity<TutorGroupDto>(TutorGroupDto.mapFromTutorGroupEntity(tutorGroupSaved), HttpStatus.CREATED);
    }
    
    /**
     * The tutorGroupsTutorGroupIdGet method is used to retrieve an instance of a TutorGroupDto object as identified by the tutorGroupId provided
     *
     * @param tutorGroupId the tutorGroup ID for the TutorGroup object retrieve
     * @return A ResponseEntity with the corresponding TutorGroupDto object
     * @throws NotFoundException if nothing is found then the the NotFoundException is thrown.
     */
    @ApiOperation(value = "Retrieves an indivdual instance of a TutorGroup identified by the tutorGroupId", notes = "A getGET request to the TutorGroup instance endpoint will retrieve an instance of a TutorGroup entity as identified by the tutorGroupId provided in the URI.", response = TutorGroupDto.class)
    @ApiResponses(value = [
        @ApiResponse(code = 200, message = "Returns a copy of the TutorGroup as identified by the tutorGroupId"),
        @ApiResponse(code = 404, message = "Returns an Error object stating that the resource could not be found. This happens if the specified id cannot be found in the database.")
    ])
    @RequestMapping(value = "/{tutorGroupId}", produces = ["application/json"], method = RequestMethod.GET)
    public ResponseEntity<TutorGroupDto> tutorGroupsTutorGroupIdGet(@ApiParam(value = "The unique ID of the TutorGroup to retrieve", required = true) @PathVariable("tutorGroupId") Integer tutorGroupId) throws NotFoundException {
        LOGGER.info("** TutorGroupsApi - tutorGroupsTutorGroupIdGet");
        TutorGroup tutorGroup = tutorGroupService.findById(tutorGroupId);
        if (tutorGroup == null) {
            throw new NotFoundException();
        }
        return new ResponseEntity<TutorGroupDto>(TutorGroupDto.mapFromTutorGroupEntity(tutorGroup), HttpStatus.OK);
    }
    
    @ApiOperation(value = "Retrieves an indivdual instance of a TutorGroup identified by the tutorGroupId", notes = "A getGET request to the TutorGroup instance endpoint will retrieve an instance of a TutorGroup entity as identified by the tutorGroupId provided in the URI.", response = TutorGroupDto.class)
    @ApiResponses(value = [
        @ApiResponse(code = 200, message = "Returns a copy of the TutorGroup as identified by the tutorGroupId"),
        @ApiResponse(code = 404, message = "Returns an Error object stating that the resource could not be found. This happens if the specified id cannot be found in the database.")
    ])
    @RequestMapping(value = "/{tutorGroupId}/students", produces = ["application/json"], method = RequestMethod.GET)
    public ResponseEntity<TutorGroupDto> studentsTutorGroupIdGet(
            @ApiParam(value = "The unique ID of the TutorGroup to retrieve", required = true)
            @PathVariable("tutorGroupId") Integer tutorGroupId,
            @ApiParam(value="Academic YearId", required= false) @RequestParam(value="yearId", required=false) Integer yearId) throws NotFoundException {
        LOGGER.info("** TutorGroupsApi - tutorGroupsTutorGroupIdGet");
        AcademicYear academicYear
        if (yearId == null) {
            yearId = academicYearService.getCurrentAcademicYear().id;
        }
        List<StudentYear> student = studentYearService.findByTutorGroupAndYearId(tutorGroupId, yearId)
        return new ResponseEntity<List<StudentYearDto>>(StudentYearDto.mapFromStudentYearEntities(student), HttpStatus.OK);
    }
    
    
    
    
    /**
     * The tutorGroupsTutorGroupIdPut is used to update
     *
     * @param tutorGroupId the tutorGroup ID for the TutorGroup object to update
     * @param tutorGroup the new data for the TutorGroup object
     * @return the newly updated TutorGroupDto object
     * @throws NotFoundException if nothing is found then the the NotFoundException is thrown.
     * @throws InvalidDataException if there is an issue with the data provided in the RequestBody then an InvalidDataException is thrown
     */
    @ApiOperation(value = "Used to update a TutorGroup entity", notes = "A PUT request to the TutorGroup instance endpoint with a TutorGroup object in the request body will update an existing TutorGroup entity in the database.", response = TutorGroupDto.class)
    @ApiResponses(value = [
        @ApiResponse(code = 200, message = "Returns a copy of the newly updated TutorGroup object."),
        @ApiResponse(code = 400, message = "Returns an Error object stating the request body does not match the expected input."),
        @ApiResponse(code = 404, message = "Returns an Error object stating that the resource could not be found. This happens if the specified id cannot be found in the database.")
    ])
    @RequestMapping(value = "/{tutorGroupId}", produces = ["application/json"], method = RequestMethod.PUT)
    public ResponseEntity<TutorGroupDto> tutorGroupsTutorGroupIdPut(
            @ApiParam(value = "The unique ID of the TutorGroup to retrieve", required = true) @PathVariable("tutorGroupId") Integer tutorGroupId,
            @ApiParam(value = "The TutorGroup object to be created, without the tutorGroupId fields", required = true) @RequestBody TutorGroupDto tutorGroup) throws NotFoundException, InvalidDataException {
        LOGGER.info("** TutorGroupsApi - tutorGroupsPUT");
        if (tutorGroupId != tutorGroup.id) {
            throw new InvalidDataException()
        }
        Faculty faculty
        if(tutorGroup.facultyId != null){
            faculty = facultyService.findById(tutorGroup.facultyId)
        }
        Staff tutor
        if(tutorGroup.tutorId != null) {
            tutor = staffService.findById(tutorGroup.tutorId)
        }
        Staff seniorTutor
        if(tutorGroup.seniorTutorId != null) {
            seniorTutor = staffService.findById(tutorGroup.seniorTutorId)
        }
        Room room
        if(tutorGroup.roomId != null){
            room = roomService.findById(tutorGroup.roomId)
        }
        TutorGroup tutorGroupToSave = TutorGroupDto.mapToTutorGroupEntity(tutorGroup, faculty, tutor, seniorTutor, room)
        TutorGroup tutorGroupSaved = tutorGroupService.save(tutorGroupToSave)
        return new ResponseEntity<TutorGroupDto>(TutorGroupDto.mapFromTutorGroupEntity(tutorGroupSaved), HttpStatus.OK);
    }
}
