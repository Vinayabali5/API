package uk.ac.reigate.dto;

import static org.junit.Assert.*

import org.junit.Test
import org.junit.Before
import uk.ac.reigate.domain.Address
import uk.ac.reigate.domain.Person
import uk.ac.reigate.domain.Staff
import uk.ac.reigate.domain.academic.Department
import uk.ac.reigate.domain.academic.Faculty
import uk.ac.reigate.domain.lookup.Gender
import uk.ac.reigate.domain.lookup.StaffType
import uk.ac.reigate.domain.lookup.Title



public class DepartmentDtoTest {
    
    private Title mrs
    
    private Gender female
    
    private Address address1
    
    private Person person1
    
    private StaffType staffType1
    
    private Staff staff
    
    private Faculty faculty
    
    private Department department1
    
    private Department department2
    
    private List<Department> departments
    
    @Before
    public void setupTests() {
        this.female = new Gender(1, 'F', 'Female')
        this.mrs = new Title(1, '2', 'Mrs')
        this.address1 = new Address(1, 'Flat D', 'Stag', 'Stanley', 'west', 'park', 'Wallington', '', 'E161FF', '', '', '', '')
        this.person1 = new Person(1, mrs, 'Vinaya', 'Vin', 'Vin', 'Bali', 'Mick', 'Uday', new Date().parse('yyyy/MM/dd', '1991/11/11'), female, address1, '07809817665', '0890788889', '08937737737', 'mbvinayabali@gmail.com', true, null, 'vbm')
        this.staffType1 = new StaffType(1, 'L', 'Lower', true)
        this.staff = new Staff(1, person1, staffType1, true, 'VBM', 'vin', 'vbm', new Date().parse('yyyy/MM/dd', '2011/11/11'), new Date().parse('yyyy/MM/dd', '2016/11/11'), 11, 11);
        this.faculty = new Faculty(1, 'm', 'maths', staff, staff, staff, staff, staff, new Date().parse('yyyy/MM/dd', '2011/11/11'), new Date().parse('yyyy/MM/dd', '2013/11/11'))
        this.department1 = new Department(
                id: 1,
                name: 'A',
                description: 'A Department',
                faculty: faculty,
                hod: staff,
                hod2:staff,
                academic: true
                );
        this.department2 = new Department(
                id: 2,
                name: 'B',
                description: 'B Department',
                faculty: faculty,
                hod: staff,
                hod2:staff,
                academic: true
                );
        this.departments = Arrays.asList(department1, department2);
    }
    
    DepartmentDto generateDepartmentDto() {
        return generateDepartment1Dto()
    }
    
    DepartmentDto generateDepartment1Dto(){
        return new DepartmentDto(department1.id, department1.name, department1.description, department1.faculty.id, department1.hod.id, department1.hod2.id,  department1.hod.knownAs, department1.hod2.knownAs, department1.academic)
    }
    
    DepartmentDto generateDepartment2Dto(){
        return new DepartmentDto(department2.id, department2.name, department2.description, department2.faculty.id, department2.hod.id, department2.hod2.id, department2.hod.knownAs, department2.hod2.knownAs, department2.academic)
    }
    
    @Test
    public void testMapFromDepartmentEntityTest(){
        DepartmentDto departmentTest = DepartmentDto.mapFromDepartmentEntity( department1 )
        assertEquals( departmentTest.id, department1.id );
        assertEquals( departmentTest.name, department1.name);
        assertEquals( departmentTest.description, department1.description);
        assertEquals( departmentTest.facultyId, department1.faculty.id);
        assertEquals( departmentTest.hodId, department1.hod.id);
        assertEquals( departmentTest.academic, department1.academic);
    }
    
