package uk.ac.reigate.dto;

import static org.junit.Assert.*

import java.util.Date;

import org.junit.Before
import org.junit.Test
import uk.ac.reigate.domain.Staff
import uk.ac.reigate.domain.academic.SpecialCategory
import uk.ac.reigate.domain.academic.Student;
import uk.ac.reigate.domain.learning_support.StudentSpecialCategory



public class StudentSpecialCategoryDtoTest {
    
    private StudentSpecialCategory studentSpecialCategory1
    
    private StudentSpecialCategory studentSpecialCategory2
    
    private List<StudentSpecialCategory> studentSpecialCategorys
    
    Student createStudent() {
        Student student = new Student()
        student.id = 1
        return student
    }
    
    SpecialCategory createSpecialCategory() {
        SpecialCategory specialCategory = new SpecialCategory()
    }
    
    Staff createStaffRequesting() {
        Staff staffRequesting = new Staff()
    }
    
    Staff createRiskAssessmentToBeCompletedBy() {
        Staff riskAssessmentToBeCompletedBy = new Staff()
    }
    
    Staff createStaffConcerned() {
        Staff staffConcerned = new Staff()
    }
    
    Staff createRiskAssessmentRaisedBy() {
        Staff riskAssessmentRaisedBy = new Staff()
    }
    
    Staff createRiskAssessmentCarriedOutBy() {
        Staff riskAssessmentCarriedOutBy = new Staff()
    }
    
    @Before
    public void setup() {
        studentSpecialCategory1 = new StudentSpecialCategory(
                id: 1,
                student: createStudent(),
                requestDate: new Date().parse('yyyy/MM/dd', '2015/09/01'),
                specialCategory : createSpecialCategory(),
                specialConfirmed : true,
                classificationDate : new Date().parse('yyyy/MM/dd', '2015/09/01'),
                closedDate : new Date().parse('yyyy/MM/dd', '2015/09/01'),
                monitoringNotes : 'n',
                staffRequesting : createStaffRequesting(),
                riskAssessmentRequired : true,
                riskAssessmentToBeCompletedBy : createRiskAssessmentToBeCompletedBy(),
                informationConfidential : true,
                writtenEvidence : true,
                passToStaffConcerned : true,
                staffConcerned : createStaffConcerned(),
                riskToStudentOrOthers : 't',
                emergencyContactNos : '34324',
                outsideAgenciesInvolved : 'df',
                toBeInformedPotentialRisks :'test',
                riskAssessmentRaisedBy : createRiskAssessmentRaisedBy(),
                riskAssessmentRaisedDate : new Date().parse('yyyy/MM/dd', '2015/09/01'),
                riskAssessmentCarriedOutBy : createRiskAssessmentCarriedOutBy(),
                riskAssessmentCarriedOutDate : new Date().parse('yyyy/MM/dd', '2015/09/01'),
                inEventEmergency : 'T'
                );
        studentSpecialCategory2 = new StudentSpecialCategory(
                id: 2,
                student: createStudent(),
                requestDate: new Date().parse('yyyy/MM/dd', '2015/09/01'),
                specialCategory : createSpecialCategory(),
                specialConfirmed : true,
                classificationDate : new Date().parse('yyyy/MM/dd', '2015/09/01'),
                closedDate : new Date().parse('yyyy/MM/dd', '2015/09/01'),
                monitoringNotes : 'n',
                staffRequesting : createStaffRequesting(),
                riskAssessmentRequired : true,
                riskAssessmentToBeCompletedBy : createRiskAssessmentToBeCompletedBy(),
                informationConfidential : true,
                writtenEvidence : true,
                passToStaffConcerned : true,
                staffConcerned : createStaffConcerned(),
                riskToStudentOrOthers : 't',
                emergencyContactNos : '34324',
                outsideAgenciesInvolved : 'df',
                toBeInformedPotentialRisks :'test',
                riskAssessmentRaisedBy : createRiskAssessmentRaisedBy(),
                riskAssessmentRaisedDate : new Date().parse('yyyy/MM/dd', '2015/09/01'),
                riskAssessmentCarriedOutBy : createRiskAssessmentCarriedOutBy(),
                riskAssessmentCarriedOutDate : new Date().parse('yyyy/MM/dd', '2015/09/01'),
                inEventEmergency : 'T'
                );
        studentSpecialCategorys = Arrays.asList(studentSpecialCategory1, studentSpecialCategory2);
    }
    
