package uk.ac.reigate.api.integration

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestMethod
import org.springframework.web.bind.annotation.RestController

import uk.ac.reigate.dto.export.CourseDataExportDto
import uk.ac.reigate.services.integration.CourseDataExportService

@RestController
@RequestMapping(value = '/api/export/courses')
class CourseDataExportApi {
    
    @Autowired
    CourseDataExportService courseDataExportService
    
    @RequestMapping(value = "", method = RequestMethod.GET, produces = ["application/json"])
    ResponseEntity<List<CourseDataExportDto>> exportCourses() {
        List<CourseDataExportDto> courses = courseDataExportService.courseList()
        return new ResponseEntity<List<CourseDataExportDto>>(courses, HttpStatus.OK)
    }
}
