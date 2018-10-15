package uk.ac.reigate.dto.admissions

import javax.validation.constraints.NotNull
import javax.validation.constraints.Past
import javax.validation.constraints.Size

import com.fasterxml.jackson.annotation.JsonProperty

import org.hibernate.validator.constraints.NotEmpty

import org.springframework.format.annotation.DateTimeFormat

import uk.ac.reigate.domain.Address
import uk.ac.reigate.domain.Contact
import uk.ac.reigate.domain.Staff
import uk.ac.reigate.domain.academic.AcademicYear
import uk.ac.reigate.domain.academic.School
import uk.ac.reigate.domain.academic.SpecialCategory
import uk.ac.reigate.domain.academic.Student
import uk.ac.reigate.domain.academic.StudentYear
import uk.ac.reigate.domain.admissions.ApplicationStatus
import uk.ac.reigate.domain.admissions.CollegeFundPaid
import uk.ac.reigate.domain.admissions.Interview
import uk.ac.reigate.domain.admissions.OfferType
import uk.ac.reigate.domain.admissions.Request
import uk.ac.reigate.domain.ilr.LLDDHealthProblem
import uk.ac.reigate.domain.ilr.RestrictedUseIndicator
import uk.ac.reigate.domain.lookup.Ethnicity
import uk.ac.reigate.domain.lookup.Gender
import uk.ac.reigate.domain.lookup.Nationality
import uk.ac.reigate.domain.lookup.SchoolReportStatus
import uk.ac.reigate.domain.lookup.StudentType
import uk.ac.reigate.domain.lookup.Title
import uk.ac.reigate.domain.lookup.TutorGroup

/**
 * The ApplicationForm object is a form backing object that is used to create new applications only.
 *
 * @author Vinayabali
 *
 */
class ApplicationFormDto {
    
    /*
     * Phase 1 Fields
     */
    
    @JsonProperty
    Integer id
    
    @JsonProperty
    String referenceNo
    
    @JsonProperty
    ApplicationStatus status
    
    @JsonProperty
    @DateTimeFormat(pattern = "dd/MM/yyyy")
    Date endDate
    
    @JsonProperty
    AcademicYear academicYear
    
    @JsonProperty
    AcademicYear year
    
    @JsonProperty
    @NotNull
    @DateTimeFormat(pattern = "dd/MM/yyyy")
    Date received
    
    @JsonProperty
    Integer personId
    
    @JsonProperty
    @NotEmpty
    @Size(min=2, max=50)
    String firstName
    
    @JsonProperty
    @NotEmpty
    @Size(min=2, max=50)
    String surname
    
    @JsonProperty
    String middleNames
    
    @JsonProperty
    String preferredName
    
    @JsonProperty
    String previousSurname
    
    @JsonProperty
    @NotNull
    @Past
    @DateTimeFormat(pattern = "dd/MM/yyyy")
    Date dob
    
    @JsonProperty
    @NotNull
    Gender gender
    
    @JsonProperty
    Title title
    
    @JsonProperty
    String home
    
    @JsonProperty
    String mobile
    
    @JsonProperty
    String email
    
    @JsonProperty
    Nationality nationality
    
    @JsonProperty
    String countryOfResidence
    
    @JsonProperty
    Boolean resident
    
    @JsonProperty
    Integer addressId
    
    @JsonProperty
    @NotEmpty
    String line1
    
    @JsonProperty
    String line2
    
    @JsonProperty
    String line3
    
    @JsonProperty
    String line4
    
    @JsonProperty
    String line5
    
    @JsonProperty
    String buildingName
    
    @JsonProperty
    String subBuilding
    
    @JsonProperty
    String udprn
    
    @JsonProperty
    @Deprecated
    String paon
    
    @JsonProperty
    String street
    
    @JsonProperty
    String town
    
    @JsonProperty
    String county
    
    @JsonProperty
    @NotEmpty
    String postcode
    
    @JsonProperty
    Boolean sibling
    
    @JsonProperty
    String siblingName
    
    @JsonProperty
    Integer siblingYear
    
    @JsonProperty
    String siblingAdmNo
    
    @JsonProperty
    @NotNull
    School school
    
    @JsonProperty
    SchoolReportStatus schoolReportStatus
    
    @JsonProperty
    List<Contact> contacts
    
    @JsonProperty
    List<Address> addresses
    
    @JsonProperty
    List<Request> requests
    
    @JsonProperty
    CollegeFundPaid collegeFundPaid
    
    @JsonProperty
    Integer collegeFundPaidYears
    
    //	List<CollegeFundPayment> collegeFundPayments
    
    /*
     * Phase 2 Fields
     */
    
    @JsonProperty
    @DateTimeFormat(pattern = "dd/MM/yyyy")
    Date refRequested
    
    @JsonProperty
    @DateTimeFormat(pattern = "dd/MM/yyyy")
    Date refReceived
    
    @JsonProperty
    @DateTimeFormat(pattern = "dd/MM/yyyy")
    Date reportRequested
    
    @JsonProperty
    @DateTimeFormat(pattern = "dd/MM/yyyy")
    Date reportReceived
    
    @JsonProperty
    TutorGroup tutorGroup
    
    @JsonProperty
    Date acceptanceReceived
    
    @JsonProperty
    Ethnicity ethnicity
    
    @JsonProperty
    RestrictedUseIndicator restrictedUseIndicator
    
    @JsonProperty
    LLDDHealthProblem llddHealthProblem
    
    @JsonProperty
    StudentType studentType
    
    @JsonProperty
    String uln
    
