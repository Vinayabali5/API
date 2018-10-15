package uk.ac.reigate.services.search

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.jdbc.core.RowMapper
import org.springframework.jdbc.core.namedparam.BeanPropertySqlParameterSource
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate
import org.springframework.jdbc.core.namedparam.SqlParameterSource
import org.springframework.stereotype.Service

import uk.ac.reigate.mappers.StudentSearchResultMapper
import uk.ac.reigate.model.search.StudentCourseGroupSearchParams
import uk.ac.reigate.model.search.StudentSearchParams
import uk.ac.reigate.model.search.StudentSearchResult;
import uk.ac.reigate.services.AcademicYearService

@Service
class StudentCourseGroupSearchService {
    
    private final static String STUDENT_SEARCH_SQL = "\
    EXEC [API].[StudentCourseGroupSearch] @YearId = :yearId, \
		@Name = :name, \
		@ReferenceDate = :referenceDate, \
		@CourseGroupMask = '%', \
		@StudentTypeMask = :studentTypeMask, \
		@TutorGroupMask = :tutorGroupMask, \
		@IncludeWithdrawn = :includeWithdrawn \
    "
    
    private final static String STUDENT_COURSE_GROUP_SEARCH_SQL = "\
    EXEC [API].[StudentCourseGroupSearch] @YearId = :yearId, \
		@Name = :name, \
		@ReferenceDate = :referenceDate, \
		@CourseGroupMask = :courseGroupMask, \
		@StudentTypeMask = :studentTypeMask, \
		@TutorGroupMask = :tutorGroupMask, \
		@IncludeWithdrawn = :includeWithdrawn \
    "
    
    @Autowired
    NamedParameterJdbcTemplate namedParameterJdbcTemplate
    
    @Autowired
    AcademicYearService academicYearService
    
    List<StudentSearchResult> search(String sql, SqlParameterSource namedParameters) {
        return this.namedParameterJdbcTemplate.query(sql, namedParameters, new StudentSearchResultMapper());
    }
    
    List<StudentSearchResult> search(String sql, SqlParameterSource namedParameters, RowMapper<StudentSearchResult> mapper) {
        return this.namedParameterJdbcTemplate.query(sql, namedParameters, mapper);
    }
    
    List<StudentSearchResult> search(StudentSearchParams searchParams) {
        if (searchParams != null) {
            // Set default year if not supplied
            if (searchParams.yearId == null) {
                if (searchParams.yearCode == null) {
                    searchParams.yearId = academicYearService.getCurrentAcademicYear().getId();
                } else {
                    searchParams.yearId = academicYearService.findByCode(searchParams.yearCode).getId();
                }
            }
            
            SqlParameterSource namedParameters = new BeanPropertySqlParameterSource(searchParams);
            
            List<StudentSearchResult> searchResults = search(this.STUDENT_SEARCH_SQL, namedParameters);
            
            return searchResults;
        }
        
    }
    
    List<StudentSearchResult> search(StudentCourseGroupSearchParams searchParams) {
        if (searchParams != null) {
            // Set default year if not supplied
            if (searchParams.yearId == null) {
                if (searchParams.yearCode == null) {
                    searchParams.yearId = academicYearService.getCurrentAcademicYear().getId();
                } else {
                    searchParams.yearId = academicYearService.findByCode(searchParams.yearCode).getId();
                }
            }
            
            SqlParameterSource namedParameters = new BeanPropertySqlParameterSource(searchParams);
            
            List<StudentSearchResult> searchResults = search(this.STUDENT_COURSE_GROUP_SEARCH_SQL, namedParameters);
            
            return searchResults;
        }
        
    }
    
}
