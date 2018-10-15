package uk.ac.reigate.config.testing

import org.mockito.Mockito
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.context.annotation.Profile

import uk.ac.reigate.services.AcademicYearService
import uk.ac.reigate.services.AddressService
import uk.ac.reigate.services.AimTypeService
import uk.ac.reigate.services.ApplicationStatusService
import uk.ac.reigate.services.BlockService
import uk.ac.reigate.services.CollegeFundPaidService
import uk.ac.reigate.services.CollegeFundPaymentService
import uk.ac.reigate.services.CompletionStatusService
import uk.ac.reigate.services.ContactService
import uk.ac.reigate.services.ContactTypeService
import uk.ac.reigate.services.CourseGroupService
import uk.ac.reigate.services.CourseService
import uk.ac.reigate.services.DepartmentService
import uk.ac.reigate.services.EthnicityService
import uk.ac.reigate.services.FacultyService
import uk.ac.reigate.services.FundingModelService
import uk.ac.reigate.services.GenderService
import uk.ac.reigate.services.HolidayService
import uk.ac.reigate.services.LLDDHealthProblemCategoryService
import uk.ac.reigate.services.LLDDHealthProblemService
import uk.ac.reigate.services.LevelService
import uk.ac.reigate.services.NationalityService
import uk.ac.reigate.services.NoteService
import uk.ac.reigate.services.NoteTypeService
import uk.ac.reigate.services.OfferTypeService
import uk.ac.reigate.services.OutcomeService
import uk.ac.reigate.services.PeriodService
import uk.ac.reigate.services.PersonService
import uk.ac.reigate.services.PossibleGradeService
import uk.ac.reigate.services.PriorAttainmentService
import uk.ac.reigate.services.ProgrammeTypeService
import uk.ac.reigate.services.RegisterService
import uk.ac.reigate.services.RegisteredSessionService
import uk.ac.reigate.services.RequestService
import uk.ac.reigate.services.RestrictedUseIndicatorService
import uk.ac.reigate.services.RoomService
import uk.ac.reigate.services.SchoolPriorityService
import uk.ac.reigate.services.SchoolService
import uk.ac.reigate.services.SchoolTypeService
import uk.ac.reigate.services.SettingService
import uk.ac.reigate.services.SpecialCategoryService
import uk.ac.reigate.services.StaffService
import uk.ac.reigate.services.StaffTypeService
import uk.ac.reigate.services.StudentTypeService
import uk.ac.reigate.services.SubjectService
import uk.ac.reigate.services.TimetableService
import uk.ac.reigate.services.TitleService
import uk.ac.reigate.services.TutorGroupService
import uk.ac.reigate.services.UserService
import uk.ac.reigate.services.WithdrawalReasonService
import uk.ac.reigate.services.YearGroupService
import uk.ac.reigate.services.exam.ExamBoardService


@Configuration
@Profile("test")
class TestMockServiceConfig {
    
    @Bean
    AcademicYearService academicYearService() {
        return Mockito.mock(AcademicYearService.class)
    }
    
    @Bean
    AddressService addressService() {
        return Mockito.mock(AddressService.class)
    }
    
    @Bean
    AimTypeService aimTypeService() {
        return Mockito.mock(AimTypeService.class)
    }
    
    @Bean
    ApplicationStatusService applicationStatusService() {
        return Mockito.mock(ApplicationStatusService.class)
    }
    
    @Bean
    BlockService blockService() {
        return Mockito.mock(BlockService.class)
    }
    
    @Bean
    CollegeFundPaidService collegeFundPaidService() {
        return Mockito.mock(CollegeFundPaidService.class)
    }
    
    @Bean
    CollegeFundPaymentService collegeFundPaymentService() {
        return Mockito.mock(CollegeFundPaymentService.class)
    }
    
    @Bean
    CompletionStatusService completionStatusService() {
        return Mockito.mock(CompletionStatusService.class)
    }
    
    @Bean
    ContactService contactService() {
        return Mockito.mock(ContactService.class)
    }
    
    @Bean
    ContactTypeService contactTypeService() {
        return Mockito.mock(ContactTypeService.class)
    }
    
    @Bean
    CourseGroupService courseGroupService() {
        return Mockito.mock(CourseGroupService.class)
    }
    
    @Bean
    CourseService courseService() {
        return Mockito.mock(CourseService.class)
    }
    
    @Bean
    DepartmentService departmentService() {
        return Mockito.mock(DepartmentService.class)
    }
    
    @Bean
    EthnicityService ethnicityService() {
        return Mockito.mock(EthnicityService.class)
    }
    
