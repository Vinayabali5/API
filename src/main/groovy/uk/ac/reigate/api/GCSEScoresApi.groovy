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

import uk.ac.reigate.domain.academic.GCSEScore
import uk.ac.reigate.dto.GCSEScoreDto
import uk.ac.reigate.exceptions.InvalidDataException
import uk.ac.reigate.exceptions.NotFoundException
import uk.ac.reigate.model.SearchResult
import uk.ac.reigate.services.GCSEScoreService


@Controller
@RequestMapping(value = "/api/gCSEScores", produces = [ APPLICATION_JSON_VALUE ])
@Api(value = "/api/gCSEScores", description = "the gCSEScores API")
public class GCSEScoresApi {
    
    private static final Logger LOGGER = Logger.getLogger(GCSEScoresApi.class);
    
    @Autowired
    private final GCSEScoreService gCSEScoreService;
    
    /**
     * Default NoArgs constructor
     */
    GCSEScoresApi() {}
    
    /**
     * Autowired constructor
     */
    GCSEScoresApi(GCSEScoreService gCSEScoreService) {
        this.gCSEScoreService = gCSEScoreService;
    }
    
    /**
     * The gCSEScoresGet method is used to retrieve a full list of all the GCSEScoreDto objects
     *
     * @return A ResponseEntity with the corresponding list of GCSEScoreDto objects
     * @throws NotFoundException if nothing is found then the the NotFoundException is thrown.
     */
    @ApiOperation(value = "Collection of GCSEScore entities", notes = "A GET request to the GCSEScores endpoint returns an array of all the gCSEScores in the system.", response = GCSEScoreDto.class, responseContainer = "List")
    @ApiResponses(value = [
        @ApiResponse(code = 200, message = "An array of gCSEScores")
    ])
    @RequestMapping(value = "", produces = ["application/json"], method = RequestMethod.GET)
    public ResponseEntity<List<GCSEScoreDto>> gCSEScoresGet() throws NotFoundException {
        LOGGER.info("** GCSEScoresApi - gCSEScoresGet");
        List<GCSEScore> gCSEScores = gCSEScoreService.findAll();
        return new ResponseEntity<List<GCSEScoreDto>>(GCSEScoreDto.mapFromGCSEScoresEntities(gCSEScores), HttpStatus.OK);
    }
    
    
    /**
     * The gCSEScoresGCSEScoreIdGet method is used to retrieve an instance of a GCSEScoreDto object as identified by the gCSEScoreId provided
     *
     * @param gCSEScoreId the gCSEScore ID for the GCSEScore object retrieve
     * @return A ResponseEntity with the corresponding GCSEScoreDto object
     * @throws NotFoundException if nothing is found then the the NotFoundException is thrown.
     */
    @ApiOperation(value = "Retrieves an indivdual instance of a GCSEScore identified by the gCSEScoreId", notes = "A getGET request to the GCSEScore instance endpoint will retrieve an instance of a GCSEScore entity as identified by the gCSEScoreId provided in the URI.", response = GCSEScoreDto.class)
    @ApiResponses(value = [
        @ApiResponse(code = 200, message = "Returns a copy of the GCSEScore as identified by the gCSEScoreId"),
        @ApiResponse(code = 404, message = "Returns an Error object stating that the resource could not be found. This happens if the specified id cannot be found in the database.")
    ])
    @RequestMapping(value = "/{studentId}/gCSEScores", produces = ["application/json"], method = RequestMethod.GET)
    public ResponseEntity<GCSEScoreDto> gCSEScoresGCSEScoreIdGet(@ApiParam(value = "The unique ID of the GCSEScore to retrieve", required = true) @PathVariable("studentId") Integer studentId) throws NotFoundException {
        LOGGER.info("** GCSEScoresApi - gCSEScoresGCSEScoreIdGet");
        GCSEScore gCSEScore = gCSEScoreService.findGCSEScore(studentId);
        if (gCSEScore == null) {
            throw new NotFoundException();
        }
        return new ResponseEntity<GCSEScoreDto>(GCSEScoreDto.mapFromGCSEScoreEntity(gCSEScore), HttpStatus.OK);
    }
}
