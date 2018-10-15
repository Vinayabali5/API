package uk.ac.reigate.dto;

import static org.junit.Assert.*

import org.junit.Before
import org.junit.Test

import uk.ac.reigate.domain.Address
import uk.ac.reigate.domain.Person
import uk.ac.reigate.domain.Staff
import uk.ac.reigate.domain.lookup.Gender
import uk.ac.reigate.domain.lookup.StaffType
import uk.ac.reigate.domain.lookup.Title



public class StaffDtoTest {
    
    private Title mrs
    
    private Gender female
    
    private Address address1
    
    private Person person
    
    private StaffType type
    
    private Staff staff1
    
    private Staff staff2
    
    private List<Staff> staffs
    
    @Before
    public void setupTests() {
        this.mrs = new Title(1, 'mrs', 'Mrs')
        this.female = new Gender(1, 'F', 'Female')
        this.address1 = new Address(1, 'Flat D', 'Stag', 'Stanley', 'west', 'park', 'Wallington', '', 'E161FF', '', '', '', '')
        this.person =  new Person(1, mrs, 'Vinaya', 'Vin', 'Vin', 'Bali', 'Mick', 'Uday', null, female, address1, '07809817665', '0890788889', '08937737737', 'mbvinayabali@gmail.com', true, null, 'vbm')
        this.type = new StaffType(1, 'L', 'Lower', true)
        this.staff1 = new Staff(
                id: 1,
                person: person,
                type: type,
                onTimetable: true,
                initials: 'vbm',
                knownAs: 'vinaya',
                networkLogin:'vbm',
                startDate: new Date().parse('yyyy/MM/dd', '2011/07/09'),
                endDate: new Date().parse('yyyy/MM/dd', '2013/07/09'),
                staffRef: 11,
                staffDetailsId: 1
                );
        this.staff2 = new Staff(
                id: 2,
                person: person,
                type: type,
                onTimetable: true,
                initials: 'vbm',
                knownAs: 'vinaya',
                networkLogin:'vbm',
                startDate: new Date().parse('yyyy/MM/dd', '2011/07/09'),
                endDate: new Date().parse('yyyy/MM/dd', '2013/07/09'),
                staffRef: 12,
                staffDetailsId: 2
                
                );
        this.staffs = Arrays.asList(staff1, staff2);
    }
    
    StaffDto generateStaffDto() {
        return generateStaff1Dto()
    }
    
    StaffDto generateStaff1Dto() {
        return new StaffDto(staff1.id, staff1.person.id, staff1.type.id, staff1.onTimetable, staff1.initials, staff1.knownAs, staff1.networkLogin, staff1.startDate, staff1.endDate, staff1.staffRef, staff1.staffDetailsId)
    }
    
    StaffDto generateStaff2Dto() {
        return new StaffDto(staff2.id, staff2.person.id, staff2.type.id, staff2.onTimetable, staff2.initials, staff2.knownAs, staff2.networkLogin, staff2.startDate, staff2.endDate, staff2.staffRef, staff2.staffDetailsId)
    }
    
    @Test
    public void testMapFromStaffEntityTest() {
        StaffDto staffTest = StaffDto.mapFromStaffEntity( staff1 )
        assertEquals( staffTest.id, staff1.id);
        assertEquals( staffTest.personId, staff1.person.id);
        assertEquals( staffTest.typeId, staff1.type.id);
        assertEquals( staffTest.initials, staff1.initials);
        assertEquals( staffTest.onTimetable, staff1.onTimetable);
        assertEquals( staffTest.knownAs, staff1.knownAs);
        assertEquals( staffTest.networkLogin, staff1.networkLogin);
        assertEquals( staffTest.startDate, staff1.startDate);
        assertEquals( staffTest.endDate, staff1.endDate);
    }
    
