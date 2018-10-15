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

import uk.ac.reigate.domain.academic.AcademicYear
import uk.ac.reigate.domain.academic.Course
import uk.ac.reigate.domain.academic.CourseGroup
import uk.ac.reigate.domain.academic.Enrolment
import uk.ac.reigate.domain.academic.Student
import uk.ac.reigate.domain.ilr.AimType
import uk.ac.reigate.domain.ilr.CompletionStatus
import uk.ac.reigate.domain.ilr.FundingModel;
import uk.ac.reigate.domain.ilr.Outcome
import uk.ac.reigate.domain.ilr.SourceOfFunding;
import uk.ac.reigate.domain.ilr.WithdrawalReason;
import uk.ac.reigate.domain.lookup.CentralMonitoring
import uk.ac.reigate.dto.EnrolmentDto
import uk.ac.reigate.exceptions.InvalidDataException
import uk.ac.reigate.exceptions.NotFoundException
import uk.ac.reigate.model.SearchResult
import uk.ac.reigate.services.AcademicYearService
import uk.ac.reigate.services.AimTypeService
import uk.ac.reigate.services.CentralMonitoringService
import uk.ac.reigate.services.CompletionStatusService
import uk.ac.reigate.services.CourseGroupService;
import uk.ac.reigate.services.CourseService
import uk.ac.reigate.services.FundingModelService
import uk.ac.reigate.services.OutcomeService
import uk.ac.reigate.services.SourceOfFundingService
import uk.ac.reigate.services.StudentService
import uk.ac.reigate.services.WithdrawalReasonService
import uk.ac.reigate.services.enrolments.EnrolmentService;


@Controller
@RequestMapping(value = "/api/enrolments", produces = [ APPLICATION_JSON_VALUE ])
@Api(value = "/api/enrolments", description = "the enrolments API")
public class EnrolmentsApi {
    
    private static final Logger LOGGER = Logger.getLogger(EnrolmentsApi.class);
    
    @Autowired
    private final EnrolmentService enrolmentService;
    
    @Autowired
    private final AcademicYearService academicYearService;
    
    @Autowired
    private final CourseGroupService courseGroupService;
    
    @Autowired
    private final CourseService courseService;
    
    @Autowired
    private final OutcomeService outcomeService;
    
    @Autowired
    private final FundingModelService fundingModelService;
    
    @Autowired
    private final SourceOfFundingService sourceOfFundingService;
    
    @Autowired
    private final AimTypeService aimTypeService;
    
    @Autowired
    private final WithdrawalReasonService withdrawalReasonService;
    
    @Autowired
    private final StudentService studentService;
    
    @Autowired
    private final CompletionStatusService completionStatusService;
    
    @Autowired
    private final CentralMonitoringService centralMonitoringService;
    /**
     * Default NoArgs constructor
     */
    EnrolmentsApi() {}
    
    /**
     * Autowired constructor
     */
    EnrolmentsApi(EnrolmentService enrolmentService) {
        this.enrolmentService = enrolmentService;
    }
    
    /**
     * The enrolmentsGet method is used to retrieve a full list of all the EnrolmentDto objects
     *
     * @return A ResponseEntity with the corresponding list of EnrolmentDto objects
     * @throws NotFoundException if nothing is found then the the NotFoundException is thrown.
     */
    @ApiOperation(value = "Collection of Enrolment entities", notes = "A GET request to the Enrolments endpoint returns an array of all the enrolments in the system.", response = EnrolmentDto.class, responseContainer = "List")
    @ApiResponses(value = [
        @ApiResponse(code = 200, message = "An array of enrolments")
    ])
    @RequestMapping(value = "", produces = ["application/json"], method = RequestMethod.GET)
    public ResponseEntity<List<EnrolmentDto>> enrolmentsGet(
            @ApiParam(value = "The code of the AcademicYear to retrieve", required = false)
            @RequestParam(value = "year", required = false) Integer yearId
    ) throws NotFoundException {
        LOGGER.info("** EnrolmentsApi - enrolmentsGet");
        List<Enrolment> enrolments = new ArrayList<Enrolment>();
        if (yearId) {
            LOGGER.info("CourseGroupApi Searching for Year : " + yearId);
            enrolments = enrolmentService.findByYearId(yearId)
        }else{
            enrolments  = enrolmentService.findAll()
        }
        return new ResponseEntity<List<EnrolmentDto>>(EnrolmentDto.mapFromEnrolmentsEntity(enrolments), HttpStatus.OK);
    }
    
