package uk.ac.reigate.api;

import javax.validation.Valid

import io.swagger.annotations.Api
import io.swagger.annotations.ApiImplicitParam
import io.swagger.annotations.ApiImplicitParams
import io.swagger.annotations.ApiOperation
import io.swagger.annotations.ApiParam
import io.swagger.annotations.ApiResponse
import io.swagger.annotations.ApiResponses

import org.apache.log4j.Logger

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.stereotype.Controller
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestMethod
import org.springframework.web.bind.annotation.RequestParam

import uk.ac.reigate.domain.Person
import uk.ac.reigate.domain.Staff
import uk.ac.reigate.domain.academic.AcademicYear
import uk.ac.reigate.domain.academic.CourseGroup
import uk.ac.reigate.domain.academic.Timetable
import uk.ac.reigate.domain.lookup.StaffType
import uk.ac.reigate.dto.CourseGroupDto
import uk.ac.reigate.dto.StaffDto
import uk.ac.reigate.dto.TimetableDto
import uk.ac.reigate.exceptions.InvalidDataException
import uk.ac.reigate.exceptions.NotFoundException
import uk.ac.reigate.services.AcademicYearService
import uk.ac.reigate.services.CourseGroupService
import uk.ac.reigate.services.PersonService
import uk.ac.reigate.services.StaffService
import uk.ac.reigate.services.StaffTypeService
import uk.ac.reigate.services.TimetableService

import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE

import springfox.documentation.annotations.ApiIgnore


@Controller
@RequestMapping(value = "/api/staff", produces = [ APPLICATION_JSON_VALUE ])
@Api(value = "/api/staff", description = "The Staff Resource API")
public class StaffApi {
    
    private static final Logger LOGGER = Logger.getLogger(StaffApi.class);
    
    @Autowired
    private final StaffService staffService;
    
    @Autowired
    private final PersonService personService;
    
    @Autowired
    private final StaffTypeService staffTypeService;
    
    @Autowired
    private final CourseGroupService courseGroupService;
    
    @Autowired
    private final AcademicYearService academicYearService;
    
    @Autowired
    private final TimetableService timetableService;
    
    
    /**
     * Default No Args constructor
     */
    StaffApi() {}
    
    /**
     * Default Autowired constructor
     */
    StaffApi(StaffService staffService) {
        this.staffService = staffService;
    }
    
    /**
     * This method is used to retrieve a collection of Current Staff entities of the StaffDto objects
     * 
     * @Page results page you want to retrieve
     * @size A number of records per page
     * @sort Sorting criteria in the format i.e ascending/ descending
     * @return A ResponseEntity with the corresponding list of StaffDto objects
     * @throws NotFoundException if nothing is found then the the NotFoundException is thrown.
     */
    @ApiOperation(value = "Collection of Current Staff entities", notes = "A GET request to the Staffs endpoint returns an array of all the current staffs in the system.", response = StaffDto.class, responseContainer = "SearchResult")
    @ApiImplicitParams([
        @ApiImplicitParam(name = "page", dataType = "integer", paramType = "query", value = "Results page you want to retrieve (0..N)"),
        @ApiImplicitParam(name = "size", dataType = "integer", paramType = "query", value = "Number of records per page."),
        @ApiImplicitParam(name = "sort", allowMultiple = true, dataType = "string", paramType = "query", value = "Sorting criteria in the format: property(,asc|desc). Default sort order is ascending. Multiple sort criteria are supported.")
    ])
    @ApiResponses(value = [
        @ApiResponse(code = 200, message = "An array of staffs")
    ])
    @RequestMapping(value = "", produces = ["application/json"], method = RequestMethod.GET)
    public ResponseEntity<List<StaffDto>> getStaffPageable(
            @ApiParam(value = "A boolean flag used to switch between loading current staff and all staff.", required = false)
            @RequestParam(value = "current", required = false, defaultValue = "1") boolean current,
            @ApiParam(value = "Pageable parameters (page, size, sort).", required = false)
            @ApiIgnore Pageable pageRequest
    ) throws NotFoundException {
        LOGGER.info("** StaffsApi - getStaffPageable");
        Page<Staff> staff;
        if (current) {
            staff = staffService.findStaffCurrent(pageRequest);
        } else {
            staff = staffService.findStaffByPage(pageRequest);
        }
        return new ResponseEntity<List<StaffDto>>(StaffDto.mapFromList(staff.getContent()), HttpStatus.OK);
    }
    
    /**
     * This method is used to retrieve a full list of all the StaffDto objects
     *
     * @return A ResponseEntity with the corresponding list of StaffDto objects
     * @throws NotFoundException if nothing is found then the the NotFoundException is thrown.
     */
    @ApiOperation(value = "Collection of Current Staff entities", notes = "A GET request to the Staffs endpoint returns an array of all the current staffs in the system.", response = StaffDto.class, responseContainer = "List")
    @ApiResponses(value = [
        @ApiResponse(code = 200, message = "An array of staffs")
    ])
    @RequestMapping(value = "/current", produces = ["application/json"], method = RequestMethod.GET)
    public ResponseEntity<List<StaffDto>> getCurrentStaff() throws NotFoundException {
        LOGGER.info("** StaffsApi - staffsGet (Current)");
        List<Staff> staff = staffService.findStaffCurrent();
        return new ResponseEntity<List<StaffDto>>(StaffDto.mapFromList(staff), HttpStatus.OK);
    }
    
