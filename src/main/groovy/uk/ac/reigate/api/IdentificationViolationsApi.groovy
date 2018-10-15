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

import uk.ac.reigate.domain.academic.IdentificationViolation
import uk.ac.reigate.dto.IdentificationViolationDto
import uk.ac.reigate.exceptions.InvalidDataException
import uk.ac.reigate.exceptions.NotFoundException
import uk.ac.reigate.services.IdentificationViolationService

import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE


@Controller
@RequestMapping(value = "/api/", produces = [ APPLICATION_JSON_VALUE ])
@Api(value = "/api/", description = "the id violations API")
public class IdentificationViolationsApi {
    
    private static final Logger LOGGER = Logger.getLogger(IdentificationViolationsApi.class);
    
    @Autowired
    private final IdentificationViolationService identificationViolationService;
    
    /**
     * Default NoArgs constructor
     */
    IdentificationViolationsApi() {}
    
    /**
     * Autowired constructor
     */
    IdentificationViolationsApi(IdentificationViolationService identificationViolationService) {
        this.identificationViolationService = identificationViolationService;
    }
    
    /**
     * This method is used to retrieve a full list of all the IdentificationViolationDto objects
     *
     * @return A ResponseEntity with the corresponding list of DepartmentDto objects
     * @throws NotFoundException if nothing is found then the the NotFoundException is thrown.
     */
    @ApiOperation(value = "Collection of Department entities", notes = "A GET request to the Departments endpoint returns an array of all the idViolations in the system.", response = IdentificationViolationDto.class, responseContainer = "List")
    @ApiResponses(value = [
        @ApiResponse(code = 200, message = "An array of idViolations")
    ])
    @RequestMapping(value = "/id-violations", produces = ["application/json"], method = RequestMethod.GET)
    public ResponseEntity<List<IdentificationViolationDto>> getAll() throws NotFoundException {
        LOGGER.info("** IdentificationViolationsApi - getAll");
        List<IdentificationViolation> idViolations = identificationViolationService.findAll();
        return new ResponseEntity<List<IdentificationViolationDto>>(IdentificationViolationDto.mapFromEntities(idViolations), HttpStatus.OK);
    }
    
    /**
     * This method is used to create a new instance of a IdentificationViolation from the supplied IdentificationViolationDto
     *
     * @param idViolation the IdentificationViolationDto to use to create the new IdentificationViolation object
     * @return A ResponseEntity with the newly created IdentificationViolation object
     * @throws InvalidDataException if there is an issue with the data provided in the RequestBody then an InvalidDataException is thrown
     */
    @ApiOperation(value = "Creates a new IdentificationViolation entity", notes = "A POST request to the IdentificationViolations endpoint with a IdentificationViolation object in the request body will create a new IdentificationViolation entity in the database.", response = IdentificationViolationDto.class)
    @ApiResponses(value = [
        @ApiResponse(code = 201, message = "Returns a copy of the created IdentificationViolation entity including the idViolationId that has just been created."),
        @ApiResponse(code = 400, message = "Returns an Error object stating the request body does not match the expected input.")
    ])
    @RequestMapping(value = "/id-violations", produces = ["application/json"], method = RequestMethod.POST)
    public ResponseEntity<IdentificationViolationDto> create(@ApiParam(value = "The IdentificationViolation object to be created, without the idViolationId fields", required = true) @RequestBody @Valid IdentificationViolationDto idViolation) throws NotFoundException, InvalidDataException {
        LOGGER.info("** IdentificationViolationsApi - create");
        if (idViolation.id != null) {
            throw new InvalidDataException(400, "No ID field should be provided when creating")
        }
        IdentificationViolation idViolationToSave = identificationViolationService.convert(idViolation)
        IdentificationViolation idViolationSaved = identificationViolationService.save(idViolationToSave)
        return new ResponseEntity<IdentificationViolationDto>(new IdentificationViolationDto(idViolationToSave), HttpStatus.CREATED);
    }
    
    /**
     * This method is used to retrieve an instance of a IdentificationViolationDto object as identified by the idViolationId provided
     *
     * @param idViolationId the idViolation ID for the IdentificationViolation object retrieve
     * @return A ResponseEntity with the corresponding IdentificationViolationDto object
     * @throws NotFoundException if nothing is found then the the NotFoundException is thrown.
     */
    @ApiOperation(value = "Retrieves an indivdual instance of a IdentificationViolation identified by the idViolationId", notes = "A getGET request to the IdentificationViolation instance endpoint will retrieve an instance of a IdentificationViolation entity as identified by the idViolationId provided in the URI.", response = IdentificationViolationDto.class)
    @ApiResponses(value = [
        @ApiResponse(code = 200, message = "Returns a copy of the IdentificationViolation as identified by the idViolationId"),
        @ApiResponse(code = 404, message = "Returns an Error object stating that the resource could not be found. This happens if the specified id cannot be found in the database.")
    ])
    @RequestMapping(value = "/id-violations/{idViolationId}", produces = ["application/json"], method = RequestMethod.GET)
    public ResponseEntity<IdentificationViolationDto> getById(@ApiParam(value = "The unique ID of the IdentificationViolation to retrieve", required = true) @PathVariable("idViolationId") Integer idViolationId) throws NotFoundException {
        LOGGER.info("** IdentificationViolationsApi - getById");
        IdentificationViolation idViolation = identificationViolationService.findById(idViolationId);
        if (idViolation == null) {
            throw new NotFoundException();
        }
        return new ResponseEntity<IdentificationViolationDto>(new IdentificationViolationDto(idViolation), HttpStatus.OK);
    }
    
