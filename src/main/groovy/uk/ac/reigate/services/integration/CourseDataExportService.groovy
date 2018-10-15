package uk.ac.reigate.services.integration

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.security.access.prepost.PreAuthorize
import org.springframework.stereotype.Service

import uk.ac.reigate.domain.academic.Course
import uk.ac.reigate.dto.export.CourseDataExportDto
import uk.ac.reigate.services.AcademicYearService
import uk.ac.reigate.services.CourseService

@Service
class CourseDataExportService {
    
    @Autowired
    AcademicYearService academicYearService
    
    @Autowired
    CourseService courseService
    
    @PreAuthorize("@securityChecker.checkReader(authentication) or hasRole('ROLE_Service User')")
    List<CourseDataExportDto> courseList() {
        Integer yearId = academicYearService.getNextAcademicYear().id
        List<Course> courses = courseService.findAllPublishedCoursesValidInYear(yearId)
        courses.collect { it ->
            new CourseDataExportDto(it)
        }
    }
}
