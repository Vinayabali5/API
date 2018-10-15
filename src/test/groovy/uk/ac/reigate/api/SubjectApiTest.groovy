package uk.ac.reigate.api;

import static org.hamcrest.Matchers.*
import static org.junit.Assert.*
import static org.mockito.Mockito.*
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.*
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.MediaType
import org.springframework.test.web.servlet.MockMvc
import org.springframework.web.context.WebApplicationContext

import uk.ac.reigate.domain.lookup.Subject
import uk.ac.reigate.model.SearchResult
import uk.ac.reigate.repositories.lookup.SubjectRepository
import uk.ac.reigate.services.SubjectService


//@RunWith(SpringJUnit4ClassRunner.class)
////@ActiveProfiles("test")
//@ContextConfiguration(classes=[WebAppContextConfig.class, TestMockServiceConfig.class, TestMockRepositoryConfig.class])
//@WebAppConfiguration
public class SubjectApiTest {
    
    //    private MediaType contentType = new MediaType(MediaType.APPLICATION_JSON.getType(), MediaType.APPLICATION_JSON.getSubtype(), Charset.forName("utf8"));
    
    private MockMvc mockMvc;
    
    @Autowired
    private SubjectRepository subjectRepoisitory
    
    @Autowired
    private SubjectService subjectService
    
    @Autowired
    private WebApplicationContext webApplicationContext;
    
    //    Subject subject1
    //    Subject subject2
    //    SearchResult<Subject> subjectList
    
    //    @Before
    public void init()  {
        this.mockMvc = webAppContextSetup(webApplicationContext).build();
        
        //mockMvc = MockMvcBuilders.webAppContextSetup(this.wac).build();
        
        //MockitoAnnotations.initMocks(this);
        //reset(subjectService)
        //subjectService = mock(SubjectService.class);
        //subjectService = new SubjectService(subjectRepoisitory)
        
        subject1 = new Subject(1, 'MA', 'Maths')
        subject2 = new Subject(2, 'EN', 'English')
        
        subjectList = new SearchResult<Subject>(2L, [subject1, subject2])
        
        when(subjectService.findAll()).thenReturn(subjectList)
        when(subjectService.findById(1)).thenReturn(subject1);
        when(subjectService.findById(99)).thenReturn(null);
    }
    
    //    @Test
    void testFindSubject() throws Exception {
        mockMvc.perform(get("/api/subjects/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath('$.id').value(1))
                .andExpect(jsonPath('$.code').value('MA'))
                .andExpect(jsonPath('$.description').value('Maths'))
    }
    
    //@Test
    void testFindSubject_NotFound() throws Exception {
        mockMvc.perform(get("/api/subjects/1"))
                .andExpect(status().isNotFound())
                .andExpect(jsonPath('$.code').value(404))
                .andExpect(jsonPath('$.message').value('Not Found'))
    }
    
    //@Test
    void testFindSubjects() throws Exception {
        
        mockMvc.perform(
                get("/api/subjects")
                .accept(MediaType.APPLICATION_JSON)
                )
                .andExpect(status().isOk())
                .andExpect(content().contentType("application/json"))
                .andExpect(jsonPath('$.', hasSize(2)))
                .andExpect(jsonPath('$.[0].code').value('T1'))
                .andExpect(jsonPath('$.[0].description').value('Test 1'))
    }
    
    //@Test
    void testSaveSubject() throws Exception {
        String json = '{ "code": "T1", "description": "Test 1" }'
        Subject saved = new Subject(1, 'T1', 'Test 1')
        
        mockMvc.perform(
                post("/api/subjects")
                .contentType(MediaType.APPLICATION_JSON)
                .content(json)
                .accept(MediaType.APPLICATION_JSON)
                )
                .andExpect(status().isCreated())
                .andExpect(content().contentType("application/json"))
                .andExpect(jsonPath('$.id').value(1))
                .andExpect(jsonPath('$.code').value('T1'))
                .andExpect(jsonPath('$.description').value('Test 1'))
        
        verify(subjectService, times(4)).save(any())
    }
    
    //@Test
    void testUpdateSubject() throws Exception {
        String json = '{ "id": 2, "description": "Test", "code": "TE" }'
        mockMvc.perform(
                put("/api/subjects/2")
                .contentType(MediaType.APPLICATION_JSON)
                .content(json)
                .accept(MediaType.APPLICATION_JSON)
                )
                .andExpect(status().isOk())
                .andExpect(content().contentType("application/json;charset=UTF-8"))
                .andExpect(jsonPath('$.id').value(2))
                .andExpect(jsonPath('$.code').value('TE'))
                .andExpect(jsonPath('$.description').value('Test'))
    }
}
