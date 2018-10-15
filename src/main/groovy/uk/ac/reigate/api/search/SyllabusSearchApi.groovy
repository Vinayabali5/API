package uk.ac.reigate.api.search

import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE

import java.util.List
import java.util.Optional

import org.apache.log4j.Logger
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.ResponseEntity
import org.springframework.stereotype.Controller
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestMethod
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController
import org.springframework.http.HttpStatus

import io.swagger.annotations.Api
import io.swagger.annotations.ApiOperation
import io.swagger.annotations.ApiParam
import io.swagger.annotations.ApiResponse
import io.swagger.annotations.ApiResponses
import uk.ac.reigate.domain.exam.Syllabus
import uk.ac.reigate.dto.exam.SyllabusDto
import uk.ac.reigate.dto.search.StudentSearchDto
import uk.ac.reigate.exceptions.NotFoundException
import uk.ac.reigate.services.exam.SyllabusService

@RestController
@RequestMapping(value="/api")
@Api(value = "/api", description = "The Exam basedata Syllabus Searching API")
class SyllabiSearchApi {
    
    private final static Logger LOGGER = Logger.getLogger(SyllabiSearchApi.class);
    
    @Autowired
    SyllabusService syllabusService
    
    @ApiOperation(value = "Performs a search for syllabi based on given parameters.", response = SyllabusDto.class, responseContainer = "List")
    @ApiResponses(value = [
        @ApiResponse(code = 200, message = "An array of Syllabi objects")
    ])
    @RequestMapping(value="/search/exam-syllabus", produces = ["application/json"], method = RequestMethod.GET)
    public ResponseEntity<List<SyllabusDto>> searchSyllabi(
            @ApiParam(value = "exam Board Id", name = "examBoardId", required = false)
            @RequestParam(value = "examBoardId", required = false) Optional<Integer> examBoardId,
            @ApiParam(value = "syllabus Code", name = "syllabusCode", required = false)
            @RequestParam(value = "syllabusCode", required = false) Optional<String> syllabusCode,
            @ApiParam(value = "exam Year", name = "examYear", required = false)
            @RequestParam(value = "examYear", required = false) Optional<String> examYear,
            @ApiParam(value = "exam Series", name = "examSeries", required = false)
            @RequestParam(value = "examSeries", required = false) Optional<String> examSeries
    ) throws NotFoundException {
        LOGGER.info("** SyllabiApi - syllabusGetAll");
        List<Syllabus> syllabus = syllabusService.findAll(examBoardId, syllabusCode, examYear, examSeries);
        List<Syllabus> entryCodeSyllabi;
        if (syllabusCode.isPresent()) {
            entryCodeSyllabi = syllabusService.findByEntryCode(syllabusCode.get())
        }
        return new ResponseEntity<List<SyllabusDto>>(SyllabusDto.mapFromSyllabusEntities((syllabus + entryCodeSyllabi).unique(false)), HttpStatus.OK);
    }
}
