package uk.ac.reigate.dto;

import org.junit.Before
import org.junit.Test

import uk.ac.reigate.domain.academic.Timetable

import static org.junit.Assert.*

public class TimetableDtoTest {
    
    private Timetable timetable1
    
    private Timetable timetable2
    
    private List<Timetable> timetables
    
    @Before
    public void setup() {
        timetable1 = new Timetable(
                id: 1,
                courseGroup: null,
                period: null,
                room: null,
                teacher: null,
                validFrom: new Date().parse('yyyy/MM/dd', '2015/07/09'),
                validTo: new Date().parse('yyyy/MM/dd', '2015/07/09')
                );
        timetable2 = new Timetable(
                id: 2,
                courseGroup: null,
                period: null,
                room: null,
                teacher: null,
                validFrom: new Date().parse('yyyy/MM/dd', '2014/07/09'),
                validTo: new Date().parse('yyyy/MM/dd', '2014/07/09')
                );
        timetables = Arrays.asList(timetable1, timetable2);
    }
    
    @Test
    public void testMapFromTimetableEntity(){
        TimetableDto timetableTest = TimetableDto.mapFromEntity( timetable1 )
        assertEquals( timetableTest.id, timetable1.id );
        assertEquals( timetableTest.courseGroupId, timetable1.courseGroup);
        assertEquals( timetableTest.periodId, timetable1.period);
        assertEquals( timetableTest.roomId, timetable1.room);
        assertEquals( timetableTest.teacherId, timetable1.teacher);
        assertEquals( timetableTest.validFrom, timetable1.validFrom);
        assertEquals( timetableTest.validTo, timetable1.validTo);
    }
    
    @Test
    public void testMapFromTimetablesEntities(){
        List<TimetableDto> timetablesDtoTest = TimetableDto.mapFromList( timetables )
        assertEquals( timetablesDtoTest[0].id, timetable1.id );
        assertEquals( timetablesDtoTest[0].courseGroupId, timetable1.courseGroup);
        assertEquals( timetablesDtoTest[0].periodId, timetable1.period);
        assertEquals( timetablesDtoTest[0].roomId, timetable1.room);
        assertEquals( timetablesDtoTest[0].teacherId, timetable1.teacher);
        assertEquals( timetablesDtoTest[0].validFrom, timetable1.validFrom);
        assertEquals( timetablesDtoTest[0].validTo, timetable1.validTo);
        assertEquals( timetablesDtoTest[1].id, timetable2.id );
        assertEquals( timetablesDtoTest[1].courseGroupId, timetable2.courseGroup);
        assertEquals( timetablesDtoTest[1].periodId, timetable2.period);
        assertEquals( timetablesDtoTest[1].roomId, timetable2.room);
        assertEquals( timetablesDtoTest[1].teacherId, timetable2.teacher);
        assertEquals( timetablesDtoTest[1].validFrom, timetable2.validFrom);
        assertEquals( timetablesDtoTest[1].validTo, timetable2.validTo);
    }
    
    @Test
    public void testMapToTimetableEntity(){
        TimetableDto timetableDto = new TimetableDto(timetable1)
        Timetable timetable = TimetableDto.mapToTimetableEntity( timetableDto, null, null, null, null )
        assertEquals( timetable.id, timetable1.id );
        assertEquals( timetable.courseGroup, timetable1.courseGroup);
        assertEquals( timetable.period, timetable1.period);
        assertEquals( timetable.room, timetable1.room);
        assertEquals( timetable.teacher, timetable1.teacher);
        assertEquals( timetable.validFrom, timetable1.validFrom);
        assertEquals( timetable.validTo, timetable1.validTo);
    }
    
    @Test
    public void testEquals_Same() {
        TimetableDto timetableDto1 = new TimetableDto(timetable1);
        TimetableDto timetableDto2 = new TimetableDto(timetable1);
        assertEquals(timetableDto1, timetableDto2)
    }
    
    @Test
    public void testEquals_Different() {
        TimetableDto timetableDto1 = new TimetableDto(timetable1);
        TimetableDto timetableDto2 = new TimetableDto(timetable2);
        assertNotEquals(timetableDto1, timetableDto2)
    }
    
    @Test
    public void testHashCode_Same() {
        TimetableDto timetableDto1 = new TimetableDto(timetable1);
        TimetableDto timetableDto2 = new TimetableDto(timetable1);
        assertEquals(timetableDto1.hashCode(), timetableDto2.hashCode())
    }
    
    @Test
    public void testHashCode_Different() {
        TimetableDto timetableDto1 = new TimetableDto(timetable1);
        TimetableDto timetableDto2 = new TimetableDto(timetable2);
        assertNotEquals(timetableDto1.hashCode(), timetableDto2.hashCode())
    }
    
    @Test
    public void testConstructor_Timetable() {
        TimetableDto timetableTest = new TimetableDto(timetable1)
        assertEquals( timetableTest.id, timetable1.id );
        assertEquals( timetableTest.courseGroupId, timetable1.courseGroup);
        assertEquals( timetableTest.periodId, timetable1.period);
        assertEquals( timetableTest.roomId, timetable1.room);
        assertEquals( timetableTest.teacherId, timetable1.teacher);
        assertEquals( timetableTest.validFrom, timetable1.validFrom);
        assertEquals( timetableTest.validTo, timetable1.validTo);
    }
}
