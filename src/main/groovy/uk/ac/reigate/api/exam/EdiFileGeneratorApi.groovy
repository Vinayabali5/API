package uk.ac.reigate.api.exam;

import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE
import io.swagger.annotations.Api
import io.swagger.annotations.ApiOperation
import io.swagger.annotations.ApiParam
import io.swagger.annotations.ApiResponse
import io.swagger.annotations.ApiResponses

import org.apache.log4j.Logger
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.jdbc.UncategorizedSQLException;
import org.springframework.security.access.annotation.Secured
import org.springframework.stereotype.Controller
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestMethod
import org.springframework.web.bind.annotation.RequestParam

import uk.ac.reigate.domain.exam.StudentOptionEntry
import uk.ac.reigate.dto.exam.StudentOptionEntryDto
import uk.ac.reigate.exceptions.EdiCreationException;
import uk.ac.reigate.exceptions.InvalidDataException
import uk.ac.reigate.exceptions.NotFoundException
import uk.ac.reigate.services.exam.EdiDataGeneratorService;


@Controller
@RequestMapping(value = "/api/ediDataGenerator", produces = [ APPLICATION_JSON_VALUE ])
@Api(value = "/api/ediDataGenerator", description = "the edi data generator API")
public class EdiDataGeneratorApi {
    
    private static final Logger LOGGER = Logger.getLogger(EdiDataGeneratorApi.class);
    
    @Autowired
    EdiDataGeneratorService ediDataGeneratorService;
    
    /**
     * Default NoArgs constructor
     */
    EdiDataGeneratorApi() {}
    
    /**
     * Autowired constructor
     */
    EdiDataGeneratorApi(EdiDataGeneratorService ediDataGeneratorService) {
        this.ediDataGeneratorService = ediDataGeneratorService;
    }
    
    /**
     * The ediGenerateEntries method is used to generate the exam entries required for students
     *
     * @return ResponseEntity<StudentOptionEntry>
     * @throws InvalidDataException if there is an issue with the data provided in the RequestBody then an InvalidDataException is thrown
     */
    @Secured([
        "ROLE_Core Data",
        "ROLE_Office Administration",
        "ROLE_Enrolment Manager",
        "ROLE_Administration",
        "ROLE_Student Services",
        "ROLE_Exams Officer"
    ])
    @ApiOperation(value = "Generate an the student option entries", notes = "A POST request to the EdiFileGenerator endpoint will generate the student option entries.")
    @ApiResponses(value = [
        @ApiResponse(code = 201, message = "Returns a copy of the created student option entries."),
        @ApiResponse(code = 400, message = "Returns an Error object.")
    ])
    @RequestMapping(value = "studentOptionEntries", produces = ["application/json"], method = RequestMethod.POST)
    public ResponseEntity<StudentOptionEntryDto> ediGenerateEntries() throws NotFoundException, InvalidDataException {
        LOGGER.info("** EdiStatusTypesApi - ediGenerateEntries()");
        
        List<StudentOptionEntry> toReturn = ediDataGeneratorService.generateEntries();
        return new ResponseEntity<List<StudentOptionEntryDto>>(StudentOptionEntryDto.mapFromStudentOptionEntriesEntities(toReturn), HttpStatus.OK);
    }
    
    /**
     * The ediFileGeneratorGet method is used to generate an EDI file for submission to the exams boards
     *
     * @param examBoardId - int - the ID of the exam board for which to generate the EDI file
     * @param year - String - the year of which to generate the EDI file
     * @param seies - String - the series of which to generate the EDI file
     * @return HttpStatus.OK
     * @throws InvalidDataException if there is an issue with the data provided in the RequestBody then an InvalidDataException is thrown
     */
    @Secured([
        "ROLE_Core Data",
        "ROLE_Exams Officer"
    ])
    @ApiOperation(value = "Generate an EDI file",
    notes = "A POST request to the EdiFileGenerator endpoint with an examBoardId, year and series will generate an EDI file.")
    @ApiResponses(value = [
        @ApiResponse(code = 200, message = "Returns an Http OK status."),
        @ApiResponse(code = 500, message = "Returns an Error object.")
    ])
    @RequestMapping(value = "ediFile", produces = ["application/json"], method = RequestMethod.POST)
    public ResponseEntity<String> generateEdiFile (
            @ApiParam(value = "exam year", name = "examYear", required = true) @RequestParam(value = "examYear", required = true) String examYear,
            @ApiParam(value = "exam series", name = "examSeries", required = true) @RequestParam(value = "examSeries", required = true) String examSeries,
            @ApiParam(value = "exam Board Id", name = "examBoardId", required = true) @RequestParam(value = "examBoardId", required = true) Integer examBoardId
    ) {
        LOGGER.info("** EdiStatusTypesApi - ediStatusTypesGet");
        try {
            ediDataGeneratorService.generateEdiFile(examYear, examSeries, examBoardId);
        } catch (UncategorizedSQLException ex) {
            return new ResponseEntity<String>(HttpStatus.BAD_REQUEST, ex.message);
        } catch (Exception ex) {
            return new ResponseEntity<?>(HttpStatus.BAD_REQUEST);
        }
        return new ResponseEntity<?>(HttpStatus.OK);
    }
}