    /**
     * The enrolmentsPost method is used to create a new instance of a Enrolment from the supplied EnrolmentDto
     *
     * @param enrolment the EnrolmentDto to use to create the new Enrolment object
     * @return A ResponseEntity with the newly created Enrolment object
     * @throws InvalidDataException if there is an issue with the data provided in the RequestBody then an InvalidDataException is thrown
     */
    @ApiOperation(value = "Creates a new Enrolment entity", notes = "A POST request to the Enrolments endpoint with a Enrolment object in the request body will create a new Enrolment entity in the database.", response = EnrolmentDto.class)
    @ApiResponses(value = [
        @ApiResponse(code = 201, message = "Returns a copy of the created Enrolment entity including the enrolmentId that has just been created."),
        @ApiResponse(code = 400, message = "Returns an Error object stating the request body does not match the expected input.")
    ])
    @RequestMapping(value = "", produces = ["application/json"], method = RequestMethod.POST)
    public ResponseEntity<EnrolmentDto> enrolmentsPost(
            @ApiParam(value = "The Enrolment object to be created, without the enrolmentId fields", required = true)
            @RequestBody @Valid EnrolmentDto enrolment)
    throws NotFoundException, InvalidDataException {
        LOGGER.info("** EnrolmentsApi - enrolmentsPOST");
        if (enrolment.enrolmentId != null) {
            throw new InvalidDataException(400, "No ID field should be provided when creating")
        }
        
        Student student
        if(enrolment.studentId != null) {
            student = studentService.findById(enrolment.studentId)
        }
        AcademicYear year
        if(enrolment.yearId != null){
            year = academicYearService.findById(enrolment.yearId)
        }
        Course course
        if(enrolment.courseId != null){
            course = courseService.findById(enrolment.courseId)
        }
        CourseGroup courseGroup
        if(enrolment.courseGroupId != null){
            courseGroup = courseGroupService.findById(enrolment.courseGroupId)
        }
        AimType aimType
        if(enrolment.aimTypeId != null){
            aimType = aimTypeService.findById(enrolment.aimTypeId)
        }
        CompletionStatus completionStatus
        if(enrolment.completionStatusId != null){
            completionStatus = completionStatusService.findById(enrolment.completionStatusId)
        }
        WithdrawalReason withdrawalReason
        if(enrolment.withdrawalReasonId != null){
            withdrawalReason = withdrawalReasonService.findById(enrolment.withdrawalReasonId)
        }
        Outcome outcome
        if(enrolment.outcomeId != null){
            outcome = outcomeService.findById(enrolment.outcomeId)
        }
        CentralMonitoring centralMonitoring
        if(enrolment.centralMonitoringId != null){
            centralMonitoring = centralMonitoringService.findById(enrolment.centralMonitoringId)
        }
        FundingModel fundingModel
        if(enrolment.fundingModelId != null){
            fundingModel = fundingModelService.findById(enrolment.fundingModelId)
        }
        SourceOfFunding sourceOfFunding
        if(enrolment.sourceOfFundingId != null){
            sourceOfFunding = sourceOfFundingService.findById(enrolment.sourceOfFundingId)
        }
        
        Enrolment enrolmentToSave = EnrolmentDto.mapToEnrolmentEntity(enrolment, student, year, course, courseGroup, aimType, completionStatus, withdrawalReason, outcome, centralMonitoring, fundingModel, sourceOfFunding)
        Enrolment enrolmentSaved = enrolmentService.save(enrolmentToSave)
        return new ResponseEntity<EnrolmentDto>(EnrolmentDto.mapFromEnrolmentEntity(enrolmentSaved), HttpStatus.CREATED);
    }
    
    /**
     * The enrolmentsEnrolmentIdGet method is used to retrieve an instance of a EnrolmentDto object as identified by the enrolmentId provided
     *
     * @param enrolmentId the enrolment ID for the Enrolment object retrieve
     * @return A ResponseEntity with the corresponding EnrolmentDto object
     * @throws NotFoundException if nothing is found then the the NotFoundException is thrown.
     */
    @ApiOperation(value = "Retrieves an indivdual instance of a Enrolment identified by the enrolmentId", notes = "A getGET request to the Enrolment instance endpoint will retrieve an instance of a Enrolment entity as identified by the enrolmentId provided in the URI.", response = EnrolmentDto.class)
    @ApiResponses(value = [
        @ApiResponse(code = 200, message = "Returns a copy of the Enrolment as identified by the enrolmentId"),
        @ApiResponse(code = 404, message = "Returns an Error object stating that the resource could not be found. This happens if the specified id cannot be found in the database.")
    ])
    @RequestMapping(value = "/{enrolmentId}", produces = ["application/json"], method = RequestMethod.GET)
    public ResponseEntity<EnrolmentDto> enrolmentsEnrolmentIdGet(
            @ApiParam(value = "The unique ID of the Enrolment to retrieve", required = true)
            @PathVariable("enrolmentId") Integer enrolmentId
    ) throws NotFoundException {
        LOGGER.info("** EnrolmentsApi - enrolmentsEnrolmentIdGet");
        Enrolment enrolment = enrolmentService.findById(enrolmentId);
        if (enrolment == null) {
            throw new NotFoundException();
        }
        return new ResponseEntity<EnrolmentDto>(EnrolmentDto.mapFromEnrolmentEntity(enrolment), HttpStatus.OK);
    }
    
