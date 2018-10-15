package uk.ac.reigate.dto;

import static org.junit.Assert.*
import org.junit.Before
import org.junit.Test
import uk.ac.reigate.domain.academic.InterimReport
import uk.ac.reigate.domain.academic.AcademicYear;



public class InterimReportDtoTest {
    
    private InterimReport interimReport1
    
    private InterimReport interimReport2
    
    private List<InterimReport> interimReports
    
    AcademicYear createAcademicYear() {
        AcademicYear year = new AcademicYear()
    }
    
    @Before
    public void setup() {
        interimReport1 = new InterimReport(
                id: 1,
                year: createAcademicYear(),
                code:'M',
                description:'MAth',
                startDate : new Date().parse('yyyy/MM/dd', '2015/09/01'),
                endDate : new Date().parse('yyyy/MM/dd', '2015/09/01'),
                publishDate : new Date().parse('yyyy/MM/dd', '2015/09/01'),
                active: true
                );
        interimReport2 = new InterimReport(
                id: 2,
                year: createAcademicYear(),
                code:'M',
                description:'MAth',
                startDate : new Date().parse('yyyy/MM/dd', '2015/09/01'),
                endDate : new Date().parse('yyyy/MM/dd', '2015/09/01'),
                publishDate : new Date().parse('yyyy/MM/dd', '2015/09/01'),
                active: true
                );
        interimReports = Arrays.asList(interimReport1, interimReport2);
    }
    
    @Test
    public void testMapFromInterimReportEntity(){
        InterimReportDto interimReportTest = InterimReportDto.mapFromInterimReportEntity( interimReport1 )
        assertEquals( interimReportTest.id, interimReport1.id );
        assertEquals( interimReportTest.code, interimReport1.code);
        assertEquals( interimReportTest.description, interimReport1.description);
        assertEquals( interimReportTest.startDate, interimReport1.startDate)
        assertEquals( interimReportTest.endDate, interimReport1.endDate)
        assertEquals( interimReportTest.yearId, interimReport1.year.id)
        assertEquals( interimReportTest.publishDate, interimReport1.publishDate)
    }
    
    @Test
    public void testMapFromInterimReportsEntities(){
        List<InterimReportDto> interimReportsDtoTest = InterimReportDto.mapFromInterimReportsEntities( interimReports )
        assertEquals( interimReportsDtoTest[0].id, interimReport1.id );
        assertEquals( interimReportsDtoTest[0].code, interimReport1.code);
        assertEquals( interimReportsDtoTest[0].description, interimReport1.description);
        assertEquals( interimReportsDtoTest[0].startDate, interimReport1.startDate);
        assertEquals( interimReportsDtoTest[0].endDate, interimReport1.endDate);
        assertEquals( interimReportsDtoTest[0].yearId, interimReport1.year.id);
        assertEquals( interimReportsDtoTest[0].publishDate, interimReport1.publishDate);
        assertEquals( interimReportsDtoTest[1].id, interimReport2.id );
        assertEquals( interimReportsDtoTest[1].code, interimReport2.code);
        assertEquals( interimReportsDtoTest[1].description, interimReport2.description);
        assertEquals( interimReportsDtoTest[1].startDate, interimReport2.startDate);
        assertEquals( interimReportsDtoTest[1].endDate, interimReport2.endDate);
        assertEquals( interimReportsDtoTest[1].yearId, interimReport2.year.id);
        assertEquals( interimReportsDtoTest[1].publishDate, interimReport2.publishDate);
    }
    
    @Test
    public void testEquals_Same() {
        InterimReportDto interimReportDto1 = new InterimReportDto(interimReport1.id, interimReport1.code, interimReport1.description, interimReport1.year, interimReport1.startDate, interimReport1.endDate, interimReport1.publishDate, interimReport1.active )
        InterimReportDto interimReportDto2 = new InterimReportDto(interimReport1.id, interimReport1.code, interimReport1.description, interimReport1.year, interimReport1.startDate, interimReport1.endDate, interimReport1.publishDate, interimReport1.active )
        assertEquals(interimReportDto1, interimReportDto2)
    }
    
    @Test
    public void testEquals_Different() {
        InterimReportDto interimReportDto1 = new InterimReportDto(interimReport1.id, interimReport1.code, interimReport1.description, interimReport1.year, interimReport1.startDate, interimReport1.endDate, interimReport1.publishDate, interimReport1.active )
        InterimReportDto interimReportDto2 = new InterimReportDto(interimReport2.id, interimReport2.code, interimReport2.description, interimReport2.year, interimReport2.startDate, interimReport2.endDate, interimReport2.publishDate, interimReport2.active )
        assertNotEquals(interimReportDto1, interimReportDto2)
    }
    
    @Test
    public void testHashCode_Same() {
        InterimReportDto interimReportDto1 = new InterimReportDto(interimReport1.id, interimReport1.code, interimReport1.description, interimReport1.year, interimReport1.startDate, interimReport1.endDate, interimReport1.publishDate, interimReport1.active )
        InterimReportDto interimReportDto2 = new InterimReportDto(interimReport1.id, interimReport1.code, interimReport1.description, interimReport1.year, interimReport1.startDate, interimReport1.endDate, interimReport1.publishDate, interimReport1.active )
        assertEquals(interimReportDto1.hashCode(), interimReportDto2.hashCode())
    }
    
    @Test
    public void testHashCode_Different() {
        InterimReportDto interimReportDto1 = new InterimReportDto(interimReport1.id, interimReport1.code, interimReport1.description, interimReport1.year, interimReport1.startDate, interimReport1.endDate, interimReport1.publishDate, interimReport1.active )
        InterimReportDto interimReportDto2 = new InterimReportDto(interimReport2.id, interimReport2.code, interimReport2.description, interimReport2.year, interimReport2.startDate, interimReport2.endDate, interimReport2.publishDate, interimReport2.active )
        assertNotEquals(interimReportDto1.hashCode(), interimReportDto2.hashCode())
    }
    
    @Test
    public void testConstructor_InterimReport() {
        InterimReportDto interimReportTest= new InterimReportDto(interimReport1)
        assertEquals( interimReportTest.code, interimReport1.code);
        assertEquals( interimReportTest.description, interimReport1.description);
        assertEquals( interimReportTest.startDate, interimReport1.startDate)
        assertEquals( interimReportTest.endDate, interimReport1.endDate)
        assertEquals( interimReportTest.yearId, interimReport1.year.id)
        assertEquals( interimReportTest.publishDate, interimReport1.publishDate)
    }
}