    @Test
    public void testMapFromDepartmentsEntitiesTest(){
        List<DepartmentDto> departmentTest = DepartmentDto.mapFromDepartmentsEntities(departments)
        assertEquals( departmentTest[0].id, department1.id );
        assertEquals( departmentTest[0].name, department1.name);
        assertEquals( departmentTest[0].description, department1.description);
        assertEquals( departmentTest[0].facultyId, department1.faculty.id);
        assertEquals( departmentTest[0].hodId, department1.hod.id);
        assertEquals( departmentTest[1].id, department2.id );
        assertEquals( departmentTest[1].name, department2.name);
        assertEquals( departmentTest[1].description, department2.description);
        assertEquals( departmentTest[1].facultyId, department2.faculty.id);
        assertEquals( departmentTest[1].hodId, department2.hod.id);
    }
    
    @Test
    public void testMapToDepartmentEntityTest(){
        DepartmentDto departmentDto = generateDepartmentDto()
        Department departmentTest = DepartmentDto.mapToDepartmentEntity( departmentDto, faculty, staff, staff );
        assertEquals( departmentTest.id, department1.id );
        assertEquals( departmentTest.name, department1.name);
        assertEquals( departmentTest.description, department1.description);
        assertEquals( departmentTest.faculty, department1.faculty);
        assertEquals( departmentTest.hod, department1.hod);
        assertEquals( departmentTest.academic, department1.academic);
    }
    
    @Test
    public void testEquals_Same() {
        DepartmentDto departmentDto1 = new DepartmentDto(department1.id, department1.name, department1.description, department1.faculty.id, department1.hod.id, department1.hod2.id, department1.hod.knownAs,department1.hod2.knownAs, department1.academic)
        DepartmentDto departmentDto2 = new DepartmentDto(department1.id, department1.name, department1.description, department1.faculty.id, department1.hod.id, department1.hod2.id, department1.hod.knownAs, department1.hod2.knownAs, department1.academic)
        assertEquals( departmentDto1, departmentDto2)
    }
    
    @Test
    public void testEquals_Different() {
        DepartmentDto departmentDto1 = new DepartmentDto(department1.id, department1.name, department1.description, department1.faculty.id, department1.hod.id, department1.hod2.id, department1.hod.knownAs, department1.hod2.knownAs, department1.academic)
        DepartmentDto departmentDto2 = new DepartmentDto(department2.id, department2.name, department2.description, department2.faculty.id, department2.hod.id, department2.hod2.id, department2.hod.knownAs, department2.hod2.knownAs, department2.academic)
        assertNotEquals( departmentDto1, departmentDto2)
    }
    
    @Test
    public void testHashCode_Same() {
        DepartmentDto departmentDto1 = new DepartmentDto(department1.id, department1.name, department1.description, department1.faculty.id, department1.hod.id, department1.hod2.id, department1.hod.knownAs, department1.hod2.knownAs, department1.academic)
        DepartmentDto departmentDto2 = new DepartmentDto(department1.id, department1.name, department1.description, department1.faculty.id, department1.hod.id, department1.hod2.id, department2.hod.knownAs, department2.hod2.knownAs, department2.academic)
        assertEquals( departmentDto1.hashCode(), departmentDto2.hashCode())
    }
    
    @Test
    public void testHashCode_Different() {
        DepartmentDto departmentDto1 = new DepartmentDto(department1.id, department1.name, department1.description, department1.faculty.id, department1.hod.id, department1.hod2.id, department1.hod.knownAs, department1.hod2.knownAs, department1.academic)
        DepartmentDto departmentDto2 = new DepartmentDto(department2.id, department2.name, department2.description, department2.faculty.id, department2.hod.id, department2.hod2.id, department2.hod.knownAs, department2.hod2.knownAs, department2.academic)
        assertNotEquals( departmentDto1.hashCode(), departmentDto2.hashCode())
    }
    
    @Test
    public void testConstructor_Department() {
        DepartmentDto departmentDto = new DepartmentDto(department1)
        assertEquals( departmentDto.name, department1.name )
        assertEquals( departmentDto.description, department1.description )
        assertEquals( departmentDto.facultyId, department1.faculty.id )
        assertEquals( departmentDto.hodId, department1.hod.id )
        assertEquals( departmentDto.academic, department1.academic )
    }
}
