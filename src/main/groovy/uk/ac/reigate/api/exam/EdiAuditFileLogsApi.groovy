package uk.ac.reigate.api.exam

import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE
import io.swagger.annotations.Api

import org.apache.log4j.Logger
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.stereotype.Controller
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestMethod

import uk.ac.reigate.domain.exam.EdiAuditFileLog
import uk.ac.reigate.dto.exam.EdiAuditFileLogDto
import uk.ac.reigate.exceptions.NotFoundException
import uk.ac.reigate.model.SearchResult
import uk.ac.reigate.services.exam.EdiAuditFileLogService


@Controller
@RequestMapping(value = "/api", produces = [ APPLICATION_JSON_VALUE ])
@Api(value = "/api", description = "the EdiAuditFileLog API")
public class EdiAuditFileLogsApi {
    
    private static final Logger LOGGER = Logger.getLogger(EdiAuditFileLogsApi.class);
    
    @Autowired
    private final EdiAuditFileLogService ediAuditFileLogService;
    
    /**
     * Default No Args constructor
     */
    EdiAuditFileLogsApi() {}
    
    /**
     * Default Autowired constructor
     */
    EdiAuditFileLogsApi(EdiAuditFileLogService ediAuditFileLogService) {
        this.ediAuditFileLogService = ediAuditFileLogService;
    }
    
    
    /**
     * @return List of EdiAuditFileLogDto
     * @throws NotFoundException
     */
    @RequestMapping(value = "/ediAuditFileLogs", produces = ["application/json"], method = RequestMethod.GET)
    public ResponseEntity<List<EdiAuditFileLogDto>> ediAuditFileLogsGet() throws NotFoundException {
        LOGGER.info("** EdiAuditFileLogsApi - ediAuditFileLogsGet");
        List<EdiAuditFileLog> ediAuditFileLogs = ediAuditFileLogService.findAll();
        return new ResponseEntity<List<EdiAuditFileLogDto>>(EdiAuditFileLogDto.mapFromEdiAuditFileLogsEntities(ediAuditFileLogs), HttpStatus.OK);
    }
    
    /*
     @RequestMapping(value = "/ediAuditFileLogs/examYear", produces = ["application/json"], method = RequestMethod.GET)
     public ResponseEntity<List<EdiAuditFileLogDto>> ediAuditFileLogsGetByYear(
     @ApiParam(value = "exam Year", name = "examYear", required = true)
     @RequestParam(value = "examYear", required = true)String examYear) throws NotFoundException {
     List<EdiAuditFileLog> ediAuditFileLog = ediAuditFileLogService.getByYear(examYear);
     return new ResponseEntity<List<EdiAuditFileLogDto>>(EdiAuditFileLogDto.mapFromEdiAuditFileLogsEntities(ediAuditFileLog), HttpStatus.OK)
     }
     */
}