    /**
     * This method is used to retrieve a list of all the StaffDto objects
     *
     * @return A ResponseEntity with the corresponding list of StaffDto objects
     * @throws NotFoundException if nothing is found then the the NotFoundException is thrown.
     */
    @ApiOperation(value = "Collection of All Staff entities", notes = "A GET request to the Staffs endpoint returns an array of all the staffs in the system.", response = StaffDto.class, responseContainer = "List")
    @ApiResponses(value = [
        @ApiResponse(code = 200, message = "An array of staffs")
    ])
    @RequestMapping(value = "/all", produces = ["application/json"], method = RequestMethod.GET)
    public ResponseEntity<List<StaffDto>> getAllStaff() throws NotFoundException {
        LOGGER.info("** StaffsApi - getAllStaff");
        List<Staff> staff = staffService.findAll();
        return new ResponseEntity<List<StaffDto>>(StaffDto.mapFromList(staff), HttpStatus.OK);
    }
    
    /**
     * This method is used to create a new instance of a Staff from the supplied StaffDto
     *
     * @param staff the StaffDto to use to create the new Staff object
     * @return A ResponseEntity with the newly created Staff object
     * @throws InvalidDataException if there is an issue with the data provided in the RequestBody then an InvalidDataException is thrown
     */
    @ApiOperation(value = "Creates a new Staff entity", notes = "A POST request to the Staffs endpoint with a Staff object in the request body will create a new Staff entity in the database.", response = StaffDto.class)
    @ApiResponses(value = [
        @ApiResponse(code = 201, message = "Returns a copy of the created Staff entity including the staffId that has just been created."),
        @ApiResponse(code = 400, message = "Returns an Error object stating the request body does not match the expected input.")
    ])
    @RequestMapping(value = "", produces = ["application/json"], method = RequestMethod.POST)
    public ResponseEntity<StaffDto> createStaff(@ApiParam(value = "The Staff object to be created, without the staffId fields", required = true) @RequestBody @Valid StaffDto staff) throws NotFoundException, InvalidDataException {
        LOGGER.info("** StaffsApi - createStaff");
        if (staff.id != null) {
            throw new InvalidDataException(400, "No ID field should be provided when creating")
        }
        Person person
        if(staff.personId != null){
            person = personService.findById(staff.personId)
        }
        StaffType type
        if(staff.typeId != null){
            type = staffTypeService.findById(staff.typeId)
        }
        Staff staffToSave = StaffDto.mapToStaffEntity(staff, person, type)
        Staff staffSaved = staffService.save(staffToSave)
        return new ResponseEntity<StaffDto>(StaffDto.mapFromEntity(staffSaved), HttpStatus.CREATED);
    }
    
    /**
     * This method is used to retrieve an instance of a StaffDto object as identified by the staffId provided
     *
     * @param staffId the staff ID for the Staff object retrieve
     * @return A ResponseEntity with the corresponding StaffDto object
     * @throws NotFoundException if nothing is found then the the NotFoundException is thrown.
     */
    @ApiOperation(value = "Retrieves an indivdual instance of a Staff identified by the staffId", notes = "A getGET request to the Staff instance endpoint will retrieve an instance of a Staff entity as identified by the staffId provided in the URI.", response = StaffDto.class)
    @ApiResponses(value = [
        @ApiResponse(code = 200, message = "Returns a copy of the Staff as identified by the staffId"),
        @ApiResponse(code = 404, message = "Returns an Error object stating that the resource could not be found. This happens if the specified id cannot be found in the database.")
    ])
    @RequestMapping(value = "/{staffId}", produces = ["application/json"], method = RequestMethod.GET)
    public ResponseEntity<StaffDto> getStaffById(@ApiParam(value = "The unique ID of the Staff to retrieve", required = true) @PathVariable("staffId") Integer staffId) throws NotFoundException {
        LOGGER.info("** StaffsApi - getStaffById");
        Staff staff = staffService.findById(staffId);
        if (staff == null) {
            throw new NotFoundException();
        }
        return new ResponseEntity<StaffDto>(StaffDto.mapFromEntity(staff), HttpStatus.OK);
    }
    
