package uk.ac.reigate.services;

import static org.junit.Assert.assertNull
import static org.junit.Assert.assertTrue
import static org.mockito.Mockito.*

import org.junit.Before
import org.junit.Test
import org.mockito.Mockito
import uk.ac.reigate.domain.cristal.MasterRegister
import uk.ac.reigate.domain.academic.Faculty;
import uk.ac.reigate.domain.lookup.ContactType
import uk.ac.reigate.model.SearchResult
import uk.ac.reigate.repositories.cristal.MasterRegisterRepository;
import uk.ac.reigate.services.MasterRegisterService;


public class MasterRegisterServiceTest {
    
    private MasterRegisterRepository masterRegisterRepository;
    
    private MasterRegisterService masterRegisterService;
    
    MasterRegister masterRegister1
    
    MasterRegister masterRegister2
    
    @Before
    public void setup() {
        this.masterRegisterRepository = mock(MasterRegisterRepository.class);
        this.masterRegisterService = new MasterRegisterService(masterRegisterRepository);
        
        masterRegister1 = new MasterRegister(id: 1, sessionRef: 10,  subjectCode: 'Arts MasterRegister', group:'A')
        masterRegister2 = new MasterRegister(id: 2, sessionRef: 11,  subjectCode: 'Maths MasterRegister', group:'B')
        
        when(masterRegisterRepository.findAll()).thenReturn([
            masterRegister1,
            masterRegister2
        ]);
        when(masterRegisterRepository.findOne(1)).thenReturn(masterRegister1);
        when(masterRegisterRepository.findOne(null)).thenThrow(new IllegalArgumentException());
        when(masterRegisterRepository.save(any(MasterRegister.class))).thenReturn(masterRegister1);
    }
    
    
    @Test
    public void testFindMasterRegister() {
        List<MasterRegister> result = masterRegisterService.findAll();
        verify(masterRegisterRepository, times(1)).findAll()
        verifyNoMoreInteractions(masterRegisterRepository)
    }
    
    @Test
    public void testFindMasterRegisterById() {
        MasterRegister result = masterRegisterService.findById(1);
        verify(masterRegisterRepository, times(1)).findOne(1)
        verifyNoMoreInteractions(masterRegisterRepository)
    }
    
    @Test
    public void testSaveNewMasterRegister() {
        masterRegister1.id = null
        masterRegisterService.save(masterRegister1);
        verify(masterRegisterRepository, times(1)).save(masterRegister1)
        verifyNoMoreInteractions(masterRegisterRepository)
    }
    
    @Test
    public void testSaveMasterRegister() {
        masterRegisterService.save(masterRegister1);
        verify(masterRegisterRepository, times(1)).save(masterRegister1)
        verifyNoMoreInteractions(masterRegisterRepository)
    }
}


