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
import org.springframework.http.HttpRequest;
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.stereotype.Controller
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestMethod

import uk.ac.reigate.domain.academic.AcademicYear
import uk.ac.reigate.domain.academic.Holiday
import uk.ac.reigate.dto.HolidayDto
import uk.ac.reigate.exceptions.InvalidDataException
import uk.ac.reigate.exceptions.NotFoundException
import uk.ac.reigate.model.SearchResult
import uk.ac.reigate.services.AcademicYearService
import uk.ac.reigate.services.HolidayService


@Controller
@RequestMapping(value = "/api/holidays", produces = [ APPLICATION_JSON_VALUE ])
@Api(value = "/api/holidays", description = "the holidays API")
public class HolidaysApi {
    
    private static final Logger LOGGER = Logger.getLogger(HolidaysApi.class);
    
    @Autowired
    private final HolidayService holidayService;
    
    @Autowired
    private final AcademicYearService academicYearService;
    
    /**
     * Default NoArgs constructor
     */
    HolidaysApi() {}
    
    /**
     * Autowired constructor
     */
    HolidaysApi(HolidayService holidayService) {
        this.holidayService = holidayService;
    }
    
    /**
     * The holidaysGet method is used to retrieve a full list of all the HolidayDto objects
     *
     * @return A ResponseEntity with the corresponding list of HolidayDto objects
     * @throws NotFoundException if nothing is found then the the NotFoundException is thrown.
     */
    @ApiOperation(value = "Collection of Holiday entities", notes = "A GET request to the Holidays endpoint returns an array of all the holidays in the system.", response = HolidayDto.class, responseContainer = "List")
    @ApiResponses(value = [
        @ApiResponse(code = 200, message = "An array of holidays")
    ])
    @RequestMapping(value = "", produces = ["application/json"], method = RequestMethod.GET)
    public ResponseEntity<List<HolidayDto>> holidaysGet() throws NotFoundException {
        LOGGER.info("** HolidaysApi - holidaysGet");
        List<Holiday> holidays = holidayService.findAll();
        return new ResponseEntity<List<HolidayDto>>(HolidayDto.mapFromHolidaysEntities(holidays), HttpStatus.OK);
    }
    
    /**
     * The holidaysPost method is used to create a new instance of a Holiday from the supplied HolidayDto
     *
     * @param holiday the HolidayDto to use to create the new Holiday object
     * @return A ResponseEntity with the newly created Holiday object
     * @throws InvalidDataException if there is an issue with the data provided in the RequestBody then an InvalidDataException is thrown
     */
    @ApiOperation(value = "Creates a new Holiday entity", notes = "A POST request to the Holidays endpoint with a Holiday object in the request body will create a new Holiday entity in the database.", response = HolidayDto.class)
    @ApiResponses(value = [
        @ApiResponse(code = 201, message = "Returns a copy of the created Holiday entity including the holidayId that has just been created."),
        @ApiResponse(code = 400, message = "Returns an Error object stating the request body does not match the expected input.")
    ])
    @RequestMapping(value = "", produces = ["application/json"], method = RequestMethod.POST)
    public ResponseEntity<HolidayDto> holidaysPost(@ApiParam(value = "The Holiday object to be created, without the holidayId fields", required = true) @RequestBody @Valid HolidayDto holiday) throws NotFoundException, InvalidDataException {
        LOGGER.info("** HolidaysApi - holidaysPOST");
        if (holiday.id != null) {
            throw new InvalidDataException(400, "No ID field should be provided when creating")
        }
        AcademicYear academicYear
        if (holiday.yearId != null) {
            academicYear = academicYearService.findById(holiday.yearId)
        }
        Holiday holidayToSave = HolidayDto.mapToHolidayEntity(holiday, academicYear)
        Holiday holidaySaved = holidayService.save(holidayToSave)
        return new ResponseEntity<HolidayDto>(HolidayDto.mapFromHolidayEntity(holidaySaved), HttpStatus.CREATED);
    }
    
