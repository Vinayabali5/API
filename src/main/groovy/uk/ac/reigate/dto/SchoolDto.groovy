package uk.ac.reigate.dto;


import com.fasterxml.jackson.databind.annotation.JsonSerialize;

import groovy.transform.EqualsAndHashCode;
import uk.ac.reigate.domain.academic.School
import uk.ac.reigate.domain.lookup.SchoolPriority
import uk.ac.reigate.domain.lookup.SchoolType

import javax.persistence.Column

import com.fasterxml.jackson.annotation.JsonFormat
import com.fasterxml.jackson.annotation.JsonProperty
/**
 *
 * JSON serializable DTO containing School data
 *
 */
@JsonSerialize
@EqualsAndHashCode(includeFields=true)
public class SchoolDto implements Serializable {
    
    @JsonProperty
    private Integer id;
    
    @JsonProperty
    private String name;
    
    @JsonProperty
    private Integer typeId;
    
    @JsonProperty
    private String _typeDescription
    
    @JsonProperty
    private String urn;
    
    @JsonProperty
    private Integer priorityId;
    
    @JsonProperty
    private String _priorityCode
    
    @JsonProperty
    private String line1;
    
    @JsonProperty
    private String line2;
    
    @JsonProperty
    private String line3;
    
    @JsonProperty
    private String postcode;
    
    @JsonProperty
    private String telephone
    
    @JsonProperty
    private String headTitle
    
    @JsonProperty
    private String headFirstName
    
    @JsonProperty
    private String headSurname
    
    @JsonProperty
    private String contactTitle
    
    @JsonProperty
    private  String contactFirstName
    
    @JsonProperty
    private String contactSurname
    
    
    /**
     * Default No Args constructor
     */
    public SchoolDto() {
    }
    
    /**
     * Constructor to create a SchoolDto object from a School object
     *
     * @param school the School object to use for construction
     */
    SchoolDto(School school){
        this.id = school.id;
        this.name = school.name;
        this.typeId = school.type != null ? school.type.id : null;
        this._typeDescription = school.type != null ? school.type.description : null;
        this.priorityId = school.priority != null ? school.priority.id : null;
        this._priorityCode = school.priority != null ? school.priority.code : null;
        this.urn = school.urn;
        this.line1 = school.line1;
        this.line2 = school.line2;
        this.line3 = school.line3;
        this.postcode = school.postcode;
        this.telephone = school.telephone;
        this.headTitle = school.headTitle;
        this.headFirstName = school.headFirstName;
        this.headSurname = school.headSurname;
        this.contactTitle = school.contactTitle;
        this.contactFirstName = school.contactFirstName;
        this.contactSurname = school.contactSurname;
    }
    
    @Override
    public String toString() {
        return "SchoolDto [id=" + id + ", name=" + name + ", type=" + typeId + ", priority=" + priorityId + ", urn=" + urn + ", line1=" + line1 + ", line2=" + line2 + ", line3=" + line3 + ", postcode=" + postcode + "]";
    }
    
    public static SchoolDto mapFromSchoolEntity(School school) {
        return new SchoolDto(school)
    }
    
    public static List<SchoolDto> mapFromSchoolsEntities(List<School> schools) {
        return schools.collect { school ->  new SchoolDto(school) };
    }
    
    public static School mapToSchoolEntity(SchoolDto schoolDto, SchoolPriority priority, SchoolType type){
        return new School(schoolDto.id, schoolDto.name, type, priority, schoolDto.urn,schoolDto.line1, schoolDto.line2,schoolDto.line3, schoolDto.postcode, schoolDto.telephone, schoolDto.headTitle, schoolDto.headFirstName, schoolDto.headSurname, schoolDto.contactTitle, schoolDto.contactFirstName, schoolDto.contactSurname)
    }
}