    /**
     * The enrolmentsEnrolmentIdPut is used to update
     *
     * @param enrolmentId the enrolment ID for the Enrolment object to update
     * @param enrolment the new data for the Enrolment object
     * @return the newly updated EnrolmentDto object
     * @throws NotFoundException if nothing is found then the the NotFoundException is thrown.
     * @throws InvalidDataException if there is an issue with the data provided in the RequestBody then an InvalidDataException is thrown
     */
    @ApiOperation(value = "Used to update a Enrolment entity", notes = "A PUT request to the Enrolment instance endpoint with a Enrolment object in the request body will update an existing Enrolment entity in the database.", response = EnrolmentDto.class)
    @ApiResponses(value = [
        @ApiResponse(code = 200, message = "Returns a copy of the newly updated Enrolment object."),
        @ApiResponse(code = 400, message = "Returns an Error object stating the request body does not match the expected input."),
        @ApiResponse(code = 404, message = "Returns an Error object stating that the resource could not be found. This happens if the specified id cannot be found in the database.")
    ])
    @RequestMapping(value = "/{enrolmentId}", produces = ["application/json"], method = RequestMethod.PUT)
    public ResponseEntity<EnrolmentDto> enrolmentsEnrolmentIdPut(
            @ApiParam(value = "The unique ID of the Enrolment to retrieve", required = true)
            @PathVariable("enrolmentId") Integer enrolmentId,
            @ApiParam(value = "The Enrolment object to be created, without the enrolmentId fields", required = true)
            @RequestBody EnrolmentDto enrolment)
    throws NotFoundException, InvalidDataException {
        LOGGER.info("** EnrolmentsApi - enrolmentsPUT");
        if (enrolmentId != enrolment.enrolmentId) {
            throw new InvalidDataException()
        }
        Student student
        if(enrolment.studentId != null) {
            student = studentService.findById(enrolment.studentId)
        }
        AcademicYear year
        if(enrolment.yearId != null){
            year = academicYearService.findById(enrolment.yearId)
        }
        Course course
        if(enrolment.courseId != null){
            course = courseService.findById(enrolment.courseId)
        }
        CourseGroup courseGroup
        if(enrolment.courseGroupId != null){
            courseGroup = courseGroupService.findById(enrolment.courseGroupId)
        }
        AimType aimType
        if(enrolment.aimTypeId != null){
            aimType = aimTypeService.findById(enrolment.aimTypeId)
        }
        CompletionStatus completionStatus
        if(enrolment.completionStatusId != null){
            completionStatus = completionStatusService.findById(enrolment.completionStatusId)
        }
        WithdrawalReason withdrawalReason
        if(enrolment.withdrawalReasonId != null){
            withdrawalReason = withdrawalReasonService.findById(enrolment.withdrawalReasonId)
        }
        Outcome outcome
        if(enrolment.outcomeId != null){
            outcome = outcomeService.findById(enrolment.outcomeId)
        }
        CentralMonitoring centralMonitoring
        if(enrolment.centralMonitoringId != null){
            centralMonitoring = centralMonitoringService.findById(enrolment.centralMonitoringId)
        }
        FundingModel fundingModel
        if(enrolment.fundingModelId != null){
            fundingModel = fundingModelService.findById(enrolment.fundingModelId)
        }
        SourceOfFunding sourceOfFunding
        if(enrolment.sourceOfFundingId != null){
            sourceOfFunding = sourceOfFundingService.findById(enrolment.sourceOfFundingId)
        }
        Enrolment enrolmentToSave = EnrolmentDto.mapToEnrolmentEntity(enrolment, student, year, course, courseGroup, aimType, completionStatus, withdrawalReason, outcome, centralMonitoring, fundingModel, sourceOfFunding)
        Enrolment enrolmentSaved = enrolmentService.save(enrolmentToSave)
        return new ResponseEntity<EnrolmentDto>(EnrolmentDto.mapFromEnrolmentEntity(enrolmentSaved), HttpStatus.OK);
    }
}
