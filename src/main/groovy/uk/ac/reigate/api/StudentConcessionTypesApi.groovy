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

import uk.ac.reigate.domain.academic.Student
import uk.ac.reigate.domain.learning_support.ConcessionType
import uk.ac.reigate.domain.learning_support.StudentConcessionType
import uk.ac.reigate.dto.StudentConcessionTypeDto
import uk.ac.reigate.exceptions.NotFoundException
import uk.ac.reigate.services.ConcessionTypeService
import uk.ac.reigate.services.StudentConcessionTypeService
import uk.ac.reigate.services.StudentService


@Controller
@RequestMapping(value = "/api/students/{studentId}/concessionTypes", produces = [ APPLICATION_JSON_VALUE ])
@Api(value = "/api/students/{studentId}/concessionTypes", description = "the studentConcessionTypes API")
public class StudentConcessionTypesApi {
    
    private static final Logger LOGGER = Logger.getLogger(StudentConcessionTypesApi.class);
    
    @Autowired
    private final StudentService studentService;
    
    @Autowired
    private final StudentConcessionTypeService studentConcessionTypeService;
    
    @Autowired
    private final ConcessionTypeService concessionTypeService;
    
    /**
     * Default NoArgs constructor
     */
    StudentConcessionTypesApi() {}
    
    /**
     * Autowired constructor
     */
    StudentConcessionTypesApi(StudentService studentService, ConcessionTypeService concessionTypeService) {
        this.studentService = studentService;
    }
    
    /**
     * The studentConcessionTypesGet method is used to retrieve a full list of all the StudentConcessionTypeDto objects
     *
     * @return A ResponseEntity with the corresponding list of StudentConcessionTypeDto objects
     * @throws NotFoundException if nothing is found then the the NotFoundException is thrown.
     */
    @ApiOperation(value = "Collection of StudentConcessionType entities", notes = "A GET request to the StudentConcessionTypes endpoint returns an array of all the studentConcessionTypes in the system.", response = StudentConcessionTypeDto.class, responseContainer = "List")
    @ApiResponses(value = [
        @ApiResponse(code = 200, message = "An array of studentConcessionTypes")
    ])
    @RequestMapping(value = "", produces = ["application/json"], method = RequestMethod.GET)
    public ResponseEntity<List<StudentConcessionTypeDto>> studentConcessionTypesGet(
            @ApiParam(value = "The unique ID of the StudentConcessionType to retrieve", required = true)
            @PathVariable("studentId") Integer studentId) throws NotFoundException {
        LOGGER.info("** StudentConcessionTypesApi - studentConcessionTypesGet");
        List<StudentConcessionType> studentConcessionTypes = studentConcessionTypeService.getByStudent(studentId);
        if(studentConcessionTypes.size() != 0) {
            LOGGER.info("II Results found: " + studentConcessionTypes.size())
            List<StudentConcessionTypeDto> studentConcession = new ArrayList<StudentConcessionTypeDto>()
            return new ResponseEntity<List<StudentConcessionTypeDto>>(StudentConcessionTypeDto.mapFromStudentConcessionTypesEntities(studentConcessionTypes), HttpStatus.OK)
        } else {
            throw new NotFoundException()
        }
    }
    
    /**
     * The studentConcessionTypesPost method is used to create a new instance of a StudentConcessionType from the supplied StudentConcessionTypeDto
     *
     * @param studentConcessionType the StudentConcessionTypeDto to use to create the new StudentConcessionType object
     * @return A ResponseEntity with the newly created StudentConcessionType object
     * @throws InvalidDataException if there is an issue with the data provided in the RequestBody then an InvalidDataException is thrown
     */
    @ApiOperation(value = "Create new StudentConcessionType entity", notes = "A POST request to the StudentConcessionTypes endpoint with a StudentConcessionType as the body will create a new instance of a StudentConcessionTypes in the database.", response = StudentConcessionTypeDto.class)
    @ApiResponses(value = [
        @ApiResponse(code = 200, message = "The instance of the newly created StudentConcessionType object.")
    ])
    @RequestMapping(value = "", produces = ["application/json"], method = RequestMethod.POST)
    public ResponseEntity<StudentConcessionTypeDto> studentConcessionTypesPost(
            @ApiParam(value = "The StudentConcessionType object to be created", required = true)
            @RequestBody @Valid StudentConcessionTypeDto studentConcessionType) {
        //throws NotFoundException, InvalidDataException {
        LOGGER.info("** StudentConcessionTypesApi - studentConcessionTypesPOST");
        
        Student student
        if (studentConcessionType.studentId != null) {
            student = studentService.findById(studentConcessionType.studentId)
        }
        
        ConcessionType concessionType
        if(studentConcessionType.concessionTypeId != null) {
            concessionType = concessionTypeService.findById(studentConcessionType.concessionTypeId)
        }
        
        LOGGER.info("** StudentConcessionTypesApi - studentConcessionTypesPOST12");
        StudentConcessionType studentConcessionTypeToSave = StudentConcessionTypeDto.mapToStudentConcessionTypeEntity(studentConcessionType, student, concessionType )
        
        StudentConcessionType studentConcessionTypeSaved = studentConcessionTypeService.save(studentConcessionTypeToSave)
        
        return new ResponseEntity<StudentConcessionTypeDto>(StudentConcessionTypeDto.mapFromStudentConcessionTypeEntity(studentConcessionTypeSaved), HttpStatus.CREATED);
    }
    
    
    /**
     * This method is using studentId and concessionTypeId to delete By StudentConcessionType
     */
    @Secured('ROLE_Exams Officer')
    @RequestMapping(value = "/{concessionTypeId}", produces = ["application/json"], method = RequestMethod.DELETE)
    public ResponseEntity<StudentConcessionTypeDto> studentConcessionTypesDeleteByStudentAndConcessionType(
            @ApiParam(value = "The unique ID of the CourseGroup to retrieve", required = true)
            @PathVariable("studentId") Integer studentId,
            @ApiParam(value = "The CourseGroup object to be created, without the courseGroupId fields", required = true)
            @PathVariable("concessionTypeId") Integer concessionTypeId ) {
        LOGGER.info("** StudentConcessionTypesApi - studentConcessionTypesDelete");
        Boolean deleted = studentConcessionTypeService.deleteByIds(studentId, concessionTypeId);
        LOGGER.info("***StudentConcessionTypesApi:- Deleted !!! ")
        return new ResponseEntity<>(HttpStatus.OK)
    }
}