    @Test
    public void testMapFromStudentSpecialCategoryEntity(){
        StudentSpecialCategoryDto studentSpecialCategoryTest = StudentSpecialCategoryDto.mapFromStudentSpecialCategoryEntity( studentSpecialCategory1 )
        assertEquals( studentSpecialCategoryTest.id, studentSpecialCategory1.id );
        assertEquals( studentSpecialCategoryTest.requestDate, studentSpecialCategory1.requestDate);
        assertEquals( studentSpecialCategoryTest.specialCategoryId, studentSpecialCategory1.specialCategory.id);
        assertEquals( studentSpecialCategoryTest.specialConfirmed, studentSpecialCategory1.specialConfirmed)
        assertEquals( studentSpecialCategoryTest.classificationDate, studentSpecialCategory1.classificationDate)
        assertEquals( studentSpecialCategoryTest.closedDate , studentSpecialCategory1.closedDate )
        assertEquals( studentSpecialCategoryTest.monitoringNotes, studentSpecialCategory1.monitoringNotes)
        assertEquals( studentSpecialCategoryTest.riskAssessmentRequired, studentSpecialCategory1.riskAssessmentRequired)
        assertEquals( studentSpecialCategoryTest.riskAssessmentToBeCompletedById, studentSpecialCategory1.riskAssessmentToBeCompletedBy.id)
        assertEquals( studentSpecialCategoryTest.informationConfidential, studentSpecialCategory1.informationConfidential)
        assertEquals( studentSpecialCategoryTest.writtenEvidence, studentSpecialCategory1.writtenEvidence)
        assertEquals( studentSpecialCategoryTest.passToStaffConcerned, studentSpecialCategory1.passToStaffConcerned)
        assertEquals( studentSpecialCategoryTest.staffConcernedId, studentSpecialCategory1.staffConcerned.id)
        assertEquals( studentSpecialCategoryTest.riskToStudentOrOthers, studentSpecialCategory1.riskToStudentOrOthers)
        assertEquals( studentSpecialCategoryTest.emergencyContactNos, studentSpecialCategory1.emergencyContactNos)
        assertEquals( studentSpecialCategoryTest.outsideAgenciesInvolved, studentSpecialCategory1.outsideAgenciesInvolved)
        assertEquals( studentSpecialCategoryTest.toBeInformedPotentialRisks, studentSpecialCategory1.toBeInformedPotentialRisks)
        assertEquals( studentSpecialCategoryTest.riskAssessmentRaisedById, studentSpecialCategory1.riskAssessmentRaisedBy.id)
        assertEquals( studentSpecialCategoryTest.riskAssessmentRaisedDate, studentSpecialCategory1.riskAssessmentRaisedDate)
        assertEquals( studentSpecialCategoryTest.riskAssessmentCarriedOutById, studentSpecialCategory1.riskAssessmentCarriedOutBy.id)
        assertEquals( studentSpecialCategoryTest.riskAssessmentCarriedOutDate, studentSpecialCategory1.riskAssessmentCarriedOutDate)
        assertEquals( studentSpecialCategoryTest.inEventEmergency, studentSpecialCategory1.inEventEmergency)
    }
    
