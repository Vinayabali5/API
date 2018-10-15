package uk.ac.reigate.services

import static org.junit.Assert.assertNull
import static org.junit.Assert.assertTrue
import static org.mockito.Mockito.*

import org.junit.Before
import org.junit.Test
import org.mockito.Mockito

import uk.ac.reigate.domain.ilr.ProgrammeType;
import uk.ac.reigate.domain.ilr.ProgrammeType
import uk.ac.reigate.model.SearchResult
import uk.ac.reigate.repositories.ilr.ProgrammeTypeRepository


class ProgrammeTypeServiceTest {
    
    private ProgrammeTypeRepository programmeTypeRepository;
    
    private ProgrammeTypeService programmeTypeService;
    
    ProgrammeType programmeType1;
    ProgrammeType programmeType2;
    
    @Before
    public void setup() {
        this.programmeTypeRepository = Mockito.mock(ProgrammeTypeRepository.class)
        this.programmeTypeService = new ProgrammeTypeService(programmeTypeRepository);
        
        programmeType1 = new ProgrammeType(id: 1, code: 'N', description: 'New', shortDescription: 'New', validFrom: new Date().parse('yyyy/MM/dd', '2011/11/11'), validTo: new Date().parse('yyyy/MM/dd', '2016/11/11'))
        programmeType2 = new ProgrammeType(id: 2, code: 'C', description: 'Complete', shortDescription: 'Complete', validFrom: new Date().parse('yyyy/MM/dd', '2013/11/11'), validTo: new Date().parse('yyyy/MM/dd', '2015/11/11') )
        when(programmeTypeRepository.findAll()).thenReturn([
            programmeType1,
            programmeType2
        ]);
        when(programmeTypeRepository.findOne(1)).thenReturn(programmeType1);
        when(programmeTypeRepository.findOne(null)).thenThrow(new IllegalArgumentException());
        when(programmeTypeRepository.save(any(ProgrammeType.class))).thenReturn(programmeType1);
    }
    
    @Test
    public void testFindProgrammeTypes() {
        List<ProgrammeType> result = programmeTypeService.findAll();
        verify(programmeTypeRepository, times(1)).findAll()
        verifyNoMoreInteractions(programmeTypeRepository)
    }
    
    @Test
    public void testFindProgrammeType() {
        ProgrammeType result = programmeTypeService.findById(1);
        verify(programmeTypeRepository, times(1)).findOne(1)
        verifyNoMoreInteractions(programmeTypeRepository)
    }
    
    @Test
    public void testSaveNewProgrammeType() {
        ProgrammeType savedProgrammeType = programmeTypeService.save(programmeType1);
        verify(programmeTypeRepository, times(1)).save(any())
        verifyNoMoreInteractions(programmeTypeRepository)
    }
    
    @Test
    public void testSaveProgrammeType() {
        ProgrammeType savedProgrammeType = programmeTypeService.save(programmeType1);
        verify(programmeTypeRepository, times(1)).save(any())
        verifyNoMoreInteractions(programmeTypeRepository)
    }
    
    @Test
    public void testSaveProgrammeTypes() {
        List<ProgrammeType> savedProgrammeTypes = programmeTypeService.saveProgrammeTypes([
            programmeType1,
            programmeType2
        ]);
        verify(programmeTypeRepository, times(2)).save(any(ProgrammeType.class))
        verifyNoMoreInteractions(programmeTypeRepository)
    }
    
    @Test
    public void testSaveProgrammeTypeByFields_WithNullId() {
        ProgrammeType savedProgrammeType = programmeTypeService.saveProgrammeType(null, programmeType1.code, programmeType1.description, programmeType1.shortDescription, programmeType1.validFrom, programmeType1.validTo);
        verify(programmeTypeRepository, times(1)).save(any())
        verifyNoMoreInteractions(programmeTypeRepository)
    }
    
    
    @Test
    public void testSaveProgrammeTypeByFields_WithId() {
        ProgrammeType savedProgrammeType = programmeTypeService.saveProgrammeType(1, programmeType1.code, programmeType1.description, programmeType1.shortDescription, programmeType1.validFrom, programmeType1.validTo);
        verify(programmeTypeRepository, times(1)).findOne(1)
        verify(programmeTypeRepository, times(1)).save(any())
        verifyNoMoreInteractions(programmeTypeRepository)
    }
}

