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

import uk.ac.reigate.domain.academic.Block
import uk.ac.reigate.dto.BlockDto
import uk.ac.reigate.exceptions.InvalidDataException
import uk.ac.reigate.exceptions.NotFoundException
import uk.ac.reigate.model.SearchResult
import uk.ac.reigate.services.BlockService


@Controller
@RequestMapping(value = "/api/blocks", produces = [ APPLICATION_JSON_VALUE ])
@Api(value = "/api/blocks", description = "the blocks API")
public class BlocksApi {
    
    private static final Logger LOGGER = Logger.getLogger(BlocksApi.class);
    
    @Autowired
    private final BlockService blockService;
    
    /**
     * Default NoArgs constructor
     */
    BlocksApi() {}
    
    /**
     * Autowired constructor
     */
    BlocksApi(BlockService blockService) {
        this.blockService = blockService;
    }
    
    /**
     * The blocksGet method is used to retrieve a full list of all the BlockDto objects
     *
     * @return A ResponseEntity with the corresponding list of BlockDto objects
     * @throws NotFoundException if nothing is found then the the NotFoundException is thrown.
     */
    @ApiOperation(value = "Collection of Block entities", notes = "A GET request to the Blocks endpoint returns an array of all the blocks in the system.", response = BlockDto.class, responseContainer = "List")
    @ApiResponses(value = [
        @ApiResponse(code = 200, message = "An array of blocks")
    ])
    @RequestMapping(value = "", produces = ["application/json"], method = RequestMethod.GET)
    public ResponseEntity<List<BlockDto>> blocksGet() throws NotFoundException {
        LOGGER.info("** BlocksApi - blocksGet");
        List<Block> blocks = blockService.findAll();
        return new ResponseEntity<List<BlockDto>>(BlockDto.mapFromBlocksEntities(blocks), HttpStatus.OK);
    }
    
    /**
     * The blocksPost method is used to create a new instance of a Block from the supplied BlockDto
     *
     * @param block the BlockDto to use to create the new Block object
     * @return A ResponseEntity with the newly created Block object
     * @throws InvalidDataException if there is an issue with the data provided in the RequestBody then an InvalidDataException is thrown
     */
    @ApiOperation(value = "Creates a new Block entity", notes = "A POST request to the Blocks endpoint with a Block object in the request body will create a new Block entity in the database.", response = BlockDto.class)
    @ApiResponses(value = [
        @ApiResponse(code = 201, message = "Returns a copy of the created Block entity including the blockId that has just been created."),
        @ApiResponse(code = 400, message = "Returns an Error object stating the request body does not match the expected input.")
    ])
    @RequestMapping(value = "", produces = ["application/json"], method = RequestMethod.POST)
    public ResponseEntity<BlockDto> blocksPost(@ApiParam(value = "The Block object to be created, without the blockId fields", required = true) @RequestBody @Valid BlockDto block) throws NotFoundException, InvalidDataException {
        LOGGER.info("** BlocksApi - blocksPOST");
        if (block.id != null) {
            throw new InvalidDataException(400, "No ID field should be provided when creating")
        }
        Block blockToSave = BlockDto.mapToBlockEntity(block)
        Block blockSaved = blockService.save(blockToSave)
        return new ResponseEntity<BlockDto>(BlockDto.mapFromBlockEntity(blockSaved), HttpStatus.CREATED);
    }
    
    /**
     * The blocksBlockIdGet method is used to retrieve an instance of a BlockDto object as identified by the blockId provided
     *
     * @param blockId the block ID for the Block object retrieve
     * @return A ResponseEntity with the corresponding BlockDto object
     * @throws NotFoundException if nothing is found then the the NotFoundException is thrown.
     */
    @ApiOperation(value = "Retrieves an indivdual instance of a Block identified by the blockId", notes = "A getGET request to the Block instance endpoint will retrieve an instance of a Block entity as identified by the blockId provided in the URI.", response = BlockDto.class)
    @ApiResponses(value = [
        @ApiResponse(code = 200, message = "Returns a copy of the Block as identified by the blockId"),
        @ApiResponse(code = 404, message = "Returns an Error object stating that the resource could not be found. This happens if the specified id cannot be found in the database.")
    ])
    @RequestMapping(value = "/{blockId}", produces = ["application/json"], method = RequestMethod.GET)
    public ResponseEntity<BlockDto> blocksBlockIdGet(@ApiParam(value = "The unique ID of the Block to retrieve", required = true) @PathVariable("blockId") Integer blockId) throws NotFoundException {
        LOGGER.info("** BlocksApi - blocksBlockIdGet");
        Block block = blockService.findById(blockId);
        if (block == null) {
            throw new NotFoundException();
        }
        return new ResponseEntity<BlockDto>(BlockDto.mapFromBlockEntity(block), HttpStatus.OK);
    }
    
    /**
     * The blocksBlockIdPut is used to update
     *
     * @param blockId the block ID for the Block object to update
     * @param block the new data for the Block object
     * @return the newly updated BlockDto object
     * @throws NotFoundException if nothing is found then the the NotFoundException is thrown.
     * @throws InvalidDataException if there is an issue with the data provided in the RequestBody then an InvalidDataException is thrown
     */
    @ApiOperation(value = "Used to update a Block entity", notes = "A PUT request to the Block instance endpoint with a Block object in the request body will update an existing Block entity in the database.", response = BlockDto.class)
    @ApiResponses(value = [
        @ApiResponse(code = 200, message = "Returns a copy of the newly updated Block object."),
        @ApiResponse(code = 400, message = "Returns an Error object stating the request body does not match the expected input."),
        @ApiResponse(code = 404, message = "Returns an Error object stating that the resource could not be found. This happens if the specified id cannot be found in the database.")
    ])
    @RequestMapping(value = "/{blockId}", produces = ["application/json"], method = RequestMethod.PUT)
    public ResponseEntity<BlockDto> blocksBlockIdPut(
            @ApiParam(value = "The unique ID of the Block to retrieve", required = true) @PathVariable("blockId") Integer blockId,
            @ApiParam(value = "The Block object to be created, without the blockId fields", required = true) @RequestBody BlockDto block) throws NotFoundException, InvalidDataException {
        LOGGER.info("** BlocksApi - blocksPUT");
        if (blockId != block.id) {
            throw new InvalidDataException()
        }
        Block blockToSave = BlockDto.mapToBlockEntity(block)
        Block blockSaved = blockService.updateBlock(blockToSave)
        return new ResponseEntity<BlockDto>(BlockDto.mapFromBlockEntity(blockSaved), HttpStatus.OK);
    }
}
