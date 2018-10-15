package uk.ac.reigate.api

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestMethod
import org.springframework.web.bind.annotation.RestController

import uk.ac.reigate.services.CourseSpecService

@RestController
@RequestMapping(value = "/api/courseSpecs")
class CourseSpecApi {
    
    @Autowired
    CourseSpecService courseSpecService
    
    @RequestMapping(value = "{spec}", method = RequestMethod.GET, produces = 'application/json')
    ResponseEntity<?> getCourseSpecDescription(@PathVariable String spec) {
        def out = [spec: spec, description: courseSpecService.getCourseSpecDescription(spec)]
        return new ResponseEntity<?>(out, HttpStatus.OK)
    }
    
    @RequestMapping(value = "{spec}/valid", method = RequestMethod.GET, produces = 'application/json')
    ResponseEntity<?> checkCourseSpecIsValid(@PathVariable String spec) {
        def out = [spec: spec, valid: courseSpecService.isCourseSpecOnTimetable(spec)]
        return new ResponseEntity<?>(out, HttpStatus.OK)
    }
}
