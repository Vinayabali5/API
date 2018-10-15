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

import uk.ac.reigate.domain.system.Setting
import uk.ac.reigate.dto.SettingDto
import uk.ac.reigate.exceptions.InvalidDataException
import uk.ac.reigate.exceptions.NotFoundException
import uk.ac.reigate.model.SearchResult
import uk.ac.reigate.services.SettingService


@Controller
@RequestMapping(value = "/api/settings", produces = [ APPLICATION_JSON_VALUE ])
@Api(value = "/api/settings", description = "the settings API")
public class SettingsApi {
    
    private static final Logger LOGGER = Logger.getLogger(SettingsApi.class);
    
    @Autowired
    private final SettingService settingService;
    
    /**
     * Default NoArgs constructor
     */
    SettingsApi() {}
    
    /**
     * Autowired constructor
     */
    SettingsApi(SettingService settingService) {
        this.settingService = settingService;
    }
    
    /**
     * The settingsGet method is used to retrieve a full list of all the SettingDto objects
     *
     * @return A ResponseEntity with the corresponding list of SettingDto objects
     * @throws NotFoundException if nothing is found then the the NotFoundException is thrown.
     */
    @ApiOperation(value = "Collection of Setting entities", notes = "A GET request to the Settings endpoint returns an array of all the settings in the system.", response = SettingDto.class, responseContainer = "List")
    @ApiResponses(value = [
        @ApiResponse(code = 200, message = "An array of settings")
    ])
    @RequestMapping(value = "", produces = ["application/json"], method = RequestMethod.GET)
    public ResponseEntity<List<SettingDto>> settingsGet() throws NotFoundException {
        LOGGER.info("** SettingsApi - settingsGet");
        List<Setting> settings = settingService.findAll();
        return new ResponseEntity<List<SettingDto>>(SettingDto.mapFromSettingsEntities(settings), HttpStatus.OK);
    }
    
    /**
     * The settingsPost method is used to create a new instance of a Setting from the supplied SettingDto
     *
     * @param setting the SettingDto to use to create the new Setting object
     * @return A ResponseEntity with the newly created Setting object
     * @throws InvalidDataException if there is an issue with the data provided in the RequestBody then an InvalidDataException is thrown
     */
    @ApiOperation(value = "Creates a new Setting entity", notes = "A POST request to the Settings endpoint with a Setting object in the request body will create a new Setting entity in the database.", response = SettingDto.class)
    @ApiResponses(value = [
        @ApiResponse(code = 201, message = "Returns a copy of the created Setting entity including the settingId that has just been created."),
        @ApiResponse(code = 400, message = "Returns an Error object stating the request body does not match the expected input.")
    ])
    @RequestMapping(value = "", produces = ["application/json"], method = RequestMethod.POST)
    public ResponseEntity<SettingDto> settingsPost(@ApiParam(value = "The Setting object to be created, without the settingId fields", required = true) @RequestBody @Valid SettingDto setting) throws NotFoundException, InvalidDataException {
        LOGGER.info("** SettingsApi - settingsPOST");
        if (setting.id != null) {
            throw new InvalidDataException(400, "No ID field should be provided when creating")
        }
        Setting settingToSave = SettingDto.mapToSettingEntity(setting)
        Setting settingSaved = settingService.save(settingToSave)
        return new ResponseEntity<SettingDto>(SettingDto.mapFromSettingEntity(settingSaved), HttpStatus.CREATED);
    }
    
    /**
     * The settingsSettingIdGet method is used to retrieve an instance of a SettingDto object as identified by the settingId provided
     *
     * @param settingId the setting ID for the Setting object retrieve
     * @return A ResponseEntity with the corresponding SettingDto object
     * @throws NotFoundException if nothing is found then the the NotFoundException is thrown.
     */
    @ApiOperation(value = "Retrieves an indivdual instance of a Setting identified by the settingId", notes = "A getGET request to the Setting instance endpoint will retrieve an instance of a Setting entity as identified by the settingId provided in the URI.", response = SettingDto.class)
    @ApiResponses(value = [
        @ApiResponse(code = 200, message = "Returns a copy of the Setting as identified by the settingId"),
        @ApiResponse(code = 404, message = "Returns an Error object stating that the resource could not be found. This happens if the specified id cannot be found in the database.")
    ])
    @RequestMapping(value = "/{settingId}", produces = ["application/json"], method = RequestMethod.GET)
    public ResponseEntity<SettingDto> settingsSettingIdGet(@ApiParam(value = "The unique ID of the Setting to retrieve", required = true) @PathVariable("settingId") Integer settingId) throws NotFoundException {
        LOGGER.info("** SettingsApi - settingsSettingIdGet");
        Setting setting = settingService.findById(settingId);
        if (setting == null) {
            throw new NotFoundException();
        }
        return new ResponseEntity<SettingDto>(SettingDto.mapFromSettingEntity(setting), HttpStatus.OK);
    }
    
    /**
     * The settingsSettingIdPut is used to update
     *
     * @param settingId the setting ID for the Setting object to update
     * @param setting the new data for the Setting object
     * @return the newly updated SettingDto object
     * @throws NotFoundException if nothing is found then the the NotFoundException is thrown.
     * @throws InvalidDataException if there is an issue with the data provided in the RequestBody then an InvalidDataException is thrown
     */
    @ApiOperation(value = "Used to update a Setting entity", notes = "A PUT request to the Setting instance endpoint with a Setting object in the request body will update an existing Setting entity in the database.", response = SettingDto.class)
    @ApiResponses(value = [
        @ApiResponse(code = 200, message = "Returns a copy of the newly updated Setting object."),
        @ApiResponse(code = 400, message = "Returns an Error object stating the request body does not match the expected input."),
        @ApiResponse(code = 404, message = "Returns an Error object stating that the resource could not be found. This happens if the specified id cannot be found in the database.")
    ])
    @RequestMapping(value = "/{settingId}", produces = ["application/json"], method = RequestMethod.PUT)
    public ResponseEntity<SettingDto> settingsSettingIdPut(
            @ApiParam(value = "The unique ID of the Setting to retrieve", required = true) @PathVariable("settingId") Integer settingId,
            @ApiParam(value = "The Setting object to be created, without the settingId fields", required = true) @RequestBody SettingDto setting) throws NotFoundException, InvalidDataException {
        LOGGER.info("** SettingsApi - settingsPUT");
        if (settingId != setting.id) {
            throw new InvalidDataException()
        }
        Setting settingToSave = SettingDto.mapToSettingEntity(setting)
        Setting settingSaved = settingService.updateSetting(settingToSave)
        return new ResponseEntity<SettingDto>(SettingDto.mapFromSettingEntity(settingSaved), HttpStatus.OK);
    }
}
