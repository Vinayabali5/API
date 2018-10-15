package uk.ac.reigate.dto;


import groovy.transform.EqualsAndHashCode
import uk.ac.reigate.domain.academic.EntryQualification
import uk.ac.reigate.domain.academic.EntryQualificationType

import com.fasterxml.jackson.annotation.JsonProperty
import com.fasterxml.jackson.databind.annotation.JsonSerialize

/**
 *
 * JSON serializable DTO containing EntryQualification data
 *
 */
@JsonSerialize
@EqualsAndHashCode(includeFields=true)
public class EntryQualificationDto implements Serializable {
    
    @JsonProperty
    private Integer id;
    
    @JsonProperty
    private String title;
    
    @JsonProperty
    private Integer entryQualificationTypeId;
    
    @JsonProperty
    String _entryQualificationTypeDescription
    
    @JsonProperty
    private boolean basicList
    
    @JsonProperty
    private boolean shortCourse
    
    @JsonProperty
    private String dataMatchCode;
    
    @JsonProperty
    private Integer webLinkCode;
    
    /**
     * Default No Args constructor
     */
    public EntryQualificationDto() {
    }
    
    /**
     * Constructor to create an EntryQualificationDto object from a EntryQualification object
     *
     * @param entryQualification the EntryQualification object to use for construction
     */
    EntryQualificationDto(EntryQualification entryQualification) {
        this.id = entryQualification.id;
        this.title = entryQualification.title;
        this.entryQualificationTypeId = entryQualification.type != null ? entryQualification.type.id : null
        this._entryQualificationTypeDescription = entryQualification.type != null ? entryQualification.type.description : 'Not Set'
        this.basicList = entryQualification.basicList
        this.shortCourse = entryQualification.shortCourse
        this.dataMatchCode = entryQualification.dataMatchCode;
        this.webLinkCode = entryQualification.webLinkCode;
    }
    
    /**
     * Constructor to create a EntryQualificationDto object
     * 
     * @param id The Id for the EntryQualification
     * @param title The title for the EntryQualification
     * @param entryQualificationTypeId The entryQualificationTypeId for the EntryQualification
     * @param basicList The basicList for the EntryQualification
     * @param shortCourse The shortCourse for the EntryQualification
     * @param dataMatchCode The dataMatchCode for the EntryQualification
     * @param webLinkCode The webLinkCode for the EntryQualification
     */
    public EntryQualificationDto(Integer id, String title, Integer entryQualificationTypeId, boolean basicList, boolean shortCourse, String dataMatchCode, Integer webLinkCode) {
        this.id = id;
        this.title = title;
        this.entryQualificationTypeId = entryQualificationTypeId
        this.basicList = basicList
        this.shortCourse = shortCourse
        this.dataMatchCode = dataMatchCode;
        this.webLinkCode = webLinkCode;
    }
    /**
     * Constructor to create a EntryQualificationDto object
     *
     * @param id the Id for the EntryQualification
     * @param title the title for the EntryQualification
     * @param dataMatchCode the dataMatchCode for the EntryQualification
     */
    public EntryQualificationDto(Integer id, String title, EntryQualificationType entryQualificationType, boolean basicList, boolean shortCourse, String dataMatchCode, Integer webLinkCode) {
        this(id, title, entryQualificationType != null ? entryQualificationType.id : null, basicList, shortCourse, dataMatchCode, webLinkCode)
    }
    
    @Override
    public String toString() {
        return "EntryQualificationDto [id=" + id + ", title=" + title + ", entryQualificationType=" + entryQualificationTypeId + ", basicList=" + basicList + ", shortCourse=" + shortCourse + ", dataMatchCode=" + dataMatchCode + ", webLinkCode=" + webLinkCode +"]";
    }
    
    public static EntryQualificationDto mapFromEntryQualificationEntity(EntryQualification entryQualification) {
        return new EntryQualificationDto(entryQualification)
    }
    
    public static List<EntryQualificationDto> mapFromEntryQualificationsEntities(List<EntryQualification> entryQualifications) {
        List<EntryQualificationDto> output = entryQualifications.collect { entryQualification ->  new EntryQualificationDto(entryQualification) };
        return output
    }
    
    public static EntryQualification mapToEntryQualificationEntity(EntryQualificationDto entryQualificationDto, EntryQualificationType entryQualificationType) {
        return new EntryQualification(entryQualificationDto.id, entryQualificationDto.title, entryQualificationType, entryQualificationDto.basicList, entryQualificationDto.shortCourse, entryQualificationDto.dataMatchCode, entryQualificationDto.webLinkCode)
    }
}
