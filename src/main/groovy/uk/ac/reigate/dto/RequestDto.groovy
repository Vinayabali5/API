package uk.ac.reigate.dto;


import groovy.transform.EqualsAndHashCode
import uk.ac.reigate.domain.academic.AcademicYear
import uk.ac.reigate.domain.academic.Student
import uk.ac.reigate.domain.admissions.Request

import com.fasterxml.jackson.annotation.JsonProperty
import com.fasterxml.jackson.databind.annotation.JsonSerialize

/**
 *
 * JSON serializable DTO containing Request data
 *
 */
@JsonSerialize
@EqualsAndHashCode(includeFields=true)
public class RequestDto implements Serializable {
    
    @JsonProperty
    private Integer id;
    
    @JsonProperty
    private Integer studentId;
    
    @JsonProperty
    private String request;
    
    @JsonProperty
    private Integer academicYearId;
    
    @JsonProperty
    private Boolean coreAim;
    
    @JsonProperty
    private Boolean broadeningSubject;
    
    @JsonProperty
    private Boolean chosenAgainstAdvice
    
    @JsonProperty
    private Boolean allocated
    
    @JsonProperty
    private String _academicYearCode
    
    /**
     * Default No Args constructor
     */
    public RequestDto() {
    }
    
    /**
     * Constructor to create a RequestDto object from a Request object
     *
     * @param request the Request object to use for construction
     */
    RequestDto(Request request) {
        this.id = request.id;
        this.studentId = request.student != null ? request.student.id : null;
        this.request = request.request;
        this.academicYearId = request.academicYear != null ? request.academicYear.id : null;
        this.coreAim = request.coreAim;
        this.broadeningSubject = request.broadeningSubject;
        this.chosenAgainstAdvice = request.chosenAgainstAdvice;
        this.allocated = request.allocated;
        this._academicYearCode=request.academicYear != null ? request.academicYear.code : null;
    }
    /**
     * Constructor to create a RequestDto object with the basic data with no linked objects.
     *
     * @param id the Id for the Request
     * @param studentId the studentId for the Request
     * @param request the request for the Request
     * @Param academicYearId the academicYearId of the Request
     * @Param coreAim the coreAim of the Request
     * @Param broadeningSubject the broadeningSubject of the Request
     */
    public RequestDto(Integer id, Integer studentId, String request, Integer academicYearId, Boolean coreAim, Boolean broadeningSubject, Boolean chosenAgainstAdvice, Boolean allocated, String academicYearCode) {
        this.id = id;
        this.studentId = studentId;
        this.request = request;
        this.academicYearId = academicYearId;
        this.coreAim = coreAim;
        this.broadeningSubject = broadeningSubject;
        this.chosenAgainstAdvice = chosenAgainstAdvice;
        this.allocated = allocated;
        this._academicYearCode=academicYearCode;
    }
    
    /**
     * Constructor to create a RequestDto object with the basic data and the linked student object 
     *
     * @param id the Id for the Request
     * @param student the student for the Request
     * @param request the request for the Request
     * @Param academicYearId the academicYearId of the Request
     * @Param coreAim the coreAim of the Request
     * @Param broadeningSubject the broadeningSubject of the Request
     */
    public RequestDto(Integer id, Student student, String request, AcademicYear academicYear, Boolean coreAim, Boolean broadeningSubject, Boolean chosenAgainstAdvice, Boolean allocated) {
        this(id, student != null ? student.id : null, request, academicYear != null ? academicYear.id : null, coreAim, broadeningSubject, chosenAgainstAdvice, allocated,academicYear != null ? academicYear.code : null)
    }
    
    @Override
    public String toString() {
        return "Request [id=" + id + ", student=" + studentId + ", request=" + request + ", academicYear=" + academicYearId + ", coreAim=" + coreAim + ", broadeningSubject=" + broadeningSubject + ", chosenAgainstAdvice=" + chosenAgainstAdvice +", allocated=" + allocated + "]";
    }
    
    public static RequestDto mapFromRequestEntity(Request request) {
        return new RequestDto(request)
    }
    
    public static List<RequestDto> mapFromRequestsEntities(List<Request> requests) {
        return requests.collect { request ->  new RequestDto(request) };
    }
    
    public static Request mapToRequestEntity(RequestDto requestDto, Student student, AcademicYear academicYear) {
        return new Request(requestDto.id, student, requestDto.request, academicYear, requestDto.coreAim, requestDto.broadeningSubject, requestDto.chosenAgainstAdvice, requestDto.allocated)
    }
}
