package uk.ac.reigate.services

import static org.mockito.Mockito.*

import org.junit.Before
import org.junit.Test
import org.mockito.Mockito

import uk.ac.reigate.domain.lookup.Title
import uk.ac.reigate.model.SearchResult
import uk.ac.reigate.repositories.lookup.TitleRepository


class TitleServiceTest {
    
    private TitleRepository titleRepository;
    
    private TitleService titleService;
    
    Title title1
    Title title2
    
    @Before
    public void setup() {
        this.titleRepository = Mockito.mock(TitleRepository.class);
        this.titleService = new TitleService(titleRepository);
        
        title1 = new Title(id: 1, code: 'Mr', description: 'Mr')
        title2 = new Title(id: 2, code: 'Mrs', description: 'Mrs')
        
        when(titleRepository.findAll()).thenReturn([title1, title2]);
        when(titleRepository.findOne(1)).thenReturn(title1);
        when(titleRepository.findOne(null)).thenThrow(new IllegalArgumentException());
        when(titleRepository.save(any(Title.class))).thenReturn(title1);
    }
    
    @Test
    public void testFindTitles() {
        List<Title> result = titleService.findAll();
        verify(titleRepository, times(1)).findAll()
        verifyNoMoreInteractions(titleRepository)
    }
    
    @Test
    public void testFindTitle() {
        Title result = titleService.findById(1);
        verify(titleRepository, times(1)).findOne(1)
        verifyNoMoreInteractions(titleRepository)
    }
    
    @Test
    public void testSaveNewTitle() {
        Title savedTitle = titleService.save(title1);
        verify(titleRepository, times(1)).save(any())
        verifyNoMoreInteractions(titleRepository)
    }
    
    @Test
    public void testSaveTitle() {
        Title savedTitle = titleService.save(title1);
        verify(titleRepository, times(1)).save(any())
        verifyNoMoreInteractions(titleRepository)
    }
    
    @Test
    public void testSaveTitles() {
        List<Title> savedTitles = titleService.saveTitles([title1, title2]);
        verify(titleRepository, times(2)).save(any(Title.class))
        verifyNoMoreInteractions(titleRepository)
    }
    
    @Test
    public void testSaveTitleByFields_WithNullId() {
        Title savedTitle = titleService.saveTitle(null, title1.code, title1.description);
        verify(titleRepository, times(1)).save(any())
        verifyNoMoreInteractions(titleRepository)
    }
    
    
    @Test
    public void testSaveTitleByFields_WithId() {
        Title savedTitle = titleService.saveTitle(1, title1.code, title1.description);
        verify(titleRepository, times(1)).findOne(1)
        verify(titleRepository, times(1)).save(any())
        verifyNoMoreInteractions(titleRepository)
    }
}