    @Bean
    ExamBoardService examBoardService() {
        return Mockito.mock(ExamBoardService.class)
    }
    
    @Bean
    FacultyService facultyService() {
        return Mockito.mock(FacultyService.class)
    }
    
    @Bean
    FundingModelService fundingModelService() {
        return Mockito.mock(FundingModelService.class)
    }
    
    @Bean
    GenderService genderService() {
        return Mockito.mock(GenderService.class)
    }
    
    @Bean
    HolidayService holidayService() {
        return Mockito.mock(HolidayService.class)
    }
    
    @Bean
    LevelService levelService() {
        return Mockito.mock(LevelService.class)
    }
    
    @Bean
    LLDDHealthProblemCategoryService lLDDHealthProblemCategoryService() {
        return Mockito.mock(LLDDHealthProblemCategoryService.class)
    }
    
    @Bean
    LLDDHealthProblemService lLDDHealthProblemService() {
        return Mockito.mock(LLDDHealthProblemService.class)
    }
    
    @Bean
    NationalityService nationalityService() {
        return Mockito.mock(NationalityService.class)
    }
    
    @Bean
    NoteService noteService() {
        return Mockito.mock(NoteService.class)
    }
    
    @Bean
    NoteTypeService noteTypeService() {
        return Mockito.mock(NoteTypeService.class)
    }
    
    @Bean
    OfferTypeService offerTypeService() {
        return Mockito.mock(OfferTypeService.class)
    }
    
    @Bean
    OutcomeService outcomeService() {
        return Mockito.mock(OutcomeService.class)
    }
    
    @Bean
    PeriodService periodService() {
        return Mockito.mock(PeriodService.class)
    }
    
    @Bean
    PersonService personService() {
        return Mockito.mock(PersonService.class)
    }
    
    @Bean
    PossibleGradeService possibleGradeService() {
        return Mockito.mock(PossibleGradeService.class)
    }
    
    @Bean
    PriorAttainmentService priorAttainmentService() {
        return Mockito.mock(PriorAttainmentService.class)
    }
    
    @Bean
    ProgrammeTypeService programmeTypeService() {
        return Mockito.mock(ProgrammeTypeService.class)
    }
    
    @Bean
    RegisterService registerService() {
        return Mockito.mock(RegisterService.class)
    }
    
    @Bean
    RequestService requestService() {
        return Mockito.mock(RequestService.class)
    }
    
    
    @Bean
    RestrictedUseIndicatorService restrictedUseIndicatorService() {
        return Mockito.mock(RestrictedUseIndicatorService.class)
    }
    
    @Bean
    RoomService roomService() {
        return Mockito.mock(RoomService.class)
    }
    
    @Bean
    SchoolPriorityService schoolPriorityService() {
        return Mockito.mock(SchoolPriorityService.class)
    }
    
    @Bean
    SchoolService schoolService() {
        return Mockito.mock(SchoolService.class)
    }
    
    @Bean
    SchoolTypeService schoolTypeService() {
        return Mockito.mock(SchoolTypeService.class)
    }
    
    @Bean
    RegisteredSessionService registeredSessionService() {
        return Mockito.mock(RegisteredSessionService.class)
    }
    
    @Bean
    SettingService settingService() {
        return Mockito.mock(SettingService.class)
    }
    
    @Bean
    SpecialCategoryService specialCategoryService() {
        return Mockito.mock(SpecialCategoryService.class)
    }
    
    @Bean
    StaffService staffService() {
        return Mockito.mock(StaffService.class)
    }
    
    @Bean
    StaffTypeService staffTypeService() {
        return Mockito.mock(StaffTypeService.class)
    }
    
    @Bean
    StudentTypeService studentTypeService() {
        return Mockito.mock(StudentTypeService.class)
    }
    
    @Bean
    SubjectService subjectService() {
        return Mockito.mock(SubjectService.class)
    }
    
    @Bean
    TimetableService timetableService() {
        return Mockito.mock(TimetableService.class)
    }
    
    @Bean
    TitleService titleService() {
        return Mockito.mock(TitleService.class)
    }
    
    @Bean
    TutorGroupService tutorGroupService() {
        return Mockito.mock(TutorGroupService.class)
    }
    
    @Bean
    UserService userService() {
        return Mockito.mock(UserService.class)
    }
    
    @Bean
    WithdrawalReasonService withdrawalReasonService() {
        return Mockito.mock(WithdrawalReasonService.class)
    }
    
    @Bean
    YearGroupService yearGroupService() {
        return Mockito.mock(YearGroupService.class)
    }
}
