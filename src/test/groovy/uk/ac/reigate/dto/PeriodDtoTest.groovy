package uk.ac.reigate.dto;

import org.junit.Test
import org.junit.Before
import uk.ac.reigate.domain.academic.Block
import uk.ac.reigate.domain.academic.Period
import uk.ac.reigate.dto.PeriodDto
import static org.junit.Assert.*


public class PeriodDtoTest {
    
    private Block block
    
    private Period period1
    
    private Period period2
    
    private List<Period> periods
    
    @Before
    public void setupTests() {
        this.block = new Block(1, 'A', 'BlockA', '#FAFA50')
        this.period1 = new Period(
                id: 1,
                code:'A',
                description:'A Period',
                block: block,
                startTime: null,
                endTime: null,
                day: 11,
                dayPeriod: 1,
                boxNo: 4,
                cristalPeriod:2
                );
        this.period2 = new Period(
                id: 2,
                code:'B',
                description:'B Period',
                block: block,
                startTime: null,
                endTime: null,
                day: 11,
                dayPeriod: 1,
                boxNo: 42,
                cristalPeriod:21
                );
        this.periods = Arrays.asList(period1, period2);
    }
    
    PeriodDto generatePeriodDto() {
        return generatePeriod1Dto()
    }
    
    PeriodDto generatePeriod1Dto() {
        return new PeriodDto(period1.id, period1.code, period1.description, period1.block.id, period1.startTime, period1.endTime, period1.day, period1.dayPeriod, period1.block.description, period1.boxNo, period1.cristalPeriod)
    }
    
    PeriodDto generatePeriod2Dto() {
        return new PeriodDto(period2.id, period2.code, period2.description, period2.block.id, period2.startTime, period2.endTime, period2.day, period2.dayPeriod, period2.block.description, period2.boxNo, period2.cristalPeriod)
    }
    
    @Test
    public void testMapFromPeriodEntityTest(){
        PeriodDto periodTest = PeriodDto.mapFromPeriodEntity( period1 )
        assertEquals( periodTest.id, period1.id );
        assertEquals( periodTest.code, period1.code);
        assertEquals( periodTest.description, period1.description);
        assertEquals( periodTest.blockId, period1.block.id);
        assertEquals( periodTest.startTime, period1.startTime);
        assertEquals( periodTest.endTime, period1.endTime);
        assertEquals( periodTest.day, period1.day);
        assertEquals( periodTest.dayPeriod, period1.dayPeriod);
    }
    
    @Test
    public void testMapFromPeriodsEntitiesTest(){
        List<PeriodDto> periodTest = PeriodDto.mapFromPeriodsEntities( periods )
        assertEquals( periodTest[0].id, period1.id );
        assertEquals( periodTest[0].code, period1.code);
        assertEquals( periodTest[0].description, period1.description);
        assertEquals( periodTest[0].blockId, period1.block.id);
        assertEquals( periodTest[0].startTime, period1.startTime);
        assertEquals( periodTest[0].endTime, period1.endTime);
        assertEquals( periodTest[0].day, period1.day);
        assertEquals( periodTest[0].dayPeriod, period1.dayPeriod);
        assertEquals( periodTest[1].id, period2.id );
        assertEquals( periodTest[1].code, period2.code);
        assertEquals( periodTest[1].description, period2.description);
        assertEquals( periodTest[1].blockId, period2.block.id);
        assertEquals( periodTest[1].startTime, period2.startTime);
        assertEquals( periodTest[1].endTime, period2.endTime);
        assertEquals( periodTest[1].day, period2.day);
        assertEquals( periodTest[1].dayPeriod, period2.dayPeriod);
    }
    
    @Test
    public void testMapToPeriodEntityTest(){
        PeriodDto periodDto = generatePeriodDto()
        Period periodTest = PeriodDto.mapToPeriodEntity( periodDto, block );
        assertEquals( periodTest.id, period1.id );
        assertEquals( periodTest.code, period1.code);
        assertEquals( periodTest.description, period1.description);
        assertEquals( periodTest.block, period1.block);
        assertEquals( periodTest.startTime, period1.startTime);
        assertEquals( periodTest.endTime, period1.endTime);
        assertEquals( periodTest.day, period1.day);
        assertEquals( periodTest.dayPeriod, period1.dayPeriod);
    }
    
    @Test
    public void testEquals_Same() {
        PeriodDto periodDto1 = new PeriodDto(period1.id, period1.code, period1.description, period1.block.id, period1.startTime, period1.endTime, period1.day, period1.dayPeriod, period1.block.description, period1.boxNo, period1.cristalPeriod)
        PeriodDto periodDto2 = new PeriodDto(period1.id, period1.code, period1.description, period1.block.id, period1.startTime, period1.endTime, period1.day, period1.dayPeriod, period1.block.description, period1.boxNo, period1.cristalPeriod)
        assertEquals(periodDto1, periodDto2)
    }
    
    @Test
    public void testEquals_Different() {
        PeriodDto periodDto1 = new PeriodDto(period1.id, period1.code, period1.description, period1.block.id, period1.startTime, period1.endTime, period1.day, period1.dayPeriod, period1.block.description, period1.boxNo, period1.cristalPeriod)
        PeriodDto periodDto2 = new PeriodDto(period2.id, period2.code, period2.description, period2.block.id, period2.startTime, period2.endTime, period2.day, period2.dayPeriod, period2.block.description, period2.boxNo, period2.cristalPeriod)
        assertNotEquals(periodDto1, periodDto2)
    }
    
    @Test
    public void testHashCode_Same() {
        PeriodDto periodDto1 = new PeriodDto(period1.id, period1.code, period1.description, period1.block.id, period1.startTime, period1.endTime, period1.day, period1.dayPeriod, period1.block.description, period1.boxNo, period1.cristalPeriod)
        PeriodDto periodDto2 = new PeriodDto(period1.id, period1.code, period1.description, period1.block.id, period1.startTime, period1.endTime, period1.day, period1.dayPeriod, period1.block.description, period1.boxNo, period1.cristalPeriod)
        assertEquals(periodDto1.hashCode(), periodDto2.hashCode())
    }
    
    @Test
    public void testHashCode_Different() {
        PeriodDto periodDto1 = new PeriodDto(period1.id, period1.code, period1.description, period1.block.id, period1.startTime, period1.endTime, period1.day, period1.dayPeriod, period1.block.description, period1.boxNo, period1.cristalPeriod)
        PeriodDto periodDto2 = new PeriodDto(period2.id, period2.code, period2.description, period2.block.id, period2.startTime, period2.endTime, period2.day, period2.dayPeriod, period2.block.description, period2.boxNo, period2.cristalPeriod)
        assertNotEquals(periodDto1.hashCode(), periodDto2.hashCode())
    }
}
