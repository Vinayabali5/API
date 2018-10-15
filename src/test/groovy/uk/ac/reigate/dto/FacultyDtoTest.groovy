package uk.ac.reigate.dto;

import org.junit.Test
import org.junit.Before
import uk.ac.reigate.domain.Address
import uk.ac.reigate.domain.Person
import uk.ac.reigate.domain.Staff
import uk.ac.reigate.domain.academic.Faculty
import uk.ac.reigate.domain.lookup.Gender
import uk.ac.reigate.domain.lookup.StaffType
import uk.ac.reigate.domain.lookup.Title
import uk.ac.reigate.dto.FacultyDto
import static org.junit.Assert.*

public class FacultyDtoTest {
    
    private Gender female
    
    private StaffType staffType1
    
    private Title mrs
    
    private Address address1
    
    private Person person1
    
    private Staff staff
    
    private Faculty faculty1
    
    private Faculty faculty2
    
    private List<Faculty> faculties
    
    @Before
    public void setupTests() {
        this.staffType1 = new StaffType('FT', 'Full Time', true)
        this.staff = new Staff(1, null, staffType1, true, 'VBM', 'vin', 'vbm', new Date().parse('yyyy/MM/dd', '2011/11/11'), new Date().parse('yyyy/MM/dd', '2016/11/11'), 11, 11);
        this.faculty1 = new Faculty(
                id: 1,
                code:'A',
                description:'A Faculty',
                hof: staff,
                dol: staff,
                adol: staff,
                pd: staff,
                apd: staff,
                validFrom: new Date().parse('yyyy/MM/dd', '2011/07/09'),
                validTo: new Date().parse('yyyy/MM/dd', '2013/07/09')
                );
        this.faculty2 = new Faculty(
                id: 2,
                code:'A',
                description:'A Faculty',
                hof: staff,
                dol: staff,
                adol: staff,
                pd: staff,
                apd: staff,
                validFrom: new Date().parse('yyyy/MM/dd', '2011/07/09'),
                validTo: new Date().parse('yyyy/MM/dd', '2013/07/09')
                );
        this.faculties = Arrays.asList(faculty1, faculty2);
    }
    
    FacultyDto generateFacultyDto() {
        return generateFaculty1Dto()
    }
    
    FacultyDto generateFaculty1Dto() {
        return new FacultyDto(faculty1.id, faculty1.code, faculty1.description, faculty1.hof.id, faculty1.dol.id, faculty1.adol.id, faculty1.pd.id, faculty1.apd.id, faculty1.validFrom, faculty1.validTo, faculty1.hof.knownAs)
    }
    
    FacultyDto generateFaculty2Dto() {
        return new FacultyDto(faculty2.id, faculty2.code, faculty2.description, faculty2.hof.id, faculty2.dol.id, faculty2.adol.id, faculty2.pd.id, faculty2.apd.id, faculty2.validFrom, faculty2.validTo , faculty2.hof.knownAs)
    }
    @Test
    public void testMapFromFacultyEntity(){
        FacultyDto facultyTest = FacultyDto.mapFromFacultyEntity( faculty1 )
        assertEquals( facultyTest.id, faculty1.id );
        assertEquals( facultyTest.code, faculty1.code);
        assertEquals( facultyTest.description, faculty1.description);
        assertEquals( facultyTest.hofId, faculty1.hof.id);
        assertEquals( facultyTest.dolId, faculty1.dol.id);
        assertEquals( facultyTest.adolId, faculty1.adol.id);
        assertEquals( facultyTest.pdId, faculty1.pd.id);
        assertEquals( facultyTest.apdId, faculty1.apd.id);
        assertEquals( facultyTest.validFrom, faculty1.validFrom);
        assertEquals( facultyTest.validTo, faculty1.validTo);
    }
    
    @Test
    public void testMapFromFacultiesEntities(){
        List<FacultyDto> facultysTest = FacultyDto.mapFromFacultiesEntities( faculties )
        assertEquals( facultysTest[0].id, faculty1.id );
        assertEquals( facultysTest[0].code, faculty1.code);
        assertEquals( facultysTest[0].description, faculty1.description);
        assertEquals( facultysTest[0].hofId, faculty1.hof.id);
        assertEquals( facultysTest[0].dolId, faculty1.dol.id);
        assertEquals( facultysTest[0].adolId, faculty1.adol.id);
        assertEquals( facultysTest[0].pdId, faculty1.pd.id);
        assertEquals( facultysTest[0].apdId, faculty1.apd.id);
        assertEquals( facultysTest[0].validFrom, faculty1.validFrom)
        assertEquals( facultysTest[0].validTo, faculty1.validTo);
        assertEquals( facultysTest[1].id, faculty2.id );
        assertEquals( facultysTest[1].code, faculty2.code);
        assertEquals( facultysTest[1].description, faculty2.description);
        assertEquals( facultysTest[1].hofId, faculty2.hof.id);
        assertEquals( facultysTest[1].dolId, faculty2.dol.id);
        assertEquals( facultysTest[1].adolId, faculty2.adol.id);
        assertEquals( facultysTest[1].pdId, faculty2.pd.id);
        assertEquals( facultysTest[1].apdId, faculty2.apd.id);
        assertEquals( facultysTest[1].validFrom, faculty2.validFrom);
        assertEquals( facultysTest[1].validTo, faculty2.validTo);
    }
    
