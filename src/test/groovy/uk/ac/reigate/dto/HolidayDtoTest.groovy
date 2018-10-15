package uk.ac.reigate.dto;

import static org.junit.Assert.*

import org.junit.Before
import org.junit.Test
import uk.ac.reigate.domain.academic.AcademicYear;
import uk.ac.reigate.domain.academic.Holiday

public class HolidayDtoTest {
    
    private AcademicYear year
    
    private Holiday holiday1
    
    private Holiday holiday2
    
    private List<Holiday> holidays
    
    @Before
    public void setup() {
        this.year = new AcademicYear(1, '11', '2011', new Date().parse('yyyy/MM/dd', '2011/11/11'), new Date().parse('yyyy/MM/dd', '2016/11/11'))
        this.holiday1 = new Holiday(
                id: 1,
                year: year,
                description: 'Academic Year 15/16',
                startDate: new Date().parse('yyyy/MM/dd', '2011/07/09'),
                endDate: new Date().parse('yyyy/MM/dd', '2013/07/09'),
                halfTerm: true
                );
        this.holiday2 = new Holiday(
                id: 2,
                year: year,
                description: 'Academic Year 15/15',
                startDate: new Date().parse('yyyy/MM/dd', '2011/08/09'),
                endDate: new Date().parse('yyyy/MM/dd', '2013/08/09'),
                halfTerm: true
                );
        this.holidays = Arrays.asList(this.holiday1, this.holiday2);
    }
    
    HolidayDto generateHolidayDto() {
        return generateHoliday1Dto()
    }
    
    HolidayDto generateHoliday1Dto() {
        return new HolidayDto(holiday1.id, holiday1.year.id, holiday1.description, holiday1.startDate, holiday1.endDate, holiday1.halfTerm)
    }
    
    HolidayDto generateHoliday2Dto() {
        return new HolidayDto(holiday2.id, holiday2.year.id, holiday2.description, holiday2.startDate, holiday2.endDate, holiday2.halfTerm)
    }
    
    @Test
    public void testMapFromHolidayEntity() {
        HolidayDto holidayTest = HolidayDto.mapFromHolidayEntity( holiday1 );
        assertEquals( holidayTest.id, holiday1.id);
        assertEquals( holidayTest.yearId, holiday1.year.id);
        assertEquals( holidayTest.description, holiday1.description);
        assertEquals( holidayTest.startDate, holiday1.startDate);
        assertEquals( holidayTest.endDate, holiday1.endDate);
    }
    
    @Test
    public void testMapFromHolidaysEntities(){
        List<HolidayDto> holidayTest = HolidayDto.mapFromHolidaysEntities( holidays )
        assertEquals( holidayTest [0].id, holiday1.id);
        assertEquals( holidayTest [0].yearId, holiday1.year.id);
        assertEquals( holidayTest [0].description, holiday1.description);
        assertEquals( holidayTest [0].startDate, holiday1.startDate);
        assertEquals( holidayTest [0].endDate, holiday1.endDate);
        assertEquals( holidayTest [1].id, holiday2.id );
        assertEquals( holidayTest [1].yearId,  holiday2.year.id);
        assertEquals( holidayTest [1].description, holiday2.description );
        assertEquals( holidayTest [1].startDate, holiday2.startDate);
        assertEquals( holidayTest [1].endDate, holiday2.endDate);
    }
    
    @Test
    public void testMapToHolidayEntity(){
        HolidayDto holidayDto = generateHolidayDto()
        Holiday holiday = HolidayDto.mapToHolidayEntity( holidayDto, year );
        assertEquals( holiday.id, holiday1.id );
        assertEquals( holiday.year, holiday1.year );
        assertEquals( holiday.description, holiday1.description );
        assertEquals( holiday.startDate, holiday1.startDate );
        assertEquals( holiday.endDate, holiday1.endDate );
    }
    
    @Test
    public void testEquals_Same() {
        HolidayDto holidayDto1 = new HolidayDto(holiday1.id, holiday1.year.id, holiday1.description, holiday1.startDate, holiday1.endDate, holiday1.halfTerm)
        HolidayDto holidayDto2 = new HolidayDto(holiday1.id, holiday1.year.id, holiday1.description, holiday1.startDate, holiday1.endDate, holiday1.halfTerm)
        assertEquals(holidayDto1, holidayDto2)
    }
    
    @Test
    public void testEquals_Different() {
        HolidayDto holidayDto1 = new HolidayDto(holiday1.id, holiday1.year.id, holiday1.description, holiday1.startDate, holiday1.endDate, holiday1.halfTerm)
        HolidayDto holidayDto2 = new HolidayDto(holiday2.id, holiday2.year.id, holiday2.description, holiday2.startDate, holiday2.endDate, holiday2.halfTerm)
        assertNotEquals(holidayDto1, holidayDto2)
    }
    
    @Test
    public void testHashCode_Same() {
        HolidayDto holidayDto1 = new HolidayDto(holiday1.id, holiday1.year.id, holiday1.description, holiday1.startDate, holiday1.endDate, holiday1.halfTerm)
        HolidayDto holidayDto2 = new HolidayDto(holiday1.id, holiday1.year.id, holiday1.description, holiday1.startDate, holiday1.endDate, holiday1.halfTerm)
        assertEquals(holidayDto1.hashCode(), holidayDto2.hashCode())
    }
    
    @Test
    public void testHashCode_Different() {
        HolidayDto holidayDto1 = new HolidayDto(holiday1.id, holiday1.year.id, holiday1.description, holiday1.startDate, holiday1.endDate, holiday1.halfTerm)
        HolidayDto holidayDto2 = new HolidayDto(holiday2.id, holiday2.year.id, holiday2.description, holiday2.startDate, holiday2.endDate, holiday2.halfTerm)
        assertNotEquals(holidayDto1.hashCode(), holidayDto2.hashCode())
    }
    
    @Test
    public void testConstructor_Holiday(){
        HolidayDto holidayDto = generateHolidayDto()
        Holiday holiday = HolidayDto.mapToHolidayEntity( holidayDto, year );
        assertEquals( holiday.id, holiday1.id );
        assertEquals( holiday.year, holiday1.year );
        assertEquals( holiday.description, holiday1.description );
        assertEquals( holiday.startDate, holiday1.startDate );
        assertEquals( holiday.endDate, holiday1.endDate );
    }
}
