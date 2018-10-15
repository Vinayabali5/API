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

import uk.ac.reigate.domain.learning_support.LearningSupportCostCategory
import uk.ac.reigate.dto.LearningSupportCostCategoryDto
import uk.ac.reigate.exceptions.InvalidDataException
import uk.ac.reigate.exceptions.NotFoundException
import uk.ac.reigate.model.SearchResult
import uk.ac.reigate.services.LearningSupportCostCategoryService


@Controller
@RequestMapping(value = "/api/learningSupportCostCategories", produces = [ APPLICATION_JSON_VALUE ])
@Api(value = "/api/learningSupportCostCategories", description = "the learningSupportCostCategories API")
public class LearningSupportCostCategoriesApi {
    
    private static final Logger LOGGER = Logger.getLogger(LearningSupportCostCategoriesApi.class);
    
    @Autowired
    private final LearningSupportCostCategoryService learningSupportCostCategoryService;
    
    /**
     * Default NoArgs constructor
     */
    LearningSupportCostCategoriesApi() {}
    
    /**
     * Autowired constructor
     */
    LearningSupportCostCategoriesApi(LearningSupportCostCategoryService learningSupportCostCategoryService) {
        this.learningSupportCostCategoryService = learningSupportCostCategoryService;
    }
    
    /**
     * The learningSupportCostCategorysGet method is used to retrieve a full list of all the LearningSupportCostCategoryDto objects
     *
     * @return A ResponseEntity with the corresponding list of LearningSupportCostCategoryDto objects
     * @throws NotFoundException if nothing is found then the the NotFoundException is thrown.
     */
    @ApiOperation(value = "Collection of LearningSupportCostCategory entities", notes = "A GET request to the LearningSupportCostCategorys endpoint returns an array of all the learningSupportCostCategories in the system.", response = LearningSupportCostCategoryDto.class, responseContainer = "List")
    @ApiResponses(value = [
        @ApiResponse(code = 200, message = "An array of learningSupportCostCategorys")
    ])
    @RequestMapping(value = "", produces = ["application/json"], method = RequestMethod.GET)
    public ResponseEntity<List<LearningSupportCostCategoryDto>> learningSupportCostCategoriesGet() throws NotFoundException {
        LOGGER.info("** LearningSupportCostCategoriesApi - learningSupportCostCategoriesGet");
        List<LearningSupportCostCategory> learningSupportCostCategories = learningSupportCostCategoryService.findAll();
        return new ResponseEntity<List<LearningSupportCostCategoryDto>>(LearningSupportCostCategoryDto.mapFromLearningSupportCostCategorysEntities(learningSupportCostCategories), HttpStatus.OK);
    }
    
    /**
     * The learningSupportCostCategorysPost method is used to create a new instance of a LearningSupportCostCategory from the supplied LearningSupportCostCategoryDto
     *
     * @param learningSupportCostCategory the LearningSupportCostCategoryDto to use to create the new LearningSupportCostCategory object
     * @return A ResponseEntity with the newly created LearningSupportCostCategory object
     * @throws InvalidDataException if there is an issue with the data provided in the RequestBody then an InvalidDataException is thrown
     */
    @ApiOperation(value = "Creates a new LearningSupportCostCategory entity", notes = "A POST request to the LearningSupportCostCategories endpoint with a LearningSupportCostCategory object in the request body will create a new LearningSupportCostCategory entity in the database.", response = LearningSupportCostCategoryDto.class)
    @ApiResponses(value = [
        @ApiResponse(code = 201, message = "Returns a copy of the created LearningSupportCostCategory entity including the learningSupportCostCategoryId that has just been created."),
        @ApiResponse(code = 400, message = "Returns an Error object stating the request body does not match the expected input.")
    ])
    @RequestMapping(value = "", produces = ["application/json"], method = RequestMethod.POST)
    public ResponseEntity<LearningSupportCostCategoryDto> learningSupportCostCategorysPost(@ApiParam(value = "The LearningSupportCostCategory object to be created, without the learningSupportCostCategoryId fields", required = true) @RequestBody @Valid LearningSupportCostCategoryDto learningSupportCostCategory) throws NotFoundException, InvalidDataException {
        LOGGER.info("** LearningSupportCostCategorysApi - learningSupportCostCategorysPOST");
        if (learningSupportCostCategory.id != null) {
            throw new InvalidDataException(400, "No ID field should be provided when creating")
        }
        LearningSupportCostCategory learningSupportCostCategoryToSave = LearningSupportCostCategoryDto.mapToLearningSupportCostCategoryEntity(learningSupportCostCategory)
        LearningSupportCostCategory learningSupportCostCategorySaved = learningSupportCostCategoryService.save(learningSupportCostCategoryToSave)
        return new ResponseEntity<LearningSupportCostCategoryDto>(LearningSupportCostCategoryDto.mapFromLearningSupportCostCategoryEntity(learningSupportCostCategorySaved), HttpStatus.CREATED);
    }
    
