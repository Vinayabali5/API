package uk.ac.reigate.dto;

import static org.junit.Assert.*

import org.junit.Test
import org.junit.Before
import uk.ac.reigate.domain.Address
import uk.ac.reigate.domain.Person
import uk.ac.reigate.domain.Staff
import uk.ac.reigate.domain.academic.Faculty
import uk.ac.reigate.domain.lookup.Gender
import uk.ac.reigate.domain.lookup.StaffType
import uk.ac.reigate.domain.lookup.Title
import uk.ac.reigate.domain.lookup.TutorGroup



public class TutorGroupDtoTest {
    
    private Gender female
    
    private Title mrs
    
    private Address address1
    
    private StaffType staffType1
    
    private Person person1
    
    private Staff staff
    
    private Faculty faculty
    
    private TutorGroup tutorGroup1
    
    private TutorGroup tutorGroup2
    
    private List<TutorGroup> tutorGroups
    
    @Before
    public void setupTests() {
        this.female = new Gender('F', 'Female')
        this.mrs = new Title('2', 'Mrs')
        this.address1 = new Address('Flat D', 'Stag', 'Stanley', 'west', 'park', 'Wallington', '', 'E161FF', '', '', '', '')
        this.staffType1 = new StaffType('L', 'Lower', true)
        this.person1 =  new Person(mrs, 'Vinaya', 'Vin', 'Vin', 'Bali', 'Mick', 'Uday', new Date().parse('yyyy/MM/dd', '1991/11/11'), female, address1, '07809817665', '0890788889', '08937737737', 'mbvinayabali@gmail.com', true, null, 'vbm')
        this.staff = new Staff(person1, staffType1, true, 'VBM', 'vin', 'vbm', new Date().parse('yyyy/MM/dd', '2011/11/11'), new Date().parse('yyyy/MM/dd', '2016/11/11'), 11, 11);
        this.faculty= new Faculty('m', 'maths', staff, staff, staff, staff, staff, new Date().parse('yyyy/MM/dd', '2011/11/11'), new Date().parse('yyyy/MM/dd', '2013/11/11'))
        this.tutorGroup1 = new TutorGroup(
                id: 1,
                code: 'M',
                description: 'Male',
                faculty: faculty
                );
        this.tutorGroup2 = new TutorGroup(
                id: 2,
                code: 'F',
                description: 'Female',
                faculty: faculty
                );
        this.tutorGroups = Arrays.asList(tutorGroup1, tutorGroup2);
    }
    
    TutorGroupDto generateTutorGroupDto() {
        return generateTutorGroup1Dto()
    }
    
    TutorGroupDto generateTutorGroup1Dto() {
        return new TutorGroupDto(tutorGroup1.id, tutorGroup1.code, tutorGroup1.description, tutorGroup1.faculty.id)
    }
    
    TutorGroupDto generateTutorGroup2Dto() {
        return new TutorGroupDto(tutorGroup2.id, tutorGroup2.code, tutorGroup2.description, tutorGroup2.faculty.id)
    }
    
    @Test
    public void testMapFromTutorGroupEntityTest(){
        TutorGroupDto tutorGroup = TutorGroupDto.mapFromTutorGroupEntity( tutorGroup1 )
        assertEquals( tutorGroup.id, tutorGroup1.id );
        assertEquals( tutorGroup.code, tutorGroup1.code);
        assertEquals( tutorGroup.description, tutorGroup1.description);
        assertEquals( tutorGroup.facultyId, tutorGroup1.faculty.id);
    }
    
    @Test
    public void testMapFromTutorGroupsEntitiesTest(){
        List<TutorGroupDto> tutorGroup = TutorGroupDto.mapFromTutorGroupsEntities( tutorGroups )
        assertEquals( tutorGroup[0].id, tutorGroup1.id );
        assertEquals( tutorGroup[0].code, tutorGroup1.code);
        assertEquals( tutorGroup[0].description, tutorGroup1.description);
        assertEquals( tutorGroup[0].facultyId, tutorGroup1.faculty.id);
        assertEquals( tutorGroup[1].id, tutorGroup2.id );
        assertEquals( tutorGroup[1].code, tutorGroup2.code);
        assertEquals( tutorGroup[1].description, tutorGroup2.description);
        assertEquals( tutorGroup[1].facultyId, tutorGroup2.faculty.id);
    }
    
    @Test
    public void testMapToTutorGroupEntityTest(){
        TutorGroupDto tutorGroupDto = generateTutorGroupDto()
        TutorGroup tutorGroup = TutorGroupDto.mapToTutorGroupEntity( tutorGroupDto, faculty );
        assertEquals( tutorGroup.id, tutorGroup1.id );
        assertEquals( tutorGroup.code, tutorGroup1.code);
        assertEquals( tutorGroup.description, tutorGroup1.description);
        assertEquals( tutorGroup.faculty, tutorGroup1.faculty);
    }
    
    @Test
    public void testEquals_Same() {
        TutorGroupDto tutorGroupDto1 = new TutorGroupDto(tutorGroup1.id, tutorGroup1.code, tutorGroup1.description, tutorGroup1.faculty.id)
        TutorGroupDto tutorGroupDto2 = new TutorGroupDto(tutorGroup1.id, tutorGroup1.code, tutorGroup1.description, tutorGroup1.faculty.id)
        assertEquals(tutorGroupDto1, tutorGroupDto2)
    }
    
    @Test
    public void testEquals_Different() {
        TutorGroupDto tutorGroupDto1 = new TutorGroupDto(tutorGroup1.id, tutorGroup1.code, tutorGroup1.description, tutorGroup1.faculty.id)
        TutorGroupDto tutorGroupDto2 = new TutorGroupDto(tutorGroup2.id, tutorGroup2.code, tutorGroup2.description, tutorGroup2.faculty.id)
        assertNotEquals(tutorGroupDto1, tutorGroupDto2)
    }
    
    @Test
    public void testHashCode_Same() {
        TutorGroupDto tutorGroupDto1 = new TutorGroupDto(tutorGroup1.id, tutorGroup1.code, tutorGroup1.description, tutorGroup1.faculty.id)
        TutorGroupDto tutorGroupDto2 = new TutorGroupDto(tutorGroup1.id, tutorGroup1.code, tutorGroup1.description, tutorGroup1.faculty.id)
        assertEquals(tutorGroupDto1.hashCode(), tutorGroupDto2.hashCode())
    }
    
    @Test
    public void testHashCode_Different() {
        TutorGroupDto tutorGroupDto1 = new TutorGroupDto(tutorGroup1.id, tutorGroup1.code, tutorGroup1.description, tutorGroup1.faculty.id)
        TutorGroupDto tutorGroupDto2 = new TutorGroupDto(tutorGroup2.id, tutorGroup2.code, tutorGroup2.description, tutorGroup2.faculty.id)
        assertNotEquals(tutorGroupDto1.hashCode(), tutorGroupDto2.hashCode())
    }
    
    @Test
    public void testConstructor_TutorGroupp() {
        TutorGroupDto tutorGroup = new TutorGroupDto(tutorGroup1)
        assertEquals( tutorGroup.id, tutorGroup1.id );
        assertEquals( tutorGroup.code, tutorGroup1.code);
        assertEquals( tutorGroup.description, tutorGroup1.description);
        assertEquals( tutorGroup.facultyId, tutorGroup1.faculty.id);
    }
}
