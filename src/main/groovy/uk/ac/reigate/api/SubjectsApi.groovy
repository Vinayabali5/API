package uk.ac.reigate.api;

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
import org.springframework.stereotype.Controller
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestMethod

import uk.ac.reigate.domain.lookup.Subject
import uk.ac.reigate.dto.SubjectDto
import uk.ac.reigate.exceptions.InvalidDataException
import uk.ac.reigate.exceptions.NotFoundException
import uk.ac.reigate.model.SearchResult
import uk.ac.reigate.services.SubjectService


@Controller
@RequestMapping(value = "/api/subjects", produces = [ APPLICATION_JSON_VALUE ])
@Api(value = "/api/subjects", description = "the subjects API")
public class SubjectsApi {
    
    private static final Logger LOGGER = Logger.getLogger(SubjectsApi.class);
    
    @Autowired
    private final SubjectService subjectService;
    
    /**
     * Default NoArgs constructor
     */
    SubjectsApi() {}
    
    /**
     * Autowired constructor
     */
    SubjectsApi(SubjectService subjectService) {
        this.subjectService = subjectService;
    }
    
    /**
     * The subjectsGet method is used to retrieve a full list of all the SubjectDto objects 
     * 
     * @return A ResponseEntity with the corresponding list of SubjectDto objects
     * @throws NotFoundException if nothing is found then the the NotFoundException is thrown. 
     */
    @ApiOperation(value = "Collection of Subject entities", notes = "A GET request to the Subjects endpoint returns an array of all the subjects in the system.", response = SubjectDto.class, responseContainer = "List")
    @ApiResponses(value = [
        @ApiResponse(code = 200, message = "An array of subjects")
    ])
    @RequestMapping(value = "/", produces = ["application/json"], method = RequestMethod.GET)
    public ResponseEntity<List<SubjectDto>> subjectsGet() throws NotFoundException {
        LOGGER.info("** SubjectsApi - subjectsGet");
        List<Subject> subjects = subjectService.findAll();
        return new ResponseEntity<List<SubjectDto>>(SubjectDto.mapFromSubjectsEntities(subjects), HttpStatus.OK);
    }
    
    /**
     * The subjectsPost method is used to create a new instance of a Subject from the supplied SubjectDto
     * 
     * @param subject the SubjectDto to use to create the new Subject object
     * @return A ResponseEntity with the newly created Subject object
     * @throws InvalidDataException if there is an issue with the data provided in the RequestBody then an InvalidDataException is thrown
     */
    @ApiOperation(value = "Creates a new Subject entity", notes = "A POST request to the Subjects endpoint with a Subject object in the request body will create a new Subject entity in the database.", response = SubjectDto.class)
    @ApiResponses(value = [
        @ApiResponse(code = 201, message = "Returns a copy of the created Subject entity including the subjectId that has just been created."),
        @ApiResponse(code = 400, message = "Returns an Error object stating the request body does not match the expected input.")
    ])
    @RequestMapping(value = "", produces = ["application/json"], method = RequestMethod.POST)
    public ResponseEntity<SubjectDto> subjectsPost(@ApiParam(value = "The Subject object to be created, without the subjectId fields", required = true) @RequestBody @Valid SubjectDto subject) throws NotFoundException, InvalidDataException {
        LOGGER.info("** SubjectsApi - subjectsPost");
        if (subject.id != null) {
            LOGGER.error("EE - Invalid Data");
            throw new InvalidDataException(400, "No ID field should be provided when creating")
        }
        Subject subjectToSave = SubjectDto.mapToSubjectEntity(subject)
        Subject subjectSaved = subjectService.save(subjectToSave)
        return new ResponseEntity<SubjectDto>(SubjectDto.mapFromSubjectEntity(subjectSaved), HttpStatus.CREATED);
    }
    
    /**
     * The subjectsSubjectIdGet method is used to retrieve an instance of a SubjectDto object as identified by the subjectId provided 
     * 
     * @param subjectId the subject ID for the Subject object retrieve 
     * @return A ResponseEntity with the corresponding SubjectDto object
     * @throws NotFoundException if nothing is found then the the NotFoundException is thrown. 
     */
    @ApiOperation(value = "Retrieves an indivdual instance of a Subject identified by the subjectId", notes = "A getGET request to the Subject instance endpoint will retrieve an instance of a Subject entity as identified by the subjectId provided in the URI.", response = SubjectDto.class)
    @ApiResponses(value = [
        @ApiResponse(code = 200, message = "Returns a copy of the Subject as identified by the subjectId"),
        @ApiResponse(code = 404, message = "Returns an Error object stating that the resource could not be found. This happens if the specified id cannot be found in the database.")
    ])
    @RequestMapping(value = "/{subjectId}", produces = ["application/json"], method = RequestMethod.GET)
    public ResponseEntity<SubjectDto> subjectsSubjectIdGet(@ApiParam(value = "The unique ID of the Subject to retrieve", required = true) @PathVariable("subjectId") Integer subjectId) throws NotFoundException {
        LOGGER.info("** SubjectsApi - subjectsSubjectIdGet");
        Subject subject = subjectService.findById(subjectId);
        if (subject == null) {
            LOGGER.error("EE - No data found with id: $subjectId")
            throw new NotFoundException();
        }
        return new ResponseEntity<SubjectDto>(SubjectDto.mapFromSubjectEntity(subject), HttpStatus.OK);
    }
    
    /**
     * The subjectsSubjectIdPut is used to update
     *     
     * @param subjectId the subject ID for the Subject object to update
     * @param subject the new data for the Subject object
     * @return the newly updated SubjectDto object 
     * @throws NotFoundException if nothing is found then the the NotFoundException is thrown. 
     * @throws InvalidDataException if there is an issue with the data provided in the RequestBody then an InvalidDataException is thrown
     */
    @ApiOperation(value = "Used to update a Subject entity", notes = "A PUT request to the Subject instance endpoint with a Subject object in the request body will update an existing Subject entity in the database.", response = SubjectDto.class)
    @ApiResponses(value = [
        @ApiResponse(code = 200, message = "Returns a copy of the newly updated Subject object."),
        @ApiResponse(code = 400, message = "Returns an Error object stating the request body does not match the expected input."),
        @ApiResponse(code = 404, message = "Returns an Error object stating that the resource could not be found. This happens if the specified id cannot be found in the database.")
    ])
    @RequestMapping(value = "/{subjectId}", produces = ["application/json"], method = RequestMethod.PUT)
    public ResponseEntity<SubjectDto> subjectsSubjectIdPut(
            @ApiParam(value = "The unique ID of the Subject to retrieve", required = true) @PathVariable("subjectId") Integer subjectId,
            @ApiParam(value = "The Subject object to be created, without the subjectId fields", required = true) @RequestBody SubjectDto subject) throws NotFoundException, InvalidDataException {
        LOGGER.info("** SubjectsApi - subjectsPUT");
        if (subjectId != subject.id) {
            throw new InvalidDataException()
        }
        Subject subjectToSave = SubjectDto.mapToSubjectEntity(subject)
        Subject subjectSaved = subjectService.updateSubject(subjectToSave)
        return new ResponseEntity<SubjectDto>(SubjectDto.mapFromSubjectEntity(subjectSaved), HttpStatus.OK);
    }
}