    @Test
    public void testMapFromStudentSpecialCategorysEntities(){
        List<StudentSpecialCategoryDto> studentSpecialCategorysDtoTest = StudentSpecialCategoryDto.mapFromStudentSpecialCategoriesEntities( studentSpecialCategorys )
        assertEquals( studentSpecialCategorysDtoTest[0].id, studentSpecialCategory1.id );
        assertEquals( studentSpecialCategorysDtoTest[0].requestDate, studentSpecialCategory1.requestDate);
        assertEquals( studentSpecialCategorysDtoTest[0].specialCategoryId, studentSpecialCategory1.specialCategory.id);
        assertEquals( studentSpecialCategorysDtoTest[0].specialConfirmed, studentSpecialCategory1.specialConfirmed)
        assertEquals( studentSpecialCategorysDtoTest[0].classificationDate, studentSpecialCategory1.classificationDate)
        assertEquals( studentSpecialCategorysDtoTest[0].closedDate , studentSpecialCategory1.closedDate )
        assertEquals( studentSpecialCategorysDtoTest[0].monitoringNotes, studentSpecialCategory1.monitoringNotes)
        assertEquals( studentSpecialCategorysDtoTest[0].riskAssessmentRequired, studentSpecialCategory1.riskAssessmentRequired)
        assertEquals( studentSpecialCategorysDtoTest[0].riskAssessmentToBeCompletedById, studentSpecialCategory1.riskAssessmentToBeCompletedBy.id)
        assertEquals( studentSpecialCategorysDtoTest[0].informationConfidential, studentSpecialCategory1.informationConfidential)
        assertEquals( studentSpecialCategorysDtoTest[0].writtenEvidence, studentSpecialCategory1.writtenEvidence)
        assertEquals( studentSpecialCategorysDtoTest[0].passToStaffConcerned, studentSpecialCategory1.passToStaffConcerned)
        assertEquals( studentSpecialCategorysDtoTest[0].staffConcernedId, studentSpecialCategory1.staffConcerned.id)
        assertEquals( studentSpecialCategorysDtoTest[0].riskToStudentOrOthers, studentSpecialCategory1.riskToStudentOrOthers)
        assertEquals( studentSpecialCategorysDtoTest[0].emergencyContactNos, studentSpecialCategory1.emergencyContactNos)
        assertEquals( studentSpecialCategorysDtoTest[0].outsideAgenciesInvolved, studentSpecialCategory1.outsideAgenciesInvolved)
        assertEquals( studentSpecialCategorysDtoTest[0].toBeInformedPotentialRisks, studentSpecialCategory1.toBeInformedPotentialRisks)
        assertEquals( studentSpecialCategorysDtoTest[0].riskAssessmentRaisedById, studentSpecialCategory1.riskAssessmentRaisedBy.id)
        assertEquals( studentSpecialCategorysDtoTest[0].riskAssessmentRaisedDate, studentSpecialCategory1.riskAssessmentRaisedDate)
        assertEquals( studentSpecialCategorysDtoTest[0].riskAssessmentCarriedOutById, studentSpecialCategory1.riskAssessmentCarriedOutBy.id)
        assertEquals( studentSpecialCategorysDtoTest[0].riskAssessmentCarriedOutDate, studentSpecialCategory1.riskAssessmentCarriedOutDate)
        assertEquals( studentSpecialCategorysDtoTest[0].inEventEmergency, studentSpecialCategory1.inEventEmergency)
        assertEquals( studentSpecialCategorysDtoTest[1].id, studentSpecialCategory2.id );
        assertEquals( studentSpecialCategorysDtoTest[1].requestDate, studentSpecialCategory2.requestDate);
        assertEquals( studentSpecialCategorysDtoTest[1].specialCategoryId, studentSpecialCategory2.specialCategory.id);
        assertEquals( studentSpecialCategorysDtoTest[1].specialConfirmed, studentSpecialCategory2.specialConfirmed)
        assertEquals( studentSpecialCategorysDtoTest[1].classificationDate, studentSpecialCategory2.classificationDate)
        assertEquals( studentSpecialCategorysDtoTest[1].closedDate , studentSpecialCategory2.closedDate )
        assertEquals( studentSpecialCategorysDtoTest[1].monitoringNotes, studentSpecialCategory2.monitoringNotes)
        assertEquals( studentSpecialCategorysDtoTest[1].riskAssessmentRequired, studentSpecialCategory2.riskAssessmentRequired)
        assertEquals( studentSpecialCategorysDtoTest[1].riskAssessmentToBeCompletedById, studentSpecialCategory2.riskAssessmentToBeCompletedBy.id)
        assertEquals( studentSpecialCategorysDtoTest[1].informationConfidential, studentSpecialCategory2.informationConfidential)
        assertEquals( studentSpecialCategorysDtoTest[1].writtenEvidence, studentSpecialCategory2.writtenEvidence)
        assertEquals( studentSpecialCategorysDtoTest[1].passToStaffConcerned, studentSpecialCategory2.passToStaffConcerned)
        assertEquals( studentSpecialCategorysDtoTest[1].staffConcernedId, studentSpecialCategory2.staffConcerned.id)
        assertEquals( studentSpecialCategorysDtoTest[1].riskToStudentOrOthers, studentSpecialCategory2.riskToStudentOrOthers)
        assertEquals( studentSpecialCategorysDtoTest[1].emergencyContactNos, studentSpecialCategory2.emergencyContactNos)
        assertEquals( studentSpecialCategorysDtoTest[1].outsideAgenciesInvolved, studentSpecialCategory2.outsideAgenciesInvolved)
        assertEquals( studentSpecialCategorysDtoTest[1].toBeInformedPotentialRisks, studentSpecialCategory2.toBeInformedPotentialRisks)
        assertEquals( studentSpecialCategorysDtoTest[1].riskAssessmentRaisedById, studentSpecialCategory2.riskAssessmentRaisedBy.id)
        assertEquals( studentSpecialCategorysDtoTest[1].riskAssessmentRaisedDate, studentSpecialCategory2.riskAssessmentRaisedDate)
        assertEquals( studentSpecialCategorysDtoTest[1].riskAssessmentCarriedOutById, studentSpecialCategory2.riskAssessmentCarriedOutBy.id)
        assertEquals( studentSpecialCategorysDtoTest[1].riskAssessmentCarriedOutDate, studentSpecialCategory2.riskAssessmentCarriedOutDate)
    }
    
