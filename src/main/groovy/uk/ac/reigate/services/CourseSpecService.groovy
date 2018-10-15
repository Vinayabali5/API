package uk.ac.reigate.services

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.jdbc.core.JdbcTemplate
import org.springframework.stereotype.Service


@Service
class CourseSpecService {
    
    @Autowired
    JdbcTemplate jdbcTemplate
    
    /**
     * This method is used to retrieve the course description of a specified course spec.
     *
     * @param spec The spec to retrieve a description for
     * @return A description of the supplied spec
     */
    public String getCourseSpecDescription(String spec) {
        def sql = "SELECT [Data].[GetCourseSpecDescription]('" + spec + "')";
        String description = (String)jdbcTemplate.queryForObject(sql, String.class)
        return description
    }
    
    /**
     * This method is used to retrieve the validity of a  specified course spec.
     *
     * @param spec The spec to retrieve a validity of
     * @return A boolean ff the course spec's valid
     */
    public Boolean isCourseSpecOnTimetable(String spec) {
        def sql = "SELECT [Data].[IsCourseSpecOnTimetable]('" + spec + "')";
        Boolean description = (Boolean)jdbcTemplate.queryForObject(sql, Boolean.class)
        return description
    }
    
    /**
     * This method is used to retrieve the validity of a specified course spec with in a specified year.
     *
     * @param spec The spec to retrieve a validity of
     * @param academicYearId The academicYearId for the year to retrieve a validity of
     * @return A boolean ff the course spec's valid
     */
    public Boolean isCourseSpecOnTimetable(String spec, Integer academicYearId) {
        def sql = "SELECT [Data].[IsCourseSpecOnTimetableInYear]('" + spec + "', " + academicYearId + ")";
        Boolean description = (Boolean)jdbcTemplate.queryForObject(sql, Boolean.class)
        return description
    }
}
