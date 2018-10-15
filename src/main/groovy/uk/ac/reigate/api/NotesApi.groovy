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

import uk.ac.reigate.domain.Note
import uk.ac.reigate.domain.NoteType
import uk.ac.reigate.domain.Person
import uk.ac.reigate.dto.NoteDto
import uk.ac.reigate.exceptions.InvalidDataException
import uk.ac.reigate.exceptions.NotFoundException
import uk.ac.reigate.services.NoteService
import uk.ac.reigate.services.NoteTypeService
import uk.ac.reigate.services.PersonService

import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE


@Controller
@RequestMapping(value = "/api/notes", produces = [ APPLICATION_JSON_VALUE ])
@Api(value = "/api/notes", description = "the notes API")
public class NotesApi {
    
    private static final Logger LOGGER = Logger.getLogger(NotesApi.class);
    
    @Autowired
    private final NoteService noteService;
    
    @Autowired
    private final PersonService personService;
    
    @Autowired
    private final NoteTypeService noteTypeService;
    
    /**
     * Default NoArgs constructor
     */
    NotesApi() {}
    
    /**
     * Autowired constructor
     */
    NotesApi(NoteService noteService) {
        this.noteService = noteService;
    }
    
    /**
     * The notesGet method is used to retrieve a full list of all the NoteDto objects
     *
     * @return A ResponseEntity with the corresponding list of NoteDto objects
     * @throws NotFoundException if nothing is found then the the NotFoundException is thrown.
     */
    @ApiOperation(value = "Collection of Note entities", notes = "A GET request to the Notes endpoint returns an array of all the notes in the system.", response = NoteDto.class, responseContainer = "List")
    @ApiResponses(value = [
        @ApiResponse(code = 200, message = "An array of notes")
    ])
    @RequestMapping(value = "", produces = ["application/json"], method = RequestMethod.GET)
    public ResponseEntity<List<NoteDto>> notesGet() throws NotFoundException {
        LOGGER.info("** NotesApi - notesGet");
        List<Note> notes = noteService.findAll();
        return new ResponseEntity<List<NoteDto>>(NoteDto.mapFromList(notes), HttpStatus.OK);
    }
    
    /**
     * The notesPost method is used to create a new instance of a Note from the supplied NoteDto
     *
     * @param note the NoteDto to use to create the new Note object
     * @return A ResponseEntity with the newly created Note object
     * @throws InvalidDataException if there is an issue with the data provided in the RequestBody then an InvalidDataException is thrown
     */
    @ApiOperation(value = "Creates a new Note entity", notes = "A POST request to the Notes endpoint with a Note object in the request body will create a new Note entity in the database.", response = NoteDto.class)
    @ApiResponses(value = [
        @ApiResponse(code = 201, message = "Returns a copy of the created Note entity including the noteId that has just been created."),
        @ApiResponse(code = 400, message = "Returns an Error object stating the request body does not match the expected input.")
    ])
    @RequestMapping(value = "", produces = ["application/json"], method = RequestMethod.POST)
    public ResponseEntity<NoteDto> createNote(
            @ApiParam(value = "The Note object to be created, without the noteId fields", required = true)
            @RequestBody @Valid NoteDto note
    ) throws NotFoundException, InvalidDataException {
        LOGGER.info("** NotesApi - createNote");
        if (note.id != null) {
            throw new InvalidDataException(400, "No ID field should be provided when creating")
        }
        if(note.personId == null || note.typeId == null) {
            throw new InvalidDataException(400, "Invalid data supplied")
        }
        Note noteSaved = noteService.createNote(note)
        return new ResponseEntity<NoteDto>(NoteDto.mapFromEntity(noteSaved), HttpStatus.CREATED);
    }
    
    /**
     * The notesNoteIdGet method is used to retrieve an instance of a NoteDto object as identified by the noteId provided
     *
     * @param noteId the note ID for the Note object retrieve
     * @return A ResponseEntity with the corresponding NoteDto object
     * @throws NotFoundException if nothing is found then the the NotFoundException is thrown.
     */
    @ApiOperation(value = "Retrieves an indivdual instance of a Note identified by the noteId", notes = "A getGET request to the Note instance endpoint will retrieve an instance of a Note entity as identified by the noteId provided in the URI.", response = NoteDto.class)
    @ApiResponses(value = [
        @ApiResponse(code = 200, message = "Returns a copy of the Note as identified by the noteId"),
        @ApiResponse(code = 404, message = "Returns an Error object stating that the resource could not be found. This happens if the specified id cannot be found in the database.")
    ])
    @RequestMapping(value = "/{noteId}", produces = ["application/json"], method = RequestMethod.GET)
    public ResponseEntity<NoteDto> notesNoteIdGet(@ApiParam(value = "The unique ID of the Note to retrieve", required = true) @PathVariable("noteId") Integer noteId) throws NotFoundException {
        LOGGER.info("** NotesApi - notesNoteIdGet");
        Note note = noteService.findById(noteId);
        if (note == null) {
            throw new NotFoundException();
        }
        return new ResponseEntity<NoteDto>(NoteDto.mapFromEntity(note), HttpStatus.OK);
    }
    
    /**
     * The notesNoteIdPut is used to update
     *
     * @param noteId the note ID for the Note object to update
     * @param note the new data for the Note object
     * @return the newly updated NoteDto object
     * @throws NotFoundException if nothing is found then the the NotFoundException is thrown.
     * @throws InvalidDataException if there is an issue with the data provided in the RequestBody then an InvalidDataException is thrown
     */
    @ApiOperation(value = "Used to update a Note entity", notes = "A PUT request to the Note instance endpoint with a Note object in the request body will update an existing Note entity in the database.", response = NoteDto.class)
    @ApiResponses(value = [
        @ApiResponse(code = 200, message = "Returns a copy of the newly updated Note object."),
        @ApiResponse(code = 400, message = "Returns an Error object stating the request body does not match the expected input."),
        @ApiResponse(code = 404, message = "Returns an Error object stating that the resource could not be found. This happens if the specified id cannot be found in the database.")
    ])
    @RequestMapping(value = "/{noteId}", produces = ["application/json"], method = RequestMethod.PUT)
    public ResponseEntity<NoteDto> notesNoteIdPut(
            @ApiParam(value = "The unique ID of the Note to retrieve", required = true) @PathVariable("noteId") Integer noteId,
            @ApiParam(value = "The Note object to be created, without the noteId fields", required = true) @RequestBody NoteDto note) throws NotFoundException, InvalidDataException {
        LOGGER.info("** NotesApi - notesPUT");
        if (noteId != note.id) {
            throw new InvalidDataException()
        }
        Person person
        if(note.personId != null) {
            person = personService.findById(note.personId)
        }
        NoteType type
        if(note.typeId != null) {
            type = noteTypeService.findById(note.typeId)
        }
        Note noteToSave = NoteDto.mapToNoteEntity(note, person, type)
        Note noteSaved = noteService.save(noteToSave)
        return new ResponseEntity<NoteDto>(NoteDto.mapFromEntity(noteSaved), HttpStatus.OK);
    }
}
