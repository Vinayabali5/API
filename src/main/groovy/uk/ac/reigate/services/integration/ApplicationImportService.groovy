package uk.ac.reigate.services.integration

import java.util.logging.Logger

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service

import uk.ac.reigate.domain.Address
import uk.ac.reigate.domain.Contact
import uk.ac.reigate.domain.Person
import uk.ac.reigate.domain.academic.Student
import uk.ac.reigate.domain.admissions.Request
import uk.ac.reigate.dto.IAddressDTO
import uk.ac.reigate.dto.integration.ApplicationImportDTO
import uk.ac.reigate.dto.integration.ContactImportDTO
import uk.ac.reigate.services.AcademicYearService
import uk.ac.reigate.services.ContactTypeService
import uk.ac.reigate.services.EthnicityService
import uk.ac.reigate.services.GenderService
import uk.ac.reigate.services.SchoolService
import uk.ac.reigate.services.SettingService
import uk.ac.reigate.services.StudentService
import uk.ac.reigate.services.TitleService
import uk.ac.reigate.services.admissions.LookupAdmissionsService

@Service
class ApplicationImportService {
    
    private final static Logger LOGGER = Logger.getLogger("Application Import Service");
    
    private final String DEFAULT_COUNTRY_OF_RESIDENCE = 'UK'
    
    @Autowired
    SettingService settingService
    
    @Autowired
    LookupAdmissionsService lookupService
    
    @Autowired
    AcademicYearService academicYearService
    
    @Autowired
    ContactTypeService contactTypeService
    
    @Autowired
    TitleService titleService
    
    @Autowired
    GenderService genderService
    
    @Autowired
    SchoolService schoolService
    
    @Autowired
    EthnicityService ethnicityService
    
    @Autowired
    StudentService studentService
    
    public Student processImport(ApplicationImportDTO app) {
        Student student = createStudent(app)
        studentService.save(student)
        LOGGER.info("Create new student with ID: $student.id")
        return student
    }
    
    private createStudent(ApplicationImportDTO app) {
        String defaultOfferType = settingService.getSetting('DefaultOfferType').value
        
        Student student = new Student()
        student.status = lookupService.findApplicationStatusByDescription("New")
        student.offerType = lookupService.findOfferTypeByDescription(defaultOfferType)
        student.academicYear = academicYearService.getNextAcademicYear()
        student.received = new Date()
        student.submitted = new Date()
        
        student.person = createPerson(app)
        
        student.resident = app.resident
        student.countryOfResidence = app.countryOfResidence != null ? app.countryOfResidence : DEFAULT_COUNTRY_OF_RESIDENCE
        
        student.school = schoolService.findByName(app.schoolName)
        
        student.ehcp = app.ehcp
        
        student.ethnicity = app.ethnicityCode != null ? ethnicityService.findByCode(app.ethnicityCode) : null
        
        /*
         student.requests = new ArrayList<Request>()
         app.requestCodes.each {
         student.requests.add(new Request(student, it))
         }
         */
        student.requests =  app.requestCodes.collect {
            Request request = new Request()
            request.academicYear = academicYearService.getNextAcademicYear()
            request.student = student
            request.request = it
            return request
        }
        
        
        return student
    }
    
    private Person createPerson(ApplicationImportDTO app) {
        Person person = new Person()
        person.firstName = app.firstName
        person.surname = app.surname
        person.legalSurname = app.surname
        person.middleNames = app.middleNames
        person.dob = app.dob
        person.gender = app.genderCode != null ? genderService.findByCode(app.genderCode) : null
        person.contacts = app.contacts.collect { createContact(it, person) }
        person.home = app.home
        person.mobile = app.mobile
        person.email = app.email
        person.address = createAddress(app)
        return person
    }
    
    private Address createAddress(IAddressDTO source) {
        Address address = new Address()
        address.line1 = source.line1
        address.line2 = source.line2
        address.line3 = source.line3
        address.line4 = source.line4
        address.line5 = source.line5
        address.postcode = source.postcode
        address.town = source.town
        address.county = source.county
        return address
    }
    
    private Contact createContact(ContactImportDTO source, Person person) {
        Contact contact = new Contact()
        contact.person = person
        contact.contactType = source.contactType != null ? contactTypeService.findByDescription(source.contactType) : null
        contact.contact = createContactPerson(source)
        contact.contactable = source.contactable
        contact.preferred = source.preferred
        return contact
    }
    
    private createContactPerson(ContactImportDTO source) {
        Person person = new Person()
        person.title = source.title != null ? titleService.findByDescription(source.title) : null
        person.firstName = source.firstName
        person.surname = source.surname
        person.home = source.home
        person.mobile = source.mobile
        person.email = source.email
        if (source._alternativeAddress && source.address != null) {
            person.address = createAddress(source.address)
        }
        return person
    }
}
