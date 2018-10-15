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

import uk.ac.reigate.domain.ilp.ILPInterview;
import uk.ac.reigate.domain.ilp.Target
import uk.ac.reigate.dto.TargetDto;
import uk.ac.reigate.dto.TargetDto
import uk.ac.reigate.exceptions.InvalidDataException
import uk.ac.reigate.exceptions.NotFoundException
import uk.ac.reigate.model.SearchResult
import uk.ac.reigate.services.ILPInterviewService
import uk.ac.reigate.services.TargetService


@Controller
@RequestMapping(value = "/api/targets", produces = [ APPLICATION_JSON_VALUE ])
@Api(value = "/api/targets", description = "the targets API")
public class TargetsApi {
    
    private static final Logger LOGGER = Logger.getLogger(TargetsApi.class);
    
    @Autowired
    private final TargetService targetService;
    
    @Autowired
    private final ILPInterviewService iLPInterviewService;
    
    /**
     * Default NoArgs constructor
     */
    TargetsApi() {}
    
    /**
     * Autowired constructor
     */
    TargetsApi(TargetService targetService) {
        this.targetService = targetService;
    }
    
    /**
     * The targetsGet method is used to retrieve a full list of all the TargetDto objects
     *
     * @return A ResponseEntity with the corresponding list of TargetDto objects
     * @throws NotFoundException if nothing is found then the the NotFoundException is thrown.
     */
    @ApiOperation(value = "Collection of Target entities", notes = "A GET request to the Targets endpoint returns an array of all the targets in the system.", response = TargetDto.class, responseContainer = "List")
    @ApiResponses(value = [
        @ApiResponse(code = 200, message = "An array of targets")
    ])
    @RequestMapping(value = "", produces = ["application/json"], method = RequestMethod.GET)
    public ResponseEntity<List<TargetDto>> targetsGet() throws NotFoundException {
        LOGGER.info("** TargetsApi - targetsGet");
        List<Target> targets = targetService.findAll();
        return new ResponseEntity<List<TargetDto>>(TargetDto.mapFromTargetsEntities(targets), HttpStatus.OK);
    }
    
    @ApiOperation(value = "Creates a new Target entity", notes = "A POST request to the Targets endpoint with a Target object in the request body will create a new Target entity in the database.", response = TargetDto.class)
    @ApiResponses(value = [
        @ApiResponse(code = 201, message = "Returns a copy of the created Target entity including the targetId that has just been created."),
        @ApiResponse(code = 400, message = "Returns an Error object stating the request body does not match the expected input.")
    ])
    @RequestMapping(value = "", produces = ["application/json"], method = RequestMethod.POST)
    public ResponseEntity<TargetDto> targetsPost(
            @ApiParam(value = "The Target object to be created, without the targetId fields", required = true)
            @RequestBody @Valid TargetDto target
    ) throws NotFoundException, InvalidDataException {
        LOGGER.info("** TargetsApi - targetsPOST");
        if (target.id != null) {
            throw new InvalidDataException(400, "No ID field should be provided when creating")
        }
        ILPInterview interview
        if(target.interviewId != null) {
            interview = iLPInterviewService.findById(target.interviewId)
        }
        
        Target targetToSave = TargetDto.mapToTargetEntity(target, interview)
        Target targetSaved = targetService.save(targetToSave)
        return new ResponseEntity<TargetDto>(TargetDto.mapFromTargetEntity(targetSaved), HttpStatus.CREATED);
    }
    
    /**
     * The targetsTargetIdGet method is used to retrieve an instance of a TargetDto object as identified by the targetId provided
     *
     * @param targetId the target ID for the Target object retrieve
     * @return A ResponseEntity with the corresponding TargetDto object
     * @throws NotFoundException if nothing is found then the the NotFoundException is thrown.
     */
    @ApiOperation(value = "Retrieves an indivdual instance of a Target identified by the targetId", notes = "A getGET request to the Target instance endpoint will retrieve an instance of a Target entity as identified by the targetId provided in the URI.", response = TargetDto.class)
    @ApiResponses(value = [
        @ApiResponse(code = 200, message = "Returns a copy of the Target as identified by the targetId"),
        @ApiResponse(code = 404, message = "Returns an Error object stating that the resource could not be found. This happens if the specified id cannot be found in the database.")
    ])
    @RequestMapping(value = "/{targetId}", produces = ["application/json"], method = RequestMethod.GET)
    public ResponseEntity<TargetDto> targetsTargetIdGet(@ApiParam(value = "The unique ID of the Target to retrieve", required = true) @PathVariable("targetId") Integer targetId) throws NotFoundException {
        LOGGER.info("** TargetsApi - targetsTargetIdGet");
        Target target = targetService.findById(targetId);
        if (target == null) {
            throw new NotFoundException();
        }
        return new ResponseEntity<TargetDto>(TargetDto.mapFromTargetEntity(target), HttpStatus.OK);
    }
    
    /**
     * The targetsTargetIdPut is used to update
     *
     * @param targetId the target ID for the Target object to update
     * @param target the new data for the Target object
     * @return the newly updated TargetDto object
     * @throws NotFoundException if nothing is found then the the NotFoundException is thrown.
     * @throws InvalidDataException if there is an issue with the data provided in the RequestBody then an InvalidDataException is thrown
     */
    @ApiOperation(value = "Used to update a Target entity", notes = "A PUT request to the Target instance endpoint with a Target object in the request body will update an existing Target entity in the database.", response = TargetDto.class)
    @ApiResponses(value = [
        @ApiResponse(code = 200, message = "Returns a copy of the newly updated Target object."),
        @ApiResponse(code = 400, message = "Returns an Error object stating the request body does not match the expected input."),
        @ApiResponse(code = 404, message = "Returns an Error object stating that the resource could not be found. This happens if the specified id cannot be found in the database.")
    ])
    @RequestMapping(value = "/{targetId}", produces = ["application/json"], method = RequestMethod.PUT)
    public ResponseEntity<TargetDto> targetsTargetIdPut(
            @ApiParam(value = "The unique ID of the Target to retrieve", required = true) @PathVariable("targetId") Integer targetId,
            @ApiParam(value = "The Target object to be created, without the targetId fields", required = true) @RequestBody TargetDto target) throws NotFoundException, InvalidDataException {
        LOGGER.info("** TargetsApi - targetsPUT");
        if (targetId != target.id) {
            throw new InvalidDataException()
        }
        ILPInterview interview
        if(target.interviewId != null) {
            interview = iLPInterviewService.findById(target.interviewId)
        }
        
        Target targetToSave = TargetDto.mapToTargetEntity(target, interview)
        Target targetSaved = targetService.save(targetToSave)
        return new ResponseEntity<TargetDto>(TargetDto.mapFromTargetEntity(targetSaved), HttpStatus.OK);
    }
}
