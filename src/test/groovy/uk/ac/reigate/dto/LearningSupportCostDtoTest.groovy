package uk.ac.reigate.dto;

import static org.junit.Assert.*
import org.junit.Before
import org.junit.Test
import uk.ac.reigate.domain.Staff
import uk.ac.reigate.domain.academic.Student;
import uk.ac.reigate.domain.learning_support.LearningSupportCost
import uk.ac.reigate.domain.learning_support.LearningSupportCostCategory;



public class LearningSupportCostDtoTest {
    
    private LearningSupportCost learningSupportCost1
    
    private LearningSupportCost learningSupportCost2
    
    private List<LearningSupportCost> learningSupportCosts
    
    Student createStudent() {
        Student student = new Student()
        student.id = 1
        return student
    }
    
    Staff createStaff() {
        Staff staff = new Staff()
    }
    
    LearningSupportCostCategory createLearningSupportCostCategory() {
        LearningSupportCostCategory costCategory = new LearningSupportCostCategory()
    }
    
    @Before
    public void setup() {
        learningSupportCost1 = new LearningSupportCost(
                id: 1,
                student: createStudent(),
                startDate : new Date().parse('yyyy/MM/dd', '2015/09/01'),
                endDate : new Date().parse('yyyy/MM/dd', '2015/09/01'),
                hoursPerWeek : 2.1f,
                weeks : 1.1f,
                rate : 1.2f,
                costCategory : createLearningSupportCostCategory(),
                description : 'm',
                periodDescription : 'p1',
                staff: createStaff()
                );
        learningSupportCost2 = new LearningSupportCost(
                id: 2,
                student: createStudent(),
                startDate : new Date().parse('yyyy/MM/dd', '2015/09/01'),
                endDate : new Date().parse('yyyy/MM/dd', '2015/09/01'),
                hoursPerWeek : 2.1f,
                weeks : 1.1f,
                rate : 1.2f,
                costCategory : createLearningSupportCostCategory(),
                description : 'm',
                periodDescription : 'p1',
                staff: createStaff()
                );
        learningSupportCosts = Arrays.asList(learningSupportCost1, learningSupportCost2);
    }
    
    @Test
    public void testMapFromLearningSupportCostEntity(){
        LearningSupportCostDto learningSupportCostTest = LearningSupportCostDto.mapFromLearningSupportCostEntity( learningSupportCost1 )
        assertEquals( learningSupportCostTest.id, learningSupportCost1.id );
        assertEquals( learningSupportCostTest.periodDescription, learningSupportCost1.periodDescription);
        assertEquals( learningSupportCostTest.description, learningSupportCost1.description);
        assertEquals( learningSupportCostTest.startDate, learningSupportCost1.startDate)
        assertEquals( learningSupportCostTest.endDate, learningSupportCost1.endDate)
        assertEquals( learningSupportCostTest.staffId, learningSupportCost1.staff.id)
    }
    
    @Test
    public void testMapFromLearningSupportCostsEntities(){
        List<LearningSupportCostDto> learningSupportCostsDtoTest = LearningSupportCostDto.mapFromLearningSupportCostsEntities( learningSupportCosts )
        assertEquals( learningSupportCostsDtoTest[0].id, learningSupportCost1.id );
        assertEquals( learningSupportCostsDtoTest[0].periodDescription, learningSupportCost1.periodDescription);
        assertEquals( learningSupportCostsDtoTest[0].description, learningSupportCost1.description);
        assertEquals( learningSupportCostsDtoTest[0].startDate, learningSupportCost1.startDate);
        assertEquals( learningSupportCostsDtoTest[0].endDate, learningSupportCost1.endDate);
        assertEquals( learningSupportCostsDtoTest[0].staffId, learningSupportCost1.staff.id);
        assertEquals( learningSupportCostsDtoTest[1].id, learningSupportCost2.id );
        assertEquals( learningSupportCostsDtoTest[1].periodDescription, learningSupportCost2.periodDescription);
        assertEquals( learningSupportCostsDtoTest[1].description, learningSupportCost2.description);
        assertEquals( learningSupportCostsDtoTest[1].startDate, learningSupportCost2.startDate);
        assertEquals( learningSupportCostsDtoTest[1].endDate, learningSupportCost2.endDate);
        assertEquals( learningSupportCostsDtoTest[1].staffId, learningSupportCost2.staff.id);
    }
    
