package uk.ac.reigate.services.search

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service

import com.querydsl.core.types.dsl.BooleanExpression
import com.querydsl.jpa.impl.JPAQuery

import uk.ac.reigate.domain.academic.AcademicYear
import uk.ac.reigate.domain.academic.QStudent
import uk.ac.reigate.domain.academic.QStudentYear
import uk.ac.reigate.domain.academic.Student
import uk.ac.reigate.predicates.StudentPredicates
import uk.ac.reigate.repositories.academic.StudentRepository
import uk.ac.reigate.repositories.search.StudentSearchRepository
import uk.ac.reigate.services.AbstractQueryDslService
import uk.ac.reigate.services.AcademicYearService

@Service
class StudentSearchService extends AbstractQueryDslService {
    
    @Autowired
    AcademicYearService academicYearService
    
    @Autowired
    private StudentSearchRepository studentServiceRepository
    
    @Autowired
    private StudentRepository studentRepository
    
    /**
     * This method is used to search for a list of students in a specified year that match the given criteria. 
     * @param year
     * @param surname
     * @param firstName
     * @param reference
     * @return
     */
    public List<Student> searchByYearSurnameFirstNameAndReference(AcademicYear year, String surname, String firstName, String reference) {
        List<Student> students = studentServiceRepository.findByStudentYears_YearAndPerson_SurnameLikeAndPerson_FirstNameLikeAndReferenceNoLike(year, surname, firstName, reference)
        return students
    }
    
    public List<Student> searchByYearSurnameFirstNameOrPreferredNameAndReference(AcademicYear year, String surname, String firstName, String preferredName, String reference) {
        List<Student> students = studentServiceRepository.findByStudentYears_YearAndPerson_SurnameLikeAndPerson_FirstNameLikeOrPerson_PreferredNameLikeAndReferenceNoLike(year, surname, firstName, preferredName, reference)
        return students
    }
    
    public List<Student> searchByYearCandidateNo(AcademicYear year, Integer candidateNo) {
        List<Student> students = studentServiceRepository.findByStudentYears_YearAndStudentYears_CandidateNo(year, candidateNo)
        return students
    }
    
    public List<Student> searchByYearSurnameName(AcademicYear year, String surname, String firstName, String reference) {
        List<Student>	students = studentServiceRepository.searchStudent(year,surname,firstName,reference)
        return students
    }
    
    /**
     * This method is used to search for a list of students that match the supplied criteria. 
     * 
     * @param year the academicYear to search for students in. If no year is supplied then the default current year will be used.
     * @param studentId the student ID to search for. 
     * @param surname the surname to search for
     * @param forenames 
     * @param candidateNo
     * @param studentTypeMask
     * @param tutorGroupMask
     * @param current
     * @return
     */
    public List<Student> search(AcademicYear year, Integer studentId, String surname, String forenames, Integer candidateNo, String studentTypeMask, String tutorGroupMask, Boolean current) {
        QStudent student = QStudent.student;
        QStudentYear studentYear = QStudentYear.studentYear;
        JPAQuery query = from(student).innerJoin(student.studentYears, studentYear);
        if (year == null) {
            // Set the default year to the current year if none supplied
            year = academicYearService.getCurrentAcademicYear()
        }
        query.where(studentYear.year.eq(year))
        if (studentId != null) {
            query.where(student.id.eq(studentId))
        }
        if (surname != null) {
            query.where(StudentPredicates.anySurnameLike(surname))
        }
        if (forenames != null) {
            query.where(StudentPredicates.anyForenamesLike(forenames))
        }
        if (candidateNo != null) {
            query.where(studentYear.candidateNo.eq(candidateNo))
        }
        if (studentTypeMask != null) {
            query.where(studentYear.studentType.code.like(studentTypeMask))
        }
        if (tutorGroupMask != null) {
            query.where(studentYear.tutorGroup.code.like(tutorGroupMask))
        }
        if (current != null) {
            if (current) {
                query.where(studentYear.endDate.isNull())
            } else {
                query.where(studentYear.endDate.isNotNull())
            }
        }
        return query.fetch()
    }
}
