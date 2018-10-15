package uk.ac.reigate.api.exam;

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

import uk.ac.reigate.domain.exam.ExamBoard
import uk.ac.reigate.dto.exam.ExamBoardDto
import uk.ac.reigate.exceptions.InvalidDataException
import uk.ac.reigate.exceptions.NotFoundException
import uk.ac.reigate.model.SearchResult
import uk.ac.reigate.services.exam.ExamBoardService


@Controller
@RequestMapping(value = "/api/examBoards", produces = [ APPLICATION_JSON_VALUE ])
@Api(value = "/api/examBoards", description = "the examBoards API")
public class ExamBoardsApi {
    
    private static final Logger LOGGER = Logger.getLogger(ExamBoardsApi.class);
    
    @Autowired
    private final ExamBoardService examBoardService;
    
    /**
     * Default NoArgs constructor
     */
    ExamBoardsApi() {}
    
    /**
     * Autowired constructor
     */
    ExamBoardsApi(ExamBoardService examBoardService) {
        this.examBoardService = examBoardService;
    }
    
    /**
     * The examBoardsGet method is used to retrieve a full list of all the ExamBoardDto objects
     *
     * @return A ResponseEntity with the corresponding list of ExamBoardDto objects
     * @throws NotFoundException if nothing is found then the the NotFoundException is thrown.
     */
    @ApiOperation(value = "Collection of ExamBoard entities", notes = "A GET request to the ExamBoards endpoint returns an array of all the examBoards in the system.", response = ExamBoardDto.class, responseContainer = "List")
    @ApiResponses(value = [
        @ApiResponse(code = 200, message = "An array of examBoards")
    ])
    @RequestMapping(value = "", produces = ["application/json"], method = RequestMethod.GET)
    public ResponseEntity<List<ExamBoardDto>> examBoardsGet() throws NotFoundException {
        LOGGER.info("** ExamBoardsApi - examBoardsGet");
        List<ExamBoard> examBoardsList = examBoardService.findAll();
        return new ResponseEntity<List<ExamBoardDto>>(ExamBoardDto.mapFromExamBoardsEntities(examBoardsList), HttpStatus.OK);
    }
    
    /**
     * The examBoardsPost method is used to create a new instance of a ExamBoard from the supplied ExamBoardDto
     *
     * @param examBoard the ExamBoardDto to use to create the new ExamBoard object
     * @return A ResponseEntity with the newly created ExamBoard object
     * @throws InvalidDataException if there is an issue with the data provided in the RequestBody then an InvalidDataException is thrown
     */
    @ApiOperation(value = "Creates a new ExamBoard entity", notes = "A POST request to the ExamBoards endpoint with a ExamBoard object in the request body will create a new ExamBoard entity in the database.", response = ExamBoardDto.class)
    @ApiResponses(value = [
        @ApiResponse(code = 201, message = "Returns a copy of the created ExamBoard entity including the examBoardId that has just been created."),
        @ApiResponse(code = 400, message = "Returns an Error object stating the request body does not match the expected input.")
    ])
    @RequestMapping(value = "", produces = ["application/json"], method = RequestMethod.POST)
    public ResponseEntity<ExamBoardDto> create(
            @ApiParam(value = "The ExamBoard object to be created, without the examBoardId fields", required = true)
            @RequestBody @Valid ExamBoardDto examBoard) throws NotFoundException, InvalidDataException {
        LOGGER.info("** ExamBoardsApi - examBoardsPOST");
        
        ExamBoard examBoardToSave = examBoard.toExamBoard()//ExamBoardDto.mapToExamBoardEntity(examBoard)
        ExamBoard examBoardSaved = examBoardService.save(examBoardToSave)
        return new ResponseEntity<ExamBoardDto>(new ExamBoardDto(examBoardSaved), HttpStatus.CREATED);
    }
    
    /**
     * The examBoardsExamBoardIdGet method is used to retrieve an instance of a ExamBoardDto object as identified by the examBoardId provided
     *
     * @param examBoardId the examBoard ID for the ExamBoard object retrieve
     * @return A ResponseEntity with the corresponding ExamBoardDto object
     * @throws NotFoundException if nothing is found then the the NotFoundException is thrown.
     */
    @ApiOperation(value = "Retrieves an indivdual instance of a ExamBoard identified by the examBoardId", notes = "A getGET request to the ExamBoard instance endpoint will retrieve an instance of a ExamBoard entity as identified by the examBoardId provided in the URI.", response = ExamBoardDto.class)
    @ApiResponses(value = [
        @ApiResponse(code = 200, message = "Returns a copy of the ExamBoard as identified by the examBoardId"),
        @ApiResponse(code = 404, message = "Returns an Error object stating that the resource could not be found. This happens if the specified id cannot be found in the database.")
    ])
    @RequestMapping(value = "/{examBoardId}", produces = ["application/json"], method = RequestMethod.GET)
    public ResponseEntity<ExamBoardDto> examBoardsExamBoardIdGet(@ApiParam(value = "The unique ID of the ExamBoard to retrieve", required = true) @PathVariable("examBoardId") Integer examBoardId) throws NotFoundException {
        LOGGER.info("** ExamBoardsApi - examBoardsExamBoardIdGet");
        ExamBoard examBoard = examBoardService.findById(examBoardId);
        if (examBoard == null) {
            throw new NotFoundException();
        }
        return new ResponseEntity<ExamBoardDto>(ExamBoardDto.mapFromExamBoardEntity(examBoard), HttpStatus.OK);
    }
    
    /**
     * The examBoardsExamBoardIdPut is used to update
     *
     * @param examBoardId the examBoard ID for the ExamBoard object to update
     * @param examBoard the new data for the ExamBoard object
     * @return the newly updated ExamBoardDto object
     * @throws NotFoundException if nothing is found then the the NotFoundException is thrown.
     * @throws InvalidDataException if there is an issue with the data provided in the RequestBody then an InvalidDataException is thrown
     */
    @ApiOperation(value = "Used to update a ExamBoard entity", notes = "A PUT request to the ExamBoard instance endpoint with a ExamBoard object in the request body will update an existing ExamBoard entity in the database.", response = ExamBoardDto.class)
    @ApiResponses(value = [
        @ApiResponse(code = 200, message = "Returns a copy of the newly updated ExamBoard object."),
        @ApiResponse(code = 400, message = "Returns an Error object stating the request body does not match the expected input."),
        @ApiResponse(code = 404, message = "Returns an Error object stating that the resource could not be found. This happens if the specified id cannot be found in the database.")
    ])
    @RequestMapping(value = "/{examBoardId}", produces = ["application/json"], method = RequestMethod.PUT)
    public ResponseEntity<ExamBoardDto> examBoardsExamBoardIdPut(
            @ApiParam(value = "The unique ID of the ExamBoard to retrieve", required = true) @PathVariable("examBoardId") Integer examBoardId,
            @ApiParam(value = "The ExamBoard object to be created, without the examBoardId fields", required = true) @RequestBody ExamBoardDto examBoard) throws NotFoundException, InvalidDataException {
        LOGGER.info("** ExamBoardsApi - examBoardsPUT");
        if (examBoardId != examBoard.id) {
            throw new InvalidDataException()
        }
        ExamBoard examBoardToSave = ExamBoardDto.mapToExamBoardEntity(examBoard)
        ExamBoard examBoardSaved = examBoardService.updateExamBoard(examBoardToSave)
        return new ResponseEntity<ExamBoardDto>(ExamBoardDto.mapFromExamBoardEntity(examBoardSaved), HttpStatus.OK);
    }
}
