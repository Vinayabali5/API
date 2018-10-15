package uk.ac.reigate.services

import static org.mockito.Mockito.*

import org.junit.Before
import org.junit.Test
import org.mockito.Mockito

import uk.ac.reigate.domain.lookup.YearGroup
import uk.ac.reigate.model.SearchResult
import uk.ac.reigate.repositories.lookup.YearGroupRepository


class YearGroupServiceTest {
    
    private YearGroupRepository yearGroupRepository;
    
    private YearGroupService yearGroupService;
    
    YearGroup yearGroup1
    YearGroup yearGroup2
    
    @Before
    public void setup() {
        this.yearGroupRepository = Mockito.mock(YearGroupRepository.class);
        this.yearGroupService = new YearGroupService(yearGroupRepository);
        
        yearGroup1 = new YearGroup(id: 1, code: '15', description: '2015')
        yearGroup2 = new YearGroup(id: 2, code: '16', description: '2016')
        
        when(yearGroupRepository.findAll()).thenReturn([yearGroup1, yearGroup2]);
        when(yearGroupRepository.findOne(1)).thenReturn(yearGroup1);
        when(yearGroupRepository.findOne(null)).thenThrow(new IllegalArgumentException());
        when(yearGroupRepository.save(any(YearGroup.class))).thenReturn(yearGroup1);
    }
    
    @Test
    public void testFindYearGroups() {
        List<YearGroup> result = yearGroupService.findAll();
        verify(yearGroupRepository, times(1)).findAll()
        verifyNoMoreInteractions(yearGroupRepository)
    }
    
    @Test
    public void testFindYearGroup() {
        YearGroup result = yearGroupService.findById(1);
        verify(yearGroupRepository, times(1)).findOne(1)
        verifyNoMoreInteractions(yearGroupRepository)
    }
    
    @Test
    public void testSaveNewYearGroup() {
        YearGroup savedYearGroup = yearGroupService.save(yearGroup1);
        verify(yearGroupRepository, times(1)).save(any())
        verifyNoMoreInteractions(yearGroupRepository)
    }
    
    @Test
    public void testSaveYearGroup() {
        YearGroup savedYearGroup = yearGroupService.save(yearGroup1);
        verify(yearGroupRepository, times(1)).save(any())
        verifyNoMoreInteractions(yearGroupRepository)
    }
    
    @Test
    public void testSaveYearGroups() {
        List<YearGroup> savedYearGroups = yearGroupService.saveYearGroups([yearGroup1, yearGroup2]);
        verify(yearGroupRepository, times(2)).save(any(YearGroup.class))
        verifyNoMoreInteractions(yearGroupRepository)
    }
    
    @Test
    public void testSaveYearGroupByFields_WithNullId() {
        YearGroup savedYearGroup = yearGroupService.saveYearGroup(null, yearGroup1.code, yearGroup1.description);
        verify(yearGroupRepository, times(1)).save(any())
        verifyNoMoreInteractions(yearGroupRepository)
    }
    
    
    @Test
    public void testSaveYearGroupByFields_WithId() {
        YearGroup savedYearGroup = yearGroupService.saveYearGroup(1, yearGroup1.code, yearGroup1.description);
        verify(yearGroupRepository, times(1)).findOne(1)
        verify(yearGroupRepository, times(1)).save(any())
        verifyNoMoreInteractions(yearGroupRepository)
    }
}

