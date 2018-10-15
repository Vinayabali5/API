package uk.ac.reigate.api;

import org.springframework.http.ResponseEntity

import uk.ac.reigate.dto.AcademicYearDto

public interface ICoreDataApi<DTO, ID> {
    
    ResponseEntity<List<DTO>> getAll()
    
    ResponseEntity<DTO> getById(ID id)
    
    ResponseEntity<DTO> create(DTO dto)
    
    ResponseEntity<DTO> update(ID id, DTO dto)
}
