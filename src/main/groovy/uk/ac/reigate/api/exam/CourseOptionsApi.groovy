package uk.ac.reigate.api.exam

import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE
import io.swagger.annotations.Api
import io.swagger.annotations.ApiOperation
import io.swagger.annotations.ApiParam
import io.swagger.annotations.ApiResponse
import io.swagger.annotations.ApiResponses

import javax.validation.Valid

import org.apache.log4j.Logger
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.security.access.annotation.Secured
import org.springframework.stereotype.Controller
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestMethod

import uk.ac.reigate.domain.exam.CourseOption
import uk.ac.reigate.dto.exam.CourseOptionDto
import uk.ac.reigate.exceptions.NotFoundException
import uk.ac.reigate.model.SearchResult
import uk.ac.reigate.services.exam.CourseOptionService


@Controller
@RequestMapping(value = "/api/course-options", produces = [APPLICATION_JSON_VALUE])
@Api(value = "/api/CourseOptionsApi", description = "The Exam CourseOptions Resource API")
public class CourseOptionsApi {
    
    private final static Logger LOGGER = Logger.getLogger(CourseOptionsApi.class)
    
    @Autowired
    CourseOptionService courseOptionService
    
    /**
     * Default No Args constructor
     */
    CourseOptionsApi() {}
    
    /**
     * Default Autowired constructor
     */
    CourseOptionsApi(CourseOptionService courseOptionService) {
        this.courseOptionService = courseOptionService;
    }
    
    /**
     * The courseOptionsGet method is used to retrieve a full list of all the CourseOptionsDto objects
     * 
     * @return A ResponseEntity with the corresponding list of CourseOptionDto objects
     * @throws NotFoundException if nothing is found then the NotFoundException is thrown
     */
    @ApiOperation(value = "Collection of CourseOptions entities", notes = "A GET request to the CourseOptions endpoint returns an array of all the courseOptions in the system",
    response = CourseOptionDto.class, responseContainer = "List")
    @ApiResponses(value = [
        @ApiResponse(code = 200, message = "An array of courseOptions")
    ])
    @RequestMapping(value = "", produces = ["application/json"], method = RequestMethod.GET)
    public ResponseEntity<List<CourseOptionDto>> courseOptionsGet() throws NotFoundException {
        LOGGER.info("** CourseOptionApi - courseOptionsGet");
        List<CourseOption> courseOptions = courseOptionService.findAll();
        return new ResponseEntity<List<CourseOptionDto>>(CourseOptionDto.mapFromCourseOptionEntities(courseOptions), HttpStatus.OK);
    }
    
    /**
     * The courseOptionCourseIdOptionIdGet method is used to retrieve an instance of a CourseOptionDto object as identified by the courseId and examOptionId provided
     * 
     * @param courseId   } The courseId/examOptionId composite ID of the CourseOption object to retrieve
     * @param examOptionId }
     * @return A ResponseEntity with the corresponding CourseOptionDto object
     * @throws NotFoundException if nothing is found then the NotFoundException is thrown
     */
    @ApiOperation(value = "Retrieves an individual instance of a CourseOption identified by the courseId and examOptionId",
    notes = "A GET request to the CourseOptions endpoint will retrieve an instance of a CourseOption entity as identified by the courseId and examOptionId provided in the URI.",
    response = CourseOptionDto.class)
    @ApiResponses(value = [
        @ApiResponse(code = 200, message = "Returns a copy of the CourseOption as identified by the courseId and examOptionId"),
        @ApiResponse(code = 404, message = "Returns an Error object stating that the resource could not be found. This happens if the specified composite Id cannot be found in the database.")
    ])
    @RequestMapping(value = "/{courseId}/{examOptionId}", produces = ["application/json"], method = RequestMethod.GET)
    public ResponseEntity<CourseOptionDto> courseOptionCourseIdExamOptionIdGet(
            @ApiParam(value = "The courseId element of the composite Id of the CourseOption to retrieve", required = true) @PathVariable("courseId") Integer courseId,
            @ApiParam(value = "The examOptionId element of the composite Id of the CourseOption to retrieve", required = true) @PathVariable("examOptionId") Integer examOptionId
    ) throws NotFoundException {
        LOGGER.info("** CourseOptionApi - courseOptionCourseIdOptionIdGet");
        CourseOption courseOption = courseOptionService.findCourseOption(courseId, examOptionId);
        if (courseOption == null) {
            throw new NotFoundException();
        }
        return new ResponseEntity<CourseOptionDto>(CourseOptionDto.mapFromCourseOptionEntity(courseOption), HttpStatus.OK);
    }
    
    /**
     * The courseOptionPost method is used to create a new instance of a CourseOption object from the supplied CourseOptionDto entity
     * 
     * @param courseOption The courseOptionDto to use to create the new CourseOption object
     * @return A ResponseEntity with an OK object
     * @throws NotFoundException
     */
    @Secured([
        "ROLE_Core Data",
        "ROLE_Office Administration",
        "ROLE_Enrolment Manager",
        "ROLE_Administration",
        "ROLE_Student Services",
        "ROLE_Exams Officer"
    ])
    @ApiOperation(value = "Creates a new CourseOption entity",
    notes = "A POST request to the CourseOptions endpoint with a CourseOption object in the request body will create a new CourseOption entity in the database")
    @ApiResponses(value = [
        @ApiResponse(code = 201, message = "Returns an OK object stating that the CourseOption entity has just been created")
    ])
    @RequestMapping(value = "", produces = ["application/json"], method = RequestMethod.POST)
    public ResponseEntity<String> courseOptionPost(
            @ApiParam(value = "The courseOption object to be created", required = true) @RequestBody @Valid CourseOptionDto courseOption
    ) throws NotFoundException {
        LOGGER.info("** CourseOptionApi - courseOptionPost");
        //        CourseOption toSave = CourseOptionDto.mapToCourseOptionEntity(courseOption);
        CourseOption savedCourseOption = courseOptionService.saveCourseOption(courseOption.courseId, courseOption.examOptionId);
        return new ResponseEntity<String>(HttpStatus.OK);
    }
    
    /**
     * The courseOptionDelete method is used to delete an instance of a CourseOption object from the supplied CourseOptionDto entity
     * 
     * @param courseOption The courseOptionDto from which to delete the CourseOption object
     * @return A ResponseEntity with an OK object
     * @throws NotFoundException
     */
    @Secured('ROLE_Exams Officer')
    @ApiOperation(value = "Deletes a CourseOption entity",
    notes = "A DELETE request to the CourseOption endpoint with a CourseOption object in the request body will delete a CourseOption entity in the database")
    @ApiResponses(value = [
        @ApiResponse(code = 201, message = "Returns an OK object stating that the CourseOption entity has just been deleted")
    ])
    @RequestMapping(value = "/{courseId}/{examOptionId}", produces = ["application/json"], method = RequestMethod.DELETE)
    public ResponseEntity<String> courseOptionDelete(
            @ApiParam(value = "The courseId element of the composite Id of the CourseOption to retrieve", required = true) @PathVariable("courseId") Integer courseId,
            @ApiParam(value = "The examOptionId element of the composite Id of the CourseOption to retrieve", required = true) @PathVariable("examOptionId") Integer examOptionId
    ) throws NotFoundException {
        LOGGER.info("** CourseOptionApi - courseOptionDelete");
        try {
            courseOptionService.deleteCourseOption(courseId, examOptionId);
        } catch (Exception e) {
            throw new NotFoundException();
        }
        return new ResponseEntity<String>(HttpStatus.OK);
    }
}
