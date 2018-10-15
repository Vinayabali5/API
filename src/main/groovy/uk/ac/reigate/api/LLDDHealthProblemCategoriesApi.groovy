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

import uk.ac.reigate.domain.ilr.LLDDHealthProblemCategory
import uk.ac.reigate.dto.LLDDHealthProblemCategoryDto
import uk.ac.reigate.exceptions.InvalidDataException
import uk.ac.reigate.exceptions.NotFoundException
import uk.ac.reigate.model.SearchResult
import uk.ac.reigate.services.LLDDHealthProblemCategoryService


@Controller
@RequestMapping(value = "/api/lLDDHealthProblemCategories", produces = [ APPLICATION_JSON_VALUE ])
@Api(value = "/api/lLDDHealthProblemCategories", description = "the lLDDHealthProblemCategories API")
public class LLDDHealthProblemCategoriesApi {
    
    private static final Logger LOGGER = Logger.getLogger(LLDDHealthProblemCategoriesApi.class);
    
    @Autowired
    private final LLDDHealthProblemCategoryService lLDDHealthProblemCategoryService;
    
    /**
     * Default NoArgs constructor
     */
    LLDDHealthProblemCategoriesApi() {}
    
    /**
     * Autowired constructor
     */
    LLDDHealthProblemCategoriesApi(LLDDHealthProblemCategoryService lLDDHealthProblemCategoryService) {
        this.lLDDHealthProblemCategoryService = lLDDHealthProblemCategoryService;
    }
    
    /**
     * The lLDDHealthProblemCategoriesGet method is used to retrieve a full list of all the LLDDHealthProblemCategoryDto objects
     *
     * @return A ResponseEntity with the corresponding list of LLDDHealthProblemCategoryDto objects
     * @throws NotFoundException if nothing is found then the the NotFoundException is thrown.
     */
    @ApiOperation(value = "Collection of LLDDHealthProblemCategory entities", notes = "A GET request to the LLDDHealthProblemCategories endpoint returns an array of all the lLDDHealthProblemCategories in the system.", response = LLDDHealthProblemCategoryDto.class, responseContainer = "List")
    @ApiResponses(value = [
        @ApiResponse(code = 200, message = "An array of lLDDHealthProblemCategories")
    ])
    @RequestMapping(value = "", produces = ["application/json"], method = RequestMethod.GET)
    public ResponseEntity<List<LLDDHealthProblemCategoryDto>> lLDDHealthProblemCategorysGet() throws NotFoundException {
        LOGGER.info("** LLDDHealthProblemCategoriesApi - lLDDHealthProblemCategoriesGet");
        List<LLDDHealthProblemCategory> lLDDHealthProblemCategories = lLDDHealthProblemCategoryService.findAll();
        return new ResponseEntity<List<LLDDHealthProblemCategoryDto>>(LLDDHealthProblemCategoryDto.mapFromLLDDHealthProblemCategoriesEntities(lLDDHealthProblemCategories), HttpStatus.OK);
    }
    
    /**
     * The lLDDHealthProblemCategoriesPost method is used to create a new instance of a LLDDHealthProblemCategory from the supplied LLDDHealthProblemCategoryDto
     *
     * @param lLDDHealthProblemCategory the LLDDHealthProblemCategoryDto to use to create the new LLDDHealthProblemCategory object
     * @return A ResponseEntity with the newly created LLDDHealthProblemCategory object
     * @throws InvalidDataException if there is an issue with the data provided in the RequestBody then an InvalidDataException is thrown
     */
    @ApiOperation(value = "Creates a new LLDDHealthProblemCategory entity", notes = "A POST request to the LLDDHealthProblemCategories endpoint with a LLDDHealthProblemCategory object in the request body will create a new LLDDHealthProblemCategory entity in the database.", response = LLDDHealthProblemCategoryDto.class)
    @ApiResponses(value = [
        @ApiResponse(code = 201, message = "Returns a copy of the created LLDDHealthProblemCategory entity including the lLDDHealthProblemCategoryId that has just been created."),
        @ApiResponse(code = 400, message = "Returns an Error object stating the request body does not match the expected input.")
    ])
    @RequestMapping(value = "", produces = ["application/json"], method = RequestMethod.POST)
    public ResponseEntity<LLDDHealthProblemCategoryDto> lLDDHealthProblemCategoriesPost(@ApiParam(value = "The LLDDHealthProblemCategory object to be created, without the lLDDHealthProblemCategoryId fields", required = true) @RequestBody @Valid LLDDHealthProblemCategoryDto lLDDHealthProblemCategory) throws NotFoundException, InvalidDataException {
        LOGGER.info("** LLDDHealthProblemCategoriesApi - lLDDHealthProblemCategoriesPOST");
        if (lLDDHealthProblemCategory.id != null) {
            throw new InvalidDataException(400, "No ID field should be provided when creating")
        }
        LLDDHealthProblemCategory lLDDHealthProblemCategoryToSave = LLDDHealthProblemCategoryDto.mapToLLDDHealthProblemCategoryEntity(lLDDHealthProblemCategory)
        LLDDHealthProblemCategory lLDDHealthProblemCategorySaved = lLDDHealthProblemCategoryService.save(lLDDHealthProblemCategoryToSave)
        return new ResponseEntity<LLDDHealthProblemCategoryDto>(LLDDHealthProblemCategoryDto.mapFromLLDDHealthProblemCategoryEntity(lLDDHealthProblemCategorySaved), HttpStatus.CREATED);
    }
    
