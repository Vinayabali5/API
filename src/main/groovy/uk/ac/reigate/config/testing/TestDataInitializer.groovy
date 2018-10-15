package uk.ac.reigate.config.testing;


import javax.persistence.EntityManagerFactory

import org.hibernate.Session
import org.hibernate.SessionFactory
import org.hibernate.Transaction
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.context.annotation.Profile
import org.springframework.stereotype.Component

import uk.ac.reigate.domain.Address
import uk.ac.reigate.domain.Contact
import uk.ac.reigate.domain.Note
import uk.ac.reigate.domain.NoteType
import uk.ac.reigate.domain.Person
import uk.ac.reigate.domain.Room
import uk.ac.reigate.domain.Staff
import uk.ac.reigate.domain.academic.AcademicYear
import uk.ac.reigate.domain.academic.Block
import uk.ac.reigate.domain.academic.Course
import uk.ac.reigate.domain.academic.CourseGroup
import uk.ac.reigate.domain.academic.Department
import uk.ac.reigate.domain.academic.Faculty
import uk.ac.reigate.domain.academic.Holiday
import uk.ac.reigate.domain.academic.Period
import uk.ac.reigate.domain.academic.School
import uk.ac.reigate.domain.academic.SpecialCategory
import uk.ac.reigate.domain.admissions.ApplicationStatus
import uk.ac.reigate.domain.admissions.CollegeFundPaid
import uk.ac.reigate.domain.admissions.OfferType
import uk.ac.reigate.domain.exam.ExamBoard
import uk.ac.reigate.domain.ilr.AimType
import uk.ac.reigate.domain.ilr.CompletionStatus
import uk.ac.reigate.domain.ilr.FundingModel
import uk.ac.reigate.domain.ilr.LLDDHealthProblem
import uk.ac.reigate.domain.ilr.LLDDHealthProblemCategory
import uk.ac.reigate.domain.ilr.Outcome
import uk.ac.reigate.domain.ilr.ProgrammeType
import uk.ac.reigate.domain.ilr.RestrictedUseIndicator
import uk.ac.reigate.domain.ilr.WithdrawalReason
import uk.ac.reigate.domain.lookup.ContactType
import uk.ac.reigate.domain.lookup.Ethnicity
import uk.ac.reigate.domain.lookup.Gender
import uk.ac.reigate.domain.lookup.Level
import uk.ac.reigate.domain.lookup.Nationality
import uk.ac.reigate.domain.lookup.PossibleGrade
import uk.ac.reigate.domain.lookup.SchoolPriority
import uk.ac.reigate.domain.lookup.SchoolType
import uk.ac.reigate.domain.lookup.StaffType
import uk.ac.reigate.domain.lookup.StudentType
import uk.ac.reigate.domain.lookup.Subject
import uk.ac.reigate.domain.lookup.Title
import uk.ac.reigate.domain.lookup.YearGroup
import uk.ac.reigate.domain.system.Setting
/**
 *
 * This is a initializing bean that inserts some test data in the database. It is only active in
 * the development profile, to see the data login with user123 / PAssword2 and do a search starting on
 * 1st of January 2015.
 *
 */
@Component
@Profile(value="test")
public class TestDataInitializer {
    
    @Autowired
    private EntityManagerFactory entityManagerFactory;
    
