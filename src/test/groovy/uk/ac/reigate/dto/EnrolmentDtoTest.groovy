package uk.ac.reigate.dto;

import org.junit.Before
import org.junit.Test

import uk.ac.reigate.domain.academic.AcademicYear;
import uk.ac.reigate.domain.academic.Course
import uk.ac.reigate.domain.academic.CourseGroup
import uk.ac.reigate.domain.academic.Enrolment
import uk.ac.reigate.domain.academic.Student
import uk.ac.reigate.domain.ilr.AimType
import uk.ac.reigate.domain.ilr.CompletionStatus
import uk.ac.reigate.domain.ilr.FundingModel
import uk.ac.reigate.domain.ilr.Outcome
import uk.ac.reigate.domain.ilr.SourceOfFunding
import uk.ac.reigate.domain.ilr.WithdrawalReason
import uk.ac.reigate.domain.lookup.CentralMonitoring

import static org.junit.Assert.*


public class EnrolmentDtoTest {
    
    private Enrolment enrolment1
    
    private Enrolment enrolment2
    
    private List<Enrolment> enrolments
    
    Student createStudent() {
        Student student = new Student()
        student.id = 1
        return student
    }
    
    AcademicYear createAcademicYear() {
        AcademicYear year = new AcademicYear()
        year.id = 1
        return year
    }
    
    Course createCourse() {
        Course course = new Course()
        course.id = 1
        return course
    }
    
    CourseGroup createCourseGroup() {
        CourseGroup courseGroup = new CourseGroup()
        courseGroup.id = 1
        return courseGroup
    }
    
    AimType createAimType() {
        AimType aimType = new AimType()
        aimType.id = 1
        return aimType
    }
    
    CompletionStatus createCompletionStatus() {
        CompletionStatus completionStatus = new CompletionStatus()
        completionStatus.id = 1
        return completionStatus
    }
    
    Outcome createOutcome() {
        Outcome outcome = new Outcome()
        outcome.id = 1
        return outcome
    }
    
    WithdrawalReason createWithdrawalReason() {
        WithdrawalReason withdrawalReason = new WithdrawalReason()
        withdrawalReason.id = 1
        return withdrawalReason
    }
    
    CentralMonitoring createCentralMonitoring() {
        CentralMonitoring centralMonitoring = new CentralMonitoring()
        centralMonitoring.id = 1
        return centralMonitoring
    }
    
    FundingModel createFundingModel() {
        FundingModel fundingModel = new FundingModel()
        fundingModel.id = 1
        return fundingModel
    }
    
    SourceOfFunding createSourceOfFunding() {
        SourceOfFunding sourceOfFunding = new SourceOfFunding()
        sourceOfFunding.id = 1
        return sourceOfFunding
    }
    
    @Before
    public void setup() {
        enrolment1 = new Enrolment(
                id: 1,
                student: createStudent(),
                year : createAcademicYear(),
                course: createCourse(),
                courseGroup: createCourseGroup(),
                startDate: new Date().parse('yyyy/MM/dd', '2013/07/09'),
                endDate: new Date().parse('yyyy/MM/dd', '2013/07/09'),
                qualificationStartDate: new Date().parse('yyyy/MM/dd', '2013/07/09'),
                plannedEndDate:new Date().parse('yyyy/MM/dd', '2013/07/09'),
                aimType : createAimType(),
                completionStatus : createCompletionStatus(),
                withdrawalReason : createWithdrawalReason(),
                outcome : createOutcome(),
                grade : null,
                ilr : true,
                centralMonitoring : createCentralMonitoring(),
                plh: 300,
                peeph : 60,
                fundingModel : createFundingModel(),
                sourceOfFunding : createSourceOfFunding()
                );
        enrolment2 = new Enrolment(
                id: 2,
                student: createStudent(),
                year : createAcademicYear(),
                course: createCourse(),
                courseGroup: createCourseGroup(),
                startDate: new Date().parse('yyyy/MM/dd', '2013/07/09'),
                endDate: new Date().parse('yyyy/MM/dd', '2013/07/09'),
                qualificationStartDate: new Date().parse('yyyy/MM/dd', '2013/07/09'),
                plannedEndDate:new Date().parse('yyyy/MM/dd', '2013/07/09'),
                aimType : createAimType(),
                completionStatus : createCompletionStatus(),
                withdrawalReason : createWithdrawalReason(),
                outcome : createOutcome(),
                grade : 'A*',
                ilr : true,
                centralMonitoring : createCentralMonitoring(),
                plh: 150,
                peeph : 30,
                fundingModel : createFundingModel(),
                sourceOfFunding : createSourceOfFunding()
                );
        enrolments = Arrays.asList(enrolment1, enrolment2);
    }
    
    @Test
    public void testMapFromEnrolmentEntity(){
        EnrolmentDto enrolmentTest = EnrolmentDto.mapFromEntity( enrolment1 )
        assertEquals( enrolmentTest.enrolmentId, enrolment1.id );
        assertEquals( enrolmentTest.studentId, enrolment1.student.id);
        assertEquals( enrolmentTest.yearId, enrolment1.year.id);
        assertEquals( enrolmentTest.courseId, enrolment1.course.id);
        assertEquals( enrolmentTest.courseGroupId, enrolment1.courseGroup.id);
        assertEquals( enrolmentTest.startDate, enrolment1.startDate);
        assertEquals( enrolmentTest.endDate, enrolment1.endDate);
        assertEquals( enrolmentTest.qualificationStartDate, enrolment1.qualificationStartDate);
        assertEquals( enrolmentTest.plannedEndDate, enrolment1.plannedEndDate);
        assertEquals( enrolmentTest.aimTypeId, enrolment1.aimType.id);
        assertEquals( enrolmentTest.completionStatusId, enrolment1.completionStatus.id);
        assertEquals( enrolmentTest.withdrawalReasonId, enrolment1.withdrawalReason.id);
        assertEquals( enrolmentTest.outcomeId, enrolment1.outcome.id);
        assertEquals( enrolmentTest.grade, enrolment1.grade);
        assertEquals( enrolmentTest.ilr, enrolment1.ilr);
        assertEquals( enrolmentTest.centralMonitoringId, enrolment1.centralMonitoring.id);
        assertEquals( enrolmentTest.plh, enrolment1.plh);
        assertEquals( enrolmentTest.peeph, enrolment1.peeph);
        assertEquals( enrolmentTest.fundingModelId, enrolment1.fundingModel.id);
        assertEquals( enrolmentTest.sourceOfFundingId, enrolment1.sourceOfFunding.id);
    }
    
