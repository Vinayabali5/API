package uk.ac.reigate.api

import javax.validation.Valid

import io.swagger.annotations.Api
import io.swagger.annotations.ApiOperation
import io.swagger.annotations.ApiParam
import io.swagger.annotations.ApiResponse
import io.swagger.annotations.ApiResponses

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.stereotype.Controller
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestMethod

import uk.ac.reigate.domain.lookup.TextLookup
import uk.ac.reigate.dto.TextLookUpDto
import uk.ac.reigate.exceptions.InvalidDataException
import uk.ac.reigate.exceptions.NotFoundException
import uk.ac.reigate.services.TextLookupService

import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE

/**
 * @author sat
 *
 */
@Controller
@RequestMapping(value="/api/text-lookup", produces = [ APPLICATION_JSON_VALUE ])
@Api(value="/api/text-lookup", description ="text look up data")
class TextLookUpApi {
    
    @Autowired
    TextLookupService textLookupService
    
    /** Get method - retrieves all Text Lookup Data
     * @return
     * @throws NotFoundException
     */
    @ApiOperation(value = "Retrieves the list of Text Lookup values", notes = "A GET request to the Text Lookup Data", response = TextLookUpDto.class, responseContainer = "List")
    @ApiResponses(value = [
        @ApiResponse(code = 200, message = "An array of Text Lookup data")
    ])
    @RequestMapping(value = "", produces = ["application/json"], method = RequestMethod.GET)
    public ResponseEntity<List<TextLookUpDto>> getAllTextLookUp() throws NotFoundException {
        List<TextLookup> textLookUpList = textLookupService.findAll();
        return new ResponseEntity<List<TextLookUpDto>>(TextLookUpDto.mapFromEntityList(textLookUpList), HttpStatus.OK);
    }
    
    /**
     * @param id id of the TextLookUp object that needs to be updated
     * @param textLookupDto
     * @return
     * @throws NotFoundException
     */
    @ApiOperation(value = "Updates an instance of a TextLookUp identified by the id", notes = "A PUT request to update the Text Lookup value", response = TextLookUpDto.class)
    @ApiResponses(value = [
        @ApiResponse(code = 200, message = "Returns a copy of the TextLookUp as identified by the id"),
        @ApiResponse(code = 404, message = "Returns an Error object stating that the resource could not be found. This happens if the specified id cannot be found in the database.")
    ])
    @RequestMapping(value = "/{id}", produces = ["application/json"], method = RequestMethod.PUT)
    public ResponseEntity<TextLookUpDto> updateTextLookUp(
            @ApiParam(value = "id for object to be updated", required = true)
            @PathVariable(value = "id") Integer id,
            @RequestBody @Valid TextLookUpDto textLookupDto
    ) throws NotFoundException {
        if(id != textLookupDto.id){
            throw new InvalidDataException("ID of supplied object does not match the ID in the URI");
        }
        TextLookup textLookUp = textLookupService.updateTextLookUp(id, textLookupDto)
        return new ResponseEntity<TextLookUpDto>(new TextLookUpDto(textLookUp), HttpStatus.OK);
    }
    
    /**
     * @param id - unique id to be retrieved
     * @return
     * @throws NotFoundException
     */
    @ApiOperation(value="Retrieves an instance of a TextLookUp identified by the id", response = TextLookUpDto.class)
    @ApiResponses(value = [
        @ApiResponse(code = 200, message = "An instance of text look up")
    ])
    @RequestMapping(value = "/{id}", produces = ["application/json"], method = RequestMethod.GET)
    public ResponseEntity<TextLookUpDto> getTextLookUpById(
            @ApiParam(value = "id", required = true)
            @PathVariable (value = "id") Integer id
    )throws NotFoundException{
        TextLookup textLookUp =  textLookupService.findById(id)
        if(TextLookup == null){
            throw new NotFoundException();
        }
        return new ResponseEntity<TextLookUpDto>(new TextLookUpDto(textLookUp), HttpStatus.OK)
    }
}
