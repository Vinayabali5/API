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

import uk.ac.reigate.domain.lookup.Nationality
import uk.ac.reigate.dto.NationalityDto
import uk.ac.reigate.exceptions.InvalidDataException
import uk.ac.reigate.exceptions.NotFoundException
import uk.ac.reigate.model.SearchResult
import uk.ac.reigate.services.NationalityService


@Controller
@RequestMapping(value = "/api/nationalities", produces = [ APPLICATION_JSON_VALUE ])
@Api(value = "/api/nationalities", description = "the nationalities API")
public class NationalitiesApi {
    
    private static final Logger LOGGER = Logger.getLogger(NationalitiesApi.class);
    
    @Autowired
    private final NationalityService nationalityService;
    
    /**
     * Default NoArgs constructor
     */
    NationalitiesApi() {}
    
    /**
     * Autowired constructor
     */
    NationalitiesApi(NationalityService nationalityService) {
        this.nationalityService = nationalityService;
    }
    
    /**
     * The nationalitiesGet method is used to retrieve a full list of all the NationalityDto objects
     *
     * @return A ResponseEntity with the corresponding list of NationalityDto objects
     * @throws NotFoundException if nothing is found then the the NotFoundException is thrown.
     */
    @ApiOperation(value = "Collection of Nationality entities", notes = "A GET request to the Nationalities endpoint returns an array of all the nationalities in the system.", response = NationalityDto.class, responseContainer = "List")
    @ApiResponses(value = [
        @ApiResponse(code = 200, message = "An array of nationalities")
    ])
    @RequestMapping(value = "", produces = ["application/json"], method = RequestMethod.GET)
    public ResponseEntity<List<NationalityDto>> nationalitiesGet() throws NotFoundException {
        LOGGER.info("** NationalitiesApi - nationalitiesGet");
        List<Nationality> nationalities = nationalityService.findAll();
        return new ResponseEntity<List<NationalityDto>>(NationalityDto.mapFromNationalitiesEntities(nationalities), HttpStatus.OK);
    }
    
    /**
     * The nationalitiesPost method is used to create a new instance of a Nationality from the supplied NationalityDto
     *
     * @param nationality the NationalityDto to use to create the new Nationality object
     * @return A ResponseEntity with the newly created Nationality object
     * @throws InvalidDataException if there is an issue with the data provided in the RequestBody then an InvalidDataException is thrown
     */
    @ApiOperation(value = "Creates a new Nationality entity", notes = "A POST request to the Nationalities endpoint with a Nationality object in the request body will create a new Nationality entity in the database.", response = NationalityDto.class)
    @ApiResponses(value = [
        @ApiResponse(code = 201, message = "Returns a copy of the created Nationality entity including the nationalityId that has just been created."),
        @ApiResponse(code = 400, message = "Returns an Error object stating the request body does not match the expected input.")
    ])
    @RequestMapping(value = "", produces = ["application/json"], method = RequestMethod.POST)
    public ResponseEntity<NationalityDto> nationalitiesPost(@ApiParam(value = "The Nationality object to be created, without the nationalityId fields", required = true) @RequestBody @Valid NationalityDto nationality) throws NotFoundException, InvalidDataException {
        LOGGER.info("** NationalitiesApi - nationalitiesPOST");
        if (nationality.id != null) {
            throw new InvalidDataException(400, "No ID field should be provided when creating")
        }
        Nationality nationalityToSave = NationalityDto.mapToNationalityEntity(nationality)
        Nationality nationalitySaved = nationalityService.save(nationalityToSave)
        return new ResponseEntity<NationalityDto>(NationalityDto.mapFromNationalityEntity(nationalitySaved), HttpStatus.CREATED);
    }
    
    /**
     * The nationalitiesNationalityIdGet method is used to retrieve an instance of a NationalityDto object as identified by the nationalityId provided
     *
     * @param nationalityId the nationality ID for the Nationality object retrieve
     * @return A ResponseEntity with the corresponding NationalityDto object
     * @throws NotFoundException if nothing is found then the the NotFoundException is thrown.
     */
    @ApiOperation(value = "Retrieves an indivdual instance of a Nationality identified by the nationalityId", notes = "A getGET request to the Nationality instance endpoint will retrieve an instance of a Nationality entity as identified by the nationalityId provided in the URI.", response = NationalityDto.class)
    @ApiResponses(value = [
        @ApiResponse(code = 200, message = "Returns a copy of the Nationality as identified by the nationalityId"),
        @ApiResponse(code = 404, message = "Returns an Error object stating that the resource could not be found. This happens if the specified id cannot be found in the database.")
    ])
    @RequestMapping(value = "/{nationalityId}", produces = ["application/json"], method = RequestMethod.GET)
    public ResponseEntity<NationalityDto> nationalitysNationalityIdGet(@ApiParam(value = "The unique ID of the Nationality to retrieve", required = true) @PathVariable("nationalityId") Integer nationalityId) throws NotFoundException {
        LOGGER.info("** NationalitiesApi - nationalitysNationalityIdGet");
        Nationality nationality = nationalityService.findById(nationalityId);
        if (nationality == null) {
            throw new NotFoundException();
        }
        return new ResponseEntity<NationalityDto>(NationalityDto.mapFromNationalityEntity(nationality), HttpStatus.OK);
    }
    
    /**
     * The nationalitiesNationalityIdPut is used to update
     *
     * @param nationalityId the nationality ID for the Nationality object to update
     * @param nationality the new data for the Nationality object
     * @return the newly updated NationalityDto object
     * @throws NotFoundException if nothing is found then the the NotFoundException is thrown.
     * @throws InvalidDataException if there is an issue with the data provided in the RequestBody then an InvalidDataException is thrown
     */
    @ApiOperation(value = "Used to update a Nationality entity", notes = "A PUT request to the Nationality instance endpoint with a Nationality object in the request body will update an existing Nationality entity in the database.", response = NationalityDto.class)
    @ApiResponses(value = [
        @ApiResponse(code = 200, message = "Returns a copy of the newly updated Nationality object."),
        @ApiResponse(code = 400, message = "Returns an Error object stating the request body does not match the expected input."),
        @ApiResponse(code = 404, message = "Returns an Error object stating that the resource could not be found. This happens if the specified id cannot be found in the database.")
    ])
    @RequestMapping(value = "/{nationalityId}", produces = ["application/json"], method = RequestMethod.PUT)
    public ResponseEntity<NationalityDto> nationalitiesNationalityIdPut(
            @ApiParam(value = "The unique ID of the Nationality to retrieve", required = true) @PathVariable("nationalityId") Integer nationalityId,
            @ApiParam(value = "The Nationality object to be created, without the nationalityId fields", required = true) @RequestBody NationalityDto nationality) throws NotFoundException, InvalidDataException {
        LOGGER.info("** NationalitiesApi - nationalitiesPUT");
        if (nationalityId != nationality.id) {
            throw new InvalidDataException()
        }
        Nationality nationalityToSave = NationalityDto.mapToNationalityEntity(nationality)
        Nationality nationalitySaved = nationalityService.updateNationality(nationalityToSave)
        return new ResponseEntity<NationalityDto>(NationalityDto.mapFromNationalityEntity(nationalitySaved), HttpStatus.OK);
    }
}