    /**
     * The learningSupportCostCategorysLearningSupportCostCategoryIdGet method is used to retrieve an instance of a LearningSupportCostCategoryDto object as identified by the learningSupportCostCategoryId provided
     *
     * @param learningSupportCostCategoryId the learningSupportCostCategory ID for the LearningSupportCostCategory object retrieve
     * @return A ResponseEntity with the corresponding LearningSupportCostCategoryDto object
     * @throws NotFoundException if nothing is found then the the NotFoundException is thrown.
     */
    @ApiOperation(value = "Retrieves an indivdual instance of a LearningSupportCostCategory identified by the learningSupportCostCategoryId", notes = "A getGET request to the LearningSupportCostCategory instance endpoint will retrieve an instance of a LearningSupportCostCategory entity as identified by the learningSupportCostCategoryId provided in the URI.", response = LearningSupportCostCategoryDto.class)
    @ApiResponses(value = [
        @ApiResponse(code = 200, message = "Returns a copy of the LearningSupportCostCategory as identified by the learningSupportCostCategoryId"),
        @ApiResponse(code = 404, message = "Returns an Error object stating that the resource could not be found. This happens if the specified id cannot be found in the database.")
    ])
    @RequestMapping(value = "/{learningSupportCostCategoryId}", produces = ["application/json"], method = RequestMethod.GET)
    public ResponseEntity<LearningSupportCostCategoryDto> learningSupportCostCategorysLearningSupportCostCategoryIdGet(@ApiParam(value = "The unique ID of the LearningSupportCostCategory to retrieve", required = true) @PathVariable("learningSupportCostCategoryId") Integer learningSupportCostCategoryId) throws NotFoundException {
        LOGGER.info("** LearningSupportCostCategoriesApi - learningSupportCostCategorysLearningSupportCostCategoryIdGet");
        LearningSupportCostCategory learningSupportCostCategory = learningSupportCostCategoryService.findById(learningSupportCostCategoryId);
        if (learningSupportCostCategory == null) {
            throw new NotFoundException();
        }
        return new ResponseEntity<LearningSupportCostCategoryDto>(LearningSupportCostCategoryDto.mapFromLearningSupportCostCategoryEntity(learningSupportCostCategory), HttpStatus.OK);
    }
    
    /**
     * The learningSupportCostCategorysLearningSupportCostCategoryIdPut is used to update
     *
     * @param learningSupportCostCategoryId the learningSupportCostCategory ID for the LearningSupportCostCategory object to update
     * @param learningSupportCostCategory the new data for the LearningSupportCostCategory object
     * @return the newly updated LearningSupportCostCategoryDto object
     * @throws NotFoundException if nothing is found then the the NotFoundException is thrown.
     * @throws InvalidDataException if there is an issue with the data provided in the RequestBody then an InvalidDataException is thrown
     */
    @ApiOperation(value = "Used to update a LearningSupportCostCategory entity", notes = "A PUT request to the LearningSupportCostCategory instance endpoint with a LearningSupportCostCategory object in the request body will update an existing LearningSupportCostCategory entity in the database.", response = LearningSupportCostCategoryDto.class)
    @ApiResponses(value = [
        @ApiResponse(code = 200, message = "Returns a copy of the newly updated LearningSupportCostCategory object."),
        @ApiResponse(code = 400, message = "Returns an Error object stating the request body does not match the expected input."),
        @ApiResponse(code = 404, message = "Returns an Error object stating that the resource could not be found. This happens if the specified id cannot be found in the database.")
    ])
    @RequestMapping(value = "/{learningSupportCostCategoryId}", produces = ["application/json"], method = RequestMethod.PUT)
    public ResponseEntity<LearningSupportCostCategoryDto> learningSupportCostCategorysLearningSupportCostCategoryIdPut(
            @ApiParam(value = "The unique ID of the LearningSupportCostCategory to retrieve", required = true) @PathVariable("learningSupportCostCategoryId") Integer learningSupportCostCategoryId,
            @ApiParam(value = "The LearningSupportCostCategory object to be created, without the learningSupportCostCategoryId fields", required = true) @RequestBody LearningSupportCostCategoryDto learningSupportCostCategory) throws NotFoundException, InvalidDataException {
        LOGGER.info("** LearningSupportCostCategoriesApi - learningSupportCostCategorysPUT");
        if (learningSupportCostCategoryId != learningSupportCostCategory.id) {
            throw new InvalidDataException()
        }
        LearningSupportCostCategory learningSupportCostCategoryToSave = LearningSupportCostCategoryDto.mapToLearningSupportCostCategoryEntity(learningSupportCostCategory)
        LearningSupportCostCategory learningSupportCostCategorySaved = learningSupportCostCategoryService.updateLearningSupportCostCategory(learningSupportCostCategoryToSave)
        return new ResponseEntity<LearningSupportCostCategoryDto>(LearningSupportCostCategoryDto.mapFromLearningSupportCostCategoryEntity(learningSupportCostCategorySaved), HttpStatus.OK);
    }
}
