package uk.ac.reigate.api.search

import io.swagger.annotations.ApiImplicitParam
import io.swagger.annotations.ApiImplicitParams
import io.swagger.annotations.ApiOperation
import io.swagger.annotations.ApiResponse
import io.swagger.annotations.ApiResponses

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.stereotype.Controller
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestMethod

import uk.ac.reigate.exceptions.InvalidDataException
import uk.ac.reigate.model.search.StudentCourseGroupSearchParams
import uk.ac.reigate.model.search.StudentSearchResult
import uk.ac.reigate.services.search.StudentCourseGroupSearchService

import springfox.documentation.annotations.ApiIgnore


@RequestMapping(value="/api")
@Controller
class StudentCourseSearchApi {
    
    @Autowired
    StudentCourseGroupSearchService studentCourseGroupSearchService
    
    @ApiOperation(value = "Retrieves an indivdual instance of a Student identified by the academicYearId", notes = "A GET request to the Student instance endpoint will retrieve an instance of a Student entity as identified by the studentId provided in the URI.", response = StudentSearchResult.class)
    @ApiImplicitParams([
        @ApiImplicitParam(name = "yearId", dataType = "integer", paramType = "query", value = "The academic year id to use for the search. If not provided the current year is used."),
        @ApiImplicitParam(name = "yearCode", dataType = "string", paramType = "query", value = "The academic year code to use for the search."),
        @ApiImplicitParam(name = "date", dataType = "date", paramType = "query", value = "The reference date to use for the search."),
        @ApiImplicitParam(name = "name", dataType = "string", paramType = "query", value = "The name to use for the search. This will search multiple combinations of the name fields."),
        @ApiImplicitParam(name = "studentTypeMask", dataType = "string", paramType = "query", value = "The student type mask to use for the search."),
        @ApiImplicitParam(name = "tutorGroupMask", dataType = "string", paramType = "query", value = "The tutor group mask to use for the search."),
        @ApiImplicitParam(name = "courseGroupMask", dataType = "string", paramType = "query", value = "The course group mask to use for the search."),
        @ApiImplicitParam(name = "includeWithdrawn", dataType = "boolean", paramType = "query", value = "Used to include withdrawn student is results set.")
    ])
    @ApiResponses(value = [
        @ApiResponse(code = 200, message = "Returns a copy of the Student as identified by the studentId")
    ])
    @RequestMapping(value = "/search/studentCourse", produces = ["application/json"], method = RequestMethod.GET)
    ResponseEntity<List<StudentSearchResult>> search(@ApiIgnore StudentCourseGroupSearchParams searchParams) {
        if (searchParams != null) {
            return new  ResponseEntity<List<StudentSearchResult>>(studentCourseGroupSearchService.search(searchParams), HttpStatus.OK);
        } else {
            throw new InvalidDataException(500, "No search parameters provided");
        }
    }
}

