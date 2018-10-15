package uk.ac.reigate.api.exam

import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE

import java.util.List
import java.util.Optional;

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

import uk.ac.reigate.domain.academic.Student
import uk.ac.reigate.domain.exam.ExamOption
import uk.ac.reigate.domain.exam.EdiAuditEntryLog
import uk.ac.reigate.dto.exam.EdiAuditEntryLogDto
import uk.ac.reigate.exceptions.InvalidDataException
import uk.ac.reigate.exceptions.NotFoundException
import uk.ac.reigate.model.SearchResult
import uk.ac.reigate.services.StudentService;
import uk.ac.reigate.services.exam.EdiAuditEntryLogService
import uk.ac.reigate.services.exam.ExamOptionService


@Controller
@RequestMapping(value = "/api", produces = [ APPLICATION_JSON_VALUE ])
@Api(value = "/api", description = "the EdiAuditEntryLog API")
public class EdiAuditEntryLogsApi {
    
    private static final Logger LOGGER = Logger.getLogger(EdiAuditEntryLogsApi.class);
    
    @Autowired
    private final EdiAuditEntryLogService ediAuditEntryLogService;
    
    @Autowired
    private final StudentService studentService;
    
    /**
     * Default No Args constructor
     */
    EdiAuditEntryLogsApi() {}
    
    /**
     * Default Autowired constructor
     */
    EdiAuditEntryLogsApi(EdiAuditEntryLogService ediAuditEntryLogService) {
        this.ediAuditEntryLogService = ediAuditEntryLogService;
    }
    
    
    /**
     * @return List of EdiAuditEntryLogDto objects
     * @throws NotFoundException
     */
    @RequestMapping(value = "/ediAuditEntryLogs", produces = ["application/json"], method = RequestMethod.GET)
    public ResponseEntity<List<EdiAuditEntryLogDto>> ediAuditEntryLogsGet() throws NotFoundException {
        LOGGER.info("** EdiAuditEntryLogsApi - ediAuditEntryLogsGet");
        List<EdiAuditEntryLog> ediAuditEntryLogs = ediAuditEntryLogService.findAll();
        return new ResponseEntity<List<EdiAuditEntryLogDto>>(EdiAuditEntryLogDto.mapFromEdiAuditEntryLogsEntities(ediAuditEntryLogs), HttpStatus.OK);
    }
    
    
    /**
     * @param examYear
     * @return List of EdiAuditEntryLogDto objects By examYear
     * @throws NotFoundException
     */
    @RequestMapping(value = "/ediAuditEntryLogs/examYear", produces = ["application/json"], method = RequestMethod.GET)
    public ResponseEntity<List<EdiAuditEntryLogDto>> ediAuditEntryLogsGetByYear(
            @ApiParam(value = "exam Year", name = "examYear", required = true)
            @RequestParam(value = "examYear", required = true)String examYear) throws NotFoundException {
        List<EdiAuditEntryLog> ediAuditEntryLog = ediAuditEntryLogService.getByYear(examYear);
        return new ResponseEntity<List<EdiAuditEntryLogDto>>(EdiAuditEntryLogDto.mapFromEdiAuditEntryLogsEntities(ediAuditEntryLog), HttpStatus.OK)
    }
    
    /**
     * @param studentId
     * @param examYear
     * @return List of EdiAuditEntryLogDto objects on studentId and exam year
     * @throws NotFoundException
     */
    @RequestMapping(value = "/ediAuditEntryLogs/studentId/examYear", produces = ["application/json"], method = RequestMethod.GET)
    public ResponseEntity<List<EdiAuditEntryLogDto>> ediAuditEntryLogsGetByStudentAndYear(
            @ApiParam(value = "studentId", name = "studentId", required = true)
            @RequestParam(value = "studentId", required = true)Integer studentId,
            @ApiParam(value = "exam Year", name = "examYear", required = true)
            @RequestParam(value = "examYear", required = true)String examYear) throws NotFoundException {
        List<EdiAuditEntryLog> ediAuditEntryLog = ediAuditEntryLogService.getByStudentAndYear(studentId, examYear);
        return new ResponseEntity<List<EdiAuditEntryLogDto>>(EdiAuditEntryLogDto.mapFromEdiAuditEntryLogsEntities(ediAuditEntryLog), HttpStatus.OK)
    }
}
