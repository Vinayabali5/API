package uk.ac.reigate.api.student

import javax.validation.Valid

import io.swagger.annotations.Api
import io.swagger.annotations.ApiOperation
import io.swagger.annotations.ApiParam
import io.swagger.annotations.ApiResponse
import io.swagger.annotations.ApiResponses

import org.apache.log4j.Logger

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.HttpStatus
import org.springframework.http.MediaType
import org.springframework.http.ResponseEntity
import org.springframework.security.access.annotation.Secured
import org.springframework.stereotype.Controller
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestMethod
import org.springframework.web.bind.annotation.RequestParam

import uk.ac.reigate.domain.Staff
import uk.ac.reigate.domain.academic.AcademicYear
import uk.ac.reigate.domain.academic.Enrolment
import uk.ac.reigate.domain.academic.Student
import uk.ac.reigate.domain.academic.StudentYear
import uk.ac.reigate.domain.lookup.SchoolReportStatus
import uk.ac.reigate.dto.EnrolmentDto
import uk.ac.reigate.dto.StudentAdmissionDto
import uk.ac.reigate.dto.StudentDto
import uk.ac.reigate.dto.StudentYearDto
import uk.ac.reigate.dto.student.StudentWithdrawalDto
import uk.ac.reigate.exceptions.InvalidDataException
import uk.ac.reigate.exceptions.NotFoundException
import uk.ac.reigate.services.AcademicYearService
import uk.ac.reigate.services.DestinationService
import uk.ac.reigate.services.EthnicityService
import uk.ac.reigate.services.LLDDHealthProblemService
import uk.ac.reigate.services.NationalityService
import uk.ac.reigate.services.RestrictedUseIndicatorService
import uk.ac.reigate.services.SchoolReportStatusService
import uk.ac.reigate.services.SchoolService
import uk.ac.reigate.services.StaffService
import uk.ac.reigate.services.StudentRemarkPermissionService
import uk.ac.reigate.services.StudentService
import uk.ac.reigate.services.StudentYearService
import uk.ac.reigate.services.enrolments.EnrolmentService;

@Controller
@RequestMapping(value = "/api/students", produces = [ MediaType.APPLICATION_JSON_VALUE ])
@Api(value = "/api/students", description = "the Students API")
public class StudentApi {
    
    private static final Logger LOGGER = Logger.getLogger(StudentApi.class);
    
    @Autowired
    AcademicYearService academicYearService
    
    @Autowired
    StudentService studentService
    
    @Autowired
    SchoolService schoolService
    
    @Autowired
    StudentYearService studentYearService
    
    @Autowired
    EnrolmentService enrolmentService
    
    @Autowired
    DestinationService destinationService
    
    @Autowired
    EthnicityService ethnicityService
    
    @Autowired
    NationalityService nationalityService
    
    @Autowired
    RestrictedUseIndicatorService restrictedUseIndicatorService
    
    @Autowired
    StudentRemarkPermissionService studentRemarkPermissionService
    
    @Autowired
    LLDDHealthProblemService llddHealthProblemService
    
    @Autowired
    StaffService staffService
    
    @Autowired
    SchoolReportStatusService schoolReportStatusService
    
    /**
     * Default No Args constructor
     */
    StudentApi() {}
    
    /**
     * Default Autowired constructor
     */
    StudentApi(AcademicYearService academicYearService, StudentService studentService, StudentYearService studentYearService) {
        this.studentService = studentService;
    }
    
    @ApiOperation(value = "Retrieves an indivdual instance of a Student identified by the academicYearId", notes = "A GET request to the Student instance endpoint will retrieve an instance of a Student entity as identified by the studentId provided in the URI.", response = StudentDto.class)
    @ApiResponses(value = [
        @ApiResponse(code = 200, message = "Returns a copy of the Student as identified by the studentId"),
        @ApiResponse(code = 404, message = "Returns an Error object stating that the resource could not be found. This happens if the specified id cannot be found in the database.")
    ])
    @RequestMapping(value = "/{studentId}", produces = ["application/json"], method = RequestMethod.GET)
    public ResponseEntity<StudentDto> get(
            @ApiParam(value = "The unique ID of the Student to retrieve", required = true)
            @PathVariable("studentId") Integer studentId,
            @ApiParam(value = "The code of the AcademicYear to retrieve", required = false)
            @RequestParam(value = "year", required = false) String year
    ) throws NotFoundException {
        LOGGER.info("** StudentsApi - studentsStudentIdGet");
        AcademicYear academicYear
        if (year) {
            LOGGER.info("II Searching for Year Code: " + year);
            academicYear = academicYearService.findByCode(year)
        }
        if (academicYear == null) {
            LOGGER.info("II No Year Found or Supplied - Using default")
            academicYear = academicYearService.getCurrentAcademicYear()
        }
        Student student = studentService.findById(studentId)
        
        if (student == null) {
            throw new NotFoundException();
        } else {
            return new ResponseEntity<?>(new StudentDto(student), HttpStatus.OK);
        }
    }
    
