package uk.ac.reigate.services

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

import uk.ac.reigate.domain.academic.Student
import uk.ac.reigate.domain.ilp.Letter
import uk.ac.reigate.domain.ilp.LetterType
import uk.ac.reigate.exceptions.InvalidOperationException
import uk.ac.reigate.repositories.academic.StudentRepository
import uk.ac.reigate.repositories.ilp.LetterRepository
import uk.ac.reigate.repositories.register.AttendanceCodeRepository
import uk.ac.reigate.utils.ValidationUtils

@Service
class LetterService implements ICoreDataService<Letter, Integer>{
    
    @Autowired
    LetterRepository letterRepository
    
    @Autowired
    StudentRepository studentRepository
    
    @Autowired
    AttendanceCodeRepository attendanceCodeRepository
    
    
    /**
     * Default NoArgs constructor
     */
    LetterService() {}
    
    /**
     * Autowired Constructor
     *
     * @param letterRepository
     */
    LetterService(LetterRepository letterRepository) {
        this.letterRepository = letterRepository;
    }
    
    /**
     * Find an individual Letter using the Letter ID fields
     *
     * @param id the ID fields to search for
     * @return the Letter object that matches the ID supplied, or null if not found
     */
    @Override
    @Transactional(readOnly = true)
    Letter findById(Integer id) {
        return letterRepository.findOne(id);
    }
    
    /**
     * Find a single page of Letter objects
     * @return a SearchResult set with the list of Letters
     */
    @Override
    @Transactional(readOnly = true)
    List<Letter> findAll() {
        return letterRepository.findAll();
    }
    
    /**
     *
     * @param id
     * @param code
     * @param description
     * @return
     */
    @Transactional
    public Letter saveLetter(Integer id, Student student, Date requestedDate, Date letterDate, String subject, String re, Boolean reviewMeeting, Boolean attendance, Boolean behaviour, Boolean homework, Boolean punctuality, Boolean focus, Boolean deadlines, Boolean incompleteCoursework, Integer nonEntryWarning, Boolean letterCreated, String notes, LetterType letterType){
        ValidationUtils.assertNotBlank(student, "student cannot be blank");
        Letter letter = null;
        if (id != null) {
            letter = findById(id);
            letter.setStudent(student);
            letter.setRequestedDate(requestedDate);
            letter.setLetterDate(letterDate);
            letter.setSubject(subject);
            letter.setRe(re);
            letter.setReviewMeeting(reviewMeeting);
            letter.setAttendance(attendance);
            letter.setBehaviour(behaviour);
            letter.setHomework(homework);
            letter.setPunctuality(punctuality);
            letter.setFocus(focus);
            letter.setDeadlines(deadlines);
            letter.setIncompleteCoursework(incompleteCoursework);
            letter.setNonEntryWarning(nonEntryWarning);
            letter.setLetterCreated(letterCreated);
            letter.setNotes(notes);
            letter.setLetterType(letterType);
            save(letter);
        } else {
            letter = save(new Letter( student, requestedDate, letterDate, subject,  re, reviewMeeting, attendance, behaviour, homework,  punctuality, focus, deadlines, incompleteCoursework, nonEntryWarning, letterCreated, notes, letterType));
        }
        return letter;
    }
    
    /**
     * This service method is used to save a complete Letter object in the database
     *
     * @param letter the new Letter object to be saved
     * @return the saved version of the Letter object
     */
    @Override
    @Transactional
    public Letter save(Letter letter) {
        return letterRepository.save(letter)
    }
    /**
     * Saves a list of Letter objects to the database
     *
     * @param letters a list of Letters to be saved to the database
     * @return the list of save Letter objects
     */
    @Transactional
    public List<Letter> saveLetters(List<Letter> letters) {
        return letters.collect { letter -> save(letter) };
    }
    
    /**
     * @param studentId
     * @return List of Letter of studentId
     */
    public  List<Letter> getByStudent(Integer studentId){
        return letterRepository.findByStudent_Id(studentId);
    }
    
    /**
     * @param studentId
     * @param letterId
     * @return
     */
    public  Letter getByStudentAndLetter(Integer studentId, Integer letterId){
        return letterRepository.findByStudent_IdAndId(studentId, letterId);
    }
    
    /**
     * This methods throws an InvalidOperationException when called. Letter should not be deleted.
     */
    @Override
    public void delete(Letter obj) {
        throw new InvalidOperationException("Letter should not be deleted")
    }
}
