package uk.ac.reigate.dto;

import static org.junit.Assert.*;

import org.junit.Before
import org.junit.Test

import uk.ac.reigate.domain.academic.AcademicYear

public class AcademicYearDtoTest {
    
    private AcademicYear academicYear1
    
    private AcademicYear academicYear2
    
    private List<AcademicYear> academicYears
    
    @Before
    public void setup() {
        this.academicYear1 = new AcademicYear(
                id: 1,
                code: '15/16',
                description: 'Academic Year 15/16',
                startDate: new Date().parse('yyyy/MM/dd', '2015/09/01'),
                endDate: new Date().parse('yyyy/MM/dd', '2016/06/30')
                );
        this.academicYear2 = new AcademicYear(
                id: 2,
                code: '14/15',
                description: 'Academic Year 14/15',
                startDate: new Date().parse('yyyy/MM/dd', '2014/09/01'),
                endDate: new Date().parse('yyyy/MM/dd', '2015/06/30')
                );
        this.academicYears = Arrays.asList(this.academicYear1, this.academicYear2);
    }
    
    @Test
    void testConstructor_academicYear() {
        AcademicYearDto academicYearDto = new AcademicYearDto( academicYear1 )
        assertEquals( academicYearDto.id, academicYear1.id);
        assertEquals( academicYearDto.code, academicYear1.code);
        assertEquals( academicYearDto.description, academicYear1.description);
        assertEquals( academicYearDto.startDate, academicYear1.startDate);
        assertEquals( academicYearDto.endDate, academicYear1.endDate);
    }
    
    
    @Test
    public void testMapFromAcademicYearEntity() {
        AcademicYearDto academicYearDto = AcademicYearDto.mapFromEntity( academicYear1 );
        assertEquals( academicYearDto.id, academicYear1.id);
        assertEquals( academicYearDto.code, academicYear1.code);
        assertEquals( academicYearDto.description, academicYear1.description);
        assertEquals( academicYearDto.startDate, academicYear1.startDate);
        assertEquals( academicYearDto.endDate, academicYear1.endDate);
    }
    
    @Test
    public void testMapFromAcademicYearsEntities(){
        List<AcademicYearDto> academicYearDtoList = AcademicYearDto.mapFromEntityList( this.academicYears )
        assertEquals( academicYearDtoList[0].id, academicYear1.id);
        assertEquals( academicYearDtoList[0].code, academicYear1.code);
        assertEquals( academicYearDtoList[0].description, academicYear1.description);
        assertEquals( academicYearDtoList[0].startDate, academicYear1.startDate);
        assertEquals( academicYearDtoList[0].endDate, academicYear1.endDate);
        assertEquals( academicYearDtoList[1].id, academicYear2.id );
        assertEquals( academicYearDtoList[1].code, academicYear2.code );
        assertEquals( academicYearDtoList[1].description, academicYear2.description );
        assertEquals( academicYearDtoList[1].startDate, academicYear2.startDate);
        assertEquals( academicYearDtoList[1].endDate, academicYear2.endDate);
    }
    
    @Test
    public void testMapToEntity(){
        AcademicYearDto academicYearDto = new AcademicYearDto( academicYear1.id, academicYear1.code, academicYear1.description, academicYear1.startDate, academicYear1.endDate )
        AcademicYear academicYear = AcademicYearDto.mapToEntity( academicYearDto )
        assertEquals( academicYear.id, academicYear1.id );
        assertEquals( academicYear.code, academicYear1.code );
        assertEquals( academicYear.description, academicYear1.description );
        assertEquals( academicYear.startDate, academicYear1.startDate );
        assertEquals( academicYear.endDate, academicYear1.endDate );
    }
    
    
    @Test
    public void testEquals_Same() {
        AcademicYearDto academicYearDto1 = new AcademicYearDto(academicYear1)
        AcademicYearDto academicYearDto2 = new AcademicYearDto(academicYear1)
        assertEquals( academicYearDto1, academicYearDto2 );
    }
    
    @Test
    public void testEquals_Different() {
        AcademicYearDto academicYearDto1 = new AcademicYearDto(academicYear1)
        AcademicYearDto academicYearDto2 = new AcademicYearDto(academicYear2)
        assertNotEquals( academicYearDto1, academicYearDto2 );
    }
    
    @Test
    public void testHashCode_Same() {
        AcademicYearDto academicYearDto1 = new AcademicYearDto(academicYear1)
        AcademicYearDto academicYearDto2 = new AcademicYearDto(academicYear1)
        assertEquals( academicYearDto1.hashCode(), academicYearDto2.hashCode() );
    }
    
    @Test
    public void testHashCode_Different() {
        AcademicYearDto academicYearDto1 = new AcademicYearDto(academicYear1)
        AcademicYearDto academicYearDto2 = new AcademicYearDto(academicYear2)
        assertNotEquals( academicYearDto1.hashCode(), academicYearDto2.hashCode() );
    }
    
    @Test
    public void testMapToAcademicYearEntity() {
        AcademicYearDto academicYearDto = new AcademicYearDto( academicYear1.id, academicYear1.code, academicYear1.description, academicYear1.startDate, academicYear1.endDate )
        AcademicYear academicYear = AcademicYearDto.mapToEntity( academicYearDto )
        assertEquals( academicYear.id, academicYear1.id );
        assertEquals( academicYear.code, academicYear1.code );
        assertEquals( academicYear.description, academicYear1.description );
        assertEquals( academicYear.startDate, academicYear1.startDate );
        assertEquals( academicYear.endDate, academicYear1.endDate );
    }
    
    @Test
    public void testConstructor_AcademicYear() {
        AcademicYearDto academicYear = new AcademicYearDto( academicYear1)
        assertEquals( academicYear.id, academicYear1.id );
        assertEquals( academicYear.code, academicYear1.code );
        assertEquals( academicYear.description, academicYear1.description );
        assertEquals( academicYear.startDate, academicYear1.startDate );
        assertEquals( academicYear.endDate, academicYear1.endDate );
    }
}
