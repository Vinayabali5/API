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

import uk.ac.reigate.domain.lookup.Title
import uk.ac.reigate.dto.TitleDto
import uk.ac.reigate.exceptions.InvalidDataException
import uk.ac.reigate.exceptions.NotFoundException
import uk.ac.reigate.model.SearchResult
import uk.ac.reigate.services.TitleService


@Controller
@RequestMapping(value = "/api/titles", produces = [ APPLICATION_JSON_VALUE ])
@Api(value = "/api/titles", description = "the titles API")
public class TitlesApi {
    
    private static final Logger LOGGER = Logger.getLogger(TitlesApi.class);
    
    @Autowired
    private final TitleService titleService;
    
    /**
     * Default NoArgs constructor
     */
    TitlesApi() {}
    
    /**
     * Autowired constructor
     */
    TitlesApi(TitleService titleService) {
        this.titleService = titleService;
    }
    
    /**
     * The titlesGet method is used to retrieve a full list of all the TitleDto objects
     *
     * @return A ResponseEntity with the corresponding list of TitleDto objects
     * @throws NotFoundException if nothing is found then the the NotFoundException is thrown.
     */
    @ApiOperation(value = "Collection of Title entities", notes = "A GET request to the Titles endpoint returns an array of all the titles in the system.", response = TitleDto.class, responseContainer = "List")
    @ApiResponses(value = [
        @ApiResponse(code = 200, message = "An array of titles")
    ])
    @RequestMapping(value = "", produces = ["application/json"], method = RequestMethod.GET)
    public ResponseEntity<List<TitleDto>> titlesGet() throws NotFoundException {
        LOGGER.info("** TitlesApi - titlesGet");
        List<Title> titles = titleService.findAll();
        return new ResponseEntity<List<TitleDto>>(TitleDto.mapFromTitlesEntities(titles), HttpStatus.OK);
    }
    
    /**
     * The titlesPost method is used to create a new instance of a Title from the supplied TitleDto
     *
     * @param title the TitleDto to use to create the new Title object
     * @return A ResponseEntity with the newly created Title object
     * @throws InvalidDataException if there is an issue with the data provided in the RequestBody then an InvalidDataException is thrown
     */
    @ApiOperation(value = "Creates a new Title entity", notes = "A POST request to the Titles endpoint with a Title object in the request body will create a new Title entity in the database.", response = TitleDto.class)
    @ApiResponses(value = [
        @ApiResponse(code = 201, message = "Returns a copy of the created Title entity including the titleId that has just been created."),
        @ApiResponse(code = 400, message = "Returns an Error object stating the request body does not match the expected input.")
    ])
    @RequestMapping(value = "", produces = ["application/json"], method = RequestMethod.POST)
    public ResponseEntity<TitleDto> titlesPost(@ApiParam(value = "The Title object to be created, without the titleId fields", required = true) @RequestBody @Valid TitleDto title) throws NotFoundException, InvalidDataException {
        LOGGER.info("** TitlesApi - titlesPOST");
        if (title.id != null) {
            throw new InvalidDataException(400, "No ID field should be provided when creating")
        }
        Title titleToSave = TitleDto.mapToTitleEntity(title)
        Title titleSaved = titleService.save(titleToSave)
        return new ResponseEntity<TitleDto>(TitleDto.mapFromTitleEntity(titleSaved), HttpStatus.CREATED);
    }
    
    /**
     * The titlesTitleIdGet method is used to retrieve an instance of a TitleDto object as identified by the titleId provided
     *
     * @param titleId the title ID for the Title object retrieve
     * @return A ResponseEntity with the corresponding TitleDto object
     * @throws NotFoundException if nothing is found then the the NotFoundException is thrown.
     */
    @ApiOperation(value = "Retrieves an indivdual instance of a Title identified by the titleId", notes = "A getGET request to the Title instance endpoint will retrieve an instance of a Title entity as identified by the titleId provided in the URI.", response = TitleDto.class)
    @ApiResponses(value = [
        @ApiResponse(code = 200, message = "Returns a copy of the Title as identified by the titleId"),
        @ApiResponse(code = 404, message = "Returns an Error object stating that the resource could not be found. This happens if the specified id cannot be found in the database.")
    ])
    @RequestMapping(value = "/{titleId}", produces = ["application/json"], method = RequestMethod.GET)
    public ResponseEntity<TitleDto> titlesTitleIdGet(@ApiParam(value = "The unique ID of the Title to retrieve", required = true) @PathVariable("titleId") Integer titleId) throws NotFoundException {
        LOGGER.info("** TitlesApi - titlesTitleIdGet");
        Title title = titleService.findById(titleId);
        if (title == null) {
            throw new NotFoundException();
        }
        return new ResponseEntity<TitleDto>(TitleDto.mapFromTitleEntity(title), HttpStatus.OK);
    }
    
    /**
     * The titlesTitleIdPut is used to update
     *
     * @param titleId the title ID for the Title object to update
     * @param title the new data for the Title object
     * @return the newly updated TitleDto object
     * @throws NotFoundException if nothing is found then the the NotFoundException is thrown.
     * @throws InvalidDataException if there is an issue with the data provided in the RequestBody then an InvalidDataException is thrown
     */
    @ApiOperation(value = "Used to update a Title entity", notes = "A PUT request to the Title instance endpoint with a Title object in the request body will update an existing Title entity in the database.", response = TitleDto.class)
    @ApiResponses(value = [
        @ApiResponse(code = 200, message = "Returns a copy of the newly updated Title object."),
        @ApiResponse(code = 400, message = "Returns an Error object stating the request body does not match the expected input."),
        @ApiResponse(code = 404, message = "Returns an Error object stating that the resource could not be found. This happens if the specified id cannot be found in the database.")
    ])
    @RequestMapping(value = "/{titleId}", produces = ["application/json"], method = RequestMethod.PUT)
    public ResponseEntity<TitleDto> titlesTitleIdPut(
            @ApiParam(value = "The unique ID of the Title to retrieve", required = true) @PathVariable("titleId") Integer titleId,
            @ApiParam(value = "The Title object to be created, without the titleId fields", required = true) @RequestBody TitleDto title) throws NotFoundException, InvalidDataException {
        LOGGER.info("** TitlesApi - titlesPUT");
        if (titleId != title.id) {
            throw new InvalidDataException()
        }
        Title titleToSave = TitleDto.mapToTitleEntity(title)
        Title titleSaved = titleService.updateTitle(titleToSave)
        return new ResponseEntity<TitleDto>(TitleDto.mapFromTitleEntity(titleSaved), HttpStatus.OK);
    }
}
