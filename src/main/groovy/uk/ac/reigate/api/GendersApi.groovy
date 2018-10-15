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

import uk.ac.reigate.domain.lookup.Gender
import uk.ac.reigate.dto.GenderDto
import uk.ac.reigate.exceptions.InvalidDataException
import uk.ac.reigate.exceptions.NotFoundException
import uk.ac.reigate.model.SearchResult
import uk.ac.reigate.services.GenderService


@Controller
@RequestMapping(value = "/api/genders", produces = [ APPLICATION_JSON_VALUE ])
@Api(value = "/api/genders", description = "the genders API")
public class GendersApi {
    
    private static final Logger LOGGER = Logger.getLogger(GendersApi.class);
    
    @Autowired
    private final GenderService genderService;
    
    /**
     * Default NoArgs constructor
     */
    GendersApi() {}
    
    /**
     * Autowired constructor
     */
    GendersApi(GenderService genderService) {
        this.genderService = genderService;
    }
    
    /**
     * The gendersGet method is used to retrieve a full list of all the GenderDto objects
     *
     * @return A ResponseEntity with the corresponding list of GenderDto objects
     * @throws NotFoundException if nothing is found then the the NotFoundException is thrown.
     */
    @ApiOperation(value = "Collection of Gender entities", notes = "A GET request to the Genders endpoint returns an array of all the genders in the system.", response = GenderDto.class, responseContainer = "List")
    @ApiResponses(value = [
        @ApiResponse(code = 200, message = "An array of genders")
    ])
    @RequestMapping(value = "", produces = ["application/json"], method = RequestMethod.GET)
    public ResponseEntity<List<GenderDto>> gendersGet() throws NotFoundException {
        LOGGER.info("** GendersApi - gendersGet");
        List<Gender> genders = genderService.findAll();
        return new ResponseEntity<List<GenderDto>>(GenderDto.mapFromGendersEntities(genders), HttpStatus.OK);
    }
    
    /**
     * The gendersPost method is used to create a new instance of a Gender from the supplied GenderDto
     *
     * @param gender the GenderDto to use to create the new Gender object
     * @return A ResponseEntity with the newly created Gender object
     * @throws InvalidDataException if there is an issue with the data provided in the RequestBody then an InvalidDataException is thrown
     */
    @ApiOperation(value = "Creates a new Gender entity", notes = "A POST request to the Genders endpoint with a Gender object in the request body will create a new Gender entity in the database.", response = GenderDto.class)
    @ApiResponses(value = [
        @ApiResponse(code = 201, message = "Returns a copy of the created Gender entity including the genderId that has just been created."),
        @ApiResponse(code = 400, message = "Returns an Error object stating the request body does not match the expected input.")
    ])
    @RequestMapping(value = "", produces = ["application/json"], method = RequestMethod.POST)
    public ResponseEntity<GenderDto> gendersPost(@ApiParam(value = "The Gender object to be created, without the genderId fields", required = true) @RequestBody @Valid GenderDto gender) throws NotFoundException, InvalidDataException {
        LOGGER.info("** GendersApi - gendersPOST");
        if (gender.id != null) {
            throw new InvalidDataException(400, "No ID field should be provided when creating")
        }
        Gender genderToSave = GenderDto.mapToGenderEntity(gender)
        Gender genderSaved = genderService.save(genderToSave)
        return new ResponseEntity<GenderDto>(GenderDto.mapFromGenderEntity(genderSaved), HttpStatus.CREATED);
    }
    
    /**
     * The gendersGenderIdGet method is used to retrieve an instance of a GenderDto object as identified by the genderId provided
     *
     * @param genderId the gender ID for the Gender object retrieve
     * @return A ResponseEntity with the corresponding GenderDto object
     * @throws NotFoundException if nothing is found then the the NotFoundException is thrown.
     */
    @ApiOperation(value = "Retrieves an indivdual instance of a Gender identified by the genderId", notes = "A getGET request to the Gender instance endpoint will retrieve an instance of a Gender entity as identified by the genderId provided in the URI.", response = GenderDto.class)
    @ApiResponses(value = [
        @ApiResponse(code = 200, message = "Returns a copy of the Gender as identified by the genderId"),
        @ApiResponse(code = 404, message = "Returns an Error object stating that the resource could not be found. This happens if the specified id cannot be found in the database.")
    ])
    @RequestMapping(value = "/{genderId}", produces = ["application/json"], method = RequestMethod.GET)
    public ResponseEntity<GenderDto> gendersGenderIdGet(@ApiParam(value = "The unique ID of the Gender to retrieve", required = true) @PathVariable("genderId") Integer genderId) throws NotFoundException {
        LOGGER.info("** GendersApi - gendersGenderIdGet");
        Gender gender = genderService.findById(genderId);
        if (gender == null) {
            throw new NotFoundException();
        }
        return new ResponseEntity<GenderDto>(GenderDto.mapFromGenderEntity(gender), HttpStatus.OK);
    }
    
    /**
     * The gendersGenderIdPut is used to update
     *
     * @param genderId the gender ID for the Gender object to update
     * @param gender the new data for the Gender object
     * @return the newly updated GenderDto object
     * @throws NotFoundException if nothing is found then the the NotFoundException is thrown.
     * @throws InvalidDataException if there is an issue with the data provided in the RequestBody then an InvalidDataException is thrown
     */
    @ApiOperation(value = "Used to update a Gender entity", notes = "A PUT request to the Gender instance endpoint with a Gender object in the request body will update an existing Gender entity in the database.", response = GenderDto.class)
    @ApiResponses(value = [
        @ApiResponse(code = 200, message = "Returns a copy of the newly updated Gender object."),
        @ApiResponse(code = 400, message = "Returns an Error object stating the request body does not match the expected input."),
        @ApiResponse(code = 404, message = "Returns an Error object stating that the resource could not be found. This happens if the specified id cannot be found in the database.")
    ])
    @RequestMapping(value = "/{genderId}", produces = ["application/json"], method = RequestMethod.PUT)
    public ResponseEntity<GenderDto> gendersGenderIdPut(
            @ApiParam(value = "The unique ID of the Gender to retrieve", required = true) @PathVariable("genderId") Integer genderId,
            @ApiParam(value = "The Gender object to be created, without the genderId fields", required = true) @RequestBody GenderDto gender) throws NotFoundException, InvalidDataException {
        LOGGER.info("** GendersApi - gendersPUT");
        if (genderId != gender.id) {
            throw new InvalidDataException()
        }
        Gender genderToSave = GenderDto.mapToGenderEntity(gender)
        Gender genderSaved = genderService.updateGender(genderToSave)
        return new ResponseEntity<GenderDto>(GenderDto.mapFromGenderEntity(genderSaved), HttpStatus.OK);
    }
}
