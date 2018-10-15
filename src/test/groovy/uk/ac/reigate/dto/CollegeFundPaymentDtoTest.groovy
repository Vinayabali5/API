package uk.ac.reigate.dto;

import static org.junit.Assert.*
import org.junit.Before

import java.util.List;

import org.junit.Before;
import org.junit.Test

import uk.ac.reigate.domain.academic.Student;
import uk.ac.reigate.domain.admissions.CollegeFundPayment


public class CollegeFundPaymentDtoTest {
    
    private CollegeFundPayment collegeFundPayment1
    
    private CollegeFundPayment collegeFundPayment2
    
    private List<CollegeFundPayment> collegeFundPayments
    
    Student createStudent() {
        Student student = new Student()
        student.id = 1
        return student
    }
    
    Student createStudent2() {
        Student student = new Student()
        student.id = 2
        return student
    }
    
    @Before
    public void setup() {
        collegeFundPayment1 = new CollegeFundPayment(
                id: 1,
                student: createStudent(),
                paymentDate: new Date().parse('yyyy/MM/dd', '2013/07/09'),
                amount: 2.1f,
                payee: 'vinaya',
                giftAid: true,
                cash: true,
                chequeDate: new Date().parse('yyyy/MM/dd', '2013/07/09')
                );
        collegeFundPayment2 = new CollegeFundPayment(
                id: 2,
                student: createStudent2(),
                paymentDate: new Date().parse('yyyy/MM/dd', '2014/07/09'),
                amount: 2.1f,
                payee: 'Mich',
                giftAid: true,
                cash: true,
                chequeDate: new Date().parse('yyyy/MM/dd', '2015/07/09')
                );
        collegeFundPayments = Arrays.asList(collegeFundPayment1, collegeFundPayment2);
    }
    
    @Test
    void testConstructor_collegeFundPayment() {
        CollegeFundPaymentDto collegeFundPaymentTest = new CollegeFundPaymentDto( collegeFundPayment1 )
        assertEquals( collegeFundPaymentTest.id, collegeFundPayment1.id );
        assertEquals( collegeFundPaymentTest.studentId, collegeFundPayment1.student.id);
        assertEquals( collegeFundPaymentTest.paymentDate, collegeFundPayment1.paymentDate);
        assertEquals( collegeFundPaymentTest.amount, collegeFundPayment1.amount, 2.1f);
        assertEquals( collegeFundPaymentTest.payee, collegeFundPayment1.payee);
        assertEquals( collegeFundPaymentTest.giftAid, collegeFundPayment1.giftAid);
        assertEquals( collegeFundPaymentTest.cash, collegeFundPayment1.cash);
        assertEquals( collegeFundPaymentTest.chequeDate, collegeFundPayment1.chequeDate);
    }
    
    @Test
    public void testMapFromCollegeFundPaymentEntity(){
        CollegeFundPaymentDto collegeFundPaymentTest = CollegeFundPaymentDto.mapFromCollegeFundPaymentEntity( collegeFundPayment1 )
        assertEquals( collegeFundPaymentTest.id, collegeFundPayment1.id );
        assertEquals( collegeFundPaymentTest.studentId, collegeFundPayment1.student.id);
        assertEquals( collegeFundPaymentTest.paymentDate, collegeFundPayment1.paymentDate);
        assertEquals( collegeFundPaymentTest.amount, collegeFundPayment1.amount, 2.1f);
        assertEquals( collegeFundPaymentTest.payee, collegeFundPayment1.payee);
        assertEquals( collegeFundPaymentTest.giftAid, collegeFundPayment1.giftAid);
        assertEquals( collegeFundPaymentTest.cash, collegeFundPayment1.cash);
        assertEquals( collegeFundPaymentTest.chequeDate, collegeFundPayment1.chequeDate);
    }
    
