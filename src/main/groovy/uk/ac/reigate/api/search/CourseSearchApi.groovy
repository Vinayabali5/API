package uk.ac.reigate.api.search

import javax.persistence.EntityManager

import org.apache.log4j.Logger
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestMethod
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController

import uk.ac.reigate.domain.academic.Course
import uk.ac.reigate.dto.ErrorMessageDto
import uk.ac.reigate.dto.search.CourseSearchDto
import uk.ac.reigate.exceptions.NoSearchResultsFoundException
import uk.ac.reigate.repositories.search.StudentSearchRepository
import uk.ac.reigate.services.AcademicYearService
import uk.ac.reigate.services.search.CourseSearchService
import uk.ac.reigate.services.search.StudentSearchService

@RestController
@RequestMapping('/api')
class CourseSearchApi {
    
    private static final Logger LOGGER = Logger.getLogger(CourseSearchApi.class);
    
    @Autowired
    private EntityManager entityManager
    
    @Autowired
    private AcademicYearService academicYearService
    
    @Autowired
    private StudentSearchService studentSearchService
    
    @Autowired
    private StudentSearchRepository studentSearchRepository
    
    @Autowired
    private CourseSearchService courseSearchService
    
    @RequestMapping(value = [
        '/courseSearch',
        '/search/course'
    ], produces = ["application/json"], method = RequestMethod.GET)
    ResponseEntity<List<CourseSearchDto>> searchCourse(
            @RequestParam(value = "yearId", required = false) Integer yearId,
            @RequestParam(value = "search", required = true) String search
    ) throws NoSearchResultsFoundException {
        LOGGER.info("** CourseSearchApi - searchCourse");
        
        // Check to see if any search parameters where sent through in the request return BAD_REQUEST if not
        if (!search) {
            return new ResponseEntity(new ErrorMessageDto("No search parameters provides", "The request to the course search provided no search parameters."), HttpStatus.BAD_REQUEST)
        }
        if(!yearId){
            yearId= academicYearService.getCurrentAcademicYear().id
        }
        List<Course> searchResults = courseSearchService.searchBySpecOrDescriptionAndYear(search, yearId)
        
        List<CourseSearchDto> output = new ArrayList<CourseSearchDto>()
        searchResults.each { it ->
            output.add(new CourseSearchDto(it))
        }
        
        return new ResponseEntity<?>(output, HttpStatus.OK)
    }
}