    /**
     * @param studentId
     * @return
     * @throws NotFoundException
     */
    @ApiOperation(value = "Retrieves an indivdual instance of a Student identified by the academicYearId", notes = "A GET request to the Student instance endpoint will retrieve an instance of a Student entity as identified by the studentId provided in the URI.", response = StudentAdmissionDto.class)
    @ApiResponses(value = [
        @ApiResponse(code = 200, message = "Returns a copy of the Student as identified by the studentId"),
        @ApiResponse(code = 404, message = "Returns an Error object stating that the resource could not be found. This happens if the specified id cannot be found in the database.")
    ])
    @RequestMapping(value = "/{studentId}/admissions", produces = ["application/json"], method = RequestMethod.GET)
    public ResponseEntity<StudentDto> getAdmissions(
            @ApiParam(value = "The unique ID of the Student to retrieve", required = true)
            @PathVariable("studentId") Integer studentId
    ) throws NotFoundException {
        LOGGER.info("** StudentsApi - studentsStudentIdGetAdmissions");
        Student student = studentService.findById(studentId)
        if (student == null) {
            throw new NotFoundException();
        } else {
            return new ResponseEntity<StudentDto>(new StudentAdmissionDto(student), HttpStatus.OK);
        }
    }
    
    /**
     * @param studentId
     * @return
     * @throws NotFoundException
     */
    @ApiOperation(value = "Retrieves an indivdual instance of a Student identified by the academicYearId", notes = "A GET request to the Student instance endpoint will retrieve an instance of a Student entity as identified by the studentId provided in the URI.", response = StudentAdmissionDto.class)
    @ApiResponses(value = [
        @ApiResponse(code = 200, message = "Returns a copy of the Student as identified by the studentId"),
        @ApiResponse(code = 404, message = "Returns an Error object stating that the resource could not be found. This happens if the specified id cannot be found in the database.")
    ])
    @RequestMapping(value = "/{studentId}/admissions", produces = ["application/json"], method = RequestMethod.PUT)
    public ResponseEntity<StudentDto> putAdmissions(
            @ApiParam(value = "The unique ID of the Student to retrieve", required = true)
            @PathVariable("studentId") Integer studentId,
            @ApiParam(value ="The StudentDto", required= true)
            @RequestBody @Valid StudentAdmissionDto studentAdmissionsDto
    ) throws NotFoundException {
        LOGGER.info("** StudentsApi - studentsStudentIdGetAdmissions");
        Student student = studentService.findById(studentId)
        if (student == null) {
            throw new NotFoundException();
        } else {
            Staff interviwer = studentAdmissionsDto.interviewerId !=null? staffService.findById(studentAdmissionsDto.interviewerId) : null
            SchoolReportStatus schoolReportStatus = studentAdmissionsDto.schoolReportStatusId !=null ?schoolReportStatusService.findById(studentAdmissionsDto.schoolReportStatusId): null
            Student updatedStudent = StudentAdmissionDto.updateStudentAdmissions(student, studentAdmissionsDto, interviwer, schoolReportStatus)
            Student studentSaved = studentService.save(updatedStudent)
            return new ResponseEntity<StudentDto>(new StudentAdmissionDto(studentSaved), HttpStatus.OK);
        }
    }
    /**
     * @param studentId
     * @param yearId
     * @return
     * @throws NotFoundException
     */
    @RequestMapping(value = "/{studentId}/enrolments", produces = ["application/json"], method = RequestMethod.GET)
    public ResponseEntity<EnrolmentDto> getEnrolments (
            @ApiParam(value = "The unique ID of the Student to retrieve", required = true)
            @PathVariable("studentId") Integer studentId,
            @ApiParam(value = "The code of the AcademicYear to retrieve", required = false)
            @RequestParam(value = "yearId", required = false) Integer yearId
    ) throws NotFoundException {
        LOGGER.info("** StudentsApi - studentsStudentIdGet");
        
        List<Enrolment> enrolments = new ArrayList<Enrolment>();
        if(yearId==null){
            enrolments = enrolmentService.findByStudentId(studentId)
        }else{
            enrolments = enrolmentService.findByStudentAndYearId(studentId, yearId);
        }
        return new ResponseEntity<EnrolmentDto>(EnrolmentDto.mapFromEnrolmentListEntities(enrolments),HttpStatus.OK);
    }
    