    @JsonProperty
    String uci
    
    @JsonProperty
    String admissionsNotes
    
    @JsonProperty
    Interview interview
    
    @JsonProperty
    Staff interviewer
    
    @JsonProperty
    Date interviewDate
    
    @JsonProperty
    OfferType offerType
    
    @JsonProperty
    Date offerSent
    
    @JsonProperty
    Boolean ehcp
    
    @JsonProperty
    String medicalNote
    
    @JsonProperty
    Boolean contactByPost
    
    @JsonProperty
    Boolean contactByPhone
    
    @JsonProperty
    Boolean contactByEmail
    
    @JsonProperty
    Boolean lrsOptOut
    
    @JsonProperty
    Boolean cannotAttendIntro
    
    @JsonProperty
    Boolean cannotAttendInduction
    
    @JsonProperty
    Date inductionDate
    
    @JsonProperty
    Date blueCard
    
    @JsonProperty
    //@JsonFormat(pattern="dd/MM/yyyy HH:mm")
    Date enrolmentInterviewDateTime
    
    @JsonProperty
    Date enrolmentInterviewDate
    
    @JsonProperty
    // @JsonFormat(pattern="yyyy-MM-dd'T'HH:mm:ss")
    Date enrolmentInterviewTime
    
    @JsonProperty
    SpecialCategory specialCategory
    
    /**
     * Default noargs constructor for new blank ApplicationForm
     */
    ApplicationFormDto() {
    }
    
    /**
     * A constructor that uses an Application object to populate the required fields.
     *
     * @param app an Application object, typically retrieved from the database
     */
    ApplicationFormDto(Student student, StudentYear studentYear) {
        this.id = student.id
        this.referenceNo = student.referenceNo
        this.status = student.status
        //endDate is using studentYear table
        this.endDate = studentYear.endDate
        this.received = student.received
        this.academicYear = student.academicYear
        this.year = studentYear.year
        
        // Load Person fields
        this.personId = student.person.id
        this.firstName = student.person.firstName
        this.surname = student.person.surname
        this.middleNames = student.person.middleNames
        this.preferredName = student.person.preferredName
        this.previousSurname = student.person.previousSurname
        this.dob = student.person.dob
        this.gender = student.person.gender
        this.title = student.person.title
        this.home = student.person.home
        this.mobile = student.person.mobile
        this.email = student.person.email
        this.nationality = student.nationality
        this.countryOfResidence = student.countryOfResidence
        this.resident = student.resident
        
        // Load Person.Address fields
        this.addressId = student.person.address.id
        this.line1 = student.person.address.line1
        this.line2 = student.person.address.line2
        this.line3 = student.person.address.line3
        this.line4 = student.person.address.line4
        this.line5 = student.person.address.line5
        this.buildingName = student.person.address.buildingName
        this.subBuilding = student.person.address.subBuilding
        this.postcode = student.person.address.postcode
        
        this.paon = student.person.address.paon
        this.street = student.person.address.street
        this.town = student.person.address.town
        this.county = student.person.address.county
        this.postcode = student.person.address.postcode
        
        // Load sibling fields
        this.sibling = student.sibling
        this.siblingName = student.siblingName
        this.siblingYear = student.siblingYear
        this.siblingAdmNo = student.siblingAdmNo
        
        //load school field
        this.school = student.school
        this.schoolReportStatus = student.schoolReportStatus
        this.refRequested = student.refRequested
        this.refReceived = student.refReceived
        this.reportRequested = student.reportRequested
        this.reportReceived = student.reportReceived
        
        //load tutor field
        this.tutorGroup = studentYear.tutorGroup
        
        //load interview fields
        this.interviewer = student.interviewer
        this.interviewDate = student.interviewDate
        
        //load acceptance field
        this.acceptanceReceived = student.acceptanceReceived
        this.ethnicity = student.ethnicity
        this.restrictedUseIndicator = student.restrictedUseIndicator
        
        //load disability field
        this.llddHealthProblem = student.llddHealthProblem
        this.ehcp = student.ehcp
        this.medicalNote = student.medicalNote
        this.specialCategory = student.specialCategory
        
        //this.lLDDHealthProblem = student.lLDDHealthProblem
        
        //load offer type field
        this.offerType = student.offerType
        this.offerSent = student.offerSent
        
        //load data sharing fields
        this.contactByPost = student.contactByPost
        this.contactByPhone = student.contactByPhone
        this.contactByEmail = student.contactByEmail
        
        this.lrsOptOut = student.lrsOptOut
        
        //load Unique Learner No field and Unique Candidate Identifier field
        this.studentType = studentYear.type
        this.uln = student.uln
        this.uci = student.uci
        this.admissionsNotes = student.admissionsNotes
        
        // load intro and induction day fields
        this.cannotAttendIntro = student.cannotAttendIntro
        this.cannotAttendInduction = student.cannotAttendInduction
        this.inductionDate = student.inductionDate
        this.blueCard  = student.blueCard
        this.enrolmentInterviewDateTime = student.enrolmentInterviewDate
        this.enrolmentInterviewDate = student.enrolmentInterviewDate
        this.enrolmentInterviewTime = student.enrolmentInterviewTime
        
        
        // load interviewer fields
        this.interview = student.interview
        
        this.collegeFundPaid = student.collegeFundPaid
        this.collegeFundPaidYears = student.collegeFundPaidYears
        this.requests = student.requests
        this.refRequested = student.refRequested
        this.refReceived = student.refReceived
        this.reportRequested = student.reportRequested
        this.reportReceived = student.reportReceived
        
        
    }
    
}