    @Test
    public void testMapFromEntity(){
        EnrolmentDto enrolmentTest = EnrolmentDto.mapFromEntity( enrolment1 )
        assertEquals( enrolmentTest.enrolmentId, enrolment1.id );
        assertEquals( enrolmentTest.studentId, enrolment1.student.id);
        assertEquals( enrolmentTest.yearId, enrolment1.year.id);
        assertEquals( enrolmentTest.courseId, enrolment1.course.id);
        assertEquals( enrolmentTest.courseGroupId, enrolment1.courseGroup.id);
        assertEquals( enrolmentTest.startDate, enrolment1.startDate);
        assertEquals( enrolmentTest.endDate, enrolment1.endDate);
        assertEquals( enrolmentTest.qualificationStartDate, enrolment1.qualificationStartDate);
        assertEquals( enrolmentTest.plannedEndDate, enrolment1.plannedEndDate);
        assertEquals( enrolmentTest.aimTypeId, enrolment1.aimType.id);
        assertEquals( enrolmentTest.completionStatusId, enrolment1.completionStatus.id);
        assertEquals( enrolmentTest.withdrawalReasonId, enrolment1.withdrawalReason.id);
        assertEquals( enrolmentTest.outcomeId, enrolment1.outcome.id);
        assertEquals( enrolmentTest.grade, enrolment1.grade);
        assertEquals( enrolmentTest.ilr, enrolment1.ilr);
        assertEquals( enrolmentTest.centralMonitoringId, enrolment1.centralMonitoring.id);
        assertEquals( enrolmentTest.plh, enrolment1.plh);
        assertEquals( enrolmentTest.peeph, enrolment1.peeph);
        assertEquals( enrolmentTest.fundingModelId, enrolment1.fundingModel.id);
        assertEquals( enrolmentTest.sourceOfFundingId, enrolment1.sourceOfFunding.id);
    }
    
    @Test
    public void testEquals_Same() {
        EnrolmentDto enrolmentDto1 = new EnrolmentDto(enrolment1)
        EnrolmentDto enrolmentDto2 = new EnrolmentDto(enrolment1)
        assertEquals(enrolmentDto1, enrolmentDto2)
    }
    
    @Test
    public void testEquals_Different() {
        EnrolmentDto enrolmentDto1 = new EnrolmentDto(enrolment1)
        EnrolmentDto enrolmentDto2 = new EnrolmentDto(enrolment2)
        assertNotEquals(enrolmentDto1, enrolmentDto2)
    }
    
    @Test
    public void testHashCode_Different() {
        EnrolmentDto enrolmentDto1 = new EnrolmentDto(enrolment1)
        EnrolmentDto enrolmentDto2 = new EnrolmentDto(enrolment2)
        assertNotEquals(enrolmentDto1.hashCode(), enrolmentDto2.hashCode())
    }
    
    @Test
    public void testHashCode_Same() {
        EnrolmentDto enrolmentDto1 = new EnrolmentDto(enrolment1)
        EnrolmentDto enrolmentDto2 = new EnrolmentDto(enrolment1)
        assertEquals(enrolmentDto1.hashCode(), enrolmentDto2.hashCode())
    }
    
    
    @Test
    public void testConstructor_Enrolment() {
        EnrolmentDto enrolment = new EnrolmentDto(enrolment1)
        assertEquals( enrolment.enrolmentId, enrolment1.id );
        assertEquals( enrolment.studentId, enrolment1.student.id);
        assertEquals( enrolment.courseId, enrolment1.course.id);
        assertEquals( enrolment.yearId, enrolment1.year.id);
        assertEquals( enrolment.courseId, enrolment1.course.id);
        assertEquals( enrolment.courseGroupId, enrolment1.courseGroup.id);
        assertEquals( enrolment.startDate, enrolment1.startDate);
        assertEquals( enrolment.endDate, enrolment1.endDate);
        assertEquals( enrolment.qualificationStartDate, enrolment1.qualificationStartDate);
        assertEquals( enrolment.plannedEndDate, enrolment1.plannedEndDate);
        assertEquals( enrolment.aimTypeId, enrolment1.aimType.id);
        assertEquals( enrolment.completionStatusId, enrolment1.completionStatus.id);
        assertEquals( enrolment.withdrawalReasonId, enrolment1.withdrawalReason.id);
        assertEquals( enrolment.outcomeId, enrolment1.outcome.id);
        assertEquals( enrolment.grade, enrolment1.grade);
        assertEquals( enrolment.ilr, enrolment1.ilr);
        assertEquals( enrolment.centralMonitoringId, enrolment1.centralMonitoring.id);
        assertEquals( enrolment.plh, enrolment1.plh);
        assertEquals( enrolment.peeph, enrolment1.peeph);
        assertEquals( enrolment.fundingModelId, enrolment1.fundingModel.id);
        assertEquals( enrolment.sourceOfFundingId, enrolment1.sourceOfFunding.id);
    }
}
