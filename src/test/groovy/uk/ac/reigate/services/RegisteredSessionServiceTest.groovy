package uk.ac.reigate.services

import static org.junit.Assert.assertNull
import static org.junit.Assert.assertTrue
import static org.mockito.Mockito.*

import org.junit.Before
import org.junit.Test
import org.mockito.Mockito

import uk.ac.reigate.domain.register.RegisteredSession
import uk.ac.reigate.model.SearchResult
import uk.ac.reigate.repositories.academic.RegisteredSessionRepository


class RegisteredSessionServiceTest {
    
    private RegisteredSessionRepository registeredSessionRepository
    
    private RegisteredSessionService registeredSessionService;
    
    RegisteredSession session1
    RegisteredSession session2
    
    @Before
    public void setup() {
        this.registeredSessionRepository = Mockito.mock(RegisteredSessionRepository.class);
        this.registeredSessionService = new RegisteredSessionService(registeredSessionRepository);
        
        session1 = new RegisteredSession(id: 1, date: new Date(2015, 9, 1))
        session2 = new RegisteredSession(id: 2, date: new Date(2011, 1, 1))
        
        when(registeredSessionRepository.findAll()).thenReturn([session1, session2]);
        when(registeredSessionRepository.findOne(1)).thenReturn(session1);
        when(registeredSessionRepository.findOne(null)).thenThrow(new IllegalArgumentException());
        when(registeredSessionRepository.save(any(RegisteredSession.class))).thenReturn(session1);
    }
    
    @Test
    public void testFindSession() {
        List<RegisteredSession> result = registeredSessionService.findAll();
        verify(registeredSessionRepository, times(1)).findAll()
        verifyNoMoreInteractions(registeredSessionRepository)
    }
    
    @Test
    public void testFindSessionById() {
        RegisteredSession result = registeredSessionService.findById(1);
        verify(registeredSessionRepository, times(1)).findOne(1)
        verifyNoMoreInteractions(registeredSessionRepository)
    }
    
    @Test
    public void testSaveNewSession() {
        session1.id = null
        registeredSessionService.save(session1);
        verify(registeredSessionRepository, times(1)).save(session1)
        verifyNoMoreInteractions(registeredSessionRepository)
    }
    
    @Test
    public void testSaveSession() {
        registeredSessionService.save(session1);
        verify(registeredSessionRepository, times(1)).save(session1)
        verifyNoMoreInteractions(registeredSessionRepository)
    }
}