    @Secured([
        "ROLE_Core Data",
        "ROLE_Office Administration",
        "ROLE_Enrolment Manager",
        "ROLE_Administration",
        "ROLE_Student Services",
        "ROLE_Exams Officer",
        "ROLE_First Aid Coordinator"
    ])
    @RequestMapping(value = "/{studentId}", produces = ["application/json"], method = RequestMethod.PUT)
    public ResponseEntity<StudentDto> put(
            @ApiParam(value = "The unique ID of the Student to retrieve", required = true)
            @PathVariable("studentId") Integer studentId,
            @ApiParam(value = "The Student object should be created, without the studentId fields", required = true)
            @RequestBody StudentDto studentDto
    ) throws NotFoundException {
        if (studentId != studentDto.id) {
            throw new InvalidDataException("The data provided is not valid. The student ID does not match.")
        }
        Student student = studentService.findById(studentDto.id);
        if(student != null) {
            student.uln = studentDto.uln
            student.uci = studentDto.uci
            student.medicalNote = studentDto.medicalNote
            student.resident = studentDto.resident
            student.contactByPost = studentDto.contactByPost
            student.contactByPhone = studentDto.contactByPhone
            student.contactByEmail = studentDto.contactByEmail
            student.monitorable = studentDto.monitorable
            student.school = studentDto.previousSchoolId != null ? schoolService.findById(studentDto.previousSchoolId) : student.school
            student.ethnicity = studentDto.ethnicityId != null ? ethnicityService.findById(studentDto.ethnicityId) : student.ethnicity
            student.nationality = studentDto.nationalityId != null ? nationalityService.findById(studentDto.nationalityId) : student.nationality
            student.restrictedUseIndicator = studentDto.restrictedUseIndicatorId != null ? restrictedUseIndicatorService.findById(studentDto.restrictedUseIndicatorId) : student.restrictedUseIndicator
            student.studentRemarkPermission =  studentDto.studentRemarkPermissionId != null ? studentRemarkPermissionService.findById(studentDto.studentRemarkPermissionId) : null
            student.llddHealthProblem = studentDto.llddHealthProblemId != null ? llddHealthProblemService.findById(studentDto.llddHealthProblemId) : student.llddHealthProblem
            Student studentSaved = studentService.save(student)
            return new ResponseEntity<?>(HttpStatus.OK)
        } else {
            throw new NotFoundException();
        }
    }
    
    @ApiOperation(value = "This method is used to withdraw a student from a specified academic year.", notes = "A GET request to the Student instance endpoint will retrieve an instance of a Student entity as identified by the studentId provided in the URI.", response = StudentYearDto.class)
    @ApiResponses(value = [
        @ApiResponse(code = 200, message = "Returns a copy of the Student as identified by the studentId"),
        @ApiResponse(code = 404, message = "Returns an Error object stating that the resource could not be found. This happens if the specified id cannot be found in the database.")
    ])
    @RequestMapping(value = "/{studentId}/withdraw", produces = ["application/json"], method = RequestMethod.POST)
    public ResponseEntity<?> studentWithdrawal(
            @ApiParam(value = "The unique ID of the Student to withdraw", required = true)
            @PathVariable("studentId") Integer studentId,
            @ApiParam(value = "The StudentWithdrawalDto object to be used to perform the withdrawal of a student.", required = true)
            @RequestBody @Valid StudentWithdrawalDto studentWithdrawal
    ) throws NotFoundException, InvalidDataException {
        LOGGER.info("** StudentsApi - studentWithdrawal");
        if (studentId != studentWithdrawal.studentId) {
            throw new InvalidDataException(400, "The data provided is not valid. The student ID does not match the withdrawal request.");
        }
        
        try {
            StudentYear studentYear = studentYearService.withdrawStudent(studentWithdrawal.studentId, studentWithdrawal.yearId, studentWithdrawal.withdrawalDate, studentWithdrawal.destinationId, studentWithdrawal.collegeEmployer, studentWithdrawal.courseCareer);
            StudentYearDto output = new StudentYearDto(studentYear);
            return new ResponseEntity<?>(output, HttpStatus.OK);
        } catch (Exception e) {
            throw e;
        }
    }
}
