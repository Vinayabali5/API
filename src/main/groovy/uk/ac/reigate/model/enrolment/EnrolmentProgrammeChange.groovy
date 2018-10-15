package uk.ac.reigate.model.enrolment

import javax.validation.constraints.NotNull

import org.hibernate.validator.constraints.NotEmpty


/**
 * This object is used to describe a programme change for a given student. 
 * 
 * @author Michael Horgan
 *
 */
class EnrolmentProgrammeChange {
    
    /**
     * The student_id fields for the programme to be modified
     */
    @NotNull
    Integer studentId
    
    /**
     * The academic_year_id for the programme to be modified
     */
    @NotNull
    Integer academicYearId
    
    /**
     * The date that the programme change will be effective from
     */
    @NotNull
    Date changeDate
    
    /**
     * A list of the new course groups that make up the new programme
     */
    @NotEmpty
    EnrolmentSelection[] courseGroups
}