    /**
     * The holidaysHolidayIdGet method is used to retrieve an instance of a HolidayDto object as identified by the holidayId provided
     *
     * @param holidayId the holiday ID for the Holiday object retrieve
     * @return A ResponseEntity with the corresponding HolidayDto object
     * @throws NotFoundException if nothing is found then the the NotFoundException is thrown.
     */
    @ApiOperation(value = "Retrieves an indivdual instance of a Holiday identified by the holidayId", notes = "A getGET request to the Holiday instance endpoint will retrieve an instance of a Holiday entity as identified by the holidayId provided in the URI.", response = HolidayDto.class)
    @ApiResponses(value = [
        @ApiResponse(code = 200, message = "Returns a copy of the Holiday as identified by the holidayId"),
        @ApiResponse(code = 404, message = "Returns an Error object stating that the resource could not be found. This happens if the specified id cannot be found in the database.")
    ])
    @RequestMapping(value = "/{holidayId}", produces = ["application/json"], method = RequestMethod.GET)
    public ResponseEntity<HolidayDto> holidaysHolidayIdGet(@ApiParam(value = "The unique ID of the Holiday to retrieve", required = true) @PathVariable("holidayId") Integer holidayId) throws NotFoundException {
        LOGGER.info("** HolidaysApi - holidaysHolidayIdGet");
        Holiday holiday = holidayService.findById(holidayId);
        if (holiday == null) {
            throw new NotFoundException();
        }
        return new ResponseEntity<HolidayDto>(HolidayDto.mapFromHolidayEntity(holiday), HttpStatus.OK);
    }
    
    /**
     * The holidaysHolidayIdPut is used to update
     *
     * @param holidayId the holiday ID for the Holiday object to update
     * @param holiday the new data for the Holiday object
     * @return the newly updated HolidayDto object
     * @throws NotFoundException if nothing is found then the the NotFoundException is thrown.
     * @throws InvalidDataException if there is an issue with the data provided in the RequestBody then an InvalidDataException is thrown
     */
    @ApiOperation(value = "Used to update a Holiday entity", notes = "A PUT request to the Holiday instance endpoint with a Holiday object in the request body will update an existing Holiday entity in the database.", response = HolidayDto.class)
    @ApiResponses(value = [
        @ApiResponse(code = 200, message = "Returns a copy of the newly updated Holiday object."),
        @ApiResponse(code = 400, message = "Returns an Error object stating the request body does not match the expected input."),
        @ApiResponse(code = 404, message = "Returns an Error object stating that the resource could not be found. This happens if the specified id cannot be found in the database.")
    ])
    @RequestMapping(value = "/{holidayId}", produces = ["application/json"], method = RequestMethod.PUT)
    public ResponseEntity<HolidayDto> holidaysHolidayIdPut(
            @ApiParam(value = "The unique ID of the Holiday to retrieve", required = true) @PathVariable("holidayId") Integer holidayId,
            @ApiParam(value = "The Holiday object to be created, without the holidayId fields", required = true) @RequestBody HolidayDto holiday) throws NotFoundException, InvalidDataException {
        LOGGER.info("** HolidaysApi - holidaysPUT");
        if (holidayId != holiday.id) {
            LOGGER.error("EE - ID in URI does not match ID in RequestBody. ")
            throw new InvalidDataException()
        }
        AcademicYear academicYear
        if (holiday.yearId != null) {
            academicYear = academicYearService.findById(holiday.yearId)
        }
        Holiday holidayToSave = HolidayDto.mapToHolidayEntity(holiday, academicYear)
        Holiday holidaySaved = holidayService.save(holidayToSave)
        return new ResponseEntity<HolidayDto>(HolidayDto.mapFromHolidayEntity(holidaySaved), HttpStatus.OK);
    }
}
