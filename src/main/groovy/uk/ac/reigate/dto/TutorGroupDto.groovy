package uk.ac.reigate.dto;


import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;

import groovy.transform.EqualsAndHashCode
import uk.ac.reigate.domain.Room;
import uk.ac.reigate.domain.Staff;
import uk.ac.reigate.domain.academic.Faculty
import uk.ac.reigate.domain.lookup.TutorGroup

/**
 *
 * JSON serializable DTO containing TutorGroup data
 *
 */
@JsonSerialize
@EqualsAndHashCode(includeFields=true)
public class TutorGroupDto implements Serializable {
    
    @JsonProperty
    private Integer id;
    
    @JsonProperty
    private String code;
    
    @JsonProperty
    private String description;
    
    @JsonProperty
    private Integer facultyId;
    
    @JsonProperty
    private Integer tutorId;
    
    @JsonProperty
    private Integer seniorTutorId;
    
    @JsonProperty
    private Integer roomId;
    
    @JsonProperty
    private Integer _facultyCode;
    
    @JsonProperty
    private String _facultyDesc;
    
    @JsonProperty
    private String _roomCode
    
    @JsonProperty
    private String _tutorInitials
    
    @JsonProperty
    private String _tutorName
    
    @JsonProperty
    private String _seniorTutorInitials
    
    @JsonProperty
    private String _seniorTutorName
    
    @JsonProperty
    private Boolean inUse
    
    
    /**
     * Default No Args constructor
     */
    public TutorGroupDto() {
    }
    
    /**
     * Constructor to create a TutorGroupDto object from a TutorGroup object
     *
     * @param tutorGroup the TutorGroup object to use for construction
     */
    TutorGroupDto(TutorGroup tutorGroup) {
        this.id = tutorGroup.id;
        this.code = tutorGroup.code;
        this.description = tutorGroup.description;
        this.facultyId = tutorGroup.faculty != null ? tutorGroup.faculty.id : null;
        this.tutorId = tutorGroup.tutor != null ? tutorGroup.tutor.id : null;
        this.seniorTutorId = tutorGroup.seniorTutor != null ? tutorGroup.seniorTutor.id : null;
        this.roomId = tutorGroup.room != null ? tutorGroup.room.id : null;
        this._facultyCode =  tutorGroup.faculty != null ? tutorGroup.faculty.code : null;
        this._facultyDesc =  tutorGroup.faculty != null ? tutorGroup.faculty.description : null;
        this._roomCode = tutorGroup.room != null ? tutorGroup.room.code : null;
        this._tutorInitials = tutorGroup.tutor != null ? tutorGroup.tutor.initials : null;
        this._tutorName = tutorGroup.tutor != null ? tutorGroup.tutor.knownAs : null;
        this._seniorTutorInitials = tutorGroup.seniorTutor != null ? tutorGroup.seniorTutor.initials : null;
        this._seniorTutorName = tutorGroup.seniorTutor != null ? tutorGroup.seniorTutor.knownAs : null;
        this.inUse = tutorGroup.inUse;
    }
    /**
     * Constructor to create a TutorGroupDto object with the basic data with no linked objects.
     *
     * @param id the Id for the TutorGroup
     * @param code the code for the TutorGroup
     * @param description the description for the TutorGroup
     * @param facultyId the facultyId for the TutorGroup
     */
    public TutorGroupDto(Integer id, String code, String description, Integer facultyId, String facultyCode , String facultyDescrition) {
        this.id = id;
        this.code = code;
        this.description = description;
        this.facultyId = facultyId;
        this.tutorId = null;
        this.seniorTutorId = null;
        this.roomId = null;
        this._facultyCode =  facultyCode;
        this._facultyDesc =  facultyDescrition;
    }
    
