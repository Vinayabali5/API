package uk.ac.reigate.dto.integration

import javax.validation.constraints.NotNull
import javax.validation.constraints.Past
import javax.validation.constraints.Size

import com.fasterxml.jackson.annotation.JsonProperty

import org.springframework.format.annotation.DateTimeFormat

import uk.ac.reigate.dto.IAddressDTO

class ApplicationImportDTO implements IAddressDTO {
    
    @JsonProperty
    Integer id
    
    @JsonProperty
    Integer originalId
    
    @JsonProperty
    @NotNull
    @DateTimeFormat(pattern = "dd/MM/yyyy")
    Date received
    
    @JsonProperty
    @Size(min=2, max=50)
    String firstName
    
    @JsonProperty
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
    String genderCode
    
    @JsonProperty
    String title
    
    @JsonProperty
    String home
    
    @JsonProperty
    String mobile
    
    @JsonProperty
    String email
    
    @JsonProperty
    String nationality
    
    @JsonProperty
    String countryOfResidence
    
    @JsonProperty
    Boolean resident
    
    @JsonProperty
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
    String postcode
    
    @JsonProperty
    String schoolName
    
    @JsonProperty
    String schoolUkprn
    
    @JsonProperty
    List<ContactImportDTO> contacts
    
    @JsonProperty
    List<String> requestCodes
    
    @JsonProperty
    Boolean ehcp
    
    @JsonProperty
    String ethnicityCode
}