    @Test
    public void testEquals_Same() {
        StudentSpecialCategoryDto studentSpecialCategoryDto1 = new StudentSpecialCategoryDto(studentSpecialCategory1.id, studentSpecialCategory1.student, studentSpecialCategory1.requestDate, studentSpecialCategory1.specialCategory, studentSpecialCategory1.specialConfirmed, studentSpecialCategory1.classificationDate, studentSpecialCategory1.closedDate, studentSpecialCategory1.monitoringNotes, studentSpecialCategory1.staffRequesting, studentSpecialCategory1.riskAssessmentRequired, studentSpecialCategory1.riskAssessmentToBeCompletedBy, studentSpecialCategory1.informationConfidential, studentSpecialCategory1.writtenEvidence, studentSpecialCategory1.passToStaffConcerned, studentSpecialCategory1.staffConcerned, studentSpecialCategory1.riskToStudentOrOthers, studentSpecialCategory1.emergencyContactNos, studentSpecialCategory1.outsideAgenciesInvolved, studentSpecialCategory1.toBeInformedPotentialRisks, studentSpecialCategory1.riskAssessmentRaisedBy, studentSpecialCategory1.riskAssessmentRaisedDate, studentSpecialCategory1.riskAssessmentCarriedOutBy, studentSpecialCategory1.riskAssessmentCarriedOutDate, studentSpecialCategory1.inEventEmergency)
        StudentSpecialCategoryDto studentSpecialCategoryDto2 = new StudentSpecialCategoryDto(studentSpecialCategory1.id, studentSpecialCategory1.student, studentSpecialCategory1.requestDate, studentSpecialCategory1.specialCategory, studentSpecialCategory1.specialConfirmed, studentSpecialCategory1.classificationDate, studentSpecialCategory1.closedDate, studentSpecialCategory1.monitoringNotes, studentSpecialCategory1.staffRequesting, studentSpecialCategory1.riskAssessmentRequired, studentSpecialCategory1.riskAssessmentToBeCompletedBy, studentSpecialCategory1.informationConfidential, studentSpecialCategory1.writtenEvidence, studentSpecialCategory1.passToStaffConcerned, studentSpecialCategory1.staffConcerned, studentSpecialCategory1.riskToStudentOrOthers, studentSpecialCategory1.emergencyContactNos, studentSpecialCategory1.outsideAgenciesInvolved, studentSpecialCategory1.toBeInformedPotentialRisks, studentSpecialCategory1.riskAssessmentRaisedBy, studentSpecialCategory1.riskAssessmentRaisedDate, studentSpecialCategory1.riskAssessmentCarriedOutBy, studentSpecialCategory1.riskAssessmentCarriedOutDate, studentSpecialCategory1.inEventEmergency)
        assertEquals(studentSpecialCategoryDto1, studentSpecialCategoryDto2)
    }
    
