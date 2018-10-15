package uk.ac.reigate.services

import static org.junit.Assert.assertNull
import static org.junit.Assert.assertTrue
import static org.mockito.Mockito.*

import org.junit.Before
import org.junit.Test
import org.mockito.Mockito

import uk.ac.reigate.domain.register.AttendanceCode;
import uk.ac.reigate.model.SearchResult
import uk.ac.reigate.repositories.register.AttendanceCodeRepository
import uk.ac.reigate.services.AttendanceCodeService;


class AttendanceCodeServiceTest {
    
    private AttendanceCodeRepository attendanceCodeRepository
    
    private AttendanceCodeService attendanceCodeService;
    
    AttendanceCode attendanceCode1
    AttendanceCode attendanceCode2
    
    @Before
    public void setup() {
        this.attendanceCodeRepository = Mockito.mock(AttendanceCodeRepository.class);
        this.attendanceCodeService = new AttendanceCodeService(attendanceCodeRepository);
        
        attendanceCode1 = new AttendanceCode(id: 1, code: 'A', description: 'Absent', registerMark: 'A', absence: true, authorisedAbsence: false, late: true, included: true, authorisedLate: false, lastDateOfAttendance: true, htmlColour: "red", accessColour: "r")
        attendanceCode2 = new AttendanceCode(id: 2, code: 'P', description: 'Present', registerMark: 'A', absence: true, authorisedAbsence: false, late: true, included: true, authorisedLate: false, lastDateOfAttendance: true, htmlColour: "red", accessColour: "r")
        
        when(attendanceCodeRepository.findAll()).thenReturn([
            attendanceCode1,
            attendanceCode2
        ]);
        when(attendanceCodeRepository.findOne(1)).thenReturn(attendanceCode1);
        when(attendanceCodeRepository.findOne(null)).thenThrow(new IllegalArgumentException());
        when(attendanceCodeRepository.save(any(AttendanceCode.class))).thenReturn(attendanceCode1);
    }
    
    @Test
    public void testFindAttendanceCodes() {
        List<AttendanceCode> result = attendanceCodeService.findAll();
        verify(attendanceCodeRepository, times(1)).findAll()
        verifyNoMoreInteractions(attendanceCodeRepository)
    }
    
    @Test
    public void testFindAttendanceCode() {
        AttendanceCode result = attendanceCodeService.findById(1);
        verify(attendanceCodeRepository, times(1)).findOne(1)
        verifyNoMoreInteractions(attendanceCodeRepository)
    }
    
    @Test
    public void testSaveNewAttendanceCode() {
        AttendanceCode savedAttendanceCode = attendanceCodeService.save(attendanceCode1);
        verify(attendanceCodeRepository, times(1)).save(any())
        verifyNoMoreInteractions(attendanceCodeRepository)
    }
    
    @Test
    public void testSaveAttendanceCodes() {
        List<AttendanceCode> savedAttendanceCodes = attendanceCodeService.saveAttendanceCodes([
            attendanceCode1,
            attendanceCode2
        ]);
        verify(attendanceCodeRepository, times(2)).save(any(AttendanceCode.class))
        verifyNoMoreInteractions(attendanceCodeRepository)
    }
    
    @Test
    public void testSaveAttendanceCodeByFields_WithNullId() {
        AttendanceCode savedAttendanceCode = attendanceCodeService.saveAttendanceCode(null, attendanceCode1.code, attendanceCode1.description, attendanceCode1.registerMark, attendanceCode1.absence, attendanceCode1.authorisedAbsence, attendanceCode1.late,  attendanceCode1.authorisedLate, attendanceCode1.included, attendanceCode1.lastDateOfAttendance, attendanceCode1.htmlColour, attendanceCode1.accessColour);
        verify(attendanceCodeRepository, times(1)).save(any())
        verifyNoMoreInteractions(attendanceCodeRepository)
    }
    
    
    @Test
    public void testSaveAttendanceCodeByFields_WithId() {
        AttendanceCode savedAttendanceCode = attendanceCodeService.saveAttendanceCode(1, attendanceCode2.code, attendanceCode2.description, attendanceCode2.registerMark, attendanceCode2.absence, attendanceCode2.authorisedAbsence, attendanceCode2.late,  attendanceCode2.authorisedLate, attendanceCode2.included, attendanceCode2.lastDateOfAttendance, attendanceCode2.htmlColour, attendanceCode2.accessColour);
        verify(attendanceCodeRepository, times(1)).findOne(1)
        verify(attendanceCodeRepository, times(1)).save(any())
        verifyNoMoreInteractions(attendanceCodeRepository)
    }
}

