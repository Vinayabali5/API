package uk.ac.reigate.services

import static org.junit.Assert.assertNull
import static org.junit.Assert.assertTrue
import static org.mockito.Mockito.*

import org.junit.Before
import org.junit.Test
import org.mockito.Mockito


import uk.ac.reigate.domain.lookup.TutorGroup
import uk.ac.reigate.model.SearchResult
import uk.ac.reigate.repositories.lookup.TutorGroupRepository
import uk.ac.reigate.services.TutorGroupService;


class TutorGroupServiceTest {
    
    private TutorGroupRepository tutorGroupRepository;
    
    private TutorGroupService tutorGroupService;
    
    TutorGroup tutorGroup1
    TutorGroup tutorGroup2
    
    @Before
    public void setup() {
        this.tutorGroupRepository = Mockito.mock(TutorGroupRepository.class);
        this.tutorGroupService = new TutorGroupService(tutorGroupRepository);
        
        tutorGroup1 = new TutorGroup(id: 1, code: 'M', description: 'Maths')
        tutorGroup2 = new TutorGroup(id: 2, code: 'F', description: 'Fashion')
        
        when(tutorGroupRepository.findAll()).thenReturn([tutorGroup1, tutorGroup2]);
        when(tutorGroupRepository.findOne(1)).thenReturn(tutorGroup1);
        when(tutorGroupRepository.findOne(null)).thenThrow(new IllegalArgumentException());
        when(tutorGroupRepository.save(any(TutorGroup.class))).thenReturn(tutorGroup1);
    }
    
    @Test
    public void testFindTutorGroup() {
        List<TutorGroup> result = tutorGroupService.findAll();
        verify(tutorGroupRepository, times(1)).findAll()
        verifyNoMoreInteractions(tutorGroupRepository)
    }
    
    @Test
    public void testFindTutorGroupById() {
        TutorGroup result = tutorGroupService.findById(1);
        verify(tutorGroupRepository, times(1)).findOne(1)
        verifyNoMoreInteractions(tutorGroupRepository)
    }
    
    @Test
    public void testSaveNewTutorGroup() {
        tutorGroup1.id = null
        tutorGroupService.save(tutorGroup1);
        verify(tutorGroupRepository, times(1)).save(tutorGroup1)
        verifyNoMoreInteractions(tutorGroupRepository)
    }
    
    @Test
    public void testSaveTutorGroup() {
        tutorGroupService.save(tutorGroup1);
        verify(tutorGroupRepository, times(1)).save(tutorGroup1)
        verifyNoMoreInteractions(tutorGroupRepository)
    }
}