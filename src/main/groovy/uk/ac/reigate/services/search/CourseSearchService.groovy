package uk.ac.reigate.services.search

import java.sql.ResultSet
import java.sql.SQLException

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.jdbc.core.RowMapper
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate
import org.springframework.jdbc.core.namedparam.SqlParameterSource
import org.springframework.stereotype.Service

import uk.ac.reigate.domain.academic.Course
import uk.ac.reigate.model.CourseSearch
import uk.ac.reigate.repositories.academic.CourseRepository

@Service
class CourseSearchService {
    
    @Autowired
    CourseRepository courseRepository
    
    @Autowired
    NamedParameterJdbcTemplate namedParameterJdbcTemplate
    
    private final static String SEARCH_SQL = "\
		SELECT \
			[course_id],\
			[CourseSpec], \
			[level].[description] + ' ' + [subject].[description] AS [Description] \
		FROM \
			[dbo].[course] \
			INNER JOIN [dbo].[subject] \
			ON [subject].[subject_id] = [course].[subject_id] \
			INNER JOIN [dbo].[level] \
			ON [level].[level_id] = [course].[level_id] \
		WHERE \
			[level].[description] LIKE '%' + :search + '%' OR \
			[subject].[description] LIKE '%' + :search + '%' OR \
			[CourseSpec] LIKE '%' + :search + '%' OR \
			[CourseSpec] + ' - ' + [level].[description] + ' ' + [subject].[description] LIKE '%' + :search + '%' \
	"
    
    List<CourseSearch> search(String searchTerm) {
        SqlParameterSource namedParameters = new MapSqlParameterSource("search", searchTerm);
        
        return namedParameterJdbcTemplate.query(
                this.SEARCH_SQL,
                namedParameters,
                new RowMapper<CourseSearch>() {
                    public CourseSearch mapRow(ResultSet rs, int rowNum) throws SQLException {
                        CourseSearch cs = new CourseSearch();
                        cs.courseId = rs.getInt("course_id");
                        cs.spec = rs.getString("CourseSpec");
                        cs.description = rs.getString("Description");
                        return cs;
                    }
                });
    }
    
    List<Course> searchBySpecAndYear(String spec, Integer yearId) {
        return courseRepository.findCoursesBySpecAndCourseValidOnYear(spec, yearId)
    }
    
    List<Course> searchBySpecOrDescriptionAndYear(String search, Integer yearId) {
        return courseRepository.findCoursesBySpecOrDescriptionAndCourseValidOnYear(search, yearId)
    }
}
