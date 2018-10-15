package uk.ac.reigate.services

import static org.mockito.Mockito.*

import org.junit.Before
import org.junit.Test
import org.mockito.Mockito

import uk.ac.reigate.domain.lookup.StaffType
import uk.ac.reigate.model.SearchResult
import uk.ac.reigate.repositories.lookup.StaffTypeRepository


class StaffTypeServiceTest {
    
    private StaffTypeRepository staffTypeRepository;
    
    private StaffTypeService staffTypeService;
    
    StaffType staffType1
    StaffType staffType2
    
    @Before
    public void setup() {
        this.staffTypeRepository = Mockito.mock(StaffTypeRepository.class);
        this.staffTypeService = new StaffTypeService(staffTypeRepository);
        
        staffType1 = new StaffType(id: 1, code: 'S', description: 'Support Staff', needInitials: true)
        staffType2 = new StaffType(id: 2, code: 'P', description: 'Permanent Staff', needInitials:false)
        
        when(staffTypeRepository.findAll()).thenReturn([staffType1, staffType2]);
        when(staffTypeRepository.findOne(1)).thenReturn(staffType1);
        when(staffTypeRepository.findOne(null)).thenThrow(new IllegalArgumentException());
        when(staffTypeRepository.save(any(StaffType.class))).thenReturn(staffType1);
    }
    
    @Test
    public void testFindStaffTypes() {
        List<StaffType> result = staffTypeService.findAll();
        verify(staffTypeRepository, times(1)).findAll()
        verifyNoMoreInteractions(staffTypeRepository)
    }
    
    @Test
    public void testFindStaffType() {
        StaffType result = staffTypeService.findById(1);
        verify(staffTypeRepository, times(1)).findOne(1)
        verifyNoMoreInteractions(staffTypeRepository)
    }
    
    @Test
    public void testSaveNewStaffType() {
        StaffType savedStaffType = staffTypeService.save(staffType1);
        verify(staffTypeRepository, times(1)).save(any())
        verifyNoMoreInteractions(staffTypeRepository)
    }
    
    @Test
    public void testSaveStaffType() {
        StaffType savedStaffType = staffTypeService.save(staffType1);
        verify(staffTypeRepository, times(1)).save(any())
        verifyNoMoreInteractions(staffTypeRepository)
    }
    
    @Test
    public void testSaveStaffTypes() {
        List<StaffType> savedStaffTypes = staffTypeService.saveStaffTypes([staffType1, staffType2]);
        verify(staffTypeRepository, times(2)).save(any(StaffType.class))
        verifyNoMoreInteractions(staffTypeRepository)
    }
    
    @Test
    public void testSaveStaffTypeByFields_WithNullId() {
        StaffType savedStaffType = staffTypeService.saveStaffType(null, staffType1.code, staffType1.description, staffType1.needInitials);
        verify(staffTypeRepository, times(1)).save(any())
        verifyNoMoreInteractions(staffTypeRepository)
    }
    
    
    @Test
    public void testSaveStaffTypeByFields_WithId() {
        StaffType savedStaffType = staffTypeService.saveStaffType(1, staffType1.code, staffType1.description, staffType1.needInitials);
        verify(staffTypeRepository, times(1)).findOne(1)
        verify(staffTypeRepository, times(1)).save(any())
        verifyNoMoreInteractions(staffTypeRepository)
    }
}

