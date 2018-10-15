package uk.ac.reigate.services

import static org.junit.Assert.assertNull
import static org.junit.Assert.assertTrue
import static org.mockito.Mockito.*

import org.junit.Before
import org.junit.Test
import org.mockito.Mockito

import uk.ac.reigate.domain.register.Register;
import uk.ac.reigate.model.SearchResult
import uk.ac.reigate.repositories.register.RegisterRepository
import uk.ac.reigate.services.RegisterService;


class RegisterServiceTest {
    
    private RegisterRepository registerRepository
    
    private RegisterService registerService;
    
    Register register1
    Register register2
    
    @Before
    public void setup() {
        this.registerRepository = Mockito.mock(RegisterRepository.class);
        this.registerService = new RegisterService(registerRepository);
        
        register1 = new Register(id: 1, completed: true, dateCompleted: new Date(2015, 9, 1))
        register2 = new Register(id: 2, completed: true, dateCompleted: new Date(2015, 9, 1))
        
        when(registerRepository.findAll()).thenReturn([register1, register2]);
        when(registerRepository.findOne(1)).thenReturn(register1);
        when(registerRepository.findOne(null)).thenThrow(new IllegalArgumentException());
        when(registerRepository.save(any(Register.class))).thenReturn(register1);
    }
    
    @Test
    public void testFindRegister() {
        List<Register> result = registerService.findAll();
        verify(registerRepository, times(1)).findAll()
        verifyNoMoreInteractions(registerRepository)
    }
    
    @Test
    public void testFindRegisterById() {
        Register result = registerService.findById(1);
        verify(registerRepository, times(1)).findOne(1)
        verifyNoMoreInteractions(registerRepository)
    }
    
    @Test
    public void testSaveNewRegister() {
        register1.id = null
        registerService.save(register1);
        verify(registerRepository, times(1)).save(register1)
        verifyNoMoreInteractions(registerRepository)
    }
    
    @Test
    public void testSaveRegister() {
        registerService.save(register1);
        verify(registerRepository, times(1)).save(register1)
        verifyNoMoreInteractions(registerRepository)
    }
}

