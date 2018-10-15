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
import org.springframework.security.access.annotation.Secured
import org.springframework.stereotype.Controller
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestMethod

import uk.ac.reigate.domain.Address
import uk.ac.reigate.dto.AcademicYearDto
import uk.ac.reigate.dto.AddressDto
import uk.ac.reigate.exceptions.InvalidDataException
import uk.ac.reigate.exceptions.NotFoundException
import uk.ac.reigate.model.SearchResult
import uk.ac.reigate.services.AddressService


@Controller
@RequestMapping(value = "/api/addresses", produces = [ APPLICATION_JSON_VALUE ])
@Api(value = "/api/addresses", description = "the addresses API")
public class AddressesApi implements ICoreDataApi<AddressDto, Integer> {
    
    private static final Logger LOGGER = Logger.getLogger(AddressesApi.class);
    
    @Autowired
    private final AddressService addressService;
    
    /**
     * Default NoArgs constructor
     */
    AddressesApi() {}
    
    /**
     * Autowired constructor
     */
    AddressesApi(AddressService addressService) {
        this.addressService = addressService;
    }
    
    /**
     * The addresssGet method is used to retrieve a full list of all the AddressDto objects
     *
     * @return A ResponseEntity with the corresponding list of AddressDto objects
     * @throws NotFoundException if nothing is found then the the NotFoundException is thrown.
     */
    @ApiOperation(value = "Collection of Address entities", notes = "A GET request to the Addresss endpoint returns an array of all the addresss in the system.", response = AddressDto.class, responseContainer = "List")
    @ApiResponses(value = [
        @ApiResponse(code = 200, message = "An array of addresses")
    ])
    @RequestMapping(value = "", produces = ["application/json"], method = RequestMethod.GET)
    public ResponseEntity<List<AddressDto>> getAll() throws NotFoundException {
        LOGGER.info("** AddressesApi - addressesGet");
        List<Address> addresses = addressService.findAll();
        return new ResponseEntity<List<AddressDto>>(AddressDto.mapFromAddressesEntities(addresses), HttpStatus.OK);
    }
    
    /**
     * The addresssAddressIdGet method is used to retrieve an instance of a AddressDto object as identified by the addressId provided
     *
     * @param addressId the address ID for the Address object retrieve
     * @return A ResponseEntity with the corresponding AddressDto object
     * @throws NotFoundException if nothing is found then the the NotFoundException is thrown.
     */
    @ApiOperation(value = "Retrieves an indivdual instance of a Address identified by the addressId", notes = "A getGET request to the Address instance endpoint will retrieve an instance of a Address entity as identified by the addressId provided in the URI.", response = AddressDto.class)
    @ApiResponses(value = [
        @ApiResponse(code = 200, message = "Returns a copy of the Address as identified by the addressId"),
        @ApiResponse(code = 404, message = "Returns an Error object stating that the resource could not be found. This happens if the specified id cannot be found in the database.")
    ])
    @RequestMapping(value = "/{addressId}", produces = ["application/json"], method = RequestMethod.GET)
    public ResponseEntity<AddressDto> getById(@ApiParam(value = "The unique ID of the Address to retrieve", required = true) @PathVariable("addressId") Integer addressId) throws NotFoundException {
        LOGGER.info("** AddressesApi - addressesAddressIdGet");
        Address address = addressService.findById(addressId);
        if (address == null) {
            throw new NotFoundException();
        }
        return new ResponseEntity<AddressDto>(AddressDto.mapFromAddressEntity(address), HttpStatus.OK);
    }
    
    /**
     * The addresssPost method is used to create a new instance of a Address from the supplied AddressDto
     *
     * @param address the AddressDto to use to create the new Address object
     * @return A ResponseEntity with the newly created Address object
     * @throws InvalidDataException if there is an issue with the data provided in the RequestBody then an InvalidDataException is thrown
     */
    @Secured([
        "ROLE_Core Data",
        "ROLE_Office Administration",
        "ROLE_Enrolment Manager",
        "ROLE_Administration",
        "ROLE_Student Services",
        "ROLE_Exams Officer"
    ])
    @ApiOperation(value = "Creates a new Address entity", notes = "A POST request to the Addresses endpoint with a Address object in the request body will create a new Address entity in the database.", response = AddressDto.class)
    @ApiResponses(value = [
        @ApiResponse(code = 201, message = "Returns a copy of the created Address entity including the addressId that has just been created."),
        @ApiResponse(code = 400, message = "Returns an Error object stating the request body does not match the expected input.")
    ])
    @RequestMapping(value = "", produces = ["application/json"], method = RequestMethod.POST)
    public ResponseEntity<AddressDto> create(@ApiParam(value = "The Address object to be created, without the addressId fields", required = true) @RequestBody @Valid AddressDto address) throws NotFoundException, InvalidDataException {
        LOGGER.info("** AddressesApi - addressesPOST");
        if (address.id != null) {
            throw new InvalidDataException(400, "No ID field should be provided when creating")
        }
        Address addressToSave = AddressDto.mapToAddressEntity(address)
        Address addressSaved = addressService.save(addressToSave)
        return new ResponseEntity<AddressDto>(AddressDto.mapFromAddressEntity(addressSaved), HttpStatus.CREATED);
    }
    
    /**
     * The addresssAddressIdPut is used to update
     *
     * @param addressId the address ID for the Address object to update
     * @param address the new data for the Address object
     * @return the newly updated AddressDto object
     * @throws NotFoundException if nothing is found then the the NotFoundException is thrown.
     * @throws InvalidDataException if there is an issue with the data provided in the RequestBody then an InvalidDataException is thrown
     */
    @Secured([
        "ROLE_Core Data",
        "ROLE_Office Administration",
        "ROLE_Enrolment Manager",
        "ROLE_Administration",
        "ROLE_Student Services",
        "ROLE_Exams Officer"
    ])
    @ApiOperation(value = "Used to update a Address entity", notes = "A PUT request to the Address instance endpoint with a Address object in the request body will update an existing Address entity in the database.", response = AddressDto.class)
    @ApiResponses(value = [
        @ApiResponse(code = 200, message = "Returns a copy of the newly updated Address object."),
        @ApiResponse(code = 400, message = "Returns an Error object stating the request body does not match the expected input."),
        @ApiResponse(code = 404, message = "Returns an Error object stating that the resource could not be found. This happens if the specified id cannot be found in the database.")
    ])
    @RequestMapping(value = "/{addressId}", produces = ["application/json"], method = RequestMethod.PUT)
    public ResponseEntity<AddressDto> update(
            @ApiParam(value = "The unique ID of the Address to retrieve", required = true) @PathVariable("addressId") Integer addressId,
            @ApiParam(value = "The Address object to be created, without the addressId fields", required = true) @RequestBody AddressDto address) throws NotFoundException, InvalidDataException {
        LOGGER.info("** AddressesApi - addressPUT");
        if (addressId != address.id) {
            throw new InvalidDataException()
        }
        Address addressToSave = AddressDto.mapToAddressEntity(address)
        Address addressSaved = addressService.updateAddress(addressToSave)
        return new ResponseEntity<AddressDto>(AddressDto.mapFromAddressEntity(addressSaved), HttpStatus.OK);
    }
}
