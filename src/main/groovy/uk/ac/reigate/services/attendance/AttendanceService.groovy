package uk.ac.reigate.services.attendance

import org.springframework.stereotype.Service

import uk.ac.reigate.model.attendance.AttendanceRange
import uk.ac.reigate.model.attendance.OverallAttendance
import uk.ac.reigate.model.attendance.SessionRange

@Service
class AttendanceService {
    
    OverallAttendance getCourseOverallAttendance(int courseId) {
        // #TODO: add the required functionality
    }
    
    AttendanceRange getCourseAttendance(int courseId, SessionRange range) {
        // #TODO: add the required functionality
    }
    
    OverallAttendance getCourseGroupOverallAttendance(int courseId) {
        // #TODO: add the required functionality
    }
    
    AttendanceRange getCourseGroupAttendance(int courseId, SessionRange range) {
        // #TODO: add the required functionality
    }
    
    OverallAttendance getStudentOverallAttendance(int courseId) {
        // #TODO: add the required functionality
    }
    
    AttendanceRange getStudentAttendance(int courseId, SessionRange range) {
        // #TODO: add the required functionality
    }
    
    OverallAttendance getStudentCourseOverallAttendance(int courseId) {
        // #TODO: add the required functionality
    }
    
    AttendanceRange getStudentCourseAttendance(int courseId, SessionRange range) {
        // #TODO: add the required functionality
    }
    
    OverallAttendance getStudentCourseGroupOverallAttendance(int courseId) {
        // #TODO: add the required functionality
    }
    
    AttendanceRange getStudentCourseGroupAttendance(int courseId, SessionRange range) {
        // #TODO: add the required functionality
    }
}
