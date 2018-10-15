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

import uk.ac.reigate.domain.admissions.CollegeFundPaid
import uk.ac.reigate.dto.CollegeFundPaidDto
import uk.ac.reigate.exceptions.InvalidDataException
import uk.ac.reigate.exceptions.NotFoundException
import uk.ac.reigate.model.SearchResult
import uk.ac.reigate.services.CollegeFundPaidService


@Controller
@RequestMapping(value = "/api/collegeFundPaids", produces = [ APPLICATION_JSON_VALUE ])
@Api(value = "/api/collegeFundPaids", description = "the collegeFundPaids API")
public class CollegeFundPaidsApi {
    
    private static final Logger LOGGER = Logger.getLogger(CollegeFundPaidsApi.class);
    
    @Autowired
    private final CollegeFundPaidService collegeFundPaidService;
    
    /**
     * Default NoArgs constructor
     */
    CollegeFundPaidsApi() {}
    
    /**
     * Autowired constructor
     */
    CollegeFundPaidsApi(CollegeFundPaidService collegeFundPaidService) {
        this.collegeFundPaidService = collegeFundPaidService;
    }
    
    /**
     * The collegeFundPaidsGet method is used to retrieve a full list of all the CollegeFundPaidDto objects
     *
     * @return A ResponseEntity with the corresponding list of CollegeFundPaidDto objects
     * @throws NotFoundException if nothing is found then the the NotFoundException is thrown.
     */
    @ApiOperation(value = "Collection of CollegeFundPaid entities", notes = "A GET request to the CollegeFundPaids endpoint returns an array of all the collegeFundPaids in the system.", response = CollegeFundPaidDto.class, responseContainer = "List")
    @ApiResponses(value = [
        @ApiResponse(code = 200, message = "An array of collegeFundPaids")
    ])
    @RequestMapping(value = "", produces = ["application/json"], method = RequestMethod.GET)
    public ResponseEntity<List<CollegeFundPaidDto>> collegeFundPaidsGet() throws NotFoundException {
        LOGGER.info("** CollegeFundPaidsApi - collegeFundPaidsGet");
        List<CollegeFundPaid> collegeFundPaids = collegeFundPaidService.findAll();
        return new ResponseEntity<List<CollegeFundPaidDto>>(CollegeFundPaidDto.mapFromCollegeFundPaidsEntities(collegeFundPaids), HttpStatus.OK);
    }
    
    /**
     * The collegeFundPaidsPost method is used to create a new instance of a CollegeFundPaid from the supplied CollegeFundPaidDto
     *
     * @param collegeFundPaid the CollegeFundPaidDto to use to create the new CollegeFundPaid object
     * @return A ResponseEntity with the newly created CollegeFundPaid object
     * @throws InvalidDataException if there is an issue with the data provided in the RequestBody then an InvalidDataException is thrown
     */
    @ApiOperation(value = "Creates a new CollegeFundPaid entity", notes = "A POST request to the CollegeFundPaids endpoint with a CollegeFundPaid object in the request body will create a new CollegeFundPaid entity in the database.", response = CollegeFundPaidDto.class)
    @ApiResponses(value = [
        @ApiResponse(code = 201, message = "Returns a copy of the created CollegeFundPaid entity including the collegeFundPaidId that has just been created."),
        @ApiResponse(code = 400, message = "Returns an Error object stating the request body does not match the expected input.")
    ])
    @RequestMapping(value = "", produces = ["application/json"], method = RequestMethod.POST)
    public ResponseEntity<CollegeFundPaidDto> collegeFundPaidsPost(@ApiParam(value = "The CollegeFundPaid object to be created, without the collegeFundPaidId fields", required = true) @RequestBody @Valid CollegeFundPaidDto collegeFundPaid) throws NotFoundException, InvalidDataException {
        LOGGER.info("** CollegeFundPaidsApi - collegeFundPaidsPOST");
        if (collegeFundPaid.id != null) {
            throw new InvalidDataException(400, "No ID field should be provided when creating")
        }
        CollegeFundPaid collegeFundPaidToSave = CollegeFundPaidDto.mapToCollegeFundPaidEntity(collegeFundPaid)
        CollegeFundPaid collegeFundPaidSaved = collegeFundPaidService.save(collegeFundPaidToSave)
        return new ResponseEntity<CollegeFundPaidDto>(CollegeFundPaidDto.mapFromCollegeFundPaidEntity(collegeFundPaidSaved), HttpStatus.CREATED);
    }
    
