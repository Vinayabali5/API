package uk.ac.reigate.dto;


import groovy.transform.EqualsAndHashCode
import uk.ac.reigate.domain.Staff
import uk.ac.reigate.domain.academic.Faculty

import com.fasterxml.jackson.annotation.JsonFormat
import com.fasterxml.jackson.annotation.JsonProperty
import com.fasterxml.jackson.databind.annotation.JsonSerialize

/**
 *
 * JSON serializable DTO containing Faculty data
 *
 */
@JsonSerialize
@EqualsAndHashCode(includeFields=true)
public class FacultyDto implements Serializable {
    
    @JsonProperty
    private Integer id;
    
    @JsonProperty
    private String code;
    
    @JsonProperty
    private String description;
    
    @JsonProperty
    private Integer hofId;
    
    @JsonProperty
    private Integer dolId;
    
    @JsonProperty
    private Integer adolId;
    
    @JsonProperty
    private Integer pdId;
    
    @JsonProperty
    private Integer apdId;
    
    @JsonProperty
    private Date validFrom;
    
    @JsonProperty
    private Date validTo;
    
    @JsonProperty
    private String _hofName
    
    @JsonProperty
    private String _hofInitials
    
    @JsonProperty
    private String _pdName
    
    @JsonProperty
    private String _pdInitials
    
    @JsonProperty
    private String _apdName
    
    @JsonProperty
    private String _apdInitials
    
    @JsonProperty
    private String _dolName
    
    @JsonProperty
    private String _dolInitials
    
    @JsonProperty
    private String _adolName
    
    @JsonProperty
    private String _adolInitials
    
    
    
    
    /**
     * Default No Args constructor
     */
    public FacultyDto() {
    }
    
    /**
     * Constructor to create an FacultyDto object from a Faculty object
     *
     * @param faculty the Faculty object to use for construction
     */
    FacultyDto(Faculty faculty) {
        this.id = faculty.id;
        this.code = faculty.code;
        this.description = faculty.description;
        this.hofId = faculty.hof != null ? faculty.hof.id : null;
        this.dolId = faculty.dol !=  null ? faculty.dol.id : null
        this.adolId = faculty.adol != null ? faculty.adol.id : null;
        this.pdId = faculty.pd != null ? faculty.pd.id : null;
        this.apdId = faculty.apd != null ? faculty.apd.id : null
        this.validFrom = faculty.validFrom
        this.validTo = faculty.validTo
        this._hofName = faculty.hof != null ? faculty.hof.knownAs : null;
        this._hofInitials = faculty.hof != null ? faculty.hof.initials : null;
        this._pdName = faculty.pd != null ? faculty.pd.knownAs : null;
        this._pdInitials = faculty.pd != null ? faculty.pd.initials : null;
        this._apdName = faculty.apd != null ? faculty.apd.knownAs : null;
        this._apdInitials = faculty.apd != null? faculty.apd.initials : null;
        this._dolName = faculty.dol !=  null ? faculty.dol.knownAs : null;
        this._dolInitials = faculty.dol !=  null ? faculty.dol.initials : null;
        this._adolName = faculty.adol != null ? faculty.adol.knownAs : null;
        this._adolInitials = faculty.adol != null ? faculty.adol.initials : null;
    }
    /**
     * Constructor to create a FacultyDto object
     */
    public FacultyDto(Integer id, String code, String description, Integer hofId, Integer dolId, Integer adolId, Integer pdId, Integer apdId, Date validFrom, Date validTo , String hofName) {
        this.id = id;
        this.code = code;
        this.description = description;
        this.hofId = hofId;
        this.dolId = dolId;
        this.adolId = adolId;
        this.pdId = pdId;
        this.apdId = apdId;
        this.validFrom = validFrom;
        this.validTo = validTo;
        this._hofName = hofName;
    }
    
    /**
     * Constructor to create a FacultyDto object
     * 
     * @param id The Id for the Faculty
     * @param code The code of the Faculty
     * @param description The description of the Faculty
     * @param hof The Staff object for the Faculty
     * @param dol The Staff object for the Faculty
     * @param adol The Staff object for the Faculty
     * @param pd The Staff object for the Faculty
     * @param apd The Staff object for the Faculty
     * @param validFrom The validFrom  for the Faculty
     * @param validTo The validTo of the Faculty
     */
    public FacultyDto(Integer id, String code, String description, Staff hof, Staff dol, Staff adol, Staff pd, Staff apd, Date validFrom, Date validTo , String hofName) {
        this(id, code, description, hof != null ? hof.id : null, dol != null ? dol.id : null, adol != null ? adol.id : null, pd != null ? pd.id : null, apd != null ? apd.id : null, validFrom, validTo, hof != null ? hof.knownAs : null)
    }
    
    @Override
    public String toString() {
        return "FacultyDto [id=" + id + ", code=" + code + ", description=" + description + ", hof=" + hofId + ", dol=" + dolId + ", adol=" + adolId + ", pd=" + pdId + ", apd=" + apdId + ", validFrom=" + validFrom + ", validTo=" + validTo + "]";
    }
    
    public static FacultyDto mapFromFacultyEntity(Faculty faculty) {
        return new FacultyDto(faculty)
    }
    
    public static List<FacultyDto> mapFromFacultiesEntities(List<Faculty> faculties) {
        return faculties.collect { faculty ->  new FacultyDto(faculty) };
    }
    
    public static Faculty mapToFacultyEntity(FacultyDto facultyDto, Staff hof, Staff dol, Staff adol, Staff pd, Staff apd) {
        return new Faculty(facultyDto.id, facultyDto.code, facultyDto.description, hof, dol, adol, pd, apd, facultyDto.validFrom, facultyDto.validTo)
    }
}
