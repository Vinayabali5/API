package uk.ac.reigate.api.exam

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
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestMethod

import uk.ac.reigate.domain.exam.OptionComponent
import uk.ac.reigate.dto.exam.OptionComponentDto
import uk.ac.reigate.exceptions.NotFoundException
import uk.ac.reigate.services.exam.OptionComponentService

@Controller
@RequestMapping(value = "/api/option-components", produces = [APPLICATION_JSON_VALUE])
@Api(value = "/api/OptionComponentsApi", description = "The Exam basedata OptionComponent Resource API")
public class OptionComponentsApi {
    
    private final static Logger LOGGER = Logger.getLogger(OptionComponentsApi.class)
    
    @Autowired
    OptionComponentService optionComponentService
    
    // NOTE : Reading data directly via option-controller is NOT an option, as this is purely a many-to-many link.
    
    /**
     * Default No Args constructor
     */
    OptionComponentsApi() {}
    
    /**
     * Default Autowired constructor
     */
    OptionComponentsApi(OptionComponentService optionComponentService) {
        this.optionComponentService = optionComponentService;
    }
    
    /**
     * The optionComponentPost method is used to create a new instance of an OptionComponent object from the supplied OptionComponentDto entity
     * 
     * @param optionComponent The optionComponentDto to use to create the new OptionComponent object
     * @return A ResponseEntity with an OK object
     * @throws NotFoundException
     */
    @ApiOperation(value = "Creates a new OptionComponent entity",
    notes = "A POST request to the OptionComponents endpoint with an OptionComponent object in the request body will create a new OptionComponent entity in the database")
    @ApiResponses(value = [
        @ApiResponse(code = 201, message = "Returns an OK object stating that the OptionComponent entity has just been created")
    ])
    @RequestMapping(value = "", produces = ["application/json"], method = RequestMethod.POST)
    public ResponseEntity<String> optionComponentPost(
            @ApiParam(value = "The optionComponent object to be created", required = true) @RequestBody @Valid OptionComponentDto optionComponent
    ) throws NotFoundException {
        LOGGER.info("** OptionComponentApi - optionComponentPost");
        OptionComponent toSave = OptionComponentDto.mapToOptionComponentEntity(optionComponent);
        OptionComponent savedOptionComponent = optionComponentService.save(toSave);
        return new ResponseEntity<String>(HttpStatus.OK);
    }
}
