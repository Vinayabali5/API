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

import uk.ac.reigate.domain.lookup.Ethnicity
import uk.ac.reigate.dto.EthnicityDto
import uk.ac.reigate.exceptions.InvalidDataException
import uk.ac.reigate.exceptions.NotFoundException
import uk.ac.reigate.model.SearchResult
import uk.ac.reigate.services.EthnicityService


@Controller
@RequestMapping(value = "/api/ethnicities", produces = [ APPLICATION_JSON_VALUE ])
@Api(value = "/api/ethnicities", description = "the ethnicities API")
public class EthnicitiesApi {
    
    private static final Logger LOGGER = Logger.getLogger(EthnicitiesApi.class);
    
    @Autowired
    private final EthnicityService ethnicityService;
    
    /**
     * Default NoArgs constructor
     */
    EthnicitiesApi() {}
    
    /**
     * Autowired constructor
     */
    EthnicitiesApi(EthnicityService ethnicityService) {
        this.ethnicityService = ethnicityService;
    }
    
    /**
     * The ethnicitiesGet method is used to retrieve a full list of all the EthnicityDto objects
     *
     * @return A ResponseEntity with the corresponding list of EthnicityDto objects
     * @throws NotFoundException if nothing is found then the the NotFoundException is thrown.
     */
    @ApiOperation(value = "Collection of Ethnicity entities", notes = "A GET request to the Ethnicities endpoint returns an array of all the ethnicities in the system.", response = EthnicityDto.class, responseContainer = "List")
    @ApiResponses(value = [
        @ApiResponse(code = 200, message = "An array of ethnicities")
    ])
    @RequestMapping(value = "", produces = ["application/json"], method = RequestMethod.GET)
    public ResponseEntity<List<EthnicityDto>> ethnicitiesGet() throws NotFoundException {
        LOGGER.info("** EthnicitiesApi - ethnicitiesGet");
        List<Ethnicity> ethnicities = ethnicityService.findAll();
        return new ResponseEntity<List<EthnicityDto>>(EthnicityDto.mapFromEthnicitiesEntities(ethnicities), HttpStatus.OK);
    }
    
    /**
     * The ethnicitiesPost method is used to create a new instance of a Ethnicity from the supplied EthnicityDto
     *
     * @param ethnicity the EthnicityDto to use to create the new Ethnicity object
     * @return A ResponseEntity with the newly created Ethnicity object
     * @throws InvalidDataException if there is an issue with the data provided in the RequestBody then an InvalidDataException is thrown
     */
    @ApiOperation(value = "Creates a new Ethnicity entity", notes = "A POST request to the Ethnicities endpoint with a Ethnicity object in the request body will create a new Ethnicity entity in the database.", response = EthnicityDto.class)
    @ApiResponses(value = [
        @ApiResponse(code = 201, message = "Returns a copy of the created Ethnicity entity including the ethnicityId that has just been created."),
        @ApiResponse(code = 400, message = "Returns an Error object stating the request body does not match the expected input.")
    ])
    @RequestMapping(value = "", produces = ["application/json"], method = RequestMethod.POST)
    public ResponseEntity<EthnicityDto> ethnicitiesPost(@ApiParam(value = "The Ethnicity object to be created, without the ethnicityId fields", required = true) @RequestBody @Valid EthnicityDto ethnicity) throws NotFoundException, InvalidDataException {
        LOGGER.info("** EthnicitiesApi - ethnicitiesPOST");
        if (ethnicity.id != null) {
            throw new InvalidDataException(400, "No ID field should be provided when creating")
        }
        Ethnicity ethnicityToSave = EthnicityDto.mapToEthnicityEntity(ethnicity)
        Ethnicity ethnicitySaved = ethnicityService.save(ethnicityToSave)
        return new ResponseEntity<EthnicityDto>(EthnicityDto.mapFromEthnicityEntity(ethnicitySaved), HttpStatus.CREATED);
    }
    
