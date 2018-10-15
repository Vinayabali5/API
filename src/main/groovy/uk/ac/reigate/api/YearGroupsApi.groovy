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

import uk.ac.reigate.domain.lookup.YearGroup
import uk.ac.reigate.dto.YearGroupDto
import uk.ac.reigate.exceptions.InvalidDataException
import uk.ac.reigate.exceptions.NotFoundException
import uk.ac.reigate.model.SearchResult
import uk.ac.reigate.services.YearGroupService


@Controller
@RequestMapping(value = ["/api/yearGroups", "/api/year-groups"], produces = [ APPLICATION_JSON_VALUE ])
@Api(value = "/api/yearGroups", description = "the yearGroups API")
public class YearGroupsApi {
    
    private static final Logger LOGGER = Logger.getLogger(YearGroupsApi.class);
    
    @Autowired
    private final YearGroupService yearGroupService;
    
    /**
     * Default NoArgs constructor
     */
    YearGroupsApi() {}
    
    /**
     * Autowired constructor
     */
    YearGroupsApi(YearGroupService yearGroupService) {
        this.yearGroupService = yearGroupService;
    }
    
    /**
     * The yearGroupsGet method is used to retrieve a full list of all the YearGroupDto objects
     *
     * @return A ResponseEntity with the corresponding list of YearGroupDto objects
     * @throws NotFoundException if nothing is found then the the NotFoundException is thrown.
     */
    @ApiOperation(value = "Collection of YearGroup entities", notes = "A GET request to the YearGroups endpoint returns an array of all the yearGroups in the system.", response = YearGroupDto.class, responseContainer = "List")
    @ApiResponses(value = [
        @ApiResponse(code = 200, message = "An array of yearGroups")
    ])
    @RequestMapping(value = "", produces = ["application/json"], method = RequestMethod.GET)
    public ResponseEntity<List<YearGroupDto>> yearGroupsGet() throws NotFoundException {
        LOGGER.info("** YearGroupsApi - yearGroupsGet");
        List<YearGroup> yearGroups = yearGroupService.findAll();
        return new ResponseEntity<List<YearGroupDto>>(YearGroupDto.mapFromYearGroupsEntities(yearGroups), HttpStatus.OK);
    }
    
    /**
     * The yearGroupsPost method is used to create a new instance of a YearGroup from the supplied YearGroupDto
     *
     * @param yearGroup the YearGroupDto to use to create the new YearGroup object
     * @return A ResponseEntity with the newly created YearGroup object
     * @throws InvalidDataException if there is an issue with the data provided in the RequestBody then an InvalidDataException is thrown
     */
    @ApiOperation(value = "Creates a new YearGroup entity", notes = "A POST request to the YearGroups endpoint with a YearGroup object in the request body will create a new YearGroup entity in the database.", response = YearGroupDto.class)
    @ApiResponses(value = [
        @ApiResponse(code = 201, message = "Returns a copy of the created YearGroup entity including the yearGroupId that has just been created."),
        @ApiResponse(code = 400, message = "Returns an Error object stating the request body does not match the expected input.")
    ])
    @RequestMapping(value = "", produces = ["application/json"], method = RequestMethod.POST)
    public ResponseEntity<YearGroupDto> yearGroupsPost(@ApiParam(value = "The YearGroup object to be created, without the yearGroupId fields", required = true) @RequestBody @Valid YearGroupDto yearGroup) throws NotFoundException, InvalidDataException {
        LOGGER.info("** YearGroupsApi - yearGroupsPOST");
        if (yearGroup.id != null) {
            throw new InvalidDataException(400, "No ID field should be provided when creating")
        }
        YearGroup yearGroupToSave = YearGroupDto.mapToYearGroupEntity(yearGroup)
        YearGroup yearGroupSaved = yearGroupService.save(yearGroupToSave)
        return new ResponseEntity<YearGroupDto>(YearGroupDto.mapFromYearGroupEntity(yearGroupSaved), HttpStatus.CREATED);
    }
    
