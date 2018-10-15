package uk.ac.reigate.dto;

import static org.junit.Assert.*

import org.junit.Before
import org.junit.Test
import uk.ac.reigate.domain.academic.School
import uk.ac.reigate.domain.lookup.SchoolPriority
import uk.ac.reigate.domain.lookup.SchoolType
import uk.ac.reigate.dto.SchoolDto

public class SchoolDtoTest {
    
    private SchoolType schoolType
    
    private SchoolPriority schoolPriority
    
    private School school1
    
    private School school2
    
    private List<School> schools
    
    @Before
    public void setupTests() {
        this.schoolType = new SchoolType(1,'CRY', 'Croydon')
        this.schoolPriority = new SchoolPriority(1, 'A', 'As')
        this.school1 = new School(
                id: 1,
                name: 'Bishop',
                type: schoolType,
                priority: schoolPriority,
                urn: '13',
                line1: 'Mercury',
                line2: 'Oxford',
                line3: 'London',
                postcode: 'E151DD'
                );
        this.school2 = new School(
                id: 2,
                name: 'Bishop',
                type: schoolType,
                priority: schoolPriority,
                urn: '15',
                line1: 'Mercury',
                line2: 'Oxford',
                line3: 'Oxford',
                postcode: 'CR51RY'
                );
        this.schools = Arrays.asList(school1, school2);
    }
    SchoolDto generateSchoolDto() {
        return generateSchool1Dto()
    }
    SchoolDto generateSchool1Dto() {
        return new SchoolDto(school1)
    }
    SchoolDto generateSchool2Dto() {
        return new SchoolDto(school2)
    }
    
    @Test
    public void testMapFromSchoolEntityTest(){
        SchoolDto schoolTest = SchoolDto.mapFromSchoolEntity( school1 )
        assertEquals( schoolTest.id, school1.id );
        assertEquals( schoolTest.name, school1.name);
        assertEquals( schoolTest.typeId, school1.type.id);
        assertEquals( schoolTest.priorityId, school1.priority.id);
        assertEquals( schoolTest.urn, school1.urn);
        assertEquals( schoolTest.line1, school1.line1);
        assertEquals( schoolTest.line2, school1.line2);
        assertEquals( schoolTest.line3, school1.line3);
        assertEquals( schoolTest.postcode, school1.postcode);
    }
    
    @Test
    public void testMapFromSchoolsEntitiesTest(){
        List<SchoolDto> schoolTest = SchoolDto.mapFromSchoolsEntities( schools )
        assertEquals( schoolTest[0].id, school1.id );
        assertEquals( schoolTest[0].name, school1.name);
        assertEquals( schoolTest[0].typeId, school1.type.id);
        assertEquals( schoolTest[0].priorityId, school1.priority.id);
        assertEquals( schoolTest[0].urn, school1.urn);
        assertEquals( schoolTest[0].line1, school1.line1);
        assertEquals( schoolTest[0].line2, school1.line2);
        assertEquals( schoolTest[0].line3, school1.line3);
        assertEquals( schoolTest[0].postcode, school1.postcode);
        assertEquals( schoolTest[1].id, school2.id );
        assertEquals( schoolTest[1].name, school2.name);
        assertEquals( schoolTest[1].typeId, school2.type.id);
        assertEquals( schoolTest[1].priorityId, school2.priority.id);
        assertEquals( schoolTest[1].urn, school2.urn);
        assertEquals( schoolTest[1].line1, school2.line1);
        assertEquals( schoolTest[1].line2, school2.line2);
        assertEquals( schoolTest[1].line3, school2.line3);
        assertEquals( schoolTest[1].postcode, school2.postcode);
    }
    
    @Test
    public void testEquals_Same() {
        SchoolDto schoolDto1 = new SchoolDto(school1)
        SchoolDto schoolDto2 = new SchoolDto(school1)
        assertEquals(schoolDto1, schoolDto2)
    }
    
    @Test
    public void testEquals_Different() {
        SchoolDto schoolDto1 = new SchoolDto(school1)
        SchoolDto schoolDto2 = new SchoolDto(school2)
        assertNotEquals(schoolDto1, schoolDto2)
    }
    
    @Test
    public void testHashCode_Same() {
        SchoolDto schoolDto1 = new SchoolDto(school1)
        SchoolDto schoolDto2 = new SchoolDto(school1)
        assertEquals(schoolDto1.hashCode(), schoolDto2.hashCode())
    }
    
    @Test
    public void testHashCode_Different() {
        SchoolDto schoolDto1 = new SchoolDto(school1)
        SchoolDto schoolDto2 = new SchoolDto(school2)
        assertNotEquals(schoolDto1.hashCode(), schoolDto2.hashCode())
    }
}
