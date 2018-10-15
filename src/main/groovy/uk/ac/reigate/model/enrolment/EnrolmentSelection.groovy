package uk.ac.reigate.model.enrolment

import javax.validation.constraints.NotNull;

/**
 * This object represents an Enrolment Selection, that is a specified course group to be used for an enrolment 
 * programme. 
 * 
 * @author Michael Horgan
 *
 */
class EnrolmentSelection {
    
    /**
     * The course_group_id for the enrolment selection
     */
    @NotNull
    Integer courseGroupId
    
    /**
     * The course groups spec code for the the enrolment selection
     */
    @NotNull
    String spec
}