    public void init() throws Exception {
        SessionFactory sessionFactory = entityManagerFactory.unwrap(SessionFactory.class);
        
        Session session = sessionFactory.openSession();
        Transaction transaction = session.beginTransaction();
        
        Subject maths = new Subject('MA', 'Mathematics')
        session.persist( maths );
        Subject biology = new Subject('BI', 'Biology')
        session.persist( biology );
        Subject physics = new Subject('PH', 'Physics')
        session.persist( physics );
        Subject chemistry = new Subject('CH', 'Chemistry')
        session.persist( chemistry );
        
        ContactType father = new ContactType('Father', 'Father')
        session.persist( father );
        ContactType mother = new ContactType('Mother', 'Mother')
        session.persist( mother );
        
        Level level1 = new Level('H', 'AS Level')
        session.persist( level1 );
        Level level2 = new Level('A', 'A Level')
        session.persist( level2 );
        Level level3 = new Level('9', 'BTEC 90 Credit Dip')
        session.persist( level3 );
        Level level4 = new Level('F', 'F skills')
        session.persist( level4 );
        
        PossibleGrade possibleGrade1 = new PossibleGrade('A', 'A Level', level1, 'A', 11)
        session.persist( possibleGrade1 );
        PossibleGrade possibleGrade2 = new PossibleGrade('M', 'Maths', level1, 'B', 12)
        session.persist( possibleGrade2 );
        
        Gender male = new Gender('M', 'Male')
        session.persist( male );
        Gender female = new Gender('F', 'Female')
        session.persist( female );
        
        SchoolType schoolType = new SchoolType('CRY', 'Croydon')
        session.persist(schoolType);
        
        SchoolPriority schoolPriority = new SchoolPriority('A', 'As')
        session.persist( schoolPriority );
        
        Title mr = new Title('1', 'Mr')
        session.persist( mr );
        Title mrs = new Title('2', 'Mrs')
        session.persist( mrs );
        
        YearGroup yearGroup1 = new YearGroup('15', '2015')
        session.persist( yearGroup1 );
        YearGroup yearGroup2 = new YearGroup('16', '2016')
        session.persist( yearGroup2 );
        
        Room room1 = new Room('1', 'Room1')
        session.persist( room1 );
        Room room2 = new Room('2', 'Room2')
        session.persist( room2 );
        
        Nationality uk = new Nationality('UK', 'United Kingdom')
        session.persist( uk );
        Nationality ind = new Nationality('IN', 'India')
        session.persist( ind );
        
        Ethnicity ethnicity1 = new Ethnicity('31', 'United Kingdom', 'United Kingdom', null, null)
        session.persist( ethnicity1 );
        Ethnicity ethnicity2 = new Ethnicity('32', 'Indian', 'Indian', null, null)
        session.persist( ethnicity2 );
        
        StaffType staffType1 = new StaffType('L', 'Lower')
        session.persist( staffType1 );
        StaffType staffType2 = new StaffType('U', 'Upper')
        session.persist( staffType2 );
        
        StudentType lower = new StudentType('L', 'Lower')
        session.persist( lower );
        StudentType upper = new StudentType('U', 'Upper')
        session.persist( upper );
        
        Block block1 = new Block('A', 'BlockA')
        session.persist( block1 );
        Block block2 = new Block('B', 'BlockB')
        session.persist( block2 );
        
        NoteType noteType1 = new NoteType('GEN', 'General Notes')
        session.persist( noteType1 );
        NoteType noteType2 = new NoteType('PER', 'Permanent Notes')
        session.persist( noteType2 );
        
        ApplicationStatus applicationStatus1 = new ApplicationStatus('N', 'New')
        session.persist( applicationStatus1 );
        ApplicationStatus applicationStatus2 = new ApplicationStatus('C', 'Complete')
        session.persist( applicationStatus2 );
        ApplicationStatus applicationStatus3 = new ApplicationStatus('X', 'Rejected')
        session.persist( applicationStatus3 );
        
        OfferType normal = new OfferType('N', 'Normal')
        session.persist( normal );
        OfferType provisional = new OfferType('P', 'Provisional')
        session.persist( provisional );
        
        session.persist(new SpecialCategory('S1', 'Long Term Medical'));
        session.persist(new SpecialCategory('S2', 'Long Term serious'));
        
        Address address1 = new Address('Flat D', 'Stag', 'Stanley', 'west', 'park', 'Wallington', '', 'E161FF', '', '', '', '')
        session.persist( address1 );
        
        ExamBoard examBoard1 = new ExamBoard('EDEXCEL', 'EXCEL Science', 'B', '10', '11')
        session.persist( examBoard1 );
        
        session.persist(new School('Addington High School', schoolType, schoolPriority, '', '', '', '', ''));
        
        Person person1 = new Person(mrs, 'Vinaya', 'Vin', 'Bali', 'Mick', 'Uday', new Date().parse('yyyy/MM/dd', '1991/11/11'), female, address1, '07809817665', '0890788889', '08937737737', 'mbvinayabali@gmail.com')
        session.persist( person1 )
        
        Person contact1 = new Person(mrs, 'Vinaya', 'Vin', 'Bali', 'Mick', 'Uday', new Date().parse('yyyy/MM/dd', '1991/11/11'), female, address1, '07809817665', '0890788889', '08937737737', 'mbvinayabali@gmail.com')
        session.persist( contact1 )
        
        Staff staff = new Staff(person1, staffType1, true, 'VBM', 'vin', 'vbm', new Date().parse('yyyy/MM/dd', '2011/11/11'), new Date().parse('yyyy/MM/dd', '2016/11/11'));
        session.persist( staff )
        
        Contact contact = new Contact(person1, contact1, father, true, true)
        session.persist( contact )
        
        Note note = new Note(person1, 'Permanent Notes', noteType1)
        session.persist( note );
        
        session.persist(new CollegeFundPaid('yes'));
        
        AcademicYear academicYear1 = new AcademicYear('11', '2011', new Date().parse('yyyy/MM/dd', '2011/11/11'), new Date().parse('yyyy/MM/dd', '2016/11/11'))
        session.persist( academicYear1 );
        
        Faculty faculty = new Faculty('m', 'maths', staff, staff, staff, staff, staff, new Date().parse('yyyy/MM/dd', '2011/11/11'), new Date().parse('yyyy/MM/dd', '2013/11/11'))
        session.persist( faculty )
        
        Period period1 = new Period('1', 'period1', block1, null, null, 10, 11)
        session.persist( period1 )
        Period period2 = new Period('2', 'period2', block2, null, null, 10, 11)
        session.persist( period2 )
        
        Department department1 = new Department('maths', 'maths Departmenmt', faculty, staff)
        session.persist( department1 )
        
        Course course1 = new Course(level1, maths, 11, 'learn', examBoard1, '1', academicYear1, academicYear1, 'E161FF', 'London', 'working')
        session.persist( course1 );
        
        CourseGroup courseGroup1 = new CourseGroup(yearGroup1, course1, academicYear1, '1', department1, staff, true, true, 'working')
        session.persist( courseGroup1 );
        
        Holiday holiday = new Holiday(academicYear1, '15/2015', new Date().parse('yyyy/MM/dd', '2011/11/11'), new Date().parse('yyyy/MM/dd', '2016/11/11'))
        session.persist( holiday );
        
        CompletionStatus completionStatus = new CompletionStatus('N', 'New', 'New', new Date().parse('yyyy/MM/dd', '2011/11/11'), new Date().parse('yyyy/MM/dd', '2016/11/11') )
        session.persist( completionStatus );
        
        AimType aimType = new AimType('N', 'New', 'New', new Date().parse('yyyy/MM/dd', '2011/11/11'), new Date().parse('yyyy/MM/dd', '2016/11/11') )
        session.persist( aimType );
        
        FundingModel fundingModel = new FundingModel('N', 'New', 'New', new Date().parse('yyyy/MM/dd', '2011/11/11'), new Date().parse('yyyy/MM/dd', '2016/11/11') )
        session.persist( fundingModel );
        
        LLDDHealthProblem lLDDHealthProblem = new LLDDHealthProblem('N', 'New', 'New', new Date().parse('yyyy/MM/dd', '2011/11/11'), new Date().parse('yyyy/MM/dd', '2016/11/11') )
        session.persist( lLDDHealthProblem );
        
        LLDDHealthProblemCategory lLDDHealthProblemCategory = new LLDDHealthProblemCategory('N', 'New', 'New', new Date().parse('yyyy/MM/dd', '2011/11/11'), new Date().parse('yyyy/MM/dd', '2016/11/11') )
        session.persist( lLDDHealthProblemCategory );
        
        Outcome outcome = new Outcome('N', 'New', 'New', new Date().parse('yyyy/MM/dd', '2011/11/11'), new Date().parse('yyyy/MM/dd', '2016/11/11') )
        session.persist( outcome );
        
        
        ProgrammeType programmeType = new ProgrammeType('N', 'New', 'New', new Date().parse('yyyy/MM/dd', '2011/11/11'), new Date().parse('yyyy/MM/dd', '2016/11/11') )
        session.persist( programmeType );
        
        RestrictedUseIndicator restrictedUseIndicator = new RestrictedUseIndicator('N', 'New', 'New', new Date().parse('yyyy/MM/dd', '2011/11/11'), new Date().parse('yyyy/MM/dd', '2016/11/11') )
        session.persist( restrictedUseIndicator );
        
        WithdrawalReason withdrawalReason = new WithdrawalReason('N', 'New', 'New', new Date().parse('yyyy/MM/dd', '2011/11/11'), new Date().parse('yyyy/MM/dd', '2016/11/11') )
        session.persist( withdrawalReason );
        
        Setting setting = new Setting('S', 'setting')
        session.persist( setting );
        //	 session.persist(new Request(null,'L-MAH',academicYear1,null,null));
        
        transaction.commit();
    }
}