    /**
     * This method is used to update
     *
     * @param staffId the staff ID for the Staff object to update
     * @param staff the new data for the Staff object
     * @return the newly updated StaffDto object
     * @throws NotFoundException if nothing is found then the the NotFoundException is thrown.
     * @throws InvalidDataException if there is an issue with the data provided in the RequestBody then an InvalidDataException is thrown
     */
    @ApiOperation(value = "Used to update a Staff entity", notes = "A PUT request to the Staff instance endpoint with a Staff object in the request body will update an existing Staff entity in the database.", response = StaffDto.class)
    @ApiResponses(value = [
        @ApiResponse(code = 200, message = "Returns a copy of the newly updated Staff object."),
        @ApiResponse(code = 400, message = "Returns an Error object stating the request body does not match the expected input."),
        @ApiResponse(code = 404, message = "Returns an Error object stating that the resource could not be found. This happens if the specified id cannot be found in the database.")
    ])
    @RequestMapping(value = "/{staffId}", produces = ["application/json"], method = RequestMethod.PUT)
    public ResponseEntity<StaffDto> updateStaff(
            @ApiParam(value = "The unique ID of the Staff to retrieve", required = true) @PathVariable("staffId") Integer staffId,
            @ApiParam(value = "The Staff object to be created, without the staffId fields", required = true) @RequestBody StaffDto staff) throws NotFoundException, InvalidDataException {
        LOGGER.info("** StaffsApi - updateStaff");
        if (staffId != staff.id) {
            throw new InvalidDataException()
        }
        Person person
        if(staff.personId != null){
            person = personService.findById(staff.personId)
        }
        StaffType type
        if(staff.typeId != null){
            type = staffTypeService.findById(staff.typeId)
        }
        Staff staffToSave = StaffDto.mapToStaffEntity(staff, person, type)
        Staff staffSaved = staffService.save(staffToSave)
        return new ResponseEntity<StaffDto>(StaffDto.mapFromEntity(staffSaved), HttpStatus.OK);
    }
    
    /**
     * This method is used to retrieve an instance of a StaffDto object as identified by the staffId provided
     *
     * @param staffId the staff ID for the Staff object retrieve
     * @return A ResponseEntity with the corresponding StaffDto object
     * @throws NotFoundException if nothing is found then the the NotFoundException is thrown.
     */
    @ApiOperation(value = "Retrieves an indivdual instance of a Staff identified by the staffId", notes = "A getGET request to the Staff instance endpoint will retrieve an instance of a Staff entity as identified by the staffId provided in the URI.", response = StaffDto.class)
    @ApiResponses(value = [
        @ApiResponse(code = 200, message = "Returns a copy of the Staff as identified by the staffId"),
        @ApiResponse(code = 404, message = "Returns an Error object stating that the resource could not be found. This happens if the specified id cannot be found in the database.")
    ])
    @RequestMapping(value = "/{staffId}/course-groups", produces = ["application/json"], method = RequestMethod.GET)
    public ResponseEntity<CourseGroupDto> getStaffCourseGroup(
            @ApiParam(value = "The unique ID of the Staff to retrieve", required = true)
            @PathVariable("staffId") Integer staffId,
            @ApiParam(value = "The ID of the year year to retrieve the retgister for", required = false)
            @RequestParam(name = "yearId", required = false) Integer yearId) throws NotFoundException {
        LOGGER.info("** StaffsApi - getStaffCourseGroup");
        AcademicYear academicYear
        List<CourseGroup> courseGroup = new ArrayList<CourseGroup>();
        if (yearId == null) {
            yearId = academicYearService.getCurrentAcademicYear().id;
        }
        courseGroup = courseGroupService.findByStaffAndYear(staffId, yearId)
        
        return new ResponseEntity<List<CourseGroupDto>>(CourseGroupDto.mapFromCourseGroupsEntities(courseGroup), HttpStatus.OK)
    }
    
    /**
     * This method is used to retrieve a full list of all the StaffDto objects
     *
     * @return A ResponseEntity with the corresponding list of StaffDto objects
     * @throws NotFoundException if nothing is found then the the NotFoundException is thrown.
     */
    @ApiOperation(value = "Collection of Current Staff entities", notes = "A GET request to the Staffs endpoint returns an array of all the current staffs in the system.", response = StaffDto.class, responseContainer = "List")
    @ApiResponses(value = [
        @ApiResponse(code = 200, message = "An array of staffs")
    ])
    @RequestMapping(value = "/{staffId}/timetable", produces = ["application/json"], method = RequestMethod.GET)
    public ResponseEntity<List<TimetableDto>> getStaffTimetable(
            @PathVariable Integer staffId,
            @RequestParam(name = "yearId", required = false) Integer yearId,
            @RequestParam(name = "current", required = false) Boolean current
    ) throws NotFoundException {
        LOGGER.info("** StaffsApi - getStaffTimetable");
        AcademicYear year
        if (yearId != null) {
            year = academicYearService.findById(yearId)
        } else {
            year = academicYearService.getCurrentAcademicYear()
        }
        if (current == null) {
            current = true
        }
        Staff staff = staffService.findById(staffId);
        List<Timetable> timetable
        if (current) {
            timetable = timetableService.getStaffTimetableCurrent(year, staff)
        } else {
            timetable = timetableService.getStaffTimetable(year, staff)
        }
        return new ResponseEntity<List<TimetableDto>>(TimetableDto.mapFromList(timetable), HttpStatus.OK);
    }
}
