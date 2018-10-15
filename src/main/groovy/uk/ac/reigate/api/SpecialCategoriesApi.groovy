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

import uk.ac.reigate.domain.academic.SpecialCategory
import uk.ac.reigate.dto.SpecialCategoryDto
import uk.ac.reigate.exceptions.InvalidDataException
import uk.ac.reigate.exceptions.NotFoundException
import uk.ac.reigate.model.SearchResult
import uk.ac.reigate.services.SpecialCategoryService


@Controller
@RequestMapping(value = "/api/specialCategories", produces = [ APPLICATION_JSON_VALUE ])
@Api(value = "/api/specialCategories", description = "the specialCategories API")
public class SpecialCategoriesApi {
    
    private static final Logger LOGGER = Logger.getLogger(SpecialCategoriesApi.class);
    
    @Autowired
    private final SpecialCategoryService specialCategoryService;
    
    /**
     * Default NoArgs constructor
     */
    SpecialCategoriesApi() {}
    
    /**
     * Autowired constructor
     */
    SpecialCategoriesApi(SpecialCategoryService specialCategoryService) {
        this.specialCategoryService = specialCategoryService;
    }
    
    /**
     * The specialCategoriesGet method is used to retrieve a full list of all the SpecialCategoryDto objects
     *
     * @return A ResponseEntity with the corresponding list of SpecialCategoryDto objects
     * @throws NotFoundException if nothing is found then the the NotFoundException is thrown.
     */
    @ApiOperation(value = "Collection of SpecialCategory entities", notes = "A GET request to the SpecialCategories endpoint returns an array of all the specialCategories in the system.", response = SpecialCategoryDto.class, responseContainer = "List")
    @ApiResponses(value = [
        @ApiResponse(code = 200, message = "An array of specialCategories")
    ])
    @RequestMapping(value = "", produces = ["application/json"], method = RequestMethod.GET)
    public ResponseEntity<List<SpecialCategoryDto>> specialCategoriesGet() throws NotFoundException {
        LOGGER.info("** SpecialCategorysApi - specialCategorysGet");
        List<SpecialCategory> specialCategories = specialCategoryService.findAll();
        return new ResponseEntity<List<SpecialCategoryDto>>(SpecialCategoryDto.mapFromSpecialCategoriesEntities(specialCategories), HttpStatus.OK);
    }
    
    /**
     * The specialCategoriesPost method is used to create a new instance of a SpecialCategory from the supplied SpecialCategoryDto
     *
     * @param specialCategory the SpecialCategoryDto to use to create the new SpecialCategory object
     * @return A ResponseEntity with the newly created SpecialCategory object
     * @throws InvalidDataException if there is an issue with the data provided in the RequestBody then an InvalidDataException is thrown
     */
    @ApiOperation(value = "Creates a new SpecialCategory entity", notes = "A POST request to the SpecialCategories endpoint with a SpecialCategory object in the request body will create a new SpecialCategory entity in the database.", response = SpecialCategoryDto.class)
    @ApiResponses(value = [
        @ApiResponse(code = 201, message = "Returns a copy of the created SpecialCategory entity including the specialCategoryId that has just been created."),
        @ApiResponse(code = 400, message = "Returns an Error object stating the request body does not match the expected input.")
    ])
    @RequestMapping(value = "", produces = ["application/json"], method = RequestMethod.POST)
    public ResponseEntity<SpecialCategoryDto> specialCategoriesPost(@ApiParam(value = "The SpecialCategory object to be created, without the specialCategoryId fields", required = true) @RequestBody @Valid SpecialCategoryDto specialCategory) throws NotFoundException, InvalidDataException {
        LOGGER.info("** SpecialCategorysApi - specialCategorysPOST");
        if (specialCategory.id != null) {
            throw new InvalidDataException(400, "No ID field should be provided when creating")
        }
        SpecialCategory specialCategoryToSave = SpecialCategoryDto.mapToSpecialCategoryEntity(specialCategory)
        SpecialCategory specialCategorySaved = specialCategoryService.save(specialCategoryToSave)
        return new ResponseEntity<SpecialCategoryDto>(SpecialCategoryDto.mapFromSpecialCategoryEntity(specialCategorySaved), HttpStatus.CREATED);
    }
    
