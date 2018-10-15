package uk.ac.reigate.services

import static org.junit.Assert.assertNull
import static org.junit.Assert.assertTrue
import static org.mockito.Mockito.*

import org.junit.Before
import org.junit.Test
import org.mockito.Mockito

import uk.ac.reigate.domain.Person
import uk.ac.reigate.model.SearchResult
import uk.ac.reigate.repositories.PersonRepository

class PersonServiceTest {
    
    private PersonRepository personRepository
    
    private PersonService personService;
    
    Person person1
    Person person2
    
    @Before
    public void setup() {
        this.personRepository = Mockito.mock(PersonRepository.class);
        this.personService = new PersonService(personRepository);
        
        person1 = new Person(id: 1, firstName: 'Michael', surname: 'Horgan', dob: new Date(1980, 7, 6), home: '01737221118')
        person2 = new Person(id: 2, firstName: 'John', surname: 'Smith', dob: new Date(1982, 5, 4), home: '01737242424')
        
        when(personRepository.findAll()).thenReturn([person1, person2]);
        when(personRepository.findOne(1)).thenReturn(person1);
        when(personRepository.save(any(Person.class))).thenReturn(person1);
    }
    
    @Test
    public void testFindPeople() {
        SearchResult<Person> result = personService.findAll();
        assertTrue("results not expected, total " + result.result.size(), result.result.size() == 2);
        verify(personRepository, times(1)).findAll()
        verifyNoMoreInteractions(personRepository)
    }
    
    @Test
    public void testFindPerson() {
        Person result = personService.findById(1);
        verify(personRepository, times(1)).findOne(1)
        verifyNoMoreInteractions(personRepository)
    }
    
    @Test
    public void testSaveNewPerson() {
        person1.id = null
        Person savedPerson = personService.save(person1);
        verify(personRepository, times(1)).save(any(Person.class))
        verifyNoMoreInteractions(personRepository)
    }
    
    @Test
    public void testSavePerson() {
        Person savedPerson = personService.save(person1);
        verify(personRepository, times(1)).save(any(Person.class))
        verifyNoMoreInteractions(personRepository)
    }
}