    /**
     * The lLDDHealthProblemCategoriesLLDDHealthProblemCategoryIdGet method is used to retrieve an instance of a LLDDHealthProblemCategoryDto object as identified by the lLDDHealthProblemCategoryId provided
     *
     * @param lLDDHealthProblemCategoryId the lLDDHealthProblemCategory ID for the LLDDHealthProblemCategory object retrieve
     * @return A ResponseEntity with the corresponding LLDDHealthProblemCategoryDto object
     * @throws NotFoundException if nothing is found then the the NotFoundException is thrown.
     */
    @ApiOperation(value = "Retrieves an indivdual instance of a LLDDHealthProblemCategory identified by the lLDDHealthProblemCategoryId", notes = "A getGET request to the LLDDHealthProblemCategory instance endpoint will retrieve an instance of a LLDDHealthProblemCategory entity as identified by the lLDDHealthProblemCategoryId provided in the URI.", response = LLDDHealthProblemCategoryDto.class)
    @ApiResponses(value = [
        @ApiResponse(code = 200, message = "Returns a copy of the LLDDHealthProblemCategory as identified by the lLDDHealthProblemCategoryId"),
        @ApiResponse(code = 404, message = "Returns an Error object stating that the resource could not be found. This happens if the specified id cannot be found in the database.")
    ])
    @RequestMapping(value = "/{lLDDHealthProblemCategoryId}", produces = ["application/json"], method = RequestMethod.GET)
    public ResponseEntity<LLDDHealthProblemCategoryDto> lLDDHealthProblemCategorysLLDDHealthProblemCategoryIdGet(@ApiParam(value = "The unique ID of the LLDDHealthProblemCategory to retrieve", required = true) @PathVariable("lLDDHealthProblemCategoryId") Integer lLDDHealthProblemCategoryId) throws NotFoundException {
        LOGGER.info("** LLDDHealthProblemCategoriesApi - lLDDHealthProblemCategoriesLLDDHealthProblemCategoryIdGet");
        LLDDHealthProblemCategory lLDDHealthProblemCategory = lLDDHealthProblemCategoryService.findById(lLDDHealthProblemCategoryId);
        if (lLDDHealthProblemCategory == null) {
            throw new NotFoundException();
        }
        return new ResponseEntity<LLDDHealthProblemCategoryDto>(LLDDHealthProblemCategoryDto.mapFromLLDDHealthProblemCategoryEntity(lLDDHealthProblemCategory), HttpStatus.OK);
    }
    
    /**
     * The lLDDHealthProblemCategoriesLLDDHealthProblemCategoryIdPut is used to update
     *
     * @param lLDDHealthProblemCategoryId the lLDDHealthProblemCategory ID for the LLDDHealthProblemCategory object to update
     * @param lLDDHealthProblemCategory the new data for the LLDDHealthProblemCategory object
     * @return the newly updated LLDDHealthProblemCategoryDto object
     * @throws NotFoundException if nothing is found then the the NotFoundException is thrown.
     * @throws InvalidDataException if there is an issue with the data provided in the RequestBody then an InvalidDataException is thrown
     */
    @ApiOperation(value = "Used to update a LLDDHealthProblemCategory entity", notes = "A PUT request to the LLDDHealthProblemCategory instance endpoint with a LLDDHealthProblemCategory object in the request body will update an existing LLDDHealthProblemCategory entity in the database.", response = LLDDHealthProblemCategoryDto.class)
    @ApiResponses(value = [
        @ApiResponse(code = 200, message = "Returns a copy of the newly updated LLDDHealthProblemCategory object."),
        @ApiResponse(code = 400, message = "Returns an Error object stating the request body does not match the expected input."),
        @ApiResponse(code = 404, message = "Returns an Error object stating that the resource could not be found. This happens if the specified id cannot be found in the database.")
    ])
    @RequestMapping(value = "/{lLDDHealthProblemCategoryId}", produces = ["application/json"], method = RequestMethod.PUT)
    public ResponseEntity<LLDDHealthProblemCategoryDto> lLDDHealthProblemCategoriesLLDDHealthProblemCategoryIdPut(
            @ApiParam(value = "The unique ID of the LLDDHealthProblemCategory to retrieve", required = true) @PathVariable("lLDDHealthProblemCategoryId") Integer lLDDHealthProblemCategoryId,
            @ApiParam(value = "The LLDDHealthProblemCategory object to be created, without the lLDDHealthProblemCategoryId fields", required = true) @RequestBody LLDDHealthProblemCategoryDto lLDDHealthProblemCategory) throws NotFoundException, InvalidDataException {
        LOGGER.info("** LLDDHealthProblemCategoriesApi - lLDDHealthProblemCategoriesPUT");
        if (lLDDHealthProblemCategoryId != lLDDHealthProblemCategory.id) {
            throw new InvalidDataException()
        }
        LLDDHealthProblemCategory lLDDHealthProblemCategoryToSave = LLDDHealthProblemCategoryDto.mapToLLDDHealthProblemCategoryEntity(lLDDHealthProblemCategory)
        LLDDHealthProblemCategory lLDDHealthProblemCategorySaved = lLDDHealthProblemCategoryService.updateLLDDHealthProblemCategory(lLDDHealthProblemCategoryToSave)
        return new ResponseEntity<LLDDHealthProblemCategoryDto>(LLDDHealthProblemCategoryDto.mapFromLLDDHealthProblemCategoryEntity(lLDDHealthProblemCategorySaved), HttpStatus.OK);
    }
}