    @Test
    public void testEquals_Different() {
        StudentSpecialCategoryDto studentSpecialCategoryDto1 = new StudentSpecialCategoryDto(studentSpecialCategory1.id, studentSpecialCategory1.student, studentSpecialCategory1.requestDate, studentSpecialCategory1.specialCategory, studentSpecialCategory1.specialConfirmed, studentSpecialCategory1.classificationDate, studentSpecialCategory1.closedDate, studentSpecialCategory1.monitoringNotes, studentSpecialCategory1.staffRequesting, studentSpecialCategory1.riskAssessmentRequired, studentSpecialCategory1.riskAssessmentToBeCompletedBy, studentSpecialCategory1.informationConfidential, studentSpecialCategory1.writtenEvidence, studentSpecialCategory1.passToStaffConcerned, studentSpecialCategory1.staffConcerned, studentSpecialCategory1.riskToStudentOrOthers, studentSpecialCategory1.emergencyContactNos, studentSpecialCategory1.outsideAgenciesInvolved, studentSpecialCategory1.toBeInformedPotentialRisks, studentSpecialCategory1.riskAssessmentRaisedBy, studentSpecialCategory1.riskAssessmentRaisedDate, studentSpecialCategory1.riskAssessmentCarriedOutBy, studentSpecialCategory1.riskAssessmentCarriedOutDate, studentSpecialCategory1.inEventEmergency)
        StudentSpecialCategoryDto studentSpecialCategoryDto2 = new StudentSpecialCategoryDto(studentSpecialCategory2.id, studentSpecialCategory2.student, studentSpecialCategory2.requestDate, studentSpecialCategory2.specialCategory, studentSpecialCategory2.specialConfirmed, studentSpecialCategory2.classificationDate, studentSpecialCategory2.closedDate, studentSpecialCategory2.monitoringNotes, studentSpecialCategory2.staffRequesting, studentSpecialCategory2.riskAssessmentRequired, studentSpecialCategory2.riskAssessmentToBeCompletedBy, studentSpecialCategory2.informationConfidential, studentSpecialCategory2.writtenEvidence, studentSpecialCategory2.passToStaffConcerned, studentSpecialCategory2.staffConcerned, studentSpecialCategory2.riskToStudentOrOthers, studentSpecialCategory2.emergencyContactNos, studentSpecialCategory2.outsideAgenciesInvolved, studentSpecialCategory2.toBeInformedPotentialRisks, studentSpecialCategory2.riskAssessmentRaisedBy, studentSpecialCategory2.riskAssessmentRaisedDate, studentSpecialCategory2.riskAssessmentCarriedOutBy, studentSpecialCategory2.riskAssessmentCarriedOutDate, studentSpecialCategory2.inEventEmergency)
        assertNotEquals(studentSpecialCategoryDto1, studentSpecialCategoryDto2)
    }
    
