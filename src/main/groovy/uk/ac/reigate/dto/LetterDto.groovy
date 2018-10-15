package uk.ac.reigate.dto

import groovy.transform.EqualsAndHashCode

import com.fasterxml.jackson.annotation.JsonProperty
import com.fasterxml.jackson.databind.annotation.JsonSerialize

import uk.ac.reigate.domain.ilp.Letter
/**
 *
 * JSON serializable DTO containing Letter data
 *
 */
@JsonSerialize
@EqualsAndHashCode(includeFields=true)
public class LetterDto implements Serializable{
    
    @JsonProperty
    private Integer id;
    
    @JsonProperty
    private Integer studentId;
    
    @JsonProperty
    private Date requestedDate;
    
    @JsonProperty
    private Date letterDate
    
    @JsonProperty
    private String subject
    
    @JsonProperty
    private String re
    
    @JsonProperty
    private Integer letterTypeId
    
    @JsonProperty
    private String _letterType
    
    /**
     * Default No Args constructor
     */
    public LetterDto() {
    }
    
    /**
     * Constructor to create a LetterDto object from a Letter object
     *
     * @param letter the Letter object to use for construction
     */
    LetterDto(Letter letter) {
        this.id = letter.id;
        this.studentId = letter.student != null ? letter.student.id : null;
        this.requestedDate = letter.requestedDate;
        this.letterDate = letter.letterDate;
        this.subject = letter.subject;
        this.re = letter.re;
        this.letterTypeId = letter.letterType != null ? letter.letterType.id : null;
        this._letterType = letter.letterType != null ? letter.letterType.type : null;
    }
    
    @Override
    public String toString() {
        return "LetterDto [id=" + id + ", student=" + studentId + ", requestedDate=" + requestedDate + ", letterDate=" + letterDate + ", subject=" + subject + ", re=" + re + ", reviewMeeting=" + reviewMeeting +", attendance=" + attendance + ", behaviour=" + behaviour +", homework=" + homework+",  punctuality=" + punctuality + ", focus=" + focus + ", deadlines=" + deadlines+ ", incompleteCoursework=" + incompleteCoursework + ", nonEntryWarning=" + nonEntryWarning + ", letterCreated=" + letterCreated + ", notes=" + notes + ", letterType=" + letterTypeId +" ]";
    }
    
    public static LetterDto mapFromLetterEntity(Letter letter) {
        return  new LetterDto(letter)
    }
    
    public static List<LetterDto> mapFromLettersEntities(List<Letter> letters) {
        return letters.collect { letter ->  new LetterDto(letter) };
    }
}
