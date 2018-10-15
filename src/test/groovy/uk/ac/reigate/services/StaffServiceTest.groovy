package uk.ac.reigate.services

import static org.junit.Assert.assertNull
import static org.junit.Assert.assertTrue
import static org.mockito.Mockito.*

import org.junit.Before
import org.junit.Test
import org.mockito.Mockito

import uk.ac.reigate.domain.Address
import uk.ac.reigate.domain.Person
import uk.ac.reigate.domain.Staff
import uk.ac.reigate.domain.lookup.Gender
import uk.ac.reigate.domain.lookup.StaffType
import uk.ac.reigate.domain.lookup.Title
import uk.ac.reigate.model.SearchResult
import uk.ac.reigate.repositories.StaffRepository;
import uk.ac.reigate.services.StaffService;

class StaffServiceTest {
    
    private StaffRepository staffRepository;
    
    private StaffService staffService;
    
    Staff staff1
    
    Staff staff2
    
    @Before
    public void setup() {
        this.staffRepository = mock(StaffRepository.class);
        this.staffService = new StaffService(staffRepository);
        
        staff1 = new Staff(id: 1, initials: 'MAH', knownAs: 'Michael Horgan', onTimetable: true, startDate: new Date(2015, 9, 1), endDate: null, staffRef: 1, staffDetailsId: 2)
        staff2 = new Staff(id: 2, initials: 'VBM', knownAs: 'Vinaya Bali', onTimetable: false, startDate: new Date(2016, 9, 1), endDate: null, staffRef: 2, staffDetailsId: 3 )
        
        when(staffRepository.findAll()).thenReturn([staff1, staff2]);
        when(staffRepository.findOne(1)).thenReturn(staff1);
        when(staffRepository.findOne(null)).thenThrow(new IllegalArgumentException());
        when(staffRepository.save(any(Staff.class))).thenReturn(staff1);
    }
    
    
    @Test
    public void testFindStaff() {
        List<Staff> result = staffService.findAll();
        verify(staffRepository, times(1)).findByType_NeedInitialsTrue()
        verifyNoMoreInteractions(staffRepository)
    }
    
    @Test
    public void testFindStaffById() {
        Staff result = staffService.findById(1);
        verify(staffRepository, times(1)).findOne(1)
        verifyNoMoreInteractions(staffRepository)
    }
    
    @Test
    public void testSaveNewStaff() {
        staff1.id = null
        staffService.save(staff1);
        verify(staffRepository, times(1)).save(staff1)
        verifyNoMoreInteractions(staffRepository)
    }
    
    @Test
    public void testSaveStaff() {
        staffService.save(staff1);
        verify(staffRepository, times(1)).save(staff1)
        verifyNoMoreInteractions(staffRepository)
    }
}