    @Test
    public void testMapFromCollegeFundPaymentsEntities(){
        List<CollegeFundPaymentDto> collegeFundPaymentsDtoTest = CollegeFundPaymentDto.mapFromCollegeFundPaymentsEntities( this.collegeFundPayments )
        assertEquals( collegeFundPaymentsDtoTest[0].id, collegeFundPayment1.id );
        assertEquals( collegeFundPaymentsDtoTest[0].studentId, collegeFundPayment1.student.id);
        assertEquals( collegeFundPaymentsDtoTest[0].paymentDate, collegeFundPayment1.paymentDate);
        assertEquals( collegeFundPaymentsDtoTest[0].amount, collegeFundPayment1.amount, 2.1f);
        assertEquals( collegeFundPaymentsDtoTest[0].payee, collegeFundPayment1.payee);
        assertEquals( collegeFundPaymentsDtoTest[0].giftAid, collegeFundPayment1.giftAid);
        assertEquals( collegeFundPaymentsDtoTest[0].cash, collegeFundPayment1.cash);
        assertEquals( collegeFundPaymentsDtoTest[0].chequeDate, collegeFundPayment1.chequeDate);
        assertEquals( collegeFundPaymentsDtoTest[1].id, collegeFundPayment2.id );
        assertEquals( collegeFundPaymentsDtoTest[1].studentId, collegeFundPayment2.student.id);
        assertEquals( collegeFundPaymentsDtoTest[1].paymentDate, collegeFundPayment2.paymentDate);
        assertEquals( collegeFundPaymentsDtoTest[1].amount, collegeFundPayment2.amount, 2.1f);
        assertEquals( collegeFundPaymentsDtoTest[1].payee, collegeFundPayment2.payee);
        assertEquals( collegeFundPaymentsDtoTest[1].giftAid, collegeFundPayment2.giftAid);
        assertEquals( collegeFundPaymentsDtoTest[1].cash, collegeFundPayment2.cash);
        assertEquals( collegeFundPaymentsDtoTest[1].chequeDate, collegeFundPayment2.chequeDate);
    }
    
    @Test
    public void testEquals_Same() {
        CollegeFundPaymentDto collegeFundPaymentDto1 = new CollegeFundPaymentDto(collegeFundPayment1.id, collegeFundPayment1.student, collegeFundPayment1.paymentDate, collegeFundPayment1.amount, collegeFundPayment1.payee, collegeFundPayment1.giftAid, collegeFundPayment1.cash, collegeFundPayment1.chequeDate)
        CollegeFundPaymentDto collegeFundPaymentDto2 = new CollegeFundPaymentDto(collegeFundPayment1.id, collegeFundPayment1.student, collegeFundPayment1.paymentDate, collegeFundPayment1.amount, collegeFundPayment1.payee, collegeFundPayment1.giftAid, collegeFundPayment1.cash, collegeFundPayment1.chequeDate)
        assertEquals(collegeFundPaymentDto1, collegeFundPaymentDto2)
    }
    
    @Test
    public void testEquals_Different() {
        CollegeFundPaymentDto collegeFundPaymentDto1 = new CollegeFundPaymentDto(collegeFundPayment1.id, collegeFundPayment1.student, collegeFundPayment1.paymentDate, collegeFundPayment1.amount, collegeFundPayment1.payee, collegeFundPayment1.giftAid, collegeFundPayment1.cash, collegeFundPayment1.chequeDate)
        CollegeFundPaymentDto collegeFundPaymentDto2 = new CollegeFundPaymentDto(collegeFundPayment2.id, collegeFundPayment2.student, collegeFundPayment2.paymentDate, collegeFundPayment2.amount, collegeFundPayment2.payee, collegeFundPayment2.giftAid, collegeFundPayment2.cash, collegeFundPayment2.chequeDate)
        assertNotEquals(collegeFundPaymentDto1, collegeFundPaymentDto2)
    }
    