    @Test
    public void testHashCode_Same() {
        StudentSpecialCategoryDto studentSpecialCategoryDto1 = new StudentSpecialCategoryDto(studentSpecialCategory1.id, studentSpecialCategory1.student, studentSpecialCategory1.requestDate, studentSpecialCategory1.specialCategory, studentSpecialCategory1.specialConfirmed, studentSpecialCategory1.classificationDate, studentSpecialCategory1.closedDate, studentSpecialCategory1.monitoringNotes, studentSpecialCategory1.staffRequesting, studentSpecialCategory1.riskAssessmentRequired, studentSpecialCategory1.riskAssessmentToBeCompletedBy, studentSpecialCategory1.informationConfidential, studentSpecialCategory1.writtenEvidence, studentSpecialCategory1.passToStaffConcerned, studentSpecialCategory1.staffConcerned, studentSpecialCategory1.riskToStudentOrOthers, studentSpecialCategory1.emergencyContactNos, studentSpecialCategory1.outsideAgenciesInvolved, studentSpecialCategory1.toBeInformedPotentialRisks, studentSpecialCategory1.riskAssessmentRaisedBy, studentSpecialCategory1.riskAssessmentRaisedDate, studentSpecialCategory1.riskAssessmentCarriedOutBy, studentSpecialCategory1.riskAssessmentCarriedOutDate, studentSpecialCategory1.inEventEmergency)
        StudentSpecialCategoryDto studentSpecialCategoryDto2 = new StudentSpecialCategoryDto(studentSpecialCategory1.id, studentSpecialCategory1.student, studentSpecialCategory1.requestDate, studentSpecialCategory1.specialCategory, studentSpecialCategory1.specialConfirmed, studentSpecialCategory1.classificationDate, studentSpecialCategory1.closedDate, studentSpecialCategory1.monitoringNotes, studentSpecialCategory1.staffRequesting, studentSpecialCategory1.riskAssessmentRequired, studentSpecialCategory1.riskAssessmentToBeCompletedBy, studentSpecialCategory1.informationConfidential, studentSpecialCategory1.writtenEvidence, studentSpecialCategory1.passToStaffConcerned, studentSpecialCategory1.staffConcerned, studentSpecialCategory1.riskToStudentOrOthers, studentSpecialCategory1.emergencyContactNos, studentSpecialCategory1.outsideAgenciesInvolved, studentSpecialCategory1.toBeInformedPotentialRisks, studentSpecialCategory1.riskAssessmentRaisedBy, studentSpecialCategory1.riskAssessmentRaisedDate, studentSpecialCategory1.riskAssessmentCarriedOutBy, studentSpecialCategory1.riskAssessmentCarriedOutDate, studentSpecialCategory1.inEventEmergency)
        assertEquals(studentSpecialCategoryDto1.hashCode(), studentSpecialCategoryDto2.hashCode())
    }
    
    @Test
    public void testHashCode_Different() {
        StudentSpecialCategoryDto studentSpecialCategoryDto1 = new StudentSpecialCategoryDto(studentSpecialCategory1.id, studentSpecialCategory1.student, studentSpecialCategory1.requestDate, studentSpecialCategory1.specialCategory, studentSpecialCategory1.specialConfirmed, studentSpecialCategory1.classificationDate, studentSpecialCategory1.closedDate, studentSpecialCategory1.monitoringNotes, studentSpecialCategory1.staffRequesting, studentSpecialCategory1.riskAssessmentRequired, studentSpecialCategory1.riskAssessmentToBeCompletedBy, studentSpecialCategory1.informationConfidential, studentSpecialCategory1.writtenEvidence, studentSpecialCategory1.passToStaffConcerned, studentSpecialCategory1.staffConcerned, studentSpecialCategory1.riskToStudentOrOthers, studentSpecialCategory1.emergencyContactNos, studentSpecialCategory1.outsideAgenciesInvolved, studentSpecialCategory1.toBeInformedPotentialRisks, studentSpecialCategory1.riskAssessmentRaisedBy, studentSpecialCategory1.riskAssessmentRaisedDate, studentSpecialCategory1.riskAssessmentCarriedOutBy, studentSpecialCategory1.riskAssessmentCarriedOutDate, studentSpecialCategory1.inEventEmergency)
        StudentSpecialCategoryDto studentSpecialCategoryDto2 = new StudentSpecialCategoryDto(studentSpecialCategory2.id, studentSpecialCategory2.student, studentSpecialCategory2.requestDate, studentSpecialCategory2.specialCategory, studentSpecialCategory2.specialConfirmed, studentSpecialCategory2.classificationDate, studentSpecialCategory2.closedDate, studentSpecialCategory2.monitoringNotes, studentSpecialCategory2.staffRequesting, studentSpecialCategory2.riskAssessmentRequired, studentSpecialCategory2.riskAssessmentToBeCompletedBy, studentSpecialCategory2.informationConfidential, studentSpecialCategory2.writtenEvidence, studentSpecialCategory2.passToStaffConcerned, studentSpecialCategory2.staffConcerned, studentSpecialCategory2.riskToStudentOrOthers, studentSpecialCategory2.emergencyContactNos, studentSpecialCategory2.outsideAgenciesInvolved, studentSpecialCategory2.toBeInformedPotentialRisks, studentSpecialCategory2.riskAssessmentRaisedBy, studentSpecialCategory2.riskAssessmentRaisedDate, studentSpecialCategory2.riskAssessmentCarriedOutBy, studentSpecialCategory2.riskAssessmentCarriedOutDate, studentSpecialCategory2.inEventEmergency)
        assertNotEquals(studentSpecialCategoryDto1.hashCode(), studentSpecialCategoryDto2.hashCode())
    }
    
