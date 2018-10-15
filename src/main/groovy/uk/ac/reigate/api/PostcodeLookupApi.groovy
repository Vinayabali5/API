package uk.ac.reigate.api
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

import uk.ac.reigate.domain.Address
import uk.ac.reigate.domain.lookup.PostcodeLookup
import uk.ac.reigate.dto.ErrorMessageDto;
import uk.ac.reigate.exceptions.NotFoundException;
import uk.ac.reigate.repositories.AddressRepository
import uk.ac.reigate.services.PostcodeLookupService


@Controller
@RequestMapping(value = "/api/postcodes", produces = [ APPLICATION_JSON_VALUE ])
@Api(value = "/api/postcodes", description = "the postcodes API")
public class PostcodeLookupApi {
    
    private final static Logger log = Logger.getLogger(PostcodeLookupApi.class.getName());
    
    @Autowired
    private final PostcodeLookupService postcodeLookupService;
    
    /**
     * Default NoArgs constructor
     */
    PostcodeLookupApi() {}
    
    /**
     * Autowired constructor
     */
    PostcodeLookupApi(PostcodeLookupService postcodeLookupService){
        this.postcodeLookupService = postcodeLookupService;
    }
    
    /**
     * The search method is used to retrieve a full list of a PostcodeLookup object as identified by the postcode provided
     *
     * @return A ResponseEntity with the corresponding PostcodeLookup object and with a ErrorMessage
     * @throws NotFoundException if nothing is found then the the NotFoundException is thrown.
     */
    @RequestMapping(value='/search/{postcode}', method=RequestMethod.GET)
    public ResponseEntity<List<PostcodeLookup>> search(
            @ApiParam(value = "The Unique Postcode to retrieve the address", required = true)
            @PathVariable (value='postcode') String postcode) throws NotFoundException {
        log.info("*** PostcodeLookupApi.search")
        try {
            List<PostcodeLookup> postcodes = postcodeLookupService.search(postcode)
            return new ResponseEntity<List<PostcodeLookup>>(postcodes, HttpStatus.OK)
        } catch (UnknownHostException e) {
            return new ResponseEntity<List<PostcodeLookup>>(new ErrorMessageDto("Postcode Lookup Server Unavailable", "Cannot connect to the postcode lookup server at this time."), HttpStatus.GATEWAY_TIMEOUT)
        }
    }
    
    
    /**
     * The getById method is used to retrieve an instance of a Address object as identified by the id provided
     *
     * @param id the ID The Unique Id of the Postcode to retrieve the address
     * @return A ResponseEntity with the corresponding Address object
     * @throws NotFoundException if nothing is found then the the NotFoundException is thrown.
     */
    @RequestMapping(value='/retrieve/{id}', method=RequestMethod.GET)
    public ResponseEntity <Address> getById (
            @ApiParam(value = "The Unique Id of the Postcode to retrieve the address", required = true)
            @PathVariable (value='id') String id) throws NotFoundException {
        Address postcodelookup = postcodeLookupService.retrieve(id);
        log.info("*** PostcodeLookupApi.getById(id) returned");
        return new ResponseEntity<Address>(postcodelookup, HttpStatus.OK)
    }
}

