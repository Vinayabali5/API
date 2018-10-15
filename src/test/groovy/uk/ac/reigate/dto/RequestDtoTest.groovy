package uk.ac.reigate.dto;

import static org.junit.Assert.*
import org.junit.Before

import java.util.List;

import org.junit.Before;
import org.junit.Test

import uk.ac.reigate.domain.admissions.Request




public class RequestDtoTest {
    
    private Request request1
    
    private Request request2
    
    private List<Request> requests
    
    @Before
    public void setup() {
        request1 = new Request(
                id: 1,
                student: null,
                request:'Conditional',
                coreAim: false,
                broadeningSubject: true,
                chosenAgainstAdvice: false,
                allocated: true
                );
        request2 = new Request(
                id: 2,
                student: null,
                request:'Normal',
                coreAim: false,
                broadeningSubject: true,
                chosenAgainstAdvice: false,
                allocated: true
                );
        requests = Arrays.asList(request1, request2);
    }
    
    @Test
    public void testMapFromRequestEntity(){
        RequestDto requestTest = RequestDto.mapFromRequestEntity( request1 )
        assertEquals( requestTest.id, request1.id );
        assertEquals( requestTest.studentId, request1.student);
        assertEquals( requestTest.request, request1.request);
        assertEquals( requestTest.coreAim, request1.coreAim);
        assertEquals( requestTest.broadeningSubject, request1.broadeningSubject);
        assertEquals( requestTest.chosenAgainstAdvice, request1.chosenAgainstAdvice);
        assertEquals( requestTest.allocated, request1.allocated);
    }
    
    @Test
    public void testMapFromRequestsEntities(){
        List<RequestDto> requestsDtoTest = RequestDto.mapFromRequestsEntities( requests )
        assertEquals( requestsDtoTest[0].id, request1.id );
        assertEquals( requestsDtoTest[0].studentId, request1.student);
        assertEquals( requestsDtoTest[0].request, request1.request);
        assertEquals( requestsDtoTest[1].id, request2.id );
        assertEquals( requestsDtoTest[1].studentId, request2.student);
        assertEquals( requestsDtoTest[1].request, request2.request);
    }
    
    @Test
    public void testMapToRequestEntity(){
        RequestDto requestDto = new RequestDto(request1.id, request1.student, request1.request, request1.academicYear, request1.coreAim, request1.broadeningSubject, request1.chosenAgainstAdvice, request1.allocated);
        Request request = RequestDto.mapToRequestEntity( requestDto, null, null );
        assertEquals( request.id, request1.id );
        assertEquals( request.student, request1.student);
        assertEquals( request.request, request1.request);
    }
    
    @Test
    public void testEquals_Same() {
        RequestDto requestDto1 = new RequestDto(request1.id, request1.student, request1.request, request1.academicYear, request1.coreAim, request1.broadeningSubject, request1.chosenAgainstAdvice, request1.allocated);
        RequestDto requestDto2 = new RequestDto(request1.id, request1.student, request1.request, request1.academicYear, request1.coreAim, request1.broadeningSubject, request1.chosenAgainstAdvice, request1.allocated);
        assertEquals(requestDto1, requestDto2)
    }
    
    @Test
    public void testEquals_Different() {
        RequestDto requestDto1 = new RequestDto(request1.id, request1.student, request1.request, request1.academicYear, request1.coreAim, request1.broadeningSubject, request1.chosenAgainstAdvice, request1.allocated);
        RequestDto requestDto2 = new RequestDto(request2.id, request2.student, request2.request, request2.academicYear, request2.coreAim, request2.broadeningSubject, request2.chosenAgainstAdvice, request2.allocated);
        assertNotEquals(requestDto1, requestDto2)
    }
    
    @Test
    public void testHashCode_Same() {
        RequestDto requestDto1 = new RequestDto(request1.id, request1.student, request1.request, request1.academicYear, request1.coreAim, request1.broadeningSubject, request1.chosenAgainstAdvice, request1.allocated);
        RequestDto requestDto2 = new RequestDto(request1.id, request1.student, request1.request, request1.academicYear, request1.coreAim, request1.broadeningSubject, request1.chosenAgainstAdvice, request1.allocated);
        assertEquals(requestDto1.hashCode(), requestDto2.hashCode())
    }
    
    @Test
    public void testHashCode_Different() {
        RequestDto requestDto1 = new RequestDto(request1.id, request1.student, request1.request, request1.academicYear, request1.coreAim, request1.broadeningSubject, request1.chosenAgainstAdvice, request1.allocated);
        RequestDto requestDto2 = new RequestDto(request2.id, request2.student, request2.request, request2.academicYear, request2.coreAim, request2.broadeningSubject, request2.chosenAgainstAdvice, request2.allocated);
        assertNotEquals(requestDto1.hashCode(), requestDto2.hashCode())
    }
    
    @Test
    public void testConstructor_Request() {
        RequestDto requestTest = new RequestDto(request1)
        assertEquals( requestTest.id, request1.id );
        assertEquals( requestTest.studentId, request1.student);
        assertEquals( requestTest.request, request1.request);
        assertEquals( requestTest.coreAim, request1.coreAim);
        assertEquals( requestTest.broadeningSubject, request1.broadeningSubject);
        assertEquals( requestTest.chosenAgainstAdvice, request1.chosenAgainstAdvice);
        assertEquals( requestTest.allocated, request1.allocated);
    }
}