    /**
     * This method is used to update an IdentificationViolation object in the database
     *
     * @param idViolationId the idViolation ID for the IdentificationViolation object to update
     * @param idViolation the new data for the IdentificationViolation object
     * @return the newly updated IdentificationViolationDto object
     * @throws NotFoundException if nothing is found then the the NotFoundException is thrown.
     * @throws InvalidDataException if there is an issue with the data provided in the RequestBody then an InvalidDataException is thrown
     */
    @ApiOperation(value = "Used to update a IdentificationViolation entity", notes = "A PUT request to the IdentificationViolation instance endpoint with a IdentificationViolation object in the request body will update an existing IdentificationViolation entity in the database.", response = IdentificationViolationDto.class)
    @ApiResponses(value = [
        @ApiResponse(code = 200, message = "Returns a copy of the newly updated IdentificationViolation object."),
        @ApiResponse(code = 400, message = "Returns an Error object stating the request body does not match the expected input."),
        @ApiResponse(code = 404, message = "Returns an Error object stating that the resource could not be found. This happens if the specified id cannot be found in the database.")
    ])
    @RequestMapping(value = "/id-violations/{idViolationId}", produces = ["application/json"], method = RequestMethod.PUT)
    public ResponseEntity<IdentificationViolationDto> update(
            @ApiParam(value = "The unique ID of the IdentificationViolation to retrieve", required = true) @PathVariable("idViolationId") Integer idViolationId,
            @ApiParam(value = "The IdentificationViolation object to be created, without the idViolationId fields", required = true) @RequestBody IdentificationViolationDto idViolation) throws NotFoundException, InvalidDataException {
        LOGGER.info("** IdentificationViolationsApi - update");
        if (idViolationId != idViolation.id) {
            throw new InvalidDataException()
        }
        IdentificationViolation idViolationToSave = identificationViolationService.convert(idViolation)
        IdentificationViolation idViolationSaved = identificationViolationService.save(idViolationToSave)
        return new ResponseEntity<IdentificationViolationDto>(new IdentificationViolationDto(idViolationSaved), HttpStatus.OK);
    }
    
    /**
     * This method is used to retrieve a full list of all the IdentificationViolationDto objects
     *
     * @return A ResponseEntity with the corresponding list of DepartmentDto objects
     * @throws NotFoundException if nothing is found then the the NotFoundException is thrown.
     */
    @ApiOperation(value = "Collection of IdentificationViolation entities for a given student",
    notes = "A GET request to the IdentificationViolation endpoint returns an array of all the idViolations in the system.",
    response = IdentificationViolationDto.class,
    responseContainer = "List"
    )
    @ApiResponses(value = [
        @ApiResponse(code = 200, message = "An array of idViolations")
    ])
    @RequestMapping(value = "/students/{studentId}/id-violations", produces = ["application/json"], method = RequestMethod.GET)
    public ResponseEntity<List<IdentificationViolationDto>> getAllByStudentId(
            @ApiParam(value = "The studentId to retrieve the idViolations", required = true)
            @PathVariable("studentId") Integer studentId
    ) throws NotFoundException {
        LOGGER.info("** IdentificationViolationsApi - getAllByuStudentId");
        List<IdentificationViolation> idViolations = identificationViolationService.findByStudentId(studentId);
        return new ResponseEntity<List<IdentificationViolationDto>>(IdentificationViolationDto.mapFromEntities(idViolations), HttpStatus.OK);
    }
    
    /**
     * This method is used to delete the idViolation using IdViolationId
     *
     * @throws NotFoundException if nothing is found then the NotFoundException is thrown
     * @return A new ResponseEntity 
     */
    @RequestMapping(value = "/id-violations/{idViolationId}", produces = ["application/json"], method = RequestMethod.DELETE)
    public ResponseEntity<?> deleteById (
            @ApiParam(value = "The unique ID of the idViolation to retrieve", required = true)
            @PathVariable("idViolationId") Integer idViolationId
    ) throws NotFoundException {
        LOGGER.info("** IdentificationViolationsApi - deleteById: " + idViolationId);
        IdentificationViolation idViolation = identificationViolationService.findById(idViolationId);
        if (idViolation == null) {
            throw new NotFoundException();
        }
        identificationViolationService.delete(idViolation)
        return new ResponseEntity<?>(HttpStatus.NO_CONTENT);
    }
}