    /**
     * The collegeFundPaidsCollegeFundPaidIdGet method is used to retrieve an instance of a CollegeFundPaidDto object as identified by the collegeFundPaidId provided
     *
     * @param collegeFundPaidId the collegeFundPaid ID for the CollegeFundPaid object retrieve
     * @return A ResponseEntity with the corresponding CollegeFundPaidDto object
     * @throws NotFoundException if nothing is found then the the NotFoundException is thrown.
     */
    @ApiOperation(value = "Retrieves an indivdual instance of a CollegeFundPaid identified by the collegeFundPaidId", notes = "A getGET request to the CollegeFundPaid instance endpoint will retrieve an instance of a CollegeFundPaid entity as identified by the collegeFundPaidId provided in the URI.", response = CollegeFundPaidDto.class)
    @ApiResponses(value = [
        @ApiResponse(code = 200, message = "Returns a copy of the CollegeFundPaid as identified by the collegeFundPaidId"),
        @ApiResponse(code = 404, message = "Returns an Error object stating that the resource could not be found. This happens if the specified id cannot be found in the database.")
    ])
    @RequestMapping(value = "/{collegeFundPaidId}", produces = ["application/json"], method = RequestMethod.GET)
    public ResponseEntity<CollegeFundPaidDto> collegeFundPaidsCollegeFundPaidIdGet(@ApiParam(value = "The unique ID of the CollegeFundPaid to retrieve", required = true) @PathVariable("collegeFundPaidId") Integer collegeFundPaidId) throws NotFoundException {
        LOGGER.info("** CollegeFundPaidsApi - collegeFundPaidsCollegeFundPaidIdGet");
        CollegeFundPaid collegeFundPaid = collegeFundPaidService.findById(collegeFundPaidId);
        if (collegeFundPaid == null) {
            throw new NotFoundException();
        }
        return new ResponseEntity<CollegeFundPaidDto>(CollegeFundPaidDto.mapFromCollegeFundPaidEntity(collegeFundPaid), HttpStatus.OK);
    }
    
    /**
     * The collegeFundPaidsCollegeFundPaidIdPut is used to update
     *
     * @param collegeFundPaidId the collegeFundPaid ID for the CollegeFundPaid object to update
     * @param collegeFundPaid the new data for the CollegeFundPaid object
     * @return the newly updated CollegeFundPaidDto object
     * @throws NotFoundException if nothing is found then the the NotFoundException is thrown.
     * @throws InvalidDataException if there is an issue with the data provided in the RequestBody then an InvalidDataException is thrown
     */
    @ApiOperation(value = "Used to update a CollegeFundPaid entity", notes = "A PUT request to the CollegeFundPaid instance endpoint with a CollegeFundPaid object in the request body will update an existing CollegeFundPaid entity in the database.", response = CollegeFundPaidDto.class)
    @ApiResponses(value = [
        @ApiResponse(code = 200, message = "Returns a copy of the newly updated CollegeFundPaid object."),
        @ApiResponse(code = 400, message = "Returns an Error object stating the request body does not match the expected input."),
        @ApiResponse(code = 404, message = "Returns an Error object stating that the resource could not be found. This happens if the specified id cannot be found in the database.")
    ])
    @RequestMapping(value = "/{collegeFundPaidId}", produces = ["application/json"], method = RequestMethod.PUT)
    public ResponseEntity<CollegeFundPaidDto> collegeFundPaidsCollegeFundPaidIdPut(
            @ApiParam(value = "The unique ID of the CollegeFundPaid to retrieve", required = true) @PathVariable("collegeFundPaidId") Integer collegeFundPaidId,
            @ApiParam(value = "The CollegeFundPaid object to be created, without the collegeFundPaidId fields", required = true) @RequestBody CollegeFundPaidDto collegeFundPaid) throws NotFoundException, InvalidDataException {
        LOGGER.info("** CollegeFundPaidsApi - collegeFundPaidsPUT");
        if (collegeFundPaidId != collegeFundPaid.id) {
            throw new InvalidDataException()
        }
        CollegeFundPaid collegeFundPaidToSave = CollegeFundPaidDto.mapToCollegeFundPaidEntity(collegeFundPaid)
        CollegeFundPaid collegeFundPaidSaved = collegeFundPaidService.updateCollegeFundPaid(collegeFundPaidToSave)
        return new ResponseEntity<CollegeFundPaidDto>(CollegeFundPaidDto.mapFromCollegeFundPaidEntity(collegeFundPaidSaved), HttpStatus.OK);
    }
}