    /**
     * The ethnicitiesEthnicityIdGet method is used to retrieve an instance of a EthnicityDto object as identified by the ethnicityId provided
     *
     * @param ethnicityId the ethnicity ID for the Ethnicity object retrieve
     * @return A ResponseEntity with the corresponding EthnicityDto object
     * @throws NotFoundException if nothing is found then the the NotFoundException is thrown.
     */
    @ApiOperation(value = "Retrieves an indivdual instance of a Ethnicity identified by the ethnicityId", notes = "A getGET request to the Ethnicity instance endpoint will retrieve an instance of a Ethnicity entity as identified by the ethnicityId provided in the URI.", response = EthnicityDto.class)
    @ApiResponses(value = [
        @ApiResponse(code = 200, message = "Returns a copy of the Ethnicity as identified by the ethnicityId"),
        @ApiResponse(code = 404, message = "Returns an Error object stating that the resource could not be found. This happens if the specified id cannot be found in the database.")
    ])
    @RequestMapping(value = "/{ethnicityId}", produces = ["application/json"], method = RequestMethod.GET)
    public ResponseEntity<EthnicityDto> ethnicitysEthnicityIdGet(@ApiParam(value = "The unique ID of the Ethnicity to retrieve", required = true) @PathVariable("ethnicityId") Integer ethnicityId) throws NotFoundException {
        LOGGER.info("** EthnicitiesApi - ethnicitysEthnicityIdGet");
        Ethnicity ethnicity = ethnicityService.findById(ethnicityId);
        if (ethnicity == null) {
            throw new NotFoundException();
        }
        return new ResponseEntity<EthnicityDto>(EthnicityDto.mapFromEthnicityEntity(ethnicity), HttpStatus.OK);
    }
    
    /**
     * The ethnicitiesEthnicityIdPut is used to update
     *
     * @param ethnicityId the ethnicity ID for the Ethnicity object to update
     * @param ethnicity the new data for the Ethnicity object
     * @return the newly updated EthnicityDto object
     * @throws NotFoundException if nothing is found then the the NotFoundException is thrown.
     * @throws InvalidDataException if there is an issue with the data provided in the RequestBody then an InvalidDataException is thrown
     */
    @ApiOperation(value = "Used to update a Ethnicity entity", notes = "A PUT request to the Ethnicity instance endpoint with a Ethnicity object in the request body will update an existing Ethnicity entity in the database.", response = EthnicityDto.class)
    @ApiResponses(value = [
        @ApiResponse(code = 200, message = "Returns a copy of the newly updated Ethnicity object."),
        @ApiResponse(code = 400, message = "Returns an Error object stating the request body does not match the expected input."),
        @ApiResponse(code = 404, message = "Returns an Error object stating that the resource could not be found. This happens if the specified id cannot be found in the database.")
    ])
    @RequestMapping(value = "/{ethnicityId}", produces = ["application/json"], method = RequestMethod.PUT)
    public ResponseEntity<EthnicityDto> ethnicitiesEthnicityIdPut(
            @ApiParam(value = "The unique ID of the Ethnicity to retrieve", required = true) @PathVariable("ethnicityId") Integer ethnicityId,
            @ApiParam(value = "The Ethnicity object to be created, without the ethnicityId fields", required = true) @RequestBody EthnicityDto ethnicity) throws NotFoundException, InvalidDataException {
        LOGGER.info("** EthnicitiesApi - ethnicityPUT");
        if (ethnicityId != ethnicity.id) {
            throw new InvalidDataException()
        }
        Ethnicity ethnicityToSave = EthnicityDto.mapToEthnicityEntity(ethnicity)
        Ethnicity ethnicitySaved = ethnicityService.updateEthnicity(ethnicityToSave)
        return new ResponseEntity<EthnicityDto>(EthnicityDto.mapFromEthnicityEntity(ethnicitySaved), HttpStatus.OK);
    }
}
