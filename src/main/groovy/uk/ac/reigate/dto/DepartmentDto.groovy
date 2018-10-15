package uk.ac.reigate.dto;


import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;

import groovy.transform.EqualsAndHashCode;
import uk.ac.reigate.domain.Staff
import uk.ac.reigate.domain.academic.Department
import uk.ac.reigate.domain.academic.Faculty

/**
 *
 * JSON serializable DTO containing Department data
 *
 */
@JsonSerialize
@EqualsAndHashCode(includeFields=true)
public class DepartmentDto implements Serializable {
    
    @JsonProperty
    private Integer id;
    
    @JsonProperty
    private String name;
    
    @JsonProperty
    private String description;
    
    @JsonProperty
    private Integer facultyId;
    
    @JsonProperty
    private String _facultyDescription;
    
    @JsonProperty
    private Integer hodId;
    
    @JsonProperty
    private Integer hod2Id;
    
    @JsonProperty
    private String _hodName;
    
    @JsonProperty
    private String _hod2Name;
    
    @JsonProperty
    private Boolean academic;
    
    
    /**
     * Default No Args constructor
     */
    public DepartmentDto() {
    }
    
    /**
     * Constructor to create a DepartmentDto object from a Department object
     *
     * @param department the Department object to use for construction
     */
    DepartmentDto(Department department) {
        this.id = department.id;
        this.name = department.name;
        this.description = department.description;
        this.facultyId = department.faculty != null ? department.faculty.id : null;
        this._facultyDescription = department.faculty != null ? department.faculty.description : ''
        this.hodId = department.hod != null ? department.hod.id : null
        this.hod2Id = department.hod2 != null ? department.hod2.id : null;
        this._hodName = department.hod != null ? department.hod.knownAs : null
        this._hod2Name = department.hod2 != null ? department.hod2.knownAs : null
        this.academic = department.academic;
    }
    
    /**
     * Constructor to create a DepartmentDto object
     * 
     * @param id the Id for the Department
     * @param name the name for the Department
     * @param description the description for the Department
     * @Param facultyId the facultyId for the Department
     * @Param hodId the hodId for the Department
     * @Param hod2Id the hod2Id for the Department
     */
    public DepartmentDto( Integer id, String name, String description, Integer facultyId, Integer hodId,  Integer hod2Id, String hodName, String hod2Name, Boolean academic) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.facultyId = facultyId;
        this.hodId = hodId;
        this.hod2Id = hod2Id;
        this._hodName =  hodName;
        this._hod2Name = hod2Name;
        this.academic = academic;
    }
    
    /**
     * Constructor to create a DepartmentDto object
     *
     * @param id the Id for the Department
     * @param name the name for the Department
     * @param description the description for the Department
     * @Param faculty the Faculty object for the Department
     * @Param hod the staff object for the Department
     * @Param hod2 the Staff object for the Department
     */
    public DepartmentDto(Integer id, String name, String description, Faculty faculty, Staff hod, Staff hod2, Boolean academic){
        this(id, name, description, faculty != null ? faculty.id : null, hod != null ? hod.id : null, hod2 != null ? hod2.id : null, hod != null ? hod.knownAs : null, hod2 != null ? hod2.knownAs : null, academic)
    }
    
    @Override
    public String toString() {
        return "DepartmentDto [id=" + id + ", name=" + name + ", description=" + description + ", faculty=" + facultyId + ", hod=" + hodId + ", hod2=" + hod2Id +", academic=" + academic +"]";
    }
    
    public static DepartmentDto mapFromDepartmentEntity(Department department) {
        return  new DepartmentDto(department)
    }
    
    public static List<DepartmentDto> mapFromDepartmentsEntities(List<Department> departments) {
        return departments.collect { department ->  new DepartmentDto(department) };
    }
    
    public static Department mapToDepartmentEntity(DepartmentDto departmentDto, Faculty faculty, Staff hod, Staff hod2) {
        return new Department(departmentDto.id, departmentDto.name, departmentDto.description, faculty, hod, hod2, departmentDto.academic)
    }
}
