package uk.ac.reigate.services

import static org.junit.Assert.assertNull
import static org.junit.Assert.assertTrue
import static org.mockito.Mockito.*

import org.junit.Before
import org.junit.Test
import org.mockito.Mockito

import uk.ac.reigate.domain.admissions.Request
import uk.ac.reigate.model.SearchResult
import uk.ac.reigate.repositories.admissions.RequestRepository

class RequestServiceTest {
    
    private RequestRepository requestRepository
    
    private RequestService requestService;
    
    Request request1
    Request request2
    
    @Before
    public void setup() {
        this.requestRepository = Mockito.mock(RequestRepository.class);
        this.requestService = new RequestService(requestRepository);
        
        request1 = new Request(id: 1, request: 'Maths', coreAim: false, broadeningSubject: true)
        request2 = new Request(id: 2, request: 'Arts', coreAim: false, broadeningSubject: true)
        
        when(requestRepository.findAll()).thenReturn([request1, request2]);
        when(requestRepository.findOne(1)).thenReturn(request1);
        when(requestRepository.save(any(Request.class))).thenReturn(request1);
    }
    
    @Test
    public void testFindRequests() {
        List<Request> result = requestService.findAll();
        assertTrue("results not expected, total " + result.size(), result.size() == 2);
        verify(requestRepository, times(1)).findAll()
        verifyNoMoreInteractions(requestRepository)
    }
    
    @Test
    public void testFindRequest() {
        Request result = requestService.findById(1);
        verify(requestRepository, times(1)).findOne(1)
        verifyNoMoreInteractions(requestRepository)
    }
    
    @Test
    public void testSaveNewRequest() {
        request1.id = null
        Request savedRequest = requestService.save(request1);
        verify(requestRepository, times(1)).save(any(Request.class))
        verifyNoMoreInteractions(requestRepository)
    }
    
    @Test
    public void testSaveRequest() {
        Request savedRequest = requestService.save(request1);
        verify(requestRepository, times(1)).save(any(Request.class))
        verifyNoMoreInteractions(requestRepository)
    }
}

