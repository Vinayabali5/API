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

import uk.ac.reigate.domain.exam.CourseSyllabus
import uk.ac.reigate.dto.exam.CourseSyllabusDto
import uk.ac.reigate.exceptions.NotFoundException
import uk.ac.reigate.model.SearchResult
import uk.ac.reigate.services.exam.CourseSyllabusService

@Controller
@RequestMapping(value = "/api/course-syllabi", produces = [APPLICATION_JSON_VALUE])
@Api(value = "/api/CourseSyllabiApi", description = "The Exam CourseSyllabi Resource API")
public class CourseSyllabiApi {
    
    private final static Logger LOGGER = Logger.getLogger(CourseSyllabiApi.class)
    
    @Autowired
    CourseSyllabusService courseSyllabusService
    
    /**
     * Default No Args constructor
     */
    CourseSyllabiApi() {}
    
    /**
     * Default Autowired constructor
     */
    CourseSyllabiApi(CourseSyllabusService courseSyllabusService) {
        this.courseSyllabusService = courseSyllabusService;
    }
    
    /**
     * The courseSyllabiGet method is used to retrieve a full list of all the CourseSyllabusDto objects
     * 
     * @return A ResponseEntity with the corresponding list of CourseSyllabusDto objects
     * @throws NotFoundException if nothing is found then the NotFoundException is thrown
     */
    @ApiOperation(value = "Collection of CourseSyllabi entities", notes = "A GET request to the CourseSyllabi endpoint returns an array of all the courseSyllabi in the system", response = CourseSyllabusDto.class, responseContainer = "List")
    @ApiResponses(value = [
        @ApiResponse(code = 200, message = "An array of courseSyllabi")
    ])
    @RequestMapping(value = "", produces = ["application/json"], method = RequestMethod.GET)
    public ResponseEntity<List<CourseSyllabusDto>> courseSyllabiGet() throws NotFoundException {
        LOGGER.info("** CourseSyllabiApi - courseSyllabiGet");
        List<CourseSyllabus> courseSyllabi = courseSyllabusService.findAll();
        return new ResponseEntity<List<CourseSyllabusDto>>(CourseSyllabusDto.mapFromCourseSyllabusEntities(courseSyllabi), HttpStatus.OK);
    }
    
    /**
     * The courseSyllabusCourseIdSyllabusIdGet method is used to retrieve an instance of a CourseSyllabusDto object as identified by the courseId and syllabusId provided
     * 
     * @param courseId   } The courseId/syllabusId composite ID of the CourseSyllabus object to retrieve
     * @param syllabusId }
     * @return A ResponseEntity with the corresponding CourseSyllabusDto object
     * @throws NotFoundException if nothing is found then the NotFoundException is thrown
     */
    @ApiOperation(value = "Retrieves an individual instance of a CourseSyllabus identified by the courseId and syllabusId",
    notes = "A GET request to the CourseSyllabi endpoint will retrieve an instance of a CourseSyllabus entity as identified by the courseId and syllabusId provided in the URI.",
    response = CourseSyllabusDto.class)
    @ApiResponses(value = [
        @ApiResponse(code = 200, message = "Returns a copy of the CourseSyllabus as identified by the courseId and syllabusId"),
        @ApiResponse(code = 404, message = "Returns an Error object stating that the resource could not be found. This happens if the specified composite Id cannot be found in the database.")
    ])
    @RequestMapping(value = "/{courseId}/{syllabusId}", produces = ["application/json"], method = RequestMethod.GET)
    public ResponseEntity<CourseSyllabusDto> courseSyllabusCourseIdSyllabusIdGet(
            @ApiParam(value = "The courseId element of the composite Id of the CourseSyllabus to retrieve", required = true) @PathVariable("courseId") Integer courseId,
            @ApiParam(value = "The syllabusId element of the composite Id of the CourseSyllabus to retrieve", required = true) @PathVariable("syllabusId") Integer syllabusId
    ) throws NotFoundException {
        LOGGER.info("** CourseSyllabiApi - courseSyllabusCourseIdSyllabusIdGet");
        CourseSyllabus courseSyllabus = courseSyllabusService.findCourseSyllabus(courseId, syllabusId);
        if (courseSyllabus == null) {
            throw new NotFoundException();
        }
        return new ResponseEntity<CourseSyllabusDto>(CourseSyllabusDto.mapFromCourseSyllabusEntity(courseSyllabus), HttpStatus.OK);
    }
    
    /**
     * The courseSyllabusPost method is used to create a new instance of a CourseSyllabus object from the supplied CourseSyllabusDto entity
     * 
     * @param courseSyllabus The courseSyllabusDto to use to create the new CourseSyllabus object
     * @return A ResponseEntity with an OK object
     * @throws NotFoundException
     */
    @ApiOperation(value = "Creates a new CourseSyllabus entity",
    notes = "A POST request to the CourseSyllabi endpoint with a CourseSyllabus object in the request body will create a new CourseSyllabus entity in the database")
    @ApiResponses(value = [
        @ApiResponse(code = 201, message = "Returns an OK object stating that the CourseSyllabus entity has just been created")
    ])
    @RequestMapping(value = "", produces = ["application/json"], method = RequestMethod.POST)
    public ResponseEntity<String> courseSyllabusPost(
            @ApiParam(value = "The courseSyllabus object to be created", required = true) @RequestBody @Valid CourseSyllabusDto courseSyllabus
    ) throws NotFoundException {
        LOGGER.info("** CourseSyllabusApi - courseSyllabusPost");
        //        CourseSyllabus toSave = CourseSyllabusDto.mapToCourseSyllabusEntity(courseSyllabus);
        CourseSyllabus savedCourseSyllabus = courseSyllabusService.saveCourseSyllabus(courseSyllabus.courseId, courseSyllabus.syllabusId);
        return new ResponseEntity<String>(HttpStatus.OK);
    }
    
    /**
     * The courseSyllabusDelete method is used to delete an instance of a CourseSyllabus object from the supplied CourseSyllabusDto entity
     * 
     * @param courseSyllabus The courseSyllabusDto from which to delete the CourseSyllabus object
     * @return A ResponseEntity with an OK object
     * @throws NotFoundException
     */
    @ApiOperation(value = "Deletes a CourseSyllabus entity",
    notes = "A DELETE request to the CourseSyllabus endpoint with a CourseSyllabus object in the request body will delete a CourseSyllabus entity in the database")
    @ApiResponses(value = [
        @ApiResponse(code = 201, message = "Returns an OK object stating that the CourseSyllabus entity has just been deleted")
    ])
    @RequestMapping(value = "/{courseId}/{syllabusId}", produces = ["application/json"], method = RequestMethod.DELETE)
    public ResponseEntity<?> courseSyllabusDelete(
            @ApiParam(value = "The courseId element of the composite Id of the CourseSyllabus to retrieve", required = true) @PathVariable("courseId") Integer courseId,
            @ApiParam(value = "The syllabusId element of the composite Id of the CourseSyllabus to retrieve", required = true) @PathVariable("syllabusId") Integer syllabusId
    ) throws NotFoundException {
        LOGGER.info("** CourseSyllabusApi - courseSyllabusDelete");
        try {
            courseSyllabusService.deleteCourseSyllabus(courseId, syllabusId);
        } catch (Exception e) {
            throw new NotFoundException();
        }
        return new ResponseEntity<?>(HttpStatus.OK);
    }
}