    @Test
    public void testMapToFacultyEntity(){
        FacultyDto facultyDto = generateFacultyDto();
        Faculty facultyTest = FacultyDto.mapToFacultyEntity( facultyDto, staff, staff, staff, staff, staff );
        assertEquals( facultyTest.id, faculty1.id );
        assertEquals( facultyTest.code, faculty1.code);
        assertEquals( facultyTest.description, faculty1.description);
        assertEquals( facultyTest.hof, faculty1.hof);
        assertEquals( facultyTest.dol, faculty1.dol);
        assertEquals( facultyTest.adol, faculty1.adol);
        assertEquals( facultyTest.pd, faculty1.pd);
        assertEquals( facultyTest.apd, faculty1.apd);
        assertEquals( facultyTest.validFrom, faculty1.validFrom);
        assertEquals( facultyTest.validTo, faculty1.validTo);
    }
    
    @Test
    public void testEquals_Same() {
        FacultyDto facultyDto1 = new FacultyDto(faculty1.id, faculty1.code, faculty1.description, faculty1.hof.id, faculty1.dol.id, faculty1.adol.id, faculty1.pd.id, faculty1.apd.id, faculty1.validFrom, faculty1.validTo, faculty1.hof.knownAs)
        FacultyDto facultyDto2 = new FacultyDto(faculty1.id, faculty1.code, faculty1.description, faculty1.hof.id, faculty1.dol.id, faculty1.adol.id, faculty1.pd.id, faculty1.apd.id, faculty1.validFrom, faculty1.validTo, faculty1.hof.knownAs)
        assertEquals( facultyDto1, facultyDto2)
    }
    
    @Test
    public void testEquals_Different() {
        FacultyDto facultyDto1 = new FacultyDto(faculty1.id, faculty1.code, faculty1.description, faculty1.hof.id, faculty1.dol.id, faculty1.adol.id, faculty1.pd.id, faculty1.apd.id, faculty1.validFrom, faculty1.validTo, faculty1.hof.knownAs)
        FacultyDto facultyDto2 = new FacultyDto(faculty2.id, faculty2.code, faculty2.description, faculty2.hof.id, faculty2.dol.id, faculty2.adol.id, faculty2.pd.id, faculty2.apd.id, faculty2.validFrom, faculty2.validTo, faculty2.hof.knownAs)
        assertNotEquals( facultyDto1, facultyDto2)
    }
    
    @Test
    public void testHashCode_Same() {
        FacultyDto facultyDto1 = new FacultyDto(faculty1.id, faculty1.code, faculty1.description, faculty1.hof.id, faculty1.dol.id, faculty1.adol.id, faculty1.pd.id, faculty1.apd.id, faculty1.validFrom, faculty1.validTo, faculty1.hof.knownAs)
        FacultyDto facultyDto2 = new FacultyDto(faculty1.id, faculty1.code, faculty1.description, faculty1.hof.id, faculty1.dol.id, faculty1.adol.id, faculty1.pd.id, faculty1.apd.id, faculty1.validFrom, faculty1.validTo, faculty2.hof.knownAs)
        assertEquals( facultyDto1.hashCode(), facultyDto2.hashCode())
    }
    
    @Test
    public void testHashCode_Different() {
        FacultyDto facultyDto1 = new FacultyDto(faculty1.id, faculty1.code, faculty1.description, faculty1.hof.id, faculty1.dol.id, faculty1.adol.id, faculty1.pd.id, faculty1.apd.id, faculty1.validFrom, faculty1.validTo, faculty1.hof.knownAs)
        FacultyDto facultyDto2 = new FacultyDto(faculty2.id, faculty2.code, faculty2.description, faculty2.hof.id, faculty2.dol.id, faculty2.adol.id, faculty2.pd.id, faculty2.apd.id, faculty2.validFrom, faculty2.validTo, faculty2.hof.knownAs)
        assertNotEquals( facultyDto1.hashCode(), facultyDto2.hashCode())
    }
    
    @Test
    public void testConstructor_Faculty() {
        FacultyDto facultyDto = new FacultyDto(faculty1)
        assertEquals( facultyDto.code, faculty1.code )
        assertEquals( facultyDto.description, faculty1.description )
        assertEquals( facultyDto.hofId, faculty1.hof.id )
        assertEquals( facultyDto.dolId, faculty1.dol.id )
        assertEquals( facultyDto.adolId, faculty1.adol.id )
        assertEquals( facultyDto.pdId, faculty1.pd.id )
        assertEquals( facultyDto.apdId, faculty1.apd.id )
        assertEquals( facultyDto.validFrom, faculty1.validFrom )
        assertEquals( facultyDto.validTo, faculty1.validTo)
    }
}
