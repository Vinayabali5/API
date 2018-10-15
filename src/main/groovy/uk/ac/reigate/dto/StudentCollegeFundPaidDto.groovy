package uk.ac.reigate.dto

import uk.ac.reigate.domain.academic.Student
import uk.ac.reigate.domain.admissions.CollegeFundPaid

class StudentCollegeFundPaidDto implements Serializable {
    
    Integer studentId
    
    Integer collegeFundId
    
    String _collegeFundDescription
    
    Integer collegeFundPaidYears
    
    
    StudentCollegeFundPaidDto(){
    }
    
    
    
    StudentCollegeFundPaidDto(Integer student, Integer collegeFundId, String collegeFundDescription,Integer collegeFundPaidYears){
        this.studentId = student
        this.collegeFundId = collegeFundId
        this._collegeFundDescription = collegeFundDescription
        this.collegeFundPaidYears =  collegeFundPaidYears
    }
    
    
    StudentCollegeFundPaidDto(Student student, CollegeFundPaid collegeFundPaid){
        this.studentId= student.id
        this.collegeFundId = collegeFundPaid.id
        this._collegeFundDescription = collegeFundPaid.collegeFundPaid
        this.collegeFundPaidYears = student.collegeFundPaidYears
    }
}