    /**
     * Constructor to create a TutorGroupDto object with the basic data with no linked objects.
     *
     * @param id the Id for the TutorGroup
     * @param code the code for the TutorGroup
     * @param description the description for the TutorGroup
     * @param facultyId the facultyId for the TutorGroup
     * @param tutorId the tutorId for the TutorGroup
     * @param seniorTutorId the seniorTutorId for the TutorGroup
     * @param roomId the roomId for the TutorGroup
     */
    public TutorGroupDto(Integer id, String code, String description, Integer facultyId, Integer tutorId, Integer seniorTutorId, Integer roomId, String facultyCode, String facultyDesc, String roomCode , String tutorInitials, String tutorName, String sTutorInitials, String sTutorName, Boolean inUse) {
        this.id = id;
        this.code = code;
        this.description = description;
        this.facultyId = facultyId;
        this.tutorId = tutorId;
        this.seniorTutorId = seniorTutorId;
        this.roomId = roomId;
        this._facultyCode =  facultyCode;
        this._facultyDesc =  facultyDesc;
        this._roomCode = roomCode;
        this._tutorInitials = tutorInitials;
        this._tutorName = tutorName;
        this._seniorTutorInitials = sTutorInitials;
        this._seniorTutorName = sTutorName;
        this.inUse = inUse;
    }
    
    
    /**
     * Constructor to create a TutorGroupDto object with the basic data and the linked Faculty object 
     *
     * @param id the Id for the TutorGroup
     * @param code the code for the TutorGroup
     * @param description the description for the TutorGroup
     * @param faculty the faculty for the TutorGroup
     */
    public TutorGroupDto(Integer id, String code, String description, Faculty faculty) {
        this(id, code, description, faculty != null ? faculty.id : null, faculty != null ? faculty.code : null , faculty != null ? faculty.description : null)
    }
    
    /**
     * Constructor to create a TutorGroupDto object with the basic data and the linked Faculty object 
     *
     * @param id the Id for the TutorGroup
     * @param code the code for the TutorGroup
     * @param description the description for the TutorGroup
     * @param faculty the faculty for the TutorGroup
     * @param tutor the tutor for the TutorGroup
     * @param seniorTutor the seniorTutor for the TutorGroup
     * @param room the room for the TutorGroup
     */
    public TutorGroupDto(Integer id, String code, String description, Faculty faculty, Staff tutor, Staff seniorTutor, Room room, Boolean inUse) {
        this(id, code, description, faculty != null ? faculty.id : null, tutor != null ? tutor.id : null, seniorTutor != null ? seniorTutor.id : null, room != null ? room.id : null, faculty != null ? faculty.code : null, faculty != null ? faculty.description : null, tutor != null ? tutor.initials : null, tutor != null ? tutor.knownAs : null, seniorTutor != null ? seniorTutor.initials : null, seniorTutor != null ? seniorTutor.knownAs : null, inUse)
    }
    
    @Override
    public String toString() {
        return "TutorGroupDto [id=" + id + ", code=" + code + ", description=" + description + ", faculty=" + facultyId + ", tutor=" + tutorId + ", seniorTutor=" + seniorTutorId + ", room=" + roomId + "]";
    }
    
    public static TutorGroupDto mapFromTutorGroupEntity(TutorGroup tutorGroup) {
        return new TutorGroupDto(tutorGroup)
    }
    
    public static List<TutorGroupDto> mapFromTutorGroupsEntities(List<TutorGroup> tutorGroups) {
        List<TutorGroupDto> output = tutorGroups.collect { tutorGroup ->  new TutorGroupDto(tutorGroup) };
        return output
    }
    
    public static TutorGroup mapToTutorGroupEntity(TutorGroupDto tutorGroupDto, Faculty faculty) {
        return new TutorGroup(tutorGroupDto.id, tutorGroupDto.code, tutorGroupDto.description, faculty)
    }
    
    public static TutorGroup mapToTutorGroupEntity(TutorGroupDto tutorGroupDto, Faculty faculty, Staff tutor, Staff seniorTutor, Room room) {
        return new TutorGroup(tutorGroupDto.id, tutorGroupDto.code, tutorGroupDto.description, faculty, tutor, seniorTutor, room, tutorGroupDto.inUse)
    }
}