    @Test
    public void testEquals_Same() {
        LearningSupportCostDto learningSupportCostDto1 = new LearningSupportCostDto(learningSupportCost1.id, learningSupportCost1.student, learningSupportCost1.startDate,  learningSupportCost1.endDate, learningSupportCost1.hoursPerWeek, learningSupportCost1.weeks, learningSupportCost1.rate, learningSupportCost1.costCategory, learningSupportCost1.description, learningSupportCost1.periodDescription, learningSupportCost1.staff )
        LearningSupportCostDto learningSupportCostDto2 = new LearningSupportCostDto(learningSupportCost1.id, learningSupportCost1.student, learningSupportCost1.startDate,  learningSupportCost1.endDate, learningSupportCost1.hoursPerWeek, learningSupportCost1.weeks, learningSupportCost1.rate, learningSupportCost1.costCategory, learningSupportCost1.description, learningSupportCost1.periodDescription, learningSupportCost1.staff )
        assertEquals(learningSupportCostDto1, learningSupportCostDto2)
    }
    
    @Test
    public void testEquals_Different() {
        LearningSupportCostDto learningSupportCostDto1 = new LearningSupportCostDto(learningSupportCost1.id, learningSupportCost1.student, learningSupportCost1.startDate,  learningSupportCost1.endDate, learningSupportCost1.hoursPerWeek, learningSupportCost1.weeks, learningSupportCost1.rate, learningSupportCost1.costCategory, learningSupportCost1.description, learningSupportCost1.periodDescription, learningSupportCost1.staff)
        LearningSupportCostDto learningSupportCostDto2 = new LearningSupportCostDto(learningSupportCost2.id, learningSupportCost2.student, learningSupportCost2.startDate,  learningSupportCost2.endDate, learningSupportCost2.hoursPerWeek, learningSupportCost2.weeks, learningSupportCost2.rate, learningSupportCost2.costCategory, learningSupportCost2.description, learningSupportCost2.periodDescription, learningSupportCost2.staff )
        assertNotEquals(learningSupportCostDto1, learningSupportCostDto2)
    }
    
    @Test
    public void testHashCode_Same() {
        LearningSupportCostDto learningSupportCostDto1 = new LearningSupportCostDto(learningSupportCost1.id, learningSupportCost1.student, learningSupportCost1.startDate,  learningSupportCost1.endDate, learningSupportCost1.hoursPerWeek, learningSupportCost1.weeks, learningSupportCost1.rate, learningSupportCost1.costCategory, learningSupportCost1.description, learningSupportCost1.periodDescription, learningSupportCost1.staff )
        LearningSupportCostDto learningSupportCostDto2 = new LearningSupportCostDto(learningSupportCost1.id, learningSupportCost1.student, learningSupportCost1.startDate,  learningSupportCost1.endDate, learningSupportCost1.hoursPerWeek, learningSupportCost1.weeks, learningSupportCost1.rate, learningSupportCost1.costCategory, learningSupportCost1.description, learningSupportCost1.periodDescription, learningSupportCost1.staff )
        assertEquals(learningSupportCostDto1.hashCode(), learningSupportCostDto2.hashCode())
    }
    
    @Test
    public void testHashCode_Different() {
        LearningSupportCostDto learningSupportCostDto1 = new LearningSupportCostDto(learningSupportCost1.id, learningSupportCost1.student, learningSupportCost1.startDate,  learningSupportCost1.endDate, learningSupportCost1.hoursPerWeek, learningSupportCost1.weeks, learningSupportCost1.rate, learningSupportCost1.costCategory, learningSupportCost1.description, learningSupportCost1.periodDescription, learningSupportCost1.staff )
        LearningSupportCostDto learningSupportCostDto2 = new LearningSupportCostDto(learningSupportCost2.id, learningSupportCost2.student, learningSupportCost2.startDate,  learningSupportCost2.endDate, learningSupportCost2.hoursPerWeek, learningSupportCost2.weeks, learningSupportCost2.rate, learningSupportCost2.costCategory, learningSupportCost2.description, learningSupportCost2.periodDescription, learningSupportCost2.staff )
        assertNotEquals(learningSupportCostDto1.hashCode(), learningSupportCostDto2.hashCode())
    }
    
    @Test
    public void testConstructor_LearningSupportCost() {
        LearningSupportCostDto learningSupportCostTest= new LearningSupportCostDto(learningSupportCost1)
        assertEquals( learningSupportCostTest.periodDescription, learningSupportCost1.periodDescription);
        assertEquals( learningSupportCostTest.description, learningSupportCost1.description);
        assertEquals( learningSupportCostTest.startDate, learningSupportCost1.startDate)
        assertEquals( learningSupportCostTest.endDate, learningSupportCost1.endDate)
        assertEquals( learningSupportCostTest.staffId, learningSupportCost1.staff.id)
    }
}
