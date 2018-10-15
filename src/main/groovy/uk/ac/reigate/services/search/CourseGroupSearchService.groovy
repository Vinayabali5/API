package uk.ac.reigate.services.search

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate
import org.springframework.stereotype.Service

import uk.ac.reigate.model.CourseSearch
import uk.ac.reigate.model.search.CourseGroupSearchParams
import uk.ac.reigate.services.AcademicYearService

@Service
class CourseGroupSearchService {
    
    
    @Autowired
    NamedParameterJdbcTemplate namedParameterJdbcTemplate
    
    @Autowired
    AcademicYearService academicYearService
    
    
    List<CourseSearch> search(CourseGroupSearchParams courseGroupSearchParams) {
    }
}