    @Test
    public void testConstructor_StudentSpecialCategory() {
        StudentSpecialCategoryDto studentSpecialCategoryTest= new StudentSpecialCategoryDto(studentSpecialCategory1)
        assertEquals( studentSpecialCategoryTest.requestDate, studentSpecialCategory1.requestDate);
        assertEquals( studentSpecialCategoryTest.specialCategoryId, studentSpecialCategory1.specialCategory.id);
        assertEquals( studentSpecialCategoryTest.specialConfirmed, studentSpecialCategory1.specialConfirmed)
        assertEquals( studentSpecialCategoryTest.classificationDate, studentSpecialCategory1.classificationDate)
        assertEquals( studentSpecialCategoryTest.closedDate , studentSpecialCategory1.closedDate )
        assertEquals( studentSpecialCategoryTest.monitoringNotes, studentSpecialCategory1.monitoringNotes)
        assertEquals( studentSpecialCategoryTest.riskAssessmentRequired, studentSpecialCategory1.riskAssessmentRequired)
        assertEquals( studentSpecialCategoryTest.riskAssessmentToBeCompletedById, studentSpecialCategory1.riskAssessmentToBeCompletedBy.id)
        assertEquals( studentSpecialCategoryTest.informationConfidential, studentSpecialCategory1.informationConfidential)
        assertEquals( studentSpecialCategoryTest.writtenEvidence, studentSpecialCategory1.writtenEvidence)
        assertEquals( studentSpecialCategoryTest.passToStaffConcerned, studentSpecialCategory1.passToStaffConcerned)
        assertEquals( studentSpecialCategoryTest.staffConcernedId, studentSpecialCategory1.staffConcerned.id)
        assertEquals( studentSpecialCategoryTest.riskToStudentOrOthers, studentSpecialCategory1.riskToStudentOrOthers)
        assertEquals( studentSpecialCategoryTest.emergencyContactNos, studentSpecialCategory1.emergencyContactNos)
        assertEquals( studentSpecialCategoryTest.outsideAgenciesInvolved, studentSpecialCategory1.outsideAgenciesInvolved)
        assertEquals( studentSpecialCategoryTest.toBeInformedPotentialRisks, studentSpecialCategory1.toBeInformedPotentialRisks)
        assertEquals( studentSpecialCategoryTest.riskAssessmentRaisedById, studentSpecialCategory1.riskAssessmentRaisedBy.id)
        assertEquals( studentSpecialCategoryTest.riskAssessmentRaisedDate, studentSpecialCategory1.riskAssessmentRaisedDate)
        assertEquals( studentSpecialCategoryTest.riskAssessmentCarriedOutById, studentSpecialCategory1.riskAssessmentCarriedOutBy.id)
        assertEquals( studentSpecialCategoryTest.riskAssessmentCarriedOutDate, studentSpecialCategory1.riskAssessmentCarriedOutDate)
        assertEquals( studentSpecialCategoryTest.inEventEmergency, studentSpecialCategory1.inEventEmergency)
    }
}