    @Test
    public void testHashCode_Same() {
        CollegeFundPaymentDto collegeFundPaymentDto1 = new CollegeFundPaymentDto(collegeFundPayment1.id, collegeFundPayment1.student, collegeFundPayment1.paymentDate, collegeFundPayment1.amount, collegeFundPayment1.payee, collegeFundPayment1.giftAid, collegeFundPayment1.cash, collegeFundPayment1.chequeDate)
        CollegeFundPaymentDto collegeFundPaymentDto2 = new CollegeFundPaymentDto(collegeFundPayment1.id, collegeFundPayment1.student, collegeFundPayment1.paymentDate, collegeFundPayment1.amount, collegeFundPayment1.payee, collegeFundPayment1.giftAid, collegeFundPayment1.cash, collegeFundPayment1.chequeDate)
        assertEquals(collegeFundPaymentDto1.hashCode(), collegeFundPaymentDto2.hashCode())
    }
    
    @Test
    public void testHashCode_Different() {
        CollegeFundPaymentDto collegeFundPaymentDto1 = new CollegeFundPaymentDto(collegeFundPayment1.id, collegeFundPayment1.student, collegeFundPayment1.paymentDate, collegeFundPayment1.amount, collegeFundPayment1.payee, collegeFundPayment1.giftAid, collegeFundPayment1.cash, collegeFundPayment1.chequeDate)
        CollegeFundPaymentDto collegeFundPaymentDto2 = new CollegeFundPaymentDto(collegeFundPayment2.id, collegeFundPayment2.student, collegeFundPayment2.paymentDate, collegeFundPayment2.amount, collegeFundPayment2.payee, collegeFundPayment2.giftAid, collegeFundPayment2.cash, collegeFundPayment2.chequeDate)
        assertNotEquals(collegeFundPaymentDto1.hashCode(), collegeFundPaymentDto2.hashCode())
    }
    
    @Test
    public void testMapToCollegeFundPaymentEntity() {
        Student student = new Student()
        CollegeFundPaymentDto collegeFundPaymentDto = new CollegeFundPaymentDto(collegeFundPayment1.id, collegeFundPayment1.student, collegeFundPayment1.paymentDate, collegeFundPayment1.amount, collegeFundPayment1.payee, collegeFundPayment1.giftAid, collegeFundPayment1.cash, collegeFundPayment1.chequeDate)
        CollegeFundPayment collegeFundPayment = CollegeFundPaymentDto.mapToCollegeFundPaymentEntity( collegeFundPaymentDto, student )
        assertEquals( collegeFundPayment.id, collegeFundPayment1.id );
        assertEquals( collegeFundPayment.paymentDate, collegeFundPayment1.paymentDate);
        assertEquals( collegeFundPayment.amount, collegeFundPayment1.amount, 2.1f);
        assertEquals( collegeFundPayment.payee, collegeFundPayment1.payee);
        assertEquals( collegeFundPayment.giftAid, collegeFundPayment1.giftAid);
        assertEquals( collegeFundPayment.cash, collegeFundPayment1.cash);
        assertEquals( collegeFundPayment.chequeDate, collegeFundPayment1.chequeDate);
    }
    
    @Test
    public void testConstructor_CollegeFundPayment() {
        CollegeFundPaymentDto collegeFundPayment = new CollegeFundPaymentDto(collegeFundPayment1.id, collegeFundPayment1.student, collegeFundPayment1.paymentDate, collegeFundPayment1.amount, collegeFundPayment1.payee, collegeFundPayment1.giftAid, collegeFundPayment1.cash, collegeFundPayment1.chequeDate)
        assertEquals( collegeFundPayment.id, collegeFundPayment1.id );
        assertEquals( collegeFundPayment.studentId, collegeFundPayment1.student.id);
        assertEquals( collegeFundPayment.paymentDate, collegeFundPayment1.paymentDate);
        assertEquals( collegeFundPayment.amount, collegeFundPayment1.amount, 2.1f);
        assertEquals( collegeFundPayment.payee, collegeFundPayment1.payee);
        assertEquals( collegeFundPayment.giftAid, collegeFundPayment1.giftAid);
        assertEquals( collegeFundPayment.cash, collegeFundPayment1.cash);
        assertEquals( collegeFundPayment.chequeDate, collegeFundPayment1.chequeDate);
    }
}
