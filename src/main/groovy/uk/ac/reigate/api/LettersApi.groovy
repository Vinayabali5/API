package uk.ac.reigate.api;

import javax.validation.Valid

import io.swagger.annotations.Api
import io.swagger.annotations.ApiOperation
import io.swagger.annotations.ApiParam
import io.swagger.annotations.ApiResponse
import io.swagger.annotations.ApiResponses

import org.apache.log4j.Logger

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.stereotype.Controller
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestMethod

import uk.ac.reigate.domain.academic.Student
import uk.ac.reigate.domain.ilp.Letter
import uk.ac.reigate.domain.ilp.LetterType
import uk.ac.reigate.dto.LetterDto;
import uk.ac.reigate.exceptions.InvalidDataException
import uk.ac.reigate.exceptions.NotFoundException
import uk.ac.reigate.services.LetterService
import uk.ac.reigate.services.LetterTypeService
import uk.ac.reigate.services.StudentService

import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE


@Controller
@RequestMapping(value = "/api", produces = [ APPLICATION_JSON_VALUE ])
@Api(value = "/api", description = "the letters API")
public class LettersApi {
    
    private static final Logger LOGGER = Logger.getLogger(LettersApi.class);
    
    @Autowired
    private final LetterService letterService;
    
    @Autowired
    private final StudentService studentService;
    
    @Autowired
    private final LetterTypeService letterTypeService;
    
    
    /**
     * Default NoArgs constructor
     */
    LettersApi() {}
    
    /**
     * Autowired constructor
     */
    LettersApi(LetterService letterService) {
        this.letterService = letterService;
    }
    
    /**
     * The lettersStudentIdGet method is used to retrieve an instance of a LetterDto object as identified by the studentId provided
     *
     * @param studentId the student ID for the Letter object retrieve
     * @return A ResponseEntity with the corresponding LetterDto object
     * @throws NotFoundException if nothing is found then the the NotFoundException is thrown.
     */
    @ApiOperation(value = "Retrieves an indivdual instance of a Letter identified by the letterId", notes = "A getGET request to the Letter instance endpoint will retrieve an instance of a Letter entity as identified by the letterId provided in the URI.", response = LetterDto.class)
    @ApiResponses(value = [
        @ApiResponse(code = 200, message = "Returns a copy of the Letter as identified by the letterId"),
        @ApiResponse(code = 404, message = "Returns an Error object stating that the resource could not be found. This happens if the specified id cannot be found in the database.")
    ])
    @RequestMapping(value = "/students/{studentId}/ilpLetter ", produces = ["application/json"], method = RequestMethod.GET)
    public ResponseEntity<LetterDto> lettersStudentIdGet(@ApiParam(value = "The unique ID of the Letter to retrieve", required = true)
            @PathVariable("studentId") Integer studentId) throws NotFoundException {
        LOGGER.info("** LettersApi - lettersLetterIdGet");
        List<Letter> letter = letterService.getByStudent(studentId);
        if (letter == null) {
            throw new NotFoundException();
        }
        return new ResponseEntity<LetterDto>(LetterDto.mapFromLettersEntities(letter), HttpStatus.OK);
    }
    
    /**
     * The lettersPost method is used to create a new instance of a Letter from the supplied LetterDto
     *
     * @param letter the LetterDto to use to create the new Letter object
     * @return A ResponseEntity with the newly created Letter object
     * @throws InvalidDataException if there is an issue with the data provided in the RequestBody then an InvalidDataException is thrown
     */
    @ApiOperation(value = "Creates a new Letter entity", notes = "A POST request to the Letters endpoint with a Letter object in the request body will create a new Letter entity in the database.", response = LetterDto.class)
    @ApiResponses(value = [
        @ApiResponse(code = 201, message = "Returns a copy of the created Letter entity including the letterId that has just been created."),
        @ApiResponse(code = 400, message = "Returns an Error object stating the request body does not match the expected input.")
    ])
    @RequestMapping(value = "/ilpLetter", produces = ["application/json"], method = RequestMethod.POST)
    public ResponseEntity<LetterDto> lettersPost(@ApiParam(value = "The Letter object to be created, without the letterId fields", required = true) @RequestBody @Valid LetterDto letter) throws NotFoundException, InvalidDataException {
        LOGGER.info("** LettersApi - lettersPOST");
        if (letter.id != null) {
            throw new InvalidDataException(400, "No ID field should be provided when creating")
        }
        Student student
        if(letter.studentId != null) {
            student = studentService.findById(letter.studentId)
        }
        
        LetterType letterType
        if(letter.letterTypeId != null) {
            letterType = letterTypeService.findById(letter.letterTypeId)
        }
        // TODO: rewrite the create routine to replace the LetterDto.mapToLetterEntity which no longer exists
        Letter letterToSave = LetterDto.mapToLetterEntity(letter, student, letterType)
        Letter letterSaved = letterService.save(letterToSave)
        return new ResponseEntity<LetterDto>(LetterDto.mapFromLetterEntity(letterSaved), HttpStatus.CREATED);
    }
    
