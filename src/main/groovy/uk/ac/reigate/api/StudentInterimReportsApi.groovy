package uk.ac.reigate.api;

import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE

import java.util.List;

import io.swagger.annotations.Api
import io.swagger.annotations.ApiOperation
import io.swagger.annotations.ApiParam
import io.swagger.annotations.ApiResponse
import io.swagger.annotations.ApiResponses

import javax.swing.text.DefaultEditorKit.NextVisualPositionAction
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

import uk.ac.reigate.domain.academic.InterimReport
import uk.ac.reigate.domain.academic.Student
import uk.ac.reigate.domain.academic.StudentInterimReport
import uk.ac.reigate.dto.InterimReportDto
import uk.ac.reigate.dto.StudentInterimReportDto
import uk.ac.reigate.exceptions.InvalidDataException
import uk.ac.reigate.exceptions.NotFoundException
import uk.ac.reigate.model.SearchResult
import uk.ac.reigate.services.InterimReportService
import uk.ac.reigate.services.StudentInterimReportService
import uk.ac.reigate.services.StudentService
import uk.ac.reigate.services.AcademicYearService


@Controller
@RequestMapping(value = "/api", produces = [ APPLICATION_JSON_VALUE ])
@Api(value = "/api", description = "the studentInterimReports API")
public class StudentInterimReportsApi {
    
    private static final Logger LOGGER = Logger.getLogger(StudentInterimReportsApi.class);
    
    @Autowired
    private final StudentInterimReportService studentInterimReportService;
    
    @Autowired
    private final StudentService studentService;
    
    @Autowired
    private final InterimReportService interimReportService;
    
    @Autowired
    private final AcademicYearService academicYearService;
    /**
     * Default NoArgs constructor
     */
    StudentInterimReportsApi() {}
    
    /**
     * Autowired constructor
     */
    StudentInterimReportsApi(StudentInterimReportService studentInterimReportService, InterimReportService interimReportService) {
        this.studentInterimReportService = studentInterimReportService;
    }
    
    
    /**
     * The StudentInterimReportsGet method is used to retrieve a full list of students by the StudentInterimReportDto objects
     *
     * @return A ResponseEntity with the corresponding list of StudentInterimReportDto objects for a given yearId
     * @throws NotFoundException if nothing is found then the the NotFoundException is thrown.
     */
    @ApiOperation(value = "Collection of StudentInterimReport entities", notes = "A GET request to the StudentInterimReports endpoint returns an array of all the StudentInterimReports in the system.", response = StudentInterimReportDto.class, responseContainer = "List")
    @ApiResponses(value = [
        @ApiResponse(code = 200, message = "An array of StudentInterimReports for a yearId")
    ])
    @RequestMapping(value = "/students/{studentId}/interimReports", produces = ["application/json"], method = RequestMethod.GET)
    public ResponseEntity<List<StudentInterimReportDto>> studentInterimReportGet(
            @ApiParam(value = "The unique ID of the Student retrieves the List of interimReports for the selected student", required = true)
            @PathVariable("studentId") Integer studentId,
            @ApiParam(value = "The year ID of the Student Interim Report to retrieve", required = false)
            @RequestParam(value = "yearId", required = false) Integer yearId
    ) throws NotFoundException {
        LOGGER.info("** StudentInterimReportsApi - StudentInterimReportsGet");
        List<StudentInterimReport> studentInterimReportList = new ArrayList<StudentInterimReport>();
        if(yearId){
            studentInterimReportList = studentInterimReportService.findByStudentAndYearId(studentId, yearId)
        }else{
            studentInterimReportList= studentInterimReportService.getByStudent(studentId);
        }
        if (studentInterimReportList == null) {
            throw new NotFoundException();
        }
        
        return new ResponseEntity<List<StudentInterimReportDto>>(StudentInterimReportDto.mapFromStudentInterimReportEntities(studentInterimReportList), HttpStatus.OK);
    }
}

