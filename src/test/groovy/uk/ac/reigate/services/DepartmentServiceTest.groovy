package uk.ac.reigate.services;

import static org.mockito.Mockito.*

import org.junit.Before
import org.junit.Test

import uk.ac.reigate.domain.academic.Department
import uk.ac.reigate.model.SearchResult
import uk.ac.reigate.repositories.academic.DepartmentRepository;


public class DepartmentServiceTest {
    
    private DepartmentRepository departmentRepository;
    
    private DepartmentService departmentService;
    
    Department department1
    
    Department department2
    
    @Before
    public void setup() {
        this.departmentRepository = mock(DepartmentRepository.class);
        this.departmentService = new DepartmentService(departmentRepository);
        
        department1 = new Department(id: 1, name: 'A',  description: 'Arts Department')
        department2 = new Department(id: 2, name: 'M',  description: 'Maths Department')
        
        when(departmentRepository.findAll()).thenReturn([department1, department2]);
        when(departmentRepository.findOne(1)).thenReturn(department1);
        when(departmentRepository.findOne(null)).thenThrow(new IllegalArgumentException());
        when(departmentRepository.save(any(Department.class))).thenReturn(department1);
    }
    
    
    @Test
    public void testFindDepartment() {
        List<Department> result = departmentService.findAll();
        verify(departmentRepository, times(1)).findAll()
        verifyNoMoreInteractions(departmentRepository)
    }
    
    @Test
    public void testFindDepartmentById() {
        Department result = departmentService.findById(1);
        verify(departmentRepository, times(1)).findOne(1)
        verifyNoMoreInteractions(departmentRepository)
    }
    
    @Test
    public void testSaveNewDepartment() {
        department1.id = null
        departmentService.save(department1);
        verify(departmentRepository, times(1)).save(department1)
        verifyNoMoreInteractions(departmentRepository)
    }
    
    @Test
    public void testSaveDepartment() {
        departmentService.save(department1);
        verify(departmentRepository, times(1)).save(department1)
        verifyNoMoreInteractions(departmentRepository)
    }
}