    /**
     * The lettersLetterIdGet method is used to retrieve an instance of a LetterDto object as identified by the studentId and letterId provided
     *
     * @param studentId, letterId the student ID and Letter Id for the Letter object retrieve
     * @return A ResponseEntity with the corresponding LetterDto object
     * @throws NotFoundException if nothing is found then the the NotFoundException is thrown.
     */
    @ApiOperation(value = "Retrieves an indivdual instance of a Letter identified by the letterId", notes = "A getGET request to the Letter instance endpoint will retrieve an instance of a Letter entity as identified by the letterId provided in the URI.", response = LetterDto.class)
    @ApiResponses(value = [
        @ApiResponse(code = 200, message = "Returns a copy of the Letter as identified by the letterId"),
        @ApiResponse(code = 404, message = "Returns an Error object stating that the resource could not be found. This happens if the specified id cannot be found in the database.")
    ])
    @RequestMapping(value = "/students/{studentId}/ilpLetter/{letterId} ", produces = ["application/json"], method = RequestMethod.GET)
    public ResponseEntity<LetterDto> lettersLetterIdGet(
            @ApiParam(value = "The unique ID of the Student to retrieve", required = true)
            @PathVariable("studentId") Integer studentId,
            @ApiParam(value = "The unique ID of the Letter to retrieve", required = true)
            @PathVariable("letterId") Integer letterId) throws NotFoundException {
        LOGGER.info("** LettersApi - lettersLetterIdGet");
        Letter letter = letterService.getByStudentAndLetter(studentId, letterId);
        if (letter == null) {
            throw new NotFoundException();
        }
        return new ResponseEntity<LetterDto>(LetterDto.mapFromLetterEntity(letter), HttpStatus.OK);
    }
    
    /**
     * The lettersLetterIdPut is used to update
     *
     * @param letterId the letter ID for the Letter object to update
     * @param letter the new data for the Letter object
     * @return the newly updated LetterDto object
     * @throws NotFoundException if nothing is found then the the NotFoundException is thrown.
     * @throws InvalidDataException if there is an issue with the data provided in the RequestBody then an InvalidDataException is thrown
     */
    @ApiOperation(value = "Used to update a Letter entity", notes = "A PUT request to the Letter instance endpoint with a Letter object in the request body will update an existing Letter entity in the database.", response = LetterDto.class)
    @ApiResponses(value = [
        @ApiResponse(code = 200, message = "Returns a copy of the newly updated Letter object."),
        @ApiResponse(code = 400, message = "Returns an Error object stating the request body does not match the expected input."),
        @ApiResponse(code = 404, message = "Returns an Error object stating that the resource could not be found. This happens if the specified id cannot be found in the database.")
    ])
    @RequestMapping(value = "/ilpLetter/{letterId}", produces = ["application/json"], method = RequestMethod.PUT)
    public ResponseEntity<LetterDto> lettersLetterIdPut(
            @ApiParam(value = "The unique ID of the Letter to retrieve", required = true) @PathVariable("letterId") Integer letterId,
            @ApiParam(value = "The Letter object to be created, without the letterId fields", required = true) @RequestBody LetterDto letter) throws NotFoundException, InvalidDataException {
        LOGGER.info("** LettersApi - lettersPUT");
        if (letterId != letter.id) {
            throw new InvalidDataException()
        }
        Student student
        if(letter.studentId != null) {
            student = studentService.findById(letter.studentId)
        }
        LetterType letterType
        if(letter.letterTypeId != null) {
            letterType = letterTypeService.findById(letter.letterTypeId)
        }
        // TODO: rewrite the update routine to replace the LetterDto.mapToLetterEntity which no longer exists
        Letter letterToSave = LetterDto.mapToLetterEntity(letter, student, letterType)
        Letter letterSaved = letterService.save(letterToSave)
        return new ResponseEntity<LetterDto>(LetterDto.mapFromLetterEntity(letterSaved), HttpStatus.OK);
    }
}