    @Test
    public void testMapFromStaffsEntitiesTest(){
        List<StaffDto> staffTest = StaffDto.mapFromStaffsEntities( staffs )
        assertEquals( staffTest[0].id, staff1.id);
        assertEquals( staffTest[0].personId, staff1.person.id);
        assertEquals( staffTest[0].typeId, staff1.type.id);
        assertEquals( staffTest[0].initials, staff1.initials);
        assertEquals( staffTest[0].onTimetable, staff1.onTimetable);
        assertEquals( staffTest[0].knownAs, staff1.knownAs);
        assertEquals( staffTest[0].networkLogin, staff1.networkLogin);
        assertEquals( staffTest[0].startDate, staff1.startDate);
        assertEquals( staffTest[0].endDate, staff1.endDate);
    }
    
    @Test
    public void testMapToStaffEntityTest(){
        StaffDto staffDto = generateStaffDto()
        Staff staff = StaffDto.mapToStaffEntity( staffDto, person, type )
        assertEquals( staff.id, staff1.id );
        assertEquals( staff.person, staff1.person);
        assertEquals( staff.type, staff1.type);
        assertEquals( staff.initials, staff1.initials);
        assertEquals( staff.onTimetable, staff1.onTimetable);
        assertEquals( staff.knownAs, staff1.knownAs);
        assertEquals( staff.networkLogin, staff1.networkLogin);
        assertEquals( staff.startDate, staff1.startDate);
        assertEquals( staff.endDate, staff1.endDate);
    }
    
    @Test
    public void testEquals_Same() {
        StaffDto staffDto1 = new StaffDto(staff1.id, staff1.person.id, staff1.type.id, staff1.onTimetable, staff1.initials, staff1.knownAs, staff1.networkLogin, staff1.startDate, staff1.endDate, staff1.staffRef, staff1.staffDetailsId)
        StaffDto staffDto2 = new StaffDto(staff1.id, staff1.person.id, staff1.type.id, staff1.onTimetable, staff1.initials, staff1.knownAs, staff1.networkLogin, staff1.startDate, staff1.endDate, staff1.staffRef, staff1.staffDetailsId)
        assertEquals(staffDto1, staffDto2)
    }
    
    @Test
    public void testEquals_Different() {
        StaffDto staffDto1 = new StaffDto(staff1.id, staff1.person.id, staff1.type.id, staff1.onTimetable, staff1.initials, staff1.knownAs, staff1.networkLogin, staff1.startDate, staff1.endDate, staff1.staffRef, staff1.staffDetailsId)
        StaffDto staffDto2 = new StaffDto(staff2.id, staff2.person.id, staff2.type.id, staff2.onTimetable, staff2.initials, staff2.knownAs, staff2.networkLogin, staff2.startDate, staff2.endDate, staff2.staffRef, staff2.staffDetailsId)
        assertNotEquals(staffDto1, staffDto2)
    }
    
    @Test
    public void testHashCode_Same() {
        StaffDto staffDto1 = new StaffDto(staff1.id, staff1.person.id, staff1.type.id, staff1.onTimetable, staff1.initials, staff1.knownAs, staff1.networkLogin, staff1.startDate, staff1.endDate, staff1.staffRef, staff1.staffDetailsId)
        StaffDto staffDto2 = new StaffDto(staff1.id, staff1.person.id, staff1.type.id, staff1.onTimetable, staff1.initials, staff1.knownAs, staff1.networkLogin, staff1.startDate, staff1.endDate, staff1.staffRef, staff1.staffDetailsId)
        assertEquals(staffDto1.hashCode(), staffDto2.hashCode())
    }
    
    @Test
    public void testHashCode_Different() {
        StaffDto staffDto1 = new StaffDto(staff1.id, staff1.person.id, staff1.type.id, staff1.onTimetable, staff1.initials, staff1.knownAs, staff1.networkLogin, staff1.startDate, staff1.endDate, staff1.staffRef, staff1.staffDetailsId)
        StaffDto staffDto2 = new StaffDto(staff2.id, staff2.person.id, staff2.type.id, staff2.onTimetable, staff2.initials, staff2.knownAs, staff2.networkLogin, staff2.startDate, staff2.endDate, staff2.staffRef, staff2.staffDetailsId)
        assertNotEquals(staffDto1.hashCode(), staffDto2.hashCode())
    }
}
