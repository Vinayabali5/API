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

import uk.ac.reigate.domain.ilr.FundingModel
import uk.ac.reigate.dto.FundingModelDto
import uk.ac.reigate.exceptions.InvalidDataException
import uk.ac.reigate.exceptions.NotFoundException
import uk.ac.reigate.model.SearchResult
import uk.ac.reigate.services.FundingModelService


@Controller
@RequestMapping(value = "/api/fundingModels", produces = [ APPLICATION_JSON_VALUE ])
@Api(value = "/api/fundingModels", description = "the fundingModels API")
public class FundingModelsApi {
    
    private static final Logger LOGGER = Logger.getLogger(FundingModelsApi.class);
    
    @Autowired
    private final FundingModelService fundingModelService;
    
    /**
     * Default NoArgs constructor
     */
    FundingModelsApi() {}
    
    /**
     * Autowired constructor
     */
    FundingModelsApi(FundingModelService fundingModelService) {
        this.fundingModelService = fundingModelService;
    }
    
    /**
     * The fundingModelsGet method is used to retrieve a full list of all the FundingModelDto objects
     *
     * @return A ResponseEntity with the corresponding list of FundingModelDto objects
     * @throws NotFoundException if nothing is found then the the NotFoundException is thrown.
     */
    @ApiOperation(value = "Collection of FundingModel entities", notes = "A GET request to the FundingModels endpoint returns an array of all the fundingModels in the system.", response = FundingModelDto.class, responseContainer = "List")
    @ApiResponses(value = [
        @ApiResponse(code = 200, message = "An array of fundingModels")
    ])
    @RequestMapping(value = "", produces = ["application/json"], method = RequestMethod.GET)
    public ResponseEntity<List<FundingModelDto>> fundingModelsGet() throws NotFoundException {
        LOGGER.info("** FundingModelsApi - fundingModelsGet");
        List<FundingModel> fundingModels = fundingModelService.findAll();
        return new ResponseEntity<List<FundingModelDto>>(FundingModelDto.mapFromFundingModelsEntities(fundingModels), HttpStatus.OK);
    }
    
    /**
     * The fundingModelsPost method is used to create a new instance of a FundingModel from the supplied FundingModelDto
     *
     * @param fundingModel the FundingModelDto to use to create the new FundingModel object
     * @return A ResponseEntity with the newly created FundingModel object
     * @throws InvalidDataException if there is an issue with the data provided in the RequestBody then an InvalidDataException is thrown
     */
    @ApiOperation(value = "Creates a new FundingModel entity", notes = "A POST request to the FundingModels endpoint with a FundingModel object in the request body will create a new FundingModel entity in the database.", response = FundingModelDto.class)
    @ApiResponses(value = [
        @ApiResponse(code = 201, message = "Returns a copy of the created FundingModel entity including the fundingModelId that has just been created."),
        @ApiResponse(code = 400, message = "Returns an Error object stating the request body does not match the expected input.")
    ])
    @RequestMapping(value = "", produces = ["application/json"], method = RequestMethod.POST)
    public ResponseEntity<FundingModelDto> fundingModelsPost(@ApiParam(value = "The FundingModel object to be created, without the fundingModelId fields", required = true) @RequestBody @Valid FundingModelDto fundingModel) throws NotFoundException, InvalidDataException {
        LOGGER.info("** FundingModelsApi - fundingModelsPOST");
        if (fundingModel.id != null) {
            throw new InvalidDataException(400, "No ID field should be provided when creating")
        }
        FundingModel fundingModelToSave = FundingModelDto.mapToFundingModelEntity(fundingModel)
        FundingModel fundingModelSaved = fundingModelService.save(fundingModelToSave)
        return new ResponseEntity<FundingModelDto>(FundingModelDto.mapFromFundingModelEntity(fundingModelSaved), HttpStatus.CREATED);
    }
    
    /**
     * The fundingModelsFundingModelIdGet method is used to retrieve an instance of a FundingModelDto object as identified by the fundingModelId provided
     *
     * @param fundingModelId the fundingModel ID for the FundingModel object retrieve
     * @return A ResponseEntity with the corresponding FundingModelDto object
     * @throws NotFoundException if nothing is found then the the NotFoundException is thrown.
     */
    @ApiOperation(value = "Retrieves an indivdual instance of a FundingModel identified by the fundingModelId", notes = "A getGET request to the FundingModel instance endpoint will retrieve an instance of a FundingModel entity as identified by the fundingModelId provided in the URI.", response = FundingModelDto.class)
    @ApiResponses(value = [
        @ApiResponse(code = 200, message = "Returns a copy of the FundingModel as identified by the fundingModelId"),
        @ApiResponse(code = 404, message = "Returns an Error object stating that the resource could not be found. This happens if the specified id cannot be found in the database.")
    ])
    @RequestMapping(value = "/{fundingModelId}", produces = ["application/json"], method = RequestMethod.GET)
    public ResponseEntity<FundingModelDto> fundingModelsFundingModelIdGet(@ApiParam(value = "The unique ID of the FundingModel to retrieve", required = true) @PathVariable("fundingModelId") Integer fundingModelId) throws NotFoundException {
        LOGGER.info("** FundingModelsApi - fundingModelsFundingModelIdGet");
        FundingModel fundingModel = fundingModelService.findById(fundingModelId);
        if (fundingModel == null) {
            throw new NotFoundException();
        }
        return new ResponseEntity<FundingModelDto>(FundingModelDto.mapFromFundingModelEntity(fundingModel), HttpStatus.OK);
    }
    
    /**
     * The fundingModelsFundingModelIdPut is used to update
     *
     * @param fundingModelId the fundingModel ID for the FundingModel object to update
     * @param fundingModel the new data for the FundingModel object
     * @return the newly updated FundingModelDto object
     * @throws NotFoundException if nothing is found then the the NotFoundException is thrown.
     * @throws InvalidDataException if there is an issue with the data provided in the RequestBody then an InvalidDataException is thrown
     */
    @ApiOperation(value = "Used to update a FundingModel entity", notes = "A PUT request to the FundingModel instance endpoint with a FundingModel object in the request body will update an existing FundingModel entity in the database.", response = FundingModelDto.class)
    @ApiResponses(value = [
        @ApiResponse(code = 200, message = "Returns a copy of the newly updated FundingModel object."),
        @ApiResponse(code = 400, message = "Returns an Error object stating the request body does not match the expected input."),
        @ApiResponse(code = 404, message = "Returns an Error object stating that the resource could not be found. This happens if the specified id cannot be found in the database.")
    ])
    @RequestMapping(value = "/{fundingModelId}", produces = ["application/json"], method = RequestMethod.PUT)
    public ResponseEntity<FundingModelDto> fundingModelsFundingModelIdPut(
            @ApiParam(value = "The unique ID of the FundingModel to retrieve", required = true) @PathVariable("fundingModelId") Integer fundingModelId,
            @ApiParam(value = "The FundingModel object to be created, without the fundingModelId fields", required = true) @RequestBody FundingModelDto fundingModel) throws NotFoundException, InvalidDataException {
        LOGGER.info("** FundingModelsApi - fundingModelPUT");
        if (fundingModelId != fundingModel.id) {
            throw new InvalidDataException()
        }
        FundingModel fundingModelToSave = FundingModelDto.mapToFundingModelEntity(fundingModel)
        FundingModel fundingModelSaved = fundingModelService.updateFundingModel(fundingModelToSave)
        return new ResponseEntity<FundingModelDto>(FundingModelDto.mapFromFundingModelEntity(fundingModelSaved), HttpStatus.OK);
    }
}
