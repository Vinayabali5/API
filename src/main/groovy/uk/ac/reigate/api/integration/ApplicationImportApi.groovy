package uk.ac.reigate.api.integration

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestMethod
import org.springframework.web.bind.annotation.RestController

import uk.ac.reigate.domain.academic.Student
import uk.ac.reigate.dto.integration.ApplicationImportDTO
import uk.ac.reigate.services.integration.ApplicationImportService

@RestController
@RequestMapping(value = '/api/import/application')
class ApplicationImportApi {
    
    @Autowired
    ApplicationImportService applicationImportService
    
    @RequestMapping(value = '', method = RequestMethod.POST, produces = 'application/json')
    public ResponseEntity<Student> importApplication(@RequestBody ApplicationImportDTO app) {
        return new ResponseEntity<Student>(applicationImportService.processImport(app), HttpStatus.CREATED)
    }
}