    /**
     * The yearGroupsYearGroupIdGet method is used to retrieve an instance of a YearGroupDto object as identified by the yearGroupId provided
     *
     * @param yearGroupId the yearGroup ID for the YearGroup object retrieve
     * @return A ResponseEntity with the corresponding YearGroupDto object
     * @throws NotFoundException if nothing is found then the the NotFoundException is thrown.
     */
    @ApiOperation(value = "Retrieves an indivdual instance of a YearGroup identified by the yearGroupId", notes = "A getGET request to the YearGroup instance endpoint will retrieve an instance of a YearGroup entity as identified by the yearGroupId provided in the URI.", response = YearGroupDto.class)
    @ApiResponses(value = [
        @ApiResponse(code = 200, message = "Returns a copy of the YearGroup as identified by the yearGroupId"),
        @ApiResponse(code = 404, message = "Returns an Error object stating that the resource could not be found. This happens if the specified id cannot be found in the database.")
    ])
    @RequestMapping(value = "/{yearGroupId}", produces = ["application/json"], method = RequestMethod.GET)
    public ResponseEntity<YearGroupDto> yearGroupsYearGroupIdGet(@ApiParam(value = "The unique ID of the YearGroup to retrieve", required = true) @PathVariable("yearGroupId") Integer yearGroupId) throws NotFoundException {
        LOGGER.info("** YearGroupsApi - yearGroupsYearGroupIdGet");
        YearGroup yearGroup = yearGroupService.findById(yearGroupId);
        if (yearGroup == null) {
            throw new NotFoundException();
        }
        return new ResponseEntity<YearGroupDto>(YearGroupDto.mapFromYearGroupEntity(yearGroup), HttpStatus.OK);
    }
    
    /**
     * The yearGroupsYearGroupIdPut is used to update
     *
     * @param yearGroupId the yearGroup ID for the YearGroup object to update
     * @param yearGroup the new data for the YearGroup object
     * @return the newly updated YearGroupDto object
     * @throws NotFoundException if nothing is found then the the NotFoundException is thrown.
     * @throws InvalidDataException if there is an issue with the data provided in the RequestBody then an InvalidDataException is thrown
     */
    @ApiOperation(value = "Used to update a YearGroup entity", notes = "A PUT request to the YearGroup instance endpoint with a YearGroup object in the request body will update an existing YearGroup entity in the database.", response = YearGroupDto.class)
    @ApiResponses(value = [
        @ApiResponse(code = 200, message = "Returns a copy of the newly updated YearGroup object."),
        @ApiResponse(code = 400, message = "Returns an Error object stating the request body does not match the expected input."),
        @ApiResponse(code = 404, message = "Returns an Error object stating that the resource could not be found. This happens if the specified id cannot be found in the database.")
    ])
    @RequestMapping(value = "/{yearGroupId}", produces = ["application/json"], method = RequestMethod.PUT)
    public ResponseEntity<YearGroupDto> yearGroupsYearGroupIdPut(
            @ApiParam(value = "The unique ID of the YearGroup to retrieve", required = true) @PathVariable("yearGroupId") Integer yearGroupId,
            @ApiParam(value = "The YearGroup object to be created, without the yearGroupId fields", required = true) @RequestBody YearGroupDto yearGroup) throws NotFoundException, InvalidDataException {
        LOGGER.info("** YearGroupsApi - yearGroupsPUT");
        if (yearGroupId != yearGroup.id) {
            throw new InvalidDataException()
        }
        YearGroup yearGroupToSave = YearGroupDto.mapToYearGroupEntity(yearGroup)
        YearGroup yearGroupSaved = yearGroupService.updateYearGroup(yearGroupToSave)
        return new ResponseEntity<YearGroupDto>(YearGroupDto.mapFromYearGroupEntity(yearGroupSaved), HttpStatus.OK);
    }
}
