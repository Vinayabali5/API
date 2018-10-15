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
import uk.ac.reigate.domain.register.StudentOverallAttendance
import uk.ac.reigate.dto.StudentOverallAttendanceDto
import uk.ac.reigate.exceptions.InvalidDataException
import uk.ac.reigate.exceptions.NotFoundException
import uk.ac.reigate.model.SearchResult
import uk.ac.reigate.services.StudentOverallAttendanceService


@Controller
@RequestMapping(value = "/api", produces = [ APPLICATION_JSON_VALUE ])
@Api(value = "/api", description = "the studentOverallAttendances API")
public class StudentOverallAttendancesApi {
    
    private static final Logger LOGGER = Logger.getLogger(StudentOverallAttendancesApi.class);
    
    @Autowired
    private final StudentOverallAttendanceService studentOverallAttendanceService;
    
    /**
     * Default NoArgs constructor
     */
    StudentOverallAttendancesApi() {}
    
    /**
     * Autowired constructor
     */
    StudentOverallAttendancesApi(StudentOverallAttendanceService studentOverallAttendanceService) {
        this.studentOverallAttendanceService = studentOverallAttendanceService;
    }
    
    /**
     * The studentOverallAttendancesGet method is used to retrieve a full list of all the StudentOverallAttendanceDto objects
     *
     * @return A ResponseEntity with the corresponding list of StudentOverallAttendanceDto objects
     * @throws NotFoundException if nothing is found then the the NotFoundException is thrown.
     */
    @ApiOperation(value = "Collection of StudentOverallAttendance entities", notes = "A GET request to the StudentOverallAttendances endpoint returns an array of all the studentOverallAttendances in the system.", response = StudentOverallAttendanceDto.class, responseContainer = "List")
    @ApiResponses(value = [
        @ApiResponse(code = 200, message = "An array of studentOverallAttendances")
    ])
    @RequestMapping(value = "/studentOverallAttendances", produces = ["application/json"], method = RequestMethod.GET)
    public ResponseEntity<List<StudentOverallAttendanceDto>> studentOverallAttendancesGet() throws NotFoundException {
        LOGGER.info("** StudentOverallAttendancesApi - studentOverallAttendancesGet");
        List<StudentOverallAttendance> studentOverallAttendances = studentOverallAttendanceService.findAll();
        return new ResponseEntity<List<StudentOverallAttendanceDto>>(StudentOverallAttendanceDto.mapFromStudentOverallAttendancesEntities(studentOverallAttendances), HttpStatus.OK);
    }
    
    
    /**
     * The studentOverallAttendancesStudentOverallAttendanceIdGet method is used to retrieve an instance of a StudentOverallAttendanceDto object as identified by the studentOverallAttendanceId provided
     *
     * @param studentOverallAttendanceId the studentOverallAttendance ID for the StudentOverallAttendance object retrieve
     * @return A ResponseEntity with the corresponding StudentOverallAttendanceDto object
     * @throws NotFoundException if nothing is found then the the NotFoundException is thrown.
     */
    @ApiOperation(value = "Retrieves an indivdual instance of a StudentOverallAttendance identified by the studentOverallAttendanceId", notes = "A getGET request to the StudentOverallAttendance instance endpoint will retrieve an instance of a StudentOverallAttendance entity as identified by the studentOverallAttendanceId provided in the URI.", response = StudentOverallAttendanceDto.class)
    @ApiResponses(value = [
        @ApiResponse(code = 200, message = "Returns a copy of the StudentOverallAttendance as identified by the studentOverallAttendanceId"),
        @ApiResponse(code = 404, message = "Returns an Error object stating that the resource could not be found. This happens if the specified id cannot be found in the database.")
    ])
    @RequestMapping(value = "/students/{studentId}/attendance", produces = ["application/json"], method = RequestMethod.GET)
    public ResponseEntity<StudentOverallAttendanceDto> studentOverallAttendancesStudentOverallAttendanceIdGet(
            @ApiParam(value = "The unique ID of the StudentOverallAttendance to retrieve", required = true)
            @PathVariable("studentId") Integer studentId,
            @ApiParam(value ="Academic Year Id", required = false)
            @RequestParam(value = "yearId", required = false) Integer yearId)
    throws NotFoundException {
        StudentOverallAttendance studentOverallAttendance = studentOverallAttendanceService.findStudentOverallAttendanceByYear(studentId, yearId);
        LOGGER.info("** StudentOverallAttendancesApi - studentOverallAttendancesStudentOverallAttendanceIdGet");
        if (studentOverallAttendance == null) {
            throw new NotFoundException();
        }
        return new ResponseEntity<StudentOverallAttendanceDto>(StudentOverallAttendanceDto.mapFromStudentOverallAttendanceEntity(studentOverallAttendance), HttpStatus.OK);
    }
}
