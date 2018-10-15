package uk.ac.reigate.api.search

import io.swagger.annotations.ApiImplicitParam
import io.swagger.annotations.ApiImplicitParams
import io.swagger.annotations.ApiOperation
import io.swagger.annotations.ApiParam
import io.swagger.annotations.ApiResponse
import io.swagger.annotations.ApiResponses

import javax.persistence.EntityManager

import org.apache.log4j.Logger
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.validation.BindingResult
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestMethod
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController

import springfox.documentation.annotations.ApiIgnore
import uk.ac.reigate.domain.academic.AcademicYear
import uk.ac.reigate.domain.academic.Student
import uk.ac.reigate.domain.academic.StudentYear
import uk.ac.reigate.dto.ErrorMessageDto
import uk.ac.reigate.dto.StudentDto
import uk.ac.reigate.dto.search.StudentEmailDto
import uk.ac.reigate.dto.search.StudentSearchDto
import uk.ac.reigate.exceptions.InvalidDataException
import uk.ac.reigate.exceptions.NoSearchResultsFoundException
import uk.ac.reigate.model.search.StudentCourseGroupSearchParams
import uk.ac.reigate.repositories.search.StudentSearchRepository
import uk.ac.reigate.services.AcademicYearService
import uk.ac.reigate.services.StudentYearService
import uk.ac.reigate.services.search.StudentCourseGroupSearchService
import uk.ac.reigate.services.search.StudentSearchService

@RestController
@RequestMapping('/api')
class StudentSearchApi {
    
    private static final Logger LOGGER = Logger.getLogger(StudentSearchApi.class);
    
    @Autowired
    EntityManager entityManager
    
    @Autowired
    AcademicYearService academicYearService
    
    @Autowired
    StudentSearchService studentSearchService
    
    @Autowired
    StudentSearchRepository studentSearchRepository
    
    @Autowired
    StudentCourseGroupSearchService studentCourseGroupSearchService
    
    @Autowired
    StudentYearService studentYearService
    
    @ApiOperation(value = "Performs a search for students based on given parapmeters.", notes = "This endpoint is used to perform a search for students for a given set of parameters.", response = StudentSearchDto.class, responseContainer = "List")
    @ApiResponses(value = [
        @ApiResponse(code = 200, message = "An array of StudentSearch objects")
    ])
    @RequestMapping(value = [
        '/studentSearch',
        '/search/student'
    ], produces = ["application/json"], method = RequestMethod.GET)
    ResponseEntity<List<StudentSearchDto>> searchStudent(
            @ApiParam(value = "The yearId of the AcademicYear to search", required = false)
            @RequestParam(value = "yearId", required = false) Integer yearId,
            @ApiParam(value = "The student's surname to search for", required = false)
            @RequestParam(value = "surname", required = false) String surname,
            @ApiParam(value = "The student's first name to search for", required = false)
            @RequestParam(value = "firstName", required = false) String firstName,
            @ApiParam(value = "The student's ID to search for", required = false)
            @RequestParam(value = "studentId", required = false) Integer studentId,
            @ApiParam(value = "The student's candidate number to search for", required = false)
            @RequestParam(value = "candidateNo", required = false) Integer candidateNo,
            @ApiParam(value = "The student type mask to apply to the search", required = false)
            @RequestParam(value = "studentTypeMask", required = false) String studentTypeMask,
            @ApiParam(value = "The tutor group mask to  apply to the search", required = false)
            @RequestParam(value = "tutorGroupMask", required = false) String tutorGroupMask,
            @ApiParam(value = "The if true then only return current students, if false return left students, if omitted return all students", required = false)
            @RequestParam(value = "current", required = false) Boolean current
            
    ) throws NoSearchResultsFoundException {
        LOGGER.info("** StudentSearchApi - searchStudent");
        AcademicYear academicYear
        if (yearId) {
            LOGGER.info("II Searching for Year ID: " + yearId);
            academicYear = academicYearService.findById(yearId)
        }
        if (academicYear == null) {
            LOGGER.info("II No Year Found or Supplied - Using default")
            academicYear = academicYearService.getCurrentAcademicYear()
        }
        if (surname == null) {
            LOGGER.info("II No surname supplied");
        }
        if (firstName == null) {
            LOGGER.info("II No first name supplied");
        }
        if (studentId == null) {
            LOGGER.info("II No student ID supplied");
        }
        if (current == null) {
            LOGGER.info("II Searching for all student");
        } else {
            LOGGER.info("II Searching for current student only");
        }
        if (surname == null && firstName == null && studentId == null && candidateNo == null && current == null) {
            throw new InvalidDataException(500, "No search parameters provided")
        }
        List<Student> studentList =  studentSearchService.search(academicYear, studentId, surname, firstName, candidateNo, studentTypeMask, tutorGroupMask, current)
        if (studentList.size() != 0) {
            LOGGER.info("II Results found: " + studentList.size())
            List<StudentSearchDto> studentSearchResults = new ArrayList<StudentSearchDto>()
            studentList.each { it ->
                StudentYear studentYear = studentYearService.findByStudentAndYear(it, academicYear)
                studentSearchResults.add(new StudentSearchDto(it, studentYear))
            }
            return new ResponseEntity<List<StudentSearchDto>>(studentSearchResults.sort { it.studentName }, HttpStatus.OK)
        } else {
            LOGGER.info("WW No results found")
            return new ResponseEntity(new ErrorMessageDto("No results found", "The search parameters vielded no results"), HttpStatus.NOT_FOUND)
        }
    }
    
    @ApiOperation(value = "Retrieves an indivdual instance of a Student identified by the academicYearId", notes = "A GET request to the Student instance endpoint will retrieve an instance of a Student entity as identified by the studentId provided in the URI.", response = StudentDto.class)
    @ApiImplicitParams([
        @ApiImplicitParam(name = "yearId", dataType = "integer", paramType = "query", value = "The academic year id to use for the search. If not provided the current year is used."),
        @ApiImplicitParam(name = "yearCode", dataType = "string", paramType = "query", value = "The academic year code to use for the search."),
        @ApiImplicitParam(name = "date", dataType = "date", paramType = "query", value = "The reference date to use for the search."),
        @ApiImplicitParam(name = "name", dataType = "string", paramType = "query", value = "The name to use for the search. This will search multiple combinations of the name fields."),
        @ApiImplicitParam(name = "typeMask", dataType = "string", paramType = "query", value = "The student type mask to use for the search."),
        @ApiImplicitParam(name = "tutorGroupMask", dataType = "string", paramType = "query", value = "The tutor group mask to use for the search."),
        @ApiImplicitParam(name = "courseGroupMask", dataType = "string", paramType = "query", value = "The course group mask to use for the search.")
    ])
    @RequestMapping(value = '/search/studentEmail', produces = ["application/json"], method = RequestMethod.GET)
    ResponseEntity<List<StudentSearchDto>> searchStudentToEmail(@ApiIgnore StudentCourseGroupSearchParams searchParams, BindingResult results) throws NoSearchResultsFoundException {
        if (results != null) {
        }
        if (searchParams != null) {
            def studentList = studentCourseGroupSearchService.search(searchParams)
            return new  ResponseEntity<List<StudentEmailDto>>(studentList, HttpStatus.OK);
        } else {
            throw new InvalidDataException(500, "No search parameters provided")
        }
    }
}
