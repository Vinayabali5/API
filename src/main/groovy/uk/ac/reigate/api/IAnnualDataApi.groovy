package uk.ac.reigate.api;

import java.util.List

import org.springframework.http.ResponseEntity

public interface IAnnualDataApi<T, ID> {
    
    ResponseEntity<List<?>> getAll(Integer yearId)
    
    ResponseEntity<?> getById(ID id)
}
