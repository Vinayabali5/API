package uk.ac.reigate.model.enrolment

import org.hibernate.validator.constraints.NotEmpty

class WorkingCombinationCheck {
    
    @NotEmpty
    String[] specs
    
    Date date
}