    /**
     * The specialCategoriesSpecialCategoryIdGet method is used to retrieve an instance of a SpecialCategoryDto object as identified by the specialCategoryId provided
     *
     * @param specialCategoryId the specialCategory ID for the SpecialCategory object retrieve
     * @return A ResponseEntity with the corresponding SpecialCategoryDto object
     * @throws NotFoundException if nothing is found then the the NotFoundException is thrown.
     */
    @ApiOperation(value = "Retrieves an indivdual instance of a SpecialCategory identified by the specialCategoryId", notes = "A getGET request to the SpecialCategory instance endpoint will retrieve an instance of a SpecialCategory entity as identified by the specialCategoryId provided in the URI.", response = SpecialCategoryDto.class)
    @ApiResponses(value = [
        @ApiResponse(code = 200, message = "Returns a copy of the SpecialCategory as identified by the specialCategoryId"),
        @ApiResponse(code = 404, message = "Returns an Error object stating that the resource could not be found. This happens if the specified id cannot be found in the database.")
    ])
    @RequestMapping(value = "/{specialCategoryId}", produces = ["application/json"], method = RequestMethod.GET)
    public ResponseEntity<SpecialCategoryDto> specialCategoriesSpecialCategoryIdGet(@ApiParam(value = "The unique ID of the SpecialCategory to retrieve", required = true) @PathVariable("specialCategoryId") Integer specialCategoryId) throws NotFoundException {
        LOGGER.info("** SpecialCategoriesApi - specialCategorysSpecialCategoryIdGet");
        SpecialCategory specialCategory = specialCategoryService.findById(specialCategoryId);
        if (specialCategory == null) {
            throw new NotFoundException();
        }
        return new ResponseEntity<SpecialCategoryDto>(SpecialCategoryDto.mapFromSpecialCategoryEntity(specialCategory), HttpStatus.OK);
    }
    
    /**
     * The specialCategoriesSpecialCategoryIdPut is used to update
     *
     * @param specialCategoryId the specialCategory ID for the SpecialCategory object to update
     * @param specialCategory the new data for the SpecialCategory object
     * @return the newly updated SpecialCategoryDto object
     * @throws NotFoundException if nothing is found then the the NotFoundException is thrown.
     * @throws InvalidDataException if there is an issue with the data provided in the RequestBody then an InvalidDataException is thrown
     */
    @ApiOperation(value = "Used to update a SpecialCategory entity", notes = "A PUT request to the SpecialCategory instance endpoint with a SpecialCategory object in the request body will update an existing SpecialCategory entity in the database.", response = SpecialCategoryDto.class)
    @ApiResponses(value = [
        @ApiResponse(code = 200, message = "Returns a copy of the newly updated SpecialCategory object."),
        @ApiResponse(code = 400, message = "Returns an Error object stating the request body does not match the expected input."),
        @ApiResponse(code = 404, message = "Returns an Error object stating that the resource could not be found. This happens if the specified id cannot be found in the database.")
    ])
    @RequestMapping(value = "/{specialCategoryId}", produces = ["application/json"], method = RequestMethod.PUT)
    public ResponseEntity<SpecialCategoryDto> specialCategoriesSpecialCategoryIdPut(
            @ApiParam(value = "The unique ID of the SpecialCategory to retrieve", required = true) @PathVariable("specialCategoryId") Integer specialCategoryId,
            @ApiParam(value = "The SpecialCategory object to be created, without the specialCategoryId fields", required = true) @RequestBody SpecialCategoryDto specialCategory) throws NotFoundException, InvalidDataException {
        LOGGER.info("** SpecialCategorysApi - specialCategorysPUT");
        if (specialCategoryId != specialCategory.id) {
            throw new InvalidDataException()
        }
        SpecialCategory specialCategoryToSave = SpecialCategoryDto.mapToSpecialCategoryEntity(specialCategory)
        SpecialCategory specialCategorySaved = specialCategoryService.updateSpecialCategory(specialCategoryToSave)
        return new ResponseEntity<SpecialCategoryDto>(SpecialCategoryDto.mapFromSpecialCategoryEntity(specialCategorySaved), HttpStatus.OK);
    }
}
