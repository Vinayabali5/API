package uk.ac.reigate.api.enrolment

import java.sql.ResultSet
import java.sql.SQLException

import javax.sql.DataSource
import javax.validation.Valid

import org.apache.log4j.Logger
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.jdbc.core.JdbcTemplate
import org.springframework.jdbc.core.RowMapper
import org.springframework.security.access.annotation.Secured
import org.springframework.validation.BindingResult
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestMethod
import org.springframework.web.bind.annotation.RestController

import uk.ac.reigate.model.allocation.WorkingCombination
import uk.ac.reigate.model.enrolment.EnrolmentProgrammeChange
import uk.ac.reigate.model.enrolment.WorkingCombinationCheck
import uk.ac.reigate.services.enrolments.EnrolmentService;

@RestController
@RequestMapping('/api')
class EnrolmentCheckerApi {
    
    private static final Logger LOGGER = Logger.getLogger(EnrolmentCheckerApi.class);
    
    @Autowired
    DataSource dataSource
    
    @Autowired
    JdbcTemplate jdbcTemplate
    
    @Autowired
    EnrolmentService enrolmentService
    
    protected class RequestDescription {
        String request
        String description
        
        RequestDescription () {}
    }
    
    @Secured([
        "ROLE_Office Administration",
        "ROLE_Enrolment Manager"
    ])
    @RequestMapping(value = '/enrolmentChecker/validRequest', method = RequestMethod.POST)
    ResponseEntity<?> validRequest(@RequestBody Map<String, String> input, BindingResult results) {
        if (results) {
        }
        
        if (input.request && input.request != null) {
            
            def sql = "SELECT \
                           '" + input.request + "' AS [Request], \
                           [Data].[GetCourseDescription]([cg].[course_id]) AS [Description] \
                       FROM \
                           [dbo].[course_group] AS [cg] \
                       WHERE \
                           [cg].[CourseGroupSpec] LIKE '" + input.request + "%' \
                        "
            def requestValid = jdbcTemplate.query(sql, new RowMapper<RequestDescription >(){
                        @Override
                        public RequestDescription  mapRow(ResultSet rs, int rownumber) throws SQLException {
                            RequestDescription  out = new RequestDescription ();
                            out.request = rs.getString(1);
                            out.description = rs.getString(2);
                            return out;
                        }
                    })
            if (requestValid != null && requestValid.size != 0) {
                return new ResponseEntity<?>(requestValid[0], HttpStatus.OK)
            } else {
                return new ResponseEntity<?>(HttpStatus.NO_CONTENT)
            }
        } else {
            return new ResponseEntity<?>(HttpStatus.BAD_REQUEST)
        }
    }
    
    @Secured([
        "ROLE_Office Administration",
        "ROLE_Enrolment Manager"
    ])
    @RequestMapping(value = '/enrolmentChecker', method = RequestMethod.POST)
    ResponseEntity<?> checkEnrolments(@RequestBody WorkingCombinationCheck workingCombinationCheck, BindingResult results) {
        List<WorkingCombination> list = enrolmentService.findWorkingCombinationsOnDate(workingCombinationCheck.specs, workingCombinationCheck.date)
        if (list && list.size() > 0) {
            return new ResponseEntity<?>(list, HttpStatus.OK)
        } else {
            String message = "{\"message\": \"No valid working combination could be found.\"}"
            return new ResponseEntity<?>(message, HttpStatus.BAD_REQUEST)
        }
    }
    
    @Secured([
        "ROLE_Office Administration",
        "ROLE_Enrolment Manager"
    ])
    @RequestMapping(value = '/enrolments/change/programme', method = RequestMethod.POST)
    ResponseEntity<?> changeEnrolments(@RequestBody @Valid EnrolmentProgrammeChange programmeChange, BindingResult results) {
        LOGGER.info("II Enrolment API - Change Programme Called");
        if (enrolmentService.changeProgramme(programmeChange)) {
            def message = "{\"message\": \"Successfully Executed Programe Change\"}";
            return new ResponseEntity<?> (message, HttpStatus.OK);
        } else {
            def message = "{\"message\": \"There was a problem with the request.\"}";
            return new ResponseEntity<?> (message, HttpStatus.BAD_REQUEST);
        }
    }
}
